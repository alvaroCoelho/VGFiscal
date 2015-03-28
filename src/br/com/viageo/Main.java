package br.com.viageo;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{
	
	//String url_php = "http://192.168.0.15/geo_goianesia/conexaoAndroid/conexao_android.php";
	//String url_php = "http://geo.goianesia.go.gov.br/geo_goianesia/conexaoAndroid/conexao_android.php";
	
	EditText etUsuario, etSenha;
	Button btnConectar;	
	String usuario,Nmusuario,idUsuario, senha, o, ok;	
	HttpConection objConexao;
	AsyncTask<String, Long, Void> thread_exec;
	
	String erroLogar = null;
	JSONArray resultados;
	
	
 
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
  
        
        initialise();
    }

	private void initialise() {
		etUsuario = (EditText) findViewById(R.id.etUsuario);
		etSenha = (EditText) findViewById(R.id.etSenha);
		btnConectar = (Button) findViewById(R.id.btnConectar);		
		
		btnConectar.setOnClickListener(this);
	}
	 
    private boolean TemConexao() { 
        boolean lblnRet = false;
        try {
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
      
        Toast.makeText(getBaseContext(), "ERRO DE CONECTIVIDADE = "+msg, Toast.LENGTH_LONG).show();
    } 

	@Override
	public void onClick(View v) {
		
 if (TemConexao()){
			
			 thread_exec = new threadLogar().execute();
			
		}else{
			
		     thread_exec = new threadLogarOff().execute();
	
		
		}
		
	}
	
	private class threadLogar extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(Main.this);
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						try {
							Toast.makeText(getBaseContext(), "Você cancelou o processo de fazer Login!", Toast.LENGTH_LONG).show();
							thread_exec.cancel(true);
						} catch (Exception e) {
							 Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadLogar (Main): "+e.getMessage());
						}
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
		protected Void doInBackground(final String... args) {
			try {
					logar();
					
			} catch (Exception e) {
				
					Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadLogar (Main): "+e.getMessage());
					
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
			
	 
			 mostraRetornoLogar();
			 
			 
			 
		}
		@Override
		protected void onCancelled() {
			super.onCancelled();
			
		}
		
	}//AsyncTask
		
	private class threadLogarOff extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(Main.this);
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						try {
							Toast.makeText(getBaseContext(), "Você cancelou o processo de fazer Login!", Toast.LENGTH_LONG).show();
							thread_exec.cancel(true);
						} catch (Exception e) {
							 Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadLogar (Main): "+e.getMessage());
						}
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
		protected Void doInBackground(final String... args) {
			try {
				logarOff();
					
			} catch (Exception e) {
				
					Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadLogar (Main): "+e.getMessage());
					
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
			
	 
			 mostraRetornoLogar();
			 
			 
			 
		}
		@Override
		protected void onCancelled() {
			super.onCancelled();
			
		}
		
	}//AsyncTask
	
	public void logar(){
		usuario = etUsuario.getText().toString();
		senha = etSenha.getText().toString();
		o = "1";
		objConexao = new HttpConection();
		objConexao.setDados("usuario", usuario);
		objConexao.setDados("senha", senha);
		objConexao.setDados("o", o);
		
		try {
			
			JSONObject retorno = objConexao.envia();
			
			ok = retorno.getString("ok");
			if(ok.equals("1")){

				SharedPreferences sp = getSharedPreferences("VGFISCAL", 0);
				

				SharedPreferences.Editor spedit = sp.edit();
				
				
				spedit.putString("user", retorno.getString("user"));
				spedit.putString("pass", retorno.getString("pass"));
				spedit.putString("usuario", retorno.getString("nome"));
				spedit.putString("id_usuario", retorno.getString("id_usuario"));
				
				Nmusuario = retorno.getString("nome");
				idUsuario = retorno.getString("id_usuario");
				spedit.commit();
			
				
			
			

			
				Intent winPrincipal = new Intent("android.intent.action.PRINCIPAL");
				startActivity(winPrincipal);
			}else{
				
				erroLogar = retorno.getString("erro");
			}
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método logar (Main): "+e.getMessage());
			erroLogar = "Erro Fatal!";
		}	
		
		
		
		
	}
		
    public void logarOff(){
    	
    	 SharedPreferences preferencia = getSharedPreferences("VGFISCAL",0);
         String user = preferencia.getString("user","");
         String pass = preferencia.getString("pass","");
    	
         usuario = etUsuario.getText().toString();
 		 senha = etSenha.getText().toString();
    	
 		 if(user.equalsIgnoreCase(usuario)&&pass.equalsIgnoreCase(senha)){
 			 

				Intent winPrincipal = new Intent("android.intent.action.PRINCIPAL");
				startActivity(winPrincipal);
 			 
 		   }else{
 			  erroLogar = "Login ou senha incorretos!";
 			   
 		   }
    	
    }
 	
	private void mostraRetornoLogar() {
		if(erroLogar != null){
			Toast.makeText(getBaseContext(), erroLogar, Toast.LENGTH_LONG).show();
			erroLogar = null;	
		}
		
		thread_exec.cancel(true);
		thread_exec = null;
		

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
	
}