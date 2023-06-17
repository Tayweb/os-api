package com.spring.domain.enuns;

import com.spring.service.exceptions.NegocioException;

public enum StatusEnum {

	ABERTO(0, "ABERTO"), ANDAMENTO(1, "ANDAMENTO"), ENCERRADO(2, "ENCERRADO");

	private Integer cod;
	private String descricao;

	private StatusEnum(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static StatusEnum toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (StatusEnum x : StatusEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new NegocioException("Status inválido!" + cod);
	}

	public static Integer consultarStatus(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (StatusEnum statusEnum : StatusEnum.values()) {
			if (cod.equals(statusEnum.getCod())) {
				return statusEnum.getCod();
			}
		}

		throw new NegocioException("Status inválido!" + cod);
	}

}
