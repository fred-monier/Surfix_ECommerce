package br.pe.recife.surfix.ecommerce.http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CartaoCreditoHttp {
	
	private String bandeiraCartao; 
	private String numCartao; 
	private String mesAnoExpDate;
	private String nomeClienteCartao; 
	private String cvv;
	
	public String getBandeiraCartao() {
		return bandeiraCartao;
	}
	public void setBandeiraCartao(String bandeiraCartao) {
		this.bandeiraCartao = bandeiraCartao;
	}
	public String getNumCartao() {
		return numCartao;
	}
	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}
	public String getMesAnoExpDate() {
		return mesAnoExpDate;
	}
	public void setMesAnoExpDate(String mesAnoExpDate) {
		this.mesAnoExpDate = mesAnoExpDate;
	}
	public String getNomeClienteCartao() {
		return nomeClienteCartao;
	}
	public void setNomeClienteCartao(String nomeClienteCartao) {
		this.nomeClienteCartao = nomeClienteCartao;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	
}
