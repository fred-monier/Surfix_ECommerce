package br.pe.recife.surfix.ecommerce.http;

import br.pe.recife.surfix.ecommerce.entity.http.TransacaoHttp;

//Essa classe ter� uma irm� (RetornoTransacaoHttp). Definir qual fica.
public class RetornoManutRecHttp extends RetornoHttp {
	
	private TransacaoHttp transacao;
	
	public TransacaoHttp getTransacao() {
		return transacao;
	}
	public void setTransacao(TransacaoHttp transacao) {
		this.transacao = transacao;
	}

}
