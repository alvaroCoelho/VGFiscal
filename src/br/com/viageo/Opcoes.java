package br.com.viageo;

public class Opcoes {  
	private int id;  
	private String nome;  
	
	public int getId() {  
	 return id;  
	}  
	public void setId(int id) {  
	 this.id = id;  
	}  
	public String getNome() {  
	 return nome;  
	}  
	public void setNome(String nome) {  
	 this.nome = nome;  
	}
	
	//Aqui esta a magica, sem este metodo voce terq uma excecao  
	public String toString() {  
	 return (this.getNome());  
	}  
}  