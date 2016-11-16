package br.com.fametro.wine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fametro.wine.model.Vinho;
import br.com.fametro.wine.repository.helper.vinho.VinhosQueries;

public interface Vinhos extends JpaRepository<Vinho, Long>, VinhosQueries {

}
