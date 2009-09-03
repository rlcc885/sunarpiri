<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="java.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.publicidad.bean.*" %>
<%@ page import="gob.pe.sunarp.extranet.framework.session.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<%
FormOutputBuscarPartida output = (FormOutputBuscarPartida) request.getAttribute("output");
ArrayList listadoAsientos= (ArrayList)output.getResultadoActosRMC();
PartidaBean objpartida = (PartidaBean)output.getPartidaBean();
ArrayList arr1 = output.getResultado();
UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("Usuario");
boolean certi=true;
if(usuarioBean.getPerfilId()!=Constantes.PERFIL_CAJERO && ((usuarioBean.getPerfilId()!=Constantes.PERFIL_ADMIN_ORG_EXT && usuarioBean.getPerfilId()!=Constantes.PERFIL_AFILIADO_EXTERNO && usuarioBean.getPerfilId()!=Constantes.PERFIL_INDIVIDUAL_EXTERNO)
			 || usuarioBean.getExonPago()))
{
	certi=false;
}
%>

<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE></TITLE>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">

<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="JavaScript">

function Regresa()
{
	history.back();
}

function VerPartida(refnum_Part,estado)
{
  doyou = true;
  if (estado == '0') { 
    doyou = confirm("Esta partida está cerrada, ¿Desea visualizarla?"); //Your question.
  }
  if(doyou == true) {
	ventana=window.open('/iri/PublicidadIRI.do?state=visualizaPartida&refnum_part=' + refnum_Part,'1024x768','toolbar=no,status=yes,scrollbars=yes,resizable=yes,location=no,menubar=no,directories=no,width=950,height=650, top=0, left=0');
  }
}

</script>

</HEAD>

<BODY background="<%=request.getContextPath()%>/images/trama.gif">
	<div id="maincontent">
	<div class="innertube">
	
	<br>
	<table class=titulo cellspacing=0>
	  <tr> 
	    <td>
	    	<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Consulta de Partidas</font>&nbsp;Detalle Partida</b>
	    </td>
	  </tr>
	</table>
	<table border=0 width=600>
	  <tr> 
		    <td vAlign=top align=left width = 10%><font color="black"><b>Costo</b></font><BR>S/.&nbsp;<%=request.getAttribute("tarifa")%></td>
		    <td vAlign=top align=left width = 15%><font color="black"><b>Usuario</b></font><BR><%=request.getAttribute("usuaEtiq")%></td>
		    <td vAlign=top align=left width = 20%><font color="black"><b>Fecha Actual</b></font><BR><%=request.getAttribute("fechaAct")%></td>
		    <td vAlign=top align=left width = 55%><font color="black"><!-- Ahora tenemos un nuevo medio de publicidad registral, de libre acceso para todos: www.sunarp.gob.pe. --></font></td>
	  </tr>
	</table>
	<br>	
	<table border=0>
		<TABLE border=0>
		  <TBODY>
			  <TR>
			    <Th align=left width=100%>
			      	<font color="#949400" size="2">DETALLE DE PARTIDA</font>
			    </Th>
			  </TR>
		  </TBODY>
		</TABLE>
		
	  <logic:present name="output" property="partidaBean">
		  <tr bgcolor="#949400">	
			  <td><b>Partida&nbsp;&nbsp;<bean:write name="output" property="partidaBean.numPartida"/></b></td>
			  <td><b>Visualizar Asiento&nbsp;&nbsp;<input name="image2" type="image" onclick="VerPartida('<bean:write name="output" property="partidaBean.refNumPart"/>','<bean:write name="output" property="partidaBean.estado"/>')" value="Visualizar" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Asiento');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></b></td>
		  </tr>	 
		  <br><br>
		  <tr> 
			  <TABLE class="grilla" border=0>
				  <TBODY>
					    <Th align=left width=100% height=20 >
					      	<font color="#949400" size="2">T&iacute;tulos Pendientes</font>
					    </Th>
				  </TBODY>
			  </TABLE>
			  <br>
			  <table class=grilla cellspacing=0 width="600px" border="1">
			  	 <tr bgcolor="#949400">
				    <th width="3%"><font color="white">Zo<br>na</font></th>
				    <th width="4%"><font color="white">N° de titulo</font></th>
				    <th width="8%"><font color="white">Fecha de Presentación<br>Fecha de Vencimiento</font></th>
				    <th width="7%"><font color="white">Acto</font></th>
				    <th width="7%"><font color="white">Estado</font></th>
			     	<th width="4%"><font color="white">Ver<br>Detalle</font></th>
		     	 </tr>
		     	 <logic:present name="output" property="resultadoTituloPendientesRMC">
			     	<logic:iterate name="output" property="resultadoTituloPendientesRMC" id="objtitulo" scope="request">	
				     	<tr class=grilla2>
					     	<td align="center"><bean:write name="objtitulo" property="zonaReg"/></td>
					     	<td align="center"><bean:write name="objtitulo" property="nroTitulo"/></td>
					       	<td align="center"><bean:write name="objtitulo" property="fechaPresentacion"/>
					       		<logic:equal name="objtitulo" property="estado_id" value="110">Sin Fecha
					       		</logic:equal>
					       		<logic:notEqual name="objtitulo" property="estado_id" value="110">
					       			<logic:equal name="objtitulo" property="estado_id" value="120">Sin Fecha
					       			</logic:equal>
					       			<logic:notEqual name="objtitulo" property="estado_id" value="120">
								       	<br><bean:write name="objtitulo" property="fechaVencimiento"/>
						       		</logic:notEqual>
					       		</logic:notEqual>
					       	</td>
					      	<td align="center">
					      		<logic:present name="objtitulo" property="actoDescripcion">
						      		<bean:write name="objtitulo" property="actoDescripcion"/>
					      		</logic:present>
					      		<logic:notPresent name="objtitulo" property="actoDescripcion">
					      			&nbsp;
					      		</logic:notPresent>
					      	</td>
					      	<td align="center"><bean:write name="objtitulo" property="estadoDescripcion"/></td>
					      	<td align="center">
						        <a href="/iri/BusquedaTitulo.do?state=buscarXNroTituloDet&oficinas=<bean:write name="objtitulo" property="oficinas"/>&ano=<bean:write name="objtitulo" property="aaTitulo"/>&numtitu=<bean:write name="objtitulo" property="nroTitulo"/>&areareg=<bean:write name="objtitulo" property="area_registral"/>&pagina=1">
						        <image src="/iri/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>
					      	</td>
					    </tr> 	
				    </logic:iterate>
			     </logic:present>
			   </table> 
		  </tr>
		  <br>
		  <%int i=0; %>
		  <% String simbolo = "";
		  if (listadoAsientos.size()>0){ %>
		  	  <% for (int t=0; t<listadoAsientos.size(); t++){ %>
			  	  <TABLE class="grilla" border=0>
					  <TBODY>
						  <TR>
						    <Th align=left width=100% height=20>
						      	<font color="#949400" size="2">Inscripci&oacute;n &nbsp;<%=++i%></font>
						    </Th>
						  </TR> 
					  </TBODY>
				  </TABLE>	
				  	<tr> 
					  	<%if(certi){%>
					    	<td><b>Solicitar<br>Copia Literal</b>&nbsp;
					    		 <a href="/iri/Certificados.do?state=guardarDatosBasicos&refnum_part=<bean:write name="output" property="partidaBean.refNumPart" />&noPartida=<bean:write name="output" property="partidaBean.numPartida" />&hidOfic=<bean:write name="output" property="partidaBean.oficRegDescripcion" />&area=<bean:write name="output" property="partidaBean.areaRegistralId" />&hidTipo=LR">
							    	<image src="/iri/images/copia.gif" style="border:0" onmouseover="javascript:mensaje_status('Solicitar Partida');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
							  	 </a>
					    	</td>
						<%}%>	
				  	</tr>
				  	<br><br>
				   <table class=grilla cellspacing=0 width="600px" border="1">
				  		<tr bgcolor="#949400">
						    <th width="8%"><font color="white">ACTO</font></th>
						    <th width="6%" ><font color="white">FECHA ACTO<BR>CONSTITUTIVO</font></th>
						    <th width="4%" ><font color="white">PLAZO</font></th>
						    <th width="5%" ><font color="white">MONTO DE<br>AFECTACI&Oacute;N</font></th>
				     	</tr>
				     	
				     	<% AsientoRMCBean asiento= (AsientoRMCBean)listadoAsientos.get(t);
				     	   ActoBean acto= (ActoBean)asiento.getActoBean();
				     	   if (acto != null){%>
					     	<tr class=grilla2>
						     	<td align="center"><%= acto.getActoDescripcion() %></td>
						     	<td align="center"><%= acto.getFechaConstitutivo() %></td>
						     	<td align="center">
						     		<% if (acto.getAnoPlazo() != null){ 
						     			 if (acto.getAnoPlazo().equals("0")){ %>
						     			 	&nbsp;   
						     			 <%}else{%>
							     			<%= acto.getAnoPlazo() %>&nbsp; años
							     		<%} %>	
						     		<% }if (acto.getMesPlazo() != null){
						     			  if (acto.getMesPlazo().equals("0")){ %>
						     			    &nbsp; 
						     			  <%}else{%>	
						     				<%= acto.getMesPlazo() %>&nbsp; meses
						     			  <%} %>	
						     		<% }if (acto.getDiaPlazo() != null){
						     			  if (acto.getDiaPlazo().equals("0")){ %>
						     			    &nbsp;
						     			  <%}else{%>	
							     			<%= acto.getDiaPlazo() %>&nbsp; d&iacute;as		       		
							     		  <%} %>	
							     	<% } %>		
						       	</td>
						       	<td align="center">
						       		<% simbolo = acto.getSimboloMonto();%>
						       		<%= acto.getSimboloMonto() %>&nbsp; <%= acto.getMontoAfectacion() %>
						      	</td>
						    </tr>  
						  <%} %>  
					</table>
					<br>
					<table class=grilla cellspacing=0 width="600px" border="1">
				  		<tr bgcolor="#949400">
						    <th width="8%"><font color="white">PARTICIPANTES</font></th>
						    <th width="6%"><font color="white">IDENTIFICACI&Oacute;N</font></th>
						    <th width="6%"><font color="white">TIPO DE<BR>PARTICIPACI&Oacute;N</font></th>
						    <th width="8%"><font color="white">DOMICILIO</font></th>
				     	</tr>
				     	<% AsientoRMCBean asientoParticipante= (AsientoRMCBean)listadoAsientos.get(t);
				     	   ArrayList participantes= (ArrayList)asientoParticipante.getParticipantes();
				     	   if (participantes.size() > 0){
				     	      for (int q=0; q< participantes.size(); q++){
				     	      	ParticipanteBean objparticipante= (ParticipanteBean)participantes.get(q);	%>			     		  	
						     	<tr class=grilla2>
						     		<% if (objparticipante.getTipoPersona().equals("N")){ %>
							     		<td align="center">
							     			<%= objparticipante.getNombre() %>&nbsp;
							     			<%= objparticipante.getApellidoPaterno() %>&nbsp;
							     			<%= objparticipante.getApellidoMaterno() %>
							     		</td>
							     	<%} %>	
							     	<% if (objparticipante.getTipoPersona().equals("J")){ %>
							     	   	<td align="center">
							     	   		<%= objparticipante.getRazonSocial()%>
							     	   	</td>
							     	<%} %>   					     	
							       	<td align="center">
							       		<%if (objparticipante.getDescripcionTipoDocumento()!= null){%>
								       		<%= objparticipante.getDescripcionTipoDocumento()%>
								       	<%}else{ %>	
								       		&nbsp;
								       	<%} 
								       	  if (objparticipante.getNumeroDocumento() != null){ %>
								       	  	 <%= objparticipante.getNumeroDocumento() %>
								       	  <%}else{ %>	 
								       	  	&nbsp;
								       	  <%} %>	
								    </td>
							      	<td align="center"><%= objparticipante.getDescripcionTipoParticipacion() %></td>
							      	<td align="center">
							      		<% if (objparticipante.getDescripcionDomicilio() != null){ %>	
							      			<%= objparticipante.getDescripcionDomicilio() %>
							      		  <%}else{ %>
							      		   	  &nbsp;
							      		   <%} %>	  
							      	</td>
							    </tr>
							<%}
							} %>     	
			    	</table>
			    	<br>
			    	<%int j=0; int k=0; double total=0.00;%>
			    	
			       <table class=grilla cellspacing=0>
			       	<% AsientoRMCBean asientoBien= (AsientoRMCBean)listadoAsientos.get(t);
				       ArrayList bienes= (ArrayList)asientoBien.getBienes();
					   if (bienes.size() > 0){ 
					   	  for (int r=0; r<bienes.size(); r++){
					   	  	  BienBean objbien= (BienBean)bienes.get(r);%>
					   	  	  <% if (objbien.getTipo().equals("E")){ 
					   	  	  		if (j==0){ %>	<!-- solo habria un bien especifico -->       				
				       					<tr>
										    <th width="9%"><font color="white">DESCRIPCI&Oacute;N DEL BIEN (ESPEC&Iacute;FICO)</font></th>
										    <th width="7%"><font color="white">VALORIZACI&Oacute;N</font></th>
								     	</tr>	
								     	<% j++;
								     }if (j > 0){ %>
								       <tr class=grilla2>
								            <% if (j<=10){%>
										         <td align="center">
										         	<%if (objbien.getMarca()!= null){ %>
										         		<%=  objbien.getMarca()%>&nbsp;-&nbsp;
										            <%}else{ %>
										            	&nbsp;
										            <%} %>
										            <%if (objbien.getModelo()!= null){ %>
										         		<%=  objbien.getModelo()%>&nbsp;-&nbsp;
										            <%}else{ %>
										            	&nbsp;
										            <%} %>
										            <%if (objbien.getDescripcion()!= null){ %>
										         		<%=  objbien.getDescripcion()%>&nbsp;
										            <%}else{ %>
										            	&nbsp;
										            <%} %>
										         </td> 								               
										         <td align="center">
										         	<%if (objbien.getMonto()!= null ){ %>
										         		<%=  objbien.getMonto()%>&nbsp;
										         		<% total += Double.parseDouble(objbien.getMonto()); %>
										            <%}else{ %>
										            	<%=  total%>&nbsp;
										            <%} %>
										         </td> 
										    <%}else{ %>
										    	<td align="center">Otros</td> 
										    	<td align="center">&nbsp;</td> <!-- sin valorización -->
										    <% }   %>
										</tr>       
								     <% } 
								 } %>	
				       		<%} 
				       	 } %>	
				       	 
			       	  <% AsientoRMCBean asiento2= (AsientoRMCBean)listadoAsientos.get(t);
				         ArrayList bienes2= (ArrayList)asiento2.getBienes();
					     if (bienes2.size() > 0){ 
					   	    for (int s=0; s<bienes2.size(); s++){
					   	  	    BienBean objbien2= (BienBean)bienes2.get(s);%> 
					   	  	    <% if (objbien2.getTipo().equals("G")){
					       			  if (k==0){ %>
					       				<tr>
										    <th width="9%"><font color="white">DESCRIPCI&Oacute;N DEL BIEN (GEN&Eacute;RICO)</font></th>
										    <th width="7%"><font color="white">VALORIZACI&Oacute;N</font></th>
								     	</tr>	
								     	<% k++;
								       }if (k > 0){ %>
								       <tr class=grilla2>
								          <% if (j+k<=10){%>
									       		<td align="center">
										       		<%if (objbien2.getDescripcion()!= null){ %>
											         	<%=  objbien2.getDescripcion()%>&nbsp;
											         <%}else{ %>
											            	&nbsp;
										             <%} %>
									       			<%if (objbien2.getCantidad()!= null){ %>
											         	: (cantidad : <%=  objbien2.getCantidad()%> )
											         <%}else{ %>
											            : (cantidad : 0 )
										             <%} %>
									       		</td> 
										        <td align="center">
											        <%if (objbien2.getMonto()!= null ){ %>
											         	 <%=  objbien2.getMonto()%>&nbsp;
											         	 <% total += Double.parseDouble(objbien2.getMonto()); %>
											         <%}else{ %>
											            	<%=  total%>&nbsp;
										             <%} %>
										        </td> 
									     	<%}else{ %>
										    	<td align="center">Otros</td> 
										    	<td align="center">&nbsp;</td> <!-- sin valorización -->
										    <% } %>
										 </tr>        
								      <% } %>	
				       			 <%}
				       			} %>
				       		<%} %>	
			       		<tr class=grilla2>
			       			<td align="center" colspan="1"><b>TOTAL</b></td>
			       			<td align="center" colspan="1"><%= simbolo%>&nbsp;<%= total%></td>
			       		</tr>
				   </table>	
				   <br>
				   <!-- Forma y condiciones de ejecucion del bien / pactos especiales -->  	
				    <table class=grilla cellspacing=0 width="600px">	
						<tr bgcolor="#949400">
						    <th colspan="6" width="9%">
						    	<font color="white">FORMA Y CONDICIONES DE EJECUCI&Oacute;N DEL BIEN</font>
						    </th>
						</tr> 
						<% AsientoRMCBean asientoForma= (AsientoRMCBean)listadoAsientos.get(t);
				     	   ActoBean acto2= (ActoBean)asientoForma.getActoBean();
				     	   if (acto2 != null){%>
							 <tr class=grilla2>
					     		<td align="center">
					     			<%if (acto2.getForma()!= null){ %>
					     				<%= acto2.getForma() %>
					     			<%}else{ %>	
					     				&nbsp;
					     			<%} %>
					     			<%if (acto2.getCondicion()!= null){ %>
					     				/&nbsp;<%= acto2.getCondicion() %>
					     			<%}else{ %>	
					     				&nbsp;
					     			<%} %>		
						     	</td>&nbsp;&nbsp;				     		
					      	</tr>
					      <%} %>	
					</table>	
					<br>
					 <!-- Datos del titulo -->  
				   <table class=grilla cellspacing=0 width="600px" border="1">	
						<tr bgcolor="#949400">
						    <th colspan="6" width="9%" align="center">
						    	<font color="white">DATOS DEL T&Iacute;TULO</font>
						   	</th>
						</tr>   
						<tr bgcolor="#949400">
						    <th align="center" width="4%"><font color="white">T&iacute;tulo N°</font></th>
						    <th align="center" width="4%"><font color="white">Orden N°</font></th>
						    <th align="center" width="4%"><font color="white">Fecha de <br>Presentaci&oacute;n</font></th>
						    <th align="center" width="4%"><font color="white">Derechos Pagados</font></th>
						    <th align="center" width="4%"><font color="white">Recibo</font></th>
						    <th align="center" width="4%"><font color="white">Oficina<BR>Registral</font></th>
						</tr>	
						<% AsientoRMCBean asientoTitulo= (AsientoRMCBean)listadoAsientos.get(t);
				     	   TituloBean titulo= (TituloBean)asientoTitulo.getTituloBean();
				     	   if (titulo != null){%>
		  					  <tr class=grilla2>
					     		<td align="center"><%= titulo.getTitulo() %></td>
					     		<td align="center"><%= titulo.getNumeroOrden() %></td>
					     		<td align="center"><%= titulo.getFechaPresentacion() %></td>
					     		<td align="center"><%= titulo.getSimboloMontoPagado() %>&nbsp;<%= titulo.getMontoPagado() %></td>
					     		<td align="center"><%= titulo.getNumeroRecibo() %></td>
					     		<td align="center"><%= titulo.getOficReg_nombre() %></td>
						  	  </tr>
		   			  	<%} %>
		   		   </table> 
		   		   <br>
		   		      <!-- Documentos que dan merito a inscripcion -->  
				    <table class=grilla cellspacing=0 width="600px">	
						<tr bgcolor="#949400">
						    <th colspan="3" width="9%"><font color="white">DOCUMENTOS QUE DAN M&Eacute;RITO A INSCRIPCI&Oacute;N</font></th>
						</tr>   
						<tr bgcolor="#949400">
						    <th align="center" width="4%"><font color="white">Documento</font></th>
						    <th align="center"width="4%"><font color="white">Funcionario</font></th>
					        <th align="center" width="4%"><font color="white">Fecha</font></th>
						</tr>	
					  <% AsientoRMCBean asientoDocumentos= (AsientoRMCBean)listadoAsientos.get(t);
				         ArrayList documentos= (ArrayList)asientoDocumentos.getDocumentosFuncionarios();
					     if (documentos.size() > 0){ 
					   	    for (int p=0; p<documentos.size(); p++){
					   	  	    DocumentoFuncionarioBean objdocumento= (DocumentoFuncionarioBean)documentos.get(p);%> 
								<tr class=grilla2>
						     		<td align="center"><%= objdocumento.getDocumento() %></td>
						     		<td align="center">
						     			<%if (objdocumento.getTipoFuncionario()!=null){ %>
						     				<%= objdocumento.getTipoFuncionario() %>
						     			<%}else{ %>	&nbsp;
						     			<%} %>
						     			<%if (objdocumento.getFuncionario()!=null){ %>
						     				:&nbsp;<%= objdocumento.getFuncionario() %>
						     			<%}else{ %>	&nbsp;
						     			<%} %>					     			
						     		</td>
						     		<td align="center"><%= objdocumento.getFecha() %></td>
						     	</tr>
						    <%}
						  } %>
					</table>
					<br>  
					
			  <%}
			 } %>	
		  <!-- Final de la pagina --> 
	
		   <%
		   String numpartidaMigrado= objpartida.getNumPartidaMigrado();%>
		   <% if ( numpartidaMigrado!= null  ){%>
		  	    <tr>	
				   <td><font color="black"><b>Partida</b></td>
				   <td><b><%= numpartidaMigrado %>&nbsp;</b></td>
				   <td>Migrada hacia la partida&nbsp;<b><%= objpartida.getNumPartida()%></b></td>
				   <td><font color="black"><b>Visualizar Asiento&nbsp;&nbsp;
		   		  	   <input name="image2" type="image" onclick="VerPartida('<bean:write name="output" property="partidaBean.refNumPartMigrado"/>','<bean:write name="output" property="partidaBean.estado"/>')" value="Visualizar" src="<%=request.getContextPath()%>/images/lupa.gif" style="border:0" onmouseover="javascript:mensaje_status('Visualizar Asiento');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
				   	   </b></font>
				   </td>
			     </tr>	
		  <% } %>    
	  	</logic:present>
	</table> 
<table class=tablasinestilo>
  <tr>
	<td width="50%" align="right">
		<div id="HOJA3">	
			<input type="button" class="formbutton" onclick="javascript:Regresa()" value="Regresar" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		</div>
	</td>
  </tr>
</table>
	<script>
	window.top.frames[0].location.reload();
	</script>
	</div>
</div>
</BODY>
</HTML>