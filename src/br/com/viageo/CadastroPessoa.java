package br.com.viageo;

import java.util.ArrayList;
import java.util.InputMismatchException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.viageo.R.id;
import br.com.viageo.banco.BancoBairro;
import br.com.viageo.banco.BancoLogradouro;
import br.com.viageo.banco.BancoPessoa;
import br.com.viageo.entidade.Bairro;
import br.com.viageo.entidade.Logradouro;
import br.com.viageo.entidade.Pessoa;

public class CadastroPessoa extends Activity implements OnClickListener {
	
	EditText pessoaNome, pessoaNomeFantasia, pessoaCpf, cdLogr,id_logra,cd_bairro, pessoaRg, pessoaRgDataEmissao, pessoaRgOrgaoExpedidor, pessoaNascimento, pessoaNomeMae, pessoaEstadoCivil, pessoaNacionalidade, pessoaEmail, pessoaTelefoneRes, pessoaComercial, pessoaCelular, pessoaFax, pessoaLogradouro, pessoaNumero, pessoaEndereco, pessoaComplemento, pessoaBairro, pessoaCep, pessoaCidade, pessoaUf, pessoaObs;
	Spinner spNaturezaJuridica, spEstabelecido, spSexo, spTipoLogradouro, spAtributoLogradouro;
	ImageButton btnImgPesquisarLogrPessoa, btnImgPesquisarBairroPessoa;
	Intent pega_intent;
	ArrayList<Opcoes> opcoesNaturezaJuridica, opcoesEstabelecido, opcoesSexo, opcoesTipoLogradouro,opcoesAtributoLogradouro;
	ArrayList<String> lista,lista2;
	
	JSONArray resultados;
	AsyncTask<String, Long, Void> thread_execSalvarPessoa;
	String msgSalvarPessoa = null;
	String  nu_insc_imbl = null;
	
	Pessoa pessoa = new Pessoa();
    Bairro bairro = new Bairro();
	Logradouro logradouro = new Logradouro();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_pessoa);
		
		Button btnSalvar = (Button)findViewById(R.id.btnSalvarFormularioEdit);
		btnSalvar.setOnClickListener(this);
		
		btnImgPesquisarLogrPessoa = (ImageButton)findViewById(R.id.btnImgPesquisarLogrPessoa);
		btnImgPesquisarLogrPessoa.setOnClickListener(this);
		btnImgPesquisarLogrPessoa.setFocusableInTouchMode(true);
		btnImgPesquisarLogrPessoa.requestFocus();
		btnImgPesquisarBairroPessoa = (ImageButton)findViewById(R.id.btnImgPesquisarBairroPessoa);
		btnImgPesquisarBairroPessoa.setOnClickListener(this);
		btnImgPesquisarBairroPessoa.setFocusableInTouchMode(true);
		btnImgPesquisarBairroPessoa.requestFocus();
		pessoaNome = (EditText)findViewById(R.id.etPessoaNomeEdit);
		pessoaCpf = (EditText)findViewById(R.id.etPessoaCpfEdit);	    
	    pessoaRg = (EditText) findViewById(R.id.etRgEdit);
	    cdLogr = (EditText)findViewById(id.etCdLogrPess);
	    cd_bairro = (EditText) findViewById(id.etCdBarrPessoa);
	    id_logra = (EditText) findViewById( id.etIdlograPessoa);
	    pessoaTelefoneRes = (EditText) findViewById(R.id.etTelefoneEdit);
	    pessoaLogradouro = (EditText) findViewById(R.id.etLogradouroEdit);	    
	    pessoaNumero = (EditText) findViewById(R.id.etNumeroEdit);
	    pessoaComplemento = (EditText)findViewById(R.id.etComplementoEdit);
	    pessoaCep = (EditText)findViewById(R.id.etCEPEdit);
	    pessoaBairro = (EditText)findViewById(R.id.etBairroEdit);
	    pessoaCidade = (EditText)findViewById(R.id.etCidadeEdit);
	    pessoaUf = (EditText)findViewById(R.id.etPessoaUfEdit);
	    //spinners
	    spNaturezaJuridica = (Spinner)findViewById(R.id.spNaturezaJuridica);
	    spNaturezaJuridica.setFocusableInTouchMode(true);
	    spNaturezaJuridica.requestFocus();
	    spTipoLogradouro = (Spinner)findViewById(R.id.spTipoLogradouro);
	    spTipoLogradouro.setFocusableInTouchMode(true);
	    spTipoLogradouro.requestFocus(); 
	    spAtributoLogradouro = (Spinner)findViewById(R.id.spAtributoLogradouro);
	    spAtributoLogradouro.setFocusableInTouchMode(true);
	    spAtributoLogradouro.requestFocus(); 
	    
	    inicializaSpinner();	
	    
	    pega_intent = getIntent();
	    nu_insc_imbl = pega_intent.getStringExtra("inscricao");
	    if(pega_intent.getStringExtra("edicao").equalsIgnoreCase("true")){
	        preencheFormEdicao(pega_intent.getStringExtra("cpf"));
	    }
	    
	}
	
	/* Botao Voltar - Controle */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Você perderá todas as alterações não salvas. Tem certeza que deseja voltar? ")
		    	.setCancelable(false)
		    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int id) {
		    			finish();
			       	}
		       }).setNegativeButton("Cancela", new DialogInterface.OnClickListener() {
		    	   @Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
			});
			AlertDialog alert = builder.create();
			alert.show();
	    }
	    return super.onKeyDown(keyCode, event);
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
        Log.d ("teste", msg);
          Toast.makeText(getBaseContext(), "ERRO DE CONECTIVIDADE = "+msg, Toast.LENGTH_LONG).show();
    }

	private void preencheFormEdicao(String cpf) {
		
	if (TemConexao()){
		
				try {
					HttpConection objConexao = new HttpConection();
					objConexao.setDados("o", "12");
					objConexao.setDados("cpf", cpf);
					JSONObject retorno = objConexao.envia();
				
				
							if (retorno.getString("ok").equals("1")){
			
										pessoaNome.setText(retorno.getString("nmcontribuinte"));
										pessoaCpf.setText(retorno.getString("cpf"));
										cdLogr.setText(retorno.getString("cd_logr"));
										String natureza_aux = "";
										if (retorno.getString("innaturezajuridica").equalsIgnoreCase("F")){
											natureza_aux = "Pessoa Física";
										}
										if (retorno.getString("innaturezajuridica").equalsIgnoreCase("J")){
											natureza_aux = "Pessoa Jurídica";
										}					
									    for (int i1 = 0; i1 < spNaturezaJuridica.getCount(); i1++) {  
									    	if (spNaturezaJuridica.getItemAtPosition(i1).toString().equalsIgnoreCase(natureza_aux)) {  
									    		spNaturezaJuridica.setSelection(i1);  
								            }
								        }			    
									    
										pessoaRg.setText(retorno.getString("nrcartidentidade"));					
										pessoaTelefoneRes.setText(retorno.getString("nrtelefoneresid"));					
										
										  for (int i1 = 0; i1 < spTipoLogradouro.getCount(); i1++) {  
										    	if (spTipoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(retorno.getString("tp_logr"))) {  
										    		spTipoLogradouro.setSelection(i1);  
									                  }
									        }	
									    
								
									    for (int i1 = 0; i1 < spAtributoLogradouro.getCount(); i1++) {  
								            if (spAtributoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(retorno.getString("tp_atrb_logr"))) {  
								            	spAtributoLogradouro.setSelection(i1);  
								                }  
								         }
										pessoaLogradouro.setText(retorno.getString("logradouro_pessoa")); 
										pessoaNumero.setText(retorno.getString("nrimovel"));
										pessoaComplemento.setText(retorno.getString("dscomplemento"));
										pessoaBairro.setText(retorno.getString("bairro"));
										pessoaCep.setText(retorno.getString("nu_cep_logr"));
										pessoaCidade.setText(retorno.getString("cidade_pessoa"));
										pessoaUf.setText(retorno.getString("uf_pessoa"));
								
								
								
							}else{
								
								if(retorno.getString("ok").equals("0")){
									Toast.makeText(getBaseContext(), "ERRO Conexão: "+retorno.getString("erro"), Toast.LENGTH_LONG).show();
								}else{
									Toast.makeText(getBaseContext(), "ERRO ao Excluir: "+retorno.getString("erro"), Toast.LENGTH_LONG).show();
									}
							
					     }
				
				
				
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro método preencheFormEdicao (CadastroPessoa):"+e.getMessage());
			}
			
			
		}else{
			
			//RECUPERA DADOS DO BANCO DA PESSOA
			BancoPessoa bancoPessoa = new BancoPessoa(this);
			BancoLogradouro bancoLogradouro = new BancoLogradouro(this);
			pessoa = bancoPessoa.pesquisaPessoaBD(cpf);
			logradouro = bancoLogradouro.pesquisaLogradouroBD(pessoa.getCd_logr());
			cdLogr.setText(pessoa.getCd_logr());
			pessoaNome.setText(pessoa.getNm_pess());
			pessoaCpf.setText(pessoa.getNu_pess());
			pessoaRg.setText(pessoa.getNu_cart_idnt());
			pessoaTelefoneRes.setText(pessoa.getNu_telf());
		    pessoaLogradouro.setText(logradouro.getNm_logr()); 
			pessoaNumero.setText(pessoa.getNu_imvl());
			pessoaComplemento.setText(pessoa.getDe_comp());
			pessoaBairro.setText(logradouro.getNm_barr());
			pessoaCep.setText(logradouro.getNu_cep_logr());
			pessoaCidade.setText(logradouro.getNm_cidd());
			pessoaUf.setText(logradouro.getSg_uf());
			
			String natureza_aux = "";
			if (pessoa.getIn_pfis_pjur().equalsIgnoreCase("1")){
				natureza_aux = "Pessoa Física";
				}
			
			if (pessoa.getIn_pfis_pjur().equalsIgnoreCase("2")){
				natureza_aux = "Pessoa Jurídica";
				}					
		   
			for (int i1 = 0; i1 < spNaturezaJuridica.getCount(); i1++) {  
		    	if (spNaturezaJuridica.getItemAtPosition(i1).toString().equalsIgnoreCase(natureza_aux)) {  
		    		spNaturezaJuridica.setSelection(i1);  
	            	}
		    	}			    
		  
			  for (int i1 = 0; i1 < spTipoLogradouro.getCount(); i1++) {  
			    	if (spTipoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(logradouro.getTp_logr())) {  
			    		spTipoLogradouro.setSelection(i1);  
		            }
		        }	
		
		    for (int i1 = 0; i1 < spAtributoLogradouro.getCount(); i1++) {  
	            if (spAtributoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(logradouro.getTp_atrb_logr())) {  
	            	spAtributoLogradouro.setSelection(i1);  
	            }  
	        }
			
		
			
			
			
		}
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()){
		case R.id.btnSalvarFormularioEdit:
		    if(validaCampoObrigatorio()){			
			thread_execSalvarPessoa = new threadSalvarPessoa().execute();
		     }
		  break;
		case R.id.btnImgPesquisarLogrPessoa:
			Intent winPesquisaPadraoLogrPessoa = new Intent(CadastroPessoa.this, PesquisaPadrao.class);
			winPesquisaPadraoLogrPessoa.putExtra("tp_pesquisa", 10);
			startActivityForResult(winPesquisaPadraoLogrPessoa, 1);
			break; 
		case R.id.btnImgPesquisarBairroPessoa:
			Intent winPesquisaPadraoBairroPessoa = new Intent(CadastroPessoa.this, PesquisaPadrao.class);
			winPesquisaPadraoBairroPessoa.putExtra("tp_pesquisa", 11);
			startActivityForResult(winPesquisaPadraoBairroPessoa, 1);
			break; 
		    
	    }	 
		
	}	
	
	private class threadSalvarPessoa extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(CadastroPessoa.this);
		// can use UI thread here
		@Override
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Salvar Pessoa Cancelado!", Toast.LENGTH_LONG).show();
						thread_execSalvarPessoa.cancel(true);
						//thread_exec = null;
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Enviando...");
			this.dialog.show();
		}
		
		@Override
		protected Void doInBackground(final String... args) {
			try {
				salvaPessoa();
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro método threadSalvarPessoa (CadastroPessoa):"+e.getMessage());
			}
			return null;
		}
		// periodic updates - it is OK to change UI
		@Override
		protected void onProgressUpdate(Long... value) {
		super.onProgressUpdate(value);
			
		}
		// can use UI thread here
		@Override
		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			//retornoEnviaFotos();
			finalizaThreadSalvarPessoa();
			//finish();
		}
	}//AsyncTask	
	
	public void finalizaThreadSalvarPessoa(){
		if(msgSalvarPessoa != null){
			Toast.makeText(getBaseContext(), msgSalvarPessoa, Toast.LENGTH_LONG).show();
			msgSalvarPessoa = null;
		}
		thread_execSalvarPessoa.cancel(true);
		thread_execSalvarPessoa = null;
		/*if(!pessoa.getNu_pess().equalsIgnoreCase("")){
			Intent intentForm = getIntent();
			setResult(RESULT_OK, intentForm);
			intentForm.putExtra("inscricao", nu_insc_imbl);
			intentForm.putExtra("tipo","banco");
			intentForm.putExtra("cpf",pessoa.getNu_pess());
			intentForm.putExtra("o", 1);
			finish();*/
		/**/
	}

	private boolean validaCampoObrigatorio(){
		boolean ret = true;
		String texto = "";
		
		if(pessoaNome.getText().toString().trim().equals("")){
			ret = false;
			texto += "O campo 'Nome' é obrigatório!\n";
		}
		
		if(spNaturezaJuridica.getId()== 0){
			ret= false;
			texto +="O campo 'Natureza Jurídica' é obrigatório!\n";			
		}
		
		
		if(pessoaCpf.getText().toString().trim().equals("")){
			ret = false;
			texto += "O campo 'CPF/CNPJ'é obrigatório!";
			
			
		}else{
			String cpf = (pessoaCpf.getText().toString().trim()).replace(".", "");
			cpf = cpf.replace("-", "");
			if(cpf.length() == 11){
				if(!isCPF(cpf)){
					ret = false;
					texto += "O campo 'CPF/CNPJ' está inválido!";
				}
			}else{
				if(cpf.length() == 14){
					if(!isCNPJ(cpf)){
						ret = false;
						texto += "O campo 'CPF/CNPJ' está inválido!";
					}
				}else{
					ret = false;
					texto += "O campo 'CPF/CNPJ' está inválido!";
				}
			}
		}
		if(!ret){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(texto)
		    	.setCancelable(false)
		    	.setTitle("Atenção")
		    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int id) {
		    			dialog.cancel();
			       	}
		       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
		return ret;
	}
	
	public boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
	    if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
	        CPF.equals("22222222222") || CPF.equals("33333333333") ||
	        CPF.equals("44444444444") || CPF.equals("55555555555") ||
	        CPF.equals("66666666666") || CPF.equals("77777777777") ||
	        CPF.equals("88888888888") || CPF.equals("99999999999") ||
	       (CPF.length() != 11))
	       return(false);
	 
	    char dig10, dig11;
	    int sm, i, r, num, peso;
	 
	    // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
	    try {
	    	// Calculo do 1o. Digito Verificador
	      sm = 0;
	      peso = 10;
	      for (i=0; i<9; i++) {             
	    	  // converte o i-esimo caractere do CPF em um numero:
	    	  // por exemplo, transforma o caractere '0' no inteiro 0        
	    	  // (48 eh a posicao de '0' na tabela ASCII)        
	        num = (int)(CPF.charAt(i) - 48);
	        sm = sm + (num * peso);
	        peso = peso - 1;
	      }
	 
	      r = 11 - (sm % 11);
	      if ((r == 10) || (r == 11))
	         dig10 = '0';
	      else dig10 = (char)(r + 48); // converte no respectivo caractere numerico
	 
	      // Calculo do 2o. Digito Verificador
	      sm = 0;
	      peso = 11;
	      for(i=0; i<10; i++) {
	        num = (int)(CPF.charAt(i) - 48);
	        sm = sm + (num * peso);
	        peso = peso - 1;
	      }
	 
	      r = 11 - (sm % 11);
	      if ((r == 10) || (r == 11))
	         dig11 = '0';
	      else dig11 = (char)(r + 48);
	 
	      // Verifica se os digitos calculados conferem com os digitos informados.
	      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
	         return(true);
	      else return(false);
	    } catch (InputMismatchException erro) {
	        return(false);
	    }
	}
		
	public boolean isCNPJ(String CNPJ) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
	    if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
	        CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
	        CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
	        CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
	        CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
	       (CNPJ.length() != 14))
	       return(false);
	 
	    char dig13, dig14;
	    int sm, i, r, num, peso;
	 
	    // "try" - protege o c�digo para eventuais erros de conversao de tipo (int)
	    try {
	    	// Calculo do 1o. Digito Verificador
	      sm = 0;
	      peso = 2;
	      for (i=11; i>=0; i--) {
	    	  // converte o i-�simo caractere do CNPJ em um n�mero:
	    	  // por exemplo, transforma o caractere '0' no inteiro 0
	    	  // (48 eh a posi��o de '0' na tabela ASCII)
	        num = (int)(CNPJ.charAt(i) - 48);
	        sm = sm + (num * peso);
	        peso = peso + 1;
	        if (peso == 10)
	           peso = 2;
	      }
	 
	      r = sm % 11;
	      if ((r == 0) || (r == 1))
	         dig13 = '0';
	      else dig13 = (char)((11-r) + 48);
	 
	      // Calculo do 2o. Digito Verificador
	      sm = 0;
	      peso = 2;
	      for (i=12; i>=0; i--) {
	        num = (int)(CNPJ.charAt(i)- 48);
	        sm = sm + (num * peso);
	        peso = peso + 1;
	        if (peso == 10)
	           peso = 2;
	      }
	 
	      r = sm % 11;
	      if ((r == 0) || (r == 1))
	         dig14 = '0';
	      else dig14 = (char)((11-r) + 48);
	 
	      // Verifica se os d�gitos calculados conferem com os d�gitos informados.
	      if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
	         return(true);
	      else return(false);
	    } catch (InputMismatchException erro) {
	        return(false);
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String tipo = "";
		if(resultCode == RESULT_OK && requestCode == 1){
		
			
			switch (data.getIntExtra("o", 0)) {
			case 10: //Logradouro Pessoa
				tipo = data.getStringExtra("tipo");
				if(tipo.equalsIgnoreCase("banco")){
				   preencherDadosLogrBD(data.getStringExtra("valor"));	
				   
				}else{
					preencheDadosLogr(data.getStringExtra("json"));
				};
				
				break;
			case 11: //Bairro Pessoa
				 tipo = data.getStringExtra("tipo");
				if (tipo.equalsIgnoreCase("banco")){			
					preencheDadosBairroBD(data.getStringExtra("valor"));
				}else{
					preencheDadosBairro(data.getStringExtra("json"));
					};
			break;
			
			default:
				break;
			}
		}else{
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método onActivityResult (CadastroPessoa): ");
		}
	}
	
    public void preencheDadosLogr(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				cdLogr.setText(jsonObj.getString("cd_logr"));
				pessoaLogradouro.setText(jsonObj.getString("nm_logr"));
				id_logra.setText(jsonObj.getString("id_logra"));
				pessoaBairro.setText(jsonObj.getString("nm_barr"));
				pessoaCidade.setText(jsonObj.getString("nm_cidd"));
				pessoaUf.setText(jsonObj.getString("sg_uf"));
				
				for (int i1 = 0; i1 < spTipoLogradouro.getCount(); i1++) {  
		            if (spTipoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(jsonObj.getString("tp_logr"))) {  
		            	spTipoLogradouro.setSelection(i1);  
		            }  
		        }	
				
				for (int i1 = 0; i1 < spAtributoLogradouro.getCount(); i1++) {  
		            if (spAtributoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(jsonObj.getString("tp_atrb_logr"))) {  
		            	spAtributoLogradouro.setSelection(i1);  
		            }  
		        }	
				
				
			}else{
				Toast.makeText(getBaseContext(), "Erro: "+jsonObj.getString("erro"), Toast.LENGTH_LONG).show();
			}
			
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencheDadosLogr (CadastroPessoa): "+e.getLocalizedMessage());
			
		}
	}
	
	public void preencherDadosLogrBD(String cd_logr){
		
		try{
		BancoLogradouro logradouroBanco = new BancoLogradouro(this);
	   logradouro = logradouroBanco.pesquisaLogradouroBD(cd_logr); 
		cdLogr.setText(logradouro.getCd_logr());
		pessoaLogradouro.setText(logradouro.getNm_logr());
		id_logra.setText(logradouro.getId_logra());
		pessoaBairro.setText(logradouro.getNm_barr());
		pessoaCidade.setText(logradouro.getNm_cidd());
		pessoaCep.setText(logradouro.getNu_cep_logr());
		pessoaUf.setText(logradouro.getSg_uf());
		
		for (int i1 = 0; i1 < spTipoLogradouro.getCount(); i1++) {  
            if (spTipoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(logradouro.getTp_logr())) {  
            	spTipoLogradouro.setSelection(i1);  
            }  
        }	
		
		for (int i1 = 0; i1 < spAtributoLogradouro.getCount(); i1++) {  
            if (spAtributoLogradouro.getItemAtPosition(i1).toString().equalsIgnoreCase(logradouro.getTp_atrb_logr())) {  
            	spAtributoLogradouro.setSelection(i1);  
            }  
        }
		
		}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencherDadosLogrBD (CadastroPessoa): "+e.getLocalizedMessage());
			
		   }
		
		
	}
	
	public void preencheDadosBairro(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				pessoaBairro.setText(jsonObj.getString("nm_bairro"));
				cd_bairro.setText(jsonObj.getString("cd_bairro"));
				
			}else{
				Toast.makeText(getBaseContext(), "Erro: "+jsonObj.getString("erro"), Toast.LENGTH_LONG).show();
			}
			
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencheDadosBairro (CadastroPessoa): "+e.getLocalizedMessage());
			
		}
	}	
	
	public void preencheDadosBairroBD(String mslink){
		try{
		
		BancoBairro bairroBanco = new BancoBairro(this);
	   	bairro = bairroBanco.pesquisaBairroBD(mslink);
	    pessoaBairro.setText(bairro.getNm_bairro());
	    cd_bairro.setText(bairro.getMslink());
		
		}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencheDadosBairroBD (CadastroPessoa): "+e.getLocalizedMessage());
			
		}
		
		
	}
		
	private void salvaPessoa(){
		
		pessoa.setNm_pess(pessoaNome.getText().toString().trim());
		pessoa.setNu_pess(pessoaCpf.getText().toString().trim());
		Opcoes naturezaJuridica = (Opcoes) spNaturezaJuridica.getSelectedItem();
		pessoa.setIn_pfis_pjur(Integer.toString(naturezaJuridica.getId()));
		pessoa.setNu_cart_idnt(pessoaRg.getText().toString().trim());
		pessoa.setNu_telf(pessoaTelefoneRes.getText().toString().trim());
		pessoa.setCd_logr(cdLogr.getText().toString().trim());
		pessoa.setId_logra(id_logra.getText().toString().trim());
		pessoa.setNu_imvl(pessoaNumero.getText().toString().trim());
		pessoa.setDe_comp(pessoaComplemento.getText().toString().trim());
		pessoa.setSincronizado("SIM");
		 BancoPessoa bancoPessoa = new BancoPessoa(this);
		
		
		
		if (TemConexao()){
			
		
			HttpConection objConexao = new HttpConection();			
			objConexao.setDados("nmcontribuinte", pessoaNome.getText().toString().trim());		
			objConexao.setDados("cpf", pessoaCpf.getText().toString().trim());
			naturezaJuridica = (Opcoes) spNaturezaJuridica.getSelectedItem();
			objConexao.setDados("innaturezajuridica", Integer.toString(naturezaJuridica.getId()));
			objConexao.setDados("nrcartidentidade", pessoaRg.getText().toString().trim());
			objConexao.setDados("nrtelefoneresid",pessoaTelefoneRes.getText().toString().trim());
			objConexao.setDados("cd_logr_pessoa", cdLogr.getText().toString().trim());
			objConexao.setDados("id_logra_pessoa", id_logra.getText().toString().trim());
			objConexao.setDados("nrimovel", pessoaNumero.getText().toString().trim());
			objConexao.setDados("dscomplemento", pessoaComplemento.getText().toString().trim());
		
			
			SharedPreferences sp = getSharedPreferences("VGFISCAL", 0);
			String usuario = sp.getString("usuario", "nao");
			if(usuario.equalsIgnoreCase("nao")){
				usuario = "tablet";
			}
			objConexao.setDados("nm_usuario", usuario);
			
			
			if(pega_intent.getStringExtra("edicao").equalsIgnoreCase("true")){			
				objConexao.setDados("o", "13");
				
				//ATUALIZA PESSOA NO BANCO------------------------------------------------------------------------------------------------------------------
				bancoPessoa.atualizar(pessoa);
				
				objConexao.setDados("cpf", pega_intent.getStringExtra("cpf"));
				
			}else{
				
				objConexao.setDados("o", "11");
				
				//SALVA PESSOA NO BANCO------------------------------------------------------------------------------------------------------------------
				
				
				bancoPessoa.inserir(pessoa);
			}
			
			JSONObject retorno;
			
			try {
				 retorno = objConexao.envia();
				
				 
				if (retorno.getString("ok").equals("1")){
			
					msgSalvarPessoa = "Dados Gravados com Sucesso!";
					
					ArrayList<String> arrayDadosPessoa = new ArrayList<String>();
					
					arrayDadosPessoa.add(retorno.getString("nmcontribuinte"));
					arrayDadosPessoa.add(retorno.getString("cpf"));
					arrayDadosPessoa.add(retorno.getString("innaturezajuridica"));
					arrayDadosPessoa.add(retorno.getString("nrcartidentidade"));
					arrayDadosPessoa.add(retorno.getString("nrtelefoneresid"));
					arrayDadosPessoa.add(retorno.getString("cd_logr_pessoa"));
					arrayDadosPessoa.add(retorno.getString("id_logra_pessoa"));
					arrayDadosPessoa.add(retorno.getString("nrimovel"));
					arrayDadosPessoa.add(retorno.getString("dscomplemento"));
				
					
					setResult(RESULT_OK, pega_intent);
		
	    	        pega_intent.putExtra("o",13);
					
				pega_intent.putStringArrayListExtra("arrayDados", arrayDadosPessoa);
				pega_intent.putExtra("codigo", retorno.getString("cd_logr_pessoa"));
					
					finish();
					return;
					
				}else{
					
					
					
					if(retorno.getString("ok").equals("3")){
						
						
						Intent intentForm = new Intent(this,Formulario.class);
						intentForm.putExtra("inscricao",nu_insc_imbl);
						intentForm.putExtra("tipo","banco");
						intentForm.putExtra("cpf",pessoa.getNu_pess());
						startActivity(intentForm);
						finish();
						return;
				
					}
					
				
					
					if(retorno.getString("ok").equals("0")){
			
						msgSalvarPessoa = "ERRO Conexão: "+retorno.getString("erro")+" Gravado somente no banco!";
						 bancoPessoa.inserir(pessoa);
					}else{
			
						msgSalvarPessoa = "ERRO ao Gravar: "+retorno.getString("error");
					}				
				}
			} catch (JSONException e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método salvaPessoa Web (CadastroPessoa): "+e.getLocalizedMessage());
				msgSalvarPessoa = "ERRO FATAL full";
			}
			
			
		}else{
			
		
			//INSERÇÃO PESSOA BANCO (SEM CONEXAO)-----------------------------------------------------------------------------------------------------------------
		
			  pessoa.setSincronizado("NAO");
			
			if(pega_intent.getStringExtra("edicao").equalsIgnoreCase("true")){
				
						try{
							bancoPessoa.atualizar(pessoa);
									msgSalvarPessoa = "Contribuinte alterado com Sucesso!";
							
							
							
							ArrayList<String> arrayDadosPessoa = new ArrayList<String>();
							
							arrayDadosPessoa.add(pessoa.getNm_pess());
							arrayDadosPessoa.add(pessoa.getNu_pess());
							arrayDadosPessoa.add(pessoa.getIn_pfis_pjur());
							arrayDadosPessoa.add(pessoa.getNu_cart_idnt());
							arrayDadosPessoa.add(pessoa.getNu_telf());
							arrayDadosPessoa.add(pessoa.getCd_logr());
							arrayDadosPessoa.add(pessoa.getId_logra());
							arrayDadosPessoa.add(pessoa.getNu_imvl());
							arrayDadosPessoa.add(pessoa.getDe_comp());
							setResult(RESULT_OK, pega_intent);
							pega_intent.putExtra("o", 13);
					
							pega_intent.putStringArrayListExtra("arrayDados", arrayDadosPessoa);
							pega_intent.putExtra("codigo", pessoa.getCd_logr());
							finish();
							
							
							
							
							
						}catch(Exception e){
							msgSalvarPessoa = "Erro, Contribuinte não foi alterado!";
							Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método salvaPessoa Web edição (CadastroPessoa): "+e.getLocalizedMessage());
							
						}

			  }else{
								
							try{
 								
							if(!bancoPessoa.ConsultaPessoaBD(pessoa.getNu_pess())){
								msgSalvarPessoa = "Já existe cadastro com esse CPF/CNPJ!";
								pessoa.setNu_pess("");
							
							}else{
								bancoPessoa.inserir(pessoa);
								Intent intentForm = new Intent(this,Formulario.class);
								intentForm.putExtra("inscricao",nu_insc_imbl);
								intentForm.putExtra("tipo","banco");
								intentForm.putExtra("cpf",pessoa.getNu_pess());
								startActivity(intentForm);
								finish();
								return;
							}
							
							
						/*	ArrayList<String> arrayDadosPessoa = new ArrayList<String>();
							
							arrayDadosPessoa.add(pessoa.getNm_pess());
							arrayDadosPessoa.add(pessoa.getNu_pess());
							arrayDadosPessoa.add(pessoa.getIn_pfis_pjur());
							arrayDadosPessoa.add(pessoa.getNu_cart_idnt());
							arrayDadosPessoa.add(pessoa.getNu_telf());
							arrayDadosPessoa.add(pessoa.getCd_logr());
							arrayDadosPessoa.add(pessoa.getId_logra());
							arrayDadosPessoa.add(pessoa.getNu_imvl());
							arrayDadosPessoa.add(pessoa.getDe_comp());
							setResult(RESULT_OK, pega_intent);
							pega_intent.putExtra("o", 13);
							
							pega_intent.putStringArrayListExtra("arrayDados", arrayDadosPessoa);
							pega_intent.putExtra("codigo", pessoa.getId_logra());
							finish();*/
							//--------------------------------------------------------------------------------------------------------------------------------------------
							
							}catch (Exception e){
								msgSalvarPessoa = "Erro, Contribuinte não foi salvo!";
								Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método salvaPessoa tablet (CadastroPessoa): "+e.getLocalizedMessage());
								}
							
				  }
			
		   }
	
	}
	
	public void inicializaSpinner(){
		
		//NATUREZA JURIDICA   ----------------------INICIO
		Opcoes s1 = new Opcoes();  
		s1.setId(0);  
		s1.setNome("Selecione...");
		Opcoes s2 = new Opcoes();  
		s2.setId(1);
		s2.setNome("Pessoa Física");
		Opcoes s3 = new Opcoes();  
		s3.setId(2);  
		s3.setNome("Pessoa Jurídica");
		
		opcoesNaturezaJuridica = new ArrayList<Opcoes>();  
		opcoesNaturezaJuridica.add(s1);  
		opcoesNaturezaJuridica.add(s2);  
		opcoesNaturezaJuridica.add(s3);
		
		ArrayAdapter<Opcoes> naturezaJuridicaAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesNaturezaJuridica);
		naturezaJuridicaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spNaturezaJuridica.setAdapter(naturezaJuridicaAdapter);
		//SITUACAO DE QUADRA   ----------------------FIM*/
		
	
		
		//TIPO DE LOGRADOURO  ----------------------INICIO
		
		
		
		if (TemConexao()){
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "9");			
			objConexao.setDados("tipoConsulta","tipoLogradouro");
			JSONObject retorno;
			
			try {			
				
			    retorno = objConexao.envia();
				resultados = retorno.getJSONArray("results");
				JSONObject rec = resultados.getJSONObject(0);
							
				 lista = new ArrayList<String>();				
				 lista.add(0, "Selecione...");
				  
				for (int i = 0; i < resultados.length(); ++i) {					
					rec = resultados.getJSONObject(i);
				    lista.add(rec.getString("tp_logr"));			  
		
				}
				
				opcoesTipoLogradouro = new ArrayList<Opcoes>();  
				
				for (int i = 0; i < lista.size(); ++i){
					
					
					Opcoes op = new Opcoes();
					op.setId(i);
					op.setNome(lista.get(i));
					opcoesTipoLogradouro.add(op);
				}			
			
		      
		        
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método inicializaSpinner (CadastroPessoa): "+e.getLocalizedMessage());
			}
			
			
			
			
		}else{
		
		
		Opcoes tp1 = new Opcoes();  
		tp1.setId(0);  
		tp1.setNome("Selecione...");
		Opcoes tp2 = new Opcoes();  
		tp2.setId(94);
		tp2.setNome(".");
		Opcoes tp3 = new Opcoes();  
		tp3.setId(95);  
		tp3.setNome("TRAVESSA");
		Opcoes tp4 = new Opcoes();  
		tp4.setId(96);  
		tp4.setNome("RODOVIA");
		Opcoes tp5 = new Opcoes();  
		tp5.setId(97);  
		tp5.setNome("RUA");
		Opcoes tp6 = new Opcoes();  
		tp6.setId(98);  
		tp6.setNome("AVENIDA");
		Opcoes tp7 = new Opcoes();  
		tp7.setId(97);  
		tp7.setNome("PRAÇA");
		
		opcoesTipoLogradouro = new ArrayList<Opcoes>();  
		opcoesTipoLogradouro.add(tp1);  
		opcoesTipoLogradouro.add(tp2);  
		opcoesTipoLogradouro.add(tp3);
		opcoesTipoLogradouro.add(tp4);
		opcoesTipoLogradouro.add(tp5);
		opcoesTipoLogradouro.add(tp6);
		opcoesTipoLogradouro.add(tp7);
		
	
		}
	
		
		ArrayAdapter<Opcoes> tipoLogradouroAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesTipoLogradouro);
		tipoLogradouroAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spTipoLogradouro.setAdapter(tipoLogradouroAdapter);
		//TIPO DE LOGRADOURO   ----------------------FIM*/
		
		
		
		//ATRIBUTO LOGRADOURO  ----------------------INICIO
		
		
		if (TemConexao()){
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o","15");			
		
			JSONObject retorno;
			
			try {			
				
				 retorno = objConexao.envia();
					resultados = retorno.getJSONArray("results");
					JSONObject rec = resultados.getJSONObject(0);
								
					 lista2 = new ArrayList<String>();				
					  lista2.add(0, "Selecione...");
					  
					for (int i = 0; i < resultados.length(); ++i) {					
						rec = resultados.getJSONObject(i);
					    lista2.add(rec.getString("tp_atrb_logr"));			  
			
					}
					
					opcoesAtributoLogradouro = new ArrayList<Opcoes>();  
					
					for (int i = 0; i < lista2.size(); ++i){
						
						
						Opcoes op = new Opcoes();
						op.setId(i);
						op.setNome(lista2.get(i));
						opcoesAtributoLogradouro.add(op);
					}
				
			
		        
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método inicializaSpinner (CadastroPessoa): "+e.getLocalizedMessage());
			}
			
			
		}else{
		
	
		
		Opcoes tpa1 = new Opcoes();  
		tpa1.setId(0);  
		tpa1.setNome("Selecione...");
		Opcoes tpa2 = new Opcoes();  
		tpa2.setId(94);
		tpa2.setNome("DRA");
		Opcoes tpa3 = new Opcoes();  
		tpa3.setId(95);  
		tpa3.setNome("IMIGR");
		Opcoes tpa4 = new Opcoes();  
		tpa4.setId(96);  
		tpa4.setNome("CEL");
		Opcoes tpa5 = new Opcoes();  
		tpa5.setId(97);  
		tpa5.setNome("DR");
		Opcoes tpa6 = new Opcoes();  
		tpa6.setId(98);  
		tpa6.setNome("PADRE");
		Opcoes tpa7 = new Opcoes();  
		tpa7.setId(97);  
		tpa7.setNome("DONA");
		
		opcoesAtributoLogradouro = new ArrayList<Opcoes>();  
		opcoesAtributoLogradouro.add(tpa1);  
		opcoesAtributoLogradouro.add(tpa2);  
		opcoesAtributoLogradouro.add(tpa3);
		opcoesAtributoLogradouro.add(tpa4);
		opcoesAtributoLogradouro.add(tpa5);
		opcoesAtributoLogradouro.add(tpa6);
		opcoesAtributoLogradouro.add(tpa7);
		
		}
		
		
		ArrayAdapter<Opcoes> AtributoLogradouroAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesAtributoLogradouro);
		AtributoLogradouroAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spAtributoLogradouro.setAdapter(AtributoLogradouroAdapter);
		//ATRIBUTO DE LOGRADOURO   ----------------------FIM*/
				
		
		
	        }
	
		
	  }
	

