package br.com.demo.repository.filtro;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PessoaFiltro {

	private String nome;

	private String cpf;

	private String ddd;

	private String telefone;
}
