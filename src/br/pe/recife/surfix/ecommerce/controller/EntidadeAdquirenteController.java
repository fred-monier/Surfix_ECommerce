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

@Path("/adquirente")
public class EntidadeAdquirenteController {

	private FachadaDB fachadaDB = FachadaDB.getInstancia();
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoAdquirentesHttp listar() {
	
		RetornoAdquirentesHttp res = new RetornoAdquirentesHttp();
		res.setResultado(RetornoAdquirentesHttp.SUCESSO);
		
		try { 
		
			List<Adquirente> adquirentes = fachadaDB.adquirenteListar();
			
			AdquirenteHttp[] adquirentesHttp = gerarArranjoAdquirentesHttp(adquirentes);	
			
			res.setAdquirentes(adquirentesHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}
	
	private AdquirenteHttp[] gerarArranjoAdquirentesHttp(List<Adquirente> adquirentes) {
		
		AdquirenteHttp[] adquirentesHttp = new AdquirenteHttp[adquirentes.size()];			
		
		for (int i=0; i < adquirentes.size(); i++) {
			
			Adquirente adquirente = adquirentes.get(i);
			
			AdquirenteHttp adquirenteHttp = new AdquirenteHttp();
			adquirenteHttp.setId(adquirente.getId());
			adquirenteHttp.setNome(adquirente.getNome());
			adquirenteHttp.setDescricao(adquirente.getDescricao());
			
			adquirentesHttp[i] = adquirenteHttp;
		}
		
		return adquirentesHttp;
	}
	
}
