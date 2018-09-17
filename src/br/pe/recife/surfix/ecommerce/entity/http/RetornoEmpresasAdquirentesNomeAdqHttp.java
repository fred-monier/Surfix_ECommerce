package br.pe.recife.surfix.ecommerce.entity.http;

import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

public class RetornoEmpresasAdquirentesNomeAdqHttp extends RetornoHttp {
	
	private EmpresaAdquirenteNomeAdqHttp[] empresasAdquirentesNomeAdqHttp;

	public EmpresaAdquirenteNomeAdqHttp[] getEmpresasAdquirentesNomeAdqHttp() {
		return empresasAdquirentesNomeAdqHttp;
	}

	public void setEmpresasAdquirentesNomeAdqHttp(EmpresaAdquirenteNomeAdqHttp[] empresasAdquirentesNomeAdqHttp) {
		this.empresasAdquirentesNomeAdqHttp = empresasAdquirentesNomeAdqHttp;
	}
	

}
