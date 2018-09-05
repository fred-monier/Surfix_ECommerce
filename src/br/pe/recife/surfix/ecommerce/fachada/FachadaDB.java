package br.pe.recife.surfix.ecommerce.fachada;

import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Adquirente;
import br.pe.recife.surfix.ecommerce.entity.Empresa;
import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.entity.Transacao;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.service.AdquirenteService;
import br.pe.recife.surfix.ecommerce.service.EmpresaAdquirenteService;
import br.pe.recife.surfix.ecommerce.service.EmpresaService;
import br.pe.recife.surfix.ecommerce.service.TransacaoService;

public class FachadaDB {
	
	private static FachadaDB instancia;
	
	private EmpresaService empresaService;
	private AdquirenteService adquirenteService;
	private EmpresaAdquirenteService empresaAdquirenteService;
	private TransacaoService transacaoService;
	
	private FachadaDB() {
		this.empresaService = EmpresaService.getInstancia();
		this.adquirenteService = AdquirenteService.getInstancia();
		this.empresaAdquirenteService = EmpresaAdquirenteService.getInstancia();
		this.transacaoService = TransacaoService.getInstancia();
	}
	
	public static FachadaDB getInstancia() {
		
		if (instancia == null) {
			instancia = new FachadaDB();			
		}
		
		return instancia;		
	}
	
	//EmpresaService*****************************************************
	public List<Empresa> empresaListar()  throws InfraException {
		return empresaService.listar();
	}
		
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
	
	//AdquirenteService*****************************************************
	public List<Adquirente> adquirenteListar()  throws InfraException {
		return adquirenteService.listar();
	}
		
	public Adquirente adquirenteConsultarPorId(Integer id) throws InfraException {
		
		return adquirenteService.consultarPorId(id);		
	}
	
	public void adquirenteSalvar(Adquirente adquirente) throws InfraException {
		
		adquirenteService.salvar(adquirente);		
	}
	
	public void adquirenteExcluir(Integer id) throws InfraException {		
		
		adquirenteService.excluir(id);
		
	}						
	//*******************************************************************
	
	//EmpresaAdquirenteService*****************************************************
	public List<EmpresaAdquirente> empresaAdquirenteListar()  throws InfraException {
		return empresaAdquirenteService.listar();
	}
	
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
	
	//TrnsacaoService*****************************************************
	public List<Transacao> transacaoListar()  throws InfraException {
		return transacaoService.listar();
	}
	
	public Transacao transacaoConsultarPorId(Integer id) throws InfraException {
		
		return transacaoService.consultarPorId(id);		
	}
	
	public void transacaoSalvar(Transacao transacao) throws InfraException {
		
		transacaoService.salvar(transacao);		
	}
	
	public void transacaoExcluir(Integer id) throws InfraException {		
		
		transacaoService.excluir(id);
		
	}						
	//*******************************************************************	
				
}
