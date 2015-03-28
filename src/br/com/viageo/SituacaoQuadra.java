package br.com.viageo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.viageo.banco.BancoFormulario;
import br.com.viageo.entidade.FormularioEntidade;
import br.com.viageo.entidade.Quadra;

public class SituacaoQuadra extends ListActivity implements OnClickListener {
    
	
	 TextView tvQuadra;
	 Button btAtualizarQuadra;
	 String inscricao,cd_quadra;
	 String erroPesquisa = null;
	 private ArrayList<MyData> list = null;
	 AsyncTask<String, Long, Void> thread_exec;
	 ArrayList<Quadra> listaInscricoes = new ArrayList<Quadra>();
	 HttpConection objConexao;
	 ArrayList<String> array_validacoes = new ArrayList<String>();
	 String colocaBotao = "false";
	 String colocaBotao2 = "false";	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.situacao_quadra);
	
		
		tvQuadra = (TextView)findViewById(R.id.tvQuadra);
		tvQuadra.setText(" "+getIntent().getStringExtra("valor"));
		//btAtualizarQuadra = (Button) findViewById(R.id.btnAtualizarQuadra);
		//btAtualizarQuadra.setOnClickListener(this);
		cd_quadra = getIntent().getStringExtra("valor");
		
		inicializaListaInscricoes(cd_quadra);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	     finish();   	
	
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
      private boolean conexaoWIFI(){

   	 ConnectivityManager cm = (ConnectivityManager)
   	            getSystemService(Context.CONNECTIVITY_SERVICE);   
   	final NetworkInfo wifiNetworkInfo =  cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
   	  
   	if (wifiNetworkInfo == null) {  
   	    return false;  
   	}  
   	  
   	boolean connected = wifiNetworkInfo.getState() == NetworkInfo.State.CONNECTED  
   	        || wifiNetworkInfo.getState() == NetworkInfo.State.CONNECTING; 
   	
   	return connected;
       
   }
	
	  private boolean TemConexao() { 
	        boolean lblnRet = false;
	        try{
	            ConnectivityManager cm = (ConnectivityManager)
	            getSystemService(Context.CONNECTIVITY_SERVICE); 
	            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) { 
	                lblnRet = true; 
	            } else { 
	                lblnRet = false; 
	            }
	        }catch (Exception e) {
	            trace(e.getMessage());
	            
	        }
	        return lblnRet;
	    }
	  
	  private void trace (String msg){
	        Log.d ("teste", msg);
	       
	        Toast.makeText(getBaseContext(), "ERRO DE CONECTIVIDADE = "+msg, Toast.LENGTH_LONG).show();
	    }
	
	  public void inicializaListaInscricoes(String cd_quadra){
	    

		  
			list = pesquisaInscricoes(cd_quadra);
				if (list.isEmpty()){
					Toast.makeText(getBaseContext(), "Quadra sem Inscrições cadastradas!", Toast.LENGTH_LONG).show();	
					return;
			        }
		
	  
		
		montaListaView();
		
	} 

	  @Override
	  public void onClick(View v) {
		
		/*  switch (v.getId()) {
			case R.id.btnAtualizarQuadra:
				if(TemConexao()){
					thread_exec = new threadAtualizaQuadra().execute();
					
				}else{
					
					Toast.makeText(getBaseContext(), "Atualização de Quadra somente com Conexão!", Toast.LENGTH_LONG).show();
					
				}
		  
		  
		  
		  }
		*/
		
	}
	
	  public void montaListaView(){
		if(erroPesquisa != null){
			Toast.makeText(getBaseContext(), erroPesquisa, Toast.LENGTH_LONG).show();
			erroPesquisa = null;
		}else{
			this.getListView().setAdapter(new MyListAdapter(this, list,"1"));
	        this.getListView().setFastScrollEnabled(true);
		}
		
		
		
	}

	  public ArrayList<MyData> pesquisaInscricoesBD(String cd_quadra){
		
		ArrayList<MyData> lista = new ArrayList<MyData>();
		
		try{		
			BancoFormulario formularioBanco = new BancoFormulario(this);
			
			
			ArrayList<FormularioEntidade> listaForm = new ArrayList<FormularioEntidade>();
			listaForm = formularioBanco.ListaInscricoesQuadras(cd_quadra);
			
			for (int i = 0; i < listaForm.size(); ++i) {
				String inscricao = listaForm.get(i).getNu_insc_imbl();
				String status = listaForm.get(i).getStatus_cadastro();
				String local = listaForm.get(i).getSincronizado();
						if(status.equalsIgnoreCase("")){
						    lista.add(new MyData(inscricao,"Inscrição Nova!",""));
						    	
						}else{
						
							if (local.equalsIgnoreCase("SIM")){
								local = "WEB / TABLET";
							}else{
								local = "TABLET";
							}
							 lista.add(new MyData(inscricao,status,"Atualizado: "+local));
						 
						 
						} 
			  }

         }catch (Exception e){
        	 
        	 Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no método pesquisaInscricoes banco (SituacaoQuadra):"+e.toString());
        	 
         } 

		
		return lista;
		
		
	}
	
	  public ArrayList<MyData> pesquisaInscricoes(String cd_quadra){
		
		ArrayList<MyData> lista = new ArrayList<MyData>();
		BancoFormulario formulariobanco = new BancoFormulario(this);
		
		if(TemConexao()){
		
						try {
					        objConexao = new HttpConection();
							objConexao.setDados("o", "24");
							objConexao.setDados("quadra",cd_quadra);
							JSONObject retorno = objConexao.envia();
							JSONArray resultados = retorno.getJSONArray("results");
							
							for (int i = 0; i < resultados.length(); ++i) {
							    JSONObject rec = resultados.getJSONObject(i);
							    String inscricao = rec.getString("nu_insc_imbl");
							    
							    StringBuilder stringBuilder = new StringBuilder(inscricao); 
							    stringBuilder.insert(inscricao.length() - 3,'-');
							    stringBuilder.insert(inscricao.length() - 6,'.');
							    stringBuilder.insert(inscricao.length() - 10,'.');
							    String insc = stringBuilder.toString();
							    
							    
							    
							    String status = rec.getString("status_cadastro");
							    String local = ""; 
							    if(formulariobanco.consultaInscricaoControleTablet(inscricao)){
							    	local = "WEB / TABLET";
							    }else{
							    	local = "TABLET";
							    }
							  
								    if(status.equalsIgnoreCase("")){
								    lista.add(new MyData(insc,"Inscrição Nova!",""));	
								    	
								    }else{
									    
								    int total = contaErrosInscricao(inscricao);
								  
								    if(local.equalsIgnoreCase("TABLET")){
								    lista.add(new MyData(insc,status,"Quadra não sincronizada!"));
								    }else{
								    	 lista.add(new MyData(insc,status,"Atualizado: "+local));
								       }
								    }
							
							}
							
							
							
							
							}catch (Exception e){
							
								Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no método pesquisaInscricoes web (SituacaoQuadra):"+e.toString());
								
							}
		
		}else{
			
	                lista = pesquisaInscricoesBD(cd_quadra);
			
					
		}
		
		return lista;
	}
	
	  public int contaErrosInscricao(String nu_insc_imbl){
		
		
		int total = 0;
				try {
	        objConexao = new HttpConection();
			objConexao.setDados("o", "23");
			objConexao.setDados("nu_insc_imbl",nu_insc_imbl);
			JSONObject retorno = objConexao.envia();
			JSONArray resultados = retorno.getJSONArray("results");
			
			for (int i = 0; i < resultados.length(); ++i) {
			    JSONObject rec = resultados.getJSONObject(i);
			     total = Integer.parseInt(rec.getString("total"));
			
			         }
		
			}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro método contaErrosInscricao (SituacaoQuadra):"+e.toString());	
				
				
			}
		
		
		
		return total;
		
	}

	  @Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		if( this.list.get(position).getText2().equalsIgnoreCase("Aguardando Retorno") || this.list.get(position).getText2().equalsIgnoreCase("Editado sem Erro")){
		Toast.makeText(this,"Inscrição sem erro", Toast.LENGTH_LONG).show();
			
		}else{
		 inscricao = this.list.get(position).getText1();
		 chamaEdicaoQuadra(inscricao);
		
		}
	}

	  private void chamaEdicaoQuadra(final String inscricao){
	  
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Inscrição: "+inscricao)
		    	.setCancelable(true)
		    	.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int id) {
		    			
		    			
		    			Intent winEditForm = new Intent(SituacaoQuadra.this, Formulario.class);
		    		
		    			winEditForm.putExtra("inscricao",inscricao);
		    			startActivityForResult(winEditForm, 1);
		    			finish();
		    			
			       	}
		    		
		       }).setNegativeButton("Visualizar Erros", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		        	  if(TemConexao()){ 
		        		  
		        		  
		        		thread_exec = new threadPesquisaErros().execute();
		        			
		        		
		        			
		        	 }else{
		        		  
		        			Toast.makeText(getBaseContext(), "Pesquisa de erros somente com conexão!", Toast.LENGTH_LONG).show();  
		        	  }
		        	  
		        	  
		        	  
		        	  
		           }
		       });
			AlertDialog alert = builder.create();
			alert.show();
			
		
	}
	  
	  private class threadPesquisaErros extends AsyncTask <String, Long, Void> {
			private final ProgressDialog dialog = new ProgressDialog(SituacaoQuadra.this);
		
			protected void onPreExecute() {
				this.dialog.setOnKeyListener(new OnKeyListener() {
					
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
						if(keyCode == KeyEvent.KEYCODE_BACK){
							Toast.makeText(getBaseContext(), "Sua Pesquisa foi cancelada!", Toast.LENGTH_LONG).show();
							thread_exec.cancel(true);
							
						}
						return false;
					}
				});
				this.dialog.setTitle("Aguarde");
				this.dialog.setMessage("Processando Erros...");
				this.dialog.show();
			}
			
			protected Void doInBackground(final String... args) {
				try {
					pesquisaErrosInscricao(inscricao);
					
					if(array_validacoes.size() > 0){
	    				Intent winErros = new Intent(SituacaoQuadra.this, Erros.class);
	    				winErros.putStringArrayListExtra("array_validacoes", array_validacoes);
	    				winErros.putExtra("colocaBotao", colocaBotao);
	    				winErros.putExtra("colocaBotao2", colocaBotao2);
	    				winErros.putExtra("tipo","quadra");
	    				winErros.putExtra("inscricao",inscricao);
	    				
	    				startActivityForResult(winErros, 1);
	    				erroPesquisa = "";
			    }else{
			    	   erroPesquisa= "Não existe Erro ou a Inscrição é Nova!";
			       }
					
					
					
				} catch (Exception e) {
					Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadPesquisaErros: "+e.getMessage());
				}
				return null;
			}
			
			@Override
			protected void onProgressUpdate(Long... value) {
			super.onProgressUpdate(value);
				
			}
		
			protected void onPostExecute(final Void unused) {
				if (this.dialog.isShowing()) {
					this.dialog.dismiss();
				}
				
				if(!erroPesquisa.equalsIgnoreCase("")){
				Toast.makeText(getBaseContext(), erroPesquisa, Toast.LENGTH_LONG).show();
				}
				 thread_exec.cancel(true);
				 finish();
			}
		}//AsyncTask
	
	  public void pesquisaErrosInscricao(String inscricao){
		
		if(TemConexao()){
		
				try {
			        objConexao = new HttpConection();
					objConexao.setDados("o","25");
					objConexao.setDados("nu_insc_imbl",inscricao);
					JSONObject retorno = objConexao.envia();
					JSONArray resultados = retorno.getJSONArray("results");
					 JSONObject rec = resultados.getJSONObject(0);
					 if(rec.get("ok").equals("3")){
						 
						
					 
					 }else{
						
					for (int i = 0; i < resultados.length(); ++i) {
						rec = resultados.getJSONObject(i);
											colocaBotao = "true";
									    	colocaBotao2 = "true";
									    	
									    	String campo =""; 
									    	String descricao = "";
									    	
									    	campo = rec.getString("campo");
									     descricao = rec.getString("descricao");
									     array_validacoes.add(campo+" - "+descricao); 
									   
								}
					 }
				
					}catch (Exception e){
						Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro método pesquisaErrosInscricao (SituacaoQuadra):"+e.toString());	
							
					}
		          
				
				
		}else{
			
		
			return;
			
		   }
		
	}

	
	


}
