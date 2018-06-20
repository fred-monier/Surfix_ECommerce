package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.Sale;

public class RetornoSaleHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private Sale sale;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
		
}
