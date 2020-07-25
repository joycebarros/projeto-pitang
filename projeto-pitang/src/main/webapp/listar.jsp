<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">

	<title>Lista de Usuários</title>

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
	<script type="text/javascript" src="js/principal.js"></script>
</head>

<body>
	<div class="container-fluid">
		<header>
			<div align="center">
				<img src="img/imagem.jpg" alt="logo da Pagina">
				<h2>Lista de Usuários</h2>
			</div>
		</header>
		
		<c:if test="${not empty erro}"><div align="center" id="erro" class="p-3 mb-2 bg-danger text-white">${erro}</div></c:if>
		<c:if test="${not empty mensagem}"><div align="center" id="mensagem" class="p-3 mb-2 bg-success text-white">${mensagem}</div></c:if>
		
		<div align="right">
			<button class="btn btn-dark" name="botao" type="submit" value="logout" onclick="window.location.href='listarTodos?logout=sim'">Logout</button>
		</div>
		<br>
		<input type="hidden" name="idUsuario" id="idUsuario" value="">		
		
		<table class="table">
  			
  			<thead class="thead-dark">
    			<tr>
      				<th scope="col">Nome</th>
      				<th scope="col">E-mail</th>
      				<th scope="col">&nbsp;</th>
      				<th scope="col">&nbsp;</th>
      				<th scope="col">&nbsp;</th>
      				
    			</tr>
  			</thead>
		
			<tbody>
				<c:forEach var="usuario" items="${listaUsuarios}">
			    	<tr>
			       		<td>${usuario.nome}</td>
			      		<td>${usuario.email}</td>
			    		<td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#usuario_${usuario.id}">Telefones</button></td>
						<td><button class="btn btn-warning" name="botao" type="submit" value="alterar" onclick="window.location.href='alterar?id=${usuario.id}'">Alterar</button></td>
						<td><button class="btn btn-danger" name="botao" type="submit" value="excluir" id="excluir" onclick="confirmExclusao('${usuario.id}')">Excluir</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<!-- Modal para Exibir os Telefones -->
		<c:forEach var="usuario" items="${listaUsuarios}">
			<div class="modal fade" id="usuario_${usuario.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  			<div class="modal-dialog">
		    			<div class="modal-content">
		      				<div class="modal-header">
		        				<h5 class="modal-title" id="exampleModalLabel">${usuario.nome}</h5>
		        				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		        					<span aria-hidden="true">&times;</span>
		        				</button>
		        			</div>
		      				<c:forEach var="telefone" items="${usuario.telefones}">
								<div class="modal-body">
									<div class="row">
										<div class="col">${telefone.ddd}</div>
		        						<div class="col">${telefone.numero}</div>
		        						<div class="col">${telefone.tipo}</div>
		        					</div>
		      					</div>
		      				</c:forEach>	
		      				<div class="modal-footer">
		        				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        			</div>
		    			</div>
		  			</div>
				</div>
		</c:forEach>
	</div>
</body>
</html>