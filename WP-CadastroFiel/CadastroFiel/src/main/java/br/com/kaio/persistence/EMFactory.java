package br.com.kaio.persistence;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

public class EMFactory {

	public static EntityManager getEntityManager() throws Exception {

		try {
			Context ctx = new InitialContext();
			return (EntityManager) ctx.lookup("java:comp/env/CadastroFiel/EntityManager");
		} catch (Exception e) {
			throw e;
		}
	}

	public static UserTransaction getUserTransaction() throws Exception {

		try {
			Context ctx = new InitialContext();
			return (UserTransaction) ctx.lookup("java:comp/UserTransaction");
		} catch (Exception e) {
			throw e;
		}
	}
}
