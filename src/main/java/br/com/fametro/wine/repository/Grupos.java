package br.com.fametro.wine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fametro.wine.model.Grupo;

public interface Grupos extends JpaRepository<Grupo, Long> {

}
