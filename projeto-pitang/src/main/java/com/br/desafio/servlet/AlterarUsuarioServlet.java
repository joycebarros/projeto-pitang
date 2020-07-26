package com.br.desafio.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.br.desafio.controlador.ControladorUsuario;
import com.br.desafio.entidade.Telefone;
import com.br.desafio.entidade.Usuario;
import com.br.desafio.filter.UsuarioLogado;

@WebServlet(urlPatterns = "/alterar")
public class AlterarUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idUsuario = request.getParameter("id");
		Usuario usuario = ControladorUsuario.getInstance().consultarUsuarioPorId(Integer.parseInt(idUsuario));
		
		//sessão do usuario
		HttpSession session = request.getSession();
		UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");
		
		if(usuario != null) {
		
			request.setAttribute("usuario", usuario);
			request.getRequestDispatcher("alterar.jsp").forward(request, response);	
		
		}else {
			List<Usuario> listaUsuarios = ControladorUsuario.getInstance().listarUsuarios(usuarioLogado);
			request.setAttribute("listaUsuarios", listaUsuarios);
			request.setAttribute("erro", "Não foi possível alterar este Usuário.");
			request.getRequestDispatcher("listar.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String botao = req.getParameter("botao");
		
		if (botao.equals("atualizar")) {
		
			// Declaracao das variaveis e recuperacao dos parametros do request
			ArrayList<Telefone> arrayTelefones = new ArrayList<Telefone>();
			String id = req.getParameter("id");
			String nome = req.getParameter("nome");
			String email = req.getParameter("email");
			String senha = req.getParameter("senha");
			String listaTelefones = req.getParameter("listaTelefones");
			String[] arrTelefones = listaTelefones.split("&");
	
			// criacao do objeto usuario
			Usuario usuario = new Usuario();
			usuario.setId(Integer.parseInt(id));
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
			
			//sessão do usuario
			HttpSession session = req.getSession();
			UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");
			
			// chama o controladorUsuario para alterar o Usuario. (retorno do metodo boolean)
			// se o metodo retornar true -> redireciona para a tela listar todos e informar que o usuario foi alterado com sucesso
			// se o metodo retornar false -> exibir uma mensagem tratada para o usuario dizendo que nao foi possivel realizar a alteração - exibe a tela de alteração novamente
			//if (ControladorUsuario.alterarUsuario(usuario,arrayTelefones)) {
			if (ControladorUsuario.getInstance().alterarUsuario(usuario)) {
					List<Usuario> listaUsuarios = ControladorUsuario.getInstance().listarUsuarios(usuarioLogado);
					req.setAttribute("listaUsuarios", listaUsuarios);
					req.setAttribute("mensagem", "Usuário alterado com sucesso!");
					req.getRequestDispatcher("listar.jsp").forward(req, resp);
				} else { // retornou false
					req.setAttribute("erro", "Não foi possível realizar a alteração, tente novamente!");
					req.setAttribute("usuario", usuario);
					req.getRequestDispatcher("alterar.jsp").forward(req, resp);
				}
	    }else if (botao.equals("voltar")) {
	    	resp.sendRedirect("listar");
	    }
	}
}
