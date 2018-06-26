package br.com.demo.repository.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.demo.modelo.Pessoa;
import br.com.demo.repository.filtro.PessoaFiltro;

@Repository
public class PessoaRepositoryImpl implements PessoaRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Pessoa> filtrar(PessoaFiltro filtro) {

		final StringBuilder sb = new StringBuilder();
		sb.append(" SELECT bean FROM Pessoa bean WHERE 1=1 ");

		final Map<String, Object> params = new HashMap<>();
		
		if (StringUtils.hasText(filtro.getNome())) {
			sb.append(" AND bean.nome LIKE :nome ");
			params.put("nome", "%" + filtro.getNome() + "%");
		}

		if (StringUtils.hasText(filtro.getCpf())) {
			sb.append(" AND bean.cpf LIKE :cpf ");
			params.put("cpf", "%" + filtro.getCpf() + "%");
		}

		TypedQuery<Pessoa> query = manager.createQuery(sb.toString(), Pessoa.class);
		preencherParametrosDaQuery(params, query);

		return query.getResultList();
	}

	private void preencherParametrosDaQuery(Map<String, Object> params, Query query) {
		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
	}

}
