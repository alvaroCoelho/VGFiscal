package br.com.viageo.banco;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.viageo.HttpConection;
import br.com.viageo.entidade.Logradouro;
import br.com.viageo.entidade.Pessoa;
import br.com.viageo.entidade.Quadra;

public class BancoPessoa{
	
	private CriacaoBanco banco;
	JSONArray resultados;
	ArrayList<Pessoa> lista;
	
	
	
	
	public BancoPessoa(Context context) {
		super();
		this.banco = new CriacaoBanco(context);
		
	}

	
	public void inserir(Pessoa pessoa){
	    
	
		
		ContentValues valores = new ContentValues();
		
		valores.put("nm_pess",VerificaCamposNulos(pessoa.getNm_pess()));
		valores.put("nu_pess", VerificaCamposNulos(pessoa.getNu_pess()));
		valores.put("in_pfis_pjur", VerificaCamposNulos(pessoa.getIn_pfis_pjur()));
		valores.put("nu_cart_idnt",VerificaCamposNulos( pessoa.getNu_cart_idnt()));
		valores.put("nu_telf", VerificaCamposNulos(pessoa.getNu_telf()));
		valores.put("cd_logr",VerificaCamposNulos( pessoa.getCd_logr()));
		valores.put("nu_imvl", VerificaCamposNulos(pessoa.getNu_imvl()));
		valores.put("de_comp", VerificaCamposNulos(pessoa.getDe_comp()));
		valores.put("id_logra",VerificaCamposNulos( pessoa.getId_logra()));
		valores.put("sincronizado",VerificaCamposNulos(pessoa.getSincronizado()));
		
		banco.getWritableDatabase().insert("pessoa",null, valores);	
	    banco.close();
		
	}
	
	public boolean atualizar(Pessoa pessoa){
	
		String[] Idselecionado = {pessoa.getNu_pess()}; 
		ContentValues valores = new ContentValues();
		valores.put("nm_pess",VerificaCamposNulos(pessoa.getNm_pess()));
		valores.put("nu_pess", VerificaCamposNulos(pessoa.getNu_pess()));
		valores.put("in_pfis_pjur", VerificaCamposNulos(pessoa.getIn_pfis_pjur()));
		valores.put("nu_cart_idnt",VerificaCamposNulos( pessoa.getNu_cart_idnt()));
		valores.put("nu_telf", VerificaCamposNulos(pessoa.getNu_telf()));
		valores.put("cd_logr",VerificaCamposNulos( pessoa.getCd_logr()));
		valores.put("nu_imvl", VerificaCamposNulos(pessoa.getNu_imvl()));
		valores.put("de_comp", VerificaCamposNulos(pessoa.getDe_comp()));
		valores.put("id_logra",VerificaCamposNulos( pessoa.getId_logra()));
		valores.put("sincronizado",VerificaCamposNulos(pessoa.getSincronizado()));
		
		banco.getWritableDatabase().update("pessoa", valores, " nu_pess = ?", Idselecionado);
		banco.close();
		
		
	return true;	
	}
	
	public void deletar(String nu_pess){
	
	banco.getWritableDatabase().delete("pessoa", "nu_pess = "+nu_pess+"",null);
	banco.close();		
	}

	public Pessoa pesquisaPessoaBD(String valorPesquisa){
		
		Pessoa nova = new Pessoa();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM pessoa WHERE nu_pess = '"+valorPesquisa+"'",null);
		if (cursor != null) {
            if (cursor.moveToFirst()) {
            	nova.setNm_pess(cursor.getString(cursor.getColumnIndex("nm_pess")));
            	nova.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
            	nova.setIn_pfis_pjur(cursor.getString(cursor.getColumnIndex("in_pfis_pjur")));
            	nova.setNu_cart_idnt(cursor.getString(cursor.getColumnIndex("nu_cart_idnt")));
            	nova.setNu_telf(cursor.getString(cursor.getColumnIndex("nu_telf")));
            	nova.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
            	nova.setNu_imvl(cursor.getString(cursor.getColumnIndex("nu_imvl")));
            	nova.setDe_comp(cursor.getString(cursor.getColumnIndex("de_comp")));
            	nova.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
            	
                }
            
            cursor.close();
            banco.close();
            
            }
		
		
		return nova;
	}
	
	public Pessoa pesquisaPessoaWEB(String valorPesquisa){
	
		Pessoa nova = new Pessoa();
		
		try {
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "6");
			objConexao.setDados("dados", valorPesquisa);
			JSONObject retorno = objConexao.envia();
			resultados = retorno.getJSONArray("results");
			JSONObject rec = resultados.getJSONObject(0);
			 
			for (int i = 0; i < resultados.length(); ++i) {
				rec = resultados.getJSONObject(i);
			    nova.setNm_pess(rec.getString("nome"));
			    nova.setCd_logr(rec.getString("cd_logr_Pessoa"));
			    nova.setNu_pess(rec.getString("cpf"));
			    nova.setDe_comp(rec.getString("complemento"));
			    nova.setNu_telf(rec.getString("telefone"));
			   
			
			}
			
	     
			} catch (Exception e) {
		
				Log.e("","Erro no método pesquisaPessoaWEB (bancoPessoa): "+e.getMessage());
		}
		
		
		
		
		 return nova;
		
		
	}
	
	public Boolean ConsultaPessoaBD(String valorPesquisa){
		
		Pessoa nova = new Pessoa();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM pessoa WHERE nu_pess = '"+valorPesquisa+"'",null);
		if (cursor != null) {
            if (cursor.moveToFirst()) {
            	nova.setNm_pess(cursor.getString(cursor.getColumnIndex("nm_pess")));
            	nova.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
            	nova.setIn_pfis_pjur(cursor.getString(cursor.getColumnIndex("in_pfis_pjur")));
            	nova.setNu_cart_idnt(cursor.getString(cursor.getColumnIndex("nu_cart_idnt")));
            	nova.setNu_telf(cursor.getString(cursor.getColumnIndex("nu_telf")));
            	nova.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
            	nova.setNu_imvl(cursor.getString(cursor.getColumnIndex("nu_imvl")));
            	nova.setDe_comp(cursor.getString(cursor.getColumnIndex("de_comp")));
            	nova.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
            	
                }
            
            cursor.close();
            banco.close();
            
            }
		
		
		if (nova.getNu_pess()==null||nova.getNu_pess().equalsIgnoreCase("")){
			return true;
			
		     }else{
			  
			return false;  
			  
		      }
		
		}
	
    public Boolean ConsultaPessoaNaCotr(String valorPesquisa){
		
		Pessoa nova = new Pessoa();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM cotr_imobiliario WHERE nu_pess = '"+valorPesquisa+"'",null);
		if (cursor != null) {
            if (cursor.moveToFirst()) {
            	nova.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
            	  }
            
            cursor.close();
            banco.close();
            
            }
		
		
		if (nova.getNu_pess()==null||nova.getNu_pess().equalsIgnoreCase("")){
			return true;
			
		     }else{
			  
			return false;  
			  
		      }
		
		}
    
    public ArrayList<Pessoa> pesquisaPessoas(){
    	
    	ArrayList<Pessoa> listaPessoa = new ArrayList<Pessoa>();
    	
    	
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM pessoa WHERE sincronizado = 'NAO'",null);
		
		  while (cursor.moveToNext()){
			  Pessoa nova = new Pessoa();
				nova.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
            	nova.setIn_pfis_pjur(cursor.getString(cursor.getColumnIndex("in_pfis_pjur")));
            	nova.setNu_cart_idnt(cursor.getString(cursor.getColumnIndex("nu_cart_idnt")));
            	nova.setNu_telf(cursor.getString(cursor.getColumnIndex("nu_telf")));
            	nova.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
            	nova.setNu_imvl(cursor.getString(cursor.getColumnIndex("nu_imvl")));
            	nova.setDe_comp(cursor.getString(cursor.getColumnIndex("de_comp")));
            	nova.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
            	listaPessoa.add(nova);
			  
		         }
		  	cursor.close();
	    	banco.close();    
    	
    	
    	return listaPessoa;
    	
    }
    
    public ArrayList<Pessoa> pesquisaLogradourosPessoas(){
    	ArrayList<Pessoa> listaPessoa = new ArrayList<Pessoa>();
    	ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
    	
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM pessoa WHERE sincronizado = 'NAO'",null);
		
		  while (cursor.moveToNext()){
			  Pessoa nova = new Pessoa();
				nova.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
            	nova.setIn_pfis_pjur(cursor.getString(cursor.getColumnIndex("in_pfis_pjur")));
            	nova.setNu_cart_idnt(cursor.getString(cursor.getColumnIndex("nu_cart_idnt")));
            	nova.setNu_telf(cursor.getString(cursor.getColumnIndex("nu_telf")));
            	nova.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
            	nova.setNu_imvl(cursor.getString(cursor.getColumnIndex("nu_imvl")));
            	nova.setDe_comp(cursor.getString(cursor.getColumnIndex("de_comp")));
            	nova.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
            	listaPessoa.add(nova);
			  
		         }
		  	cursor.close();
	    	banco.close();     
    	
    	
    	
    	for (int i = 0; i < listaPessoa.size(); i++) {
    		 
    		 if(consultaLogradouroPessoa(listaPessoa.get(i).getCd_logr())){
    			 
    			 lista.add(listaPessoa.get(i));
    		 }
			
		}
    	
    	
    	return lista;
    }
     
	public boolean consultaLogradouroPessoa(String cd_logr){
		
		Logradouro logradouro = new Logradouro();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM logradouro WHERE cd_logr = '"+cd_logr+"'",null);
		if (cursor != null) {
            if (cursor.moveToFirst()) {
            	logradouro.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
            	  }
            
            cursor.close();
            banco.close();
            
            }
		
		
		if (logradouro.getCd_logr()==null||logradouro.getCd_logr().equalsIgnoreCase("")){
			
			return true;
			
		     }else{
			  
			return false;  
			  
		      }
		
		
		
	}
    
   public ArrayList<Pessoa> pesquisaPessoasBD(String valorPesquisa){
		
		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
		
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM pessoa WHERE nm_pess LIKE '%"+valorPesquisa+"%'"
				+ " OR nu_pess LIKE '%"+valorPesquisa+"%'",null);
		
		  while (cursor.moveToNext()){
			  Pessoa nova = new Pessoa();
				nova.setNm_pess(cursor.getString(cursor.getColumnIndex("nm_pess")));
            	nova.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
            	nova.setIn_pfis_pjur(cursor.getString(cursor.getColumnIndex("in_pfis_pjur")));
            	nova.setNu_cart_idnt(cursor.getString(cursor.getColumnIndex("nu_cart_idnt")));
            	nova.setNu_telf(cursor.getString(cursor.getColumnIndex("nu_telf")));
            	nova.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
            	nova.setNu_imvl(cursor.getString(cursor.getColumnIndex("nu_imvl")));
            	nova.setDe_comp(cursor.getString(cursor.getColumnIndex("de_comp")));
            	nova.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
            	lista.add(nova);
			  
		         }
		  	cursor.close();
	    	banco.close();     
	    	return lista;
			
		
	  }
	
	public boolean carregaPessoasQuadras(ArrayList<Quadra> listaQuadra){
		
		for (int i = 0; i < listaQuadra.size(); i++) {
		carregaPessoa(listaQuadra.get(i).getCd_quadra());	
			
		}
		
		
		
		return true;
	}
		
	public boolean carregaPessoa(String cd_quadra){
		
		try{
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "27");
			objConexao.setDados("quadra", cd_quadra);
			JSONObject retorno = objConexao.envia();
			resultados = retorno.getJSONArray("results");
			JSONObject rec = resultados.getJSONObject(0);
			
			for (int i = 0; i < resultados.length(); ++i){
				rec = resultados.getJSONObject(i);	
				
				Pessoa nova = new Pessoa();
				
				nova.setNm_pess(rec.getString("nm_pess"));
				nova.setNu_pess(rec.getString("nu_pess"));
				nova.setIn_pfis_pjur(rec.getString("in_pfis_pjur"));
				nova.setNu_cart_idnt(rec.getString("nu_cart_idnt"));
				nova.setNu_telf(rec.getString("nu_telf"));
				nova.setCd_logr(rec.getString("cd_logr"));
				nova.setNu_imvl(rec.getString("nu_imvl"));
				nova.setDe_comp(rec.getString("de_comp"));
				nova.setId_logra(rec.getString("id_logra"));
				nova.setSincronizado("SIM");
						
				
		        inserir(nova);
			}
			
			return true;
			
			}catch (Exception e) {
			
			return false;
			
			    }
	
	} 
	
 	 public String VerificaCamposNulos(String valor){
		   
		   if(valor!=null){
			   return valor;
		   }else{
			   return "";
			   
		       }
	 }
	
 	public boolean descarregarBasePessoa(String nu_pess){
		
 		
 		banco.getWritableDatabase().execSQL("DELETE FROM pessoa WHERE nu_pess = '"+nu_pess+"' ");
 		banco.close();
 		
 		return true;
 		
 		}

 	 
 	public boolean atualizarPessoasWEB(ArrayList<Pessoa> listaPessoas){
 		
 		Pessoa nova = new Pessoa();
 		HttpConection objConexao = new HttpConection();
 		String opcao = new String();
 		
 		try {
			
 			for (int i = 0; i < listaPessoas.size(); i++) {
			
 				nova = pesquisaPessoaBD(listaPessoas.get(i).getNu_pess());
 				
 				objConexao.setDados("nmcontribuinte",nova.getNm_pess());
 				objConexao.setDados("cpf",nova.getNu_pess());
 				objConexao.setDados("innaturezajuridica",nova.getIn_pfis_pjur());
 				objConexao.setDados("nrcartidentidade",nova.getNu_cart_idnt());
 				objConexao.setDados("nrtelefoneresid",nova.getNu_telf());
 				objConexao.setDados("cd_logr_pessoa",nova.getCd_logr());
 				objConexao.setDados("id_logra_pessoa",nova.getId_logra());
 				objConexao.setDados("nrimovel",nova.getNu_imvl());
 				objConexao.setDados("dscomplemento",nova.getDe_comp());
 				opcao = "11";
 				objConexao.setDados("o",opcao);
				JSONObject retorno = objConexao.envia();
				
				nova.setSincronizado("SIM");
				 atualizar(nova);
			    
				if (retorno.getString("ok").equals("1")){
					
					return true;
					
				}else{
					
					opcao = "13";
					objConexao.setDados("o", opcao);
					retorno = objConexao.envia();
					
					return true;  
					
				    }
				
			
			 }
 			
 			
 			return false;
 			
 		
 			
		} catch (Exception e) {
			Log.e("erro", e.getMessage());
			
			return false;
		}
 		
 			
 	}
 	 
 	 
}
