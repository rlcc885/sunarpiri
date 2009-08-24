<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>


<link rel="stylesheet" href="styles/global.css">

<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>

<script language="javascript">
function Valida()
{ 
	if (document.form1.pwCajero.value.length==0)
		{
			alert("Ingrese contraseña de Cajero");
			document.form1.pwCajero.focus();
			return;
		}
	if (document.form1.pwTesorero.value.length==0)
		{
			alert("Ingrese contraseña de Tesorero");
			document.form1.pwTesorero.focus();
			return;
		}
	if (document.form1.glosa.value.length==0)
		{
			alert("Ingrese una glosa");
			document.form1.glosa.focus();
			return;
		}					
	document.form1.method="POST";
	document.form1.action="/iri/DiarioRecauda.do?state=extornaAbono";
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
<input type="hidden" name="monto" value="<bean:write name="monto"/>">
<table border="0" width="100%" class="titulo">
	<tr>
		<td>
		<font color=black>CAJA &gt;&gt; Movimiento diario de recaudaci&oacute;n >> </font> Extorno de Saldo Prepagado </td>
	</tr>
</table><br>


<table class=formulario width="600">
  <tr>
    <td width="35">
      <p align="right"><font face="Verdana">&nbsp;&nbsp;</font></p>
    </td>
    <td width="219"></td>
    <td width="112">
    </td>
    <td width="100">
      <p align="right"><font face="Verdana">&nbsp;</font>
      </p>
    </td>
  </tr>
  <tr>
    <td width="35"><font face="Verdana">&nbsp;</font></td>
    <td width="219"></td>
    <td width="112">
      <p align="right"><font face="Verdana"><input type="hidden" size="5" name="monto" value="0"></font></p>
    </td>
  </tr>
  <tr>
    <td width="35"></td>
    <td width="50"><b><font size="2" face="Verdana">Abono</font></b></td>
    <td width="50">: <bean:write name="nroAbono"/></td>
    
    <td>
    <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25"></a>
    </td>
    
    
  </tr>  
  <tr>
    <td width="35"></td>
    <td width="50"><b><font size="2" face="Verdana">Monto</font></b></td>
    <td width="50">: <%=request.getAttribute("monto_formateado")%></td>
    <td>
    <a href="javascript:history.back();"><img src="<%=request.getContextPath()%>/images/btn_cancelar.gif" width="83" height="24"></a>
    </td>
  </tr>  
  <tr>
    <td width="35"></td>
    <td width="219"><b><font size="2" face="Verdana">GLOSA DE EXTORNO</font></b></td>
    <td width="112"></td>
    <td width="100">
      </td>
  </tr>
  <tr>
    <td width="35"></td>
    <td width="439" colspan="3">
      <p align="left"><font face="Verdana"><textarea rows="4" name="glosa" cols="48"></textarea></font></td>
  </tr>
  <tr>
    <td width="35"></td>
    <td width="219">
      <b><font size="2" face="Verdana">CONTRASE&Ntilde;A CAJERO </font></b></td>
    <td width="112">
    <p align="right"><font face="Verdana"><input type="password" size="10" name="pwCajero" onblur="sololet(this)"></font>
    </td>
    <td>
    </td>
  </tr>
  <tr>
    <td width="35"></td>
    <td width="219">
      <b><font size="2" face="Verdana"><BR>TESORERO</font></b></td>
    <td width="112">
    <p align="right"><font face="Verdana">
    <select size="1" name="idTesorero" lenght=10>
    	<logic:iterate name="listas" id="item" scope="request">
    		<OPTION VALUE="<bean:write name="item" property="usr_id"/>"><bean:write name="item" property="usr_id"/></OPTION>
    	</logic:iterate>
	</SELECT></font>
    </td>
  </tr>  
  <tr>
    <td width="35"></td>
    <td width="219">
      <b><font size="2" face="Verdana"><BR>CONTRASE&Ntilde;A TESORERO</font></b></td>
    <td width="112">
    <p align="right"><font face="Verdana"><input type="password" size="10" name="pwTesorero" onblur="sololet(this)"></font>
    </td>
    <td width="100"><a href="javascript:Valida();"><img src="<%=request.getContextPath()%>/images/btn_aplicar.gif" width="84" height="25"></a>
    </td>
  </tr>
  <tr>
    <td width="35"></td>
    <td width="219">
    </td>
    <td width="112">
    </td>
    <td width="100">
    </td>
  </tr>
</table>
</form>
</body>
</html>