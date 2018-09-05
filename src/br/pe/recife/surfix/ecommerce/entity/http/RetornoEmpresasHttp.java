package br.pe.recife.surfix.ecommerce.entity.http;

public class RetornoEmpresasHttp {

public static final String SUCESSO = "SUCESSO";
	
	private String resultado;
	private EmpresaHttp[] empresas;
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public EmpresaHttp[] getEmpresas() {
		return empresas;
	}
	public void setEmpresas(EmpresaHttp[] empresas) {
		this.empresas = empresas;
	}
	
	
	
}
