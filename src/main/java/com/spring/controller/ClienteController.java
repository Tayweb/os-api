package com.spring.controller;

import com.spring.domain.Cliente;
import com.spring.dtos.ClienteDTO;
import com.spring.service.ClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(clienteService.buscarid(id), ClienteDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok().body(clienteService.listar()
                .stream().map(cliente ->
                        mapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente mewObj = clienteService.salvar(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mewObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO objDTO) {
        return ResponseEntity.ok().body(mapper.map(clienteService.atualizar(id, objDTO), ClienteDTO.class));
    }
}