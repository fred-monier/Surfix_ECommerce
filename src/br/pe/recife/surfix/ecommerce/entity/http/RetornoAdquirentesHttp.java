package br.pe.recife.surfix.ecommerce.entity.http;

public class RetornoAdquirentesHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private AdquirenteHttp[] adquirentes;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public AdquirenteHttp[] getAdquirentes() {
		return adquirentes;
	}
	public void setAdquirentes(AdquirenteHttp[] adquirentes) {
		this.adquirentes = adquirentes;
	}	

}
