package br.com.kaio.service;

import java.util.List;

import br.com.kaio.business.FielBss;
import br.com.kaio.business.PagamentoBss;
import br.com.kaio.domain.Pagamento;
import br.com.kaio.domain.PagamentoId;
import br.com.kaio.domain.PagamentoView;

public class PagamentoService {

	// public List<Pagamento> getFieis(String cpf) throws Exception {
	//
	// try {
	// PagamentoDao dao = new PagamentoDao();
	// return dao.getLista(cpf);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw e;
	// }
	// }

	public Pagamento adicionar(Pagamento pg) throws Exception {

		PagamentoBss bss = new PagamentoBss();
		return bss.adiocionar(pg);
	}

	public void atualizar(Pagamento pg) throws Exception {

		try {

			PagamentoBss dao = new PagamentoBss();
			dao.atualizar(pg);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void apagar(PagamentoId cpf) throws Exception {

		try {
			PagamentoBss bss = new  PagamentoBss();
			bss.apagar(cpf);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<PagamentoView> getPagamentoVw(String cpf, String nome) throws Exception {

		try {
			PagamentoBss pgss = new PagamentoBss();
			return pgss.getLista(cpf, nome);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Pagamento getPagamento(PagamentoId id) throws Exception {

		try {
			PagamentoBss pgss = new PagamentoBss();
			Pagamento pagamento = pgss.getPagamento(id);
			return pagamento;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
