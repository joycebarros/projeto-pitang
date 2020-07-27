package com.br.desafio.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.br.desafio.controlador.ControladorUsuario;
import com.br.desafio.exception.UsuarioSenhaException;
import com.br.desafio.filter.UsuarioLogado;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		String botao = req.getParameter("botao");

		if (botao.equals("entrar")) {
			
			String email = req.getParameter("email");
			String senha = req.getParameter("senha");
			
			UsuarioLogado usuarioLogado = null;
			try {
				usuarioLogado = ControladorUsuario.getInstance().consultar(email,senha);
				if(usuarioLogado != null) {
					HttpSession session = req.getSession();
					session.setAttribute("usuarioLogado", usuarioLogado);
					resp.sendRedirect("listar");
				}
			} catch (UsuarioSenhaException e) {
				req.setAttribute("erro", "Usuário ou senha inválidos!");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			} catch (Exception e) {
				req.setAttribute("erro", "Ocorreu um erro ao tentar executar o login, tente novamente!");
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}
					
			
		} else if (botao.equals("novoCadastro")) {
			resp.sendRedirect("cadastrar");
		}

	}
}
