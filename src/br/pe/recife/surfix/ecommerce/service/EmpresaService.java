package br.pe.recife.surfix.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.pe.recife.surfix.ecommerce.dao.EmpresaDAOIntf;
import br.pe.recife.surfix.ecommerce.entity.Empresa;

@Transactional
@Service
public class EmpresaService {
	
	@Autowired
	//@Qualifier("empresaDAO")
	private EmpresaDAOIntf empresaDao;
		
	public List<Empresa> listar() {				
		
		return empresaDao.listar();
	}
	
	public Empresa consultarPorId(Integer id) {
			
		return empresaDao.consultarPorId(id);		
	}
	
	public void salvar(Empresa empresa) {

		empresaDao.salvar(empresa);		
	}
	
	public void excluir(Integer id) {

		empresaDao.excluir(id);		
	}
	
}
