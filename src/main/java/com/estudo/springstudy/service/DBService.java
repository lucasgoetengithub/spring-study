package com.estudo.springstudy.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.estudo.springstudy.domain.Categoria;
import com.estudo.springstudy.domain.Cidade;
import com.estudo.springstudy.domain.Cliente;
import com.estudo.springstudy.domain.Endereco;
import com.estudo.springstudy.domain.Estado;
import com.estudo.springstudy.domain.ItemPedido;
import com.estudo.springstudy.domain.Pagamento;
import com.estudo.springstudy.domain.PagamentoComCartao;
import com.estudo.springstudy.domain.Pedido;
import com.estudo.springstudy.domain.Produto;
import com.estudo.springstudy.domain.enums.EstadoPagamento;
import com.estudo.springstudy.domain.enums.Perfil;
import com.estudo.springstudy.domain.enums.TipoCliente;
import com.estudo.springstudy.repository.CategoriaRepository;
import com.estudo.springstudy.repository.CidadeRepository;
import com.estudo.springstudy.repository.ClienteRepository;
import com.estudo.springstudy.repository.EnderecoRepository;
import com.estudo.springstudy.repository.EstadoRepository;
import com.estudo.springstudy.repository.ItemPedidoRepository;
import com.estudo.springstudy.repository.PagamentoRepository;
import com.estudo.springstudy.repository.PedidoRepository;
import com.estudo.springstudy.repository.ProdutoRepository;


@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Inform??tica");
		Categoria cat2 = new Categoria(null, "Escrit??rio");
		

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(
				Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Santa Catarina");
		Estado est2 = new Estado(null, "Sao Paulo");

		Cidade c1 = new Cidade(null, "Indaial", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Blumenau", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c3));
		est2.getCidades().addAll(Arrays.asList(c2));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		//Inicio - Cliente 1
		Cliente cli1 = new Cliente(null, "Lucas Eduardo Goeten", "lucasgoeten@email.com", "32165498764",
				TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("321654", "1234522"));

		Endereco end1 = new Endereco(null, "Rua avenida almeira", "300", "apto 1", "Jardim", "64654", cli1, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1));
		
		//Final - Cliente 1
		
		//Inicio - Cliente 2
		
		Cliente cli2 = new Cliente(null, "ADM  - Lucas Eduardo Goeten", "lucasgoetenadmin@email.com", "99200837042",
				TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));
		cli2.getTelefones().addAll(Arrays.asList("321654", "1234522"));
		cli2.addPerfis(Perfil.ADMIN);
		
		Endereco end2 = new Endereco(null, "Rua avenida Minas Gerais", "400", "apto 2", "Jardim", "64654", cli2, c1);
		
		cli2.getEnderecos().addAll(Arrays.asList(end2));
		
		//Final - Cliente 2

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2021 10:32"), cli1, end1);

		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);

		cli1.getPedidos().addAll(Arrays.asList(ped1));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		pedidoRepository.saveAll(Arrays.asList(ped1));
		pagamentoRepository.saveAll(Arrays.asList(pag1));

		ItemPedido itemPedido1 = new ItemPedido(ped1, p3, 10.00, 1, 3000.00);
		ItemPedido itemPedido2 = new ItemPedido(ped1, p2, 10.00, 1, 3000.00);

		ped1.getItemPedidos().addAll(Arrays.asList(itemPedido1, itemPedido2));
		p1.getItemPedidos().addAll(Arrays.asList(itemPedido1));
		p2.getItemPedidos().addAll(Arrays.asList(itemPedido2));

		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2));
	}
}
