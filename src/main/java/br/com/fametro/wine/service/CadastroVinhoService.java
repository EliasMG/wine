package br.com.fametro.wine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.fametro.wine.model.Vinho;
import br.com.fametro.wine.repository.Vinhos;
import br.com.fametro.wine.storage.FotoStorage;

@Service
public class CadastroVinhoService {

	@Autowired
	private Vinhos vinhos;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	public void salvar(Vinho vinho) {
		// Escrever regras de negócio...
		this.vinhos.save(vinho);
	}
	
	public String salvarFoto(Long codigo, MultipartFile foto) {
		String nomeFoto = fotoStorage.salvar(foto);
		
		Vinho vinho = vinhos.findOne(codigo);
		vinho.setFoto(nomeFoto);
		vinhos.save(vinho);
		
		return fotoStorage.getUrl(nomeFoto);
	}
	
}