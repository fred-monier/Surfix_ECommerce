package br.pe.recife.surfix.ecommerce.dao;

import javax.persistence.EntityManager;

import br.pe.recife.surfix.ecommerce.db.ECommerceDB;
import br.pe.recife.surfix.ecommerce.entity.EntidadeBase;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

public class EntidadeBaseDAO <T extends EntidadeBase> {
	
	private ECommerceDB ecommerceDB = ECommerceDB.getInstancia();
	
	public T consultarPorId(Class<T> clazz, int id) throws InfraException {
		
		EntityManager manager = null;
		
		try {
			
			manager = ecommerceDB.getEMFactory().createEntityManager();
			
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
			
			manager = ecommerceDB.getEMFactory().createEntityManager();
			
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
			
			manager = ecommerceDB.getEMFactory().createEntityManager();
			
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
