

//adiciona um telefone ao formulario
function addTelefone(ddd,numero,tipo){
	
	if($(ddd).val() == '' || $(numero).val() == '' || $(tipo).find(":selected").val() == 'Escolha uma opcao...'){
		alert('Preencha todos os campos para adicionar o telefone.');
		return;
		
	}
	
	//cria o componente de div que insere o telefone dinamicamente com os atributos preenchidos
	var componente = 
		'<div id=\"'+ $(ddd).val() + $(numero).val() +'\" class=\"form-row telefones\">'
	+		'<div class=\"form-group col-md-1\">'
	+			'<input type=\"text\" class=\"form-control\" id=\"ddd\" pattern=\"[0-9]+\" value=\"' + $(ddd).val() + '\" readonly>'
	+		'</div>'
	+		'<div class=\"form-group col-md-3\">'
	+			'<input type=\"text\" class=\"form-control\" id=\"numero\" pattern=\"[0-9]+\" value=\"' + $(numero).val() + '\" readonly>'
	+		'</div>'
	+		'<div class=\"form-group col-md-3\">'
	+			'<select id=\"tipo' + $(ddd).val() + $(numero).val() + '\" class=\"form-control\" disabled>'
	+				'<option value=\"CELULAR\">CELULAR</option>'
	+				'<option value=\"COMERCIAL\">COMERCIAL</option>'
	+				'<option value=\"RESIDENCIAL\">RESIDENCIAL</option>'
	+			'</select>'
	+		'</div>'
	+		'<div class=\"form-group col-md-1\">'
	+			'<svg class=\"bi bi-dash-square-fill\" width=\"1em\" height=\"3em\" viewBox=\"0 0 16 16\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\" onclick=\"removeTelefone(this);\">'
	+				'<path fill-rule=\"evenodd\" d=\"M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm2 7.5a.5.5 0 0 0 0 1h8a.5.5 0 0 0 0-1H4z\"/>'
	+			'</svg>'
	+		'</div>'
	+	'</div>';
	
	//marca a opcao na div inserida da opcao do telefone
	$('select[id="tipo'+ $(ddd).val() + $(numero).val() +'"]').find('option[value="' + $(tipo).val() +'"]').attr("selected",true);
	
	//adiciona a div ao formulario	
	$('#addTelefone').append(componente);
	
	//volta para o Escolha um opcao... na adicao do telefone
	$('#tipo').prop('selectedIndex', 0);
	
	//limpa o campo de ddd
	$('#ddd').val("");
	
	//limpa o campo de numero
	$('#numero').val("");
	
}

//remove um telefone ao formulario
function removeTelefone(elemento){
	$(elemento).parent().parent().remove();
}

