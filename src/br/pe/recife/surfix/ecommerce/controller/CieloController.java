package br.pe.recife.surfix.ecommerce.controller;

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
import br.pe.recife.surfix.ecommerce.http.RetornoManutRecHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentsHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoRecurrentSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleResponseHttp;
import br.pe.recife.surfix.ecommerce.http.VendaCreditoHttp;
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
	private static final String OPERACAO_TRANSACAO_1 = "CREDITO_AVISTA";
	private static final String OPERACAO_TRANSACAO_2 = "CREDITO_AVISTA_RECORRENTE";
	private static final String OPERACAO_TRANSACAO_3 = "CREDITO_AGENDADO_RECORRENTE";	
	private static final String OPERACAO_TRANSACAO_4 = "CREDITO_AVISTA_CANCELAMENTO";
	private static final String OPERACAO_TRANSACAO_5 = "CREDITO_RECORRENTE_DESATIVACAO";
	private static final String OPERACAO_TRANSACAO_6 = "CREDITO_RECORRENTE_REATIVACAO";
			
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
			VendaCreditoHttp vendaCreditoHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
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
			
			Payment payment = fachada.gerarPagamentoCreditoAVista(this.modoProd, mecId, mecKey, 
					vendaCreditoHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoHttp.getPedidoVirtualHttp().getDescricaoVenda());					

			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(vendaCreditoHttp, empresaAdquirente, 
					payment, res, OPERACAO_TRANSACAO_1);
						
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
			
			SaleResponse saleResponse = fachada.cancelarPagamentoTotalCreditoAVista(this.modoProd, mecId, mecKey, idPayment);
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, saleResponse, idPayment, 
					res, OPERACAO_TRANSACAO_4);
						
			res.setTransacao(transacaoHttp);
			//
			
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
			VendaCreditoHttp vendaCreditoHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
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
			
			Payment payment = fachada.gerarPagamentoCreditoAVistaRecProg(this.modoProd, mecId, mecKey, 
					vendaCreditoHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoHttp.getPedidoVirtualHttp().getDescricaoVenda(),
					vendaCreditoHttp.getRecProgHttp().getIntervalo(),
					vendaCreditoHttp.getRecProgHttp().getDataFinal());					
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(vendaCreditoHttp, empresaAdquirente, 
					payment, res, OPERACAO_TRANSACAO_2);
						
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
			VendaCreditoHttp vendaCreditoHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
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
			
			Payment payment = fachada.gerarPagamentoCreditoAgendadoRecProg(this.modoProd, mecId, mecKey, 
					vendaCreditoHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoHttp.getPedidoVirtualHttp().getDescricaoVenda(),
					vendaCreditoHttp.getRecProgHttp().getDataInicial(),
					vendaCreditoHttp.getRecProgHttp().getIntervalo(),
					vendaCreditoHttp.getRecProgHttp().getDataFinal());					
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(vendaCreditoHttp, empresaAdquirente, 
					payment, res, OPERACAO_TRANSACAO_3);
						
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
			@HeaderParam("idRecPayment") String idRecPayment, VendaCreditoHttp vendaCreditoHttp) {
		
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
					vendaCreditoHttp.getPedidoVirtualHttp().getValor(),
					vendaCreditoHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoHttp.getPedidoVirtualHttp().getDescricaoVenda());											
												
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
	public RetornoManutRecHttp desabilitarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
		@HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
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
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, idRecPayment, 
					res, OPERACAO_TRANSACAO_5);
						
			res.setTransacao(transacaoHttp);
			//
															
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
	public RetornoManutRecHttp reabilitarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idRecPayment") String idRecPayment, 
			@HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
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
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, idRecPayment, 
					res, OPERACAO_TRANSACAO_6);
						
			res.setTransacao(transacaoHttp);
			//
												
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
		
	private TransacaoHttp registrarTransacao(VendaCreditoHttp vendaCreditoHttp, 
			EmpresaAdquirente empresaAdquirente, Payment payment, RetornoPaymentHttp res, 
			String operacao) {
			
		Transacao transacao = Transacao.gerarTransacao(vendaCreditoHttp, empresaAdquirente, 
				payment, operacao);
		
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
				
		return transacaoHttp;
		
	}
	
	private TransacaoHttp registrarTransacao(EmpresaAdquirente empresaAdquirente,
			SaleResponse saleResponse, String idPayment, RetornoSaleResponseHttp res, 
			String operacao) {
		
		Transacao transacao = Transacao.gerarTransacao(empresaAdquirente, saleResponse, 
				operacao, idPayment, true);
					
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}
	
	private TransacaoHttp registrarTransacao(EmpresaAdquirente empresaAdquirente,
			String idRecPayment, RetornoManutRecHttp res, String operacao) {
		
		Transacao transacao = null;
		
		if (CieloController.OPERACAO_TRANSACAO_5.equals(operacao)) {			
			transacao = Transacao.gerarTransacao(empresaAdquirente, operacao, idRecPayment, true);
			
		} else if (CieloController.OPERACAO_TRANSACAO_6.equals(operacao)) {
			transacao = Transacao.gerarTransacao(empresaAdquirente, operacao, idRecPayment, false);			
		}
							
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}	
	
	private TransacaoHttp encaminharParaFachada(Transacao transacao, RetornoHttp res) {
		
		boolean erro = false;
		InfraException exc = null;			
		try {				
			this.fachadaDB.transacaoSalvar(transacao);				
		} catch (InfraException e) {				
			erro = true;	
			exc = e;
		}			
		
		TransacaoHttp transacaoHttp = TransacaoHttp.gerarTransacaoHttp(transacao);
		
		if (erro) {
			Gson gson = new Gson();
			
			res.setResultado(res.getResultado() + " - Aviso: transação não pôde ser salva.");
			
			System.out.println("********************************");
			System.out.println("Erro ao tentar gravar transação: ");
			System.out.println(gson.toJson(transacaoHttp));
			System.out.println(exc.getExcecaoOriginal().getMessage());	
			System.out.println("********************************");		
		}
		
		return transacaoHttp;
		
	}
	
}
