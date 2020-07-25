package com.br.desafio.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.br.desafio.entidade.Telefone;
import com.br.desafio.entidade.Usuario;

public class UsuarioDAO implements InterfaceUsuarioDAO {

	/**
	 * @author joyce
	 * Método responsável por inserir um usuário.
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement.
	 * Através da interface Statment é utilizada a constante que indica que as chaves geradas 
	 * devem estar disponíveis para recuperação e por meio da interface ResultSet percorremos o resultado 
	 * do banco de dados para recuperar o id do usuario que é gerado (auto_increment) pelo MySQL.
	 * Por fim, o método retorna o id do usuário para que através da classe ControladorUsuario ao cadastrar 
	 * o usuário, os telefones sejam inseridos na mesma transação. 
	 * @return int idUsuarioInserido 
	 */
	public int inserir(Connection conexao, Usuario usuario) throws SQLException {
		
		int idUsuarioInserido = 0;
		
		PreparedStatement stmt;
		String sql = "INSERT INTO USUARIO (NOME,EMAIL,SENHA) VALUES (?,?,?)";

		stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getEmail());
		stmt.setString(3, usuario.getSenha());

		stmt.execute();
		
		ResultSet generatedKeys = stmt.getGeneratedKeys();
		if (generatedKeys.next())
			idUsuarioInserido = generatedKeys.getInt(1);
		
		stmt.close();

		return idUsuarioInserido;
	}

	/**
	 *@author joyce
	 * Método responsável por alterar um usuário.
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement.
	 */
	@Override
	public void alterar(Connection conexao, Usuario usuario) throws SQLException {
		
		String sql = "UPDATE USUARIO SET NOME = ?, EMAIL = ? ,SENHA = ? WHERE ID = ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);
		
		stmt.setString(1, usuario.getNome());
		stmt.setString(2, usuario.getEmail());
		stmt.setString(3, usuario.getSenha());
		stmt.setInt(4, usuario.getId());
		
		stmt.execute();
		
		stmt.close();
	}

	/**
	 * @author joyce
	 * Método responsável por excluir um usuário.
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement.
	 */
	public void excluir(Connection conexao, int id) throws SQLException {
		
			String sql = "DELETE FROM USUARIO WHERE ID = ?";

			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
	}

	/**
	 * @author joyce
	 * Método responsável por consultar um usuário por e-mail e senha.
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement.
	 * Através da interface ResultSet percorremos o resultado do banco de dados e recuperamos o id, e-mail,
	 * nome e senha do usuario. 
	 * @return usuario
	 */
	public Usuario consultar(Connection conexao, String email, String senha) throws SQLException {

		Usuario usuario = null;
		ResultSet resultSet = null;
		
		String sql = "SELECT * FROM USUARIO U WHERE U.EMAIL = ? AND U.SENHA = ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setString(1, email);
		stmt.setString(2, senha);

		resultSet = stmt.executeQuery();
		
		while (resultSet.next()) {
			usuario = new Usuario();
			usuario.setId(resultSet.getInt("U.ID"));
			usuario.setEmail(resultSet.getString("EMAIL"));
			usuario.setNome(resultSet.getString("NOME"));
			usuario.setSenha(resultSet.getString("SENHA"));
		}
		
		stmt.close();
		return usuario;
	}

	/**
	 * @author joyce
	 * Método responsável por consultar um usuário por id.
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement.
	 * Através da interface ResultSet percorremos o resultado do banco de dados e recuperamos: id, email, nome, 
	 * senha e telefones do usuário. Tendo em vista que um usuario pode ter vários telefones, é utilizado um 
	 * HasMap de String e usuario, sendo a chave, em String, o ID do usuario.  
	 * Retornando o usuário e sua lista de telefones.
	 * @return usuario
	 * 
	 */
	@Override
	public Usuario consultarPorId(Connection conexao, int id) throws SQLException {

		Usuario usuario = null;
		
		ResultSet resultSet = null;

		String sql = "SELECT * FROM USUARIO U INNER JOIN TELEFONE T ON U.ID = T.USUARIO_ID WHERE U.ID = ?";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		stmt.setInt(1, id);

		resultSet = stmt.executeQuery();

		HashMap<String, Usuario> hashIdObjetoUsuario = new HashMap<String, Usuario>();

		while (resultSet.next()) {

			if (hashIdObjetoUsuario.containsKey(String.valueOf(resultSet.getInt("U.ID")))) {
				usuario = hashIdObjetoUsuario.get(String.valueOf(resultSet.getInt("U.ID")));
			} else { // se nao existir
				usuario = new Usuario();
				usuario.setId(resultSet.getInt("U.ID"));
				usuario.setEmail(resultSet.getString("EMAIL"));
				usuario.setNome(resultSet.getString("NOME"));
				usuario.setSenha(resultSet.getString("SENHA"));
			}
			
			List<Telefone> listaDeTelefones = usuario.getTelefones();
			
			Telefone telefone = new Telefone();
			telefone.setId(resultSet.getInt("T.ID"));
			telefone.setDdd(resultSet.getInt("DDD"));
			telefone.setNumero(resultSet.getString("NUMERO"));
			telefone.setTipo(resultSet.getString("TIPO"));
			listaDeTelefones.add(telefone);
			
			usuario.setTelefones(listaDeTelefones);
			hashIdObjetoUsuario.put(String.valueOf(usuario.getId()), usuario);
		}
		
		stmt.close();
		return usuario;
	}
	
	/**
	 * @author joyce
	 * Método responsável por listar os usuários.
	 * Para executar o comando SQL é utilizada a interface java.sql.PreparedStatement.
	 * Através da interface ResultSet percorremos o resultado do banco de dados e recuperamos: id, email, nome, 
	 * senha e telefones dos usuários. Tendo em vista que um usuario pode ter vários telefones, é utilizado um 
	 * HasMap de String e usuario, sendo a chave, em String, o ID do usuario.  
	 * Retornando a lista de usuários e seus telefones.
	 * @return lista
	 */
	public List<Usuario> listar(Connection conexao) throws SQLException {

		List<Usuario> lista = null;
		Usuario usuario = null;
		ResultSet resultSet = null;
		
		String sql = "SELECT * FROM USUARIO U INNER JOIN TELEFONE T ON U.ID = T.USUARIO_ID ORDER BY U.NOME, T.TIPO";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		resultSet = stmt.executeQuery();

		HashMap<String, Usuario> hashIdObjetoUsuario = new HashMap<String, Usuario>();

		while (resultSet.next()) {

			if (hashIdObjetoUsuario.containsKey(String.valueOf(resultSet.getInt("ID")))) { 
				usuario = hashIdObjetoUsuario.get(String.valueOf(resultSet.getInt("ID")));
			} else { //se nao existir
				usuario = new Usuario();
				usuario.setId(resultSet.getInt("U.ID"));
				usuario.setEmail(resultSet.getString("EMAIL"));
				usuario.setNome(resultSet.getString("NOME"));
				usuario.setSenha(resultSet.getString("SENHA"));
			}

			Telefone telefone = new Telefone();
			telefone.setId(resultSet.getInt("T.ID"));
			telefone.setDdd(resultSet.getInt("DDD"));
			telefone.setNumero(resultSet.getString("NUMERO"));
			telefone.setTipo(resultSet.getString("TIPO"));

			List<Telefone> listaDeTelefones = usuario.getTelefones();
			listaDeTelefones.add(telefone);

			usuario.setTelefones(listaDeTelefones);
			hashIdObjetoUsuario.put(String.valueOf(usuario.getId()), usuario);
		}

		if (!hashIdObjetoUsuario.isEmpty()) {
			lista = new ArrayList<Usuario>();
			lista.addAll(hashIdObjetoUsuario.values());
		}

		stmt.close();
		return lista;
	}

}
