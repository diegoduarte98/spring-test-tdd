package br.com.demo.repository.helper;

import java.util.List;

import br.com.demo.modelo.Pessoa;
import br.com.demo.repository.filtro.PessoaFiltro;

public interface PessoaRepositoryQueries {
	
	List<Pessoa> filtrar(PessoaFiltro filtro);
}
