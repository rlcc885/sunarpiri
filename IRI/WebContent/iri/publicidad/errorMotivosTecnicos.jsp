<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<SCRIPT language="JavaScript">
function doCancel()
{
  if((window.opener)&&(!window.opener.closed)){
	close();
  } else {
	history.back(1);
  }
}
</script>


<title>Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
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
    <td>
Lo sentimos, en este momento no podemos mostrarle la partida solicitada. Por favor intente mas tarde. <br>
Su saldo no ha sido descontado.<br>
Si persiste el error ac&eacute;rquese a la oficina registral mas cercana para obtener la publicidad solicitada.
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
    <td width="591">
    	<div align="center">
    		<input type="button" class="formbutton" onclick="javascript:doCancel()" value="Regresar"/>
    	</div>
    </td>
  </tr>
</table>
<br>
<br>
</body>
</html>



