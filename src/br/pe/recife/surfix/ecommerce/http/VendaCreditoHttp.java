package br.pe.recife.surfix.ecommerce.http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VendaCreditoHttp {
	
	private PedidoVirtualHttp pedidoVirtualHttp;
	private CartaoCreditoHttp cartaoCreditoHttp;
	private RecProgHttp recProgHttp;
	
	public PedidoVirtualHttp getPedidoVirtualHttp() {
		return pedidoVirtualHttp;
	}
	public void setPedidoVirtualHttp(PedidoVirtualHttp pedidoVirtualHttp) {
		this.pedidoVirtualHttp = pedidoVirtualHttp;
	}
	public CartaoCreditoHttp getCartaoCreditoHttp() {
		return cartaoCreditoHttp;
	}
	public void setCartaoCreditoHttp(CartaoCreditoHttp cartaoCreditoHttp) {
		this.cartaoCreditoHttp = cartaoCreditoHttp;
	}		
	public RecProgHttp getRecProgHttp() {
		return recProgHttp;
	}
	public void setRecProgHttp(RecProgHttp recProgHttp) {
		this.recProgHttp = recProgHttp;
	}	

}
