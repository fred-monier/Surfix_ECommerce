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
@Table(name="\"ADQUIRENTE\"")
public class Adquirente implements EntidadeBase {
	
	@Id
    @GeneratedValue
    @Column(name = "\"ID\"", nullable = false)
	private Integer id;
		
	@OneToMany(mappedBy = "adquirente", targetEntity = EmpresaAdquirente.class, 
			fetch =	FetchType.LAZY, cascade = CascadeType.ALL)	
    private Set<EmpresaAdquirente> empresaAdquirentes;
	
	@Column(name = "\"NOME\"", nullable = false)
	private String nome;
	
	@Column(name = "\"DESCRICAO\"", nullable = true)
	private String descricao;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
