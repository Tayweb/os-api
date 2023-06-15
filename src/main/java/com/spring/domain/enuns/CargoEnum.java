package com.spring.domain.enuns;

public enum CargoEnum {

	ASSISTENTE(0, "ASSISTENTE"), TECNICO(1, "TECNICO");

	private Integer cod;
	private String descricao;

	private CargoEnum(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public static CargoEnum toEnum(Integer cod) throws IllegalAccessException {
		if (cod == null) {
			return null;
		}
		
		for(CargoEnum x: CargoEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalAccessException("Cargo inválido!" + cod);
	}

}
