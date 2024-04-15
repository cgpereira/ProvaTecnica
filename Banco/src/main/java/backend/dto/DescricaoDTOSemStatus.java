package backend.dto;

import java.io.Serializable;

public class DescricaoDTOSemStatus implements Serializable{

	private static final long serialVersionUID = 1L;

	private String valor;
	private String dataHora;
	private String estabelecimento;

	public DescricaoDTOSemStatus() {}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
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

}
