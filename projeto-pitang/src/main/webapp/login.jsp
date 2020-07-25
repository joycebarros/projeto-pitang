<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Login</title>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
		<script type="text/javascript" src="js/principal.js"></script>
	</head>

	<body>
		<div class="container">
		<form action= "login" method="post">
			
		<header>
			<div align="center">
				<img src="img/imagem.jpg" alt="logo da Pagina">
				<h2>Home</h2>
			</div>
		</header>	
			
			<c:if test="${not empty erro}"><div align="center" id="erro" class="p-3 mb-2 bg-danger text-white">${erro}</div></c:if>
			<c:if test="${not empty mensagem}"><div align="center" id="mensagem" class="p-3 mb-2 bg-success text-white">${mensagem}</div></c:if>
			
			
			<div class="form-row">
				<div class="form-group col-md">
					<label for="email">E-mail</label>
					<input type="text" name= "email" class="form-control" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,63}$" required placeholder="seuemail@dominio.com">
				</div>
			</div>
			
			<div class="form-row">	
				<div class="form-group col-md">
					<label for="senha">Senha</label>
					<input type="password" name= "senha" class="form-control" required>
				</div>
			</div>
			
			<div align="center">
				<button name="botao" type="submit" value="entrar" 		class="btn btn-primary">Entrar</button>
				<button name="botao" type="submit" value="novoCadastro" class="btn btn-secondary" formnovalidate>Novo Cadastro</button>
			</div>
		</form>
		</div>
	</body>
</html>