package com.auth.model;

public class Login {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setLogin(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Login(String login, String password) {
		super();
		this.username = login;
		this.password = password;
	}

}
