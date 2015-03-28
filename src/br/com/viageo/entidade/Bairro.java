package br.com.viageo.entidade;

public class Bairro {
	
	String mslink,nm_bairro;

	public Bairro(String mslink, String nm_bairro) {
		super();
		this.mslink = mslink;
		this.nm_bairro = nm_bairro;
	}
	
	public Bairro(){
		
		
	}
	
	public String getMslink() {
		return mslink;
	}

	public void setMslink(String mslink) {
		this.mslink = mslink;
	}

	public String getNm_bairro() {
		return nm_bairro;
	}

	public void setNm_bairro(String nm_bairro) {
		this.nm_bairro = nm_bairro;
	}

	
	
	
	

}
