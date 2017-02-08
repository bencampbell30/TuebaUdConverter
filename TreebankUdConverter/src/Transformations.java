import java.util.ArrayList;

public class Transformations 
{
	private static Transformations instance = null;
	
	//These relations need to be specifically handled in the code, ie. not automatically
	private ArrayList<RelationTemplate> HDKONJKONJ_hdc1c2;
	private ArrayList<RelationTemplate> KONJKONJ_hdconj;
	private ArrayList<RelationTemplate> PREDPREDMOD_PREDacl;
	private ArrayList<RelationTemplate> PREDPREDMOD_PREDadvcl;
	private ArrayList<RelationTemplate> ONSIMPXVPASS_csubjpassVPASS;
	private ArrayList<RelationTemplate> ONVPASS_nsubjpassVPASS;
	private ArrayList<RelationTemplate> APPAPP_HDappos;
	private ArrayList<RelationTemplate> NE_HDflat;
	private ArrayList<RelationTemplate> N_APP;
	private ArrayList<RelationTemplate> NX_APP;
	private ArrayList<RelationTemplate> N_SIMPX_acl;
	private ArrayList<RelationTemplate> SIMPX_advcl;
	private ArrayList<RelationTemplate> HDFM_HDforeign;
	private ArrayList<RelationTemplate> FMFM_HDforeign;
	private ArrayList<RelationTemplate> FM_foreign;
	private ArrayList<RelationTemplate> FX_foreign;
	
	//Automatically processed relations
	private ArrayList<TransformationPair> autoProcessedMultipleTemplates;
	private ArrayList<TransformationPair> autoProcessedSingleTemplates;

	private Transformations() 
	{
		initializeTemplates();
	}

	public static Transformations getInstance() 
	{
		if (instance == null)
			instance = new Transformations();
		return instance;
	}

	private void initializeTemplates() 
	{
		// *************FUNCTIONS************************
		
		// fun -
		ArrayList<String> arrayListBlank = new ArrayList<String>();
		arrayListBlank.add("-");
		
		// fun KONJ
		ArrayList<String> arrayListKONJ = new ArrayList<String>();
		arrayListKONJ.add("KONJ");
		
		// fun KONJ2
		ArrayList<String> arrayListKONJ2 = new ArrayList<String>();
		arrayListKONJ2.add("KONJ2");
		
		// fun Edge Labels in split coordinations
		ArrayList<String> arrayListAllKMod = new ArrayList<String>();
		arrayListAllKMod.add("ONK");
		arrayListAllKMod.add("OAK");
		arrayListAllKMod.add("ODK");
		arrayListAllKMod.add("OGK");
		arrayListAllKMod.add("OPPK");
		arrayListAllKMod.add("FOPPK");
		arrayListAllKMod.add("OSK");
		arrayListAllKMod.add("OADVPK");
		arrayListAllKMod.add("OA-MODK");
		arrayListAllKMod.add("MODK");
		arrayListAllKMod.add("V-MODK");

		// fun PRED
		ArrayList<String> arrayListPredFun = new ArrayList<String>();
		arrayListPredFun.add("PRED");

		// fun PRED-MOD
		ArrayList<String> arrayListPredModFun = new ArrayList<String>();
		arrayListPredModFun.add("PRED-MOD");

		// fun PRED-KOKOM
		ArrayList<String> arrayListPredKokomFun = new ArrayList<String>();
		arrayListPredKokomFun.add("PRED-KOKOM");
		
		// fun PREDWITHKOKOM
		ArrayList<String> arrayListPredWithKokomFun = new ArrayList<String>();
		arrayListPredWithKokomFun.add("PREDWITHKOKOM");
		
		// fun PREDALL
		ArrayList<String> arrayListPredAllFun = new ArrayList<String>();
		arrayListPredAllFun.add("PRED");
		arrayListPredAllFun.add("PREDWITHKOKOM");
		
		// fun OBL
		ArrayList<String> arrayListOblFun = new ArrayList<String>();
		arrayListOblFun.add("OBL");

		// fun HD
		ArrayList<String> arrayListHdFun = new ArrayList<String>();
		arrayListHdFun.add("HD");

		// fun VC-HD
		ArrayList<String> arrayListVcHdFun = new ArrayList<String>();
		arrayListVcHdFun.add("VC-HD");

		// fun ON
		ArrayList<String> arrayListOnFun = new ArrayList<String>();
		arrayListOnFun.add("ON");

		// fun OS
		ArrayList<String> arrayListOsFun = new ArrayList<String>();
		arrayListOsFun.add("OS");
		
		// fun OSC
		ArrayList<String> arrayListOscFun = new ArrayList<String>();
		arrayListOscFun.add("OSC");

		// fun OA
		ArrayList<String> arrayListOaFun = new ArrayList<String>();
		arrayListOaFun.add("OA");
		
		// fun Object
		ArrayList<String> arrayListObjFun = new ArrayList<String>();
		arrayListObjFun.add("OA");
		arrayListObjFun.add("OS");
		arrayListObjFun.add("OSC");

		// fun OD
		ArrayList<String> arrayListOdFun = new ArrayList<String>();
		arrayListOdFun.add("OD");

		// fun OG
		ArrayList<String> arrayListOgFun = new ArrayList<String>();
		arrayListOgFun.add("OG");
		
		// fun IOBJ
		ArrayList<String> arrayListIobjFun = new ArrayList<String>();
		arrayListIobjFun.add("OD");
		arrayListIobjFun.add("OG");

		// fun GEN
		ArrayList<String> arrayListGenFun = new ArrayList<String>();
		arrayListGenFun.add("GEN");

		// fun OV
		ArrayList<String> arrayListOvFun = new ArrayList<String>();
		arrayListOvFun.add("OV");
		
		// fun VERB
		ArrayList<String> arrayListVerbFun = new ArrayList<String>();
		arrayListVerbFun.add("HD");
		arrayListVerbFun.add("OV");
		arrayListVerbFun.add("VC-HD");
		
		// fun PARA
		ArrayList<String> arrayListParaFun = new ArrayList<String>();
		arrayListParaFun.add("PARA");
		
		// fun V-MOD
		ArrayList<String> arrayListVModFun = new ArrayList<String>();
		arrayListVModFun.add("V-MOD");

		// fun Noun MOD
		ArrayList<String> arrayListNounModFun = new ArrayList<String>();
		arrayListNounModFun.add("ON-MOD");
		arrayListNounModFun.add("OA-MOD");
		arrayListNounModFun.add("OD-MOD");
		arrayListNounModFun.add("OG-MOD");
		
		// fun OS-MOD
		ArrayList<String> arrayListOsModFun = new ArrayList<String>();
		arrayListOsModFun.add("OS-MOD");

		// fun All MOD
		ArrayList<String> arrayListAllModFun = new ArrayList<String>();
		arrayListAllModFun.add("ON-MOD");
		arrayListAllModFun.add("OA-MOD");
		arrayListAllModFun.add("OD-MOD");
		arrayListAllModFun.add("OG-MOD");
		arrayListAllModFun.add("MOD");
		arrayListAllModFun.add("OS-MOD");
		arrayListAllModFun.add("OPP-MOD");
		arrayListAllModFun.add("FOPP-MOD");
		arrayListAllModFun.add("PRED-MOD");
		arrayListAllModFun.add("OADJP-MO");
		arrayListAllModFun.add("OADVP-MO");
		arrayListAllModFun.add("V-MOD");
		arrayListAllModFun.add("MOD-MOD");

		// fun APP
		ArrayList<String> arrayListAppFun = new ArrayList<String>();
		arrayListAppFun.add("APP");
		
		// fun VPT
		ArrayList<String> arrayListVptFun = new ArrayList<String>();
		arrayListVptFun.add("VPT");
		
		// fun ES
		ArrayList<String> arrayListEsFun = new ArrayList<String>();
		arrayListEsFun.add("ES");
		
		// fun OS/ON-MOD
		ArrayList<String> arrayListOsOnModFun = new ArrayList<String>();
		arrayListOsOnModFun.add("OS-MOD");
		arrayListOsOnModFun.add("ON-MOD");
		
		// fun NEG_DET
		ArrayList<String> arrayListNegDetFun = new ArrayList<String>();
		arrayListNegDetFun.add("NEG_DET");
		
		// fun NEG_ADV
		ArrayList<String> arrayListNegAdvFun = new ArrayList<String>();
		arrayListNegAdvFun.add("NEG_ADV");
		
		// fun OAD
		ArrayList<String> arrayListOadFun = new ArrayList<String>();
		arrayListOadFun.add("OADJP");
		arrayListOadFun.add("OADVP");
		
		// fun OPP
		ArrayList<String> arrayListOppFun = new ArrayList<String>();
		arrayListOppFun.add("FOPP");
		arrayListOppFun.add("OPP");

		// *************NODE NAMES************************
		// node SIMPX
		ArrayList<String> arrayListSimpxNode = new ArrayList<String>();
		arrayListSimpxNode.add("SIMPX");

		// node R-SIMPX
		ArrayList<String> arrayListRSimpxNode = new ArrayList<String>();
		arrayListRSimpxNode.add("R-SIMPX");
		
		// node P-SIMPX
		ArrayList<String> arrayListPSimpxNode = new ArrayList<String>();
		arrayListPSimpxNode.add("P-SIMPX");

		// node PX
		ArrayList<String> arrayListPxNode = new ArrayList<String>();
		arrayListPxNode.add("PX");
		
		// node NX
		ArrayList<String> arrayListNxNode = new ArrayList<String>();
		arrayListNxNode.add("NX");
		
		// node FX
		ArrayList<String> arrayListFxNode = new ArrayList<String>();
		arrayListFxNode.add("FX");
		
		// node DP
		ArrayList<String> arrayListDpNode = new ArrayList<String>();
		arrayListDpNode.add("DP");

		// node ADJX
		ArrayList<String> arrayListAdjxNode = new ArrayList<String>();
		arrayListAdjxNode.add("ADJX");

		// node ADVX
		ArrayList<String> arrayListAdvxNode = new ArrayList<String>();
		arrayListAdvxNode.add("ADVX");
		
		// node VXINF
		ArrayList<String> arrayListVxInfNode = new ArrayList<String>();
		arrayListAdvxNode.add("VXINF");
		
		// node WORD
		ArrayList<String> arrayListWordNode = new ArrayList<String>();
		arrayListWordNode.add("WORD");

		// Node DM
		ArrayList<String> arrayListDmNode = new ArrayList<String>();
		arrayListDmNode.add("DM");

		// *************POS************************
		// fun APPR
		ArrayList<String> arrayListAdpPos = new ArrayList<String>();
		arrayListAdpPos.add("APPR");
		arrayListAdpPos.add("APPO");
				
		// pos CARD
		ArrayList<String> arrayListCard = new ArrayList<String>();
		arrayListCard.add("CARD");
				
		// pos N*
		ArrayList<String> arrayListNPos = new ArrayList<String>();
		arrayListNPos.add("NN");
		arrayListNPos.add("NE");
		
		// pos ADJ*
		ArrayList<String> arrayListAdjPos = new ArrayList<String>();
		arrayListAdjPos.add("ADJA");
		arrayListAdjPos.add("ADJD");
		
		// pos ADV_NEG
		ArrayList<String> arrayListAdvNegPos = new ArrayList<String>();
		arrayListAdvNegPos.add("ADV_NEG");
		
		// pos ADJADV*
		ArrayList<String> arrayListAdjAdvPos = new ArrayList<String>();
		arrayListAdjAdvPos.add("ADJA");
		arrayListAdjAdvPos.add("ADJD");
		arrayListAdjAdvPos.add("ADV");
		
		// pos NE
		ArrayList<String> arrayListNEPos = new ArrayList<String>();
		arrayListNEPos.add("NE");
		
		// pos PPER
		ArrayList<String> arrayListPperPos = new ArrayList<String>();
		arrayListPperPos.add("PPER");
		
		// pos PROP
		ArrayList<String> arrayListPropPos = new ArrayList<String>();
		arrayListPropPos.add("PROP");
		
		// pos Determiner Pronouns
		ArrayList<String> arrayListDetPronPos = new ArrayList<String>();
		arrayListDetPronPos.add("PDAT");
		arrayListDetPronPos.add("PIDAT");
		arrayListDetPronPos.add("PIS");
		arrayListDetPronPos.add("PIAT");
		arrayListDetPronPos.add("PWAT");
		arrayListDetPronPos.add("PRELAT");

		// pos ART
		ArrayList<String> arrayListArtPos = new ArrayList<String>();
		arrayListArtPos.add("ART");

		// pos PPOSAT
		ArrayList<String> arrayListPposatPos = new ArrayList<String>();
		arrayListPposatPos.add("PPOSAT");

		// pos KOUS
		ArrayList<String> arrayListKousPos = new ArrayList<String>();
		arrayListKousPos.add("KOUS");
		arrayListKousPos.add("KOUI");

		// pos FM
		ArrayList<String> arrayListFmPos = new ArrayList<String>();
		arrayListFmPos.add("FM");

		// pos KON
		ArrayList<String> arrayListKonPos = new ArrayList<String>();
		arrayListKonPos.add("KON");
		
		// pos KOKOM
		ArrayList<String> arrayListKokomPos = new ArrayList<String>();
		arrayListKokomPos.add("KOKOM");
		
		// pos PTKNEG
		ArrayList<String> arrayListPtkNegPos = new ArrayList<String>();
		arrayListPtkNegPos.add("PTKNEG");
		
		// pos PTKZU
		ArrayList<String> arrayListPtkZuPos = new ArrayList<String>();
		arrayListPtkZuPos.add("PTKZU");
		
		// pos PTKVZ
		ArrayList<String> arrayListPtkVzPos = new ArrayList<String>();
		arrayListPtkVzPos.add("PTKVZ");
		
		// pos PTKANT
		ArrayList<String> arrayListPtkDisPos = new ArrayList<String>();
		arrayListPtkDisPos.add("PTKANT");
		arrayListPtkDisPos.add("ITJ");
		
		// pos PTKZU-PASS
		ArrayList<String> arrayListPtkZuPassPos = new ArrayList<String>();
		arrayListPtkZuPassPos.add("PTKZU-PASS");
		
		// pos PTKZU-LASS
		ArrayList<String> arrayListPtkZuLassPos = new ArrayList<String>();
		arrayListPtkZuLassPos.add("PTKZU-LASS");
		
		// pos PTKZU-ALL
		ArrayList<String> arrayListPtkZuAllPos = new ArrayList<String>();
		arrayListPtkZuAllPos.add("PTKZU");
		arrayListPtkZuAllPos.add("PTKZU-LASS");
		arrayListPtkZuAllPos.add("PTKZU-PASS");
		
		// pos PTKA
		ArrayList<String> arrayListPtkAPos = new ArrayList<String>();
		arrayListPtkAPos.add("PTKA");
		
		// pos APZR
		ArrayList<String> arrayListApzrPos = new ArrayList<String>();
		arrayListApzrPos.add("APZR");
		
		// pos TRUNC
		ArrayList<String> arrayListTruncPos = new ArrayList<String>();
		arrayListTruncPos.add("TRUNC");
		
		// pos ZUWORD
		ArrayList<String> arrayListZuWordPos = new ArrayList<String>();
		arrayListZuWordPos.add("PTKZU");
		arrayListZuWordPos.add("VVIZU");

		// pos Verb Infinitive
		ArrayList<String> arrayListVInfPos = new ArrayList<String>();
		arrayListVInfPos.add("VVINF");
		arrayListVInfPos.add("VVIZU");
		arrayListVInfPos.add("VAINF");
		arrayListVInfPos.add("VMINF");
		arrayListVInfPos.add("VVINF_PASS");
		arrayListVInfPos.add("VVIZU_PASS");
		arrayListVInfPos.add("VAINF_PASS");
		arrayListVInfPos.add("VMINF_PASS");
		arrayListVInfPos.add("VVINF_LASSEN");
		arrayListVInfPos.add("VVIZU_LASSEN");
		arrayListVInfPos.add("VAINF_LASSEN");
		arrayListVInfPos.add("VMINF_LASSEN");
		

		// pos Verb Passive
		ArrayList<String> arrayListVerbPassivePos = new ArrayList<String>();
		arrayListVerbPassivePos.add("VVFIN_PASS");
		arrayListVerbPassivePos.add("VVIMP_PASS");
		arrayListVerbPassivePos.add("VVINF_PASS");
		arrayListVerbPassivePos.add("VVIZU_PASS");
		arrayListVerbPassivePos.add("VVPP_PASS");
		arrayListVerbPassivePos.add("VAFIN_PASS");
		arrayListVerbPassivePos.add("VAIMP_PASS");
		arrayListVerbPassivePos.add("VAINF_PASS");
		arrayListVerbPassivePos.add("VAPP_PASS");
		arrayListVerbPassivePos.add("VMFIN_PASS");
		arrayListVerbPassivePos.add("VMINF_PASS");
		arrayListVerbPassivePos.add("VMPP_PASS");
		arrayListVerbPassivePos.add("PTKZU-PASS");
		
		// pos Verb Lassen
		ArrayList<String> arrayListVerbLassenPos = new ArrayList<String>();
		arrayListVerbLassenPos.add("VVFIN_LASSEN");
		arrayListVerbLassenPos.add("VVIMP_LASSEN");
		arrayListVerbLassenPos.add("VVINF_LASSEN");
		arrayListVerbLassenPos.add("VVIZU_LASSEN");
		arrayListVerbLassenPos.add("VVPP_LASSEN");
		arrayListVerbLassenPos.add("VAFIN_LASSEN");
		arrayListVerbLassenPos.add("VAIMP_LASSEN");
		arrayListVerbLassenPos.add("VAINF_LASSEN");
		arrayListVerbLassenPos.add("VAPP_LASSEN");
		arrayListVerbLassenPos.add("VMFIN_LASSEN");
		arrayListVerbLassenPos.add("VMINF_LASSEN");
		arrayListVerbLassenPos.add("VMPP_LASSEN");
		arrayListVerbLassenPos.add("PTKZU-LASS");
		
		// pos Verb Main
		ArrayList<String> arrayListVerbMainPos = new ArrayList<String>();
		arrayListVerbMainPos.add("VVFIN");
		arrayListVerbMainPos.add("VVIMP");
		arrayListVerbMainPos.add("VVINF");
		arrayListVerbMainPos.add("VVIZU");
		arrayListVerbMainPos.add("VVPP");
		
		// pos Verb Modal/Aux
		ArrayList<String> arrayListVerbAuxPos = new ArrayList<String>();
		arrayListVerbAuxPos.add("VAFIN");
		arrayListVerbAuxPos.add("VAIMP");
		arrayListVerbAuxPos.add("VAINF");
		arrayListVerbAuxPos.add("VAPP");
		arrayListVerbAuxPos.add("VMFIN");
		arrayListVerbAuxPos.add("VMINF");
		arrayListVerbAuxPos.add("VMPP");
		
		// pos Verb VVFIN
		ArrayList<String> arrayListVerbVVFINPos = new ArrayList<String>();
		arrayListVerbVVFINPos.add("VVFIN");
		
		// pos Verb VVPP
		ArrayList<String> arrayListVerbVVPPPos = new ArrayList<String>();
		arrayListVerbVVPPPos.add("VVPP");

		// KONJ
		RelationTemplate templateKONJ = new RelationTemplate(arrayListKONJ, null, null, true, false, false);
		// KONJ
		RelationTemplate templateKONJ2 = new RelationTemplate(arrayListKONJ2, null, null, true, false, false);
		// [,WORD,KON]
		RelationTemplate templateWordKON = new RelationTemplate(null, arrayListWordNode, arrayListKonPos, false, true, true);
		// KOKOM
		RelationTemplate templateKOKOM = new RelationTemplate(null, arrayListWordNode, arrayListKokomPos, false, true, true);
		// PRED
		RelationTemplate templatePred = new RelationTemplate(arrayListPredFun, null, null, true, false, false);
		// [PRED,PX,]
		RelationTemplate templatePredPx = new RelationTemplate(arrayListPredAllFun, arrayListPxNode, null, true, true, false);
		// PRED-MOD
		RelationTemplate templatePredMod = new RelationTemplate(arrayListPredModFun, arrayListSimpxNode, null, true, true, false);
		// PRED-KOKOM
		RelationTemplate templatePredKokom = new RelationTemplate(arrayListPredKokomFun, null, arrayListKokomPos, true, false, true);
		// PREDWITHKOKOM
		RelationTemplate templatePredWithKokom = new RelationTemplate(arrayListPredWithKokomFun, null, null, true, false, false);
		// PREDALL
		RelationTemplate templatePredAll = new RelationTemplate(arrayListPredAllFun, null, null, true, false, false);
		// OBL
		RelationTemplate templateObl = new RelationTemplate(arrayListOblFun, null, null, true, false, false);
		// [PRED,,N*]
		RelationTemplate templatePredN = new RelationTemplate(arrayListPredWithKokomFun, null, arrayListNPos, true, false,
				true);
		// [,,NE]
		RelationTemplate templateNE = new RelationTemplate(null, arrayListWordNode, arrayListNEPos, false, true,
		true);
		// [,,N*]
		RelationTemplate templateN = new RelationTemplate(null, null, arrayListNPos, false, false,
		true);
		// ON
		RelationTemplate templateON = new RelationTemplate(arrayListOnFun, null, null, true, false, false);
		// [ON,SIMPX,]
		RelationTemplate templateOnSimpx = new RelationTemplate(arrayListOnFun, arrayListSimpxNode, null, true, true,
				false);
		// [(ON-MOD, OS-MOD),,PPER]
		RelationTemplate templateEsModPper = new RelationTemplate(arrayListOsOnModFun, null, arrayListPperPos,
				true, false, true);
		// [OS-MOD,,PPER]
		RelationTemplate templateOsModPper = new RelationTemplate(arrayListOsModFun, null, arrayListPperPos,
				true, false, true);
		// [-,,(PDAT,PIDAT,PIS,PIAT,PWAT)]
		RelationTemplate templateDetPron = new RelationTemplate(arrayListBlank, null, arrayListDetPronPos,
				true, false, true);
		// [,WORD,(PDAT,PIDAT,PIS,PIAT,PWAT)]
		RelationTemplate templateWordDetPron = new RelationTemplate(null, arrayListWordNode, arrayListDetPronPos,
				false, true, true);
		// [(ON-MOD, OA-MOD, OD-MOD, OG-MOD),SIMPX,]
		RelationTemplate templateNounModSimpx = new RelationTemplate(arrayListNounModFun, arrayListSimpxNode, null,
				true, true, false);
		// [MOD*,SIMPX,]
		RelationTemplate templateAllModSimpx = new RelationTemplate(arrayListAllModFun, arrayListSimpxNode, null, true,
				true, false);
		// [MOD*,R-SIMPX,]
		RelationTemplate templateAllModRSimpx = new RelationTemplate(null, arrayListRSimpxNode, null,
				false, true, false);
		// [ONK,OAK,ODK,...]
		RelationTemplate templateAllKMod = new RelationTemplate(arrayListAllKMod, null, null,
				true, false, false);
		// OV
		RelationTemplate templateOV = new RelationTemplate(arrayListOvFun, null, null, true, false, false);
		// [OV,,(PTKZU,VVIZU)]
		RelationTemplate templateOVZu = new RelationTemplate(arrayListOvFun, null, arrayListZuWordPos, true, false, true);
		// [OV,,PTKZU]
		RelationTemplate templateOVPtkZu = new RelationTemplate(arrayListOvFun, null, arrayListPtkZuPos, true, false, true);
		// [OV,,(V*PASSIV,PTKZU-PASS)]
		RelationTemplate templateOvPass = new RelationTemplate(arrayListOvFun, null, arrayListVerbPassivePos, true,
				false, true);
		// [OV,,VAUX]
		RelationTemplate templateOvAux = new RelationTemplate(arrayListOvFun, null, arrayListVerbAuxPos, true,
				false, true);
		// [OV,,VMAIN]
		RelationTemplate templateOvMain = new RelationTemplate(arrayListOvFun, null, arrayListVerbMainPos, true,
				false, true);
		// [OV,,VVPP]
		RelationTemplate templateOvVvppMain = new RelationTemplate(arrayListOvFun, null, arrayListVerbVVPPPos, true,
				false, true);
		// [VC-HD,,VAUX]
		RelationTemplate templateVcHdAux = new RelationTemplate(arrayListVcHdFun, null, arrayListVerbAuxPos, true,
				false, true);
		// [VC-HD,,VMAIN]
		RelationTemplate templateVcHdMain = new RelationTemplate(arrayListVcHdFun, null, arrayListVerbMainPos, true,
				false, true);
		// [VC-HD,,VMAIN]
		RelationTemplate templateVcHdZu = new RelationTemplate(arrayListVcHdFun, null, arrayListZuWordPos, true,
				false, true);
		RelationTemplate templateVerbPass = new RelationTemplate(arrayListVerbFun, null, arrayListVerbPassivePos, true,
				false, true);
		// OA
		RelationTemplate templateOA = new RelationTemplate(arrayListOaFun, null, null, true, false, false);
		// (OA,OS,OSC)
		RelationTemplate templateObject = new RelationTemplate(arrayListObjFun, null, null, true, false, false);
		// OD
		RelationTemplate templateOD = new RelationTemplate(arrayListOdFun, null, null, true, false, false);
		// OG
		RelationTemplate templateOG = new RelationTemplate(arrayListOgFun, null, null, true, false, false);
		// (OD,OG)
		RelationTemplate templateIOBJ = new RelationTemplate(arrayListIobjFun, null, null, true, false, false);
		// GEN
		RelationTemplate templateGEN = new RelationTemplate(arrayListGenFun, null, null, true, false, false);
		// OS
		RelationTemplate templateOS = new RelationTemplate(arrayListOsFun, null, null, true, false, false);
		// OSC
		RelationTemplate templateOSC = new RelationTemplate(arrayListOscFun, null, null, true, false, false);
		// VPT
		RelationTemplate templateVPT = new RelationTemplate(arrayListVptFun, null, null, true, false, false);
		// ES
		RelationTemplate templateES = new RelationTemplate(arrayListEsFun, null, null, true, false, false);
		// NEG_DET
		RelationTemplate templateNEG_DET = new RelationTemplate(arrayListNegDetFun, null, null, true, false, false);
		// NEG_ADV
		RelationTemplate templateNEG_ADV = new RelationTemplate(arrayListNegAdvFun, null, null, true, false, false);
		//APPR
		RelationTemplate templateADP = new RelationTemplate(null, arrayListWordNode, arrayListAdpPos, false, true, true);
		//APP
		RelationTemplate templateAPP = new RelationTemplate(arrayListAppFun, null, null, true, false, false);
		// [OS,,(VVIZU, VVINF)]
		RelationTemplate templateOsVinf = new RelationTemplate(arrayListOsFun, null, arrayListVInfPos, true, false,
				true);
		// [,WORD,V*INF]
		RelationTemplate templateWordVInf = new RelationTemplate(null, arrayListWordNode, arrayListVInfPos, false, true, true);
		// [,,VXINF]
		RelationTemplate templateVxInf = new RelationTemplate(null, arrayListVxInfNode, null, false, true, false);
		// HD
		RelationTemplate templateHD = new RelationTemplate(arrayListHdFun, null, null, true, false, false);
		// [HD,,(V*PASSIV,PTKZU-PASS)]
		RelationTemplate templateHdPass = new RelationTemplate(arrayListHdFun, null, arrayListVerbPassivePos, true,
				false, true);
		// [HD,,VVFIN]
		RelationTemplate templateHdVvfin = new RelationTemplate(arrayListHdFun, null, arrayListVerbVVFINPos, true,
				false, true);
		// [HD,,N*]
		RelationTemplate templateHdN = new RelationTemplate(arrayListHdFun, null, arrayListNPos, true,
				false, true);
		// [HD,,ADJ*]
		RelationTemplate templateHdAdj = new RelationTemplate(arrayListHdFun, null, arrayListAdjPos, true,
				false, true);
		// [HD,,(ADV,ADJD,ADJA)]
		RelationTemplate templateHdAdjAdv = new RelationTemplate(arrayListHdFun, null, arrayListAdjAdvPos, true,
				false, true);
		// VC-HD
		RelationTemplate templateVcHD = new RelationTemplate(arrayListVcHdFun, null, null, true, false, false);
		// [VC-HD,,(V*PASSIV,PTKZU-PASS)]
		RelationTemplate templateVcHdPass = new RelationTemplate(arrayListVcHdFun, null, arrayListVerbPassivePos, true,
				false, true);
		// [HD,,V*LASSEN]
		RelationTemplate templateHdLass = new RelationTemplate(arrayListHdFun, null, arrayListVerbLassenPos, true,
				false, true);
		// [VC-HD,,V*LASSEN]
		RelationTemplate templateVcHdLass = new RelationTemplate(arrayListVcHdFun, null, arrayListVerbLassenPos, true,
				false, true);
		// [OV,,V*LASSEN]
		RelationTemplate templateOvLass = new RelationTemplate(arrayListOvFun, null, arrayListVerbLassenPos, true,
				false, true);
		// KOUS
		RelationTemplate templateKOUS = new RelationTemplate(null, null, arrayListKousPos, false, false, true);
		// [,PX,]
		RelationTemplate templatePX = new RelationTemplate(null, arrayListPxNode, null, false, true, false);
		// [HD,PX,]
		RelationTemplate templatePxProp = new RelationTemplate(null, arrayListPxNode, arrayListPropPos, false, true, true);
		// [-,FX,]
		RelationTemplate templateBlankFX = new RelationTemplate(arrayListBlank, arrayListFxNode, null, true, true, false);
		// [,,PTKNEG]
		RelationTemplate templatePtkNeg = new RelationTemplate(null, null, arrayListPtkNegPos, false, false, true);
		// [,WORD,(PTKZU,PTKZU-LASS,PTKZU-PASS)]
		RelationTemplate templateWordPtkZuAll = new RelationTemplate(null, arrayListWordNode, arrayListPtkZuAllPos, false, true, true);
		// [,WORD,PTKA]
		RelationTemplate templatePtkA = new RelationTemplate(null, arrayListWordNode, arrayListPtkAPos, false, true, true);
		// [,WORD,PTKVZ]
		RelationTemplate templatePtkVz = new RelationTemplate(null, arrayListWordNode, arrayListPtkVzPos, false, true, true);
		// [,WORD,(PTKANT,ITJ)]
		RelationTemplate templatePtkDis = new RelationTemplate(null, arrayListWordNode, arrayListPtkDisPos, false, true, true);
		// [,WORD,APZR]
		RelationTemplate templateWordApzr = new RelationTemplate(null, arrayListWordNode, arrayListApzrPos, false, true, true);
		// [,WORD,TRUNC]
		RelationTemplate templateWordTrunc = new RelationTemplate(null, arrayListWordNode, arrayListTruncPos, false, true, true);
		// [,WORD,FM]
		RelationTemplate templateFm = new RelationTemplate(null, arrayListWordNode, arrayListFmPos, false, true, true);
		// [,ADVX,ADV_NEG]
		RelationTemplate templateAdvXNeg = new RelationTemplate(null, arrayListAdvxNode, arrayListAdvNegPos, false, true, true);
		// [,ADVX,]
		RelationTemplate templateAdvX = new RelationTemplate(null, arrayListAdvxNode, null, false, true, false);
		// [V-MOD,ADJX,]
		RelationTemplate templateVmodAdjX = new RelationTemplate(arrayListVModFun, arrayListAdjxNode, null, true, true, false);
		// [HD,ADJX,]
		RelationTemplate templateHdAdjX = new RelationTemplate(arrayListHdFun, arrayListAdjxNode, null, true, true, false);
		// [MOD*,NX,]
		RelationTemplate templateAllModNx = new RelationTemplate(arrayListAllModFun, arrayListNxNode, null, true, true, false);
		// [,NX,]
		RelationTemplate templateNX = new RelationTemplate(null, arrayListNxNode, null, false, true, false);
		// [-,NX,]
		RelationTemplate templateBlankNX = new RelationTemplate(arrayListBlank, arrayListNxNode, null, true, true, false);
		// [MOD*,,]
		RelationTemplate templateAllMod = new RelationTemplate(arrayListAllModFun, null, null, true, false, false);
		// [OAD*,,]
		RelationTemplate templateOad = new RelationTemplate(arrayListOadFun, null, null, true, false, false);
		// [*OPP,,]
		RelationTemplate templateOpp = new RelationTemplate(arrayListOppFun, null, null, true, false, false);
		// [,ADJX,CARD]
		RelationTemplate templateAdjXCard = new RelationTemplate(null, arrayListAdjxNode, arrayListCard, false, true, true);
		// [,WORD,CARD]
		RelationTemplate templateWordCard = new RelationTemplate(null, arrayListWordNode, arrayListCard, false, true, true);
		// [,ADJX,PTKZU]
		RelationTemplate templatePtkZuAdjX = new RelationTemplate(null, arrayListAdjxNode, arrayListPtkZuPos, false, true, true);
		// [,ADJX,]
		RelationTemplate templateAdjX = new RelationTemplate(null, arrayListAdjxNode, null, false, true, false);
		// [-,WORD,ADJ*]
		RelationTemplate templateAdj = new RelationTemplate(arrayListBlank, arrayListWordNode, arrayListAdjPos, true, true, true);
		// [,DP,]
		RelationTemplate templateDpX = new RelationTemplate(null, arrayListDpNode, null, false, true, false);
		// [,SIMPX,]
		RelationTemplate templateSimpx = new RelationTemplate(null, arrayListSimpxNode, null, false, true, false);
		// [,P-SIMPX,]
		RelationTemplate templatePSimpx = new RelationTemplate(null, arrayListPSimpxNode, null, false, true, false);
		// PARA
		RelationTemplate templatePara = new RelationTemplate(arrayListParaFun, null, null, true, false, false);
		// DM
		RelationTemplate templateDm = new RelationTemplate(null, arrayListDmNode, null, false, true, false);
		// [,WORD,ART]
		RelationTemplate templateWordART = new RelationTemplate(null, arrayListWordNode, arrayListArtPos, false, true, true);
		// [,WORD,ADJ]
		RelationTemplate templateWordADJ = new RelationTemplate(null, arrayListWordNode, arrayListAdjPos, false, true, true);
		// [,WORD,PPOSAT]
		RelationTemplate templateWordPPOSAT = new RelationTemplate(null, arrayListWordNode, arrayListPposatPos, false, true, true);

		// **********************Structures****************************//

		//Specifically handled templates
		
		//HD,KONJ,KONJ,...:HD,c1,c2,... ***
		HDKONJKONJ_hdc1c2 = new ArrayList<RelationTemplate>();
		HDKONJKONJ_hdc1c2.add(templateHD);
		HDKONJKONJ_hdc1c2.add(templateKONJ);
		HDKONJKONJ_hdc1c2.add(templateKONJ);
		
		// KONJ,KONJ,...:HD,conj,... --> First KONJ becomes head
		KONJKONJ_hdconj = new ArrayList<RelationTemplate>();
		KONJKONJ_hdconj.add(templateKONJ);
		KONJKONJ_hdconj.add(templateKONJ);
		
		//[PRED,,N*],[PRED-MOD,SIMPX,]:[PRED,,N*],acl
		PREDPREDMOD_PREDacl = new ArrayList<RelationTemplate>();
		PREDPREDMOD_PREDacl.add(templatePredN);
		PREDPREDMOD_PREDacl.add(templatePredMod);
		
		//[PRED,,],[PRED-MOD,SIMPX,]:[PRED,,],advcl
		PREDPREDMOD_PREDadvcl = new ArrayList<RelationTemplate>();
		PREDPREDMOD_PREDadvcl.add(templatePred);
		PREDPREDMOD_PREDadvcl.add(templatePredMod);
		
		//[ON,SIMPX,],[(HD,OV,VC-HD),,V*PASSIV]:csubjpass
		ONSIMPXVPASS_csubjpassVPASS = new ArrayList<RelationTemplate>();
		ONSIMPXVPASS_csubjpassVPASS.add(templateOnSimpx);
		ONSIMPXVPASS_csubjpassVPASS.add(templateVerbPass);
		
		//ON,[(HD,OV,VC-HD),,V*PASSIV]:nsubjpass
		ONVPASS_nsubjpassVPASS = new ArrayList<RelationTemplate>();
		ONVPASS_nsubjpassVPASS.add(templateON);
		ONVPASS_nsubjpassVPASS.add(templateVerbPass);
		
		//APP,...:HD,(appos,...)
		APPAPP_HDappos = new ArrayList<RelationTemplate>();
		APPAPP_HDappos.add(templateAPP);
				
		//[,,NE],...:HD,(flat,...)
		NE_HDflat = new ArrayList<RelationTemplate>();
		NE_HDflat.add(templateNE);
		NE_HDflat.add(templateNE);
		
		//[,,N*]:appos
		N_APP = new ArrayList<RelationTemplate>();
		N_APP.add(templateN);
		
		//[,NX,]:appos
		NX_APP = new ArrayList<RelationTemplate>();
		NX_APP.add(templateNX);
		
		//[HD,,N*],[,SIMPX,]:acl
		N_SIMPX_acl = new ArrayList<RelationTemplate>();
		N_SIMPX_acl.add(templateHdN);
		N_SIMPX_acl.add(templateSimpx);
		
		//[,SIMPX,]:advcl
		SIMPX_advcl = new ArrayList<RelationTemplate>();
		SIMPX_advcl.add(templateSimpx);
		
		//HD,[,,FM],[,,FM],...:HD,foreign,foreign,...
		HDFM_HDforeign = new ArrayList<RelationTemplate>();
		HDFM_HDforeign.add(templateHD);
		HDFM_HDforeign.add(templateFm);
		
		//[,,FM],[,,FM],...:HD,foreign,...
		FMFM_HDforeign = new ArrayList<RelationTemplate>();
		FMFM_HDforeign.add(templateFm);
		FMFM_HDforeign.add(templateFm);
		
		//[,WORD,FM]:foreign
		FM_foreign = new ArrayList<RelationTemplate>();
		FM_foreign.add(templateFm);
		
		//[-,FX,]:foreign
		FX_foreign = new ArrayList<RelationTemplate>();
		FX_foreign.add(templateBlankFX);
		
		//Automatically processed templates
		
		autoProcessedMultipleTemplates = new ArrayList<TransformationPair>();
		autoProcessedSingleTemplates = new ArrayList<TransformationPair>();
		ArrayList<RelationTemplate> currentTemplate = new ArrayList<RelationTemplate>();
		ArrayList<String> currentNewRelations = new ArrayList<String>();
		
		//[HD,,(V*PASSIV,PTKZU-PASS)],PRED,OV(HD):aux:pass,xcomp,HD --> take care to only convert the leftmost OV
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
	
		//[HD,,(V*PASSIV,PTKZU-PASS)],PRED,VC-HD:aux:pass,xcomp,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU_PASS)],[OV,,VMAIN],[OV,,VMAIN]:aux:pass,xcomp,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateOvMain);
		currentTemplate.add(templateOvMain);
						
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU_PASS)],[OV,,VMAIN],[VC-HD,,VMAIN]:aux:pass,xcomp,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateOvMain);
		currentTemplate.add(templateVcHdMain);
						
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU_PASS)],[OV,,(PTKZU,VVIZU)],[OV,,VMAIN]:aux:pass,xcomp,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateOVZu);
		currentTemplate.add(templateOvMain);
						
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU_PASS)],[OV,,(PTKZU,VVIZU)],[VC-HD,,VMAIN]:aux:pass,xcomp,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateOVZu);
		currentTemplate.add(templateVcHdMain);
						
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,OV,[OV(HD),,(V*PASSIV,PTKZU-PASS)]:aux,xcomp,HD,aux:pass
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateOvPass);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,OV,[VC-HD,,(V*PASSIV,PTKZU-PASS)]:aux,xcomp,HD,aux:pass
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateOV);
		currentTemplate.add(templateVcHdPass);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		currentNewRelations.add("aux:pass");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU-PASS)],OV:aux:pass,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU-PASS)],VC-HD:aux:pass,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,[OS-MOD,,PPER],OV(HD):aux,xcomp,expl,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateOsModPper);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("expl");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,[OS-MOD,,PPER],VC-HD:aux,xcomp,expl,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateOsModPper);
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("expl");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,[OS-MOD,,PPER]:HD,xcomp,expl
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateOsModPper);
		
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("expl");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,(OA,OS,OSC),OV(HD):aux,xcomp,obj,HD --> take care to only convert the leftmost OV
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateObject);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("obj");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,(OA,OS,OSC),VC-HD:aux,xcomp,obj,HD - *
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateObject);
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("obj");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,(OA,OS,OSC):HD,xcomp,obj - *
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateObject);
		
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("obj");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,(OD,OG),OV(HD):aux,xcomp,iobj,HD --> take care to only convert the leftmost OV
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateIOBJ);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("iobj");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,(OD,OG),VC-HD:aux,xcomp,iobj,HD - *
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateIOBJ);
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("iobj");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,(OD,OG):HD,xcomp,iobj - *
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredAll);
		currentTemplate.add(templateIOBJ);
		
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("iobj");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[PRED,PX,],OV(HD):aux,obl,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredPx);
		currentTemplate.add(templateOV);
				
		currentNewRelations.add("aux");
		currentNewRelations.add("obl");
		currentNewRelations.add("HD");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[PRED,PX,],VC-HD:aux,obl,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredPx);
		currentTemplate.add(templateVcHD);
				
		currentNewRelations.add("aux");
		currentNewRelations.add("obl");
		currentNewRelations.add("HD");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[PRED,PX,]:HD,obl
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredPx);
						
		currentNewRelations.add("HD");
		currentNewRelations.add("obl");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PREDWITHKOKOM,OV(HD):aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredWithKokom);
		currentTemplate.add(templateOV);
				
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PREDWITHKOKOM,VC-HD:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredWithKokom);
		currentTemplate.add(templateVcHD);
				
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PREDWITHKOKOM:HD,xcomp
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePredWithKokom);
						
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear(); 
		
		//HD,PRED,[OV,,PTKZU],[OV,,VVPP]:aux,xcomp,cop,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePred);
		currentTemplate.add(templateOVPtkZu);
		currentTemplate.add(templateOvVvppMain);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("cop");
		currentNewRelations.add("HD");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,VVFIN],PRED,[OV,,PTKZU]:HD,xcomp,cop
		currentTemplate.add(templateHdVvfin);
		currentTemplate.add(templatePred);
		currentTemplate.add(templateOVPtkZu);
						
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("cop");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,[OV,,PTKZU],OV:aux,HD,xcomp,cop
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePred);
		currentTemplate.add(templateOV);
		currentTemplate.add(templateOVPtkZu);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("cop");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,[OV,,PTKZU]:cop,HD,xcomp
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePred);
		currentTemplate.add(templateOVPtkZu);
						
		currentNewRelations.add("cop");
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED,OV(HD):aux,HD,cop --> take care to only convert the leftmost OV
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePred);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("HD");
		currentNewRelations.add("cop");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,PRED:cop,HD - *
		currentTemplate.add(templateHD);
		currentTemplate.add(templatePred);
		
		currentNewRelations.add("cop");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[OV,,LASSEN],OV:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOvLass);
		currentTemplate.add(templateOV);
						
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[VC-HD,,LASSEN],OV:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateVcHdLass);
		currentTemplate.add(templateOV);
						
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,LASSEN],OV:HD,xcomp
		currentTemplate.add(templateHdLass);
		currentTemplate.add(templateOV);
						
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
						
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[OV,,VMAIN],[OV,,VMAIN]:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOvMain);
		currentTemplate.add(templateOvMain);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[OV,,VMAIN],[VC-HD,,VMAIN]:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOvMain);
		currentTemplate.add(templateVcHdMain);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[OV,,(PTKZU,VVIZU)],[OV,,VMAIN]:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOVZu);
		currentTemplate.add(templateOvMain);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[OV,,(PTKZU,VVIZU)],[VC-HD,,VMAIN]:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOVZu);
		currentTemplate.add(templateVcHdMain);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[OV,,(LASSEN,PTKZU_LASS)],OV:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOvLass);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[VC-HD,,(LASSEN,PTKZU_LASS)],OV:aux,xcomp,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateVcHdLass);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("xcomp");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(LASSEN,PTKZU_LASS)],OV:HD,xcomp
		currentTemplate.add(templateHdLass);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU_PASS)],OV:aux:pass,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[HD,,(V*PASSIV,PTKZU_PASS)],VC-HD:aux:pass,HD
		currentTemplate.add(templateHdPass);
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux:pass");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,[OV,,(PTKZU,VVIZU)]:HD,xcomp
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOVZu);
				
		currentNewRelations.add("HD");
		currentNewRelations.add("xcomp");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,OV:aux,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//HD,VC-HD:aux,HD
		currentTemplate.add(templateHD);
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,(PTKZU,PTKZU-LASS,PTKZU-PASS)],[,WORD,V*INF]:aux,HD 
		currentTemplate.add(templateWordPtkZuAll);
		currentTemplate.add(templateWordVInf);
				
		currentNewRelations.add("mark");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,(PTKZU,PTKZU-LASS,PTKZU-PASS)],[,VXINF,]:aux,HD 
		currentTemplate.add(templateWordPtkZuAll);
		currentTemplate.add(templateVxInf);
				
		currentNewRelations.add("mark");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,PTKZU],[,WORD,ADJ*]:aux,HD 
		currentTemplate.add(templateWordPtkZuAll);
		currentTemplate.add(templateWordADJ);
				
		currentNewRelations.add("mark");
		currentNewRelations.add("HD");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[PRED-KOKOM,,KOKOM],[HD,,N*]:HD,obl
		currentTemplate.add(templatePredKokom);
		currentTemplate.add(templateHdN);
				
		currentNewRelations.add("HD");
		currentNewRelations.add("obl");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[PRED-KOKOM,,KOKOM],[HD,,ADJ*]:HD,amod
		currentTemplate.add(templatePredKokom);
		currentTemplate.add(templateHdAdj);
				
		currentNewRelations.add("HD");
		currentNewRelations.add("amod");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//(APPR,APPO),HD:case,HD
		currentTemplate.add(templateADP);
		currentTemplate.add(templateHD);
		
		currentNewRelations.add("case");
		currentNewRelations.add("HD");
		
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//(APPR,APPO),[,NX,]:case,HD
		currentTemplate.add(templateADP);
		currentTemplate.add(templateNX);
				
		currentNewRelations.add("case");
		currentNewRelations.add("HD");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		// [HD,,(ADV,ADJD,ADJA)],[-,NX,]:HD,nmod
		currentTemplate.add(templateHdAdjAdv);
		currentTemplate.add(templateBlankNX);
				
		currentNewRelations.add("HD");
		currentNewRelations.add("nmod");
				
		autoProcessedMultipleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//		**ONE-TO-ONE TRANSFORMATIONS**
		
		//HD:HD
		currentTemplate.add(templateHD);
				
		currentNewRelations.add("HD");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//PARA:parataxis
		currentTemplate.add(templatePara);
						
		currentNewRelations.add("parataxis");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//DM:discourse
		currentTemplate.add(templateDm);
						
		currentNewRelations.add("discourse");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,KON]:cc
		currentTemplate.add(templateWordKON);
		
		currentNewRelations.add("cc");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//KONJ2:conj
		currentTemplate.add(templateKONJ2);
						
		currentNewRelations.add("conj");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//KONJ:HD
		currentTemplate.add(templateKONJ);
						
		currentNewRelations.add("HD");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[ON,SIMPX,]:csubj
		currentTemplate.add(templateOnSimpx);
		
		currentNewRelations.add("csubj");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//ON:nsubj
		currentTemplate.add(templateON);
		
		currentNewRelations.add("nsubj");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//OA:obj
		currentTemplate.add(templateOA);
		
		currentNewRelations.add("obj");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//OD:iobj
		currentTemplate.add(templateOD);
		
		currentNewRelations.add("iobj");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//OG:iobj
		currentTemplate.add(templateOG);
		
		currentNewRelations.add("iobj");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[OV,,(V*PASSIV,PTKZU-PASS)]:aux:pass
		currentTemplate.add(templateOvPass);
		
		currentNewRelations.add("aux:pass");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[OV,,(LASSEN,PTKZU_LASS)]:xcomp
		currentTemplate.add(templateOvLass);
		
		currentNewRelations.add("xcomp");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[OV,,(PTKZU,VVIZU)]:xcomp
		currentTemplate.add(templateOVZu);
		
		currentNewRelations.add("xcomp");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//OV:aux
		currentTemplate.add(templateOV);
		
		currentNewRelations.add("aux");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,(PTKZU,PTKZU-LASS,PTKZU-PASS)]:aux
		currentTemplate.add(templateWordPtkZuAll);
				
		currentNewRelations.add("mark");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//GEN:nmod:poss
		currentTemplate.add(templateGEN);
		
		currentNewRelations.add("nmod:poss");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[VC-HD,,(V*PASSIV,PTKZU-PASS)]:aux:pass
		currentTemplate.add(templateVcHdPass);
		
		currentNewRelations.add("aux:pass");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[VC-HD,,(LASSEN,PTKZU-LASS)]:xcomp
		currentTemplate.add(templateVcHdLass);
				
		currentNewRelations.add("xcomp");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[VC-HD,,(PTKZU,VVIZU)]:xcomp
		currentTemplate.add(templateVcHdZu);
		
		currentNewRelations.add("xcomp");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[VC-HD,,VMAIN]:xcomp
		currentTemplate.add(templateVcHdMain);
		
		currentNewRelations.add("xcomp");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[VC-HD,,]:aux
		currentTemplate.add(templateVcHD);
		
		currentNewRelations.add("aux");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//OSC:ccomp
		currentTemplate.add(templateOSC);
		
		currentNewRelations.add("ccomp");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//OS:xcomp
		currentTemplate.add(templateOS);
		
		currentNewRelations.add("xcomp");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[(ON-MOD, OA-MOD, OD-MOD, OG-MOD),SIMPX,]:acl
		currentTemplate.add(templateNounModSimpx);
		
		currentNewRelations.add("acl");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[MOD*,SIMPX,]:advcl
		currentTemplate.add(templateAllModSimpx);
		
		currentNewRelations.add("advcl");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[MOD*,R-SIMPX,]:acl:relcl
		currentTemplate.add(templateAllModRSimpx);
		
		currentNewRelations.add("acl:relcl");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,,(KOUS, KOUI)]:mark
		currentTemplate.add(templateKOUS);
		
		currentNewRelations.add("mark");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,PX,PROP]:advmod
		currentTemplate.add(templatePxProp);
		
		currentNewRelations.add("advmod");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,PX,]:obl
		currentTemplate.add(templatePX);
		
		currentNewRelations.add("obl");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//OBL:obl
		currentTemplate.add(templateObl);
				
		currentNewRelations.add("obl");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		// [,WORD,KOKOM]:case
		currentTemplate.add(templateKOKOM);

		currentNewRelations.add("case");

		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate),new ArrayList<String>(currentNewRelations)));

		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,,PTKNEG]:neg
		currentTemplate.add(templatePtkNeg);
				
		currentNewRelations.add("advmod:neg");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//NEG_DET:det:neg
		currentTemplate.add(templateNEG_DET);
										
		currentNewRelations.add("det:neg");
										
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
										
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//NEG_ADV:advmod:neg
		currentTemplate.add(templateNEG_ADV);
										
		currentNewRelations.add("advmod:neg");
										
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
										
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,ADVX,ADV_NEG]:advmod:neg
		currentTemplate.add(templateAdvXNeg);
		
		currentNewRelations.add("advmod:neg");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,ADVX,]:advmod
		currentTemplate.add(templateAdvX);
		
		currentNewRelations.add("advmod");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[V-MOD,ADJX,]:advmod
		currentTemplate.add(templateVmodAdjX);
				
		currentNewRelations.add("advmod");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,ADJX,CARD]:nummod
		currentTemplate.add(templateAdjXCard);
				
		currentNewRelations.add("nummod");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,CARD]:nummod
		currentTemplate.add(templateWordCard);
				
		currentNewRelations.add("nummod");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,ADJX,PTKZU]:acl
		currentTemplate.add(templatePtkZuAdjX);
		
		currentNewRelations.add("acl");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,ADJX,]:amod
		currentTemplate.add(templateAdjX);
		
		currentNewRelations.add("amod");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[-,WORD,ADJ*]:amod
		currentTemplate.add(templateAdj);
				
		currentNewRelations.add("amod");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,DP,]:det
		currentTemplate.add(templateDpX);
				
		currentNewRelations.add("det");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,ART]:det
		currentTemplate.add(templateWordART);

		currentNewRelations.add("det");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,,PPOSAT]:nmod:poss
		currentTemplate.add(templateWordPPOSAT);
		
		currentNewRelations.add("nmod:poss");
		
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
		
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,PTKA]:advmod
		currentTemplate.add(templatePtkA);
						
		currentNewRelations.add("advmod");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,APZR]:mwe
		currentTemplate.add(templateWordApzr);
								
		currentNewRelations.add("mwe");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//VPT:mark
		currentTemplate.add(templateVPT);
						
		currentNewRelations.add("mark");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,PTKVZ]:mark
		currentTemplate.add(templatePtkVz);
						
		currentNewRelations.add("mark");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//ES:expl
		currentTemplate.add(templateES);
								
		currentNewRelations.add("expl");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[(ON-MOD, OS-MOD),,PPER]:expl
		currentTemplate.add(templateEsModPper);
		
		currentNewRelations.add("expl");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,(PDAT,PIDAT,PIS,PIAT,PWAT)]:det
		currentTemplate.add(templateWordDetPron);
		
		currentNewRelations.add("det");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[-,,(PDAT,PIDAT,PIS,PIAT,PWAT)]:det
		currentTemplate.add(templateDetPron);
		
		currentNewRelations.add("det");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[ONK,OAK,ODK,...]:conj
		currentTemplate.add(templateAllKMod);
				
		currentNewRelations.add("conj");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,P-SIMPX,]:parataxis
		currentTemplate.add(templatePSimpx);
								
		currentNewRelations.add("parataxis");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[MOD*,NX,]:obl
		currentTemplate.add(templateAllModNx);
						
		currentNewRelations.add("obl");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,TRUNC]:advmod
		currentTemplate.add(templateWordTrunc);
				
		currentNewRelations.add("advmod");
				
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
				
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[,WORD,(PTKANT,ITJ)]:discourse
		currentTemplate.add(templatePtkDis);
						
		currentNewRelations.add("discourse");
						
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
						
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[OAD*,,]:advmod
		currentTemplate.add(templateOad);
								
		currentNewRelations.add("advmod");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[*OPP,,]:obl
		currentTemplate.add(templateOpp);
								
		currentNewRelations.add("obl");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
		
		//[MOD*,,]:advmod
		currentTemplate.add(templateAllMod);
								
		currentNewRelations.add("advmod");
								
		autoProcessedSingleTemplates.add(new TransformationPair(new ArrayList<RelationTemplate>(currentTemplate), new ArrayList<String>(currentNewRelations)));
								
		currentTemplate.clear();
		currentNewRelations.clear();
	}

	public ArrayList<RelationTemplate> getKONJKONJ_hdconj() 
	{
		return KONJKONJ_hdconj;
	}

	public ArrayList<RelationTemplate> getPREDPREDMOD_PREDacl() 
	{
		return PREDPREDMOD_PREDacl;
	}

	public ArrayList<RelationTemplate> getPREDPREDMOD_PREDadvcl() 
	{
		return PREDPREDMOD_PREDadvcl;
	}

	public ArrayList<TransformationPair> getAutoProcessedSingleTemplates() 
	{
		return autoProcessedSingleTemplates;
	}
	
	public ArrayList<TransformationPair> getAutoProcessedMultipleTemplates() 
	{
		return autoProcessedMultipleTemplates;
	}

	public ArrayList<RelationTemplate> getAPPAPP_HDappos() 
	{
		return APPAPP_HDappos;
	}

	public ArrayList<RelationTemplate> getNE_HDflat() 
	{
		return NE_HDflat;
	}

	public ArrayList<RelationTemplate> getN_APP() 
	{
		return N_APP;
	}
	
	public ArrayList<RelationTemplate> getNX_APP() 
	{
		return NX_APP;
	}

	public ArrayList<RelationTemplate> getN_SIMPX_acl() 
	{
		return N_SIMPX_acl;
	}

	public ArrayList<RelationTemplate> getSIMPX_advcl() 
	{
		return SIMPX_advcl;
	}

	public ArrayList<RelationTemplate> getHDFM_HDforeign() 
	{
		return HDFM_HDforeign;
	}

	public ArrayList<RelationTemplate> getFMFM_HDforeign() 
	{
		return FMFM_HDforeign;
	}

	public ArrayList<RelationTemplate> getHDKONJKONJ_hdc1c2()
	{
		return HDKONJKONJ_hdc1c2;
	}

	public ArrayList<RelationTemplate> getFM_foreign() 
	{
		return FM_foreign;
	}

	public ArrayList<RelationTemplate> getFX_foreign() 
	{
		return FX_foreign;
	}

	public ArrayList<RelationTemplate> getONSIMPXVPASS_csubjpassVPASS() 
	{
		return ONSIMPXVPASS_csubjpassVPASS;
	}

	public ArrayList<RelationTemplate> getONVPASS_nsubjpassVPASS() 
	{
		return ONVPASS_nsubjpassVPASS;
	}
}