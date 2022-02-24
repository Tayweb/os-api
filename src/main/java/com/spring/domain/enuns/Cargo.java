package com.spring.domain.enuns;

public enum Cargo {

	ASSISTENTE(0, "ASSISTENTE"), TECNICO(1, "TECNICO");

	private Integer cod;
	private String descricao;

	private Cargo(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public static Cargo toEnum(Integer cod) throws IllegalAccessException {
		if (cod == null) {
			return null;
		}
		
		for(Cargo x: Cargo.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalAccessException("Cargo inválido!" + cod);
	}

}
