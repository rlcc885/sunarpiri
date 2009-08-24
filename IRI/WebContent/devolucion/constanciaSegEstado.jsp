
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

<head>
<title>Formulario de Consulta de Estado de T&iacute;tulos</title>
<!--meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"-->
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">

//arreglo 2 empieza vacio:

function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	HOJA4.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
	HOJA4.style.visibility="visible";	
}
function Finalizar(){
	document.frm1.method = "POST";
  document.frm1.action="/iri/Publicidad.do?state=solicitarFormulario";
  document.frm1.submit();
}
</script>
<body>
<form name="frm1" method="post">
<logic:present name="Solicitud" scope="request">				
<input type="hidden" name="hid1">
<input type="hidden" name="hid2">
<br>
<%
  boolean flagInter=false;
  long perfilId=0;
  if(session!=null)
  {
  	UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
  	if(usuarioBean!=null)
  	 perfilId = usuarioBean.getPerfilId();
  	  if(usuarioBean.getFgInterno())
  	    flagInter = true;
  }
  //long perfilusuarioid =usuarioBean.getPerfilId();
%>
<table class=titulo cellspacing=0>
  <tr> 
      <td><font color=black>SERVICIOS &gt;&gt;Devoluciones&gt;&gt;</font><font color="#993300"> Constancia estado de Solicitud</font></td>
	</tr>
</table>
<br>
<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong> DATOS BASICOS DE LA SOLICITUD</strong></td>
  </tr>
</table>
  <table class=formulario cellspacing=0>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="133"><strong>NUMERO &nbsp; </strong></td>     
     
      <td>&nbsp;
      <logic:present name="Solicitud" scope="request">				
		<bean:write name="Solicitud" property="solicitud_id" scope="request"/>
		<input type="hidden" name="sol_id" value="<bean:write name="Solicitud" property="solicitud_id" scope="request"/>">	    
	  </logic:present>
	  </td>
      <td width="133">&nbsp; </td>
      <td width="133">
		
      </td>      
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>	
    <tr>    
    	<td>&nbsp;</td>
	    <td><strong>ESTADO</strong></td>
	    <%if(flagInter) {%>
 	    <td >&nbsp;<strong><font color="#993300"><bean:write name="Solicitud" property="estado_descripcion" scope="request"/></font></strong> 
        <%} else {%>
        <td >&nbsp;<strong><font color="#993300"><bean:write name="Solicitud" property="estado_ext_descripcion" scope="request"/></font></strong>
        <%}%>
        </td>
        <logic:equal name="Solicitud" property="estado" value="<%= Constantes.ESTADO_SOL_DESPACHADA%>">
        	<logic:present name="Solicitud" parameter="destinatarioBean" scope="request" >	      		
				<logic:equal name="Solicitud" property="destinatarioBean.tpo_env" value="V">
				<!--td>&nbsp;</td-->
				<td colspan="2">Puede apersonarse a ventanilla a recoger su certificado registral.</td>	
				</logic:equal>
			</logic:present>         
		</logic:equal>
    </tr>
    <tr> 
      <td width="4">&nbsp;</td>
      <td>&nbsp;</td>
      <td width="425">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="163"><strong>TIPO DE CERTIFICADO</strong></td>
      <td>
	  <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol" scope="request">		
		<bean:write name="objsol" property="certificado_desc"/>		
	  </logic:iterate>
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol" scope="request">				
       
    <logic:equal name="objsol" property="certificado_id" value="8">        	
    <!-- Copia Literal Asiento--> 
	    <tr>
 	   		<td>&nbsp;</td>        	        	
        	<td><strong>NRO SEC ASIENTO</strong></td>	
        	<td><bean:write name="objsol" property="ns_asiento"/></td> 
        	<td><strong>ACTO</strong></td>	
        	<td><bean:write name="objsol" property="desc_acto"/></td>                	
    	</tr>
    	<tr> 
     		 <td>&nbsp;</td>
     		 <td>&nbsp;</td>
     	 	 <td>&nbsp;</td>
    	</tr>
    	<tr>
 	   		<td>&nbsp;</td>        	        	
        	<td><strong>AÑO TITULO</strong></td>	
        	<td><bean:write name="objsol" property="aa_titu"/></td> 
        	<td><strong>NRO TITULO</strong></td>	
        	<td><bean:write name="objsol" property="num_titu"/></td>                	
    	</tr>
    	<tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    </logic:equal>                	      
    </logic:iterate>          
    <tr> 
      
      <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol" scope="request">		

        <!-- Certificado Negativo--> 
        <!-- Certificado Negativo--> 
        <logic:equal name="objsol" property="tpo_cert" value="N">                     		
        	<logic:equal name="objsol" property="tpo_pers" value="N">
      			<td>&nbsp;</td>
        		<td width="163"><strong>APELLIDOS Y NOMBRES</strong></td>
        		<td>      	
				<bean:write name="objsol" property="ape_pat"/>
				<bean:write name="objsol" property="ape_mat"/>
				<bean:write name="objsol" property="nombres"/>
				</td>			
			</logic:equal>	
			<logic:equal name="objsol" property="tpo_pers" value="J">
	      		<td>&nbsp;</td>
	        	<td width="163"><strong>RAZON SOCIAL</strong></td>
	        	<td>      	
				<bean:write name="objsol" property="raz_soc"/>			
				</td>			
			</logic:equal>					
		</logic:equal> 
        
        <!-- Copia Literal--> 
        <logic:equal name="objsol" property="tpo_cert" value="L">                     		
        	<td>&nbsp;</td>
	       	<td width="163"><strong>NUMERO:</strong></td>
	       	<td><bean:write name="objsol" property="refnum_part"/></td>        
	       	<logic:equal name="objsol" property="certificado_id" value="8">
	       		<td width="163"><strong>NRO PAGINAS:</strong></td>
	       		<td><bean:write name="objsol" property="numpag"/></td>
	       	</logic:equal>
	 	</logic:equal>		
	  </logic:iterate>	        
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="163"><strong>OFICINA REGISTRAL</strong></td>
      <td>
      <logic:iterate name="Solicitud" property="objetoSolicitudLista" id="objsol" scope="request">		
		<bean:write name="objsol" property="ofic_reg_desc"/>	
	  </logic:iterate>
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>

  </table>
<table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>1. DATOS DEL SOLICITANTE</strong></td>
  </tr>
</table>

  <table class=formulario cellspacing=0>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
    <!--tr> 
      <td>&nbsp;</td>
      <td width="133"><strong>TIPO DE PERSONA</strong></td>
      <td> 
      <logic:present name="Solicitud" parameter="solicitanteBean" scope="request" >	      		
		<logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="N">
		NATURAL
		</logic:equal>
		<logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="J">
		JURIDICA
		</logic:equal>
	  </logic:present>
      </td>
      <td width="133">&nbsp; </td>
      <td width="133">&nbsp; </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr-->
    <tr>         
 
      <logic:present name="Solicitud" parameter="solicitanteBean" scope="request" >	      
        <logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="N">
           <td>&nbsp;</td>
	       <td width="133"><strong>APELLIDOS Y NOMBRES</strong></td>
 	       <td colspan="2">
			<bean:write name="Solicitud" property="solicitanteBean.ape_pat" scope="request"/>
			<bean:write name="Solicitud" property="solicitanteBean.ape_mat" scope="request"/>
			<bean:write name="Solicitud" property="solicitanteBean.nombres" scope="request"/>
		   </td>
	       <td width="133">&nbsp;</td>		
    	</logic:equal>
    	
    	<logic:equal name="Solicitud" property="solicitanteBean.tpo_pers" value="J">
           <td>&nbsp;</td>
	       <td width="133"><strong>RAZON SOCIAL</strong></td>
 	       <td colspan="2">
			<bean:write name="Solicitud" property="solicitanteBean.raz_soc" scope="request"/>			
		   </td>
	       <td width="133">&nbsp;</td>		
    	</logic:equal>
	  </logic:present>
   
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="133"><strong>TIPO DE DOCUMENTO</strong></td>
      <td>
      <logic:present name="Solicitud" parameter="solicitanteBean" scope="request" >	      
		<bean:write name="Solicitud" property="solicitanteBean.tipo_doc_id" scope="request"/>
	  </logic:present>
      </td>
      <td width="133"> <strong>NUMERO DE DOCUMENTO</strong> </td>
      <td width="133">
      <logic:present name="Solicitud" parameter="solicitanteBean" scope="request" >	      
		<bean:write name="Solicitud" property="solicitanteBean.num_doc_iden" scope="request"/>
	  </logic:present>
      </td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="133">&nbsp;</td>
    </tr>
  </table>
  


 <table class=cabeceraformulario cellspacing=0>
  <tr>
      <td><strong>2. DATOS DEL PAGO</strong></td>
  </tr>
</table>

  <table class=formulario cellspacing=0>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133"><strong>MONTO</strong></td>
      <td width="150">
      <logic:present name="Solicitud" scope="request">				
		S/<bean:write name="Solicitud" property="total" scope="request"/>
	  </logic:present>      
      </td>
      <td width="150"><strong>FECHA:</strong> </td>
      <td width="150">
	  <logic:present name="Solicitud" parameter="pagoBean" scope="request" >	      
		<bean:write name="Solicitud" property="pagoBean.ts_crea" scope="request"/>
	  </logic:present>     
      </td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>
      <td width="133"><strong>FORMA DE PAGO</strong></td>
      <td colspan="2"> <strong>
	  <logic:present name="Solicitud" property="pagoBean.tipo_abono" scope="request" >	      
		
		<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_CHEQUE%>">
			Cheque		
		</logic:equal>
		<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_EFECTIVO%>">
			Efectivo		
		</logic:equal>
		<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_LINEA_PREPAGO%>">
			Linea Prepago		
		</logic:equal>
		<logic:equal name="Solicitud" property="pagoBean.tipo_abono" value="<%= Constantes.PAGO_TARJETA_DE_CREDITO%>">
			Tarjeta de credito		
		</logic:equal>
	  </logic:present>           
      </strong> </td>
      <td width="150"> <div align="center"> </div></td>
    </tr>
    <tr> 
      <td width="5">&nbsp;</td>
      <td width="133">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
      <td width="150">&nbsp;</td>
    </tr>
  </table>
<br>
<table class=tablasinestilo>
  <tr>
  	<td width="100%" align="right">
  	<div id="HOJA2"> 
  	<a href="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
  	 	</div>
  	 	
	<div id="HOJA3">	
	<a href="javascript:history.back();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</div>
	<%
	
	if ( perfilId == Constantes.PERFIL_DEVOLUCIONES) {
	
	%>
	<div id="HOJA4">	
	
	</div>
	<% } else {%>
	<div id="HOJA4">	
	<a href="javascript:Finalizar();" onmouseover="javascript:mensaje_status('Finalizar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_finalizar.gif" width=83 align=absMiddle vspace=5 border=0></a>
	</div>
	<%} %>
	</td>
  </tr>
</table>


</logic:present>
</form>



</BODY>
</HTML>

