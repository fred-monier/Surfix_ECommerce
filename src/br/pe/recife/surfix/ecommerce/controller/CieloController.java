package br.pe.recife.surfix.ecommerce.controller;

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
import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import br.pe.recife.surfix.ecommerce.exception.InfraException;
import br.pe.recife.surfix.ecommerce.http.RetornoHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoManutRecHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoPaymentsHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoRecurrentSaleHttp;
import br.pe.recife.surfix.ecommerce.http.RetornoSaleHttp;
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
@Path("/cielo")
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
					payment, res, Transacao.OPERACAO_TRANSACAO_1);
						
			res.setTransacao(transacaoHttp);
			//	
			
			res.setPayment(payment);
			
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;				
	}	
	
	//TODO - Criar um novo método que utilize transacaoId Pai (verificar se é, de fato), ao invés de idPayment (trazendo todas as transações filhas). Serve para RecProg.
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
	
	//TODO - Criar um novo método que retorne os transacaoIds que sejam Pai, ao invés de idPayments
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
	
	//TODO - Usar mesmo RetornoSaleResponseHttp pros dois?
	//TODO - Atualizar Postman (1 revertido mais 1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idPayment para tal	
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
			
			res.setSaleResponse(saleResponse);
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/cancelar_pag_total_cred_a_vista_por_transacaoid")
	@RolesAllowed("ADMIN")
	public RetornoSaleResponseHttp cancelarPagamentoTotalCreditoAVistaPorTransacaoId(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idTransacao") String idTransacao) {
		
		RetornoSaleResponseHttp res = new RetornoSaleResponseHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, false);						
			//				
			
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
			
			SaleResponse saleResponse = fachada.cancelarPagamentoTotalCreditoAVista(this.modoProd, mecId, mecKey, transacaoPai.getPaymentId());
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, saleResponse, transacaoPai, 
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
	
	//TODO - Atualizar Postman (1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal
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
	@PUT
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_pag_cred_rec_prog_por_transacaoid")
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp alterarPagamentoCreditoRecProgPorTransacaoId(@HeaderParam("idComAdq") String idComAdq,
			@HeaderParam("idTransacao") String idTransacao, VendaCreditoHttp vendaCreditoHttp) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);						
			//			
			
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
					transacaoPai.getRecPaymentId(),
					vendaCreditoHttp.getPedidoVirtualHttp().getValor(),
					vendaCreditoHttp.getCartaoCreditoHttp().getBandeiraCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getNumCartao(),					
					vendaCreditoHttp.getCartaoCreditoHttp().getMesAnoExpDate(), 
					vendaCreditoHttp.getCartaoCreditoHttp().getNomeClienteCartao(),
					vendaCreditoHttp.getCartaoCreditoHttp().getCvv(),
					vendaCreditoHttp.getPedidoVirtualHttp().getDescricaoVenda());		
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(vendaCreditoHttp, empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_7);
						
			res.setTransacao(transacaoHttp);
			//
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}					
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	//TODO - Atualizar Postman (1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal	
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
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_final_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp alterarVendaCreditoRecProgDataFinalPorTransacaoId(@HeaderParam("idTransacao") String idTransacao,
		@HeaderParam("dataFinal") String dataFinal, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);						
			//	
			
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
					transacaoPai.getRecPaymentId(), dataFinal);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_8, dataFinal);
						
			res.setTransacao(transacaoHttp);
			//
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
		
	//TODO - Atualizar Postman (1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal	
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
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_dia_rec_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp alterarVendaCreditoRecProgDiaRecPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("diaRec") int diaRec, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);						
			//				
			
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
					transacaoPai.getRecPaymentId(), diaRec);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_9, diaRec + "");
						
			res.setTransacao(transacaoHttp);
			//			
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	//TODO - Atualizar Postman (1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal
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
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_valor_rec_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp alterarVendaCreditoRecProgValorRecPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("valorRec") int valorRec, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);					
			//	
			
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
					transacaoPai.getRecPaymentId(), valorRec);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_10, valorRec + "");
						
			res.setTransacao(transacaoHttp);
			//				
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
		
	//TODO - Atualizar Postman (1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal
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
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_data_prox_rec_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp alterarVendaCreditoRecProgDataProxRecPorTransferenciaId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("dataProxRec") String dataProxRec, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
						
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);						
			//	
						
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
					transacaoPai.getRecPaymentId(), dataProxRec);	
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_11, dataProxRec);
						
			res.setTransacao(transacaoHttp);
			//				
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	
	//TODO - Atualizar Postman (1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
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
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/alterar_venda_cred_rec_prog_intervalo_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp alterarVendaCreditoRecProgIntervaloPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("intervalo") String intervalo, @HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);						
			//				
			
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
					transacaoPai.getRecPaymentId(), intervalo);		
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_12, intervalo);
						
			res.setTransacao(transacaoHttp);
			//				
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------	
	
	
	//TODO - Atualizar Postman (1 revertido mais 1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal
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
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/desabilitar_venda_cred_rec_prog_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp desabilitarVendaCreditoRecProgPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
		@HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);						
			//	
			
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
			
			fachada.desabilitarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, transacaoPai.getRecPaymentId());
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_5);
						
			res.setTransacao(transacaoHttp);
			//
															
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	//TODO - Atualizar Postman (1 revertido mais 1 método novo)
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	//Criado um novo método que utiliza transacaoId (registrando a Transação), verificando se existe um idRecPayment para tal
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
	@PUT
	@Produces("application/json; charset=UTF-8")
	@Path("/reabilitar_venda_cred_rec_prog_por_transacaoid")	
	@RolesAllowed("ADMIN")
	public RetornoManutRecHttp reabilitarVendaCreditoRecProgPorTransacaoId(@HeaderParam("idTransacao") String idTransacao, 
			@HeaderParam("idComAdq") String idComAdq) {
		
		RetornoManutRecHttp res = new RetornoManutRecHttp();
		res.setResultado(RetornoHttp.SUCESSO);
		
		try {
			
			//Recuperando Transacao
			Transacao transacaoPai = transacaoRequisitada(idTransacao, true);						
			//
			
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
			
			fachada.reabilitarVendaCreditoRecProgPorRecurrentPaymentId(this.modoProd, mecId, mecKey, transacaoPai.getRecPaymentId());
			
			//Registrar Transação
			TransacaoHttp transacaoHttp = this.registrarTransacao(empresaAdquirente, transacaoPai, 
					res, Transacao.OPERACAO_TRANSACAO_6);
						
			res.setTransacao(transacaoHttp);
			
												
		} catch (FachadaCieloException e) {
			
			res.setResultado(e.getMensagem());							
		}
		
		return res;
	}	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	//***************************************
	
	private EmpresaAdquirente empresaAdquirenteRequisitado(String id) throws FachadaCieloException {
				
		EmpresaAdquirente empresaAdquirente = null;
		
		try {
			Integer idEmpresaAquirente = Integer.valueOf(id);		
			
			empresaAdquirente = empresaAdquirenteService.consultarPorId(idEmpresaAquirente);
									
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
	
	//
	private Transacao transacaoRequisitada(String id, boolean recorrente) throws FachadaCieloException {
		
		Transacao transacao = null;
		
		try {
			Integer idTransacao = Integer.valueOf(id);		
			
			transacao = transacaoService.consultarPorId(idTransacao);
									
		} catch (InfraException e) {
			
			throw new FachadaCieloException(e, "Erro ao tentar recuperar Transação " + id);
			
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
	//
		
	//Registra uma compra no cartão à vista ou recorrente
	private TransacaoHttp registrarTransacao(VendaCreditoHttp vendaCreditoHttp, 
			EmpresaAdquirente empresaAdquirente, Payment payment, RetornoPaymentHttp res, 
			String operacao) {
			
		Transacao transacao = Transacao.gerarTransacao(vendaCreditoHttp, empresaAdquirente, 
				payment, operacao);
		
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
				
		return transacaoHttp;
		
	}
	
	//Registra o cancelamento de uma compra no cartão à vista
	private TransacaoHttp registrarTransacao(EmpresaAdquirente empresaAdquirente,
			SaleResponse saleResponse, Transacao transacaoPai, RetornoSaleResponseHttp res, 
			String operacao) {
		
		Transacao transacao = Transacao.gerarTransacao(empresaAdquirente, saleResponse, 
				operacao, transacaoPai);
					
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}
	
	//Registra a desativação ou reativação de um pagamento recorrente
	private TransacaoHttp registrarTransacao(EmpresaAdquirente empresaAdquirente,
			Transacao transacaoPai, RetornoManutRecHttp res, String operacao) {
		
		Transacao transacao = null;
		
		if (Transacao.OPERACAO_TRANSACAO_5.equals(operacao)) {			
			transacao = Transacao.gerarTransacao(empresaAdquirente, operacao, transacaoPai, true);
			
		} else if (Transacao.OPERACAO_TRANSACAO_6.equals(operacao)) {
			transacao = Transacao.gerarTransacao(empresaAdquirente, operacao, transacaoPai, false);			
		}
							
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}	
	
	//Registra a alteração dos dados de um pagamento recorrente
	private TransacaoHttp registrarTransacao(VendaCreditoHttp vendaCreditoHttp, EmpresaAdquirente empresaAdquirente,
			Transacao transacaoPai, RetornoManutRecHttp res, String operacao) {
		
		Transacao transacao = null;
		
		transacao = Transacao.gerarTransacao(vendaCreditoHttp, empresaAdquirente, operacao, transacaoPai);
		
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;		
	}
	
	//Registra alterações de um pagamento recorrente (exceto de dados de pagamento)
	private TransacaoHttp registrarTransacao(EmpresaAdquirente empresaAdquirente,
			Transacao transacaoPai, RetornoManutRecHttp res, String operacao, String outraAlteracao) {
		
		Transacao transacao = null;
				
		transacao = Transacao.gerarTransacao(empresaAdquirente, operacao, transacaoPai, outraAlteracao);
						
		TransacaoHttp transacaoHttp = this.encaminharParaFachada(transacao, res);
		
		return transacaoHttp;
	}	
		
	private TransacaoHttp encaminharParaFachada(Transacao transacao, RetornoHttp res) {
		
		boolean erro = false;
		InfraException exc = null;			
		try {				
			transacaoService.salvar(transacao);				
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
			
			//TODO - Salvar em arquivo texto no servidor, nesse caso
		}
		
		return transacaoHttp;
		
	}
	
}
