$().ready(function() {
	setTimeout(function () {
		$('#mensagem').hide();
	}, 2500);
	setTimeout(function () {
		$('#erro').hide();
	}, 2500);
});


function validarFormulario(botao){
	
	if($("#nome").val() == ''){
		alert('Campo Nome e Obrigatorio.');
		return false;
	}
	
	if($("#email").val() == ''){
		alert('Campo Email e Obrigatorio');
		return false;
	}
	
	var emailValido = /^.+@.+..{2,}$/;

    if (!emailValido.test($("#email").val())){
      alert('Email invalido!');
      return false;
    }
	
	if($("#senha").val() == ''){
		alert('Campo Senha e Obrigatorio');
		return false;
	}
	
	var hiddenTelefones = '';
	
	if($('.telefones').length == 0){
		exibirMensagemTelefoneObrigatorio();
		return false;
	}else{
		var divTelefones = $('.telefones').each(
			function(indice) {
				hiddenTelefones = hiddenTelefones + $(this).find('#ddd').val() + '%' + $(this).find('#numero').val() + '%' + $(this).find('select[class=form-control]').val() + '&';
			}
		);
		
		$('#formulario').append('<input type="hidden" name="listaTelefones" value="' + hiddenTelefones.substring(0,hiddenTelefones.length - 1) + '">');
		
		$('#formulario').append('<input type="hidden" name="botao" value="' + $(botao).val() + '">');
				
		$('#formulario').submit();
	}
}

function confirmExclusao(id) {
  if (confirm("Tem certeza que deseja excluir esse usuario?")) {
	window.location.href="excluir?id=" + id;
   }
}

function exibirMensagemTelefoneObrigatorio(){
	
	var modal = '<div id=\"alerta\" class=\"modal\" tabindex=\"-1\" role=\"dialog\">'
			+ 		'<div class=\"modal-dialog\">'
			+  			'<div class=\"modal-content\">'
	        +				'<div class=\"modal-header\">'
	        +					'<h5 class=\"modal-title\">Alerta</h5>'
	        +						'<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">'
	        +  							'<span aria-hidden=\"true\">&times;</span>'
	        +						'</button>'
	        +				'</div>'
	        +				'<div class=\"modal-body\">'
	        +					'<p>Voce precisa adicionar ao menos 1 (um) telefone. Caso ja tenha preenchido cique no botao '
	        +						'<svg class=\"bi bi-plus-square-fill\" width=\"1em\" height=\"6em\" viewBox=\"0 0 16 16\" fill=\"currentColor\" xmlns=\"http://www.w3.org/2000/svg\">'
			+							'<path fill-rule=\"evenodd\" d=\"M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm6.5 4a.5.5 0 0 0-1 0v3.5H4a.5.5 0 0 0 0 1h3.5V12a.5.5 0 0 0 1 0V8.5H12a.5.5 0 0 0 0-1H8.5V4z\"/>'
			+						'</svg>'
	        +					'</p>'
	        +				'</div>'
	        +				'<div class=\"modal-footer\">'
	        +					'<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Close</button>'
	        +				'</div>'
	        +			'</div>'
	        +		'</div>'
	        +	'</div>';
	
	if($('#alerta').length == 0){
		$('#formulario').append(modal);
	}
	
	$('.modal').modal('show');
}