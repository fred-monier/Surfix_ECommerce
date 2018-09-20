package br.pe.recife.surfix.ecommerce.dao;

import java.time.LocalDateTime;
import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Transacao;

public interface TransacaoDAOIntf {
	
	public List<Transacao> listarPais();
	public List<Transacao> listarPaisPorEmpresaENumPedidoVirtual(int idEmpresa, String numPedVirtual);
	public List<Transacao> listarPaisPorEmpAdqEDataHoraEOperacao(int idEmp, int idEmpAdq, LocalDateTime dataHoraInicio,
			LocalDateTime dataHoraFim, String operacao);
	public Transacao consultarPorId(int id);
	public void salvar(Transacao transacao);
	public void excluir(int id);

}
