package com.spring.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.domain.enuns.Cargo;
import com.spring.domain.enuns.Status;

@Entity

public class Funcionario implements UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@CPF
	private String cpf;
	private String telefone;
	private Integer cargo;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataAdimissao;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataDemissao;
	private boolean ativo = true;
	private String login;
	private String senha;
	private String token = "";

	@JsonIgnore
	@OneToMany(mappedBy = "funcionario")
	private List<OS> list = new ArrayList<OS>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_role", uniqueConstraints = @UniqueConstraint(columnNames = {"funcionario_id",
			"role_id"}, name = "unique_role_user"), joinColumns = @JoinColumn(name = "funcionario_id",
			referencedColumnName = "id", table = "funcionario", unique = false, 
			foreignKey = @ForeignKey(name = "funcionario_fk", value = ConstraintMode.CONSTRAINT)), 
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", table = "role", unique = false, 
			updatable = false, foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)))
	private List<Role> roles = new ArrayList<Role>();

	public Funcionario() {
		super();
		this.setDataAdimissao(LocalDateTime.now());

	}

	public Funcionario(Long id, String nome, @CPF String cpf, String telefone, Cargo cargo, 
			String login, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.cargo = (cargo == null) ? 0 : cargo.getCod();
		this.dataAdimissao = (LocalDateTime.now());
		this.login = login;
		this.senha = senha;
		this.ativo = true;
	}

	// São os tipos de acessos do usuário
	@Override
	public Collection<Role> getAuthorities() {

		return roles;
	}

	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataAdimissao() {
		return dataAdimissao;
	}

	public void setDataAdimissao(LocalDateTime dataAdimissao) {
		this.dataAdimissao = dataAdimissao;
	}

	public LocalDateTime getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(LocalDateTime dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public Cargo getCargo() throws IllegalAccessException {
		return Cargo.toEnum(this.cargo);
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo.getCod();
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public List<OS> getList() {
		return list;
	}

	public void setList(List<OS> list) {
		this.list = list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	@Override
	public String getPassword() {

		return this.senha;
	}

	@JsonIgnore
	@Override
	public String getUsername() {

		return this.login;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {

		return true;
	}

}
