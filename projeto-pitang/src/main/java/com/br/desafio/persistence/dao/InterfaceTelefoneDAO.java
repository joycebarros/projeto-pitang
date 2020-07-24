package com.br.desafio.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.br.desafio.entidade.Telefone;

public interface InterfaceTelefoneDAO {

	public void inserir(Connection conexao, Telefone telefone, int idUsuario) throws SQLException;
	
	public void excluir(Connection conexao, int idUsuario) throws SQLException;
}
