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

	function regresar(){
		document.form1.method="POST";
		document.form1.action="/iri/ResumenIngresoTesorero.do?state=muestra";
		document.form1.submit();
	}

	function imprimir(){
		botones.style.visibility="hidden";
		window.print();
		botones.style.visibility="visible";
	}

</script>
	
	
</HEAD>
<BODY>
<FORM name="form1">
<TABLE border="0">
	<TBODY>
		<TR>
			<TD width="222"><IMG border="0" src="images/orlclogo.gif"
				width="253" height="75"></TD>
			<TD width="172"></TD>
			<TD width="40"><div align="left"><STRONG>Fecha</STRONG><br><STRONG>Hora</STRONG></div></TD>
			<TD width="140" ><bean:write name="fechaActual"/><br><bean:write name="horaActual"/></TD>
		</TR>
		<TR>
			<TD width="222" height="22">&nbsp;</TD>
			<TD width="172" height="22"></TD>
			<TD width="40" height="22"></TD>
			<TD width="140" height="22"></TD>
		</TR>
	</TBODY>
</TABLE>
<table class=titulo>
	<tr>
		<td>
		<div align="center">
		<H2><FONT color="black">RESUMEN DE INGRESO</FONT></H2>
		<H3><FONT color="black">(Del <bean:write name="fechaInicio"/> al <bean:write name="fechaFin"/>)</FONT></H3>
		</div>
		</td>
	</tr>
	</table>
<br>
<TABLE border="0">
	<TBODY>
		<TR>
			<TD colspan="3"><STRONG><bean:write name="zonaRegistral"/></STRONG></TD>
			
		</TR>
		<TR>
			<TD colspan="3">&nbsp;</TD>
		</TR>

		<logic:iterate name="listaResumen" id="resumen">
		
			<TR>
				<TD width="205"><STRONG>&nbsp;&nbsp;ZONAL</STRONG></TD>
				<TD width="180"><STRONG><bean:write name="resumen" property="descEdificio"/></STRONG></TD>
				<TD width="214"></TD>
			</TR>
	

			<logic:iterate name="resumen" property="listaCajas" id="caja">
			
				<TR>
					<TD width="205">&nbsp;&nbsp;&nbsp;&nbsp;CAJA</TD>
					<TD width="180"><bean:write name="caja" property="descCaja"/></TD>
					<TD width="214" align="right"><bean:write name="caja" property="totalIngresoCajaAsString"/></TD>
				</TR>
	
			
			</logic:iterate>

	
			<TR>
				<TD width="205"><STRONG>&nbsp;&nbsp;TOTAL</STRONG></TD>
				<TD width="180"></TD>
				<TD width="214" align="right"><STRONG>S./ <bean:write name="resumen" property="totalIngresoAsString"/></STRONG></TD>
			</TR>

			<TR>
				<TD colspan="3" height="16"></TD>
			</TR>

		
		</logic:iterate>


		<TR>
			<TD width="205" height="19"><STRONG></STRONG></TD>
			<TD width="180" height="19"></TD>
			<TD width="214" height="19"></TD>
		</TR>
		
		
		<TR>
			<TD width="205" height="28"><STRONG>TOTAL</STRONG></TD>
			<TD width="180" height="28"></TD>
			<TD width="214" height="28" align="right"><STRONG>S./ <bean:write name="totalGeneral"/></STRONG></TD>
		</TR>
		<tr>
			<td colspan="3" height="30"></td>
		</tr>
		<tr>
			<td colspan="2" height="36"></td>
			<td width="214" height="36">
				<DIV id="botones">
		  			<A href="javascript:regresar();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		  				<IMG border="0" src="images/btn_regresa.gif" width="83" height="25"></A>
					<A href="javascript:imprimir();">
						<IMG border="0" src="images/btn_print.gif" width="83" height="25"></A>
				</DIV> 						
			</td>
		</tr>
	</TBODY>
</TABLE>
</FORM>
</BODY>
</HTML>
