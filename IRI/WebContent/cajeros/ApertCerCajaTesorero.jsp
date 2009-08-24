<%@ page contentType="text/html;charset=ISO-8859-1" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.administracion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.solicitud.inscripcion.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.caja.*" %>

<html>
<head>
<link href="<%=request.getContextPath()%>/styles/global.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
</head>
<body>
<script>

function Aperturar(str){
	if (document.frm1.cboTipoMoneda.value=='00') {
			alert("Seleccione la moneda del Tipo de Cambio");
			document.frm1.cboTipoMoneda.focus();
			return;
		}
	
	if (esVacio(document.frm1.txtTipoCambio.value))
	{
		alert("Ingrese el Tipo de Cambio");
		document.frm1.txtTipoCambio.focus();
		return
	}

	if (!esDecimal(document.frm1.txtTipoCambio.value))
	{
		alert("Debe ingresar un valor numérico en el Tipo de Cambio");					
		document.frm1.txtTipoCambio.focus();
		return;
	}	

	var p = confirm("¿Esta seguro que desea Aperturar las cajas?");
	if (p == true){
		document.frm1.hid1.value= str;
		document.frm1.action="/iri/Caja.do?state=aperturarCajas";
		document.frm1.submit();
	   }
}
function Cerrar(str){
	document.frm1.hid1.value= str;
	document.frm1.action="/iri/Caja.do?state=cierreInicio";
	document.frm1.submit();
}
</script>
<table cellspacing=0 class=titulo>
  <br>
  <tr>
	<td>
		<FONT COLOR="black">TESORERIA <font size="1">&gt;&gt;</font></FONT><font color="900000">Aperturar / Cerrar Cajas</FONT>
	</td>
  </tr>
</table>
<br>
<form name="frm1" method="POST" class="formulario">
  
  <table class=tablasinestilo>
    <tr>
        <th colspan=5></th>
    </tr>
    <tr>
    	<td width="5">&nbsp;</td>
    	<td width="127">
    		<strong>FECHA</strong></td>
      	<%
      	if (request.getAttribute("fecha")!=null)
      	%>
      	<td width="409"><%= request.getAttribute("fecha")%></td>
        
	   
    </tr>
    
    <tr>
        <td width="5">&nbsp;</td>
        <td width="127">
          <strong>ZONA REGISTRAL <font color="900000"></font></strong>
        </td>
        <% 
        if (request.getAttribute("zonaRegistral")!=null)
        
        %>
        <td width="409"><%= request.getAttribute("zonaRegistral") %></td>
        
    </tr>
  	<tr>
    	<td colspan="5">&nbsp;</td>
  	</tr>
	<tr>
		<th colspan="5"></th>
	</tr>
    <tr>
        <td colspan="5">
        
           <table>
			    <tr>
			      <td width="5">&nbsp;</td>
				  <td width="133"><STRONG>MONEDA</STRONG></td>


				<logic:present name="moneda">
				<td width="200"><SELECT size="1" name="cboTipoMoneda" disabled="disabled"
						style="width: 120" >
						<logic:equal name="moneda" value="00">
							<OPTION value="00" selected="selected"></OPTION>
						</logic:equal>
						<logic:notEqual name="moneda" value="00">
							<OPTION value="00"></OPTION>
						</logic:notEqual>
						<logic:equal name="moneda" value="2">
							<OPTION value="2" selected="selected">DOLARES</OPTION>
						</logic:equal>
						<logic:notEqual name="moneda" value="2">
							<OPTION value="2">DOLARES</OPTION>
						</logic:notEqual>
					</SELECT></td>
				</logic:present>
				<logic:notPresent name="moneda">
				<td width="409"><SELECT size="1" name="cboTipoMoneda" style="width: 120" >
					<OPTION value="00"></OPTION>
					<OPTION value="2">DOLARES</OPTION>
				</SELECT></td>
				</logic:notPresent>
				</tr>
			    <tr> 
			      <td width="5">&nbsp;</td>
			      <td width="133"><strong>TIPO DE CAMBIO</strong></td>
			      <logic:present name="monto">
			      	<td width="200"><INPUT type="text" style="width: 120" value='<bean:write name="monto"/>' readonly="readonly" name="txtTipoCambio" size="14"></td>
			      </logic:present>
			      <logic:notPresent name="monto">
			      	<td width="409"><INPUT type="text" value="" name="txtTipoCambio" size="14" style="width: 120"></td>
			      </logic:notPresent>
			      
			    </tr>
		        <tr></tr>
	       </table>
        
         </td>
    </tr>        
</table>
<table>	
 		<tr>	
		<td colspan="5">
			<table class="grilla" >
				<tr>
		      		<th align="center" height="19" width="24%"><div align="center">CAJA</div></th>
		      		<th align="center" height="19" width="38%"><div align="center"> CAJERO ASIGNADO </div></th>
		      		<th align="center" height="19" width="37%"><div align="center">ESTADO</div></th>
		      	</tr>
				<% 
				java.util.ArrayList listaDetalleCajas = null;
				gob.pe.sunarp.extranet.caja.bean.DetalleCajaBean detCaja = null;
				
				if (request.getAttribute("listaDetalleCaja")!=null)
										listaDetalleCajas = (java.util.ArrayList)request.getAttribute("listaDetalleCaja");
				int size = 0;
				if(listaDetalleCajas!=null){
				size = listaDetalleCajas.size();
					
					if(size>0){
						 for (int i=0; i<size; i++) {
						 	
						 	detCaja = (gob.pe.sunarp.extranet.caja.bean.DetalleCajaBean)listaDetalleCajas.get(i);
				%>
				<tr>
					<td width="127"><div align="center"><%=detCaja.getCaja()%></div> </td>
					<td width="193"><div align="center"><%=detCaja.getCajeroAsignado()%></div></td>
					<% if ( detCaja.getEstado().equals(gob.pe.sunarp.extranet.caja.bean.DetalleCajaBean.APERTURA_CAJERO) ){%>
					<td width="350" align="center">
						<table>
							<tr>
								<td style="border: 0px;" align="center">
										<%=detCaja.getDesEstado() %>
								</td>
								<td style="border: 0px;" align="center">
									<A href="javascript:Cerrar('<%=detCaja.getCajeroAsignado()%>');" onmouseover="javascript:mensaje_status('Cerrar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG border="0" src="images/btn_cerrar.gif" width="89" height="29"></td>
								
							</tr>
						</table>
					<% } else { %>
					<td align="center"><%=detCaja.getDesEstado() %></td>
					<% 
					}
					%>
				</tr>		
				<%
										}
							}
					}
				%>
</table>    
<br>
<table width="100%">
	<tr align="center">
		<td colspan="3" align="center"> 
		<A href="javascript:Aperturar();" onmouseover="javascript:mensaje_status('Aperturar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		<IMG border="0" src="images/btn_aperturar.gif" ></A></td>
	</tr>	
<table>

<input type="hidden" name="hid1" value="">
</table>
</table>
</td>
</tr>
</table>
</form>
</body>

</html>


