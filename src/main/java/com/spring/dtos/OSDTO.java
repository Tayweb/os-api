package com.spring.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OSDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataAbertura;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataFechamento;
	private Integer prioridade;
	@NotNull(message = "Observações não informado")
	private String observacoes;
	private Integer status;
	private Long funcionario;
	private Long cliente;
}
