package com.spring.domain.enuns;

import com.spring.service.exceptions.NegocioException;

public enum PrioridadeEnum {

	BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

	private Integer cod;
	private String descricao;

	private PrioridadeEnum(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public static PrioridadeEnum toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for(PrioridadeEnum x: PrioridadeEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new NegocioException("Prioridade inválida!" + cod);
	}

	public static Integer consultarPrioridade(Integer cod) {
		if (cod == null) {
			return null;
		}

		for(PrioridadeEnum prioridadeEnum: PrioridadeEnum.values()) {
			if (cod.equals(prioridadeEnum.getCod())) {
				return prioridadeEnum.getCod();
			}
		}

		throw new NegocioException("Prioridade inválida!" + cod);
	}

}
