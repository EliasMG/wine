package br.com.fametro.wine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fametro.wine.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long> {

}
