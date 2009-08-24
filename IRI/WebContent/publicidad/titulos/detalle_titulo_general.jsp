<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<head><link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">

function Regresa()
{
	history.back();
}

function VerEsquela(params)
{
	ventana=window.open('/iri/VerEsquela.do?state=mostrarEsquela' + params,'1024x768','status=yes,scrollbars=yes,resizable=yes,location=no,directories=no,width=950,height=650, top=0, left=0');
}
/*Inicio:mgarate:27/10/2007 PCM
 *	
 */
 function anotacion(params)
 {
	//ventana=window.open('/iri/BusquedaTituloPublico.do?state=anotacionInscripcion' + params,'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,directories=no,location=no');
//  window.open("/iri/CargaLaboral.do?state=verCertificadosNegativo&Tipo_Cert="+tipocert+"&sol_id="+solid+"&EstadoCertificado='C'&Constancia="+constancia,"Certificado",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=850');		
	window.open("/iri/BusquedaTituloPublico.do?state=anotacionInscripcion&params="+params,"",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=yes,resizable=no,directories=no,location=no,directories=no,width=719,height=850');		
 }
/*Fin:mgarate:PCM
 *
 */

</script>
<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body>
<form name="form1">
	<input type="hidden" name="detalle" value="SI">
	<!-- <input type="hidden" name="CboOficinas" value="<!-- bean:write name="oficina"/-->
	<!--  <input type="hidden" name="ano" value="<!-- bean:write name="ano"/-->
</form>
<table class=titulo>
  <tr> 
    <td colspan="3"><font color=black>SERVICIOS &gt;&gt; Consulta de Estado de T&iacute;tulos &gt;&gt; </font>Detalle del T&iacute;tulo</td>
  </tr>
</table>
<br>
<table class=titulo2 cellspacing=0 >
        <tr> 
          <td width="20%" ><b>N&uacute;mero T&iacute;tulo :</b></td>
          <td width="25%" ><b><bean:write name="lista" property="num_titulo"/></b></td>
          <td width="10%" align="right" ><b>A&ntilde;o :</b></td>
          <td width="20%" ><b><bean:write name="lista" property="ano_titulo"/></b></td>
          <td width="25%" align="center" ><b>Oficina <bean:write name="lista" property="oficina"/></b></td> 
        </tr>
</table>
<br>
<table class=formulario cellspacing=0 >
<logic:notPresent name="presentado">
        <tr> 
          <th width="25%" >Tipo Registro</th>
          <td width="55%" ><bean:write name="lista" property="tipoRegistro"/></td>
        </tr>
</table>
<br>
<table class=formulario cellspacing=0 >        
        <tr> 
          <th width="25%" >Actos Registrales</th>	
          <td width="55%" >
	    	<font color="#800000">          
	    	<logic:present name="listaRegistrales">
				<logic:iterate name="listaRegistrales" id="item1" scope="request">
					<logic:present name="item1">
					- <bean:write name="item1"/> <br>
					</logic:present>
				</logic:iterate>
			</logic:present>
			</font>
          </td>
        </tr>

<!--/logic:notPresent-->
        <tr> 
          <th width="25%" >Resultado de la calificaci&oacute;n </th>
          <td width="55%" ><bean:write name="lista" property="resultado"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a href="javascript:VerEsquela('<bean:write name="lista" property="url"/>');"> <bean:write name="lista" property="desc_url"/></a></td>       		
          <!--Inicio:mgarate:26/10/2007 PCM -->          
          <!--Link para la anotacion de inscripción-->
          <logic:present name="flagSolicitudInscripcion">
           <td><a href="javascript:anotacion('<bean:write name="refnumtitu"/>');" onmouseover="javascript:mensaje_status('Anotación de Inscripción');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver Anotacion de Inscripción</a></td>
          </logic:present>
          <!--Fin:mgarate PCM  -->
        </tr>
        <tr> 
          <th width="25%" >Mensaje</th>
          <td width="55%" ><bean:write name="lista" property="mensaje"/></td>
        </tr>
        <tr> 
          <th width="25%" >Fecha Hora de Presentaci&oacute;n</th>
          <td width="55%" ><bean:write name="lista" property="fecha"/></td>
        </tr>
<logic:notPresent name="presentado">
        <tr> 
          <th width="25%" >Vencimiento</th>
          <td width="55%" ><bean:write name="lista" property="vencimiento"/></td>
        </tr>
</logic:notPresent>
</table>
<br>
<logic:notPresent name="presentado"> 
      <table class=cabeceraformulario cellspacing=0 >
        <tr > 
          <td width="205%" >Presentante</tr>
	</table>
      <table class=formulario cellspacing=0 >
        <tr> 
          <td >
            <bean:write name="lista" property="presentante_nom"/>
            <br><br>
            Documento : <bean:write name="lista" property="presentante_num_doc"/></td>
        </tr>
      </table>

<logic:notEqual name="lista" property="areaRegistral" value = "24000">
<br> 
   <table class=cabeceraformulario cellspacing=0 > 
        <tr > 
          <td width="205%" ><p align="left">Participantes</p></tr>
        <tr> 
   </table>
      <table class=formulario cellspacing=0 >
              <tr> 
                <td width="25%"><strong>Tipo</strong></td>
                <td width="55%"><strong>NOMBRES/RAZ&Oacute;N SOCIAL</strong></td>
              </tr>
              <tr> 
              <logic:present name="listaEntidades">
				<logic:iterate name="listaEntidades" id="item1" scope="request">
                                <tr>    
				<logic:present name="item1">
					<td width="25%"><bean:write name="item1" property="tipo"/></td>
					<td width="55%"><bean:write name="item1" property="entidad"/></td>
				</logic:present>
				</tr>
				</logic:iterate>
			  </logic:present>
      </table>
</logic:notEqual> 
<!-- Inicio:jascencio:11/07/07
	 CC:REGMOBCOM-2006
 -->

<logic:present name="listaPartidasBloqueadas">
<br>
 <table class="cabeceraformulario">
 <tr>
 	<td>Partidas <br> Bloqueadas</td>
 	<td>Oficina&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td> 	
 	<td>Titulos asociados</td>
 	<td>Detalle</td>
 	
 </tr>
 </table>
 <table class="formulario">
<logic:iterate name="listaPartidasBloqueadas" id="partida" scope="request">
 <tr>
 	<td><STRONG><bean:write name="partida" property="partidaBloqueada"/></STRONG></td>
 	<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
 	<td><STRONG><bean:write name="partida" property="nombreOficina"/></STRONG></td>
 	<td align="center"><STRONG><bean:write name="partida" property="anoTitulo"/>&nbsp;-&nbsp;<bean:write name="partida" property="numeroTitulo"/></STRONG></td>
 	<td align="center"><a href="/iri/BusquedaTituloPublico.do?state=buscarXNroTituloDet&ano=<bean:write name="partida" property="anoTitulo"/>&numtitu=<bean:write name="partida" property="numeroTitulo"/>&areareg=<bean:write name="partida" property="areaRegistral"/>&oficinas=<bean:write name="partida" property="registro"/>|<bean:write name="partida" property="oficina"/>" >Ver Detalle</a><strong>
 	
 	</strong></td>
 </tr>
</logic:iterate>
 <tr>
 	<td>&nbsp;</td>
 	<td>&nbsp;&nbsp;</td>
 	<td>&nbsp;</td> 	
 	<td>&nbsp;</td>
 	<td>&nbsp;</td>
 </tr>
 </table>
</logic:present>
 <!--Fin:jascencio -->

      <br>
      <table class=formulario cellspacing=0 >
        <tr> 
          <th width="25%" >Fecha de Impresi&oacute;n</th>
          <td width="55%" ><bean:write name="lista" property="fecha_ult_sinc"/></td>
        </tr>
      </table>      
</logic:notPresent>

   <table class=tablasinestilo>
   	<tr> 
    	<td align=right>&nbsp;</td>
	</tr>   
   	<tr> 
    	<td align=right><a href="javascript:window.print();" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25" hspace="4"></a><a href="javascript:Regresa()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><img src="<%=request.getContextPath()%>/images/btn_regresa.gif" width="83" height="25" hspace="4" border="0"></a></td>
	</tr>
	</table>
<br>
</body>
</html>