<%@ page contentType="text/html;charset=ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*"%>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*"%>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<script language="JavaScript" src="javascript/util.js">
</script>

<link rel="stylesheet" href="styles/global.css">

<script language="javascript">
function validarformulario()
{
	if(document.form1.radio[0].checked==true)
	{
		if(esVacio(document.form1.razsoc.value) && esVacio(document.form1.ruc.value))
		{
			alert("Datos Incorrectos. Ingrese la Razón Social o el número de RUC");
			document.form1.razsoc.focus();
			return false;
		}	
		if(esVacio(document.form1.razsoc.value))
		{
			if(isNaN(document.form1.ruc.value) || document.form1.ruc.value.indexOf(".") > 0 || !esEnteroMayor(document.form1.ruc.value,1) || !esLongitudMayor(document.form1.ruc.value,11))
			{
				alert("Datos Incorrectos. Ingrese el número de RUC correctamente");
				document.form1.ruc.focus();
				return false;
			}			
		}
	}
	else
	{
		if(document.form1.radio[1].checked)
		{
			if(esVacio(document.form1.userId.value))
			{
				alert("Datos Incorrectos. Ingrese el Usuario");
				document.form1.userId.focus();		
				return false;
			}
		}else
		{
				alert("Seleccione un Tipo de Usuario");
				return false;
		}
	}		
	return true;	
}

function ShowReport(pagina)
{
	if(validarformulario())
	{
		document.form1.method="POST";
		document.form1.action="/iri/ConstanciaPago.do?state=decide&pagina=" + pagina;
		document.form1.submit();
		return true;
	}
	return false;
}

function exportar()
{
	if(validarformulario())
	{
		document.form1.method="POST";
		document.form1.action="/iri/ConstanciaPago.do?state=exporta";
		document.form1.submit();
		return true;
	}
	return false;
}

function Mostrar(linea)
{ 
	document.form2.method = "POST";
	document.form2.action = "/iri/ConstanciaPago.do?state=muestraMovimientos&lineaPrepago=" + linea;
	document.form2.submit();
}

function doCambiaRadio(obj, valor)
{ 
for (var rr = 0; rr < obj.length; rr++)
	{
		var xvlr = obj[rr].value;
		if (xvlr == valor)
			obj[rr].checked=true;
	}
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head> 




<body>
<form name="form1">
<br>
<table class=titulo>
  <tr> 
       <td><font color=black>CONSTANCIAS &gt;&gt;</font> Constancia de pago</td>
  </tr>
</table>
<br>
<table class=formulario width="626" cellspacing=0>
  <tr>
    <th  valign="middle" colspan="2" width="246" ><b>Desde</b></th>
    <th  valign="middle" width="286" ><b>Hasta</b></th>
    <th  valign="middle" width="91" >
    <input type="image" src="images/btn_ejecuta.gif" border="0" onClick="return ShowReport(1);">
    </th>
  </tr>
  <tr>
    <td  valign="middle" nowrap colspan="2" width="246" >d&iacute;a<select size="1" name="diainicio">
        <option <logic:present name="di1"><logic:equal name="di1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="12">selected</logic:equal></logic:present>>12</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="13">selected</logic:equal></logic:present>>13</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="14">selected</logic:equal></logic:present>>14</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="15">selected</logic:equal></logic:present>>15</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="16">selected</logic:equal></logic:present>>16</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="17">selected</logic:equal></logic:present>>17</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="18">selected</logic:equal></logic:present>>18</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="19">selected</logic:equal></logic:present>>19</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="20">selected</logic:equal></logic:present>>20</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="21">selected</logic:equal></logic:present>>21</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="22">selected</logic:equal></logic:present>>22</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="23">selected</logic:equal></logic:present>>23</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="24">selected</logic:equal></logic:present>>24</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="25">selected</logic:equal></logic:present>>25</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="26">selected</logic:equal></logic:present>>26</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="27">selected</logic:equal></logic:present>>27</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="28">selected</logic:equal></logic:present>>28</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="29">selected</logic:equal></logic:present>>29</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="30">selected</logic:equal></logic:present>>30</option>
        <option <logic:present name="di1"><logic:equal name="di1" value="31">selected</logic:equal></logic:present>>31</option>
      </select>mes<select size="1" name="mesinicio">
        <option <logic:present name="mi1"><logic:equal name="mi1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="mi1"><logic:equal name="mi1" value="12">selected</logic:equal></logic:present>>12</option>
      </select>a&ntilde;o<select size="1" name="anoinicio">
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2003">selected</logic:equal></logic:present>>2003</option>
        <option <logic:present name="ai1"><logic:equal name="ai1" value="2004">selected</logic:equal></logic:present>>2004</option>
      </select></td>
    <td valign="middle" nowrap colspan="2" width="342" >
      &nbsp;dia <select size="1" name="diafin">
        <option <logic:present name="df1"><logic:equal name="df1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="12">selected</logic:equal></logic:present>>12</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="13">selected</logic:equal></logic:present>>13</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="14">selected</logic:equal></logic:present>>14</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="15">selected</logic:equal></logic:present>>15</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="16">selected</logic:equal></logic:present>>16</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="17">selected</logic:equal></logic:present>>17</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="18">selected</logic:equal></logic:present>>18</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="19">selected</logic:equal></logic:present>>19</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="20">selected</logic:equal></logic:present>>20</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="21">selected</logic:equal></logic:present>>21</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="22">selected</logic:equal></logic:present>>22</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="23">selected</logic:equal></logic:present>>23</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="24">selected</logic:equal></logic:present>>24</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="25">selected</logic:equal></logic:present>>25</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="26">selected</logic:equal></logic:present>>26</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="27">selected</logic:equal></logic:present>>27</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="28">selected</logic:equal></logic:present>>28</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="29">selected</logic:equal></logic:present>>29</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="30">selected</logic:equal></logic:present>>30</option>
        <option <logic:present name="df1"><logic:equal name="df1" value="31">selected</logic:equal></logic:present>>31</option>
      </select>mes&nbsp; <select size="1" name="mesfin">
        <option <logic:present name="mf1"><logic:equal name="mf1" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="mf1"><logic:equal name="mf1" value="12">selected</logic:equal></logic:present>>12</option>      
      </select>
      a&ntilde;o<select size="1" name="anofin">
        <option <logic:present name="af1"><logic:equal name="af1" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2003">selected</logic:equal></logic:present>>2003</option>
        <option <logic:present name="af1"><logic:equal name="af1" value="2004">selected</logic:equal></logic:present>>2004</option>
      </select>
    </td>
  </tr>
  <tr>
    <td valign="middle" width="112"><b>Tipo de Usuario</b></td>
    <td valign="middle" width="130"><input type="radio" <logic:present name="r1"><logic:equal name="r1" value="2">checked</logic:equal></logic:present> value="2" name="radio"><b>Organizaci&oacute;n</b></td>
    <td valign="middle" colspan="2" width="342"><b>&nbsp;Razon
      Social&nbsp; </b><input type="text" size="20" name="razsoc" maxlength=100 onfocus="doCambiaRadio(document.form1.radio,'2');" onblur="solonumlet(this)" value="<logic:present name="rs1"><bean:write name="rs1"/></logic:present>">&nbsp; <b>RUC&nbsp;</b><input type="text" size="11" maxlength=11 name="ruc" onfocus="doCambiaRadio(document.form1.radio,'2');" onblur="solonumlet(this)" value="<logic:present name="ruc1"><bean:write name="ruc1"/></logic:present>"></td>
  </tr>
  <tr>
    <td  valign="middle" width="112"></td>
    <td  valign="middle" width="130"><input type="radio" <logic:present name="r1"><logic:equal name="r1" value="1">checked</logic:equal></logic:present> value="1" name="radio"><b>Individual</b></td>
    <td  valign="middle" colspan="2" width="342">
      <p align="left"><b> &nbsp;Usuario ID&nbsp; </b><input type="text" size="20" maxlength=13 name="userId" onfocus="doCambiaRadio(document.form1.radio,'1');" onblur="solonumlet(this)" value="<logic:present name="usr1"><bean:write name="usr1"/></logic:present>"></p>
    </td>
  </tr>
  <tr>
    <td  valign="middle" width="112"></td>
    <td valign="middle" nowrap colspan="3" width="476"><b>Oficina Registral</b> 
      <select size="1" name="agencia">
  		<option <logic:present name="ag1"><logic:equal name="ag1" value="T"> selected</logic:equal></logic:present> value="T">TODAS</option>
		<logic:iterate name="listaOficinas" id="item1" scope="request">
			<option <logic:present name="ag1"> <% if(request.getAttribute("ag1").equals( 
														((OficRegistralesBean)item1).getRegPubId() + "|" + 
														((OficRegistralesBean)item1).getOficRegId())){ out.print("selected");}%>  </logic:present> value="<bean:write name="item1" property="regPubId"/>|<bean:write name="item1" property="oficRegId"/>"> <bean:write name="item1" property="nombre"/></option>
		</logic:iterate>
	  </select>
   <b>Tipo Pago</b><select size="1" name="tipopago">
        <option <logic:present name="tp1"><logic:equal name="tp1" value="X">selected</logic:equal></logic:present> value="X">TODOS</option>
        <option <logic:present name="tp1"><logic:equal name="tp1" value="D">selected</logic:equal></logic:present> value="D">DEPOSITOS</option>
        <option <logic:present name="tp1"><logic:equal name="tp1" value="A">selected</logic:equal></logic:present> value="A">AMPLIACIONES</option>
      </select></td>
  </tr>
</table>
<br>
<input type="hidden" name="origenConstancia" value="<%=request.getAttribute("origenConstancia")%>">
</form>







<logic:present name="contador0">
<center><font color="red"><b>No se han encontrado pagos realizados por los usuarios u organizaciones especificados</b></font></center>
</logic:present>


<br>


<logic:present name="contador1">
<table class=titulo>
  <tr> 
      <td><font color=black>Constancia de Pagos del </font><bean:write name="fecini"/><font color=black> al </font > <bean:write name="fecfin"/><font color=black> - <bean:write name="entidad"/></td>
  </tr>
</table>

<table class=grilla>
<tr>
    <td width="16%" align="center" height="19"></td>
    <td width="8%" align="center" height="19"></td>
    <td width="7%" align="center" height="19"></td>
    <td width="10%" align="center" height="19"></td>
    <td width="10%" align="center" height="19"></td>
    <td width="11%" align="center" height="19"></td>
    <td width="18%" align="center" height="19"></td>
  </tr>

  <tr>
    <th width="16%" align="center" height="12">N&uacute;mero de Abono</th>
    <th width="8%" align="center" height="12">Fecha</th>
    <th width="7%" align="center" height="12">Hora</th>
    <th width="10%" align="center" height="12">Tipo</th>
    <th width="10%" align="center" height="12">Monto</th>
    <th width="11%" align="center" height="12">Agencia</th>
    <th width="18%" align="center" height="12">Forma de Pago</th>
  </tr>
	<logic:iterate name="listaMovimiento" id="item" scope="request">
	  <tr class=grilla2>
	    <td width="16%" align="center" height="12"><bean:write name="item" property="numAbono"/></td>
	    <td width="8%" align="center" height="12"><bean:write name="item" property="fecha"/></td>
	    <td width="7%" align="center" height="12"><bean:write name="item" property="hora"/></td>
	    <td width="10%" align="center" height="12"><bean:write name="item" property="tipo"/></td>
	    <td width="10%" align="center" height="12"><bean:write name="item" property="monto"/></td>
	    <td width="11%" align="center" height="12"><bean:write name="item" property="agencia"/></td>
	    <td width="18%" align="center" height="12"><bean:write name="item" property="formaPago"/></td>
	  </tr>
	</logic:iterate>  
</table>





















<!-- ***********formulario para paginación************** -->
<form name="form2">
<CENTER>
<table>
<tr>
	<td>
	<logic:present name="previous">
		<a href="javascript:ShowReport(<bean:write name="previous"/>);"><img src="images/btn_ant.gif"></a>
	</logic:present>
	</td>
	
	<td>
	<logic:present name="next">
		<a href="javascript:ShowReport(<bean:write name="next"/>);"><img src="images/btn_sig.gif"></a>
	</logic:present>
	</td>
</tr>
</table>

<br>
<a href="javascript:history.back();"><img src="images/btn_regresa.gif"></a>
<a href="javascript:window.print();"><img src="images/btn_print.gif"></a>
<a href="javascript:exportar();"><img src="images/btn_exportar.gif"></a>
</center>





</logic:present>

<logic:present name="contador2">

<input type="hidden" name="di2" value="<bean:write name="di1"/>">
<input type="hidden" name="mi2" value="<bean:write name="mi1"/>">
<input type="hidden" name="ai2" value="<bean:write name="ai1"/>">
<input type="hidden" name="df2" value="<bean:write name="df1"/>">
<input type="hidden" name="mf2" value="<bean:write name="mf1"/>">
<input type="hidden" name="af2" value="<bean:write name="af1"/>">
<input type="hidden" name="ag2" value="<bean:write name="ag1"/>">
<input type="hidden" name="rad2" value="<bean:write name="r1"/>">
<input type="hidden" name="ruc2" value="<bean:write name="ruc1"/>">
<input type="hidden" name="razsoc2" value="<bean:write name="rs1"/>">
<input type="hidden" name="user2" value="<bean:write name="usr1"/>">
<input type="hidden" name="tp2" value="<bean:write name="tp1"/>">



<input type="hidden" name="fecinig" value="<bean:write name="fecinig"/>">
<input type="hidden" name="fecfing" value="<bean:write name="fecfing"/>">
<input type="hidden" name="tipog" value="<bean:write name="tipog"/>">
<input type="hidden" name="regId" value="<bean:write name="regId"/>">
<input type="hidden" name="oficId" value="<bean:write name="oficId"/>">

<input type="hidden" name="varios" value="x">
<logic:present name="listaUsuarios">
<h3>Resultados de la B&uacute;squeda</h3>


		<logic:present name="RR">
			<input type="hidden" name="EE" value="<bean:write name="RR"/>">
		</logic:present>

		<logic:present name="FF">
			<input type="hidden" name="LL" value="<bean:write name="FF"/>">
		</logic:present>


		
<table border="0" width="100%" height="53" class=grilla>
  <tr>
    <!--<th width="15%">USUARIO ID</th>-->
    <th width="25%">NOMBRE / RAZON SOCIAL</th>
    <th width="8%" >TPO DOC</th>
    <th width="13%">NUM DOC</th>
    <!--<th width="20%">AFILIADO A ORGANIZACION</th>-->
    <th width="19%"></th>
  </tr>
  <logic:iterate name="listaUsuarios" id="item" scope="request">
  <tr class=grilla2> 
	<!--<td width="15%"><bean:write name="item" property="usuarioId"/></td>-->
	<td width="25%"><bean:write name="item" property="nombre"/></td>
	<td width="8%"><bean:write name="item" property="tipo_doc"/></td>
	<td width="13%"><bean:write name="item" property="num_doc"/></td>
    <!--<td width="20%"><bean:write name="item" property="afil_organiz"/></td>-->
	<td width="19%"><input type="button" value="Mostrar" name="B3" onclick="Mostrar('<bean:write name="item" property="lineaPrepago"/>')"></td>
	<input type="hidden" value="<bean:write name='item' property='nombre'/>" name="ent">		
  </tr>
</logic:iterate>
</table>
</logic:present>

<CENTER>
<a href="javascript:history.back();"><img src="images/btn_regresa.gif"></a>
<a href="javascript:window.print();"><img src="images/btn_print.gif"></a>
</center>

</logic:present>






</body>
</html>