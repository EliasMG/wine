package br.com.fametro.wine.service.exception;

public class EmailUsuarioJaCadastradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EmailUsuarioJaCadastradoException(String message) {
		super(message);
	}

}
