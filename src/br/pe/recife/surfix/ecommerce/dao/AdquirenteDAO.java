package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.pe.recife.surfix.ecommerce.entity.Adquirente;

@Repository
public class AdquirenteDAO implements AdquirenteDAOIntf {
	
	@PersistenceContext
    private EntityManager manager;

	@Override
	public List<Adquirente> listar() {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Adquirente> cq = cb.createQuery(Adquirente.class);
        Root<Adquirente> rootEntry = cq.from(Adquirente.class);
        CriteriaQuery<Adquirente> all = cq.select(rootEntry);
        TypedQuery<Adquirente> allQuery = manager.createQuery(all);
        
        return allQuery.getResultList();
	}

	@Override
	public Adquirente consultarPorId(int id) {
		return manager.find(Adquirente.class, id);
	}

	@Override
	public void salvar(Adquirente adquirente) {
		
		if (adquirente.getId() == null) {			
			manager.persist(adquirente);
			
		} else {			
			manager.merge(adquirente);
		}	
		
	}

	@Override
	public void excluir(int id) {
		
		Adquirente adquirente = consultarPorId(id);
        manager.remove(adquirente);
		
	}

}
