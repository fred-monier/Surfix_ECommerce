package br.pe.recife.surfix.ecommerce.fachada;

import br.pe.recife.surfix.ecommerce.entity.Empresa;
import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.service.EmpresaAdquirenteService;
import br.pe.recife.surfix.ecommerce.service.EmpresaService;

public class FachadaDB {
	
	private static FachadaDB instancia;
	
	private EmpresaService empresaService;
	private EmpresaAdquirenteService empresaAdquirenteService;
	
	private FachadaDB() {
		this.empresaService = EmpresaService.getInstancia();
		this.empresaAdquirenteService = EmpresaAdquirenteService.getInstancia();
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
	
	//EmpresaAdquirenteService*****************************************************
		public EmpresaAdquirente empresaAdquirenteConsultarPorId(Integer id) throws InfraException {
			
			return empresaAdquirenteService.consultarPorId(id);		
		}
		
		public void empresaAdquirenteSalvar(EmpresaAdquirente empresaAdquirente) throws InfraException {
			
			empresaAdquirenteService.salvar(empresaAdquirente);		
		}
		
		public void empresaAdquirenteExcluir(Integer id) throws InfraException {		
			
			empresaAdquirenteService.excluir(id);
			
		}						
		//*******************************************************************

}
