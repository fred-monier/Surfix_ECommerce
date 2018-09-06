package br.pe.recife.surfix.ecommerce.entity.http;

import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Empresa;

public class EmpresaHttp {
	
	private Integer id;
	private String cnpj;
	private String nome;
	private String usuario;
	private String senha;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public static EmpresaHttp[] gerarArrayEmpresasHttp(List<Empresa> empresas) {
		
		EmpresaHttp[] empresasHttp = new EmpresaHttp[empresas.size()];			
		
		for (int i=0; i < empresas.size(); i++) {
			
			Empresa empresa = empresas.get(i);
			
			EmpresaHttp empresaHttp = new EmpresaHttp();
			empresaHttp.setId(empresa.getId());
			empresaHttp.setNome(empresa.getNome());
			empresaHttp.setCnpj(empresa.getCnpj());
			empresaHttp.setUsuario(empresa.getUsuario());
			empresaHttp.setSenha(empresa.getSenha());
			
			empresasHttp[i] = empresaHttp;
		}
		
		return empresasHttp;
	}
	
}
