package br.pe.recife.surfix.ecommerce.entity.http;

import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

public class RetornoEmpresasHttp extends RetornoHttp {

	private EmpresaHttp[] empresas;
	
	public EmpresaHttp[] getEmpresas() {
		return empresas;
	}
	public void setEmpresas(EmpresaHttp[] empresas) {
		this.empresas = empresas;
	}		
	
}
