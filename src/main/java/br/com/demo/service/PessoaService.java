package br.com.demo.service;

import br.com.demo.modelo.Pessoa;
import br.com.demo.modelo.Telefone;
import br.com.demo.service.exception.TelefoneNaoEncontratoException;
import br.com.demo.service.exception.UnicidadeCpfException;
import br.com.demo.service.exception.UnicidadeTelefoneException;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException;

	Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontratoException;

}
