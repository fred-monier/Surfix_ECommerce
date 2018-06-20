package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.RecurrentSale;

public class RetornoRecurrentSaleHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private RecurrentSale recurrentSale;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public RecurrentSale getRecurrentSale() {
		return recurrentSale;
	}
	public void setRecurrentSale(RecurrentSale recurrentSale) {
		this.recurrentSale = recurrentSale;
	}
		
}
