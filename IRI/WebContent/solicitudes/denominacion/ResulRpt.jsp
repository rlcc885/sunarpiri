<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<html>
<head>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
	<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">

</head>
<body>
<script type="text/javascript">
function IrPaginaPrincipal()
{
	history.go(-1)
}
</script>
<table cellspacing=0 class="titulo">
  <br>
  <tr>
	<td>
		<FONT COLOR="black">REPORTE <font size="1">&gt;&gt;</font></FONT><font color="900000"> Hojas Presentadas / Trabajadas - Reserva de Denominacion</FONT>
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
<logic:equal name="tipoReporte" value="01">
<table>	
 		<tr>	
		<td colspan="8">
			<table class="grilla" >
				<tr>
		      		<th align="center" height="19" width="24%"><div align="center">AÑO</div></th>
				      		<th align="center" height="19" width="38%"><div align="center">NUMERO</div></th>
				      		<th align="center" height="19" width="37%"><div align="center">SERVICIO</div></th>
				      		<th align="center" height="19" width="37%"><div align="center">OFICINA</div></th>
				      		<th align="center" height="19" width="37%"><div align="center">PRESENTANTE</div></th>
				      		<th align="center" height="19" width="37%"><div align="center">USUARIO</div></th>
				      		<th align="center" height="19" width="37%"><div align="center">MAIL</div></th>
				      		<th align="center" height="19" width="37%"><div align="center">FECHA</div></th>
		      	</tr>
				<logic:iterate name="arrHojas" id="hojas">
					<tr>
						<td width="127"><div align="center"><bean:write name="hojas" property="aniooHoja"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="numHoja"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="servicio"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="oficina"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="presentante"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="usuario"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="mail"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="fecha"/></div></td>

				</tr>	
				</logic:iterate>	
				
</table>    
<br>
<table width="100%">
	<tr align="center">
		<td colspan="3" align="center"> 
		<A href="javascript:IrPaginaPrincipal();" onmouseover="javascript:mensaje_status('Aperturar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		<IMG border="0" src="<%=request.getContextPath()%>/images/btn_continuar.gif" ></A></td>
	</tr>	


<!-- /logic:equal-->

<logic:equal name="tipoReporte" value="02">
<table>	
 		<tr>	
		<td colspan="8">
			<table class="grilla" >
				<tr>
		      		   		<th align="center" height="19" width="24%"><div align="center">AÑO</div></th>
		      				<th align="center" height="19" width="38%"><div align="center">NUMERO</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">OFICINA</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">AÑO TITULO</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">NUMERO TITULO</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">SERVICIO</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">NS DETALLE</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">ESTADO</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">FECHA</div></th>
		      				<th align="center" height="19" width="37%"><div align="center">PLAZO</div></th>
		      	</tr>
				<logic:iterate name="arrHojas" id="hojas">
					<tr>
						<td width="127"><div align="center"><bean:write name="hojas" property="aniooHoja"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="numHoja"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="oficina"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="anioTitu"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="numTitu"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="servicio"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="nsDetalle"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="estado"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="fecha"/></div></td>
							<td width="200"><div align="center"><bean:write name="hojas" property="plazo"/></div></td>
				</tr>	
				</logic:iterate>	
</table>    
<br>
<table width="100%">
	<tr align="center">
		<td colspan="3" align="center"> 
		<A href="javascript:IrPaginaPrincipal();" onmouseover="javascript:mensaje_status('Aperturar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		<IMG border="0" src="<%=request.getContextPath()%>/images/btn_continuar.gif" ></A></td>
	</tr>	
<table>
</table>
</table>
</td>
</tr>
</table>
</logic:equal>
</table>
</table>

</logic:equal>
</form>
</body>
</html>