<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<head><link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="javascript">
function Regresa(){ 
	document.form1.method = "POST";
	document.form1.action = "/iri/BusquedaTituloVehicularPublico.do";
	document.form1.submit();
}
function VerEsquela(params){
	ventana=window.open('/iri/BusquedaTituloVehicularPublico.do?state=mostrarEsquela' + params,'1024x768','status=yes,scrollbars=yes,resizable=yes,location=no,directories=no,width=950,height=650, top=0, left=0');
}
</script>
<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body>
<form name="form1">
	<input type="hidden" name="detalle" value="SI">
	<input type="hidden" name="CboOficinas" value="<bean:write name="oficina"/>">
	<input type="hidden" name="ano" value="<bean:write name="ano"/>">
</form>
<table class=titulo>
  <tr> 
    <td colspan="3"><font color=black>SERVICIOS &gt;&gt; Consulta de Estado de T&iacute;tulos RPV&gt;&gt; </font>Detalle del T&iacute;tulo</td>
  </tr>
</table>
<br>
<table class=titulo2 cellspacing=0 >
        <tr> 
          <td width="20%" >
            <b>N&uacute;mero T&iacute;tulo :</b>
          </td>
          <td width="25%" >
            <b>
              <logic:present name="lista" property="num_titulo">
                <bean:write name="lista" property="num_titulo"/>
              </logic:present>
              &nbsp;
              
            </b>
          </td>
          <td width="10%" align="right" >
            <b>A&ntilde;o :</b>
          </td>
          <td width="20%" >
            <b>
              <logic:present name="lista" property="ano_titulo">
                <bean:write name="lista" property="ano_titulo"/>
              </logic:present>
              &nbsp;
              
            </b>
          </td>
          <td width="25%" align="center" >
            <b>Oficina: 
              <logic:present name="lista" property="oficina">
                <bean:write name="lista" property="oficina"/>
              </logic:present>
              &nbsp;
              
            </b>
          </td>
        </tr>
</table>
<br>
<table class=formulario cellspacing =0>
<logic:notPresent name="presentado">
        <tr> 
          <th width="25%" >Tipo Registro</th>
          <td width="55%" >Registro de Propiedad Vehicular <%--<bean:write name="lista" property="tipoRegistro"/>--%></td>
        </tr>
</table>
<br>
<table class=formulario cellspacing=0 >        
        <tr> 
          <th width="25%" >Actos Registrales</th>	
          <td width="55%" >
	    	<font color="#800000">
	    		<logic:present name="listaRegistrales">          
				<logic:iterate name="listaRegistrales" id="item2" scope="request">
					<logic:present name="item2">
					- <bean:write name="item2"/><br>
					</logic:present>
				</logic:iterate>
				</logic:present>
				&nbsp;
			</font>
          </td>
        </tr>
<!-- /logic:notPresent-->

        <tr> 
          <th width="25%" >Resultado de la calificaci&oacute;n </th>
          <td width="55%" >
            <logic:present name="lista" property="resultado">
              <bean:write name="lista" property="resultado"/>
            </logic:present>
            &nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <%--
            <logic:present name="lista" property="url">
            <a href="javascript:VerEsquela('<bean:write name="lista" property="url"/>');">
              <logic:present name="lista" property="desc_url">
                <bean:write name="lista" property="desc_url"/>
              </logic:present>
              
            </a>
            </logic:present>
            --%>
          </td>
        </tr>
        <tr> 
          <th width="25%" >Mensaje</th>
          <td width="55%" >
            <logic:present name="lista" property="mensaje">
              <bean:write name="lista" property="mensaje"/>
            </logic:present>
            &nbsp;
            
          </td>
        </tr>
        <tr> 
          <th width="25%" >Fecha Hora de Presentaci&oacute;n</th>
          <td width="55%" >
            <logic:present name="lista" property="fecha">
              <bean:write name="lista" property="fecha"/>
            </logic:present>
            &nbsp;
            
          </td>
        </tr>
        <tr> 
          <th width="25%" >Placa</th>
          <td width="55%" > 
			<font color="#800000">          
			  <logic:present name="listaPlacas">
				<logic:iterate name="listaPlacas" id="item1" scope="request">
					<logic:present name="item1">
					- <bean:write name="item1"/><br>
					</logic:present>
				</logic:iterate>
			  </logic:present>	
			</font>
          </td>
        </tr>        
	<logic:notPresent name="presentado">
        <tr> 
          <th width="25%" >Vencimiento</th>
          <td width="55%" >
            <logic:present name="lista" property="vencimiento">
              <bean:write name="lista" property="vencimiento"/>
            </logic:present>
            &nbsp;
            
          </td>
        </tr>
	</logic:notPresent>

</table>
<br>
<logic:notPresent name="presentado">
  <table class=cabeceraformulario cellspacing=0 >
    <tr > 
      <td width="205%" >Presentante</td>
    </tr>
  </table>
	<br>
      <table class=formulario cellspacing=0 >
        <tr> 
          <th width="25%" >Nombre </th>
          <td width="55%" >
            <logic:present name="lista" property="presentante_nom">
              <bean:write name="lista" property="presentante_nom"/>
            </logic:present>
            &nbsp;
          </td>
		</tr>
         <tr>  
          <th width="25%" >Documento </th>
          <td width="55%" >
            <logic:present name="lista" property="presentante_num_doc">
              <bean:write name="lista" property="presentante_num_doc"/>
            </logic:present>
            &nbsp;
            
          </td>
        </tr>
      </table>
      <br>
      <table class=formulario cellspacing=0 >
        <tr> 
          <th width="25%" >Informacion actualizada al</th>
          <td width="55%" >
            <logic:present name="lista" property="fecha_ult_sinc">
              <bean:write name="lista" property="fecha_ult_sinc"/>
            </logic:present>
            &nbsp;
            
          </td>
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