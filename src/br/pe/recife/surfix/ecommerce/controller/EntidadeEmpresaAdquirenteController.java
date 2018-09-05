package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.entity.http.EmpresaAdquirenteHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoEmpresasAdquirentesHttp;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.fachada.FachadaDB;

@Path("/empresaAdquirente")
public class EntidadeEmpresaAdquirenteController {
	
	private FachadaDB fachadaDB = FachadaDB.getInstancia();
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoEmpresasAdquirentesHttp listar() {
	
		RetornoEmpresasAdquirentesHttp res = new RetornoEmpresasAdquirentesHttp();
		res.setResultado(RetornoEmpresasAdquirentesHttp.SUCESSO);
		
		try { 
		
			List<EmpresaAdquirente> empresasAdquirentes = fachadaDB.empresaAdquirenteListar();
			
			EmpresaAdquirenteHttp[] empresasAdquirentesHttp = 
					gerarArrayEmpresasAdquirentesHttp(empresasAdquirentes);	
			
			res.setEmpresasAdquirentes(empresasAdquirentesHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}
	
	private EmpresaAdquirenteHttp[] gerarArrayEmpresasAdquirentesHttp
		(List<EmpresaAdquirente> empresasAdquirentes) {
		
		EmpresaAdquirenteHttp[] empresasAdquirentesHttp = 
				new EmpresaAdquirenteHttp[empresasAdquirentes.size()];			
		
		for (int i=0; i < empresasAdquirentes.size(); i++) {
			
			EmpresaAdquirente empresaAdquirente = empresasAdquirentes.get(i);
			
			EmpresaAdquirenteHttp empresaAdquirenteHttp = new EmpresaAdquirenteHttp();
			empresaAdquirenteHttp.setId(empresaAdquirente.getId());
			empresaAdquirenteHttp.setIdEmpresa(empresaAdquirente.getEmpresa().getId());
			empresaAdquirenteHttp.setIdAdquirente(empresaAdquirente.getAdquirente().getId());
			empresaAdquirenteHttp.setMecId(empresaAdquirente.getMecId());
			empresaAdquirenteHttp.setMecKey(empresaAdquirente.getMecKey());
			empresaAdquirenteHttp.setMecIdTeste(empresaAdquirente.getMecIdTeste());
			empresaAdquirenteHttp.setMecKeyTeste(empresaAdquirente.getMecKeyTeste());
			
			empresasAdquirentesHttp[i] = empresaAdquirenteHttp;
		}
		
		return empresasAdquirentesHttp;
	}

}
