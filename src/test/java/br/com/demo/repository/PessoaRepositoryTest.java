package br.com.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.demo.modelo.Pessoa;
import br.com.demo.repository.filtro.PessoaFiltro;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Test
	public void deve_procurar_pessoa_pelo_cpf() {
		Optional<Pessoa> optional = pessoaRepository.findByCpf("38767897100");

		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();
		assertThat(pessoa.getCodigo()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");

	}

	@Test
	public void deve_encontrar_pessoa_cpf_inexistente() {
		Optional<Pessoa> optional = pessoaRepository.findByCpf("88888888888");

		assertThat(optional.isPresent()).isFalse();
	}

	@Test
	public void deve_encontrar_pessoa_pelo_ddd_e_numero_de_telefone() {
		Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero("86", "35006330");

		assertThat(optional.isPresent()).isTrue();

		Pessoa pessoa = optional.get();
		assertThat(pessoa.getCodigo()).isEqualTo(3L);
		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");
	}

	@Test
	public void nao_deve_encontrar_pessoa_cujo_ddd_e_telefone_nao_estejam_cadastradados() {
		Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero("11", "888888888888");

		assertThat(optional.isPresent()).isFalse();
	}

	@Test
	public void deve_filtrar_pessoas_por_parte_do_nome() {
		PessoaFiltro filtro = PessoaFiltro.builder().nome("a").build();

		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(3);
	}

	@Test
	public void deve_filtrar_pessoas_por_parte_do_cpf() {
		PessoaFiltro filtro = PessoaFiltro.builder().cpf("78").build();

		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(3);
	}

	@Test
	public void deve_filtrar_pessoas_por_filtro_composto() {
		PessoaFiltro filtro = PessoaFiltro.builder().nome("a").cpf("78").build();

		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(2);
	}

	@Test
	public void deve_filtrar_pessoas_pelo_ddd_do_telefone() {
		PessoaFiltro filtro = PessoaFiltro.builder().ddd("21").build();

		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(1);
	}

	@Test
	public void deve_filtrar_pessoas_pelo_numero_do_telefone() {
		PessoaFiltro filtro = PessoaFiltro.builder().telefone("999570146").build();

		List<Pessoa> pessoas = pessoaRepository.filtrar(filtro);

		assertThat(pessoas.size()).isEqualTo(1);
	}
}
