package br.pe.recife.surfix.ecommerce.fachada;

import br.pe.recife.surfix.ecommerce.entity.Empresa;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.service.EmpresaService;

public class FachadaDB {
	
	private static FachadaDB instancia;
	
	private EmpresaService empresaService;
	
	private FachadaDB() {
		this.empresaService = EmpresaService.getInstancia();
	}
	
	public static FachadaDB getInstancia() {
		
		if (instancia == null) {
			instancia = new FachadaDB();			
		}
		
		return instancia;		
	}
	
	//EmpresaService*****************************************************
	public Empresa empresaConsultarPorId(Integer id) throws InfraException {
		
		return empresaService.consultarPorId(id);		
	}
	
	public void empresaSalvar(Empresa empresa) throws InfraException {
		
		empresaService.salvar(empresa);		
	}
	
	public void empresaExcluir(Integer id) throws InfraException {		
		
		empresaService.excluir(id);
		
	}						
	//*******************************************************************

}
