package br.pe.recife.surfix.ecommerce.http;

import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import cieloecommerce.sdk.ecommerce.SaleResponse;

public class RetornoSaleResponseHttp extends RetornoHttp {
	
	private SaleResponse saleResponse;
	private TransacaoHttp transacao;
	
	public SaleResponse getSaleResponse() {
		return saleResponse;
	}
	public void setSaleResponse(SaleResponse saleResponse) {
		this.saleResponse = saleResponse;
	}
	public TransacaoHttp getTransacao() {
		return transacao;
	}
	public void setTransacao(TransacaoHttp transacao) {
		this.transacao = transacao;
	}	
		
}
