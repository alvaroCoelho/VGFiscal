package br.com.viageo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.viageo.banco.BancoBairro;
import br.com.viageo.banco.BancoLogradouro;
import br.com.viageo.banco.BancoPessoa;
import br.com.viageo.banco.BancoQuadra;
import br.com.viageo.entidade.Bairro;
import br.com.viageo.entidade.Logradouro;
import br.com.viageo.entidade.Pessoa;
import br.com.viageo.entidade.Quadra;

public class PesquisaPadrao extends ListActivity implements OnClickListener {
	int pesquisa;
	private ArrayList<MyData> list = null;
	
	JSONArray resultados;
	ImageButton btnAddCadastro;
	AsyncTask<String, Long, Void> thread_exec;
	String erroPesquisa = null;
	String nu_insc_imbl = null;
	Context context = this;
	ArrayList<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	ArrayList<Logradouro> listaLogradouros = new ArrayList<Logradouro>();
	ArrayList<Bairro> listaBairros = new ArrayList<Bairro>();
	ArrayList<Quadra> listaQuadras = new ArrayList<Quadra>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pesquisa_padrao);
		
		Button btnPesquisaPadrao = (Button)findViewById(R.id.btnPesquisaPadrao);
		btnPesquisaPadrao.setOnClickListener(this);
		
		btnAddCadastro = (ImageButton)findViewById(R.id.btnAddCadastro);
		btnAddCadastro.setOnClickListener(this);
		
		setPesquisa();
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
	
	private void setPesquisa() {
		Intent tp_pesquisa = getIntent();
		pesquisa = tp_pesquisa.getIntExtra("tp_pesquisa", 0);
		switch (pesquisa) {
		case 1: // pessoa
			inicializaPessoa();	
			break;
		case 2: //"logradouro":
			inicializaLogr();
			break;
		case 3: //"bairro":
			inicializaBairro();			
			break;
		case 7: //"logradouro2":
			inicializaLogr();
			break;
		case 8: //"logradouro3":
			inicializaLogr();
			break;
		case 9: //"logradouro4":
			inicializaLogr();
			break;
		case 10: //"logradouroPessoa":
			inicializaLogr();
			break;
		case 11: //"BairroPessoa":
			inicializaBairro();			
			break;
			case 12: //"Quadra":
			inicializaQuadra();			
			break;
			
			
		default:
			break;
		}
		
		if(pesquisa != 1){
			tirarBotaoAdd();
		}
	
		
	}
	
	private void tirarBotaoAdd(){
		btnAddCadastro.setVisibility(View.GONE);
	}
	
	public void montaListaView(){
		if(erroPesquisa != null){
			Toast.makeText(getBaseContext(), erroPesquisa, Toast.LENGTH_LONG).show();
			this.getListView().setAdapter(new MyListAdapter(this, list,"1"));
			erroPesquisa = null;
		}else{
			this.getListView().setAdapter(new MyListAdapter(this, list,"1"));
	        // Habilita o scroll rápido na lista
			this.getListView().setFastScrollEnabled(true);
		}
		
		//thread_exec = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddCadastro:
			//if (TemConexao()){
				Intent winAddPessoa = new Intent(PesquisaPadrao.this, CadastroPessoa.class);
				winAddPessoa.putExtra("edicao", "false");
				winAddPessoa.putExtra("inscricao",nu_insc_imbl);
				startActivityForResult(winAddPessoa, 0);
				finish();
			//}else{
			//	Toast.makeText(getBaseContext(), "Sem conexão com a internet. \nFavor verifique o acesso e tente novamente.", Toast.LENGTH_LONG).show();
			//}
			break;
		case R.id.btnPesquisaPadrao:
			thread_exec = new threadPesquisaPadrao().execute();
			break;

		default:
			break;
		}
		
	}
	
	private class threadPesquisaPadrao extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(PesquisaPadrao.this);
	
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
				onClickBtnPesquisa();
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadPesquisaPadrao: "+e.getMessage());
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
			 thread_exec.cancel(true);
		}
	}//AsyncTask
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 1){
			
			Toast.makeText(getBaseContext(), "retornou ao PesquisaPadrao", Toast.LENGTH_LONG).show();
		}
	}

	public void onClickBtnPesquisa(){

		switch (pesquisa) {
		case 1: // pessoa
			pesquisaPessoa();	
			break;
		case 2: //"logradouro":
			pesquisaLogr();
			break;
		case 3: //"bairro":
			pesquisaBairro();			
			break;
		case 7: //"logradouro2":
			pesquisaLogr();
			break;
		case 8: //"logradouro3":
			pesquisaLogr();
			break;
		case 9: //"logradouro4":
			pesquisaLogr();
			break;
		case 10: //"logradouroPessoa":
			pesquisaLogr();
			break;
		case 11: //"BairroPessoa":
			pesquisaBairro();
			break;
			
		case 12: //"Quadra":
			pesquisaQuadra();
			break;
		default:
			break;
		}
	}
	
	public void inicializaPessoa(){
		nu_insc_imbl  = getIntent().getStringExtra("inscricao"); 
	
		TextView txtTituloPesquisaPadrao = (TextView)findViewById(R.id.txtTituloPesquisaPadrao);
		txtTituloPesquisaPadrao.setText("Pesquisar Proprietário");
		
		TextView txtPesquisarPor = (TextView)findViewById(R.id.txtPesquisarPor);
		txtPesquisarPor.setText("Pesquisar por: Nome ou CPF"); 
		
	}
	
	public void inicializaLogr(){
		
		TextView txtTituloPesquisaPadrao = (TextView)findViewById(R.id.txtTituloPesquisaPadrao);
		txtTituloPesquisaPadrao.setText("Pesquisar Logradouro");
		
		TextView txtPesquisarPor = (TextView)findViewById(R.id.txtPesquisarPor);
		txtPesquisarPor.setText("Pesquisar por: Nome");
		
	}
	
	public void inicializaBairro(){
		
		TextView txtTituloPesquisaPadrao = (TextView)findViewById(R.id.txtTituloPesquisaPadrao);
		txtTituloPesquisaPadrao.setText("Pesquisar Bairro");
		
		TextView txtPesquisarPor = (TextView)findViewById(R.id.txtPesquisarPor);
		txtPesquisarPor.setText("Pesquisar por: Nome");
		
	}	
	
	public void inicializaQuadra(){
		
		TextView txtTituloPesquisaPadrao = (TextView)findViewById(R.id.txtTituloPesquisaPadrao);
		txtTituloPesquisaPadrao.setText("Situação Quadra");
		
		TextView txtPesquisarPor = (TextView)findViewById(R.id.txtPesquisarPor);
		txtPesquisarPor.setText("Pesquisar por: Quadra");
		
		String inscricao = getIntent().getStringExtra("pesquisa"); 
		
		//INICIALIZA LISTA DE QUADRAS PENDENTES
		if(inscricao.equals("QuadrasPendentes")) {
		
			BancoQuadra bancoQuadra = new BancoQuadra(context);
			listaQuadras = bancoQuadra.pesquisaInscricoesPendentes();
			ArrayList<MyData> lista = new ArrayList<MyData>();
			
		     for(int i = 0; i < listaQuadras.size(); ++i ){
		    	 String cd_quadra = listaQuadras.get(i).getCd_quadra();
		       	int total = bancoQuadra.contaInscricoes(cd_quadra);
		      	String totalInscricoes = "";
		    	if (total==0){
		    		totalInscricoes = "Nova!";
		    	}else{
		    		totalInscricoes = "Total de Inscrições: "+total; 
		    	     }
		    	
		    	lista.add( new MyData("Quadra: "+cd_quadra,totalInscricoes, ""));
		 
		       }
		
		     
		        list = lista;
		        
		        if(listaQuadras.isEmpty()){					
		         erroPesquisa = "Nenhum Resultado Encontrado!!!";
		        }
			
			
			
			
			montaListaView();
			
		}
		
		
	}	

	public void pesquisaPessoa() {
		
	if (TemConexao()){
			try {
				HttpConection objConexao = new HttpConection();
				objConexao.setDados("o", "6");
				EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
				
				objConexao.setDados("dados", etPesquisaPadrao.getText().toString());
				JSONObject retorno = objConexao.envia();
				resultados = retorno.getJSONArray("results");
				JSONObject rec = resultados.getJSONObject(0);
				
					if(rec.getString("ok").equals("3"))
						
						PesquisaPadrao.this.runOnUiThread(new Runnable() {
				            @Override
				            public void run() {
				                Toast.makeText(PesquisaPadrao.this, "Nenhum Resultado Encontrado!!!", Toast.LENGTH_SHORT).show();
				            }
				        });
				
				
				list = new ArrayList<MyData>();
				 
				for (int i = 0; i < resultados.length(); ++i) {
					rec = resultados.getJSONObject(i);
				    String nome = rec.getString("nome");
				    String cpf = rec.getString("cpf");
				    String endereco = rec.getString("endereco");
				   
				    
				    list.add(new MyData(nome,cpf,endereco));
		
				}
				
		      
		        
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método pesquisaPessoa (PesquisaPadrao): "+e.getMessage());
				erroPesquisa = "Erro: "+e.getMessage();
			}
		}else{
			      //PESQUISA BANCO PESSOA--------------------------------------------------------------------------------------
					BancoPessoa pessoaBanco = new BancoPessoa(context);
					EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
				    listaPessoas = pessoaBanco.pesquisaPessoasBD(etPesquisaPadrao.getText().toString());
					 if(listaPessoas.isEmpty()||listaPessoas==null)	{				
						 erroPesquisa = "Nenhum Resultado Encontrado!!!";	

					 }else{
									list = new ArrayList<MyData>();
									
									for(int i = 0; i < listaPessoas.size(); ++i ){
										String nome = listaPessoas.get(i).getNm_pess();
										String cpf = listaPessoas.get(i).getNu_pess();
										
										list.add(new MyData(nome,cpf,""));
							            }
									 }
					
				//FIM PESQUISA PESSOA BANCO-----------------------------------------------------------------------------------	
					
					
		
		}
		
	}
	
	public void pesquisaLogr() {
	if (TemConexao()){
			try {
				HttpConection objConexao = new HttpConection();
				objConexao.setDados("o", "17");
				EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
				
				objConexao.setDados("nm_logr", etPesquisaPadrao.getText().toString());
				JSONObject retorno = objConexao.envia();
				resultados = retorno.getJSONArray("results");
				 JSONObject rec = resultados.getJSONObject(0);
				
				 if(rec.getString("ok").equals("3"))
					 
					PesquisaPadrao.this.runOnUiThread(new Runnable() {
			            @Override
			            public void run() {
			                Toast.makeText(PesquisaPadrao.this, "Nenhum Resultado Encontrado!!!", Toast.LENGTH_SHORT).show();

			            }
			        });
					
					
				list = new ArrayList<MyData>();
				 
				for (int i = 0; i < resultados.length(); ++i) {
					rec = resultados.getJSONObject(i);
					
				    String cd_logr = rec.getString("cd_logr");
				    
				    String nm_logr;				    
				    if (rec.getString("tp_atrb_logr").trim().equalsIgnoreCase("")){
				        nm_logr = (rec.getString("tp_logr")+" "+rec.getString("nm_logr"));					
				    }else{			    	
					    nm_logr = (rec.getString("tp_logr")+" "+rec.getString("tp_atrb_logr")+" "+rec.getString("nm_logr"));
					    }   
				     String id_logra = rec.getString("id_logra");
				     
				    list.add(new MyData(cd_logr, nm_logr, id_logra));
		
				}
				
		        
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método pesquisaLogr (PesquisaPadrao): "+e.getMessage());	
			}
		}else{
			//PESQUISA BANCO LOGRADOURO--------------------------------------------------------------------------------------------------------------
			   
			    BancoLogradouro logradouroBanco = new BancoLogradouro(context);
			    EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
			   listaLogradouros = logradouroBanco.pesquisaLogradourosBD(etPesquisaPadrao.getText().toString());
			    list = new ArrayList<MyData>(); 
			
			    for(int i = 0; i < listaLogradouros.size(); ++i ){
					String cd_logr = listaLogradouros.get(i).getCd_logr();
					String nm_logr = listaLogradouros.get(i).getLabel();
					String id_logra = listaLogradouros.get(i).getId_logra();
					list.add(new MyData(cd_logr, nm_logr,id_logra));
		            }
		  
		
		
	               if(listaLogradouros.isEmpty())						
					PesquisaPadrao.this.runOnUiThread(new Runnable() {
			            @Override
			            public void run() {
			                Toast.makeText(PesquisaPadrao.this, "Nenhum Resultado Encontrado!!!", Toast.LENGTH_SHORT).show();
			            }
			        }); 
			
			
			
			
			//-------------------------------------------------------------------------------------------------------------------------------------------------
			
			//erroPesquisa = "Sem conexão com a internet. \nFavor verifique o acesso e tente novamente.";
			//Toast.makeText(getBaseContext(), "Sem conexÃ¯Â¿Â½o com a internet. \nFavor verifique o acesso e tente novamente.", Toast.LENGTH_LONG).show();
		}
	}
	
	public void pesquisaBairro() {
	if(TemConexao()){
			try {
				HttpConection objConexao = new HttpConection();
				objConexao.setDados("o", "8");
				EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
				
				objConexao.setDados("nm_bairro", etPesquisaPadrao.getText().toString());
				JSONObject retorno = objConexao.envia();
				resultados = retorno.getJSONArray("results");
				 JSONObject rec = resultados.getJSONObject(0);
				 
					if(rec.getString("ok").equals("3"))
						PesquisaPadrao.this.runOnUiThread(new Runnable() {
				            @Override
				            public void run() {
				                Toast.makeText(PesquisaPadrao.this, "Nenhum Resultado Encontrado!!!", Toast.LENGTH_SHORT).show();
				            }
				        });
				
				list = new ArrayList<MyData>();
				 
				for (int i = 0; i < resultados.length(); ++i) {
				     rec = resultados.getJSONObject(i);				    
				    String cd_bairro = rec.getString("cd_bairro");
				    String nm_bairro = rec.getString("nm_bairro");
				  
				    list.add(new MyData(cd_bairro,nm_bairro, ""));
	
				}
				// Agora vou popular minha lista
				//this.getListView().setAdapter(new MyListAdapter(this, list, "1"));
		        // Habilita o scroll rÃ¯Â¿Â½pido na lista
		        //this.getListView().setFastScrollEnabled(true);
		        
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método pesquisaBairro (pesquisaPadrao): "+e.toString());
				
			}
			
			
		}else{
			
			//PESQUISA BAIRRO BANCO DADOS-----------------------------------------------------------------------------------------------------------------------
		         listaBairros = new ArrayList<Bairro>(); 
			     BancoBairro bairroBanco = new BancoBairro(context);
			     
			     EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
			     listaBairros = bairroBanco.pesquisaBairrosBD(etPesquisaPadrao.getText().toString());
			     list = new ArrayList<MyData>();
			
			      
			     for(int i = 0; i < listaBairros.size(); ++i ){
			    	 String cd_bairro = listaBairros.get(i).getMslink();
			    	 String nm_bairro = listaBairros.get(i).getNm_bairro();
			    	 
			    	 list.add(new MyData(cd_bairro, nm_bairro, ""));
			    	 
			     }
			
			
			        if(listaBairros.isEmpty()){					
			         erroPesquisa = "Nenhum Resultado Encontrado!!!";
			        }
			       
			//-------------------------------------------------------------------------------------------------------------------------------------------------
			
			
		}
			
		
		
	}
	
	public void pesquisaQuadra(){
	
		if(TemConexao()){
			
			
				try{
					
					HttpConection objConexao = new HttpConection();
					objConexao.setDados("o", "22");
					EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
					SharedPreferences preferencia = getSharedPreferences("VGFISCAL",0);
			   		 String usuario = preferencia.getString("id_usuario","");
					
					
					objConexao.setDados("quadra", etPesquisaPadrao.getText().toString());
					objConexao.setDados("id_usuario",usuario);
					JSONObject retorno = objConexao.envia();
					resultados = retorno.getJSONArray("results");
					 JSONObject rec = resultados.getJSONObject(0);	
					 listaQuadras = new ArrayList<Quadra>(); 
					  list = new ArrayList<MyData>();
					 
					if(rec.getString("ok").equalsIgnoreCase("3")){
						 erroPesquisa = "Nenhum Resultado Encontrado!";
						
					}else{
								for (int i = 0; i < resultados.length(); ++i) {
									Quadra nova = new Quadra();
								     rec = resultados.getJSONObject(i);				    
								    String cd_quadra = rec.getString("cd_quadra");
								    nova.setCd_quadra(cd_quadra);
								    listaQuadras.add(nova);
								    String total = rec.getString("total");
								    String totalInscricoes = "";
								    if(total.equalsIgnoreCase("0")||total.equalsIgnoreCase("")){
								    	totalInscricoes = "Nova!";	
							 
								    }else{		    
								    
								    	totalInscricoes = "Total de Inscrições: "+total; 
								         
								        }
								        
								    list.add(new MyData("Quadra: "+cd_quadra,totalInscricoes," "));
					
								}
								
					}		
							
				   }catch (Exception e){
					   
					   Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método pesquisaQuadra (PesquisaPadrao): "+e.toString());
					   
				   }
				
		       }else{
					
					//PESQUISA QUADRAS BANCO DADOS-----------------------------------------------------------------------------------------------------------------------
				         listaQuadras = new ArrayList<Quadra>(); 
					     BancoQuadra quadraBanco = new BancoQuadra(context);
					     
					     EditText etPesquisaPadrao = (EditText)findViewById(R.id.etPesquisaPadrao);
					     listaQuadras = quadraBanco.pesquisaQuadras(etPesquisaPadrao.getText().toString());
					     list = new ArrayList<MyData>();
					     
					      
					     
					     for(int i = 0; i < listaQuadras.size(); ++i ){
					    	 String cd_quadra = listaQuadras.get(i).getCd_quadra();
					    	int total = quadraBanco.contaInscricoes(cd_quadra);
					    	 
					    	String totalInscricoes = "";
					    	if (total==0){
					    		totalInscricoes = "Nova!";
					    	}else{
					    		totalInscricoes = "Total de Inscrições: "+total; 
					    	}
					    	
					    	 list.add(new MyData("Quadra: "+cd_quadra,totalInscricoes, ""));
					    	 
					    	
					    	 
					     }
					
					
					        if(listaQuadras.isEmpty()){					
					         erroPesquisa = "Nenhum Resultado Encontrado!!!";
					        }
					       
					//-------------------------------------------------------------------------------------------------------------------------------------------------
					
					
				}
		
		
		
	}
	
		@Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        try {
			switch (pesquisa) {
			case 1: // PESSOA BANCO
				
				if(!listaPessoas.isEmpty()){
				String nu_pess = listaPessoas.get(position).getNu_pess();
				Intent inForm = new Intent(this,Formulario.class);
				inForm.putExtra("inscricao",nu_insc_imbl);
				inForm.putExtra("tipo","banco");
				inForm.putExtra("cpf",nu_pess);
				startActivity(inForm);
				finish();
				
				}else{
					
					String nu_pess = this.list.get(position).getText2();
					Intent inForm = new Intent(this,Formulario.class);
					inForm.putExtra("inscricao",nu_insc_imbl);
					
					inForm.putExtra("tipo","banco");
					inForm.putExtra("cpf",nu_pess);
					startActivity(inForm);
					finish();
					
					
				}
				
				
			
				/*if(!listaPessoas.isEmpty()){
					String nu_pess = listaPessoas.get(position).getNu_pess();
					Intent intent = getIntent();
					setResult(RESULT_OK, intent);
					intent.putExtra("o", pesquisa);
					intent.putExtra("tipo","banco");
					intent.putExtra("valor",nu_pess);
					finish();
					
				}else{
				//PESSOA WEB			
				JSONObject rec = resultados.getJSONObject(position);
				
				Intent intent = getIntent();
				setResult(RESULT_OK, intent);
				intent.putExtra("o", pesquisa);
				intent.putExtra("tipo","web");
				intent.putExtra("json", rec.toString());
				finish();
				}*/
			
				break;
			case 2: //LOGRADOURO BANCO
				if(!listaLogradouros.isEmpty()){
					String cd_logr = listaLogradouros.get(position).getCd_logr();
					Intent intent2 = getIntent();
					setResult(RESULT_OK, intent2);
					intent2.putExtra("o", pesquisa);
					intent2.putExtra("tipo","banco");
					intent2.putExtra("valor", cd_logr);
					finish();
					
				}else{
					//LOGRADOURO WEB		
				JSONObject rec2 = resultados.getJSONObject(position);
				
				Intent intent2 = getIntent();
				setResult(RESULT_OK, intent2);
				intent2.putExtra("o", pesquisa);
				intent2.putExtra("tipo","web");
				intent2.putExtra("json", rec2.toString());
				finish();
				}
				
				break;
			case 3: //BAIRRO BANCO
				if(!listaBairros.isEmpty()){
				String cd_bairro = listaBairros.get(position).getMslink();
				Intent intent3 = getIntent();
				setResult(RESULT_OK, intent3);
				intent3.putExtra("o", pesquisa);
				intent3.putExtra("tipo", "banco");
				intent3.putExtra("valor",cd_bairro);	
                finish();			
					
				}else{
				
				//BAIRRO WEB
				JSONObject rec3 = resultados.getJSONObject(position);
				
				Intent intent3 = getIntent();
				setResult(RESULT_OK, intent3);
				intent3.putExtra("o", pesquisa);
				intent3.putExtra("tipo","web");
				intent3.putExtra("json", rec3.toString());
				finish();
				}
				break;
			case 7: //LOGRADOURO 2 BANCO:
				if(!listaLogradouros.isEmpty()){
					String cd_logr = listaLogradouros.get(position).getCd_logr();
					Intent intent4 = getIntent();
					setResult(RESULT_OK, intent4);
					intent4.putExtra("o", pesquisa);
					intent4.putExtra("tipo","banco");
					intent4.putExtra("valor", cd_logr);
					finish();
					
				}else{
					//LOGRADOURO 2 WEB		
				JSONObject rec4 = resultados.getJSONObject(position);
				
				Intent intent4 = getIntent();
				setResult(RESULT_OK, intent4);
				intent4.putExtra("o", pesquisa);
				intent4.putExtra("tipo","web");
				intent4.putExtra("json", rec4.toString());
				finish();
				}
				
				break;
			case 8:  //LOGRADOURO 3 BANCO:
				if(!listaLogradouros.isEmpty()){
					String cd_logr = listaLogradouros.get(position).getCd_logr();
					Intent intent5 = getIntent();
					setResult(RESULT_OK, intent5);
					intent5.putExtra("o", pesquisa);
					intent5.putExtra("tipo","banco");
					intent5.putExtra("valor", cd_logr);
					finish();
					
				}else{
					//LOGRADOURO 3 WEB		
				JSONObject rec5 = resultados.getJSONObject(position);
				
				Intent intent5 = getIntent();
				setResult(RESULT_OK, intent5);
				intent5.putExtra("o", pesquisa);
				intent5.putExtra("tipo","web");
				intent5.putExtra("json", rec5.toString());
				finish();
				}
				
				break;
			case 9: //LOGRADOURO 4 BANCO:
				if(!listaLogradouros.isEmpty()){
					String cd_logr = listaLogradouros.get(position).getCd_logr();
					Intent intent6 = getIntent();
					setResult(RESULT_OK, intent6);
					intent6.putExtra("o", pesquisa);
					intent6.putExtra("tipo","banco");
					intent6.putExtra("valor", cd_logr);
					finish();
					
				}else{
					//LOGRADOURO 4 WEB		
				JSONObject rec6 = resultados.getJSONObject(position);
				
				Intent intent6 = getIntent();
				setResult(RESULT_OK, intent6);
				intent6.putExtra("o", pesquisa);
				intent6.putExtra("tipo","web");
				intent6.putExtra("json", rec6.toString());
				finish();
				}
				
				break;
			case 10: //"Logradouro Pessoa":
				if(!listaLogradouros.isEmpty()){
					String cd_logr = listaLogradouros.get(position).getCd_logr();
					Intent intent7 = getIntent();
					setResult(RESULT_OK, intent7);
					intent7.putExtra("o", pesquisa);
					intent7.putExtra("tipo","banco");
					intent7.putExtra("valor",cd_logr);
					finish();
					
				}else{
							
				JSONObject rec7 = resultados.getJSONObject(position);
				
				Intent intent7 = getIntent();
				setResult(RESULT_OK, intent7);
				intent7.putExtra("o", pesquisa);
				intent7.putExtra("tipo","web");
				intent7.putExtra("json", rec7.toString());
				finish();
				}
				
				break;
				
			case 11: //"Bairro Pessoa":
				if(!listaBairros.isEmpty()){
					String cd_bairro = listaBairros.get(position).getMslink();
					Intent intent8 = getIntent();
					setResult(RESULT_OK, intent8);
					intent8.putExtra("o", pesquisa);
					intent8.putExtra("tipo", "banco");
					intent8.putExtra("valor",cd_bairro);	
	                finish();			
						
					}else{
					
					
					JSONObject rec8 = resultados.getJSONObject(position);
					
					Intent intent8 = getIntent();
					setResult(RESULT_OK, intent8);
					intent8.putExtra("o", pesquisa);
					intent8.putExtra("tipo","web");
					intent8.putExtra("json", rec8.toString());
					finish();
					}
				
				break;
				
				
			case 12: //"Quadra":
			
					String cd_quadra = listaQuadras.get(position).getCd_quadra();
					Intent intent9 = new Intent(this,SituacaoQuadra.class);
					intent9.putExtra("o", pesquisa);
					intent9.putExtra("valor",cd_quadra);
					startActivity(intent9);
					finish();
				  		
				
			default:
				break;
			}
			
			
		} catch (Exception e) {
			 Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método onListItemClick (PesquisaPadrao): "+e.toString());
			e.printStackTrace();
		}
        //Intent winFormulario = new Intent("android.intent.action.FORMULARIO");
        //winFormulario.putExtra("inscricao", this.list.get(position).getText1());
        //startActivityForResult(winFormulario, 1);
    }

}
