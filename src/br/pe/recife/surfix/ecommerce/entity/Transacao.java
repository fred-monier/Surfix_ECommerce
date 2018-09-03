package br.pe.recife.surfix.ecommerce.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="\"TRANSACAO\"")
public class Transacao implements EntidadeBase {
	
	@Id
    @GeneratedValue
    @Column(name = "\"ID\"", nullable = false)
	private Integer id;
			
	@ManyToOne
    //@JoinColumn(name = "\"ID_EMPRESA_ADQUIRENTE\"",  referencedColumnName="ID", nullable = false) 
	@JoinColumn(name = "\"ID_EMPRESA_ADQUIRENTE\"", nullable = false)
	private EmpresaAdquirente empresaAdquirente;
		
	@Column(name = "\"JSON_IN\"", nullable = false)
	private String jSonIn;
	
	@Column(name = "\"JSON_OUT\"", nullable = false)
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
				
}
