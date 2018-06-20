package br.pe.recife.surfix.ecommerce.http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PedidoVirtualHttp {
	
	private String numPedidoVirtual;
	private String descricaoVenda;
	private int valor;
	
	public String getNumPedidoVirtual() {
		return numPedidoVirtual;
	}
	public void setNumPedidoVirtual(String numPedidoVirtual) {
		this.numPedidoVirtual = numPedidoVirtual;
	}
	public String getDescricaoVenda() {
		return descricaoVenda;
	}
	public void setDescricaoVenda(String descricaoVenda) {
		this.descricaoVenda = descricaoVenda;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	

}
