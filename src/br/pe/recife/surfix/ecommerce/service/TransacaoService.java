package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pe.recife.surfix.ecommerce.dao.TransacaoDAOIntf;
import br.pe.recife.surfix.ecommerce.entity.Transacao;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

@Transactional
@Service
public class TransacaoService {
	
	@Autowired
	//@Qualifier("transacaoDAO")
	private TransacaoDAOIntf transacaoDao;
		
	public List<Transacao> listar() throws InfraException {
			
		return transacaoDao.listar();
	}
	
	public Transacao consultarPorId(Integer id) throws InfraException {

		return transacaoDao.consultarPorId(id);		
	}
	
	public void salvar(Transacao transacao) throws InfraException {

		transacaoDao.salvar(transacao);		
	}
	
	public void excluir(Integer id) throws InfraException {

		transacaoDao.excluir(id);		
	}
		
}
