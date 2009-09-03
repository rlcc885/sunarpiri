<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
<%-- 8sep2002HT se agrega estilo global.css y cambios de diseno --%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
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
<b><font color="#949400">DEVOLUCIONES &gt;&gt;</font><font color="#666666">Saldo Disponible Para Devoluci&oacute;n</font></b>

<br>
<form name="form1" >
<input type="hidden" name="param1">
<logic:present name="hidRegisPubId">
<input type="hidden" name="hidRegisPubId" value="<bean:write name="hidRegisPubId"/>">
</logic:present>
<logic:present name="hidOficRegId" >
<input type="hidden" name="hidOficRegId" value="<bean:write name="hidOficRegId"/>">
</logic:present>
<br>

<!-- ******************************************************************************************************* -->


<table border="1" width="600">
  <tr bgcolor="#949400">
	<th width="11%"><font color="white">USUARIO</font></th>
	<th width="8%"><font color="white">TIPO</font></th>
	<th width="16%"><font color="white">APELLIDOS Y NOMBRES</font></th>
	<th width="18%"><font color="white">AFILIADO A ORGANIZACI&Oacute;N</font></th>
	<th width="8%"><font color="white">ADM. ORG.</font></th>
	<th width="8%"><font color="white">FECHA DE AFILIACION</font></th>
	<th width="8%"><font color="white">SALDO</font></th>
	<th width="8%"><font color="white">ULTIMO ACCESO</font></th>
	<th width="8%"><font color="white">DIAS DESDE ULTIMO ACCESO</font></th>    	
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
		<input type="button" class="formbutton" value="Imprimir" onclick="javascript:Imprimir();" />
		<logic:present name="generarSolicitud">
			<input type="button" class="formbutton" value="Siguiente" onclick="javascript:Siguiente();"/>
		</logic:present>
	<br>
	<logic:present name="muestraRegresar">
		<input type="button" class="formbutton" value="Regresar" onclick="javascript:history.back();"/>
	</logic:present>

</div>
</form>
</body>
</html>