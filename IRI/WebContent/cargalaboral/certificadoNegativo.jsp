<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>

<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script> 
<TITLE>
	   <logic:present name="Certificado" scope="request">	
          <logic:equal name="Certificado" property="tpo_certificado" value="N">Certificado Negativo</logic:equal> 	
		  <logic:equal name="Certificado" property="tpo_certificado" value="P">Certificado Positivo</logic:equal> 
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
<BODY>

<logic:present name="Certificado" scope="request">	
	<table>
		<TR>
			<TD width="60">&nbsp;<TD>
			<TD width="550">&nbsp;<TD>
			<TD width="50">&nbsp;<TD>
		</TR>	
		<TR>
			<TD>&nbsp;<TD>
			<TD>&nbsp;<TD>
			<TD>&nbsp;<TD>
		</TR>	
		<TR>
			<TD>&nbsp;<TD>
			<TD>&nbsp;<TD>
			<TD>&nbsp;<TD>
		</TR>	
		<TR>
        	<TD>&nbsp;<TD>
            <TD><IMG src="<%=request.getContextPath()%>/images/orlclogo.gif"></TD>
        </TR>
        <TR>
        	<TD>&nbsp;<TD>
            <TD>&nbsp;<BR>
            <BR>
            <BR>
            <BR>
            </TD>
            <TD>&nbsp;</TD>
        </TR>  
        <TR>
        	<TD>&nbsp;<TD>
            <TD><CENTER><FONT size="4"><B><FONT face="Lucida Sans"><logic:equal name="Certificado" property="tpo_certificado" value="N"> </logic:equal>
            <logic:equal name="Certificado" property="tpo_certificado" value="N">CERTIFICADO NEGATIVO DE INSCRIPCI&Oacute;N DE </logic:equal> 	
			 <logic:equal name="Certificado" property="tpo_certificado" value="P">CERTIFICADO DE INSCRIPCI&Oacute;N DE </logic:equal> 			
			<STRONG><bean:write name="Certificado" property="detalle_certificado" scope="request"/></STRONG></FONT></B></FONT></CENTER>
			</TD>
        </TR>
        <TR>
        	<TD>&nbsp;<TD>
            <TD>
            <CENTER><FONT size="4"><B><FONT face="Lucida Sans"><STRONG><bean:write name="Certificado" property="zona_registral" scope="request"/></STRONG></FONT></B></FONT></CENTER>
            </TD>
        </TR>
        <TR>
        	<TD>&nbsp;<TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
        </TR>  
        <TR>
        	<TD>&nbsp;<TD>
            <TD>
            <CENTER><FONT size="4"><B><FONT face="Lucida Sans"><STRONG>Oficina Registral de <bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/></STRONG>
		</FONT></B></FONT></CENTER>
            </TD>
            <TD></TD>
        </TR>
        <TR>
        	<TD>&nbsp;<TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
        </TR>  
        <TR>
        	<TD>&nbsp;<TD>
            <TD>&nbsp;</TD>
            <TD>&nbsp;</TD>
        </TR>  
		<tr>		
			<TD>&nbsp;<TD>
			<td width="550"><div align="justify"><FONT face="Franklin Gothic Book" size="2">De conformidad con lo dispuesto en la Directiva Nro 014-2003 - SUNARP/SN, aprobada por Resoluci&oacute;n del Superintendente Nacional de los Registros P&uacute;blicos No. 493-2003 SUNARP/SN, <B>CERTIFICO</B> que en la Oficina Registral de <bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/>					
			<logic:equal name="Certificado" property="tpo_certificado" value="N"> <B>NO</B> aparece inscrito ni anotado preventivamente ning&uacute;n (a) </logic:equal>
			<logic:equal name="Certificado" property="tpo_certificado" value="P"> <B>SI</B> existe inscripci&oacute;n de </logic:equal><bean:write name="Certificado" property="detalle_certificado" scope="request"/> a nombre de 	
			<logic:equal name="Certificado" property="tipo_objeto" value="N">	 
			<bean:write name="Certificado" property="objeto_sol_PN" scope="request"/>
			</logic:equal> 	
			<logic:equal name="Certificado" property="tipo_objeto" value="J">	 
				<bean:write name="Certificado" property="objeto_sol_PJ" scope="request"/>
			</logic:equal>
			</FONT></div></td>
		</tr>				
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
</table>
	<table>	
	<logic:notEqual name="Certificado" property="constancia" value="">
	<logic:notEqual name="Certificado" property="constancia" value="null">
	<TR>
			<TD width="60">&nbsp;<TD>
			<TD width="550"><div align="justify"><FONT face="Franklin Gothic Book" size="2">Se deja constancia que:
			<BR><bean:write name="Certificado" property="constancia" scope="request" filter="false"/>
		</FONT></div><TD>
			<TD width="50">&nbsp;<TD>
	</TR>
	</logic:notEqual>
	</logic:notEqual>
	<TR>
			<TD width="60">&nbsp;<TD>
			<TD width="550">&nbsp;<TD>
			<TD width="50">&nbsp;<TD>
	</TR>	
	<tr>
		<TD>&nbsp;<TD>
		<td width="550"><div align="justify"><FONT face="Franklin Gothic Book" size="2">Expido el presente certificado seg&uacute;n verificaci&oacute;n realizada en la 
			Oficina Registral de <bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/>
			a las <bean:write name="Certificado" property="hora" scope="request"/> horas del d&iacute;a <bean:write name="Certificado" property="dia" scope="request"/> 
		    de <bean:write name="Certificado" property="mes" scope="request"/> 
		    de <bean:write name="Certificado" property="anno" scope="request"/>		    
		</FONT></div></td>
	</tr>
	<tr>
		<TD>&nbsp;<TD>
		<td><FONT size="1">&nbsp;</FONT></td>
	</tr>
	<tr>
		<TD>&nbsp;<TD>
		<td><FONT face="Franklin Gothic Book" size="2">N&uacute;mero de Solicitud: <bean:write name="Certificado" property="solicitud_id" scope="request"/></FONT></td>
	</tr>
	<tr>
		<TD>&nbsp;<TD>
		<td><FONT face="Franklin Gothic Book" size="2">Derechos Pagados : S/. <bean:write name="Certificado" property="total" scope="request"/> </FONT></td>
	</tr>
	<tr>
		<TD>&nbsp;<TD>
		<td><FONT face="Franklin Gothic Book" size="1">&nbsp;</FONT></td>
	</tr>
	<tr>
		<TD>&nbsp;<TD>
		<td align="right" ><FONT face="Franklin Gothic Book" size="1">
			<logic:equal name="Certificado" property="accion" value="VE">
				<bean:write name="Certificado" property="ofic_reg_id_verif" scope="request"/> 	
				<%--<bean:write name="Certificado" property="ofic_reg_id_exp" scope="request"/>--%> 
				<bean:write name="Certificado" property="dia" scope="request"/>	
				<bean:write name="Certificado" property="mes" scope="request"/>	de
				<bean:write name="Certificado" property="anno" scope="request"/>
			</logic:equal>
			<logic:equal name="Certificado" property="accion" value="EM">
				<bean:write name="Certificado" property="ofic_reg_id_exp" scope="request"/>
				<%--<bean:write name="Certificado" property="ofic_reg_id_exp" scope="request"/>--%> 
				<bean:write name="Certificado" property="diaEmi" scope="request"/>	
				<bean:write name="Certificado" property="mesEmi" scope="request"/>	de
				<bean:write name="Certificado" property="annoEmi" scope="request"/> 	
			</logic:equal>
			
		     </FONT>
		</td>				
	</tr>		
	<tr>
		<TD>&nbsp;<TD>
		<td><FONT size="1">&nbsp;</FONT></td>
	</tr>
	<!--tr>
		<TD>&nbsp;<TD>
		<td><FONT face="Franklin Gothic Book" size="4"><A href="javascript:window.close();">Cerrar</A></FONT></td>
		<td align="right"><FONT face="Franklin Gothic Book" size="4"><A href="javascript:window.close();">Imprimir</A></FONT></td>
	</tr-->
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


