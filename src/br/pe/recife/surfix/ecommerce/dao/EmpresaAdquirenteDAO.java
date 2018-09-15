package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;

@Repository
public class EmpresaAdquirenteDAO implements EmpresaAdquirenteDAOIntf {
	
	@PersistenceContext
    private EntityManager manager;

	@Override
	public List<EmpresaAdquirente> listar() {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<EmpresaAdquirente> cq = cb.createQuery(EmpresaAdquirente.class);
        Root<EmpresaAdquirente> rootEntry = cq.from(EmpresaAdquirente.class);
        CriteriaQuery<EmpresaAdquirente> all = cq.select(rootEntry);
        TypedQuery<EmpresaAdquirente> allQuery = manager.createQuery(all);
        
        return allQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EmpresaAdquirente> listarPorEmpresa(int idEmpresa) {
				
		Query query = manager
	            .createQuery("select ea from \"EMPRESA_ADQUIRENTE\" as ea " +
	    				"where ea.\"ID_EMPRESA\" = :paramIdEmpresa");
	    query.setParameter("paramIdEmpresa", idEmpresa);
		
	    List<EmpresaAdquirente> lista = query.getResultList();
	
	    return lista;					
	}
		
	
	@Override
	public EmpresaAdquirente consultarPorId(int id) {
		return manager.find(EmpresaAdquirente.class, id);
	}

	@Override
	public void salvar(EmpresaAdquirente empresaAdquirente) {
		
		if (empresaAdquirente.getId() == null) {			
			manager.persist(empresaAdquirente);
			
		} else {			
			manager.merge(empresaAdquirente);
		}	
		
	}

	@Override
	public void excluir(int id) {
		
		EmpresaAdquirente empresaAdquirente = consultarPorId(id);
        manager.remove(empresaAdquirente);
		
	}

}
