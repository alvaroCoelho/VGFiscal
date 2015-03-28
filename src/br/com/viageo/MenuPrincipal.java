package br.com.viageo;

import java.io.File;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import br.com.viageo.banco.BancoBairro;
import br.com.viageo.banco.BancoFormulario;
import br.com.viageo.banco.BancoLogradouro;
import br.com.viageo.banco.BancoPessoa;
import br.com.viageo.banco.BancoQuadra;
import br.com.viageo.entidade.FormularioEntidade;
import br.com.viageo.entidade.Pessoa;
import br.com.viageo.entidade.Quadra;

public class MenuPrincipal extends ListActivity implements OnClickListener {


	Button btnPesquisa,btnDescarregar;
	Button btnInserirImovel,btnGerenciarQuadras;
	ImageButton btnImgSair, ibtnEnviarFotos;
	EditText etPesquisa;
	String inscricao = null;
	String o = null;
	HttpConection objConexao = null;
	ProgressDialog dialog;
	MenuPrincipal ObjectObjMenuPrincipal = this;
	private ArrayList<MyData> list = null;
	Intent winFormulario;
	Intent winSituacaoQuadra;
	String editando;
	
	
	UploadFotos objUpload = new UploadFotos(MenuPrincipal.this);
	AsyncTask<String, Long, Void> thread_exec;
	AsyncTask<String, Long, Void> thread_exec_envioFoto;
	AsyncTask<String, Long, Void> thread_execFormEdit;
	AsyncTask<String, Long, Void> thread_execInserirImovel;
	
	
	
	 BancoFormulario formulario = new BancoFormulario(this);
	
	 BancoQuadra quadra = new BancoQuadra(this);
	 BancoLogradouro logradouro = new BancoLogradouro(this);
	 BancoPessoa pessoa = new BancoPessoa(this);
	 BancoBairro bairro = new BancoBairro(this);
	 
	 	
	 	 ArrayList<Quadra> listaQuadraBD = new ArrayList<Quadra>();
	 	 ArrayList<Quadra> listaQuadrasWEB = new ArrayList<Quadra>();
	 	 ArrayList<Quadra> listaQuadrasNovas = new ArrayList<Quadra>();
	 	 ArrayList<String> listaLogradouros = new ArrayList<String>(); 
	 	 ArrayList<String> listaPessoas = new ArrayList<String>();
	 	 ArrayList<FormularioEntidade> listaInscricoes = new ArrayList<FormularioEntidade>();
	 	 ArrayList<FormularioEntidade> listaInscricoesTablet = new ArrayList<FormularioEntidade>();
	 	 
	 	
	
	String erroPesquisa = null;
	String quadras_novas = "";
	String erroEnviaFoto = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_principal);
		
		//btnPesquisa = (Button)findViewById(R.id.btnPesquisa);
		//btnPesquisa.setOnClickListener(this);
		inicializandoComponentes();

		//inserindo os eventos
		btnImgSair.setOnClickListener(this);
		btnPesquisa.setOnClickListener(this);
		btnInserirImovel.setOnClickListener(this);
		ibtnEnviarFotos.setOnClickListener(this);
		btnGerenciarQuadras.setOnClickListener(this);
		btnDescarregar.setOnClickListener(this);
		
		
		
		listaInscricoesTablet = formulario.PesquisaInscricoesTablet();
		
		 
		 if(!listaInscricoesTablet.isEmpty()){
			 chamaEdicaoInscricoes();
	   	 }
		
		criarPastaFotos();
	}
	
	/* Botao Voltar - Controle */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	moveTaskToBack(true);
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

	public void inicializandoComponentes(){

    
		btnImgSair = (ImageButton)findViewById(R.id.btnImgSair);
		ibtnEnviarFotos = (ImageButton)findViewById(R.id.ibtnEnviarFotos);
		btnPesquisa = (Button)findViewById(R.id.btnProcurar);
		etPesquisa = (EditText) findViewById(R.id.etPesquisa);
		btnInserirImovel = (Button)findViewById(R.id.btnInserirImovel);
		btnGerenciarQuadras = (Button)findViewById(R.id.btnGerenciarQuadras);
		btnDescarregar = (Button)findViewById(R.id.btnDescarregar);
	}
	 
    private boolean TemConexao() { 
        boolean lblnRet = false;
        try
        {
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
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btnImgSair:
				super.finish();
				
				Intent winMain = new Intent(MenuPrincipal.this, Main.class);
				startActivity(winMain);
			break;
			
		case R.id.btnProcurar:
			/*	if(TemConexao()){
					thread_exec = new threadPesquisar().execute();
				}else{*/
					//PESQUISA INSCRIÇÃO NO BANCO----------------------------------------------------------------------
					
				     thread_exec = new threadPesquisarBD().execute();
			       //---------------------------------------------------------------------------------------------------     
					
				//}
			break;				
	     	
		case R.id.btnInserirImovel:
				//Intent winFormulario = new Intent("android.intent.action.FORMULARIO");
				//winFormulario.putExtra("inscricao", "");
				
				thread_execInserirImovel = new threadInserirImovel().execute();
				/*
				startActivityForResult(winFormulario, 1);
				*/
			break;
			
		case R.id.ibtnEnviarFotos:	
			
			if(TemConexao()){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Você tem certeza que deseja transferir as imagens para o servidor?")
			    	.setCancelable(false)
			    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    		public void onClick(DialogInterface dialog, int id) {
			    			thread_exec_envioFoto = new threadEnviaFotos().execute();
							       	}
			       }).setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
			    	   @Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}else{
				Toast.makeText(getBaseContext(), "Sem conexão com a internet. \nFavor verifique o acesso e tente novamente.", Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.btnGerenciarQuadras:
			Intent winPesquisaPadraoQuadra = new Intent(this, PesquisaPadrao.class);
			winPesquisaPadraoQuadra.putExtra("tp_pesquisa", 12);
			winPesquisaPadraoQuadra.putExtra("pesquisa", "");
			startActivityForResult(winPesquisaPadraoQuadra, 1);
		   
			break;
			
		case R.id.btnDescarregar:
			
			if(TemConexao()){
				
			thread_exec = new threadSincronizar().execute();
			
			}else{
				
				Toast.makeText(getBaseContext(), "Sem conexão de internet.\nFavor verifique o acesso e tente novamente", Toast.LENGTH_LONG).show();
				
			}
			
			break;
			
		  default:
			break;
		}
	}	 

	private class threadEnviaFotos extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(MenuPrincipal.this);
		
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Envio das fotos foi cancelada!", Toast.LENGTH_LONG).show();
						thread_exec_envioFoto.cancel(true);
		
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Enviando...");
			this.dialog.show();
		}
		
		protected Void doInBackground(final String... args) {
			try {
				String ff = Environment.getExternalStorageDirectory() + "/VgFiscalFotos";
    			File dir = new File( ff );  
    			File[] fileNames = dir.listFiles();
    			if(fileNames.length != 0){
    				int quantia = 5;
    				if(fileNames.length < quantia){
    					quantia = fileNames.length;
    					objUpload.enviandoTodasFotos(quantia);
    				}else{
    					int inteiro = fileNames.length / quantia;
    					int resultado = fileNames.length-(inteiro*quantia);
    					for(int k=0; k < (inteiro+1); k++){
    						if(k == inteiro){
    							objUpload.enviandoTodasFotos(resultado);
    						}else{
    							objUpload.enviandoTodasFotos(quantia);
    						}
    					}
    				}
    	
    			}else{
    				erroEnviaFoto = "Não existe Foto para ser enviada!";
    			}
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadEnviaFotos: "+e.getMessage());
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
			 retornoEnviaFotos();
		}
	}//AsyncTask
	
	public void retornoEnviaFotos(){
		if(erroEnviaFoto != null){
			colocaAlert(erroEnviaFoto);
			erroEnviaFoto = null;
		}else{
			colocaAlert("Foram enviadas todas as fotos para o sistema!");
		}
		thread_exec_envioFoto.cancel(true);
		thread_exec_envioFoto = null;
	}
	
	private void colocaAlert(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg)
	    	.setCancelable(false)
	    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			dialog.cancel();
		       	}
	       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void criarPastaFotos(){
		
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		
		if ((mExternalStorageAvailable == true) && (mExternalStorageWriteable == true)){			
			File folder = new File(Environment.getExternalStorageDirectory() + "/VgFiscalFotos");
			boolean success = false;
			if (!folder.exists()) {
			    success = folder.mkdir();
			}
			if (!success) {
			} else {
				
			}
		}/*else{
			if ((mExternalStorageAvailable == true) && (mExternalStorageWriteable == false)){				
				colocaAlert("Você está sem permissão de escrita no SD Card. Reinicie o Aparelho.");				
			}else{				
				colocaAlert("O seu SD Card está inacessível. Reinicie o Aparelho.");
			}
		}*/
	}
	
	private class threadPesquisar extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(MenuPrincipal.this);
		
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
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
		
		protected Void doInBackground(final String... args) {
			try {
				pesquisarInscricoes();
				
				 
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadPesquisar: "+e.getMessage());
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
			 montaListaView();
			 
		
			
			 
		}
	}//AsyncTask
	
	private class threadPesquisarBD extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(MenuPrincipal.this);
		
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
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
		
		protected Void doInBackground(final String... args) {
			try {
				pesquisarInscricoesBD();
				
				 
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadPesquisarBD: "+e.getMessage());
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
			 montaListaView();
			 
		
			
			 
		}
	}
	
	public void montaListaView(){
		if(erroPesquisa != null){
			Toast.makeText(getBaseContext(), erroPesquisa, Toast.LENGTH_LONG).show();
			this.getListView().setAdapter(new MyListAdapter(this,list,"1"));
			erroPesquisa = null;
		}else{
			this.getListView().setAdapter(new MyListAdapter(this,list,"1"));
	      
			this.getListView().setFastScrollEnabled(true);
		}
		thread_exec.cancel(true);
		thread_exec = null;
		
		
	}
	
	private void pesquisarInscricoesBD(){
		BancoFormulario formularioBanco = new BancoFormulario(this);
		list = formularioBanco.ListaInscricoes(etPesquisa.getText().toString());
		if (list.isEmpty()){
			erroPesquisa = "Nenhum Resultado Encontrado ou Sistema não Atualizado!";
			
		}
		
	}
	
	private void pesquisarInscricoes() {
		try {
			
			inscricao = etPesquisa.getText().toString();				
			o = "2";
			
			objConexao = new HttpConection();
			objConexao.setDados("inscricao", inscricao);
			objConexao.setDados("o", o);
			JSONObject retorno = objConexao.envia();
			JSONArray resultados = retorno.getJSONArray("results");
			String tmp = resultados.getJSONObject(0).getString("ok");
			
			
			   
			
		
			if (tmp.equals("1")){
				String inscricao = null;
		        list = new ArrayList<MyData>();
		        
				for (int i = 0; i < resultados.length(); ++i) {
				    JSONObject rec = resultados.getJSONObject(i);
				
				    inscricao = rec.getString("inscricao");
				    String contribuinte = rec.getString("contribuinte");
				    String logradouro = rec.getString("logradouro");
				 
				    StringBuilder stringBuilder = new StringBuilder(inscricao); 
				    stringBuilder.insert(inscricao.length() - 2,'*');
				    String insc = stringBuilder.toString();
				    list.add(new MyData(insc, contribuinte, logradouro));
				}

		 
   			}else{
   				if(tmp.equals("3")){
   					erroPesquisa = resultados.getJSONObject(0).getString("erro");
   		
   				}else{
   					if(tmp.equals("0")){
   						erroPesquisa = "ERRO Conexão: "+retorno.getString("erro");
   		
   					}else{
   						erroPesquisa = "ERRO ao Pesquisar: "+resultados.getJSONObject(0).getString("erro");
   		
   					}
   				}
   			}
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método pesquisarInscricoes (MenuPrincipal): "+e.getMessage());
	
		}
	}
	
	private class threadInserirImovel extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(MenuPrincipal.this);
		
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Abertura do formulário foi cancelada!", Toast.LENGTH_LONG).show();
						thread_execInserirImovel.cancel(true);
						
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
				
				Intent winFormulario = new Intent("android.intent.action.FORMULARIO");
				winFormulario.putExtra("inscricao", "");
				startActivityForResult(winFormulario, 1);
			
				
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadInserirImovel (MenuPrincipal): "+e.getMessage());
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
			finalizaThreadInserirImovel();
		}
	}		
	
	public void finalizaThreadInserirImovel(){
		thread_execInserirImovel.cancel(true);
		thread_execInserirImovel = null;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 1){
			
			sair();
			
			
		}
	}
	
	@Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
	
	
	        winFormulario = new Intent("android.intent.action.FORMULARIO");
	        String inscricao = this.list.get(position).getText1();
	        String contribuinte = this.list.get(position).getText2();
	        String cdLogradouro = this.list.get(position).getText3();

	/*        
	    	    try{
	                objConexao = new HttpConection();
	     			objConexao.setDados("o", "2");
	     			objConexao.setDados("inscricao", inscricao);	    			
	    			JSONObject retorno = objConexao.envia();
	    			JSONArray resultados = retorno.getJSONArray("results");

	    			for (int i = 0; i < resultados.length(); ++i) {
	    			    JSONObject rec = resultados.getJSONObject(i);
	    			    	    			    
	    			    if (rec.getString("editando").equalsIgnoreCase("t")){
	    			       Toast.makeText(getBaseContext(), "Cadastro Imobiliário sendo editado!", Toast.LENGTH_LONG).show();
	    			     
	    			       return; 
	    			     
	    			    }
	    			    
	    			} 
	    			
	             } catch (Exception e){	    	   
	    	         Toast.makeText(getBaseContext(), "ERRO FATAL full", Toast.LENGTH_LONG).show();
	    	   
	             } */
	               
	        winFormulario.putExtra("inscricao", inscricao);
	        winFormulario.putExtra("contribuinte", contribuinte);
	        winFormulario.putExtra("cdlogradouro", cdLogradouro);
	      
	       
	        thread_execFormEdit = new threadAbreFormularioEdit().execute();			
	
		}
  
	
	private class threadAbreFormularioEdit extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(MenuPrincipal.this);


		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Abertura do formulário foi cancelada!", Toast.LENGTH_LONG).show();
						thread_execFormEdit.cancel(true);
						
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
				startActivityForResult(winFormulario, 1);
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadAbreFormularioEdit (MenuPrincipal): "+e.getMessage());
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
			 finalizaThreadFormEdit();
		}
	}
	
	public void finalizaThreadFormEdit(){
		thread_execFormEdit.cancel(true);
		thread_execFormEdit = null;
		
	}
		
    public void sair(){
    	super.finish();
		
		Intent winMain = new Intent(MenuPrincipal.this, Main.class);
		startActivity(winMain);
    }
    
   private void chamaEdicaoInscricoes(){
    	
    	winSituacaoQuadra = new Intent(this, PesquisaPadrao.class);
    	winSituacaoQuadra.putExtra("tp_pesquisa", 12);
    	winSituacaoQuadra.putExtra("pesquisa", "QuadrasPendentes");
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Você possue Inscrições Armazenadas somente no Tablet, deseja sincronizar?")
	    	.setCancelable(true)
	    	.setPositiveButton("Sincronizar", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int id) {
	    			
	    			sincronizar();
	    		
	    		
	    			
		       	}
	    		
	       }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	        	 dialog.cancel(); 
       	  
	           }
	       });
		AlertDialog alert = builder.create();
		alert.show();
    
    
    
    }
    
    
	private class threadSincronizar extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(MenuPrincipal.this);
		
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Sua atualização foi cancelada!", Toast.LENGTH_LONG).show();
						thread_exec.cancel(true);
		
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Sincronizando...");
			this.dialog.show();
		}
		
		protected Void doInBackground(final String... args) {
			try {
				 
				    if(sincronizar()){
					
					     erroPesquisa = "Atualização realizada com Sucesso!";
					 
				    }else{
				    	
				    	 erroPesquisa = "Sistema não sincronizado!";
				    	
				    }
				
				 
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadSincronizar: "+e.getMessage());
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
			 
			Toast.makeText(getBaseContext(),erroPesquisa, Toast.LENGTH_SHORT).show();
			if (!quadras_novas.equalsIgnoreCase("")){
			Toast.makeText(getBaseContext(),"Quadras Adicionadas: "+quadras_novas, Toast.LENGTH_LONG).show();
			}
			quadras_novas = "";
			 thread_exec.cancel(true);
			 erroPesquisa = null;
		}
	}
    
	public boolean sincronizar(){
			
			try {
				
				
				//MÉTODOS PARA INSERÇÕES NO BANCO DE DADOS----------------------------------------------------------------
				
				
	   		 	 SharedPreferences preferencia = getSharedPreferences("VGFISCAL",0);
	   		 	 String usuario = preferencia.getString("usuario","");
	   		 	String id_usuario = preferencia.getString("id_usuario",""); 
	   		 	 
				//CARREGA NO - BD TABLET A TABELA PLAN BAIRRO  
				  bairro.descarregarBaseBairro();
				  bairro.carregarBaseBairro();
	   		 	  
				//--------------------------------------------------------------------	  
				  
				  
	   		 	  //LISTA DE QUADRAS VINDA DA WEB
	   		 	  listaQuadrasWEB = quadra.listaQuadrasWEB(usuario);
	   		 	  
	   		 	  //LISTA DE QUADRAS QUE ESTAM NO BD
	   		 	  listaQuadraBD = quadra.pesquisaQuadrasBD(id_usuario);	
	   		 	  String cd_quadra = new String();
	   		 	 
	   		 	
	   		 	  
	   		 	  //PERCORRE A LISTA DE QUADRAS DA WEB E VERIFICA O QUE NÃO TEM NO - BD TABLET - 
	   		 	  for (int i = 0; i < listaQuadrasWEB.size(); i++) {
	   		 		cd_quadra = listaQuadrasWEB.get(i).getCd_quadra();
	   		 		
	   		 		//INSERE A QUADRA QUE NÃO ESTÁ NO - BD TABLET - 
	   		 		if(quadra.consultaQuadraBD(cd_quadra)){
	   		 		 Quadra nova = new Quadra();
                     nova = listaQuadrasWEB.get(i);
                     quadra.inserir(nova);
                     listaQuadrasNovas.add(nova);
                     quadras_novas += quadras_novas+"\n"+nova.getCd_quadra();  
	   		 		  
	   		 		   }
	            
					
				}
	   		 	  cd_quadra = null;
	   		 	  
	   		 	  //--------------------------------------------------------------------------
	   		 	  
	   		 	  
	   		 	  // INSERE NO- BD TABLET - PESSOAS,LOGRADOUROS E INSCRICOES QUE ESTÃO NA WEB E NÃO ESTÃO NO TABLET POR QUADRA
	   		 	  
	   		     pessoa.carregaPessoasQuadras(listaQuadrasNovas);
	   		  	 logradouro.carregaLogradourosQuadras(listaQuadrasNovas);
	   		  	 formulario.carregaInscricoesQuadras(listaQuadrasNovas);	
	   		  	 
	   		  	 listaQuadrasNovas = new ArrayList<Quadra>();
	   		  	  
	   		  	  //---------------------------------------------------------------------------
	   		  	  
	   		  	 
	   			 //ENVIA PARA WEB TODAS AS INSCRICOES E PESSOAS QUE FORAM SÓ CADASTRADAS NO TABLET 
		   		  
			   		ArrayList<Pessoa> listaPessoas3 = pessoa.pesquisaPessoas();
			   		ArrayList<FormularioEntidade>  listaForm = formulario.PesquisaInscricoesTablet(); 	 
			   		  pessoa.atualizarPessoasWEB(listaPessoas3);
					  formulario.atualizarInscricoesWEB(listaForm,usuario);	
			   		  	 
			   		  	 
			   		//---------------------------------------------------------------------------  	 
		   		  	 
	   		  	 
	   		  	  
	   		  	  
	   		  	  // PERCORRE A LISTA DE QUADRAS DO TABLET E VERIFICA NA WEB AS INSCRIÇÕES QUE NÃO ESTÃO ATUALIZADAS NO TABLET
	   		  	    
	   		  	     for (int i = 0; i < listaQuadraBD.size(); i++) {
	   		  	    	 
	   		  	         formulario.carregaListaInscricoesdaWEB(listaQuadraBD.get(i).getCd_quadra());
	   		  	          }     
	   		  	    	 
						
					
	   		  	  
	   		  	  //---------------------------------------------------------------------------	  
	   		  	  
	   		  	  
	   		 	
		   		  	 
		   		//CARREGA NO - BD TABLET- TODOS OS LOGRADOUROS (QUE AINDA NÃO ESTÃO NO BD) DAS PESSOAS QUE ESTÃO NO TABLET
		   		  	 
		   		     ArrayList<Pessoa> listaPessoas2 = pessoa.pesquisaLogradourosPessoas();
		   		  	 logradouro.carregaLogradourosPessoa(listaPessoas2);
		   		  	 
		   		
		   	
	   		 	  
		   	    //DESCARREGA DO - BD TABLET - TODA A INFORMAÇÃO DA QUADRA QUE NÃO ESTÁ MAIS ASSOCIADA AO USUÁRIO NA WEB
		   		 	  
		   		 	  for (int i = 0; i < listaQuadraBD.size(); i++) {
		   		 		  cd_quadra = listaQuadraBD.get(i).getCd_quadra();
		   		 		  
		   		 		  if(quadra.consultaQuadraWEB(cd_quadra)){
		   		 			
		   		 			 
		   		 			 listaLogradouros = quadra.listaLogradourosQuadra(cd_quadra);
	                            for (int j = 0; j < listaLogradouros.size(); j++) {
	                           	 logradouro.descarregarBaseLogradouro(listaLogradouros.get(j));
									
				     				 }
	                            
	                        listaPessoas = quadra.listaPessoasQuadra(cd_quadra);
	                            for (int h = 0; h < listaPessoas.size(); h++) {
	                           	 pessoa.descarregarBasePessoa(listaPessoas.get(h));
									
								}
	                        
	                            quadra.descarregarBaseQuadras(cd_quadra);
	  	   		 			     formulario.descarregarBaseFormulario(cd_quadra);
	                            
		   		 			  
		   		 	    	  }
		   		 		
				    	}
		   		 	 
		   		 	  
		   		 	return true;
				
	              //----------------------------------------------------------------------------------------------------------
					
					
				
			           } catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método sincronizar: "+e.getMessage());
				
				return false;
			}
					
			
		}
    
    
 	
    
	
	
}
