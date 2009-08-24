<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<SCRIPT language="JavaScript">
function Salir()
{  
  document.form1.method = "POST";
  document.form1.action="/iri/Salir.do?state=salir";
  document.form1.submit();
}
</script>


<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link href="styles/global.css" rel="stylesheet" type="text/css">
</head>


<body onload="setTimeout('Salir(2)',3000)">
<form name="form1" class=formulario target="_top">
<br>
<table class="titulo">
  <tr> 
    <td>&nbsp;</td>
  </tr>
</table>
<br>
<br>
	<table class="formulario" border="0">
	  
	  <tr>
	
	    <td >
	</td>
	
	  </tr>
	  <tr>
	  <td>Por su seguridad su cuenta est&aacute; siendo bloqueada.</td>
	  </tr>
	
	</table>
<br>

<br>
<br>
</form>
</body>
</html>