package br.com.viageo;

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Foto extends ListActivity implements OnClickListener {
	ArrayList<String> arrayNomeArquivo = new ArrayList<String>();
	String inscricao;
	Intent intentForm;
	String arquivo;
	//private Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foto);
		
		ImageButton btnImg = (ImageButton)findViewById(R.id.btnTirarFotoLista);
		btnImg.setOnClickListener(this);
		
		Button btnSalvar = (Button)findViewById(R.id.btnSalvarFotoCamera);
		btnSalvar.setOnClickListener(this);
		
		
		
		intentForm = getIntent();
		
		arrayNomeArquivo = intentForm.getStringArrayListExtra("arrayNomeArquivo");
		
		inscricao = intentForm.getStringExtra("inscricao");
		if(arrayNomeArquivo.size() > 0){
			montaListView();
		}
		/*
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		String nome_arquivo = inscricao+"_"+System.currentTimeMillis();
		//arrayNomeArquivo.add(nome_arquivo);
		String arquivo = Environment.getExternalStorageDirectory() + "/" + nome_arquivo + ".jpg";
		File file = new File(arquivo);
		Uri outputFileUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, 1);
		*/
		
	}
	
	public void excluirFoto(int indice) {
	
		//String tmp = Environment.getExternalStorageDirectory() + "/" + (arrayNomeArquivo.get(indice)).replace("/mnt/sdcard/", "");
		File file = new File(arrayNomeArquivo.get(indice));
		boolean deleted = file.delete();
		if(deleted){
			arrayNomeArquivo.remove(indice);
			montaListView();
		}else{
			Toast.makeText(getBaseContext(), "Erro ao excluir a foto!", Toast.LENGTH_LONG).show();
		}
	}

	private void montaListView(){
		
		/*
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_fotos, null);
        
        // Recuperando as views do layout
        ImageView img = (ImageView) view.findViewById(R.id.imageViewMini);
        
        // Setando os dados no layout inflado 
        // Dados estes que vem da minha lista
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(arrayNomeArquivo.get(0), options);
        img.setImageBitmap(bm);
        //img.setImageBitmap(arrayNomeArquivo.get(0).toString());
        
        this.getListView().setAdapter((ListAdapter) view);
        */
		
		
		
        this.getListView().setAdapter(new ListFotos(this, arrayNomeArquivo, Foto.this));
        //this.getListView().setAdapter(new MyListAdapter(this, list, "2"));

		// Habilita o scroll r�pido na lista
		this.getListView().setFastScrollEnabled(true);

		this.getListView().setItemsCanFocus(true);
		/*
		ListAdapter a = this.getListView().getAdapter();
		
		*/
		/*
		this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
		       public void onItemClick(AdapterView<?> list, View v, int pos, long id) {
		    	   ImageButton imgExclui = (ImageButton)v.findViewById(R.id.ibtnExcluirFoto);
		    	   Toast.makeText(getBaseContext(), "FOR"+list.getItemAtPosition(pos), Toast.LENGTH_LONG).show();
		       }
		    });
		*/
		/*
		this.getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
				Toast.makeText(getApplicationContext(),
					"Click ListItem Number " + position, Toast.LENGTH_LONG)
					.show();
				
				ImageButton fotoExcluir = (ImageButton)findViewById(R.id.ibtnExcluirFoto);
				fotoExcluir.setOnClickListener(position);
			}
		});*/
		
		//ImageButton fotoExcluir = (ImageButton)findViewById(R.id.ibtnExcluirFoto);
		//fotoExcluir.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnTirarFotoLista:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			String nome_arquivo = inscricao+"_"+System.currentTimeMillis();
			
			//caminho do cartao SD+ nome do arquivo
			arquivo = Environment.getExternalStorageDirectory() + "/VgFiscalFotos/" + nome_arquivo + ".jpg";
			
			//Criando um arquivo da imagem
			File file = new File(arquivo);
			Uri outputFileUri = Uri.fromFile(file);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			
			startActivityForResult(intent, 1);
			break;
		case R.id.btnSalvarFotoCamera:			
			//Setando resultado da Intent Foto.Java
			setResult(RESULT_OK, intentForm);
			
			//parametros que vao retornar a Formulario.Java
			intentForm.putExtra("o", 10);
			intentForm.putStringArrayListExtra("arrayNomeArquivo", (ArrayList<String>) arrayNomeArquivo);
			
			//Finalizando a Intent Foto 
			finish();
			break;
	
		default:
			break;
		}
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		
		//resultCode = -1 quer dizer que a foto foi aceita (botao OK)
		//resultCode = 1 quer dizer que a foto foi cancelada
		//requestCode = 1 eh o sucesso nesse caso
		if(requestCode == 1 && resultCode == -1){
		
			
			//Adiciona a nova foto no array de fotos
			arrayNomeArquivo.add(arquivo);
			
			//Coloca a foto na Lista de Fotos (Visual)
			montaListView();
			
			/*
			ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
			BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 2;
	        Bitmap bm = BitmapFactory.decodeFile(arrayNomeArquivo.get(0), options);
	        imageView1.setImageBitmap(bm);
		    */
		}
	}
	
}
