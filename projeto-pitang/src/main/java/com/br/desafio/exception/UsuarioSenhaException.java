package com.br.desafio.exception;

@SuppressWarnings("serial")
public class UsuarioSenhaException extends Exception {

	public UsuarioSenhaException() {
		System.out.println("Usuário e/ou senha inválidos");
	}

}
