<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
<HEAD>
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
	<script language="JavaScript" src="javascript/util.js"></script>
	<title>Impresion del Resumem Ingreso Diario</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
	
<script language="JavaScript">

	function Regresar(){
	
		document.form1.method="POST";
		document.form1.action="/iri/ConsolidadoExtornoAdm.do?state=muestra";
		document.form1.submit();
	}

</script>
	
	
</HEAD>
<BODY>
<FORM name="form1" method="post" class="formulario">
<TABLE border="0">
	<TBODY>
		<TR>
			<TD><IMG border="0" src="images/orlclogo.gif"
				width="253" height="75"></TD>
			<TD width="234"></TD>
			<TD align="left" width="47"><div align="left"><STRONG>Fecha</STRONG><br><STRONG>Hora</STRONG></div></TD>
			<TD align="left" width="54"><bean:write name="fechaActual"/><br><bean:write name="horaActual"/></TD>
		</TR>
		<TR>
			<TD height="22">&nbsp;</TD>
			<TD height="22" width="234"></TD>
			<TD height="22" width="47"></TD>
			<TD height="22" width="54"></TD>
		</TR>
	</TBODY>
</TABLE>
<table class=titulo>
	<tr>
		<td>
		<div align="center">
		<H2><FONT color="black">CONSOLIDADO GENERAL DE EXTORNOS</FONT></H2>
		<H3><FONT color="black">(Del <bean:write name="fechaInicio"/> al <bean:write name="fechaFin"/>)</FONT></H3>
		</div>
		</td>
	</tr>
</table>
<br>
<TABLE width="100%" cellspacing="4">
	<tr>
		<td width="5%"><div align="center"><strong>Tesorero</strong></div></td>
		<td width="5%"><div align="center"><strong>Cajero</strong></div></td>
		<td width="10%"><div align="center"><strong>Nro. Abono</strong></div></td>
		<td width="30%"><div align="center"><strong>Concepto</strong></div></td>
		<td width="30%"><div align="center"><strong>Motivo</strong></div></td>
		<td width="20%"><div align="right"><strong>Monto</strong></div></td>
	</tr>

		<logic:iterate name="listaConsulta" id="consulta">

			<logic:iterate name="consulta" property="listaExtornos" id="extorno">
				<TR>
					<TD width="5%"><div align="center"><bean:write name="extorno" property="usuarioTesorero"/></div></TD>
					<TD width="5%"><div align="center"><bean:write name="extorno" property="operador"/></div></TD>
					<TD width="10%"><div align="center"><bean:write name="extorno" property="nroAbono"/></div></TD>
					<TD width="30%"><div align="center"><bean:write name="extorno" property="concepto"/></div></TD>
					<TD width="35%"><div align="center"><bean:write name="extorno" property="motivo"/></div></TD>
					<TD width="15%"><div align="right"><bean:write name="extorno" property="monto"/></div></TD>
				</TR>
			</logic:iterate>

			<TR>
				<TD width="5%"></TD>
				<TD width="5%"></TD>
				<TD width="10%"></TD>
				<TD width="30%"></TD>
				<TD width="35%" align="right"><STRONG>SUBTOTAL S./: </STRONG></TD>
				<TD width="15%"><STRONG><bean:write name="consulta" property="subTotalFormat"/></STRONG></TD>
			</TR>

		</logic:iterate>

			<TR>
				<TD width="5%"></TD>
				<TD width="5%"></TD>
				<TD width="10%"></TD>
				<TD width="30%"></TD>
				<TD width="35%" align="right"><STRONG>TOTAL S./:</STRONG></TD>
				<TD width="15%"><STRONG> <bean:write name="total" /></STRONG></TD>
			</TR>
	

<br>


	<!--<tr>
		<td ></td>
		<td ></td>
		<td ><DIV align="center"><STRONG>Total Monto</STRONG></DIV></td>
		<td ><div align="right"><strong><bean:write name="total"/></strong></div></td>
	</tr>-->


	
</TABLE>
</FORM>
<br>
<table width="605">
<tr>
		<td width="40%"></td>
		<td width="40%"></td>
		<td align="right" width="10%"><A href="javascript:Regresar();"
			onmouseover="javascript:mensaje_status('Regresar');return true;"
			onmouseout="javascript:mensaje_status(' ');return true;"><IMG border="0"
			src="images/btn_regresa.gif" width="83" height="25"></A></td>

		<td align="left" width="10%"><A href="javascript:window.print();"
		    onmouseover="javascript:mensaje_status('Imprimir');return true;"
			onmouseout="javascript:mensaje_status(' ');return true;"><IMG border="0"
			src="images/btn_print.gif" width="83" height="25"></A>
        </td>
		
	</tr>
</table>
</BODY>
</HTML>
