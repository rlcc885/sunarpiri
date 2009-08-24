
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gob.pe.sunarp.extranet.framework.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.dbobj.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.certificada.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.Constantes" %>

<html>
<head><title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
<META HTTP-EQUIV="Cache-Control", "private">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<SCRIPT LANGUAGE="JavaScript" src="javascript/util.js">
</script>
<script language="javascript">
Navegador();
//arreglo 2 empieza vacio:
var arr2 = new Array();
var cont2=0;

	function Destino(){	
		//alert(comando_destino);
		document.frm1.action = "/iri/<bean:write name="destino" scope="request"/>";
		document.frm1.submit();
	}	

</SCRIPT>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<form name="frm1" method="post">
<br>

  <table class=formulario cellspacing=0>
    <tr> 
      <td width="8"></td>
      <td width="111"></td>


    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>
      </td>
        </tr>
	<tr>
		<td>&nbsp;</td>
    	<logic:present name="mensaje" scope="request">							
    		<td><CENTER>
			<bean:write name="mensaje" scope="request"/></CENTER>	    
			</td>
    	</logic:present>     
  	</tr>  
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
        </tr>
    <tr>
    	<td>&nbsp;</td>
	    <td width="50%" align="right"><CENTER>
		<div id="HOJA3">	
		<a href="javascript:Destino();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_aceptar.gif" width=83 align=absMiddle vspace=5 border=0></a>
		</div></CENTER></td>
	</tr>
  </table>	
</form>
</BODY>
</HTML>
