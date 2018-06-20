package br.pe.recife.surfix.ecommerce.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
	@Path("/consultar_vend_cred_a_vista_por_payid/{payId}")
	public RetornoSaleHttp consultarVendaCreditoAVistaPorPaymentId(@PathParam("payId") String payId,
			@QueryParam("idComercial") String idComercial) {
 
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
	@Path("/consultar_vendas_por_pednum/{pedNum}")
	public RetornoPaymentsHttp consultarVendasPorNumPedidoVirtual(@PathParam("pedNum") String pedNum,
			@QueryParam("idComercial") String idComercial) {
		
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
	@Path("/cancelar_pag_total_cred_a_vista/{payId}")
	public RetornoSaleResponseHttp cancelarPagamentoTotalCreditoAVista(@PathParam("payId") String payId,
			@QueryParam("idComercial") String idComercial) {
		
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
	@Path("/consultar_vend_cred_rec_prog_por_recpayid/{recPayId}")
	public RetornoRecurrentSaleHttp consultarVendaCreditoRecProgPorRecurrentPaymentId(@PathParam("recPayId") String recPayId,
			@QueryParam("idComercial") String idComercial) {
		
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
	
	//Restantes:
	
	//alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId
	//alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId
	//alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId
	//alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId
	//alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId
	//desabilitarVendaCreditoRecProgPorRecurrentPaymentId
	//reabilitarVendaCreditoRecProgPorRecurrentPaymentId
}
