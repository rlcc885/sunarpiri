<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="java.util.*" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title></title>

<script language="javascript">

function Ordenar(){ 
	document.form1.method="POST";
	document.form1.action="/iri/BusquedaTitulo.do?state=buscaOrden&pagina=1";
	document.form1.submit();
}

function ShowReport(pagina){
	
	/* Comentado el 2004/03/29 al momento de corregir problema de siguiente
	if(document.form1.orden.checked == true)
	{
	*/ 
		document.form1.method="POST";
		document.form1.action="/iri/BusquedaTitulo.do?state=buscaOrden&pagina=" + pagina;
		document.form1.submit();
	/*
	}
	else
	{
		alert("No se ha seleccionado el check");	
	}
	*/
}
function VerEsquela(params)
{
	ventana=window.open('/iri/VerEsquela.do?state=mostrarEsquela' + params,'1024x768','status=yes,scrollbars=yes,resizable=yes,location=no,directories=no,width=950,height=650, top=0, left=0');
}
function Imprimir()
{
	HOJA2.style.visibility="hidden";
	HOJA3.style.visibility="hidden";
	window.print();
	HOJA2.style.visibility="visible";
	HOJA3.style.visibility="visible";	
}
function Regresa()
{ 
	history.back();
}

</script>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
<div id="maincontent">
	<div class="innertube">
    	<b><font color="#949400">SERVICIOS &gt;&gt;</font><font color="#666666">Consulta de Estado de T&iacute;tulos &gt;&gt; Resultado de T&iacute;tulo</b>
		<br>
		<table border=0 width=600>
		  <tr> 
			    <td vAlign=top align=left width = 10%><font color="black"><b>Costo</b></font><BR>S/.&nbsp;<%=request.getAttribute("tarifa")%></td>
			    <td vAlign=top align=left width = 15%><font color="black"><b>Usuario</b></font><BR><%=request.getAttribute("usuaEtiq")%></td>
			    <td vAlign=top align=left width = 20%><font color="black"><b>Fecha Actual</b></font><BR><%=request.getAttribute("fechaAct")%></td>
			    <td vAlign=top align=left width = 55%></td>
		  </tr>
		</table>
		<br>
		<form name="form1">
		<logic:present name="encontro">
			<logic:present name="tamano">
				<input type="hidden" name="tamano" value="<bean:write name="tamano"/>">
			</logic:present>
		<table class="tablasinestilo">
		  <tr> 
		    <td valign="middle">
		    <input type="checkbox" name="orden" <logic:present name="orden">checked</logic:present>>ORDENAR POR ESTADO&nbsp; 
		    <input type="button" class="formbutton" value="Aplicar" onclick="javascript:Ordenar();" onmouseover="javascript:mensaje_status('Ordenar por Estado de Titulo');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		    </td>
		  </tr>
		</table>
		</logic:present>
		<logic:present name="tipo">
			<input type="hidden" name="tipo" value="<bean:write name="tipo"/>">
		</logic:present>
		
		<logic:present name="hid1">
			<input type="hidden" name="hid1" value="<bean:write name="hid1"/>">
		</logic:present>
		
		<logic:present name="tipob">
			<input type="hidden" name="tipob" value="<bean:write name="tipob"/>">
		</logic:present>
		
		<logic:present name="ordOficina">
			<input type="hidden" name="ordOficina" value="<bean:write name="ordOficina"/>">
		</logic:present>
		
		<!-- Inicio:jascencio:06/09/2007  CC:SUNARP-REGMOBCON-2006-->
		<logic:present name="regPub">
			<logic:present name="ordOficina">
				<input type="hidden" name="oficinas" value="<bean:write name="regPub"/>|<bean:write name="ordOficina"/>|">
			</logic:present>
		</logic:present>
		
		<!-- Fin:jascencio -->
		
		<logic:present name="regPub">
			<input type="hidden" name="regPub" value="<bean:write name="regPub"/>">
		</logic:present>
		
		<%-- Para NroTitulo --%>
		<logic:present name="ano">
			<input type="hidden" name="ano" value="<bean:write name="ano"/>">
		</logic:present>
		
		<logic:present name="numtitu">
			<input type="hidden" name="numtitu" value="<bean:write name="numtitu"/>">
		</logic:present>
		
		<%-- Para AreaRegistral --%>
		<logic:present name="areareg">
			<input type="hidden" name="areareg" value="<bean:write name="areareg"/>">
		</logic:present>
		
		<%-- Para Presentante Natural--%>
		<logic:present name="nombres">
			<input type="hidden" name="nombres" value="<bean:write name="nombres"/>">
		</logic:present>
		
		<logic:present name="apepat">
			<input type="hidden" name="apepat" value="<bean:write name="apepat"/>">
		</logic:present>
		
		<logic:present name="apemat">
			<input type="hidden" name="apemat" value="<bean:write name="apemat"/>">
		</logic:present>
		
		<logic:present name="tipdocpn">
			<input type="hidden" name="tipdocpn" value="<bean:write name="tipdocpn"/>">
		</logic:present>
		
		<logic:present name="numdocpn">
			<input type="hidden" name="numdocpn" value="<bean:write name="numdocpn"/>">
		</logic:present>
		
		<%-- Para Presentante Juridico --%>
		<logic:present name="ruc">
			<input type="hidden" name="ruc" value="<bean:write name="ruc"/>">
		</logic:present>
		
		<logic:present name="tipdocpj">
			<input type="hidden" name="tipdocpj" value="<bean:write name="tipdocpj"/>">
		</logic:present>
		
		<logic:present name="razsoc">
			<input type="hidden" name="razsoc" value="<bean:write name="razsoc"/>">
		</logic:present>
		
		<logic:present name="siglas">
			<input type="hidden" name="siglas" value="<bean:write name="siglas"/>">
		</logic:present>
		
		<%-- Para Participante Natural --%>
		<logic:present name="nombrep">
			<input type="hidden" name="nombrep" value="<bean:write name="nombrep"/>">
		</logic:present>
		
		<logic:present name="apepatp">
			<input type="hidden" name="apepatp" value="<bean:write name="apepatp"/>">
		</logic:present>
		
		<logic:present name="apematp">
			<input type="hidden" name="apematp" value="<bean:write name="apematp"/>">
		</logic:present>
		
		<logic:present name="tipdocpnp">
			<input type="hidden" name="tipdocpnp" value="<bean:write name="tipdocpnp"/>">
		</logic:present>
		
		<logic:present name="numdocpnp">
			<input type="hidden" name="numdocpnp" value="<bean:write name="numdocpnp"/>">
		</logic:present>
		
		<%-- Para Participante Juridico --%>
		<logic:present name="rucp">
			<input type="hidden" name="rucp" value="<bean:write name="rucp"/>">
		</logic:present>
		
		<logic:present name="tipdocpjp">
			<input type="hidden" name="tipdocpjp" value="<bean:write name="tipdocpjp"/>">
		</logic:present>
		
		<logic:present name="razsocp">
			<input type="hidden" name="razsocp" value="<bean:write name="razsocp"/>">
		</logic:present>
		
		<logic:present name="siglasp">
			<input type="hidden" name="siglasp" value="<bean:write name="siglasp"/>">
		</logic:present>
		</form>
		
		
		<logic:present name="encontro">
		
		<% java.util.List lista1 = (java.util.List) request.getAttribute("lista"); %>
		
		Total de registros encontrados : <bean:write name="numeroderegistros"/>
		
		<table cellspacing=0 border="1">
			<tr bgcolor="#949400">
	  			<th width="8%"><font color="white">Presentante</font></th>
			    <th width="6%"><font color="white">Ofic. Reg.</font></th>
			    <th width="6%"><font color="white">A&ntilde;o</font></th>
			    <th width="10%"><font color="white">T&iacute;tulo</font></th>
			    <th width="8%"><font color="white">Tpo <br>Reg.</font></th>
			    <!--No borrar
			    	<th width="8%">Tipo Acto</th>
			    -->
			    <th width="10%"><font color="white">Acto Registral</font></th>
			    <th width="7%"><font color="white">Partida</font></th>
			    <th width="10%"><font color="white">Fec/Hor Pres.</font></th>
			    <th width="7%"><font color="white">Fec. Venc.</font></th>
				<!--No borrar
			    	<th width="6%">Participante</th>
			    -->
			    <th width="6%"><font color="white">Estado</font></th>
			<!--    <th width="7%">SEDE</th>-->
			    <td width="9%"></td>
				<td width="9%"></td>
		  </tr>
		
		<% for (int w=0; w < lista1.size(); w++ ) {
			gob.pe.sunarp.extranet.publicidad.bean.GeneralTituloBean item1 = (gob.pe.sunarp.extranet.publicidad.bean.GeneralTituloBean) lista1.get(w); %>
		  <tr  class="grilla2"> 
		  
		  	<!--    Colocar el codigo para diferecnciar PN y PJ ---- JACR -->  	
		    <td width="10%" align="center"><%=item1.getPresentante()%></td>
		    <td width="6%" align="center" ><%=item1.getDependencia()%></td>
		    <td width="6%" align="center" ><%=item1.getAno()%></td>
		    <td width="10%" align="center"><%=item1.getTitulo()%></td>
		    <td width="8%" align="center" ><%=item1.getTipo_registro()%></td>
		    <%--No Borrar
		    	<td width="8%" align="center" ><%=item1.getTipo_acto()%></td>
		    --%>
		    <td width="10%" align="center">
		    <%//Inicio:jascencio:16/07/07
		      //CC:REGMOBCON-2006
		    List listaActos=item1.getListadoActos();
		    	int tamano=0;
		    	StringBuffer descripcionActo=null;
		    	if(listaActos!=null){
		    		descripcionActo=new StringBuffer();
		    		tamano=listaActos.size();
		    		descripcionActo.append(listaActos.get(0));
		    		if(tamano!=1){
						descripcionActo.append(" Y OTROS");				
		    		}
		    		%>
		    		<%=descripcionActo.toString()%>
		    		<%
		    	}else{%>&nbsp;<%}
		    	//Fin:jascencio
		     %>
		    </td>
		    <td width="7%" align="center" ><%=item1.getPartida()%></td>
		    <td width="10%" align="center"><%=item1.getFec_presentacion()%></td>
		    <td width="7%" align="center" ><%=item1.getFec_vencimiento()%></td>
		    <%--No Borrar
		    	<td width="6%" align="center" ><%=item1.getParticipante()%></td>
		    --%>
		    <td width="6%" align="center" ><%=item1.getEstado()%></td>
		<%--    <td width="7%" align="center"><bean:write name="item1" property="sede"/></td> --%>
		    <td width="9%" align="center" ><a href="/iri/BusquedaTitulo.do?state=buscarXNroTituloDet&oficinas=<%=item1.getReg_pub_id()%>|<%=item1.getOfic_reg_id()%>|<%=item1.getSede()%>&ano=<%=item1.getAno()%>&numtitu=<%=item1.getTitulo()%>&areareg=<%=item1.getArea_Reg()%>&pagina=1" onmouseover="javascript:mensaje_status('Ver Detalle');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Ver Detalle</a></td>
		 
		    <%if (item1.getUrl_detalle().trim().length()==0) {%>   
			<td width="9%" align="center">&nbsp;</td>
			<% }else{ %>
			<td width="9%" align="center">
				<a href="javascript:VerEsquela('<%=item1.getUrl_detalle()%>');" onmouseover="javascript:mensaje_status('Ver Esquela');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><%=item1.getUrl_esquela()%></a>
			</td> 
			<% } %>
		  </tr>
		<% } %>
		</table>
		
		</logic:present>
		<!-- ************************ P A G I N A C I O N ******************************* -->
		<logic:present name="encontro">
			<logic:present name="pagdetalle">
				<bean:write name="pagdetalle"/>
			</logic:present>
		</logic:present>
		<center>
		<table  class="tablasinestilo">
		<tr>
		<td>
		<logic:present name="previous">
			<a href="javascript:ShowReport(<bean:write name="previous"/>);" onmouseover="javascript:mensaje_status('Anteriores');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Anteriores</a>
		</logic:present>
		</td>
		<td>
		<logic:present name="next">
			<a href="javascript:ShowReport(<bean:write name="next"/>);" onmouseover="javascript:mensaje_status('Siguientes');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">Siguientes</a>
		</logic:present>
		</td>
		</tr>
		</table>
		</center>
		<!-- ************************ P A G I N A C I O N ******************************* -->
		
		<logic:notPresent name="encontro">
		<table class="tablasinestilo">
		  <tr> 
		    <td align="right"><font color="#666666">Su b&uacute;squeda no contiene resultados.</font></td>
		    <td align="right">
		    	<input type="button" class="formbutton" value="Regresar" onclick="javascript:Regresa();" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"/>
		    </td>
		  </tr>
		</table>
		</logic:notPresent>
		
		<logic:present name="encontro">
		<table class=tablasinestilo>
		  <tr>
		  	<td width="50%" align="left">
		  	<div id="HOJA2"> 
		  	<a href="javascript:Imprimir()" onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_print.gif" width=83 align=absMiddle vspace=5 border=0></a>
		  	</div></td>
			<td width="50%" align="right">
			<div id="HOJA3">	
			<a href="javascript:Regresa()" onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"><IMG height=25 hspace=4 src="images/btn_regresa.gif" width=83 align=absMiddle vspace=5 border=0></a>
			</div></td>
		  </tr>
		</table>
		</logic:present>
	</div>
</div>

</body>
</html>