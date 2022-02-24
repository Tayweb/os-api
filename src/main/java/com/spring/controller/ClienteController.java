package com.spring.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.domain.Cliente;
import com.spring.dtos.ClienteDTO;
import com.spring.service.ClienteService;


@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/buscar/{id}", produces = "application/json")
	public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
		ClienteDTO objDto = new ClienteDTO(clienteService.buscarid(id));
		return ResponseEntity.ok().body(objDto);
	}
	
	@GetMapping(value = "listar")
	public ResponseEntity<List<ClienteDTO>> listar() {
		List<Cliente> list = clienteService.listar();
		List<ClienteDTO> listDTO = new ArrayList<>();

		for (Cliente obj : list) {
			listDTO.add(new ClienteDTO(obj));
		}
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping(value = "salvar")
	public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteDTO objDTO) {
		Cliente mewObj = clienteService.salvar(objDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mewObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO objDTO) {

		ClienteDTO newObj = new ClienteDTO(clienteService.atualizar(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}
	
	@DeleteMapping(value = "excluir/{id}")
	public ResponseEntity<Void>delete(@PathVariable Long id){
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
}