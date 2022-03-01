package com.spring.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.domain.Funcionario;
import com.spring.dtos.FuncionarioDTO;
import com.spring.service.FuncionarioService;

@RestController
@RequestMapping(value = "/funcionario")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@GetMapping(value = "/buscar/{id}", produces = "application/json")
	public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) throws Exception {
		FuncionarioDTO objDto = new FuncionarioDTO(funcionarioService.buscarid(id));
		return ResponseEntity.ok().body(objDto);
	}

	@GetMapping(value = "listar")
	public ResponseEntity<List<FuncionarioDTO>> listar() throws Exception {
		List<Funcionario> list = funcionarioService.listar();
		List<FuncionarioDTO> listDTO = new ArrayList<>();

		for (Funcionario obj : list) {
			listDTO.add(new FuncionarioDTO(obj));
		}
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@GetMapping(value = "listatecnico")
	public ResponseEntity<List<FuncionarioDTO>> listarTecnico() throws Exception {
		List<Funcionario> list = funcionarioService.listarTecnico();
		List<FuncionarioDTO> listDTO = new ArrayList<>();
		
		for (Funcionario obj : list) {
			listDTO.add(new FuncionarioDTO(obj));
		}
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping(value = "salvar")
	public ResponseEntity<FuncionarioDTO> salvar(@Valid @RequestBody FuncionarioDTO objDTO) throws Exception {
		Funcionario mewObj = funcionarioService.salvar(objDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mewObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioDTO objDTO)
			throws IllegalAccessException, Exception {

		FuncionarioDTO newObj = new FuncionarioDTO(funcionarioService.atualizar(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}

	@PutMapping(value = "/desativar/{id}")
	public ResponseEntity<FuncionarioDTO> desativarFuncionario(@PathVariable Long id) throws IllegalAccessException {
		funcionarioService.desativarFuncionario(id);
		return ResponseEntity.noContent().build();
	}

//	@DeleteMapping(value = "excluir/{id}")
//	public ResponseEntity<Void> delete(@PathVariable Long id) {
//		funcionarioService.delete(id);
//		return ResponseEntity.noContent().build();
//	}
}