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

@WebServlet(urlPatterns = "/listarTodos")
public class ListarUsuariosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String logout = req.getParameter("logout");
		HttpSession session = req.getSession();
		
		//se o usuario quiser deslogar
		if(logout != null) {
			session.removeAttribute("usuarioLogado");
			req.setAttribute("mensagem", "Você saiu do sistema com sucesso. Volte Sempre!!");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}else { //caso contrario lista os usuarios
			
			UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");
			List<Usuario> listaUsuarios = ControladorUsuario.getInstance().listarUsuarios(usuarioLogado);
			req.setAttribute("listaUsuarios", listaUsuarios);
			req.getRequestDispatcher("listar.jsp").forward(req, resp);
		}
	}

}
