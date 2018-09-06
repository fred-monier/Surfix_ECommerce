package br.pe.recife.surfix.ecommerce.entity.http;

import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

public class RetornoAdquirentesHttp extends RetornoHttp {
	
	private AdquirenteHttp[] adquirentes;
	
	public AdquirenteHttp[] getAdquirentes() {
		return adquirentes;
	}
	public void setAdquirentes(AdquirenteHttp[] adquirentes) {
		this.adquirentes = adquirentes;
	}	

}
