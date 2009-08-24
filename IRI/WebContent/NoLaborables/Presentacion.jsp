<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<html>
<head>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
	<LINK REL="stylesheet" type="text/css" href="styles/global.css">

</head>
<body>
<script type="text/javascript">
function IrPaginaPrincipal()
{
	history.go(-2)
}
</script>
<table cellspacing=0 class=titulo>
  <br>
  <tr>
	<td>
		<FONT COLOR="black">CONSULTA <font size="1">&gt;&gt;</font></FONT><font color="900000"> Dias no laborables</FONT>
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
  
<table>	
 		<tr>	
		<td colspan="5">
			<table class="grilla" >
				<tr>
		      		<th align="center" height="19" width="24%"><div align="center">DIA</div></th>
		      		<th align="center" height="19" width="38%"><div align="center">DESCRIPCION</div></th>
		      		<th align="center" height="19" width="37%"><div align="center">DOC. SUSTENTO</div></th>
		      		<th align="center" height="19" width="37%"><div align="center">ESTADO</div></th>
		      		<!--  <th align="center" height="19" width="37%"><div align="center">TIPO</div></th>-->
		      	</tr>
				<logic:iterate name="listaDias" id="dias">
					<tr>
						<td width="127"><div align="center"><bean:write name="dias" property="dia"/></div> </td>
						<td width="193"><div align="center"><bean:write name="dias" property="descripcion"/></div></td>
						<td width="193"><div align="center"><bean:write name="dias" property="docuSustento"/></div></td>
						<td width="193"><div align="center"><bean:write name="dias" property="estado"/></div></td>
						<!--  <td width="193"><div align="center"></div></td>-->
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

<input type="hidden" name="hid1" value=""/>
</table>
</table>
</td>
</tr>
</table>
</form>
</body>

</html>


