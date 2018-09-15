package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.pe.recife.surfix.ecommerce.entity.Empresa;

@Repository
public class EmpresaDAO implements EmpresaDAOIntf {
	
	@PersistenceContext
    private EntityManager manager;
	
	@Override
	public List<Empresa> listar() {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Empresa> cq = cb.createQuery(Empresa.class);
        Root<Empresa> rootEntry = cq.from(Empresa.class);
        CriteriaQuery<Empresa> all = cq.select(rootEntry);
        TypedQuery<Empresa> allQuery = manager.createQuery(all);
        
        return allQuery.getResultList();
	}

	@Override
	public Empresa consultarPorId(int id) {
		return manager.find(Empresa.class, id);
	}
	
	@Override
	public void salvar(Empresa empresa) {
		
		if (empresa.getId() == null) {			
			manager.persist(empresa);
			
		} else {			
			manager.merge(empresa);
		}		
	}
	
	@Override
	public void excluir(int id) {		
		Empresa empresa = consultarPorId(id);
        manager.remove(empresa);		
	}

}
