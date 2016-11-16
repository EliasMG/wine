Wine.Venda = (function() {
	
	function Venda(tabelaItens) {
		this.tabelaItens = tabelaItens;
		this.valorTotalBox = $('.js-valor-total-box');
		this.valorFreteInput = $('#valorFrete');
		this.valorDescontoInput = $('#valorDesconto');
		this.valorTotalBoxContainer = $('.js-valor-total-box-container');
		
		this.valorTotalItens = 0;
		this.valorFrete = 0;
		this.valorDesconto = 0;
	}
	
	Venda.prototype.iniciar = function() {
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		this.valorFreteInput.on('keyup', onValorFreteAlterado.bind(this));
		this.valorDescontoInput.on('keyup', onValorDescontoAlterado.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		this.valorFreteInput.on('keyup', onValoresAlterados.bind(this));
		this.valorDescontoInput.on('keyup', onValoresAlterados.bind(this));
	}
		
	function onTabelaItensAtualizada(evento, valorTotalItens) {
		this.valorTotalItens = valorTotalItens == null ? 0 : numeral(valorTotalItens);
	}
	
	function onValorFreteAlterado(evento) {
		this.valorFrete = Wine.recuperarValor($(evento.target).val());
	}
	
	function onValorDescontoAlterado(evento) {
		this.valorDesconto = Wine.recuperarValor($(evento.target).val());
	}
	
	function onValoresAlterados() {
		var valorTotal = this.valorTotalItens + this.valorFrete - this.valorDesconto;
		this.valorTotalBox.html(Wine.formatarMoeda(valorTotal));
		
		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
		
	}
	
	return Venda;
	
}());


$(function() {
	
	var autocomplete = new Wine.Autocomplete();
	autocomplete.iniciar();
	
	var tabelaItens = new Wine.TabelaItens(autocomplete);
	tabelaItens.iniciar();
	
	var venda = new Wine.Venda(tabelaItens);
	venda.iniciar();
	
});