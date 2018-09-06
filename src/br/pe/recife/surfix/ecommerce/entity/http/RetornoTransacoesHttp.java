package br.pe.recife.surfix.ecommerce.entity.http;

import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

public class RetornoTransacoesHttp extends RetornoHttp {
	
	private TransacaoHttp[] transacoes;
	
	public TransacaoHttp[] getTransacoes() {
		return transacoes;
	}
	public void setTransacoes(TransacaoHttp[] transacoes) {
		this.transacoes = transacoes;
	}

}
