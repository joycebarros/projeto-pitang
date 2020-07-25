package com.br.desafio.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.br.desafio.controlador.ControladorUsuario;
import com.br.desafio.entidade.Telefone;
import com.br.desafio.entidade.Usuario;

@WebServlet(urlPatterns = "/cadastrar")
public class CadastrarUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("cadastrar.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Declaracao das variaveis e recuperacao dos parametros do request
		ArrayList<Telefone> arrayTelefones = new ArrayList<Telefone>();
		String nome = req.getParameter("nome");
		String email = req.getParameter("email");
		String senha = req.getParameter("senha");
		String listaTelefones = req.getParameter("listaTelefones");
		String[] arrTelefones = listaTelefones.split("&");

		// criacao do objeto usuario
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);

		// percorre o array de telefones vindo do request e cria para cada telefone um objeto telefone
		// correspondente e adiciona no arraylist de telefone
		for (int i = 0; i < arrTelefones.length; i++) {
			Telefone telefone = new Telefone();
			String[] arrayCamposTelefone = arrTelefones[i].split("%");
			telefone.setDdd(Short.parseShort(arrayCamposTelefone[0]));
			telefone.setNumero(arrayCamposTelefone[1]);
			telefone.setTipo(arrayCamposTelefone[2]);
			arrayTelefones.add(telefone);
		}
		
		usuario.setTelefones(arrayTelefones);

		// chama o controladorUsuario para inserir o Usuario. (retorno do metodo boolean)
		// se o metodo retornar true -> redireciona para a tela de login e informar que o usuario foi cadastrado com sucesso
		// se o metodo retornar false -> exibir uma mensagem tratada para o usuario dizendo que nao foi possivel realizar o cadastro - exibir tela de cadastro novamente
		if (ControladorUsuario.getInstance().cadastrarUsuario(usuario)) {
			req.setAttribute("mensagem", "Usuário cadastrado com sucesso!");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		} else { // retornou false
			req.setAttribute("erro", "Não foi possível realizar o cadastro, tente novamente!");
			req.getRequestDispatcher("cadastrar.jsp").forward(req, resp);
		}

	}
}
