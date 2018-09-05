package br.pe.recife.surfix.ecommerce.entity.http;

import java.time.LocalDateTime;

public class TransacaoHttp {
		
	private Integer id;
	private Integer idEmpresaAdquirente;
	private String operacao;
	private LocalDateTime dataHora;
	private String provider;
	private Integer amount;
	private String creditCardBrand;
	private String creditCardNumber;
	private String status;
	private String paymentId;
	private String paymentAuthCode;
	private String paymentProofOfSale;
	private String paymentTid;
	private String paymentReceivedDate;
	private String paymentReturnCode;
	private String paymentReturnMessage;
	private String recPaymentId;
	private Boolean recPaymentAuthNow;
	private String recPaymentStartDate;
	private String recPaymentEndDate;
	private String recPaymentNextRecurrency;
	private String recPaymentReasonCode;
	private String recPaymentReasonMessage;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdEmpresaAdquirente() {
		return idEmpresaAdquirente;
	}
	public void setIdEmpresaAdquirente(Integer idEmpresaAdquirente) {
		this.idEmpresaAdquirente = idEmpresaAdquirente;
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
