package br.pe.recife.surfix.ecommerce.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentsHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoRecurrentSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleResponseHttp;
import br.pe.recife.surfix.ecommerce.http.VendaCreditoAVistaHttp;
import br.pe.recife.surfix.ecommerce.http.VendaCreditoRecProgHttp;
import cielo.environment.util.FachadaCielo;
import cielo.environment.util.FachadaCieloException;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.RecurrentSale;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.SaleResponse;

@Path("/cielo")
public class CieloController {
			
	private FachadaCielo fachada = FachadaCielo.getInstancia();
	
	
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
			VendaCreditoAVistaHttp vendaCreditoAVistaHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			Payment payment = fachada.gerarPagamentoCreditoAVista(idComercial, 
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
			@HeaderParam("idPayment") String idPayment) {
 
		RetornoSaleHttp res = new RetornoSaleHttp();
		res.setResultado(RetornoSaleHttp.SUCESSO);
		
		try {
			
			Sale sale = fachada.consultarVendaCreditoAVistaPorPaymentId(idComercial, idPayment);					
			
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
			@HeaderParam("pedNum") String pedNum) {
		
		RetornoPaymentsHttp res = new RetornoPaymentsHttp();
		res.setResultado(RetornoPaymentsHttp.SUCESSO);
		
		try {
			
			Payment[] payments = fachada.consultarVendasPorNumPedidoVirtual(idComercial, pedNum);					
			
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
			@HeaderParam("idPayment") String idPayment) {
		
		RetornoSaleResponseHttp res = new RetornoSaleResponseHttp();
		res.setResultado(RetornoSaleResponseHttp.SUCESSO);
		
		try {
			
			SaleResponse saleResponse = fachada.cancelarPagamentoTotalCreditoAVista(idComercial, idPayment);					
			
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
			VendaCreditoRecProgHttp vendaCreditoAVistaRecProgHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			Payment payment = fachada.gerarPagamentoCreditoAVistaRecProg(idComercial, 
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
			VendaCreditoRecProgHttp vendaCreditoAVistaRecProgHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			Payment payment = fachada.gerarPagamentoCreditoAgendadoRecProg(idComercial, 
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
			@HeaderParam("idRecPayment") String idRecPayment) {
		
		RetornoRecurrentSaleHttp res = new RetornoRecurrentSaleHttp();
		res.setResultado(RetornoRecurrentSaleHttp.SUCESSO);
		
		try {
			
			RecurrentSale recSale = fachada.consultarVendaCreditoRecProgPorRecurrentPaymentId(idComercial, idRecPayment);					
			
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
			@HeaderParam("idRecPayment") String idRecPayment, VendaCreditoRecProgHttp vendaCreditoRecProgHttp) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarPagamentoCreditoRecProgPorRecurrentPaymentId(idComercial, idRecPayment,
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
		@HeaderParam("dataFinal") String dataFinal) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId(idComercial, idRecPayment, dataFinal);									
												
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
		@HeaderParam("diaRec") int diaRec) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId(idComercial, idRecPayment, diaRec);									
												
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
		@HeaderParam("valorRec") int valorRec) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId(idComercial, idRecPayment, valorRec);									
												
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
		String idComercial,	@HeaderParam("idRecPayment") String idRecPayment, @HeaderParam("dataProxRec") String dataProxRec) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId(idComercial, idRecPayment, dataProxRec);									
												
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
		@HeaderParam("intervalo") String intervalo) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId(idComercial, idRecPayment, intervalo);									
												
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
		String idComercial,	@HeaderParam("idRecPayment") String idRecPayment) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.desabilitarVendaCreditoRecProgPorRecurrentPaymentId(idComercial, idRecPayment);									
												
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
			@HeaderParam("idRecPayment") String idRecPayment) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.reabilitarVendaCreditoRecProgPorRecurrentPaymentId(idComercial, idRecPayment);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
}
