package com.spring.domain.enuns;

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
	
	
	public static PrioridadeEnum toEnum(Integer cod) throws IllegalAccessException {
		if (cod == null) {
			return null;
		}
		
		for(PrioridadeEnum x: PrioridadeEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalAccessException("Prioridade inválida!" + cod);
	}

}
