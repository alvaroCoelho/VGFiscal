package br.com.viageo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ListFotos extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;
	private Foto obj;
   // Object pos;
    //private int pos = 0;
    
    public ListFotos(Context context, ArrayList<String> list, Foto objFoto) {
        this.context = context;
        this.list = list;
        this.obj = objFoto;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public Object getItem(int position) {
        return list.get(position);
    }
    
    public long getItemId(int position) {
        return position;
    }
    
    
    public View getView(int position, View convertView, ViewGroup parent) {
        final String lista = list.get(position);    	
    	
        // Inflando o layout das linhas, ou seja, aqui eu defino que as linhas ser�o
        // conforme o arquivo de layout "R.layout.row_layout"
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        view = inflater.inflate(R.layout.list_fotos, null);
        
        view.setClickable(true);  
        view.setFocusable(true);
        
        // Recuperando as views do layout
        ImageView img = (ImageView) view.findViewById(R.id.imageViewMini);
        ImageButton imgButton = (ImageButton) view.findViewById(R.id.ibtnExcluirFoto);
        /*
        view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//CARREGA REGISTRO DE ACORDO COM O ITEM SELECIONADO  
                int indice = list.indexOf(lista);
       
			}
		});
        */
        
        
        imgButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
          	  AlertDialog alertDialog = new AlertDialog.Builder(context).create();  
              alertDialog.setTitle("Deseja realmente excluir?");
              alertDialog.setMessage("Ao clicar em 'OK' a foto "+" será excluída permanentemente!");  
              alertDialog.setButton("OK", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int which) {      
                       	 int indice = list.indexOf(lista);
       
                         //context.carregaCadastro(indice);//Metodo nao encontrado
                         obj.excluirFoto(indice);
                 }  
      });  
              alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int which) {  
                        return;  
                  }   
              });  
              alertDialog.show();  
            }
        });

        
        
        //imgButton.setOnClickListener(this);
        //pos = this.getItem(position);
        /*
        System.gc();
        //define o tamanho e converte a imagem de jpg para bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
		options.inTempStorage = new byte[8*1024];
	    options.inPurgeable = true;
        Bitmap bm = BitmapFactory.decodeFile(list.get(position), options);
        //coloca na lista visual (list_foto.xml)
        img.setImageBitmap(bm);
        //bm.recycle();
        */
        File f = new File(list.get(position));
        Bitmap bm1 = decodeFile(f);
        img.setImageBitmap(bm1);
        
        return view;
    }
  //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //The new size we want to scale to
            final int REQUIRED_SIZE=70;

            //Find the correct scale value. It should be the power of 2.
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
          Log.e(null, "Erro no método decodeFile:"+e.getLocalizedMessage());
        }
        return null;
    }
    /*
	@Override
	public void onClick(View v) {
		Toast.makeText(context, "ssss"+pos, Toast.LENGTH_LONG).show();

		list.remove(pos);
		
	}
	*/
    
}