package br.com.viageo;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.viageo.banco.BancoBairro;
import br.com.viageo.banco.BancoFormulario;
import br.com.viageo.banco.BancoLogradouro;
import br.com.viageo.banco.BancoPessoa;
import br.com.viageo.banco.BancoQuadra;
import br.com.viageo.entidade.Bairro;
import br.com.viageo.entidade.FormularioEntidade;
import br.com.viageo.entidade.Logradouro;
import br.com.viageo.entidade.Pessoa;
import br.com.viageo.entidade.Quadra;


@SuppressLint("NewApi")
public class Formulario extends ListActivity implements OnClickListener {

	//String url_php = "http://192.168.0.15/geo_goianesia/conexaoAndroid/conexao_android.php";
	//String url_php = "http://geo.goianesia.go.gov.br/geo_goianesia/conexaoAndroid/conexao_android.php";
	String o;
	
	//Campos de Controle (invisiveis)
	EditText id_logra,cd_bairro,id_bairro,cd_bairro_pessoa,id_logra_1,id_logra_2,id_logra_3,id_logra_4,src_img,controle_warning,valida,id_bci,nu_insc_imbl_ant,etO;
	//Localizacao do Imovel 
	EditText nu_insc_imbl,cdCadastro,imovelEndereco,logradouro,numero,cdLogr,secao,complemento,bairro,loteamento,quadra,lote,apto,bloco,edificio;
	//Ddaos do Proprietario
	EditText pessoaNome,pessoaCpf,pessoaCd_logr,cdContribuinte,pessoaEndereco,pessoaComplemento,pessoaBairro,pessoaTelefone,pessoaCep,pessoaCidade,pessoaUf;
	EditText coproprietario,cod_imobiliaria;	
	//Informacoes Gerais sobre o Imovel
	Spinner spMurado, spPatrimonio,spMarinha,spArvore,spElevador,spRedeAgua,spEsgoto,spEletricidade, spUtilizacao, spPasseio, spIsentoIptu, spIsentoTsu, spLancEnglobado;
	Spinner retMurado,retPatrimonio,retUtilizacao,retPasseio,retIsentoIptu,retIsentoTsu,retLancEnglobado;
	EditText numCadEnglobado, afastamentoFrontal, numPavimentos,numCelesc,numCasan,cartMatricula,cartLivro,cartFolha,cartCodigo,valorVenal;
	//Informacoes Sobre o Terreno
	EditText areaTerreno, areaTributavel, areaTotalConst, areaEscritura, profundidade;
	Spinner spSitQuadra, spTopografia, spPedologia;
	EditText testada1,cdLogr1,secao1,logradouro1,testada2,cdLogr2,secao2,logradouro2,testada3,cdLogr3,secao3,logradouro3,testada4,cdLogr4,secao4,logradouro4;
	//Informacoes SObre a Edificacao
	EditText anoConst,numPavmEdf,areaConstUnid,alvaraConstrucao,apartamento,nrGaragem;
	Spinner spTipo, spAlinhamento, spLocacao, spSituacao,spTp_ocupacao, spEstrutura, spCobertura, spParedes, spVedacoesEsquadrias, spRevExterno, spPadraoConstrucao, spOcupacao, retOcupacao;
	CheckBox cbAltColetiva,cbEndCorrespondencia,cbClimatizacao,cbEquipSeguranca,cbSalaFestas,cbChurrasqueira,cbSauna,cbPiscina,cbLareira,cbSalaoJogos,cbsalaGinastica,cbAquecedoresGas,cbElevServico,cbElevadorSocial;
	//Informacoes Complementares
	EditText numEscritura,folhaEscritura,livroEscritura,dataEscritura,numHabitese,anoHabitese,dataHabitese,numHidrometro,cadastrador,dataCadastro,averbacao,observacoes;
		
	ImageButton  btnImgPesquisarBairro, btnImgEditPessoa;
	ImageButton btnImgSairForm, btnImgPesquisarPessoa, btnImgPesquisarLogr, btnImgPesquisarLogr2, btnImgPesquisarLogr3, btnImgPesquisarLogr4, btnImgPesquisarLoteamento;
	Button btnSalvarFormulario, btnExcluirBCI;
	ImageButton imageTirarFoto, imageErrosValidacao;	
	HttpConection objConexao;	
	
	//Informacoes Gerais SObre o Imovel
	ArrayList<Opcoes> opcoesMurado, opcoesPatrimonio,opcoesMarinha,opcoesArvore,opcoesElevador,opcoesRedeAgua,opcoesEsgoto,opcoesEletricidade,opcoesUtilizacao, opcoesPasseio, opcoesIsentoIptu, opcoesIsentoTsu, opcoesLancEnglobado;
	//Informacoes Sobre o Terreno
	ArrayList<Opcoes> opcoess, opcoesTopologia, opcoesPedologia;
	//Informacoes SObre a Edificacao
	ArrayList<Opcoes> opcoesTipo, opcoesAlinhamento, opcoesLocacao, opcoesSituacao,opcoesTpOcupacao, opcoesEstrutura, opcoesCobertura, opcoesParedes, opcoesVedacoesEsquadrias, opcoesRevExterno, opcoesPadraoConstrucao, opcoesOcupacao;
		
	ArrayList<String> arrayNomeArquivo = new ArrayList<String>();
	ArrayList<String> array_validacoes = new ArrayList<String>();
	
	private ArrayList<MyData> list = new ArrayList<MyData>();
	private ArrayList<ArrayList<String>> arrayJsonEdif = new ArrayList<ArrayList<String>>();
	
	int positionEditEdificacao = 999;
	String colocaBotao = "false";
	String colocaBotao2 = "false";
	
	AsyncTask<String, Long, Void> thread_execSalvar;
	JSONObject retornoSalvar;
	int numero_caseSalvar = 0;
	String msgSalvar,confirma_salva,alteracao_coletiva;
	
	AsyncTask<String, Long, Void> thread_execExcluirBci;
	int numero_caseExcluirBci = 0;
	String msgExcluirBci,inscricaoAntiga,altera;
	
	AsyncTask<String, Long, Void> thread_execTirarFoto;
	Intent winFoto;
	
	AsyncTask<String, Long, Void> thread_execEditPessoa;
	
	AsyncTask<String, Long, Void> thread_execDeletaEdif;
	
	int numero_caseDeletaEdif = 0;
	String msgDeletaEdif;
	Pessoa pessoaForm = new Pessoa();
	
	Logradouro logradouroForm = new Logradouro();
	Bairro bairroForm = new Bairro();
	FormularioEntidade form = new FormularioEntidade();
	Quadra quadraForm = new Quadra();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		btnImgSairForm = (ImageButton)findViewById(R.id.btnImgSairForm);
		btnImgSairForm.setOnClickListener(this);
		
		btnSalvarFormulario = (Button)findViewById(R.id.btnSalvarFormulario);
		btnSalvarFormulario.setOnClickListener(this);

		btnExcluirBCI = (Button)findViewById(R.id.btnExcluirBci);
		btnExcluirBCI.setOnClickListener(this);
		
	 	btnImgPesquisarPessoa = (ImageButton)findViewById(R.id.btnImgPesquisarPessoa);
		btnImgPesquisarPessoa.setOnClickListener(this);
		btnImgPesquisarPessoa.setFocusableInTouchMode(true);
		btnImgPesquisarPessoa.requestFocus();
		
		btnImgPesquisarLogr = (ImageButton)findViewById(R.id.btnImgPesquisarLogr);
		btnImgPesquisarLogr.setOnClickListener(this);
		btnImgPesquisarLogr.setFocusableInTouchMode(true);
		btnImgPesquisarLogr.requestFocus();
		
		btnImgPesquisarLogr2 = (ImageButton)findViewById(R.id.btnImgPesquisarLogr2);
		btnImgPesquisarLogr2.setOnClickListener(this);
		btnImgPesquisarLogr2.setFocusableInTouchMode(true);
		btnImgPesquisarLogr2.requestFocus();
		
		btnImgPesquisarLogr3 = (ImageButton)findViewById(R.id.btnImgPesquisarLogr3);
		btnImgPesquisarLogr3.setOnClickListener(this);
		btnImgPesquisarLogr3.setFocusableInTouchMode(true);
		btnImgPesquisarLogr3.requestFocus();
		
		btnImgPesquisarLogr4 = (ImageButton)findViewById(R.id.btnImgPesquisarLogr4);
		btnImgPesquisarLogr4.setOnClickListener(this);
		btnImgPesquisarLogr4.setFocusableInTouchMode(true);
		btnImgPesquisarLogr4.requestFocus();
		
		btnImgPesquisarBairro = (ImageButton)findViewById(R.id.btnImgPesquisarBairro);
		btnImgPesquisarBairro.setOnClickListener(this);
		btnImgPesquisarBairro.setFocusableInTouchMode(true);
		btnImgPesquisarBairro.requestFocus();
		
		
		imageTirarFoto = (ImageButton)findViewById(R.id.imageTirarFoto);
		imageTirarFoto.setOnClickListener(this);
		
		imageErrosValidacao = (ImageButton) findViewById(R.id.imageErrosValidacao);
		imageErrosValidacao.setOnClickListener(this);
		
		btnImgEditPessoa = (ImageButton)findViewById(R.id.btnImgEditPessoa);
		btnImgEditPessoa.setOnClickListener(this);
		btnImgEditPessoa.setFocusableInTouchMode(true);
		btnImgEditPessoa.requestFocus();
		
		inicializarFindViewById();		
		inicializaSpinner();
		
		nu_insc_imbl.setFocusableInTouchMode(true);
		nu_insc_imbl.requestFocus();
		
		 String cpf = "";
		
		Intent inscricaoEscolha = getIntent();
		final String inscricao = inscricaoEscolha.getStringExtra("inscricao");
	     cpf = inscricaoEscolha.getStringExtra("cpf");
			if(cpf == null){
				cpf= "";
			} 
		if(TemConexao()){
			
				if(!inscricao.equals("")||inscricao == null){
					preencheForm(inscricao,cpf);
				}else{
					   if(!cpf.equals("")){
               		   preencherDadosPessoaWEB(cpf);   
					   preencheFormInsert();
						   
					   }else{		
						
						preencheFormInsert();
					   }
				   }		
		
				
				}else{
					if(!inscricao.equals("")||inscricao == null){
						preencheFormBD(inscricao,cpf);
					}else{
						 if(!cpf.equals("")){
							   preencherDadosPessoaBD(cpf);
							   preencheFormInsert();
							   
						   }else{		
							
							preencheFormInsert();
						   }
						
					   }		
					
					
				}
				
				
	}
	 
    private boolean TemConexao() { 
        boolean lblnRet = false;
        try
        {
            ConnectivityManager cm = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE); 
            if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) { 
                lblnRet = true; 
            } else { 
                lblnRet = false; 
            }
        }catch (Exception e) {
            trace(e.getMessage());
        }
        return lblnRet;
    }
  
    private void trace (String msg){
        Log.d ("teste", msg);
    
        Toast.makeText(getBaseContext(), "ERRO DE CONECTIVIDADE = "+msg, Toast.LENGTH_LONG).show();
    }
	
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
	
	private void inicializarFindViewById(){
		
		//localização do imóvel
		nu_insc_imbl = (EditText)findViewById(R.id.etInscricao);
		imovelEndereco = (EditText)findViewById(R.id.etLogradouro);	    
	    numero = (EditText)findViewById(R.id.etNumero);
	    cdLogr = (EditText)findViewById(R.id.etCdLogr);
	    secao = (EditText)findViewById(R.id.etSecao);
	    complemento = (EditText)findViewById(R.id.etComplemento);
	    bairro = (EditText)findViewById(R.id.etBairro);
	    loteamento = (EditText)findViewById(R.id.etLoteamento);
	    quadra = (EditText)findViewById(R.id.etQuadra);
	    lote = (EditText)findViewById(R.id.etLote);
	    apto = (EditText)findViewById(R.id.etAptoGaragem);
	    bloco = (EditText)findViewById(R.id.etBloco);
	    edificio = (EditText)findViewById(R.id.etEdificio);
	    
	    //DADOS DO PROPRIETARIO			    
	    pessoaNome = (EditText)findViewById(R.id.etPessoaNome);
	    pessoaCpf = (EditText)findViewById(R.id.etPessoaCpf);
	    pessoaCd_logr = (EditText)findViewById(R.id.etCod_logrPessoa);
	    pessoaEndereco = (EditText)findViewById(R.id.etPessoaEndereco);
	    pessoaComplemento = (EditText)findViewById(R.id.etPessoaComplemento);
	    pessoaBairro = (EditText)findViewById(R.id.etPessoaBairro);
	    pessoaTelefone = (EditText) findViewById(R.id.etPessoaTelefone);
	    pessoaCep = (EditText)findViewById(R.id.etPessoaCep);
	    pessoaCidade = (EditText)findViewById(R.id.etPessoaCidade);
	    pessoaUf = (EditText)findViewById(R.id.etPessoaUf);
	    cod_imobiliaria = (EditText)findViewById(R.id.etCdImobiliaria);
	 
	    //INFORMACOES GERAIS SOBRE O IMOVEL
	    afastamentoFrontal = (EditText) findViewById(R.id.etAfastamentoFrontal);
	    numPavimentos = (EditText) findViewById(R.id.etNumPavimentos);
	    numCelesc = (EditText)findViewById(R.id.etNumCelesc);
	    numCasan = (EditText)findViewById(R.id.etNumCasan);
	    cartMatricula = (EditText)findViewById(R.id.etCartMatricula);
	    cartLivro = (EditText)findViewById(R.id.etCartLivro);
	    cartFolha = (EditText)findViewById(R.id.etCartFolha);
	    cartCodigo = (EditText)findViewById(R.id.etCartCodigo);
	    valorVenal = (EditText)findViewById(R.id.etValorVenal);
	    
	    //INFORMACOES SOBRE O TERRENO
	    areaTerreno = (EditText)findViewById(R.id.etAreaTerreno);
	    areaTotalConst = (EditText)findViewById(R.id.etAreaTotalConstruida);
	    profundidade = (EditText)findViewById(R.id.etProfundidade);
	    testada1 = (EditText)findViewById(R.id.etTestada1);
	    cdLogr1 = (EditText)findViewById(R.id.etCodigo1);
	    secao1 = (EditText)findViewById(R.id.etSecao1);
	    logradouro1 = (EditText)findViewById(R.id.etLogradouro1);
	    testada2 = (EditText)findViewById(R.id.etTestada2);
	    cdLogr2 = (EditText)findViewById(R.id.etCodigo2);
	    secao2 = (EditText)findViewById(R.id.etSecao2);
	    logradouro2 = (EditText)findViewById(R.id.etLogradouro2);
	    testada3 = (EditText)findViewById(R.id.etTestada3);
	    cdLogr3 = (EditText)findViewById(R.id.etCodigo3);
	    secao3 = (EditText)findViewById(R.id.etSecao3);
	    logradouro3 = (EditText)findViewById(R.id.etLogradouro3);
	    testada4 = (EditText)findViewById(R.id.etTestada4);
	    cdLogr4 = (EditText)findViewById(R.id.etCodigo4);
	    secao4 = (EditText)findViewById(R.id.etSecao4);
	    logradouro4 = (EditText)findViewById(R.id.etLogradouro4);
	   
	    //INFORMACOES SOBRE A EDIFICACAO
	    anoConst = (EditText)findViewById(R.id.etAnoConstrucao);
	    numPavmEdf = (EditText)findViewById(R.id.etNumeroPavimentosEdificacao);
	    areaConstUnid = (EditText)findViewById(R.id.etAreaConstUnidade);
	   // cadastrador = (EditText)findViewById(R.id.etCadastrador);
	   // dataCadastro = (EditText)findViewById(R.id.etDataCadastro);
	    observacoes = (EditText)findViewById(R.id.etObservacao);
	    nrGaragem = (EditText)findViewById(R.id.etNuGaragem);
	    
	    //CAMPOS INVISIVEIS
	    id_bairro = (EditText)findViewById(R.id.etIdBairro);
	    cd_bairro = (EditText)findViewById(R.id.etCdBairro);
	    id_logra = (EditText)findViewById(R.id.etIdLogra);
	    id_logra_1 = (EditText)findViewById(R.id.etIdLogra1);
	    id_logra_2 = (EditText)findViewById(R.id.etIdLogra2);
	    id_logra_3 = (EditText)findViewById(R.id.etIdLogra3);
	    id_logra_4 = (EditText)findViewById(R.id.etIdLogra4);
	    src_img = (EditText)findViewById(R.id.etSrcImg);
	    controle_warning = (EditText)findViewById(R.id.etControleWarning);
	    valida = (EditText)findViewById(R.id.etValida);
        nu_insc_imbl_ant = (EditText) findViewById(R.id.etNu_insc_imbl_ant);  
	    etO = (EditText)findViewById(R.id.etO);
	    confirma_salva = "false";
	    altera = "";
	    
	    //spinners
	    
	    spMurado= (Spinner)findViewById(R.id.spMurado);
	    spMurado.setFocusableInTouchMode(true);
	    spMurado.requestFocus();
	    
	    spPatrimonio = (Spinner)findViewById(R.id.spPatrimonio);
	    spPatrimonio.setFocusableInTouchMode(true);
	    spPatrimonio.requestFocus();
	    
	    spMarinha = (Spinner)findViewById(R.id.spMarinha);
	    spMarinha.setFocusableInTouchMode(true);
	    spMarinha.requestFocus();
	    
	    spArvore = (Spinner)findViewById(R.id.spArvore);
	    spArvore.setFocusableInTouchMode(true);
	    spArvore.requestFocus();
	    
	    spElevador = (Spinner)findViewById(R.id.spElevador);
	    spElevador.setFocusableInTouchMode(true);
	    spElevador.requestFocus();
	    
	    spRedeAgua = (Spinner)findViewById(R.id.spRedeAgua);
	    spRedeAgua.setFocusableInTouchMode(true);
	    spRedeAgua.requestFocus();
	    
	    spEsgoto = (Spinner)findViewById(R.id.spEsgoto);
	    spEsgoto.setFocusableInTouchMode(true);
	    spEsgoto.requestFocus();
	    
	    spEletricidade = (Spinner)findViewById(R.id.spEletricidade);
	    spEletricidade.setFocusableInTouchMode(true);
	    spEletricidade.requestFocus();
	    
	    spUtilizacao = (Spinner)findViewById(R.id.spUtilizacao);
	    spUtilizacao.setFocusableInTouchMode(true);
	    spUtilizacao.requestFocus();
	    
	    spPasseio= (Spinner)findViewById(R.id.spPasseio);
	    spPasseio.setFocusableInTouchMode(true);
	    spPasseio.requestFocus();
	    
	    spIsentoIptu = (Spinner)findViewById(R.id.spIsentoIptu);
	    spIsentoIptu.setFocusableInTouchMode(true);
	    spIsentoIptu.requestFocus();
	    
	    spIsentoTsu = (Spinner)findViewById(R.id.spIsentoTsu);
	    spIsentoTsu.setFocusableInTouchMode(true);
	    spIsentoTsu.requestFocus();
	    
	    spSitQuadra = (Spinner)findViewById(R.id.spSitQuadra);
	    spSitQuadra.setFocusableInTouchMode(true);
	    spSitQuadra.requestFocus();
	    
	    spTopografia = (Spinner)findViewById(R.id.spTopografia);
	    spTopografia.setFocusableInTouchMode(true);
	    spTopografia.requestFocus();
	    
	    spPedologia = (Spinner)findViewById(R.id.spPedologia);
	    spPedologia.setFocusableInTouchMode(true);
	    spPedologia.requestFocus();
	    
	    spTipo = (Spinner)findViewById(R.id.spTipo);
	    spTipo.setFocusableInTouchMode(true);
	    spTipo.requestFocus();
	    
	    spAlinhamento = (Spinner)findViewById(R.id.spAlinhamento);
	    spAlinhamento.setFocusableInTouchMode(true);
	    spAlinhamento.requestFocus();
	    
	    spLocacao = (Spinner)findViewById(R.id.spLocacao);
	    spLocacao.setFocusableInTouchMode(true);
	    spLocacao.requestFocus();
	    
	    spSituacao = (Spinner)findViewById(R.id.spSituacao);
	    spSituacao.setFocusableInTouchMode(true);
	    spSituacao.requestFocus();
	    
	    spTp_ocupacao = (Spinner)findViewById(R.id.spTp_ocupacao);
	    spTp_ocupacao.setFocusableInTouchMode(true);
	    spTp_ocupacao.requestFocus();
	    
	    spEstrutura = (Spinner)findViewById(R.id.spEstrutura); 
	    spEstrutura.setFocusableInTouchMode(true);
	    spEstrutura.requestFocus();
	    
	    spCobertura = (Spinner)findViewById(R.id.spCobertura);
	    spCobertura.setFocusableInTouchMode(true);
	    spCobertura.requestFocus();
	    
	    spParedes = (Spinner)findViewById(R.id.spParedes);
	    spParedes.setFocusableInTouchMode(true);
	    spParedes.requestFocus();
	    
	    spVedacoesEsquadrias = (Spinner)findViewById(R.id.spVedacoesEsquadrias);
	    spVedacoesEsquadrias.setFocusableInTouchMode(true);
	    spVedacoesEsquadrias.requestFocus();
	    
	    spRevExterno = (Spinner)findViewById(R.id.spRevExterno);
	    spRevExterno.setFocusableInTouchMode(true);
	    spRevExterno.requestFocus();
	    
	    spPadraoConstrucao = (Spinner)findViewById(R.id.spPadraoConstrucao);
	    spPadraoConstrucao.setFocusableInTouchMode(true);
	    spPadraoConstrucao.requestFocus();
	    
	    spOcupacao = (Spinner)findViewById(R.id.spOcupacao);
	    spOcupacao.setFocusableInTouchMode(true);
	    spOcupacao.requestFocus();
	    
	    //checkbox
	   cbAltColetiva = (CheckBox)findViewById(R.id.altColetiva);
	    cbEndCorrespondencia = (CheckBox)findViewById(R.id.cbEndCorrespondencia);
	    cbClimatizacao = (CheckBox)findViewById(R.id.cbClimatizacao);
	    cbEquipSeguranca = (CheckBox) findViewById(R.id.cbEquipSeguranca);
	    cbSalaFestas = (CheckBox)findViewById(R.id.cbSalaoFestas);
	    cbChurrasqueira = (CheckBox)findViewById(R.id.cbChurrasqueira);
	    cbSauna = (CheckBox)findViewById(R.id.cbSauna);
	    cbPiscina = (CheckBox)findViewById(R.id.cbPiscina);
	    cbLareira = (CheckBox)findViewById(R.id.cbLareira);
	    cbSalaoJogos  = (CheckBox)findViewById(R.id.cbSalaoJogos);
	    cbsalaGinastica = (CheckBox)findViewById(R.id.cbSalaGinastica);
	    cbAquecedoresGas = (CheckBox)findViewById(R.id.cbAquecedorGas);
	    cbElevServico = (CheckBox)findViewById(R.id.cbElevServico);
	    cbElevadorSocial = (CheckBox)findViewById(R.id.cbElevSocial);
	    
	    
	}
	
	private void preencheFormInsert(){
	
		nu_insc_imbl.addTextChangedListener(Mascaras.insert("#######.####.###", nu_insc_imbl));
		
		//CAMPOS INVISIVEIS
	     id_logra.setText("");
	   
	     cd_bairro.setText("");
	    
	     id_bairro.setText("");
	
	     id_logra_1.setText("");
	    
	     id_logra_2.setText("");
	    
	     id_logra_3.setText("");
	    
	     id_logra_4.setText("");
	    
	    src_img.setText("");
      
	    controle_warning.setText("false");
	    
	    
	    
	    valida.setText("true");
	    
	    etO.setText("5");
	    
	    nu_insc_imbl_ant.setText("");
	
	    valida.setText("true");
	
	    
		
	}
	
	private void preencheForm(String inscricao,String cpf) {
		try {
	        objConexao = new HttpConection();
			objConexao.setDados("o", "3");
			inscricao = inscricao.replace(".","");
			inscricao = inscricao.replace("-","");
			objConexao.setDados("inscricao", inscricao);
			objConexao.setDados("cpf",cpf);
			JSONObject retorno = objConexao.envia();
			JSONArray resultados = retorno.getJSONArray("results");
			
			etO.setText("4");
		
			for (int i = 0; i < resultados.length(); ++i) {
			    JSONObject rec = resultados.getJSONObject(i);
			
			    //LOCALIZACAO DO IMOVEL
			    
			    nu_insc_imbl.addTextChangedListener(Mascaras.insert("#######.####.###-###",nu_insc_imbl));
			    
			    nu_insc_imbl.setText( rec.getString("nu_insc_imbl"));
			    inscricaoAntiga = rec.getString("nu_insc_imbl");
			    
                nu_insc_imbl_ant.setText( rec.getString("nu_insc_imbl_ant"));
                
			    imovelEndereco.setText(rec.getString("logradouro"));
			    
			 /*   if (rec.getString("atributo_logradouro").trim().equalsIgnoreCase("")){
			    	imovelEndereco.setText(rec.getString("tipo_logradouro")+" "+rec.getString("logradouro"));
			    }else{			    	
			    	imovelEndereco.setText(rec.getString("tipo_logradouro")+" "+rec.getString("atributo_logradouro")+" "+rec.getString("logradouro"));
			    }*/
			            
                numero.setText(rec.getString("numero"));
			    
			    cdLogr.setText(rec.getString("cd_logr"));
			    
			    secao.setText(rec.getString("secao"));
			    
			    complemento.setText(rec.getString("complemento"));
			    
			    bairro.setText(rec.getString("bairro"));
			    
			    loteamento.setText(rec.getString("nu_lotm"));
			    
			    quadra.setText(rec.getString("quadra"));
			    
			    lote.setText(rec.getString("lote"));
			    
			    apto.setText(rec.getString("nu_apto_garg"));
			    
			    bloco.setText(rec.getString("nm_bloc"));
			    
			    edificio.setText(rec.getString("nm_edfc"));
			    
			    if(rec.getString("in_endr_corr").equalsIgnoreCase("S")){
			    	cbEndCorrespondencia.setChecked(true);
			    }
			    
			    
			    //DADOS DO PROPRIETARIO
			    pessoaNome.setText(rec.getString("pessoaNome"));			  
			    
			    pessoaCpf.setText(rec.getString("pessoaCpf")); 
			    
			    if (pessoaCpf.getText().toString().trim().equalsIgnoreCase("")){
			    	
			    	preencherDadosPessoaBD(cpf);
			    	
			    }
			    
			    
			    pessoaCd_logr.setText(rec.getString("cd_logrPessoa"));
			    
			    pessoaEndereco.setText(rec.getString("pessoaLogradouro")+", Nº"+rec.getString("pessoaNumero"));
			    
			    pessoaComplemento.setText(rec.getString("pessoaComplemento"));
			    
			    pessoaBairro.setText(rec.getString("pessoaBairro"));
			    
			    pessoaTelefone.setText(rec.getString("pessoaTelefone"));
			    
			    pessoaCep.setText(rec.getString("pessoaCep"));
			    
			    pessoaCidade.setText(rec.getString("pessoaCidade"));
			    
			    pessoaUf.setText(rec.getString("pessoaUf"));
			    
			    cod_imobiliaria.setText(rec.getString("cd_imbl"));
			    
				    //INFORMACOES GERAIS SOBRE O IMOVEL
			    
			    for (int i1 = 0; i1 < spOcupacao.getCount(); i1++) {  
		            if (spOcupacao.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("ocupacao"))) {  
		            	spOcupacao.setSelection(i1);  
		            }  
		        }
			    
			    
			   for (int i1 = 0; i1 < spMurado.getCount(); i1++) {  
		            if (spMurado.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("murado"))) {  
		            	spMurado.setSelection(i1);  
		            }  
		        }		
			   for (int i1 = 0; i1 < spPatrimonio.getCount(); i1++) {  
		            if (spPatrimonio.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("patrimonio"))) {  
		            	spPatrimonio.setSelection(i1);  
		            }  
		        }
			   
			   for (int i1 = 0; i1 < spMarinha.getCount(); i1++) {  
		            if (spMarinha.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("marinha"))) {  
		            	spMarinha.setSelection(i1);  
		            }  
		        }
			   
			    for (int i1 = 0; i1 < spUtilizacao.getCount(); i1++) {  
		            if (spUtilizacao.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("utilizacao"))) {  
		            	spUtilizacao.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spPasseio.getCount(); i1++) {  
		            if (spPasseio.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("passeio"))) {  
		            	spPasseio.setSelection(i1);  
		            }  
		        }
			    
			    for (int i1 = 0; i1 < spArvore.getCount(); i1++) {  
		            if (spArvore.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("arvore"))) {  
		            	spArvore.setSelection(i1);  
		            }  
		        }
			    
			    for (int i1 = 0; i1 < spIsentoIptu.getCount(); i1++) {  
		            if (spIsentoIptu.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("isento_iptu"))) {  
		            	spIsentoIptu.setSelection(i1);  
		            }  
		        }
			    
			    for (int i1 = 0; i1 < spElevador.getCount(); i1++) {  
		            if (spElevador.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("elevador"))) {  
		            	spElevador.setSelection(i1);  
		            }  
		        }
			    
			    for (int i1 = 0; i1 < spRedeAgua.getCount(); i1++) {  
		            if (spRedeAgua.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("rede_agua"))) {  
		            	spRedeAgua.setSelection(i1);  
		            }  
		        }
			    			    
			    for (int i1 = 0; i1 < spEsgoto.getCount(); i1++) {  
		            if (spEsgoto.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("esgoto"))) {  
		            	spEsgoto.setSelection(i1);  
		            }  
		        }
			    
			    for (int i1 = 0; i1 < spEletricidade.getCount(); i1++) {  
		            if (spEletricidade.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("eletricidade"))) {  
		            	spEletricidade.setSelection(i1);  
		            }  
		        }
			    
			    
			    for (int i1 = 0; i1 < spIsentoTsu.getCount(); i1++) {  
		            if (spIsentoTsu.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("isento_tsu"))) {  
		            	spIsentoTsu.setSelection(i1);  
		            }  
		        }
		
			   
			   numPavimentos.setText(rec.getString("numeroPavimentos"));
			    
			   numCelesc.setText(rec.getString("nu_celesc"));
			    
			   numCasan.setText(rec.getString("nu_casan"));
			    
			   cartMatricula.setText(rec.getString("nu_matr_cart"));
			   
			   cartLivro.setText(rec.getString("nu_livr_cart"));
			   
			   cartFolha.setText(rec.getString("nu_folh_cart"));
			    
			   cartCodigo.setText(rec.getString("cd_tabl_cart"));
			   
			   valorVenal.setText(rec.getString("vvenal"));
			   
			   
			    //INFORMACOES SOBRE O IMOVEL
			    areaTerreno.setText(rec.getString("areaTerreno"));
			    
			    areaTotalConst.setText(rec.getString("areaTotalConstruida"));
			    
			    for (int i1 = 0; i1 < spSitQuadra.getCount(); i1++) {  
		            if (spSitQuadra.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("situ_quadra"))) {  
		            	spSitQuadra.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spTopografia.getCount(); i1++) {  
		            if (spTopografia.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("topografia"))) {  
		            	spTopografia.setSelection(i1);  
		            }  
		        }
			    
		     for (int i1 = 0; i1 < spPedologia.getCount(); i1++) {  
		            if (spPedologia.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("pedologia"))) {  
		            	spPedologia.setSelection(i1);  
		            }  
		        }
			    
			    profundidade.setText(rec.getString("profundidade"));
			    
			    testada1.setText(rec.getString("testada1"));
			    
			    cdLogr1.setText(rec.getString("cd_logr"));
			    
			    secao1.setText(rec.getString("secao"));
			    
			    logradouro1.setText(imovelEndereco.getText());
			    
			    testada2.setText(rec.getString("testada2"));
			    
			    cdLogr2.setText(rec.getString("cd_logr2"));
			    
			    secao2.setText(rec.getString("secao2"));
			    
			    logradouro2.setText(rec.getString("logradouro2"));
			    
			    testada3.setText(rec.getString("testada3"));
			    
			    cdLogr3.setText(rec.getString("cd_logr3"));
			    
			    secao3.setText(rec.getString("secao3"));
			    
			    logradouro3.setText(rec.getString("logradouro3"));
			    
			    testada4.setText(rec.getString("testada4"));
			    
			    cdLogr4.setText(rec.getString("cd_logr4"));
			    
			    secao4.setText(rec.getString("secao4"));
			    
			    logradouro4.setText(rec.getString("logradouro4"));
			    
			    //INFORMACOES SOBRE A EDIFICACAO
			    anoConst.setText(rec.getString("ano_construcao"));
			    
			    numPavmEdf.setText(rec.getString("nu_pav_edif"));
			    
			    areaConstUnid.setText(rec.getString("area_const_unidade"));
			    
			    for (int i1 = 0; i1 < spTipo.getCount(); i1++) {  
		            if (spTipo.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("tipo"))) {  
		            	spTipo.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spAlinhamento.getCount(); i1++) {  
		            if (spAlinhamento.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("alinhamento"))) {  
		            	spAlinhamento.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spLocacao.getCount(); i1++) {  
		            if (spLocacao.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("locacao"))) {  
		            	spLocacao.setSelection(i1);  
		            }  
		        }
			   for (int i1 = 0; i1 < spTp_ocupacao.getCount(); i1++) {  
		            if (spTp_ocupacao.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("tp_ocpc"))) {  
		            	spTp_ocupacao.setSelection(i1);  
		            }  
		        }
			   
			   
			   for (int i1 = 0; i1 < spSituacao.getCount(); i1++) {  
		            if (spSituacao.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("situacao"))) {  
		            	spSituacao.setSelection(i1);  
		            }  
		        }
			    
	            for (int i1 = 0; i1 < spEstrutura.getCount(); i1++) {  
		            if (spEstrutura.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("estrutura"))) {  
		            	spEstrutura.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spCobertura.getCount(); i1++) {  
		            if (spCobertura.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("cobertura"))) {  
		            	spCobertura.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spParedes.getCount(); i1++) {  
		            if (spParedes.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("paredes"))) {  
		            	spParedes.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spVedacoesEsquadrias.getCount(); i1++) {  
		            if (spVedacoesEsquadrias.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("vedacoes_esquadrias"))) {  
		            	spVedacoesEsquadrias.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spRevExterno.getCount(); i1++) {  
		            if (spRevExterno.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("rev_externo"))) {  
		            	spRevExterno.setSelection(i1);  
		            }  
		        }
			    for (int i1 = 0; i1 < spPadraoConstrucao.getCount(); i1++) {  
		            if (spPadraoConstrucao.getItemAtPosition(i1).toString().equalsIgnoreCase(rec.getString("padrao_construcao"))) {  
		            	spPadraoConstrucao.setSelection(i1);  
		            }  
		        }
			   
			    afastamentoFrontal.setText(rec.getString("afastamento_frontal"));
			    
			    if(rec.getString("in_clim_ambi").equalsIgnoreCase("S")){
			    	cbClimatizacao.setChecked(true);
			    }
			    
			    if(rec.getString("in_eqpt_segr").equalsIgnoreCase("S")){
			    	cbEquipSeguranca.setChecked(true);
			    }
			    
			    if(rec.getString("in_salo_fest").equalsIgnoreCase("S")){
			    	cbSalaFestas.setChecked(true);
			    }
			    
			    if(rec.getString("in_chur").equalsIgnoreCase("S")){
			    	cbChurrasqueira.setChecked(true);
			    }
			    
			    if(rec.getString("in_saun").equalsIgnoreCase("S")){
			    	cbSauna.setChecked(true);
			    } 
			    
			    if(rec.getString("in_pisc").equalsIgnoreCase("S")){
			    	cbPiscina.setChecked(true);
			    }
			    
			    if(rec.getString("in_lare").equalsIgnoreCase("S")){
			    	cbLareira.setChecked(true);
			    }
			    
			    if(rec.getString("in_sala_jogo").equalsIgnoreCase("S")){
			    	cbSalaoJogos.setChecked(true);
			    }
			    
			    if(rec.getString("in_sala_gina").equalsIgnoreCase("S")){
			    	cbsalaGinastica.setChecked(true);
			    }
			    
			    if(rec.getString("in_aque_gas").equalsIgnoreCase("S")){
			    	cbAquecedoresGas.setChecked(true);
			    }
			    
			    if(rec.getString("in_elev_serv").equalsIgnoreCase("S")){
			    	cbElevServico.setChecked(true);
			    }
			    
			    if(rec.getString("in_elev_soci").equalsIgnoreCase("S")){
			    	cbElevadorSocial.setChecked(true);
			    }
			    
			   nrGaragem.setText(rec.getString("qt_garg"));
			    
			   //cadastrador.setText(rec.getString("cadastrador"));			
			    
			   observacoes.setText(rec.getString("obs"));
			    
			   controle_warning.setText("false");
			    
			   valida.setText("true");
			    
			   
			    

			}
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencheForm (Formulario): "+e.getMessage());
			//Toast.makeText(getBaseContext(), "ERRO FATAL full", Toast.LENGTH_LONG).show();
		}
		
	}
	
	private void preencheFormBD(String inscricao,String cpf) {
		
		etO.setText("4");
		controle_warning.setText("true");
	    valida.setText("true");
		
		try {
				
	       
			BancoFormulario formularioBanco = new BancoFormulario(this);
			BancoLogradouro logradouroBanco = new BancoLogradouro(this);
		       form = formularioBanco.retornaDadosBciBD(inscricao);
			   logradouroForm = logradouroBanco.pesquisaLogradouroBD(form.getCd_logr());
			   
			    //LOCALIZACAO DO IMOVEL
			   
			   nu_insc_imbl.addTextChangedListener(Mascaras.insert("#######.####.###-###", nu_insc_imbl));
			    nu_insc_imbl.setText(form.getNu_insc_imbl());
			    inscricaoAntiga = inscricao;
			    nu_insc_imbl_ant.setText(form.getNu_insc_imbl_ant());
                imovelEndereco.setText(logradouroForm.getLabel());
		        numero.setText(form.getNu_imvl());
			    cdLogr.setText(form.getCd_logr());
			    secao.setText(form.getCd_seca());
			    complemento.setText(form.getDe_comp());
			    bairro.setText(logradouroForm.getNm_barr());
			    loteamento.setText(form.getNu_lotm());
			    quadra.setText(form.getNu_qudr());
			    lote.setText(form.getNu_lote());
			    apto.setText(form.getNu_apto_garg());
			    bloco.setText(form.getNm_bloc());
			    edificio.setText(form.getNm_edfc());
			    
			    if(form.getIn_endr_corr().equalsIgnoreCase("S")){
			    	cbEndCorrespondencia.setChecked(true);
			    }
			    
			    //DADOS DO PROPRIETARIO
			    String cpf_pessoa = "";
			    if (cpf.equalsIgnoreCase("0")){
			    	cpf_pessoa = form.getNu_pess();
			    }else{
			    	cpf_pessoa = cpf;
			    }
			    
			    BancoPessoa pessoaBanco = new BancoPessoa(this);
			    pessoaForm = pessoaBanco.pesquisaPessoaBD(cpf_pessoa);
			    pessoaNome.setText(pessoaForm.getNm_pess());			  
			    pessoaCpf.setText(cpf_pessoa); 
			
		     Logradouro logradouroPessoa = new Logradouro();
		     logradouroPessoa = logradouroBanco.pesquisaLogradouroBD(pessoaForm.getCd_logr());
		     
		       pessoaCd_logr.setText(logradouroPessoa.getCd_logr());
		       pessoaEndereco.setText(logradouroPessoa.getLabel());
			   pessoaComplemento.setText(pessoaForm.getDe_comp());
			   pessoaBairro.setText(logradouroPessoa.getNm_barr());
			   pessoaTelefone.setText(pessoaForm.getNu_telf());			    
			   pessoaCep.setText(logradouroPessoa.getNu_cep_logr());
			   pessoaCidade.setText(logradouroPessoa.getNm_cidd());
			   pessoaUf.setText(logradouroPessoa.getSg_uf());
			   cod_imobiliaria.setText(form.getCd_imbl());
			   
			   
			   
			   
			    
				    //INFORMACOES GERAIS SOBRE O IMOVEL
			    for (int i1 = 0; i1 < opcoesOcupacao.size(); i1++) {  
			         if(opcoesOcupacao.get(i1).getId()==Integer.parseInt(form.getTp_ocpc_lote())){
		            	spOcupacao.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesMurado.size(); i1++) {  
			         if(opcoesMurado.get(i1).getId()==Integer.parseInt(form.getTp_murd())){
		            	spMurado.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesPatrimonio.size(); i1++) {  
			         if(opcoesPatrimonio.get(i1).getId()==Integer.parseInt(form.getTp_patr())){
		            	spPatrimonio.setSelection(i1);  
			            }  
		        }
			
			  		   
			    for (int i1 = 0; i1 < opcoesMarinha.size(); i1++) {  
			         if(opcoesMarinha.get(i1).getId()==Integer.parseInt(form.getTp_terr_marn())){
		            	spMarinha.setSelection(i1);  
			            }  
		        }
			   
			    for (int i1 = 0; i1 < opcoesUtilizacao.size(); i1++) {  
			         if(opcoesUtilizacao.get(i1).getId()==Integer.parseInt(form.getTp_utlz())){
		            	spUtilizacao.setSelection(i1);  
			            }  
		        }
			    
			    
			    for (int i1 = 0; i1 < opcoesPasseio.size(); i1++) {  
			         if(opcoesPasseio.get(i1).getId()==Integer.parseInt(form.getTp_pass())){
		            	spPasseio.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesArvore.size(); i1++) {  
			         if(opcoesArvore.get(i1).getId()==Integer.parseInt(form.getTp_arvr())){
		            	spArvore.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesIsentoIptu.size(); i1++) {  
			         if(opcoesIsentoIptu.get(i1).getId()==Integer.parseInt(form.getTp_isnt_iptu())){
		            	spIsentoIptu.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesElevador.size(); i1++) {  
			         if(opcoesElevador.get(i1).getId()==Integer.parseInt(form.getTp_elvd())){
		            	spElevador.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesRedeAgua.size(); i1++) {  
			         if(opcoesRedeAgua.get(i1).getId() == Integer.parseInt(form.getTp_ligc_agua())){
		            	spRedeAgua.setSelection(i1);  
			            }  
		        }
			    			    
			    for (int i1 = 0; i1 < opcoesEsgoto.size(); i1++) {  
			         if(opcoesEsgoto.get(i1).getId()==Integer.parseInt(form.getTp_esgt())){
		            	spEsgoto.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesEletricidade.size(); i1++) {  
			         if(opcoesEletricidade.get(i1).getId()==Integer.parseInt(form.getTp_eltr())){
		            	spEletricidade.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesIsentoTsu.size(); i1++) {  
			         if(opcoesIsentoTsu.get(i1).getId()==Integer.parseInt(form.getTp_isnt_tsu())){
		            	spIsentoTsu.setSelection(i1);  
			            }  
		        }
			   
			    numPavimentos.setText(form.getNu_pavm());
			    
			    numCelesc.setText(form.getNu_celesc());
			    
			    numCasan.setText(form.getNu_casan());
			    
			    cartMatricula.setText(form.getNu_matr_cart());
			    
			    cartLivro.setText(form.getNu_livr_cart());
			    
			    cartCodigo.setText(form.getCd_tabl_cart());
			    
			    valorVenal.setText(form.getVvenal());
			    
			    //INFORMACOES SOBRE O IMOVEL
			    areaTerreno.setText(form.getQt_area_lote());
			    areaTotalConst.setText(form.getQt_area_cons());
			    
			  
			    for (int i1 = 0; i1 < opcoess.size(); i1++) {  
			         if(opcoess.get(i1).getId()==Integer.parseInt(form.getTp_sitc_qudr())){
		            	spSitQuadra.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesTopologia.size(); i1++) {  
			         if(opcoesTopologia.get(i1).getId()==Integer.parseInt(form.getTp_topg())){
		            	spTopografia.setSelection(i1);  
			            }  
		        }
			    
			    for (int i1 = 0; i1 < opcoesPedologia.size(); i1++) {  
			         if(opcoesPedologia.get(i1).getId()==Integer.parseInt(form.getTp_pedl())){
		            	spPedologia.setSelection(i1);  
			            }  
		        }
			    
			    profundidade.setText(form.getQt_prfn());
			    testada1.setText(form.getNu_test_prnc());
			    cdLogr1.setText(form.getCd_logr());
			    secao1.setText(form.getCd_seca());
			    logradouro1.setText(logradouroForm.getLabel());
			    testada2.setText(form.getNu_tes2());
			    cdLogr2.setText(form.getCd_logr_tes2());
			    secao2.setText(form.getCd_seca_tes2());
	
			    logradouroForm = logradouroBanco.pesquisaLogradouroBD(form.getCd_logr_tes2());
			    logradouro2.setText(logradouroForm.getLabel());
			    testada3.setText(form.getNu_tes3());
			    cdLogr3.setText(form.getCd_logr_tes3());
			    secao3.setText(form.getCd_seca_tes3());
			    
			    logradouroForm = logradouroBanco.pesquisaLogradouroBD(form.getCd_logr_tes3());
			    logradouro3.setText(logradouroForm.getLabel());
			    testada4.setText(form.getNu_tes4());
			    cdLogr4.setText(form.getCd_logr_tes4());
			    secao4.setText(form.getCd_seca_tes4());
			    
			    logradouroForm = logradouroBanco.pesquisaLogradouroBD(form.getCd_logr_tes4());
			    logradouro4.setText(logradouroForm.getLabel());
			    
			    //INFORMACOES SOBRE A EDIFICACAO
			   anoConst.setText(form.getAa_cons());
			   numPavmEdf.setText(form.getNu_pav_edif());
			   areaConstUnid.setText(form.getQt_area_undd());
			    
			   
			   for (int i1 = 0; i1 < opcoesTipo.size(); i1++) {  
			         if(opcoesTipo.get(i1).getId()==Integer.parseInt(form.getTp_edfc())){
			        	 spTipo.setSelection(i1);  
			            }  
		        }
			   
			   for (int i1 = 0; i1 < opcoesAlinhamento.size(); i1++) {  
			         if(opcoesAlinhamento.get(i1).getId()==Integer.parseInt(form.getTp_alnh())){
			        	 spAlinhamento.setSelection(i1);  
			            }  
		        }
			   
			   for (int i1 = 0; i1 < opcoesLocacao.size(); i1++) {  
			         if(opcoesLocacao.get(i1).getId()==Integer.parseInt(form.getTp_locc())){
			        	 spLocacao.setSelection(i1);  
			            }  
		        }
			   
			   for (int i1 = 0; i1 < opcoesSituacao.size(); i1++) {  
			         if(opcoesSituacao.get(i1).getId()==Integer.parseInt(form.getTp_sitc())){
			        	 spSituacao.setSelection(i1);  
			            }  
		        }
			   
			   
			   for (int i1 = 0; i1 < opcoesTpOcupacao.size(); i1++) {  
			         if(opcoesTpOcupacao.get(i1).getId()==Integer.parseInt(form.getTp_ocpc())){
			        	 spTp_ocupacao.setSelection(i1);  
			            }  
		        }
			   
			   
			   for (int i1 = 0; i1 < opcoesEstrutura.size(); i1++) {  
			         if(opcoesEstrutura.get(i1).getId()==Integer.parseInt(form.getTp_estr())){
			        	 spEstrutura.setSelection(i1);  
			            }  
		        }
			  
			   for (int i1 = 0; i1 < opcoesCobertura.size(); i1++) {  
			         if(opcoesCobertura.get(i1).getId()==Integer.parseInt(form.getTp_cobr())){
			        	 spCobertura.setSelection(i1);  
			            }  
		        }
			  
			   for (int i1 = 0; i1 < opcoesParedes.size(); i1++) {  
			         if(opcoesParedes.get(i1).getId()==Integer.parseInt(form.getTp_pard())){
			        	 spParedes.setSelection(i1);  
			            }  
		        }
			   
			   for (int i1 = 0; i1 < opcoesVedacoesEsquadrias.size(); i1++) {  
			         if(opcoesVedacoesEsquadrias.get(i1).getId()==Integer.parseInt(form.getTp_vedc())){
			        	 spVedacoesEsquadrias.setSelection(i1);  
			            }  
		        }
			   
			   for (int i1 = 0; i1 < opcoesRevExterno.size(); i1++) {  
			         if(opcoesRevExterno.get(i1).getId()==Integer.parseInt(form.getTp_revs_extr())){
			        	 spRevExterno.setSelection(i1);  
			            }  
		        }
			   
			   for (int i1 = 0; i1 < opcoesPadraoConstrucao.size(); i1++) {  
			         if(opcoesPadraoConstrucao.get(i1).getId()==Integer.parseInt(form.getTp_padr_cons())){
			        	 spPadraoConstrucao.setSelection(i1);  
			            }  
		        }
						   
			    afastamentoFrontal.setText(form.getQt_afst_frnt());
			    
			    if(form.getIn_clim_ambi().equalsIgnoreCase("S")){
			    	cbClimatizacao.setChecked(true);
			    }
			    
			    if(form.getIn_eqpt_segr().equalsIgnoreCase("S")){
			    	cbEquipSeguranca.setChecked(true);
			    }
			    
			    if(form.getIn_salo_fest().equalsIgnoreCase("S")){
			    	cbSalaFestas.setChecked(true);
			    }
			    
			    if(form.getIn_chur().equalsIgnoreCase("S")){
			    	cbChurrasqueira.setChecked(true);
			    }
			    
			    if(form.getIn_saun().equalsIgnoreCase("S")){
			    	cbSauna.setChecked(true);
			    }
			    
			    if(form.getIn_pisc().equalsIgnoreCase("S")){
			    	cbPiscina.setChecked(true);
			    }
			    
			    if(form.getIn_lare().equalsIgnoreCase("S")){
			    	cbLareira.setChecked(true);
			    }
			    
			    if(form.getIn_sala_jogo().equalsIgnoreCase("S")){
			    	cbSalaoJogos.setChecked(true);
			    }
			    
			    if(form.getIn_sala_gina().equalsIgnoreCase("S")){
			    	cbsalaGinastica.setChecked(true);
			    }
			    
			    if(form.getIn_aque_gas().equalsIgnoreCase("S")){
			    	cbAquecedoresGas.setChecked(true);
			    }
			    
			    if(form.getIn_elev_serv().equalsIgnoreCase("S")){
			    	cbElevServico.setChecked(true);
			    }
			    
			    if(form.getIn_elev_soci().equalsIgnoreCase("S")){
			    	cbElevadorSocial.setChecked(true);
			    }
			    
			    nrGaragem.setText(form.getQt_garg());
			    
			   // cadastrador.setText(form.getCadastrador());			
			   // dataCadastro.setText(form.getData_cadastro());
			    observacoes.setText(form.getObs_geo());
			 
			    
			    
			    
			
			    
			    
	
		
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencheFormBD (Formulario): "+e.getMessage());
			//Toast.makeText(getBaseContext(), "ERRO FATAL full", Toast.LENGTH_LONG).show();
		}
		
	}
	
	public void procuraFotosInscricaoNoSdCard(String inscricao){
		
		
		String ff = Environment.getExternalStorageDirectory() + "/VgFiscalFotos";
		File dir = new File( ff );  
		File[] fileNames = dir.listFiles();
		
		arrayNomeArquivo.clear();
		for(int i=0; i< fileNames.length; i++){
		
			String[] tmp2 = fileNames[i].getName().split("_");
			if(tmp2[0].trim().equalsIgnoreCase(inscricao.trim())){
				arrayNomeArquivo.add(fileNames[i].getAbsolutePath());
		
			}
		}
		if(arrayNomeArquivo.size() > 0){
			TextView numFotos = (TextView)findViewById(R.id.txtNumeroFotos);
			numFotos.setText(arrayNomeArquivo.size()+"");
		}
	}
	
	public void listViewEdif(String json){
		try {
			list = new ArrayList<MyData>();
			
			JSONObject jsonResponse;
			jsonResponse = new JSONObject("{\"results_edif\":"+json+"}");
			JSONArray resultados2 = jsonResponse.getJSONArray("results_edif");
			
			
			int conta = 2;
			for (int j = 0; j < resultados2.length(); ++j) {
			    JSONObject rec_edif = resultados2.getJSONObject(j);	
			    String tipo = null, area;
			    for(int y=0; y < opcoesTipo.size(); y++){
			    	if(opcoesTipo.get(y).getId() == Integer.parseInt(rec_edif.getString("tipo_codigo"))){
			    		tipo = opcoesTipo.get(y).getNome();
			    
			    	}
			    }
			    area = rec_edif.getString("area_const_unidade");
			    String titulo = "Edificação nº "+(conta);
				list.add(new MyData(titulo, "Tipo: "+tipo, "Área Construída da Unidade: "+area));
			    
			    
			    ArrayList<String> arrayTmp = new ArrayList<String>();
			    arrayTmp.add(rec_edif.getString("val"));
			    arrayTmp.add(rec_edif.getString("id_dados_edificacao"));
			    arrayTmp.add(rec_edif.getString("ano_construcao"));
			    arrayTmp.add(rec_edif.getString("nu_pav_edif"));
			    arrayTmp.add(rec_edif.getString("area_const_unidade"));
			    arrayTmp.add(tipo);
			    
			    
			    String alinhamento = null;
			    for(int y=0; y < opcoesAlinhamento.size(); y++){
			    	if(opcoesAlinhamento.get(y).getId() == Integer.parseInt(rec_edif.getString("ali_codigo"))){
			    		alinhamento = opcoesAlinhamento.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(alinhamento);
			   
			    String locacao = null;
			    for(int y=0; y < opcoesLocacao.size(); y++){
			    	if(opcoesLocacao.get(y).getId() == Integer.parseInt(rec_edif.getString("locacao"))){
			    		locacao = opcoesLocacao.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(locacao);
			    
			    String situacao = null;
			    for(int y=0; y < opcoesSituacao.size(); y++){
			    	if(opcoesSituacao.get(y).getId() == Integer.parseInt(rec_edif.getString("ste_codigo"))){
			    		situacao = opcoesSituacao.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(situacao);
			    
			    String Estrutura = null;
			    for(int y=0; y < opcoesEstrutura.size(); y++){
			    	if(opcoesEstrutura.get(y).getId() == Integer.parseInt(rec_edif.getString("est_codigo"))){
			    		Estrutura = opcoesEstrutura.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(Estrutura);
			    
			    String Cobertura = null;
			    for(int y=0; y < opcoesCobertura.size(); y++){
			    	if(opcoesCobertura.get(y).getId() == Integer.parseInt(rec_edif.getString("cob_codigo"))){
			    		Cobertura = opcoesCobertura.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(Cobertura);
			    
			    String Paredes = null;
			    for(int y=0; y < opcoesParedes.size(); y++){
			    	if(opcoesParedes.get(y).getId() == Integer.parseInt(rec_edif.getString("par_codigo"))){
			    		Paredes = opcoesParedes.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(Paredes);
			    
			    String VedacoesEsquadrias = null;
			    for(int y=0; y < opcoesVedacoesEsquadrias.size(); y++){
			    	if(opcoesVedacoesEsquadrias.get(y).getId() == Integer.parseInt(rec_edif.getString("vedacoesEsquadrias"))){
			    		VedacoesEsquadrias = opcoesVedacoesEsquadrias.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(VedacoesEsquadrias);
			    
			    String RevExterno = null;
			    for(int y=0; y < opcoesRevExterno.size(); y++){
			    	if(opcoesRevExterno.get(y).getId() == Integer.parseInt(rec_edif.getString("rev_codigo"))){
			    		RevExterno = opcoesRevExterno.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(RevExterno);
			    
			    String padraoConstrucao = null;
			    for(int y=0; y < opcoesPadraoConstrucao.size(); y++){
			    	if(opcoesPadraoConstrucao.get(y).getId() == Integer.parseInt(rec_edif.getString("padraoConstrucao"))){
			    		padraoConstrucao = opcoesPadraoConstrucao.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(padraoConstrucao);
			    
			    String ocupacaoLote = null;
			    for(int y=0; y < opcoesOcupacao.size(); y++){
			    	if(opcoesOcupacao.get(y).getId() == Integer.parseInt(rec_edif.getString("ocupacaoLote"))){
			    		ocupacaoLote = opcoesOcupacao.get(y).getNome();
			    	}
			    }
			    arrayTmp.add(ocupacaoLote);
			    
			    arrayTmp.add(rec_edif.getString("nro_alvara_construcao"));
			    arrayTmp.add(rec_edif.getString("apartamento_nro"));
			    arrayTmp.add(rec_edif.getString("bloco_nro"));
			    
			    arrayTmp.add(rec_edif.getString("mslink_edif"));
			    arrayJsonEdif.add(arrayTmp);
			    conta++;
			    
			}
			
			// Agora vou popular minha lista
			this.getListView().setAdapter(new MyListAdapter(this, list, "2"));
	        
	       
	        this.getListView().setFastScrollEnabled(true);
	        Integer tamanho = 80 * resultados2.length();
	        
			this.getListView().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,tamanho));
	        
	        
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método listViewEdif (Formulario): "+e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		positionEditEdificacao = position;
		adicionaEdificacaoExtra(true);
	}
	
	private class threadSalvar extends AsyncTask <String, Long, Void> {
		
	private final ProgressDialog dialog = new ProgressDialog(Formulario.this);
		
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Foi cancelado a ação de salvar!", Toast.LENGTH_LONG).show();
						thread_execSalvar.cancel(true);
					
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
	
		protected Void doInBackground(final String... args) {
			try {
				salvarArquivo();
				
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadSalvar (Formulario): "+e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Long... value) {
		super.onProgressUpdate(value);
	
		}
	
		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			 retornoSalvarForm();
		}
	}
	
	public void retornoSalvarForm(){
		
		
		thread_execSalvar.cancel(true);
		
		
		switch (numero_caseSalvar) {
		case 0:
			Toast.makeText(getBaseContext(), msgSalvar, Toast.LENGTH_LONG).show();
			break;
		case 1:
			Toast.makeText(getBaseContext(), msgSalvar, Toast.LENGTH_LONG).show();
			finish();
			break;
		case 2:
			Toast.makeText(getBaseContext(), msgSalvar, Toast.LENGTH_LONG).show();
			break;
		case 3:
			Toast.makeText(getBaseContext(), msgSalvar, Toast.LENGTH_LONG).show();
			break;
		case 4:
			//mostraOsErros(retornoSalvar);
			break;
		case 5:
			Toast.makeText(getBaseContext(), msgSalvar, Toast.LENGTH_LONG).show();
			break;
		case 6:
			Toast.makeText(getBaseContext(), msgSalvar, Toast.LENGTH_LONG).show();
			break;

	
		}
		
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btnImgSairForm:
			    super.finish();
			    Intent winMain = new Intent(Formulario.this, Main.class);
				startActivity(winMain);
				
				
			break;

		case R.id.btnSalvarFormulario:			
			
			if(cbAltColetiva.isChecked()){		
							
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setMessage("Deseja realizar uma alteração Coletiva? ")
					    	.setCancelable(true)
					    	.setPositiveButton("sim", new DialogInterface.OnClickListener() {
					    		public void onClick(DialogInterface dialog, int id) {
					    			if(validaFormulario()){
										
										thread_execSalvar = new threadSalvar().execute();
										
										}
					   	       	}
					    		
					       }).setNegativeButton("não", new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					        	   
					        	
					           }
					       });
						AlertDialog alert = builder.create();
						alert.show();
						
									
				}else{
					
					
					if(validaFormulario()){
						
						thread_execSalvar = new threadSalvar().execute();
						
						}
					
					
				}						
											
		
			break;
		
		case R.id.btnImgPesquisarPessoa:
			Intent winPesquisaPadraoPessoa = new Intent(Formulario.this, PesquisaPadrao.class);
			winPesquisaPadraoPessoa.putExtra("tp_pesquisa", 1);
			winPesquisaPadraoPessoa.putExtra("inscricao",nu_insc_imbl.getText().toString().trim());
			winPesquisaPadraoPessoa.putExtra("cpf",pessoaCpf.getText().toString().trim());
			startActivity(winPesquisaPadraoPessoa);
			finish();
			
			break;
		case R.id.btnImgPesquisarLogr:
			Intent winPesquisaPadraoLogr = new Intent(Formulario.this, PesquisaPadrao.class);
			winPesquisaPadraoLogr.putExtra("tp_pesquisa", 2);
			startActivityForResult(winPesquisaPadraoLogr, 1);
			break;
		case R.id.btnImgPesquisarLogr2:
			Intent winPesquisaPadraoLogr2 = new Intent(Formulario.this, PesquisaPadrao.class);
			winPesquisaPadraoLogr2.putExtra("tp_pesquisa", 7);
			startActivityForResult(winPesquisaPadraoLogr2, 1);
			break;
		case R.id.btnImgPesquisarLogr3:
			Intent winPesquisaPadraoLogr3 = new Intent(Formulario.this, PesquisaPadrao.class);
			winPesquisaPadraoLogr3.putExtra("tp_pesquisa", 8);
			startActivityForResult(winPesquisaPadraoLogr3, 1);
			break;
		case R.id.btnImgPesquisarLogr4:
			Intent winPesquisaPadraoLogr4 = new Intent(Formulario.this, PesquisaPadrao.class);
			winPesquisaPadraoLogr4.putExtra("tp_pesquisa", 9);
			startActivityForResult(winPesquisaPadraoLogr4, 1);
			break;
		case R.id.btnImgPesquisarBairro:
			Intent winPesquisaPadraoBairro = new Intent(Formulario.this, PesquisaPadrao.class);
			winPesquisaPadraoBairro.putExtra("tp_pesquisa", 3);
			startActivityForResult(winPesquisaPadraoBairro, 1);
			break;
		case R.id.btnExcluirBci:
			excluirBci();
		
			break;
		case R.id.imageTirarFoto:
			entrarTirarFoto();
		
			break;
		case R.id.imageErrosValidacao:
			if(TemConexao()){	
			
				if(array_validacoes.size() > 0){
					Intent winErros = new Intent(Formulario.this, Erros.class);
					winErros.putStringArrayListExtra("array_validacoes", array_validacoes);
					winErros.putExtra("colocaBotao", colocaBotao);
					winErros.putExtra("colocaBotao2", colocaBotao2);
					winErros.putExtra("tipo","formulario");
					
					startActivityForResult(winErros, 1);
				}else{
					if(validaFormulario()){	
							thread_execSalvar = new threadSalvar().execute();
					      }
				    }
				
				//Toast.makeText(getBaseContext(), "Não existe Erro!", Toast.LENGTH_LONG).show();
			}else{
				 Toast.makeText(getBaseContext(), "Consulta de Erro somente com Conexão!", Toast.LENGTH_LONG).show();	
						
					}
			
			
			break;
		case R.id.btnImgEditPessoa:
			chamaEditPessoa();
			break;
	
		default:
			break;
		}
		
	}
	
	private class threadExcluirBci extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(Formulario.this);
	
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Foi cancelado a ação de excluir!", Toast.LENGTH_LONG).show();
						thread_execExcluirBci.cancel(true);
	
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
		protected Void doInBackground(final String... args) {
			try {
				excluirBciBD();
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadExcluirBci (Formulario): "+e.getMessage());
			}
			return null;
		}
	
		@Override
		protected void onProgressUpdate(Long... value) {
		super.onProgressUpdate(value);
		}
	
		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			 
			 retornoExcluirBciForm();
			 
		}
	}//AsyncTask
	
	private class threadExcluirBciBD extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(Formulario.this);
	
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Foi cancelado a ação de excluir!", Toast.LENGTH_LONG).show();
						thread_execExcluirBci.cancel(true);
	
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
	
		protected Void doInBackground(final String... args) {
			try {
				excluirFormularioBD();
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadExcluirBciBD (Formulario): "+e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Long... value) {
		super.onProgressUpdate(value);
		
		}
		
		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}
			 
			 retornoExcluirBciForm();
			 
		}
	}//AsyncTask
	
	public void retornoExcluirBciForm(){
		
		if (numero_caseExcluirBci == 1){
			Toast.makeText(getBaseContext(), msgExcluirBci, Toast.LENGTH_LONG).show();
			finish();
		}else{
			if(numero_caseExcluirBci == 0){
				Toast.makeText(getBaseContext(), msgExcluirBci, Toast.LENGTH_LONG).show();
			}else{
				if(numero_caseExcluirBci == 10){
					finish();
				}else{
					if(numero_caseExcluirBci == 11){
						Toast.makeText(getBaseContext(), msgExcluirBci, Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(getBaseContext(), msgExcluirBci, Toast.LENGTH_LONG).show();
					}
				}
			}
   		}
		thread_execExcluirBci.cancel(true);
		thread_execExcluirBci = null;
		Intent winPrincipal3 = new Intent(this, MenuPrincipal.class) ;
		startActivity(winPrincipal3);
       
		
	}
	
	private void chamaEditPessoa(){
		if(!pessoaCpf.getText().toString().trim().equals("")){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Você tem certeza que deseja alterar os dados desta Pessoa?")
		    	.setCancelable(false)
		    	.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int id) {
		    			thread_execEditPessoa = new threadEditPessoa().execute();
		
		    		}
		       }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               	dialog.cancel();
		           }
		       });
			AlertDialog alert = builder.create();
			alert.show();
		}else{
			colocaAlert("Primeiro selecione a pessoa que você deseja alterar!");
		}
	}
	
	private class threadEditPessoa extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(Formulario.this);
		
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Foi cancelado a ação de editar a Pessoa!", Toast.LENGTH_LONG).show();
						thread_execEditPessoa.cancel(true);
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
	
		protected Void doInBackground(final String... args) {
			try {
				Intent winAddPessoa = new Intent(Formulario.this, CadastroPessoa.class);
    			winAddPessoa.putExtra("edicao", "true");
    			winAddPessoa.putExtra("cpf", pessoaCpf.getText().toString().trim());
    			winAddPessoa.putExtra("inscricao",nu_insc_imbl.getText().toString().trim());
    			startActivityForResult(winAddPessoa, 1);
    			//finish();
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadExcluirBciBD (Formulario): "+e.getMessage());
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Long... value) {
		super.onProgressUpdate(value);
	
		}
	
		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}

			thread_execEditPessoa.cancel(true);
			thread_execEditPessoa = null;
		}
	}//AsyncTask
	
	protected void entrarTirarFoto(){
	
		String insc = nu_insc_imbl.getText().toString().trim();
		String inscricao = insc.replaceAll("[.]","").replaceAll("[-]","");
		
		
		if(inscricao.trim().equals("") || inscricao.length() != 17){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Você precisa preencher a inscrição imobiliária corretamente!")
		    	.setCancelable(false)
		    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int id) {
		    			dialog.cancel();
			       	}
		       });
			AlertDialog alert = builder.create();
			alert.show();
		}else{
			
			
			procuraFotosInscricaoNoSdCard(inscricao);
	
				winFoto = new Intent(Formulario.this, Foto.class);
				winFoto.putExtra("inscricao", inscricao);
				winFoto.putStringArrayListExtra("arrayNomeArquivo", arrayNomeArquivo);
				thread_execTirarFoto = new threadTirarFoto().execute();
	
		}
		
		
	}
	
	private class threadTirarFoto extends AsyncTask <String, Long, Void> {
		private final ProgressDialog dialog = new ProgressDialog(Formulario.this);
	
		protected void onPreExecute() {
			this.dialog.setOnKeyListener(new OnKeyListener() {
				
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK){
						Toast.makeText(getBaseContext(), "Foi cancelado a ação de tirar foto!", Toast.LENGTH_LONG).show();
						thread_execTirarFoto.cancel(true);
					}
					return false;
				}
			});
			this.dialog.setTitle("Aguarde");
			this.dialog.setMessage("Carregando...");
			this.dialog.show();
		}
	
		protected Void doInBackground(final String... args) {
			try {
			
				startActivityForResult(winFoto, 1);
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método threadTirarFoto (Formulario): "+e.getMessage());
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Long... value) {
		super.onProgressUpdate(value);
		
		}
		
		protected void onPostExecute(final Void unused) {
			if (this.dialog.isShowing()) {
				this.dialog.dismiss();
			}

			thread_execTirarFoto.cancel(true);
			thread_execTirarFoto = null;
		}
	}//AsyncTask
	
	private void excluirBciBD(){
		try {
			Intent inscricaoEscolha = getIntent();
			String inscricao = inscricaoEscolha.getStringExtra("inscricao");
			if(!inscricao.equals("")){ //se for edicao
       			objConexao = new HttpConection();
       			objConexao.setDados("o", "10");
       			
       			objConexao.setDados("inscricao", nu_insc_imbl.getText().toString());
       			JSONObject retorno = objConexao.envia();
       		 BancoFormulario formularioBanco = new BancoFormulario(this);
			    formularioBanco.deletar(inscricao);  			
			
       					
       			numero_caseExcluirBci = Integer.parseInt(retorno.getString("ok"));
       			if (retorno.getString("ok").equals("1")){
       			
       				msgExcluirBci = "BCI excluído com Sucesso!";
       			  
       				//finish();
       			}else{
       				if(retorno.getString("ok").equals("0")){
       			
       					msgExcluirBci = "ERRO Conexão: "+retorno.getString("erro");
       				}else{
       			
       					msgExcluirBci = "ERRO ao Excluir: "+retorno.getString("erro");
       				}
       			}
			}else{//se for insert
				numero_caseExcluirBci = 10;
				//finish();
			}
			
			
   			//EXCLUI NO BANCO----------------------------------------------------------------------------------------------------------------
   			   
			    BancoFormulario formularioBanco = new BancoFormulario(this);
			 
			    form.setIn_canc_cimb("*");
			    form.setAtivo("f");
			    form.setSincronizado("SIM");
                formularioBanco.atualizar(form); 
             
              
   			    
   			
   			//---------------------------------------------------------------------------------------------------------------------------------
   			
   
			
			
   		} catch (JSONException e) {
   			numero_caseExcluirBci = 11;
   			msgExcluirBci = "Erro Fatal: "+e.getMessage();
   			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método excluirBciBD (Formulario): "+e.getMessage());
   		
   		}
	}
	
	private void excluirBci(){
		if(TemConexao()){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Tem certeza que deseja excluir o BCI?")
		    	.setCancelable(false)
		    	.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		    		public void onClick(DialogInterface dialog, int id) {
		    			 
		    			thread_execExcluirBci = new threadExcluirBci().execute();
		    			
			       	}
		       })
		       .setNegativeButton("Não", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        
		                dialog.cancel();
		           }
		       });
			AlertDialog alert = builder.create();
			alert.show();
		}else{
			//DELETAR NO BANCO-----------------------------------------------------------------------------------------------------------------
			
			      thread_execExcluirBci = new threadExcluirBciBD().execute();
			
				
			//----------------------------------------------------------------------------------------------------------------------------------	
			}
			
		}
	
	private void excluirFormularioBD(){
		BancoFormulario formulariobanco = new BancoFormulario(this);
		
		try{
			form.setIn_canc_cimb("*");
			form.setAtivo("f");
			form.setSincronizado("NAO");
			formulariobanco.atualizar(form);
			msgExcluirBci = "BCI excluído com Sucesso!";
			}catch (Exception e){
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método excluirFormularioBD (Formulario): "+e.getMessage());
				msgExcluirBci = "Não foi possível excluir BCI!";
		
	       }

	}		
		
	protected void adicionaEdificacaoExtra(Boolean edicao){
		Intent winEdificacao = new Intent(Formulario.this, Edificacao.class);
		
		ArrayList<String> tipo = new ArrayList<String>();
		for (int i1 = 0; i1 < spTipo.getCount(); i1++) {  
			tipo.add(spTipo.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("tipo", (ArrayList<String>) tipo);
		
		ArrayList<String> alinhamento = new ArrayList<String>();
		for (int i1 = 0; i1 < spAlinhamento.getCount(); i1++) {  
			alinhamento.add(spAlinhamento.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("alinhamento", (ArrayList<String>) alinhamento);

		ArrayList<String> locacao = new ArrayList<String>();
		for (int i1 = 0; i1 < spLocacao.getCount(); i1++) {  
			locacao.add(spLocacao.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("locacao", (ArrayList<String>) locacao);

		ArrayList<String> situacao = new ArrayList<String>();
		for (int i1 = 0; i1 < spSituacao.getCount(); i1++) {  
			situacao.add(spSituacao.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("situacao", (ArrayList<String>) situacao);

		ArrayList<String> estrutura = new ArrayList<String>();
		for (int i1 = 0; i1 < spEstrutura.getCount(); i1++) {  
			estrutura.add(spEstrutura.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("estrutura", (ArrayList<String>) estrutura);

		ArrayList<String> cobertura = new ArrayList<String>();
		for (int i1 = 0; i1 < spCobertura.getCount(); i1++) {  
			cobertura.add(spCobertura.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("cobertura", (ArrayList<String>) cobertura);

		ArrayList<String> paredes = new ArrayList<String>();
		for (int i1 = 0; i1 < spParedes.getCount(); i1++) {  
			paredes.add(spParedes.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("paredes", (ArrayList<String>) paredes);

		ArrayList<String> vedacoesEsquadrias = new ArrayList<String>();
		for (int i1 = 0; i1 < spVedacoesEsquadrias.getCount(); i1++) {  
			vedacoesEsquadrias.add(spVedacoesEsquadrias.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("vedacoesEsquadrias", (ArrayList<String>) vedacoesEsquadrias);

		ArrayList<String> revExterno = new ArrayList<String>();
		for (int i1 = 0; i1 < spRevExterno.getCount(); i1++) {  
			revExterno.add(spRevExterno.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("revExterno", (ArrayList<String>) revExterno);

		ArrayList<String> padraoConstrucao = new ArrayList<String>();
		for (int i1 = 0; i1 < spPadraoConstrucao.getCount(); i1++) {  
			padraoConstrucao.add(spPadraoConstrucao.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("padraoConstrucao", (ArrayList<String>) padraoConstrucao);

		ArrayList<String> ocupacaoLote = new ArrayList<String>();
		for (int i1 = 0; i1 < spOcupacao.getCount(); i1++) {  
			ocupacaoLote.add(spOcupacao.getItemAtPosition(i1).toString());
        }
		winEdificacao.putStringArrayListExtra("ocupacaoLote", (ArrayList<String>) ocupacaoLote);
		
		if(edicao){
			winEdificacao.putStringArrayListExtra("arraySelected", arrayJsonEdif.get(positionEditEdificacao));
			winEdificacao.putExtra("edit", "true");
		}else{
			winEdificacao.putExtra("edit", "false");
		}
		
		startActivityForResult(winEdificacao, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK && requestCode == 1){
		
			String tipo;
			switch (data.getIntExtra("o", 0)) {
			case 1: //pessoa
				tipo = data.getStringExtra("tipo");
				if(tipo.equalsIgnoreCase("banco")){
					preencherDadosPessoaBD(data.getStringExtra("valor"));
				}else{
					preencheDadosPessoa(data.getStringExtra("json"));
				     }
				break;
			case 2: //logradouro
				tipo = data.getStringExtra("tipo");
				if(tipo.equalsIgnoreCase("banco")){
					 preencherDadosLogrBD(data.getStringExtra("valor"));
				}else{
				     preencheDadosLogr(data.getStringExtra("json"));
				};
			break;
			case 3: //bairro
				tipo = data.getStringExtra("tipo");
				if(tipo.equalsIgnoreCase("banco")){
					preencheDadosBairroBD(data.getStringExtra("valor"));
				}else{
				preencheDadosBairro(data.getStringExtra("json"));
				}
			break;
		
			case 7: //logradouro2
				tipo = data.getStringExtra("tipo");
				if(tipo.equalsIgnoreCase("banco")){
					 preencherDadosLogrBD2(data.getStringExtra("valor"));
				}else{
				     preencheDadosLogr2(data.getStringExtra("json"));
				};
				
			break;
			case 8: //logradouro
				tipo = data.getStringExtra("tipo");
				if(tipo.equalsIgnoreCase("banco")){
					 preencherDadosLogrBD3(data.getStringExtra("valor"));
				}else{
				     preencheDadosLogr3(data.getStringExtra("json"));
				};
			break;
			case 9: //logradouro
				tipo = data.getStringExtra("tipo");
				if(tipo.equalsIgnoreCase("banco")){
					 preencherDadosLogrBD4(data.getStringExtra("valor"));
				}else{
				     preencheDadosLogr4(data.getStringExtra("json"));
				};			break;
			case 10: //fotos
				arrayNomeArquivo = data.getStringArrayListExtra("arrayNomeArquivo");
				if(arrayNomeArquivo.size() > 0){
					TextView numFotos = (TextView)findViewById(R.id.txtNumeroFotos);
					numFotos.setText(arrayNomeArquivo.size()+"");
				}
			break;
			case 11: //gravar temporario
				valida.setText("true");
				controle_warning.setText("true");
				confirma_salva ="true";
				salvarArquivo();
				Toast.makeText(getBaseContext(), "Dados Gravados Temporariamente!", Toast.LENGTH_LONG).show();
				finish();
				Intent winPrincipal = new Intent(this, MenuPrincipal.class) ;
				startActivity(winPrincipal);
		
				
				//colocaBotao = "false";
			break;
			case 12: //gravar definitivo
			    valida.setText("false");
				controle_warning.setText("false");
				confirma_salva ="true";
				salvarArquivo();
				Toast.makeText(getBaseContext(), "Dados Gravados Definitivamente!", Toast.LENGTH_LONG).show();
				finish();
				Intent winPrincipal2 = new Intent(this, MenuPrincipal.class) ;
				startActivity(winPrincipal2);
		
				//colocaBotao = "false";
			break;
			case 13: //atualiza dados alterados no formulario
			    atualizaDadosPessoa(data.getStringArrayListExtra("arrayDados"),data.getStringExtra("codigo"));
			break;
		
			default:
				break;
			}
		}
	}
	
	private void atualizaDadosPessoa(ArrayList<String> arrayDados, String codigo) {
	
		
		//DADOS DO PROPRIETARIO			    
	    pessoaNome.setText(arrayDados.get(0).trim().toString());	    
	    pessoaCpf.setText(arrayDados.get(1).trim().toString());
	    pessoaComplemento.setText(arrayDados.get(8).trim().toString());
	    pessoaTelefone.setText(arrayDados.get(4).trim().toString());	    
	    cdLogr.setText(arrayDados.get(5).trim().toString());
	    
	    
	  
	    
	    if(TemConexao()){
			try {
				HttpConection objConexao = new HttpConection();
				objConexao.setDados("o", "16");
					
				objConexao.setDados("codigo",codigo);
				JSONObject retorno = objConexao.envia();
				JSONArray resultados = retorno.getJSONArray("results");
				 JSONObject rec = resultados.getJSONObject(0);
				
				 
				for (int i = 0; i < resultados.length(); ++i) {
				     rec = resultados.getJSONObject(i);				    
				    pessoaEndereco.setText(rec.getString("label"));
				    pessoaBairro.setText(rec.getString("nm_barr"));
				    pessoaCep.setText(rec.getString("nu_cep_logr"));
				    pessoaCidade.setText(rec.getString("nm_cidd"));
				    pessoaUf.setText(rec.getString("sg_uf"));
				    
	    
	    
				}
	    
			}catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método atualizaDadosPessoa (Formulario): "+e.getMessage());
				e.printStackTrace();
			
			}
		}else{
			
			try {
				if (codigo.equalsIgnoreCase("")){
					return;
					
				}else{
				BancoLogradouro logradouroBanco = new BancoLogradouro(this);
				logradouroForm = logradouroBanco.pesquisaLogradouroBD(codigo);
				pessoaEndereco.setText(logradouroForm.getLabel());
				pessoaBairro.setText(logradouroForm.getNm_barr());
				pessoaCep.setText(logradouroForm.getNu_cep_logr());
				pessoaCidade.setText(logradouroForm.getNm_cidd());
				pessoaUf.setText(logradouroForm.getSg_uf());
				}
				
			} catch (Exception e) {
				Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método atualizaDadosPessoa BD (Formulario): "+e.getMessage());
				e.printStackTrace();
			}
			
			
		}
	    
	}
		
	public void preencheDadosPessoa(String json) {
     	try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				pessoaNome.setText(jsonObj.getString("nome"));
				pessoaCpf.setText(jsonObj.getString("cpf"));
				pessoaCd_logr.setText(jsonObj.getString("cd_logr_Pessoa"));
				pessoaEndereco.setText(jsonObj.getString("endereco"));
				pessoaComplemento.setText(jsonObj.getString("complemento"));
				pessoaBairro.setText(jsonObj.getString("bairro"));
				pessoaCep.setText(jsonObj.getString("cep"));
				pessoaCidade.setText(jsonObj.getString("cidade"));
				pessoaUf.setText(jsonObj.getString("uf"));
				pessoaTelefone.setText(jsonObj.getString("telefone"));

	
			}else{
				Toast.makeText(getBaseContext(), "Erro: "+jsonObj.getString("erro"), Toast.LENGTH_LONG).show();
			}
			
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencheDadosPessoa (Formulario): "+e.getMessage());  
			
		}
	}
	
	public void preencherDadosPessoaBD(String nu_pess){
		
		try{
			BancoPessoa pessoaBanco = new BancoPessoa(this);
			pessoaForm = pessoaBanco.pesquisaPessoaBD(nu_pess);
			
			pessoaNome.setText(pessoaForm.getNm_pess());
			pessoaCpf.setText(pessoaForm.getNu_pess());
			Logradouro logradouroPessoa = new BancoLogradouro(this).pesquisaLogradouroBD(pessoaForm.getCd_logr());
			pessoaCd_logr.setText(logradouroPessoa.getCd_logr());
			pessoaEndereco.setText(logradouroPessoa.getLabel());
			pessoaComplemento.setText(pessoaForm.getDe_comp());
			pessoaBairro.setText(logradouroPessoa.getNm_barr());
			pessoaCep.setText(logradouroPessoa.getNu_cep_logr());
			pessoaCidade.setText(logradouroPessoa.getNm_cidd());
			pessoaUf.setText(logradouroPessoa.getSg_uf());
			pessoaTelefone.setText(pessoaForm.getNu_telf());
			
			
					
			
		}catch (Exception e){
			
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencherDadosPessoaBD (Formulario): "+e.getMessage());
		
		}
		
		
		
	}
	
	public void preencherDadosPessoaWEB(String nu_pess){
		  BancoPessoa pessoaBanco = new BancoPessoa(this);
		  BancoLogradouro logradouroBanco = new BancoLogradouro(this);
          Pessoa pessoa = new Pessoa();
          Logradouro logradouro = new Logradouro();
         
         pessoa = pessoaBanco.pesquisaPessoaWEB(nu_pess);
         logradouro = logradouroBanco.pesquisaLogradouroWEB(pessoa.getCd_logr());
         
            pessoaNome.setText(pessoa.getNm_pess());
			pessoaCpf.setText(pessoa.getNu_pess());
			pessoaCd_logr.setText(logradouro.getCd_logr());
			pessoaEndereco.setText(logradouro.getLabel());
			pessoaComplemento.setText(pessoa.getDe_comp());
			pessoaBairro.setText(logradouro.getNm_barr());
			pessoaCep.setText(logradouro.getNu_cep_logr());
			pessoaCidade.setText(logradouro.getNm_cidd());
			pessoaUf.setText(logradouro.getSg_uf());
			pessoaTelefone.setText(pessoa.getNu_telf());
         
		
		
	}
	
	
	
	
	
	
	
	
    public void preencherDadosLogrBD(String cd_logr){
		
		try{
		BancoLogradouro logradouroBanco = new BancoLogradouro(this);
	    logradouroForm = logradouroBanco.pesquisaLogradouroBD(cd_logr); 
		cdLogr.setText(logradouroForm.getCd_logr());
		imovelEndereco.setText(logradouroForm.getLabel());
		id_logra.setText(logradouroForm.getId_logra());
		pessoaBairro.setText(logradouroForm.getNm_barr());
		pessoaCidade.setText(logradouroForm.getNm_cidd());
		pessoaUf.setText(logradouroForm.getSg_uf());
		
		cdLogr1.setText(logradouroForm.getCd_logr());
		logradouro1.setText(logradouroForm.getLabel());
		id_logra_1.setText(logradouroForm.getId_logra());
		bairro.setText(logradouroForm.getNm_barr());
		
		}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencherDadosLogrBD (Formulario): "+e.getMessage());
			
		}
	}
	
    public void preencherDadosLogrBD2(String cd_logr){
		
		try{
		BancoLogradouro logradouroBanco = new BancoLogradouro(this);
	    logradouroForm = logradouroBanco.pesquisaLogradouroBD(cd_logr); 
		cdLogr2.setText(logradouroForm.getCd_logr());
		logradouro2.setText(logradouroForm.getLabel());
		id_logra_2.setText(logradouroForm.getId_logra());
		bairro.setText(logradouroForm.getNm_barr());
		
		}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencherDadosLogrBD2 (Formulario): "+e.getMessage());
			
		}
	}

    public void preencherDadosLogrBD3(String cd_logr){
	
		try{
		BancoLogradouro logradouroBanco = new BancoLogradouro(this);
	    logradouroForm = logradouroBanco.pesquisaLogradouroBD(cd_logr); 
		cdLogr3.setText(logradouroForm.getCd_logr());
		logradouro3.setText(logradouroForm.getLabel());
		id_logra_3.setText(logradouroForm.getId_logra());
		bairro.setText(logradouroForm.getNm_barr());
		
		}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencherDadosLogrBD3 (Formulario): "+e.getMessage());
			
		    }
    }
	
    public void preencherDadosLogrBD4(String cd_logr){
	
		try{
		BancoLogradouro logradouroBanco = new BancoLogradouro(this);
	    logradouroForm = logradouroBanco.pesquisaLogradouroBD(cd_logr); 
		cdLogr4.setText(logradouroForm.getCd_logr());
		logradouro4.setText(logradouroForm.getLabel());
		id_logra_4.setText(logradouroForm.getId_logra());
		bairro.setText(logradouroForm.getNm_barr());
		
		}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE,"Erro no método preencherDadosLogrBD4 (Formulario): "+e.getMessage());
		
		}
    }
    
    public void preencheDadosLogr(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				cdLogr.setText(jsonObj.getString("cd_logr"));
				cdLogr1.setText(jsonObj.getString("cd_logr"));
			
				if (jsonObj.getString("tp_atrb_logr").trim().equalsIgnoreCase("")){
				   	imovelEndereco.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("nm_logr"));
				    }else{			    	
				   	imovelEndereco.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("tp_atrb_logr")+" "+jsonObj.getString("nm_logr"));
				    }
					
				logradouro1.setText(jsonObj.getString("nm_logr"));				
				id_logra.setText(jsonObj.getString("id_logra"));
				id_logra_1.setText(jsonObj.getString("id_logra"));
				bairro.setText(jsonObj.getString("nm_barr"));
			}else{
				Toast.makeText(getBaseContext(), "Erro: "+jsonObj.getString("erro"), Toast.LENGTH_LONG).show();
			}
			
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - preencheDadosLogr: "+e.toString());
		}
	}
    
	public void preencheDadosLogr2(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				cdLogr2.setText(jsonObj.getString("cd_logr"));
				if (jsonObj.getString("tp_atrb_logr").trim().equalsIgnoreCase("")){
				   	logradouro2.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("nm_logr"));
				    }else{			    	
				   	logradouro2.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("tp_atrb_logr")+" "+jsonObj.getString("nm_logr"));
				    }
				id_logra_2.setText(jsonObj.getString("id_logra"));
			}else{
				Toast.makeText(getBaseContext(), "Erro: "+jsonObj.getString("erro"), Toast.LENGTH_LONG).show();
			}
			
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - preencheDadosLogr2: "+e.toString());
		}
	}
	
	public void preencheDadosLogr3(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				cdLogr3.setText(jsonObj.getString("cd_logr"));
				if (jsonObj.getString("tp_atrb_logr").trim().equalsIgnoreCase("")){
				   	logradouro3.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("nm_logr"));
				    }else{			    	
				   	logradouro3.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("tp_atrb_logr")+" "+jsonObj.getString("nm_logr"));
				    }
				id_logra_3.setText(jsonObj.getString("id_logra"));
			}else{
				
			}
			
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - preencheDadosLogr3: "+e.toString());
		}
	}
	
	public void preencheDadosLogr4(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				cdLogr4.setText(jsonObj.getString("cd_logr"));
				if (jsonObj.getString("tp_atrb_logr").trim().equalsIgnoreCase("")){
				   	logradouro4.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("nm_logr"));
				    }else{			    	
				   	logradouro4.setText(jsonObj.getString("tp_logr")+" "+jsonObj.getString("tp_atrb_logr")+" "+jsonObj.getString("nm_logr"));
				    }
				id_logra_4.setText(jsonObj.getString("id_logra"));
			}else{
				Toast.makeText(getBaseContext(), "Erro: "+jsonObj.getString("erro"), Toast.LENGTH_LONG).show();
			}
			
			
		} catch (Exception e) {
			//e.printStackTrace();
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - preencheDadosLogr4: "+e.toString());
		}
	}
	
	public void preencheDadosBairro(String json) {
		try {
			JSONObject jsonObj = new JSONObject(json);
			if(jsonObj.getString("ok").equals("1")){
				bairro.setText(jsonObj.getString("nm_bairro"));

				cd_bairro.setText(jsonObj.getString("cd_bairro"));
				//id_bairro.setText(jsonObj.getString("id_bairro"));
			}else{
				Toast.makeText(getBaseContext(), "Erro: "+jsonObj.getString("erro"), Toast.LENGTH_LONG).show();
			}
			
			
		} catch (Exception e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - preencheDadosBairro: "+e.toString());
		}
	}
	
	public void preencheDadosBairroBD(String valor){
		
		try{
		BancoBairro bairroBanco = new BancoBairro(this); 
		bairroForm = bairroBanco.pesquisaBairroBD(valor);
		bairro.setText(bairroForm.getNm_bairro());
		cd_bairro.setText(bairroForm.getMslink());
		}catch (Exception e){
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - preencheDadosBairroBD: "+e.toString());
			
		}
	}
	
	public Boolean validaFormulario(){
		
		Boolean ret = true;
		
		String inscricao = (nu_insc_imbl.getText().toString().trim()).replaceAll("[.]","").replaceAll("[-]","");
	
		String opcao = etO.getText().toString();
		String numero_logr = numero.getText().toString();
		String logradouro = imovelEndereco.getText().toString();
		String texto = "";
	
	    int caracteres = 0;
	    String mensagem = "";
	    if(opcao.equalsIgnoreCase("4")){
	    	 caracteres = 17;
	    	 mensagem = "O campo 'Inscrição' tem que ter 17 algarismos!\n";
	    	
	    }else{
	    	  
	    		   caracteres = 14;
		  	       mensagem = "O campo 'Inscrição' tem que ter 14 algarismos!\n";
	    		   
	    	      
	    	
	     }
		
		
		if(inscricao.trim().equals("")){
			texto +="Campo 'Inscrição' Obrigatório!\n";
			ret = false;			
		}else{
			if(inscricao.trim().length() != caracteres){
				texto  +=mensagem;
				ret = false;				
			}else {
				
							String cd_quadra = inscricao.substring(0,7);
							BancoQuadra quadraBanco = new BancoQuadra(this);
						
							if (TemConexao()){
								
							  	if(quadraBanco.consultaQuadraWEB(cd_quadra)){
							  		texto +="Inscrição não pertence as Quadras Associadas!\n";
									ret = false;
							  			}
								
									}else{
										 if(quadraBanco.consultaQuadraBD(cd_quadra)){
											 texto +="Inscrição "+inscricao+" não pertence as Quadras Associadas!\n";
												ret = false;
										 }
							}
							
				
				
			     } 
		}
		
		if(numero_logr.trim().equals("")){
			texto +="Campo 'N°' Obrigatório!\n";
			ret = false;			
		  }
		if(logradouro.trim().equals("")){
			texto +="Campo 'Logradouro' Obrigatório!\n";
			ret = false;			
		  }			
			if(!ret){
			colocaAlert(texto);	
		    nu_insc_imbl.requestFocus();	
		}
			
		return ret;
	}
	
	
	public Boolean alteracaoColetiva(){
		
    
		
							AlertDialog.Builder builder = new AlertDialog.Builder(this);
							builder.setMessage("Deseja realizar uma alteração Coletiva? ")
						    	.setCancelable(true)
						    	.setPositiveButton("sim", new DialogInterface.OnClickListener() {
						    		public void onClick(DialogInterface dialog, int id) {
						    			
						    	     altera = "SIM";
						    	     alteracao_coletiva = "N";	
							       	}
						    		
						       }).setNegativeButton("não", new DialogInterface.OnClickListener() {
						           public void onClick(DialogInterface dialog, int id) {
						        	   
						        	altera = "NAO";
						        	alteracao_coletiva = "N";	
						       	  
						           }
						       });
							AlertDialog alert = builder.create();
							alert.show();
							
							if(altera.equalsIgnoreCase("SIM")){
								
								return true;
								
							}else{
								
								return false;
							}
	
 			
		
	}
	
		
	public void colocaAlert(String msg){
		
		
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
	
	public void salvarArquivo(){
		
		
		String insc = nu_insc_imbl.getText().toString().trim();
		String inscricao = insc.replaceAll("[.]","").replaceAll("[-]","");
		
		
		
		String inscricaoNova = inscricao;
	
	    form.setNu_insc_imbl_ant(nu_insc_imbl_ant.getText().toString().trim());
		form.setCd_logr(cdLogr.getText().toString().trim());
		form.setId_logra(id_logra.getText().toString().trim());
		form.setCd_seca(secao.getText().toString().trim());
		form.setNu_imvl(numero.getText().toString().trim());
		form.setDe_comp( complemento.getText().toString().trim());
		form.setId_bairro(cd_bairro.getText().toString().trim());
		form.setNu_lotm(loteamento.getText().toString().trim());
		form.setNu_qudr(quadra.getText().toString().trim());
		form.setNu_lote(lote.getText().toString().trim());
		form.setNu_apto_garg(apto.getText().toString().trim());
		form.setNm_bloc(bloco.getText().toString().trim());
		form.setNm_edfc(edificio.getText().toString().trim());
		
		
		
		if(cbEndCorrespondencia.isChecked()){
			form.setIn_endr_corr("S");
			}else{
			form.setIn_endr_corr("N");	
			}
		
		//dados pessoa
		form.setNu_pess(pessoaCpf.getText().toString().trim());
		pessoaForm.setNm_pess(pessoaNome.getText().toString().trim());
		pessoaForm.setNu_pess(pessoaCpf.getText().toString().trim());
		pessoaForm.setCd_logr(pessoaCd_logr.getText().toString().trim());
		pessoaForm.setDe_comp(pessoaComplemento.getText().toString().trim());
		pessoaForm.setNu_telf(pessoaTelefone.getText().toString().trim());
		
		
		form.setCd_imbl(cod_imobiliaria.getText().toString().trim());
		Opcoes ocupacao = (Opcoes) spOcupacao.getSelectedItem();
		form.setTp_ocpc_lote(Integer.toString(ocupacao.getId()));
		Opcoes murado = (Opcoes) spMurado.getSelectedItem();
		form.setTp_murd(Integer.toString(murado.getId()));
		Opcoes patrimonio = (Opcoes) spPatrimonio.getSelectedItem();
		form.setTp_patr(Integer.toString(patrimonio.getId()));
		Opcoes marinha = (Opcoes) spMarinha.getSelectedItem();
		form.setTp_terr_marn(Integer.toString(marinha.getId()));
		Opcoes utilizacao = (Opcoes) spUtilizacao.getSelectedItem();
		form.setTp_utlz(Integer.toString(utilizacao.getId()));
		Opcoes passeio = (Opcoes) spPasseio.getSelectedItem();
		form.setTp_pass(Integer.toString(passeio.getId()));
		Opcoes arvore = (Opcoes) spArvore.getSelectedItem();
		form.setTp_arvr(Integer.toString(arvore.getId()));
		Opcoes elevador = (Opcoes) spElevador.getSelectedItem();
		form.setTp_elvd(Integer.toString(elevador.getId()));
		Opcoes redeAgua = (Opcoes) spRedeAgua.getSelectedItem();
		form.setTp_ligc_agua(Integer.toString(redeAgua.getId()));
		Opcoes esgoto = (Opcoes) spEsgoto.getSelectedItem();
		form.setTp_esgt(Integer.toString(esgoto.getId()));
		Opcoes eletricidade = (Opcoes) spEletricidade.getSelectedItem();
		form.setTp_eltr(Integer.toString(eletricidade.getId()));
		Opcoes isento_iptu = (Opcoes) spIsentoIptu.getSelectedItem();
		form.setTp_isnt_iptu(Integer.toString(isento_iptu.getId()));
		Opcoes isento_tsu = (Opcoes) spIsentoTsu.getSelectedItem();
		form.setTp_isnt_tsu(Integer.toString(isento_tsu.getId()));
		form.setNu_pavm(numPavimentos.getText().toString().trim());
		form.setNu_celesc(numCelesc.getText().toString().trim());
		form.setNu_casan(numCasan.getText().toString().trim());
		form.setNu_matr_cart(cartMatricula.getText().toString().trim());
		form.setNu_livr_cart(cartLivro.getText().toString().trim());
		form.setCd_tabl_cart(cartCodigo.getText().toString().trim());
		form.setVvenal(valorVenal.getText().toString().trim());
		form.setQt_area_cons(areaTotalConst.getText().toString().trim());
		form.setQt_area_lote(areaTerreno.getText().toString().trim());
		Opcoes situacao_quadra = (Opcoes) spSitQuadra.getSelectedItem();
		form.setTp_sitc_qudr(Integer.toString(situacao_quadra.getId()));
		Opcoes topografia = (Opcoes) spTopografia.getSelectedItem();
		form.setTp_topg(Integer.toString(topografia.getId()));
		form.setQt_prfn( profundidade.getText().toString());
		Opcoes pedologia = (Opcoes) spPedologia.getSelectedItem();
		form.setTp_pedl(Integer.toString(pedologia.getId()));
	    form.setNu_test_prnc( testada1.getText().toString().trim());
	    form.setNu_tes2(testada2.getText().toString().trim());
	    form.setCd_logr_tes2(cdLogr2.getText().toString().trim());
	    form.setCd_seca_tes2(secao2.getText().toString().trim());
	    form.setNu_tes3(testada3.getText().toString().trim());
	    form.setCd_logr_tes3(cdLogr3.getText().toString().trim());
		form.setCd_seca_tes3(secao3.getText().toString().trim());
		form.setNu_tes4(testada4.getText().toString().trim());
		form.setCd_logr_tes4(cdLogr4.getText().toString().trim());
		form.setCd_seca_tes4(secao4.getText().toString().trim());
		form.setAa_cons(anoConst.getText().toString().trim());
		form.setNu_pav_edif(numPavmEdf.getText().toString().trim());
		form.setQt_area_undd(areaConstUnid.getText().toString().trim());
		Opcoes tipo = (Opcoes) spTipo.getSelectedItem();
		form.setTp_edfc(Integer.toString(tipo.getId()));
		Opcoes alinhamento = (Opcoes) spAlinhamento.getSelectedItem();
		form.setTp_alnh(Integer.toString(alinhamento.getId()));
		Opcoes locacao = (Opcoes) spLocacao.getSelectedItem();
		form.setTp_locc(Integer.toString(locacao.getId()));
		Opcoes situacao = (Opcoes) spSituacao.getSelectedItem();
		form.setTp_sitc(Integer.toString(situacao.getId()));
		Opcoes estrutura = (Opcoes) spEstrutura.getSelectedItem();
		form.setTp_estr(Integer.toString(estrutura.getId()));
		Opcoes cobertura = (Opcoes) spCobertura.getSelectedItem();
		form.setTp_cobr(Integer.toString(cobertura.getId()));
		Opcoes paredes = (Opcoes) spParedes.getSelectedItem();
		form.setTp_pard(Integer.toString(paredes.getId()));
		Opcoes vedacoesEsquadrias = (Opcoes) spVedacoesEsquadrias.getSelectedItem();
		form.setTp_vedc(Integer.toString(vedacoesEsquadrias.getId()));
		Opcoes revestimento_externo = (Opcoes) spRevExterno.getSelectedItem();
		form.setTp_revs_extr(Integer.toString(revestimento_externo.getId()));
		Opcoes padraoConstrucao = (Opcoes) spPadraoConstrucao.getSelectedItem();
		form.setTp_padr_cons(Integer.toString(padraoConstrucao.getId()));
		form.setQt_afst_frnt(afastamentoFrontal.getText().toString().trim());
	
		
		if(cbClimatizacao.isChecked()){
			form.setIn_clim_ambi("S");
			}else{
			form.setIn_clim_ambi("N");	
			}
		
		if(cbEquipSeguranca.isChecked()){
			form.setIn_eqpt_segr("S");
			}else{
			form.setIn_eqpt_segr("N");	
			}
		
		if(cbSalaFestas.isChecked()){
			form.setIn_salo_fest("S");
			}else{
			form.setIn_salo_fest("N");	
			}
		
		if(cbChurrasqueira.isChecked()){
			form.setIn_chur("S");
			}else{
			form.setIn_chur("N");	
			}
		
		if(cbSauna.isChecked()){
			form.setIn_saun("S");
			}else{
			form.setIn_saun("N");	
			}
		
		if(cbPiscina.isChecked()){
			form.setIn_pisc("S");
			}else{
			form.setIn_pisc("N");	
			}
		
		if(cbLareira.isChecked()){
			form.setIn_lare("S");
			}else{
			form.setIn_lare("N");	
			}
		
		if(cbSalaoJogos.isChecked()){
			form.setIn_sala_jogo("S");
			}else{
			form.setIn_sala_jogo("N");	
			}
		
		if(cbsalaGinastica.isChecked()){
			form.setIn_sala_gina("S");
			}else{
			form.setIn_sala_gina("N");	
			}
		
		if(cbAquecedoresGas.isChecked()){
			form.setIn_aque_gas("S");
			}else{
			form.setIn_aque_gas("N");	
			}
		
		if(cbElevServico.isChecked()){
			form.setIn_elev_serv("S");
			}else{
			form.setIn_elev_serv("N");	
			}
		
		if(cbElevadorSocial.isChecked()){
			form.setIn_elev_soci("S");
			}else{
			form.setIn_elev_soci("N");	
			}
		
		
		//form.setCadastrador(cadastrador.getText().toString().trim());
		form.setObs_geo(observacoes.getText().toString().trim());
		form.setDt_cadastro(new  Timestamp(System.currentTimeMillis()).toString());
        form.setControle_tablet("1");
        //form.setAtivo("t");
        
      /*  if(controle_warning.getText().toString().equalsIgnoreCase("true")){
        form.setStatus_cadastro("Editado com Erro");
        }else{
        form.setStatus_cadastro("Editado sem Erro");
        }*/
        
       
       
       SharedPreferences preferencia = getSharedPreferences("VGFISCAL",0);
       String usuario = preferencia.getString("usuario","");
       form.setNm_user_cadastro(usuario);
       form.setAtivo("t");
		
			
		
		if(TemConexao()){
				
			try {
				
					objConexao = new HttpConection();

					
					
					//LOCALIZACAO DO IMOVEL
					objConexao.setDados("nu_insc_imbl",inscricao);
					objConexao.setDados("cd_logr", cdLogr.getText().toString().trim());
					objConexao.setDados("id_logra", id_logra.getText().toString().trim());
					objConexao.setDados("cd_seca", secao.getText().toString().trim());
					objConexao.setDados("numero_logr", numero.getText().toString().trim());
					objConexao.setDados("complemento", complemento.getText().toString().trim());
					objConexao.setDados("cd_bairro", cd_bairro.getText().toString().trim());
					objConexao.setDados("nu_lotm",loteamento.getText().toString().trim());
					objConexao.setDados("quadra", quadra.getText().toString().trim());
					objConexao.setDados("lote", lote.getText().toString().trim());
					objConexao.setDados("nu_apto_garg", apto.getText().toString().trim());
					objConexao.setDados("nm_bloc", bloco.getText().toString().trim());
					objConexao.setDados("nm_edfc", edificio.getText().toString().trim());
				
					if (cbAltColetiva.isChecked()){
						objConexao.setDados("alteracao_coletiva", "S");
					}else{
						objConexao.setDados("alteracao_coletiva", "N");
						
					}
					
					if(cbEndCorrespondencia.isChecked()){
						objConexao.setDados("in_endr_corr","S");
						}else{
							objConexao.setDados("in_endr_corr","N");	
						}
					
					
					//DADOS DO PROPRIETARIO
					
			
					objConexao.setDados("cpf", pessoaCpf.getText().toString().trim());
					objConexao.setDados("cd_imbl", cod_imobiliaria.getText().toString().trim());
				
		            //INFORMACOES GERAIS SOBRE O IMOVEL
					
					 ocupacao = (Opcoes) spOcupacao.getSelectedItem();
					objConexao.setDados("ocupacao", Integer.toString(ocupacao.getId()));
					 murado = (Opcoes) spMurado.getSelectedItem();
					objConexao.setDados("murado", Integer.toString(murado.getId()));
					 patrimonio = (Opcoes) spPatrimonio.getSelectedItem();
					objConexao.setDados("patrimonio", Integer.toString(patrimonio.getId()));
					 marinha = (Opcoes) spMarinha.getSelectedItem();
					objConexao.setDados("marinha", Integer.toString(marinha.getId()));
					 utilizacao = (Opcoes) spUtilizacao.getSelectedItem();
					objConexao.setDados("utilizacao", Integer.toString(utilizacao.getId()));
					 passeio = (Opcoes) spPasseio.getSelectedItem();
					objConexao.setDados("passeio", Integer.toString(passeio.getId()));
					 arvore = (Opcoes) spArvore.getSelectedItem();
					objConexao.setDados("arvore", Integer.toString(arvore.getId()));
					 elevador = (Opcoes) spElevador.getSelectedItem();
					objConexao.setDados("elevador", Integer.toString(elevador.getId()));
					 redeAgua = (Opcoes) spRedeAgua.getSelectedItem();
					objConexao.setDados("rede_agua", Integer.toString(redeAgua.getId()));
					 esgoto = (Opcoes) spEsgoto.getSelectedItem();
					objConexao.setDados("esgoto", Integer.toString(esgoto.getId()));
					 eletricidade = (Opcoes) spEletricidade.getSelectedItem();
					objConexao.setDados("eletricidade", Integer.toString(eletricidade.getId()));					
					 isento_iptu = (Opcoes) spIsentoIptu.getSelectedItem();
					objConexao.setDados("isento_iptu", Integer.toString(isento_iptu.getId()));
					 isento_tsu = (Opcoes) spIsentoTsu.getSelectedItem();
					objConexao.setDados("isento_tsu", Integer.toString(isento_tsu.getId()));
					objConexao.setDados("numero_pavimentos", numPavimentos.getText().toString().trim());
					objConexao.setDados("nu_celesc", numCelesc.getText().toString().trim());
					objConexao.setDados("nu_casan", numCasan.getText().toString().trim());
					objConexao.setDados("nu_matr_cart", cartMatricula.getText().toString().trim());
					objConexao.setDados("nu_livr_cart", cartLivro.getText().toString().trim());
					objConexao.setDados("nu_folh_cart", cartFolha.getText().toString().trim());
					objConexao.setDados("cd_tabl_cart", cartCodigo.getText().toString().trim());
					objConexao.setDados("vvenal", valorVenal.getText().toString().trim());
				
		            //INFORMACOES SOBRE O TERRENO
					
					
				
					objConexao.setDados("area_total_const", areaTotalConst.getText().toString().trim());
					objConexao.setDados("area_terreno", areaTerreno.getText().toString().trim());						
					 situacao_quadra = (Opcoes) spSitQuadra.getSelectedItem();
					objConexao.setDados("situacao_quadra", Integer.toString(situacao_quadra.getId()));
					 topografia = (Opcoes) spTopografia.getSelectedItem();
					objConexao.setDados("topografia", Integer.toString(topografia.getId()));
					objConexao.setDados("profundidade", profundidade.getText().toString());
					 pedologia = (Opcoes) spPedologia.getSelectedItem();
					objConexao.setDados("pedologia", Integer.toString(pedologia.getId()));
					objConexao.setDados("testada1", testada1.getText().toString().trim());
					objConexao.setDados("cd_logr", cdLogr1.getText().toString());
					objConexao.setDados("secao",secao.getText().toString().trim());
					objConexao.setDados("logradouro", logradouro1.getText().toString());
					objConexao.setDados("testada2", testada2.getText().toString().trim());
					objConexao.setDados("cd_logr2", cdLogr2.getText().toString().trim());
					objConexao.setDados("cd_seca2", secao2.getText().toString().trim());
					objConexao.setDados("logradouro2", logradouro2.getText().toString().trim());
					objConexao.setDados("testada3", testada3.getText().toString().trim());
					objConexao.setDados("cd_logr3", cdLogr3.getText().toString().trim());
					objConexao.setDados("cd_seca3", secao3.getText().toString().trim());
					objConexao.setDados("logradouro3", logradouro3.getText().toString().trim());
					objConexao.setDados("testada4", testada4.getText().toString().trim());
					objConexao.setDados("cd_logr4", cdLogr4.getText().toString().trim());
					objConexao.setDados("cd_seca4", secao4.getText().toString().trim());
					objConexao.setDados("logradouro4", logradouro4.getText().toString().trim());

		            //INFORMACOES SOBRE A EDIFICACAO
					objConexao.setDados("ano_construcao", anoConst.getText().toString().trim());
					objConexao.setDados("nu_pav_edif", numPavmEdf.getText().toString().trim());
					objConexao.setDados("area_contruida_unidade", areaConstUnid.getText().toString().trim());
					 tipo = (Opcoes) spTipo.getSelectedItem();
					objConexao.setDados("tipo", Integer.toString(tipo.getId()));
					 alinhamento = (Opcoes) spAlinhamento.getSelectedItem();
					objConexao.setDados("alinhamento", Integer.toString(alinhamento.getId()));
					 locacao = (Opcoes) spLocacao.getSelectedItem();
					objConexao.setDados("locacao", Integer.toString(locacao.getId()));
					 situacao = (Opcoes) spSituacao.getSelectedItem();
					objConexao.setDados("situacao", Integer.toString(situacao.getId()));
					 estrutura = (Opcoes) spEstrutura.getSelectedItem();
					objConexao.setDados("estrutura", Integer.toString(estrutura.getId()));
					 cobertura = (Opcoes) spCobertura.getSelectedItem();
					objConexao.setDados("cobertura", Integer.toString(cobertura.getId()));
					 paredes = (Opcoes) spParedes.getSelectedItem();
					objConexao.setDados("paredes", Integer.toString(paredes.getId()));
					 vedacoesEsquadrias = (Opcoes) spVedacoesEsquadrias.getSelectedItem();
					objConexao.setDados("vedacoes_esquadrias", Integer.toString(vedacoesEsquadrias.getId()));
					 revestimento_externo = (Opcoes) spRevExterno.getSelectedItem();
					objConexao.setDados("revestimento_externo", Integer.toString(revestimento_externo.getId()));
					 padraoConstrucao = (Opcoes) spPadraoConstrucao.getSelectedItem();
					objConexao.setDados("padrao_construcao", Integer.toString(padraoConstrucao.getId()));
					objConexao.setDados("afastamento_frontal", afastamentoFrontal.getText().toString().trim());
				
					if(cbClimatizacao.isChecked()){
						objConexao.setDados("in_clim_ambi","S");
						}else{
							objConexao.setDados("in_clim_ambi","N");	
						}
					
					if(cbEquipSeguranca.isChecked()){
						objConexao.setDados("in_eqpt_segr","S");
						}else{
							objConexao.setDados("in_eqpt_segr","N");	
						}
					
					if(cbSalaFestas.isChecked()){
						objConexao.setDados("in_salo_fest","S");
						}else{
							objConexao.setDados("in_salo_fest","N");	
						}
					
					if(cbChurrasqueira.isChecked()){
						objConexao.setDados("in_chur","S");
						}else{
							objConexao.setDados("in_chur","N");	
						}
					
					if(cbSauna.isChecked()){
						objConexao.setDados("in_saun","S");
						}else{
							objConexao.setDados("in_saun","N");	
						}
					
					if(cbPiscina.isChecked()){
						objConexao.setDados("in_pisc","S");
						}else{
							objConexao.setDados("in_pisc","N");	
						}
					
					if(cbLareira.isChecked()){
						objConexao.setDados("in_lare","S");
						}else{
							objConexao.setDados("in_lare","N");	
						}
					
					if(cbSalaoJogos.isChecked()){
						objConexao.setDados("in_sala_jogo","S");
						}else{
							objConexao.setDados("in_sala_jogo","N");	
						}
					
					if(cbsalaGinastica.isChecked()){
						objConexao.setDados("in_sala_gina","S");
						}else{
							objConexao.setDados("in_sala_gina","N");	
						}
					
					if(cbAquecedoresGas.isChecked()){
						objConexao.setDados("in_aque_gas","S");
						}else{
							objConexao.setDados("in_aque_gas","N");	
						}
					
					if(cbElevServico.isChecked()){
						objConexao.setDados("in_elev_serv","S");
						}else{
							objConexao.setDados("in_elev_serv","N");	
						}
					
					if(cbElevadorSocial.isChecked()){
						objConexao.setDados("in_elev_soci","S");
						}else{
							objConexao.setDados("in_elev_soci","N");	
						}
					
				objConexao.setDados("qt_garg",nrGaragem.getText().toString().trim());
					
					
					
					//INFORMACOES COMPLEMENTARES
					//objConexao.setDados("cadastrador", cadastrador.getText().toString().trim());
					//objConexao.setDados("data_cadastro", dataCadastro.getText().toString().trim());
					objConexao.setDados("obs", observacoes.getText().toString().trim());
					
					//CAMPOS INVISIVEIS
					
					//INCLUSÃO POR SUBSTITUICAO
					//objConexao.setDados("nu_insc_imbl_ant",inscricaoAntiga);
						
			
					objConexao.setDados("controle_warning",controle_warning.getText().toString().trim());
					objConexao.setDados("valida", valida.getText().toString().trim());
					
					objConexao.setDados("o",etO.getText().toString().trim());
					
					if (etO.getText().toString().trim().equals("4")){
						objConexao.setDados("bci","edicao");
						form.setTipo_cadastro("edicao");
					}else{
						
						objConexao.setDados("bci","insercao");
						form.setTipo_cadastro("insercao");
					}
					
		
					if(!inscricaoAntiga.equalsIgnoreCase(inscricao)){
					
						objConexao.setDados("inscricao_antiga",inscricaoAntiga);
						
					}
						objConexao.setDados("ativo","t");
						
					
					
					objConexao.setDados("controle_tablet","1");	
					
					objConexao.setDados("confirma_salva",confirma_salva);
					
					SharedPreferences sp = getSharedPreferences("VGFISCAL", 0);
					 usuario = sp.getString("usuario", "nao");
					
					objConexao.setDados("nm_usuario", usuario);
				
						  JSONObject retorno = objConexao.envia();

               
						  //MÉTODOS PARA INSERÇÕES NO BD DO TABLET
//--------------------------------------------------------------------------------------------------------
		
					if(confirma_salva.equalsIgnoreCase("true")){	  
							  BancoFormulario novo = new BancoFormulario(this);
							    
					        if (etO.getText().toString().trim().equals("4")){
					        	
								//ATUALIZA BCI NO BANCO-----------------------------------------------------------------------------------------------------------------------
					           
					        	if(inscricaoAntiga.equalsIgnoreCase(inscricaoNova)){
						        	 form.setNu_insc_imbl(inscricaoNova);	
						        	 novo.atualizar(form);
						        	 form.setSincronizado("SIM");
						        	 novo.atualizarColetiva(form);
						        	 
						        	}else{
						        		
						        	form.setNu_insc_imbl(inscricaoNova);	
						        	String Insc_Antiga = form.getNu_insc_imbl_ant();
						        	form.setNu_insc_imbl_ant(inscricaoAntiga);
						        	form.setSincronizado("SIM");
						        	novo.inserir(form);
						        	
						        	
						        	form.setNu_insc_imbl(inscricaoAntiga);
						        	form.setNu_insc_imbl_ant(Insc_Antiga);
						        	form.setAtivo("f");
						        	form.setIn_canc_cimb("*");
						        	form.setSincronizado("SIM");
						        		novo.atualizar(form);
						         		
						        	}
					        	
					        	
					   
					        		        	
					        	
					        }else{
							
					        	//SALVA BCI NO BANCO--------------------------------------------------------------------------------------------------------------------------
								form.setNu_insc_imbl(inscricaoNova);
								form.setSincronizado("SIM");
					       		novo.inserir(form);
							}
								//--------------------------------------------------------------------------------------------------------------------------------------------
								
				
					    	//ATUALIZA CONTRIBUINTE NO BANCO-----------------------------------------------------------------------------------------------------------------------
							
					        BancoPessoa pessoaBanco = new BancoPessoa(this);
					        
					        if(pessoaBanco.ConsultaPessoaBD(form.getNu_pess())){
					        	pessoaForm.setSincronizado("SIM");
					         	pessoaBanco.inserir(pessoaForm);
					        	
					        }else{
					        	pessoaForm.setSincronizado("SIM");
					        	pessoaBanco.atualizar(pessoaForm);
					        }
					        
				       	//------------------------------------------------------------------------------------------------------------------------------------------------------
						        	
//--------------------------------------------------------------------------------------------------------
		     
					retornoSalvar = retorno;
					numero_caseSalvar = Integer.parseInt(retorno.getString("ok"));
					switch (Integer.parseInt(retorno.getString("ok"))) {
					case 0:
					
						msgSalvar = "ERRO Conexão: "+retorno.getString("erro");
						break;
					case 1:
					
						msgSalvar = "Dados Gravados com Sucesso!";
						form.setStatus_cadastro("Editado sem Erro");
						novo.atualizar(form);
						//novo.atualizarColetiva(form);
						finish();
						break;
					case 2:
						
						msgSalvar = "ERRO em Gravar: "+retorno.getString("error");
						break;
					case 3:
						
						msgSalvar = "Os dados não podem ser gravados! Erro: "+retorno.getString("error");
						break;
					case 4:
						mostraOsErros(retorno);
						form.setStatus_cadastro("Editado com Erro");
						novo.atualizar(form);
						//novo.atualizarColetiva(form);
						break;

					default:
						break;
					}
					
					}else{
						
						
						retornoSalvar = retorno;
						numero_caseSalvar = Integer.parseInt(retorno.getString("ok"));
						switch (Integer.parseInt(retorno.getString("ok"))) {
						case 0:
						
							msgSalvar = "ERRO Conexão: "+retorno.getString("erro");
							break;
						case 1:
						
							msgSalvar = "Dados Gravados com Sucesso!";
							//form.setStatus_cadastro("Editado sem Erro");
						//	novo.atualizar(form);
							finish();
							break;
						case 2:
							
							msgSalvar = "ERRO em Gravar: "+retorno.getString("error");
							break;
						case 3:
							
							msgSalvar = "Os dados não podem ser gravados! Erro: "+retorno.getString("error");
							break;
						case 4:
							mostraOsErros(retorno);
							//form.setStatus_cadastro("Editado com Erro");
							//novo.atualizar(form);
							break;

						default:
							break;
						}
						
						
						
						
						
						
						
					}
				
	        } catch (Exception e) {
	        	numero_caseSalvar = 5;	        	
	        	msgSalvar = "ERRO Salvar Arquivo: "+e.getMessage();
	        	Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - SalvarArquivo (web): "+e.toString());
	        	
			}
			
			
			}else{
		
                     //SEM CONEXÃO COM INTERNET
//---------------------------------------------------------------------------------------------------------------				
				
				try{
			    	 
			
			    	           BancoFormulario novo = new BancoFormulario(this);
					          
					               
					             String opcao = etO.getText().toString().trim(); 
					        	 form.setSincronizado("NAO");
					        	 
						        if (opcao.equals("4")){
						        	
									//ATUALIZA BCI NO BANCO-----------------------------------------------------------------------------------------------------------------------
						           	if(inscricaoAntiga.equalsIgnoreCase(inscricaoNova)){
							        	 form.setNu_insc_imbl(inscricaoNova);
							        	 form.setTipo_cadastro("edicao");
							        	 novo.atualizar(form);	
							        	}else{
							        		
							        	form.setNu_insc_imbl(inscricaoNova);
							        	String Insc_Antiga = form.getNu_insc_imbl_ant();
							        	form.setNu_insc_imbl_ant(inscricaoAntiga);
							        	novo.inserir(form);
							        	
							        	
							        	form.setNu_insc_imbl(inscricaoAntiga);
							        	form.setNu_insc_imbl_ant(Insc_Antiga);
							        	form.setAtivo("f");
							        	form.setIn_canc_cimb("*");
							        	novo.atualizar(form);
							        		
							        	}
						        	
						        	if (cbAltColetiva.isChecked()){
						        		
						        	
						        	novo.atualizarColetiva(form);
						        
						        	novo.atualizar(form);
						        	
						        	}
						        	
						     
						        	msgSalvar = "Dados alterados somente no Tablet!";
						        	numero_caseSalvar = 1;
						         	//--------------------------------------------------------------------------------------------------------------------------------------------
								
						        }else{
						        	
									//SALVA BCI NO BANCO--------------------------------------------------------------------------------------------------------------------------
								    form.setNu_insc_imbl(inscricaoNova);
							        form.setSincronizado("NAO");
							        form.setTipo_cadastro("insercao");
							      	novo.inserir(form);
									msgSalvar = "Dados Gravados somente no Tablet!";
									numero_caseSalvar = 1;
									//--------------------------------------------------------------------------------------------------------------------------------------------
							
									
									
								}
						        
						        
						      	//ATUALIZA CONTRIBUINTE NO BANCO-----------------------------------------------------------------------------------------------------------------------
							      BancoPessoa pessoaBanco = new BancoPessoa(this);
						         if(pessoaBanco.ConsultaPessoaBD(form.getNu_pess())){
						         	pessoaBanco.inserir(pessoaForm);
						        	
						        }else{
						        	pessoaBanco.atualizar(pessoaForm);
						        }
						        
						        	//------------------------------------------------------------------------------------------------------------------------------------------------------
					        	
//---------------------------------------------------------------------------------------------------------------			
						        
			     }catch (Exception e){
			    	 Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - SalvarArquivo (web): "+e.toString());
			    	 
			    	 
			     }		        
		
		}
		
	}
	
	private void mostraOsErros(JSONObject retorno){
		JSONArray resultados;
		try {
			Intent winErros = new Intent(Formulario.this, Erros.class);
			resultados = retorno.getJSONArray("results");
			
			 JSONObject rec = resultados.getJSONObject(0);
			if(rec.getString("tipo_erro").equals("1")){
		    	colocaBotao = "true";
		    	colocaBotao2 = "false";
		    }
			
			array_validacoes = new ArrayList<String>();
			
			for (int i = 0; i < resultados.length(); ++i) {
			     rec = resultados.getJSONObject(i);
			    
			    String descricao = rec.getString("descricao");
			    array_validacoes.add(descricao);
			}
			winErros.putExtra("colocaBotao", colocaBotao);
			winErros.putExtra("colocaBotao2", colocaBotao2);
			winErros.putExtra("tipo", "formulario");
			winErros.putStringArrayListExtra("array_validacoes", array_validacoes);
		    startActivityForResult(winErros, 1);
			
		} catch (JSONException e) {
			Log.e(TEXT_SERVICES_MANAGER_SERVICE, "Erro no Método - mostraOsErros: "+e.toString());
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

    public void inicializaSpinner(){
		
		//INFORMACOES GERAIS SOBRE O IMOVEL --------------------INICIO
		
		
	//OCUPACAO  ----------------------INICIO
			Opcoes o1 = new Opcoes();
			o1.setId(0);
			o1.setNome("Selecione...");
			Opcoes o2 = new Opcoes();
			o2.setId(15);
			o2.setNome("Não Construído");
			Opcoes o3 = new Opcoes();
			o3.setId(23);
			o3.setNome("Ruínas");
			Opcoes o4 = new Opcoes();
			o4.setId(40);
			o4.setNome("Construção Paralizada");
			Opcoes o5 = new Opcoes();
			o5.setId(58);
			o5.setNome("Construção em Andamento");
			Opcoes o6 = new Opcoes();
			o6.setId(66);
			o6.setNome("Improvisado");
			Opcoes o7 = new Opcoes();
			o7.setId(74);
			o7.setNome("Construído");
			
			opcoesOcupacao = new ArrayList<Opcoes>();  
			opcoesOcupacao.add(o1);  
			opcoesOcupacao.add(o2);  
			opcoesOcupacao.add(o3);  
			opcoesOcupacao.add(o4);  
			opcoesOcupacao.add(o5);
			opcoesOcupacao.add(o6);
			opcoesOcupacao.add(o7);
			
			ArrayAdapter<Opcoes> ocupacaoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesOcupacao);
			ocupacaoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			spOcupacao.setAdapter(ocupacaoAdapter);
			//OCUPACAO ----------------------FIM
	
		
	
		
		
		//MURADO   ----------------------INICIO
		Opcoes mu1 = new Opcoes();
		mu1.setId(0);
		mu1.setNome("Selecione...");
		Opcoes mu2 = new Opcoes();
		mu2.setId(18);
		mu2.setNome("não");
		Opcoes mu3 = new Opcoes();
		mu3.setId(26);
		mu3.setNome("sim");
		
		opcoesMurado = new ArrayList<Opcoes>();  
		opcoesMurado.add(mu1);  
		opcoesMurado.add(mu2);  
		opcoesMurado.add(mu3);
		
		ArrayAdapter<Opcoes> muradoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesMurado);
		muradoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spMurado.setAdapter(muradoAdapter);
		//MURADO   ----------------------FIM
		
		
		
		
		//PATRIMONIO   ----------------------INICIO
		Opcoes pa1 = new Opcoes();
		pa1.setId(0);
		pa1.setNome("Selecione...");
		Opcoes pa2 = new Opcoes();
		pa2.setId(0);
		pa2.setNome("Nenhum");
		Opcoes pa3 = new Opcoes();
		pa3.setId(20);
		pa3.setNome("Particular");
		Opcoes pa4 = new Opcoes();
		pa4.setId(39);
		pa4.setNome("religioso");
		Opcoes pa5 = new Opcoes();
		pa5.setId(86);
		pa5.setNome("Público Federal");
		Opcoes pa6 = new Opcoes();
		pa6.setId(87);
		pa6.setNome("Público Estadual");
		Opcoes pa7 = new Opcoes();
		pa7.setId(89);
		pa7.setNome("Público Municipal");
		
		
		opcoesPatrimonio = new ArrayList<Opcoes>();  
		opcoesPatrimonio.add(pa1);  
		opcoesPatrimonio.add(pa2);  
		opcoesPatrimonio.add(pa3);  
		opcoesPatrimonio.add(pa4);
		opcoesPatrimonio.add(pa5);
		opcoesPatrimonio.add(pa6);
		opcoesPatrimonio.add(pa7);
				
		
		ArrayAdapter<Opcoes> patrimonioAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesPatrimonio);
		patrimonioAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spPatrimonio.setAdapter(patrimonioAdapter);
		//PATRIMONIO   ----------------------FIM
		
		//TERRENO MARINHA ----------------------INICIO
				Opcoes te1 = new Opcoes();
				te1.setId(0);
				te1.setNome("Selecione...");
				Opcoes te2 = new Opcoes();
				te2.setId(10);
				te2.setNome("Ocupação");
				Opcoes te3 = new Opcoes();
				te3.setId(28);
				te3.setNome("Posse");
				Opcoes te4 = new Opcoes();
				te4.setId(36);
				te4.setNome("Aforamento");
				
				
				opcoesMarinha = new ArrayList<Opcoes>();  
				opcoesMarinha.add(te1);  
				opcoesMarinha.add(te2);  
				opcoesMarinha.add(te3);  
				opcoesMarinha.add(te4);  
				
				
				ArrayAdapter<Opcoes> marinhaAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesMarinha);
				marinhaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spMarinha.setAdapter(marinhaAdapter);
				//TERRENO MARINHA ----------------------FIM
		
		
		
		//UTILIZACAO   ----------------------INICIO
		Opcoes u1 = new Opcoes();
		u1.setId(0);
		u1.setNome("Selecione...");
		Opcoes u2 = new Opcoes();
		u2.setId(17);
		u2.setNome("Terreno sem Uso");
		Opcoes u3 = new Opcoes();
		u3.setId(25);
		u3.setNome("Residencial");
		Opcoes u4 = new Opcoes();
		u4.setId(33);
		u4.setNome("Comercial");
		Opcoes u5 = new Opcoes();
		u5.setId(41);
		u5.setNome("Prestação de Serviço");
		Opcoes u6 = new Opcoes();
		u6.setId(50);
		u6.setNome("Serviço Público");
		Opcoes u7 = new Opcoes();
		u7.setId(68);
		u7.setNome("Industrial");
		Opcoes u8 = new Opcoes();
		u8.setId(76);
		u8.setNome("Religioso");
		Opcoes u9 = new Opcoes();
		u9.setId(84);
		u9.setNome("Mista");
		Opcoes u10 = new Opcoes();
		u10.setId(86);
		u10.setNome("Praça");
	
		
		opcoesUtilizacao = new ArrayList<Opcoes>();  
		opcoesUtilizacao.add(u1);  
		opcoesUtilizacao.add(u2);  
		opcoesUtilizacao.add(u3);  
		opcoesUtilizacao.add(u4);
		opcoesUtilizacao.add(u5);
		opcoesUtilizacao.add(u6);
		opcoesUtilizacao.add(u7);
		opcoesUtilizacao.add(u8);
		opcoesUtilizacao.add(u9);
		opcoesUtilizacao.add(u10);
	
		
		
		ArrayAdapter<Opcoes> utilizacaoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesUtilizacao);
		utilizacaoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spUtilizacao.setAdapter(utilizacaoAdapter);
		//UTILIZACAO   ----------------------FIM
		
		//PASSEIO   ----------------------INICIO
		Opcoes pas1 = new Opcoes();
		pas1.setId(0);
		pas1.setNome("Selecione...");
		Opcoes pas2 = new Opcoes();
		pas2.setId(15);
		pas2.setNome("não");
		Opcoes pas3 = new Opcoes();
		pas3.setId(23);
		pas3.setNome("sim");
		
		opcoesPasseio = new ArrayList<Opcoes>();  
		opcoesPasseio.add(pas1);  
		opcoesPasseio.add(pas2);  
		opcoesPasseio.add(pas3);
		
		ArrayAdapter<Opcoes> passeioAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesPasseio);
		passeioAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spPasseio.setAdapter(passeioAdapter);
		//PASSEIO   ----------------------FIM
		
		
		//ARVORE   ----------------------INICIO
				Opcoes ar1 = new Opcoes();
				ar1.setId(0);
				ar1.setNome("Selecione...");
				Opcoes ar2 = new Opcoes();
				ar2.setId(12);
				ar2.setNome("não");
				Opcoes ar3 = new Opcoes();
				ar3.setId(20);
				ar3.setNome("sim");
				
				opcoesArvore = new ArrayList<Opcoes>();  
				opcoesArvore.add(ar1);  
				opcoesArvore.add(ar2);  
				opcoesArvore.add(ar3);
				
				ArrayAdapter<Opcoes> arvoreAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesArvore);
				arvoreAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spArvore.setAdapter(arvoreAdapter);
				//ARVORE   ----------------------FIM
		
				//ELEVADOR   ----------------------INICIO
				Opcoes elev1 = new Opcoes();
				elev1.setId(0);
				elev1.setNome("Selecione...");
				Opcoes elev2 = new Opcoes();
				elev2.setId(10);
				elev2.setNome("não");
				Opcoes elev3 = new Opcoes();
				elev3.setId(28);
				elev3.setNome("sim");
				
				opcoesElevador = new ArrayList<Opcoes>();  
				opcoesElevador.add(elev1);  
				opcoesElevador.add(elev2);  
				opcoesElevador.add(elev3);
				
				ArrayAdapter<Opcoes> ElevadorAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesElevador);
				ElevadorAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spElevador.setAdapter(ElevadorAdapter);
				//ELEVADOR   ----------------------FIM
				
			
				//REDE ÁGUA   ----------------------INICIO
				Opcoes red1 = new Opcoes();
				red1.setId(0);
				red1.setNome("Selecione...");
				Opcoes red2 = new Opcoes();
				red2.setId(17);
				red2.setNome("não");
				Opcoes red3 = new Opcoes();
				red3.setId(25);
				red3.setNome("sim");
				
				opcoesRedeAgua = new ArrayList<Opcoes>();  
				opcoesRedeAgua.add(red1);  
				opcoesRedeAgua.add(red2);  
				opcoesRedeAgua.add(red3);
				
				ArrayAdapter<Opcoes> RedeAguaAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesRedeAgua);
				RedeAguaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spRedeAgua.setAdapter(RedeAguaAdapter);
				//REDE ÁGUA   ----------------------FIM
				
				
				//ESGOTO  ----------------------INICIO
				Opcoes esg1 = new Opcoes();
				esg1.setId(0);
				esg1.setNome("Selecione...");
				Opcoes esg2 = new Opcoes();
				esg2.setId(14);
				esg2.setNome("não");
				Opcoes esg3 = new Opcoes();
				esg3.setId(22);
				esg3.setNome("sim");
				
				opcoesEsgoto = new ArrayList<Opcoes>();  
				opcoesEsgoto.add(esg1);  
				opcoesEsgoto.add(esg2);  
				opcoesEsgoto.add(esg3);
				
				ArrayAdapter<Opcoes> EsgotoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesEsgoto);
				EsgotoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spEsgoto.setAdapter(EsgotoAdapter);
				//ESGOTO   ----------------------FIM
				
				//ELETRICIDADAE  ----------------------INICIO
				Opcoes elet1 = new Opcoes();
				elet1.setId(0);
				elet1.setNome("Selecione...");
				Opcoes elet2 = new Opcoes();
				elet2.setId(11);
				elet2.setNome("não");
				Opcoes elet3 = new Opcoes();
				elet3.setId(20);
				elet3.setNome("sim");
				
				opcoesEletricidade = new ArrayList<Opcoes>();  
				opcoesEletricidade.add(elet1);  
				opcoesEletricidade.add(elet2);  
				opcoesEletricidade.add(elet3);
				
				ArrayAdapter<Opcoes> EletricidadeAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesEletricidade);
				EletricidadeAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spEletricidade.setAdapter(EletricidadeAdapter);
				//ESGOTO   ----------------------FIM
				
		//isentoIptu   ----------------------INICIO
		Opcoes ii1 = new Opcoes();
		ii1.setId(0);
		ii1.setNome("Selecione...");
		Opcoes ii2 = new Opcoes();
		ii2.setId(14);
		ii2.setNome("não");
		Opcoes ii3 = new Opcoes();
		ii3.setId(22);
		ii3.setNome("Imune");
		Opcoes ii4 = new Opcoes();
		ii4.setId(30);
		ii4.setNome("Isento");
		
		
		opcoesIsentoIptu = new ArrayList<Opcoes>();  
		opcoesIsentoIptu.add(ii1);  
		opcoesIsentoIptu.add(ii2);  
		opcoesIsentoIptu.add(ii3); 
		opcoesIsentoIptu.add(ii4);
		
		
		
		ArrayAdapter<Opcoes> isentoiptuAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesIsentoIptu);
		isentoiptuAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spIsentoIptu.setAdapter(isentoiptuAdapter);
		//isentoIptu   ----------------------FIM
		
		//spIsentoTsu   ----------------------INICIO
		Opcoes it1 = new Opcoes();
		it1.setId(0);
		it1.setNome("Selecione...");
		Opcoes it2 = new Opcoes();
		it2.setId(11);
		it2.setNome("não");
		Opcoes it3 = new Opcoes();
		it3.setId(20);
		it3.setNome("sim");
		
		opcoesIsentoTsu = new ArrayList<Opcoes>();  
		opcoesIsentoTsu.add(it1);  
		opcoesIsentoTsu.add(it2);  
		opcoesIsentoTsu.add(it3); 
		
		
		ArrayAdapter<Opcoes> isentotsuAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesIsentoTsu);
		isentotsuAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spIsentoTsu.setAdapter(isentotsuAdapter);
		//spIsentoTsu   ----------------------FIM		
	
		//INFORMACOES GERAIS SOBRE O IMOVEL --------------------FIM
		
		
		
		//INFORMACOES SOBRE O TERRENO --------------------------INICIO
		
		//SITUACAO DE QUADRA   ----------------------INICIO
		Opcoes s1 = new Opcoes();  
		s1.setId(0);  
		s1.setNome("Selecione...");
		Opcoes s2 = new Opcoes();  
		s2.setId(0);  
		s2.setNome("nenhum");
		Opcoes s3 = new Opcoes();  
		s3.setId(16);  
		s3.setNome("Meio de Quadra");
		Opcoes s4 = new Opcoes();  
		s4.setId(24);  
		s4.setNome("Esquina Mais Uma Frente");
		Opcoes s5 = new Opcoes();  
		s5.setId(32);  
		s5.setNome("Vila");
		Opcoes s6 = new Opcoes();  
		s6.setId(40);  
		s6.setNome("Condomínio Horizontal");
		Opcoes s7 = new Opcoes();  
		s7.setId(59);  
		s7.setNome("Encravado");
		Opcoes s8 = new Opcoes();  
		s8.setId(67);  
		s8.setNome("Gleba");
		Opcoes s9 = new Opcoes();  
		s9.setId(75);  
		s9.setNome("Aglomerado");
		
		opcoess = new ArrayList<Opcoes>();  
		opcoess.add(s1);  
		opcoess.add(s2);  
		opcoess.add(s3);  
		opcoess.add(s4);  
		opcoess.add(s5);  
		opcoess.add(s6);
		opcoess.add(s7);
		opcoess.add(s8);	
		opcoess.add(s9);
		
		ArrayAdapter<Opcoes> sitquadraAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoess);
		sitquadraAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spSitQuadra.setAdapter(sitquadraAdapter);
		//SITUACAO DE QUADRA   ----------------------FIM
		
		//TOPOGRAFIA   ----------------------INICIO
		Opcoes t1 = new Opcoes();
		t1.setId(0);
		t1.setNome("Selecione...");
		Opcoes t2 = new Opcoes();
		t2.setId(0);
		t2.setNome("nenhum");
		Opcoes t3 = new Opcoes();
		t3.setId(13);
		t3.setNome("Plano");
		Opcoes t4 = new Opcoes();
		t4.setId(21);
		t4.setNome("Aclive");
		Opcoes t5 = new Opcoes();
		t5.setId(30);
		t5.setNome("Declive");
		Opcoes t6 = new Opcoes();
		t6.setId(48);
		t6.setNome("Irregular");
		
		opcoesTopologia = new ArrayList<Opcoes>();  
		opcoesTopologia.add(t1);  
		opcoesTopologia.add(t2);  
		opcoesTopologia.add(t3);  
		opcoesTopologia.add(t4);  
		opcoesTopologia.add(t5);
		opcoesTopologia.add(t6);
		
		
		ArrayAdapter<Opcoes> topologiaAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesTopologia);
		topologiaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spTopografia.setAdapter(topologiaAdapter);
		//TOPOGRAFIA   ----------------------FIM
		
		//PEDOLOGIA   ----------------------INICIO
		Opcoes p1 = new Opcoes();
		p1.setId(0);
		p1.setNome("Selecione...");
		Opcoes p2 = new Opcoes();
		p2.setId(0);
		p2.setNome("nenhum");
		Opcoes p3 = new Opcoes();
		p3.setId(10);
		p3.setNome("Inundável");
		Opcoes p4 = new Opcoes();
		p4.setId(29);
		p4.setNome("Firme");
		Opcoes p5 = new Opcoes();
		p5.setId(86);
		p5.setNome("Alagado");
		Opcoes p6 = new Opcoes();
		p6.setId(4);
		p6.setNome("Mangue");
		Opcoes p7 = new Opcoes();
		p7.setId(87);
		p7.setNome("Rochoso");
		Opcoes p8 = new Opcoes();
		p8.setId(90);
		p8.setNome("Arenoso");
		Opcoes p9 = new Opcoes();
		p9.setId(91);
		p9.setNome("Duna");
		
		opcoesPedologia = new ArrayList<Opcoes>();  
		opcoesPedologia.add(p1);  
		opcoesPedologia.add(p2);  
		opcoesPedologia.add(p3);  
		opcoesPedologia.add(p4);  
		opcoesPedologia.add(p5);
		opcoesPedologia.add(p6);
		opcoesPedologia.add(p7);
		opcoesPedologia.add(p8);
		opcoesPedologia.add(p9);
		
		ArrayAdapter<Opcoes> pedologiaAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesPedologia);
		pedologiaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spPedologia.setAdapter(pedologiaAdapter);
		//PEDOLOGIA   ----------------------FIM
		//INFORMACOES GERAIS SOBRE O IMOVEL -----------------------FIM
		
		

		//INFORMACOES SOBRE A EDIFICACAO-----------------------INICIO
		//spTipo   ----------------------INICIO
		Opcoes tipo1 = new Opcoes();
		tipo1.setId(0);
		tipo1.setNome("Selecione...");
		Opcoes tipo2 = new Opcoes();
		tipo2.setId(15);
		tipo2.setNome("Casa");
		Opcoes tipo3 = new Opcoes();
		tipo3.setId(31);
		tipo3.setNome("Apartamento");
		Opcoes tipo4 = new Opcoes();
		tipo4.setId(65);
		tipo4.setNome("Galpão");
		Opcoes tipo5 = new Opcoes();
		tipo5.setId(74);
		tipo5.setNome("Telheiro");
		Opcoes tipo6 = new Opcoes();
		tipo6.setId(87);
		tipo6.setNome("Especial");
		Opcoes tipo7 = new Opcoes();
		tipo7.setId(10);
		tipo7.setNome("Cômodo");
		Opcoes tipo8 = new Opcoes();
		tipo8.setId(11);
		tipo8.setNome("Barraco");
		Opcoes tipo9 = new Opcoes();
		tipo9.setId(85);
		tipo9.setNome("Sala Loja");
		
		
		opcoesTipo= new ArrayList<Opcoes>();  
		opcoesTipo.add(tipo1);  
		opcoesTipo.add(tipo2);  
		opcoesTipo.add(tipo3); 
		opcoesTipo.add(tipo4); 
		opcoesTipo.add(tipo5); 
		opcoesTipo.add(tipo6); 
		opcoesTipo.add(tipo7); 
		opcoesTipo.add(tipo8); 
		opcoesTipo.add(tipo9); 
		
		ArrayAdapter<Opcoes> tipoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesTipo);
		tipoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spTipo.setAdapter(tipoAdapter);
		//spTipo   ----------------------FIM
		
		//spAlinhamento   ----------------------INICIO
		Opcoes a1 = new Opcoes();
		a1.setId(0);
		a1.setNome("Selecione...");
		Opcoes a2 = new Opcoes();
		a2.setId(12);
		a2.setNome("Alinhada");
		Opcoes a3 = new Opcoes();
		a3.setId(20);
		a3.setNome("Recuada");
		
		opcoesAlinhamento= new ArrayList<Opcoes>();  
		opcoesAlinhamento.add(a1);  
		opcoesAlinhamento.add(a2);  
		opcoesAlinhamento.add(a3); 
		
		
		ArrayAdapter<Opcoes> alinhamentoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesAlinhamento);
		alinhamentoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spAlinhamento.setAdapter(alinhamentoAdapter);
		//spAlinhamento   ----------------------FIM
		
		//spLocacao   ----------------------INICIO
		Opcoes po1 = new Opcoes();
		po1.setId(0);
		po1.setNome("Selecione...");
		Opcoes po2 = new Opcoes();
		po2.setId(10);
		po2.setNome("Isolada");
		Opcoes po3 = new Opcoes();
		po3.setId(28);
		po3.setNome("Conjugada");
		Opcoes po4 = new Opcoes();
		po4.setId(36);
		po4.setNome("Germinada");
		
		opcoesLocacao= new ArrayList<Opcoes>();  
		opcoesLocacao.add(po1);  
		opcoesLocacao.add(po2);  
		opcoesLocacao.add(po3); 
		opcoesLocacao.add(po4); 
		
		
		ArrayAdapter<Opcoes> locacaoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesLocacao);
		locacaoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spLocacao.setAdapter(locacaoAdapter);
		//spLocacao   ----------------------FIM
		
		//spSituacao   ----------------------INICIO
		Opcoes si1 = new Opcoes();
		si1.setId(0);
		si1.setNome("Selecione...");
		Opcoes si2 = new Opcoes();
		si2.setId(17);
		si2.setNome("Frente");
		Opcoes si3 = new Opcoes();
		si3.setId(25);
		si3.setNome("Fundos");
		Opcoes si4 = new Opcoes();
		si4.setId(33);
		si4.setNome("Superposta Frente");
		Opcoes si5 = new Opcoes();
		si5.setId(41);
		si5.setNome("Superposta Fundos");
		Opcoes si6 = new Opcoes();
		si6.setId(50);
		si6.setNome("Sobre Loja");
		Opcoes si7 = new Opcoes();
		si7.setId(68);
		si7.setNome("Sub solo");
		Opcoes si8 = new Opcoes();
		si8.setId(76);
		si8.setNome("Galeria");
		Opcoes si9 = new Opcoes();
		si9.setId(86);
		si9.setNome("Terraco");
		
		opcoesSituacao= new ArrayList<Opcoes>();  
		opcoesSituacao.add(si1);  
		opcoesSituacao.add(si2);  
		opcoesSituacao.add(si3);  
		opcoesSituacao.add(si4);  
		opcoesSituacao.add(si5); 
		opcoesSituacao.add(si6); 
		opcoesSituacao.add(si7); 
		opcoesSituacao.add(si8);
		opcoesSituacao.add(si9);
		
		ArrayAdapter<Opcoes> situacaoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesSituacao);
		situacaoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spSituacao.setAdapter(situacaoAdapter);
		//spSituacao   ----------------------FIM
		
		
		//spTp_ocupacao   ----------------------INICIO
		Opcoes ocpt1 = new Opcoes();
		ocpt1.setId(0);
		ocpt1.setNome("Selecione...");
		Opcoes ocpt2 = new Opcoes();
		ocpt2.setId(14);
		ocpt2.setNome("Ocupado");
		Opcoes ocpt3 = new Opcoes();
		ocpt3.setId(28);
		ocpt3.setNome("Casa Veraneio");
		Opcoes ocpt4 = new Opcoes();
		ocpt4.setId(30);
		ocpt4.setNome("Vago");
		Opcoes ocpt5 = new Opcoes();
		ocpt5.setId(22);
		ocpt5.setNome("Fechado");
		
		
		opcoesTpOcupacao= new ArrayList<Opcoes>();  
		opcoesTpOcupacao.add(ocpt1);  
		opcoesTpOcupacao.add(ocpt2);  
		opcoesTpOcupacao.add(ocpt3);  
		opcoesTpOcupacao.add(ocpt4);  
		opcoesTpOcupacao.add(ocpt5); 
		
		
		ArrayAdapter<Opcoes> tp_Adapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item,opcoesTpOcupacao);
		tp_Adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spTp_ocupacao.setAdapter(tp_Adapter);
		//spSituacao   ----------------------FIM
		
		
		
		//spEstrutura   ----------------------INICIO
		Opcoes e1 = new Opcoes();
		e1.setId(0);
		e1.setNome("Selecione...");
		Opcoes e2 = new Opcoes();
		e2.setId(20);
		e2.setNome("Madeira");
		Opcoes e3 = new Opcoes();
		e3.setId(38);
		e3.setNome("Metálica");
		Opcoes e4 = new Opcoes();
		e4.setId(86);
		e4.setNome("Alvenaria-Concreto");
		Opcoes e5 = new Opcoes();
		e5.setId(87);
		e5.setNome("Mista");
	
		
		opcoesEstrutura= new ArrayList<Opcoes>();  
		opcoesEstrutura.add(e1);  
		opcoesEstrutura.add(e2);  
		opcoesEstrutura.add(e3);
		opcoesEstrutura.add(e4); 
		opcoesEstrutura.add(e5); 

		
		ArrayAdapter<Opcoes> estruturaAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesEstrutura);
		estruturaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spEstrutura.setAdapter(estruturaAdapter);
		//spEstrutura   ----------------------FIM
		
		//spCobertura   ----------------------INICIO
		Opcoes c1 = new Opcoes();
		c1.setId(0);
		c1.setNome("Selecione...");
		Opcoes c2 = new Opcoes();
		c2.setId(35);
		c2.setNome("Barro");
		Opcoes c3 = new Opcoes();
		c3.setId(43);
		c3.setNome("Laje");
		Opcoes c4 = new Opcoes();
		c4.setId(86);
		c4.setNome("Especial");
		Opcoes c5 = new Opcoes();
		c5.setId(27);
		c5.setNome("Cimento Amianto");
		Opcoes c6 = new Opcoes();
		c6.setId(19);
		c6.setNome("Zinco Metalica");
		
		
		opcoesCobertura= new ArrayList<Opcoes>();  
		opcoesCobertura.add(c1);  
		opcoesCobertura.add(c2);  
		opcoesCobertura.add(c3);
		opcoesCobertura.add(c4); 
		opcoesCobertura.add(c5); 
		opcoesCobertura.add(c6); 
		
		ArrayAdapter<Opcoes> coberturaAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesCobertura);
		coberturaAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spCobertura.setAdapter(coberturaAdapter);
		//spCobertura   ----------------------FIM
		
		//spParedes   ----------------------INICIO
		Opcoes paredes1 = new Opcoes();
		paredes1.setId(0);
		paredes1.setNome("Selecione...");
		Opcoes paredes2 = new Opcoes();
		paredes2.setId(10);
		paredes2.setNome("Sem");
		Opcoes paredes3 = new Opcoes();
		paredes3.setId(36);
		paredes3.setNome("Alvenaria");
		Opcoes paredes4 = new Opcoes();
		paredes4.setId(44);
		paredes4.setNome("Madeira");
		Opcoes paredes5 = new Opcoes();
		paredes5.setId(86);
		paredes5.setNome("Refugos Madeira Aproveitada");
		Opcoes paredes6= new Opcoes();
		paredes6.setId(28);
		paredes6.setNome("Taipa");
		
		
		opcoesParedes= new ArrayList<Opcoes>();  
		opcoesParedes.add(paredes1);  
		opcoesParedes.add(paredes2);  
		opcoesParedes.add(paredes3);
		opcoesParedes.add(paredes4); 
		opcoesParedes.add(paredes5);
		opcoesParedes.add(paredes6);
		
		ArrayAdapter<Opcoes> paredesAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesParedes);
		paredesAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spParedes.setAdapter(paredesAdapter);
		//spParedes   ----------------------FIM
		
		//spVedacoesEsquadrias   ----------------------INICIO
		Opcoes f1 = new Opcoes();
		f1.setId(0);
		f1.setNome("Selecione...");
		Opcoes f2 = new Opcoes();
		f2.setId(16);
		f2.setNome("Madeira");
		Opcoes f3 = new Opcoes();
		f3.setId(24);
		f3.setNome("Ferro");
		Opcoes f4 = new Opcoes();
		f4.setId(32);
		f4.setNome("Alumínio");
		Opcoes f5 = new Opcoes();
		f5.setId(86);
		f5.setNome("Especial");
		
		opcoesVedacoesEsquadrias= new ArrayList<Opcoes>();  
		opcoesVedacoesEsquadrias.add(f1);  
		opcoesVedacoesEsquadrias.add(f2);  
		opcoesVedacoesEsquadrias.add(f3);
		opcoesVedacoesEsquadrias.add(f4); 
		opcoesVedacoesEsquadrias.add(f5);
		
		ArrayAdapter<Opcoes> vedacoesEsquadriasAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesVedacoesEsquadrias);
		vedacoesEsquadriasAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spVedacoesEsquadrias.setAdapter(vedacoesEsquadriasAdapter);
		//spVedacoesEsquadrias   ----------------------FIM
		
		//spRevExterno   ----------------------INICIO
		Opcoes r1 = new Opcoes();
		r1.setId(0);
		r1.setNome("Selecione...");
		Opcoes r2 = new Opcoes();
		r2.setId(14);
		r2.setNome("Sem");
		Opcoes r3 = new Opcoes();
		r3.setId(30);
		r3.setNome("Rebôco");
		Opcoes r4 = new Opcoes();
		r4.setId(49);
		r4.setNome("Material Cerâmico");
		Opcoes r5 = new Opcoes();
		r5.setId(57);
		r5.setNome("Madeira");
		Opcoes r6 = new Opcoes();
		r6.setId(86);
		r6.setNome("Pedra Natural");
		Opcoes r7 = new Opcoes();
		r7.setId(67);
		r7.setNome("Especial");
		
		opcoesRevExterno= new ArrayList<Opcoes>();  
		opcoesRevExterno.add(r1);  
		opcoesRevExterno.add(r2);  
		opcoesRevExterno.add(r3);
		opcoesRevExterno.add(r4); 
		opcoesRevExterno.add(r5);
		opcoesRevExterno.add(r6);  
		opcoesRevExterno.add(r7);
		
		ArrayAdapter<Opcoes> revexternoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesRevExterno);
		revexternoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spRevExterno.setAdapter(revexternoAdapter);
		//spRevExterno   ----------------------FIM
		
		//spPadraoConstrucao   ----------------------INICIO
		Opcoes is1 = new Opcoes();
		is1.setId(0);
		is1.setNome("Selecione...");
		Opcoes is2 = new Opcoes();
		is2.setId(30);
		is2.setNome("Inferior");
		Opcoes is3 = new Opcoes();
		is3.setId(21);
		is3.setNome("Normal");
		Opcoes is4 = new Opcoes();
		is4.setId(13);
		is4.setNome("Luxo");
		
		
		opcoesPadraoConstrucao= new ArrayList<Opcoes>();
		opcoesPadraoConstrucao.add(is1);
		opcoesPadraoConstrucao.add(is2);
		opcoesPadraoConstrucao.add(is3);
		opcoesPadraoConstrucao.add(is4);
		
		
		
		ArrayAdapter<Opcoes> spPadraoConstrucaoAdapter = new ArrayAdapter<Opcoes>(this, android.R.layout.simple_spinner_item, opcoesPadraoConstrucao);
		spPadraoConstrucaoAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spPadraoConstrucao.setAdapter(spPadraoConstrucaoAdapter);
		//spPadraoConstrucao   ----------------------FIM
		
		
		
		//INFORMACOES SOBRE A EDIFICACAO-----------------------FIM
		
		
	}

}
