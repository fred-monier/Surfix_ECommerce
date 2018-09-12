package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.Empresa;

public interface EmpresaDAOIntf {
	
	public List<Empresa> listar();
	public Empresa consultarPorId(int id);
	public void salvar(Empresa empresa);
	public void excluir(int id);

}
