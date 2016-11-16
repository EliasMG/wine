package br.com.fametro.wine.repository.helper.vinho;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.fametro.wine.dto.VinhoDTO;

public class VinhosImpl implements VinhosQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<VinhoDTO> porNome(String nome) {
		String jpql = "select new br.com.fametro.wine.dto.VinhoDTO(codigo, nome, safra, valor, foto) "
					+ "from Vinho where lower(nome) like lower(:nome)";
		
		List<VinhoDTO> vihosFiltrados = manager.createQuery(jpql, VinhoDTO.class)
				.setParameter("nome", nome + "%")
				.getResultList();
		
		return vihosFiltrados;
	}

}
