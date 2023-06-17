package com.spring.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.spring.domain.Funcionario;
import com.spring.dtos.FuncionarioDTO;
import com.spring.service.FuncionarioService;

@RestController
@RequestMapping(value = "/funcionario")
public class FuncionarioController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(funcionarioService.buscarId(id), FuncionarioDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> listar() {
        return ResponseEntity.ok().body(funcionarioService.listar()
                .stream().map(funcionario ->
                        mapper.map(funcionario, FuncionarioDTO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "listatecnico")
    public ResponseEntity<List<FuncionarioDTO>> listarTecnico(){
        return ResponseEntity.ok().body(funcionarioService.listarTecnico()
                .stream().map(funcionario ->
                        mapper.map(funcionario, FuncionarioDTO.class))
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> salvar(@Valid @RequestBody FuncionarioDTO objDTO) throws Exception {
        Funcionario mewObj = funcionarioService.salvar(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(mewObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionarioDTO objDTO) throws Exception {
        return ResponseEntity.ok().body(mapper.map(funcionarioService.atualizar(id, objDTO), FuncionarioDTO.class));
    }

    @PutMapping(value = "/desativar/{id}")
    public ResponseEntity<FuncionarioDTO> desativarFuncionario(@PathVariable Long id) {
        funcionarioService.desativarFuncionario(id);
        return ResponseEntity.noContent().build();
    }
}