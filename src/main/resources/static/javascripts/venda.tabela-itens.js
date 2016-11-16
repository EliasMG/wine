Wine.TabelaItens = (function() {
	
	function TabelaItens(autocomplete) {
		this.autocomplete = autocomplete;
		this.tabelaVinhosContainer = $('.js-tabela-vinhos-container');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	TabelaItens.prototype.iniciar = function() {
		this.autocomplete.on('item-selecionado', onItemSelecionado.bind(this));
	}
	
	function onItemSelecionado(evento, item) {
		var resposta = $.ajax({
			url: 'item',
			method: 'POST',
			data: {
				codigoVinho: item.codigo
			}
		});
		
		resposta.done(onItenAtualizadoNoServidor.bind(this));
	}
	
	function onItenAtualizadoNoServidor(html) {
		this.tabelaVinhosContainer.html(html);
		$('.tabela-vinho-quantiade-item').on('change', onQuantidadeItemAlterado.bind(this));
		
		var tabelaItem = $('.js-tabela-item');
		tabelaItem.on('dblclick', onDoubleClick);
		$('.js-exclusao-item-btn').on('click', onExclusaoItemClick.bind(this));
		
		this.emitter.trigger('tabela-itens-atualizada', tabelaItem.data('valor-total'));
	}
	
	function onQuantidadeItemAlterado(evento) {
		var input = $(evento.target);
		var quantidade = input.val();
		var codigoVinho = input.data('codigo-vinho');
		
		var resposta = $.ajax({
			url: 'item/' + codigoVinho,
			method: 'PUT',
			data: {
				quantidade: quantidade
			}
		});
		
		resposta.done(onItenAtualizadoNoServidor.bind(this));
	}
	
	function onDoubleClick(evento) {
		$(this).toggleClass('solicitando-exclusao');
	}
	
	function onExclusaoItemClick(evento) {
		var codigoVinho = $(evento.target).data('codigo-vinho');
		var resposta = $.ajax({
			url: 'item/' + codigoVinho,
			method: 'DELETE'
		});
		
		resposta.done(onItenAtualizadoNoServidor.bind(this));
	}
	
	return TabelaItens;
	
}());
