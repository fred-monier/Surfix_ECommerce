package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.SaleResponse;

public class RetornoSaleResponseHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private SaleResponse saleResponse;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public SaleResponse getSaleResponse() {
		return saleResponse;
	}
	public void setSaleResponse(SaleResponse saleResponse) {
		this.saleResponse = saleResponse;
	}
	
	

}
