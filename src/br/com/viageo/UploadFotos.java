package br.com.viageo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

public class UploadFotos {
	
	ArrayList<String> arrayImagens = new ArrayList<String>();
	ArrayList<String> arrayNomeImagens = new ArrayList<String>();
	int quantidadeTentativas = 0;
	ArrayList<String> arrayCaminhosImagens = new ArrayList<String>();
	
	private Context context;
	
	public UploadFotos(Context context){
		this.context = context;
	}
		
	public void enviandoTodasFotos(int quantiaImg){
		arrayImagens.clear();
		arrayNomeImagens.clear();
		arrayCaminhosImagens.clear();
		
		String ff = Environment.getExternalStorageDirectory() + "/VgFiscalFotos";
			
		File dir = new File( ff );  
		File[] fileNames = dir.listFiles();
		
		//for(int i=0; i< fileNames.length; i++){
		for(int i=0; i < quantiaImg; i++){
			if(fileNames[i].isFile()){
				if(fileNames[i].getAbsolutePath().endsWith(".jpg")){
					//arrayImagens.add(fileNames[i].toString());
					arrayCaminhosImagens.add(fileNames[i].toString());
					//Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.camera);
					
					System.gc();

					BitmapFactory.Options options = new BitmapFactory.Options();
					//options.inPreferredConfig = Config.RGB_565;
					options.inSampleSize = 3;
					options.inTempStorage = new byte[8*1024];
				    options.inPurgeable = true;
					Bitmap bitmap = BitmapFactory.decodeFile(fileNames[i].toString(), options);
					
					//Bitmap bitmap = BitmapFactory.decodeFile(fileNames[i].toString());
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream); //compress to which format you want.
					byte [] byte_arr = stream.toByteArray();
					//String image_str = Base64.encodeBytes(byte_arr);
					String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
					arrayImagens.add(image_str);
					String[] tmp = (fileNames[i].toString()).split("/");
					String[] tmp2 = (tmp[(tmp.length-1)]).split("_");
					arrayNomeImagens.add(tmp2[0]);
					//if(bitmap != null){
					//	bitmap.recycle();
					//	bitmap = null;
					//}
					bitmap.recycle();
					bitmap = null;
					stream = null;
					byte_arr = null;
					image_str = null;
					tmp = null;
					tmp2 = null;
				    //Runtime.getRuntime().gc();
				}
			}
		}
		ff = null;
		dir = null;
		fileNames= null;
		
		//percorreArray(arrayImagens);
		if(arrayImagens.size() > 0){
			//synchronized (context) {
				enviaFoto();
				//colocaAlert("Enviou!");
			//}
		}else{
			colocaAlert("Não existe Foto para ser enviada!");
		}
	
		//finish();
	}
	/*
	public void testeEnvia(){
		HttpPost post = new HttpPost(URL);
		HttpClient client = new DefaultHttpClient();
		HttpClient httpclient;
		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );

		// picture
		entity.addPart( "userfile", new FileBody( 
		    new File( MyApp.getContext().getFilesDir(), "userfile.jpg" ),
		    "image/jpeg")
		);

		entity.addPart( "blahblah", new StringBody( "blah" ));  // string value

		post.setEntity( entity );
		client.execute( post );
	}*/
	
	public void colocaAlert(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
	/*
	public void percorreArray(ArrayList<String> array){
		if(quantidadeTentativas <= 10){
			for(int j=0; j < array.size(); j++){
				quantidadeTentativas++;
				//Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.camera);
				Bitmap bitmap = BitmapFactory.decodeFile(array.get(j));
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream); //compress to which format you want.
				byte [] byte_arr = stream.toByteArray();
				String image_str = Base64.encodeBytes(byte_arr);
				if(enviaFoto(image_str)){
					arrayImagens.remove(j);
				}else{
					percorreArray(arrayImagens);
					break;
				}
			}
		}
	}*/
	
	public boolean enviaFoto(){
		boolean ret = false;
		
		try {
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "14");
			
			for(int k=0; k < arrayImagens.size(); k++){
				//String tmp = arrayImagens.get(k);
	
				objConexao.setDados("image"+k, arrayImagens.get(k));
				objConexao.setDados("inscricao"+k, arrayNomeImagens.get(k));
			}
			
			objConexao.setDados("numero", arrayImagens.size()+"");
			
			JSONObject retorno = objConexao.envia();
			
			if (retorno.getString("ok").equals("1")){
				removeFoto();
				arrayImagens.clear();
				arrayNomeImagens.clear();
				arrayCaminhosImagens.clear();
				//ret = true;
				System.gc();
			}
		} catch (Exception e) {
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			//e.printStackTrace();
		}
		
		return ret;
	}
	
	public void removeFoto(){
		for(int y=0; y < arrayCaminhosImagens.size(); y++){
			File file = new File(arrayCaminhosImagens.get(y));
			file.delete();
		}
	}
}
