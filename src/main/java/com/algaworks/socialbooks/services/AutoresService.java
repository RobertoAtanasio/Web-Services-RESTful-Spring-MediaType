package com.algaworks.socialbooks.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Autor;
import com.algaworks.socialbooks.repository.AutoresRepository;
import com.algaworks.socialbooks.services.exceptions.RegistroExistenteException;
import com.algaworks.socialbooks.services.exceptions.RegistroInexistenteException;

@Service
public class AutoresService {

	@Autowired
	private AutoresRepository autoresRepository;
	
	public List<Autor> listar() {
		return autoresRepository.findAll();
	}	
	
	public Autor salvar (Autor autor) {
		if(autor.getId() != null) {
			Autor autorLido = autoresRepository.findOne(autor.getId());
			if(autorLido != null) {
				throw new RegistroExistenteException("O autor já existe.");
			}
		}
		autor.setId(null);
		return autoresRepository.save(autor);
	}
	
	public Autor buscar (Long id) { 
		Autor autorSalvo = autoresRepository.findOne(id);
		if (autorSalvo == null) {
			throw new RegistroInexistenteException("Autor não encontrado.");
		}
		return autorSalvo;
	}
	
	public void deletar (Long id) {
		try {
			autoresRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RegistroInexistenteException("Autor não encontrado.");
		}
	}
	
	public Autor atualizar (Autor autor) {
		Autor autorSalvo = this.buscar(autor.getId());
		BeanUtils.copyProperties(autor, autorSalvo, "id");
		return autoresRepository.save(autorSalvo);
	}
	
}
