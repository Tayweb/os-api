package com.spring.dtos;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import com.spring.domain.Cliente;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotEmpty(message = "Campo Nome obrigatório")
	private String nome;

	@CPF
	@NotEmpty(message = "Campo CPF obrigatório")
	private String cpf;
	
	@NotEmpty(message = "Campo Telefone obrigatório")
	private String telefone;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Cliente obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.telefone = obj.getTelefone();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
