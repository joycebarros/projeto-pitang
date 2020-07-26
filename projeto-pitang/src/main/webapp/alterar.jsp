<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Atualização do Usuário</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
		<script type="text/javascript" src="js/principal.js"></script>
		<script type="text/javascript" src="js/telefones.js"></script>
	</head>
	
	<body>
		<div class="container">
			
			<header>
				<div align="center">
					<img src="img/imagem.jpg" alt="logo da Pagina">
					<h2>Atualização do Usuário</h2>
				</div>
			</header>
			
			<c:if test="${not empty erro}"><div align="center" id="erro" class="p-3 mb-2 bg-danger text-white">${erro}</div></c:if>
			<c:if test="${not empty mensagem}"><div align="center" id="mensagem" class="p-3 mb-2 bg-success text-white">${mensagem}</div></c:if>
			
			<form id="formulario" action="alterar" method="post">
				<div class="form-group">
    				<input type="hidden" name="id" value="${usuario.id}">
    			</div>
			
				<div class="form-group">
    				<label for="nome">Nome</label>
    				<input type="text" class="form-control" id="nome" name="nome" placeholder="Digite seu nome"  value="${usuario.nome}" required>
    			</div>
			
  				<div class="form-group">
    				<label for="email">E-mail</label>
    				<input type="email" class="form-control" id="email" name="email" placeholder="Digite seu e-mail"  value="${usuario.email}" required>
    			</div>
  
  				<div class="form-group">
    				<label for="senha">Senha</label>
    				<input type="password" class="form-control" id="senha" name="senha" placeholder="Digite sua senha" value="${usuario.senha}" required>
  				</div>
  
  				<div class="form-row">
    				<div class="form-group col-md-1">
      					<label for="inputDDD">DDD</label>
      					<input type="text" class="form-control" id="ddd" pattern="[0-9]" maxlength="2">
    				</div>
    				<div class="form-group col-md-3">
      					<label for="inputNumero">Número</label>
      					<input type="text" class="form-control" id="numero" pattern="[0-9]" maxlength="9">
    				</div>
    				<div class="form-group col-md-3">
      					<label for="inputTipo">Tipo</label>
      					<select id="tipo" class="form-control">
        					<option value= "Escolha uma opção..." selected>Escolha uma opção...</option>
        					<option value="CELULAR">CELULAR</option>
        					<option value="COMERCIAL">COMERCIAL</option>
        					<option VALUE="RESIDENCIAL">RESIDENCIAL</option>
      					</select>
    				</div>
    				<div class="form-group col-md-1">
    					<svg class="bi bi-plus-square-fill" width="1em" height="6em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg" onclick="addTelefone($('#ddd'),$('#numero'),$('#tipo'));">
	  						<path fill-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4a.5.5 0 0 0-1 0v3.5H4a.5.5 0 0 0 0 1h3.5V12a.5.5 0 0 0 1 0V8.5H12a.5.5 0 0 0 0-1H8.5V4z"/>
						</svg>
    				</div>
  				</div>
  				
  				<div id="addTelefone"></div>
  				
  				<c:forEach var="telefone" items="${usuario.telefones}">
	  				<div id="${telefone.ddd}${telefone.numero}" class="form-row telefones">
	  					<div class="form-group col-md-1">
	  						<input type="text" class="form-control" id="ddd" pattern="[0-9]+" value="${telefone.ddd}" readonly="">
	  					</div>
	  					<div class="form-group col-md-3">
	  						<input type="text" class="form-control" id="numero" pattern="[0-9]+" value="${telefone.numero}" readonly="">
	  					</div>
	  					<div class="form-group col-md-3">
	  						<select id="tipo${telefone.ddd}${telefone.numero}" class="form-control" disabled="">
	  							<option value="${telefone.tipo}">${telefone.tipo}</option>
	  						</select>
	  					</div>
	  					<div class="form-group col-md-1">
	  						<svg class="bi bi-dash-square-fill" width="1em" height="3em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg" onclick="removeTelefone(this);">
	  							<path fill-rule="evenodd" d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm2 7.5a.5.5 0 0 0 0 1h8a.5.5 0 0 0 0-1H4z"></path>
	  						</svg>
	  					</div>
	  				</div>
  				</c:forEach>	
			    
  				<div class="for-group" align="center">
  					<button name="botao" type="button" value="voltar" class="btn btn-secondary" formnovalidate onclick="validarFormulario(this);" >Voltar</button>
					<button name="botao" type="button" value="atualizar" class="btn btn-primary" onclick="validarFormulario(this);" >Atualizar</button>
				</div>
			</form>
	</body>
</html>