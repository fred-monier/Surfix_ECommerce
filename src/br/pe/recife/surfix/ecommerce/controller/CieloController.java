package br.pe.recife.surfix.ecommerce.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public RetornoPaymentHttp gerarPagamentoCreditoAVista(VendaCreditoAVistaHttp vendaCreditoAVistaHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			Payment payment = fachada.gerarPagamentoCreditoAVista(vendaCreditoAVistaHttp.getIdComercial(), 
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
	@Path("/consultar_vend_cred_a_vista_por_payid/{idComercial}/{payId}")
	public RetornoSaleHttp consultarVendaCreditoAVistaPorPaymentId(@PathParam("idComercial") String idComercial,
			@PathParam("payId") String payId) {
 
		RetornoSaleHttp res = new RetornoSaleHttp();
		res.setResultado(RetornoSaleHttp.SUCESSO);
		
		try {
			
			Sale sale = fachada.consultarVendaCreditoAVistaPorPaymentId(idComercial, payId);					
			
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
	@Path("/consultar_vendas_por_pednum/{idComercial}/{pedNum}")
	public RetornoPaymentsHttp consultarVendasPorNumPedidoVirtual(@PathParam("idComercial") String idComercial,
			@PathParam("pedNum") String pedNum) {
		
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
	@Path("/cancelar_pag_total_cred_a_vista/{idComercial}/{payId}")
	public RetornoSaleResponseHttp cancelarPagamentoTotalCreditoAVista(@PathParam("idComercial") String idComercial, 
			@PathParam("payId") String payId) {
		
		RetornoSaleResponseHttp res = new RetornoSaleResponseHttp();
		res.setResultado(RetornoSaleResponseHttp.SUCESSO);
		
		try {
			
			SaleResponse saleResponse = fachada.cancelarPagamentoTotalCreditoAVista(idComercial, payId);					
			
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
	public RetornoPaymentHttp gerarPagamentoCreditoAVistaRecProg(VendaCreditoRecProgHttp vendaCreditoAVistaRecProgHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			Payment payment = fachada.gerarPagamentoCreditoAVistaRecProg(vendaCreditoAVistaRecProgHttp.getIdComercial(), 
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
	public RetornoPaymentHttp gerarPagamentoCreditoAgendadoRecProg(VendaCreditoRecProgHttp vendaCreditoAVistaRecProgHttp) {
		
		RetornoPaymentHttp res = new RetornoPaymentHttp();
		res.setResultado(RetornoPaymentHttp.SUCESSO);
		
		try {
			
			Payment payment = fachada.gerarPagamentoCreditoAgendadoRecProg(vendaCreditoAVistaRecProgHttp.getIdComercial(), 
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
	@Path("/consultar_vend_cred_rec_prog_por_recpayid/{idComercial}/{recPayId}")
	public RetornoRecurrentSaleHttp consultarVendaCreditoRecProgPorRecurrentPaymentId(@PathParam("idComercial") String idComercial,
			@PathParam("recPayId") String recPayId) {
		
		RetornoRecurrentSaleHttp res = new RetornoRecurrentSaleHttp();
		res.setResultado(RetornoRecurrentSaleHttp.SUCESSO);
		
		try {
			
			RecurrentSale recSale = fachada.consultarVendaCreditoRecProgPorRecurrentPaymentId(idComercial, recPayId);					
			
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
	@Path("/alterar_pag_cred_rec_prog_por_recpayid/{recPayId}")
	public RetornoHttp alterarPagamentoCreditoRecProgPorRecurrentPaymentId(@PathParam("recPayId") String recPayId,
			VendaCreditoRecProgHttp vendaCreditoRecProgHttp) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarPagamentoCreditoRecProgPorRecurrentPaymentId(vendaCreditoRecProgHttp.getIdComercial(),
					recPayId,
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
	@Path("/alterar_venda_cred_rec_prog_data_final_por_recpayid/{idComercial}/{recPayId}/{dataFinal}")	
	public RetornoHttp alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId(@PathParam("idComercial") 
		String idComercial, @PathParam("recPayId") String recPayId, @PathParam("dataFinal") String dataFinal) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId(idComercial, recPayId, dataFinal);									
												
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
	@Path("/alterar_venda_cred_rec_prog_dia_rec_por_recpayid/{idComercial}/{recPayId}/{diaRec}")	
	public RetornoHttp alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId(@PathParam("idComercial") String idComercial, 
			@PathParam("recPayId") String recPayId, @PathParam("diaRec") int diaRec) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId(idComercial, recPayId, diaRec);									
												
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
	@Path("/alterar_venda_cred_rec_prog_valor_rec_por_recpayid/{idComercial}/{recPayId}/{valorRec}")	
	public RetornoHttp alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId(@PathParam("idComercial") String idComercial,
			@PathParam("recPayId") String recPayId, @PathParam("valorRec") int valorRec) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId(idComercial, recPayId, valorRec);									
												
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
	@Path("/alterar_venda_cred_rec_prog_data_prox_rec_por_recpayid/{idComercial}/{recPayId}/{dataProxRec}")	
	public RetornoHttp alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId(@PathParam("idComercial") String idComercial,
			@PathParam("recPayId") String recPayId, @PathParam("dataProxRec") String dataProxRec) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId(idComercial, recPayId, dataProxRec);									
												
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
	@Path("/alterar_venda_cred_rec_prog_intervalo_por_recpayid/{idComercial}/{recPayId}/{intervalo}")	
	public RetornoHttp alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId(@PathParam("idComercial") String idComercial,
			@PathParam("recPayId") String recPayId, @PathParam("intervalo") String intervalo) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId(idComercial, recPayId, intervalo);									
												
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
	@Path("/desabilitar_venda_cred_rec_prog_por_recpayid/{idComercial}/{recPayId}")	
	public RetornoHttp desabilitarVendaCreditoRecProgPorRecurrentPaymentId(@PathParam("idComercial") 
		String idComercial,	@PathParam("recPayId") String recPayId) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.desabilitarVendaCreditoRecProgPorRecurrentPaymentId(idComercial, recPayId);									
												
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
	@Path("/reabilitar_venda_cred_rec_prog_por_recpayid/{idComercial}/{recPayId}")	
	public RetornoHttp reabilitarVendaCreditoRecProgPorRecurrentPaymentId(@PathParam("idComercial") String idComercial, 
			@PathParam("recPayId") String recPayId) {
		
		RetornoHttp res = new RetornoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			fachada.reabilitarVendaCreditoRecProgPorRecurrentPaymentId(idComercial, recPayId);									
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}
}
