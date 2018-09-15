package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.entity.http.EmpresaAdquirenteHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoEmpresasAdquirentesHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.service.EmpresaAdquirenteService;

@Component
@Path("/empresaAdquirente")
public class EntidadeEmpresaAdquirenteController {
	
	@Autowired
	private EmpresaAdquirenteService empresaAdquirenteService;
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
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
	
	//TODO - Devolver no formato Id da EmpAdq e Nome Adquirente e validar senha de acesso para Empresa (nesse caso não haverá EmpAdquirente!!!)
	//TODO - Atualizar Postman entidade (1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listarPorEmpresaId")
	@PermitAll
	public RetornoEmpresasAdquirentesHttp listarPorEmpresaId(@HeaderParam("idEmpresa") String idEmpresa) {
	
		RetornoEmpresasAdquirentesHttp res = new RetornoEmpresasAdquirentesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			Integer id = Integer.valueOf(idEmpresa);	
			
			List<EmpresaAdquirente> empresasAdquirentes = empresaAdquirenteService.listarPorEmpresa(id);
			
			EmpresaAdquirenteHttp[] empresasAdquirentesHttp = 
					EmpresaAdquirenteHttp.gerarArrayEmpresasAdquirentesHttp(empresasAdquirentes);	
			
			res.setEmpresasAdquirentes(empresasAdquirentesHttp);
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}		

}
