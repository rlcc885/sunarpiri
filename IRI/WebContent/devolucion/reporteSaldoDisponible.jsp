<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<%-- 8sep2002HT se agrega estilo global.css y cambios de diseno --%>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";	
}
function Siguiente()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Baja.do?state=generaSolicitudDevolucion";
  document.form1.submit();
}
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table border="0" width="100%" class="titulo">
	<tr>
		<td>
		<font color=black>DEVOLUCIONES&gt;&gt;</font><font color="#993300">Saldo Disponible Para Devoluci&oacute;n</font> </td>
	</tr>
</table>
<br>
<form name="form1" class=formulario>
<input type="hidden" name="param1">
<logic:present name="hidRegisPubId">
<input type="hidden" name="hidRegisPubId" value="<bean:write name="hidRegisPubId"/>">
</logic:present>
<logic:present name="hidOficRegId" >
<input type="hidden" name="hidOficRegId" value="<bean:write name="hidOficRegId"/>">
</logic:present>
<br>

<!-- ******************************************************************************************************* -->


<table class="grilla">
  <tr class=grilla2>
	<th width="11%">USUARIO</th>
	<th width="8%">TIPO</th>
	<th width="16%">APELLIDOS Y NOMBRES</th>
	<th width="18%">AFILIADO A ORGANIZACI&Oacute;N</th>
	<th width="8%">ADM. ORG.</th>
	<th width="8%">FECHA DE AFILIACION</th>
	<th width="8%">SALDO</th>
	<th width="8%">ULTIMO ACCESO</th>
	<th width="8%">DIAS DESDE ULTIMO ACCESO</th>    	
  </tr>
  <tr > 
	<td  width="11%" height="27"><bean:write name="usuario" property="usuario"/></td>
    <td  width="8%"  height="27"><bean:write name="usuario" property="tipo"/></td>
    <td  width="16%" height="27"><bean:write name="usuario" property="ape_Nom"/></td>
    <td  width="18%" height="27"><bean:write name="usuario" property="org_afiliada"/></td>
	<td  width="8%"  height="27"><bean:write name="usuario" property="admin_Org"/></td>
	<td  width="8%" height="27"><bean:write name="usuario" property="fechaAfiliacion"/></td>
	<td  width="8%" height="27" align="center">S/. <bean:write name="usuario" property="saldo"/></td>
	<td  width="8%" height="27"><bean:write name="usuario" property="fechaUltimoAcceso"/></td>
  	<td  width="8%" height="27" align="center"><bean:write name="usuario" property="diasDesdeUltimoAcceso"/></td>
  </tr>
</table>
<div id="HOJA2" style="position:absolute; left:480px;visibility: visible;">
	<br>
	<a href="javascript:Imprimir();"><img src="<%=request.getContextPath()%>/images/btn_print.gif"></a>	<logic:present name="generarSolicitud"><a href="javascript:Siguiente();"><img src="<%=request.getContextPath()%>/images/btn_sig.gif"></a></logic:present>
	<br>

	<logic:present name="muestraRegresar">
		<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
			<img src="images/btn_regresa.gif" >
		</a>
	</logic:present>

</div>
</form>
</body>
</html>