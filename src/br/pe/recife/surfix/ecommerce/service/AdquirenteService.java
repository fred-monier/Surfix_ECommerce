package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pe.recife.surfix.ecommerce.dao.AdquirenteDAOIntf;
import br.pe.recife.surfix.ecommerce.entity.Adquirente;

@Transactional
@Service
public class AdquirenteService {
	
	@Autowired
	//@Qualifier("adquirenteDAO")
	private AdquirenteDAOIntf adquirenteDao;
		
	public List<Adquirente> listar() {
		
		return adquirenteDao.listar();
	}
	
	public Adquirente consultarPorId(Integer id) {

		return adquirenteDao.consultarPorId(id);		
	}
	
	public void salvar(Adquirente Adquirente) {

		adquirenteDao.salvar(Adquirente);		
	}
	
	public void excluir(Integer id) {

		adquirenteDao.excluir(id);
	}

}
