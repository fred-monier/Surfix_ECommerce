package br.pe.recife.surfix.ecommerce.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.pe.recife.surfix.ecommerce.entity.Transacao;

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
	public List<Transacao> listarPaisPorEmpresaENumPedidoVirtual(int idEmpresa, String numPedVirtual) {
		
		Query query = manager				
	            //.createQuery("select distinct t from Transacao t left join fetch t.transacoesFilhas " + // <-- Originalmente em PostgreSql:
	            //		"where t.empresaAdquirente.empresa.id = :idEmpresa and " +
	    		//		"t.numPedidoVirtual = :paramNumPedVirtual and t.transacaoPai = null");		
				.createQuery("select distinct t from Transacao t left join fetch t.transacoesFilhas " +
						"join fetch t.empresaAdquirente ed " +
						"where ed.empresa.id = :idEmpresa and " +
						"t.numPedidoVirtual = :paramNumPedVirtual and t.transacaoPai = null");
		
		query.setParameter("idEmpresa", idEmpresa);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transacao> listarPaisPorEmpAdqEDataHoraEOperacao(int idEmp, int idEmpAdq, LocalDateTime dataHoraInicio, 
			LocalDateTime dataHoraFim, String operacao) {
		
		boolean pEmp = false;
		boolean pEmpAdq = false;
		boolean pOper = false;;
		
		String sql = "select distinct t from Transacao t left join fetch t.transacoesFilhas " +
				"join fetch t.empresaAdquirente ed " +
				"where t.dataHora >= :dataHoraInicio and t.dataHora <= :dataHoraFim " +
        		"and t.transacaoPai = null";
		
		if (idEmp > 0) {			
			sql = sql + " and ed.empresa.id = :idEmp";
			pEmp = true;
		}
		
		if (idEmpAdq > 0) {
			sql = sql + " and ed.id = :idEmpAdq";
			pEmpAdq = true;
		}
			
		if (operacao != null && !operacao.equals("")) {
			sql = sql + " and t.operacao = :operacao";
			pOper = true;
		}			
		
		Query query = manager
	            .createQuery(sql);
		
		query.setParameter("dataHoraInicio", dataHoraInicio);
		query.setParameter("dataHoraFim", dataHoraFim);
		
		if(pEmp) {
			query.setParameter("idEmp", idEmp);
		}
		
		if (pEmpAdq) {
			query.setParameter("idEmpAdq", idEmpAdq);
		}
		
		if (pOper) {
			query.setParameter("operacao", operacao);
		}
	    
	    List<Transacao> lista = query.getResultList();
		
		return lista;
	}

	@Override
	public Transacao consultarPorId(int id) {
		
		//return manager.find(Transacao.class, id);
		
		Query q = manager.createQuery("select t from Transacao t left join fetch " + 
				"t.transacoesFilhas WHERE t.id = :id");
		q.setParameter("id", id);
		
		return (Transacao) q.getSingleResult();		
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
