package br.com.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.demo.modelo.Pessoa;
import br.com.demo.modelo.Telefone;
import br.com.demo.repository.PessoaRepository;
import br.com.demo.service.exception.TelefoneNaoEncontradoException;
import br.com.demo.service.exception.UnicidadeCpfException;
import br.com.demo.service.exception.UnicidadeTelefoneException;
import br.com.demo.service.impl.PessoaServiceImpl;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

	private static final String NUMERO = "12345678";

	private static final String DDD = "55";

	private static final String CPF = "12345678901";

	private static final String NOME = "Diego Duarte";

	@MockBean
	private PessoaRepository pessoaRepository;
	
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

	private PessoaService pessoaService;

	private Pessoa pessoa;

	private Telefone telefone;

	@Before
	public void setUp() {
		pessoaService = new PessoaServiceImpl(pessoaRepository);

		telefone = Telefone.builder().ddd(DDD).numero(NUMERO).build();

		pessoa = Pessoa.builder().nome(NOME).cpf(CPF).telefones(Arrays.asList(telefone)).build();
		
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
	}

	@Test
	public void deve_salvar_pessoa_no_repositorio() throws Exception {
		pessoaService.salvar(pessoa);

		verify(pessoaRepository).save(pessoa);
	}

	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
		expectedException.expect(UnicidadeCpfException.class);
		expectedException.expectMessage("Já existe pessoa cadastrada com o CPF "+ CPF);

		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));

		pessoaService.salvar(pessoa);
	}

	@Test
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_telefone() throws Exception {
		expectedException.expect(UnicidadeTelefoneException.class);
        expectedException.expectMessage("Já existe pessoa cadastrada com o Telefone ("+ DDD +")" + NUMERO);
        
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		pessoaService.salvar(pessoa);
	}
	
	@Test(expected = TelefoneNaoEncontradoException.class)
	public void deve_retornar_execao_de_nao_encontrado_quando_nao_existir_pessoa_com_o_ddd_e_numero_de_telefone() throws Exception {
		pessoaService.buscarPorTelefone(telefone);
	}
	
	@Test
    public void deve_retornar_dados_do_telefone_dentro_da_excecao_de_telefone_nao_encontrado_exception() throws Exception {
        expectedException.expect(TelefoneNaoEncontradoException.class);
        expectedException.expectMessage("Não existe pessoa com o telefone (" + DDD +")" + NUMERO);

        pessoaService.buscarPorTelefone(telefone);
    }
	
	@Test
	public void deve_procurar_pessoa_pelo_ddd_e_numero_do_telefone() throws Exception {
		
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));
		
		Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);
		
		verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);
		
		assertThat(pessoa).isNotNull();
		assertThat(pessoa.getNome()).isEqualTo(NOME);
		assertThat(pessoa.getCpf()).isEqualTo(CPF);
	}
}
