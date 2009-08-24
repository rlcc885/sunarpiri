<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>

<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<html>
<head>
<title>Formulario de Consulta de Estado de T&iacute;tulos IRI</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>

<script language="javascript">
bloqueo=0;
Navegador();
function Cambio(valor)
{ 
	document.frm1.hidAreaLibro.value=document.frm1.comboAreaLibro.options[document.frm1.comboAreaLibro.selectedIndex].text;
	//Tarifario
	<logic:iterate name="arrAreaLibro" id="item22" scope="request">
		if (valor==<bean:write name="item22" property="codigo"/>)
			document.frm1.costo.value=<bean:write name="item22" property="atributo1"/>;
	</logic:iterate>
	//Fin Tarifario
	invisible_navegador(navegador,'area6','narea6','i');	  			
	if (valor!=6) 
	{	
		//document.frm1.CboOficinas.disabled=false;
		invisible_navegador(navegador,'area1','narea1','v');	    	
		invisible_navegador(navegador,'area2','narea2','i');
		invisible_navegador(navegador,'area4','narea4','v');
		invisible_navegador(navegador,'area5','narea5','i');
		llenaComboHijo2();   	
		// inicio: jrosas 20-07-07
		if (valor==21) // registro mobiliario contratos
		{
			invisible_navegador(navegador,'area6','narea6','v');	  
		}
		// fin: jrosas 20-07-07					
	}
	if (valor==6) // propiedad vehicular
	{	
		//alert("La opción seleccionada se encuentra en desarrollo.");
		invisible_navegador(navegador,'area1','narea1','i');
		invisible_navegador(navegador,'area2','narea2','v');	
		invisible_navegador(navegador,'area4','narea4','i');	 
		invisible_navegador(navegador,'area5','narea5','v');	 
		document.frm1.CboOficinas.value="01|01";
		//document.frm1.CboOficinas.disabled=true;    		    					
	}
}
function RefrescaCombos(codProv) {
	fbox = document.frm1.CboOficinas;
	if (codProv == '8') {
		fbox.options[6].selected=true;
	} else {
		for(var i=0; i<fbox.options.length; i++)
	  	{
	  		if (fbox.options[i].value == codProv) {
				fbox.options[i].selected=true;
			}
	  	}
	}
}

//combo libro depende de area registral
var arrLibro = new Array();
<% int q = 0; %>
<logic:iterate name="arr3" id="item3" scope="request">
	var arrx = new Array();
	arrx[0]="<bean:write name="item3" property="value01"/>"; //cod libro
	arrx[1]="<bean:write name="item3" property="value02"/>"; //descripcion libro
	arrx[2]="<bean:write name="item3" property="value03"/>"; //codigo area
	arrx[3]="<bean:write name="item3" property="value04"/>"; //codigo criterio
	arrLibro[<%=q%>]=arrx;
	<% q++; %>
</logic:iterate>

function llenaComboHijo2()
{
	var obj1;
	var obj2;
	obj1 = document.frm1.comboAreaLibro;  //papa
	obj2 = document.frm1.combo3;  //hijo
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
	//limpiar combo hijo
	if (obj2.length != 0)
	{ 
		for(var i=0; i<obj2.options.length ; ++i)
			{
				obj2.options[i]=null;
						--i;
			}
    } 
	var x0;
	var x1;
	var x2;			
	for (var j=0; j < arrLibro.length; j++)
	{
		x0 = arrLibro[j][0];
		x1 = arrLibro[j][1];
		//Tarifario
		//x2 = arrLibro[j][2];
		x2 = arrLibro[j][3];
		if (x2 == codigoPapa){
			if (x0 != '099'){
				obj2.options[obj2.options.length] = new Option(x1,x0);
			}	
		}	
	}
} // function llenaComboHijo2



//***********************************FUNTION ENVIAR**************************************
function enviar(opc,param)
{
	//************************************************************************************
	// SUNARP-RMC-BORRAR
	//inicio:dbravo:02/10/2007
	//descripcion: cambio para validar acceso a nuevos recursos
	//if(document.frm1.comboAreaLibro.value==21){
	//	<%--
	//	  UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
	//	  long perfilusuarioid =usuarioBean.getPerfilId();
	//	--%>  
	//	
	//	if(noTieneAccesoRecursoRMC(10, <%--=usuarioBean.getPerfilId()%>, '<%=//usuarioBean.getUserId()--%>')){
	//		return;	
	//	}
	//}
	//fin:dbravo:02/10/2007
	//************************************************************************************
	document.frm1.hidCriter.value+=" Oficina: "+document.frm1.CboOficinas.options[document.frm1.CboOficinas.selectedIndex].text
	//alert(document.frm1.hidCriter.value);
	//return;
	if (bloqueo>0) return;
	var tipob = "";
	// Inicio:mgarate:29/05/2007
	var cadena = "oficinaregistral="+document.frm1.CboOficinas.value+"/arearegistral="+document.frm1.comboAreaLibro.value
	// Fin:mgarate:29/05/2007
	//obtener el valor del radio
	if (opc=='1'){
		if (document.frm1.radBuscar[0].checked=="1" ){
			tipob = "F"; //buscar por numero de ficha
	
		}else{
			tipob ="P";  //buscar por numero de partida
			
		}
	}else{
		if (document.frm1.radBuscar2[0].checked=="1"){
			tipob = "F"; //buscar por numero de placa
		}else{
			tipob ="P";  //buscar por numero de partida
		}
	}	
	if (param=='1')
		{
		
		//Inicio:mgarate:29/05/2007
			if (document.frm1.radBuscar[0].checked=="1")
			{
				if(document.frm1.txt1.value!=null)
				{
				 cadena = cadena+"/ficha="+document.frm1.txt1.value
				}
				 
			}else
			{
				if(document.frm1.txt1.value!=null)
				{
				 cadena = cadena+"/partida="+document.frm1.txt1.value
				}
			}
		
		//Fin:mgarate:29/05/2007
		
	/*	if (esVacio(document.frm1.txt1.value) || !esEntero(document.frm1.txt1.value) || !esEnteroMayor(document.frm1.txt1.value,1))
			{
				alert("Por favor ingrese correctamente el Numero de Partida");
				document.frm1.txt1.focus();
				return;
			}*/
		
		if	(tipob =='F')
			{
				if (esVacio(document.frm1.txt1.value) || !esEntero(document.frm1.txt1.value) || !esEnteroMayor(document.frm1.txt1.value,1) || !esLongitudMenor(document.frm1.txt1.value,10))
					{
						alert("Por favor ingrese correctamente el Numero de Ficha.\nEl Numero de Ficha admite como maximo 10 digitos");
						document.frm1.txt1.focus();					
						return;
					}
		        
				document.frm1.txt1.value = rellenaIzq(document.frm1.txt1.value,"0",10);
				if (document.frm1.comboAreaLibro.value!=21){
					document.frm1.action="/iri/PublicidadIRI.do?state=buscarXFicha";
				}else{
					document.frm1.action="/iri/PublicidadIRI.do?state=buscarXFichaRMC";
				}	
			}
		if	(tipob =='P')
			{
				if (esVacio(document.frm1.txt1.value) || !esEntero(document.frm1.txt1.value) || !esEnteroMayor(document.frm1.txt1.value,1) || !esLongitudMenor(document.frm1.txt1.value,8))
					{
						alert("Por favor ingrese correctamente el Numero de Partida.\nEl Numero de Partida admite como maximo 8 digitos");
						document.frm1.txt1.focus();					
						return;
					}
					
				document.frm1.txt1.value = rellenaIzq(document.frm1.txt1.value,"0",8);
				if (document.frm1.comboAreaLibro.value!=21){
					document.frm1.action="/iri/PublicidadIRI.do?state=buscarXNroPartida";
				}else{
					document.frm1.action="/iri/PublicidadIRI.do?state=buscarXNroPartidaRMC";
				}		
			}
			
		}// param 1
		
		
	if	(param=='2')
		{
		
	
		
		if (esVacio(document.frm1.txt2.value) && esVacio(document.frm1.txt3.value))
			{
				alert("Por favor ingrese correctamente el Numero de Tomo y el Numero de Folio\nLos Numeros de Tomo y Folio admiten como maximo 6 digitos");
				document.frm1.txt2.focus();
				return;
			}	
		if (esVacio(document.frm1.txt2.value) || !esEntero(document.frm1.txt2.value) || !esEnteroMayor(document.frm1.txt2.value,1) || !esLongitudMenor(document.frm1.txt2.value,6))
			{
				alert("Por favor ingrese correctamente el Numero de Tomo\nEl Numero de Tomo admite como maximo 6 digitos");
				document.frm1.txt2.focus();
				return;
			}
		if (esVacio(document.frm1.txt3.value) || !esEntero(document.frm1.txt3.value) || !esEnteroMayor(document.frm1.txt3.value,1) || !esLongitudMenor(document.frm1.txt3.value,6))
			{
				alert("Por favor ingrese correctamente el Numero de Folio\nEl Numero de Folio admite como maximo 6 digitos");
				document.frm1.txt3.focus();
				return;
			}
			
		document.frm1.txt2.value = rellenaIzq(document.frm1.txt2.value,"0",6);
		document.frm1.txt3.value = rellenaIzq(document.frm1.txt3.value,"0",6);
		// Inicio:mgarate:29/05/2007
			
			if(document.frm1.combo3.value!="")
			{
			   cadena = cadena+"/registro="+document.frm1.combo3.value
			}
			if(document.frm1.txt2.value!="")
		   	{
		   		cadena = cadena+"/tomo="+document.frm1.txt2.value
		   	}
		    if(document.frm1.txt3.value)
		    {
		    	cadena = cadena+"/folio="+document.frm1.txt3.value
		    } 
		// Fin:mgarate:29/05/2007
		if (document.frm1.comboAreaLibro.value!=21){
			document.frm1.action="/iri/PublicidadIRI.do?state=buscarXTomoFolio";	
		}else{
			document.frm1.action="/iri/PublicidadIRI.do?state=buscarXTomoFolioRMC";
		}	
			
		} //param 2
	
	if (param=='3')
		{
	/*	if (esVacio(document.frm1.txt1.value) || !esEntero(document.frm1.txt1.value) || !esEnteroMayor(document.frm1.txt1.value,1))
			{
				alert("Por favor ingrese correctamente el Numero de Partida");
				document.frm1.txt1.focus();
				return;
			}*/
		if	(tipob =='F')
			{
				/**Validacion de oficinas**/
				/**Aqui se ponen las oficinas que estan habilitadas para la validacion**/
				if(document.frm1.CboOficinas.value!='01|01'
				   && document.frm1.CboOficinas.value!='10|01'
				   && document.frm1.CboOficinas.value!='12|01'
				   && document.frm1.CboOficinas.value!='07|01'
				   && document.frm1.CboOficinas.value!='11|01'
				   && document.frm1.CboOficinas.value!='06|01'
				   && document.frm1.CboOficinas.value!='12|02'
				   && document.frm1.CboOficinas.value!='07|01'
				   && document.frm1.CboOficinas.value!='04|03'
				   && document.frm1.CboOficinas.value!='07|03'
				   && document.frm1.CboOficinas.value!='02|06'
				   && document.frm1.CboOficinas.value!='07|05'
				   && document.frm1.CboOficinas.value!='02|02'
				   && document.frm1.CboOficinas.value!='11|02'
				   && document.frm1.CboOficinas.value!='07|04'
				   && document.frm1.CboOficinas.value!='05|03'
				   && document.frm1.CboOficinas.value!='10|08'
				   && document.frm1.CboOficinas.value!='02|08'
				   && document.frm1.CboOficinas.value!='02|04'
				   && document.frm1.CboOficinas.value!='10|05'
				   && document.frm1.CboOficinas.value!='11|03'
				   && document.frm1.CboOficinas.value!='06|02'
				   && document.frm1.CboOficinas.value!='10|02'
				   && document.frm1.CboOficinas.value!='07|02'
				   && document.frm1.CboOficinas.value!='12|03'
				   && document.frm1.CboOficinas.value!='09|02'
				   && document.frm1.CboOficinas.value!='11|05'
				   && document.frm1.CboOficinas.value!='11|04'
				   && document.frm1.CboOficinas.value!='04|02'
				   && document.frm1.CboOficinas.value!='11|06'
				   && document.frm1.CboOficinas.value!='02|01'
				   && document.frm1.CboOficinas.value!='04|01'
				   && document.frm1.CboOficinas.value!='06|03'
				   && document.frm1.CboOficinas.value!='09|01'
				   && document.frm1.CboOficinas.value!='05|01'
				   && document.frm1.CboOficinas.value!='13|01'
				   && document.frm1.CboOficinas.value!='08|01'
				   && document.frm1.CboOficinas.value!='03|01'
				  )
				{
					alert("La Oficina Registral seleccionada no tiene Registro Vehicular.");
					return;
				}
				/****/
				if (esVacio(document.frm1.txt4.value) || !esLongitudMenor(document.frm1.txt4.value,7))
				{
						alert("Por favor ingrese correctamente el Numero de Placa.\nEl Numero de Placa admite como maximo 6 digitos");
						document.frm1.txt4.focus();					
						return;
				}
				if ((document.frm1.txt4.value.indexOf(" ")!=-1) || document.frm1.txt4.value.indexOf("-")!=-1)
				{
						alert("Por favor ingrese el numero de placa sin guiones ni espacios en blanco");
						document.frm1.txt4.focus();					
						return;
				}
				
				bloqueo=1;
				
				document.frm1.action="/iri/PublicidadIRI.do?state=buscarXPlaca";
			}
		if	(tipob =='P')
			{
				if (esVacio(document.frm1.txt4.value) || !esEntero(document.frm1.txt4.value) || !esEnteroMayor(document.frm1.txt4.value,1) || !esLongitudMenor(document.frm1.txt4.value,8))
					{
						alert("Por favor ingrese correctamente el Numero de Partida.\nEl Numero de Partida admite como maximo 8 digitos");
						document.frm1.txt4.focus();					
						return;
					}
					
				document.frm1.txt4.value = rellenaIzq(document.frm1.txt4.value,"0",8);
				bloqueo=1;
				document.frm1.action="/iri/PublicidadIRI.do?state=buscarXPlaca";
				/*document.frm1.action="/iri/Publicidad.do?state=buscarXNroPartidaVehicular";*/
			}
			// Inicio:mgarate:29/05/2007
				if (document.frm1.radBuscar2[0].checked=="1")
				{
					//numero de placa
					cadena = cadena+"/placa="+document.frm1.txt4.value
				}else
				{
					//numero de partida
					cadena = cadena+"/partida="+document.frm1.txt4.value
				}
			// Fin:mgarate:29/05/2007
		}
		// Inicio:mgarate:29/05/2007
	    document.frm1.criterio.value = cadena	
		// Fin:mgarate:29/05/2007
		document.frm1.submit();
		alert('action-->'+document.frm1.action);
	} 	// enviar

</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body onLoad="Cambio(document.frm1.comboAreaLibro.value);">
	<div id="maincontent">
		<div class="innertube">
		<form name="frm1" method="POST">
			<input type="hidden" name="hidCriter" value="Búsqueda Directa">
			<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">B&uacute;squeda Directa de Partidas</font></b>
			<br><br>
		    <font color="#949400" size="2"><b>B&uacute;squeda Directa de Partidas</b></font>
			<table class="punteadoTablaTop" width="600px">
			<tr> 
		      <td  align="left" width="100">Oficina Registral </td>
		      <td width="30%">
		        <select name="CboOficinas">
		          <option value="10|07">Andahuaylas</option>
		          <option value="06|02">Apurimac</option>
		          <option value="03|01">Arequipa</option>
		          <option value="10|05">Ayacucho</option>
		          <option value="01|06">Barranca</option>
		          <option value="11|04">Bagua</option>
		          <option value="01|02">Callao</option>
		          <option value="11|02">Cajamarca</option>
		          <option value="03|02">Caman&aacute;</option>
		          <option value="01|05">Ca&ntilde;ete</option>
		          <option value="04|02">Casma</option>
		          <option value="03|03">Castilla</option>
		          <option value="11|05">Chachapoyas</option>
		          <option value="08|02">Chep&eacute;n</option>
		          <option value="11|01">Chiclayo</option>
		          <option value="04|03">Chimbote</option>
		          <option value="10|02">Chincha</option>
		          <option value="11|06">Chota</option>
		          <!--option value="13|01">Coronel portillo</option-->
		          <option value="06|01">Cusco</option>
		          <option value="01|04">Huacho</option>
		          <option value="08|03">Huamachuco</option>
		          <option value="10|08">Huancavelica</option>
		          <option value="02|01">Huancayo</option>
		          <option value="10|06">Huanta</option>
		          <option value="02|02">Hu&aacute;nuco</option>
		          <option value="01|03">Huaral</option>
		          <option value="04|01">Huaraz</option>
		          <option value="10|01">Ica</option>
		          <option value="07|02">Ilo</option>
		          <option value="09|01">Iquitos</option>
		          <option value="03|04">Islay</option>
		          <option value="11|03">Ja&eacute;n</option>
		          <option value="12|03">Juanju&iacute;</option>
		          <option value="07|03">Juliaca</option>
		          <option value="02|06">La Merced</option>
		          <option value="01|01">Lima</option>
		          <option value="06|03">Madre de dios</option>
		          <option value="07|04">Moquegua</option>
		          <option value="12|01">Moyobamba</option>
		          <option value="10|04">Nazca</option>
		          <option value="08|04">Otuzco</option>
		          <option value="02|04">Pasco</option>
		          <option value="10|03">Pisco</option>
		          <option value="05|01">Piura</option>
		          <option value="13|01">Pucallpa</option>
		          <option value="07|05">Puno</option>
		          <option value="06|04">Quillabamba</option>
		          <option value="08|05">San pedro de lloc</option>
		          <option value="02|05">Satipo</option>
		          <!--option value="02|06">Selva Central</option-->
		          <option value="06|05">Sicuani</option>
		          <option value="05|02">Sullana</option>
		          <option value="07|01">Tacna</option>
		          <option value="12|02">Tarapoto</option>
		          <option value="02|07">Tarma</option>
		          <option value="02|08">Tingo Mar&iacute;a</option>
		          <option value="08|01">Trujillo</option>
		          <option value="05|03">Tumbes</option>
		          <option value="09|02">Yurimaguas</option>
		        </select>
		      </td>
		      <td align="left">
				  <layer id="narea4">
			        <div id="area4" style="visibility: visible;align:left">	
			          <a class="linkInicio" href="javascript:Abrir_Ventana('/iri/acceso/mapas/MAPA1.htm','Oficinas_Registrales','',500,600)" onmouseover="javascript:mensaje_status('Identifique su Oficina Resgistral');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				        Identifique su Oficina Registral
				      </a>
				    </div>
			      </layer>
			  </td>
		    </tr>
		    </table>
		    <table class="punteadoTabla" width="600px">
			<tr> 
		      <td align="center" vAlign="center" colspan="3">
		        <layer id="narea5" visibility="hide">
		          <div id="area5" style="visibility: hidden; background-color: #666666 width: 430px; border-width: 1px; border-color: 000000; border-style: solid;">	
		            Vehículos registrados a nivel nacional.
		          </div>
		        </layer>
		      </td>
		    </tr>
		    </table>
		    <table class="punteadoTabla" width="600px">
		    <tr> 
		    	<td align=left width="100">Area Registral
		    		<input type="hidden" name="hidAreaLibro" value="">
				</td>
				<td>
					<select name="comboAreaLibro" onchange=Cambio(this.value);>
					<logic:iterate name="arrAreaLibro" id="item22" scope="request">
							<logic:equal name="item22" property="codigo" value="1">
								<option value="<bean:write name="item22" property="codigo"/>" selected> <bean:write name="item22" property="descripcion"/> </option>
							</logic:equal>
							<logic:notEqual name="item22" property="codigo" value="1">
								<option value="<bean:write name="item22" property="codigo"/>"> <bean:write name="item22" property="descripcion"/> </option>
							</logic:notEqual>
					</logic:iterate>
					</select>
				</td>
				<td align=center><font color="#949400"><b>Costo Por Búsqueda</b></font>&nbsp;<input type="text" name="costo" size="2" value="0" disabled="true"></td>
			</tr>
			</table>
			<br>
<%
String positionLeft="0";
String positionTop="150";
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
	<div id="area1" style="left:<%=positionLeft%>px; top:<%=positionTop%>px; visibility: visible;">	
		<table cellspacing=0>
	      <tr> 
	    	<td width="685" colspan="8" ><p align="left"><strong><font color="#949400" size="2">B&uacute;squeda Directa por N&uacute;mero de Partida o de Ficha</font></strong></p></td>
	      </tr>
		</table>
		<table class="punteadoTablaTop" width="600px">
		  <tr> 
			<td width="100">Buscar por	</td>
	        <td align="left" width="30%"> <input type="radio" value="F" checked name="radBuscar">Ficha&nbsp;&nbsp;<input type="radio" value="P" name="radBuscar">Partida</td>
			<td align="left">
				&nbsp;
			</td>
		  </tr>
		</table>
		<table class="punteadoTabla" width="600px">
		  <tr> 
			<td width="100">N&uacute;mero</td>
			<td align= "left" width="30%"><input type="text" name="txt1" size="12" maxlength="10"></td>
			<td>&nbsp;</td>		
		  </tr>
		</table>
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
				<a href="javascript:onclick=enviar('1','1')" onmouseover="javascript:mensaje_status('Busqueda Directa por Numero de Partida o de Ficha');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				<!-- img src="/iri/images/btn_buscar2.gif" border=0-->
				<input type="button" class="formbutton" onclick="javascript:onclick=enviar('1','1')" value="Buscar" onmouseover="javascript:mensaje_status('Busqueda Directa por Numero de Partida o de Ficha');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
				</a>
			</td>
		  </tr>
		</table>	
		<br>
		<table cellspacing=0>
		  <tr > 
			<td><strong><font color="#949400" size="2">B&uacute;squeda Directa por Tomo/Folio</font></strong></td>
		  </tr>
		</table>
		<table class="punteadoTablaTop" width="600px">
		  <tr > 
			<td width="100" >Registro de</td>
			<td width="30%">
	    		<select name="combo3" style="width=400px">
	    		</select>
			</td>
			<td align=right >
				&nbsp;
			</td>
		  </tr>
		  <tr> 
			<td width="100" >Tomo</td>
			<td width="30%"><input type="text" name="txt2" size="20" maxlength="6"></td>
			<td >&nbsp;</td>
		  </tr>
		  <tr> 
			<td width="100" >Folio</td>
			<td width="30%"><input type="text" name="txt3" size="20" maxlength="6"></td>
			<td >&nbsp;</td>
		  </tr>
		</table> 
		<table cellspacing=0 border="0">
		  <tr>
			<td colspan="3" align="center">
		  		<a href="javascript:onclick=enviar('1','2')" onmouseover="javascript:mensaje_status('Busqueda Directa por Tomo/Folio');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">

				<input type="button" class="formbutton" value="Buscar" onclick="javascript:onclick=enviar('1','2')" onmouseover="javascript:mensaje_status('Busqueda Directa por Tomo/Folio');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
				</a>
		 	</td>
		  </tr>
		</table>
	<br>
	<!-- inicio: jrosas 20-07-07 -->
	<layer id="narea6" visibility="hide">
		<div id="area6" style="visibility:hidden;">	
		<table cellspacing=0 style="border-color: #949400; border-style:ridge;" width="600px">
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
	<!-- fin: jrosas 20-07-07 -->
</div>
</layer>
<%-- Inicio:mgarate:29/05/2007 --%>
<input type="hidden" name="criterio" value=""/>
<%-- Fin:mgarate:29/05/2007 --%>
<%-- *********************************************************************** --%>
<%-- *********************************************************************** --%>
<%-- *********************************************************************** --%>
<%-- *********************************************************************** --%>
<%-- *******************    DIV 2                *************************** --%>
<%-- *********************************************************************** --%>
<%-- *********************************************************************** --%>
<%-- *********************************************************************** --%>
<br>
<br>
<layer id="narea2">
<div id="area2" style="left:<%=positionLeft%>px; top:<%=positionTop%>px; visibility: visible;">	
	<table cellspacing=0>
      <tr> 
    	<td width="685" colspan="8">
    		<p align="left"><font color="#949400" size="2"><strong>B&uacute;squeda Directa por N&uacute;mero de Partida o de Ficha</strong></font></p>
    	</td>
      </tr>
	</table>
	<table cellspacing=0  class="punteadoTablaTop" width="600px">
	  <tr> 
		<td width="100">Buscar por:	</td>
        <td align="left">
          <input type="radio" value="F" checked name="radBuscar2" onClick="javascript:invisible_navegador(navegador,'area3','narea3','v');">Placa&nbsp;&nbsp;
          <input type="radio" value="P" name="radBuscar2" onClick="javascript:invisible_navegador(navegador,'area3','narea3','i');">
          Partida
        </td>
		<td align="right">
		  &nbsp;
		</td>        
	  </tr>
	  <tr> 
		<td width="100">N&uacute;mero</td>
		<td align= "left"><input type="text" name="txt4" size="12" maxlength="10" onblur="sololet(this)"></td>
		<td>&nbsp;</td>		
	  </tr>
	</table>
	<table cellspacing=0 border="0">
	  <tr>
		<td colspan="3" align="center">
			<input type="button" class="formbutton" value="Buscar" onclick="javascript:onclick=enviar('2','3')" onmouseover="javascript:mensaje_status('Busqueda Directa por Numero de Partida o de Placa');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		</td>
	  </tr>
	</table>
	<br>
	<table cellspacing=0  width="600px">
	  <tr> 
	    <td width="100"></td>
		<td colspan="2">
		  <layer id="narea3">
            <div id="area3" style="visibility: visible;">	
              El n&uacute;mero de placa debe ser ingresado sin guiones ni espacios en blanco.
            </div>
          </layer>
        </td>
				
	  </tr>
	  <tr> 
		<td width="100"></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>		
	  </tr>
	</table>	
	<br>
</div>
</layer>

</form>
</div>
</div>

<script LANGUAGE="JavaScript">
	llenaComboHijo2();	
</script>
</body>
</html>