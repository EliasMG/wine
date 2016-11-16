Wine = Wine || {};

Wine.Autocomplete = (function() {
	
	function Autocomplete() {
		this.NomeInput = $('.js-nome-vinho-input');
		var htmlTemplateAutocomplete = $('#template-autocomplete-vinho').html();
		this.template = Handlebars.compile(htmlTemplateAutocomplete);
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	Autocomplete.prototype.iniciar = function() {
		var options = {
			url: function(Nome) {
				return '/vinhos?nome=' + Nome;
			},
			getValue: 'nome',
			minCharNumber: 3,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},
			template: {
				type: 'custom',
				method: template.bind(this)
			},
			list: {
				onChooseEvent: onItemSelecionado.bind(this)
			}
		};
		
		this.NomeInput.easyAutocomplete(options);
	}
	
	function onItemSelecionado() {
		this.emitter.trigger('item-selecionado', this.NomeInput.getSelectedItemData());
		this.NomeInput.val('');
		this.NomeInput.focus();
	}
	
	function template(nome, vinho) {
		vinho.valorFormatado = Wine.formatarMoeda(vinho.valor);
		return this.template(vinho);
	}
	
	return Autocomplete
	
}());


