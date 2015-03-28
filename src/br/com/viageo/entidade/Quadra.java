package br.com.viageo.entidade;

public class Quadra {

	String id_associacao,cd_quadra,id_usuario,data_associacao;
	
	
public Quadra() {
		
	}


	public Quadra(String id_associacao, String cd_quadra, String id_usuario, String data_associacao) {
	super();
	this.id_associacao = id_associacao;
	this.cd_quadra = cd_quadra;
	this.id_usuario = id_usuario;
	this.data_associacao = data_associacao;
}

	public String getId_associacao() {
		return id_associacao;
	}

	public void setId_associacao(String id_associacao) {
		this.id_associacao = id_associacao;
	}

	public String getCd_quadra() {
		return cd_quadra;
	}

	public void setCd_quadra(String cd_quadra) {
		this.cd_quadra = cd_quadra;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}


	public String getData_associacao() {
		return data_associacao;
	}


	public void setData_associacao(String data_associacao) {
		this.data_associacao = data_associacao;
	}


	

}
