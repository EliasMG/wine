package br.com.fametro.wine.repository.helper.venda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fametro.wine.model.Venda;
import br.com.fametro.wine.repository.filter.VendaFilter;

public interface VendasQueries {
	
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
	public Venda buscarComItens(Long codigo);
	
}
