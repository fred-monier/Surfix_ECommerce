package br.pe.recife.surfix.ecommerce.http;

import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import cieloecommerce.sdk.ecommerce.Payment;

public class RetornoPaymentHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private Payment payment;
	private TransacaoHttp transacao;
	
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
	public TransacaoHttp getTransacao() {
		return transacao;
	}
	public void setTransacao(TransacaoHttp transacao) {
		this.transacao = transacao;
	}	
		
}
