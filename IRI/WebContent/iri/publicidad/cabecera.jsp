<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
	
	<title></title>
	<script language="javascript">
		function Cerrar() 
		{
			parent.close();
		}
		function ShowImage() 
		{ 
			imagen.style.visibility="visible"
		}
		function HideImage() 
		{
			imagen.style.visibility="hidden";
		}

		function AbrirVP() {
			document.frm.TxtNombreImagen.value = "/iri/PublicidadIRI.do?state=muestraImagen&accion=<%= request.getParameter("accion") %>&noSolicitud=<%= request.getParameter("noSolicitud") %>&objetoID=<%= request.getParameter("objetoID") %>&numPagina=<%= request.getParameter("numPagina") %>&noPgna=<%= request.getParameter("noPgna") %>&noPgnaTotal=<%= request.getParameter("noPgnaTotal") %>&noPartida=<%= request.getParameter("noPartida") %>&indexClass=<%= request.getParameter("indexClass") %>";
		  	ValorFlotante = window.open("/iri/PublicidadIRI.do?state=impresion",'VP','resizable=no,toolbar=no,scrollbars=yes,width=600,height=470,top=20,left=20,status=yes,menubar=yes,location=no');
			if (parseInt(navigator.appVersion) >= 4) 
			{ 
				ValorFlotante.focus(); 
			}
		}
		
		function terminoMostrarImagen() 
		{
			<logic:present name="hayImagen">
					parent.top_nav.location.reload();
					parent.opener.parent.parent.top_frame.location.reload();
			</logic:present>
			window.focus();
		}
	</script>
</head>

<body onload="terminoMostrarImagen()">
 
	<form name="frm" onSubmit="javascript:Zoom(document.frm.zoom.value);return false;">

		<input type=hidden name="TxtNombreImagen">

		<table border="0" width="100%">
		<tr>
			<th width="100%"><H3 align="center"><bean:write name="OFICREGNOMBRE" scope="session"/></H3></th>
		</tr>
		<tr>
			<td width="100%">
				<logic:notEqual name="costo" value="0">
				<b>El costo unitario por visualizaci&oacute;n de&nbsp; cada imagen de ficha o folio es de 
				<font color="#800000"><bean:write name="costo" scope="request"/></font> soles. El costo por
				visualizacion de asiento electrónico es de <font color="#800000"><bean:write name="costo" scope="request"/></font> soles.
				</logic:notEqual>
				Para visualizar la p&aacute;gina deseada haga click en el link respectivo de la estructura
				del lado izquierdo de su pantalla.</br>
			</td>
		</tr>
		</table>

		<hr>

	<!--	<span id="imagen"> -->
		<logic:present name="hayImagen">
			<% 	
				int contador = Integer.parseInt( request.getParameter("contador") ) ;
				int ultimo = Integer.parseInt( request.getParameter("ultimo") ) ;
				String anterior = null;
				String posterior = null;
				if (contador > 0) anterior = new Integer(contador-1).toString();
				if (contador < ultimo) posterior = new Integer(contador+1).toString();
			%>
			<table border="0" width="100%">
				<tr><td>
					<center>Ver al: 
							<input type="text" size="3" name="zoom" onChange="Zoom(this.value)"> %
					<!--	<select name=zoom onChange="Zoom(this.value)">
							<option value=1.5>150%</option>
							<option value=1.25>125%</option>
							<option value=1 selected>100%</option>
							<option value=0.75>75%</option>
							<option value=0.5>50%</option>
							<option value=0.25>25%</option>
						</select> -->
		<%	if (contador != 0) { %>
		&nbsp;&nbsp;&nbsp;
		
		<A href="javascript:parent.main_nav.callVisualizar0(<%= ultimo %>,'',<%= ultimo + 1%>);">[&lt;&lt;]</A>
		<%	} %>
		<%	if (anterior != null) { %>
		&nbsp;&nbsp;&nbsp;<A href="javascript:parent.main_nav.callVisualizar<%= anterior %>(<%= ultimo %>,'',<%= ultimo - Integer.parseInt(anterior) + 1%>);">[&lt;]</A>
		<%	} %>
	    &nbsp;&nbsp;&nbsp;<A href="javascript:AbrirVP();">[Imprimir Asiento]</A>
		<%	if (posterior != null) { %>
	    &nbsp;&nbsp;&nbsp;<A href="javascript:parent.main_nav.callVisualizar<%= posterior %>(<%= ultimo %>,'',<%= ultimo - Integer.parseInt(posterior) + 1%>);">[&gt;]</A>
		<%	} %>
		<%	if (contador != ultimo) { %>
	    &nbsp;&nbsp;&nbsp;<A href="javascript:parent.main_nav.callVisualizar<%= ultimo %>(<%= ultimo %>,'',1);">[&gt;&gt;]</A>
		<%	} %>
	    &nbsp;&nbsp;&nbsp;<A href="javascript:Cerrar();">[Cerrar]</A>
					</center>
				</td></tr>
			</table>

			<br>
 
			<img 
				src="/iri/PublicidadIRI.do?state=muestraImagen&accion=<%= request.getParameter("accion") %>&noSolicitud=<%=request.getParameter("noSolicitud")%>&objetoID=<%= request.getParameter("objetoID") %>&numPagina=<%= request.getParameter("numPagina") %>&indexClass=<%= request.getParameter("indexClass") %>&noPgna=<%= request.getAttribute("noPgna") %>&noPgnaTotal=<%= request.getAttribute("noPgnaTotal") %>&noPartida=<%= request.getAttribute("noPartida") %>"
				name=img1
				height="<bean:write name="height" scope="request"/>"
				width="<bean:write name="width" scope="request"/>" 
				
			>
		</logic:present>
<!--		</span> -->

	</form>
 
		<logic:present name="hayImagen">
	<script language=javascript>

		var Xx = document.img1.width 
		var Yy = document.img1.height 
/*		var mXx = Math.round(document.img1.width / 4)
		var mYy = Math.round(document.img1.height / 4)
		document.img1.width=mXx
		document.img1.height = mYy

		function ZoomOUT(){
			document.img1.width = document.img1.width + Math.round(Xx/4)
			if (document.img1.width > Xx ) {
				document.img1.width=Xx
			}
			document.img1.height = document.img1.height + Math.round(Yy/4)
			if (document.img1.height > Yy) {
				document.img1.height = Yy
			}
		}

		function ZoomIN(){
			document.img1.width = document.img1.width - Math.round(Xx/4)
			if (document.img1.width < mXx ) {
				document.img1.width = mXx
			}
			document.img1.height = document.img1.height - Math.round(Xx/4)
			if (document.img1.height < mYy) {
				document.img1.height = mYy
			}
		}
*/	
		function Zoom(porcentaje){
			if (isNaN(porcentaje)) {
				document.frm.zoom.focus();
				alert ("Por favor, ingrese un porcentaje válido.");
				return;
			}
			if (porcentaje > 100) {
				document.frm.zoom.focus();
				alert ("El porcentaje de visualización no puede ser mayor al 100%");
				return;
			} 
			if (porcentaje < 1) {
				document.frm.zoom.focus();
				alert ("El porcentaje de visualización no puede ser menor al 1%");
				return;
			}
			factor = porcentaje / 100;
			document.img1.width = Math.round(Xx*factor)
			document.img1.height = Math.round(Yy*factor)
		}
		document.frm.zoom.value=<bean:write name="ZOOMDEFAULT" scope="request"/>;
		Zoom(<bean:write name="ZOOMDEFAULT" scope="request"/>);
	</script>
		</logic:present>
</body>
</html>