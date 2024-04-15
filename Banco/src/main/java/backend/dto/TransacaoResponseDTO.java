package backend.dto;

import java.io.Serializable;

public class TransacaoResponseDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private TransacaoDTOpos transacao;

	public TransacaoResponseDTO(TransacaoDTOpos transacao) {
		this.transacao = transacao;
	}

	public TransacaoDTOpos getTransacao() {
		return transacao;
	}

	public void setTransacao(TransacaoDTOpos transacao) {
		this.transacao = transacao;
	}
}
