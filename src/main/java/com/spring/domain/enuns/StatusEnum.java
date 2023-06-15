package com.spring.domain.enuns;

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

	public static StatusEnum toEnum(Integer cod) throws IllegalAccessException {
		if (cod == null) {
			return null;
		}

		for (StatusEnum x : StatusEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalAccessException("Status inválido!" + cod);
	}

}
