package br.pe.recife.surfix.ecommerce.entity.http;

import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Adquirente;

public class AdquirenteHttp {
	
	private Integer id;
	private String nome;	
	private String descricao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
		
	public static AdquirenteHttp[] gerarArranjoAdquirentesHttp(List<Adquirente> adquirentes) {
		
		AdquirenteHttp[] adquirentesHttp = new AdquirenteHttp[adquirentes.size()];			
		
		for (int i=0; i < adquirentes.size(); i++) {
			
			Adquirente adquirente = adquirentes.get(i);
			
			AdquirenteHttp adquirenteHttp = new AdquirenteHttp();
			adquirenteHttp.setId(adquirente.getId());
			adquirenteHttp.setNome(adquirente.getNome());
			adquirenteHttp.setDescricao(adquirente.getDescricao());
			
			adquirentesHttp[i] = adquirenteHttp;
		}
		
		return adquirentesHttp;
	}
	
	

}
