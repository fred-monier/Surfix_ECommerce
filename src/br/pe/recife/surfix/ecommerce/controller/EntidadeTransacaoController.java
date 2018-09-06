package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.pe.recife.surfix.ecommerce.entity.Transacao;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoTransacoesHttp;
import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.fachada.FachadaDB;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

@Path("/transacao")
public class EntidadeTransacaoController {
	
	private FachadaDB fachadaDB = FachadaDB.getInstancia();
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoTransacoesHttp listar() {
	
		RetornoTransacoesHttp res = new RetornoTransacoesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<Transacao> transacoes = fachadaDB.transacaoListar();
			
			TransacaoHttp[] transacoesHttp = TransacaoHttp.gerarArrayTransacoesHttp(transacoes);	
			
			res.setTransacoes(transacoesHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}		

}
