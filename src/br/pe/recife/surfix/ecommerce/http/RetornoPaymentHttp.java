package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.Payment;

public class RetornoPaymentHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private Payment payment;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
		
}
