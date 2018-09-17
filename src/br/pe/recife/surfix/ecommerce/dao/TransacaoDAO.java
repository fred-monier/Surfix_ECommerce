package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.pe.recife.surfix.ecommerce.entity.Transacao;

//TODO - Qual a melhor forma de gerar selects?
@Repository
public class TransacaoDAO implements TransacaoDAOIntf {
	
	@PersistenceContext
    private EntityManager manager;

	/*
	public List<Transacao> listar() {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Transacao> cq = cb.createQuery(Transacao.class);
        Root<Transacao> rootEntry = cq.from(Transacao.class);
        CriteriaQuery<Transacao> all = cq.select(rootEntry);
        TypedQuery<Transacao> allQuery = manager.createQuery(all);
        
        return allQuery.getResultList();
	}
	*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transacao> listarPais() {
			
		Query query = manager.createQuery("select distinct t from Transacao t " + 
				"left join fetch t.transacoesFilhas where t.transacaoPai = null");
		List<Transacao> lista = query.getResultList(); 
			
	    return lista;
	    
	    /*
	    String jpql = "select t from Transacao t where t.transacaoPai = null";
	    return this.manager
	        .createQuery(jpql, Usuario.class)
	        .setParameter("email", email)
	        .getSingleResult();	    
	    */
	    
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transacao> listarPaisPorEmpAdqENumPedidoVirtual(int idEmpAdq, String numPedVirtual) {
		
		Query query = manager
	            .createQuery("select t from Transacao t left join fetch t.transacoesFilhas " +
	            		"where t.empresaAdquirente.id = :idEmpAdq and " +
	    				"t.numPedidoVirtual = :paramNumPedVirtual and t.transacaoPai = null");
		
		query.setParameter("idEmpAdq", idEmpAdq);
	    query.setParameter("paramNumPedVirtual", numPedVirtual);
	    
	    /*
		entityManager.createQuery(
        "select pc " +
        "from PostComment pc " +
        "where pc.review = :review", PostComment.class)
    		.setParameter("review", review)
    		.getResultList();	     
    	*/
		
	    List<Transacao> lista = query.getResultList();
	
	    return lista;		
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
