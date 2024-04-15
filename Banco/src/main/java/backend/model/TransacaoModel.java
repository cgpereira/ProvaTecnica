package backend.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="BANCO_TRANSACAO")
public class TransacaoModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@NotNull
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="cartao", referencedColumnName="id",nullable=false) 
	@NotNull
	private ContaModel conta;
	
	@Column(name="valor")
	@NotNull
	private double valor;
	
	@Size(max = 19)
	@Column(name="dataHora")
	@NotNull
	private String dataHora;
	
	@Size(max = 255)
	@Column(name="estabelecimento")
	@NotNull
	private String estabelecimento;
	
	@Column(name="nsu")
	private Long nsu;

	@Column(name="codigoAutorizacao")
	private Long codigoAutorizacao;

	@Size(max = 127)
	@Column(name="status")
	private String status;
	
	@Size(max = 127)
	@Column(name="tipo")
	@NotNull
	private String tipo;
	
    @Column(name="parcelas")
	@NotNull
	private Integer parcelas;
	
	public TransacaoModel() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	public Long getCodigoAutorizacao() {
		return codigoAutorizacao;
	}

	public void setCodigoAutorizacao(Long codigoAutorizacao) {
		this.codigoAutorizacao = codigoAutorizacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}

	public ContaModel getConta() {
		return conta;
	}

	public void setConta(ContaModel conta) {
		this.conta = conta;
	}

}
