package br.pe.recife.surfix.ecommerce.entity.http;

public class EmpresaAdquirenteHttp {
	
	private Integer id;
    private Integer idEmpresa;
    private Integer idAdquirente;
    private String mecId;
    private String mecKey;
    private String mecIdTeste;
    private String mecKeyTeste;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Integer getIdAdquirente() {
		return idAdquirente;
	}
	public void setIdAdquirente(Integer idAdquirente) {
		this.idAdquirente = idAdquirente;
	}
	public String getMecId() {
		return mecId;
	}
	public void setMecId(String mecId) {
		this.mecId = mecId;
	}
	public String getMecKey() {
		return mecKey;
	}
	public void setMecKey(String mecKey) {
		this.mecKey = mecKey;
	}
	public String getMecIdTeste() {
		return mecIdTeste;
	}
	public void setMecIdTeste(String mecIdTeste) {
		this.mecIdTeste = mecIdTeste;
	}
	public String getMecKeyTeste() {
		return mecKeyTeste;
	}
	public void setMecKeyTeste(String mecKeyTeste) {
		this.mecKeyTeste = mecKeyTeste;
	}
        
}
