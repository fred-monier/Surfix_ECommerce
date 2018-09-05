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

@Path("/transacao")
public class EntidadeTransacaoController {
	
	private FachadaDB fachadaDB = FachadaDB.getInstancia();
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar")
	@PermitAll
	public RetornoTransacoesHttp listar() {
	
		RetornoTransacoesHttp res = new RetornoTransacoesHttp();
		res.setResultado(RetornoTransacoesHttp.SUCESSO);
		
		try { 
		
			List<Transacao> transacoes = fachadaDB.transacaoListar();
			
			TransacaoHttp[] transacoesHttp = gerarArrayTransacoesHttp(transacoes);	
			
			res.setTransacoes(transacoesHttp);
			
		} catch	(InfraException e) {
		
			res.setResultado(e.getMensagem());	
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}
	
	private TransacaoHttp[] gerarArrayTransacoesHttp(List<Transacao> transacoes) {
		
		TransacaoHttp[] transacoesHttp = new TransacaoHttp[transacoes.size()];			
		
		for (int i=0; i < transacoes.size(); i++) {
			
			Transacao transacao = transacoes.get(i);
			
			TransacaoHttp transacaoHttp = new TransacaoHttp();
			transacaoHttp.setId(transacao.getId());
			transacaoHttp.setIdEmpresaAdquirente(transacao.getEmpresaAdquirente().getId());
			transacaoHttp.setOperacao(transacao.getOperacao());
			transacaoHttp.setDataHora(transacao.getDataHora());
			transacaoHttp.setNumPedidoVirtual(transacao.getNumPedidoVirtual());
			transacaoHttp.setProvider(transacao.getProvider());
			transacaoHttp.setAmount(transacao.getAmount());
			transacaoHttp.setCreditCardBrand(transacao.getCreditCardBrand());
			transacaoHttp.setCreditCardNumber(transacao.getCreditCardNumber());
			transacaoHttp.setStatus(transacao.getStatus());
			transacaoHttp.setPaymentId(transacao.getPaymentId());
			transacaoHttp.setPaymentAuthCode(transacao.getPaymentAuthCode());
			transacaoHttp.setPaymentProofOfSale(transacao.getPaymentProofOfSale());
			transacaoHttp.setPaymentTid(transacao.getPaymentTid());
			transacaoHttp.setPaymentReceivedDate(transacao.getPaymentReceivedDate());
			transacaoHttp.setPaymentReturnCode(transacao.getPaymentReturnCode());
			transacaoHttp.setPaymentReturnMessage(transacao.getPaymentReturnMessage());
			transacaoHttp.setRecPaymentId(transacao.getRecPaymentId());
			transacaoHttp.setRecPaymentAuthNow(transacao.getRecPaymentAuthNow());
			transacaoHttp.setRecPaymentStartDate(transacao.getRecPaymentStartDate());
			transacaoHttp.setRecPaymentEndDate(transacao.getRecPaymentEndDate());
			transacaoHttp.setRecPaymentNextRecurrency(transacao.getRecPaymentNextRecurrency());
			transacaoHttp.setRecPaymentReasonCode(transacao.getRecPaymentReasonCode());
			transacaoHttp.setRecPaymentReasonMessage(transacao.getRecPaymentReasonMessage());
			
			
			transacoesHttp[i] = transacaoHttp;
		}
		
		return transacoesHttp;
	}

}
