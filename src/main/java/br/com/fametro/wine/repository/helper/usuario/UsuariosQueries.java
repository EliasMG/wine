package br.com.fametro.wine.repository.helper.usuario;

import java.util.Optional;

import br.com.fametro.wine.model.Usuario;

public interface UsuariosQueries {
	
	public Optional<Usuario> porEmailAtivo(String email);
}
