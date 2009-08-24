<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head><link rel="stylesheet" href="styles/global.css">
<head><script>

function Imprimir()
{ 
	HOJA2.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";	
}

function Valida(){ 
	document.form1.method="POST";
	document.form1.action="/iri/DiarioRecauda.do?state=muestra";
	document.form1.submit();
}
 
</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>





<body>
<form name="form1">
<font face="Verdana">
<br>
</font>
<input type="hidden" name="nroAbono" value="<bean:write name="nroAbono"/>">
<input type="hidden" name="tpoAbono" value="<bean:write name="tpoAbono"/>">
<table border="0" width="100%" class="titulo">
	<tr>
		<td>
		<font color=black>CAJA &gt;&gt; Movimiento diario de recaudaci&oacute;n &gt;&gt; </font> Extorno de Saldo Prepagado </td>
	</tr>
</table><br>

<input disabled="true" type="hidden" size="5" name="SALDO">
<table class=formulario width="600">
  <tr>
    <td width="35"></td>
    <td width="50"><b><font size="2" face="Verdana">Abono</font></b></td>
    <td width="50">: <bean:write name="nroAbono"/></td>
    
    <td></td>
    
    
  </tr>  
  <tr>
    <td width="35"></td>
    <td width="50"><b><font size="2" face="Verdana">Monto</font></b></td>
    <td width="50">: <%=request.getAttribute("monto_formateado")%></td>
    <td></td>
  </tr> 
  <tr>
    <td width="35"></td>
    <td width="219"><b><font size="2" face="Verdana">GLOSA DE EXTORNO</font></b></td>
    <td width="112"></td>
    <td width="100"></td>
  </tr>
  <tr>
    <td width="35"></td>
    <td width="439" colspan="3">
      <p align="left"><font face="Verdana"><textarea disabled="true" rows="4" name="GLOSA" cols="48"><bean:write name="list1" property="glosa"/></textarea></font></td>
  </tr>
  <tr>
    <td width="35"></td>
    <td colspan="2">
      <b><font size="2" face="Verdana"><BR>TESORERO: &nbsp;&nbsp;&nbsp;&nbsp;</font><font color="blue" face="Verdana"><%--<bean:write name="list1" property="cuentaTesorero_id"/> - --%><bean:write name="list1" property="ape_pat"/> <bean:write name="list1" property="ape_mat"/> <bean:write name="list1" property="nombres"/></font></b></td>
    <td width="100"></td>
  </tr>
</table>

<div id="HOJA2" style="position:absolute; left:480px; top:55px; visibility: visible;">
<a href="javascript:Imprimir();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25"></a>
<br>
<a href="javascript:history.go(-1)"><img border="0" src="images/btn_regresa.gif" width="83" height="25"></a>
</div>
</form>
</body>
</html>