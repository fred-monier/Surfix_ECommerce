package br.pe.recife.surfix.ecommerce.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="\"EMPRESA\"")
public class Empresa implements EntidadeBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="empresa_sequence")
	@SequenceGenerator(name = "empresa_sequence", sequenceName = "\"EMPRESA_ID_SEQ\"") //Originalmente em PostgreSql: \"EMPRESA_ID_seq\"
    @Column(name = "\"ID\"", nullable = false)
	private Integer id;
			
	@OneToMany(mappedBy = "empresa", targetEntity = EmpresaAdquirente.class, 
			fetch =	FetchType.LAZY, cascade = CascadeType.ALL)	
    private Set<EmpresaAdquirente> empresaAdquirentes;
	
	@Column(name = "\"CPF_CNPJ\"", nullable = false)
	private String cpfCnpj;
	
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

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
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
