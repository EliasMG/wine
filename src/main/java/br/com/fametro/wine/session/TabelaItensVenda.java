package br.com.fametro.wine.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.fametro.wine.model.ItemVenda;
import br.com.fametro.wine.model.Vinho;

@SessionScope
@Component
public class TabelaItensVenda {
	
private List<ItemVenda> itens = new ArrayList<>();
	
	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public void adicionarItem(Vinho vinho, Integer quantidade) {
		Optional<ItemVenda> itemVendaOptional = buscarItemPorVinho(vinho);
		
		ItemVenda itemVenda = null;
		if (itemVendaOptional.isPresent()) {
			itemVenda = itemVendaOptional.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
		} else {
			
			itemVenda = new ItemVenda();
			itemVenda.setVinho(vinho);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(vinho.getValor());
			itens.add(0,itemVenda);
		}
		
	}
	
	public void excluirItem(Vinho vinho) {
		int indice = IntStream.range(0, itens.size())
				.filter(i -> itens.get(i).getVinho().equals(vinho))
				.findAny().getAsInt();
		itens.remove(indice);
	}

	private Optional<ItemVenda> buscarItemPorVinho(Vinho vinho) {
		Optional<ItemVenda> itemVendaOptional = itens.stream()
			.filter(i -> i.getVinho().equals(vinho))
			.findAny();
		return itemVendaOptional;
	}
	
	public void alterarQuantidadeItens(Vinho vinho, Integer quantidade) {
		ItemVenda itemVenda = buscarItemPorVinho(vinho).get();
		itemVenda.setQuantidade(quantidade);
	}
	
	public int total() {
		return itens.size();
	}

	public List<ItemVenda> getItens() {
		return itens;
	}
}
