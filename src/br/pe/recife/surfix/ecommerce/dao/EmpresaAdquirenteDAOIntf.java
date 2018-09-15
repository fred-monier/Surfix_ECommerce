package br.pe.recife.surfix.ecommerce.dao;

import java.util.List;

import br.pe.recife.surfix.ecommerce.entity.EmpresaAdquirente;

public interface EmpresaAdquirenteDAOIntf {
	
	public List<EmpresaAdquirente> listar();
	public List<EmpresaAdquirente> listarPorEmpresa(int idEmpresa);
	public EmpresaAdquirente consultarPorId(int id);
	public void salvar(EmpresaAdquirente empresaAdquirente);
	public void excluir(int id);

}
