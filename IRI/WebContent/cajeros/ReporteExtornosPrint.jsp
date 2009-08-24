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
		document.form1.action="/iri/ConsolidadoExtorno.do?state=muestra";
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
			<TD width="237"></TD>
			<TD align="left" width="41"><div align="left"><STRONG>&nbsp;Fecha</STRONG><br><STRONG>&nbsp;Hora</STRONG></div></TD>
			<TD width="57"><bean:write name="fechaActual"/><br><bean:write name="horaActual"/></TD>
		</TR>
		<TR>
			<TD height="22">&nbsp;</TD>
			<TD height="22" width="237"></TD>
			<TD height="22" width="41"></TD>
			<TD height="22" width="57"></TD>
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
<TABLE width="100%" >
	<tr>
		<td width="10%"><div align="center"><strong>Cajero</strong></div></td>
		<td width="10%"><div align="center"><strong>Nro. Abono</strong></div></td>
		<td width="30%"><div align="center"><strong>Concepto</strong></div></td>
		<td width="30%"><div align="center"><strong>Motivo</strong></div></td>
		<td width="20%"><div align="right"><strong>Monto</strong></div></td>
	</tr>


		<% 
				java.util.ArrayList listadoReporte = null;
				gob.pe.sunarp.extranet.caja.bean.ConsolidadoExtornoBean bean = null;
				
				if (request.getAttribute("listaConsulta")!=null)
										listadoReporte = (java.util.ArrayList)request.getAttribute("listaConsulta");
				int size = 0;
				if(listadoReporte!=null){
				size = listadoReporte.size();
					
					if(size>0){
						 for (int i=0; i<size; i++) {
						 	
						 	bean = (gob.pe.sunarp.extranet.caja.bean.ConsolidadoExtornoBean)listadoReporte.get(i);
		%>
	<tr>
		<td width="10%"><div align="center"><%=bean.getOperador() %></div></td>
		<td width="10%"><div align="center"><%=bean.getNroAbono() %></div></td>
		<td width="30%"><div align="left"><%=bean.getConcepto()%></div></td>
		<td width="30%"><div align="left"><%=bean.getMotivo() %></div></td>
		<td width="20%"><div align="right"><%=bean.getMonto() %></div></td>
	</tr>
	<%
										}
							}
					}
				%>

<br>


	<tr>
		<td ></td>
		<td ></td>
		<td ></td>
		<td ><DIV align="center"><STRONG>Total Monto S./</STRONG></DIV></td>
		<td ><div align="right"><strong><%=request.getAttribute("totalMonto") %></strong></div></td>
	</tr>


	
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
