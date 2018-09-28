package br.pe.recife.surfix.ecommerce.controller;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;
import br.pe.recife.surfix.ecommerce.entity.Transacao;
import br.pe.recife.surfix.ecommerce.entity.http.RetornoTransacaoHttp;
import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoGeneralSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentsHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleResponseHttp;
import br.pe.recife.surfix.ecommerce.http.VendaCreditoHttp;
import br.pe.recife.surfix.ecommerce.service.EmpresaAdquirenteService;
import br.pe.recife.surfix.ecommerce.service.TransacaoService;
import br.pe.recife.surfix.ecommerce.util.Configuracao;
import cielo.environment.util.FachadaCielo;
import cielo.environment.util.FachadaCieloException;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.RecurrentSale;
import cieloecommerce.sdk.ecommerce.Sale;
import cieloecommerce.sdk.ecommerce.SaleResponse;

@Component
@Path("/hib")
public class CieloController {
	
	private static final String TESTE_PROPERTY = "prod";
	private static final String TESTE_VALUE = "true";
					
	private final Configuracao configuracao;	
	private final boolean modoProd;
			
	private FachadaCielo fachada = FachadaCielo.getInstancia();
	
	@Autowired
	private EmpresaAdquirenteService empresaAdquirenteService;		
	
	@Autowired
	private TransacaoService transacaoService;
	
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
	 * Chama [1-gerarPagamentoCreditoAVista] - (HÍBRIDO)*
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
			TransacaoHttp transacaoHttp = this.registrarTransacao( empresaAdquirente, vendaCreditoHttp,
					payment, res, Transacao.OPERACAO_TRANSACAO_1);
						
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
	@DenyAll
	public RetornoGeneralSaleHttp consultarVendaCreditoAVistaPorPaymentId(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idPayment") String idPayment) {
 
		RetornoGeneralSaleHttp res = new RetornoGeneralSaleHttp();
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	//Novo método que utiliza transacaoId, verificando se existe idPayment ou idRecPayment 
	//  para tal (serve tanto para pagamentos normais como recorrentes) 	
	/**
	 * 
	 * Chama [2.a-consultarVendaPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */		
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/consultar_vend_por_transacaoid")
	@RolesAllowed("ADMIN")
	public RetornoGeneralSaleHttp consultarVendaPorTransacaoId(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idTransacao") String idTransacao) {
 
		RetornoGeneralSaleHttp res = new RetornoGeneralSaleHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao);
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//					
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
						
			Sale sale = null;
			RecurrentSale recSale = null;
			
			if (transacaoPai.getPaymentId() != null) {
				sale = fachada.consultarVendaCreditoAVistaPorPaymentId(this.modoProd, mecId, mecKey, transacaoPai.getPaymentId());
				res.setSale(sale);
			}			
			if (transacaoPai.getRecPaymentId() != null) {
				recSale = fachada.consultarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, transacaoPai.getRecPaymentId());
				res.setRecurrentSale(recSale);
			}		
			
			res.setTransacao(TransacaoHttp.gerarTransacaoHttp(transacaoPai));
						
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	
	/**
	 * 
	 * Chama [3-consultarVendasPorNumPedidoVirtual]
	 * 
	 * */
	@GET
	@Produces("application/json; charset=UTF-8")
	@Path("/consultar_vendas_por_pednum")	
	@DenyAll
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
	@DenyAll
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
			
			res.setSaleResponse(saleResponse);
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------		
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idPayment para tal	
	/**
	 * 
	 * Chama [4.a-cancelarPagamentoTotalCreditoAVistaPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/cancelar_pag_total_cred_a_vista_por_transacaoid")
	@RolesAllowed("ADMIN")
	public RetornoSaleResponseHttp cancelarPagamentoTotalCreditoAVistaPorTransacaoId(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idTransacao") String idTransacao) {
		
		RetornoSaleResponseHttp res = new RetornoSaleResponseHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, false);			
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//				
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			SaleResponse saleResponse = fachada.cancelarPagamentoTotalCreditoAVista(this.modoProd, mecId, mecKey, transacaoPai.getPaymentId());
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, saleResponse, 
					res, Transacao.OPERACAO_TRANSACAO_4);
						
			res.setTransacao(transacaoHttp);
			//
			
			res.setSaleResponse(saleResponse);
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}		
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	
	//PARTE II - Compra recorrente
	
	/**
	 * 
	 * Chama [5-gerarPagamentoCreditoAVistaRecProg] - (HÍBRIDO)*
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
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, vendaCreditoHttp,
					payment, res, Transacao.OPERACAO_TRANSACAO_2);
						
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
	 * Chama [6-gerarPagamentoCreditoAgendadoRecProg] - (HÍBRIDO)*
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
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, vendaCreditoHttp, 
					payment, res, Transacao.OPERACAO_TRANSACAO_3);
						
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
	@DenyAll
	public RetornoGeneralSaleHttp consultarVendaCreditoRecProgPorRecurrentPaymentId(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idRecPayment") String idRecPayment) {
		
		RetornoGeneralSaleHttp res = new RetornoGeneralSaleHttp();
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
	@DenyAll
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal	
	/**
	 * 
	 * Chama [8.a-alterarPagamentoCreditoRecProgPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */	
	@PUT
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_pag_cred_rec_prog_por_transacaoid")
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp alterarPagamentoCreditoRecProgPorTransacaoId(@HeaderParam("idComercial") String idComercial,
			@HeaderParam("idTransacao") String idTransacao, VendaCreditoHttp vendaCreditoHttp) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);	
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//			
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

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
					transacaoPai.getRecPaymentId(),
					vendaCreditoHttp.getPedidoVirtualHttp().getValor(),
					vendaCreditoHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoHttp.getPedidoVirtualHttp().getDescricaoVenda());		
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, vendaCreditoHttp, 
					res, Transacao.OPERACAO_TRANSACAO_7);
						
			res.setTransacao(transacaoHttp);
			//
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}					
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * Chama [9-alterarVendaCreditoRecProgDataFinalPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_final_por_recpayid")	
	@DenyAll
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

	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal	
	/**
	 * 
	 * Chama [9.a-alterarVendaCreditoRecProgDataFinalPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_final_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp alterarVendaCreditoRecProgDataFinalPorTransacaoId(@HeaderParam("idTransacao") String idTransacao,
		@HeaderParam("dataFinal") String dataFinal, @HeaderParam("idComercial") String idComercial) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);			
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//	
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

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
					transacaoPai.getRecPaymentId(), dataFinal);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_8, dataFinal);
						
			res.setTransacao(transacaoHttp);
			//
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
			
	/**
	 * 
	 * Chama [10-alterarVendaCreditoRecProgDiaRecPorRecurrentPaymentId]
	 * 
	 * */
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_dia_rec_por_recpayid")	
	@DenyAll
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal	
	/**
	 * 
	 * Chama [10.a-alterarVendaCreditoRecProgDiaRecPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_dia_rec_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp alterarVendaCreditoRecProgDiaRecPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("diaRec") int diaRec, @HeaderParam("idComercial") String idComercial) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);	
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//				
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

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
					transacaoPai.getRecPaymentId(), diaRec);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_9, diaRec + "");
						
			res.setTransacao(transacaoHttp);
			//			
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * Chama [11-alterarVendaCreditoRecProgValorRecPorRecurrentPaymentId]
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_valor_rec_por_recpayid")	
	@DenyAll
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal		
	/**
	 * 
	 * Chama [11.a-alterarVendaCreditoRecProgValorRecPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_valor_rec_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp alterarVendaCreditoRecProgValorRecPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("valorRec") int valorRec, @HeaderParam("idComercial") String idComercial) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);	
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//	
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

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
					transacaoPai.getRecPaymentId(), valorRec);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_10, valorRec + "");
						
			res.setTransacao(transacaoHttp);
			//				
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	

	/**
	 * 
	 * Chama [12-alterarVendaCreditoRecProgDataProxRecPorRecurrentPaymentId]
	 * 
	 * */		
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_prox_rec_por_recpayid")	
	@DenyAll
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
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal		
	/**
	 * 
	 * Chama [12.a-alterarVendaCreditoRecProgDataProxRecPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_prox_rec_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp alterarVendaCreditoRecProgDataProxRecPorTransferenciaId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("dataProxRec") String dataProxRec, @HeaderParam("idComercial") String idComercial) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
						
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);		
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//	
						
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

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
					transacaoPai.getRecPaymentId(), dataProxRec);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_11, dataProxRec);
						
			res.setTransacao(transacaoHttp);
			//				
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	

	/**
	 * 
	 * Chama [13-alterarVendaCreditoRecProgIntervaloPorRecurrentPaymentId]
	 * 
	 * */		
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_intervalo_por_recpayid")	
	@DenyAll
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal		
	/**
	 * 
	 * Chama [13.a-alterarVendaCreditoRecProgIntervaloPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_intervalo_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp alterarVendaCreditoRecProgIntervaloPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("intervalo") String intervalo, @HeaderParam("idComercial") String idComercial) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);		
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//				
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

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
					transacaoPai.getRecPaymentId(), intervalo);		
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_12, intervalo);
						
			res.setTransacao(transacaoHttp);
			//				
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
		
	/**
	 * 
	 * Chama [14-desabilitarVendaCreditoRecProgPorRecurrentPaymentId]
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/desabilitar_venda_cred_rec_prog_por_recpayid")	
	@DenyAll
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal	
	/**
	 * 
	 * Chama [14.a-desabilitarVendaCreditoRecProgPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */		
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/desabilitar_venda_cred_rec_prog_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp desabilitarVendaCreditoRecProgPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("idComercial") String idComercial) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);	
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//	
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.desabilitarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, transacaoPai.getRecPaymentId());
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_5);
						
			res.setTransacao(transacaoHttp);
			//
															
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * Chama [15-reabilitarVendaCreditoRecProgPorRecurrentPaymentId]
	 * 
	 * */		
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/reabilitar_venda_cred_rec_prog_por_recpayid")	
	@DenyAll
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
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal	
	/**
	 * 
	 * Chama [15.a-reabilitarVendaCreditoRecProgPorTransacaoId] - (HÍBRIDO)*
	 * 
	 * */	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/reabilitar_venda_cred_rec_prog_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoTransacaoHttp reabilitarVendaCreditoRecProgPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
			@HeaderParam("idComercial") String idComercial) {
		
		RetornoTransacaoHttp res = new RetornoTransacaoHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);		
			this.checarTransacaoEmpresaAdquirente(transacaoPai, idComercial);
			//
			
			EmpresaAdquirente empresaAdquirente = transacaoPai.getEmpresaAdquirente();

			String mecId;
			String mecKey;
			if (this.modoProd) {
				mecId = empresaAdquirente.getMecId();
				mecKey = empresaAdquirente.getMecKey();
			} else {
				mecId = empresaAdquirente.getMecIdTeste();
				mecKey = empresaAdquirente.getMecKeyTeste();
			}
			
			fachada.reabilitarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, transacaoPai.getRecPaymentId());
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_6);
						
			res.setTransacao(transacaoHttp);
			
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	//*Os métodos híbridos manipulam dados da Cielo e do BD ecommerce
	// Os não híbridos, apenas os dados da Cielo
	
	//***************************************		
	private EmpresaAdquirente empresaAdquirenteRequisitado(String id) throws FachadaCieloException {
				
		EmpresaAdquirente empresaAdquirente = null;
		
		try {
			Integer idEmpresaAquirente = Integer.valueOf(id);		
			
			empresaAdquirente = empresaAdquirenteService.consultarPorId(idEmpresaAquirente);
									
		} catch (Exception e) {        	     
        	
        	throw new FachadaCieloException(e, "ID Empresa Adquirente inválido");
        }				
                
		if (empresaAdquirente != null) {			
			return empresaAdquirente;
		} else {
			throw new FachadaCieloException(null, "Erro ao tentar recuperar credenciais de acesso");
		}
	}

	private Transacao transacaoRequisitada(String id) throws FachadaCieloException {
		
		Transacao transacao = null;
		
		try {
			Integer idTransacao = Integer.valueOf(id);		
			
			transacao = transacaoService.consultarPorId(idTransacao);
									
		} catch (Exception e) {        	     
        	
        	throw new FachadaCieloException(e, "ID Transação inválido");
        }				
                
		if (transacao != null) {				
			if (transacao.getTransacaoPai() == null) {				
				return transacao;
			} else {							
				throw new FachadaCieloException(null, "A Transação informada não é uma transação pai");
			}			
		} else {			
			throw new FachadaCieloException(null, "Erro ao tentar recuperar Transação " + id);
		}
	}			
		
	private Transacao transacaoRequisitada(String id, boolean recorrente) throws FachadaCieloException {
		
		Transacao transacao = null;
		
		try {
			Integer idTransacao = Integer.valueOf(id);		
			
			transacao = transacaoService.consultarPorId(idTransacao);
									
		} catch (Exception e) {        	     
        	
        	throw new FachadaCieloException(e, "ID Transação inválido");
        }				
                
		if (transacao != null) {	
			
			if (recorrente) {
				if (transacao.getRecPaymentId() != null && transacao.getTransacaoPai() == null) {				
					return transacao;
				} else {				
					if (transacao.getRecPaymentId() == null) {					
						throw new FachadaCieloException(null, "A Transação informada não é um pagamento recorrente");
					} else {					
						throw new FachadaCieloException(null, "A Transação informada não é uma transação pai");
					}
				}			
			} else {
				if (transacao.getPaymentId() != null && transacao.getTransacaoPai() == null) {				
					return transacao;
				} else {				
					if (transacao.getPaymentId() == null) {					
						throw new FachadaCieloException(null, "A Transação informada não é um pagamento à vista");
					} else {					
						throw new FachadaCieloException(null, "A Transação informada não é uma transação pai");
					}
				}				
			}
		} else {			
			throw new FachadaCieloException(null, "Erro ao tentar recuperar Transação " + id);
		}
	}		
	
	private void checarTransacaoEmpresaAdquirente(Transacao transacao, String idComercial) throws FachadaCieloException {
		
		Integer i;
		
		try {
			
			i = Integer.parseInt(idComercial);
			
			if (!i.equals(transacao.getEmpresaAdquirente().getEmpresa().getId())) {
				throw new FachadaCieloException(null, "Transação não encontrada para a Empresa informada");
			}
			
		} catch (Exception e) {
			throw new FachadaCieloException(null, "Transação não encontrada para a Empresa informada");
		}		
	}
		
	//Registra uma compra no cartão à vista ou recorrente
	private TransacaoHttp registrarTransacao(EmpresaAdquirente empresaAdquirente, 
			VendaCreditoHttp vendaCreditoHttp, Payment payment, RetornoPaymentHttp res, 
			String operacao) {
			
		Transacao transacao = Transacao.gerarTransacao(empresaAdquirente, operacao, vendaCreditoHttp,  
				payment);
		
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
				
		return transacaoHttp;
		
	}
	
	//Registra o cancelamento de uma compra no cartão à vista
	private TransacaoHttp registrarTransacao(Transacao transacaoPai, 
			SaleResponse saleResponse, RetornoSaleResponseHttp res, String operacao) {
		
		Transacao transacao = Transacao.gerarTransacao(transacaoPai, operacao, saleResponse);
					
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}
	
	//Registra a desativação ou reativação de um pagamento recorrente
	private TransacaoHttp registrarTransacao(Transacao transacaoPai, 
			RetornoTransacaoHttp res, String operacao) {
		
		Transacao transacao = null;
		
		if (Transacao.OPERACAO_TRANSACAO_5.equals(operacao)) {			
			transacao = Transacao.gerarTransacao(transacaoPai, operacao, true);
			
		} else if (Transacao.OPERACAO_TRANSACAO_6.equals(operacao)) {
			transacao = Transacao.gerarTransacao(transacaoPai, operacao, false);			
		}
							
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}	
	
	//Registra a alteração dos dados de um pagamento recorrente
	private TransacaoHttp registrarTransacao(Transacao transacaoPai, 
			VendaCreditoHttp vendaCreditoHttp, RetornoTransacaoHttp res, String operacao) {
		
		Transacao transacao = null;
		
		transacao = Transacao.gerarTransacao(transacaoPai, operacao, vendaCreditoHttp);
		
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;		
	}
	
	//Registra alterações de um pagamento recorrente (exceto de dados de pagamento)
	private TransacaoHttp registrarTransacao(Transacao transacaoPai, 
			RetornoTransacaoHttp res, String operacao, String outraAlteracao) {
		
		Transacao transacao = null;
				
		transacao = Transacao.gerarTransacao(transacaoPai, operacao, outraAlteracao);
						
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}	
		
	private TransacaoHttp encaminharParaFachada(Transacao transacao, RetornoHttp res) {
		
		boolean erro = false;
		Exception exc = null;			
		try {				
			transacaoService.salvar(transacao);				
		} catch (Exception e) {		
			
			e.printStackTrace();
			
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
			System.out.println(exc.getMessage());	
			System.out.println("********************************");		
			
			//TODO - Salvar em arquivo texto no servidor, nesse caso
		}
		
		return transacaoHttp;
		
	}
	
}
