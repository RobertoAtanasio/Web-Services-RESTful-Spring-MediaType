package com.algaworks.socialbooks.services.exceptions;

public class RegistroInexistenteException extends RuntimeException {

	private static final long serialVersionUID = -8714165334956682175L;

	public RegistroInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public RegistroInexistenteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
