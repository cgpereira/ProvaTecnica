package backend.dto;

import java.io.Serializable;

public class TransacaoRequestDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TransacaoDTOpre transacao;

	public TransacaoRequestDTO() {}

	public TransacaoDTOpre getTransacao() {
		return transacao;
	}

	public void setTransacao(TransacaoDTOpre transacao) {
		this.transacao = transacao;
	}
}
