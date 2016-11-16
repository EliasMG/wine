package br.com.fametro.wine.repository.helper.vinho;

import java.util.List;

import br.com.fametro.wine.dto.VinhoDTO;

public interface VinhosQueries {
	
	public List<VinhoDTO> porNome(String nome); 
}
