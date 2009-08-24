<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.EstadoTituloBean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
<script language="Javascript">
function orden() {
	document.form1.method="POST";
	document.form1.action="/iri/MisTitulos.do?botonordenado=X&pagina=1";
	document.form1.submit();
}

function buscar() {
	document.form1.method="POST";
	document.form1.action="/iri/MisTitulos.do?aplica=X";
	document.form1.submit();
}

function ShowReport(pagina) {
	document.form1.method="POST";
	document.form1.action="/iri/MisTitulos.do?aplica=X&pagina=" + pagina; 
	document.form1.submit();
}
</script>
</head>
<body>
<form name="form1">
<table class=titulo>
        <tr>
          <td height="15"><font color="BLACK">MIS TITULOS &gt;&gt; VER &gt;&gt; 
            </font>NOTARIO <font color=black>T&iacute;tulos en Tr&aacute;nsito presentados por</font> 
            <bean:write name="user"/></td>
        </tr>
      </table>

<br>
<br>
<table cellspacing=0 class="titulo">
  <tr>
    <td>T&iacute;tulos en Tr&aacute;nsito presentados por <font color="#800000"><bean:write name="user"/> </font></td>
  </tr>
</table>
<br>


<table cellspacing=0 width="904" border="0" class="formulario">
  <tr> 
    <td width="247">Desde</td>
    <td width="257">Hasta</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>dia&nbsp; <select size="1" name="diainicio">
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="12">selected</logic:equal></logic:present>>12</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="13">selected</logic:equal></logic:present>>13</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="14">selected</logic:equal></logic:present>>14</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="15">selected</logic:equal></logic:present>>15</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="16">selected</logic:equal></logic:present>>16</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="17">selected</logic:equal></logic:present>>17</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="18">selected</logic:equal></logic:present>>18</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="19">selected</logic:equal></logic:present>>19</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="20">selected</logic:equal></logic:present>>20</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="21">selected</logic:equal></logic:present>>21</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="22">selected</logic:equal></logic:present>>22</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="23">selected</logic:equal></logic:present>>23</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="24">selected</logic:equal></logic:present>>24</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="25">selected</logic:equal></logic:present>>25</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="26">selected</logic:equal></logic:present>>26</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="27">selected</logic:equal></logic:present>>27</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="28">selected</logic:equal></logic:present>>28</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="29">selected</logic:equal></logic:present>>29</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="30">selected</logic:equal></logic:present>>30</option>
        <option <logic:present name="diainicio"><logic:equal name="diainicio" value="31">selected</logic:equal></logic:present>>31</option>
      </select>
      mes&nbsp;<select size="1" name="mesinicio">
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="mesinicio"><logic:equal name="mesinicio" value="12">selected</logic:equal></logic:present>>12</option>
      </select>
      a&ntilde;o<select size="1" name="anoinicio">
        <option <logic:present name="anoinicio"><logic:equal name="anoinicio" value="2001">selected</logic:equal></logic:present>>2001</option>
        <option <logic:present name="anoinicio"><logic:equal name="anoinicio" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="anoinicio"><logic:equal name="anoinicio" value="2003">selected</logic:equal></logic:present>>2003</option>
      </select></td>
    <td><div align="left">dia&nbsp; 
      <select size="1" name="diafin">
        <option <logic:present name="diafin"><logic:equal name="diafin" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="12">selected</logic:equal></logic:present>>12</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="13">selected</logic:equal></logic:present>>13</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="14">selected</logic:equal></logic:present>>14</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="15">selected</logic:equal></logic:present>>15</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="16">selected</logic:equal></logic:present>>16</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="17">selected</logic:equal></logic:present>>17</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="18">selected</logic:equal></logic:present>>18</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="19">selected</logic:equal></logic:present>>19</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="20">selected</logic:equal></logic:present>>20</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="21">selected</logic:equal></logic:present>>21</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="22">selected</logic:equal></logic:present>>22</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="23">selected</logic:equal></logic:present>>23</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="24">selected</logic:equal></logic:present>>24</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="25">selected</logic:equal></logic:present>>25</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="26">selected</logic:equal></logic:present>>26</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="27">selected</logic:equal></logic:present>>27</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="28">selected</logic:equal></logic:present>>28</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="29">selected</logic:equal></logic:present>>29</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="30">selected</logic:equal></logic:present>>30</option>
        <option <logic:present name="diafin"><logic:equal name="diafin" value="31">selected</logic:equal></logic:present>>31</option>
      </select>
      mes
      <select size="1" name="mesfin">
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="01">selected</logic:equal></logic:present>>01</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="02">selected</logic:equal></logic:present>>02</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="03">selected</logic:equal></logic:present>>03</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="04">selected</logic:equal></logic:present>>04</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="05">selected</logic:equal></logic:present>>05</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="06">selected</logic:equal></logic:present>>06</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="07">selected</logic:equal></logic:present>>07</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="08">selected</logic:equal></logic:present>>08</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="09">selected</logic:equal></logic:present>>09</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="10">selected</logic:equal></logic:present>>10</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="11">selected</logic:equal></logic:present>>11</option>
        <option <logic:present name="mesfin"><logic:equal name="mesfin" value="12">selected</logic:equal></logic:present>>12</option>
      </select>
      a&ntilde;o
      <select size="1" name="anofin">
        <option <logic:present name="anofin"><logic:equal name="anofin" value="2001">selected</logic:equal></logic:present>>2001</option>
        <option <logic:present name="anofin"><logic:equal name="anofin" value="2002">selected</logic:equal></logic:present>>2002</option>
        <option <logic:present name="anofin"><logic:equal name="anofin" value="2003">selected</logic:equal></logic:present>>2003</option>
      </select>
	</div>
	</td>
    <td width="80"><a href="javascript:buscar();"><img src="<%=request.getContextPath()%>/images/btn_aplicar.gif" width="84" height="25" border=0></a> 
      <!--<input type="button" value="Aplicar" name="aplica" onclick="buscar()">--> </td>
  </tr>


  <tr>
    <td colspan="3">Estados
    <select size="1" name="estado">
  		<logic:iterate name="estados" id="item" scope="request">
			<option <logic:present name="estado"> <% if(request.getAttribute("estado").equals(((EstadoTituloBean) item).getEstado_id())){ out.print("selected");}%>  </logic:present> value="<bean:write name="item" property="estado_id"/>"><bean:write name="item" property="descripcion"/></option>
		</logic:iterate>
	  </select>
  </tr>


</table>

<input type="checkbox" name="orden" <logic:present name="orden">checked</logic:present>>ORDENAR POR
ESTADO&nbsp;<a href="javascript:orden();"><img src="<%=request.getContextPath()%>/images/btn_aplicar.gif" width="84" height="25" border=0></a>  
<!--<input type="button" value="Aplicar" name="orden" onclick="orden()">-->

<input type="hidden" name="estado" value="<bean:write name="estado"/>">

<hr>
<br>
<%boolean color = true;%>
<logic:present name="encontro">
<table border="0" width="100%">
  <tr>
    <th width="7%">ZONA REGISTRAL</th>
    <th width="7%">Ofic.Reg.</th>
    <th width="6%">A&ntilde;o</th>
    <th width="10%">T&iacute;tulo</th>
    <th width="8%">Fec Presen</th>
    <th width="8%">Hor Pres</th>
    <th width="9%">Estado</th>
    <th width="8%">Monto Liquidac.</th>
    <th width="7%">Fec. Venc.</th>
    <th width="10%">Participantes</th>
    <td width="6%"></td>
    <td width="9%"></td>
  </tr>
  <logic:present name="list">
  <logic:iterate name="list" id="item1" scope="request">
  <tr <%if(color){ out.print("bgcolor='#E2E2E2'"); color = !color;} %>>
    <td width="7%" align="center"><bean:write name="item1" property="regPub_nombre"/></td>
    <td width="7%" align="center"><bean:write name="item1" property="oficReg_nombre"/></td>
    <td width="6%" align="center"><bean:write name="item1" property="anno"/></td>
    <td width="10%" align="center"><bean:write name="item1" property="titulo"/></td>
    <td width="8%" align="center"><bean:write name="item1" property="fecPresent"/></td>
    <td width="8%" align="center"><bean:write name="item1" property="horPresent"/></td>
    <td width="9%" align="center"><bean:write name="item1" property="estado_id"/></td>
    <td width="8%" align="center"><bean:write name="item1" property="montoLiquida"/></td>
    <td width="7%" align="center"><bean:write name="item1" property="fecVencimiento"/></td>
    <td width="10%" align="center"><bean:write name="item1" property="primerPrtcApePat"/><bean:write name="item1" property="primerPrtcApeMat"/> <bean:write name="item1" property="primerPrtcNombres"/></td>
    <td width="6%" align="center"><a href="/iri/BusquedaTitulo.do?state=buscarXNroTitulo&oficinas=<bean:write name="item1" property="regPub_id"/>|<bean:write name="item1" property="oficReg_id"/>&ano=<bean:write name="item1" property="anno"/>&numtitu=<bean:write name="item1" property="titulo"/>" target="_self">Detalle</a></td>
    <td width="9%" align="center"><a href="javascript:alert('Aun no se dispone de esquela');"><bean:write name="item1" property="desc_url"/></a></td>
  </tr>
  </logic:iterate>
  </logic:present>
</table>
	<!-- ************************ P A G I N A C I O N ******************************* -->
	<table>
	<tr>
	<td>
	<logic:present name="previous">
		<a href="javascript:ShowReport(<bean:write name="previous"/>);"><bean:write name="numeropaginas"/> Anteriores</a>
	</logic:present>
	</td>
	<td>
	<logic:present name="next">
		<a href="javascript:ShowReport(<bean:write name="next"/>);">Siguientes <bean:write name="numeropaginas"/> </a>
	</logic:present>
	</td>
	</tr>
	</table>
	<!-- ************************ P A G I N A C I O N ******************************* -->
</logic:present>

<logic:notPresent name="encontro">
<br>
<center><font color="red">NO SE ENCONTRO REGISTRO ALGUNO</font></center>
</logic:notPresent>
<br><br>
<center>
  <a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border=0></a> 
  <!--input type="button" value="Regresar" onclick="history.go(-1)"-->
  <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border=0></a> 
  <!--input type="button" value="Imprimir Listado"--></center>
</form>
</body>
</html>