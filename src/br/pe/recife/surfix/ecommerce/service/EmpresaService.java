package br.pe.recife.surfix.ecommerce.service;

import br.pe.recife.surfix.ecommerce.dao.EntidadeBaseDAO;
import br.pe.recife.surfix.ecommerce.entity.Empresa;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

public class EmpresaService {
	
	private static EmpresaService instancia;
	
	private EmpresaService() {		
	}
	
	public static EmpresaService getInstancia() {
		
		if (instancia == null) {
			instancia = new EmpresaService();			
		}
		
		return instancia;
		
	}
	
	public Empresa consultarPorId(Integer id) throws InfraException {
		
		EntidadeBaseDAO<Empresa> dao = new EntidadeBaseDAO<Empresa>();
		
		return dao.consultarPorId(Empresa.class, id);
		
	}
	
	public void salvar(Empresa empresa) throws InfraException {
		
		EntidadeBaseDAO<Empresa> dao = new EntidadeBaseDAO<Empresa>();
		
		dao.salvar(empresa);
		
	}
	
	public void excluir(Integer id) throws InfraException {
		
		EntidadeBaseDAO<Empresa> dao = new EntidadeBaseDAO<Empresa>();
		
		dao.excluir(Empresa.class, id);
		
	}
	
}
