package br.com.kaio.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import br.com.kaio.domain.Fiel;
import br.com.kaio.persistence.EMFactory;

public class FielDao {

	private EntityManager getEm() throws Exception {
		return EMFactory.getEntityManager();
	}

	private UserTransaction getUt() throws Exception {
		return EMFactory.getUserTransaction();
	}

	public void adicionar(Fiel f) throws Exception {
		EntityManager em = getEm();
		UserTransaction ut = getUt();
		try {
			ut.begin();
			em.persist(f);
			ut.commit();
		} catch (Exception e) {
			rollbackIfActive(ut);
			throw new RuntimeException("Erro ao adicionar fiel", e);
		}
	}

	public List<Fiel> getLista(String cpf, String nome) throws Exception {
		EntityManager em = getEm();
		try {
			String jpql = """
					SELECT o FROM Fiel o
					WHERE (:nome = '' OR o.nome LIKE :nome)
					  AND (:cpf = '' OR o.cpf = :cpf)
					""";
			TypedQuery<Fiel> query = em.createQuery(jpql, Fiel.class);
			query.setParameter("cpf", cpf != null ? cpf.trim() : "");
			query.setParameter("nome", (nome != null && !nome.trim().isEmpty()) ? "%" + nome.trim() + "%" : "");
			return query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao listar fiéis", e);
		}
	}

	public void apagar(String cpf) throws Exception {
		EntityManager em = getEm();
		UserTransaction ut = getUt();
		try {
			ut.begin();
			Fiel f = em.find(Fiel.class, cpf);
			if (f != null) {
				em.remove(f);
			}
			ut.commit();
		} catch (Exception e) {
			rollbackIfActive(ut);
			throw new RuntimeException("Erro ao apagar fiel", e);
		}
	}

	public void atualizar(Fiel fiel) throws Exception {
		EntityManager em = getEm();
		UserTransaction ut = getUt();
		try {
			ut.begin();
			em.merge(fiel);
			ut.commit();
		} catch (Exception e) {
			rollbackIfActive(ut);
			throw new RuntimeException("Erro ao atualizar fiel", e);
		}
	}

	public Fiel getFiel(String cpf) throws Exception {
		return getEm().find(Fiel.class, cpf);
	}

	public Fiel getEntityByCond(String condicao, Object... params) throws Exception {
		if (condicao == null || condicao.trim().isEmpty()) {
			return null;
		}
		EntityManager em = getEm();
		try {
			TypedQuery<Fiel> query = em.createQuery("SELECT f FROM Fiel f WHERE " + condicao, Fiel.class);
			bindParams(query, params);
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			// Pode logar: log.warn("Mais de um resultado para condição: {}", condicao);
			return null;
		} catch (Exception e) {
			throw new RuntimeException("Erro em getEntityByCond: " + condicao, e);
		}
	}

	public List<Fiel> getListByOrder(String orderBy) throws Exception {
		EntityManager em = getEm();
		String safeOrderBy = getSafeOrderBy(orderBy); // Validação whitelist para evitar injeção

		String jpql = "SELECT f FROM Fiel f ORDER BY " + safeOrderBy;
		TypedQuery<Fiel> query = em.createQuery(jpql, Fiel.class);
		return query.getResultList();
	}

	public List<Fiel> getListByCond(String condicao, Object... params) throws Exception {
		EntityManager em = getEm();
		String jpql = "SELECT f FROM Fiel f";
		if (condicao != null && !condicao.trim().isEmpty()) {
			jpql += " WHERE " + condicao;
		}
		TypedQuery<Fiel> query = em.createQuery(jpql, Fiel.class);
		bindParams(query, params);
		return query.getResultList();
	}

	// Helpers privados
	private void rollbackIfActive(UserTransaction ut) {
		try {
			if (ut.getStatus() == javax.transaction.Status.STATUS_ACTIVE) {
				ut.rollback();
			}
		} catch (Exception ignored) {
			// Ignorar erro no rollback (já estamos em exceção)
		}
	}

	private void bindParams(TypedQuery<?> query, Object... params) {
		if (params != null) {
			for (int i = 0; i < params.length; i += 2) {
				String nomeParam = (String) params[i];
				Object valor = params[i + 1];
				query.setParameter(nomeParam, valor);
			}
		}
	}

	/**
	 * Validação simples de ORDER BY para evitar injeção JPQL/HQL. Use whitelist de
	 * campos permitidos. Expanda conforme seus campos reais.
	 */
	private String getSafeOrderBy(String orderBy) {
		if (orderBy == null || orderBy.trim().isEmpty()) {
			return "f.nome ASC"; // default seguro
		}

		String cleaned = orderBy.trim().toLowerCase();

		// Whitelist de campos + direção permitida
		if (cleaned.startsWith("nome") || cleaned.startsWith("f.nome")) {
			return cleaned.contains("desc") ? "f.nome DESC" : "f.nome ASC";
		}
		if (cleaned.startsWith("cpf") || cleaned.startsWith("f.cpf")) {
			return cleaned.contains("desc") ? "f.cpf DESC" : "f.cpf ASC";
		}
		// Adicione mais campos conforme sua entidade: dataNascimento, codAgencia, etc.

		// Se não for reconhecido → fallback seguro
		return "f.nome ASC";
	}
}