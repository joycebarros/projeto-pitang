package com.br.desafio.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.br.desafio.controlador.ControladorUsuario;
import com.br.desafio.entidade.Usuario;
import com.br.desafio.filter.UsuarioLogado;

@WebServlet(urlPatterns = "/excluir")
public class ExcluirUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idUsuario = request.getParameter("id");
		HttpSession session = request.getSession();
		UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");
		
		if (ControladorUsuario.getInstance().excluirUsuario(Integer.parseInt(idUsuario))) {
			request.setAttribute("mensagem", "Usuário excluído com Sucesso!");
		} else {
			request.setAttribute("erro", "Não foi possível excluir este Usuário.");
		}

		if (usuarioLogado.getEmail().equals("admin@admin.com")) {
			List<Usuario> listaUsuarios = ControladorUsuario.getInstance().listarUsuarios(usuarioLogado);
			request.setAttribute("listaUsuarios", listaUsuarios);
			request.getRequestDispatcher("listar.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}
}
