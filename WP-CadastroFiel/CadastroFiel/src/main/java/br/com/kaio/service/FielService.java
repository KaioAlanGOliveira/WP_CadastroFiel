package br.com.kaio.service;

import java.util.List;

import br.com.kaio.business.FielBss;
import br.com.kaio.domain.Fiel;
import br.com.kaio.persistence.dao.FielDao;

public class FielService {

	public Fiel getEntity(String cpf) throws Exception {
		
		FielBss fss = new FielBss();
		return fss.getEntity(cpf);
		
	}

	public Fiel adicionar(Fiel f) throws Exception {

		replaceData(f);
		
		FielBss bss = new  FielBss();
		return bss.adiocionar(f);
	}

	public void apagar(String cpf) throws Exception {

		try {
			FielBss bss = new  FielBss();
			bss.apagar(cpf);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void atualizar(Fiel f) throws Exception {

		replaceData(f);
		
		try {
			
			FielDao dao = new FielDao();
			dao.atualizar(f);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<Fiel> getFieis(String cpf, String nome) throws Exception {

		try {
			FielDao dao = new FielDao();
			return dao.getLista(cpf, nome);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void replaceData(Fiel f) {
		
		if (f.getIdade() != null && f.getIdade() == 0 ) {
			f.setIdade(null);
		}
	}


}
