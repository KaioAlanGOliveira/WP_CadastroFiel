package br.com.kaio.business;

import java.util.List;

import br.com.kaio.domain.Fiel;
import br.com.kaio.domain.PagamentoView;
import br.com.kaio.persistence.dao.FielDao;
import br.com.kaio.persistence.dao.PagamentoViewDao;
import br.com.kaio.ultil.Confs;

public class FielBss {

	public Fiel adiocionar(Fiel f) throws Exception {

		try {
			FielDao dao = new FielDao();

			// Valida se ja existe para customizar mensagem
			Fiel fielTmp = dao.getFiel(f.getCpf());
			if (fielTmp != null)
				throw new Exception("Essa CPF ja está cadastrado!");

			dao.adicionar(f);

			return f;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Fiel getEntity( String cpf) throws Exception {

		try {
			FielDao dao = new FielDao();

			Fiel f = dao.getFiel(cpf);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void apagar(String cpf) throws Exception {

		FielDao daoFl = new FielDao();

		if (Confs.PERMITE_APAGAR_PAGAMENTO) {
			// Valida se ja existe para customizar mensagem
			PagamentoViewDao daoPg = new PagamentoViewDao();
			List<PagamentoView> pagamentos = daoPg.getLista(cpf, "");
			if (pagamentos != null && !pagamentos.isEmpty())
				throw new Exception("Essa CPF ja está tem pagamento!");
		} else {
			// Apaga tudo
			PagamentoBss pagamentoBss = new PagamentoBss();
			pagamentoBss.apagarTodos(cpf);
		}
		daoFl.apagar(cpf);
	}

}
