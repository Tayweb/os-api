package com.spring.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Length(min = 5, message = "Nome deve ter no mínimo 5 caracteres")
	private String nome;

	@CPF
	@NotNull(message = "CPF não informado")
	private String cpf;

	@NotNull(message = "Telefone não informado")
	private String telefone;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataAdimissao;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataDemissao;
	private Integer cargo;
	private String login;
	private String senha;
	private boolean ativo;
}
