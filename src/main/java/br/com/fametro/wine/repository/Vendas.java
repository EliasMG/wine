package br.com.fametro.wine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fametro.wine.model.Venda;
import br.com.fametro.wine.repository.helper.venda.VendasQueries;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries {

}
