package br.com.viageo;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class Erros extends ListActivity implements OnClickListener{
	
	ArrayList<String> arrayValidacoes = new ArrayList<String>();
	Button btnCorrigir, btnSalvarTmp, btnSalvarDefinitivo;
	Intent intentForm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.erros);
		
		btnCorrigir = (Button)findViewById(R.id.btnCorrigir);
		btnCorrigir.setOnClickListener(this);	
		
		btnSalvarTmp = (Button)findViewById(R.id.btnSalvarTmp);
		btnSalvarTmp.setOnClickListener(this);	
		
		btnSalvarDefinitivo = (Button)findViewById(R.id.btnSalvarDefinitivo);
		btnSalvarDefinitivo.setOnClickListener(this);
		
		intentForm = getIntent();
		arrayValidacoes = intentForm.getStringArrayListExtra("array_validacoes");

		
		if(arrayValidacoes.size() > 0){
			montaListViewValidacoes();
		}
	}

	private void montaListViewValidacoes(){
		
		setListAdapter(new ArrayAdapter<String>(this,R.layout.modelo_item_da_lista,R.id.list_content, arrayValidacoes));
    
		this.getListView().setFastScrollEnabled(true);
		
		String colocaBotao = intentForm.getStringExtra("colocaBotao");
		String colocaBotao2 = intentForm.getStringExtra("colocaBotao2");
		
		
		
		if (colocaBotao.equals("true")){		
			btnSalvarDefinitivo.setVisibility(View.GONE);
		}
		
		if(colocaBotao2.equals("true")){
			btnSalvarTmp.setVisibility(View.GONE);
			
			
		}
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnCorrigir:
			String quadra = intentForm.getStringExtra("tipo");
			
			if (quadra.equals("quadra")){
				String inscricao = intentForm.getStringExtra("inscricao");
				Intent winForm = new Intent(this,Formulario.class);
				winForm.putExtra("inscricao",inscricao);
				startActivity(winForm);
				finish();
				arrayValidacoes = null;
			}else{
				//intentForm.putExtra("o", 0);
				finish();	
				arrayValidacoes = null;
			}
			break;
		case R.id.btnSalvarTmp:
			setResult(RESULT_OK, intentForm);
			intentForm.putExtra("o", 11);
   			finish();
   			arrayValidacoes = null;
		break;
		case R.id.btnSalvarDefinitivo:
			
			setResult(RESULT_OK, intentForm);
			intentForm.putExtra("o", 12);
   			finish();
   			arrayValidacoes = null;
		break;

		
		}
		
	}

}
