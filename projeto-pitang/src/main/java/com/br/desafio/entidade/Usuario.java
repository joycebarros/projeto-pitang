package com.br.desafio.entidade;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements Comparable<Usuario> {

	private int id;
	private String nome;
	private String email;
	private String senha;
	private List<Telefone> telefones;

	public Usuario() {
		this.nome = "";
		this.email = "";
		this.senha = "";
		this.telefones = new ArrayList<Telefone>();
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public int compareTo(Usuario usuario) {
		return this.getNome().compareTo(usuario.getNome());
	}

}
