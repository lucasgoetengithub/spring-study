package com.estudo.springstudy.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int code;
	private String descricao;
	
	private EstadoPagamento(int code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}
	
	public int getCode() {
		return code;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoPagamento toEnum(Integer code) {
		if (code == null) {
			return null;
		} else {
			for (EstadoPagamento x : EstadoPagamento.values()) {
				if (code.equals(x.getCode())) {
					return x;
				}
			}
		}		
		throw new IllegalArgumentException("Id inválido: " + code);
	}
}
