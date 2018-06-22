package br.com.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.demo.modelo.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Optional<Pessoa> findByCpf(String cpf);

	@Query("SELECT p FROM Pessoa p JOIN p.telefones t WHERE t.ddd = :ddd AND t.numero = :numero")
	Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(@Param("ddd") String ddd, @Param("numero") String numero);

}
