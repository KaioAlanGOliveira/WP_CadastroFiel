package br.com.kaio.business;

import java.util.List;

import br.com.kaio.domain.Pagamento;
import br.com.kaio.domain.PagamentoId;
import br.com.kaio.domain.PagamentoView;
import br.com.kaio.persistence.dao.PagamentoDao;
import br.com.kaio.persistence.dao.PagamentoViewDao;

public class PagamentoBss {

	/**
	 * apagar todos desse cpf
	 */
	public void apagarTodos(String cpf) throws Exception {

		PagamentoDao dao = new PagamentoDao();
		List<Pagamento> lista = dao.getLista(cpf);
		for (Pagamento pagamento : lista) {
			dao.apagar(pagamento);
		}
	}

	public void apagar(PagamentoId cpf) throws Exception {

		PagamentoDao dao = new PagamentoDao();
		var pagamento = dao.getPagamento(cpf);
		dao.apagar(pagamento);
	}

	public Pagamento adiocionar(Pagamento pg) throws Exception {

		try {
			PagamentoDao dao = new PagamentoDao();
			dao.adicionar(pg);

			return pg;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void atualizar(Pagamento pg) throws Exception {

		PagamentoDao dao = new PagamentoDao();

		Pagamento existente = dao.getPagamento(pg.getId());
		if (existente != null) {
			dao.atualizar(pg);
		}
	}

	public List<PagamentoView> getLista(String cpf, String nome) throws Exception {
		try {
			PagamentoViewDao dao = new PagamentoViewDao();
			return dao.getLista(cpf, nome);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Pagamento getPagamento(PagamentoId id) throws Exception {
		try {
			PagamentoDao dao = new PagamentoDao();
			Pagamento pagamento = dao.getPagamento(id);
			return pagamento;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
