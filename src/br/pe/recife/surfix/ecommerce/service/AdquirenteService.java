package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pe.recife.surfix.ecommerce.dao.AdquirenteDAOIntf;
import br.pe.recife.surfix.ecommerce.entity.Adquirente;
import br.pe.recife.surfix.ecommerce.exception.InfraException;

@Transactional
@Service
public class AdquirenteService {
	
	@Autowired
	//@Qualifier("adquirenteDAO")
	private AdquirenteDAOIntf adquirenteDao;
		
	public List<Adquirente> listar() throws InfraException {
		
		return adquirenteDao.listar();
	}
	
	public Adquirente consultarPorId(Integer id) throws InfraException {

		return adquirenteDao.consultarPorId(id);		
	}
	
	public void salvar(Adquirente Adquirente) throws InfraException {

		adquirenteDao.salvar(Adquirente);		
	}
	
	public void excluir(Integer id) throws InfraException {

		adquirenteDao.excluir(id);
	}

}
