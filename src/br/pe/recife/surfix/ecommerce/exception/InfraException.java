package br.pe.recife.surfix.ecommerce.exception;

public class InfraException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private Exception excecaoOriginal;
	private String mensagem;
	
	public InfraException(Exception excecaoOriginal) {
		this.excecaoOriginal = excecaoOriginal;
	}
	
	public InfraException(Exception excecaoOriginal, String mensagem) {
		this.excecaoOriginal = excecaoOriginal;
		this.mensagem = mensagem;
	}

	public Exception getExcecaoOriginal() {
		return excecaoOriginal;
	}

	public void setExcecaoOriginal(Exception excecaoOriginal) {
		this.excecaoOriginal = excecaoOriginal;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
		
}
