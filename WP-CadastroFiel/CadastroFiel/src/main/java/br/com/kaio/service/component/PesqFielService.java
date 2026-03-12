package br.com.kaio.service.component;

import java.util.List;
import br.com.kaio.business.BusinessException;
import br.com.kaio.domain.Fiel;
import br.com.kaio.persistence.dao.FielDao;

public class PesqFielService {

    private final FielDao dao = new FielDao();

    /**
     * Busca um Fiel pelo CPF (assumindo que o CPF é a chave primária da entidade Fiel)
     */
    public Fiel getEntity(String cpf) throws BusinessException {
        try {
            if (cpf == null || cpf.trim().isEmpty()) {
                return null;
            }

            // Forma segura com parâmetro nomeado
            Fiel fiel = dao.getEntityByCond("f.cpf = :cpf", "cpf", cpf.trim());

            if (fiel == null) {
                throw new BusinessException("Fiel não encontrado para o CPF informado.");
            }

            return fiel;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar fiel por CPF: " + e.getMessage(), e);
        }
    }

    /**
     * Lista fiéis: 
     * - se pesquisa vazia → retorna todos ordenados por codAgencia (ou outro critério)
     * - se pesquisa preenchida → busca por parte do nome (case insensitive)
     */
    public List<Fiel> getList(String pesquisa) throws BusinessException {
        try {
            if (pesquisa == null || pesquisa.trim().isEmpty()) {
                // Retorna todos ordenados (você pode mudar o campo de ordenação padrão)
                return dao.getListByOrder("f.nome ASC");   // ← alterei para nome, mais natural
                // ou mantenha "f.codAgencia ASC" se fizer mais sentido no seu negócio
            } else {
                // Busca por parte do nome (case insensitive)
                String condicao = "UPPER(f.nome) LIKE :pesq";
                String valorPesq = "%" + pesquisa.trim().toUpperCase() + "%";

                return dao.getListByCond(condicao, "pesq", valorPesq);
            }
        } catch (Exception e) {
            throw new BusinessException("Não foi possível retornar a lista de fiéis.", e);
        }
    }
}