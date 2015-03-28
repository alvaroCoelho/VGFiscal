package br.com.viageo.banco;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.viageo.HttpConection;
import br.com.viageo.MyData;
import br.com.viageo.entidade.Quadra;

public class BancoQuadra {

	private CriacaoBanco banco;
	private JSONArray resultados;
	
	public BancoQuadra(Context context){
		super();
		this.banco = new CriacaoBanco(context);
		
	  }
		
	public void inserir(Quadra quadra){
		   
		ContentValues valores = new ContentValues();
		valores.put("id_associacao",quadra.getId_associacao());   
		valores.put("cd_quadra",quadra.getCd_quadra()); 
		valores.put("id_usuario", quadra.getId_usuario());
		valores.put("data_associacao",quadra.getData_associacao());
		banco.getWritableDatabase().insert("quadra", null,valores);
		banco.close();
		
	}
	
	public ArrayList<Quadra> listaQuadrasWEB(String usuario){
		
		ArrayList<Quadra> listaQuadras = new ArrayList<Quadra>();
		
		try{
		
		HttpConection objConexao = new HttpConection();
		objConexao.setDados("o", "21");
		objConexao.setDados("usuario",usuario);
		JSONObject retorno = objConexao.envia();
		resultados = retorno.getJSONArray("results");
		JSONObject rec = resultados.getJSONObject(0);
		
		
			
		for (int i = 0; i < resultados.length(); ++i){
			rec = resultados.getJSONObject(i);
		Quadra nova = new Quadra();
		nova.setId_associacao(rec.getString("id_associacao"));
		nova.setCd_quadra(rec.getString("cd_quadra"));
		nova.setId_usuario(rec.getString("id_usuario"));	
		nova.setData_associacao(rec.getString("data_associacao"));
		listaQuadras.add(nova);
		
		   }
		
		
		}catch (Exception e){
			Log.v("","ERRO:"+e.toString());
			
		}
		
		return listaQuadras;
		
	} 
	
    public boolean descarregarBaseQuadras(String cd_quadra){
		
		banco.getWritableDatabase().execSQL("DELETE FROM quadra WHERE  cd_quadra = '"+cd_quadra+"'");
		
	    banco.close();
		
		
		return false;
	}
    
    public ArrayList<Quadra> pesquisaQuadrasBD(String id_usuario){
    	
    	ArrayList<Quadra> lista = new ArrayList<Quadra>();
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM quadra WHERE id_usuario LIKE '%"+id_usuario+"%'",null);
    	  while (cursor.moveToNext()){
    		  Quadra nova = new Quadra();
    		  nova.setId_associacao(cursor.getString(cursor.getColumnIndex("id_associacao")));
    		  nova.setCd_quadra(cursor.getString(cursor.getColumnIndex("cd_quadra")));
    		  nova.setId_usuario(cursor.getString(cursor.getColumnIndex("id_usuario")));
    		  nova.setData_associacao(cursor.getString(cursor.getColumnIndex("data_associacao")));
    		  lista.add(nova);
    	  }
    	
    	cursor.close();
      	banco.close();     
      	return lista;
    }
    
    public ArrayList<Quadra> pesquisaQuadras(String cd_quadra){
    	
    	ArrayList<Quadra> lista = new ArrayList<Quadra>();
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM quadra WHERE cd_quadra LIKE '%"+cd_quadra+"%'",null);
    	  while (cursor.moveToNext()){
    		  Quadra nova = new Quadra();
    		  nova.setId_associacao(cursor.getString(cursor.getColumnIndex("id_associacao")));
    		  nova.setCd_quadra(cursor.getString(cursor.getColumnIndex("cd_quadra")));
    		  nova.setId_usuario(cursor.getString(cursor.getColumnIndex("id_usuario")));
    		  nova.setData_associacao(cursor.getString(cursor.getColumnIndex("data_associacao")));
    		  lista.add(nova);
    	  }
    	
    	cursor.close();
      	banco.close();     
      	return lista;
    }
    
    public ArrayList<Quadra> pesquisaInscricoesPendentes(){
    	
    	ArrayList<Quadra> lista = new ArrayList<Quadra>();
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM controle WHERE local_ultima_alteracao = 'Tablet' ",null);
    	 
    	while (cursor.moveToNext()){
    	String inscricao =  cursor.getString(cursor.getColumnIndex("nu_insc_imbl")); 
    		 Quadra nova = new Quadra(); 
    				nova = pesquisaQuadra(inscricao);
    		  
    		lista.add(nova);
    		  
    	  }
    	  
    	  
    	    cursor.close();
        	banco.close();     
        	return lista;
    }
    
    public int contaInscricoes(String cd_quadra){
    	
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT cd_quadra FROM cotr_imobiliario WHERE cd_quadra = '"+cd_quadra+"'",null);
    	int total = cursor.getCount();
    	
    	cursor.close();
      	banco.close(); 
    	return total;
    }
    
    public Quadra pesquisaQuadra(String inscricao){
    	
    	 Quadra nova = new Quadra();
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM cotr_imobiliario WHERE nu_insc_imbl = '"+inscricao+"'",null);
       
        
    	if (cursor != null) {
            if (cursor.moveToFirst()) {
            	nova.setCd_quadra(cursor.getString(cursor.getColumnIndex("cd_quadra")));
                        }
            
            
            
            cursor.close();
            banco.close();
             
    	     }
    	
    	
     	return nova;
    }
    
    public ArrayList<MyData> pesquisaInscricoesBD(String cd_quadra){
    	
    	ArrayList<MyData> lista = new ArrayList<MyData>();
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT nu_insc_imbl FROM cotr_imobiliario WHERE cd_quadra LIKE '%"+cd_quadra+"%'",null);
    	  while (cursor.moveToNext()){
    		  MyData nova = new MyData(cursor.getString(cursor.getColumnIndex("nu_insc_imbl")),"","");
    		
    		  lista.add(nova);
    	  }
    	
    	cursor.close();
      	banco.close();     
      	return lista;
    }
    
    public boolean consultaQuadraBD(String cd_quadra){
   	 String teste = "";
    	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM quadra WHERE cd_quadra = '"+cd_quadra+"'",null);
       
        
    	if (cursor != null) {
            if (cursor.moveToFirst()) {
            	teste = cursor.getString(cursor.getColumnIndex("cd_quadra"));
                        }
             
            cursor.close();
            banco.close();
             
    	     }
   	
    	if(teste.equals("")||teste==null){
    		
   	  return true;
   	  
    	}else{
    		
    	 return false;
    	 
    	}
   }

    public boolean consultaQuadraWEB(String cd_quadra){
    	
    	 String teste = "";	
		try{
		
		HttpConection objConexao = new HttpConection();
		objConexao.setDados("o", "26");
		objConexao.setDados("quadra",cd_quadra);
		JSONObject retorno = objConexao.envia();
		resultados = retorno.getJSONArray("results");
		JSONObject rec = resultados.getJSONObject(0);
		
		if(rec.getString("ok").equalsIgnoreCase("1")){
			
			for (int i = 0; i < resultados.length(); ++i){
				rec = resultados.getJSONObject(i);
			
			teste = rec.getString("cd_quadra");
			
			     }
		
		}else{
			
			teste = "";
			
		}
    	
		}catch (Exception e){
			
			Log.v("","ERRO:"+e.toString());	
			
		   }
		
    	
		
		if(teste.equals("")||teste==null){
			return true;
			
			
		}
    	
    	return false;
    }
    
    
   public ArrayList<String> listaLogradourosQuadra(String cd_quadra){
	 
	   ArrayList<String> lista = new ArrayList<String>();
       Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT DISTINCT cd_logr FROM cotr_imobiliario WHERE cd_quadra = '"+cd_quadra+"'",null);
      
       while (cursor.moveToNext()){
        	  String cd_logr = cursor.getString(cursor.getColumnIndex("cd_logr"));
        	  lista.add(cd_logr);
        	  
        	  
                       }
            
           cursor.close();
           banco.close();
            
   	     
	   
	   
	   return lista;
		   
	   
   } 
    
    
    public ArrayList<String> listaPessoasQuadra(String cd_quadra){
    	
    	
    	   ArrayList<String> lista = new ArrayList<String>();
           Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT DISTINCT nu_pess FROM cotr_imobiliario WHERE cd_quadra = '"+cd_quadra+"'",null);
          
           while (cursor.moveToNext()){
              
            	  String nu_pess = cursor.getString(cursor.getColumnIndex("nu_pess"));
            	  lista.add(nu_pess);
                           }
                
               cursor.close();
               banco.close();
                
       	     
    	
    	
    	return lista;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	

}
