package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.pe.recife.surfix.ecommerce.entity.Transacao;

@Repository
public class TransacaoDAO implements TransacaoDAOIntf {
	
	@PersistenceContext
    private EntityManager manager;

	@Override
	public List<Transacao> listar() {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Transacao> cq = cb.createQuery(Transacao.class);
        Root<Transacao> rootEntry = cq.from(Transacao.class);
        CriteriaQuery<Transacao> all = cq.select(rootEntry);
        TypedQuery<Transacao> allQuery = manager.createQuery(all);
        
        return allQuery.getResultList();
	}

	@Override
	public Transacao consultarPorId(int id) {
		return manager.find(Transacao.class, id);
	}

	@Override
	public void salvar(Transacao transacao) {
		
		if (transacao.getId() == null) {			
			manager.persist(transacao);
			
		} else {			
			manager.merge(transacao);
		}	
		
	}

	@Override
	public void excluir(int id) {
		
		Transacao transacao = consultarPorId(id);
        manager.remove(transacao);
		
	}

}
