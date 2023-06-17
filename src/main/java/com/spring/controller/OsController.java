package com.spring.controller;

import com.spring.domain.OS;
import com.spring.dtos.OSDTO;
import com.spring.service.OsService;
import org.modelmapper.ModelMapper;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/os")
public class OsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private OsService osService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OSDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(osService.buscarid(id), OSDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<OSDTO>> listar() {
        List<OS> list = osService.listar();
        return ResponseEntity.ok().body(osService.listar()
                .stream()
                .map(os -> mapper.map(os, OSDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<OSDTO> salvar(@Valid @RequestBody OSDTO obj) {
        OSDTO osdto = mapper.map(osService.salvar(obj), OSDTO.class);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(osdto.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<OSDTO> atualizar(@Valid @RequestBody OSDTO obj) {
        return ResponseEntity.ok().body(mapper.map(osService.atualizar(obj), OSDTO.class));
    }

}
