package com.estudo.springstudy.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.estudo.springstudy.domain.enums.EstadoPagamento;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	private Integer estadoPagamento;
	
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId
	private Pedido pedido;
	
	public Pagamento() {
	}

	public Pagamento(Integer id, EstadoPagamento estadoPagamento, Pedido pedido) {
		super();
		this.id = id;
		this.estadoPagamento = estadoPagamento.getCode();
		this.pedido = pedido;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstadoPagamento() {
		return EstadoPagamento.toEnum(estadoPagamento);
	}

	public void setEstadoPagamento(EstadoPagamento estadoPagamento) {
		this.estadoPagamento = estadoPagamento.getCode();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		return Objects.equals(id, other.id);
	}	
}
