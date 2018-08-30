package br.pe.recife.surfix.ecommerce.db;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ECommerceDB {
	
	private static ECommerceDB instancia;
	
	private EntityManagerFactory entityManagerFactory;
	
	private ECommerceDB() {		
		entityManagerFactory = Persistence.createEntityManagerFactory("surfix-ecommerce");
	}
	
	public static ECommerceDB getInstancia() {
		
		if (instancia == null) {
			instancia = new ECommerceDB();			
		}
		
		return instancia;
	}
	
	public EntityManagerFactory getEMFactory() {
		return this.entityManagerFactory;
	}
	
	

}
