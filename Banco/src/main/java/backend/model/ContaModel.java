package backend.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="BANCO_CONTA")
public class ContaModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Column(name="id")
	private Long id;
	
	@Column(name="limite")
	@NotNull
	private double limite;
	
	@Column(name="bloqueio")
	@NotNull
	private double bloqueio;
	
	@Column(name="saldo")
	@NotNull
	private double saldo;
	
	@OneToMany(mappedBy = "conta", fetch = FetchType.LAZY)
	private List<TransacaoModel> transacoes;
	
	public ContaModel() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public double getBloqueio() {
		return bloqueio;
	}

	public void setBloqueio(double bloqueio) {
		this.bloqueio = bloqueio;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public List<TransacaoModel> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<TransacaoModel> transacoes) {
		this.transacoes = transacoes;
	}

}
