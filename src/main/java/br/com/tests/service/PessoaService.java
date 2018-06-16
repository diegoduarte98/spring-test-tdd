package br.com.tests.service;

import br.com.tests.modelo.Pessoa;
import br.com.tests.modelo.Telefone;
import br.com.tests.service.exception.TelefoneNaoEncontratoException;
import br.com.tests.service.exception.UnicidadeCpfException;
import br.com.tests.service.exception.UnicidadeTelefoneException;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException;

	Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontratoException;

}
