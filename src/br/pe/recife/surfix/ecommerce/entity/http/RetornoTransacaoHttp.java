package br.pe.recife.surfix.ecommerce.entity.http;

import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

public class RetornoTransacaoHttp extends RetornoHttp {
	
	private TransacaoHttp transacao;
	
	public TransacaoHttp getTransacao() {
		return transacao;
	}
	public void setTransacao(TransacaoHttp transacao) {
		this.transacao = transacao;
	}

}
