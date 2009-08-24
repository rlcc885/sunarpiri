<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ page import="java.util.*" %>
<%@ page import="gob.pe.sunarp.extranet.util.*" %>

<%
FormOutputListado output = (FormOutputListado)	request.getAttribute("output");
Vector vec          = output.getLista();
String[] cabezas    = output.getCabezas();
%>

<HTML>
<HEAD>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<TITLE></TITLE>

<script language="JavaScript">

function proceso(codPro, k1, k2, k3, k4, k5, k6, k7, k8, k9 ,k10)
{
	document.frm1.P1.value = codPro;
	document.frm1.PK1.value = k1;
	document.frm1.PK2.value = k2;
	document.frm1.PK3.value = k3;
	document.frm1.PK4.value = k4;
	document.frm1.PK5.value = k5;
	document.frm1.PK6.value = k6;
	document.frm1.PK7.value = k7;
	document.frm1.PK8.value = k8;
	document.frm1.PK9.value = k9;
	document.frm1.PK10.value = k10;

	var v = true;
		
	if (codPro=='3')
		v = confirm("¿Esta seguro que desea Eliminar el registro?");
	
	if (v==true)	
		document.frm1.submit();
}

function paginar(numero)
{
	document.frmPagineo.PP.value=numero;
	document.frmPagineo.submit();
}

</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>

<BODY>
<BR>
<table class=titulo>
<tr><td><FONT color=black>ADMINISTRACI&Oacute;N EXTRANET &gt;&gt;Mantenimiento de tablas &gt;&gt; </FONT> Tabla <%=output.getNombreTabla()%></td></tr>
</table>
<!--<table class=titulo>
  <tr> 
    <td>Mantenimiento de tabla : <%=output.getNombreTabla()%></td>
    <td>LISTADO</td>
  </tr>
</table>-->
<br>
<a href="javascript:proceso('4','','','','','','','','','','')">Insertar Nuevo Registro</a>
<br>

<%--
Total de registros : <%=output.getCantidadRegistros()%>
<br>--%>

<table class=grilla>
<tr class=grilla2>
	                              <th><%=cabezas[0]%></th>
	<%if (cabezas.length>=2)  {%> <th><%=cabezas[1]%></th> <%}%>
	<%if (cabezas.length>=3)  {%> <th><%=cabezas[2]%></th> <%}%>
	<%if (cabezas.length>=4)  {%> <th><%=cabezas[3]%></th> <%}%>
	<%if (cabezas.length>=5)  {%> <th><%=cabezas[4]%></th> <%}%>
	<%if (cabezas.length>=6)  {%> <th><%=cabezas[5]%></th> <%}%>
	<%if (cabezas.length>=7)  {%> <th><%=cabezas[6]%></th> <%}%>
	<%if (cabezas.length>=8)  {%> <th><%=cabezas[7]%></th> <%}%>
	<%if (cabezas.length>=9)  {%> <th><%=cabezas[8]%></th> <%}%>
	<%if (cabezas.length>=10) {%> <th><%=cabezas[9]%></th> <%}%>
									<th></th>
									<th></th>
								<%--	<th></th> --%>
</tr>

  <%
  //atributos de diseno
  	  	boolean flag1=false;
	  	String dFila, dColumna;
	  	
  for (int i=0; i < vec.size(); i++)
  	{
  	if (flag1==false)
					{
						dFila="";
						dColumna="bgcolor='#e2e2e2'";
					}
				else
					{
						dFila="class=grilla2";
						dColumna="";
					}
	flag1 = !flag1;
  	GenericBean bean = (GenericBean) vec.elementAt(i);%>
<tr <%=dFila%>>  	
	                              <td <%=dColumna%>><%=bean.getValor01()%></td>
	<%if (cabezas.length>=2)  {%> <td <%=dColumna%>><%=bean.getValor02()%></td> <%}%>
	<%if (cabezas.length>=3)  {%> <td <%=dColumna%>><%=bean.getValor03()%></td> <%}%>
	<%if (cabezas.length>=4)  {%> <td <%=dColumna%>><%=bean.getValor04()%></td> <%}%>
	<%if (cabezas.length>=5)  {%> <td <%=dColumna%>><%=bean.getValor05()%></td> <%}%>
	<%if (cabezas.length>=6)  {%> <td <%=dColumna%>><%=bean.getValor06()%></td> <%}%>
	<%if (cabezas.length>=7)  {%> <td <%=dColumna%>><%=bean.getValor07()%></td> <%}%>
	<%if (cabezas.length>=8)  {%> <td <%=dColumna%>><%=bean.getValor08()%></td> <%}%>
	<%if (cabezas.length>=9)  {%> <td <%=dColumna%>><%=bean.getValor09()%></td> <%}%>
	<%if (cabezas.length>=10) {%> <td <%=dColumna%>><%=bean.getValor10()%></td> <%}%>  	
  	
<!-- view detail of the record -->
<td <%=dColumna%>>
<a href="javascript:proceso('1','<%=bean.getLlave01()%>','<%=bean.getLlave02()%>','<%=bean.getLlave03()%>','<%=bean.getLlave04()%>','<%=bean.getLlave05()%>','<%=bean.getLlave06()%>','<%=bean.getLlave07()%>','<%=bean.getLlave08()%>','<%=bean.getLlave09()%>','<%=bean.getLlave10()%>')">Ver detalle</a>
</td>

<!-- update record -->
<td <%=dColumna%>>
<a href="javascript:proceso('2','<%=bean.getLlave01()%>','<%=bean.getLlave02()%>','<%=bean.getLlave03()%>','<%=bean.getLlave04()%>','<%=bean.getLlave05()%>','<%=bean.getLlave06()%>','<%=bean.getLlave07()%>','<%=bean.getLlave08()%>','<%=bean.getLlave09()%>','<%=bean.getLlave10()%>')">Actualizar</a>
</td>

<%-- la opcion de borrado NO  se muestra
<td <%=dColumna%>>
<a href="javascript:proceso('3','<%=bean.getLlave01()%>','<%=bean.getLlave02()%>','<%=bean.getLlave03()%>','<%=bean.getLlave04()%>','<%=bean.getLlave05()%>','<%=bean.getLlave06()%>','<%=bean.getLlave07()%>','<%=bean.getLlave08()%>','<%=bean.getLlave09()%>','<%=bean.getLlave10()%>')">Eliminar</a>
</td>   
--%>
   
</tr>
<% } %>
</table>

<%-- formulario para detalle--%>
<form name="frm1" method="post" action="/iri/ManteTabla.do?state=inicioProceso">
	<input type="hidden" name="P0" value ="<%=output.getCodTabla()%>">
	<input type="hidden" name="P1">
	<input type="hidden" name="PK1">
	<input type="hidden" name="PK2">
	<input type="hidden" name="PK3">
	<input type="hidden" name="PK4">
	<input type="hidden" name="PK5">
	<input type="hidden" name="PK6">
	<input type="hidden" name="PK7">
	<input type="hidden" name="PK8">
	<input type="hidden" name="PK9">
	<input type="hidden" name="PK10">
</form>

<%-- formulario para pagineo --%>
<br>
<form  name="frmPagineo" method="post" action="/iri/ManteTabla.do?state=listado" class="tablasinestilo">
	<input type="hidden" name="P0"    value ="<%=output.getCodTabla()%>">
	<input type="hidden" name="PP" value="1">
	<table width="100%">    
	 <tr>
	    <td width="50%" align="left">
	    <% if (output.getPagAnterior() >= 0) {%>
	    	<a href="javascript:paginar('<%=output.getPagAnterior()%>')" target="_self" onmouseover="javascript:mensaje_status('Anterior');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">anterior</a>
	    <% } else {%>
	    &nbsp;
	    <% } %>
	    </td>
	    <td width="50%" align="right">
	    <% if (output.getPagSiguiente() >= 0) {%>
	    	<a href="javascript:paginar('<%=output.getPagSiguiente()%>')" target="_self" onmouseover="javascript:mensaje_status('Siguiente');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">siguiente</a>
	    <% } else {%>
	    &nbsp;
	    <% } %>
	    </td>
	  </tr>
	</table> 
</form>

</BODY>
</HTML>