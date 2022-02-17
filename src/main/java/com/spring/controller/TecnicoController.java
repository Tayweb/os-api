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

import com.spring.domain.Tecnico;
import com.spring.dtos.TecnicoDTO;
import com.spring.service.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnico")
public class TecnicoController {

	@Autowired
	private TecnicoService tecnicoService;

	@GetMapping(value = "/buscar/{id}", produces = "application/json")
	public ResponseEntity<TecnicoDTO> buscarPorId(@PathVariable Long id) {
		TecnicoDTO objDto = new TecnicoDTO(tecnicoService.buscarid(id));
		return ResponseEntity.ok().body(objDto);
	}

	@GetMapping(value = "listar")
	public ResponseEntity<List<TecnicoDTO>> listar() {
		List<Tecnico> list = tecnicoService.listar();
		List<TecnicoDTO> listDTO = new ArrayList<>();

		for (Tecnico obj : list) {
			listDTO.add(new TecnicoDTO(obj));
		}
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping(value = "salvar")
	public ResponseEntity<TecnicoDTO> salvar(@Valid @RequestBody TecnicoDTO objDTO) {
		Tecnico mewObj = tecnicoService.salvar(objDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mewObj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<TecnicoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TecnicoDTO objDTO) {

		TecnicoDTO newObj = new TecnicoDTO(tecnicoService.atualizar(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}
	
	@DeleteMapping(value = "excluir/{id}")
	public ResponseEntity<Void>delete(@PathVariable Long id){
		tecnicoService.delete(id);
		return ResponseEntity.noContent().build();
	}
}