package br.pe.recife.surfix.ecommerce.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPRESA_ADQUIRENTE")
public class EmpresaAdquirente {
	
	@Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
	private int id;
	
	/*
    @ManyToOne
    @JoinColumn(name = "ID_EMPRESA",  referencedColumnName="ID", nullable = false)
    */
    private Empresa empresa;
    
    /*
    @ManyToOne
    @JoinColumn(name = "ID_ADQUIRENTE", referencedColumnName="ID", nullable = false)
    */
    private Adquirente adquirente;
    
    /*
    @OneToMany(mappedBy = "empresaAdquirente", targetEntity = Transacao.class, 
			fetch =	FetchType.LAZY, cascade = CascadeType.ALL)
    */
    private Set<Transacao> transacoes;
    
    @Column(name = "MEC_ID", nullable = false)
    private String mecId;
    
    @Column(name = "MEC_KEY", nullable = false)
    private String mecKey;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Adquirente getAdquirente() {
		return adquirente;
	}

	public void setAdquirente(Adquirente adquirente) {
		this.adquirente = adquirente;
	}

	public Set<Transacao> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(Set<Transacao> transacoes) {
		this.transacoes = transacoes;
	}

	public String getMecId() {
		return mecId;
	}

	public void setMecId(String mecId) {
		this.mecId = mecId;
	}

	public String getMecKey() {
		return mecKey;
	}

	public void setMecKey(String mecKey) {
		this.mecKey = mecKey;
	}        

}
