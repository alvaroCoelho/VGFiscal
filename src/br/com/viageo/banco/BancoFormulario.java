package br.com.viageo.banco;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.viageo.HttpConection;
import br.com.viageo.MyData;
import br.com.viageo.entidade.FormularioEntidade;
import br.com.viageo.entidade.Quadra;


 public class BancoFormulario{
	
	private CriacaoBanco banco;
	JSONArray resultados1, resultados2;
	int tamanhoBase,restante;
	
	
	
	
	
   public BancoFormulario(Context context) {
		super();
		this.banco = new CriacaoBanco(context);
	}

   public void inserir(FormularioEntidade formulario) {
	

		
	ContentValues valores = new ContentValues();
	
	
	String cd_quadra = formulario.getNu_insc_imbl().substring(0, 7);
	String cd_lote = formulario.getNu_insc_imbl().substring(0, 11);
    String inscricao = "";
	
	
	if(formulario.getNu_insc_imbl().length()== 14){
		inscricao = DV(formulario.getNu_insc_imbl());
		
	}else{
		inscricao = formulario.getNu_insc_imbl();
	}
	
	valores.put("nu_insc_imbl",inscricao);
	valores.put("cd_logr",formulario.getCd_logr());
	valores.put("id_logra",formulario.getId_logra());
	valores.put("cd_seca",VerificaCamposNulos(formulario.getCd_seca()));
	valores.put("nu_imvl", VerificaCamposNulos(formulario.getNu_imvl()));
	valores.put("de_comp",VerificaCamposNulos(formulario.getDe_comp()));
	valores.put("id_bairro",VerificaCamposNulos(formulario.getId_bairro()));
	valores.put("nu_lotm",VerificaCamposNulos(formulario.getNu_lotm()));		
	valores.put("nu_qudr",VerificaCamposNulos(formulario.getNu_qudr()));
	valores.put("nu_lote",VerificaCamposNulos(formulario.getNu_lote()));
	valores.put("nu_pess",VerificaCamposNulos(formulario.getNu_pess()));
	valores.put("nu_apto_garg",VerificaCamposNulos(formulario.getNu_apto_garg()));
	valores.put("in_endr_corr",VerificaCamposNulos(formulario.getIn_endr_corr()));
	valores.put("nm_bloc",VerificaCamposNulos(formulario.getNm_bloc()));
	valores.put("nm_edfc",VerificaCamposNulos(formulario.getNm_edfc()));
	valores.put("tp_ocpc_lote",VerificaCamposNumericosNulos(formulario.getTp_ocpc_lote()));
	valores.put("tp_murd",VerificaCamposNumericosNulos(formulario.getTp_murd()));
	valores.put("tp_patr",VerificaCamposNumericosNulos(formulario.getTp_patr()));
	valores.put("tp_terr_marn",VerificaCamposNumericosNulos(formulario.getTp_terr_marn()));
	valores.put("tp_utlz",VerificaCamposNumericosNulos(formulario.getTp_utlz()));
	valores.put("tp_pass",VerificaCamposNumericosNulos(formulario.getTp_pass()));
	valores.put("tp_arvr",VerificaCamposNumericosNulos(formulario.getTp_arvr()));
	valores.put("tp_ligc_agua",VerificaCamposNumericosNulos(formulario.getTp_ligc_agua()));
	valores.put("tp_elvd",VerificaCamposNumericosNulos(formulario.getTp_elvd()));
	valores.put("tp_esgt",VerificaCamposNumericosNulos(formulario.getTp_esgt()));
	valores.put("tp_eltr",VerificaCamposNumericosNulos(formulario.getTp_eltr()));
	valores.put("tp_isnt_iptu",VerificaCamposNumericosNulos(formulario.getTp_isnt_iptu()));
	valores.put("tp_isnt_tsu",VerificaCamposNulos(formulario.getTp_isnt_tsu()));
	valores.put("nu_pavm",VerificaCamposNulos(formulario.getNu_pavm()));
	valores.put("nu_casan",VerificaCamposNulos(formulario.getNu_casan()));
	valores.put("nu_celesc",VerificaCamposNulos(formulario.getNu_celesc()));
	valores.put("nu_matr_cart",VerificaCamposNulos(formulario.getNu_matr_cart()));
	valores.put("nu_livr_cart",VerificaCamposNulos(formulario.getNu_livr_cart()));
	valores.put("nu_folh_cart",VerificaCamposNulos(formulario.getNu_folh_cart()));
	valores.put("cd_tabl_cart",VerificaCamposNulos(formulario.getCd_tabl_cart()));
	valores.put("vvenal",VerificaCamposNulos(formulario.getVvenal()));
	valores.put("qt_area_lote",VerificaCamposNulos(formulario.getQt_area_lote()));
	valores.put("qt_area_cons",VerificaCamposNulos(formulario.getQt_area_cons()));
	valores.put("tp_sitc_qudr",VerificaCamposNumericosNulos(formulario.getTp_sitc_qudr()));
	valores.put("tp_topg",VerificaCamposNumericosNulos(formulario.getTp_topg()));
	valores.put("qt_prfn",VerificaCamposNumericosNulos(formulario.getQt_prfn()));
	valores.put("tp_pedl",VerificaCamposNumericosNulos(formulario.getTp_pedl()));
	valores.put("nu_test_prnc",VerificaCamposNulos(formulario.getNu_test_prnc()));
	valores.put("cd_logr_tes2",VerificaCamposNulos(formulario.getCd_logr_tes2()));
	valores.put("cd_seca_tes2",VerificaCamposNulos(formulario.getCd_seca_tes2()));
	valores.put("nu_tes2",VerificaCamposNulos(formulario.getNu_tes2()));
	valores.put("cd_logr_tes3",VerificaCamposNulos(formulario.getCd_logr_tes3()));
	valores.put("cd_seca_tes3",VerificaCamposNulos(formulario.getCd_seca_tes3()));
	valores.put("nu_tes3",VerificaCamposNulos(formulario.getNu_tes3()));
	valores.put("cd_logr_tes4",VerificaCamposNulos(formulario.getCd_logr_tes4()));
	valores.put("cd_seca_tes4",VerificaCamposNulos(formulario.getCd_seca_tes4()));
	valores.put("nu_tes4",VerificaCamposNulos(formulario.getNu_tes4()));
	valores.put("aa_cons",VerificaCamposNulos(formulario.getAa_cons()));
	valores.put("nu_pav_edif",VerificaCamposNulos(formulario.getNu_pav_edif()));
	valores.put("qt_area_undd",VerificaCamposNulos(formulario.getQt_area_undd()));
	valores.put("tp_edfc",VerificaCamposNumericosNulos(formulario.getTp_edfc()));
	valores.put("tp_alnh",VerificaCamposNumericosNulos(formulario.getTp_alnh()));
	valores.put("tp_locc",VerificaCamposNumericosNulos(formulario.getTp_locc()));
	valores.put("tp_sitc",VerificaCamposNumericosNulos(formulario.getTp_sitc()));
	valores.put("tp_ocpc",VerificaCamposNumericosNulos(formulario.getTp_ocpc()));
	valores.put("tp_estr",VerificaCamposNumericosNulos(formulario.getTp_estr()));
	valores.put("tp_cobr",VerificaCamposNumericosNulos(formulario.getTp_cobr()));
	valores.put("tp_pard",VerificaCamposNumericosNulos(formulario.getTp_pard()));
	valores.put("tp_vedc",VerificaCamposNumericosNulos(formulario.getTp_vedc()));
	valores.put("tp_revs_extr",VerificaCamposNumericosNulos(formulario.getTp_revs_extr()));
	valores.put("tp_padr_cons",VerificaCamposNumericosNulos(formulario.getTp_padr_cons()));
	valores.put("qt_afst_frnt",VerificaCamposNulos(formulario.getQt_afst_frnt()));
	valores.put("in_clim_ambi",VerificaCamposNulos(formulario.getIn_clim_ambi()));
	valores.put("in_eqpt_segr",VerificaCamposNulos(formulario.getIn_eqpt_segr()));
	valores.put("in_salo_fest",VerificaCamposNulos(formulario.getIn_salo_fest()));
	valores.put("in_chur",VerificaCamposNulos(formulario.getIn_chur()));
	valores.put("in_saun",VerificaCamposNulos(formulario.getIn_saun()));
	valores.put("in_pisc",VerificaCamposNulos(formulario.getIn_pisc()));
	valores.put("in_lare",VerificaCamposNulos(formulario.getIn_lare()));
	valores.put("in_sala_jogo",VerificaCamposNulos(formulario.getIn_sala_jogo()));
	valores.put("in_sala_gina",VerificaCamposNulos(formulario.getIn_sala_gina()));
	valores.put("in_aque_gas",VerificaCamposNulos(formulario.getIn_aque_gas()));
	valores.put("in_elev_serv",VerificaCamposNulos(formulario.getIn_elev_serv()));
	valores.put("in_elev_soci",VerificaCamposNulos(formulario.getIn_elev_soci()));
	valores.put("qt_garg",VerificaCamposNulos(formulario.getQt_garg()));
	valores.put("cd_lote",VerificaCamposNulos(cd_lote));
	valores.put("cd_quadra",VerificaCamposNulos(cd_quadra));
	valores.put("cadastrador",VerificaCamposNulos(formulario.getCadastrador()));
	valores.put("obs_geo",VerificaCamposNulos(formulario.getObs_geo()));
	valores.put("data_cadastro",VerificaCamposNulos(formulario.getData_cadastro()));
	valores.put("ativo",VerificaCamposNulos(formulario.getAtivo()));
	valores.put("dt_atualizacao",VerificaCamposNulos(formulario.getDt_atualizacao()));
	valores.put("nm_user_atualizacao",VerificaCamposNulos(formulario.getNm_user_atualizacao()));
	valores.put("comando",VerificaCamposNulos(formulario.getComando()));
	valores.put("dt_cadastro",VerificaCamposNulos(formulario.getDt_cadastro()));
	valores.put("nm_user_cadastro",VerificaCamposNulos(formulario.getNm_user_cadastro()));
	valores.put("controle_tablet",VerificaCamposNulos(formulario.getControle_tablet()));
	valores.put("edicao",VerificaCamposNulos(formulario.getEdicao()));
	valores.put("status_associacao",VerificaCamposNulos(formulario.getStatus_associacao()));
	valores.put("status_cadastro",VerificaCamposNulos(formulario.getStatus_cadastro()));
	valores.put("nu_insc_imbl_ant",VerificaCamposNulos(formulario.getNu_insc_imbl_ant()));
	valores.put("in_canc_cimb",VerificaCamposNulos(formulario.getIn_canc_cimb()));
	valores.put("sincronizado",VerificaCamposNulos(formulario.getSincronizado()));
	valores.put("tipo_cadastro",VerificaCamposNulos(formulario.getTipo_cadastro()));
	banco.getWritableDatabase().insert("cotr_imobiliario",null, valores);	
	banco.close();
	
	
	 
	}
	
   public void atualizar(FormularioEntidade formulario){
    	
    	String cd_quadra = formulario.getNu_insc_imbl().substring(0, 7);
    	String cd_lote = formulario.getNu_insc_imbl().substring(0, 11);
    	
    	String[] Idselecionado = {formulario.getNu_insc_imbl()}; 
		ContentValues valores = new ContentValues();
    	
		valores.put("nu_insc_imbl",formulario.getNu_insc_imbl());
		valores.put("cd_logr",formulario.getCd_logr());
		valores.put("id_logra",formulario.getId_logra());
		valores.put("cd_seca",VerificaCamposNulos(formulario.getCd_seca()));
		valores.put("nu_imvl", VerificaCamposNulos(formulario.getNu_imvl()));
		valores.put("de_comp",VerificaCamposNulos(formulario.getDe_comp()));
		valores.put("id_bairro",VerificaCamposNulos(formulario.getId_bairro()));
		valores.put("nu_lotm",VerificaCamposNulos(formulario.getNu_lotm()));
		valores.put("nu_lote",VerificaCamposNulos(formulario.getNu_lote()));
		valores.put("nu_pess",VerificaCamposNulos(formulario.getNu_pess()));
		valores.put("cd_imbl",VerificaCamposNulos(formulario.getCd_imbl()));
		valores.put("nu_apto_garg",VerificaCamposNulos(formulario.getNu_apto_garg()));
		valores.put("nm_bloc",VerificaCamposNulos(formulario.getNm_bloc()));
		valores.put("nm_edfc",VerificaCamposNulos(formulario.getNm_edfc()));
		valores.put("in_endr_corr",VerificaCamposNulos(formulario.getIn_endr_corr()));
		valores.put("nu_qudr",VerificaCamposNulos(formulario.getNu_qudr()));
		valores.put("tp_ocpc_lote",VerificaCamposNulos(formulario.getTp_ocpc_lote()));
		valores.put("tp_murd",VerificaCamposNulos(formulario.getTp_murd()));
		valores.put("tp_patr",VerificaCamposNulos(formulario.getTp_patr()));
		valores.put("tp_terr_marn",VerificaCamposNulos(formulario.getTp_terr_marn()));
		valores.put("tp_utlz",VerificaCamposNulos(formulario.getTp_utlz()));
		valores.put("tp_pass",VerificaCamposNulos(formulario.getTp_pass()));
		valores.put("tp_arvr",VerificaCamposNulos(formulario.getTp_arvr()));
		valores.put("tp_ligc_agua",VerificaCamposNulos(formulario.getTp_ligc_agua()));
		valores.put("tp_elvd",VerificaCamposNulos(formulario.getTp_elvd()));
		valores.put("tp_esgt",VerificaCamposNulos(formulario.getTp_esgt()));
		valores.put("tp_eltr",VerificaCamposNulos(formulario.getTp_eltr()));
		valores.put("tp_isnt_iptu",VerificaCamposNulos(formulario.getTp_isnt_iptu()));
		valores.put("tp_isnt_tsu",VerificaCamposNulos(formulario.getTp_isnt_tsu()));
		valores.put("nu_pavm",VerificaCamposNulos(formulario.getNu_pavm()));
		valores.put("nu_celesc",VerificaCamposNulos(formulario.getNu_celesc()));
		valores.put("nu_casan",VerificaCamposNulos(formulario.getNu_casan()));
		valores.put("nu_matr_cart",VerificaCamposNulos(formulario.getNu_matr_cart()));
		valores.put("nu_livr_cart",VerificaCamposNulos(formulario.getNu_livr_cart()));
		valores.put("nu_folh_cart",VerificaCamposNulos(formulario.getNu_folh_cart()));
		valores.put("cd_tabl_cart",VerificaCamposNulos(formulario.getCd_tabl_cart()));
		valores.put("vvenal",VerificaCamposNulos(formulario.getVvenal()));
		valores.put("dt_prcs_revs",VerificaCamposNulos(formulario.getNu_prcs_lanc_revs()));
		valores.put("qt_area_lote",VerificaCamposNulos(formulario.getQt_area_lote()));
		valores.put("qt_area_cons",VerificaCamposNulos(formulario.getQt_area_cons()));
		valores.put("tp_sitc_qudr",VerificaCamposNulos(formulario.getTp_sitc_qudr()));
		valores.put("tp_topg",VerificaCamposNulos(formulario.getTp_topg()));
		valores.put("qt_prfn",VerificaCamposNulos(formulario.getQt_prfn()));
		valores.put("tp_pedl",VerificaCamposNulos(formulario.getTp_pedl()));
		valores.put("nu_test_prnc",VerificaCamposNulos(formulario.getNu_test_prnc()));
		valores.put("cd_logr_tes2",VerificaCamposNulos(formulario.getCd_logr_tes2()));
		valores.put("cd_seca_tes2",VerificaCamposNulos(formulario.getCd_seca_tes2()));
		valores.put("nu_tes2",VerificaCamposNulos(formulario.getNu_tes2()));
		valores.put("cd_logr_tes3",VerificaCamposNulos(formulario.getCd_logr_tes3()));
		valores.put("cd_seca_tes3",VerificaCamposNulos(formulario.getCd_seca_tes3()));
		valores.put("nu_tes3",VerificaCamposNulos(formulario.getNu_tes3()));
		valores.put("cd_logr_tes4",VerificaCamposNulos(formulario.getCd_logr_tes4()));
		valores.put("cd_seca_tes4",VerificaCamposNulos(formulario.getCd_seca_tes4()));
		valores.put("nu_tes4",VerificaCamposNulos(formulario.getNu_tes4()));
		valores.put("aa_cons",VerificaCamposNulos(formulario.getAa_cons()));
		valores.put("nu_pav_edif",VerificaCamposNulos(formulario.getNu_pav_edif()));
		valores.put("qt_area_undd",VerificaCamposNulos(formulario.getQt_area_undd()));
		valores.put("tp_edfc",VerificaCamposNulos(formulario.getTp_edfc()));
		valores.put("tp_alnh",VerificaCamposNulos(formulario.getTp_alnh()));
		valores.put("tp_locc",VerificaCamposNulos(formulario.getTp_locc()));
		valores.put("tp_sitc",VerificaCamposNulos(formulario.getTp_sitc()));
		valores.put("tp_ocpc",VerificaCamposNulos(formulario.getTp_ocpc()));
		valores.put("tp_estr",VerificaCamposNulos(formulario.getTp_estr()));
		valores.put("tp_cobr",VerificaCamposNulos(formulario.getTp_cobr()));
		valores.put("tp_pard",VerificaCamposNulos(formulario.getTp_pard()));
		valores.put("tp_vedc",VerificaCamposNulos(formulario.getTp_vedc()));
		valores.put("tp_revs_extr",VerificaCamposNulos(formulario.getTp_revs_extr()));
		valores.put("tp_padr_cons",VerificaCamposNulos(formulario.getTp_padr_cons()));
		valores.put("in_clim_ambi",VerificaCamposNulos(formulario.getIn_clim_ambi()));
		valores.put("in_eqpt_segr",VerificaCamposNulos(formulario.getIn_eqpt_segr()));
		valores.put("in_salo_fest",VerificaCamposNulos(formulario.getIn_salo_fest()));
		valores.put("in_chur",VerificaCamposNulos(formulario.getIn_chur()));
		valores.put("in_saun",VerificaCamposNulos(formulario.getIn_saun()));
		valores.put("in_pisc",VerificaCamposNulos(formulario.getIn_pisc()));
		valores.put("in_lare",VerificaCamposNulos(formulario.getIn_lare()));
		valores.put("in_sala_jogo",VerificaCamposNulos(formulario.getIn_sala_jogo()));
		valores.put("in_sala_gina",VerificaCamposNulos(formulario.getIn_sala_gina()));
		valores.put("in_aque_gas",VerificaCamposNulos(formulario.getIn_aque_gas()));
		valores.put("in_elev_serv",VerificaCamposNulos(formulario.getIn_elev_serv()));
		valores.put("in_elev_soci",VerificaCamposNulos(formulario.getIn_elev_soci()));
		valores.put("qt_garg",VerificaCamposNumericosNulos(formulario.getQt_garg()));
		valores.put("cd_lote",VerificaCamposNulos(cd_lote));
		valores.put("cd_quadra",VerificaCamposNulos(cd_quadra));
		valores.put("cadastrador",VerificaCamposNulos(formulario.getCadastrador()));
		valores.put("obs_geo",VerificaCamposNulos(formulario.getObs_geo()));
		valores.put("data_cadastro",VerificaCamposNulos(formulario.getData_cadastro()));
		valores.put("ativo",VerificaCamposNulos(formulario.getAtivo()));
		valores.put("dt_atualizacao",VerificaCamposNulos(formulario.getDt_atualizacao()));
		valores.put("nm_user_atualizacao",VerificaCamposNulos(formulario.getNm_user_atualizacao()));
		valores.put("comando",VerificaCamposNulos(formulario.getComando()));
		valores.put("dt_cadastro",VerificaCamposNulos(formulario.getDt_cadastro()));
		valores.put("nm_user_cadastro",VerificaCamposNulos(formulario.getNm_user_cadastro()));
		valores.put("controle_tablet",VerificaCamposNulos(formulario.getControle_tablet()));
		valores.put("edicao",VerificaCamposNulos(formulario.getEdicao()));
		valores.put("status_associacao",VerificaCamposNulos(formulario.getStatus_associacao()));
		valores.put("status_cadastro",VerificaCamposNulos(formulario.getStatus_cadastro()));
		valores.put("nu_insc_imbl_ant",VerificaCamposNulos(formulario.getNu_insc_imbl_ant()));
		valores.put("in_canc_cimb",VerificaCamposNulos(formulario.getIn_canc_cimb()));
		valores.put("sincronizado",VerificaCamposNulos(formulario.getSincronizado()));
		
		banco.getWritableDatabase().update("cotr_imobiliario", valores, " nu_insc_imbl = ?", Idselecionado);
		banco.close();
    	
    }
    
   public void atualizarColetiva(FormularioEntidade formulario){
	   
	String cd_lote = formulario.getNu_insc_imbl().substring(0, 11);
    	
    	String[] Idselecionado = {cd_lote}; 
		ContentValues valores = new ContentValues();
		
		
	   
		valores.put("cd_seca",VerificaCamposNulos(formulario.getCd_seca()));
		valores.put("tp_ocpc_lote",VerificaCamposNulos(formulario.getTp_ocpc_lote()));
		valores.put("tp_murd",VerificaCamposNulos(formulario.getTp_murd()));
		valores.put("tp_pass",VerificaCamposNulos(formulario.getTp_pass()));
		valores.put("qt_area_lote",VerificaCamposNulos(formulario.getQt_area_lote()));
		valores.put("tp_sitc",VerificaCamposNulos(formulario.getTp_sitc()));
		valores.put("tp_topg",VerificaCamposNulos(formulario.getTp_topg()));
		valores.put("qt_prfn",VerificaCamposNulos(formulario.getQt_prfn()));
		valores.put("tp_pedl",VerificaCamposNulos(formulario.getTp_pedl()));
		valores.put("cd_logr_tes2",VerificaCamposNulos(formulario.getCd_logr_tes2()));
		valores.put("cd_seca_tes2",VerificaCamposNulos(formulario.getCd_seca_tes2()));
		valores.put("cd_logr_tes3",VerificaCamposNulos(formulario.getCd_logr_tes3()));
		valores.put("cd_seca_tes3",VerificaCamposNulos(formulario.getCd_seca_tes3()));
		valores.put("cd_logr_tes4",VerificaCamposNulos(formulario.getCd_logr_tes4()));
		valores.put("cd_seca_tes4",VerificaCamposNulos(formulario.getCd_seca_tes4()));
		valores.put("sincronizado",VerificaCamposNulos(formulario.getSincronizado()));
		banco.getWritableDatabase().update("cotr_imobiliario", valores, " cd_lote = ?", Idselecionado);
		banco.close();
		
		
   }
   
   public boolean carregaInscricoesQuadras(ArrayList<Quadra> listaQuadras){
		
		   ArrayList<String> listaLotes = new ArrayList<String>();
		   ArrayList<String> listaLotesTotal = new ArrayList<String>();
	   
		try{
	
			
			for (int i = 0; i < listaQuadras.size(); i++) {
				listaLotes = carregaLotesQuadra(listaQuadras.get(i).getCd_quadra());
				     for (int j = 0; j < listaLotes.size(); j++) {
				    	 listaLotesTotal.add(listaLotes.get(j));
					}
				
			}
			
			
	
			for (int i = 0; i < listaLotesTotal.size(); i++) {
				
				carregaListaInscricoesLote(listaLotesTotal.get(i));
				
			}


		
		}catch (Exception e) {
			Log.e("erro", e.getMessage());
		return false;
		
		    }
		
		if(listaLotesTotal.isEmpty()){
			
			return false;
			
		   }else{
		
		return true;
		   }
	}
		
  /* public boolean carregaListaInscricoes(String quadra){
	
		
		try{
		HttpConection objConexao = new HttpConection();
		objConexao.setDados("o", "19");
		objConexao.setDados("quadra",quadra);
		JSONObject retorno = objConexao.envia();
		resultados2 = retorno.getJSONArray("results");
		JSONObject rec = resultados2.getJSONObject(0);
		
			if (rec.getString("ok").equals("1")){
			
			
			
		 
		for (int i = 0; i < resultados2.length(); ++i){
			rec = resultados2.getJSONObject(i);	
			
		
			  FormularioEntidade formulario = new FormularioEntidade();
			  
			  formulario.setNu_insc_imbl(rec.getString("nu_insc_imbl"));
			  formulario.setCd_logr(rec.getString("cd_logr"));
			  formulario.setId_logra(rec.getString("id_logra"));
			  formulario.setCd_seca(rec.getString("cd_seca"));
			  formulario.setNu_imvl(rec.getString("nu_imvl"));
			  formulario.setDe_comp(rec.getString("de_comp"));
			  formulario.setId_bairro(rec.getString("id_bairro"));
			  formulario.setId_bairro(rec.getString("nu_lotm"));
			  formulario.setNu_qudr(rec.getString("nu_qudr"));
			  formulario.setNu_lote(rec.getString("nu_lote"));
			  formulario.setNu_apto_garg(rec.getString("nu_apto_garg"));
			  formulario.setIn_endr_corr(rec.getString("in_endr_corr"));
			  formulario.setNm_edfc(rec.getString("nm_edfc"));
			  formulario.setNu_pess(rec.getString("nu_pess"));
			  formulario.setCd_imbl(rec.getString("cd_imbl"));
			  formulario.setTp_ocpc_lote(rec.getString("tp_ocpc_lote"));
			  formulario.setTp_murd(rec.getString("tp_murd"));
			  formulario.setTp_patr(rec.getString("tp_patr"));
			  formulario.setTp_terr_marn(rec.getString("tp_terr_marn"));
			  formulario.setTp_utlz(rec.getString("tp_utlz"));
			  formulario.setTp_pass(rec.getString("tp_pass"));
			  formulario.setTp_arvr(rec.getString("tp_arvr"));
			  formulario.setTp_ligc_agua(rec.getString("tp_ligc_agua"));
			  formulario.setTp_elvd(rec.getString("tp_elvd"));
			  formulario.setTp_esgt(rec.getString("tp_esgt"));
			  formulario.setTp_eltr(rec.getString("tp_eltr"));
			  formulario.setTp_isnt_iptu(rec.getString("tp_isnt_iptu"));
			  formulario.setTp_isnt_tsu(rec.getString("tp_isnt_tsu"));
			  formulario.setNu_pavm(rec.getString("nu_pavm"));
			  formulario.setNu_celesc(rec.getString("nu_celesc"));
			  formulario.setNu_casan(rec.getString("nu_casan"));
			  formulario.setNu_matr_cart(rec.getString("nu_matr_cart"));
			  formulario.setNu_livr_cart(rec.getString("nu_livr_cart"));
			  formulario.setNu_folh_cart(rec.getString("nu_folh_cart"));
			  formulario.setCd_tabl_cart(rec.getString("cd_tabl_cart"));
			  formulario.setNu_prcs_lanc_revs(rec.getString("nu_prcs_lanc_revs"));
			  formulario.setDt_prcs_revs(rec.getString("dt_prcs_revs"));
			  formulario.setVvenal(rec.getString("vvenal"));
			  formulario.setQt_area_lote(rec.getString("qt_area_lote"));
			  formulario.setQt_area_cons(rec.getString("qt_area_cons"));
			  formulario.setTp_sitc_qudr(rec.getString("tp_sitc_qudr"));
			  formulario.setTp_topg(rec.getString("tp_topg"));
			  formulario.setQt_prfn(rec.getString("qt_prfn"));
			  formulario.setTp_pedl(rec.getString("tp_pedl"));
			  formulario.setNu_test_prnc(rec.getString("nu_test_prnc"));
			  formulario.setCd_logr_tes2(rec.getString("cd_logr_tes2"));
			  formulario.setCd_seca_tes2(rec.getString("cd_seca_tes2"));
			  formulario.setNu_tes2(rec.getString("nu_tes2"));
			  formulario.setCd_logr_tes3(rec.getString("cd_logr_tes3"));
			  formulario.setCd_seca_tes3(rec.getString("cd_seca_tes3"));
			  formulario.setNu_tes3(rec.getString("nu_tes3"));
			  formulario.setCd_logr_tes4(rec.getString("cd_logr_tes4"));
			  formulario.setCd_seca_tes4(rec.getString("cd_seca_tes4"));
			  formulario.setNu_tes4(rec.getString("nu_tes4"));
			  formulario.setAa_cons(rec.getString("aa_cons"));
			  formulario.setNu_pav_edif(rec.getString("nu_pav_edif"));
			  formulario.setQt_area_undd(rec.getString("qt_area_undd"));
			  formulario.setTp_edfc(rec.getString("tp_edfc"));
			  formulario.setTp_alnh(rec.getString("tp_alnh"));
			  formulario.setTp_locc(rec.getString("tp_locc"));
			  formulario.setTp_sitc(rec.getString("tp_sitc"));
			  formulario.setTp_estr(rec.getString("tp_estr"));
			  formulario.setTp_cobr(rec.getString("tp_cobr"));
			  formulario.setTp_pard(rec.getString("tp_pard"));
			  formulario.setTp_vedc(rec.getString("tp_vedc"));
			  formulario.setTp_revs_extr(rec.getString("tp_revs_extr"));
			  formulario.setTp_padr_cons(rec.getString("tp_padr_cons"));
			  formulario.setQt_afst_frnt(rec.getString("qt_afst_frnt"));
			  formulario.setIn_clim_ambi(rec.getString("in_clim_ambi"));
			  formulario.setIn_eqpt_segr(rec.getString("in_eqpt_segr"));
			  formulario.setIn_salo_fest(rec.getString("in_salo_fest"));
			  formulario.setIn_chur(rec.getString("in_chur"));
			  formulario.setIn_saun(rec.getString("in_saun"));
			  formulario.setIn_pisc(rec.getString("in_pisc"));
			  formulario.setIn_lare(rec.getString("in_lare"));
			  formulario.setIn_sala_jogo(rec.getString("in_sala_jogo"));
			  formulario.setIn_sala_gina(rec.getString("in_sala_gina"));
			  formulario.setIn_aque_gas(rec.getString("in_aque_gas"));
			  formulario.setIn_elev_serv(rec.getString("in_elev_serv"));
			  formulario.setIn_elev_soci(rec.getString("in_elev_soci"));
			  formulario.setQt_garg(rec.getString("qt_garg"));
			  formulario.setCd_lote(rec.getString("cd_lote"));
			  formulario.setCd_quadra(rec.getString("cd_quadra"));
			  formulario.setCadastrador(rec.getString("cadastrador"));
			  formulario.setObs_geo(rec.getString("obs_geo"));
			  formulario.setData_cadastro(rec.getString("data_cadastro"));
			  formulario.setAtivo(rec.getString("ativo"));
			  formulario.setDt_atualizacao(rec.getString("dt_atualizacao"));
			  formulario.setNm_user_atualizacao(rec.getString("nm_user_atualizacao"));
			  formulario.setComando(rec.getString("comando"));
			  formulario.setDt_cadastro(rec.getString("dt_cadastro"));
			  formulario.setNm_user_cadastro(rec.getString("nm_user_cadastro"));
			  formulario.setControle_tablet(rec.getString("controle_tablet"));
			  formulario.setEdicao(rec.getString("edicao"));
			  formulario.setStatus_cadastro(rec.getString("status_cadastro"));
			  formulario.setNu_insc_imbl_ant(rec.getString("nu_insc_imbl_ant"));
		
			                inserir(formulario);
			               
			      
			            
  
		      }
		
		
		}
		
		return true;
		
		}catch (Exception e) {
			Log.e("erro", e.getMessage());
		return false;
		                 }
		
		       }*/
  
     
   public boolean carregaListaInscricoesLote(String lote){
	   
	   
	   
	   
		try{
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "30");
			objConexao.setDados("lote",lote);
			JSONObject retorno = objConexao.envia();
			resultados2 = retorno.getJSONArray("results");
			JSONObject rec = resultados2.getJSONObject(0);
			
				if (rec.getString("ok").equals("1")){
				
				
				
			 
			for (int i = 0; i < resultados2.length(); ++i){
				rec = resultados2.getJSONObject(i);	
				
			
				  FormularioEntidade formulario = new FormularioEntidade();
				  
				  formulario.setNu_insc_imbl(rec.getString("nu_insc_imbl"));
				  formulario.setCd_logr(rec.getString("cd_logr"));
				  formulario.setId_logra(rec.getString("id_logra"));
				  formulario.setCd_seca(rec.getString("cd_seca"));
				  formulario.setNu_imvl(rec.getString("nu_imvl"));
				  formulario.setDe_comp(rec.getString("de_comp"));
				  formulario.setId_bairro(rec.getString("id_bairro"));
				  formulario.setId_bairro(rec.getString("nu_lotm"));
				  formulario.setNu_qudr(rec.getString("nu_qudr"));
				  formulario.setNu_lote(rec.getString("nu_lote"));
				  formulario.setNu_apto_garg(rec.getString("nu_apto_garg"));
				  formulario.setIn_endr_corr(rec.getString("in_endr_corr"));
				  formulario.setNm_edfc(rec.getString("nm_edfc"));
				  formulario.setNu_pess(rec.getString("nu_pess"));
				  formulario.setCd_imbl(rec.getString("cd_imbl"));
				  formulario.setTp_ocpc_lote(rec.getString("tp_ocpc_lote"));
				  formulario.setTp_murd(rec.getString("tp_murd"));
				  formulario.setTp_patr(rec.getString("tp_patr"));
				  formulario.setTp_terr_marn(rec.getString("tp_terr_marn"));
				  formulario.setTp_utlz(rec.getString("tp_utlz"));
				  formulario.setTp_pass(rec.getString("tp_pass"));
				  formulario.setTp_arvr(rec.getString("tp_arvr"));
				  formulario.setTp_ligc_agua(rec.getString("tp_ligc_agua"));
				  formulario.setTp_elvd(rec.getString("tp_elvd"));
				  formulario.setTp_esgt(rec.getString("tp_esgt"));
				  formulario.setTp_eltr(rec.getString("tp_eltr"));
				  formulario.setTp_isnt_iptu(rec.getString("tp_isnt_iptu"));
				  formulario.setTp_isnt_tsu(rec.getString("tp_isnt_tsu"));
				  formulario.setNu_pavm(rec.getString("nu_pavm"));
				  formulario.setNu_celesc(rec.getString("nu_celesc"));
				  formulario.setNu_casan(rec.getString("nu_casan"));
				  formulario.setNu_matr_cart(rec.getString("nu_matr_cart"));
				  formulario.setNu_livr_cart(rec.getString("nu_livr_cart"));
				  formulario.setNu_folh_cart(rec.getString("nu_folh_cart"));
				  formulario.setCd_tabl_cart(rec.getString("cd_tabl_cart"));
				  formulario.setNu_prcs_lanc_revs(rec.getString("nu_prcs_lanc_revs"));
				  formulario.setDt_prcs_revs(rec.getString("dt_prcs_revs"));
				  formulario.setVvenal(rec.getString("vvenal"));
				  formulario.setQt_area_lote(rec.getString("qt_area_lote"));
				  formulario.setQt_area_cons(rec.getString("qt_area_cons"));
				  formulario.setTp_sitc_qudr(rec.getString("tp_sitc_qudr"));
				  formulario.setTp_topg(rec.getString("tp_topg"));
				  formulario.setQt_prfn(rec.getString("qt_prfn"));
				  formulario.setTp_pedl(rec.getString("tp_pedl"));
				  formulario.setNu_test_prnc(rec.getString("nu_test_prnc"));
				  formulario.setCd_logr_tes2(rec.getString("cd_logr_tes2"));
				  formulario.setCd_seca_tes2(rec.getString("cd_seca_tes2"));
				  formulario.setNu_tes2(rec.getString("nu_tes2"));
				  formulario.setCd_logr_tes3(rec.getString("cd_logr_tes3"));
				  formulario.setCd_seca_tes3(rec.getString("cd_seca_tes3"));
				  formulario.setNu_tes3(rec.getString("nu_tes3"));
				  formulario.setCd_logr_tes4(rec.getString("cd_logr_tes4"));
				  formulario.setCd_seca_tes4(rec.getString("cd_seca_tes4"));
				  formulario.setNu_tes4(rec.getString("nu_tes4"));
				  formulario.setAa_cons(rec.getString("aa_cons"));
				  formulario.setNu_pav_edif(rec.getString("nu_pav_edif"));
				  formulario.setQt_area_undd(rec.getString("qt_area_undd"));
				  formulario.setTp_edfc(rec.getString("tp_edfc"));
				  formulario.setTp_alnh(rec.getString("tp_alnh"));
				  formulario.setTp_locc(rec.getString("tp_locc"));
				  formulario.setTp_sitc(rec.getString("tp_sitc"));
				  formulario.setTp_ocpc(rec.getString("tp_ocpc"));
				  formulario.setTp_estr(rec.getString("tp_estr"));
				  formulario.setTp_cobr(rec.getString("tp_cobr"));
				  formulario.setTp_pard(rec.getString("tp_pard"));
				  formulario.setTp_vedc(rec.getString("tp_vedc"));
				  formulario.setTp_revs_extr(rec.getString("tp_revs_extr"));
				  formulario.setTp_padr_cons(rec.getString("tp_padr_cons"));
				  formulario.setQt_afst_frnt(rec.getString("qt_afst_frnt"));
				  formulario.setIn_clim_ambi(rec.getString("in_clim_ambi"));
				  formulario.setIn_eqpt_segr(rec.getString("in_eqpt_segr"));
				  formulario.setIn_salo_fest(rec.getString("in_salo_fest"));
				  formulario.setIn_chur(rec.getString("in_chur"));
				  formulario.setIn_saun(rec.getString("in_saun"));
				  formulario.setIn_pisc(rec.getString("in_pisc"));
				  formulario.setIn_lare(rec.getString("in_lare"));
				  formulario.setIn_sala_jogo(rec.getString("in_sala_jogo"));
				  formulario.setIn_sala_gina(rec.getString("in_sala_gina"));
				  formulario.setIn_aque_gas(rec.getString("in_aque_gas"));
				  formulario.setIn_elev_serv(rec.getString("in_elev_serv"));
				  formulario.setIn_elev_soci(rec.getString("in_elev_soci"));
				  formulario.setQt_garg(rec.getString("qt_garg"));
				  formulario.setCd_lote(rec.getString("cd_lote"));
				  formulario.setCd_quadra(rec.getString("cd_quadra"));
				  formulario.setCadastrador(rec.getString("cadastrador"));
				  formulario.setObs_geo(rec.getString("obs_geo"));
				  formulario.setData_cadastro(rec.getString("data_cadastro"));
				  formulario.setAtivo(rec.getString("ativo"));
				  formulario.setDt_atualizacao(rec.getString("dt_atualizacao"));
				  formulario.setNm_user_atualizacao(rec.getString("nm_user_atualizacao"));
				  formulario.setComando(rec.getString("comando"));
				  formulario.setDt_cadastro(rec.getString("dt_cadastro"));
				  formulario.setNm_user_cadastro(rec.getString("nm_user_cadastro"));
				  formulario.setControle_tablet(rec.getString("controle_tablet"));
				  formulario.setEdicao(rec.getString("edicao"));
				  formulario.setStatus_cadastro(rec.getString("status_cadastro"));
				  formulario.setNu_insc_imbl_ant(rec.getString("nu_insc_imbl_ant"));
				  formulario.setSincronizado("SIM");
			
				                inserir(formulario);
				               
				      
				            
	  
			      }
			
			
			}
			
			return true;
			
			}catch (Exception e) {
				Log.e("erro", e.getMessage());
			return false;
			                 }
	   
	   
	   
	   
	   
	   
	   
	   
   }
   
  public ArrayList<String> carregaLotesQuadra(String quadra){
	   
	   ArrayList<String> ListaLotes = new ArrayList<String>();
	   
	   try {
		   
			HttpConection objConexao = new HttpConection();
			objConexao.setDados("o", "19");
			objConexao.setDados("quadra",quadra);
			JSONObject retorno = objConexao.envia();
			resultados2 = retorno.getJSONArray("results");
			JSONObject rec = resultados2.getJSONObject(0);
			
				if (rec.getString("ok").equals("1")){
			 
			for (int i = 0; i < resultados2.length(); ++i){
				rec = resultados2.getJSONObject(i);	
			    
				String lote = rec.getString("cd_lote");
			
				ListaLotes.add(lote);
			          }
			
				}
			
		   
		
			} catch (Exception e) {
				Log.e("erro", e.getMessage());
				
			}
			   return ListaLotes;
	   
   }
   
   public boolean carregaListaInscricoesdaWEB(String quadra){
	   
	   
	   
		try{
		HttpConection objConexao = new HttpConection();
		objConexao.setDados("o", "29");
		objConexao.setDados("quadra",quadra);
		JSONObject retorno = objConexao.envia();
		resultados2 = retorno.getJSONArray("results");
		JSONObject rec = resultados2.getJSONObject(0);
		
		
		if (rec.getString("ok").equals("1")){
		for (int i = 0; i < resultados2.length(); ++i){
			rec = resultados2.getJSONObject(i);	
			
		  FormularioEntidade formulario = new FormularioEntidade();
			  
			  formulario.setNu_insc_imbl(rec.getString("nu_insc_imbl"));
			  formulario.setCd_logr(rec.getString("cd_logr"));
			  formulario.setId_logra(rec.getString("id_logra"));
			  formulario.setCd_seca(rec.getString("cd_seca"));
			  formulario.setNu_imvl(rec.getString("nu_imvl"));
			  formulario.setDe_comp(rec.getString("de_comp"));
			  formulario.setId_bairro(rec.getString("id_bairro"));
			  formulario.setId_bairro(rec.getString("nu_lotm"));
			  formulario.setNu_qudr(rec.getString("nu_qudr"));
			  formulario.setNu_lote(rec.getString("nu_lote"));
			  formulario.setNu_apto_garg(rec.getString("nu_apto_garg"));
			  formulario.setIn_endr_corr(rec.getString("in_endr_corr"));
			  formulario.setNm_edfc(rec.getString("nm_edfc"));
			  formulario.setNu_pess(rec.getString("nu_pess"));
			  formulario.setCd_imbl(rec.getString("cd_imbl"));
			  formulario.setTp_ocpc_lote(rec.getString("tp_ocpc_lote"));
			  formulario.setTp_murd(rec.getString("tp_murd"));
			  formulario.setTp_patr(rec.getString("tp_patr"));
			  formulario.setTp_terr_marn(rec.getString("tp_terr_marn"));
			  formulario.setTp_utlz(rec.getString("tp_utlz"));
			  formulario.setTp_ocpc(rec.getString("tp_ocpc"));
			  formulario.setTp_pass(rec.getString("tp_pass"));
			  formulario.setTp_arvr(rec.getString("tp_arvr"));
			  formulario.setTp_ligc_agua(rec.getString("tp_ligc_agua"));
			  formulario.setTp_elvd(rec.getString("tp_elvd"));
			  formulario.setTp_esgt(rec.getString("tp_esgt"));
			  formulario.setTp_eltr(rec.getString("tp_eltr"));
			  formulario.setTp_isnt_iptu(rec.getString("tp_isnt_iptu"));
			  formulario.setTp_isnt_tsu(rec.getString("tp_isnt_tsu"));
			  formulario.setNu_pavm(rec.getString("nu_pavm"));
			  formulario.setNu_celesc(rec.getString("nu_celesc"));
			  formulario.setNu_casan(rec.getString("nu_casan"));
			  formulario.setNu_matr_cart(rec.getString("nu_matr_cart"));
			  formulario.setNu_livr_cart(rec.getString("nu_livr_cart"));
			  formulario.setNu_folh_cart(rec.getString("nu_folh_cart"));
			  formulario.setCd_tabl_cart(rec.getString("cd_tabl_cart"));
			  formulario.setNu_prcs_lanc_revs(rec.getString("nu_prcs_lanc_revs"));
			  formulario.setDt_prcs_revs(rec.getString("dt_prcs_revs"));
			  formulario.setVvenal(rec.getString("vvenal"));
			  formulario.setQt_area_lote(rec.getString("qt_area_lote"));
			  formulario.setQt_area_cons(rec.getString("qt_area_cons"));
			  formulario.setTp_sitc_qudr(rec.getString("tp_sitc_qudr"));
			  formulario.setTp_topg(rec.getString("tp_topg"));
			  formulario.setQt_prfn(rec.getString("qt_prfn"));
			  formulario.setTp_pedl(rec.getString("tp_pedl"));
			  formulario.setNu_test_prnc(rec.getString("nu_test_prnc"));
			  formulario.setCd_logr_tes2(rec.getString("cd_logr_tes2"));
			  formulario.setCd_seca_tes2(rec.getString("cd_seca_tes2"));
			  formulario.setNu_tes2(rec.getString("nu_tes2"));
			  formulario.setCd_logr_tes3(rec.getString("cd_logr_tes3"));
			  formulario.setCd_seca_tes3(rec.getString("cd_seca_tes3"));
			  formulario.setNu_tes3(rec.getString("nu_tes3"));
			  formulario.setCd_logr_tes4(rec.getString("cd_logr_tes4"));
			  formulario.setCd_seca_tes4(rec.getString("cd_seca_tes4"));
			  formulario.setNu_tes4(rec.getString("nu_tes4"));
			  formulario.setAa_cons(rec.getString("aa_cons"));
			  formulario.setNu_pav_edif(rec.getString("nu_pav_edif"));
			  formulario.setQt_area_undd(rec.getString("qt_area_undd"));
			  formulario.setTp_edfc(rec.getString("tp_edfc"));
			  formulario.setTp_alnh(rec.getString("tp_alnh"));
			  formulario.setTp_locc(rec.getString("tp_locc"));
			  formulario.setTp_sitc(rec.getString("tp_sitc"));
			  formulario.setTp_estr(rec.getString("tp_estr"));
			  formulario.setTp_cobr(rec.getString("tp_cobr"));
			  formulario.setTp_pard(rec.getString("tp_pard"));
			  formulario.setTp_vedc(rec.getString("tp_vedc"));
			  formulario.setTp_revs_extr(rec.getString("tp_revs_extr"));
			  formulario.setTp_padr_cons(rec.getString("tp_padr_cons"));
			  formulario.setQt_afst_frnt(rec.getString("qt_afst_frnt"));
			  formulario.setIn_clim_ambi(rec.getString("in_clim_ambi"));
			  formulario.setIn_eqpt_segr(rec.getString("in_eqpt_segr"));
			  formulario.setIn_salo_fest(rec.getString("in_salo_fest"));
			  formulario.setIn_chur(rec.getString("in_chur"));
			  formulario.setIn_saun(rec.getString("in_saun"));
			  formulario.setIn_pisc(rec.getString("in_pisc"));
			  formulario.setIn_lare(rec.getString("in_lare"));
			  formulario.setIn_sala_jogo(rec.getString("in_sala_jogo"));
			  formulario.setIn_sala_gina(rec.getString("in_sala_gina"));
			  formulario.setIn_aque_gas(rec.getString("in_aque_gas"));
			  formulario.setIn_elev_serv(rec.getString("in_elev_serv"));
			  formulario.setIn_elev_soci(rec.getString("in_elev_soci"));
			  formulario.setQt_garg(rec.getString("qt_garg"));
			  formulario.setCd_lote(rec.getString("cd_lote"));
			  formulario.setCd_quadra(rec.getString("cd_quadra"));
			  formulario.setCadastrador(rec.getString("cadastrador"));
			  formulario.setObs_geo(rec.getString("obs_geo"));
			  formulario.setData_cadastro(rec.getString("data_cadastro"));
			  formulario.setAtivo(rec.getString("ativo"));
			  formulario.setDt_atualizacao(rec.getString("dt_atualizacao"));
			  formulario.setNm_user_atualizacao(rec.getString("nm_user_atualizacao"));
			  formulario.setComando(rec.getString("comando"));
			  formulario.setDt_cadastro(rec.getString("dt_cadastro"));
			  formulario.setNm_user_cadastro(rec.getString("nm_user_cadastro"));
			  formulario.setControle_tablet(rec.getString("controle_tablet"));
			  formulario.setEdicao(rec.getString("edicao"));
			  formulario.setStatus_cadastro(rec.getString("status_cadastro"));
			  formulario.setNu_insc_imbl_ant(rec.getString("nu_insc_imbl_ant"));
		
	
				
			     if(consultaInscricao(formulario.getNu_insc_imbl())){
			        
                      			    	 
			    	      inserir(formulario);
			    	     
			    	 
			     		}else{
			     			
			     		 atualizar(formulario);
			               
			     	}
			            
 
		      }
		
		
		}
		
		return true;
		
		}catch (Exception e) {
			Log.e("erro", e.getMessage());
	     
			return false;
		
		}
	   
	   
	   
   }
  
   public boolean descarregarBaseFormulario(String cd_quadra){		
		   
		   ArrayList<FormularioEntidade> lista = new ArrayList<FormularioEntidade>();
		   Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * from cotr_imobiliario WHERE cd_quadra = '"+cd_quadra+"';",null);
		   
		   
	      while (cursor.moveToNext()){
	    	  String inscricao = cursor.getString(cursor.getColumnIndex("nu_insc_imbl"));
	    	  FormularioEntidade novo = new FormularioEntidade();
	    	  novo.setNu_insc_imbl(inscricao);
	 		  lista.add(novo);
	    	
	       	  
	      }
	      cursor.close();
		  banco.close(); 
		  
		  
		  
		  for (int i = 0; i < lista.size(); ++i){
			  String inscricao = lista.get(i).getNu_insc_imbl();
			  if(consultaInscricaoControleTablet(inscricao)){
			     deletar(inscricao);
			  }
		  }
		  
		  
		  
			return true;
		}
	   
   public boolean atualizarInscricoesWEB(ArrayList<FormularioEntidade> listaInscricoes,String usuario){
		   
			HttpConection objConexao = new HttpConection();
		  
					   try {
							
						   for (int i = 0; i < listaInscricoes.size(); ++i){   
								objConexao.setDados("o", "18");
								objConexao.setDados("nu_insc_imbl",listaInscricoes.get(i).getNu_insc_imbl());
								JSONObject retorno = objConexao.envia();
				
								
								String inscricao = listaInscricoes.get(i).getNu_insc_imbl();
								FormularioEntidade novo = new FormularioEntidade();
								    novo= pesquisaInscricao(inscricao);
								    String opcao = "";
								 if(novo.getAtivo().equalsIgnoreCase("f") || novo.getIn_canc_cimb().equalsIgnoreCase("*")){
									 opcao = "4";
									 
									 
								 }   else {
											    
										if (retorno.getString("ok").equals("1")){
													opcao = "5";
												
													 
													 
												}else{
													opcao = "4";
												}
												
												
								 }		
											    
									objConexao.setDados("nu_insc_imbl",novo.getNu_insc_imbl());
									objConexao.setDados("cd_logr", novo.getCd_logr());
									objConexao.setDados("cd_logr", novo.getCd_logr());
									objConexao.setDados("id_logra", novo.getId_logra());
									objConexao.setDados("cd_seca",novo.getCd_seca());
									objConexao.setDados("numero_logr", novo.getNu_imvl());
									objConexao.setDados("complemento",novo.getDe_comp());
									objConexao.setDados("cd_bairro", novo.getId_bairro());
									objConexao.setDados("nu_lotm", novo.getNu_lotm());
									objConexao.setDados("nu_qudr", novo.getNu_qudr());
									objConexao.setDados("nu_lote",novo.getNu_lote());
									objConexao.setDados("cpf",novo.getNu_pess());
									objConexao.setDados("nu_apto_garg",novo.getNu_apto_garg());
									objConexao.setDados("nm_bloc",novo.getNm_bloc());
									objConexao.setDados("nm_edfc",novo.getNm_edfc());
									objConexao.setDados("in_endr_corr",novo.getIn_endr_corr());
									objConexao.setDados("cpf", novo.getNu_pess());
									objConexao.setDados("cd_imbl", novo.getCd_imbl());
									objConexao.setDados("ocupacao",novo.getTp_ocpc_lote());
									objConexao.setDados("murado", novo.getTp_murd());
									objConexao.setDados("patrimonio",novo.getTp_patr());
									objConexao.setDados("marinha", novo.getTp_terr_marn());
									objConexao.setDados("utilizacao", novo.getTp_utlz());
									objConexao.setDados("passeio",novo.getTp_pass());
									objConexao.setDados("arvore", novo.getTp_arvr());
									objConexao.setDados("elevador", novo.getTp_elvd());
									objConexao.setDados("rede_Agua", novo.getTp_ligc_agua());
									objConexao.setDados("esgoto", novo.getTp_esgt());
									objConexao.setDados("eletricidade",novo.getTp_eltr());
									objConexao.setDados("isento_iptu",novo.getTp_isnt_iptu());
									objConexao.setDados("isento_tsu", novo.getTp_isnt_tsu());
									objConexao.setDados("numero_pavimentos", novo.getNu_pavm());
									objConexao.setDados("nu_celesc", novo.getNu_celesc());
									objConexao.setDados("nu_casan", novo.getNu_casan());
									objConexao.setDados("nu_matr_cart",novo.getNu_matr_cart());
									objConexao.setDados("nu_livr_cart",novo.getNu_livr_cart());
									objConexao.setDados("nu_folh_cart",novo.getNu_folh_cart());
									objConexao.setDados("cd_tabl_cart",novo.getCd_tabl_cart());
									objConexao.setDados("vvenal",novo.getVvenal());
									objConexao.setDados("area_total_const", novo.getQt_area_lote());
									objConexao.setDados("area_terreno",novo.getQt_area_cons());
									objConexao.setDados("situacao_quadra",novo.getTp_sitc_qudr());
									objConexao.setDados("topografia", novo.getTp_topg());
									objConexao.setDados("profundidade",novo.getQt_prfn());
									objConexao.setDados("pedologia",novo.getTp_pedl());
									objConexao.setDados("testada1", novo.getNu_test_prnc());
									objConexao.setDados("testada2", novo.getNu_tes2());
									objConexao.setDados("cd_logr2", novo.getCd_logr_tes2());
									objConexao.setDados("cd_seca2", novo.getCd_seca_tes2());
									objConexao.setDados("testada3", novo.getNu_tes3());
									objConexao.setDados("cd_logr3", novo.getCd_logr_tes3());
									objConexao.setDados("cd_seca3", novo.getCd_seca_tes3());
									objConexao.setDados("testada4", novo.getNu_tes4());
									objConexao.setDados("cd_logr4", novo.getCd_logr_tes4());
									objConexao.setDados("cd_seca4", novo.getCd_seca_tes4());
									objConexao.setDados("ano_construcao", novo.getAa_cons());
									objConexao.setDados("nu_pav_edif", novo.getNu_pav_edif());
									objConexao.setDados("area_contruida_unidade",novo.getQt_area_undd());
									objConexao.setDados("tipo",novo.getTp_edfc());
									objConexao.setDados("alinhamento", novo.getTp_alnh());
									objConexao.setDados("locacao", novo.getTp_locc());
									objConexao.setDados("situacao", novo.getTp_sitc());
									objConexao.setDados("tp_ocpc", novo.getTp_ocpc());
									objConexao.setDados("estrutura", novo.getTp_estr());
									objConexao.setDados("cobertura", novo.getTp_cobr());
									objConexao.setDados("paredes", novo.getTp_pard());
									objConexao.setDados("vedacoes_esquadrias", novo.getTp_vedc());
									objConexao.setDados("revestimento_externo", novo.getTp_revs_extr());
									objConexao.setDados("padrao_construcao",novo.getTp_padr_cons());
									objConexao.setDados("afastamento_frontal", novo.getQt_afst_frnt());
									objConexao.setDados("in_clim_ambi", novo.getIn_clim_ambi());
									objConexao.setDados("in_eqpt_segr", novo.getIn_eqpt_segr());
									objConexao.setDados("in_salo_fest", novo.getIn_salo_fest());
									objConexao.setDados("in_chur", novo.getIn_chur());
									objConexao.setDados("in_saun", novo.getIn_saun());
									objConexao.setDados("in_pisc", novo.getIn_pisc());
									objConexao.setDados("in_lare", novo.getIn_lare());
									objConexao.setDados("in_sala_jogo", novo.getIn_sala_jogo());
									objConexao.setDados("in_sala_gina", novo.getIn_sala_gina());
									objConexao.setDados("in_aque_gas", novo.getIn_aque_gas());
									objConexao.setDados("in_elev_serv", novo.getIn_elev_serv());
									objConexao.setDados("in_elev_soci", novo.getIn_elev_soci());
									objConexao.setDados("qt_garg", novo.getQt_garg());
									objConexao.setDados("cadastrador", novo.getCadastrador());
									objConexao.setDados("data_cadastro",novo.getData_cadastro());
									objConexao.setDados("nu_insc_imbl_ant",novo.getNu_insc_imbl_ant());
									objConexao.setDados("ativo",novo.getAtivo());
									objConexao.setDados("obs", novo.getObs_geo());
									objConexao.setDados("controle_tablet", "1");
									objConexao.setDados("nm_usuario",usuario);
									objConexao.setDados("o",opcao);
									objConexao.setDados("in_canc_cimb",novo.getIn_canc_cimb());
									objConexao.setDados("valida","false");
									objConexao.setDados("bci","tablet");
									objConexao.setDados("controle_warning","true");
									
									objConexao.envia();
									
									novo.setSincronizado("SIM");
									atualizar(novo);
									
									
									
									 
							
									}
						   
							
					
					   		  } catch (Exception e) {
						
					   			return false;
					   			
					   			
					   		}
					   
		   
		   
		  return true; 
	   }
	   
   public FormularioEntidade pesquisaInscricao(String  nu_insc_imbl){
    	
    	FormularioEntidade novo = new FormularioEntidade();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM cotr_imobiliario WHERE nu_insc_imbl = '"+nu_insc_imbl+"'  ",null);
		
		if (cursor != null) {		
			
	           if (cursor.moveToFirst()) {
	          
	        		  novo.setNu_insc_imbl(cursor.getString(cursor.getColumnIndex("nu_insc_imbl")));
	            	  novo.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
	            	  novo.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
	            	  novo.setCd_seca(cursor.getString(cursor.getColumnIndex("cd_seca")));
	            	  novo.setNu_imvl(cursor.getString(cursor.getColumnIndex("nu_imvl")));
	            	  novo.setDe_comp(cursor.getString(cursor.getColumnIndex("de_comp")));
	            	  novo.setId_bairro(cursor.getString(cursor.getColumnIndex("id_bairro")));
	            	  novo.setNu_lotm(cursor.getString(cursor.getColumnIndex("nu_lotm")));
	            	  novo.setNu_qudr(cursor.getString(cursor.getColumnIndex("nu_qudr")));
	            	  novo.setNu_lote(cursor.getString(cursor.getColumnIndex("nu_lote")));
	            	  novo.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
	            	  novo.setCd_imbl(cursor.getString(cursor.getColumnIndex("cd_imbl")));
	            	  novo.setIn_endr_corr(cursor.getString(cursor.getColumnIndex("in_endr_corr")));
	            	  novo.setNu_apto_garg(cursor.getString(cursor.getColumnIndex("nu_apto_garg")));
	            	  novo.setNm_bloc(cursor.getString(cursor.getColumnIndex("nm_bloc")));
	            	  novo.setNm_edfc(cursor.getString(cursor.getColumnIndex("nm_edfc")));
	            	  novo.setTp_ocpc_lote(cursor.getString(cursor.getColumnIndex("tp_ocpc_lote")));
	            	  novo.setTp_murd(cursor.getString(cursor.getColumnIndex("tp_murd")));
	            	  novo.setTp_patr(cursor.getString(cursor.getColumnIndex("tp_patr")));
	            	  novo.setTp_terr_marn(cursor.getString(cursor.getColumnIndex("tp_terr_marn")));
	            	  novo.setTp_utlz(cursor.getString(cursor.getColumnIndex("tp_utlz")));
	            	  novo.setTp_ocpc(cursor.getString(cursor.getColumnIndex("tp_ocpc")));
	            	  novo.setTp_pass(cursor.getString(cursor.getColumnIndex("tp_pass")));
	            	  novo.setTp_arvr(cursor.getString(cursor.getColumnIndex("tp_arvr")));
	            	  novo.setTp_ligc_agua(cursor.getString(cursor.getColumnIndex("tp_ligc_agua")));
	            	  novo.setTp_elvd(cursor.getString(cursor.getColumnIndex("tp_elvd")));
	            	  novo.setTp_esgt(cursor.getString(cursor.getColumnIndex("tp_esgt")));
	            	  novo.setTp_eltr(cursor.getString(cursor.getColumnIndex("tp_eltr")));
	            	  novo.setTp_isnt_iptu(cursor.getString(cursor.getColumnIndex("tp_isnt_iptu")));
	            	  novo.setTp_isnt_tsu(cursor.getString(cursor.getColumnIndex("tp_isnt_tsu")));
	            	  novo.setNu_pavm(cursor.getString(cursor.getColumnIndex("nu_pavm")));
	            	  novo.setNu_celesc(cursor.getString(cursor.getColumnIndex("nu_celesc")));
	            	  novo.setNu_casan(cursor.getString(cursor.getColumnIndex("nu_casan")));
	            	  novo.setNu_matr_cart(cursor.getString(cursor.getColumnIndex("nu_matr_cart")));
	            	  novo.setNu_livr_cart(cursor.getString(cursor.getColumnIndex("nu_livr_cart")));
	            	  novo.setNu_folh_cart(cursor.getString(cursor.getColumnIndex("nu_folh_cart")));
	            	  novo.setCd_tabl_cart(cursor.getString(cursor.getColumnIndex("cd_tabl_cart")));
	            	  novo.setVvenal(cursor.getString(cursor.getColumnIndex("vvenal")));
	            	  novo.setQt_area_lote(cursor.getString(cursor.getColumnIndex("qt_area_lote")));
	            	  novo.setQt_area_cons(cursor.getString(cursor.getColumnIndex("qt_area_cons")));
	            	  novo.setTp_sitc_qudr(cursor.getString(cursor.getColumnIndex("tp_sitc_qudr")));
	            	  novo.setTp_topg(cursor.getString(cursor.getColumnIndex("tp_topg")));
	            	  novo.setQt_prfn(cursor.getString(cursor.getColumnIndex("qt_prfn")));
	            	  novo.setTp_pedl(cursor.getString(cursor.getColumnIndex("tp_pedl")));
	            	  novo.setNu_test_prnc(cursor.getString(cursor.getColumnIndex("nu_test_prnc")));
	            	  novo.setCd_logr_tes2(cursor.getString(cursor.getColumnIndex("cd_logr_tes2")));
	            	  novo.setCd_seca_tes2(cursor.getString(cursor.getColumnIndex("cd_seca_tes2")));
	            	  novo.setNu_tes2(cursor.getString(cursor.getColumnIndex("nu_tes2")));
	            	  novo.setCd_logr_tes3(cursor.getString(cursor.getColumnIndex("cd_logr_tes3")));
	            	  novo.setCd_logr_tes3(cursor.getString(cursor.getColumnIndex("cd_seca_tes3")));
	            	  novo.setNu_tes3(cursor.getString(cursor.getColumnIndex("nu_tes3")));
	            	  novo.setCd_logr_tes4(cursor.getString(cursor.getColumnIndex("cd_logr_tes4")));
	            	  novo.setCd_seca_tes4(cursor.getString(cursor.getColumnIndex("cd_seca_tes4")));
	            	  novo.setNu_tes4(cursor.getString(cursor.getColumnIndex("nu_tes4")));
	            	  novo.setAa_cons(cursor.getString(cursor.getColumnIndex("aa_cons")));
	            	  novo.setNu_pav_edif(cursor.getString(cursor.getColumnIndex("nu_pav_edif")));
	            	  novo.setQt_area_undd(cursor.getString(cursor.getColumnIndex("qt_area_undd")));
	            	  novo.setTp_edfc(cursor.getString(cursor.getColumnIndex("tp_edfc")));
	            	  novo.setTp_alnh(cursor.getString(cursor.getColumnIndex("tp_alnh")));
	            	  novo.setTp_locc(cursor.getString(cursor.getColumnIndex("tp_locc")));
	            	  novo.setTp_sitc(cursor.getString(cursor.getColumnIndex("tp_sitc")));
	            	  novo.setTp_estr(cursor.getString(cursor.getColumnIndex("tp_estr")));
	            	  novo.setTp_cobr(cursor.getString(cursor.getColumnIndex("tp_cobr")));
	            	  novo.setTp_pard(cursor.getString(cursor.getColumnIndex("tp_pard")));
	            	  novo.setTp_vedc(cursor.getString(cursor.getColumnIndex("tp_vedc")));
	            	  novo.setTp_revs_extr(cursor.getString(cursor.getColumnIndex("tp_revs_extr")));
	            	  novo.setTp_padr_cons(cursor.getString(cursor.getColumnIndex("tp_padr_cons")));
	            	  novo.setQt_afst_frnt(cursor.getString(cursor.getColumnIndex("qt_afst_frnt")));
	            	  novo.setIn_clim_ambi(cursor.getString(cursor.getColumnIndex("in_clim_ambi")));
	            	  novo.setIn_eqpt_segr(cursor.getString(cursor.getColumnIndex("in_eqpt_segr")));
	            	  novo.setIn_salo_fest(cursor.getString(cursor.getColumnIndex("in_salo_fest")));
	            	  novo.setIn_chur(cursor.getString(cursor.getColumnIndex("in_chur")));
	            	  novo.setIn_saun(cursor.getString(cursor.getColumnIndex("in_saun")));
	            	  novo.setIn_pisc(cursor.getString(cursor.getColumnIndex("in_pisc")));
	            	  novo.setIn_lare(cursor.getString(cursor.getColumnIndex("in_lare")));
	            	  novo.setIn_sala_jogo(cursor.getString(cursor.getColumnIndex("in_sala_jogo")));
	            	  novo.setIn_sala_gina(cursor.getString(cursor.getColumnIndex("in_sala_gina")));
	            	  novo.setIn_aque_gas(cursor.getString(cursor.getColumnIndex("in_aque_gas")));
	            	  novo.setIn_elev_serv(cursor.getString(cursor.getColumnIndex("in_elev_serv")));
	            	  novo.setIn_elev_soci(cursor.getString(cursor.getColumnIndex("in_elev_soci")));
	            	  novo.setQt_garg(cursor.getString(cursor.getColumnIndex("qt_garg")));
	            	  novo.setCd_lote(cursor.getString(cursor.getColumnIndex("cd_lote")));
	            	  novo.setCd_quadra(cursor.getString(cursor.getColumnIndex("cd_quadra")));
	            	  novo.setCadastrador(cursor.getString(cursor.getColumnIndex("cadastrador")));
	            	  novo.setObs_geo(cursor.getString(cursor.getColumnIndex("obs_geo")));
	            	  novo.setObs_geo(cursor.getString(cursor.getColumnIndex("data_cadastro")));
	            	  novo.setAtivo(cursor.getString(cursor.getColumnIndex("ativo")));
	            	  novo.setDt_atualizacao(cursor.getString(cursor.getColumnIndex("dt_atualizacao")));
	            	  novo.setNm_user_atualizacao(cursor.getString(cursor.getColumnIndex("nm_user_atualizacao")));
	            	  novo.setComando(cursor.getString(cursor.getColumnIndex("comando")));
	            	  novo.setDt_cadastro(cursor.getString(cursor.getColumnIndex("dt_cadastro")));
	            	  novo.setNm_user_cadastro(cursor.getString(cursor.getColumnIndex("nm_user_cadastro")));
	            	  novo.setControle_tablet(cursor.getString(cursor.getColumnIndex("controle_tablet")));
	            	  novo.setEdicao(cursor.getString(cursor.getColumnIndex("edicao")));
	            	  novo.setStatus_associacao(cursor.getString(cursor.getColumnIndex("status_associacao")));
	            	  novo.setStatus_cadastro(cursor.getString(cursor.getColumnIndex("status_cadastro")));
			          novo.setIn_canc_cimb(cursor.getString(cursor.getColumnIndex("in_canc_cimb")));
			          novo.setNu_insc_imbl_ant(cursor.getString(cursor.getColumnIndex("nu_insc_imbl_ant")));
			          novo.setTipo_cadastro(cursor.getString(cursor.getColumnIndex("tipo_cadastro")));
	                  novo.setSincronizado(cursor.getString(cursor.getColumnIndex("sincronizado")));

	           }
	           cursor.close();
	           banco.close();
		
		}
    	
    	
    	return novo;
    } 
   
   public boolean consultaInscricao(String nu_insc_imbl){
		
		FormularioEntidade novo = new FormularioEntidade();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM cotr_imobiliario WHERE nu_insc_imbl = '"+nu_insc_imbl+"'; ",null);
		
		if (cursor != null) {		
			
	           if (cursor.moveToFirst()) {
	           	String inscricao = cursor.getString(cursor.getColumnIndex("nu_insc_imbl"));
	           	novo.setNu_insc_imbl(inscricao);
	           	
	           }
	           
	           cursor.close();
	           banco.close();
		
		}
		
		
		if (novo.getNu_insc_imbl()==null||novo.getNu_insc_imbl().equalsIgnoreCase("")){
			
			return true;
			
		     }else{
			  
			return false;  
			  
		      }
  
  }
  
   public ArrayList<FormularioEntidade> PesquisaInscricoesTablet(){
		
		ArrayList<FormularioEntidade> lista = new ArrayList<FormularioEntidade>();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT nu_insc_imbl FROM cotr_imobiliario WHERE sincronizado = 'NAO'",null);
		
		  while (cursor.moveToNext()){
			  FormularioEntidade novo = new FormularioEntidade();
			  novo.setNu_insc_imbl(cursor.getString(cursor.getColumnIndex("nu_insc_imbl")));
			  lista.add(novo);
	     	  }
		  
			cursor.close();
	    	banco.close();     
	    	return lista;
		
	} 
   
   
   
  public boolean consultaInscricaoControleTablet(String nu_insc_imbl){
		
		FormularioEntidade novo = new FormularioEntidade();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM cotr_imobiliario WHERE nu_insc_imbl = '"+nu_insc_imbl+"' and sincronizado = 'NAO' ",null);
		
		if (cursor != null) {		
			
	           if (cursor.moveToFirst()) {
	           	String inscricao = cursor.getString(cursor.getColumnIndex("nu_insc_imbl"));
	           	novo.setNu_insc_imbl(inscricao);
	           	
	           }
	           
	           cursor.close();
	           banco.close();
		
		}
		
		
		if (novo.getNu_insc_imbl()==null||novo.getNu_insc_imbl().equalsIgnoreCase("")){
			return true;
			
		     }else{
			  
			return false;  
			  
		      }
   
   }
   
   public boolean consultaSYNC(String nu_insc_imbl){
		
		FormularioEntidade novo = new FormularioEntidade();
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM cotr_imobiliario WHERE nu_insc_imbl = '"+nu_insc_imbl+"' ",null);
		
		if (cursor != null) {		
			
	           if (cursor.moveToFirst()) {
	           	String inscricao = cursor.getString(cursor.getColumnIndex("nu_insc_imbl"));
	           	novo.setNu_insc_imbl(inscricao);
	           	
	           }
	           
	           cursor.close();
	           banco.close();
		
		}
		
		
		if (novo.getNu_insc_imbl()==null||novo.getNu_insc_imbl().equalsIgnoreCase("")){
			return true;
			
		     }else{
			  
			return false;  
			  
		      }
  
  }
	
   public ArrayList<MyData> ListaInscricoes(String valor){
		
		ArrayList<MyData> lista = new ArrayList<MyData>();
		
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT DISTINCT imob.nu_insc_imbl, "
							+ "pess.nm_pess AS contribuinte, logr.label AS logradouro "  
							+ "FROM cotr_imobiliario imob "
							+ "LEFT JOIN pessoa pess "
							+ "ON imob.nu_pess  = pess.nu_pess " 
							+ "LEFT JOIN logradouro logr "
							+ "ON imob.cd_logr = logr.cd_logr " 
							+ "WHERE (imob.nu_insc_imbl LIKE '%"+valor+"%'AND imob.ativo = 't') OR (pess.nm_pess LIKE '%"+valor+"%' AND imob.ativo = 't') LIMIT 50;",null);
		
		 while (cursor.moveToNext()){
			String inscricao = cursor.getString(cursor.getColumnIndex("nu_insc_imbl"));
			String contribuinte = cursor.getString(cursor.getColumnIndex("contribuinte"));		
			String logradouro = cursor.getString(cursor.getColumnIndex("logradouro"));
			
			   StringBuilder stringBuilder = new StringBuilder(inscricao); 
			    stringBuilder.insert(inscricao.length() - 3,'-');
			    stringBuilder.insert(inscricao.length() - 6,'.');
			    stringBuilder.insert(inscricao.length() - 10,'.');
			    String insc = stringBuilder.toString();
			    lista.add(new MyData(insc, contribuinte, logradouro));
			
			
			//lista.add(new MyData(inscricao, contribuinte, logradouro));
			
		 }
					
		   cursor.close();
	     	banco.close();     
	     	return lista;
	}
   
   public FormularioEntidade retornaDadosBciBD(String nu_insc_imbl){
	   
	  FormularioEntidade novo = new FormularioEntidade();
	  
	  Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT * FROM cotr_imobiliario WHERE nu_insc_imbl = '"+nu_insc_imbl+"'", null);
	  if (cursor != null) {
          if (cursor.moveToFirst()) {
        	  novo.setNu_insc_imbl(cursor.getString(cursor.getColumnIndex("nu_insc_imbl")));
        	  novo.setCd_logr(cursor.getString(cursor.getColumnIndex("cd_logr")));
        	  novo.setId_logra(cursor.getString(cursor.getColumnIndex("id_logra")));
        	  novo.setCd_seca(cursor.getString(cursor.getColumnIndex("cd_seca")));
        	  novo.setNu_imvl(cursor.getString(cursor.getColumnIndex("nu_imvl")));
        	  novo.setDe_comp(cursor.getString(cursor.getColumnIndex("de_comp")));
        	  novo.setId_bairro(cursor.getString(cursor.getColumnIndex("id_bairro")));
        	  novo.setNu_lotm(cursor.getString(cursor.getColumnIndex("nu_lotm")));
        	  novo.setNu_qudr(cursor.getString(cursor.getColumnIndex("nu_qudr")));
        	  novo.setNu_lote(cursor.getString(cursor.getColumnIndex("nu_lote")));
        	  novo.setNu_pess(cursor.getString(cursor.getColumnIndex("nu_pess")));
        	  novo.setCd_imbl(cursor.getString(cursor.getColumnIndex("cd_imbl")));
        	  novo.setIn_endr_corr(cursor.getString(cursor.getColumnIndex("in_endr_corr")));
        	  novo.setNu_apto_garg(cursor.getString(cursor.getColumnIndex("nu_apto_garg")));
        	  novo.setNm_bloc(cursor.getString(cursor.getColumnIndex("nm_bloc")));
        	  novo.setNm_edfc(cursor.getString(cursor.getColumnIndex("nm_edfc")));
        	  novo.setTp_ocpc_lote(cursor.getString(cursor.getColumnIndex("tp_ocpc_lote")));
        	  novo.setTp_murd(cursor.getString(cursor.getColumnIndex("tp_murd")));
        	  novo.setTp_patr(cursor.getString(cursor.getColumnIndex("tp_patr")));
        	  novo.setTp_terr_marn(cursor.getString(cursor.getColumnIndex("tp_terr_marn")));
        	  novo.setTp_utlz(cursor.getString(cursor.getColumnIndex("tp_utlz")));
        	  novo.setTp_pass(cursor.getString(cursor.getColumnIndex("tp_pass")));
        	  novo.setTp_arvr(cursor.getString(cursor.getColumnIndex("tp_arvr")));
        	  novo.setTp_ligc_agua(cursor.getString(cursor.getColumnIndex("tp_ligc_agua")));
        	  novo.setTp_elvd(cursor.getString(cursor.getColumnIndex("tp_elvd")));
        	  novo.setTp_esgt(cursor.getString(cursor.getColumnIndex("tp_esgt")));
        	  novo.setTp_eltr(cursor.getString(cursor.getColumnIndex("tp_eltr")));
        	  novo.setTp_isnt_iptu(cursor.getString(cursor.getColumnIndex("tp_isnt_iptu")));
        	  novo.setTp_isnt_tsu(cursor.getString(cursor.getColumnIndex("tp_isnt_tsu")));
        	  novo.setNu_pavm(cursor.getString(cursor.getColumnIndex("nu_pavm")));
        	  novo.setNu_celesc(cursor.getString(cursor.getColumnIndex("nu_celesc")));
        	  novo.setNu_casan(cursor.getString(cursor.getColumnIndex("nu_casan")));
        	  novo.setNu_matr_cart(cursor.getString(cursor.getColumnIndex("nu_matr_cart")));
        	  novo.setNu_livr_cart(cursor.getString(cursor.getColumnIndex("nu_livr_cart")));
        	  novo.setNu_folh_cart(cursor.getString(cursor.getColumnIndex("nu_folh_cart")));
        	  novo.setCd_tabl_cart(cursor.getString(cursor.getColumnIndex("cd_tabl_cart")));
        	  novo.setVvenal(cursor.getString(cursor.getColumnIndex("vvenal")));
        	  novo.setQt_area_lote(cursor.getString(cursor.getColumnIndex("qt_area_lote")));
        	  novo.setQt_area_cons(cursor.getString(cursor.getColumnIndex("qt_area_cons")));
        	  novo.setTp_sitc_qudr(cursor.getString(cursor.getColumnIndex("tp_sitc_qudr")));
        	  novo.setTp_topg(cursor.getString(cursor.getColumnIndex("tp_topg")));
        	  novo.setQt_prfn(cursor.getString(cursor.getColumnIndex("qt_prfn")));
        	  novo.setTp_pedl(cursor.getString(cursor.getColumnIndex("tp_pedl")));
        	  novo.setNu_test_prnc(cursor.getString(cursor.getColumnIndex("nu_test_prnc")));
        	  novo.setCd_logr_tes2(cursor.getString(cursor.getColumnIndex("cd_logr_tes2")));
        	  novo.setCd_seca_tes2(cursor.getString(cursor.getColumnIndex("cd_seca_tes2")));
        	  novo.setNu_tes2(cursor.getString(cursor.getColumnIndex("nu_tes2")));
        	  novo.setCd_logr_tes3(cursor.getString(cursor.getColumnIndex("cd_logr_tes3")));
        	  novo.setCd_logr_tes3(cursor.getString(cursor.getColumnIndex("cd_seca_tes3")));
        	  novo.setNu_tes3(cursor.getString(cursor.getColumnIndex("nu_tes3")));
        	  novo.setCd_logr_tes4(cursor.getString(cursor.getColumnIndex("cd_logr_tes4")));
        	  novo.setCd_seca_tes4(cursor.getString(cursor.getColumnIndex("cd_seca_tes4")));
        	  novo.setNu_tes4(cursor.getString(cursor.getColumnIndex("nu_tes4")));
        	  novo.setAa_cons(cursor.getString(cursor.getColumnIndex("aa_cons")));
        	  novo.setNu_pav_edif(cursor.getString(cursor.getColumnIndex("nu_pav_edif")));
        	  novo.setQt_area_undd(cursor.getString(cursor.getColumnIndex("qt_area_undd")));
        	  novo.setTp_edfc(cursor.getString(cursor.getColumnIndex("tp_edfc")));
        	  novo.setTp_alnh(cursor.getString(cursor.getColumnIndex("tp_alnh")));
        	  novo.setTp_locc(cursor.getString(cursor.getColumnIndex("tp_locc")));
        	  novo.setTp_sitc(cursor.getString(cursor.getColumnIndex("tp_sitc")));
        	  novo.setTp_ocpc(cursor.getString(cursor.getColumnIndex("tp_ocpc")));
        	  novo.setTp_estr(cursor.getString(cursor.getColumnIndex("tp_estr")));
        	  novo.setTp_cobr(cursor.getString(cursor.getColumnIndex("tp_cobr")));
        	  novo.setTp_pard(cursor.getString(cursor.getColumnIndex("tp_pard")));
        	  novo.setTp_vedc(cursor.getString(cursor.getColumnIndex("tp_vedc")));
        	  novo.setTp_revs_extr(cursor.getString(cursor.getColumnIndex("tp_revs_extr")));
        	  novo.setTp_padr_cons(cursor.getString(cursor.getColumnIndex("tp_padr_cons")));
        	  novo.setQt_afst_frnt(cursor.getString(cursor.getColumnIndex("qt_afst_frnt")));
        	  novo.setIn_clim_ambi(cursor.getString(cursor.getColumnIndex("in_clim_ambi")));
        	  novo.setIn_eqpt_segr(cursor.getString(cursor.getColumnIndex("in_eqpt_segr")));
        	  novo.setIn_salo_fest(cursor.getString(cursor.getColumnIndex("in_salo_fest")));
        	  novo.setIn_chur(cursor.getString(cursor.getColumnIndex("in_chur")));
        	  novo.setIn_saun(cursor.getString(cursor.getColumnIndex("in_saun")));
        	  novo.setIn_pisc(cursor.getString(cursor.getColumnIndex("in_pisc")));
        	  novo.setIn_lare(cursor.getString(cursor.getColumnIndex("in_lare")));
        	  novo.setIn_sala_jogo(cursor.getString(cursor.getColumnIndex("in_sala_jogo")));
        	  novo.setIn_sala_gina(cursor.getString(cursor.getColumnIndex("in_sala_gina")));
        	  novo.setIn_aque_gas(cursor.getString(cursor.getColumnIndex("in_aque_gas")));
        	  novo.setIn_elev_serv(cursor.getString(cursor.getColumnIndex("in_elev_serv")));
        	  novo.setIn_elev_soci(cursor.getString(cursor.getColumnIndex("in_elev_soci")));
        	  novo.setQt_garg(cursor.getString(cursor.getColumnIndex("qt_garg")));
        	  novo.setCd_lote(cursor.getString(cursor.getColumnIndex("cd_lote")));
        	  novo.setCd_quadra(cursor.getString(cursor.getColumnIndex("cd_quadra")));
        	  novo.setCadastrador(cursor.getString(cursor.getColumnIndex("cadastrador")));
        	  novo.setObs_geo(cursor.getString(cursor.getColumnIndex("obs_geo")));
        	  novo.setObs_geo(cursor.getString(cursor.getColumnIndex("data_cadastro")));
        	  novo.setAtivo(cursor.getString(cursor.getColumnIndex("ativo")));
        	  novo.setDt_atualizacao(cursor.getString(cursor.getColumnIndex("dt_atualizacao")));
        	  novo.setNm_user_atualizacao(cursor.getString(cursor.getColumnIndex("nm_user_atualizacao")));
        	  novo.setComando(cursor.getString(cursor.getColumnIndex("comando")));
        	  novo.setDt_cadastro(cursor.getString(cursor.getColumnIndex("dt_cadastro")));
        	  novo.setNm_user_cadastro(cursor.getString(cursor.getColumnIndex("nm_user_cadastro")));
        	  novo.setControle_tablet(cursor.getString(cursor.getColumnIndex("controle_tablet")));
        	  novo.setEdicao(cursor.getString(cursor.getColumnIndex("edicao")));
        	  novo.setStatus_associacao(cursor.getString(cursor.getColumnIndex("status_associacao")));
        	  novo.setStatus_cadastro(cursor.getString(cursor.getColumnIndex("status_cadastro")));
        	  
        	  
              }
          
          cursor.close();
          banco.close();
          
          } 
	  
	  
	  
	   
	   return novo;
   }
	
   public void deletar(String nu_insc_imbl){
	   banco.getWritableDatabase().delete("cotr_imobiliario", " nu_insc_imbl = "+nu_insc_imbl+"",null);
		banco.close();		
		
		
	}
   
   public ArrayList<FormularioEntidade> ListaInscricoesQuadras(String cd_quadra){
	   ArrayList<FormularioEntidade> lista = new ArrayList<FormularioEntidade>();
		
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT nu_insc_imbl,status_cadastro FROM cotr_imobiliario WHERE cd_quadra = '"+cd_quadra+"';",null);
		 while (cursor.moveToNext()){
			 FormularioEntidade form = new FormularioEntidade();
			 form.setNu_insc_imbl(cursor.getString(cursor.getColumnIndex("nu_insc_imbl"))); 
			 form.setStatus_cadastro(cursor.getString(cursor.getColumnIndex("status_cadastro")));
			 lista.add(form);
			 
		 }
	   
		    cursor.close();
	     	banco.close();     
	     	return lista;
   }
    
   public int contaInscricoes(String cd_quadra){
   	
   	Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT cd_quadra FROM cotr_imobiliario WHERE cd_quadra = '"+cd_quadra+"'",null);
   	int total = cursor.getCount();
   	
   	cursor.close();
     	banco.close(); 
   	return total;
   }
   
   public ArrayList<FormularioEntidade> ListaInscricoesLote(String cd_lote){
	   ArrayList<FormularioEntidade> lista = new ArrayList<FormularioEntidade>();
		
		Cursor cursor = banco.getWritableDatabase().rawQuery("SELECT nu_insc_imbl FROM cotr_imobiliario WHERE cd_lote = '"+cd_lote+"';",null);
		 while (cursor.moveToNext()){
			 FormularioEntidade form = new FormularioEntidade();
			 form.setNu_insc_imbl(cursor.getString(cursor.getColumnIndex("nu_insc_imbl"))); 
			 lista.add(form);
			 
		 }
	   
		    cursor.close();
	     	banco.close();     
	     	return lista;
   }
   
  public String VerificaCamposNulos(String valor){
	   
	   if(valor != null){
		   return valor;
	   }else{
		   return "";
		   
	       }
	   
   }
 
 
 public String VerificaCamposNumericosNulos(String valor){
	 
	 if(valor != null || valor != ""){
		 
		   return valor;
		   
	 }else{

    return "1";
		   
	       
	 	}
 
	 
 
 	}
 
 public String DV(String inscricao){
		int auxl = 14;
		int coln = 1;
		
		while( coln < 4 ){
			int aux1 = coln == 1 ? 66 : ( coln == 2 ? 80 : 54 );
			int aux2 = 2;
			
			while( auxl > 0 ){
				int auxiliar = Integer.parseInt(inscricao.substring((auxl-1),(auxl-1)+1));
				aux1 = aux1 + (auxiliar) * aux2;
				aux2 = aux2 == 9 ? 2 : aux2 + 1;
				auxl--;
			}
			
			int aux3 = 11 - ( aux1 % 11 );
			aux3 = aux3 == 10 || aux3 == 11 ? 0 : aux3;
			inscricao = inscricao+""+aux3;
			
			auxl = coln + 14;
			coln++;
		}
		
		return inscricao;
	}
 
 
 
   
}
