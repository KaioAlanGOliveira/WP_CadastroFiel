package br.com.kaio.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import br.com.kaio.domain.Pagamento;
import br.com.kaio.domain.PagamentoId;
import br.com.kaio.persistence.EMFactory;

public class PagamentoDao {

	public Pagamento getPagamento(PagamentoId id) throws Exception {
		EntityManager em = EMFactory.getEntityManager();
		return em.find(Pagamento.class, id);
	}

	public List<Pagamento> getLista(String cpf) throws Exception {

		EntityManager em = EMFactory.getEntityManager();
		try {
			String sql = """
					    select o
					    from Pagamento o
					    where
					        (:cpf = '' or o.id.cpf = :cpf)
					""";

			TypedQuery<Pagamento> query = em.createQuery(sql, Pagamento.class);
			query.setParameter("cpf", cpf);

			return query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void adicionar(Pagamento pg) throws Exception {

		EntityManager em = EMFactory.getEntityManager();
		UserTransaction um = EMFactory.getUserTransaction();
		try {

			um.begin();
			String sql = "SELECT MAX(cod_pagamento) AS maior_codigo FROM pagamento WHERE cpf = :cpf";
			Query query = em.createNativeQuery(sql);
			query.setParameter("cpf", pg.getId().getCpf());
			Number max = (Number) query.getSingleResult();
			Integer proximo = (max == null) ? 1 : max.intValue() + 1;

			pg.getId().setCodPagamento(proximo);
			
			em.persist(pg);
			
			um.commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao adicionar fiel", e);
		}
	}

	public void atualizar(Pagamento pg) throws Exception {
 
		EntityManager em = EMFactory.getEntityManager();
		UserTransaction um = EMFactory.getUserTransaction();
		
		try {
			um.begin();
			em.merge(pg);
			um.commit();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao atualizar fiel", e);
		}

	}

	public void apagar(Pagamento pagamento) throws Exception {

		EntityManager em = EMFactory.getEntityManager();
		UserTransaction ut = EMFactory.getUserTransaction();

		try {
			ut.begin();
			Pagamento pag = em.find(Pagamento.class, pagamento.getId());
			em.remove(pag);
			ut.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}