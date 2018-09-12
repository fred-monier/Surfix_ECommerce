package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Transacao;

public interface TransacaoDAOIntf {
	
	public List<Transacao> listar();
	public Transacao consultarPorId(int id);
	public void salvar(Transacao transacao);
	public void excluir(int id);

}
