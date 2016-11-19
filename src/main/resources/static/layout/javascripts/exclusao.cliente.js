$('#confirmacaoExclusaoModalCliente').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var codigoCliente = button.data('codigo');
	var nomeCliente = button.data('nome');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + codigoCliente);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o cliente <strong>' + nomeCliente + '</strong>?');
});