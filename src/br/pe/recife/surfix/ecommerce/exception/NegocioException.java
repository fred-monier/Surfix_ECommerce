package br.pe.recife.surfix.ecommerce.exception;

public class NegocioException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String mensagem;
	
	public NegocioException() {
	}
	
	public NegocioException(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}