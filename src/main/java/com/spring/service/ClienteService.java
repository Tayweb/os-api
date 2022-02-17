package com.spring.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Cliente;
import com.spring.domain.Pessoa;
import com.spring.dtos.ClienteDTO;
import com.spring.repository.ClienteRepository;
import com.spring.repository.PessoaRepository;
import com.spring.service.exceptions.DataIntegratyViolationException;
import com.spring.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente buscarid(Long id) {
		Optional<Cliente> obj = tecnicoRepository.findById(id); // Já que está Optional, ele pode encontrar o id ou não
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Cliente.class.getName()));
	}

	public List<Cliente> listar() {
		return tecnicoRepository.findAll();
	}

	public Cliente salvar(ClienteDTO objDTO) {
		if (verificarCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}
		Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return tecnicoRepository.save(newObj);
	}

	public Cliente atualizar(Long id, @Valid ClienteDTO objDTO) {
		Cliente tecnico = buscarid(id);

		if (verificarCPF(objDTO) != null && verificarCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}

		tecnico.setNome(objDTO.getNome());
		tecnico.setCpf(objDTO.getCpf());
		tecnico.setTelefone(objDTO.getTelefone());

		return tecnicoRepository.save(tecnico);
	}
	
	public void delete(Long id) {
		Cliente obj = buscarid(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço ativo, não pode ser excluido");
		}
		 tecnicoRepository.deleteById(id);
		
	}

	private Pessoa verificarCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.buscarCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}

		return null;
	}

	

}
