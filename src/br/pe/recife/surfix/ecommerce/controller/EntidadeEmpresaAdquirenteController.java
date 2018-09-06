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
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

@Path("/empresaAdquirente")
public class EntidadeEmpresaAdquirenteController {
	
	private FachadaDB fachadaDB = FachadaDB.getInstancia();
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoEmpresasAdquirentesHttp listar() {
	
		RetornoEmpresasAdquirentesHttp res = new RetornoEmpresasAdquirentesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<EmpresaAdquirente> empresasAdquirentes = fachadaDB.empresaAdquirenteListar();
			
			EmpresaAdquirenteHttp[] empresasAdquirentesHttp = 
					EmpresaAdquirenteHttp.gerarArrayEmpresasAdquirentesHttp(empresasAdquirentes);	
			
			res.setEmpresasAdquirentes(empresasAdquirentesHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}	

}
