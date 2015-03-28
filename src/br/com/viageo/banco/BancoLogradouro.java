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

public class BancoLogradouro {

    private CriacaoBanco banco;
	JSONArray resultados;
	JSONArray resultados1;
	
	public BancoLogradouro(Context context) {
		super();
		this.banco = new CriacaoBanco (context);
	}

	public void inserir(String id_logra,String cd_logr, String tp_logr,String tp_atrb_logr,
			            String nm_logr, String nu_cep_logr, String nm_cidd, 
			            String nm_barr, String sg_uf, String label ){
		
	
		ContentValues valores = new ContentValues();
		valores.put("id_logra", id_logra);
		valores.put("cd_logr", cd_logr);
		valores.put("tp_logr", tp_logr);
		valores.put("tp_atrb_logr", tp_atrb_logr);
		valores.put("nm_logr", nm_logr);
		valores.put("nu_cep_logr", nu_cep_logr);
		valores.put("nm_cidd", nm_cidd);
		valores.put("nm_barr", nm_barr);
		valores.put("sg_uf", sg_uf);
		valores.put("label", label);

		banco.getWritableDatabase().insert("logradouro",null, valores);	
	    banco.close();
		
	   }
	
	public void deletar(String cd_logr){
		
		banco.getWritableDatabase().delete("logradouro", "cd_logr = "+cd_logr+"",null);
		banco.close();
	
		
		
	}
	
	
	public boolean carregaLogradourosPessoa(ArrayList<Pessoa> listaPessoas){
		for(int i = 0; i < listaPessoas.size(); ++i){
			carregaLogradouroPessoa(listaPessoas.get(i).getNu_pess());
				
			}
		
		return true;
		
		
	
	}
	
	
	
	public boolean carregaLogradouroPessoa(String nu_pess){
		try{
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "28");
			objConexao.setDados("nu_pess", nu_pess);
			JSONObject retorno = objConexao.envia();
			resultados = retorno.getJSONArray("results");
			JSONObject rec = resultados.getJSONObject(0);
			
			for (int i = 0; i < resultados.length(); ++i){
				rec = resultados.getJSONObject(i);	
				String cd_logr = rec.getString("cd_logr");
				
				if(consultaLogradouro(cd_logr)){
				
				String id_logra = rec.getString("id_logra");
				String tp_logr = rec.getString("tp_logr");
				String tp_atrb_logr = rec.getString("tp_atrb_logr");
				String nm_logr = rec.getString("nm_logr");
				String nu_cep_logr = rec.getString("nu_cep_logr");
				String nm_cidd = rec.getString("nm_cidd");
				String nm_barr = rec.getString("nm_barr");
				String sg_uf = rec.getString("sg_uf");
				String label = rec.getString("label");
				inserir(id_logra,cd_logr,tp_logr,tp_atrb_logr,nm_logr,nu_cep_logr,nm_cidd,nm_barr,sg_uf,label);
				
				}
		
			}
			
			return true;
			
			}catch (Exception e) {
			
			return false;
			
			    }
		
		
		
	}
	
	public boolean carregaLogradourosQuadras(ArrayList<Quadra> listaQuadra){
		
		for(int i = 0; i < listaQuadra.size(); ++i){
			carregaBaseLogradouro(listaQuadra.get(i).getCd_quadra());
				
			}
		
		return true;
	}
	
	public boolean carregaBaseLogradouro(String quadra){
		try{
		HttpConection objConexao = new HttpConection();
		objConexao.setDados("o", "20");
		objConexao.setDados("quadra", quadra);
		JSONObject retorno = objConexao.envia();
		resultados = retorno.getJSONArray("results");
		JSONObject rec = resultados.getJSONObject(0);
		
		for (int i = 0; i < resultados.length(); ++i){
			rec = resultados.getJSONObject(i);	
			String id_logra = rec.getString("id_logra");
			String cd_logr = rec.getString("cd_logr");
			String tp_logr = rec.getString("tp_logr");
			String tp_atrb_logr = rec.getString("tp_atrb_logr");
			String nm_logr = rec.getString("nm_logr");
			String nu_cep_logr = rec.getString("nu_cep_logr");
			String nm_cidd = rec.getString("nm_cidd");
			String nm_barr = rec.getString("nm_barr");
			String sg_uf = rec.getString("sg_uf");
			String label = rec.getString("label");
			inserir(id_logra,cd_logr,tp_logr,tp_atrb_logr,nm_logr,nu_cep_logr,nm_cidd,nm_barr,sg_uf,label);
	
		}
		
		return true;
		
		}catch (Exception e) {
		
		return false;
		
		    }
		
		
		
	  }

	public boolean descarregarBaseLogradouro(String cd_logr){
		
	
	banco.getWritableDatabase().execSQL("DELETE FROM logradouro WHERE cd_logr = '"+cd_logr+"' ");
	banco.close();
	
	return true;
	
	}

	public Logradouro pesquisaLogradouroWEB(String cd_logr){
		Logradouro novo = new Logradouro();
		
		try {
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "16");
			objConexao.setDados("codigo", cd_logr);
			JSONObject retorno = objConexao.envia();
			resultados = retorno.getJSONArray("results");
			JSONObject rec = resultados.getJSONObject(0);
			 
			for (int i = 0; i < resultados.length(); ++i) {
				rec = resultados.getJSONObject(i);
			    novo.setCd_logr(rec.getString("cd_logr"));
			    novo.setNm_logr(rec.getString("nm_logr"));
			    novo.setId_logra(rec.getString("id_logra"));
			    novo.setNm_barr(rec.getString("nm_barr"));
			    novo.setNm_cidd(rec.getString("nm_cidd"));
			    novo.setSg_uf(rec.getString("sg_uf"));
			    novo.setNu_cep_logr(rec.getString("nu_cep_logr"));
			    novo.setLabel(rec.getString("label"));
			}
			
	     
			} catch (Exception e) {
		
				Log.e("","Erro no método pesquisaLogradouroWEB (bancoLogradouro): "+e.getMessage());
		}
		
		
		
		
		return novo;
	}
	

	public Logradouro pesquisaLogradouroBD(String cd_logr){
		
		Logradouro novo = new Logradouro();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM logradouro WHERE cd_logr = '"+cd_logr+"'", null);
		
		if (cursor != null) {
            if (cursor.moveToFirst()) {
                novo.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));	
                novo.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
            	novo.setTp_logr(cursor.getString(cursor.getColumnIndex("tp_logr")));
                novo.setTp_atrb_logr(cursor.getString(cursor.getColumnIndex("tp_atrb_logr")));
                novo.setNm_logr(cursor.getString(cursor.getColumnIndex("nm_logr")));
                novo.setNu_cep_logr(cursor.getString(cursor.getColumnIndex("nu_cep_logr")));
                novo.setNm_cidd(cursor.getString(cursor.getColumnIndex("nm_cidd")));
                novo.setNm_barr(cursor.getString(cursor.getColumnIndex("nm_barr")));
                novo.setSg_uf(cursor.getString(cursor.getColumnIndex("sg_uf")));
                novo.setLabel(cursor.getString(cursor.getColumnIndex("label")));
                
                }
            
            cursor.close();
            banco.close();
		
	    	}
            
		return novo;
	}

    public ArrayList<Logradouro> pesquisaLogradourosBD(String nm_logr){
    	
    	ArrayList<Logradouro> lista = new ArrayList<Logradouro>();
    	
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM logradouro WHERE nm_logr LIKE '%"+nm_logr+"%'",null);
    	
    	 while (cursor.moveToNext()){
    	  Logradouro novo = new Logradouro(); 
    	  novo.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
    	  novo.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
    	  novo.setTp_logr(cursor.getString(cursor.getColumnIndex("tp_logr")));
   	      novo.setTp_atrb_logr(cursor.getString(cursor.getColumnIndex("tp_atrb_logr")));
    	  novo.setNm_logr(cursor.getString(cursor.getColumnIndex("nm_logr")));
   	      novo.setNu_cep_logr(cursor.getString(cursor.getColumnIndex("nu_cep_logr")));
    	  novo.setNm_cidd(cursor.getString(cursor.getColumnIndex("nm_cidd")));
   	      novo.setNm_barr(cursor.getString(cursor.getColumnIndex("nm_barr")));
   	      novo.setSg_uf(cursor.getString(cursor.getColumnIndex("sg_uf")));
   	      novo.setLabel(cursor.getString(cursor.getColumnIndex("label")));
   	      lista.add(novo);
    	 }
    	 
    	 
    	cursor.close();
     	banco.close();     
     	return lista;
    } 

    public boolean consultaLogradouro(String cd_logr){
    	
		Logradouro novo = new Logradouro();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM logradouro WHERE cd_logr like '"+cd_logr+"%'  ",null);
		
		if (cursor != null) {
            if (cursor.moveToFirst()) {
            	String cd_logra = cursor.getString(cursor.getColumnIndex("cd_logr"));
            	novo.setCd_logr(cd_logra);
            	
            }
            
            cursor.close();
            banco.close();
		
		}
		
		
		if (novo.getCd_logr() == null || novo.getCd_logr().equalsIgnoreCase("")){
			return true;
			
		  }else{
			  
			return false;  
			  
		  }
    }
   
   

    
    
	
	
	
		
		
		
		
		
	

	
	
	
	
}
