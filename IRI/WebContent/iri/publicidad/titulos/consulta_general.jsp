<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>


<%
String odev = (String) request.getAttribute("odev");
if (odev==null)	
	odev="**__**_";
String adev = (String) request.getAttribute("adev");
if (adev==null)
	adev="**_**_";
%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<SCRIPT LANGUAGE="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="Javascript">
<!--
function noenter() {
  return !(window.event && window.event.keyCode == 13); 
}

function CambioOficinaRegistralC(opcionSeleccionada) {
	var i = parseInt(opcionSeleccionada) + 1;
	CambioOficinaRegistral(i);
}

function CambioOficinaRegistral(opcionSeleccionada) {
	var registroPublico;
	var oficinaRegistral;
	var o = parseInt(opcionSeleccionada);
	switch (o) {
		case  1 : registroPublico = "10"; oficinaRegistral = "07";break;
		case  2 : registroPublico = "06"; oficinaRegistral = "02";break;
		case  3 : registroPublico = "03"; oficinaRegistral = "01";break;
		case  4 : registroPublico = "10"; oficinaRegistral = "05";break;
		case  5 : registroPublico = "01"; oficinaRegistral = "06";break;
		case  6 : registroPublico = "11"; oficinaRegistral = "04";break;
		case  7 : registroPublico = "01"; oficinaRegistral = "02";break;
		case  8 : registroPublico = "11"; oficinaRegistral = "02";break;
		case  9 : registroPublico = "03"; oficinaRegistral = "02";break;
		case 10 : registroPublico = "01"; oficinaRegistral = "05";break;
		case 11 : registroPublico = "04"; oficinaRegistral = "02";break;
		case 12 : registroPublico = "03"; oficinaRegistral = "03";break;
		case 13 : registroPublico = "11"; oficinaRegistral = "05";break;
		case 14 : registroPublico = "08"; oficinaRegistral = "02";break;
		case 15 : registroPublico = "11"; oficinaRegistral = "01";break;
		case 16 : registroPublico = "04"; oficinaRegistral = "03";break;
		case 17 : registroPublico = "10"; oficinaRegistral = "02";break;
		case 18 : registroPublico = "11"; oficinaRegistral = "06";break;
		case 19 : registroPublico = "06"; oficinaRegistral = "01";break;
		case 20 : registroPublico = "01"; oficinaRegistral = "04";break;
		case 21 : registroPublico = "08"; oficinaRegistral = "03";break;
		case 22 : registroPublico = "08"; oficinaRegistral = "03";break;
		case 23 : registroPublico = "02"; oficinaRegistral = "01";break;
		case 24 : registroPublico = "10"; oficinaRegistral = "06";break;
		case 25 : registroPublico = "02"; oficinaRegistral = "02";break;
		case 26 : registroPublico = "01"; oficinaRegistral = "03";break;
		case 27 : registroPublico = "04"; oficinaRegistral = "01";break;
		case 28 : registroPublico = "10"; oficinaRegistral = "01";break;
		case 29 : registroPublico = "07"; oficinaRegistral = "02";break;
		case 30 : registroPublico = "09"; oficinaRegistral = "01";break;
		case 31 : registroPublico = "03"; oficinaRegistral = "04";break;
		case 32 : registroPublico = "11"; oficinaRegistral = "03";break;
		case 33 : registroPublico = "12"; oficinaRegistral = "03";break;
		case 34 : registroPublico = "07"; oficinaRegistral = "03";break;
		case 35 : registroPublico = "02"; oficinaRegistral = "06";break;
		case 36 : registroPublico = "01"; oficinaRegistral = "01";break;
		case 37 : registroPublico = "06"; oficinaRegistral = "03";break;
		case 38 : registroPublico = "07"; oficinaRegistral = "04";break;
		case 39 : registroPublico = "12"; oficinaRegistral = "01";break;
		case 40 : registroPublico = "10"; oficinaRegistral = "04";break;
		case 41 : registroPublico = "08"; oficinaRegistral = "04";break;
		case 42 : registroPublico = "02"; oficinaRegistral = "04";break;
		case 43 : registroPublico = "10"; oficinaRegistral = "03";break;
		case 44 : registroPublico = "05"; oficinaRegistral = "01";break;
		case 45 : registroPublico = "13"; oficinaRegistral = "01";break;
		case 46 : registroPublico = "07"; oficinaRegistral = "05";break;
		case 47 : registroPublico = "06"; oficinaRegistral = "04";break;
		case 48 : registroPublico = "08"; oficinaRegistral = "05";break;
		case 49 : registroPublico = "02"; oficinaRegistral = "05";break;
		case 50 : registroPublico = "06"; oficinaRegistral = "05";break;
		case 51 : registroPublico = "05"; oficinaRegistral = "02";break;
		case 52 : registroPublico = "07"; oficinaRegistral = "01";break;
		case 53 : registroPublico = "12"; oficinaRegistral = "02";break;
		case 54 : registroPublico = "02"; oficinaRegistral = "07";break;
		case 55 : registroPublico = "02"; oficinaRegistral = "08";break;
		case 56 : registroPublico = "08"; oficinaRegistral = "01";break;
		case 57 : registroPublico = "05"; oficinaRegistral = "03";break;
		case 58 : registroPublico = "09"; oficinaRegistral = "02";break;
		case 59 : registroPublico = "02"; oficinaRegistral = "03";break;
	}

	document.form1.oficinas.value = registroPublico + "|" + oficinaRegistral + "|";
}


function RefrescaCombos(codProv) {
	fbox = document.form1.CboOficinas;
	for(var i=0; i<fbox.options.length; i++)
	{
	  	if (fbox.options[i].value == codProv) {
			fbox.options[i].selected=true;
		}
	}
	CambioOficinaRegistral(codProv);
}

function ClearList(OptionList, TitleName){
	OptionList.length = 0;
}
	
function move(side)
{   
	var temp1 = new Array();
	var temp2 = new Array();
	var tempa = new Array();
	var tempb = new Array();
	var current1 = 0;
	var current2 = 0;
	var y=0;
	var attribute;
	
	//assign what select attribute treat as attribute1 and attribute2
	if (side == "right")
	{  
		attribute1 = document.rep.category_name; 
		attribute2 = document.rep.category_selected;
	}
	else
	{  
		attribute1 = document.rep.category_selected;
		attribute2 = document.rep.category_name;
	}

	//fill an array with old values
	for (var i = 0; i < attribute2.length; i++)
	{  
		y=current1++
		temp1[y] = attribute2.options[i].value;
		tempa[y] = attribute2.options[i].text;
	}

	//assign new values to arrays
	for (var i = 0; i < attribute1.length; i++)
	{   
		if ( attribute1.options[i].selected )
		{  
			y=current1++
			temp1[y] = attribute1.options[i].value;
			tempa[y] = attribute1.options[i].text;
		}
		else
		{  
			y=current2++
			temp2[y] = attribute1.options[i].value; 
			tempb[y] = attribute1.options[i].text;
		}
	}

	//generating new options 
	for (var i = 0; i < temp1.length; i++)
	{  
		attribute2.options[i] = new Option();
		attribute2.options[i].value = temp1[i];
		attribute2.options[i].text =  tempa[i];
	}

	//generating new options
	ClearList(attribute1,attribute1);
	if (temp2.length>0)
	{	
		for (var i = 0; i < temp2.length; i++)
		{   
			attribute1.options[i] = new Option();
			attribute1.options[i].value = temp2[i];
			attribute1.options[i].text =  tempb[i];
		}
	}
}
		
		
//-->
</script>
<script language="javascript">
	function busqdirectapornumero(){
		if (esVacio(document.form1.numtitu.value) || !esEntero(document.form1.numtitu.value) || !esEnteroMayor(document.form1.numtitu.value,1))
		{	
			alert("Por favor ingrese correctamente el Numero del Titulo");
			document.form1.numtitu.focus();
			return;
		}
		if(tieneCaracterNoValido(document.form1.numtitu.value)){
			alert("Por favor no ingrese caracteres no válidos");
			document.form1.numtitu.focus();
			return;
		}
	
		document.form1.numtitu.value = rellenaIzq(document.form1.numtitu.value,"0",8);
		document.form1.method = "POST";
		document.form1.action = "/iri/BusquedaTituloIRI.do?state=buscarXNroTitulo";
		
		//alert(document.form1.action);
		CambioOficinaRegistralC(document.form1.CboOficinas.selectedIndex);
		
		
		document.form1.submit();
	}
function OcultaPresentante()
{ presentante.style.display="none";
 
}
function PorPresentante()
{window.open ("ResultTituloPresentante.html",target="_self")
}
function PorParticipante()
{window.open ("ResultTituloParticipante.html",target="_self")
}
function OcultaParticipante()
{ participante.style.display="none"
}
function Sede()
{}

function Todas()
{ myform=document.forms[0]
  if (myform.C1(13).checked)
  for (i=0;i<=13;i++)
  { myform.C1(i).checked="true"
  }
  else
  for (i=0;i<=13;i++)
  { myform.C1(i).checked=0
  }
  
}
</script>
<script Language="JavaScript">
	function VentanaFlotante(pag)
		{
		var ancho= 500;
		var alto= 563;
		NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,width=" + ancho + ",height=" + alto + ",top=20,left=100");
		}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>


<body onLoad="document.form1.reset();CambioOficinaRegistralC(document.form1.CboOficinas.selectedIndex);">
<div id="maincontent">
	<div class="innertube">
		<form name="form1">
			<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Consulta de Estado de T&iacute;tulos</b>
			<br><br>
		    <SPAN id="combos">
				<table class="punteadoTablaTop" width="600px">
					<tr> 
				      <td  align="left" width="130">Oficina Registral </td>
				      <td >
				        <input type="hidden" name="oficinas">
				        <select size="1" name="CboOficinas" onChange="CambioOficinaRegistralC(this.selectedIndex);">
				          <option value="10|07" <logic:present name="odev"><logic:equal name="odev" value="0">selected</logic:equal></logic:present>>Andahuaylas</option>
				          <option value="06|02" <logic:present name="odev"><logic:equal name="odev" value="1">selected</logic:equal></logic:present>>Apurimac</option>
				          <option value="03|01" <logic:present name="odev"><logic:equal name="odev" value="2">selected</logic:equal></logic:present>>Arequipa</option>
				          <option value="10|05" <logic:present name="odev"><logic:equal name="odev" value="3">selected</logic:equal></logic:present>>Ayacucho</option>
				          <option value="01|06" <logic:present name="odev"><logic:equal name="odev" value="4">selected</logic:equal></logic:present>>Barranca</option>
				          <option value="11|04" <logic:present name="odev"><logic:equal name="odev" value="5">selected</logic:equal></logic:present>>Bagua</option>
				          <option value="01|02" <logic:present name="odev"><logic:equal name="odev" value="7">selected</logic:equal></logic:present>>Callao</option>
				          <option value="11|02" <logic:present name="odev"><logic:equal name="odev" value="8">selected</logic:equal></logic:present>>Cajamarca</option>
				          <option value="03|02" <logic:present name="odev"><logic:equal name="odev" value="9">selected</logic:equal></logic:present>>Caman&aacute;</option>
				          <option value="01|05" <logic:present name="odev"><logic:equal name="odev" value="10">selected</logic:equal></logic:present>>Ca&ntilde;ete</option>
				          <option value="04|02" <logic:present name="odev"><logic:equal name="odev" value="11">selected</logic:equal></logic:present>>Casma</option>
				          <option value="03|03" <logic:present name="odev"><logic:equal name="odev" value="12">selected</logic:equal></logic:present>>Castilla</option>
				          <option value="11|05" <logic:present name="odev"><logic:equal name="odev" value="13">selected</logic:equal></logic:present>>Chachapoyas</option>
				          <option value="08|02" <logic:present name="odev"><logic:equal name="odev" value="14">selected</logic:equal></logic:present>>Chep&eacute;n</option>
				          <option value="11|01" <logic:present name="odev"><logic:equal name="odev" value="46">selected</logic:equal></logic:present>>Chiclayo</option>
				          <option value="04|03" <logic:present name="odev"><logic:equal name="odev" value="15">selected</logic:equal></logic:present>>Chimbote</option>
				          <option value="10|02" <logic:present name="odev"><logic:equal name="odev" value="16">selected</logic:equal></logic:present>>Chincha</option>
				          <option value="11|06" <logic:present name="odev"><logic:equal name="odev" value="17">selected</logic:equal></logic:present>>Chota</option>
				          <%--<option value="13|01" <logic:present name="odev"><logic:equal name="odev" value="18">selected</logic:equal></logic:present>>Coronel portillo</option>--%>
				          <option value="06|01" <logic:present name="odev"><logic:equal name="odev" value="19">selected</logic:equal></logic:present>>Cusco</option>
				          <option value="01|04" <logic:present name="odev"><logic:equal name="odev" value="20">selected</logic:equal></logic:present>>Huacho</option>
				          <option value="08|03" <logic:present name="odev"><logic:equal name="odev" value="20">selected</logic:equal></logic:present>>Huamachuco</option>
				          <option value="10|08" <logic:present name="odev"><logic:equal name="odev" value="21">selected</logic:equal></logic:present>>Huancavelica</option>
				          <option value="02|01" <logic:present name="odev"><logic:equal name="odev" value="22">selected</logic:equal></logic:present>>Huancayo</option>
				          <option value="10|06" <logic:present name="odev"><logic:equal name="odev" value="24">selected</logic:equal></logic:present>>Huanta</option>
				          <option value="02|02" <logic:present name="odev"><logic:equal name="odev" value="23">selected</logic:equal></logic:present>>Hu&aacute;nuco</option>
				          <option value="01|03" <logic:present name="odev"><logic:equal name="odev" value="01|03">selected</logic:equal></logic:present>>Huaral</option>
				          <option value="04|01" <logic:present name="odev"><logic:equal name="odev" value="04|01">selected</logic:equal></logic:present>>Huaraz</option>
				          <option value="10|01" <logic:present name="odev"><logic:equal name="odev" value="10|01">selected</logic:equal></logic:present>>Ica</option>
				          <option value="07|02" <logic:present name="odev"><logic:equal name="odev" value="07|02">selected</logic:equal></logic:present>>Ilo</option>
				          <option value="09|01" <logic:present name="odev"><logic:equal name="odev" value="29">selected</logic:equal></logic:present>>Iquitos</option>
				          <option value="03|04" <logic:present name="odev"><logic:equal name="odev" value="03|04">selected</logic:equal></logic:present>>Islay</option>
				          <option value="11|03" <logic:present name="odev"><logic:equal name="odev" value="11|03">selected</logic:equal></logic:present>>Ja&eacute;n</option>
				          <option value="12|03" <logic:present name="odev"><logic:equal name="odev" value="12|03">selected</logic:equal></logic:present>>Juanju&iacute;</option>
				          <%--<option value="02|03" <logic:present name="odev"><logic:equal name="odev" value="02|03">selected</logic:equal></logic:present>>La Unión</option>--%>
				          <option value="07|03" <logic:present name="odev"><logic:equal name="odev" value="07|03">selected</logic:equal></logic:present>>Juliaca</option>
				          <option value="02|06" <logic:present name="odev"><logic:equal name="odev" value="02|06">selected</logic:equal></logic:present>>La Merced</option>
				          <option value="01|01" <logic:present name="odev"><logic:equal name="odev" value="01|01">selected</logic:equal></logic:present>>Lima</option>
				          <option value="06|03" <logic:present name="odev"><logic:equal name="odev" value="06|03">selected</logic:equal></logic:present>>Madre de dios</option>
				          <option value="07|04" <logic:present name="odev"><logic:equal name="odev" value="07|04">selected</logic:equal></logic:present>>Moquegua</option>
				          <option value="12|01" <logic:present name="odev"><logic:equal name="odev" value="12|01">selected</logic:equal></logic:present>>Moyobamba</option>
				          <option value="10|04" <logic:present name="odev"><logic:equal name="odev" value="10|04">selected</logic:equal></logic:present>>Nazca</option>
				          <option value="08|04" <logic:present name="odev"><logic:equal name="odev" value="08|04">selected</logic:equal></logic:present>>Otuzco</option>
				          <option value="02|04" <logic:present name="odev"><logic:equal name="odev" value="02|04">selected</logic:equal></logic:present>>Pasco</option>
				          <option value="10|03" <logic:present name="odev"><logic:equal name="odev" value="10|03">selected</logic:equal></logic:present>>Pisco</option>
				          <option value="05|01" <logic:present name="odev"><logic:equal name="odev" value="42">selected</logic:equal></logic:present>>Piura</option>
				          <option value="13|01" <logic:present name="odev"><logic:equal name="odev" value="13|01">selected</logic:equal></logic:present>>Pucallpa</option>
				          <option value="07|05" <logic:present name="odev"><logic:equal name="odev" value="07|05">selected</logic:equal></logic:present>>Puno</option>
				          <option value="06|04" <logic:present name="odev"><logic:equal name="odev" value="06|04">selected</logic:equal></logic:present>>Quillabamba</option>
				          <option value="08|05" <logic:present name="odev"><logic:equal name="odev" value="08|05">selected</logic:equal></logic:present>>San pedro de lloc</option>
				          <option value="02|05" <logic:present name="odev"><logic:equal name="odev" value="02|05">selected</logic:equal></logic:present>>Satipo</option>
				          <%--<option value="02|06" <logic:present name="odev"><logic:equal name="odev" value="02|06">selected</logic:equal></logic:present>>Selva Central</option>--%>
				          <option value="06|05" <logic:present name="odev"><logic:equal name="odev" value="06|05">selected</logic:equal></logic:present>>Sicuani</option>
				          <option value="05|02" <logic:present name="odev"><logic:equal name="odev" value="05|02">selected</logic:equal></logic:present>>Sullana</option>
				          <option value="07|01" <logic:present name="odev"><logic:equal name="odev" value="07|01">selected</logic:equal></logic:present>>Tacna</option>
				          <option value="12|02" <logic:present name="odev"><logic:equal name="odev" value="12|02">selected</logic:equal></logic:present>>Tarapoto</option>
				          <option value="02|07" <logic:present name="odev"><logic:equal name="odev" value="02|07">selected</logic:equal></logic:present>>Tarma</option>
				          <option value="02|08" <logic:present name="odev"><logic:equal name="odev" value="02|08">selected</logic:equal></logic:present>>Tingo Mar&iacute;a</option>
				          <option value="08|01" <logic:present name="odev"><logic:equal name="odev" value="08|01">selected</logic:equal></logic:present>>Trujillo</option>
				          <option value="05|03" <logic:present name="odev"><logic:equal name="odev" value="05|03">selected</logic:equal></logic:present>>Tumbes</option>
				          <option value="09|02" <logic:present name="odev"><logic:equal name="odev" value="09|02">selected</logic:equal></logic:present>>Yurimaguas</option>
				        </select>
				      </td>
				      <td align="left">
						  <a class="linkInicio" href="javascript:VentanaFlotante('/iri/acceso/mapas/MAPA1.htm')">
						      Identifique su Oficina Registral
						  </a>
					  </td>
				    </tr>
				</table>
			</span>
			<br>
			<table cellspacing=0>
		      <tr> 
		    	<td width="685" colspan="8" >
		    		<p align="left"><font color="#949400" size="2"><strong>Por N&uacute;mero de T&iacute;tulo</strong></font></p>
		    	</td>
		      </tr>
			</table>
			<table cellspacing=0  class="punteadoTablaTop" width="600px">
		      <tr bordercolor="#0000FF"> 
		     	<td align=left width="130">
		     		A&ntilde;o :
		     	</td>
		     	<td align="left">
		          <select size="1" name="ano">
		            <option value="2001">2001</option>
		            <option value="2002">2002</option>
		            <option value="2003">2003</option>
		            <option value="2004">2004</option>
		            <option value="2005">2005</option>
		            <option value="2006">2006</option>
		            <option value="2007">2007</option>
		            <option value="2008">2008</option>
		            <option value="2009" selected>2009</option>
		          </select>
		       	</td>
		      </tr>
		      <tr>
		       	<td align=left width="130">
		       		N&uacute;mero de T&iacute;tulo :
		       	</td>
		       	<td>
		       		<input type="text" name="numtitu" size="12" maxlength="8" onkeypress="return noenter()">
		       	</td>
		      </tr>
		      <tr>
		          <td colspan="4">
		          	<input type="button" class="formbutton" value="Buscar" onclick="javascript:busqdirectapornumero();"/>
		          </td>
		      </tr>
		   </table>
		   <font color="#949400">
		  	 Para los títulos que inician con la serie 77 millones no se requiere indicar la oficina registral
		   </font>
		   <logic:present name="mensaje">
				<center><font color="red"><h3><bean:write name="mensaje"/></h3></font></center>
		   </logic:present>
		   <input type="hidden" name="pagina" value="1">
		</form>
		<logic:present name="odev">
			<script language="JavaScript">
			CambioOficinaRegistral('<bean:write name="odev"/>');
			</script>
		</logic:present>
		<logic:notPresent name="odev">
			<script language="JavaScript">
			CambioOficinaRegistralC('0');
			</script>
		</logic:notPresent>
	</div>
</div>

</body>
</html>