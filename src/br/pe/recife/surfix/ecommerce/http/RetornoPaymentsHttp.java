package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.Payment;

public class RetornoPaymentsHttp extends RetornoHttp {
	
	private Payment[] payments;
	
	public Payment[] getPayments() {
		return payments;
	}
	public void setPayments(Payment[] payments) {
		this.payments = payments;
	}	
}
