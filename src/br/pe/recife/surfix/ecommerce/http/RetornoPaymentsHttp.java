package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.Payment;

public class RetornoPaymentsHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private Payment[] payments;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Payment[] getPayments() {
		return payments;
	}
	public void setPayments(Payment[] payments) {
		this.payments = payments;
	}
		
}
