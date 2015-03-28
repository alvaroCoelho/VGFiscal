package br.com.viageo.entidade;

public class FormularioEntidade {
	
	

	private String nu_insc_imbl, cd_logr,id_logra,cd_seca,nu_imvl,de_comp,id_bairro,nu_lotm, nu_qudr, nu_lote,nu_apto_garg,
	nm_bloc,nm_edfc,in_endr_corr,nu_pess,cd_imbl, tp_ocpc_lote,tp_murd, tp_patr, tp_terr_marn, tp_utlz,tp_pass,tp_arvr, 
	tp_ligc_agua, tp_elvd,tp_esgt,tp_eltr,tp_isnt_iptu,tp_isnt_tsu,nu_pavm,nu_celesc,nu_casan,nu_matr_cart,nu_livr_cart,
	nu_folh_cart,cd_tabl_cart,nu_prcs_lanc_revs,dt_prcs_revs,vvenal,qt_area_lote,qt_area_cons,tp_sitc_qudr,tp_topg,qt_prfn,tp_pedl,
	nu_test_prnc,cd_logr_tes2,cd_seca_tes2,nu_tes2,cd_logr_tes3,cd_seca_tes3,nu_tes3,cd_logr_tes4,cd_seca_tes4,nu_tes4,aa_cons,
	nu_pav_edif,qt_area_undd,tp_edfc,tp_alnh,tp_locc,tp_sitc,tp_ocpc,tp_estr,tp_cobr,	tp_pard,tp_vedc,tp_revs_extr,tp_padr_cons,qt_afst_frnt,
	in_clim_ambi,in_eqpt_segr,in_salo_fest,in_chur,in_saun,in_pisc,in_lare,in_sala_jogo,in_sala_gina,in_aque_gas,in_elev_serv,
	in_elev_soci,qt_garg,cd_lote,cd_quadra,cadastrador,obs_geo,data_cadastro,ativo,dt_atualizacao,nm_user_atualizacao,comando,
	dt_cadastro,nm_user_cadastro,controle_tablet,edicao,status_associacao,status_cadastro,sincronizado,nu_insc_imbl_ant,in_canc_cimb,tipo_cadastro;

	



	public String getTipo_cadastro() {
		return tipo_cadastro;
	}


	public void setTipo_cadastro(String tipo_cadastro) {
		this.tipo_cadastro = tipo_cadastro;
	}


	public FormularioEntidade(String nu_insc_imbl, String cd_logr,
			String id_logra, String cd_seca, String nu_imvl, String de_comp,
			String id_bairro,String nu_lotm, String nu_qudr, String nu_lote,
			String nu_apto_garg, String nm_bloc, String nm_edfc,
			String in_endr_corr, String nu_pess, String cd_imbl,
			String tp_ocpc_lote, String tp_murd, String tp_patr,
			String tp_terr_marn, String tp_utlz, String tp_pass,
			String tp_arvr, String tp_ligc_agua, String tp_elvd,
			String tp_esgt, String tp_eltr, String tp_isnt_iptu,
			String tp_isnt_tsu, String nu_pavm, String nu_celesc,
			String nu_casan, String nu_matr_cart, String nu_livr_cart,
			String nu_folh_cart, String cd_tabl_cart, String nu_prcs_lanc_revs,
			String dt_prcs_revs,String vvenal, String qt_area_lote, String qt_area_cons,
			String tp_sitc_qudr, String tp_topg, String qt_prfn,
			String tp_pedl, String nu_test_prnc, String cd_logr_tes2,
			String cd_seca_tes2, String nu_tes2, String cd_logr_tes3,
			String cd_seca_tes3, String nu_tes3, String cd_logr_tes4,
			String cd_seca_tes4, String nu_tes4, String aa_cons,
			String nu_pav_edif, String qt_area_undd, String tp_edfc,
			String tp_alnh, String tp_locc, String tp_sitc,String tp_ocpc, String tp_estr,
			String tp_cobr, String tp_pard, String tp_vedc,
			String tp_revs_extr, String tp_padr_cons, String qt_afst_frnt,
			String in_clim_ambi, String in_eqpt_segr, String in_salo_fest,
			String in_chur, String in_saun, String in_pisc, String in_lare,
			String in_sala_jogo, String in_sala_gina, String in_aque_gas,
			String in_elev_serv, String in_elev_soci, String qt_garg,
			String cd_lote, String cd_quadra, String cadastrador,
			String obs_geo, String data_cadastro, String ativo,
			String dt_atualizacao, String nm_user_atualizacao, String comando,
			String dt_cadastro, String nm_user_cadastro,
			String controle_tablet, String edicao, String status_associacao,
			String status_cadastro, String sincronizado, String nu_insc_imbl_ant, String in_canc_cimb) {
		super();
		this.nu_insc_imbl = nu_insc_imbl;
		this.cd_logr = cd_logr;
		this.id_logra = id_logra;
		this.cd_seca = cd_seca;
		this.nu_imvl = nu_imvl;
		this.de_comp = de_comp;
		this.id_bairro = id_bairro;
		this.nu_lotm = nu_lotm;
		this.nu_qudr = nu_qudr;
		this.nu_lote = nu_lote;
		this.nu_apto_garg = nu_apto_garg;
		this.nm_bloc = nm_bloc;
		this.nm_edfc = nm_edfc;
		this.in_endr_corr = in_endr_corr;
		this.nu_pess = nu_pess;
		this.cd_imbl = cd_imbl;
		this.tp_ocpc_lote = tp_ocpc_lote;
		this.tp_murd = tp_murd;
		this.tp_patr = tp_patr;
		this.tp_terr_marn = tp_terr_marn;
		this.tp_utlz = tp_utlz;
		this.tp_pass = tp_pass;
		this.tp_arvr = tp_arvr;
		this.tp_ligc_agua = tp_ligc_agua;
		this.tp_elvd = tp_elvd;
		this.tp_esgt = tp_esgt;
		this.tp_eltr = tp_eltr;
		this.tp_isnt_iptu = tp_isnt_iptu;
		this.tp_isnt_tsu = tp_isnt_tsu;
		this.nu_pavm = nu_pavm;
		this.nu_celesc = nu_celesc;
		this.nu_casan = nu_casan;
		this.nu_matr_cart = nu_matr_cart;
		this.nu_livr_cart = nu_livr_cart;
		this.nu_folh_cart = nu_folh_cart;
		this.cd_tabl_cart = cd_tabl_cart;
		this.vvenal = vvenal;
		this.nu_prcs_lanc_revs = nu_prcs_lanc_revs;
		this.dt_prcs_revs = dt_prcs_revs;
		this.qt_area_lote = qt_area_lote;
		this.qt_area_cons = qt_area_cons;
		this.tp_sitc_qudr = tp_sitc_qudr;
		this.tp_topg = tp_topg;
		this.qt_prfn = qt_prfn;
		this.tp_pedl = tp_pedl;
		this.nu_test_prnc = nu_test_prnc;
		this.cd_logr_tes2 = cd_logr_tes2;
		this.cd_seca_tes2 = cd_seca_tes2;
		this.nu_tes2 = nu_tes2;
		this.cd_logr_tes3 = cd_logr_tes3;
		this.cd_seca_tes3 = cd_seca_tes3;
		this.nu_tes3 = nu_tes3;
		this.cd_logr_tes4 = cd_logr_tes4;
		this.cd_seca_tes4 = cd_seca_tes4;
		this.nu_tes4 = nu_tes4;
		this.aa_cons = aa_cons;
		this.nu_pav_edif = nu_pav_edif;
		this.qt_area_undd = qt_area_undd;
		this.tp_edfc = tp_edfc;
		this.tp_alnh = tp_alnh;
		this.tp_locc = tp_locc;
		this.tp_sitc = tp_sitc;
		this.tp_ocpc = tp_ocpc;
		this.tp_estr = tp_estr;
		this.tp_cobr = tp_cobr;
		this.tp_pard = tp_pard;
		this.tp_vedc = tp_vedc;
		this.tp_revs_extr = tp_revs_extr;
		this.tp_padr_cons = tp_padr_cons;
		this.qt_afst_frnt = qt_afst_frnt;
		this.in_clim_ambi = in_clim_ambi;
		this.in_eqpt_segr = in_eqpt_segr;
		this.in_salo_fest = in_salo_fest;
		this.in_chur = in_chur;
		this.in_saun = in_saun;
		this.in_pisc = in_pisc;
		this.in_lare = in_lare;
		this.in_sala_jogo = in_sala_jogo;
		this.in_sala_gina = in_sala_gina;
		this.in_aque_gas = in_aque_gas;
		this.in_elev_serv = in_elev_serv;
		this.in_elev_soci = in_elev_soci;
		this.qt_garg = qt_garg;
		this.cd_lote = cd_lote;
		this.cd_quadra = cd_quadra;
		this.cadastrador = cadastrador;
		this.obs_geo = obs_geo;
		this.data_cadastro = data_cadastro;
		this.ativo = ativo;
		this.dt_atualizacao = dt_atualizacao;
		this.nm_user_atualizacao = nm_user_atualizacao;
		this.comando = comando;
		this.dt_cadastro = dt_cadastro;
		this.nm_user_cadastro = nm_user_cadastro;
		this.controle_tablet = controle_tablet;
		this.edicao = edicao;
		this.status_associacao = status_associacao;
		this.status_cadastro = status_cadastro;
		this.sincronizado = sincronizado;
		this.nu_insc_imbl_ant = nu_insc_imbl_ant;
		this.in_canc_cimb = in_canc_cimb;
	}
	

	public FormularioEntidade() {
	
	}

	public String getNu_insc_imbl() {
		return nu_insc_imbl;
	}

	public void setNu_insc_imbl(String nu_insc_imbl) {
		this.nu_insc_imbl = nu_insc_imbl;
	}

	public String getCd_logr() {
		return cd_logr;
	}

	public void setCd_logr(String cd_logr) {
		this.cd_logr = cd_logr;
	}

	public String getId_logra() {
		return id_logra;
	}

	public void setId_logra(String id_logra) {
		this.id_logra = id_logra;
	}

	public String getCd_seca() {
		return cd_seca;
	}

	public void setCd_seca(String cd_seca) {
		this.cd_seca = cd_seca;
	}

	public String getNu_imvl() {
		return nu_imvl;
	}

	public void setNu_imvl(String nu_imvl) {
		this.nu_imvl = nu_imvl;
	}

	public String getDe_comp() {
		return de_comp;
	}

	public void setDe_comp(String de_comp) {
		this.de_comp = de_comp;
	}

	public String getId_bairro() {
		return id_bairro;
	}

	public void setId_bairro(String id_bairro) {
		this.id_bairro = id_bairro;
	}

	public String getNu_lotm() {
		return nu_lotm;
	}


	public void setNu_lotm(String nu_lotm) {
		this.nu_lotm = nu_lotm;
	}


	public String getNu_qudr() {
		return nu_qudr;
	}

	public void setNu_qudr(String nu_qudr) {
		this.nu_qudr = nu_qudr;
	}

	public String getNu_lote() {
		return nu_lote;
	}

	public void setNu_lote(String nu_lote) {
		this.nu_lote = nu_lote;
	}

	public String getNu_apto_garg() {
		return nu_apto_garg;
	}

	public void setNu_apto_garg(String nu_apto_garg) {
		this.nu_apto_garg = nu_apto_garg;
	}

	public String getNm_bloc() {
		return nm_bloc;
	}

	public void setNm_bloc(String nm_bloc) {
		this.nm_bloc = nm_bloc;
	}

	public String getNm_edfc() {
		return nm_edfc;
	}

	public void setNm_edfc(String nm_edfc) {
		this.nm_edfc = nm_edfc;
	}

	public String getIn_endr_corr() {
		return in_endr_corr;
	}

	public void setIn_endr_corr(String in_endr_corr) {
		this.in_endr_corr = in_endr_corr;
	}

	public String getNu_pess() {
		return nu_pess;
	}

	public void setNu_pess(String nu_pess) {
		this.nu_pess = nu_pess;
	}

	public String getCd_imbl() {
		return cd_imbl;
	}

	public void setCd_imbl(String cd_imbl) {
		this.cd_imbl = cd_imbl;
	}

	public String getTp_ocpc_lote() {
		return tp_ocpc_lote;
	}

	public void setTp_ocpc_lote(String tp_ocpc_lote) {
		this.tp_ocpc_lote = tp_ocpc_lote;
	}

	public String getTp_murd() {
		return tp_murd;
	}

	public void setTp_murd(String tp_murd) {
		this.tp_murd = tp_murd;
	}

	public String getTp_patr() {
		return tp_patr;
	}

	public void setTp_patr(String tp_patr) {
		this.tp_patr = tp_patr;
	}

	public String getTp_terr_marn() {
		return tp_terr_marn;
	}

	public void setTp_terr_marn(String tp_terr_marn) {
		this.tp_terr_marn = tp_terr_marn;
	}

	public String getTp_utlz() {
		return tp_utlz;
	}

	public void setTp_utlz(String tp_utlz) {
		this.tp_utlz = tp_utlz;
	}

	public String getTp_pass() {
		return tp_pass;
	}

	public void setTp_pass(String tp_pass) {
		this.tp_pass = tp_pass;
	}

	public String getTp_arvr() {
		return tp_arvr;
	}

	public void setTp_arvr(String tp_arvr) {
		this.tp_arvr = tp_arvr;
	}

	public String getTp_ligc_agua() {
		return tp_ligc_agua;
	}

	public void setTp_ligc_agua(String tp_ligc_agua) {
		this.tp_ligc_agua = tp_ligc_agua;
	}

	public String getTp_elvd() {
		return tp_elvd;
	}

	public void setTp_elvd(String tp_elvd) {
		this.tp_elvd = tp_elvd;
	}

	public String getTp_esgt() {
		return tp_esgt;
	}

	public void setTp_esgt(String tp_esgt) {
		this.tp_esgt = tp_esgt;
	}

	public String getTp_eltr() {
		return tp_eltr;
	}

	public void setTp_eltr(String tp_eltr) {
		this.tp_eltr = tp_eltr;
	}

	public String getTp_isnt_iptu() {
		return tp_isnt_iptu;
	}

	public void setTp_isnt_iptu(String tp_isnt_iptu) {
		this.tp_isnt_iptu = tp_isnt_iptu;
	}

	public String getTp_isnt_tsu() {
		return tp_isnt_tsu;
	}

	public void setTp_isnt_tsu(String tp_isnt_tsu) {
		this.tp_isnt_tsu = tp_isnt_tsu;
	}

	public String getNu_pavm() {
		return nu_pavm;
	}

	public void setNu_pavm(String nu_pavm) {
		this.nu_pavm = nu_pavm;
	}

	public String getNu_celesc() {
		return nu_celesc;
	}

	public void setNu_celesc(String nu_celesc) {
		this.nu_celesc = nu_celesc;
	}

	public String getNu_casan() {
		return nu_casan;
	}

	public void setNu_casan(String nu_casan) {
		this.nu_casan = nu_casan;
	}

	public String getNu_matr_cart() {
		return nu_matr_cart;
	}

	public void setNu_matr_cart(String nu_matr_cart) {
		this.nu_matr_cart = nu_matr_cart;
	}

	public String getNu_livr_cart() {
		return nu_livr_cart;
	}

	public void setNu_livr_cart(String nu_livr_cart) {
		this.nu_livr_cart = nu_livr_cart;
	}

	public String getNu_folh_cart() {
		return nu_folh_cart;
	}

	public void setNu_folh_cart(String nu_folh_cart) {
		this.nu_folh_cart = nu_folh_cart;
	}

	public String getCd_tabl_cart() {
		return cd_tabl_cart;
	}

	public void setCd_tabl_cart(String cd_tabl_cart) {
		this.cd_tabl_cart = cd_tabl_cart;
	}

	public String getNu_prcs_lanc_revs() {
		return nu_prcs_lanc_revs;
	}

	public void setNu_prcs_lanc_revs(String nu_prcs_lanc_revs) {
		this.nu_prcs_lanc_revs = nu_prcs_lanc_revs;
	}

	public String getDt_prcs_revs() {
		return dt_prcs_revs;
	}

	public void setDt_prcs_revs(String dt_prcs_revs) {
		this.dt_prcs_revs = dt_prcs_revs;
	}

	public String getQt_area_lote() {
		return qt_area_lote;
	}

	public void setQt_area_lote(String qt_area_lote) {
		this.qt_area_lote = qt_area_lote;
	}

	public String getQt_area_cons() {
		return qt_area_cons;
	}

	public void setQt_area_cons(String qt_area_cons) {
		this.qt_area_cons = qt_area_cons;
	}

	public String getTp_sitc_qudr() {
		return tp_sitc_qudr;
	}

	public void setTp_sitc_qudr(String tp_sitc_qudr) {
		this.tp_sitc_qudr = tp_sitc_qudr;
	}

	public String getTp_topg() {
		return tp_topg;
	}

	public void setTp_topg(String tp_topg) {
		this.tp_topg = tp_topg;
	}

	public String getQt_prfn() {
		return qt_prfn;
	}

	public void setQt_prfn(String qt_prfn) {
		this.qt_prfn = qt_prfn;
	}

	public String getTp_pedl() {
		return tp_pedl;
	}

	public void setTp_pedl(String tp_pedl) {
		this.tp_pedl = tp_pedl;
	}

	public String getNu_test_prnc() {
		return nu_test_prnc;
	}

	public void setNu_test_prnc(String nu_test_prnc) {
		this.nu_test_prnc = nu_test_prnc;
	}

	public String getCd_logr_tes2() {
		return cd_logr_tes2;
	}

	public void setCd_logr_tes2(String cd_logr_tes2) {
		this.cd_logr_tes2 = cd_logr_tes2;
	}

	public String getCd_seca_tes2() {
		return cd_seca_tes2;
	}

	public void setCd_seca_tes2(String cd_seca_tes2) {
		this.cd_seca_tes2 = cd_seca_tes2;
	}

	public String getNu_tes2() {
		return nu_tes2;
	}

	public void setNu_tes2(String nu_tes2) {
		this.nu_tes2 = nu_tes2;
	}

	public String getCd_logr_tes3() {
		return cd_logr_tes3;
	}

	public void setCd_logr_tes3(String cd_logr_tes3) {
		this.cd_logr_tes3 = cd_logr_tes3;
	}

	public String getCd_seca_tes3() {
		return cd_seca_tes3;
	}

	public void setCd_seca_tes3(String cd_seca_tes3) {
		this.cd_seca_tes3 = cd_seca_tes3;
	}

	public String getNu_tes3() {
		return nu_tes3;
	}

	public void setNu_tes3(String nu_tes3) {
		this.nu_tes3 = nu_tes3;
	}

	public String getCd_logr_tes4() {
		return cd_logr_tes4;
	}

	public void setCd_logr_tes4(String cd_logr_tes4) {
		this.cd_logr_tes4 = cd_logr_tes4;
	}

	public String getCd_seca_tes4() {
		return cd_seca_tes4;
	}

	public void setCd_seca_tes4(String cd_seca_tes4) {
		this.cd_seca_tes4 = cd_seca_tes4;
	}

	public String getNu_tes4() {
		return nu_tes4;
	}

	public void setNu_tes4(String nu_tes4) {
		this.nu_tes4 = nu_tes4;
	}

	public String getAa_cons() {
		return aa_cons;
	}

	public void setAa_cons(String aa_cons) {
		this.aa_cons = aa_cons;
	}

	public String getNu_pav_edif() {
		return nu_pav_edif;
	}

	public void setNu_pav_edif(String nu_pav_edif) {
		this.nu_pav_edif = nu_pav_edif;
	}

	public String getQt_area_undd() {
		return qt_area_undd;
	}

	public void setQt_area_undd(String qt_area_undd) {
		this.qt_area_undd = qt_area_undd;
	}

	public String getTp_edfc() {
		return tp_edfc;
	}

	public void setTp_edfc(String tp_edfc) {
		this.tp_edfc = tp_edfc;
	}

	public String getTp_alnh() {
		return tp_alnh;
	}

	public void setTp_alnh(String tp_alnh) {
		this.tp_alnh = tp_alnh;
	}

	public String getTp_locc() {
		return tp_locc;
	}

	public void setTp_locc(String tp_locc) {
		this.tp_locc = tp_locc;
	}

	public String getTp_sitc() {
		return tp_sitc;
	}

	public void setTp_sitc(String tp_sitc) {
		this.tp_sitc = tp_sitc;
	}

	public String getTp_ocpc() {
		return tp_ocpc;
	}


	public void setTp_ocpc(String tp_ocpc) {
		this.tp_ocpc = tp_ocpc;
	}


	public String getTp_estr() {
		return tp_estr;
	}

	public void setTp_estr(String tp_estr) {
		this.tp_estr = tp_estr;
	}

	public String getTp_cobr() {
		return tp_cobr;
	}

	public void setTp_cobr(String tp_cobr) {
		this.tp_cobr = tp_cobr;
	}

	public String getTp_pard() {
		return tp_pard;
	}

	public void setTp_pard(String tp_pard) {
		this.tp_pard = tp_pard;
	}

	public String getTp_vedc() {
		return tp_vedc;
	}

	public void setTp_vedc(String tp_vedc) {
		this.tp_vedc = tp_vedc;
	}

	public String getTp_revs_extr() {
		return tp_revs_extr;
	}

	public void setTp_revs_extr(String tp_revs_extr) {
		this.tp_revs_extr = tp_revs_extr;
	}

	public String getTp_padr_cons() {
		return tp_padr_cons;
	}

	public void setTp_padr_cons(String tp_padr_cons) {
		this.tp_padr_cons = tp_padr_cons;
	}

	public String getQt_afst_frnt() {
		return qt_afst_frnt;
	}

	public void setQt_afst_frnt(String qt_afst_frnt) {
		this.qt_afst_frnt = qt_afst_frnt;
	}

	public String getIn_clim_ambi() {
		return in_clim_ambi;
	}

	public void setIn_clim_ambi(String in_clim_ambi) {
		this.in_clim_ambi = in_clim_ambi;
	}

	public String getIn_eqpt_segr() {
		return in_eqpt_segr;
	}

	public void setIn_eqpt_segr(String in_eqpt_segr) {
		this.in_eqpt_segr = in_eqpt_segr;
	}

	public String getIn_salo_fest() {
		return in_salo_fest;
	}

	public void setIn_salo_fest(String in_salo_fest) {
		this.in_salo_fest = in_salo_fest;
	}

	public String getIn_chur() {
		return in_chur;
	}

	public void setIn_chur(String in_chur) {
		this.in_chur = in_chur;
	}

	public String getIn_saun() {
		return in_saun;
	}

	public void setIn_saun(String in_saun) {
		this.in_saun = in_saun;
	}

	public String getIn_pisc() {
		return in_pisc;
	}

	public void setIn_pisc(String in_pisc) {
		this.in_pisc = in_pisc;
	}

	public String getIn_lare() {
		return in_lare;
	}

	public void setIn_lare(String in_lare) {
		this.in_lare = in_lare;
	}

	public String getIn_sala_jogo() {
		return in_sala_jogo;
	}

	public void setIn_sala_jogo(String in_sala_jogo) {
		this.in_sala_jogo = in_sala_jogo;
	}

	public String getIn_sala_gina() {
		return in_sala_gina;
	}

	public void setIn_sala_gina(String in_sala_gina) {
		this.in_sala_gina = in_sala_gina;
	}

	public String getIn_aque_gas() {
		return in_aque_gas;
	}

	public void setIn_aque_gas(String in_aque_gas) {
		this.in_aque_gas = in_aque_gas;
	}

	public String getIn_elev_serv() {
		return in_elev_serv;
	}

	public void setIn_elev_serv(String in_elev_serv) {
		this.in_elev_serv = in_elev_serv;
	}

	public String getIn_elev_soci() {
		return in_elev_soci;
	}

	public void setIn_elev_soci(String in_elev_soci) {
		this.in_elev_soci = in_elev_soci;
	}

	public String getQt_garg() {
		return qt_garg;
	}

	public void setQt_garg(String qt_garg) {
		this.qt_garg = qt_garg;
	}

	public String getCd_lote() {
		return cd_lote;
	}

	public void setCd_lote(String cd_lote) {
		this.cd_lote = cd_lote;
	}

	public String getCd_quadra() {
		return cd_quadra;
	}

	public void setCd_quadra(String cd_quadra) {
		this.cd_quadra = cd_quadra;
	}

	public String getCadastrador() {
		return cadastrador;
	}

	public void setCadastrador(String cadastrador) {
		this.cadastrador = cadastrador;
	}

	public String getObs_geo() {
		return obs_geo;
	}

	public void setObs_geo(String obs_geo) {
		this.obs_geo = obs_geo;
	}

	public String getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(String data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getDt_atualizacao() {
		return dt_atualizacao;
	}

	public void setDt_atualizacao(String dt_atualizacao) {
		this.dt_atualizacao = dt_atualizacao;
	}

	public String getNm_user_atualizacao() {
		return nm_user_atualizacao;
	}

	public void setNm_user_atualizacao(String nm_user_atualizacao) {
		this.nm_user_atualizacao = nm_user_atualizacao;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public String getDt_cadastro() {
		return dt_cadastro;
	}

	public void setDt_cadastro(String dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}

	public String getNm_user_cadastro() {
		return nm_user_cadastro;
	}

	public void setNm_user_cadastro(String nm_user_cadastro) {
		this.nm_user_cadastro = nm_user_cadastro;
	}

	public String getControle_tablet() {
		return controle_tablet;
	}

	public void setControle_tablet(String controle_tablet) {
		this.controle_tablet = controle_tablet;
	}

	public String getEdicao() {
		return edicao;
	}

	public void setEdicao(String edicao) {
		this.edicao = edicao;
	}

	public String getStatus_associacao() {
		return status_associacao;
	}

	public void setStatus_associacao(String status_associacao) {
		this.status_associacao = status_associacao;
	}

	public String getStatus_cadastro() {
		return status_cadastro;
	}

	public void setStatus_cadastro(String status_cadastro) {
		this.status_cadastro = status_cadastro;
	}
	public String getSincronizado() {
		return sincronizado;
	}


	public void setSincronizado(String sincronizado) {
		this.sincronizado = sincronizado;
	}


	public String getNu_insc_imbl_ant() {
		return nu_insc_imbl_ant;
	}

	public void setNu_insc_imbl_ant(String nu_insc_imbl_ant) {
		this.nu_insc_imbl_ant = nu_insc_imbl_ant;
	}

	public String getIn_canc_cimb() {
		return in_canc_cimb;
	}

	public void setIn_canc_cimb(String in_canc_cimb) {
		this.in_canc_cimb = in_canc_cimb;
	}
	
	public String getVvenal() {
		return vvenal;
	}


	public void setVvenal(String vvenal) {
		this.vvenal = vvenal;
	}


	
	

}
