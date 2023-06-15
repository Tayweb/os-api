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

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.domain.enuns.CargoEnum;

@Builder
@Getter
@Setter
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

	public Funcionario(Long id, String nome, @CPF String cpf, String telefone, CargoEnum cargo,
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
