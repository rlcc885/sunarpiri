<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page
	import="gob.pe.sunarp.extranet.solicitud.denominacion.beans.Denominacion"%>
<head><link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<script>
function Regresa(){
	history.go(-1)
}
</script>
<body>
<table class=titulo>
  <tr> 
    <td colspan="3"><font color=black>SERVICIOS &gt;&gt; Consulta de Estado de T&iacute;tulos &gt;&gt; </font>Detalle del T&iacute;tulo</td>
  </tr>
</table>
<br>
<logic:equal name="muestraResultados" value="1">
<% 
	Denominacion deno = (Denominacion)request.getAttribute("denominacion");
	String url= "/iri/DenominacionPublico.do?state=consultaResumen&nuHoja="+ deno.getNumeroHoja() +"&anio=" +deno.getAnio()+ "&ofic="+ deno.getCoOficRegiPres()+ "&coRegi="+deno.getCoRegiPres();
%>
<table class=titulo2 cellspacing=0 >
        <tr> 
          <td width="20%" ><b>N&uacute;mero T&iacute;tulo :</b></td>
          <td width="25%" ><bean:write name="denominacion" property="numeroTitulo"/> &nbsp;&nbsp;<font color="#800000"></font></td>
          <td width="10%" align="right" ><b>A&ntilde;o :</b><bean:write name="denominacion" property="anioTitu"/></td>
          <td width="20%" ></td>
          <td width="25%" align="center" ><b>Oficina :</b> <bean:write name="denominacion" property="descOficina"/></td> 
        </tr>
        <br>
        <tr> 
          <td width="20%" ><b>N&uacute;mero  Hoja :</b></td>
          <td width="25%" ><bean:write name="denominacion" property="numeroHoja"/> &nbsp;&nbsp;<font color="#800000"> <strong><a href="<%=url%>" onClick="window.open(url, opcLeft) ;return false;">[Ver Detalle]</a></strong></font></td>
          <td width="10%" align="right" ><b>A&ntilde;o :</b><bean:write name="denominacion" property="anio"/></td>
          <td width="20%" ></td>
          <td width="25%" align="center" ></td> 
        </tr>
</table>
<br>
<table class=formulario cellspacing=0 >
        <tr> 
          <th width="25%" >Tipo Registro</th>
          <td width="55%" ><bean:write name="denominacion" property="descLibro"/></td>
        </tr>
</table>
<br>
<table class=formulario cellspacing=0 >        
        <tr> 
          <th width="25%" >Actos Registrales</th>	
          <td width="55%" >
	    	<font color="#800000">          
	    	<bean:write name="denominacion" property="descActo"/>
	    	<br>
					</font>
          </td>
        </tr>
        <tr> 
          <th width="25%" >Resultado de la calificaci&oacute;n </th>
          <td width="55%" > <bean:write name="denominacion" property="estado"/></td>       		
           <td></td>
        </tr>
        <tr> 
          <th width="25%" >Mensaje</th>
          <td width="55%" ><bean:write name="denominacion" property="mensaje"/></td>
        </tr>
        <tr> 
          <th width="25%" >Fecha Hora de Presentaci&oacute;n</th>
          <td width="55%" ><bean:write name="denominacion" property="fechaPresTitu"/></td>
        </tr>
</table>
<br>
      <table class=cabeceraformulario cellspacing=0 >
        <tr> 
          <th width="25%" >Presentante: </th>     
        </tr>
	</table>
      <table class=formulario cellspacing=0 >
        <tr> 
          <td width="55%" ><bean:write name="presentante" property="nombre"/> <bean:write name="presentante" property="apePaterno"/> <bean:write name="presentante" property="apeMaterno"/></td>
          <td >Documento : <bean:write name="presentante" property="descDocu"/> <bean:write name="presentante" property="numDocu"/> </td>
        </tr>
      </table>
<br> 
   <table class=cabeceraformulario cellspacing=0 > 
        <tr> 
          <td width="205%" ><p align="left">Participantes</p></tr>
        <tr> 
   </table>
      <table class=formulario cellspacing=0 >
              <tr> 
                <td width="25%"><strong>Tipo</strong></td>
                <td width="75%"><strong>NOMBRES/RAZ&Oacute;N SOCIAL</strong></td>
              </tr>
              <tr> 
                <logic:iterate name="participantesNaturales" id="nat">
	                <tr>
		                <td width="25%"><bean:write name="nat" property="tipoParticipante"/> - </td>
		                <td width="75%"><bean:write name="nat" property="apePaterno"/> <bean:write name="nat" property="apeMaterno"/> <bean:write name="nat" property="nombre"/>  </td>
	                </tr>
                </logic:iterate>
                <logic:iterate name="participantesJuridicos" id="jur">
	                <tr>
		                <td width="25%"><bean:write name="jur" property="tipoParticipante"/> - </td>
		                <td width="75%"><bean:write name="jur" property="razonSocial"/>   </td>
	                </tr>
                </logic:iterate>
              </tr>
      </table>
      <br>
      <table class=formulario cellspacing=0 >
        <tr> 
          <th width="25%" >Fecha de Impresi&oacute;n</th>
          <td width="55%" ></td>
        </tr>
      </table>      
   <table class=tablasinestilo>
   	<tr> 
    	<td align=right>&nbsp;</td>
	</tr>   
   	<tr> 
    	<td align=right><a href="javascript:window.print();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25" hspace="4"></a><a href="javascript:Regresa()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" width="83" height="25" hspace="4" border="0"></a></td>
	</tr>
	</table>
<br>
</logic:equal>
	<logic:notEqual name="muestraResultados" value="1">
		<%=request.getAttribute("mensaje")%>
		<table>
	<td>
		<a href="javascript:Regresa()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" width="83" height="25" hspace="4" border="0"></a>
	</td>
	</table>
	</logic:notEqual>
</body>
</html>