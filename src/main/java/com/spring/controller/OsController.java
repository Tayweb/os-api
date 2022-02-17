package com.spring.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.domain.OS;
import com.spring.dtos.OSDTO;
import com.spring.service.OsService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/os")
public class OsController {

	@Autowired
	private OsService osService;

	@GetMapping(value = "/buscar/{id}")
	public ResponseEntity<OSDTO> buscarPorId(@PathVariable Long id) throws Exception {
		OSDTO objDTO = new OSDTO(osService.buscarid(id));
		return ResponseEntity.ok().body(objDTO);
	}

	@GetMapping(value = "/listar")
	public ResponseEntity<List<OSDTO>> listar() throws Exception {
		List<OS> list = osService.listar();
		List<OSDTO> listDTO = new ArrayList<>();

		for (OS obj : list) {
			listDTO.add(new OSDTO(obj));
		}

		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping(value = "/salvar")
	public ResponseEntity<OSDTO> salvar(@Valid @RequestBody OSDTO obj) throws Exception {
		obj = new OSDTO(osService.salvar(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<OSDTO>atualizar(@Valid @RequestBody OSDTO obj) throws Exception{
		obj = new OSDTO(osService.atualizar(obj));
		return ResponseEntity.ok().body(obj);
	}

}
