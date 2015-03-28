package br.com.viageo.banco;



import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.viageo.HttpConection;
import br.com.viageo.entidade.Bairro;

public class BancoBairro{
	
	private CriacaoBanco banco;
	private JSONArray resultados;
	
	
	public BancoBairro(Context context) {
		super();
		this.banco = new CriacaoBanco(context);
		
	}
	
	public void inserir(String mslink, String nm_bairro){
	    
	   ContentValues valores = new ContentValues();
		
		   valores.put("mslink", mslink);
		   valores.put("nm_bairro", nm_bairro);
	 
	    	banco.getWritableDatabase().insert("plan_bairro",null, valores);	
	    	banco.close();
	    
	}
	
	public void deletar(String mslink){
	
		banco.getReadableDatabase().delete("plan_bairro", "mslynk = "+mslink+"",null);
		banco.close();			
			
	}
	
	public boolean carregarBaseBairro(){
	
		try{
				HttpConection objConexao = new HttpConection();
				objConexao.setDados("o", "8");
				JSONObject retorno = objConexao.envia();
				resultados = retorno.getJSONArray("results");
				JSONObject rec = resultados.getJSONObject(0);
				
				for (int i = 0; i < resultados.length(); ++i){
					rec = resultados.getJSONObject(i);	
					String mslink = rec.getString("cd_bairro");
					String nomeBairro = rec.getString("nm_bairro");
				
					inserir(mslink,nomeBairro);
			
					}
		
		return true;
		
		}catch (Exception e) {
		
		return false;
		
		    }
			
	}

	public boolean descarregarBaseBairro(){
	
			banco.getWritableDatabase().execSQL("DELETE FROM plan_bairro");
	
		    banco.close();
	 
	return true;
	
	}
      
    public Bairro pesquisaBairroBD(String cd_bairro){
   	  
    	Bairro novo = new Bairro();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM plan_bairro WHERE mslink = "+cd_bairro+"",null);
   	 
           
			if (cursor != null) {
	            if (cursor.moveToFirst()) {
	            	 String mslink = cursor.getString(cursor.getColumnIndex("mslink"));
	                 String nm_bairro = cursor.getString(cursor.getColumnIndex("nm_bairro"));
	                 novo = new Bairro(mslink, nm_bairro);
	            }
	            
	            cursor.close();
	            banco.close();
	        }

   	return novo;
   	
	  
   }
    
    public ArrayList<Bairro> pesquisaBairrosBD(String nm_bairro){
    	
    	ArrayList<Bairro> lista = new ArrayList<Bairro>();
    	
            Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM plan_bairro WHERE nm_bairro LIKE '%"+nm_bairro+"%'",null);
    	  
    	 
    	     while (cursor.moveToNext()){
    		 Bairro novo = new Bairro();
    		 novo.setMslink(cursor.getString(cursor.getColumnIndex("mslink")));
    	     novo.setNm_bairro(cursor.getString(cursor.getColumnIndex("nm_bairro")));
    	     lista.add(novo);
    	     	}
    	cursor.close();
    	banco.close();     
    	return lista;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
