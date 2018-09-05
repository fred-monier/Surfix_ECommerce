package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import br.pe.recife.surfix.ecommerce.dao.EntidadeBaseDAO;
import br.pe.recife.surfix.ecommerce.entity.Adquirente;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

public class AdquirenteService {
	
	private static AdquirenteService instancia;
	
	private AdquirenteService() {		
	}
	
	public static AdquirenteService getInstancia() {
		
		if (instancia == null) {
			instancia = new AdquirenteService();			
		}
		
		return instancia;
		
	}
	
	public List<Adquirente> listar() throws InfraException {
		
		EntidadeBaseDAO<Adquirente> dao = new EntidadeBaseDAO<Adquirente>();
		
		return dao.listar(Adquirente.class);
	}
	
	public Adquirente consultarPorId(Integer id) throws InfraException {
		
		EntidadeBaseDAO<Adquirente> dao = new EntidadeBaseDAO<Adquirente>();
		
		return dao.consultarPorId(Adquirente.class, id);
		
	}
	
	public void salvar(Adquirente Adquirente) throws InfraException {
		
		EntidadeBaseDAO<Adquirente> dao = new EntidadeBaseDAO<Adquirente>();
		
		dao.salvar(Adquirente);
		
	}
	
	public void excluir(Integer id) throws InfraException {
		
		EntidadeBaseDAO<Adquirente> dao = new EntidadeBaseDAO<Adquirente>();
		
		dao.excluir(Adquirente.class, id);
		
	}

}
