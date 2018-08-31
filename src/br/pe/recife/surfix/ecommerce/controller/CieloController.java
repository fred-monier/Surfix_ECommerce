package br.pe.recife.surfix.ecommerce.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
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
	public RetornoPaymentHttp gerarPagamentoCreditoAVista(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
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
	public RetornoSaleHttp consultarVendaCreditoAVistaPorPaymentId(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
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
	public RetornoPaymentsHttp consultarVendasPorNumPedidoVirtual(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
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
	public RetornoSaleResponseHttp cancelarPagamentoTotalCreditoAVista(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
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
	public RetornoPaymentHttp gerarPagamentoCreditoAVistaRecProg(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
			VendaCreditoRecProgHttp vendaCreditoAVistaRecProgHttp) {
		
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
					vendaCreditoAVistaRecProgHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoAVistaRecProgHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoAVistaRecProgHttp.getPedidoVirtualHttp().getDescricaoVenda(),
					vendaCreditoAVistaRecProgHttp.getRecProgHttp().getIntervalo(),
					vendaCreditoAVistaRecProgHttp.getRecProgHttp().getDataFinal());					
			
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
	public RetornoPaymentHttp gerarPagamentoCreditoAgendadoRecProg(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
			VendaCreditoRecProgHttp vendaCreditoAVistaRecProgHttp) {
		
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
					vendaCreditoAVistaRecProgHttp.getPedidoVirtualHttp().getNumPedidoVirtual(), 
					vendaCreditoAVistaRecProgHttp.getPedidoVirtualHttp().getValor(), 
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoAVistaRecProgHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoAVistaRecProgHttp.getPedidoVirtualHttp().getDescricaoVenda(),
					vendaCreditoAVistaRecProgHttp.getRecProgHttp().getDataInicial(),
					vendaCreditoAVistaRecProgHttp.getRecProgHttp().getIntervalo(),
					vendaCreditoAVistaRecProgHttp.getRecProgHttp().getDataFinal());					
			
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
	public RetornoRecurrentSaleHttp consultarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
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
	public RetornoHttp alterarPagamentoCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idComAdq") String idComAdq,
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
	public RetornoHttp alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId(@HeaderParam("idComercial") 
		String idComercial, @HeaderParam("idRecPayment") String idRecPayment,
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
	public RetornoHttp alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId(@HeaderParam("idComercial") 
		String idComercial,	@HeaderParam("idRecPayment") String idRecPayment, 
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
	public RetornoHttp alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId(@HeaderParam("idComercial") 
		String idComercial,	@HeaderParam("idRecPayment") String idRecPayment, 
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
	public RetornoHttp alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId(@HeaderParam("idComercial") 
		String idComercial,	@HeaderParam("idRecPayment") String idRecPayment, 
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
	public RetornoHttp alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId(@HeaderParam("idComercial") 
		String idComercial,	@HeaderParam("idRecPayment") String idRecPayment, 
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
	public RetornoHttp desabilitarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idComercial") 
		String idComercial,	@HeaderParam("idRecPayment") String idRecPayment, 
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
	public RetornoHttp reabilitarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idComercial") String idComercial, 
			@HeaderParam("idRecPayment") String idRecPayment, @HeaderParam("idComAdq") String idComAdq) {
		
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
	
	private EmpresaAdquirente empresaAdquirenteRequisitado(String id) throws FachadaCieloException {
						
		try {
			Integer idEmpresaAquirente = Integer.valueOf(id);		
			
			EmpresaAdquirente empresaAdquirente = fachadaDB.empresaAdquirenteConsultarPorId(idEmpresaAquirente);
			
			return empresaAdquirente;
			
		} catch (InfraException e) {
			
			throw new FachadaCieloException(e, "Erro ao tentar recuperar credenciais de acesso");
			
        } catch (Exception e) {        	     
        	
        	throw new FachadaCieloException(e, "ID Empresa Adquirente inválido");
        }				
                
	}
}
