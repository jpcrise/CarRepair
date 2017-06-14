package model;

import java.io.Serializable;

public class Usuario implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2918362861647432678L;
	private String login;
	private String senha;
	private String nome;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
