<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<LINK REL="stylesheet" type="text/css" href="styles/global.css">
	<title></title>
	
	
	
<script language="javascript">
function Cerrar() 
		{
			parent.close();
		}

function paginea(nump)
{
	document.img1.src="/iri/VerEsquela.do?state=verEsquela&refnumtitu=<%=request.getAttribute("refnumtitu")%>&tipoesquela=<%=request.getAttribute("tipoesquela")%>&arearegid=<%=request.getAttribute("arearegid")%>&pagina="+nump;
}		
</script>
</head>

<body>

<%
String hayImagen = (String) request.getAttribute("hayImagen"); 

if (hayImagen==null) { %>
	<script language=javascript>
		alert('No existe imagen de Esquela en la Tabla ESQUELA');
		Cerrar();
	</script>
<% } 


else 




{

String xp = (String) request.getAttribute("totalpags");
int totalpags = Integer.parseInt(xp);

%>
<br>
<form name="frm" onSubmit="javascript:Zoom(document.frm.zoom.value);return false;">
		<input type=hidden name="TxtNombreImagen">
			<table border="0" width="100%">
				<tr>
					<td>Ver al: <input type="text" size="3" name="zoom" onChange="Zoom(this.value)"> % </td>
<%if (totalpags>1) 
 { %>
	<td>&nbsp;&nbsp;&nbsp;Ver página No.:&nbsp;
	<% for (int w=1; w<= totalpags; w++) {%>
	<A href="javascript:paginea(<%=w%>);"><%=w%></A>&nbsp;
	<%  } %>
    </td>
<%} %>
					<td>&nbsp;&nbsp;&nbsp;<A href="javascript:Cerrar();">[Cerrar]</A></td>
					<td><a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25" hspace="4"></a></td>
				</tr>
			</table>
			<br>
			<img 
				src="/iri/VerEsquela.do?state=verEsquela&refnumtitu=<%=request.getAttribute("refnumtitu")%>&tipoesquela=<%=request.getAttribute("tipoesquela")%>&arearegid=<%=request.getAttribute("arearegid")%>"
				name="img1"
				height="1318"
				width="1018"
			>
</form>


<script language="javascript">
var xx = document.img1.width;
var yy = document.img1.height;

function Zoom(porcentaje)
{
					if (isNaN(porcentaje)) 
					{
						document.frm.zoom.focus();
						alert ("Por favor, ingrese un porcentaje válido.");
						return;
					}
					if (porcentaje > 100) 
					{
						document.frm.zoom.focus();
						alert ("El porcentaje de visualización no puede ser mayor al 100%");
						return;
					} 
					if (porcentaje < 1) 
					{
						document.frm.zoom.focus();
						alert ("El porcentaje de visualización no puede ser menor al 1%");
						return;
					}
					factor = porcentaje / 100;
					document.img1.width  = Math.round(xx*factor);
					document.img1.height = Math.round(yy*factor);
}		
</script>
<%  }  %>




</body>
</html>
