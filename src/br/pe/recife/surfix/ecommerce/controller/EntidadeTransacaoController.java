package br.pe.recife.surfix.ecommerce.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import javax.annotation.security.DenyAll;
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
import br.pe.recife.surfix.ecommerce.exception.NegocioException;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.service.TransacaoService;

@Component
@Path("/transacao")
public class EntidadeTransacaoController {
	
	private static final String ZERO = "0";
	
	@Autowired
	private TransacaoService transacaoService;
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar_pais")
	@DenyAll
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
	@Path("/listar_pais_por_empresa_pednum")
	@RolesAllowed("ADMIN")
	public RetornoTransacoesHttp listarPaisPorNumPedidoVirtual(@HeaderParam("idComercial") String idComercial, 
			@HeaderParam("pedNum") String pedNum) {
	
		RetornoTransacoesHttp res = new RetornoTransacoesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
		
			List<Transacao> transacoes = transacaoService.
					listarPaisPorEmpresaENumPedidoVirtual(Integer.valueOf(idComercial), pedNum);
			
			TransacaoHttp[] transacoesHttp = TransacaoHttp.gerarArrayTransacoesHttp(transacoes);	
			
			res.setTransacoes(transacoesHttp);
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}			
	//---------------------------------------------------------------------------------------------------------------------------------------------------	

	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/listar_pais_por_empadq_data_operacao_status")
	@RolesAllowed("ADMIN")
	public RetornoTransacoesHttp listarPaisPorEmpAdqDataOperacaoStatus(@HeaderParam("idComercial") String 
			idComercial, @HeaderParam("idComAdq") String idComAdq, @HeaderParam("dataInicio") 
			String dataInicio, @HeaderParam("dataFim") String dataFim, @HeaderParam("operacao") 
			String operacao, @HeaderParam("autorizada") String autorizada, @HeaderParam("cancelada") 
			String cancelada, @HeaderParam("ativada") String ativada, 
			@HeaderParam("desativada") String desativada) {
	
		RetornoTransacoesHttp res = new RetornoTransacoesHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try { 
			
			checarValorOperacao(operacao);
			
			LocalDateTime dataInicioLDT = this.gerarLocalDateTime(dataInicio, true);
			LocalDateTime dataFimLDT = this.gerarLocalDateTime(dataFim, false);
									
			Integer idEmpresa = 0;
			Integer idEmpresaAquirente = 0;
			
			if (idComAdq != null  && !idComAdq.equals("")) {
				idEmpresaAquirente = Integer.valueOf(idComAdq);
			} else {
				idEmpresa = Integer.valueOf(idComercial);
			}
						
			boolean aut = gerarValorBoolean(autorizada);
			boolean can = gerarValorBoolean(cancelada);
			boolean ati = gerarValorBoolean(ativada);
			boolean des = gerarValorBoolean(desativada);								
			
			List<Transacao> transacoes = transacaoService.
					listarPaisPorEmpAdqEDataHoraEOperacao(idEmpresa, idEmpresaAquirente, dataInicioLDT, 
							dataFimLDT, operacao);
						
			TransacaoHttp[] transacoesHttp;
			
			if (!(aut && can && ati && des)) {
				
				List<TransacaoHttp> listaTransacoesHttp = TransacaoHttp.gerarListaTransacoesHttp(transacoes, 
						aut, can, ati, des);
				transacoesHttp = listaTransacoesHttp.toArray(new TransacaoHttp[listaTransacoesHttp.size()]);
				
			} else {		
				
				transacoesHttp = TransacaoHttp.gerarArrayTransacoesHttp(transacoes);
			}
			
			res.setTransacoes(transacoesHttp);
			
		} catch (NegocioException e) {	
			
			res.setResultado(e.getMensagem());
			
		} catch (Exception e) {
			
			res.setResultado(e.getMessage());	
		}
		
		return res;
	}			
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	
	private LocalDateTime gerarLocalDateTime(String data, boolean inicioDia) throws NegocioException {
		
		LocalDateTime res = null;
		
		boolean erro = true;
		
		if (data != null && data.length() == 10) {
			
			String ano = data.substring(0, 4);
			String mes = data.substring(5, 7);
			String dia = data.substring(8);
			
			int anoInt = 0;
			Month mesEnum = null;
			int diaInt = 0;
			
			try {
				anoInt = Integer.parseInt(ano);
				diaInt = Integer.parseInt(dia);
			} catch (Exception e) {				
			}
						
			if (mes.equals("01")) {
				mesEnum = Month.JANUARY;
			} else if (mes.equals("02")) {
				mesEnum = Month.FEBRUARY;
			} else if (mes.equals("03")) {
				mesEnum = Month.MARCH;
			} else if (mes.equals("04")) {
				mesEnum = Month.APRIL;
			} else if (mes.equals("05")) {
				mesEnum = Month.MAY;
			} else if (mes.equals("06")) {
				mesEnum = Month.JUNE;
			} else if (mes.equals("07")) {
				mesEnum = Month.JULY;
			} else if (mes.equals("08")) {
				mesEnum = Month.AUGUST;
			} else if (mes.equals("09")) {
				mesEnum = Month.SEPTEMBER;
			} else if (mes.equals("10")) {
				mesEnum = Month.OCTOBER;
			} else if (mes.equals("11")) {
				mesEnum = Month.NOVEMBER;
			} else if (mes.equals("12")) {
				mesEnum = Month.DECEMBER;
			}
			
			try {
				
				if (anoInt != 0 && mesEnum != null && diaInt !=0) {				
					if (inicioDia) {
						res = LocalDateTime.of(anoInt, mesEnum, diaInt, 0, 0);
					} else {
						res = LocalDateTime.of(anoInt, mesEnum, diaInt, 23, 59);
					}
					erro = false;
				}	
				
			} catch (Exception e) {				
			}
		}
		
		if (erro) {
			throw new NegocioException("Parâmetro dataInicio/dataFim deve ser uma data " + 
					"válida no formato YYYY_MM_DD");
		} else {
			return res;
		}
	}
	
	private boolean gerarValorBoolean(String valor) {
		
		boolean res = true;
		
		if (valor != null && valor.equals(ZERO)) {
			res = false;
		}
		
		return res;		
	}
	
	private void checarValorOperacao(String operacao) throws NegocioException  {
	
		if (operacao != null && !operacao.equals("")) {
			
			if (!(operacao.equals(Transacao.OPERACAO_TRANSACAO_1) ||
					operacao.equals(Transacao.OPERACAO_TRANSACAO_2) ||
					operacao.equals(Transacao.OPERACAO_TRANSACAO_3))) {
				
				throw new NegocioException("Parâmetro operação inválido. Escolha entre " 
						+ Transacao.OPERACAO_TRANSACAO_1 + ", " 
						+ Transacao.OPERACAO_TRANSACAO_2 + " ou "
						+ Transacao.OPERACAO_TRANSACAO_3);
			}
		}
	}
	
}
