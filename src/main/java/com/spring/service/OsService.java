package com.spring.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.domain.Cliente;
import com.spring.domain.Funcionario;
import com.spring.domain.OS;
import com.spring.domain.enuns.PrioridadeEnum;
import com.spring.domain.enuns.StatusEnum;
import com.spring.dtos.OSDTO;
import com.spring.repository.OSRepository;
import com.spring.service.exceptions.ObjectNotFoundException;

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
		newObj.setPrioridade(PrioridadeEnum.toEnum(obj.getPrioridade().getCod()));
		newObj.setStatus(StatusEnum.toEnum(obj.getStatus().getCod()));

		Cliente cliente = clienteService.buscarid(obj.getCliente());
		Funcionario funcionario = funcionarioService.buscarid(obj.getFuncionario());

		newObj.setCliente(cliente);
		newObj.setFuncionario(funcionario);
		
		if (newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return osRepository.save(newObj);
	}

}
