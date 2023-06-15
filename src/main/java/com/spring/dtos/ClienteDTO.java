package com.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Length(min = 5, message = "Nome deve ter no mínimo 5 caracteres")
	private String nome;

	@CPF
	@NotNull(message = "CPF não informado")
	private String cpf;
	
	@NotBlank(message = "Campo Telefone obrigatório")
	private String telefone;

}
