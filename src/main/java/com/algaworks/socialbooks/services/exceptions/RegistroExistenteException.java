package com.algaworks.socialbooks.services.exceptions;

public class RegistroExistenteException extends RuntimeException {

	private static final long serialVersionUID = 7234704556067141162L;

	public RegistroExistenteException(String mensagem) {
		super(mensagem);
	}
	
	public RegistroExistenteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
