package br.com.demo.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.demo.modelo.Pessoa;
import br.com.demo.modelo.Telefone;
import br.com.demo.repository.PessoaRepository;
import br.com.demo.service.PessoaService;
import br.com.demo.service.exception.TelefoneNaoEncontradoException;
import br.com.demo.service.exception.UnicidadeCpfException;
import br.com.demo.service.exception.UnicidadeTelefoneException;

@Service
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
	public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
		Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return optional.orElseThrow(() -> new TelefoneNaoEncontradoException("Não existe pessoa com o telefone (" + telefone.getDdd() +")" + telefone.getNumero()));
	}
}
