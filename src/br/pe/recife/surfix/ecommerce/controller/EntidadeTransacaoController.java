package br.pe.recife.surfix.ecommerce.controller;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.pe.recife.surfix.ecommerce.entity.Transacao;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoTransacoesHttp;
import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.service.TransacaoService;

@Component
@Path("/transacao")
public class EntidadeTransacaoController {
	
	@Autowired
	private TransacaoService transacaoService;
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar_pais")
	@PermitAll
	public RetornoTransacoesHttp listarPais() {
	
		RetornoTransacoesHttp res = new RetornoTransacoesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<Transacao> transacoes = transacaoService.listarPais();
			
			TransacaoHttp[] transacoesHttp = TransacaoHttp.gerarArrayTransacoesHttp(transacoes);	
			
			res.setTransacoes(transacoesHttp);
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}		
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar_pais_por_pednum")
	@RolesAllowed("ADMIN")
	public RetornoTransacoesHttp listarPaisPorNumPedidoVirtual(@HeaderParam("idComAdq") String idComAdq, 
			@HeaderParam("pedNum") String pedNum) {
	
		RetornoTransacoesHttp res = new RetornoTransacoesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<Transacao> transacoes = transacaoService.
					listarPaisPorEmpAdqENumPedidoVirtual(Integer.valueOf(idComAdq), pedNum);
			
			TransacaoHttp[] transacoesHttp = TransacaoHttp.gerarArrayTransacoesHttp(transacoes);	
			
			res.setTransacoes(transacoesHttp);
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}			
	//---------------------------------------------------------------------------------------------------------------------------------------------------	

}
