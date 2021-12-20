package com.estudo.springstudy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.estudo.springstudy.domain.Cidade;
import com.estudo.springstudy.domain.Cliente;
import com.estudo.springstudy.domain.Endereco;
import com.estudo.springstudy.domain.enums.TipoCliente;
import com.estudo.springstudy.dto.ClienteDTO;
import com.estudo.springstudy.dto.ClienteNewDTO;
import com.estudo.springstudy.repository.CidadeRepository;
import com.estudo.springstudy.repository.ClienteRepository;
import com.estudo.springstudy.repository.EnderecoRepository;
import com.estudo.springstudy.service.exception.DataIntegrityException;
import com.estudo.springstudy.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", tipo " + Cliente.class.getName(), null));
	}
	
	public Cliente update(Cliente obj) {
		Cliente newCliente = find(obj.getId());
		updateData(newCliente, obj);
		return repo.save(newCliente);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui pedidos");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente newCliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipoCliente()));
		Optional<Cidade> cidade = cidadeRepository.findById(clienteNewDTO.getCidadeId());
		Endereco newEndereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), newCliente, cidade.get());
		newCliente.getEnderecos().add(newEndereco);
		newCliente.getTelefones().add(clienteNewDTO.getTelefone1());
		if (clienteNewDTO.getTelefone2() != null) {
			newCliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		
		if (clienteNewDTO.getTelefone3() != null) {
			newCliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		
		return newCliente;
	}
	
	private void updateData(Cliente newCliente, Cliente oldCliente) {
		newCliente.setNome(oldCliente.getNome());
		newCliente.setEmail(oldCliente.getEmail());
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
}
