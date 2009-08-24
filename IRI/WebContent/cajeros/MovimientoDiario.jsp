<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.acceso.bean.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head><link rel="stylesheet" href="styles/global.css">
<script>
function ShowReport(pagina){ 
	document.form2.method="POST";
	document.form2.action="/iri/MovDiarioTesorero.do?state=muestra&pagina=" + pagina;
	document.form2.submit();
}

function buscarRecibo(){ 
	document.form1.method="POST";
	document.form1.action="/iri/DiarioRecauda.do?state=muestra&rec=X&pagina=1";
	document.form1.submit();
}
function Reimprimir(valor){ 
	
	document.form2.method="POST";
	document.form2.action="/iri/MovDiarioTesorero.do?state=reimprimirRecibo&nroAbono=" + valor;
	document.form2.submit();
}
function Asociar(){ 
	document.form2.method="POST";
	document.form2.action="/iri/DiarioRecauda.do?state=asociaRecibo";
	document.form2.submit();
}
function BuscaFecha(){ 
	document.form1.method="POST";
	document.form1.action="/iri/MovDiarioTesorero.do?state=muestra&pagina=1";
	document.form1.submit();
}
function Exportar(){ 
	document.form1.method="POST";
	document.form1.action="/iri/MovDiarioTesorero.do?state=exporta";
	document.form1.submit();
}
function ModificaSaldo(valor){ 
	var myvalor = valor;
	var friend = myvalor.split("|");
	var aux1 = friend[0];
	var aux2 = friend[1];
	var aux3 = friend[2];
	document.form2.method="POST";
	document.form2.action="/iri/MovDiarioTesorero.do?state=solicitaExtornarAbono&nroAbono=" + aux1 +"&tpoAbono=" + aux2 + "&monto=" + aux3;
	document.form2.submit();
}
function ModificaSaldo1(valor){ 
	var myvalor = valor;
	var friend = myvalor.split("|");
	var aux1 = friend[0];
	var aux2 = friend[1];
	var aux3 = friend[2];
	document.form2.method="POST";
	document.form2.action="/iri/MovDiarioTesorero.do?state=consultaAbono&nroAbono=" + aux1 +"&tpoAbono=" + aux2 + "&monto=" + aux3;
	document.form2.submit();
}
function SaldoModificado()
{ 
	window.open ("MontosModificados.html", target="_self");
}
function getInfo() {
	myform=document.forms[0]
	doyou = confirm("Está Seguro que desea Cerrar el día? (OK = Si   Cancel = No)"); //Your question.
	if (doyou == true)
	{
	alert ("Se ha asociado las operaciones del dia al número de recibo : "+myform.T1.value); //If your question is answered Yes.

	cierre.style.visibility="hidden";
	cierre1.style.visibility="hidden";
	cierre2.style.visibility="hidden";
	cierre3.style.visibility="hidden";
	cierre4.style.visibility="hidden";
	cierre5.style.visibility="hidden";

	//myform.cierre.disabled="true"
	myform.T1.disabled="true";
	for (i=1;i<=7;i++) {
	if (i!=6) myform.B3(i-1).disabled="true";
	}

	}
	else
	if (doyou == false)
	
	alert("Operación Cancelada"); //If your question is answered No.
	
}

function ValidaCierre()
{
	myform=document.forms[0];
	if (myform.T1.value=="")
	alert ("Debe ingresar el número de recibo");
	else {
	getInfo();
	}
}
</script>


<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table border="0" width="100%" class="titulo">
  <tr>
	<td>
		<font color=black>CAJA &gt;&gt; </font> Movimiento Diario de Recaudaci&oacute;n
	</td>
  </tr>
</table>
<br>
<!-- ************************ FORM1 ******************************* -->
<form name="form1">
<table class="formulario" width="602">
  <tr>
    <td nowrap></td>
    <td nowrap>Caja</td>
    <td nowrap valign="middle">D&iacute;a&nbsp;</td>
    <td nowrap valign="middle">Mes&nbsp;</td>
    <td nowrap valign="middle">A&ntilde;o&nbsp;</td>
    <td nowrap valign="middle"></td>
    <td></td>
    <td></td>
  </tr>
  <tr>
  	
    <td nowrap></td>
    <td nowrap>
		<select size="1" name="cboCajas">
		<option value="T">TODOS</option>
		<logic:present name="arrRepr">
	        <logic:iterate name="arrRepr" id="item1" scope="request">
				<logic:present name="codigo">
					<logic:equal name="item1" property="codigo"  value='<%=(String)request.getAttribute("codigo")%>'>
						<option value="<bean:write name="item1" property="codigo"/>" selected><bean:write name="item1" property="descripcion"/></option>
				 	</logic:equal>	
					<logic:notEqual name="item1" property="codigo"  value='<%=(String)request.getAttribute("codigo")%>'>
						<option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
				 	</logic:notEqual>	
				</logic:present>
				<logic:notPresent name="codigo">
					<option value="<bean:write name="item1" property="codigo"/>" ><bean:write name="item1" property="descripcion"/></option>
				</logic:notPresent>
			</logic:iterate>
		</logic:present>
		</select>
    </td>
    <td nowrap valign="middle">
      <p align="left">&nbsp;
      <select size="1" name="dia">
        <option <logic:present name="dia"><logic:equal name="dia" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="12">selected</logic:equal></logic:present>>12</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="13">selected</logic:equal></logic:present>>13</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="14">selected</logic:equal></logic:present>>14</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="15">selected</logic:equal></logic:present>>15</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="16">selected</logic:equal></logic:present>>16</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="17">selected</logic:equal></logic:present>>17</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="18">selected</logic:equal></logic:present>>18</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="19">selected</logic:equal></logic:present>>19</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="20">selected</logic:equal></logic:present>>20</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="21">selected</logic:equal></logic:present>>21</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="22">selected</logic:equal></logic:present>>22</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="23">selected</logic:equal></logic:present>>23</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="24">selected</logic:equal></logic:present>>24</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="25">selected</logic:equal></logic:present>>25</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="26">selected</logic:equal></logic:present>>26</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="27">selected</logic:equal></logic:present>>27</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="28">selected</logic:equal></logic:present>>28</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="29">selected</logic:equal></logic:present>>29</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="30">selected</logic:equal></logic:present>>30</option>
        <option <logic:present name="dia"><logic:equal name="dia" value="31">selected</logic:equal></logic:present>>31</option>
      </select>
      <!--input type="button" value=" Ir a" name="B42"--></p>
	</td>
    <td nowrap valign="middle">
	<select size="1" name="mes">
        <option <logic:present name="mes"><logic:equal name="mes" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="mes"><logic:equal name="mes" value="12">selected</logic:equal></logic:present>>12</option>
	</select>
	</td>
    <td nowrap valign="middle">
      <select size="1" name="ano">
        <option <logic:present name="ano"><logic:equal name="ano" value="2003"></logic:equal></logic:present>>2003</option>
        <option <logic:present name="ano"><logic:equal name="ano" value="2004"></logic:equal></logic:present>>2004</option>
        <option <logic:present name="ano"><logic:equal name="ano" value="2005"></logic:equal></logic:present>>2005</option>
        <option <logic:present name="ano"><logic:equal name="ano" value="2006"></logic:equal></logic:present>>2006</option>
        <option <logic:present name="ano"><logic:equal name="ano" value="2007"></logic:equal></logic:present>>2007</option>
        <option <logic:present name="ano"><logic:equal name="ano" value="2008"></logic:equal></logic:present>>2008</option>
        <option <logic:present name="ano"><logic:equal name="ano" value="2009">selected</logic:equal></logic:present>>2009</option>
      </select>
  </td>
  <td nowrap valign="middle"> 
  <!--<input type="button" value="Ir a" onclick="BuscaFecha()">-->
  <a href="javascript:BuscaFecha();"><img src="images/btn_buscar2.gif" border="0"></a>
  </td>

<!-- ************************ FORM2 ******************************* -->

<form name="form2">
    <td></td>
      <td> <a href="javascript:Asociar();"></a>
       	
      </td>
  </tr>
  <tr>
    <td colspan="8"></td>
  </tr>
</table>

<!--  V A R I A B L E S   G E N E R A L E S  -->
<logic:present name="ano">
	<input type="hidden" name="ano" value="<bean:write name="ano"/>">
	<input type="hidden" name="mes" value="<bean:write name="mes"/>">
	<input type="hidden" name="dia" value="<bean:write name="dia"/>">
</logic:present>

<logic:present name="rec">
	<input type="hidden" name="rec" value="<bean:write name="rec"/>">
	<input type="hidden" name="recibo" value="<bean:write name="recibo"/>">
</logic:present>

<!--  V A R I A B L E S   G E N E R A L E S  -->

<table class=grilla>  
  <tr>
    <th width="43" height="11">Cajero ID&nbsp;</th>
    <th width="48" height="11">Recibo</th>
    <th width="93" height="11">Hora</th>
    <th width="62" height="11">Abono</th>
    <th width="62" height="11">Concepto</th>
    <th width="45" height="11">Tpo Persona</th>
    <th width="242" height="11">Raz&oacute;n Social / Apellidos Nombres</th>
    <th width="43" height="11">Monto</th>
    <th width="58" height="11">Tipo</th>
    <th width="89" height="11" bordercolor="#FFFFFF">&nbsp;</th>
  </tr>
  <logic:iterate name="lista" id="item" scope="request">
  <tr class="grilla2">
    <td width="43" height="12" align="center" bgcolor="#E2E2E2"><bean:write name="item" property="idCajero"/><input type="hidden" name="abonoId" value="<bean:write name="item" property="idAbono"/>"></td>
    <td width="48" height="12" align="center" bgcolor="#E2E2E2"><bean:write name="item" property="nroRecibo"/></td>
    <td width="93" height="12" align="center" bgcolor="#E2E2E2"><bean:write name="item" property="hora"/></td>
    <td width="62" height="12" align="center" bgcolor="#E2E2E2"><bean:write name="item" property="idAbono"/></td>
    <td width="62" height="12" align="center" bgcolor="#E2E2E2"><bean:write name="item" property="tipoAbono"/></td>
    <td width="45" height="12" align="center" bgcolor="#E2E2E2"><bean:write name="item" property="tipoPersona"/></td>
    <td width="242" height="12" align="center" bgcolor="#E2E2E2"><bean:write name="item" property="nombre"/></td>
    <td width="43" height="12" align="right" bgcolor="#E2E2E2"><bean:write name="item" property="monto"/></td>
    <td width="58" height="12" align="right" bgcolor="#E2E2E2"><bean:write name="item" property="tipoPago"/></td>
    <td width="89" height="30" align="center" bgcolor="#FFFFFF" bordercolor="#FFFFFF">
	<a href="javascript:Reimprimir('<bean:write name="item" property="idAbono"/>')"><img src="images/btn_reimprimir.gif" border="0"></a>
    </td>
  </tr>
  </logic:iterate>
</table>  

<table>  
  <tr  >
    <td width="43" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="48" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="91" height="12" align="center" bgcolor="#FFFFFF"></td>
    <td width="60" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td height="12" align="center" bgcolor="#FFFFFF" width="63"></td>
    <td height="12" align="center" bgcolor="#FFFFFF" width="206"><p align="right"><font color="#990000"><b>TOTAL ABONOS</b></font></p></td>
    <td height="12" align="right" bgcolor="#E2E2E2" nowrap="nowrap" width="71"><b><bean:write name="perfect" property="totalAbonos"/></b></td>
  </tr>
  <tr>
    <td width="43" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="48" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="91" height="12" align="center" bgcolor="#FFFFFF"></td>
    <td width="60" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td height="12" align="center" bgcolor="#FFFFFF" width="63"></td>
    <td height="12" align="center" bgcolor="#FFFFFF" width="206"><p align="right"><font color="#990000"><b>TOTAL EXTORNOS</b></font></p></td>
    <td height="12" align="right" bgcolor="#E2E2E2" nowrap="nowrap" width="71"><b><bean:write name="perfect" property="totalExtornos"/></b></td>
  </tr>
  <tr>
    <td width="43" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="48" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="91" height="12" align="center" bgcolor="#FFFFFF"></td>
    <td width="60" height="12" align="center" bgcolor="#FFFFFF">&nbsp;</td>
    <td height="12" align="center" bgcolor="#FFFFFF" width="63"></td>
    <td height="12" align="center" bgcolor="#FFFFFF" width="206"><p align="right"><font color="#990000"><b>TOTAL RECAUDADO</b></font></p></td>
    <td height="12" align="right" bgcolor="#E2E2E2" nowrap="nowrap" width="71"><b><bean:write name="perfect" property="totalRecaudado"/></b></td>
  </tr> 
</table>
<br>

<!-- ************************ P A G I N A C I O N ******************************* -->
<center>
<table  class=tablasinestilo>
<tr>
<td>
<logic:present name="previous">
	<a href="javascript:ShowReport(<bean:write name="previous"/>);">Anterior</a>
</logic:present>
</td>
<td>
<logic:present name="next">
	<a href="javascript:ShowReport(<bean:write name="next"/>);">Siguiente</a>
</logic:present>
</td>
</tr>
</table>
</center>
<!-- ************************ P A G I N A C I O N ******************************* -->
<center>
<table>
  <tr>
	<td>
  		<a href="javascript:history.go(-1)"><img border="0" src="images/btn_regresa.gif" width="83" height="25"></a>
		<!--input type="button" value="Regresar" name="B43" onclick="history.back();"-->
	</td>
	<td>
		<a href="javascript:window.print();"><img border="0" src="images/btn_print.gif" width="83" height="25"></a>
	</td>
	<td>
		<a href="javascript:Exportar();"><img border="0" src="images/btn_exportar.gif" width="83" height="24"></a>
	</td>
  </tr>
</table>
</center>
</form>
<!-- ************************ FORM5 ******************************* -->
<form name="form5">
	<input type="hidden" name="nroAbono">
	<input type="hidden" name="tpoAbono">
</form>
</body>
</html>