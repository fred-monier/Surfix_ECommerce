package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.pe.recife.surfix.ecommerce.entity.Adquirente;
import br.pe.recife.surfix.ecommerce.entity.http.AdquirenteHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoAdquirentesHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.service.AdquirenteService;

@Component
@Path("/adquirente")
public class EntidadeAdquirenteController {

	@Autowired
	private AdquirenteService adquirenteService;
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoAdquirentesHttp listar() {
	
		RetornoAdquirentesHttp res = new RetornoAdquirentesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<Adquirente> adquirentes = adquirenteService.listar();
			
			AdquirenteHttp[] adquirentesHttp = AdquirenteHttp.gerarArranjoAdquirentesHttp(adquirentes);	
			
			res.setAdquirentes(adquirentesHttp);
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}
	
}
