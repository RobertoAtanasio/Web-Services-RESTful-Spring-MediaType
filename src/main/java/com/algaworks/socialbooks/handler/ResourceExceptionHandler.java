package com.algaworks.socialbooks.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.socialbooks.domain.DetalhesErro;
import com.algaworks.socialbooks.services.exceptions.RegistroExistenteException;
import com.algaworks.socialbooks.services.exceptions.RegistroInexistenteException;

@ControllerAdvice
public class ResourceExceptionHandler {

//	@ExceptionHandler({ RegistroInexistenteException.class })
//	public ResponseEntity<?> handleRegistroInexistenteException(RegistroInexistenteException ex) {
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//	}
	
	@ExceptionHandler(RegistroInexistenteException.class)
	public ResponseEntity<DetalhesErro> handleRegistroInexistenteException
							(RegistroInexistenteException ex, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(HttpStatus.NOT_FOUND.value());
		erro.setTitulo(ex.getMessage());
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
		erro.setTimestamp(new Date());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(RegistroExistenteException.class)
	public ResponseEntity<DetalhesErro> handleRegistroExistenteException
							(RegistroExistenteException ex, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(HttpStatus.CONFLICT.value());
		erro.setTitulo(ex.getMessage());
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/409");
		erro.setTimestamp(new Date());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler({ IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handleIllegalArgumentException(IllegalArgumentException ex) {
		return;
	}
	
	@ExceptionHandler({ EmptyResultDataAccessException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
		return;
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<DetalhesErro> handleDataIntegrityViolationException
							(DataIntegrityViolationException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setTitulo("Requisição inválida");
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/400");
		erro.setTimestamp(new Date());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DetalhesErro> handleHttpMessageNotReadableException
			(HttpMessageNotReadableException e, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setTitulo("Erro de conversão de dados");
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/400");
		erro.setTimestamp(new Date());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
//	@ExceptionHandler(ConstraintViolationException.class)
//	public ResponseEntity<DetalhesErro> handleConstraintViolationException
//							(ConstraintViolationException ex, HttpServletRequest request) {
//		
//		DetalhesErro erro = new DetalhesErro();
//		erro.setStatus(HttpStatus.BAD_REQUEST.value());
//		erro.setTitulo(ex.getMessage());
//		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/400");
//		erro.setTimestamp(System.currentTimeMillis());
//		
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
//	}
//	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<DetalhesErro> handleMethodArgumentNotValidException
		(MethodArgumentNotValidException ex, HttpServletRequest request) {
		
		DetalhesErro erro = new DetalhesErro();
		erro.setStatus(HttpStatus.BAD_REQUEST.value());
		erro.setTitulo(ex.getMessage());
		erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/400");
		erro.setTimestamp(new Date());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
