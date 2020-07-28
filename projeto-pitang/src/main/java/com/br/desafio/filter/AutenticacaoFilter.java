package com.br.desafio.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AutenticacaoFilter
 */
@WebFilter(urlPatterns = {"/listar","/alterar","/excluir"})
public class AutenticacaoFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AutenticacaoFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		
		HttpSession session = servletRequest.getSession();
		UsuarioLogado usuarioLogado = (UsuarioLogado) session.getAttribute("usuarioLogado");
		
		
		if(usuarioLogado == null) {
			servletRequest.setAttribute("erro", "Acesso não Autorizado");
			servletRequest.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
		}else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
