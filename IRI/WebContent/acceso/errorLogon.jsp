<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<SCRIPT language="JavaScript">
function doCancel()
{
	history.back(1);
}
</script>


<title>Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link href="styles/global.css" rel="stylesheet" type="text/css">
</head>

<body>
<br>
<table class="titulo">
  <tr> 
    <td>&nbsp;</td>
  </tr>
</table>
<br>
<br>
<table class="formulario">
  <tr> 
    <td ><div align="left"><strong><font size="3">ERROR</font> </strong></div></td>
  </tr>
  <tr>
    <td ><bean:write name="errorLogon" scope="request" />

<!------------
	<br>OTHER MESSAGE: <bean:write name="events" property="otherMessage"/>
	<br>COMPLETE MESSAGE: <bean:write name="events" property="completeMessage"/>
	<br>USER IDENTIFYING STRING: <bean:write name="events" property="userIdentifyingString"/>
	<br>LEVEL PRINCIPAL: <bean:write name="events" property="levelPrincipal"/>
------------->

</td>
  </tr>

</table>
<br>
<table >
  <tr>
    <td width="591"><div align="center">
    
	  <input type="button" class="formbutton" value="Regresar" onclick="javascript:history.back();"/>
    </div></td>
  </tr>
</table>
<br>
<br>
</body>
</html>



