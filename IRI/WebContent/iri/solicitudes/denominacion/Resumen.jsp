<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<html>
<head>
	 <title>Formulario de Reserva de Preferencia Registral - Resumen</title>
     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">

<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js"></script>
</head>
<script language="JavaScript">
	

function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";
}

function Continuar(){
	if (confirm('¿Estas seguro de finalizar?')){ 
		document.frm1.action="<%=request.getContextPath()%>/PublicidadIRI.do";
		document.frm1.submit();
		<% session.removeAttribute("solicitudDenominacion");%>
    } 
}

</script>
<%int numeral = 0; %>
<body>
<div id="maincontent">
		<div class="innertube">
	<b><font color="666666">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="#949400"> Solicitud de Inscripci&oacute;n <font size="1">&gt;&gt;</font> Reserva de Preferencia Registral
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PASO 3 DE 3
			</FONT>

			</b>
	<br>
	<form name="frm1" method="POST" >
	
	<table class="punteadoTablaTop">
		 <tr>
		   <td align="center">
   				<IMG src="<%=request.getContextPath()%>/images/logosunarp.gif" border="0">
    	   </td>
    	   <td width="60%">
			<table cellpadding="0" cellspacing="0" border=0 width="100%" class="tablasinestilo">
				<tr>
                   <td align="center" style="black;background-color: #949400;" ><strong><font size="3"><br>SOLICITUD DE RESERVA DE PREFERENCIA REGISTRAL<br>
				DE NOMBRE DE PERSONA JURIDICA<br></font></strong></td>
                </tr>
                <tr>
                   <td align="center" style="black;background-color: #949400;" ><strong><font size="3">
                   <bean:write name="denominacion" property="anio"/>-<bean:write name="denominacion" property="numeroHoja"/></font></strong>
                   </td>
                </tr>
			</table>
		  </td>		
    	 </tr>
        
		
	</table>
	<!--  <br>-->
	<table class="tablasinestilo">
    	<tr>
    		<td colspan="4"><font color="#949400"><strong><%=++numeral%>. </strong><b>RESERVA DE NOMBRE DE PERSONA JURIDICA</b></font></td>
    	</tr>
    	<tr>
            <td width="186" valign="top"><bean:write name="denominacion" property="descSeleccion"/></td>
            <td valign="top" width="210"></td>
            <td valign="top" width="170"></td>
            <td width="186" valign="top"></td>
        </tr>
      	
    	
    </table>
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. </strong><b>DENOMINACION o RAZON SOCIAL</b></font></td>
    	</tr>
      
      	<tr>
      		<td>
      			<table class="tablasinestilo" cellpadding="0" bordercolor="#666666" cellspacing="0" border=1>
		    		<logic:iterate name="denoRazSoc" id="raz">
      			    <tr>
            			<td width="260" colspan="2"><strong><bean:write name="raz" property="denominacion"/></strong></td>
            			<td width="260" colspan="2"><strong><bean:write name="raz" property="denoAbrev"/></strong></td>
			        </tr>
			   		</logic:iterate>

				   
			   
      			</table>
      		</td>
        </tr>
        
        
    </table>
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. TIPO DE PERSONA JURIDICA</strong></font></td>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td>Tipo de persona juridica:</td>
					</tr>
      			</table>
      		</td>
        </tr> 
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="600px">
			            	<table border=1  cellpadding="0" bordercolor="#666666" cellspacing="0" width="100%">
			            		<tr>
			            			<td align="center"><strong><bean:write name="juridica" property="descTipo"/></strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	   
    </table>
    <logic:equal name="denominacion" property="indicadorSeleccion" value="02">
    
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. NOMBRE DE LA PERSONA JURIDICA</strong></font> (SOLO MODIFICACION)</td>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td>Razon Social:</td>
					</tr>
      			</table>
      		</td>
        </tr> 
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="600px">
			            	<table border=1  cellpadding="0" bordercolor="#666666" cellspacing="0" width="100%">
			            		<tr>
			            			<td align="center"><strong><bean:write name="juridica" property="razonSocial"/></strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	
        <tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td>Partida:</td><td>Ficha:</td>
					</tr>
					<tr>
			            <td><bean:write name="juridica" property="partida"/></td>
			            <td><bean:write name="juridica" property="ficha"/></td>
					</tr>
      			</table>
      		</td>
        </tr> 
      	
       
    </table>
    
    </logic:equal>    
    
    
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. DOMICILIO DE LA PERSONA JURIDICA</strong></font></td>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo" width="100%">
      				<tr>
			            <td width="260">Departamento:</td><td>Provincia:</td>

					</tr>
      			</table>
      		</td>
        </tr> 
      	<tr>
      		<td>
      			<table class="tablasinestilo"  cellpadding="0" bordercolor="#666666" cellspacing="0" border=1 width="100%">
		    	
      			    <tr>
            			<td width="260" align="center"><strong></strong>
            			<bean:write name="juridica" property="descDepto"/></td>
            			<td align="center"><bean:write name="juridica" property="descProv"/></td>
			        </tr>
			   
			   
      			</table>
      		</td>
        </tr>
       
    </table> 
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. DATOS DEL SOLICITANTE</strong></font></td>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo"  cellpadding="0" bordercolor="#666666" cellspacing="0" border="1">
      				<tr>
			            <td width="200" align="center"><strong><bean:write name="presentante" property="apePaterno"/></strong></td>
			            <td width="200" align="center"><strong><bean:write name="presentante" property="apeMaterno"/></strong></td>
			            <td width="200" align="center"><strong><bean:write name="presentante" property="nombre"/></strong></td>            
					</tr>
      			</table>
      		</td>
        </tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="200" align="center">Apellido Paterno</td>
			            <td width="200" align="center">Apellido Materno</td>
			            <td width="200" align="center">Nombre(s)</td>            
					</tr>
      			</table>
      		</td>
        </tr>
      	
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="130px">Identificado(a) con:</td>
			            <td width="60px" colspan="4">&nbsp;<bean:write name="presentante" property="descDocu"/></td>
			            <td width="60px"></td>            
			            <td width="60px"></td> 
			            <td width="60px"></td> 			            
			            <td width="20px">No.</td> 			            
			            <td width="210px">
			            	<table border=1  cellpadding="0" bordercolor="#666666" cellspacing="0" width="100%">
			            		<tr>
			            			<td align="center"><bean:write name="presentante" property="numDocu"/><strong></strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="130px">Domiciliado(a) en:</td>
			            <td width="470px">
			            	<table border=1  cellpadding="0" bordercolor="#666666" cellspacing="0" width="100%">
			            		<tr>
			            			<td align="center"><bean:write name="presentante" property="direccion"/><strong></strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
        <tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="130px">Participaci&oacute;n:</td>
			            <td width="470px">
			            	<table border=1  cellpadding="0" bordercolor="#666666" cellspacing="0" width="100%">
			            		<tr>
			            			<td align="center"><strong><bean:write name="presentante" property="descParticipacion"/></strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	
    	
    </table>
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. SOLICITO</strong></font></td>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td>La inscripci&oacute;n de (acto o derecho):</td>
					</tr>
      			</table>
      		</td>
        </tr> 
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td width="600px">
			            	<table border=1  cellpadding="0" bordercolor="#666666" cellspacing="0" width="100%">
			            		<tr>
			            			<td align="center"><strong>RESERVA DE DENOMINACION</strong></td>
			            		</tr>
			            	</table>
			            </td> 			            			            
					</tr>
      			</table>
      		</td>
        </tr>
      	
    </table>
    
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. INTEGRANTES DE LA PERSONA JURIDICA</strong></font></td>
    	</tr>
      	<tr>
      		<td>
      			<table class="tablasinestilo">
      				<tr>
			            <td>Integrantes:</td>
					</tr>
      			</table>
      		</td>
        </tr> 
      	<tr>
      		<td>
      			<table class="tablasinestilo"  cellpadding="0" bordercolor="#666666" cellspacing="0" border=1 width="100%">
		    		<logic:iterate name="participantes" id="part">
	      			    <logic:notEqual name="part" property="tipoDocu" value="RUC">
	      			    <tr>
	            			<td width="260" colspan="2"><strong>&nbsp;<bean:write name="part" property="apePaterno"/>&nbsp;<bean:write name="part" property="apeMaterno"/>&nbsp;<bean:write name="part" property="nombre"/></strong></td>
	            			<td><bean:write name="part" property="descDocu"/>:<bean:write name="part" property="numDocu"/></td>
				        </tr>
				        </logic:notEqual>
				         
				        <logic:equal name="part" property="tipoDocu" value="RUC">
				        <tr>
	            			<td width="260" colspan="2"><strong>&nbsp;<bean:write name="part" property="razonSocial"/></strong></td>
	            			<td><bean:write name="part" property="descDocu"/>:<bean:write name="part" property="numDocu"/></td>
				        </tr>
				        </logic:equal>
				   </logic:iterate>
				   		   
			   
      			</table>
      		</td>
        </tr>
        
    </table>
   
    <table class="tablasinestilo">
        <tr>
    		<td><font color="#949400"><strong><%=++numeral%>. ASIENTO</strong></font></td>
    	</tr>
      	
      	<tr>
      		<td>
	<% if ("si".equals(request.getAttribute("indicadorTitulo"))) {%>
      			<table class="tablasinestilo" border="1">

      				<tr>
			            <td height="67" width="86">PAGO
			            	
			            </td>
			            <td height="67" width="450">
			            	
			            <bean:write name="pago" property="descripcionFormaPago"/> - <bean:write name="pago" property="numeroOperacion"/><br>S/ <bean:write name="pago" property="costoTotalServicio"/>.00</td> 			            			            
			            <td height="67" width="170">
			            	
			            TITULO</td>
			            <td height="67" width="547">
			            	
			            <bean:write name="denominacion" property="anioTitu"/> - <bean:write name="denominacion" property="numeroTitulo"/><br><bean:write name="denominacion" property="fechaPresTitu"/>
						</td>  			            			
					</tr>
      			</table>
      			<%}%>
      		</td>
        </tr>
      	<tr>
      		<td align="right">&nbsp;<bean:write name="denominacion" property="codigoUsuario"/> <!-- - 32472 --></td>
      	</tr>
      	
    </table> 
	
<br>
<table class=tablasinestilo width="634">
  <tr>
  	<td width="96%" align="right">
  	  <div id="HOJA2"> 
  	  <INPUT class="formbutton" value="Imprimir" type="button" onclick="javascript:Imprimir();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
  	  </div>
  	</td>
    <td align="right" width="4%">
	  <div id="HOJA3">	
	    <INPUT class="formbutton" value="Continuar" type="button" onclick="javascript:Continuar();"  onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	  </div>
	</td>
        </tr>
        
</table>
</form>
<br>
</div>
</div></body>
</html>