<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>
<%@page import="java.util.ResourceBundle"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script> 
<TITLE>
	   <logic:present name="Certificado" scope="request">	
          <logic:equal name="Certificado" property="tpo_certificado" value="V">Certificado Vigencia</logic:equal> 	
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
	Certificado certificado = (Certificado)request.getAttribute("Certificado");
	Locale local= new Locale("es","ES");
	SimpleDateFormat sd= new SimpleDateFormat( "hh 'horas con' mm 'minutos'",local);
	String horaSD = sd.format(certificado.getFechaCreacion());
 %>
<input type="hidden"  name="constanciaHistorico" value = "">
<logic:present name="Certificado" scope="request">	
	<table>
		<TR>
			<TD width="60">&nbsp;</TD>
			<TD width="420">&nbsp;</TD>
			<TD width="200">&nbsp;</TD>
		</TR>	
		<TR>
			<TD>&nbsp;</TD>
			<TD>&nbsp;</TD>
			<TD>&nbsp;</TD>
		</TR>	
		<TR>
        	<TD>&nbsp;</TD>
            <TD><IMG src="<%=request.getContextPath()%>/images/orlclogo.gif">&nbsp;&nbsp;
            </td>
            <td class="cabeceraCertificadoComunDef">
            Expediente N°&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="Certificado" property="solicitud_id"/><br>
            Derechos pagados: &nbsp;&nbsp;S/.&nbsp;<bean:write name="Certificado" property="total" scope="request"/><br>
            </TD>
        </TR>
        <TR>
        	<TD>&nbsp;</TD>
            <TD>&nbsp;<BR><BR></TD>
            <TD>&nbsp;</TD>
        </TR>  
        <TR>
        	<TD>&nbsp;</TD>
            <TD colspan="2" align="center" >
           		<span class="cabeceraCertificadoEncabezado" ><%=bundle.getString("msn.certificado.rmc.constancia.titulo1") %></span><br>
            	<span class="cabeceraCertificadoTitulo"><%=bundle.getString("msn.certificado.rmc.constancia.titulo2") %></span><BR>
            	<span class="cabeceraCertificadoSubTitulo"><%=bundle.getString("msn.certificado.rmc.vigencia.constancia.titulo") %></span>
			
			</TD>
        </TR>
        <TR>
        	<TD colspan="3"><br><br>
	        	<span class="cabeceraCertificadoDefinicion"><%=bundle.getString("msn.certificado.rmc.vigencia.constancia.asunto") %></span>
	        	<br><br>
        		<p align="justify" class="cabeceraCertificadoComunDef">
        		<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.contenido1") %>:<u>
				<logic:present name="objetoSolicitud">
					<logic:present name="objetoSolicitud" property="nombres">
	        			<bean:write name="objetoSolicitud" property="nombres" /> &nbsp;
	        		</logic:present>
	        		<logic:present name="objetoSolicitud" property="ape_pat">
	        			<bean:write name="objetoSolicitud" property="ape_pat" />&nbsp;
	        		</logic:present>
	        		<logic:present name="objetoSolicitud" property="ape_mat">
	        			<bean:write name="objetoSolicitud" property="ape_mat" />&nbsp;
	        		</logic:present>
	        		<logic:present name="objetoSolicitud" property="raz_soc">
	        			<bean:write name="objetoSolicitud" property="raz_soc" />&nbsp;
	        		</logic:present>
	        		<logic:present name="objetoSolicitud" property="siglas">
	        			<bean:write name="objetoSolicitud" property="siglas" />&nbsp;
	        		</logic:present>
	        		<logic:present name="objetoSolicitud" property="numeroDocumento">
	        			<logic:present name="objetoSolicitud" property="nombreDocumento">
	        				<bean:write name="objetoSolicitud" property="nombreDocumento" />&nbsp;
	        			</logic:present>
	        			<bean:write name="objetoSolicitud" property="numeroDocumento" />&nbsp;
	        		</logic:present>
        		</logic:present>
        		</u>
        		<br>
        		<br>
        		<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.contenido2") %> <br> 
        		</p>
        	</TD>
        </TR>
        <br>
		<TR>
			<TD colspan="3"><div align="justify" class="cabeceraCertificadoComunDef"><BR>
				<% Certificado objcert= (Certificado)request.getAttribute("Certificado"); %>
				<% StringBuffer constancia = objcert.getConstancia2();
				   if (constancia != null && constancia.length()>0){ 
						  String q = constancia.toString().trim().replace(" ","&nbsp;");
						   %>
		          <%=q%>
	              <%}else{ %>
	              		<script type="text/javascript">
			            	document.write(window.opener.document.frm1.Constancia2.value);
		            	</script>
	              <%} %>

			</div>
			</TD>
		</TR>
		
	</table>
	
	<br>
	<table>	
		<tr>
			<td colspan="3"><div align="justify" class="cabeceraCertificadoComunDef">
			<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.contenido3") %>
				<bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/>
				<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.contenido4") %> 
				<%=horaSD%>
				<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.contenido5") %>
				 <bean:write name="Certificado" property="dia" scope="request"/>
			    de <bean:write name="Certificado" property="mes" scope="request"/> 
			    del <bean:write name="Certificado" property="anno" scope="request"/>.<br><br>
			  </div></td>
		</tr>
		<TR>
			<TD colspan="3"><p align="justify" class="cabeceraCertificadoComunDef"><BR>
				<% 
				   if (objcert.getTitulo() != null && objcert.getTitulo().length()>0){ 
						  String q1 = objcert.getTitulo().trim().replace(" ","&nbsp;");
				  %>
		          <%=q1%>
	              <%}else{ %>
	              		<script type="text/javascript">
			            	document.write(window.opener.document.frm1.txtTitulos.value);
		            	</script>
	              <%} %>		
			</p>
			</TD>
		</TR>
	</table> 
	<br>  
	<table>	
		<tr>
			<td colspan="3"><div align="justify" class="cabeceraCertificadoComunDef">
			<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.baselegal") %> <BR><BR>
			<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.pie1") %>.<br>
			<%=bundle.getString("msn.certificado.rmc.vigencia.constancia.pie2") %>.<br>
			 </div></td>
		</tr>
	</table> 
<table class=tablasinestilo>
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
</logic:present>

</BODY>
</HTML>


