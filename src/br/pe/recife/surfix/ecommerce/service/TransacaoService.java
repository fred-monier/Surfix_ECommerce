package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pe.recife.surfix.ecommerce.dao.TransacaoDAOIntf;
import br.pe.recife.surfix.ecommerce.entity.Transacao;

@Transactional
@Service
public class TransacaoService {
	
	@Autowired
	//@Qualifier("transacaoDAO")
	private TransacaoDAOIntf transacaoDao;
		
	public List<Transacao> listar() {
			
		return transacaoDao.listar();
	}
	
	public List<Transacao> listarPaisPorNumPedidoVirtual(String numPedVirtual) {
		return transacaoDao.listarPaisPorNumPedidoVirtual(numPedVirtual);
	}
	
	public Transacao consultarPorId(Integer id) {

		return transacaoDao.consultarPorId(id);		
	}
	
	public void salvar(Transacao transacao) {

		transacaoDao.salvar(transacao);		
	}
	
	public void excluir(Integer id) {

		transacaoDao.excluir(id);		
	}
		
}
