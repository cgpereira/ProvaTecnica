package backend.dto;

import java.io.Serializable;

public class FormaPagamentoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String tipo;
	private String parcelas;

	public FormaPagamentoDTO() {}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getParcelas() {
		return parcelas;
	}

	public void setParcelas(String parcelas) {
		this.parcelas = parcelas;
	}

}
