package br.com.tests.service.impl;

import java.util.Optional;

import br.com.tests.modelo.Pessoa;
import br.com.tests.modelo.Telefone;
import br.com.tests.repository.PessoaRepository;
import br.com.tests.service.PessoaService;
import br.com.tests.service.exception.TelefoneNaoEncontratoException;
import br.com.tests.service.exception.UnicidadeCpfException;
import br.com.tests.service.exception.UnicidadeTelefoneException;

public class PessoaServiceImpl implements PessoaService {

	private final PessoaRepository pessoaRepository;

	public PessoaServiceImpl(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {
		
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());
		
		if(optional.isPresent()) {
			throw new UnicidadeCpfException();
		}
		
		Telefone telefone = pessoa.getTelefones().get(0);
		
		optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		
		if(optional.isPresent()) {
			throw new UnicidadeTelefoneException();
		}
		
		return pessoaRepository.save(pessoa);
	}

	@Override
	public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontratoException {
		Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return optional.orElseThrow(() -> new TelefoneNaoEncontratoException());
	}
}
