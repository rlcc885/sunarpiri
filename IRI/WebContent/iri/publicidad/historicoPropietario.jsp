<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<TITLE>HISTORICO DE PROPIETARIOS</TITLE>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<table class=grilla>
  <tr> 
    <th width="25%">
      <b>NOMBRE DEL PROPIETARIO</b>
    </th>
    <th width="25%">
      <b>TIPO Y NUMERO DE DOCUMENTO</b>
    </th>
    <th width="25%">
      <b>DIRECCIONES</b>
    </th>
    <th width="25%">
      <b>FECHA DE INSCRIPCION</b>
    </th>
  </tr>
  <logic:notPresent name="bpropi">
  <tr> 
    <td colspan="4">&nbsp;NO SE ENCONTRARON PROPIETARIOS HISTORICOS.</td>
    
  </tr>
    
  </logic:notPresent>
  <logic:present name="bpropi">
    <logic:iterate name="bpropi" id="item">
  <tr> 
    <td>&nbsp;<bean:write name="item" property="nombre"/></td>
    <td>&nbsp;<bean:write name="item" property="documentos"/></td>
    <td>&nbsp;<bean:write name="item" property="direcciones"/></td>
    <td>&nbsp;<bean:write name="item" property="fechaInsc"/></td>
  </tr>
    </logic:iterate>
  </logic:present>
  
</table>
<center>
<br>
<a href="javascript:window.close()">
  <img border=0 src="<%=request.getContextPath()%>/images/btn_regresa.gif">
</a>
</center>
</body>
</html>
