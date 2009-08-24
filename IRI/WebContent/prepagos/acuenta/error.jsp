<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
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
	<logic:present name="events">  
	    <td ><logic:present name="events" property="niceMessage">
	    		<bean:write name="events" property="niceMessage"/>
			 </logic:present>
		<logic:present name="events" property="otherMessage">
		<br><bean:write name="events" property="otherMessage"/>
		</logic:present>
	<%--
		<br>COMPLETE MESSAGE: 
		<logic:present name="events" property="completeMessage">
		<bean:write name="events" property="completeMessage"/>
		</logic:present>
		<br>USER IDENTIFYING STRING: 
		<logic:present name="events" property="userIdentifyingString">
		<bean:write name="events" property="userIdentifyingString"/>
		</logic:present>
		<br>LEVEL PRINCIPAL: 
		<logic:present name="events" property="levelPrincipal">
		<bean:write name="events" property="levelPrincipal"/>
		</logic:present>
	--%>
	</td>
	</logic:present>
	  </tr>
	  <tr>
	  <td>Lo sentimos, su operación no pudo ser completada</td>
	  </tr>
	
	</table>
<br>
<table >
  <tr>
    <td width="591"><div align="center"><a href="/iri/IncrementarSaldo.do" onmouseover="javascript:mensaje_status('Incremento de Saldo');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="images/btn_regresa.gif" border=0></a></div></td>
  </tr>
</table>
<br>
<br>
</body>
</html>



