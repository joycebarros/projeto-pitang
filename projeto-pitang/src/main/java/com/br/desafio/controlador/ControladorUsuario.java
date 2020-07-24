package com.br.desafio.controlador;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.br.desafio.entidade.Telefone;
import com.br.desafio.entidade.Usuario;
import com.br.desafio.exception.UsuarioSenhaException;
import com.br.desafio.filter.UsuarioLogado;
import com.br.desafio.persistence.dao.InterfaceTelefoneDAO;
import com.br.desafio.persistence.dao.InterfaceUsuarioDAO;
import com.br.desafio.persistence.dao.TelefoneDAO;
import com.br.desafio.persistence.dao.UsuarioDAO;
import com.br.desafio.persistence.util.ConnectionFactory;

/**
 * @author joyce
 * 
 * Classe responsavel pela regra de negocio e por controlar em uma unica
 * transacao as operacoes de inserir, alterar, apagar e listar os usuarios
 *    
 * padrao Singleton.
 *
 */
public class ControladorUsuario {
	
	private volatile static ControladorUsuario instance;
	
	private ControladorUsuario() {
	}
	
	public static ControladorUsuario getInstance() {
		if (instance == null) {
			synchronized (ControladorUsuario.class) {
				if (instance == null) 
					instance = new ControladorUsuario();
			}
		}
			return instance;		
	}
	
	/**
	 * @param usuario
	 * @return true -> se a operacao de cadastrar o usuario com seus telefones foi bem sucedida. 
	 * @return false -> se houver alguma falha na transacao que cadastra o usuario com seus telefones.
	 * 
	 */
	public boolean cadastrarUsuario(Usuario usuario) {

		boolean isCadastroConcluido = false;
		Connection conexao = null;

		try {

			InterfaceUsuarioDAO usuarioDAO = new UsuarioDAO();
			InterfaceTelefoneDAO telefoneDAO = new TelefoneDAO();

			conexao = ConnectionFactory.getInstance().getConnection();

			// coloco o AutoCommit como falso para controlar a transacao de inserir os
			// usuarios e telefones na mesma transacao ao final com o comando commit.
			conexao.setAutoCommit(false);

			int idUsuario = usuarioDAO.inserir(conexao, usuario);

			Iterator<Telefone> iterator = usuario.getTelefones().iterator();

			while (iterator.hasNext()) {
				Telefone telefone = (Telefone) iterator.next();
				telefoneDAO.inserir(conexao, telefone, idUsuario);
			}

			// tudo Ok! commit transacao e retorna verdadeiro
			conexao.commit();
			isCadastroConcluido = true;

		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException sqlException) {
				System.out.println("Ocorreu uma falha na inclusao do usuario (Banco de Dados) com a seguinte mensagem: "
						+ e.getMessage());
			}
		} catch (Exception e) {
				System.out.println("Ocorreu uma falha na inclusao do usuario com a seguinte mensagem: " + e.getMessage());
		} finally {
			ConnectionFactory.getInstance().closeConnection(conexao);
		}
		return isCadastroConcluido;
	}

	/**
	 * @param idUsuario
	 * @return usuario
	 */
	public Usuario consultarUsuarioPorId(int idUsuario) {

		Usuario usuario = null;
		Connection conexao = null;

		try {

			InterfaceUsuarioDAO usuarioDAO = new UsuarioDAO();

			conexao = ConnectionFactory.getInstance().getConnection();

			usuario = usuarioDAO.consultarPorId(conexao, idUsuario);
			
		} catch (SQLException e) {
			System.out.println("Ocorreu uma falha na consulta do usuario (Banco de Dados) com a seguinte mensagem: " + e.getMessage());
			
		} catch (Exception e) {
			System.out.println("Ocorreu uma falha na consulta do usuario com a seguinte mensagem: " + e.getMessage());

		} finally {
			ConnectionFactory.getInstance().closeConnection(conexao);
		}
		return usuario;
	}

	
	public boolean alterarUsuario(Usuario usuario) {

		boolean isAlteracaoConcluida = false;
		Connection conexao = null;

		try {

			InterfaceUsuarioDAO usuarioDAO = new UsuarioDAO();
			InterfaceTelefoneDAO telefoneDAO = new TelefoneDAO();

			conexao = ConnectionFactory.getInstance().getConnection();

			// coloco o AutoCommit como falso para controlar a transacao de inserir os usuarios
			// e telefones na mesma transacao no final com o comando commit.
			conexao.setAutoCommit(false);

			usuarioDAO.alterar(conexao, usuario);
			
			telefoneDAO.excluir(conexao, usuario.getId());

			Iterator<Telefone> iterator = usuario.getTelefones().iterator();

			while (iterator.hasNext()) {
				Telefone telefone = (Telefone) iterator.next();
				telefoneDAO.inserir(conexao, telefone, usuario.getId());
			}

			// tudo Ok! commit transacao e retorna verdadeiro
			conexao.commit();
			isAlteracaoConcluida = true;

		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException sqlException) {
				System.out.println("Ocorreu uma falha na alteração do usuario (Banco de Dados) com a seguinte mensagem: " + e.getMessage());
				
			}
		} catch (Exception e) {
				System.out.println("Ocorreu uma falha na alteração do usuario com a seguinte mensagem: " + e.getMessage());
		
		} finally {
			ConnectionFactory.getInstance().closeConnection(conexao);
		}
		return isAlteracaoConcluida;
	}

	
	public boolean excluirUsuario(int idUsuario) {
		
		boolean isExclusaoConcluida = false;
		Connection conexao = null;

		try {

			InterfaceUsuarioDAO usuarioDAO = new UsuarioDAO();
			InterfaceTelefoneDAO telefoneDAO = new TelefoneDAO();

			conexao = ConnectionFactory.getInstance().getConnection();

			// coloco o AutoCommit como falso para controlar a transacao de inserir os usuarios
			// e telefones na mesma transacao no final com o comando commit.
			conexao.setAutoCommit(false);

			telefoneDAO.excluir(conexao, idUsuario);
			
			usuarioDAO.excluir(conexao, idUsuario);
			
			// tudo Ok! commit transacao e retorna verdadeiro
			conexao.commit();
			isExclusaoConcluida = true;

		} catch (SQLException e) {
			try {
				conexao.rollback();
			} catch (SQLException sqlException) {
				System.out.println("Ocorreu uma falha na exclusão do usuario (Banco de Dados) com a seguinte mensagem: " + e.getMessage());
				
			}
		} catch (Exception e) {
			System.out.println("Ocorreu uma falha na exclusão do usuario com a seguinte mensagem: " + e.getMessage());

		} finally {
			ConnectionFactory.getInstance().closeConnection(conexao);
		}
		return isExclusaoConcluida;
	
	}

	public UsuarioLogado consultar(String email, String senha) throws UsuarioSenhaException{
		
		UsuarioLogado usuarioLogado = null;
		
		Connection conexao = null;

		try {
			InterfaceUsuarioDAO usuarioDAO = new UsuarioDAO();

			conexao = ConnectionFactory.getInstance().getConnection();

			Usuario usuario = usuarioDAO.consultar(conexao, email, senha);
			
			if(usuario != null) {
				usuarioLogado = new UsuarioLogado();
				usuarioLogado.setId(usuario.getId());
				usuarioLogado.setEmail(usuario.getEmail());
			} else {
				throw new UsuarioSenhaException();
			}
			
		} catch (SQLException e) {
			System.out.println("Ocorreu uma falha na consulta ao usuario (Banco de Dados) com a seguinte mensagem: " + e.getMessage());
					
		} finally {
			ConnectionFactory.getInstance().closeConnection(conexao);
		}
		return usuarioLogado;
	}
	


	public List<Usuario> listarUsuarios(UsuarioLogado usuarioLogado) {

		List<Usuario> listaDeUsuarios = new ArrayList<>();

		Connection conexao = null;

		try {

			InterfaceUsuarioDAO usuarioDAO = new UsuarioDAO();

			conexao = ConnectionFactory.getInstance().getConnection();

			if(usuarioLogado.getEmail().equals("admin@admin.com"))			
				listaDeUsuarios = usuarioDAO.listar(conexao);
			else {
				Usuario usuario = usuarioDAO.consultarPorId(conexao, usuarioLogado.getId());
				listaDeUsuarios.add(usuario);
			}
				
			Collections.sort(listaDeUsuarios);
			
		} catch (SQLException e) {
			System.out.println("Ocorreu uma falha na listagem dos usuarios (Banco de Dados) com a seguinte mensagem: " + e.getMessage());
		
		} catch (Exception e) {
			System.out.println("Ocorreu uma falha na listagem dos usuarios com a seguinte mensagem: " + e.getMessage());

		} finally {
			ConnectionFactory.getInstance().closeConnection(conexao);
		}

		return listaDeUsuarios;
	}

}
