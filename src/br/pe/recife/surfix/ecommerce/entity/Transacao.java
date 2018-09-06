package br.pe.recife.surfix.ecommerce.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.google.gson.Gson;

import br.pe.recife.surfix.ecommerce.http.VendaCreditoHttp;
import cieloecommerce.sdk.ecommerce.Payment;
import cieloecommerce.sdk.ecommerce.SaleResponse;

@Entity
@Table(name="\"TRANSACAO\"")
public class Transacao implements EntidadeBase {	
	
	private static final Integer PAYMENT_STATUS_CANCELADO = 10;	
	private static final Integer REC_PAYMENT_STATUS_DISABILITADO = 3;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="transacao_sequence")
	@SequenceGenerator(name = "transacao_sequence", sequenceName = "\"TRANSACAO_ID_seq\"")	
    @Column(name = "\"ID\"", nullable = false)
	private Integer id;
			
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
	
	//TODO Incluir mais atributos
	public static Transacao gerarTransacao(EmpresaAdquirente empresaAdquirente,
			SaleResponse saleResponse, String operacao, String idPayment, boolean cancelar) {
		
		Transacao res = new Transacao();
		
		Gson gson = new Gson();
		
		res.setEmpresaAdquirente(empresaAdquirente);
//		res.setjSonIn(gson.toJson(null));
		res.setjSonOut(gson.toJson(saleResponse));				
		res.setOperacao(operacao);	
		res.setDataHora(LocalDateTime.now());		
		res.setNumPedidoVirtual(saleResponse.getMerchantOrderId());
//		res.setProvider(null);
//		res.setAmount(null);
//		res.setCreditCardBrand(null);
//		res.setCreditCardNumber(null);	
		res.setStatus(saleResponse.getStatus());		
		res.setPaymentId(idPayment);		
//		res.setPaymentAuthCode(null);
//		res.setPaymentProofOfSale(null);
//		res.setPaymentTid(null);
//		res.setPaymentReceivedDate(null);
		res.setPaymentReturnCode(saleResponse.getReturnCode());
		res.setPaymentReturnMessage(saleResponse.getReturnMessage());
		res.setPaymentCancelado(cancelar);				
//		res.setRecPaymentId(payment.getRecurrentPayment().getRecurrentPaymentId());
//		res.setRecPaymentAuthNow(payment.getRecurrentPayment().isAuthorizeNow());
//		res.setRecPaymentStartDate(payment.getRecurrentPayment().getStartDate());
//		res.setRecPaymentEndDate(payment.getRecurrentPayment().getEndDate());
//		res.setRecPaymentNextRecurrency(payment.getRecurrentPayment().getNextRecurrency());
//		res.setRecPaymentReasonCode(payment.getRecurrentPayment().getReasonCode() + "");
//		res.setRecPaymentReasonMessage(payment.getRecurrentPayment().getReasonMessage());
//		res.setRecPaymentDesabilitado(null);			
		
		//Faltam do SaleResponse, para pôr na TRANSACAO Entity
		//ReasonCode
		//ReasonMessage
		//ProviderReturnCode
		//ProviderReturnMessage
					
		return res;
	}
	
	public static Transacao gerarTransacao(EmpresaAdquirente empresaAdquirente, String operacao,
			String idRecPayment, boolean cancelar) {
		
		Transacao res = new Transacao();		
		
		res.setEmpresaAdquirente(empresaAdquirente);
//		res.setjSonIn(null);
//		res.setjSonOut(null);				
		res.setOperacao(operacao);	
		res.setDataHora(LocalDateTime.now());		
//		res.setNumPedidoVirtual(null);
//		res.setProvider(null);
//		res.setAmount(null);
//		res.setCreditCardBrand(null);
//		res.setCreditCardNumber(null);		
//		res.setStatus(null);		
//		res.setPaymentId(null);		
//		res.setPaymentAuthCode(null);
//		res.setPaymentProofOfSale(null);
//		res.setPaymentTid(null);
//		res.setPaymentReceivedDate(null);
//		res.setPaymentReturnCode(null);
//		res.setPaymentReturnMessage(null);
//		res.setPaymentCancelado(null);			
		res.setRecPaymentId(idRecPayment);
//		res.setRecPaymentAuthNow(null);
//		res.setRecPaymentStartDate(null);
//		res.setRecPaymentEndDate(null);
//		res.setRecPaymentNextRecurrency(null);
//		res.setRecPaymentReasonCode(null);
//		res.setRecPaymentReasonMessage(null);
		res.setRecPaymentDesabilitado(cancelar);				
					
		return res;
	}
	
	public static Transacao gerarTransacao(VendaCreditoHttp vendaCreditoHttp, 
			EmpresaAdquirente empresaAdquirente, Payment payment, String operacao) {
	
		Transacao res = new Transacao();
		
		Gson gson = new Gson();
		
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
		res.setPaymentCancelado((Transacao.PAYMENT_STATUS_CANCELADO.
				equals(payment.getStatus()) ? true : null));
		
		if (payment.getRecurrentPayment() != null) {				
			res.setRecPaymentId(payment.getRecurrentPayment().getRecurrentPaymentId());
			res.setRecPaymentAuthNow(payment.getRecurrentPayment().isAuthorizeNow());
			res.setRecPaymentStartDate(payment.getRecurrentPayment().getStartDate());
			res.setRecPaymentEndDate(payment.getRecurrentPayment().getEndDate());
			res.setRecPaymentNextRecurrency(payment.getRecurrentPayment().getNextRecurrency());
			res.setRecPaymentReasonCode(payment.getRecurrentPayment().getReasonCode() + "");
			res.setRecPaymentReasonMessage(payment.getRecurrentPayment().getReasonMessage());
			res.setRecPaymentDesabilitado((Transacao.REC_PAYMENT_STATUS_DISABILITADO.
					equals(payment.getRecurrentPayment().getStatus()) ? true : null));			
		}
								
		return res;		
		
	}						
}
