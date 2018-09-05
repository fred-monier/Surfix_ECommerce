package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.pe.recife.surfix.ecommerce.entity.Empresa;
import br.pe.recife.surfix.ecommerce.entity.http.EmpresaHttp;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoEmpresasHttp;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.fachada.FachadaDB;

@Path("/empresa")
public class EntidadeEmpresaController {
	
	private FachadaDB fachadaDB = FachadaDB.getInstancia();
		
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoEmpresasHttp listar() {
	
		RetornoEmpresasHttp res = new RetornoEmpresasHttp();
		res.setResultado(RetornoEmpresasHttp.SUCESSO);
		
		try { 
		
			List<Empresa> empresas = fachadaDB.empresaListar();
			
			EmpresaHttp[] empresasHttp = gerarArrayEmpresasHttp(empresas);	
			
			res.setEmpresas(empresasHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}

	private EmpresaHttp[] gerarArrayEmpresasHttp(List<Empresa> empresas) {
		
		EmpresaHttp[] empresasHttp = new EmpresaHttp[empresas.size()];			
		
		for (int i=0; i < empresas.size(); i++) {
			
			Empresa empresa = empresas.get(i);
			
			EmpresaHttp empresaHttp = new EmpresaHttp();
			empresaHttp.setId(empresa.getId());
			empresaHttp.setNome(empresa.getNome());
			empresaHttp.setCnpj(empresa.getCnpj());
			empresaHttp.setUsuario(empresa.getUsuario());
			empresaHttp.setSenha(empresa.getSenha());
			
			empresasHttp[i] = empresaHttp;
		}
		
		return empresasHttp;
	}

}
