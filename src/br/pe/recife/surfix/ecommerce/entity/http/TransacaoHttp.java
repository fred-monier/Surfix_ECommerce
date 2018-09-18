package br.pe.recife.surfix.ecommerce.entity.http;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Transacao;

public class TransacaoHttp {
		
	private Integer id;
	private Integer idPai;
	private Integer idEmpresaAdquirente;
	private String operacao;
	private LocalDateTime dataHora;
	private String provider;
	private Integer amount;
	private String creditCardBrand;
	private String creditCardNumber;
	private String status;
	private String paymentId;
	private String paymentAuthCode;
	private String paymentProofOfSale;
	private String paymentTid;
	private String paymentReceivedDate;
	private String paymentReturnCode;
	private String paymentReturnMessage;
	private String paymentReasonCode;
	private String paymentReasonMessage;
	private String paymentProviderReturnCode;
	private String paymentProviderReturnMessage;
	private Boolean paymentCancelado;
	private String recPaymentId;
	private Boolean recPaymentAuthNow;
	private String recPaymentStartDate;
	private String recPaymentEndDate;
	private String recPaymentNextRecurrency;	
	private String recPaymentRecurrencyDay;
	private String recPaymentMonthsInterval;	
	private String recPaymentReasonCode;
	private String recPaymentReasonMessage;
	private Boolean recPaymentDisabilitado;
	private String numPedidoVirtual;
	
	private TransacaoHttp[] transacoesFilhas;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public Integer getIdPai() {
		return idPai;
	}
	public void setIdPai(Integer idPai) {
		this.idPai = idPai;
	}
	public Integer getIdEmpresaAdquirente() {
		return idEmpresaAdquirente;
	}
	public void setIdEmpresaAdquirente(Integer idEmpresaAdquirente) {
		this.idEmpresaAdquirente = idEmpresaAdquirente;
	}
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getCreditCardBrand() {
		return creditCardBrand;
	}
	public void setCreditCardBrand(String creditCardBrand) {
		this.creditCardBrand = creditCardBrand;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getPaymentAuthCode() {
		return paymentAuthCode;
	}
	public void setPaymentAuthCode(String paymentAuthCode) {
		this.paymentAuthCode = paymentAuthCode;
	}
	public String getPaymentProofOfSale() {
		return paymentProofOfSale;
	}
	public void setPaymentProofOfSale(String paymentProofOfSale) {
		this.paymentProofOfSale = paymentProofOfSale;
	}
	public String getPaymentTid() {
		return paymentTid;
	}
	public void setPaymentTid(String paymentTid) {
		this.paymentTid = paymentTid;
	}
	public String getPaymentReceivedDate() {
		return paymentReceivedDate;
	}
	public void setPaymentReceivedDate(String paymentReceivedDate) {
		this.paymentReceivedDate = paymentReceivedDate;
	}
	public String getPaymentReturnCode() {
		return paymentReturnCode;
	}
	public void setPaymentReturnCode(String paymentReturnCode) {
		this.paymentReturnCode = paymentReturnCode;
	}
	public String getPaymentReturnMessage() {
		return paymentReturnMessage;
	}
	public void setPaymentReturnMessage(String paymentReturnMessage) {
		this.paymentReturnMessage = paymentReturnMessage;
	}
	public String getPaymentReasonCode() {
		return paymentReasonCode;
	}
	public void setPaymentReasonCode(String paymentReasonCode) {
		this.paymentReasonCode = paymentReasonCode;
	}
	public String getPaymentReasonMessage() {
		return paymentReasonMessage;
	}
	public void setPaymentReasonMessage(String paymentReasonMessage) {
		this.paymentReasonMessage = paymentReasonMessage;
	}
	public String getPaymentProviderReturnCode() {
		return paymentProviderReturnCode;
	}
	public void setPaymentProviderReturnCode(String paymentProviderReturnCode) {
		this.paymentProviderReturnCode = paymentProviderReturnCode;
	}
	public String getPaymentProviderReturnMessage() {
		return paymentProviderReturnMessage;
	}
	public void setPaymentProviderReturnMessage(String paymentProviderReturnMessage) {
		this.paymentProviderReturnMessage = paymentProviderReturnMessage;
	}
	public Boolean getPaymentCancelado() {
		return paymentCancelado;
	}
	public void setPaymentCancelado(Boolean paymentCancelado) {
		this.paymentCancelado = paymentCancelado;
	}
	public String getRecPaymentId() {
		return recPaymentId;
	}
	public void setRecPaymentId(String recPaymentId) {
		this.recPaymentId = recPaymentId;
	}
	public Boolean getRecPaymentAuthNow() {
		return recPaymentAuthNow;
	}
	public void setRecPaymentAuthNow(Boolean recPaymentAuthNow) {
		this.recPaymentAuthNow = recPaymentAuthNow;
	}
	public String getRecPaymentStartDate() {
		return recPaymentStartDate;
	}
	public void setRecPaymentStartDate(String recPaymentStartDate) {
		this.recPaymentStartDate = recPaymentStartDate;
	}
	public String getRecPaymentEndDate() {
		return recPaymentEndDate;
	}
	public void setRecPaymentEndDate(String recPaymentEndDate) {
		this.recPaymentEndDate = recPaymentEndDate;
	}
	public String getRecPaymentNextRecurrency() {
		return recPaymentNextRecurrency;
	}
	public void setRecPaymentNextRecurrency(String recPaymentNextRecurrency) {
		this.recPaymentNextRecurrency = recPaymentNextRecurrency;
	}			
	public String getRecPaymentRecurrencyDay() {
		return recPaymentRecurrencyDay;
	}
	public void setRecPaymentRecurrencyDay(String recPaymentRecurrencyDay) {
		this.recPaymentRecurrencyDay = recPaymentRecurrencyDay;
	}
	public String getRecPaymentMonthsInterval() {
		return recPaymentMonthsInterval;
	}
	public void setRecPaymentMonthsInterval(String recPaymentMonthsInterval) {
		this.recPaymentMonthsInterval = recPaymentMonthsInterval;
	}
	public String getRecPaymentReasonCode() {
		return recPaymentReasonCode;
	}
	public void setRecPaymentReasonCode(String recPaymentReasonCode) {
		this.recPaymentReasonCode = recPaymentReasonCode;
	}
	public String getRecPaymentReasonMessage() {
		return recPaymentReasonMessage;
	}
	public void setRecPaymentReasonMessage(String recPaymentReasonMessage) {
		this.recPaymentReasonMessage = recPaymentReasonMessage;
	}	
	public Boolean getRecPaymentDisabilitado() {
		return recPaymentDisabilitado;
	}
	public void setRecPaymentDisabilitado(Boolean recPaymentDisabilitado) {
		this.recPaymentDisabilitado = recPaymentDisabilitado;
	}
	public String getNumPedidoVirtual() {
		return numPedidoVirtual;
	}
	public void setNumPedidoVirtual(String numPedidoVirtual) {
		this.numPedidoVirtual = numPedidoVirtual;
	}		
	public TransacaoHttp[] getTransacoesFilhas() {
		return transacoesFilhas;
	}
	public void setTransacoesFilhas(TransacaoHttp[] transacoesFilhas) {
		this.transacoesFilhas = transacoesFilhas;
	}
	public static TransacaoHttp gerarTransacaoHttp(Transacao transacao) {
		
		TransacaoHttp transacaoHttp = new TransacaoHttp();
		
		if (transacao != null) {
			transacaoHttp.setId(transacao.getId());
			if (transacao.getTransacaoPai() != null) {
				transacaoHttp.setIdPai(transacao.getTransacaoPai().getId());
			}
			transacaoHttp.setIdEmpresaAdquirente(transacao.getEmpresaAdquirente().getId());
			transacaoHttp.setOperacao(transacao.getOperacao());
			transacaoHttp.setDataHora(transacao.getDataHora());
			transacaoHttp.setNumPedidoVirtual(transacao.getNumPedidoVirtual());
			transacaoHttp.setProvider(transacao.getProvider());
			transacaoHttp.setAmount(transacao.getAmount());
			transacaoHttp.setCreditCardBrand(transacao.getCreditCardBrand());
			transacaoHttp.setCreditCardNumber(transacao.getCreditCardNumber());
			transacaoHttp.setStatus(transacao.getStatus());
			transacaoHttp.setPaymentId(transacao.getPaymentId());
			transacaoHttp.setPaymentAuthCode(transacao.getPaymentAuthCode());
			transacaoHttp.setPaymentProofOfSale(transacao.getPaymentProofOfSale());
			transacaoHttp.setPaymentTid(transacao.getPaymentTid());
			transacaoHttp.setPaymentReceivedDate(transacao.getPaymentReceivedDate());
			transacaoHttp.setPaymentReturnCode(transacao.getPaymentReturnCode());
			transacaoHttp.setPaymentReturnMessage(transacao.getPaymentReturnMessage());			
			transacaoHttp.setPaymentReasonCode(transacao.getPaymentReasonCode());
			transacaoHttp.setPaymentReasonMessage(transacao.getPaymentReasonMessage());
			transacaoHttp.setPaymentProviderReturnCode(transacao.getPaymentProviderReturnCode());
			transacaoHttp.setPaymentProviderReturnMessage(transacao.getPaymentProviderReturnMessage());						
			transacaoHttp.setPaymentCancelado(transacao.getPaymentCancelado());
			transacaoHttp.setRecPaymentId(transacao.getRecPaymentId());
			transacaoHttp.setRecPaymentAuthNow(transacao.getRecPaymentAuthNow());
			transacaoHttp.setRecPaymentStartDate(transacao.getRecPaymentStartDate());
			transacaoHttp.setRecPaymentEndDate(transacao.getRecPaymentEndDate());
			transacaoHttp.setRecPaymentNextRecurrency(transacao.getRecPaymentNextRecurrency());
			transacaoHttp.setRecPaymentRecurrencyDay(transacao.getRecPaymentRecurrencyDay());
			transacaoHttp.setRecPaymentMonthsInterval(transacao.getRecPaymentMonthsInterval());
			transacaoHttp.setRecPaymentReasonCode(transacao.getRecPaymentReasonCode());
			transacaoHttp.setRecPaymentReasonMessage(transacao.getRecPaymentReasonMessage());
			transacaoHttp.setRecPaymentDisabilitado(transacao.getRecPaymentDesabilitado());
			
			if (transacao.getTransacaoPai() == null) {
				
				TransacaoHttp[] transacoesFilhas;
				
				if (transacao.getTransacoesFilhas() != null) {
				
					transacoesFilhas = new TransacaoHttp[transacao.getTransacoesFilhas().size()];
					
					int i =0;
					for (Iterator<Transacao> iterator = transacao.getTransacoesFilhas().iterator(); iterator.hasNext();) {
						
						Transacao transacaoFilha = iterator.next();					
						TransacaoHttp transacaoFilhaHttp = TransacaoHttp.gerarTransacaoHttp(transacaoFilha);
						
						transacoesFilhas[i++] = transacaoFilhaHttp;
					}
				} else {
					
					transacoesFilhas = new TransacaoHttp[0];
				}
				
				transacaoHttp.setTransacoesFilhas(transacoesFilhas);				
			}
		}
		
		return transacaoHttp;
	}	
	
	public static TransacaoHttp[] gerarArrayTransacoesHttp(List<Transacao> transacoes) {
		
		TransacaoHttp[] transacoesHttp;
		
		if (transacoes != null) {
			
			transacoesHttp = new TransacaoHttp[transacoes.size()];			
			
			for (int i=0; i < transacoes.size(); i++) {
				
				Transacao transacao = transacoes.get(i);
							
				TransacaoHttp transacaoHttp = TransacaoHttp.gerarTransacaoHttp(transacao);
													
				transacoesHttp[i] = transacaoHttp;
			}
			
		} else {
			
			transacoesHttp = new TransacaoHttp[0];	
		}		
		
		return transacoesHttp;
	}
	
}
