package br.com.fametro.wine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fametro.wine.model.Cliente;

public interface Clientes extends JpaRepository<Cliente, Long> {

	List<Cliente> findByNomeStartingWithIgnoreCase(String nome);

}
