package br.pe.recife.surfix.ecommerce.http;

import cieloecommerce.sdk.ecommerce.RecurrentSale;

public class RetornoRecurrentSaleHttp extends RetornoHttp {
	
	private RecurrentSale recurrentSale;

	public RecurrentSale getRecurrentSale() {
		return recurrentSale;
	}
	public void setRecurrentSale(RecurrentSale recurrentSale) {
		this.recurrentSale = recurrentSale;
	}
		
}
