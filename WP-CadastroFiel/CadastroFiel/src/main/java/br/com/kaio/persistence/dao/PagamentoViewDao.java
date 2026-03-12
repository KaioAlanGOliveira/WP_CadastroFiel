package br.com.kaio.persistence.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query; // ← mude o import para Query
import br.com.kaio.domain.PagamentoView;
import br.com.kaio.persistence.EMFactory;

public class PagamentoViewDao {

	public List<PagamentoView> getLista(String cpf, String nome) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		try {
			String sql = """
					SELECT cpf, dizimo_id AS cod_pagamento, nome, valor
					FROM vw_fieis_pagamento_busca
					WHERE (:nome = '' OR nome LIKE :nome) 
					AND (:cpf = '' OR cpf = :cpf)
					""";

			Query query = em.createNativeQuery(sql, PagamentoView.class);

			query.setParameter("cpf", cpf);
			query.setParameter("nome", "%" + nome + "%");

			@SuppressWarnings("unchecked")
			List<PagamentoView> resultList = query.getResultList();
			return resultList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	} 
}