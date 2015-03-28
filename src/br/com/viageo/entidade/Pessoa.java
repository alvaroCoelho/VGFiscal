package br.com.viageo.entidade;

public class Pessoa {
	
	String nm_pess,
	       nu_pess,
	       in_pfis_pjur,
	       nu_cart_idnt,
	       nu_telf,
	       cd_logr,
	       nu_imvl,
	       de_comp,
	       id_logra,
	       sincronizado;
	
	
     public Pessoa(){
    	 
    	 
     }	

	public Pessoa(String nm_pess,String nu_pess, String in_pfis_pjur, String nu_cart_idnt,
			String nu_telf, String cd_logr, String nu_imvl, String de_comp,
			String id_logra,String sincronizado) {
		super();
		this.nu_pess = nu_pess;
		this.in_pfis_pjur = in_pfis_pjur;
		this.nu_cart_idnt = nu_cart_idnt;
		this.nu_telf = nu_telf;
		this.cd_logr = cd_logr;
		this.nu_imvl = nu_imvl;
		this.de_comp = de_comp;
		this.id_logra = id_logra;
		this.sincronizado = sincronizado;
	}

	public String getNm_pess() {
		return nm_pess;
	}

	public void setNm_pess(String nm_pess) {
		this.nm_pess = nm_pess;
	}

	public String getNu_pess() {
		return nu_pess;
	}

	public void setNu_pess(String nu_pess) {
		this.nu_pess = nu_pess;
	}

	public String getIn_pfis_pjur() {
		return in_pfis_pjur;
	}

	public void setIn_pfis_pjur(String in_pfis_pjur) {
		this.in_pfis_pjur = in_pfis_pjur;
	}

	public String getNu_cart_idnt() {
		return nu_cart_idnt;
	}

	public void setNu_cart_idnt(String nu_cart_idnt) {
		this.nu_cart_idnt = nu_cart_idnt;
	}

	public String getNu_telf() {
		return nu_telf;
	}

	public void setNu_telf(String nu_telf) {
		this.nu_telf = nu_telf;
	}

	public String getCd_logr() {
		return cd_logr;
	}

	public void setCd_logr(String cd_logr) {
		this.cd_logr = cd_logr;
	}

	public String getNu_imvl() {
		return nu_imvl;
	}

	public void setNu_imvl(String nu_imvl) {
		this.nu_imvl = nu_imvl;
	}

	public String getDe_comp() {
		return de_comp;
	}

	public void setDe_comp(String de_comp) {
		this.de_comp = de_comp;
	}

	public String getId_logra() {
		return id_logra;
	}

	public void setId_logra(String id_logra) {
		this.id_logra = id_logra;
	}

	public String getSincronizado() {
		return sincronizado;
	}

	public void setSincronizado(String sincronizado) {
		this.sincronizado = sincronizado;
	}
	

}
