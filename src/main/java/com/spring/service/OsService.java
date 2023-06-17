package com.spring.service;

import com.spring.domain.OS;
import com.spring.domain.enuns.PrioridadeEnum;
import com.spring.domain.enuns.StatusEnum;
import com.spring.dtos.OSDTO;
import com.spring.repository.OSRepository;
import com.spring.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OsService {

	@Autowired
	private OSRepository osRepository;

	@Autowired
	private FuncionarioService funcionarioService;

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

	public OS salvar(@Valid OSDTO obj) {
		return fromDTO(obj);
	}

	public OS atualizar(@Valid OSDTO obj) {
		buscarid(obj.getId());
		return fromDTO(obj);
		
	}

	private OS fromDTO(OSDTO obj) {
		OS newOS = OS.builder()
				.id(obj.getId())
				.observacoes(obj.getObservacoes())
				.prioridade(PrioridadeEnum.consultarPrioridade(obj.getPrioridade()))
				.status(StatusEnum.consultarStatus(obj.getStatus()))
				.cliente(clienteService.buscarid(obj.getCliente()))
				.funcionario(funcionarioService.buscarid(obj.getFuncionario()))
				.dataAbertura(LocalDateTime.now())
				.build();

		if (newOS.getStatus().getCod().equals(2)) {
			newOS.setDataFechamento(LocalDateTime.now());
		}
		
		return osRepository.save(newOS);
	}

}
