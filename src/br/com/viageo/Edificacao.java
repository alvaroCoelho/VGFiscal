package br.com.viageo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Edificacao extends Activity implements OnClickListener {
	Button btnGuardar, btnExcluirEdificacao, btnCancelarEdificacao;
	EditText anoConstrucao, numeroPavEdificacao, areaConstUnidade, alvaraConstrucao, apartamento, bloco;
	ArrayList<String> tipo, alinhamento, locacao, situacao, estrutura, cobertura, paredes, vedacoesEsquadrias, revExterno, padraoConstrucao, ocupacaoLote;
	Spinner spTipo1, spAlinhamento1, spLocacao1, spSituacao1, spEstrutura1, spCobertura1, spParedes1, spVedacoesEsquadrias1, spRevExterno1, spPadraoConstrucao1, spOcupacaoLote1;
	
	ArrayList<String> sitQuadra;
	ArrayList<String>  arraySelected;
	ArrayList<String>  arrayAnoConstrucao;
	Intent dadosForm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edificacao);
		
		btnGuardar = (Button)findViewById(R.id.btnGuardar);
		btnGuardar.setOnClickListener(this);
		
		btnExcluirEdificacao = (Button)findViewById(R.id.btnExcluirEdificacao);
		btnExcluirEdificacao.setOnClickListener(this);
		
		btnCancelarEdificacao = (Button)findViewById(R.id.btnCancelarEdificacao);
		btnCancelarEdificacao.setOnClickListener(this);
		
		inicializaFindViewById();
		
		dadosForm = getIntent();
		String edit = dadosForm.getStringExtra("edit"); 
		
		inicializaSpinner();
		
		if(edit.equals("true")){
			preencheDadosEdificacao();
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
	
	
	private void inicializaSpinner() {
		
		tipo = dadosForm.getStringArrayListExtra("tipo");
		ArrayAdapter<String> arrayAdapterTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);
		arrayAdapterTipo.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spTipo1.setAdapter(arrayAdapterTipo);

		alinhamento = dadosForm.getStringArrayListExtra("alinhamento");
		ArrayAdapter<String> arrayAdapterAlinhamento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alinhamento);
		arrayAdapterAlinhamento.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spAlinhamento1.setAdapter(arrayAdapterAlinhamento);
		
		locacao = dadosForm.getStringArrayListExtra("locacao");
		ArrayAdapter<String> arrayAdapterLocacao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locacao);
		arrayAdapterLocacao.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spLocacao1.setAdapter(arrayAdapterLocacao);
	    
		situacao = dadosForm.getStringArrayListExtra("situacao");
		ArrayAdapter<String> arrayAdapterSituacao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, situacao);
		arrayAdapterSituacao.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spSituacao1.setAdapter(arrayAdapterSituacao);
	    		
		estrutura = dadosForm.getStringArrayListExtra("estrutura");
		ArrayAdapter<String> arrayAdapterEstrutura = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estrutura);
		arrayAdapterEstrutura.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spEstrutura1.setAdapter(arrayAdapterEstrutura);
	    
		cobertura = dadosForm.getStringArrayListExtra("cobertura");
		ArrayAdapter<String> arrayAdapterCobertura = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cobertura);
		arrayAdapterCobertura.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spCobertura1.setAdapter(arrayAdapterCobertura);
	    
		paredes = dadosForm.getStringArrayListExtra("paredes");
		ArrayAdapter<String> arrayAdapterParedes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paredes);
		arrayAdapterParedes.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spParedes1.setAdapter(arrayAdapterParedes);
		
		vedacoesEsquadrias = dadosForm.getStringArrayListExtra("vedacoesEsquadrias");
		ArrayAdapter<String> arrayAdapterVedacoesEsquadrias = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vedacoesEsquadrias);
		arrayAdapterVedacoesEsquadrias.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spVedacoesEsquadrias1.setAdapter(arrayAdapterVedacoesEsquadrias);
	    		
		revExterno = dadosForm.getStringArrayListExtra("revExterno");
		ArrayAdapter<String> arrayAdapterRevExterno = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, revExterno);
		arrayAdapterRevExterno.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spRevExterno1.setAdapter(arrayAdapterRevExterno);
		
		padraoConstrucao = dadosForm.getStringArrayListExtra("padraoConstrucao");
		ArrayAdapter<String> arrayAdapterPadraoConstrucao = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, padraoConstrucao);
		arrayAdapterPadraoConstrucao.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spPadraoConstrucao1.setAdapter(arrayAdapterPadraoConstrucao);
		
		ocupacaoLote = dadosForm.getStringArrayListExtra("ocupacaoLote");
		ArrayAdapter<String> arrayAdapterOcupacaoLote = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ocupacaoLote);
		arrayAdapterOcupacaoLote.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spOcupacaoLote1.setAdapter(arrayAdapterOcupacaoLote);	    
		
	}

	public void preencheDadosEdificacao(){

		arraySelected = dadosForm.getStringArrayListExtra("arraySelected"); 
		anoConstrucao.setText(arraySelected.get(2));
		numeroPavEdificacao.setText(arraySelected.get(3));
		areaConstUnidade.setText(arraySelected.get(4));
				
		for (int i1 = 0; i1 < spTipo1.getCount(); i1++) {  
            if (spTipo1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(5))) {  
            	spTipo1.setSelection(i1);  
            }  
        }

		for (int i1 = 0; i1 < spAlinhamento1.getCount(); i1++) {  
            if (spAlinhamento1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(6))) {  
            	spAlinhamento1.setSelection(i1);  
            }  
        }

		for (int i1 = 0; i1 < spLocacao1.getCount(); i1++) {  
            if (spLocacao1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(7))) {  
            	spLocacao1.setSelection(i1);  
            }  
        }
		
		for (int i1 = 0; i1 < spSituacao1.getCount(); i1++) {  
            if (spSituacao1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(8))) {  
            	spSituacao1.setSelection(i1);  
            }  
        }
		
		for (int i1 = 0; i1 < spEstrutura1.getCount(); i1++) {  
            if (spEstrutura1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(9))) {  
            	spEstrutura1.setSelection(i1);  
            }  
        }

		for (int i1 = 0; i1 < spCobertura1.getCount(); i1++) {  
            if (spCobertura1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(10))) {  
            	spCobertura1.setSelection(i1);  
            }  
        }

		for (int i1 = 0; i1 < spParedes1.getCount(); i1++) {  
            if (spParedes1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(11))) {  
            	spParedes1.setSelection(i1);  
            }  
        }

		for (int i1 = 0; i1 < spVedacoesEsquadrias1.getCount(); i1++) {  
            if (spVedacoesEsquadrias1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(12))) {  
            	spVedacoesEsquadrias1.setSelection(i1);  
            }  
        }
		
		for (int i1 = 0; i1 < spRevExterno1.getCount(); i1++) {  
            if (spRevExterno1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(13))) {  
            	spRevExterno1.setSelection(i1);  
            }  
        }
		
		for (int i1 = 0; i1 < spPadraoConstrucao1.getCount(); i1++) {  
            if (spPadraoConstrucao1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(14))) {  
            	spPadraoConstrucao1.setSelection(i1);  
            }  
        }
		
		for (int i1 = 0; i1 < spOcupacaoLote1.getCount(); i1++) {  
            if (spOcupacaoLote1.getItemAtPosition(i1).toString().equalsIgnoreCase(arraySelected.get(15))) {  
            	spOcupacaoLote1.setSelection(i1);  
            }  
        }

		alvaraConstrucao.setText(arraySelected.get(16));
		apartamento.setText(arraySelected.get(17));
		bloco.setText(arraySelected.get(18));
	}
	
	private void inicializaFindViewById(){
		
		anoConstrucao = (EditText)findViewById(R.id.etAnoConstrucao1);
		numeroPavEdificacao = (EditText)findViewById(R.id.etNumeroPavimentosEdificacao1);
		areaConstUnidade = (EditText)findViewById(R.id.etAreaConstUnidade1);	
		
		spTipo1 = (Spinner)findViewById(R.id.spTipo1);
		spTipo1.setFocusableInTouchMode(true);
		spTipo1.requestFocus();
		
		spAlinhamento1 = (Spinner)findViewById(R.id.spAlinhamento1);
		spAlinhamento1.setFocusableInTouchMode(true);
		spAlinhamento1.requestFocus();
		
		spLocacao1 = (Spinner)findViewById(R.id.spLocacao1);
		spLocacao1.setFocusableInTouchMode(true);
		spLocacao1.requestFocus();
		
		spSituacao1 = (Spinner)findViewById(R.id.spSituacao1);
		spSituacao1.setFocusableInTouchMode(true);
		spSituacao1.requestFocus();
		
		spEstrutura1 = (Spinner)findViewById(R.id.spEstrutura1);
		spEstrutura1.setFocusableInTouchMode(true);
		spEstrutura1.requestFocus();
		
		spCobertura1 = (Spinner)findViewById(R.id.spCobertura1);
		spCobertura1.setFocusableInTouchMode(true);
		spCobertura1.requestFocus();
		
		spParedes1 = (Spinner)findViewById(R.id.spParedes1);
		spParedes1.setFocusableInTouchMode(true);
		spParedes1.requestFocus();
		
		spVedacoesEsquadrias1 = (Spinner)findViewById(R.id.spVedacoesEsquadrias1);
		spVedacoesEsquadrias1.setFocusableInTouchMode(true);
		spVedacoesEsquadrias1.requestFocus();
		
		spRevExterno1 = (Spinner)findViewById(R.id.spRevExterno1);
		spRevExterno1.setFocusableInTouchMode(true);
		spRevExterno1.requestFocus();
		
		spPadraoConstrucao1 = (Spinner)findViewById(R.id.spPadraoConstrucao1);
		spPadraoConstrucao1.setFocusableInTouchMode(true);
		spPadraoConstrucao1.requestFocus();
		
		spOcupacaoLote1 = (Spinner)findViewById(R.id.spOcupacaoLote1);
		spOcupacaoLote1.setFocusableInTouchMode(true);
		spOcupacaoLote1.requestFocus();
		
		alvaraConstrucao = (EditText)findViewById(R.id.etAlvaraConstrucao1);
		apartamento = (EditText)findViewById(R.id.etApartamento1);
		bloco = (EditText)findViewById(R.id.etBloco1);	
	}
	
	@Override
	public void onClick(View v) {
		
		
		switch (v.getId()) {

			case R.id.btnGuardar:
					guardarArquivo();
				break;

			case R.id.btnCancelarEdificacao:
					cancelarEdificacao();
				break;

			case R.id.btnExcluirEdificacao:
					excluirEdificacao();
				break;
			default:
				break;
		}
	}
	
	private void cancelarEdificacao(){
		
	     AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setMessage("Tem certeza que deseja cancelar essa edificação?")
	       .setCancelable(false)
	       .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {	       		
		       		//setResult(RESULT_OK, dadosForm);
					finish();	       		
	           }
	       })
	       .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) { 
	                dialog.cancel();
	           }
	       });
		  AlertDialog alert = builder.create();
	      alert.show();
	}
	
	private void excluirEdificacao(){
		
	     AlertDialog.Builder builder = new AlertDialog.Builder(this);
		 builder.setMessage("Tem certeza que deseja excluir essa edificação?")
	       .setCancelable(false)
	       .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                
	       		
		       		setResult(RESULT_OK, dadosForm);
		       		String edit = dadosForm.getStringExtra("edit");
		       		if(edit.equals("false")){
		       			finish();
		       		}else{
		       			dadosForm.putExtra("o", 6);
		       			finish();
		       		}
	           }
	       })
	       .setNegativeButton("Não", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	             
	                dialog.cancel();
	           }
	       });
		  AlertDialog alert = builder.create();
	      alert.show();
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
	
	private void guardarArquivo() {
		
		if (spTipo1.getSelectedItem().toString().equalsIgnoreCase("Selecione...")){
			
			colocaAlert("O campo Tipo é obrigatório!");
		}else{
			//Intent intent = getIntent();
			setResult(RESULT_OK, dadosForm);
			
			String edit = dadosForm.getStringExtra("edit");
			if(edit.equals("false")){
				dadosForm.putExtra("o", 5);
				
				arraySelected = new ArrayList<String>();
				arraySelected.add(0, "");
				arraySelected.add(1, "");
			}else{
				dadosForm.putExtra("o", 4);
			}
			
			arraySelected.add(2, anoConstrucao.getText().toString());
			arraySelected.add(3, numeroPavEdificacao.getText().toString());
			arraySelected.add(4, areaConstUnidade.getText().toString());
			arraySelected.add(5, spTipo1.getSelectedItem().toString());
			arraySelected.add(6, spAlinhamento1.getSelectedItem().toString());
			arraySelected.add(7, spLocacao1.getSelectedItem().toString());
			arraySelected.add(8, spSituacao1.getSelectedItem().toString());
			arraySelected.add(9, spEstrutura1.getSelectedItem().toString());
			arraySelected.add(10, spCobertura1.getSelectedItem().toString());
			arraySelected.add(11, spParedes1.getSelectedItem().toString());
			arraySelected.add(12, spVedacoesEsquadrias1.getSelectedItem().toString());
			arraySelected.add(13, spRevExterno1.getSelectedItem().toString());
			arraySelected.add(14, spPadraoConstrucao1.getSelectedItem().toString());
			arraySelected.add(15, spOcupacaoLote1.getSelectedItem().toString());
			arraySelected.add(16, alvaraConstrucao.getText().toString());
			arraySelected.add(17, apartamento.getText().toString());
			arraySelected.add(18, bloco.getText().toString());
			
			if(edit.equals("false")){
				arraySelected.add(19, "");
			}
			
			dadosForm.putStringArrayListExtra("arraySelected", (ArrayList<String>) arraySelected);
			finish();			
		}		
	}
	
	
	
}
