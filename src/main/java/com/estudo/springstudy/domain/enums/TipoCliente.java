package com.estudo.springstudy.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa física"),
	PESSOA_JURIDICA(2, "Pessoa juridica");
	
	private int code;
	private String descricao;
	
	private TipoCliente(int code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}

	public int getCode() {
		return code;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer code) {
		if (code == null) {
			return null;
		} else {
			for (TipoCliente x : TipoCliente.values()) {
				if (code.equals(x.getCode())) {
					return x;
				}
			}
		}		
		throw new IllegalArgumentException("Id inválido: " + code);
	}
}
