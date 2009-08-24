<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<html>
<head><link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css"><title></title>
<script language="javascript">
function Exporta(oficina){
	document.form1.method="POST";
	document.form1.action="/iri/VerReporteIngresos.do?state=exportarAbono&oficina=" + oficina;
	document.form1.submit();
	return true;
}
 
</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<br>
<table class="titulo">
	<tr>
		<td><FONT color=black>ADMINISTRACION EXTRANET &gt;&gt; Reportes &gt;&gt; </FONT>&gt;Detalle abono Usuarios   </td>
	</tr>
</table>
<br>

<form name="form1">
	<input type="hidden" name="diainicio" value="<%=request.getAttribute("selectedIDay")%>">
	<input type="hidden" name="mesinicio" value="<%=request.getAttribute("selectedIMonth")%>">
	<input type="hidden" name="anoinicio" value="<%=request.getAttribute("selectedIYear")%>">
	<input type="hidden" name="diafin" value="<%=request.getAttribute("selectedFDay")%>">
	<input type="hidden" name="mesfin" value="<%=request.getAttribute("selectedFMonth")%>">
	<input type="hidden" name="anofin" value="<%=request.getAttribute("selectedFYear")%>">
		
	<input type="hidden" name="entidad" value="<bean:write name="entidad"/>">

<table class="titulo">
	<tr>
      <td><font color="black">Detalle de Abonos Usuarios</font> 
      	  <input type="text" name="tipoEntidad" size=14 disabled=true value="<logic:equal name="tipo" value="I">INDIVIDUAL</logic:equal><logic:equal name="tipo" value="O">ORGANIZACION</logic:equal>">
        <font color="black" size="1">del</font>
        <font size="1" color="#993300"> <bean:write name="fecini"/></font> 
        <font color="black">al</font> 
        <font color="#993300"> <bean:write name="fecfin"/></font>
        <font color="black">Oficina :</font> 
        <input type="text" name="oficinas" size=15 disabled=true value="<bean:write name="nom_oficina"/>">
      </td>
    </tr>
</table>
<br>
  <%boolean color = true;%>
  <table class="grilla">
    <tr> 
      <th width="8%" align="center" height="12">USUARIO</th>
      <th width="27%" align="center" height="12"><logic:equal name="tipo" value="I">APELLIDOS Y NOMBRES</logic:equal><logic:equal name="tipo" value="O">RAZON SOCIAL</logic:equal></th>
      <th width="13%" align="center" height="12">IMPORTE</th>
      <logic:equal name="oficina" value="00|00">
      <th width="13%" align="center" height="12">NRO. ORDEN</th>
      </logic:equal>
    </tr>
	<logic:iterate name="lista" id="item" scope="request">
	    <%if(color){%>
	    	<tr class="grilla2"> 
	    <%}else{%>
	    	<tr>
	    <%}%> 
	     <%color = !color;%>

	      <!--<td width="8%" align="center" height="11"><a href="DETALLEMOVIMIENTOS.html" target="_self"><bean:write name="item" property="userId"/></a></td>-->
	      <td width="8%" align="center" height="11"><bean:write name="item" property="userId"/></td>
	      <td width="27%" align="center" height="11"><bean:write name="item" property="entidad"/></td>
	      <td width="13%" align="center" height="11"><bean:write name="item" property="monto"/></td>
          <logic:equal name="oficina" value="00|00">
	      <td width="13%" align="center" height="11"><bean:write name="item" property="pagoLineaId"/></td>
	      </logic:equal>
	    </tr>
    </logic:iterate>    
    <tr> 
      <th width="8%" align="center" height="12" bgcolor="#FFFFFF"><b>TOTAL</b></th>
      <td width="27%" align="center" height="24" bgcolor="#FFFFFF"></td>
      <td width="13%" align="center" height="24" bgcolor="#FFFFFF"> <bean:write name="totalgeneral"/> </td>
      <logic:equal name="oficina" value="00|00">
      <td width="13%" align="center" height="24" bgcolor="#FFFFFF">&nbsp;</td>
      </logic:equal>
    </tr>
    <tr> 
      <td height="24" colspan="4" align="center" bgcolor="#FFFFFF">
      	<a href="javascript:history.back(1);"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" border=0></a> 
        <a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" border=0></a> 
      
      </td>
    </tr>
  </table>
</form>
</body>
</html>