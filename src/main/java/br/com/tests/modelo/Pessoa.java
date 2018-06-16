package br.com.tests.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(length = 80, nullable = false)
	private String nome;

	@Column(length = 11, nullable = false)
	private String cpf;

	@OneToMany(mappedBy = "pessoa")
	private List<Endereco> enderecos;

	@OneToMany(mappedBy = "pessoa")
	private List<Telefone> telefones;
}
