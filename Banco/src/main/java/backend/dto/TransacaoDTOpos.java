package backend.dto;

import java.io.Serializable;

public class TransacaoDTOpos implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cartao;
	private String id;
	private DescricaoDTOComStatus descricao;
	private FormaPagamentoDTO formaPagamento;

	public TransacaoDTOpos() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCartao() {
		return cartao;
	}

	public void setCartao(String cartao) {
		this.cartao = cartao;
	}

	public DescricaoDTOComStatus getDescricao() {
		return descricao;
	}

	public void setDescricao(DescricaoDTOComStatus descricao) {
		this.descricao = descricao;
	}

	public FormaPagamentoDTO getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

}
