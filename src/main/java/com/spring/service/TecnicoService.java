package com.spring.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Pessoa;
import com.spring.domain.Tecnico;
import com.spring.dtos.TecnicoDTO;
import com.spring.repository.PessoaRepository;
import com.spring.repository.TecnicoRepository;
import com.spring.service.exceptions.DataIntegratyViolationException;
import com.spring.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico buscarid(Long id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id); // Já que está Optional, ele pode encontrar o id ou não
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Tecnico.class.getName()));
	}

	public List<Tecnico> listar() {
		return tecnicoRepository.findAll();
	}

	public Tecnico salvar(TecnicoDTO objDTO) {
		if (verificarCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}
		Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return tecnicoRepository.save(newObj);
	}

	public Tecnico atualizar(Long id, @Valid TecnicoDTO objDTO) {
		Tecnico tecnico = buscarid(id);

		if (verificarCPF(objDTO) != null && verificarCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado");
		}

		tecnico.setNome(objDTO.getNome());
		tecnico.setCpf(objDTO.getCpf());
		tecnico.setTelefone(objDTO.getTelefone());

		return tecnicoRepository.save(tecnico);
	}
	
	public void delete(Long id) {
		Tecnico obj = buscarid(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço ativo, não pode ser excluido");
		}
		 tecnicoRepository.deleteById(id);
		
	}

	private Pessoa verificarCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.buscarCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}

		return null;
	}

	

}
