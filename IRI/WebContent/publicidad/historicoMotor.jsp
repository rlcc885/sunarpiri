<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head>
<TITLE>HISTORICO DE MOTOR</TITLE>
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<table class=grilla>
  <tr> 
    <th width="25%">
      <b>PLACA</b>
    </th>
    <th width="25%">
      <b>SECUENCIAL</b>
    </th>
    <th width="25%">
      <b>SERIE</b>
    </th>
    <th width="25%">
      <b>MOTOR</b>
    </th>
  </tr>
  <logic:present name="bmotor">
    <logic:iterate name="bmotor" id="item">
  <tr> 
    <td>&nbsp;<bean:write name="item" property="placa"/></td>
    <td>&nbsp;<bean:write name="item" property="secuencial"/></td>
    <td>&nbsp;<bean:write name="item" property="serie"/></td>
    <td>&nbsp;<bean:write name="item" property="motor"/></td>
  </tr>
    </logic:iterate>
  </logic:present>
  <logic:notPresent name="bmotor">
  <tr> 
    <td colspan="4">&nbsp;NO SE ENCONTRARON DATOS HISTORICOS.</td>
    
  </tr>
    
  </logic:notPresent>
  
</table>
<center>
<br>
<a href="javascript:window.close()">
  <img border=0 src="<%=request.getContextPath()%>/images/btn_regresa.gif">
</a>
</center>
</body>
</html>