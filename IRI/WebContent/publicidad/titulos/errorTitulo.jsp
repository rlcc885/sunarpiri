<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Titulo</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
</HEAD>
<BODY>

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
    <td ><bean:write name="events" property="otherMessage"/>
</td>
  </tr>

</table>
<br>
<table >
  <tr>
    <td width="591"><div align="center"><a href="javascript:history.back();"><img src="images/btn_regresa.gif" border=0></a></div></td>
  </tr>
</table>
</BODY>
</HTML>