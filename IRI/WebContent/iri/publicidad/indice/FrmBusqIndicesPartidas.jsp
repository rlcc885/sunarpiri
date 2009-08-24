<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
String xparam = (String) request.getAttribute("flagInterno");
boolean flagInterno=false;
if (xparam.equals("1"))
	flagInterno=true;

String depLima = "99999999";	
String proLima = "99999999";	
xparam = (String) request.getAttribute("depLima");
if (xparam!=null)
	depLima = xparam;
xparam = (String) request.getAttribute("proLima");
if (xparam!=null)
	proLima = xparam;	
%>

<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<HTML>
<HEAD>
<TITLE></TITLE>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
<script language="JavaScript" SRC="<%=request.getContextPath()%>/javascript/overlib.js"></script>
<script language="javascript">
function VentanaFlotante(pag)
	{
		var ancho= 500;
		var alto= 563;
		NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,width=" + ancho + ",height=" + alto + ",top=20,left=100");
	}

var global_pidedis=false;
Navegador();


function MM_findObj(n, d) { //v4.01

  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {

    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}

  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];

  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);

  if(!x && d.getElementById) x=d.getElementById(n); return x;

}

function MM_swapImage() { //v3.0

  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)

   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}

}

function palabra(s1)
{
var w = "";
w = trim(s1);
if (w=='ASO' || w=='ASOC' || w=='ASOCI' || w=='ASOCIA' || w=='ASOCIAC'
|| w=='EMP' || w=='EMPR' || w=='EMPRE' || w=='EMPRES' || w=='EMPRESA'
|| w=='SER' || w=='SERV' || w=='SERVI' || w=='SERVIC' || w=='SERVICI'
|| w=='CLU' || w=='CLUB' || w=='CLUB ' || w=='CLUB D' || w=='CLUB DE'
|| w=='COM' || w=='COME' || w=='COMER' || w=='COMERC' || w=='COMERCI'
|| w=='DIS' || w=='DIST' || w=='DISTR' || w=='DISTRI' || w=='DISTRIB'
|| w=='INV' || w=='INVE' || w=='INVER' || w=='INVERS' || w=='INVERSI'
|| w=='COM' || w=='COMP' || w=='COMPA' || w=='COMPAÑ' || w=='COMPAÑI'
|| w=='DAT' || w=='DATO' || w=='DATO ' || w=='DATO N' || w=='DATO NO'
|| w=='PRU' || w=='PRUE' || w=='PRUEB' || w=='PRUEBA' || w=='PRUEBAS'
|| w=='TRA' || w=='TRAN' || w=='TRANS' || w=='TRANSP' || w=='TRANSPO'
|| w=='CEN' || w=='CENT' || w=='CENTR' || w=='CENTRO' || w=='CENTRO '
|| w=='IND' || w=='INDU' || w=='INDUS' || w=='INDUST' || w=='INDUSTR'
|| w=='COO' || w=='COOP' || w=='COOPE' || w=='COOPER' || w=='COOPERA'
|| w=='CON' || w=='CONS' || w=='CONST' || w=='CONSTR' || w=='CONSTRU'
|| w=='INM' || w=='INMO' || w=='INMOB' || w=='INMOBI' || w=='INMOBIL'
|| w=='IMP' || w=='IMPO' || w=='IMPOR' || w=='IMPORT' || w=='IMPORTA'
|| w=='REP' || w=='REPR' || w=='REPRE' || w=='REPRES' || w=='REPRESE'
|| w=='COM' || w=='COMI' || w=='COMIT' || w=='COMITE' || w=='COMITE '
|| w=='SOC' || w=='SOCI' || w=='SOCIE' || w=='SOCIED' || w=='SOCIEDA'
|| w=='COR' || w=='CORP' || w=='CORPO' || w=='CORPOR' || w=='CORPORA'
|| w=='COM' || w=='COMU' || w=='COMUN' || w=='COMUNI' || w=='COMUNID'
|| w=='NEG' || w=='NEGO' || w=='NEGOC' || w=='NEGOCI' || w=='NEGOCIO'
|| w=='INS' || w=='INST' || w=='INSTI' || w=='INSTIT' || w=='INSTITU'
|| w=='PRO' || w=='PROD' || w=='PRODU' || w=='PRODUC' || w=='PRODUCC'
|| w=='FAB' || w=='FABR' || w=='FABRI' || w=='FABRIC' || w=='FABRICA'
|| w=='PRO' || w=='PROM' || w=='PROMO' || w=='PROMOT' || w=='PROMOTO'
|| w=='CON' || w=='CONS' || w=='CONSO' || w=='CONSOR' || w=='CONSORC'
|| w=='INT' || w=='INTE' || w=='INTER' || w=='INTERN' || w=='INTERNA'
|| w=='REP' || w=='REPU' || w=='REPUE' || w=='REPUES' || w=='REPUEST'
|| w=='PER' || w=='PERU' || w=='PERU ' || w=='PERUA'  || w=='PERUANA' 
|| w=='MAN' || w=='MANU' || w=='MANUF' || w=='MANUFA' || w=='MANUFAC'
|| w=='GRA' || w=='GRAF' || w=='GRAFI' || w=='GRAFIC' || w=='GRAFICO'
|| w=='CAS' || w=='CASA' || w=='CASA ' || w=='CASA D' || w=='CASA DE'
|| w=='AME' || w=='AMER' || w=='AMERI' || w=='AMERIC' || w=='AMERICA'
|| w=='RAD' || w=='RADI' || w=='RADIO' || w=='RADIO ' || w=='RADIO D'
|| w=='TAL' || w=='TALL' || w=='TALLE' || w=='TALLER' || w=='TALLER '
|| w=='CON' || w=='CONT' || w=='CONTR' || w=='CONTRI' || w=='CONTRIB'
|| w=='RES' || w=='REST' || w=='RESTA' || w=='RESTAB' || w=='RESTABL'
|| w=='AGR' || w=='AGRI' || w=='AGRIC' || w=='AGRICU' || w=='AGRICUL'
|| w=='AUT' || w=='AUTO' || w=='AUTOM' || w=='AUTOMA' || w=='AUTOMAT'
|| w=='SIS' || w=='SIST' || w=='SISTE' || w=='SISTEM' || w=='SISTEMA'
|| w=='SAN' || w=='SANT' || w=='SANTA' || w=='SANTA ' 
|| w=='AGR' || w=='AGRO' || w=='AGRO ' || w=='AGRO D' || w=='AGRO DE'
|| w=='PUB' || w=='PUBL' || w=='PUBLI' || w=='PUBLIC' || w=='PUBLICI'
|| w=='TEC' || w=='TECN' || w=='TECNI' || w=='TECNIC' || w=='TECNICO'
|| w=='AGR' || w=='AGRO' || w=='AGROP' || w=='AGROPE' || w=='AGROPEC'
|| w=='FUN' || w=='FUND' || w=='FUNDA' || w=='FUNDAC' || w=='FUNDACI'
|| w=='MER' || w=='MERC' || w=='MERCA' || w=='MERCAD' || w=='MERCADO'
|| w=='PRO' || w=='PROV' || w=='PROVE' || w=='PROVEE' || w=='PROVEED'
|| w=='MOL' || w=='MOLI' || w=='MOLIN' || w=='MOLINE' || w=='MOLINER'
|| w=='ART' || w=='ARTE' || w=='ARTES' || w=='ARTESA' || w=='ARTESAN'
|| w=='FAC' || w=='FACT' || w=='FACTO' || w=='FACTOR' || w=='FACTORI'
|| w=='JUN' || w=='JUNT' || w=='JUNTA' || w=='JUNTA ' || w=='JUNTA D'
|| w=='MIN' || w=='MINE' || w=='MINER' || w=='MINERA' || w=='MINERI'
|| w=='PAN' || w=='PANA' || w=='PANAD' || w=='PANAD'  || w=='PANADE' 
|| w=='AGR' || w=='AGRO' || w=='AGROI' || w=='AGROIN' || w=='AGROIND'
|| w=='IGL' || w=='IGLE' || w=='IGLES' || w=='IGLESI' || w=='IGLESIA'
|| w=='MET' || w=='META' || w=='METAL' || w=='METAL ' || w=='METALES'
|| w=='QUI' || w=='QUIM' || w=='QUIMI' || w=='QUIMIC' || w=='QUIMICA'
|| w=='ALM' || w=='ALMA' || w=='ALMAC' || w=='ALMACE' || w=='ALMACEN'
|| w=='COM' || w=='COMI' || w=='COMIS' || w=='COMISA' || w=='COMISAR'
|| w=='LIB' || w=='LIBR' || w=='LIBRE' || w=='LIBRER' || w=='LIBRERI'
|| w=='BOT' || w=='BOTI' || w=='BOTIC' || w=='BOTICA' || w=='BOTICA '
|| w=='FED' || w=='FEDE' || w=='FEDER' || w=='FEDERA' || w=='FEDERAC'
|| w=='UNI' || w=='UNIO' || w=='UNION' || w=='UNION ' || w=='UNION '
|| w=='PLA' || w=='PLAS' || w=='PLAST' || w=='PLASTI' || w=='PLASTIC'
|| w=='GRA' || w=='GRAN' || w=='GRANJ' || w=='GRANJA' || w=='GRANJA '
|| w=='JOS' || w=='JOSE' || w=='JOSE '  
|| w=='AVI' || w=='AVIC' || w=='AVICO' || w=='AVICOL' || w=='AVICOLA'
|| w=='SEG' || w=='SEGU' || w=='SEGUR' || w=='SEGURI' || w=='SEGURID'
|| w=='GRI' || w=='GRIF' || w=='GRIFO' || w=='GRIFER' || w=='GRIFERI'
|| w=='EMP' || w=='EMP.' || w=='EMP. ' 
|| w=='LA ' || w=='LA C' || w=='LA CA' || w=='LA CAM' || w=='LA CAM'
|| w=='AGE' || w=='AGEN' || w=='AGENC' || w=='AGENCI'|| w=='AGENCIA'
|| w=='EST' || w=='ESTU' || w=='ESTUD' || w=='ESTUD' || w=='ESTUDI' || w=='ESTUDIO' || w=='ESTUDIOS'
|| w=='ASO' || w=='ASOC' || w=='ASOC.' || w=='ASOCI' || w=='ASOCIA' || w=='ASOCIAC' || w=='ASOCIACI'  || w=='ASOCIACIO'|| w=='ASOCIACION'
|| w=='BAN' || w=='BANC' || w=='BANCO' || w=='BANCO ' || w=='BANCO D' || w=='BANCO DE' || w=='BANCO DEL'
|| w=='ING' || w=='INGE' || w=='INGEN' || w=='INGENI' || w=='INGENIE'
|| w=='IMP' || w=='IMPR' || w=='IMPRE' || w=='IMPRES' || w=='IMPRESI'
|| w=='MAD' || w=='MADE' || w=='MADER' || w=='MADERA' || w=='MADERAS'
|| w=='TEX' || w=='TEXT' || w=='TEXTI' || w=='TEXTIL' || w=='TEXTILE'
|| w=='PRO' || w=='PROY' || w=='PROYE' || w=='PROYEC' || w=='PROYECT'
|| w=='EXP' || w=='EXPO' || w=='EXPOR' || w=='EXPORT' || w=='EXPORTA'
|| w=='LAB' || w=='LABO' || w=='LABOR' || w=='LABORA' || w=='LABORAL'
|| w=='TEC' || w=='TECN' || w=='TECNO' || w=='TECNOL' || w=='TECNOLO'
|| w=='PES' || w=='PESQ' || w=='PESQU' || w=='PESQUE' || w=='PESQUER'
|| w=='CRE' || w=='CREA' || w=='CREAC' || w=='CREACI' || w=='CREACIO'
|| w=='CON' || w=='CONF' || w=='CONFE' || w=='CONFEC' || w=='CONFECC'
|| w=='ORG' || w=='ORGA' || w=='ORGAN' || w=='ORGANI' || w=='ORGANIZ' 
|| w=='ORGANIZA' || w=='ORGANIZAC' || w=='ORGANIZACI' || w=='ORGANIZACIO' || w=='ORGANIZACION'
|| w=='MUL' || w=='MULT' || w=='MULTI' || w=='MULTIN' || w=='MULTINA'
|| w=='ELE' || w=='ELEC' || w=='ELECT' || w=='ELECTR' || w=='ELECTRI'
|| w=='GRU' || w=='GRUP' || w=='GRUPO' || w=='GRUPO ' || w=='GRUPO D'
|| w=='FER' || w=='FERR' || w=='FERRE' || w=='FERRET' || w=='FERRETE'
|| w=='ASE' || w=='ASES' || w=='ASESO' || w=='ASESOS' || w=='ASESOSE'
|| w=='CON' || w=='CONS' || w=='CONSU' || w=='CONSUL' || w=='CONSULT'
|| w=='EDI' || w=='EDIT' || w=='EDITO' || w=='EDITOR' || w=='EDITORI'
)
    return true;
return false;
}

function doCambiaCombo(combo, valor)
{ 
for(var i=0; i< combo.options.length; i++)
	{
		if (combo.options[i].value == valor)
				combo.options[i].selected=true;
	}
}

function tieneEspacioBlancoGuion(cadena)
{   
	var j;
   	var espacioblanco = " -\t\n\r";
   	
    if (esVacio(cadena)) return true; 
	for (j = 0;j< cadena.length;j++)
	{   
		var c = cadena.charAt(j);
		if (espacioblanco.indexOf(c) != -1) 
		{			
			return true;
		}
	}
    return false;
}	

function RefrescaCombos(codProv) 
{
	fbox = document.frm1.listbox1;
	var cod = codProv.substring(0,2);
	 for(var i=0; i < fbox.options.length; i++)
	  {
	  	if (fbox.options[i].value == cod) 
			fbox.options[i].selected=true;
	}
doAdd1();
}

function Cambio(valor)
{ 
 	var costoAux=""; var costoRMC="";
	//Tarifario
	<logic:iterate name="arrAreaLibro" id="item22" scope="request">
		if(valor==<bean:write name="item22" property="codigo"/>){
			document.frm1.comboArea.value=<bean:write name="item22" property="atributo2"/>;
			costoAux=<bean:write name="item22" property="atributo1"/>;
			if (valor=="21" || valor=="28"){
			    costoRMC=costoAux;
			}
		}	
	</logic:iterate>
	//Fin Tarifario
    invisible_navegador(navegador,'area14','narea14','v');
    invisible_navegador(navegador,'area13','narea13','i');
	invisible_navegador(navegador,'area5','narea5','i');
	invisible_navegador(navegador,'area6','narea6','v');
	invisible_navegador(navegador,'area15','narea15','i');//jascencio:1/07/07
	/******* inicio: jrosas 11-07-04 ***/
	
	if (valor==21) 
	{	
		invisible_navegador(navegador,'area14','narea14','i');
		invisible_navegador(navegador,'area12','narea12','v');
		invisible_navegador(navegador,'area13','narea13','v');
		invisible_navegador(navegador,'area15','narea15','i');
		invisible_navegador(navegador,'area1','narea1','i');	    	
		invisible_navegador(navegador,'area2','narea2','i');	    	
		invisible_navegador(navegador,'area3','narea3','i');	    			
		invisible_navegador(navegador,'area4','narea4','i');	
		invisible_navegador(navegador,'area4','narea5','i');
		invisible_navegador(navegador,'area6','narea6','i');		
		invisible_navegador(navegador,'area4','narea7','i');
		invisible_navegador(navegador,'area8','narea8','i');
		invisible_navegador(navegador,'area9','narea9','i');
		invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		document.frm1.costo.value = costoRMC;
		
		
	}
	/******* fin: jrosas 11-07-04 ***/
	if (valor==4 || valor==5) 
	{	
		invisible_navegador(navegador,'area1','narea1','v');	    	
		invisible_navegador(navegador,'area2','narea2','i');	    	
		invisible_navegador(navegador,'area3','narea3','i');
		invisible_navegador(navegador,'area4','narea4','i');
		invisible_navegador(navegador,'area12','narea12','i');
		
		document.frm1.listbox1.disabled=false;
		document.frm1.listbox2.disabled=false; 
		document.frm1.todos.disabled=false;
		document.frm1.uno.disabled=false;
		document.frm1.rtodos.disabled=false;
		document.frm1.runo.disabled=false;
		doFill1();
	}
	if (valor==3) 
	{	
		invisible_navegador(navegador,'area1','narea1','i');	    	
		invisible_navegador(navegador,'area2','narea2','v');	    	
		invisible_navegador(navegador,'area3','narea3','i');
		invisible_navegador(navegador,'area4','narea4','i');
		invisible_navegador(navegador,'area12','narea12','i');
		
		document.frm1.listbox1.disabled=false;
		document.frm1.listbox2.disabled=false;
		document.frm1.todos.disabled=false;
		document.frm1.uno.disabled=false;
		document.frm1.rtodos.disabled=false;
		document.frm1.runo.disabled=false;	  	    					
		doFill1();
	}
	if (valor==1 || valor==2 || valor==10 || valor==11 || valor==12){
		if (valor==1) 
		{
		<% if (flagInterno==true) { %>
		invisible_navegador(navegador,'area7','narea7','v');
		<%}%>
		invisible_navegador(navegador,'area8','narea8','i');
		invisible_navegador(navegador,'area9','narea9','i');
		invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		}
		else if (valor==2){
		<% if (flagInterno==true) { %>
		invisible_navegador(navegador,'area7','narea7','i');
		<%}%>
		invisible_navegador(navegador,'area8','narea8','i');
		invisible_navegador(navegador,'area9','narea9','i');
		invisible_navegador(navegador,'area10','narea10','v');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		}	
		else if (valor==10)
		{
		<% if (flagInterno==true) { %>
		invisible_navegador(navegador,'area7','narea7','i');
		<%}%>
		invisible_navegador(navegador,'area8','narea8','v');
		invisible_navegador(navegador,'area9','narea9','i');
		invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		}	
		else if (valor==11)
		{
		<% if (flagInterno==true) { %>
		invisible_navegador(navegador,'area7','narea7','i');
		<%}%>
		invisible_navegador(navegador,'area8','narea8','i');
		invisible_navegador(navegador,'area9','narea9','v');
		invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		}	
		else if (valor==12)
		{
		<% if (flagInterno==true) { %>
		invisible_navegador(navegador,'area7','narea7','i');
		<%}%>
		invisible_navegador(navegador,'area8','narea8','i');
		invisible_navegador(navegador,'area9','narea9','i');
		invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','v');
		invisible_navegador(navegador,'area12','narea12','i');
		}	
		invisible_navegador(navegador,'area1','narea1','i');	    	
		invisible_navegador(navegador,'area2','narea2','i');	    	
		invisible_navegador(navegador,'area3','narea3','v');	
		invisible_navegador(navegador,'area4','narea4','i');  
		invisible_navegador(navegador,'area12','narea12','i');
		
		document.frm1.listbox1.disabled=false;
		document.frm1.listbox2.disabled=false; 
		document.frm1.todos.disabled=false;
		document.frm1.uno.disabled=false;
		document.frm1.rtodos.disabled=false;
		document.frm1.runo.disabled=false;			
		doFill1();
	} 
	if (valor==6)
	{	
		invisible_navegador(navegador,'area1','narea1','i');	    	
		invisible_navegador(navegador,'area2','narea2','i');	    	
		invisible_navegador(navegador,'area3','narea3','i');	    					
		invisible_navegador(navegador,'area4','narea4','v');	
		invisible_navegador(navegador,'area5','narea5','v');
		invisible_navegador(navegador,'area6','narea6','i');	
		invisible_navegador(navegador,'area12','narea12','i');
		
		removeAllOptions(document.frm1.listbox2);
		//sel_DesactivarOpcion(document.frm1.listbox1);
		//sel_ActivarOpcion(document.frm1.listbox1, "01");
		//doAdd1();
		/*document.frm1.listbox1.disabled=true;
		document.frm1.listbox2.disabled=true;    					
		document.frm1.todos.disabled=true;
		document.frm1.uno.disabled=true;
		document.frm1.rtodos.disabled=true;
		document.frm1.runo.disabled=true;
		*/
		document.frm1.listbox1.disabled=false;
		document.frm1.listbox2.disabled=false; 
		document.frm1.todos.disabled=false;
		document.frm1.uno.disabled=false;
		document.frm1.rtodos.disabled=false;
		document.frm1.runo.disabled=false;
		doFill1();
   }  	 	  	
	<%if (flagInterno==true) { %>  	
	llenaComboHijo3();
	<%}%>
	//Tarifario
	//costear();
	//Inicio:jascencio:13/07/07
	//CC: REGMOBCON-2006
	if(valor==28){
	<% if (flagInterno==true) { %>
		invisible_navegador(navegador,'area7','narea7','v');
		<%}%>
		invisible_navegador(navegador,'area1','narea1','i');
		invisible_navegador(navegador,'area2','narea2','i');
		invisible_navegador(navegador,'area3','narea3','i');
		invisible_navegador(navegador,'area4','narea4','i');
		invisible_navegador(navegador,'area4','narea5','i');		
		invisible_navegador(navegador,'area4','narea7','i');
		invisible_navegador(navegador,'area6','narea6','i');	
		invisible_navegador(navegador,'area8','narea8','i');
		invisible_navegador(navegador,'area9','narea9','i');
		invisible_navegador(navegador,'area10','narea10','i');
		invisible_navegador(navegador,'area11','narea11','i');
		invisible_navegador(navegador,'area12','narea12','i');
		invisible_navegador(navegador,'area13','narea13','v');
		invisible_navegador(navegador,'area14','narea14','i');
		invisible_navegador(navegador,'area15','narea15','v');
		document.frm1.costo.value = costoRMC;
		
	}
	//Fin:jascencio
}

var arrSede = new Array();
var contador=0;
<%
int k, l;
if (flagInterno==true) {  %>  	

var arrProvincias = [
<% java.util.ArrayList arrx = (java.util.ArrayList) request.getAttribute("arrProvincias"); 
int sx = arrx.size();
for (int w =0; w < sx; w++) 
{
Value05Bean bean = (Value05Bean) arrx.get(w); %>
[<%=bean.getValue01()%>,<%=bean.getValue02()%>,"<%=bean.getValue03()%>"]<%if (w<(sx-1)){%>,<%}}%>
];

var arrDistritos = [
<% arrx = (java.util.ArrayList) request.getAttribute("arrDistritos"); 
sx = arrx.size();
for (int w =0; w < sx; w++) 
{
Value05Bean bean = (Value05Bean) arrx.get(w); %>
[<%=bean.getValue01()%>,<%=bean.getValue02()%>,<%=bean.getValue03()%>,"<%=bean.getValue04()%>"]<%if (w<(sx-1)){%>,<%}}%>
];


<%}%>

var arr4 = new Array();

var arr5 = new Array();
//Tarifario
var arr6 = new Array();//Tarifas por grupolibroarea
var costoMayor = "";

<% 
  k = 0; 
  l = 0;
%>
<logic:iterate name="arreglo12" id="item12" scope="request">
	var arrzz = new Array();
	arrzz[0]="<bean:write name="item12" property="value01"/>";
	arrzz[1]="<bean:write name="item12" property="value02"/>";
	//Tarifario
	//arrzz[2]="<bean:write name="item12" property="value03"/>";
	costoMayor = "<bean:write name="item12" property="value02"/>";
	//Tarifario
	arr5[<%=k%>]=arrzz;
	<%  
		k++; 
		if(k==14){
		  k=0;
		  l+=1;
	%>
		arr6[<%=l%>]=arr5;
		arr5 = new Array();
	<%  
		}
	%>
</logic:iterate>

function doAdd1()
{
	var objeto = document.frm1.listbox1;
	var flag001=false;
	
	if (objeto.length != 0)
	{ 
		if (objeto.selectedIndex != -1)
		{
			/**Validacion de zonas**/
			/**Aqui se pone el orden que ocupa en la lista para la validacion**/
			if(document.frm1.comboAreaLibro.value=="6" //Vehicular
			   && (objeto.selectedIndex!=8
			   && objeto.selectedIndex!=10
               && objeto.selectedIndex!=2
                                                   && objeto.selectedIndex!=12
                                                   && objeto.selectedIndex!=0
                                                   && objeto.selectedIndex!=1
                                                   && objeto.selectedIndex!=3
                                                   && objeto.selectedIndex!=4
                                                   && objeto.selectedIndex!=5
                                                   && objeto.selectedIndex!=6
                                                   && objeto.selectedIndex!=7
                                                   && objeto.selectedIndex!=9
                                                   && objeto.selectedIndex!=11)
			   )
			{
				alert("La Zona Registral seleccionada no está disponible");
				return;
			}
			/****/
			for(var i=0; i < objeto.options.length ; ++i)
			{
				if (objeto.options[i].selected)
				{
					var g = objeto.options[i].value;
					var h = objeto.options[i].text;
					var flag = false;
					for (var j = 0; j < arrSede.length ; j++)
					{
						var estado = arrSede[j][0];
						var codigo = arrSede[j][1];
						if (estado != "**********" )
						{
							if (codigo == g)
							{
								flag = true;
							}
						}
					} 
					if (flag == false)
					{
						var arrx = new Array();
						arrx[0]="activo";
						arrx[1]=g;
						arrx[2]=h;
						arrSede[contador] = arrx;
						contador++;
						if (g=="01")
							flag001=true;
					}
				}
			} 
		}
	}
	  
	doFill1();
} 

function doRemove1()
{
var objeto = document.frm1.listbox2;

if (objeto.length != 0)
  { 
     if (objeto.selectedIndex != -1)
     {
		for(var i=0; i < objeto.options.length ; ++i)
		{
	   		if (objeto.options[i].selected)
	   		{
	   			var g = objeto.options[i].value;
	   			//if (arrSede[g][1]=="35")
	   			//invisible_navegador(navegador,'area5','narea5','i');	
	       		arrSede[g][0]="**********";  
	       		objeto.options[i] = null;	       
	       		--i;
	   		}
        }           
     }
  }   
  
doFill1();
} 

function doFill1()
{
var objeto = document.frm1.listbox2;
if (objeto.length != 0)
	{ 
		for(var i=0; i<objeto.options.length ; ++i)
			{
				objeto.options[i]=null;
						--i;
			}
    	}
var cuenta = 0;    	
	for (var j=0; j < arrSede.length; j++)
		{
		var activo = arrSede[j][0];
		var xTexto = arrSede[j][2];
		var xCode  = arrSede[j][1];
		if (activo != "**********")
			{
			objeto.options[objeto.options.length] = new Option(xTexto,j);
			<%-- 3ene2003: Buscar en Lima NO cuesta, por lo tanto no se cuenta
			               como sede para cobrar--%>
			<%-- 31jul2003: Buscar en Lima SI cuesta, por lo tanto si se cuenta
			               como sede para cobrar--%>			 
			 valor = document.frm1.comboAreaLibro.value;
			 <%--alert("codigo " + xCode);--%>
			 <%--alert("valor" + valor); --%>             
			 
			//if (xCode!="01")
				cuenta++;
			//else		
				//if(valor=="24000")
					//cuenta++;               				
			
			}
		} 

var xcosto = 0;

// 20040112 kuma, corregido problema de generacion secuencial y referencia por codigo
//if (cuenta >= arr5.length)
//if (cuenta >= arr6[document.frm1.comboAreaLibro.options[document.frm1.comboAreaLibro.selectedIndex].value].length)
if (cuenta >= arr6[document.frm1.comboAreaLibro.selectedIndex + 1].length)
	xcosto = costoMayor;
else
{
	var var1 = document.frm1.comboAreaLibro.selectedIndex + 1;
	if(var1==5)
	{   
		xcosto = arr6[2][cuenta][1];
	}
	else
	{
		xcosto = arr6[document.frm1.comboAreaLibro.selectedIndex + 1][cuenta][1];
	}
}	
	//xcosto = arr6[document.frm1.comboAreaLibro.options[document.frm1.comboAreaLibro.selectedIndex].value][cuenta][1];
	//xcosto = arr5[document.frm1.comboAreaLibro.options[document.frm1.comboAreaLibro.selectedIndex].value][cuenta];

	
<%--alert("costo " + xcosto);--%>
document.frm1.costo.value = xcosto;
//alert("costo: "+document.frm1.costo.value);
} 

function doSendChildren()
{   
	var v = 0;
   var cadena1="";
   for(var i=0; i< contador; ++i)
   {  
      if (arrSede[i][0]!="**********")
      	{
      		v++;
          cadena1 = cadena1 + arrSede[i][1] + "*"; //end of field
        }
   }   
   document.frm1.hid1.value=cadena1;
   return v;
}

function llenaComboHijo()
{
if (global_pidedis==true)
	{
	doSendChildren();
	document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=verFormulario";
	document.frm1.submit();
	}
var obj1;
var obj2;
obj1 = document.frm1.area3PredioDepartamento;  
obj2 = document.frm1.area3PredioProvincia;  
var codigoPapa ="";
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPapa=obj1.options[i].value;
				break;
			}
	}
if (obj2.length != 0)
	{ 
		for(var i=0; i<obj2.options.length ; ++i)
			{
				obj2.options[i]=null;
						--i;
			}
    }
for (var j=0; j < arrProvincias.length; j++)
{		
 var xdpt = arrProvincias[j][0];
 var xpro = arrProvincias[j][1];
 var xnom = arrProvincias[j][2];
 if (xdpt == codigoPapa)
   obj2.options[obj2.options.length] = new Option(xnom,xpro);
}
llenaComboHijo2();
}

function llenaComboHijo2()
{
if (global_pidedis==true)
	{
	doSendChildren();
	document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=verFormulario";
	document.frm1.submit();
	}
var obj1;
var obj2;
obj1 = document.frm1.area3PredioDepartamento;  
obj2 = document.frm1.area3PredioProvincia;  
obj3 = document.frm1.area3PredioDistrito;  
var codigoAbuelo ="";
for(var i=0; i< obj1.options.length; i++)
{
if (obj1.options[i].selected)
{
codigoAbuelo=obj1.options[i].value;
break;
}
}
var codigoPapa ="";
for(var i=0; i< obj2.options.length; i++)
{
if (obj2.options[i].selected)
{
codigoPapa=obj2.options[i].value;
break;
}
}	
if (obj3.length != 0)
{ 
for(var i=0; i<obj3.options.length ; ++i)
{
obj3.options[i]=null;
--i;
}
}
for (var j=0; j < arrDistritos.length; j++)
{
var xdpt = arrDistritos[j][0];
var xpro = arrDistritos[j][1];
var xdis = arrDistritos[j][2];
var xnom = arrDistritos[j][3];
if (xdpt == codigoAbuelo && xpro == codigoPapa)
    obj3.options[obj3.options.length] = new Option(xnom,xdis);
}
}

function llenaComboHijo3()
{
<%-- _______3dic
var obj1;
var obj2;
var obj3;
var obj4;

obj1 = document.frm1.comboAreaLibro;  //papa
obj2 = document.frm1.area1TipoParticipacion;  //hijo1
obj3 = document.frm1.area2TipoParticipacion;  //hijo2
obj4 = document.frm1.area3TipoParticipacion;  //hijo3

//obtener codigo de papa
var codigoPapa ="";
for(var i=0; i< obj1.options.length; i++)
	{
		if (obj1.options[i].selected)
			{
				codigoPapa=obj1.options[i].value;
				break;
			}
	}

//limpiar combos hijos
if (obj2.length != 0)
	{ 
		for(var i=0; i<obj2.options.length ; ++i)
			{
				obj2.options[i]=null;
						--i;
			}
    }
if (obj3.length != 0)
	{ 
		for(var i=0; i<obj3.options.length ; ++i)
			{
				obj3.options[i]=null;
						--i;
			}
    }    
if (obj4.length != 0)
	{ 
		for(var i=0; i<obj4.options.length ; ++i)
			{
				obj4.options[i]=null;
						--i;
			}
    }    
    
//llenar combos hijos con informaci&oacute;n de acuerdo al Id de combo padre

//primero opcion "TODOS"
				obj2.options[obj2.options.length] = new Option("(TODOS)","**");
				obj3.options[obj3.options.length] = new Option("(TODOS)","**");
				obj4.options[obj4.options.length] = new Option("(TODOS)","**");
				
var x0;
var x1;
var x2;			

for (var j=0; j < arr4.length; j++)
		{
			x0 = arr4[j][0];
			x1 = arr4[j][1];
			x2 = arr4[j][2];
			if (x2 == codigoPapa)
				{
				obj2.options[obj2.options.length] = new Option(x1,x0);
				obj3.options[obj3.options.length] = new Option(x1,x0);
				obj4.options[obj4.options.length] = new Option(x1,x0);
				}
		}
--%>
} 

function doBuscar(param1, param2)
{
	v = doSendChildren();
	// Inicio:mgarate:29/05/2007
   	var cadena = "oficinaregistral="+document.frm1.hid1.value+"/arearegistral="+document.frm1.comboAreaLibro.value
    // Fin:mgarate:29/05/2007
	if (v==0)
	{
		alert("Por favor seleccione al menos una sede");
		document.frm1.listbox1.focus();
		return;
	}
	
	var b1=false;
	var b2=false;
	var b3=false;
	if (param1 == 1)
	{
		if (param2==1)
		{
			b1=false;
			b2=false;
			b3=false;
			if (!esVacio(document.frm1.area1ApePat.value))
			{
				if (!esLongitudMayor(document.frm1.area1ApePat.value,2) || !contieneCarateresValidos(document.frm1.area1ApePat.value,"nombre"))
				{						
					alert("El Apellido Paterno debe contener al menos 2 caracteres válidos");
					document.frm1.area1ApePat.focus();
					return;
				}
				b1=true;
			}
			if (!esVacio(document.frm1.area1Nombre.value))
			{
				if (!esLongitudMayor(document.frm1.area1Nombre.value,2) || !contieneCarateresValidos(document.frm1.area1Nombre.value,"nombre"))
				{			
					alert("El Nombre del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.area1Nombre.focus();
					return;
				}
				b2=true;
			}
			if (!esVacio(document.frm1.area1ApeMat.value))
			{
				if (!esLongitudMayor(document.frm1.area1ApeMat.value,2) || !contieneCarateresValidos(document.frm1.area1ApeMat.value,"nombre"))
				{
					alert("El Apellido Materno del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.area1ApeMat.focus();
					return;
				}
				b3=true;
			}
			if (b1==true && (b2==true || b3==true))
			{
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNatural";
			}else{
				alert("Por favor ingrese el Apellido Paterno, y el Apellido Materno o el Nombre");
				return;
			}
		}
		if (param2==2)
		{
			if (!esVacio(document.frm1.area1Razon.value))
			{ 
				if (!esLongitudMayor(document.frm1.area1Razon.value,3))
				{
					alert("La Razón Social del Participante debe contener al menos 3 caracteres");
					document.frm1.area1Razon.focus();
					return;
				}
			}
			if (!esVacio(document.frm1.area1Siglas.value))
			{
				if (!esLongitudMayor(document.frm1.area1Siglas.value,2))
				{
					alert("Las Siglas del Participante deben contener al menos 2 caracteres");
					document.frm1.area1Siglas.focus();
					return;
				}
			}
			if (esVacio(document.frm1.area1Razon.value) && esVacio(document.frm1.area1Siglas.value))
			{
				alert("Por favor ingrese la Razón Social del Participante o las Siglas");
				document.frm1.area1Razon.focus();
				return;		
			}
			if (palabra(document.frm1.area1Razon.value)==true)
		    {
				alert("Por favor especifique mayor detalle para la búsqueda");
				document.frm1.area1Razon.focus();
				return;
		    }				
			if (tieneCaracterNoValido(document.frm1.area1Razon.value)==true || tieneCaracterNoValido(document.frm1.area1Siglas.value)==true)
			{
				alert("Por favor no ingrese caracteres no válidos");
				return;
			}				    
			ocultaDivs();
			document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridica";		
		}
	}
	if (param1 == 2)
	{
		if (param2==1)
		{
			b1=false;
			b2=false;
			b3=false;
			
			if (!esVacio(document.frm1.area2ApePat.value))
			{
				if (!esLongitudMayor(document.frm1.area2ApePat.value,2) || !contieneCarateresValidos(document.frm1.area2ApePat.value,"nombre"))
				{
					alert("El Apellido Paterno debe contener al menos 2 caracteres válidos");
					document.frm1.area2ApePat.focus();
					return;
				}
				b1=true;
			}
			if (!esVacio(document.frm1.area2ApeMat.value))
			{
				if (!esLongitudMayor(document.frm1.area2ApeMat.value,2) || !contieneCarateresValidos(document.frm1.area2ApeMat.value,"nombre"))
				{
					alert("El Apellido Materno del Participante debe contener al menos 2 caracteres válidos");
					document.frm1.area2ApeMat.focus();
					return;
				}
				b2=true;
			}	
			if (!esVacio(document.frm1.area2Nombre.value))
			{
				if (!esLongitudMayor(document.frm1.area2Nombre.value,2) || !contieneCarateresValidos(document.frm1.area2Nombre.value,"nombre"))
				{
					alert("El Nombre Participante debe contener al menos 2 caracteres válidos");
					document.frm1.area2Nombre.focus();
					return;
				}
				b3=true;
			}				
			if (b1==true && (b2==true || b3==true))
			{
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNatural";
			}
			else
			{
				alert("Por favor ingrese el Apellido Paterno, y el Apellido Materno o el Nombre");
				return;
			}
		}
		if (param2==2)
		{
			if (!esVacio(document.frm1.area2Razon1.value))
			{
				if (!esLongitudMayor(document.frm1.area2Razon1.value,3))
				{
					alert("La Razón Social del Participante debe contener al menos 3 caracteres");
					document.frm1.area2Razon1.focus();
					return;
				}
			}
			if (!esVacio(document.frm1.area2Siglas.value))
			{
				if (!esLongitudMayor(document.frm1.area2Siglas.value,2))
				{
					alert("Las Siglas del Participante deben contener al menos 2 caracteres");
					document.frm1.area2Siglas.focus();
					return;
				}
			}		
			if (esVacio(document.frm1.area2Razon1.value) && esVacio(document.frm1.area2Siglas.value))
			{
				alert("Por favor ingrese la Razón Social del Participante o las Siglas");
				document.frm1.area2Razon1.focus();
				return;
			}
			if (palabra(document.frm1.area2Razon1.value)==true)
			{
				alert("Por favor especifique mayor detalle para la búsqueda");
				document.frm1.area2Razon1.focus();
				return;
			}		
			if (tieneCaracterNoValido(document.frm1.area2Razon1.value)==true || tieneCaracterNoValido(document.frm1.area2Siglas.value)==true)
			{
				alert("Por favor no ingrese caracteres no válidos");
				return;
			}				    								
			ocultaDivs();
			document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridica";				
		}
		if (param2==3)
		{
			if ((esVacio(document.frm1.area2Razon2.value)) && (esVacio(document.frm1.area2SiglasB.value)))
			{
				alert("Por favor ingrese la Razón Social y/o las Siglas");
				document.frm1.area2Razon2.focus();
				return;			
			}
			if (!esVacio(document.frm1.area2Razon2.value))
			{
				if (!esLongitudMayor(document.frm1.area2Razon2.value,3))
				{
					alert("La Razón Social de la Persona Juridica debe contener al menos 3 caracteres");
					document.frm1.area2Razon2.focus();
					return;
				}
				if (tieneCaracterNoValido(document.frm1.area2Razon2.value) == true)
				{
					alert("Por favor no ingrese caracteres no válidos");
					document.frm1.area2Razon2.focus();
					return;
				}
			}
			if (!esVacio(document.frm1.area2SiglasB.value))
			{
				if (!esLongitudMayor(document.frm1.area2SiglasB.value,2))
				{
					alert("Las Siglas de la Persona Juridica deben contener al menos 2 caracteres");
					document.frm1.area2SiglasB.focus();
					return;
				}
				if (tieneCaracterNoValido(document.frm1.area2SiglasB.value)==true)
				{
					alert("Por favor no ingrese caracteres no válidos");
					document.frm1.area2SiglasB.focus();
					return;
				}
			}		
			if (palabra(document.frm1.area2Razon2.value) == true)
			{
				alert("Por favor especifique mayor detalle para la búsqueda");
				document.frm1.area2Razon2.focus();
				return;
			}
			ocultaDivs();
			document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarRazonSocial";
		}
	}
	if (param1 == 3)
	{
		if (param2==1)
		{
			// Inicio:mgarate:29/05/2007
		   	cadena = cadena+"/apepat="+document.frm1.area3ParticipanteApePat.value
		   	if(document.frm1.area3ParticipanteApeMat.value!="")
		   	{
		    	cadena = cadena+"/apemat="+document.frm1.area3ParticipanteApeMat.value
		   	}
		   	if(document.frm1.area3ParticipanteNombre.value!="")
		   	{
		   		cadena = cadena+"/nombre="+document.frm1.area3ParticipanteNombre.value
		   	}
			// Fin:mgarate:29/05/2007
			b1=false;
			b2=false;
			b3=false;
			//alert("cadena: "+cadena);
			if (!esVacio(document.frm1.area3ParticipanteApePat.value))
			{
				if (!esLongitudMayor(document.frm1.area3ParticipanteApePat.value,2) || !contieneCarateresValidos(document.frm1.area3ParticipanteApePat.value,"nombre"))
				{
					alert("El Apellido Paterno del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.area3ParticipanteApePat.focus();
					return;
				}
				b1=true;
			}
			if (!esVacio(document.frm1.area3ParticipanteApeMat.value))
			{
				if (!esLongitudMayor(document.frm1.area3ParticipanteApeMat.value,2) || !contieneCarateresValidos(document.frm1.area3ParticipanteApeMat.value,"nombre"))
				{
					alert("El Apellido Materno del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.area3ParticipanteApeMat.focus();
					return;
				}
				b2=true;
			}	
			if (!esVacio(document.frm1.area3ParticipanteNombre.value))
			{
				if (!esLongitudMayor(document.frm1.area3ParticipanteNombre.value,2) || !contieneCarateresValidos(document.frm1.area3ParticipanteNombre.value,"nombre"))
				{
					alert("El Nombre del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.area3ParticipanteNombre.focus();
					return;
				}
				b2=true;
			}			
			if (b1==true && (b2==true || b3==true))
			{
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNatural";
			}else{
				alert("Por favor ingrese el Apellido Paterno, y el Apellido Materno o el Nombre");
				return;
			}
		}
		if (param2==2)
		{
			// Inicio:mgarate:29/05/2007
		    if(document.frm1.area3ParticipanteRazon.value!="")
		    {
		    	cadena = cadena+"/razonsocial="+document.frm1.area3ParticipanteRazon.value
		   	}
		   	if(document.frm1.area3Siglas.value!="")
		   	{
		    	cadena = cadena+"/sigla="+document.frm1.area3Siglas.value
		   	}
			// Fin:mgarate:29/05/2007
			if (!esVacio(document.frm1.area3ParticipanteRazon.value))
			{
				if( !esLongitudMayor(document.frm1.area3ParticipanteRazon.value,3))
				{
					alert("Por favor ingrese correctamente la Razón Social de la Persona Juridica.\nLa Razón Social de la Persona Juridica debe contener al menos 3 caracteres");
					document.frm1.area3ParticipanteRazon.focus();
					return;
				}
			}
			if (!esVacio(document.frm1.area3Siglas.value))
			{
				if (!esLongitudMayor(document.frm1.area3Siglas.value,2))
					{
						alert("Por favor ingrese correctamente las Siglas de la Persona Juridica.\nLas Siglas de la Persona Juridica deben contener al menos 2 caracteres");
						document.frm1.area3Siglas.focus();
						return;
					}
				}		
				if (esVacio(document.frm1.area3ParticipanteRazon.value) && esVacio(document.frm1.area3Siglas.value))		
				{
					alert("Por favor ingrese la Razón Social de la Persona Jurídica o las Siglas");
					document.frm1.area3ParticipanteRazon.focus();
					return;				
				}
				if (palabra(document.frm1.area3ParticipanteRazon.value) == true)
				    {
					alert("Por favor especifique mayor detalle para la búsqueda");
					document.frm1.area3ParticipanteRazon.focus();
					return;
				    }	
				if (tieneCaracterNoValido(document.frm1.area3ParticipanteRazon.value)==true ||
				    tieneCaracterNoValido(document.frm1.area3Siglas.value)==true )
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}					    							
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridica";
		}
		
	if (param2==3)
		{	
		if (document.frm1.area3PredioProvincia.options.length == 0)
			{
			alert("Por favor seleccione la Provincia");
			document.frm1.area3PredioProvincia.focus();
			return;
			}
		if (document.frm1.area3PredioDistrito.options.length == 0)
			{
			alert("Por favor seleccione el Distrito");
			document.frm1.area3PredioDistrito.focus();
			return;
			}


		if (esVacio(document.frm1.area3PredioNombreZona.value))
			{
			alert("Campo Nombre Zona es requerido para la busqueda");
			document.frm1.area3PredioNombreZona.focus();
			return;
			}
					
		if (esVacio(document.frm1.area3PredioNombreVia.value))
			{
			alert("Campo Vía es requerido para la busqueda");
			document.frm1.area3PredioNombreVia.focus();
			return;
			}
		if (tieneCaracterNoValido(document.frm1.area3PredioNombreZona.value)==true ||
		    tieneCaracterNoValido(document.frm1.area3PredioNombreVia.value)==true || 
		    tieneCaracterNoValido(document.frm1.area3PredioNumero.value)==true ||
		    tieneCaracterNoValido(document.frm1.area3PredioInteriorNro.value)==true)
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}		
			ocultaDivs();
			document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPredio";
		}
		
	if (param2==41)
		{
		if (esVacio(document.frm1.area3MineriaDerechoMinero.value) && 
		 	esVacio(document.frm1.area3MineriaSociedad.value))
			{
			alert("Por favor ingrese Derecho o Sociedad para efectuar la búsqueda");
			document.frm1.area3MineriaDerechoMinero.focus();
			return;
			}		
		if (tieneCaracterNoValido(document.frm1.area3MineriaDerechoMinero.value)==true ||
		    tieneCaracterNoValido(document.frm1.area3MineriaSociedad.value)==true )
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}					
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarMineria";
		}
		
	if (param2==51)
		{
		// Inicio:mgarate:29/05/2007
		   if(document.frm1.area3EmbarcacionNumeroMatricula.value!="")
		   {
		   	 cadena = cadena+"/numeromatricula="+document.frm1.area3EmbarcacionNumeroMatricula.value
		   }
		   if(document.frm1.area3EmbarcacionNombre.value!="")
		   {
		     cadena = cadena+"/nombreembarcacion="+document.frm1.area3EmbarcacionNombre.value
		   }         
		// Fin:mgarate:29/05/2007
		if (esVacio(document.frm1.area3EmbarcacionNumeroMatricula.value) && 
		 	esVacio(document.frm1.area3EmbarcacionNombre.value))
			{
			alert("Por favor ingrese correctamente el Número de Matrícula o el Nombre de la Embarcación");
			document.frm1.area3EmbarcacionNumeroMatricula.focus();
			return;
			}	
		if (tieneCaracterNoValido(document.frm1.area3EmbarcacionNumeroMatricula.value)==true ||
		    tieneCaracterNoValido(document.frm1.area3EmbarcacionNombre.value)==true )
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}				
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarEmbarcacion";
		}
		
	if (param2==61)
		{
		// Inicio:mgarate:29/05/2007
		   if(document.frm1.area3BuqueNumeroMatricula.value!="")
		   { 
		   	 cadena = cadena+"/numeromatricula="+document.frm1.area3BuqueNumeroMatricula.value
		   }
		   if(document.frm1.area3BuqueNombre.value!="")
		   {
		     cadena = cadena+"/nombrebuque="+document.frm1.area3BuqueNombre.value
		   }         
		// Fin:mgarate:29/05/2007
		if (esVacio(document.frm1.area3BuqueNumeroMatricula.value) && esVacio(document.frm1.area3BuqueNombre.value))
			{
			alert("Por favor ingrese correctamente el Número de Matrícula o el Nombre del Buque");
			document.frm1.area3BuqueNumeroMatricula.focus();
			return;
			}	
		if (tieneCaracterNoValido(document.frm1.area3BuqueNumeroMatricula.value)==true)
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}					
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarBuque";
		}		
		
		
	if (param2==7)
		{
		// Inicio:mgarate:29/05/2007
		   cadena = cadena+"/numeromatricula="+document.frm1.area3AeronaveNumeroMatricula.value
		// Fin:mgarate:29/05/2007
		if (esVacio(document.frm1.area3AeronaveNumeroMatricula.value))
			{
			alert("Por favor ingrese correctamente el Número de Matrícula de la Aeronave");
			document.frm1.area3AeronaveNumeroMatricula.focus();
			return;
			}	
		if (tieneCaracterNoValido(document.frm1.area3AeronaveNumeroMatricula.value)==true)
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}				
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarAeronaveXMatricula";
		}
		
	if (param2==8)
		{
		// Inicio:mgarate:29/05/2007
		   cadena = cadena+"/apepataero="+document.frm1.area3AeronaveApePat.value
		   if(document.frm1.area3AeronaveApeMat.value!="")
		   {
		   	 cadena = cadena+"/apemataero="+document.frm1.area3AeronaveApeMat.value
		   }
		   if(document.frm1.area3AeronaveNombre.value!="")
		   {
		     cadena = cadena+"/nombreaero="+document.frm1.area3AeronaveNombre.value
		   }
		// Fin:mgarate:29/05/2007
		if (esVacio(document.frm1.area3AeronaveApePat.value) && esVacio(document.frm1.area3AeronaveNombre.value))
			{
			alert("El Apellido y el Nombre del Participante deben contener al menos 2 caracteres");
			document.frm1.area3AeronaveApePat.focus();
			return;
			}
		if (esVacio(document.frm1.area3AeronaveApePat.value) || !esLongitudMayor(document.frm1.area3AeronaveApePat.value,2) ||!contieneCarateresValidos(document.frm1.area3AeronaveApePat.value,"nombre"))
			{
			alert("El Apellido y el Nombre del Participante deben contener al menos 2 caracteres");
			document.frm1.area3AeronaveApePat.focus();
			return;
			}
		if (!esVacio(document.frm1.area3AeronaveApeMat.value))
			{
			if (!esLongitudMayor(document.frm1.area3AeronaveApeMat.value,2) || !contieneCarateresValidos(document.frm1.area3AeronaveApeMat.value,"nombre"))
				{
				alert("El Apellido Materno del Participante deben contener al menos 2 caracteres");
				document.frm1.area3AeronaveApeMat.focus();
				return;
				}
			}	
		if (esVacio(document.frm1.area3AeronaveNombre.value) || !esLongitudMayor(document.frm1.area3AeronaveNombre.value,2) || !contieneCarateresValidos(document.frm1.area3AeronaveNombre.value,"nombre"))
			{
			alert("El Apellido Paterno y el Nombre del Participante deben contener al menos 2 caracteres");
			document.frm1.area3AeronaveNombre.focus();
			return;
			}
		if (tieneCaracterNoValido(document.frm1.area3AeronaveApePat.value)==true ||
		    tieneCaracterNoValido(document.frm1.area3AeronaveApeMat.value)==true ||
		    tieneCaracterNoValido(document.frm1.area3AeronaveNombre.value)==true)
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}		
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarAeronaveXNombre";
		}
		
	if (param2==9)
		{
		// Inicio:mgarate:29/05/2007
		   if(document.frm1.area3AeronaveRazon.value!="")
		   {
		     cadena = cadena+"/razonsocialaero="+document.frm1.area3AeronaveRazon.value
		   }
		   if(document.frm1.area3SiglasB.value!="")
		   {
		     cadena = cadena+"/siglaaero="+document.frm1.area3SiglasB.value
		   }
		// Fin:mgarate:29/05/2007
		if (esVacio(document.frm1.area3AeronaveRazon.value) || !esLongitudMayor(document.frm1.area3AeronaveRazon.value,2))
			{
			alert("La Razón Social de la Persona Jurídica debe contener al menos 2 caracteres");
			document.frm1.area3AeronaveRazon.focus();
			return;
			}
		if (!esVacio(document.frm1.area3SiglasB.value))
			{
			if (!esLongitudMayor(document.frm1.area3SiglasB.value,2))
				{
				alert("Las Siglas de la Persona Jurídica deben contener al menos 2 caracteres");
				document.frm1.area3SiglasB.focus();
				return;
				}
			}		
		if (tieneCaracterNoValido(document.frm1.area3AeronaveRazon.value)==true ||
		    tieneCaracterNoValido(document.frm1.area3SiglasB.value)==true)
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}		
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarAeronaveXRazonSocial";
		}
}

if (param1 == 4)
{
	
	if (param2==1)
		{
		// Inicio:mgarate:29/05/2007
		   cadena = cadena+"/apepat="+document.frm1.area4PropietarioApePat.value
		   if(document.frm1.area4PropietarioApeMat.value!="")
		   {
		     cadena = cadena+"/apemat="+document.frm1.area4PropietarioApeMat.value
		   }
		   if(document.frm1.area4PropietarioNombre.value!="")
		   {
		     cadena = cadena+"/nombre="+document.frm1.area4PropietarioNombre.value
		   }
		// Fin:mgarate:29/05/2007
		if (esVacio(document.frm1.area4PropietarioApePat.value) && esVacio(document.frm1.area4PropietarioNombre.value) && esVacio(document.frm1.area4PropietarioApeMat.value))
			{
			alert("El Apellido Paterno deben contener al menos 2 caracteres. Además el Nombre y/o Apellido Materno del Propietario deben contener al menos 2 caracteres");
			document.frm1.area4PropietarioApePat.focus();
			return;
			}
		if (esVacio(document.frm1.area4PropietarioNombre.value) && esVacio(document.frm1.area4PropietarioApeMat.value))
			{
			alert("El Nombre y/o Apellido Materno del Propietario deben contener al menos 2 caracteres");
			document.frm1.area4PropietarioNombre.focus();
			return;
			}			
		if (esVacio(document.frm1.area4PropietarioApePat.value) || !esLongitudMayor(document.frm1.area4PropietarioApePat.value,2) ||!contieneCarateresValidos(document.frm1.area4PropietarioApePat.value,"nombre"))
			{
			alert("El Apellido del Propietario deben contener al menos 2 caracteres");
			document.frm1.area4PropietarioApePat.focus();
			return;
			}
		if (!esVacio(document.frm1.area4PropietarioApeMat.value))
			{
			if (!esLongitudMayor(document.frm1.area4PropietarioApeMat.value,2) || !contieneCarateresValidos(document.frm1.area4PropietarioApeMat.value,"nombre"))
				{
				alert("El Apellido Materno del Ppropietario deben contener al menos 2 caracteres");
				document.frm1.area4PropietarioApeMat.focus();
				return;
				}
			}
		if (!esVacio(document.frm1.area4PropietarioNombre.value))	
			{			
			if ( !esLongitudMayor(document.frm1.area4PropietarioNombre.value,2) || !contieneCarateresValidos(document.frm1.area4PropietarioNombre.value,"nombre"))
				{
				alert("Por favor ingrese correctamente el Apellido Paterno y el Nombre del Propietario. Deben contener al menos 2 caracteres");
				document.frm1.area4PropietarioNombre.focus();
				return;
				}
			}
		if (tieneCaracterNoValido(document.frm1.area4PropietarioApePat.value)==true ||
		    tieneCaracterNoValido(document.frm1.area4PropietarioApeMat.value)==true ||
		    tieneCaracterNoValido(document.frm1.area4PropietarioNombre.value)==true)
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}			
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicular&tipo=1";
		}

	if (param2==2)
		{
		// Inicio:mgarate:29/05/2007
		   cadena = cadena+"/razonsocial="+document.frm1.area4PropietarioRazon.value
		// Fin:mgarate:29/05/2007
				if (!esVacio(document.frm1.area4PropietarioRazon.value))
				{
				 if( !esLongitudMayor(document.frm1.area4PropietarioRazon.value,3))
					{
					alert("Por favor ingrese correctamente la Razón Social de la Persona Jurídica.\nLa Razón Social de la Persona Juridica debe contener al menos 3 caracteres");
					document.frm1.area4PropietarioRazon.focus();
					return;
					}
				}
		
				if (esVacio(document.frm1.area4PropietarioRazon.value))		
				{
					alert("Por favor ingrese la Razón Social de la Persona Jurídica");
					document.frm1.area4Propietario.focus();
					return;				
				}
				if (palabra(document.frm1.area4PropietarioRazon.value) == true)
				    {
					alert("Por favor especifique mayor detalle para la búsqueda");
					document.frm1.area4PropietarioRazon.focus();
					return;
				    }	
				if (tieneCaracterNoValido(document.frm1.area4PropietarioRazon.value)==true  )
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}					    							
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicularJuri&tipo=2";
		}
		
	if (param2==3)
		{
			   // Inicio:mgarate:29/05/2007
			   	  cadena = cadena+"/numeromotor="+document.frm1.area4NumMotor.value
		       // Fin:mgarate:29/05/2007
				if (!esVacio(document.frm1.area4NumMotor.value))
				{
				 if( !esLongitudMayor(document.frm1.area4NumMotor.value,3))
					{
					alert("Por favor ingrese correctamente el número de motor.\nEl número de motor debe contener al menos 3 caracteres");
					document.frm1.area4NumMotor.focus();
					return;
					}
				}		
		
				if (esVacio(document.frm1.area4NumMotor.value))		
				{
					alert("Por favor ingrese el número de motor");
					document.frm1.area4NumMotor.focus();
					return;				
				}
				if (palabra(document.frm1.area4NumMotor.value) == true)
				    {
					alert("Por favor especifique mayor detalle para la búsqueda");
					document.frm1.area4NumMotor.focus();
					return;
				    }	
				if (tieneCaracterNoValido(document.frm1.area4NumMotor.value)==true  )
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}					    							
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicularMotor&tipo=2";
		}
		
	if (param2==4)
		{
				// Inicio:mgarate:29/05/2007
				   cadena = cadena+"/chasis="+document.frm1.area4Chasis.value
				// Fin:mgarate:29/05/2007
				if (!esVacio(document.frm1.area4Chasis.value))
				{
				 if( !esLongitudMayor(document.frm1.area4Chasis.value,1))
					{
					alert("Por favor ingrese correctamente el número de serie.\nEl número de serie debe contener al menos 1 caracter");
					document.frm1.area4Chasis.focus();
					return;
					}
				}					
				if (esVacio(document.frm1.area4Chasis.value))		
				{
					alert("Por favor ingrese el número de chasis");
					document.frm1.area4Chasis.focus();
					return;				
				}
				if (palabra(document.frm1.area4Chasis.value) == true)
				    {
					alert("Por favor especifique mayor detalle para la búsqueda");
					document.frm1.area4Chasis.focus();
					return;
				    }	
				if (tieneCaracterNoValido(document.frm1.area4Chasis.value)==true  )
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}					    							
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarXNombreVehicularChasis&tipo=2";
		}
		
		
}

document.frm1.hid2.value = param1;
// Inicio:mgarate:29/05/2007
   document.frm1.criterio.value = cadena
// Fin:mgarate:29/05/2007
	alert("document.frm1.action"+document.frm1.action);
document.frm1.submit();
}

/***** inicio: jrosas: 11-07-07 ***/
function doBuscarRMC(param1, param2)
{   
	//************************************************************************************
	// SUNARP-RMC-BORRAR
	//inicio:mgarate:02/10/2007
	//descripcion: cambio para validar acceso a nuevos recursos
	//	<%--
	//	  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
	//	  long perfilusuarioid =usuarioBean.getPerfilId();
	//	--%>  
	//	
	//	if(noTieneAccesoRecursoRMC(11, <%--=usuarioBean.getPerfilId()%>, '<%=usuarioBean.getUserId()--%>')){
	//		return;	
	//	}
	//fin:mgarate:02/10/2007
	//************************************************************************************
	
	b1=false;
	b2=false;
	b3=false;	
	
  if (param1 == 3){
     // BUSQUEDA PARTICIPANTE POR PERSONA NATURAL
	 if (param2==1)
	 { 
		if (!esVacio(document.frm1.txtApellidoPaterno.value))
			{
			if (!esLongitudMayor(document.frm1.txtApellidoPaterno.value,2) || !contieneCarateresValidos(document.frm1.txtApellidoPaterno.value,"nombre"))
				{
				alert("El Apellido Paterno del Participante deben contener al menos 2 caracteres válidos");
				document.frm1.txtApellidoPaterno.focus();
				return;
				}
			b1=true;
			}
		if (!esVacio(document.frm1.txtApellidoMaterno.value))
			{
			if (!esLongitudMayor(document.frm1.txtApellidoMaterno.value,2) || !contieneCarateresValidos(document.frm1.txtApellidoMaterno.value,"nombre"))
				{
				alert("El Apellido Materno del Participante deben contener al menos 2 caracteres válidos");
				document.frm1.txtApellidoMaterno.focus();
				return;
				}
			b2=true;
			}	
		if (!esVacio(document.frm1.txtNombres.value))
			{
			if (!esLongitudMayor(document.frm1.txtNombres.value,2) || !contieneCarateresValidos(document.frm1.txtNombres.value,"nombre"))
				{
				alert("El Nombre del Participante deben contener al menos 2 caracteres válidos");
				document.frm1.txtNombres.focus();
				return;
				}
			b2=true;
			}				
		
		if (b1==true && (b2==true || b3==true))
		{
			ocultaDivs();
			document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNaturalRmc&tipo=N";
			document.frm1.submit();
		}
		else
		{
			alert("Por favor ingrese el Apellido Paterno, y el Apellido Materno o el Nombre");
			return;
		}
	}
	
	// BUSQUEDA PARTICIPANTE POR PERSONA JURIDICA
	if (param2==2)
	{	
		if (!esVacio(document.frm1.txtRazonSocial.value))
		{
		  if( !esLongitudMayor(document.frm1.txtRazonSocial.value,3))
		  {
			 alert("Por favor ingrese correctamente la Razón Social de la Persona Juridica.\nLa Razón Social de la Persona Juridica debe contener al menos 3 caracteres");
			 document.frm1.txtRazonSocial.focus();
			 return;
		  }
		}
		if (!esVacio(document.frm1.txtSiglas.value))
		{
		  if (!esLongitudMayor(document.frm1.txtSiglas.value,2))
		  {
			 alert("Por favor ingrese correctamente las Siglas de la Persona Juridica.\nLas Siglas de la Persona Juridica deben contener al menos 2 caracteres");
			 document.frm1.txtSiglas.focus();
			 return;
		  }
		}		
		if (esVacio(document.frm1.txtRazonSocial.value) && esVacio(document.frm1.txtSiglas.value))		
		{
			alert("Por favor ingrese la Razón Social de la Persona Jurídica o las Siglas");
			document.frm1.txtRazonSocial.focus();
			return;				
		}
		if (palabra(document.frm1.txtRazonSocial.value) == true)
		{
		    alert("Por favor especifique mayor detalle para la búsqueda");
			document.frm1.txtRazonSocial.focus();
			return;
		}	
		if (tieneCaracterNoValido(document.frm1.txtRazonSocial.value)==true ||
		    tieneCaracterNoValido(document.frm1.txtSiglas.value)==true )
		{
			alert("Por favor no ingrese caracteres no válidos");
			return;
		}					    							
		ocultaDivs();
		document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridicaRmc&tipo=J";
		document.frm1.submit();
	}
	
	 // BUSQUEDA PARTICIPANTE POR TIPO Y NUMERO DE DOCUMENTO
	if (param2==3)
	{
	    if(document.frm1.cboTipoDocumento.value==""){
			alert("Debe ingresar el tipo de documento");
			return;
		}else{
		    if(document.frm1.txtNumeroDocumento.value==""){
				alert("Debe ingresar el Número de documento");
				return;
			}else{	
				if(!isNumericNoSpaces(trim(document.frm1.txtNumeroDocumento.value))){
					alert("Por favor ingrese correctamente el Número del Documento. \nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
					return;
				}
				else{
					if(document.frm1.txtNumeroDocumento.value.length<8){
						alert("Por favor ingrese correctamente el Número del Documento. \nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
						frm1.txtNumeroDocumento.focus();
						return;
					}
				}
			}	
	    }
		ocultaDivs();
	    document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarTipoNumeroDocumentoRmc&tipo=D";
	    document.frm1.submit();
	}  
	
	// BUSQUEDA PARTICIPANTE POR BIENES
    if (param2==4)
	{
		if (esVacio(document.frm1.txtNumeroPlaca.value) && esVacio(document.frm1.txtOtrosDatos.value))		
		{
			alert("Por favor ingrese el número de placa u otros datos");
			document.frm1.txtNumeroPlaca.focus();
			return;				
		}
		
		if (!esVacio(document.frm1.txtNumeroPlaca.value) && !esVacio(document.frm1.txtOtrosDatos.value))		
		{
			alert("Debe inggresar sólo uno de los criterios para realizar la búsqueda");
			document.frm1.txtNumeroPlaca.focus();
			return;				
		}
		
   	    if (!esVacio(document.frm1.txtNumeroPlaca.value))
		{
		  
		   if (tieneEspacioBlancoGuion(document.frm1.txtNumeroPlaca.value))
	       {
		   		alert("Por favor ingrese correctamente el número de placa");
		 	    return;
		   }else{	
			   if(document.frm1.txtNumeroPlaca.value.length<6)
			   {
				  alert("Por favor ingrese correctamente el Número de Placa. \nEl Número de Placa requiere 6 caracteres ");
				  document.frm1.txtNumeroPlaca.focus();
				  return;
			   }else{
			     b1=true;
			   }
			} 
		}
			
		if (!esVacio(document.frm1.txtOtrosDatos.value)) // numero de serie, numero de motor o descripcion del bien
		{
			if (tieneCaracterNoValido(document.frm1.txtOtrosDatos.value)==true )
	    	{
		   		alert("Por favor no ingrese caracteres no válidos");
		 	    return;
			}else{	  
				if(document.frm1.txtOtrosDatos.value.length<3) // minimo numero de caracteres
				{ 
					alert("Por favor ingrese correctamente Otros datos. \nEl campo Otros datos requiere por lo menos 3 caracteres ");
					document.frm1.txtOtrosDatos.focus();
					return;
				}else{
			   		b2=true;
				}
			}
        }
		
		if (b1==true || b2==true)
		{
			ocultaDivs();
			document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarBienesRmc&tipo=B";
			document.frm1.submit();
		}
		else
		{
			alert("Por favor ingrese el Número de placa u Otros Datos");
			return;
		}
			   
	} 
	
  }	
}
/***** fin: jrosas: 11-07-07 ***/
// Inicio:jascencio:17/07/07
//	 CC:REGMOBCON-2006
function doBuscarSIGC(param1,param2)
{
	//************************************************************************************
	// SUNARP-RMC-BORRAR
	//inicio:mgarate:02/10/2007
	//descripcion: cambio para validar acceso a nuevos recursos	
	//	if(noTieneAccesoRecursoRMC(12, <%--=usuarioBean.getPerfilId()%>, '<%=usuarioBean.getUserId()--%>')){
	//		return;	
	//	}
	//fin:mgarate:02/10/2007
	//************************************************************************************
	
	if(param1==4){
		if(param2==1){
			
			b1=false;
			b2=false;
			b3=false;
			
			if (!esVacio(document.frm1.txtParticipanteApePatArea15.value))
				{
				if (!esLongitudMayor(document.frm1.txtParticipanteApePatArea15.value,2) || !contieneCarateresValidos(document.frm1.txtParticipanteApePatArea15.value,"nombre"))
					{
					alert("El Apellido Paterno del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.txtParticipanteApePatArea15.focus();
					return;
					}
				b1=true;
				}
			if (!esVacio(document.frm1.txtParticipanteApeMatArea15.value))
				{
				if (!esLongitudMayor(document.frm1.txtParticipanteApeMatArea15.value,2) || !contieneCarateresValidos(document.frm1.txtParticipanteApeMatArea15.value,"nombre"))
					{
					alert("El Apellido Materno del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.txtParticipanteApeMatArea15.focus();
					return;
					}
				b2=true;
				}	
			if (!esVacio(document.frm1.txtParticipanteNombreArea15.value))
				{
				if (!esLongitudMayor(document.frm1.txtParticipanteNombreArea15.value,2) || !contieneCarateresValidos(document.frm1.txtParticipanteNombreArea15.value,"nombre"))
					{
					alert("El Nombre del Participante deben contener al menos 2 caracteres válidos");
					document.frm1.txtParticipanteNombreArea15.focus();
					return;
					}
				b2=true;
				}				
	
			if (b1==true && (b2==true || b3==true))
				{
					
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaNaturalSigc&tipo=N";
				}
			else
				{
				alert("Por favor ingrese el Apellido Paterno, y el Apellido Materno o el Nombre");
				return;
				}
			
		}else{
			if(param2==2){
			
				if (!esVacio(document.frm1.txtParticipanteRazonArea15.value))
					{ 
					if (!esLongitudMayor(document.frm1.txtParticipanteRazonArea15.value,3))
						{
						alert("La Razón Social del Participante debe contener al menos 3 caracteres");
						document.frm1.txtParticipanteRazonArea15.focus();
						return;
						}
					}
				if (!esVacio(document.frm1.txtSiglasArea15.value))
					{
					if (!esLongitudMayor(document.frm1.txtSiglasArea15.value,2))
						{
						alert("Las Siglas del Participante deben contener al menos 2 caracteres");
						document.frm1.txtSiglasArea15.focus();
						return;
						}
					}
					
				if (esVacio(document.frm1.txtParticipanteRazonArea15.value) && esVacio(document.frm1.txtSiglasArea15.value))
				{
						alert("Por favor ingrese la Razón Social del Participante o las Siglas");
						document.frm1.txtParticipanteRazonArea15.focus();
						return;		
				}
				
				if (palabra(document.frm1.txtParticipanteRazonArea15.value)==true)
				    {
					alert("Por favor especifique mayor detalle para la búsqueda");
					document.frm1.txtParticipanteRazonArea15.focus();
					return;
				    }				
				if (tieneCaracterNoValido(document.frm1.txtParticipanteRazonArea15.value)==true ||
				    tieneCaracterNoValido(document.frm1.txtSiglasArea15.value)==true)
					{
					alert("Por favor no ingrese caracteres no válidos");
					return;
					}				    
				ocultaDivs();
				document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarPersonaJuridicaSigc&tipo=J";
			
			}else{
				if(param2==3){
					if(document.frm1.cboTipoDocumentoArea15.value==""){
						alert("Debe ingresar el tipo de documento");
						return;
					}else{
						if(!isNumericNoSpaces(document.frm1.txtNumeroDocumentoArea15.value)){
							alert("Por favor ingrese correctamente el Número del Documento. \nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
							return;
						}
						else{
							if(document.frm1.txtNumeroDocumentoArea15.value.length<8){
								alert("Por favor ingrese correctamente el Número del Documento. \nEl Número del Documento requiere al menos 8 caracteres numéricos (0-9)");
								frm1.txtNumeroDocumentoArea15.focus();
								return;
							}
						}
			    	}
			    	
					ocultaDivs();
			    	document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarTipoNumeroDocumentoSigc&tipo=D";
			   
				}else{
					if(param2==4){
						//***
						if(document.frm1.txtNumeroPlacaArea15.value.length>0){
							if(document.frm1.txtNumeroMatriculaArea15.value.length>0){
								alert("Sólo puede ingresar en combinación nombre y número de matrícula");
								return;
							}
							else{
								if(document.frm1.txtNombreBienArea15.value.length>0){
									alert("Sólo puede ingresar en combinación nombre y número de matrícula");
									return;
								}
								else{
									if(document.frm1.txtNumeroSerieArea15.value.length>0){
										alert("Sólo puede ingresar en combinación nombre y número de matrícula");
										return;
									}
									else{
										
										if(!isAlphaNum(document.frm1.txtNumeroPlacaArea15.value)){
										  alert("Por favor ingrese correctamente el Numero de Placa.");
										  frm1.txtNumeroPlacaArea15.focus();
										  return;
										}
									}
								
								}
								
							}
						}
						else{
							if(document.frm1.txtNumeroMatriculaArea15.value.length>0){
								if(document.frm1.txtNumeroSerieArea15.value.length>0){
									alert("Sólo puede ingresar en combinación nombre y número de matrícula");
									return;
								}
								else{
								
								 	if(isCharacter(document.frm1.txtNumeroMatriculaArea15.value,"%")){
										alert("Por favor no ingrese caracteres no válidos");
										return;
									}
								}
							}
							else{
								if(document.frm1.txtNumeroSerieArea15.value.length>0){
									if(document.frm1.txtNombreBienArea15.value.length>0){
										alert("Sólo puede ingresar en combinación nombre y número de matrícula");
										return;
									}
									else{
										
										if(isCharacter(frm1.txtNumeroSerieArea15.value,"%")){
											alert("Por favor no ingrese caracteres no válidos");
											return;
										}
									}
								}
								else{
								 
									if(document.frm1.txtNombreBienArea15.value.length<=0){
										alert("Ingrese los campos requeridos");
										return;
									}
									
								}
							}
						
						}
						ocultaDivs();
						document.frm1.action="/iri/BuscaPartidasXIndicesIRI.do?state=buscarBienesSigc&tipo=B";
					}
				}
			}
		}
	}
	
	document.frm1.submit();
}

	function isCharacter(str,char){
	
	if (char == "undefined" || char == "null" ||char == "")
		return false;

	var bandera=false;
	str += ""; 

	for (i = 0; i < str.length; i++)
	{

		if (!(str.charAt(i) != char))
		{
		bandera = true;
		break;
		} 
	}
	return bandera;
	}
	function isAlphaNum( str ) {

	if (str+"" == "undefined" || str+"" == "null" || str+"" == "")
		return false;

	var isValid = true;
	str += ""; 

	for (i = 0; i < str.length; i++)
	{

		if (!(((str.charAt(i) >= "0") && (str.charAt(i) <= "9")) || 
		((str.charAt(i) >= "a") && (str.charAt(i) <= "z")) ||
		((str.charAt(i) >= "A") && (str.charAt(i) <= "Z"))))
		{
		isValid = false;
		break;
		} 
	}
	
	return isValid;
	}
	
//Fin:jascencio
function isNumericNoSpaces( str ) {

		if (str+"" == "undefined" || str+"" == "null" || str+"" == "")
			return false;
	
		var isValid = true;
		str += ""; 
	    //alert("str: "+str);
		for (i = 0; i < str.length; i++)
		{
	        //alert("charAt:"+str.charAt(i));
			if (!((str.charAt(i) >= "0") && (str.charAt(i) <= "9")))
			{
			isValid = false;
			break;
			} 
		}
		
		return isValid;
} 

function selectAllOptions(obj) {
	for (var i=0; i<obj.options.length; i++) {
		obj.options[i].selected = true;
	}
	doAdd1();
}
function removeAllOptions(obj) {
	for (var i=0; i<obj.options.length; i++) {
		obj.options[i].selected = true;
	}
	doRemove1();
}

function mayuscula(objeto){
		objeto.value=objeto.value.toUpperCase();
}
function ocultaDivs(){
  var i;
   for (i=1;i<8;i++){
		var divid= "ocultar"+i;
	   	div = document.getElementById(divid);
		div.style.visibility="hidden";
	}
	for (i=9;i<22;i++){
		var dividd= "ocultar"+i;
	   	div = document.getElementById(dividd);
		div.style.visibility="hidden";
	}
	for (i=23;i<27;i++){
		var dividd= "ocultar"+i;
	   	div = document.getElementById(dividd);
		div.style.visibility="hidden";
	}
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY onLoad="Cambio(document.frm1.comboAreaLibro.value);">
<div id="maincontent">
<div class="innertube">

<form name="frm1" method="post">
	<input type="hidden" name="hid1"> 
	<input type="hidden" name="hid2"> 
	<input type="hidden" name="pidedis" value="S"> 
	<br>
	<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">B&uacute;squeda de &Iacute;ndice de Partidas</b>

	<%-- Inicio:mgarate:29/05/2007 --%>
	<input type="hidden" name="criterio" value=""/>
	<%-- Fin:mgarate:29/05/2007 --%>
	<br>
	<%-- inicio: jrosas 11-07-07--%>

	<layer id="narea14">
		<div id="area14" style="visibility: visible">
			<table cellspacing=0>
			    <tr>
			        <td align=center>
				           <select multiple name="listbox1" size="10" width="260px" onDblClick="doAdd1()" style="width: 260px">
					            <option value="05">Zona Registral No I - Sede Piura</option>
					            <option value="11">Zona Registral No II - Sede Chiclayo</option>
					            <option value="12">Zona Registral No III - Sede Moyobamba</option>
					            <option value="09">Zona Registral No IV - Sede Iquitos</option>
					            <option value="08">Zona Registral No V - Sede Trujillo</option>
					            <option value="13">Zona Registral No VI - Sede Pucallpa</option>
					            <option value="04">Zona Registral No VII - Sede Huaraz</option>
					            <option value="02">Zona Registral No VIII - Sede Huancayo</option>
					            <option value="01">Zona Registral No IX - Sede Lima</option>
					            <option value="06">Zona Registral No X - Sede Cuzco</option>
					            <option value="10">Zona Registral No XI - Sede Ica</option>
					            <option value="03">Zona Registral No XII - Sede Arequipa</option>
					            <option value="07">Zona Registral No XIII - Sede Tacna</option>
				         </select></td>
				         <td align=center><input type="button" name="todos" value="&gt;&gt;" onclick="selectAllOptions(document.frm1.listbox1)" title="Seleccionar Todas las Oficinas Registrales" style="width: 25px"><br>
				         <input type="button" name="uno" value="&gt;" onclick="doAdd1()" title="Seleccionar Oficina Registral" style="width: 25px"><br>
				         <input type="button" name="runo" onClick="doRemove1()" value="&lt;" title="Retirar de la Seleccion la Oficina Registral" style="width: 25px"><br>
				         <input type="button" name="rtodos" value="&lt;&lt;" onclick="removeAllOptions(document.frm1.listbox2)" title="Retirar de la Seleccion Todas las Oficinas Registrales" style="width: 25px"><br>
				         </td>
				        <td align=center><select name="listbox2" multiple size="10" style="width: 260px" width="260px" onDblClick="doRemove1()">
				        </select>
			        </td>
			    </tr>
			</table>
		</div>	
	</layer>
	<%-- fin: jrosas 11-07-07--%>

	<table cellspacing=0>
	    <tr>
	        <td align="center">
	        	<layer id="narea6">
			        <div id="area6" style="visibility: visible;">
			        	<a class="linkInicio" href="javascript:VentanaFlotante('/iri/acceso/mapas/MAPA1.htm')" onmouseover="javascript:mensaje_status('Identifique su Oficina Registral');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Identifique su Oficina Registral</a>
			        </div>
			    </layer>
			</td>
	    </tr>
	</table>

	<table width="600px">
	    <tr>
	        <td align="center" vAlign="center">
	        	<layer id="narea5" visibility="hide">
			        <div id="area5" style="visibility: hidden; background-color: white; width: 100%; border-width: 1px; border-color: 000000; border-style: solid">
			          Vehículos registrados a nivel nacional.
			        </div>
	        	</layer>
	        </td>
	    </tr>
	</table>
	
	
	<table width="600px">
	<tr>
		<td align="center" width="100%">
			<layer id="narea13" visibility="hide">
		        <div id="area13" style="text-align:center;visibility: hidden; background-color: white; width: 100%; border-width: 1px; border-color: 000000; border-style: solid">
					B&uacute;squeda Nacional 
				</div>
			</layer>
		</td>
	</tr>
	</table>
	
	<table class="punteadoTablaTop" width="600px">
	    <tr> 
	    	<td align=left width="130">
	    		Area Registral
	    		<input type="hidden" name="comboArea">
				<input type="hidden" name="hidAreaLibro">
			</td>
			<td>
				<select name="comboAreaLibro" size="1" onchange=Cambio(this.value);>
		            <logic:iterate name="arrAreaLibro" id="item22" scope="request"> <logic:equal name="item22" property="codigo" value="1">
		            <option value="<bean:write name="item22" property="codigo"/>" selected><bean:write name="item22" property="descripcion"/></option>
		            </logic:equal> <logic:notEqual name="item22" property="codigo" value="1">
		            <option value="<bean:write name="item22" property="codigo"/>"><bean:write name="item22" property="descripcion"/></option>
		            </logic:notEqual> </logic:iterate>
		        </select>
			</td>
			<td align=center><font color="#949400"><b>Costo Por Búsqueda</b></font>&nbsp;<input type="text" name="costo" size="2" value="0" disabled="true"></td>
	    </tr>
	</table>


<%
String positionLeft="10";
String positionTop ="260";
%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *******************    DIV 1                *************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<layer id="narea1">
<div id="area1" style="position: absolute; left: < %=positionLeft% > px; top: < %=positionTop% > px; visibility: visible">
<table>
    <tr>
        <td>
        	<strong><font color="#949400" size="2">B&uacute;squeda Registro Personas Naturales por Participante</font></strong>
        </td>
    </tr>
</table>
<table >
    <tr>
        <td>
        	<strong><font color="#949400" size="2">Buscar por Participante Persona Natural</font></strong>
        </td>
    </tr>
</table>
<table class="punteadoTablaTop" width="600">
    <tr>
        <td width="130">Apellido Paterno</td>
        <td><input name="area1ApePat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
    </tr>
    <tr>
        <td width="130">Apellido Materno</td>
        <td><input name="area1ApeMat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
    </tr>
    <tr>
        <td width="130">Nombres</td>
        <td><input name="area1Nombre" style="width: 130" maxlength="40" onblur="sololet(this)"></td>
    </tr>
    <%if (flagInterno) { %>
    <tr>
        <td width="130"><input type="checkbox" name="checkInactivosPN3" checked> Incluir Inactivos</td>
        <td>&nbsp;</td>
    </tr>
    <%}%>
</table>

<table cellspacing=0 border="0">
  <tr>
	<td colspan="3" align="center">
  		<div id="ocultar1">
       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(1,1)" value="Buscar"/>
       	</div>
 	</td>
  </tr>
</table>

<br>
	<table cellspacing=0>
	    <tr>
	        <td>
	        	<strong><font color="#949400" size="2">Buscar por Participante Persona Jur&iacute;dica</font></strong>
	        </td>
	    </tr>
	</table>
	<table class="punteadoTabla" width="600px">
    <tr>
        <td width="130">Raz&oacute;n Social</td>
        <td><input type="text" name="area1Razon" style="width: 130" maxlength="150" onblur="solonumlet(this)"></td>
    </tr>
    <tr>
        <td width="130">Siglas</td>
        <td><input type="text" name="area1Siglas" style="width: 130" maxlength="50" onblur="solonumlet(this)"></td>
    </tr>
    <%if (flagInterno) { %>
    <tr>
        <td width="130"><input type="checkbox" name="checkInactivosPJ3" checked> Incluir Inactivos</td>
        <td>&nbsp;</td>
    </tr>
    <%}%>
	</table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar2">
	       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(1,2)" value="Buscar"/>
	       	</div>
	 	</td>
	  </tr>
	</table>


</div>
</layer> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *******************    DIV 2                *************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<layer id="narea2">
<div id="area2" style="position: absolute; left: < %=positionLeft% > px; top: < %=positionTop% > px; visibility: visible">
<table>
    <tr>
        <td>
        	<strong><font color="#949400" size="2">B&uacute;squeda Registro Personas Jur&iacute;dicas por Participante
        </td>
    </tr>
</table>
<table>
    <tr>
        <td colspan=5>
        	<strong><font color="#949400" size="2">Buscar por Participante Persona Natural</font></strong>
        </td>
    </tr>
</table>  
	<table class="punteadoTablaTop" width="600">
	    <tr>
	        <td width="130">Apellido Paterno</td>
	        <td><input name="area2ApePat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
	    </tr>
	    <tr>
	        <td width="130">Apellido Materno</td>
	        <td><input name="area2ApeMat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
	    </tr>
	    <tr>
	        <td width="130">Nombres</td>
	        <td><input name="area2Nombre" style="width: 130" maxlength="40" onblur="sololet(this)"></td>
	    </tr>
	    <tr>
	        <td width="130">&nbsp;</td>
	        <td>&nbsp;</td>
	    </tr>
	    <%if (flagInterno) { %>
	    <tr>
	        <td width="130"><input type="checkbox" name="checkInactivosPN2" checked> Incluir Inactivos</td>
	        <td>&nbsp;</td>
	    </tr>
	    <%}%>
	 </table>
 <table cellspacing=0 border="0">
  <tr>
	<td colspan="3" align="center">
  		<div id="ocultar3">
  			<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(2,1)" value="Buscar"/>
  		</div>
 	</td>
  </tr>
</table>
	<table>
    <tr>
        <td colspan=5>
        	<strong><font color="#949400" size="2">Buscar por Participante Persona Juridica</font></strong>
        </td>
    </tr>
    </table>
	<table class="punteadoTablaTop" width="600px">
	    <tr>
	        <td width="130">Raz&oacute;n Social</td>
	        <td ><input type="text" name="area2Razon1" style="width: 130" maxlength="150" onblur="solonumlet(this)"></td>
	    </tr>
	    <tr>
	        <td width="130">Siglas</td>
	        <td><input type="text" name="area2Siglas" style="width: 130" maxlength="50" onblur="solonumlet(this)"></td>
	    </tr>
	    <%if (flagInterno) { %>
	    <tr>
	        <td width="130"><input type="checkbox" name="checkInactivosPJ2" checked> Incluir Inactivos</td>
	        <td>&nbsp;</td>
	    </tr>
	    <%}%>
	</table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar4">
	       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(2,2)" value="Buscar"/>
	       	</div>
	 	</td>
	  </tr>
	</table>
	<br>
	<table >
	    <tr>
	        <td><strong><font color="#949400" size="2">B&uacute;squeda Registro Personas Jur&iacute;dicas por Raz&oacute;n Social</font></strong></td>
	    </tr>
	</table>
	<table class=punteadoTablaTop width="600">
	    <tr>
	        <td width="130">Raz&oacute;n Social</td>
	        <td><input type="text" name="area2Razon2" style="width: 130" maxlength="250" onblur="solonumlet(this)"></td>
	    </tr>
	    <tr>
	        <td width="130">Siglas</td>
	        <td><!--input type="hidden" name="area2SiglasB"--> <input type="text" name="area2SiglasB" style="width: 130" maxlength="50" onblur="solonumlet(this)"></td>
	    </tr>
	</table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar5">
	       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(2,3)" value="Buscar"/>
	       	<div>
	 	</td>
	  </tr>
	</table>
</div>
</layer> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *******************    DIV 3                *************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
<%-- *********************************************************************** --%> 
	<layer id="narea3">
	<div id="area3" style="position: absolute; left: < %=positionLeft% > px; top: < %=positionTop% > px; visibility: visible">
		<table>
		  <tr > 
			<td><strong><font color="#949400" size="2">B&uacute;squeda Registro de Propiedad Inmueble por Participante</font></strong></td>
		  </tr>
		</table>
	<table class="punteadoTabla" width="600px">
    <tr>
        <td colspan=2>
        	<strong><font color="#949400" size="2">Buscar por Participante Persona Natural</font></strong>
        </td>
    </tr>
	<tr>
        <td width="130">Apellido Paterno</td>
        <td><input type="text" name="area3ParticipanteApePat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
    </tr>
    <tr>
        <td width="130">Apellido Materno</td>
        <td><input type="text" name="area3ParticipanteApeMat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
    </tr>
    <tr>
        <td width="130">Nombres</td>
        <td><input type="text" name="area3ParticipanteNombre" style="width: 130" maxlength="40" onblur="sololet(this)"></td>
    </tr>
    <%if (flagInterno) { %>
    <tr>
        <td width="130"><input type="checkbox" name="checkInactivosPN1" checked> Incluir Inactivos</td>
        <td>&nbsp;</td>
    </tr>
    <%}%>
  </table>
  <table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar6">
        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,1)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Propiedad Inmueble por Nombre de Participante Persona Natural');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
        	</div>
	 	</td>
	  </tr>
  </table>
  <table class="punteadoTabla" width="600px">
	<tr>
        <td colspan=2><strong><font color="#949400" size="2">Buscar por Participante Persona Juridica</font></strong></td>
    </tr>
    <tr>
        <td width="130">Raz&oacute;n Social</td>
        <td><input type="text" name="area3ParticipanteRazon" style="width: 130" maxlength="150" onblur="solonumlet(this)"></td>
    </tr>
    <tr> 
        <td width="130">Siglas</td>
        <td><input type="text" name="area3Siglas" style="width: 130" maxlength="60" onblur="sololet(this)"></td>
    </tr>
    <%if (flagInterno) { %>
	    <tr>
	        <td width="130"><input type="checkbox" name="checkInactivosPJ1" checked> Incluir Inactivos</td>
	        <td>&nbsp;</td>
	    </tr>
    <%}%>
  </table>
  <table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar7">
        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,2)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Propiedad Inmueble por Nombre de Participante Persona Juridica');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
        	</div>
	 	</td>
	  </tr>
	</table>
  
<br>

<% if (flagInterno==true) { %>
<!--Tarifario-->
<%-- *******************    DIV 7 (Predios)      *************************** --%> 
<layer id="narea7">
	<div id="area7" style="visibility:visible;display:">
		<table class=punteadoTablaTop cellspacing=0>
		    <tr>
		        <td>B&uacute;squeda Registro de Predios</td>
		    </tr>
		</table>
		<table class="punteadoTablaTop">
		    <tr>
		        <td width="110">Departamento</td>
		        <td width="135">
		        	<select name="area3PredioDepartamento" onchange=llenaComboHijo(); style="width: 130">
            			<% java.util.ArrayList arrq = (java.util.ArrayList) request.getAttribute("arrDepartamentos"); 
						for (int w =0; w < arrq.size(); w++) 
						{
						Value05Bean bean = (Value05Bean) arrq.get(w); %>
			            <option value=<%=bean.getValue01()%> <%if(bean.getValue01().equals(depLima)){%> selected <%}%>><%=bean.getValue02()%></option>
			            <%}%>
			        </select>
        	<td width="140">Provincia</td>
        	<td width="135"><select name="area3PredioProvincia" onchange=llenaComboHijo2(); style="width: 130"></select></td>
        	<td width="90">&nbsp;</td>
    </tr>
    <tr>
        <td width="110">Distrito</td>
        <td width="135"><SELECT name="area3PredioDistrito" style="width: 130"></SELECT></td>
        <td width="140">&nbsp;</td>
        <td width="135">&nbsp;</td>
        <td width="90">&nbsp;</td>
    </tr>
    <tr>
        <td width="110">Tipo Zona</td>
        <td width="135"><SELECT name="area3PredioTipoZona">
            <logic:iterate name="arreglo8" id="item8" scope="request">
            <option value="<bean:write name="item8" property="codigo"/>"><bean:write name="item8" property="descripcion"/></option>
            </logic:iterate>
        </SELECT></td>
        <td width="140">Nombre Zona</td>
        <td width="135"><input type="text" name="area3PredioNombreZona" size="20" style="width: 130" maxlength="100" onblur="sololet(this)"></td>
        <td width="90">&nbsp;</td>
    </tr>
    <tr>
        <td width="110">Tipo V&iacute;a</td>
        <td width="135"><SELECT name="area3PredioTipoVia">
            <logic:iterate name="arreglo9" id="item9" scope="request">
            <option value="<bean:write name="item9" property="codigo"/>"><bean:write name="item9" property="descripcion"/></option>
            </logic:iterate>
        </SELECT></td>
        <td width="140">Nombre V&iacute;a</td>
        <td width="135"><input type="text" name="area3PredioNombreVia" size="20" style="width: 130" maxlength="100" onblur="sololet(this)"></td>
        <td width="90">&nbsp;</td>
    </tr>
    <tr>
        <td width="110">Tipo Numeraci&oacute;n</td>
        <td width="135"><SELECT name="area3TipoNumerac">
            <logic:iterate name="arreglo10" id="item10" scope="request">
            <option value="<bean:write name="item10" property="codigo"/>"><bean:write name="item10" property="descripcion"/></option>
            </logic:iterate>
        </SELECT></td>
        <td width="140">N&uacute;mero</td>
        <td width="135"><input type="text" name="area3PredioNumero" size="5" maxlength="100" onblur="sololet(this)"></td>
        <td width="90">&nbsp;</td>
    </tr>
    <tr>
        <td width="110">Tipo Interior</td>
        <td width="135"><SELECT name="area3PredioTipoInterior">
            <logic:iterate name="arreglo11" id="item11" scope="request">
            <option value="<bean:write name="item11" property="codigo"/>"><bean:write name="item11" property="descripcion"/></option>
            </logic:iterate>
        </SELECT></td>
        <td width="140">Interior Nro</td>
        <td width="135"><input type="text" name="area3PredioInteriorNro" size="5" maxlength="100" onblur="sololet(this)"></td>
        <%--<td width="178"> <input type="checkbox" name="area3PredioSinNum" value="ON">S/Numero </td>--%>
        <td width="90">
        	&nbsp;
        </td>
    </tr>
</table>
<table cellspacing=0 border="0">
  <tr>
	<td colspan="3" align="center">
  		<div id="ocultar8">
       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,3)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Predios');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
       	</div>
 	</td>
  </tr>
</table>
	
</div>
</layer>
<% } %>
<%-- *******************    DIV 8 (Mineria)       *************************** --%> 
<layer id="narea8">
	<div id="area8" style="visibility:visible;display:">
		<table class=cabeceraformulario>
		    <tr>
		        <td width="673" colspan="9">
		        	<strong><font color="#949400" size="2">B&uacute;squeda Registro de Miner&iacute;a</font></strong>
		        </td>
		    </tr>
		</table>
		<table class="punteadoTablaTop" width="600">
		    <tr>
		        <td width="130">Derecho Minero</td>
		        <td><input type="text" name="area3MineriaDerechoMinero" maxlength="100" onblur="sololet(this)"></td>
		    </tr>    
		    <tr>
		        <td width="130">Sociedad</td>
		        <td><input type="text" name="area3MineriaSociedad" maxlength="100" onblur="sololet(this)"></td>
		    </tr>
		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar9">
		       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,41)" value="Buscar"/>
		       	</div>
		 	</td>
		  </tr>
		</table>
	</div>
</layer>

<%-- *******************    DIV 9                *************************** --%> 
<layer id="narea9">
	<div id="area9" style="visibility:visible;display:">
		<table width="600px">
		    <tr>
		        <td colspan=2>
		        	<strong><font color="#949400" size="2">B&uacute;squeda Registro Embarcaciones Pesqueras</font></strong>
		        </td>
		    </tr>
		</table>
		<table class="punteadoTablaTop" width="600px">
			<tr>
		        <td width="130">N&uacute;mero Matr&iacute;cula</td>
		        <td><input type="text" name="area3EmbarcacionNumeroMatricula" maxlength="15" onblur="sololet(this)"></td>
		    </tr>
		    <tr>
		        <td width="130">Nombre Embarcaci&oacute;n</td>
		        <td><input type="text" name="area3EmbarcacionNombre" maxlength="100" onblur="sololet(this)"></td>
		    </tr>
  		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar10">
		       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,51)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Embarcaciones Pesqueras');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		       	</div>
		 	</td>
		  </tr>
		</table>
		<br>
	</div>
</layer>

<%-- *******************    DIV 10                *************************** --%> 
<layer id="narea10">
<div id="area10" style="visibility:visible;display:">
<table cellspacing=0>
    <tr>
        <td>
        	<strong><font color="#949400" size="2">B&uacute;squeda Registro Buques</font></strong>
        </td>
    </tr>
</table>
<table class="punteadoTablaTop" width="600" >
    <tr>
        <td width="130">N&uacute;mero Matr&iacute;cula</td>
        <td><input type="text" name="area3BuqueNumeroMatricula" style="width: 133" maxlength="15" onblur="sololet(this)"></td>
    </tr>
    <tr>
        <td width="130">Nombre Embarcaci&oacute;n</td>
        <td><input type="text" name="area3BuqueNombre" style="width: 133" maxlength="100" onblur="sololet(this)"></td>
    </tr>
</table>
<table cellspacing=0 border="0">
  <tr>
	<td colspan="3" align="center">
  		<div id="ocultar11">
       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,61)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Buques');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
       	</div>
 	</td>
  </tr>
</table>

<br>

</div>
</layer>

<%-- *******************    DIV 11               *************************** --%> 
<layer id="narea11">
	<div id="area11" style="visibility:visible;display:">
		<table class="punteadoTabla" width="600">
		    <tr>
		        <td colspan="2">
		        	<strong><font color="#949400" size="2">B&uacute;squeda Registro Aeronaves</font></strong>
		        </td>
		    </tr>
		    <tr>
		        <td width="130">N&uacute;mero Matr&iacute;cula</td>
		        <td ><input type="text" name="area3AeronaveNumeroMatricula" maxlength="15" style="width: 133" onblur="sololet(this)"></td>
		    </tr>
		</table>
		<br>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar12">
		       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,7)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Aereonaves por Numero de Matricula');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		       	</div>
		 	</td>
		  </tr>
		</table>
		<br>
		<table class="punteadoTablaTop" width="600">
		    <tr>
		        <td width="130">Apellido Paterno</td>
		        <td><input type="text" name="area3AeronaveApePat" maxlength="30" style="width: 133" onblur="sololet(this)"></td>
		    </tr>
		    <tr>    
		        <td width="130">Apellido Materno</td>
		        <td><input type="text" name="area3AeronaveApeMat" maxlength="30" style="width: 133" onblur="sololet(this)"></td>
		    </tr>
		    <tr>
		        <td width="130">Nombres</td>
		        <td><input type="text" name="area3AeronaveNombre" maxlength="40" style="width: 133" onblur="sololet(this)"></td>
		    </tr>
		</table>
		<br>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar13">
		       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,8)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Aereonaves por Persona Natural');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		       	</div>
		 	</td>
		  </tr>
		</table>
		<br>
		<table class="punteadoTablaTop" width="600px">
		    <tr>
		        <td width="130">Raz&oacute;n Social</td>
		        <td><input type="text" name="area3AeronaveRazon" maxlength="150" style="width: 133" onblur="sololet(this)"></td>
		    </tr>
		    <tr>
		        <td width="130">Siglas</td>
		        <td><input type="text" name="area3SiglasB" maxlength="50" style="width: 133" onblur="sololet(this)"></td>
		    </tr>
		</table>
		<br>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar14">
		       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(3,9)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Aereonaves por Organizacion');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/> 
		       	</div>
		 	</td>
		  </tr>
		</table>
	</div>
</layer>

</div>
</layer> 

<layer id="narea4">
<div id="area4" style="position: absolute; left: < %=positionLeft% > px; top: < %=positionTop% > px;  visibility: ">
<table cellspacing=0>
    <tr>
        <td>
        	<strong><font color="#949400" size="2">B&uacute;squeda Registro de Propiedad Mueble por Propietario</font></strong>
        </td>
    </tr>
</table>
<table class="punteadoTablaTop" cellspacing=0 width="600px">


    <%//if (flagInterno==true) {%>
    <%--3dic
    <tr> 
            <td width="110" style="vertical-align:middle"> Tipo Participaci&oacute;n </td>
            <td colspan=4 style="vertical-align:middle"> <select size="1" name="area3TipoParticipacion" style="vertical-align:middle"></select></td>
    </tr>
--%>
    <%//}%>

    <tr>
        <td colspan=5>
        	<strong><font color="#949400" size="2">Buscar por Propietario Persona Natural</font></strong>
        </td>
    </tr>
    <tr>
        <td width="130">Apellido Paterno</td>
        <td><input type="text" name="area4PropietarioApePat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
    </tr>
    <tr>
        <td width="130">Apellido Materno</td>
        <td ><input type="text" name="area4PropietarioApeMat" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
    </tr>
    <tr>
        <td width="130">Nombres</td>
        <td ><input type="text" name="area4PropietarioNombre" style="width: 130" maxlength="40" onblur="sololet(this)"></td>
    </tr>

    <%if (flagInterno) { %>
    <tr>
        <td width="130"><input type="checkbox" name="checkInactivosPN4" checked> Incluir Inactivos</td>
        <td >&nbsp;</td>
    </tr>
    <%}%>
    </table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar15">
        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(4,1)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Propiedad Inmueble por Nombre de Propietario Persona Natural');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
        	</div>
	 	</td>
	  </tr>
	</table>
	<table class="punteadoTabla" width="600">
    <tr>
        <td colspan=2>
        	<strong><font color="#949400" size="2">Buscar por Propietario Persona Juridica</font></strong>
        </td>
    </tr>
	<tr>
        <td width="130">Raz&oacute;n Social</td>
        <td><input type="text" name="area4PropietarioRazon" style="width: 130" maxlength="100" onblur="solonumlet(this)"></td>
    </tr>
    <%if (flagInterno) { %>
    <tr>
        <td width="130"><input type="checkbox" name="checkInactivosPJ4" checked> Incluir Inactivos</td>
        <td>&nbsp;</td>
    </tr>
    <%}%>
	</table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar16">
	       		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(4,2)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Propiedad Inmueble por Nombre de Propietario Persona Juridica');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
	       	</div>
	 	</td>
	  </tr>
	</table>
	<table class="punteadoTabla" width="600">
	    <tr>
	        <td colspan=2>
	        	<strong><font color="#949400" size="2">Buscar por N&uacute;mero de Motor</font></strong>
	        </td>
	    </tr>
	   	<tr>
	        <td width="130">N&uacute;mero de Motor</td>
	        <td><input type="text" name="area4NumMotor" style="width: 130" maxlength="30" onblur="solonumlet(this)"></td>
	    </tr>
	    <%if (flagInterno) { %>
	    <tr>
	        <td width="130"><input type="checkbox" name="checkInactivosNM4" checked> Incluir Inactivos</td>
	        <td>&nbsp;</td>
	    </tr>
	    <%}%>
	</table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar17">
        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(4,3)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Propiedad Inmueble por Número de Motor');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
        	</div>
	 	</td>
	  </tr>
	</table>
	<table class="punteadoTabla" width="600">
    <tr>
        <td colspan=2>
        	<strong><font color="#949400" size="2">Buscar por Chasis</font></strong>
        </td>
    </tr>
	<tr>
        <td width="130">N&uacute;mero de Serie</td>
        <td><input type="text" name="area4Chasis" style="width: 130" maxlength="35" onblur="solonumlet(this)"></td>
    </tr>
    <%if (flagInterno) { %>
    <tr>
        <td width="130"><input type="checkbox" name="checkInactivosCH4" checked> Incluir Inactivos</td>
        <td>&nbsp;</td>
    </tr>
    <%}%>
	</table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
	  		<div id="ocultar18">
        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscar(4,4)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Propiedad Inmueble por Número de Chasis');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/> 
        	</div>
	 	</td>
	  </tr>
	</table>
	<br>
</div>
</layer>

<%-- inicio: jrosas 11-07-07 --%>
<%-- *******************    DIV 12               *************************** --%> 
<layer id="narea12" >
	<div id="area12" style="visibility:visible">
		<table >
		    <tr>
		        <td>
		        	<strong><font color="#949400" size="2">B&uacute;squeda Registro Mobiliario de Contratos por Participante</font></strong>
		        </td>
		    </tr>
		</table>
		<table>
	   	    <tr>
		        <td colspan=5>
		        	<strong><font color="#949400" size="2">Buscar por Participante Persona Natural</font></strong>
		        </td>
		    </tr>
		</table>
		<table class="punteadoTablaTop" width="600">
		    <tr>
		        <td width="110">Apellido Paterno</td>
		        <td ><input type="text" name="txtApellidoPaterno" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
		    </tr>
		    <tr>
		        <td width="130">Apellido Materno</td>
		        <td><input type="text" name="txtApellidoMaterno" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
		    </tr>
		    <tr>
		        <td width="130">Nombres</td>
		        <td ><input type="text" name="txtNombres" style="width: 130" maxlength="40" onblur="sololet(this)"></td>
		    </tr>
		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar19">
	        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarRMC(3,1)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Mobiliario de Contratos por Nombre de Participante Persona Natural');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
	        	</div>
		 	</td>
		  </tr>
		</table>
		<table class="punteadoTablaTop" width="600">
		    <tr>
		        <td colspan=5>
		        	<strong><font color="#949400" size="2">Buscar por Participante Persona Juridica</font></strong>
		        </td>
		    </tr>
		    <tr>
		        <td width="130">Raz&oacute;n Social</td>
		        <td><input type="text" name="txtRazonSocial" style="width: 130" maxlength="150" onblur="solonumlet(this)"></td>
		    </tr>
		    <tr>
		        <td width="130">Siglas</td>
		        <td><input type="text" name="txtSiglas" style="width: 130" maxlength="60" onblur="sololet(this)"></td>
		    </tr>
		 </table>
		 <table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
				<div id="ocultar20">
	        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarRMC(3,2)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Mobiliario de Contratos por Nombre de Participante Persona Juridica');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
	        	</div>
		 	</td>
		  </tr>
		</table>
		<table class="punteadoTabla" width="600">
		    <tr>
		        <td colspan=2>
		        	<strong><font color="#949400" size="2">Buscar por Tipo y Número de Documento</font></strong>
		        </td>
		    </tr>
		    <tr>
		        <td width="130">Tipo</td>
				<td>
				    <select name="cboTipoDocumento">
						<option value="">&nbsp;--&nbsp;</option>
					    <logic:iterate name="arrDocu" id="item1" scope="request">
						  <option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
					    </logic:iterate>
					</select>
				</td>
			</tr>
			<tr>
		        <td width="130">Número de Documento</td>
				<td ><input type="text" name="txtNumeroDocumento" style="width: 130" maxlength="15"></td>
			</tr>
			<tr>
		        <td width="90" colspan="2">
		        	<div id="ocultar21">
		        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarRMC(3,3)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Mobiliario de Contratos por Tipo y Número de Documento');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		        	</div>
		       	</td>
		    </tr>
		</table>
	    <%if (flagInterno==true) { %>
		    <table class="punteadoTablaTop">
			    <tr>
			        <td colspan=2><b>B&uacute;squeda por Bienes(*)</b></td>
			    </tr>
			    <tr>
			        <td width="130">Placa(Vehiculos)</td>
			        <td ><input type="text" name="txtNumeroPlaca" style="width: 130" maxlength="7" onblur="sololet(this)"></td>
			    </tr>
			    <tr>
			        <td width="130">Otros datos</td>
			        <td ><input type="text" name="txtOtrosDatos" style="width: 130" maxlength="100" onblur="sololet(this)"></td>
			    </tr>
		   	</table>
			<table cellspacing=0 border="0">
			  <tr>
				<td colspan="3" align="center">
			  		<div id="ocultar22">
		        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarRMC(3,4)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda de Registro de Mobiliario de Contratos por Bienes');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/> 
		        	</div>
			 	</td>
			  </tr>
			</table>
		<% } %>  
		<br>
		<table>
			<tr>
		       <td colspan="5">
		          <font color="#949400">(*)Ingresar datos sin guiones y sin espacios en blanco</font>
		       </td>
		    </tr>
		</table>
		<br>
		<table class=formulario cellspacing=0 style="border-color: #949400; border-style:ridge; " width="600px">
		    <tr>
		        <td align="center" valign="center">
		          (*)Las partidas correspondientes a los libros: Prenda Agrícola, Prenda Industrial,Prenda Global y Flotante y
		          Prenda minera se visualizan a través del área registral: Registro Mobiliario de Contratos. Las partidas del
		          Registro Fiscal de Ventas a Plazo a través del Registro Mobiliario de Contratos o el Registro de Propiedad
		          Vehícular, según corresponda
		        </td>
		    </tr>
		</table>
  	</div>
</layer>

<!--  <layer id="narea13" visibility="hide">
<div id="area13" style="visibility: hidden; background-color: FFFFDD; width: 430px; border-width: 1px; border-color: 000000; border-style: solid">
	<table class=formulario cellspacing=0>
	    <tr>
	       <td align="center" vAlign="center">
	              B&uacute;squeda Nacional
	       </td>
	    </tr>
	</table>
</div>
</layer>
-->	

<%-- FIN: jrosas 11-07-07 --%>

<!-- Inicio:jascencio:16/07/07 
	CC:REGMOBCON-2006 -->	
<layer id="narea15" visibility="hide">
	<div id="area15" style="visibility:hidden;">
		<table>
		    <tr>
		        <td>
		        	<strong><font color="#949400" size="2">Búsqueda del Sistema Integrado Garantias y Contratos por participante</font></strong>
		        </td>
		    </tr>
		</table>
		<table class="punteadoTabla" width="600px">
		    <tr>
		        <td colspan=2>
		        	<strong><font color="#949400" size="2">Búsqueda por Participante Persona Natural</font></strong>
		        </td>
		    </tr>
		    <tr>
		        <td width="200">Apellido Paterno</td>
		        <td><input type="text" name="txtParticipanteApePatArea15" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
		    </tr>
		    <tr>
		        <td width="200">Apellido Materno</td>
		        <td><input type="text" name="txtParticipanteApeMatArea15" style="width: 130" maxlength="30" onblur="sololet(this)"></td>
		    </tr>
		    <tr>
		        <td width="200">Nombres</td>
		        <td><input type="text" name="txtParticipanteNombreArea15" style="width: 130" maxlength="40" onblur="sololet(this)"></td>
		    </tr>
		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar23">
	        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarSIGC(4,1)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda del Sistema Integrado Garantías y Contratos por Participante Persona Natural');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	        	</div>
		 	</td>
		  </tr>
		</table>
		<table class="punteadoTabla" width="600px">
		    <tr>
		        <td colspan=2>
		        	<strong><font color="#949400" size="2">Búsqueda por Participante Persona Juridica</font></strong>
		        </td>
		    </tr>
		    <tr>
		        <td width="200">Razón Social</td>
		        <td ><input type="text" name="txtParticipanteRazonArea15" style="width: 130" maxlength="150" onblur="solonumlet(this)"></td>
		    </tr>
		    <tr>
		        <td width="200">Siglas</td>
		        <td><input type="text" name="txtSiglasArea15" style="width: 130" maxlength="60" onblur="sololet(this)"></td>
		    </tr>
		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar24">
	        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarSIGC(4,2)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda del Sistema Integrado Garantías y Contratos por Participante Persona Juridica');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
	        	</div>
		 	</td>
		  </tr>
		</table>
		<table>
			 <tr>
			 	<td>
			 		<strong><font color="#949400" size="2">Búsqueda por Tipo y Número de documento</font></strong>
			 	</td>
			 </tr>
		</table>
		<table class="punteadoTablaTop" width="600px">	 
			<tr>
				<td width="200">Tipo</td>
		        <td > 
		        	<select name="cboTipoDocumentoArea15">
						<option value=""> -- </option>
						<logic:iterate name="arrDocu" id="item1" scope="request">
						<option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
						</logic:iterate>
					</select>
		        </td>
		    </tr>
		    <tr>
		        <td width="200">Número de Documento</td>
		        <td><input type="text" name="txtNumeroDocumentoArea15" maxlength="15"></td>
			 </tr>
		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar25">
	        		<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarSIGC(4,3)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda del Sistema Integrado Garantías y Contratos por Tipo y Número de Documento');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"> 
	        	</div>
		 	</td>
		  </tr>
		</table>
		<table>
			 <tr>
			 	<td>
			 		<strong><font color="#949400" size="2">Búsqueda por Bienes</font></strong>
			 	</td>
			 </tr>
		</table>
		<table class="punteadoTablaTop" cellspacing=0 width="600px">
			 <tr>
			 	<td width="200">Placa</td>
			 	<td ><input type="text" name="txtNumeroPlacaArea15" maxlength="15" onblur="mayuscula(this)"></td>
			 </tr>
			 <tr>
			 	<td width="200">Nro. Matricula(embarcaciones pesqueras, buques y aeronaves)</td>
			 	<td><input type="text" name="txtNumeroMatriculaArea15" maxlength="35" onblur="mayuscula(this)"></td>
			 </tr>
			 <tr>
			 	<td width="200">Nombre(embarcaciones pesqueras y buques)</td>
			 	<td><input type="text" name="txtNombreBienArea15" maxlength="15" onblur="mayuscula(this)"></td>
			 </tr>
			 <tr>	
			 	<td width="200">Nro. Serie(motores aeronaves y otros)</td>
			 	<td><input type="text" name="txtNumeroSerieArea15" maxlength="35" onblur="mayuscula(this)"></td>
			 </tr>
		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<div id="ocultar26">
					<input type="button" class="formbutton" onclick="javascript:onclick=doBuscarSIGC(4,4)" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda del Sistema Integrado Garantías y Contratos por Bienes');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				</div>
		 	</td>
		  </tr>
		</table>
	</div>
</layer>	
<%if(flagInterno == true){
%>
<input type="hidden" name="hidFlagInterno" value="<%=xparam%>"/>
<%
} %>
<!-- Fin:jascencio -->

</form>
</div>
</div>
<script LANGUAGE="JavaScript">
<%if (flagInterno==true) { %>
	llenaComboHijo();
	doCambiaCombo(document.frm1.area3PredioProvincia,<%=proLima%>);	
	llenaComboHijo2();		
	llenaComboHijo3();
	global_pidedis=true;
<% } %>

<%
InputBusqIndirectaBean beanb = (InputBusqIndirectaBean) request.getAttribute("beanb");
if (beanb!=null)
	{%>
	
	global_pidedis=false;

	doCambiaCombo(document.frm1.area3PredioDepartamento,<%=beanb.getArea3PredioDepartamento()%>);	
	llenaComboHijo();
	doCambiaCombo(document.frm1.area3PredioProvincia,<%=beanb.getArea3PredioProvincia()%>);	
	llenaComboHijo2();
	// doCambiaCombo(document.frm1.area3PredioDistrito,_beanb.getArea3PredioDistrito()_);	
	doCambiaCombo(document.frm1.area3PredioTipoZona,"<%=beanb.getArea3PredioTipoZona()%>");	
	doCambiaCombo(document.frm1.area3PredioTipoVia,"<%=beanb.getArea3PredioTipoVia()%>");	
	doCambiaCombo(document.frm1.area3TipoNumerac,"<%=beanb.getArea3PredioTipoNumerac()%>");	
	doCambiaCombo(document.frm1.area3PredioTipoInterior,"<%=beanb.getArea3PredioTipoInterior()%>");	
	
	document.frm1.area3PredioNombreZona.value="<%=beanb.getArea3PredioNombreZona()%>";
	document.frm1.area3PredioNombreVia.value="<%=beanb.getArea3PredioNombreVia()%>";
	document.frm1.area3PredioNumero.value="<%=beanb.getArea3PredioNumero()%>";
	document.frm1.area3PredioInteriorNro.value="<%=beanb.getArea3PredioInteriorNro()%>";

	var objx = document.frm1.listbox1;
	
<% String[] sedesElegidasOriginales = beanb.getSedesElegidasOriginales(); %>

	for (var i=0; i < objx.options.length; i++) 
	{
		var zeta = objx.options[i].value;
		<%for (int w = 0; w < sedesElegidasOriginales.length; w++) 
		   {%>
			if (zeta == "<%=sedesElegidasOriginales[w]%>")
				objx.options[i].selected = true;
		<% } %>
	}
	doAdd1();
<%	} %>
</script>
</BODY>
</HTML>
