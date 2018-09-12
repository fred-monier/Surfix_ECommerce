package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Adquirente;

public interface AdquirenteDAOIntf {
	
	public List<Adquirente> listar();
	public Adquirente consultarPorId(int id);
	public void salvar(Adquirente adquirente);
	public void excluir(int id);

}
