package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.pe.recife.surfix.ecommerce.entity.Empresa;
import br.pe.recife.surfix.ecommerce.entity.http.EmpresaHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoEmpresasHttp;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.service.EmpresaService;

@Component
@Path("/empresa")
public class EntidadeEmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
		
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoEmpresasHttp listar() {
	
		RetornoEmpresasHttp res = new RetornoEmpresasHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<Empresa> empresas = empresaService.listar();
			
			EmpresaHttp[] empresasHttp = EmpresaHttp.gerarArrayEmpresasHttp(empresas);	
			
			res.setEmpresas(empresasHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}

}
