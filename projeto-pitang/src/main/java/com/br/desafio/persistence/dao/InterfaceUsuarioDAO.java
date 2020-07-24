package com.br.desafio.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.br.desafio.entidade.Usuario;

public interface InterfaceUsuarioDAO {

	public int inserir(Connection conexao, Usuario usuario) throws SQLException;

	public void alterar(Connection conexao,Usuario usuario) throws SQLException;

	public void excluir(Connection conexao, int id) throws SQLException;

	public Usuario consultar(Connection conexao, String email, String senha) throws SQLException;
	
	public Usuario consultarPorId(Connection conexao, int id) throws SQLException;

	public List<Usuario> listar(Connection conexao) throws SQLException;
}
