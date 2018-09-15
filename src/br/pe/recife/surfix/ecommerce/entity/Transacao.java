package br.pe.recife.surfix.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.Gson;

import br.pe.recife.surfix.ecommerce.http.VendaCreditoHttp;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.SaleResponse;

@Entity
@Table(name="\"TRANSACAO\"")
public class Transacao implements EntidadeBase {	
		
	public static final String OPERACAO_TRANSACAO_1 = "CREDITO_AVISTA";
	public static final String OPERACAO_TRANSACAO_2 = "CREDITO_AVISTA_RECORRENTE";
	public static final String OPERACAO_TRANSACAO_3 = "CREDITO_AGENDADO_RECORRENTE";	
	public static final String OPERACAO_TRANSACAO_4 = "CREDITO_AVISTA_CANCELAMENTO";
	public static final String OPERACAO_TRANSACAO_5 = "CREDITO_RECORRENTE_DESATIVACAO";
	public static final String OPERACAO_TRANSACAO_6 = "CREDITO_RECORRENTE_REATIVACAO";
	public static final String OPERACAO_TRANSACAO_7 = "CREDITO_RECORRENTE_ALTERACAO_PAGAMENTO_RECORRENTE";		
	public static final String OPERACAO_TRANSACAO_8 = "CREDITO_RECORRENTE_ALTERACAO_DATA_FINAL_RECORRENCIA";
	public static final String OPERACAO_TRANSACAO_9 = "CREDITO_RECORRENTE_ALTERACAO_DIA_RECORENCIA";
	public static final String OPERACAO_TRANSACAO_10 = "CREDITO_RECORRENTE_ALTERACAO_VALOR_RECORRENCIA";
	public static final String OPERACAO_TRANSACAO_11 = "CREDITO_RECORRENTE_ALTERACAO_DATA_RECORRENCIA";	
	public static final String OPERACAO_TRANSACAO_12 = "CREDITO_RECORRENTE_ALTERACAO_INTERVALO_RECORRENCIA";
	
	private static final Integer PAYMENT_STATUS_CANCELADO = 10;	
	private static final Integer REC_PAYMENT_STATUS_DISABILITADO = 3;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="transacao_sequence")
	@SequenceGenerator(name = "transacao_sequence", sequenceName = "\"TRANSACAO_ID_seq\"")	
    @Column(name = "\"ID\"", nullable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "\"ID_PAI\"")
	private Transacao transacaoPai;
	
	@OneToMany(mappedBy = "transacaoPai", targetEntity = Transacao.class, 
	fetch =	FetchType.LAZY, cascade = CascadeType.ALL) 
	private Set<Transacao> transacoesFilhas;
			
	@ManyToOne
	@JoinColumn(name = "\"ID_EMPRESA_ADQUIRENTE\"", nullable = false)
	private EmpresaAdquirente empresaAdquirente;
		
	@Column(name = "\"JSON_IN\"")
	private String jSonIn;
	
	@Column(name = "\"JSON_OUT\"")
	private String jSonOut;
	
	@Column(name = "\"OPERACAO\"", nullable = false)
	private String operacao;
	
	@Column(name = "\"DATA_HORA\"", nullable = false)
	private LocalDateTime dataHora;
	
	@Column(name = "\"PROVIDER\"")
	private String provider;
	
	@Column(name = "\"AMOUNT\"")
	private Integer amount;
	
	@Column(name = "\"CREDITCARD_BRAND\"")
	private String creditCardBrand;
	
	@Column(name = "\"CREDITCARD_NUMBER\"")
	private String creditCardNumber;
	
	@Column(name = "\"STATUS\"")
	private String status;
	
	@Column(name = "\"PAYMENT_ID\"")
	private String paymentId;
	
	@Column(name = "\"PAYMENT_AUTHCODE\"")
	private String paymentAuthCode;
	
	@Column(name = "\"PAYMENT_PROOF_OF_SALE\"")
	private String paymentProofOfSale;
	
	@Column(name = "\"PAYMENT_TID\"")
	private String paymentTid;
	
	@Column(name = "\"PAYMENT_RECEIVED_DATE\"")
	private String paymentReceivedDate;
	
	@Column(name = "\"PAYMENT_RETURN_CODE\"")
	private String paymentReturnCode;
	
	@Column(name = "\"PAYMENT_RETURN_MESSAGE\"")
	private String paymentReturnMessage;

	@Column(name = "\"PAYMENT_REASON_CODE\"")
	private String paymentReasonCode;
	
	@Column(name = "\"PAYMENT_REASON_MESSAGE\"")
	private String paymentReasonMessage;	
	
	@Column(name = "\"PAYMENT_PROVIDER_RETURN_CODE\"")
	private String paymentProviderReturnCode;
	
	@Column(name = "\"PAYMENT_PROVIDER_RETURN_MESSAGE\"")
	private String paymentProviderReturnMessage;		

	@Column(name = "\"PAYMENT_CANCELADO\"")
	private Boolean paymentCancelado;
	
	@Column(name = "\"REC_PAYMENT_ID\"")
	private String recPaymentId;
	
	@Column(name = "\"REC_PAYMENT_AUTH_NOW\"")
	private Boolean recPaymentAuthNow;
	
	@Column(name = "\"REC_PAYMENT_START_DATE\"")
	private String recPaymentStartDate;
	
	@Column(name = "\"REC_PAYMENT_END_DATE\"")
	private String recPaymentEndDate;
	
	@Column(name = "\"REC_PAYMENT_NEXT_RECURRENCY\"")
	private String recPaymentNextRecurrency;
	
	@Column(name = "\"REC_PAYMENT_RECURRENCY_DAY\"")
	private String recPaymentRecurrencyDay;	
	
	@Column(name = "\"REC_PAYMENT_MONTHS_INTERVAL\"")
	private String recPaymentMonthsInterval;	
	
	@Column(name = "\"REC_PAYMENT_REASON_CODE\"")
	private String recPaymentReasonCode;
	
	@Column(name = "\"REC_PAYMENT_REASON_MESSAGE\"")
	private String recPaymentReasonMessage;	
		
	@Column(name = "\"REC_PAYMENT_DESABILITADO\"")
	private Boolean recPaymentDesabilitado;
	
	@Column(name = "\"NUM_PEDIDO_VIRTUAL\"")
	private String numPedidoVirtual;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
		
	public Transacao getTransacaoPai() {
		return transacaoPai;
	}

	public void setTransacaoPai(Transacao transacaoPai) {
		this.transacaoPai = transacaoPai;
	}
		
	public Set<Transacao> getTransacoesFilhas() {
		return transacoesFilhas;
	}

	public void setTransacoesFilhas(Set<Transacao> transacoesFilhas) {
		this.transacoesFilhas = transacoesFilhas;
	}

	public EmpresaAdquirente getEmpresaAdquirente() {
		return empresaAdquirente;
	}

	public void setEmpresaAdquirente(EmpresaAdquirente empresaAdquirente) {
		this.empresaAdquirente = empresaAdquirente;
	}

	public String getjSonIn() {
		return jSonIn;
	}

	public void setjSonIn(String jSonIn) {
		this.jSonIn = jSonIn;
	}

	public String getjSonOut() {
		return jSonOut;
	}

	public void setjSonOut(String jSonOut) {
		this.jSonOut = jSonOut;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCreditCardBrand() {
		return creditCardBrand;
	}

	public void setCreditCardBrand(String creditCardBrand) {
		this.creditCardBrand = creditCardBrand;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentAuthCode() {
		return paymentAuthCode;
	}

	public void setPaymentAuthCode(String paymentAuthCode) {
		this.paymentAuthCode = paymentAuthCode;
	}

	public String getPaymentProofOfSale() {
		return paymentProofOfSale;
	}

	public void setPaymentProofOfSale(String paymentProofOfSale) {
		this.paymentProofOfSale = paymentProofOfSale;
	}

	public String getPaymentTid() {
		return paymentTid;
	}

	public void setPaymentTid(String paymentTid) {
		this.paymentTid = paymentTid;
	}

	public String getPaymentReceivedDate() {
		return paymentReceivedDate;
	}

	public void setPaymentReceivedDate(String paymentReceivedDate) {
		this.paymentReceivedDate = paymentReceivedDate;
	}

	public String getPaymentReturnCode() {
		return paymentReturnCode;
	}

	public void setPaymentReturnCode(String paymentReturnCode) {
		this.paymentReturnCode = paymentReturnCode;
	}

	public String getPaymentReturnMessage() {
		return paymentReturnMessage;
	}

	public void setPaymentReturnMessage(String paymentReturnMessage) {
		this.paymentReturnMessage = paymentReturnMessage;
	}
			
	public String getPaymentReasonCode() {
		return paymentReasonCode;
	}

	public void setPaymentReasonCode(String paymentReasonCode) {
		this.paymentReasonCode = paymentReasonCode;
	}

	public String getPaymentReasonMessage() {
		return paymentReasonMessage;
	}

	public void setPaymentReasonMessage(String paymentReasonMessage) {
		this.paymentReasonMessage = paymentReasonMessage;
	}

	public String getPaymentProviderReturnCode() {
		return paymentProviderReturnCode;
	}

	public void setPaymentProviderReturnCode(String paymentProviderReturnCode) {
		this.paymentProviderReturnCode = paymentProviderReturnCode;
	}

	public String getPaymentProviderReturnMessage() {
		return paymentProviderReturnMessage;
	}

	public void setPaymentProviderReturnMessage(String paymentProviderReturnMessage) {
		this.paymentProviderReturnMessage = paymentProviderReturnMessage;
	}
	
	public Boolean getPaymentCancelado() {
		return paymentCancelado;
	}

	public void setPaymentCancelado(Boolean paymentCancelado) {
		this.paymentCancelado = paymentCancelado;
	}

	public String getRecPaymentId() {
		return recPaymentId;
	}

	public void setRecPaymentId(String recPaymentId) {
		this.recPaymentId = recPaymentId;
	}

	public Boolean getRecPaymentAuthNow() {
		return recPaymentAuthNow;
	}

	public void setRecPaymentAuthNow(Boolean recPaymentAuthNow) {
		this.recPaymentAuthNow = recPaymentAuthNow;
	}

	public String getRecPaymentStartDate() {
		return recPaymentStartDate;
	}

	public void setRecPaymentStartDate(String recPaymentStartDate) {
		this.recPaymentStartDate = recPaymentStartDate;
	}

	public String getRecPaymentEndDate() {
		return recPaymentEndDate;
	}

	public void setRecPaymentEndDate(String recPaymentEndDate) {
		this.recPaymentEndDate = recPaymentEndDate;
	}
	
	public String getRecPaymentNextRecurrency() {
		return recPaymentNextRecurrency;
	}

	public void setRecPaymentNextRecurrency(String recPaymentNextRecurrency) {
		this.recPaymentNextRecurrency = recPaymentNextRecurrency;
	}
		
	public String getRecPaymentRecurrencyDay() {
		return recPaymentRecurrencyDay;
	}

	public void setRecPaymentRecurrencyDay(String recPaymentRecurrencyDay) {
		this.recPaymentRecurrencyDay = recPaymentRecurrencyDay;
	}

	public String getRecPaymentMonthsInterval() {
		return recPaymentMonthsInterval;
	}

	public void setRecPaymentMonthsInterval(String recPaymentMonthsInterval) {
		this.recPaymentMonthsInterval = recPaymentMonthsInterval;
	}

	public String getRecPaymentReasonCode() {
		return recPaymentReasonCode;
	}

	public void setRecPaymentReasonCode(String recPaymentReasonCode) {
		this.recPaymentReasonCode = recPaymentReasonCode;
	}

	public String getRecPaymentReasonMessage() {
		return recPaymentReasonMessage;
	}

	public void setRecPaymentReasonMessage(String recPaymentReasonMessage) {
		this.recPaymentReasonMessage = recPaymentReasonMessage;
	}
		
	public Boolean getRecPaymentDesabilitado() {
		return recPaymentDesabilitado;
	}

	public void setRecPaymentDesabilitado(Boolean recPaymentDesabilitado) {
		this.recPaymentDesabilitado = recPaymentDesabilitado;
	}

	public String getNumPedidoVirtual() {
		return numPedidoVirtual;
	}

	public void setNumPedidoVirtual(String numPedidoVirtual) {
		this.numPedidoVirtual = numPedidoVirtual;
	}
	
	//***************************************
	
	//Cria uma transação de compra no cartão à vista ou recorrente
	//OPERACAO_TRANSACAO_1 = "CREDITO_AVISTA";
	//OPERACAO_TRANSACAO_2 = "CREDITO_AVISTA_RECORRENTE";
	//OPERACAO_TRANSACAO_3 = "CREDITO_AGENDADO_RECORRENTE";	
	public static Transacao gerarTransacao(EmpresaAdquirente empresaAdquirente, String operacao, 
			VendaCreditoHttp vendaCreditoHttp, Payment payment) {
	
		Transacao res = new Transacao();
		
		Gson gson = new Gson();
		
		//res.setTransacaoPai(null);
		res.setEmpresaAdquirente(empresaAdquirente);
		res.setjSonIn(gson.toJson(vendaCreditoHttp));
		res.setjSonOut(gson.toJson(payment));				
		res.setOperacao(operacao);	
		res.setDataHora(LocalDateTime.now());
		if (vendaCreditoHttp.getPedidoVirtualHttp() != null) {
			res.setNumPedidoVirtual(vendaCreditoHttp.getPedidoVirtualHttp().getNumPedidoVirtual());
		}
		if (payment.getProvider() != null) {
			res.setProvider(payment.getProvider().name());
		}
		res.setAmount(payment.getAmount());
		if (payment.getCreditCard() != null) {
			res.setCreditCardBrand(payment.getCreditCard().getBrand());
			res.setCreditCardNumber(payment.getCreditCard().getCardNumber());
		}		
		res.setStatus(payment.getStatus() + "");
		res.setPaymentId(payment.getPaymentId());
		res.setPaymentAuthCode(payment.getAuthorizationCode());
		res.setPaymentProofOfSale(payment.getProofOfSale());
		res.setPaymentTid(payment.getTid());
		res.setPaymentReceivedDate(payment.getReceivedDate());
		res.setPaymentReturnCode(payment.getReturnCode());
		res.setPaymentReturnMessage(payment.getReturnMessage());		
		//res.setPaymentReasonCode(null);
		//res.setPaymentReasonMessage(null);
		//res.setPaymentProviderReturnCode(null);
		//res.setPaymentProviderReturnMessage(null);					
		res.setPaymentCancelado((Transacao.PAYMENT_STATUS_CANCELADO.
				equals(payment.getStatus()) ? true : null));
		
		if (payment.getRecurrentPayment() != null) {				
			res.setRecPaymentId(payment.getRecurrentPayment().getRecurrentPaymentId());
			res.setRecPaymentAuthNow(payment.getRecurrentPayment().isAuthorizeNow());
			res.setRecPaymentStartDate(payment.getRecurrentPayment().getStartDate());
			res.setRecPaymentEndDate(payment.getRecurrentPayment().getEndDate());
			res.setRecPaymentNextRecurrency(payment.getRecurrentPayment().getNextRecurrency());
			//res.setRecPaymentRecurrencyDay(null);
			res.setRecPaymentMonthsInterval(payment.getRecurrentPayment().getInterval() + "");
			res.setRecPaymentReasonCode(payment.getRecurrentPayment().getReasonCode() + "");
			res.setRecPaymentReasonMessage(payment.getRecurrentPayment().getReasonMessage());
			res.setRecPaymentDesabilitado((Transacao.REC_PAYMENT_STATUS_DISABILITADO.
					equals(payment.getRecurrentPayment().getStatus()) ? true : null));			
		}
								
		return res;		
		
	}							
	
	//OPERACAO_TRANSACAO_4 = "CREDITO_AVISTA_CANCELAMENTO";
	//Cria uma nova transacao de cancelamento de compra no cartão à vista
	public static Transacao gerarTransacao(Transacao transacaoPai, String operacao, 
			SaleResponse saleResponse) {
		
		Transacao res = new Transacao();
		
		Gson gson = new Gson();
		
		res.setTransacaoPai(transacaoPai);
		res.setEmpresaAdquirente(transacaoPai.getEmpresaAdquirente());
		//res.setjSonIn(null);
		res.setjSonOut(gson.toJson(saleResponse));				
		res.setOperacao(operacao);	
		res.setDataHora(LocalDateTime.now());		
		res.setNumPedidoVirtual(saleResponse.getMerchantOrderId());
		//res.setProvider(null);
		//res.setAmount(null);
		//res.setCreditCardBrand(null);
		//res.setCreditCardNumber(null);	
		res.setStatus(saleResponse.getStatus());		
		res.setPaymentId(transacaoPai.getPaymentId());		
		//res.setPaymentAuthCode(null);
		//res.setPaymentProofOfSale(null);
		//res.setPaymentTid(null);
		//res.setPaymentReceivedDate(null);
		res.setPaymentReturnCode(saleResponse.getReturnCode());
		res.setPaymentReturnMessage(saleResponse.getReturnMessage());
		res.setPaymentReasonCode(saleResponse.getReasonCode());
		res.setPaymentReasonMessage(saleResponse.getReasonMessage());
		res.setPaymentProviderReturnCode(saleResponse.getProviderReturnCode());
		res.setPaymentProviderReturnMessage(saleResponse.getProviderReturnMessage());
		res.setPaymentCancelado(true);				
		//res.setRecPaymentId(null);
		//res.setRecPaymentAuthNow(null);
		//res.setRecPaymentStartDate(null);
		//res.setRecPaymentEndDate(null);
		//res.setRecPaymentNextRecurrency(null);
		//res.setRecPaymentRecurrencyDay(null);
		//res.setRecPaymentMonthsInterval(null);
		//res.setRecPaymentReasonCode(null);
		//res.setRecPaymentReasonMessage(null);
		//res.setRecPaymentDesabilitado(null);			
					
		return res;
	}
	
	//Cria uma transação de desativação ou reativação de pagamento recorrente
	//OPERACAO_TRANSACAO_5 = "CREDITO_RECORRENTE_DESATIVACAO";
	//OPERACAO_TRANSACAO_6 = "CREDITO_RECORRENTE_REATIVACAO";	
	public static Transacao gerarTransacao(Transacao transacaoPai, String operacao, boolean cancelar) {
		
		Transacao res = new Transacao();		
		
		res.setTransacaoPai(transacaoPai);
		res.setEmpresaAdquirente(transacaoPai.getEmpresaAdquirente());
		//res.setjSonIn(null);
		//res.setjSonOut(null);				
		res.setOperacao(operacao);	
		res.setDataHora(LocalDateTime.now());		
		//res.setNumPedidoVirtual(null);
		//res.setProvider(null);
		//res.setAmount(null);
		//res.setCreditCardBrand(null);
		//res.setCreditCardNumber(null);		
		//res.setStatus(null);		
		//res.setPaymentId(null);		
		//res.setPaymentAuthCode(null);
		//res.setPaymentProofOfSale(null);
		//res.setPaymentTid(null);
		//res.setPaymentReceivedDate(null);
		//res.setPaymentReturnCode(null);
		//res.setPaymentReturnMessage(null);		
		//res.setPaymentReasonCode(null);
		//res.setPaymentReasonMessage(null);
		//res.setPaymentProviderReturnCode(null);
		//res.setPaymentProviderReturnMessage(null);						
		//res.setPaymentCancelado(null);			
		res.setRecPaymentId(transacaoPai.getRecPaymentId());
		//res.setRecPaymentAuthNow(null);
		//res.setRecPaymentStartDate(null);
		//res.setRecPaymentEndDate(null);
		//res.setRecPaymentNextRecurrency(null);
		//res.setRecPaymentRecurrencyDay(null);
		//res.setRecPaymentMonthsInterval(null);
		//res.setRecPaymentReasonCode(null);
		//res.setRecPaymentReasonMessage(null);
		res.setRecPaymentDesabilitado(cancelar);				
					
		return res;
	}
	
	//Cria uma transação de alteração de dados de pagamento recorrente
	//OPERACAO_TRANSACAO_7 = "CREDITO_RECORRENTE_ALTERACAO_PAGAMENTO_RECORRENTE";
	public static Transacao gerarTransacao(Transacao transacaoPai, String operacao, 
			VendaCreditoHttp vendaCreditoHttp) {
		
		Transacao res = new Transacao();		
		
		Gson gson = new Gson();
		
		res.setTransacaoPai(transacaoPai);
		res.setEmpresaAdquirente(transacaoPai.getEmpresaAdquirente());
		res.setjSonIn(gson.toJson(vendaCreditoHttp));
		//res.setjSonOut(null);				
		res.setOperacao(operacao);	
		res.setDataHora(LocalDateTime.now());		
		//res.setNumPedidoVirtual(null);
		//res.setProvider(null);
		if (vendaCreditoHttp.getPedidoVirtualHttp() != null) {
			res.setAmount(vendaCreditoHttp.getPedidoVirtualHttp().getValor());
		}
		if (vendaCreditoHttp.getCartaoCreditoHttp() != null) {
			res.setCreditCardBrand(vendaCreditoHttp.getCartaoCreditoHttp().getBandeiraCartao());
			res.setCreditCardNumber(vendaCreditoHttp.getCartaoCreditoHttp().getNumCartao());
		}		
		//res.setStatus(null);		
		//res.setPaymentId(null);		
		//res.setPaymentAuthCode(null);
		//res.setPaymentProofOfSale(null);
		//res.setPaymentTid(null);
		//res.setPaymentReceivedDate(null);
		//res.setPaymentReturnCode(null);
		//res.setPaymentReturnMessage(null);		
		//res.setPaymentReasonCode(null);
		//res.setPaymentReasonMessage(null);
		//res.setPaymentProviderReturnCode(null);
		//res.setPaymentProviderReturnMessage(null);						
		//res.setPaymentCancelado(null);			
		res.setRecPaymentId(transacaoPai.getRecPaymentId());
		//res.setRecPaymentAuthNow(null);
		//res.setRecPaymentStartDate(null);
		//res.setRecPaymentEndDate(null);
		//res.setRecPaymentNextRecurrency(null);
		//res.setRecPaymentRecurrencyDay(null);
		//res.setRecPaymentMonthsInterval(null);
		//res.setRecPaymentReasonCode(null);
		//res.setRecPaymentReasonMessage(null);
		//res.setRecPaymentDesabilitado(null);				
					
		return res;
	}	
			
	//Cria uma transação de alteração de pagamento recorrente (exceto de dados de pagamento)
	//OPERACAO_TRANSACAO_8 = "CREDITO_RECORRENTE_ALTERACAO_DATA_FINAL_RECORRENCIA";
	//OPERACAO_TRANSACAO_9 = "CREDITO_RECORRENTE_ALTERACAO_DIA_RECORENCIA";
	//OPERACAO_TRANSACAO_10 = "CREDITO_RECORRENTE_ALTERACAO_VALOR_RECORRENCIA";
	//OPERACAO_TRANSACAO_11 = "CREDITO_RECORRENTE_ALTERACAO_DATA_RECORRENCIA";	
	//OPERACAO_TRANSACAO_12 = "CREDITO_RECORRENTE_ALTERACAO_INTERVALO_RECORRENCIA";	
	public static Transacao gerarTransacao(Transacao transacaoPai, String operacao,	String outraAlteracao) {
		
		Transacao res = new Transacao();		
		
		res.setTransacaoPai(transacaoPai);
		res.setEmpresaAdquirente(transacaoPai.getEmpresaAdquirente());
		//res.setjSonIn(null);
		//res.setjSonOut(null);				
		res.setOperacao(operacao);	
		res.setDataHora(LocalDateTime.now());		
		//res.setNumPedidoVirtual(null);
		//res.setProvider(null);
		if (operacao.equals(Transacao.OPERACAO_TRANSACAO_10)) {
			res.setAmount(Integer.valueOf(outraAlteracao));
		}
		//res.setCreditCardBrand(null);
		//res.setCreditCardNumber(null);		
		//res.setStatus(null);		
		//res.setPaymentId(null);		
		//res.setPaymentAuthCode(null);
		//res.setPaymentProofOfSale(null);
		//res.setPaymentTid(null);
		//res.setPaymentReceivedDate(null);
		//res.setPaymentReturnCode(null);
		//res.setPaymentReturnMessage(null);		
		//res.setPaymentReasonCode(null);
		//res.setPaymentReasonMessage(null);
		//res.setPaymentProviderReturnCode(null);
		//res.setPaymentProviderReturnMessage(null);						
		//res.setPaymentCancelado(null);			
		res.setRecPaymentId(transacaoPai.getRecPaymentId());
		//res.setRecPaymentAuthNow(null);
		//res.setRecPaymentStartDate(null);
		if (operacao.equals(Transacao.OPERACAO_TRANSACAO_8)) {
			res.setRecPaymentEndDate(outraAlteracao);
		}				
		if (operacao.equals(Transacao.OPERACAO_TRANSACAO_11)) {
			res.setRecPaymentNextRecurrency(outraAlteracao);	
		}
		if (operacao.equals(Transacao.OPERACAO_TRANSACAO_9)) {
			res.setRecPaymentRecurrencyDay(outraAlteracao);
		}
		if (operacao.equals(Transacao.OPERACAO_TRANSACAO_12)) {
			res.setRecPaymentMonthsInterval(outraAlteracao);
		}
		//res.setRecPaymentReasonCode(null);
		//res.setRecPaymentReasonMessage(null);
		//res.setRecPaymentDesabilitado(null);				
					
		return res;
	}	

}
