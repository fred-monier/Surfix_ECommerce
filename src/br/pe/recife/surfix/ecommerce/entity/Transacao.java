package br.pe.recife.surfix.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRANSACAO")
public class Transacao {
	
	@Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
	private int id;
	
	/*
	@ManyToOne
	   @JoinColumns({
	       @JoinColumn(
	           name = "ID_EMPRESA",
	           nullable = false),
	       @JoinColumn(
	           name = "ID_ADQUIRENTE",
	           nullable = false)
	   })
	*/
	private EmpresaAdquirente empresaAdquirente;
		
	@Column(name = "JSON_IN", nullable = false)
	private String jSonIn;
	
	@Column(name = "JSON_OUT", nullable = false)
	private String jSonOut;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public EmpresaAdquirente getEmpresaAdquirente() {
		return empresaAdquirente;
	}

	public void setEmpresaAdquirente(EmpresaAdquirente empresaAdquirente) {
		this.empresaAdquirente = empresaAdquirente;
	}

	public String getjSonIn() {
		return jSonIn;
	}

	public void setjSonIn(String jSonIn) {
		this.jSonIn = jSonIn;
	}

	public String getjSonOut() {
		return jSonOut;
	}

	public void setjSonOut(String jSonOut) {
		this.jSonOut = jSonOut;
	}
		
}
