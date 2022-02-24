package com.spring.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.domain.Funcionario;

public class FuncionarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotEmpty(message = "Campo Nome obrigatório")
	private String nome;

	@CPF
	@NotEmpty(message = "Campo CPF obrigatório")
	private String cpf;

	@NotEmpty(message = "Campo Telefone obrigatório")
	private String telefone;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataAdimissao;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataDemissao;
	private Integer cargo;
	private String login;
	private String senha;
	private boolean ativo;

	public FuncionarioDTO() {
		super();
	}

	public FuncionarioDTO(Funcionario obj) throws IllegalAccessException {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.telefone = obj.getTelefone();
		this.dataAdimissao = obj.getDataAdimissao();
		this.dataDemissao = obj.getDataDemissao();
		this.login = obj.getLogin();
		this.senha = obj.getSenha();
		this.cargo = obj.getCargo().getCod();
		this.ativo = obj.isAtivo();
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDateTime getDataAdimissao() {
		return dataAdimissao;
	}

	public void setDataAdimissao(LocalDateTime dataAdimissao) {
		this.dataAdimissao = dataAdimissao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(LocalDateTime dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public Integer getCargo() {
		return cargo;
	}

	public void setCargo(Integer cargo) {
		this.cargo = cargo;
	}

}
