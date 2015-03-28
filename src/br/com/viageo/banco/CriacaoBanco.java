package br.com.viageo.banco;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriacaoBanco extends SQLiteOpenHelper{
	private static final String NOME_DATABASE = "vgfiscal";
	private static final int VERSAO_DATABASE = 5;
	
	
	

	
	private static final String SQL_QUADRA  = "CREATE TABLE quadra ("+
			  "id_associacao TEXT,"+
			  "cd_quadra TEXT,"+
			  "data_associacao TEXT,"+
			  "status_sincronizacao TEXT,"+
			  "id_usuario TEXT)";
	
	private static final String SQL_PLAN_BAIRRO  = "CREATE TABLE plan_bairro ("+
			  "mslink TEXT,"+
			  "nm_bairro TEXT)";
	
	private static final String SQL_PESSOA  = "CREATE TABLE pessoa ("+
			  "in_pfis_pjur TEXT,"+
			  "nu_tipo_orgn_jurd TEXT,"+
			  "cd_logr TEXT,"+
			  "nu_imvl TEXT,"+
			  "in_cads TEXT,"+
			  "nu_cart_idnt TEXT,"+
			  "nu_telf TEXT,"+
			  "nu_pess TEXT,"+
			  "nm_pess TEXT,"+
			  "de_comp TEXT,"+
			  "dt_cads_pess TEXT,"+
			  "dt_vigr TEXT,"+
			  "cv_nm_nu_pess TEXT,"+
			  "cv_in_ambl TEXT,"+
			  "cv_in_divr TEXT,"+
			  "cv_in_imbl TEXT,"+
			  "cv_in_mobl TEXT,"+
			  "cv_in_mobl_ambl TEXT,"+
			  "cv_in_prtc TEXT,"+
			  "dt_atualizacao TEXT,"+
			  "nm_user_atualizacao TEXT,"+
			  "comando TEXT,"+
			  "dt_cadastro TEXT,"+
			  "sincronizado TEXT,"+
			  "nm_user_cadastro TEXT,"+
			  "id_logra TEXT)";

	private static final String SQL_LOGRADOURO  = "CREATE TABLE logradouro ("+
			  "id_logra TEXT,"+
			  "cd_logr TEXT,"+
			  "tp_logr TEXT,"+
			  "tp_atrb_logr TEXT,"+
			  "nm_logr TEXT,"+
			  "nu_cep_logr TEXT,"+
			  "nm_cidd TEXT,"+
			  "nm_barr TEXT,"+
			  "sg_uf TEXT,"+
			  "label TEXT)";
	
	private static final String SQL_COTR_IMOBILIARIO = "CREATE TABLE cotr_imobiliario ("+
			  "nu_insc_imbl TEXT,"+
			  "nu_insc_imbl_ant TEXT,"+
			  "tp_cobr TEXT,"+
			  "tp_estr TEXT,"+
			  "tp_ocpc TEXT,"+
			  "tp_sitc TEXT,"+
			  "tp_locc TEXT,"+
			  "tp_alnh TEXT,"+
			  "tp_edfc TEXT,"+
			  "cd_seca_tes4 TEXT,"+
			  "cd_logr_tes4 TEXT,"+
			  "cd_seca_tes3 TEXT,"+
			  "cd_logr_tes3 TEXT,"+
			  "cd_seca_tes2 TEXT,"+
			  "cd_logr_tes2 TEXT,"+
			  "cd_imbl TEXT,"+
			  "tp_isnc TEXT,"+
			  "tp_isnt TEXT,"+
			  "tp_isnt_tsu TEXT,"+
			  "tp_isnt_iptu TEXT,"+
			  "nm_tabl_cart TEXT,"+
			  "tp_eltr TEXT,"+
			  "tp_esgt TEXT,"+
			  "tp_ligc_agua TEXT,"+
			  "tp_elvd TEXT,"+
			  "tp_arvr TEXT,"+
			  "tp_pass TEXT,"+
			  "tp_murd TEXT,"+
			  "tp_utlz TEXT,"+
			  "cv_vl_outr_trb5 REAL,"+
			  "cv_vl_prmr_trb1 REAL,"+
			  "cv_vl_prmr_trb2 REAL,"+
			  "cv_vl_prmr_trb3 REAL,"+
			  "cv_vl_prmr_trb4 REAL,"+
			  "cv_vl_prmr_trb5 REAL,"+
			  "cv_vt_test REAL,"+
			  "cd_cads_fisc TEXT,"+
			  "qt_area_cons REAL,"+
			  "qt_afst_frnt REAL,"+
			  "qt_area_trbt_terr REAL,"+
			  "vl_venl_prdl REAL,"+
			  "vl_m2_prdl REAL,"+
			  "vl_venl_terr REAL,"+
			  "vl_m2_terr REAL,"+
			  "vl_parc_unic_trb1 REAL,"+
			  "vl_parc_unic_trb2 REAL,"+
			  "de_logr_corr TEXT,"+
			  "dt_cads_cimb TEXT,"+
			  "dt_vigr TEXT,"+
			  "dt_prcs_revs TEXT,"+
			  "nu_undd_lote INTEGER,"+
			  "nu_pavm INTEGER,"+
			  "qt_larg_meia_via REAL,"+
			  "vl_alqt_prdl REAL,"+
			  "vl_alqt_terr REAL,"+
			  "tp_padr_cons TEXT,"+
			  "cd_trb1 TEXT,"+
			  "cd_trb2 TEXT,"+
			  "cd_trb3 TEXT,"+
			  "cd_trb4 TEXT,"+
			  "cd_trb5 TEXT,"+
			  "in_canc_cimb TEXT,"+
			  "in_endr_corr TEXT,"+
			  "nu_pess TEXT,"+
			  "tp_pard TEXT,"+
			  "tp_revs_extr TEXT,"+
			  "tp_vedc TEXT,"+
			  "de_comp TEXT,"+
			  "nm_edfc TEXT,"+
			  "nm_bloc TEXT,"+
			  "aa_cons TEXT,"+
			  "nu_prcs_isnt_iptu TEXT,"+
			  "nu_prcs_isnt_tsu TEXT,"+
			  "nu_prcs_lanc_revs TEXT,"+
			  "nu_prcs_habt TEXT,"+
			  "nu_test_prnc REAL,"+
			  "nu_tes2 REAL,"+
			  "nu_tes3 REAL,"+
			  "nu_tes4 REAL,"+
			  "qt_prfn REAL,"+
			  "qt_area_lote REAL,"+
			  "qt_area_vila REAL,"+
			  "qt_area_undd REAL,"+
			  "vl_parc_unic_trb3 REAL,"+
			  "vl_parc_unic_trb4 REAL,"+
			  "vl_parc_unic_trb5 REAL,"+
			  "cv_cd_seca TEXT,"+
			  "cv_vl_outr_trb1 REAL,"+
			  "cv_vl_outr_trb2 REAL,"+
			  "cv_vl_outr_trb3 REAL,"+
			  "cv_vl_outr_trb4 REAL,"+
			  "nu_insc_futr TEXT,"+
			  "nu_casan TEXT,"+
			  "nu_celesc TEXT,"+
			  "nu_alvr_cons TEXT,"+
			  "nu_matr_cart TEXT,"+
			  "nu_livr_cart TEXT,"+
			  "nu_folh_cart TEXT,"+
			  "cd_tabl_cart TEXT,"+
			  "vvenal TEXT,"+
			  "tp_terr_marn TEXT,"+
			  "tp_patr TEXT,"+
			  "tp_ocpc_lote TEXT,"+
			  "tp_pedl TEXT,"+
			  "tp_topg TEXT,"+
			  "tp_sitc_qudr TEXT,"+
			  "nu_lote TEXT,"+
			  "nu_qudr TEXT,"+
			  "nu_lotm TEXT,"+
			  "nu_apto_garg TEXT,"+
			  "nu_imvl TEXT,"+
			  "cd_seca TEXT,"+
			  "cd_logr TEXT,"+
			  "in_eqpt_segr TEXT,"+
			  "in_salo_fest TEXT,"+
			  "in_chur TEXT,"+
			  "in_saun TEXT,"+
			  "in_pisc TEXT,"+
			  "in_lare TEXT,"+
			  "in_sala_jogo TEXT,"+
			  "in_sala_gina TEXT,"+
			  "in_aque_gas TEXT,"+
			  "in_elev_serv TEXT,"+
			  "in_elev_soci TEXT,"+
			  "in_clim_ambi TEXT,"+
			  "qt_garg TEXT,"+
			  "cd_lote TEXT,"+
			  "cd_quadra TEXT,"+
			  "cadastrador TEXT,"+
			  "obs_geo TEXT,"+
			  "data_cadastro TEXT,"+
			  "ativo TEXT,"+
			  "id_bairro INTEGER,"+
			  "dt_atualizacao TEXT,"+
			  "nm_user_atualizacao TEXT,"+
			  "comando INTEGER,"+
			  "dt_cadastro TEXT,"+
			  "nm_user_cadastro TEXT,"+
			  "nu_pav_edif INTEGER,"+
			  "controle_tablet INTEGER,"+
			  "sincronizado TEXT,"+
			  "id_logra INTEGER,"+
			  "edicao TEXT,"+
			  "status_associacao TEXT,"+
			  "status_cadastro TEXT,"+
			  "tipo_cadastro TEXT)";
	
	public CriacaoBanco(Context context) {
		super(context, NOME_DATABASE, null, VERSAO_DATABASE);
		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_QUADRA);
		database.execSQL(SQL_PLAN_BAIRRO);
		database.execSQL(SQL_PESSOA);
		database.execSQL(SQL_LOGRADOURO);
		database.execSQL(SQL_COTR_IMOBILIARIO);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS controle");
		database.execSQL("DROP TABLE IF EXISTS quadra");
		database.execSQL("DROP TABLE IF EXISTS plan_bairro");
		database.execSQL("DROP TABLE IF EXISTS pessoa");
		database.execSQL("DROP TABLE IF EXISTS logradouro");
		database.execSQL("DROP TABLE IF EXISTS cotr_imobiliario");
		onCreate(database);
		
	}
	
	
	
	
	
	
	
	
	
	

}
