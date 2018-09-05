package br.pe.recife.surfix.ecommerce.controller;

import java.time.LocalDateTime;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.entity.Transacao;
import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.fachada.FachadaDB;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentsHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoRecurrentSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleResponseHttp;
import br.pe.recife.surfix.ecommerce.http.VendaCreditoAVistaHttp;
import br.pe.recife.surfix.ecommerce.http.VendaCreditoRecProgHttp;
import br.pe.recife.surfix.ecommerce.util.Configuracao;
import cielo.environment.util.FachadaCielo;
import cielo.environment.util.FachadaCieloException;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.RecurrentSale;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.SaleResponse;

@Path("/cielo")
public class CieloController {
	
	private static final String TESTE_PROPERTY = "prod";
	private static final String TESTE_VALUE = "true";
	private static final String OPERACAO_POST_1 = "CREDITO_AVISTA";
	private static final String OPERACAO_POST_2 = "CREDITO_AVISTA_RECORRENTE";
	private static final String OPERACAO_POST_3 = "CREDITO_AGENDADO_RECORRENTE";
		
	private final Configuracao configuracao;	
	private final boolean modoProd;
			
	private FachadaDB fachadaDB = FachadaDB.getInstancia();
	private FachadaCielo fachada = FachadaCielo.getInstancia();
	
	public CieloController() {
	
		this.configuracao = Configuracao.getInstancia();
		
		String prod = this.configuracao.getProperty(CieloController.TESTE_PROPERTY);
		if (prod != null && prod.equals(CieloController.TESTE_VALUE)) {
			this.modoProd = true;
		} else {
			this.modoProd = false;
		}				
	}
		
	//PARTE I - Compra não recorrente
	
	/**
	 * 
	 * Chama [1-gerarPagamentoCreditoAVista]
	 * 
	 * */		
	@POST	
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/gerar_pag_cred_a_vista")
	@RolesAllowed("ADMIN")	
	public RetornoPaymentHttp gerarPagamentoCreditoAVista(@HeaderParam("idComAdq") String idComAdq,
			VendaCreditoAVistaHttp vendaCreditoAVistaHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);
			
			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			Payment payment = fachada.gerarPagamentoCreditoAVista(this.modoProd, mecId, mecKey, 
					vendaCreditoAVistaHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoAVistaHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoAVistaHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoAVistaHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoAVistaHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoAVistaHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoAVistaHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoAVistaHttp.getPedidoVirtualHttp().getDescricaoVenda());					

			//
			Transacao transacao = this.salvarTransacao(vendaCreditoAVistaHttp, empresaAdquirente, payment);	
			
			if (transacao.getId() == null) {
				res.setResultado(res.getResultado() + " - Aviso: transação não pôde ser salva.");
			}
			
			TransacaoHttp transacaoHttp = gerarTransacaoHttp(transacao);
			
			res.setTransacao(transacaoHttp);
			//	
			
			res.setPayment(payment);
			
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;				
	}	
	
	/**
	 * 
	 * Chama [2-consultarVendaCreditoAVistaPorPaymentId]
	 * 
	 * */	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/consultar_vend_cred_a_vista_por_payid")
	@RolesAllowed("ADMIN")
	public RetornoSaleHttp consultarVendaCreditoAVistaPorPaymentId(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idPayment") String idPayment) {
 
		RetornoSaleHttp res = new RetornoSaleHttp();
		res.setResultado(RetornoSaleHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			Sale sale = fachada.consultarVendaCreditoAVistaPorPaymentId(this.modoProd, mecId, mecKey, idPayment);					
			
			res.setSale(sale);
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
	
	/**
	 * 
	 * Chama [3-consultarVendasPorNumPedidoVirtual]
	 * 
	 * */
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/consultar_vendas_por_pednum")	
	@RolesAllowed("ADMIN")
	public RetornoPaymentsHttp consultarVendasPorNumPedidoVirtual(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("pedNum") String pedNum) {
		
		RetornoPaymentsHttp res = new RetornoPaymentsHttp();
		res.setResultado(RetornoPaymentsHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			Payment[] payments = fachada.consultarVendasPorNumPedidoVirtual(this.modoProd, mecId, mecKey, pedNum);					
			
			res.setPayments(payments);
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
	
	/**
	 * 
	 * Chama [4-cancelarPagamentoTotalCreditoAVista]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/cancelar_pag_total_cred_a_vista")
	@RolesAllowed("ADMIN")
	public RetornoSaleResponseHttp cancelarPagamentoTotalCreditoAVista(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idPayment") String idPayment) {
		
		RetornoSaleResponseHttp res = new RetornoSaleResponseHttp();
		res.setResultado(RetornoSaleResponseHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			SaleResponse saleResponse = fachada.cancelarPagamentoTotalCreditoAVista(this.modoProd, mecId, mecKey, idPayment);					
			
			res.setSaleResponse(saleResponse);
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	
	//PARTE II - Compra recorrente
	
	/**
	 * 
	 * Chama [5-gerarPagamentoCreditoAVistaRecProg]
	 * 
	 * */
	@POST	
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/gerar_pag_cred_a_vista_rec_prog")
	@RolesAllowed("ADMIN")
	public RetornoPaymentHttp gerarPagamentoCreditoAVistaRecProg(@HeaderParam("idComAdq") String idComAdq,
			VendaCreditoRecProgHttp vendaCreditoRecProgHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			Payment payment = fachada.gerarPagamentoCreditoAVistaRecProg(this.modoProd, mecId, mecKey, 
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getDescricaoVenda(),
					vendaCreditoRecProgHttp.getRecProgHttp().getIntervalo(),
					vendaCreditoRecProgHttp.getRecProgHttp().getDataFinal());					
			
			//
			Transacao transacao = this.salvarTransacao(vendaCreditoRecProgHttp, empresaAdquirente, payment, 2);
			
			if (transacao.getId() == null) {
				res.setResultado(res.getResultado() + " - Aviso: transação não pôde ser salva.");
			}
			
			TransacaoHttp transacaoHttp = gerarTransacaoHttp(transacao);
			
			res.setTransacao(transacaoHttp);
			//
			
			res.setPayment(payment);
									
			
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;				
	}	

	/**
	 * 
	 * Chama [6-gerarPagamentoCreditoAgendadoRecProg]
	 * 
	 * */
	@POST	
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/gerar_pag_cred_agend_rec_prog")
	@RolesAllowed("ADMIN")
	public RetornoPaymentHttp gerarPagamentoCreditoAgendadoRecProg(@HeaderParam("idComAdq") String idComAdq,
			VendaCreditoRecProgHttp vendaCreditoRecProgHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			Payment payment = fachada.gerarPagamentoCreditoAgendadoRecProg(this.modoProd, mecId, mecKey, 
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getDescricaoVenda(),
					vendaCreditoRecProgHttp.getRecProgHttp().getDataInicial(),
					vendaCreditoRecProgHttp.getRecProgHttp().getIntervalo(),
					vendaCreditoRecProgHttp.getRecProgHttp().getDataFinal());					
			
			//
			Transacao transacao = this.salvarTransacao(vendaCreditoRecProgHttp, empresaAdquirente, payment, 3);	
			
			if (transacao.getId() == null) {
				res.setResultado(res.getResultado() + " - Aviso: transação não pôde ser salva.");
			}
			
			TransacaoHttp transacaoHttp = gerarTransacaoHttp(transacao);
			
			res.setTransacao(transacaoHttp);
			//
			
			res.setPayment(payment);
									
			
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;				
	}
		
	/**
	 * 
	 * Chama [7-consultarVendaCreditoRecProgPorRecurrentPaymentId]
	 * 
	 * */
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/consultar_vend_cred_rec_prog_por_recpayid")
	@RolesAllowed("ADMIN")
	public RetornoRecurrentSaleHttp consultarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idRecPayment") String idRecPayment) {
		
		RetornoRecurrentSaleHttp res = new RetornoRecurrentSaleHttp();
		res.setResultado(RetornoRecurrentSaleHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			RecurrentSale recSale = fachada.consultarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, 
					idRecPayment);					
			
			res.setRecurrentSale(recSale);
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
	
	/**
	 * 
	 * Chama [8-alterarPagamentoCreditoRecProgPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_pag_cred_rec_prog_por_recpayid")
	@RolesAllowed("ADMIN")
	public RetornoHttp alterarPagamentoCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idRecPayment") String idRecPayment, VendaCreditoRecProgHttp vendaCreditoRecProgHttp) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.alterarPagamentoCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, 
					idRecPayment,
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getValor(),
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoRecProgHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoRecProgHttp.getPedidoVirtualHttp().getDescricaoVenda());											
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	
	/**
	 * 
	 * Chama [9-alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_final_por_recpayid")	
	@RolesAllowed("ADMIN")
	public RetornoHttp alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment,
		@HeaderParam("dataFinal") String dataFinal, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId(this.modoProd, mecId, mecKey, 
					idRecPayment, dataFinal);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
		
	/**
	 * 
	 * Chama [10-alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_dia_rec_por_recpayid")	
	@RolesAllowed("ADMIN")
	public RetornoHttp alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
		@HeaderParam("diaRec") int diaRec, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId(this.modoProd, mecId, mecKey, 
					idRecPayment, diaRec);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
		
	/**
	 * 
	 * Chama [11-alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_valor_rec_por_recpayid")	
	@RolesAllowed("ADMIN")
	public RetornoHttp alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
		@HeaderParam("valorRec") int valorRec, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId(this.modoProd, mecId, mecKey, 
					idRecPayment, valorRec);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
		
	/**
	 * 
	 * Chama [12-alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_prox_rec_por_recpayid")	
	@RolesAllowed("ADMIN")
	public RetornoHttp alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
		@HeaderParam("dataProxRec") String dataProxRec, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
						
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId(this.modoProd, mecId, mecKey, 
					idRecPayment, dataProxRec);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
	
	/**
	 * 
	 * Chama [13-alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_intervalo_por_recpayid")	
	@RolesAllowed("ADMIN")
	public RetornoHttp alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
		@HeaderParam("intervalo") String intervalo, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId(this.modoProd, mecId, mecKey, 
					idRecPayment, intervalo);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
	
	/**
	 * 
	 * Chama [14-desabilitarVendaCreditoRecProgPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/desabilitar_venda_cred_rec_prog_por_recpayid")	
	@RolesAllowed("ADMIN")
	public RetornoHttp desabilitarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
		@HeaderParam("idComAdq") String idComAdq) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.desabilitarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, idRecPayment);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
	
	/**
	 * 
	 * Chama [15-reabilitarVendaCreditoRecProgPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/reabilitar_venda_cred_rec_prog_por_recpayid")	
	@RolesAllowed("ADMIN")
	public RetornoHttp reabilitarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
			@HeaderParam("idComAdq") String idComAdq) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			EmpresaAdquirente empresaAdquirente = this.empresaAdquirenteRequisitado(idComAdq);

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.reabilitarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, idRecPayment);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
	
	//***************************************
	
	private EmpresaAdquirente empresaAdquirenteRequisitado(String id) throws FachadaCieloException {
				
		EmpresaAdquirente empresaAdquirente = null;
		
		try {
			Integer idEmpresaAquirente = Integer.valueOf(id);		
			
			empresaAdquirente = fachadaDB.empresaAdquirenteConsultarPorId(idEmpresaAquirente);
									
		} catch (InfraException e) {
			
			throw new FachadaCieloException(e, "Erro ao tentar recuperar credenciais de acesso");
			
        } catch (Exception e) {        	     
        	
        	throw new FachadaCieloException(e, "ID Empresa Adquirente inválido");
        }				
                
		if (empresaAdquirente != null) {			
			return empresaAdquirente;
		} else {
			throw new FachadaCieloException(null, "Erro ao tentar recuperar credenciais de acesso");
		}
	}
	
	private Transacao salvarTransacao(VendaCreditoAVistaHttp vendaCreditoAVistaHttp, 
			EmpresaAdquirente empresaAdquirente, Payment payment) {
				
		Transacao res = new Transacao();
		
		Gson gson = new Gson();
		
		res.setEmpresaAdquirente(empresaAdquirente);
		res.setjSonIn(gson.toJson(vendaCreditoAVistaHttp));
		res.setjSonOut(gson.toJson(payment));
		res.setOperacao(CieloController.OPERACAO_POST_1);
		res.setDataHora(LocalDateTime.now());
		res.setProvider(payment.getProvider().name());
		res.setAmount(payment.getAmount());
		res.setCreditCardBrand(payment.getCreditCard().getBrand());
		res.setCreditCardNumber(payment.getCreditCard().getCardNumber());
		res.setStatus(payment.getStatus() + "");
		res.setPaymentId(payment.getPaymentId());
		res.setPaymentAuthCode(payment.getAuthorizationCode());
		res.setPaymentProofOfSale(payment.getProofOfSale());
		res.setPaymentTid(payment.getTid());
		res.setPaymentReceivedDate(payment.getReceivedDate());
		res.setPaymentReturnCode(payment.getReturnCode());
		res.setPaymentReturnMessage(payment.getReturnMessage());
		
		res.setRecPaymentId(null);
		res.setRecPaymentAuthNow(null);
		res.setRecPaymentStartDate(null);
		res.setRecPaymentEndDate(null);
		res.setRecPaymentNextRecurrency(null);
		res.setRecPaymentReasonCode(null);
		res.setRecPaymentReasonMessage(null);		
				
		try {
			
			this.fachadaDB.transacaoSalvar(res);
							
		} catch (InfraException e) {
			
			System.out.println("Erro ao tentar gravar transação: ");
			System.out.println(gson.toJson(this.gerarTransacaoHttp(res)));
			System.out.println(e.getExcecaoOriginal().getMessage());	
			System.out.println("********************************");						
			
		}
						
		return res;
	}
	
	private Transacao salvarTransacao(VendaCreditoRecProgHttp vendaCreditoRecProgHttp, 
			EmpresaAdquirente empresaAdquirente, Payment payment, int operacao) {
		
		Transacao res = new Transacao();
		
		Gson gson = new Gson();
		
		res.setEmpresaAdquirente(empresaAdquirente);
		res.setjSonIn(gson.toJson(vendaCreditoRecProgHttp));
		res.setjSonOut(gson.toJson(payment));				
		res.setOperacao((operacao == 2) ? CieloController.OPERACAO_POST_2 : CieloController.OPERACAO_POST_3);	
		res.setDataHora(LocalDateTime.now());
		res.setProvider(payment.getProvider().name());
		res.setAmount(payment.getAmount());
		res.setCreditCardBrand(payment.getCreditCard().getBrand());
		res.setCreditCardNumber(payment.getCreditCard().getCardNumber());
		res.setStatus(payment.getStatus() + "");
		res.setPaymentId(payment.getPaymentId());
		res.setPaymentAuthCode(payment.getAuthorizationCode());
		res.setPaymentProofOfSale(payment.getProofOfSale());
		res.setPaymentTid(payment.getTid());
		res.setPaymentReceivedDate(payment.getReceivedDate());
		res.setPaymentReturnCode(payment.getReturnCode());
		res.setPaymentReturnMessage(payment.getReturnMessage());
		
		res.setRecPaymentId(payment.getRecurrentPayment().getRecurrentPaymentId());
		res.setRecPaymentAuthNow(payment.getRecurrentPayment().isAuthorizeNow());
		res.setRecPaymentStartDate(payment.getRecurrentPayment().getStartDate());
		res.setRecPaymentEndDate(payment.getRecurrentPayment().getEndDate());
		res.setRecPaymentNextRecurrency(payment.getRecurrentPayment().getNextRecurrency());
		res.setRecPaymentReasonCode(payment.getRecurrentPayment().getReasonCode() + "");
		res.setRecPaymentReasonMessage(payment.getRecurrentPayment().getReasonMessage());
				
		try {
			
			this.fachadaDB.transacaoSalvar(res);
							
		} catch (InfraException e) {
			
			System.out.println("********************************");
			System.out.println("Erro ao tentar gravar transação: ");
			System.out.println(gson.toJson(this.gerarTransacaoHttp(res)));
			System.out.println(e.getExcecaoOriginal().getMessage());	
			System.out.println("********************************");
			
		}
						
		return res;
	}
	
	private TransacaoHttp gerarTransacaoHttp(Transacao transacao) {
		
		TransacaoHttp transacaoHttp = new TransacaoHttp();
		transacaoHttp.setId(transacao.getId());
		transacaoHttp.setIdEmpresaAdquirente(transacao.getEmpresaAdquirente().getId());
		transacaoHttp.setOperacao(transacao.getOperacao());
		transacaoHttp.setDataHora(transacao.getDataHora());
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
		
		return transacaoHttp;
	}	
}
