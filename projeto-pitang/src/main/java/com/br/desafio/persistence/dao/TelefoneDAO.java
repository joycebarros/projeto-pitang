package com.br.desafio.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.br.desafio.entidade.Telefone;

public class TelefoneDAO implements InterfaceTelefoneDAO {

	/**
	 * @author joyce
	 * Metodo responsavel por inserir um telefone
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement
	 * 
	 */
	public void inserir(Connection conexao, Telefone telefone, int idUsuario) throws SQLException {
		String sql = "INSERT INTO TELEFONE (DDD, NUMERO, TIPO, USUARIO_ID) VALUES (?, ?, ?, ?)";
		PreparedStatement prst = conexao.prepareStatement(sql);
		prst.setInt(1, telefone.getDdd());
		prst.setString(2, telefone.getNumero());
		prst.setString(3, telefone.getTipo());
		prst.setInt(4, idUsuario);
		prst.execute();
		prst.close();
	}

	/**
	 * @author joyce
	 * Metodo responsavel por excluir um telefone
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement
	 * 
	 */
	public void excluir(Connection conexao, int idUsuario) throws SQLException {
		String sql = "DELETE FROM TELEFONE WHERE USUARIO_ID = ?";

		PreparedStatement prst = conexao.prepareStatement(sql);
		prst.setInt(1, idUsuario);

		prst.execute();
		prst.close();
	}
}
