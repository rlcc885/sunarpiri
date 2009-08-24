<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>

<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ResourceBundle"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script> 
<TITLE>
	   <logic:present name="Certificado" scope="request">	
          <logic:equal name="Certificado" property="tpo_certificado" value="N">Certificado Crem</logic:equal> 	
	   </logic:present> 	  
</TITLE>

<SCRIPT LANGUAGE="JavaScript">
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}

</script> 

</HEAD>
<BODY >
<%
	ResourceBundle bundle = ResourceBundle.getBundle("gob.pe.sunarp.extranet.publicidad.properties.Publicidad");
 %>
 
<input type="hidden"  name="constanciaHistorico" value = "">
<logic:present name="Certificado" scope="request">	
	<table>
		<TR>
			<TD width="420">&nbsp;</TD>
			<TD width="200">&nbsp;</TD>
		</TR>	
		<TR>
			<TD>&nbsp;</TD>
			<TD>&nbsp;</TD>
		</TR>	
		<TR>
            <TD><IMG src="<%=request.getContextPath()%>/images/orlclogo.gif">&nbsp;&nbsp;
            </td>
            <td>
            	<span class="cabeceraCertificadoComunDef">
            	 &#09;&#09;Expediente N° &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="cabeceraCertificadoDefinicion"><bean:write name="Certificado" property="solicitud_id"/></span><br>
            	 <logic:notEqual name="solicitud" property="estado" value="C"> <!--  mientras este en estado porVerificar="C" no se mostrara los derechos por pagar y pagados -->
	            	 <logic:present name="Certificado" property="flagPagoCrem">
	            	 	   <% Certificado objcert=(Certificado)request.getAttribute("Certificado");
	         	 		   BigDecimal pagoCrem = objcert.getPagoCrem();
	         	 		   double auxpago= pagoCrem.doubleValue(); %>
	            	 	<logic:equal name="Certificado" property="flagPagoCrem" value="1">
	            	 		<% double total= Double.parseDouble(objcert.getTotal());
	            	 		   double pagoTotal= auxpago + total;%>
			            	  <span class="cabeceraCertificadoComunDef">&#09;&#09;Derechos pagados: &nbsp;&nbsp;&nbsp;</span><span class="cabeceraCertificadoDefinicion">S/&nbsp;<%=pagoTotal %></span><br>            	 		
	            	 	</logic:equal>
	            	 	<logic:equal name="Certificado" property="flagPagoCrem" value="0">
	            	 	   	  <span class="cabeceraCertificadoComunDef">&#09;&#09;Derechos pagados: &nbsp;&nbsp;&nbsp;</span><span class="cabeceraCertificadoDefinicion">S/&nbsp;<bean:write name="Certificado" property="total" scope="request"/></span><br>            	 		
							  <% if (auxpago > 0.00){%>	            	 	   	  
				            	    <span class="cabeceraCertificadoComunDef">&#09;&#09;Derechos por pagar: &nbsp;&nbsp;&nbsp;</span><span class="cabeceraCertificadoDefinicion">S/&nbsp;<%=auxpago %></span><br>
							  <% } %>
	            	 	</logic:equal>
	            	 </logic:present>
	            </logic:notEqual> 
            </TD>
        </TR>
        <TR>
        	<TD>&nbsp;</TD>
            <TD>&nbsp;<BR><BR></TD>
            <TD>&nbsp;</TD>
        </TR>  
        <TR>
            <TD colspan="2" align="center"><span class="cabeceraCertificadoEncabezado" >
            	<%=bundle.getString("msn.certificado.crem.historico.constancia.titulo") %></span><BR><BR>
            	<span class="cabeceraCertificadoTitulo"><%=bundle.getString("msn.certificado.crem.historico.constancia.tipoCertificado") %></span><BR>
				<span class="cabeceraCertificadoSubTitulo"><%=bundle.getString("msn.certificado.crem.historico.constancia.subtipoCertificado") %></span>
			</TD>
        </TR>
        <TR>
        	<TD colspan="3"><br><br>
	        	<span class="cabeceraCertificadoDefinicion"><%=bundle.getString("msn.certificado.crem.historico.constancia.asunto") %></span>
	        	<br><br>
        		<span class="cabeceraCertificadoComunDef"><div align="justify">
        		<%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera1") %><BR>
        		<%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera2") %><br>
        		<%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera3") %><br>
        		<%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera4") %></span><span class="cabeceraCertificadoDefinicion">&nbsp;<%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera5") %></span>
        		<logic:equal name="Certificado" property="tipo_objeto" value="N">
	        		<span class="cabeceraCertificadoDefinicion"><%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera6") %></span><br><span class="cabeceraCertificadoNombrePersona"><bean:write name="Certificado" property="objeto_sol_PN"/></span>
        		</logic:equal>
				<logic:equal name="Certificado" property="tipo_objeto" value="J">
					<span class="cabeceraCertificadoDefinicion"><%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera7") %></span><br><span class="cabeceraCertificadoNombrePersona"><bean:write name="Certificado" property="objeto_sol_PJ"/></span>
				</logic:equal>
        		    <span class="cabeceraCertificadoComunDef"><%=bundle.getString("msn.certificado.crem.historico.constancia.cabecera8") %></span><br> 
        		
        	</TD>
        </TR>
        <br>
		<TR>
			<TD colspan="3"><div align="justify"><span class="cabeceraCertificadoComunDef"><BR>
				<% Certificado objcert= (Certificado)request.getAttribute("Certificado"); %>
				<% StringBuffer constancia = objcert.getConstancia2();
				   if (constancia.length()==0){ %>
						<script type="text/javascript">
			            	document.write(window.opener.document.frm1.hidVisualizaConstancia.value);
		            	</script>
	              <%}else{
	                
	                String q = constancia.toString().trim().replace(" ","&nbsp;");
	                %>
		          <%=q%>
	              <%}%>
			</span></div>
			</TD>
		</TR>
	</table>
	
	<br><br>
	<table>	
		<tr>
			 <%
				Certificado certificado = (Certificado)request.getAttribute("Certificado");
				Locale local= new Locale("es","ES");
				SimpleDateFormat diaSemanaSDF= new SimpleDateFormat("EEEE",local);
				String diaSemana = diaSemanaSDF.format(certificado.getFechaCreacion());
				SimpleDateFormat mesSDF= new SimpleDateFormat("MMMMMMMMM",local);
				String mes = mesSDF.format(certificado.getFechaCreacion());
			 %>
			<td colspan="3"><span class="cabeceraCertificadoComunDef"><div align="justify">
				En fe de lo cual se expide en la ciudad de <bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/>,&nbsp;siendo las 
				<bean:write name="Certificado" property="hora" scope="request"/> horas con <bean:write name="Certificado" property="minutos" scope="request"/> minutos del d&iacute;a de hoy&nbsp; <%=diaSemana %>&nbsp; <bean:write name="Certificado" property="dia" scope="request"/><br>
			    de <%=mes %>
			    del <bean:write name="Certificado" property="anno" scope="request"/>.==============================<br><br>
			    <%=bundle.getString("msn.certificado.crem.historico.constancia.base.legal") %><BR><BR>
			    <%=bundle.getString("msn.certificado.crem.historico.constancia.base.especificacion1") %><br>
			    <%=bundle.getString("msn.certificado.crem.historico.constancia.base.especificacion2") %><br></div></span>
			    <span class="cabeceraCertificadoDefinicion"><%=bundle.getString("msn.certificado.crem.historico.constancia.base.especificacion3") %></span>
			</td>
		</tr>
	</table> 
<table class=tablasinestilo>
  <tr>
    <logic:present name="solicitud">
		<logic:notEqual name="solicitud" property="estado" value="C">
			<td width="50%" align="left">
		  	<div id="HOJA2"> 
		  	<a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
		  	</div></td>
		</logic:notEqual>  	
	</logic:present>
	<td width="50%" align="right">
	<div id="HOJA3">	
	<a href="javascript:window.close();" onmouseover="javascript:mensaje_status('Cerrar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</div></td>
  </tr>
</table>
</logic:present>

</BODY>
</HTML>


