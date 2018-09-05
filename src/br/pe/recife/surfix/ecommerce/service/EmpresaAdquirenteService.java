package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import br.pe.recife.surfix.ecommerce.dao.EntidadeBaseDAO;
import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

public class EmpresaAdquirenteService {
	
	private static EmpresaAdquirenteService instancia;
	
	private EmpresaAdquirenteService() {		
	}
	
	public static EmpresaAdquirenteService getInstancia() {
		
		if (instancia == null) {
			instancia = new EmpresaAdquirenteService();			
		}
		
		return instancia;
		
	}
	
	public List<EmpresaAdquirente> listar() throws InfraException {
		
		EntidadeBaseDAO<EmpresaAdquirente> dao = new EntidadeBaseDAO<EmpresaAdquirente>();
		
		return dao.listar(EmpresaAdquirente.class);
	}
	
	public EmpresaAdquirente consultarPorId(Integer id) throws InfraException {
		
		EntidadeBaseDAO<EmpresaAdquirente> dao = new EntidadeBaseDAO<EmpresaAdquirente>();
		
		return dao.consultarPorId(EmpresaAdquirente.class, id);
		
	}
	
	public void salvar(EmpresaAdquirente empresaAdquirente) throws InfraException {
		
		EntidadeBaseDAO<EmpresaAdquirente> dao = new EntidadeBaseDAO<EmpresaAdquirente>();
		
		dao.salvar(empresaAdquirente);
		
	}
	
	public void excluir(Integer id) throws InfraException {
		
		EntidadeBaseDAO<EmpresaAdquirente> dao = new EntidadeBaseDAO<EmpresaAdquirente>();
		
		dao.excluir(EmpresaAdquirente.class, id);
		
	}
		
}
