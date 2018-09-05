package br.pe.recife.surfix.ecommerce.entity.http;

public class RetornoTransacoesHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private TransacaoHttp[] transacoes;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public TransacaoHttp[] getTransacoes() {
		return transacoes;
	}
	public void setTransacoes(TransacaoHttp[] transacoes) {
		this.transacoes = transacoes;
	}

}
