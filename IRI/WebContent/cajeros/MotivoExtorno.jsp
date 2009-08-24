<%@ page contentType="text/html;charset=ISO-8859-1" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.caja.*" %>
<HTML>
<head>
<link href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
</head>

<script>
function Aceptar(){
	if (document.frm1.cboMotivoExtorno.value=='00') {
			alert("Seleccione el motivo del extorno");
			document.frm1.cboMotivoExtorno.focus();
			return;
		}
	
	
	document.frm1.glosa.value=document.frm1.cboMotivoExtorno.options[document.frm1.cboMotivoExtorno.selectedIndex].text
	document.frm1.action="/iri/ExtornoPago.do?state=extornarAbono";
	document.frm1.submit();
}
</script>
<BODY>
<br>
<table width="100%"  class="titulo" cellspacing="0">
  <tr>
	<td>
		<FONT COLOR="black">TESORERIA <font size="1">&gt;&gt;</font></FONT><font color="900000">Extornos de Pago</FONT>
	</td>
  </tr>
</table>
<br>

<form name="frm1" method="POST" class="formulario">
<input type="hidden" name="nroAbono" value="<%=request.getAttribute("numeroAbono") %>">
<input type="hidden" name="tpoAbono" value="<%=request.getAttribute("tipoAbono") %>">
<input type="hidden" name="mtoAbono" value="<%=request.getAttribute("montoAbono") %>">
<input type="hidden" name="glosa" value="">

<table width="100%">

<tr>
	<td>Motivo Extorno</td>
	<td></td>
</tr>
<tr>
	<td><SELECT size="1" name="cboMotivoExtorno">
			 <OPTION value="00"></OPTION>
			<logic:present name="arrRepr">
				<logic:iterate name="arrRepr" id="item1" scope="request">
					<OPTION value='<bean:write name="item1" property="codigo"/>'>
					<bean:write name="item1" property="codigo"/>-<bean:write name="item1" property="descripcion" />
					</OPTION>
				</logic:iterate>
			</logic:present>
		</SELECT></td>
</tr>
<tr>
	<td></td>
</tr>
<tr>
	<td align="center"><A href="javascript:Aceptar();"
			onmouseover="javascript:mensaje_status('Aceptar Motivo');return true;"
			onmouseout="javascript:mensaje_status(' ');return true;"><IMG
			border="0" src="images/btn_aceptar.gif" width="89" height="29"></A></td>
</tr>
</table>
</BODY>
</HTML>
