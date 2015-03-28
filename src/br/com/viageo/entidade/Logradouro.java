package br.com.viageo.entidade;

public class Logradouro {

	String  cd_logr,
			id_logra,
			tp_logr,
			tp_atrb_logr,
			nm_logr,
			nu_cep_logr,
			nm_cidd,
			nm_barr,
			sg_uf,
			label;

	public Logradouro(){
		
		
	}
	
	public Logradouro(String cd_logr, String tp_logr, String tp_atrb_logr,
			String nm_logr, String nu_cep_logr, String nm_cidd, String nm_barr,
			String sg_uf, String label) {
		super();
		this.cd_logr = cd_logr;
		this.tp_logr = tp_logr;
		this.tp_atrb_logr = tp_atrb_logr;
		this.nm_logr = nm_logr;
		this.nu_cep_logr = nu_cep_logr;
		this.nm_cidd = nm_cidd;
		this.nm_barr = nm_barr;
		this.sg_uf = sg_uf;
		this.label = label;
	}

	public String getCd_logr() {
		return cd_logr;
	}

	public void setCd_logr(String cd_logr) {
		this.cd_logr = cd_logr;
	}

	public String getId_logra() {
		return id_logra;
	}

	public void setId_logra(String id_logra) {
		this.id_logra = id_logra;
	}

	public String getTp_logr() {
		return tp_logr;
	}

	public void setTp_logr(String tp_logr) {
		this.tp_logr = tp_logr;
	}

	public String getTp_atrb_logr() {
		return tp_atrb_logr;
	}

	public void setTp_atrb_logr(String tp_atrb_logr) {
		this.tp_atrb_logr = tp_atrb_logr;
	}

	public String getNm_logr() {
		return nm_logr;
	}

	public void setNm_logr(String nm_logr) {
		this.nm_logr = nm_logr;
	}

	public String getNu_cep_logr() {
		return nu_cep_logr;
	}

	public void setNu_cep_logr(String nu_cep_logr) {
		this.nu_cep_logr = nu_cep_logr;
	}

	public String getNm_cidd() {
		return nm_cidd;
	}

	public void setNm_cidd(String nm_cidd) {
		this.nm_cidd = nm_cidd;
	}

	public String getNm_barr() {
		return nm_barr;
	}

	public void setNm_barr(String nm_barr) {
		this.nm_barr = nm_barr;
	}

	public String getSg_uf() {
		return sg_uf;
	}

	public void setSg_uf(String sg_uf) {
		this.sg_uf = sg_uf;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	      
	
	
}
