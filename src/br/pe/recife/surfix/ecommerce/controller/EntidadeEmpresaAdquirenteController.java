package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.entity.http.EmpresaAdquirenteHttp;
import br.pe.recife.surfix.ecommerce.entity.http.EmpresaAdquirenteNomeAdqHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoEmpresasAdquirentesHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoEmpresasAdquirentesNomeAdqHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.service.EmpresaAdquirenteService;

@Component
@Path("/empresa_adquirente")
public class EntidadeEmpresaAdquirenteController {
	
	@Autowired
	private EmpresaAdquirenteService empresaAdquirenteService;
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@DenyAll
	public RetornoEmpresasAdquirentesHttp listar() {
	
		RetornoEmpresasAdquirentesHttp res = new RetornoEmpresasAdquirentesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<EmpresaAdquirente> empresasAdquirentes = empresaAdquirenteService.listar();
			
			EmpresaAdquirenteHttp[] empresasAdquirentesHttp = 
					EmpresaAdquirenteHttp.gerarArrayEmpresasAdquirentesHttp(empresasAdquirentes);	
			
			res.setEmpresasAdquirentes(empresasAdquirentesHttp);
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar_por_comercialid")
	@RolesAllowed("ADMIN")
	public RetornoEmpresasAdquirentesNomeAdqHttp listarPorEmpresaId(@HeaderParam("idComercial") String idComercial) {
	
		RetornoEmpresasAdquirentesNomeAdqHttp res = new RetornoEmpresasAdquirentesNomeAdqHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			Integer id = Integer.valueOf(idComercial);	
			
			List<EmpresaAdquirente> empresasAdquirentes = empresaAdquirenteService.listarPorEmpresa(id);
						
			EmpresaAdquirenteNomeAdqHttp[] empresasAdquirentesNomeAdqHttp = 
					EmpresaAdquirenteHttp.gerarArrayEmpresasAdquirentesNomeAdqHttp(empresasAdquirentes);	
			
			res.setEmpresasAdquirentesNomeAdqHttp(empresasAdquirentesNomeAdqHttp);
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}		
	//---------------------------------------------------------------------------------------------------------------------------------------------------	

}
