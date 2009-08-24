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
	            	 	   	<% 
	            	 	   		Certificado objcert=(Certificado)request.getAttribute("Certificado");
	         	 		   		BigDecimal pagoCrem = objcert.getPagoCrem();
	         	 		   		double auxpago= pagoCrem.doubleValue(); 
	         	 		   	%>
	            	 	<logic:equal name="Certificado" property="flagPagoCrem" value="1">
	            	 		<% 
	            	 			double total= Double.parseDouble(objcert.getTotal());
		            	 		double pagoTotal= auxpago + total;
	            	 		%>
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
            <TD colspan="2" align="center"><CENTER><FONT size="2"><B><FONT face="Franklin Gothic Book">
            	<%=bundle.getString("msn.certificado.crem.historico.constancia.titulo") %></FONT></FONT><BR><BR>
            	<FONT size="3"><FONT face="Franklin Gothic Book"><%=bundle.getString("msn.certificado.crem.condicionado.constancia.tipoCertificado") %><BR></FONT></FONT>
            	<FONT size="2"><FONT face="Franklin Gothic Book"><%=bundle.getString("msn.certificado.crem.condicionado.constancia.subtipoCertificado") %></FONT></FONT>
				</CENTER>
			</TD>
        </TR>
        <TR>
        	<TD colspan="3"><br><br>
	        	<FONT size="2"><B><FONT face="Franklin Gothic Book"><%=bundle.getString("msn.certificado.crem.condicionado.constancia.asunto") %></FONT></B></FONT>
	        	<br><br>
	        	<div align="justify"><span class="cabeceraCertificadoComunDefJustify">
        		<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera1") %><BR>
        		<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera2") %><br>
        		<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera3") %><br>
        		<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera4") %><b>&nbsp;<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera5") %><br>		
        		
	        	<% String intTipos= (String)request.getAttribute("numTipos");
		           int numTipos= Integer.parseInt(intTipos);
		           if (numTipos >= 4){%>
		                 <bean:write name="unalinea" /><br>
		                 <bean:write name="segundalinea" /><br>
				   <%}else{%>
						 <bean:write name="unalinea" />
				    <%} %>   
  			         ,<bean:write name="descFlagHistorico" />
				</b>	        	
        		<logic:equal name="Certificado" property="tipo_objeto" value="N">
	        		<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera6") %>&nbsp;&nbsp;<b><u><bean:write name="Certificado" property="objeto_sol_PN"/></u></b>
        		</logic:equal>
				<logic:equal name="Certificado" property="tipo_objeto" value="J">
					<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera7") %>&nbsp;<b><u><bean:write name="Certificado" property="objeto_sol_PJ"/></u></b>
				</logic:equal>
        		   <br><%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera8") %>&nbsp;<%=bundle.getString("msn.certificado.crem.condicionado.constancia.cabecera9") %><br> 
        		</span></div>
        	</TD>
        </TR>
        <br>
		<TR>
			<TD colspan="3"><div align="justify"><FONT face="Franklin Gothic Book" size="2"><BR>
				<% Certificado objcert= (Certificado)request.getAttribute("Certificado"); %>
				<% StringBuffer constancia = objcert.getConstancia2();
				   if (constancia.length()==0){ %>
						<script type="text/javascript">
			            	document.write(window.opener.document.frm1.Constancia2.value);
		            	</script>
	              <%}else{
	                
	                String q = constancia.toString().trim().replace(" ","&nbsp;");
	                %>
		          <%=q%>
	              <%}%>
			</FONT></div>
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
				<bean:write name="Certificado" property="hora" scope="request"/> horas con <bean:write name="Certificado" property="minutos" scope="request"/> minutos del d&iacute;a de hoy&nbsp; <%=diaSemana %> <bean:write name="Certificado" property="dia" scope="request"/><br>
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
