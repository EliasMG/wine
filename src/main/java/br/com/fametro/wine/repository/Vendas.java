package br.com.fametro.wine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fametro.wine.model.Venda;

public interface Vendas extends JpaRepository<Venda, Long> {

}
