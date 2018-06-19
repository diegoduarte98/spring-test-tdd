package br.com.tests.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tests.modelo.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Optional<Pessoa> findByCpf(String cpf);

	Optional<Pessoa> findByTelefonesDddAndTelefonesNumero(String ddd, String numero);

}
