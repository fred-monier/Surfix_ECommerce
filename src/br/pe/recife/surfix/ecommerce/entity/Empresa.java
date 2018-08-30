package br.pe.recife.surfix.ecommerce.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="\"EMPRESA\"")
public class Empresa implements EntidadeBase {
	
	@Id
    @GeneratedValue
    @Column(name = "\"ID\"", nullable = false)
	private Integer id;
			
	@OneToMany(mappedBy = "empresa", targetEntity = EmpresaAdquirente.class, 
			fetch =	FetchType.LAZY, cascade = CascadeType.ALL)	
    private Set<EmpresaAdquirente> empresaAdquirentes;
	
	@Column(name = "\"CNPJ\"", nullable = false)
	private String cnpj;
	
	@Column(name = "\"NOME\"", nullable = false)
	private String nome;
	
	@Column(name = "\"USUARIO\"", nullable = false)
	private String usuario;
	
	@Column(name = "\"SENHA\"", nullable = false)
	private String senha;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<EmpresaAdquirente> getEmpresaAdquirentes() {
		return empresaAdquirentes;
	}

	public void setEmpresaAdquirentes(Set<EmpresaAdquirente> empresaAdquirentes) {
		this.empresaAdquirentes = empresaAdquirentes;
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
	
	

}
