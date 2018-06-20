package br.pe.recife.surfix.ecommerce.http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VendaCreditoAVistaHttp {
	
	private String idComercial;
	private PedidoVirtualHttp pedidoVirtualHttp;
	private CartaoCreditoHttp cartaoCreditoHttp;
	
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
	public String getIdComercial() {
		return idComercial;
	}
	public void setIdComercial(String idComercial) {
		this.idComercial = idComercial;
	}				

}
