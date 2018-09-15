package br.pe.recife.surfix.ecommerce.http;

import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;
import cieloecommerce.sdk.ecommerce.RecurrentSale;
import cieloecommerce.sdk.ecommerce.Sale;

public class RetornoGeneralSaleHttp extends RetornoHttp {
	
	private Sale sale;
	private RecurrentSale recurrentSale;
	private TransacaoHttp transacao;
	
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public RecurrentSale getRecurrentSale() {
		return recurrentSale;
	}
	public void setRecurrentSale(RecurrentSale recurrentSale) {
		this.recurrentSale = recurrentSale;
	}
	public TransacaoHttp getTransacao() {
		return transacao;
	}
	public void setTransacao(TransacaoHttp transacao) {
		this.transacao = transacao;
	}
		
}
