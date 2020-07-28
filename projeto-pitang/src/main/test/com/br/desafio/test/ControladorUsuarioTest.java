package com.br.desafio.test;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.br.desafio.controlador.ControladorUsuario;
import com.br.desafio.entidade.Telefone;
import com.br.desafio.entidade.Usuario;
import com.br.desafio.filter.UsuarioLogado;

public class ControladorUsuarioTest {

	@Test
	public void testCadastrarUsuario() {
		Usuario joy = new Usuario();
		joy.setNome("Joyce Araújo");
		joy.setEmail("emailteste" + new Timestamp(System.currentTimeMillis()) + "@gmail.com");
		joy.setSenha("123456");

		Telefone tel1 = new Telefone();
		tel1.setDdd(81);
		tel1.setNumero("999756464");
		tel1.setTipo("Celular");

		ArrayList<Telefone> listaTel = new ArrayList<Telefone>();
		listaTel.add(tel1);
		
		joy.setTelefones(listaTel);

		Assert.assertTrue(ControladorUsuario.getInstance().cadastrarUsuario(joy));
	}

	@Test
	public void testConsultarUsuarioPorId() {
		Assert.assertNotNull(ControladorUsuario.getInstance().consultarUsuarioPorId(1));
	}

	@Test
	public void testConsultarUsuarioLogado() throws Exception {
		Assert.assertNotNull(ControladorUsuario.getInstance().consultar("joyce@gmail.com", "1234"));
	}

	@Test
	public void testListarUsuarios() {
		UsuarioLogado usuarioLogado = new UsuarioLogado();
		usuarioLogado.setId(1);
		usuarioLogado.setEmail("admin@admin.com");
		Assert.assertNotNull(ControladorUsuario.getInstance().listarUsuarios(usuarioLogado));
	}

}