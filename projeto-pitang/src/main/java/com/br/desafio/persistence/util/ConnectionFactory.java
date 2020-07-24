package com.br.desafio.persistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private volatile static ConnectionFactory instance;
	
	private String url = "jdbc:mysql://us-cdbr-iron-east-01.cleardb.net/heroku_ff17b000419a542?reconnect=true";
	private String usuario = "b1dc3bfeca0dee";
	private String senha = "e034f6d1";

	private ConnectionFactory() {
	}

	public static ConnectionFactory getInstance() {
		if(instance == null) {
			synchronized (ConnectionFactory.class) {
				if(instance == null)
					instance = new ConnectionFactory();
			}
		}
		return instance;
	}

	public Connection getConnection() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			return DriverManager.getConnection(url, usuario, senha);
		} catch (Exception e) {
			System.out.println("Falha obtendo a conexao com o banco de dados");
			return null;
		}
	}

	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Não foi possivel encerrar a conexao com o banco de dados");
		}
	}

}
