package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.Sale;

public class RetornoSaleHttp extends RetornoHttp {
	
	private Sale sale;
	
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
		
}
