package br.com.viageo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpConection {
	//String url_conexao = "http://192.168.0.167/geo_fpolis/conexaoAndroid/conexao_android.php";	
	String url_conexao = "http://www2.viageo.com.br:88/geo_fpolis/conexaoAndroid/conexao_android.php";
	//String url_conexao = "http://www2.viageo.com.br/geo_gaspar/conexaoAndroid/conexao_android.php";
	//String url_conexao = "http://geo.goianesia.go.gov.br/geo_goianesia/conexaoAndroid/conexao_android.php";
	//Create a HTTPClient as the form container
	HttpClient httpclient;
	
	HttpPost httppost;
	
	//Create an array lst for the input data to be sentt
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	
	//Create a HTTP Response and HTTP Entity
	HttpResponse response;
	HttpEntity entity;
	
	Integer statusConexao;
	String statusErro;
	JSONObject jsonResponse;
	
	//public HttpConection(String url{
	public HttpConection(){
		nameValuePairs.clear();
		//url_conexao = url; //"http://192.168.0.15/geo_goianesia/conexao_android.php"
		conecta();
	}
	
	public void conecta(){
		//Create new default HTTPClient
		httpclient = new DefaultHttpClient();
		
		//Create new HTTP POST with URL to PHP file as parameter
		httppost = new HttpPost(url_conexao);
	}
	
	public void setDados(String nomeDado, String dado){
		//plase them in an array list
		nameValuePairs.add(new BasicNameValuePair(nomeDado, dado));
	}
	
	public JSONObject envia() throws JSONException{
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			//assign executed form container to response
			response = httpclient.execute(httppost);
			
			//check status code, need to check status code 200
			statusConexao = response.getStatusLine().getStatusCode();
			if(statusConexao == 200){
				entity = response.getEntity();
				if(entity != null){
					//Create new inputs stream with received data assigned
					InputStream instream = entity.getContent();
					
					//Create new JSON Object. assign convert data as parameter
					jsonResponse = new JSONObject(convertStreamToString(instream));
					
				}
			}else{
				String jsonErro = "{\"ok\":\"0\",\"erro\":\"Erro de Conexão! Status "+statusConexao+"\"}";
				jsonResponse = new JSONObject(jsonErro);
			}
		}catch (Exception e) {
			
			statusErro = e.getMessage();
			
			String jsonErro = "{\"ok\":\"0\",\"erro\":\""+e.toString()+"!\"}";
			jsonResponse = new JSONObject(jsonErro);
		}
		return jsonResponse;
	}
	
	public String getStatusConexao(){
		String erro = "";
		if(statusConexao == 200){
			erro = "Conexão estabeleciada com sucesso!";
		}else{
			erro = "Erro de Conexão! Erro: "+statusConexao;
		}
		return erro;
	}
	
	public String getStatusErro(){
		return statusErro;
	}
	
	public static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
}
