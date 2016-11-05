$('#confirmacaoExclusaoModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget);
	
	var codigoVinho = button.data('codigo');
	var nomeVinho = button.data('nome');
	
	var modal = $(this);
	var form = modal.find('form');
	var action = form.data('url-base');
	if (!action.endsWith('/')) {
		action += '/';
	}
	form.attr('action', action + codigoVinho);
	
	modal.find('.modal-body span').html('Tem certeza que deseja excluir o vinho <strong>' + nomeVinho + '</strong>?');
});