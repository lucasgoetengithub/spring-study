package com.estudo.springstudy.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.springstudy.domain.ItemPedido;
import com.estudo.springstudy.domain.Pagamento;
import com.estudo.springstudy.domain.PagamentoComBoleto;
import com.estudo.springstudy.domain.PagamentoComCartao;
import com.estudo.springstudy.domain.Pedido;
import com.estudo.springstudy.domain.enums.EstadoPagamento;
import com.estudo.springstudy.repository.ItemPedidoRepository;
import com.estudo.springstudy.repository.PagamentoRepository;
import com.estudo.springstudy.repository.PedidoRepository;
import com.estudo.springstudy.repository.ProdutoRepository;
import com.estudo.springstudy.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", tipo " + Pedido.class.getName(), null));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItemPedidos()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findById(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItemPedidos());
		
		return repo.save(obj);
	}
}
