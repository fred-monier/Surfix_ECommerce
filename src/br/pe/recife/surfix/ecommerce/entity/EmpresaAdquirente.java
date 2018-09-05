package br.pe.recife.surfix.ecommerce.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="\"EMPRESA_ADQUIRENTE\"")
public class EmpresaAdquirente implements EntidadeBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="empresa_adquirente_sequence")
	@SequenceGenerator(name = "empresa_adquirente_sequence", sequenceName = "\"EMPRESA_ADQUIRENTE_ID_seq\"")
    @Column(name = "\"ID\"", nullable = false)
	private Integer id;
		
    @ManyToOne  
    @JoinColumn(name = "\"ID_EMPRESA\"", nullable = false)
    private Empresa empresa;
        
    @ManyToOne
    //@JoinColumn(name = "\"ID_ADQUIRENTE\"", referencedColumnName="ID", nullable = false)
    @JoinColumn(name = "\"ID_ADQUIRENTE\"", nullable = false)
    private Adquirente adquirente;
        
    @OneToMany(mappedBy = "empresaAdquirente", targetEntity = Transacao.class, 
			fetch =	FetchType.LAZY, cascade = CascadeType.ALL)    
    private Set<Transacao> transacoes;
    
    @Column(name = "\"MEC_ID\"", nullable = false)
    private String mecId;
    
    @Column(name = "\"MEC_KEY\"", nullable = false)
    private String mecKey;
    
    @Column(name = "\"MEC_ID_TESTE\"", nullable = false)
    private String mecIdTeste;
    
    @Column(name = "\"MEC_KEY_TESTE\"", nullable = false)
    private String mecKeyTeste;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getMecIdTeste() {
		return mecIdTeste;
	}

	public void setMecIdTeste(String mecIdTeste) {
		this.mecIdTeste = mecIdTeste;
	}

	public String getMecKeyTeste() {
		return mecKeyTeste;
	}

	public void setMecKeyTeste(String mecKeyTeste) {
		this.mecKeyTeste = mecKeyTeste;
	}      
		
}
