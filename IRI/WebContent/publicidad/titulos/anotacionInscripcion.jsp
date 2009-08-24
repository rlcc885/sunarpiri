<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ResourceBundle"%>
<%
	ResourceBundle recurso = ResourceBundle.getBundle("gob.pe.sunarp.extranet.publicidad.properties.Publicidad");
%>
<head>
	 <title>Formulario Solicitud Inscripci&oacute;n - Resumen</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="javascript/util.js"></script>
</head>
<script>
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
</script>
<BODY>
<form>
<table class="tablasinestilo">
		 <tr>
		   <td align="center">
   				<IMG src="<%=request.getContextPath()%>/images/logosunarp.gif" border="0">
    	   </td>
           <td align="center" style="black;background-color: D7D7D7;" ><strong><font size="3"><br>CONSTANCIA DE INSCRIPCION<br><br></font></strong></td>
                </tr>
</table>


<br>
<br>
<TABLE>
		<TR>
			<TD align="left" width="190">ZONA REGISTRAL:</TD>
			<TD align="left" width="369"><bean:write name="zonaRegistral"/></TD>
		</TR>
		<TR>
			<TD width="190" height="21" align="left">OFICINA REGISTRAL:</TD>
		    <TD align="left" width="369"><bean:write name="oficina"/></TD>
		</TR>
</TABLE>
<br>

<TABLE width="560" border="2" bordercolor="black">
	<TBODY>
		<TR>
			<TD width="244" nowrap="nowrap" height="26"><div align="center">
			<H2> TITULO Nº :</H2>
			</div>
			</TD>
			<TD width="327" nowrap="nowrap" height="26"><div align="center">
			<H2><%=request.getAttribute("anho") %>-<%=request.getAttribute("numTitulo") %></H2>
			</div>
			</TD>
			<td height="26"></td>
		</TR>
		<TR>
			<TD width="244" nowrap="nowrap" height="43"><div align="center">
			<H3>&nbsp;Fecha de Presentacion:</H3>
			</div>
			</TD>
			<TD width="327" nowrap="nowrap" height="43"><div align="center">
			<H3><%=request.getAttribute("fechaPresentacion") %></H3>
			</div>
			</TD>
			<td height="43"></td>
		</TR>

	</TBODY>
</TABLE>
<br>

<table width="560" >
<tr>
	<td colspan="3" align="left" class="textoSolicitud">Se deja Constancia que se ha registrado lo siguiente:<br>
		<br>
	</td>		
	<br>
</tr>
<TR>
	<TD class="textoSolicitud">ACTO</TD>
	<TD class="textoSolicitud">PARTIDA</TD>
	<TD class="textoSolicitud">ASIENTO</TD>
</TR>
<logic:present name="asientos">
 <logic:iterate id="lista" name="asientos">
<TR>
	 <TD class="textoSolicitud"><bean:write name="lista" property="descripcionActo"/></TD>
	 <TD class="textoSolicitud"><bean:write name="lista" property="numeroPartida"/></TD>
	 <TD class="textoSolicitud">A000<bean:write name="lista" property="secuenciaAsiento"/></TD>
</TR>
 </logic:iterate>
</logic:present>
<TR>
	<TD colspan="3"></TD>
</TR>
</TABLE><p align="justify" class="textoSolicitud">
<tr><td>Derechos S/.<bean:write name="monto"/>, con Recibo(s) Numero(s) <bean:write name="recibo"/> <bean:write name="lista" property="fechaIncripcion"/></td></tr>
</p>
<p><br></p>
<p></p>
<table width="595">
	<tr><td class="textoSolicitud">============= Datos del Registro Unico de Contribuyente - SUNAT ==============</td></tr>
	<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
	<tr></tr>
	<tr></tr>
	<tr><td class="textoSolicitud">RUC : <bean:write name="ruc"/></td></tr>
</table>
<br>
<br>

<br>
<table width="595">
	<tr><td class="textoSolicitud"><%=recurso.getString("msn.anotacion.inscripcion.mensaje1") %></td></tr>
</table>
<br>
<table class="tablasinestilo">
	<tr>	
  	<td width="50%" align="left">
  	<div id="HOJA2"> 
  	<a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
  	</div></td>
	<td width="50%" align="right">
	<div id="HOJA3">	
	<a href="javascript:window.close();" onmouseover="javascript:mensaje_status('Cerrar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</div></td>
	</tr>
</table>

</form>
</BODY>
		
  	    
