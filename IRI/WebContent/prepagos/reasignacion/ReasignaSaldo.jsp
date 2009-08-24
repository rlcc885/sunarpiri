<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="javascript">
function Calcula(){
	document.form1.method="POST";
	document.form1.action="/iri/ReasignarSaldo.do?state=decide";
	document.form1.submit();
}
</script>
<title>Actualizaci&oacute;n de Saldo</title></head>

<body>
<form name="form1">
<br>
<table class=cabeceraformulario style="width:400px">
  <tr>
    <td width="20%">&nbsp;</td>
    <th width="60%" colspan="4">Actualizaci&oacute;n de Saldo</th>
    <td width="20%">&nbsp;</td>
  </tr>
</table>
<table class=formulario style="width:400px">
  <tr>
    <td width="20%"></td>
    <td width="15%"></td>
    <td width="15%"><b><font color="#993300">SALDO DISPONIBLE DE LA ORGANIZACI&Oacute;N</font></b></td>
    <td width="15%"><input type="text" value="<bean:write name="saldo_Org"/>" size="5" name="saldo_disponible" disabled="true"></td>
    <td width="15%"></td>
    <td width="20%"></td>
  </tr>
  <tr>
    <td width="20%"></td>
    <td width="15%"></td>
    <td width="15%">&nbsp;</td>
    <td width="15%"></td>
    <td width="15%"></td>
    <td width="20%"></td>
  </tr>  
  <tr>
    <td width="20%"></td>
    <td width="15%">Saldo Inicial</td>
    <td width="15%"><input type="text" disabled="true" value="<bean:write name="saldo_Usr"/>" size="3" name="saldo_inicial"></td>
    <td width="15%"><%//Nuevo Saldo%>
    </td>
    <td width="15%"><%//<input type="text" disabled="true" value="<bean:write name="saldo_Usr"/>" size="3" name="nuevo_saldo">%>
    </td>
    <td width="20%">
    <a href="javascript:Calcula()"><img src="<%=request.getContextPath()%>/images/btn_aplicar.gif"></a>
	</td>
  </tr>
  <tr>
    <td width="20%"></td>
    <td width="15%">Tipo Modificaci&oacute;n</td>
    <td width="15%"><select size="1" name="tipo">
        <option selected value="1">AUMENTO</option>
        <option value="2">DISMINUCION</option>
      </select></td>
    <td width="15%">Monto</td>
    <td width="15%"><input type="text" value="" size="3" name="monto"></td>
    <td width="20%">
	<a href="javascript:window.history.back()"><img src="<%=request.getContextPath()%>/images/btn_cancelar.gif"></a>
	</td>
  </tr>
</table>
<input type="hidden" name="lineaUsuario" value="<bean:write name="lineaUsuario"/>">
</form>
</body>
</html>
