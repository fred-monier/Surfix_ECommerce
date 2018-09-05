package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import br.pe.recife.surfix.ecommerce.dao.EntidadeBaseDAO;
import br.pe.recife.surfix.ecommerce.entity.Transacao;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

public class TransacaoService {
	
	private static TransacaoService instancia;
	
	private TransacaoService() {		
	}
	
	public static TransacaoService getInstancia() {
		
		if (instancia == null) {
			instancia = new TransacaoService();			
		}
		
		return instancia;
		
	}
	
	public List<Transacao> listar() throws InfraException {
		
		EntidadeBaseDAO<Transacao> dao = new EntidadeBaseDAO<Transacao>();
		
		return dao.listar(Transacao.class);
	}
	
	public Transacao consultarPorId(Integer id) throws InfraException {
		
		EntidadeBaseDAO<Transacao> dao = new EntidadeBaseDAO<Transacao>();
		
		return dao.consultarPorId(Transacao.class, id);
		
	}
	
	public void salvar(Transacao empresa) throws InfraException {
		
		EntidadeBaseDAO<Transacao> dao = new EntidadeBaseDAO<Transacao>();
		
		dao.salvar(empresa);
		
	}
	
	public void excluir(Integer id) throws InfraException {
		
		EntidadeBaseDAO<Transacao> dao = new EntidadeBaseDAO<Transacao>();
		
		dao.excluir(Transacao.class, id);
		
	}
		

}
