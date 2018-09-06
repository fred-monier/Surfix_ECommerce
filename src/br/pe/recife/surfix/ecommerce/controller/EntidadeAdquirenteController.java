package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.pe.recife.surfix.ecommerce.entity.Adquirente;
import br.pe.recife.surfix.ecommerce.entity.http.AdquirenteHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoAdquirentesHttp;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.fachada.FachadaDB;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

@Path("/adquirente")
public class EntidadeAdquirenteController {

	private FachadaDB fachadaDB = FachadaDB.getInstancia();
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoAdquirentesHttp listar() {
	
		RetornoAdquirentesHttp res = new RetornoAdquirentesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<Adquirente> adquirentes = fachadaDB.adquirenteListar();
			
			AdquirenteHttp[] adquirentesHttp = AdquirenteHttp.gerarArranjoAdquirentesHttp(adquirentes);	
			
			res.setAdquirentes(adquirentesHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}
	
}
