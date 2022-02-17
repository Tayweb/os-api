package com.spring.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Cliente;
import com.spring.domain.OS;
import com.spring.domain.Tecnico;
import com.spring.domain.enuns.Prioridade;
import com.spring.domain.enuns.Status;
import com.spring.dtos.OSDTO;
import com.spring.repository.OSRepository;
import com.spring.service.exceptions.ObjectNotFoundException;

@Service
public class OsService {

	@Autowired
	private OSRepository osRepository;

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	public OS buscarid(Long id) {
		Optional<OS> obj = osRepository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id:" + id + ", Tipo:" + OS.class.getName()));
	}

	public List<OS> listar() {
		return osRepository.findAll();
	}

	public OS salvar(@Valid OSDTO obj) throws Exception {
		return fromDTO(obj);
	}

	public OS atualizar(@Valid OSDTO obj) throws Exception {
		buscarid(obj.getId());
		return fromDTO(obj);
		
	}

	private OS fromDTO(OSDTO obj) throws Exception {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));

		Tecnico tecnico = tecnicoService.buscarid(obj.getTecnico());
		Cliente cliente = clienteService.buscarid(obj.getCliente());

		newObj.setCliente(cliente);
		newObj.setTecnico(tecnico);
		
		if (newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return osRepository.save(newObj);
	}

}
