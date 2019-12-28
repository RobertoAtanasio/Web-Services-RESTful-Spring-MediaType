package com.algaworks.socialbooks.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Autor;
import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.ComentariosRepository;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.services.exceptions.RegistroExistenteException;
import com.algaworks.socialbooks.services.exceptions.RegistroInexistenteException;

@Service
public class LivrosService { 

	@Autowired
	private LivrosRepository livrosRepository;
	
	@Autowired
	private AutoresService autoresService;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	public List<Livro> listar () {
		return livrosRepository.findAll();
	}
	
	public Livro salvar (Livro livro) {
		if(livro.getId() != null) {
			Livro livroLido = livrosRepository.findOne(livro.getId());
			if(livroLido != null) {
				throw new RegistroExistenteException("O livro já existe.");
			}
		}
		Autor autor = autoresService.buscar(livro.getAutor().getId());
		livro.setAutor(autor);
		livro.setId(null);
		return livrosRepository.save(livro);
	}
	
	public Livro buscar (Long id) { 
		Livro livroSalvo = livrosRepository.findOne(id);
		if (livroSalvo == null) {
			throw new RegistroInexistenteException("Livro não encontrado.");
		}
		return livroSalvo;
	}
	
	public Livro atualizar (Livro livro) {
		Livro livroSalvo = this.buscar(livro.getId());
		BeanUtils.copyProperties(livro, livroSalvo, "id");
		return livrosRepository.save(livroSalvo);
	}
	
	public void deletar (Long id) {
		try {
			livrosRepository.delete(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RegistroInexistenteException("Livro não encontrado.");
		}
	}
	
	public Comentario salvarComentario(Long livroId, Comentario comentario) {
		Livro livro = buscar(livroId);
		
//		LocalDate dataAux = LocalDate.now();
//		LocalTime hora = LocalTime.now();
		
		comentario.setLivro(livro);
		comentario.setDataHora(new Date());
//		comentario.setData(LocalDate.now());
//		comentario.setData(LocalDate.of(dataAux, hora));
		
		return comentariosRepository.save(comentario);
	}
	
	public List<Comentario> listarComentarios(Long livroId) {
		Livro livro = buscar(livroId);
		
		return livro.getComentarios();
	}
}
