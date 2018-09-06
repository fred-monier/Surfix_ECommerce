package br.pe.recife.surfix.ecommerce.entity.http;

import br.pe.recife.surfix.ecommerce.http.RetornoHttp;

public class RetornoEmpresasAdquirentesHttp extends RetornoHttp {
	
	private EmpresaAdquirenteHttp[] empresasAdquirentes;
	
	public EmpresaAdquirenteHttp[] getEmpresasAdquirentes() {
		return empresasAdquirentes;
	}
	public void setEmpresasAdquirentes(EmpresaAdquirenteHttp[] empresasAdquirentes) {
		this.empresasAdquirentes = empresasAdquirentes;
	}

}
