package backend.dto;

import java.io.Serializable;

public class LoginDTOpre implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	public LoginDTOpre() {}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String password) {
		this.senha = password;
	}
	
}
