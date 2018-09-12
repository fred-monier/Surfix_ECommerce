package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.pe.recife.surfix.ecommerce.entity.EntidadeBase;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

//Classe teste não mais sendo utilizada em favor do Spring
public class EntidadeBaseDAO <T extends EntidadeBase> {
	
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("surfix-ecommerce");
		
	public List<T> listar(Class<T> clazz) throws InfraException {
		
		EntityManager manager = null;
		
		try {
			
			manager = factory.createEntityManager();
			
	        CriteriaBuilder cb = manager.getCriteriaBuilder();
	        CriteriaQuery<T> cq = cb.createQuery(clazz);
	        Root<T> rootEntry = cq.from(clazz);
	        CriteriaQuery<T> all = cq.select(rootEntry);
	        TypedQuery<T> allQuery = manager.createQuery(all);
	        
	        return allQuery.getResultList();			
			
			
		} catch (Exception e) {
			
			throw new InfraException(e);
		
		} finally {
			
			if (manager != null) {
				manager.close();
			}		
		}				
	}	
	
	public T consultarPorId(Class<T> clazz, int id) throws InfraException {
		
		EntityManager manager = null;
		
		try {
			
			manager = factory.createEntityManager();
			
			return manager.find(clazz, id);
			
		} catch (Exception e) {
			
			throw new InfraException(e);
		
		} finally {
			
			if (manager != null) {
				manager.close();
			}		
		}				
	}
	
	public void salvar(T obj) throws InfraException { 
		
		EntityManager manager = null;
		
		try {
			
			manager = factory.createEntityManager();
			
			manager.getTransaction().begin();
			
			if (obj.getId() == null) {
				
				manager.persist(obj);
				
			} else {
				
				manager.merge(obj);
			}
			
			manager.getTransaction().commit();
			
		} catch(Exception e) {
			
			if (manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			
			throw new InfraException(e);
			
		} finally {
			
			if (manager != null) {
				manager.close();
			}
		}
	}
	
	public void excluir(Class<T> clazz, int id) throws InfraException { 
		
		EntityManager manager = null;				
		
		try {
			
			manager = factory.createEntityManager();
			
			T t = manager.find(clazz, id);
			
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			
		} catch (Exception e) {
			
			if (manager.getTransaction() != null) {
				manager.getTransaction().rollback();
			}
			
			throw new InfraException(e);
		
		} finally {
			
			if (manager != null) {
				manager.close();
			}		
		}	
						
	}

}
