package br.com.viageo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyData> list;
    private String tipoPesquisa;
    
    public MyListAdapter(Context context, ArrayList<MyData> list, String tpPesquisa) {
        this.context = context;
        this.list = list;
        this.tipoPesquisa = tpPesquisa;
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
        
        // Inflando o layout das linhas, ou seja, aqui eu defino que as linhas ser�o
        // conforme o arquivo de layout "R.layout.row_layout"
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if(this.tipoPesquisa.equalsIgnoreCase("1")){
            view = inflater.inflate(R.layout.pesquisa, null);
            
            // Recuperando as views do layout
            TextView topText = (TextView) view.findViewById(R.id.primeiroTexto);
            TextView leftText = (TextView) view.findViewById(R.id.segundoTexto);
            TextView rightText = (TextView) view.findViewById(R.id.terceiroTexto);
            
            // Setando os dados no layout inflado 
            // Dados estes que vem da minha lista
            topText.setText(list.get(position).getText1());
            leftText.setText(list.get(position).getText2());
            rightText.setText(list.get(position).getText3());
        	
        }else{
            view = inflater.inflate(R.layout.pesquisa2, null);
            
            // Recuperando as views do layout
            TextView topText = (TextView) view.findViewById(R.id.primeiroTextoPesquisa2);
            TextView leftText = (TextView) view.findViewById(R.id.segundoTextoPesquisa2);
            TextView rightText = (TextView) view.findViewById(R.id.terceiroTextoPesquisa2);
            
            // Setando os dados no layout inflado 
            // Dados estes que vem da minha lista
            topText.setText(list.get(position).getText1());
            leftText.setText(list.get(position).getText2());
            rightText.setText(list.get(position).getText3());
        }
            
        return view;
    }
    
}