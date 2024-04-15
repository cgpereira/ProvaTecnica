package backend.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class LoginDTOpos implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String token;
	private LocalDateTime validade;

	public LoginDTOpos() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getValidade() {
		return validade;
	}

	public void setValidade(LocalDateTime validade) {
		this.validade = validade;
	}

}
