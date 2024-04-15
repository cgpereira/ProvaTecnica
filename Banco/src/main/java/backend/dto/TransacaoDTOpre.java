package backend.dto;

import java.io.Serializable;

public class TransacaoDTOpre implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cartao;
	private String id;
	private DescricaoDTOSemStatus descricao;
	private FormaPagamentoDTO formaPagamento;

	public TransacaoDTOpre() {}

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

	public DescricaoDTOSemStatus getDescricao() {
		return descricao;
	}

	public void setDescricao(DescricaoDTOSemStatus descricao) {
		this.descricao = descricao;
	}

	public FormaPagamentoDTO getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

}
