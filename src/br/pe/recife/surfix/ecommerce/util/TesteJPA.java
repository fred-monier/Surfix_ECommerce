package br.pe.recife.surfix.ecommerce.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteJPA {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("surfix-ecommerce");
		EntityManager em = emf.createEntityManager();
		em.close();
		emf.close();

	}

}
