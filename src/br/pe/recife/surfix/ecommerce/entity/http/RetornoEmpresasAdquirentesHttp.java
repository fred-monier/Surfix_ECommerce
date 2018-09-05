package br.pe.recife.surfix.ecommerce.entity.http;

public class RetornoEmpresasAdquirentesHttp {
	
	public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private EmpresaAdquirenteHttp[] empresasAdquirentes;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public EmpresaAdquirenteHttp[] getEmpresasAdquirentes() {
		return empresasAdquirentes;
	}
	public void setEmpresasAdquirentes(EmpresaAdquirenteHttp[] empresasAdquirentes) {
		this.empresasAdquirentes = empresasAdquirentes;
	}
	

}
