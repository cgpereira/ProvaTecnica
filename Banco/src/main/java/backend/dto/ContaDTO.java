package backend.dto;

import java.io.Serializable;

public class ContaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private Double limite;
	private Double bloqueio;
	private Double saldo;	

	public ContaDTO() {}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLimite() {
		return limite;
	}

	public void setLimite(Double limite) {
		this.limite = limite;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(Double bloqueio) {
		this.bloqueio = bloqueio;
	}

}
