<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<LINK REL="stylesheet" type="text/css" href="styles/global.css">
	<title></title>
	<script language="javascript">
		function Cerrar() {
			parent.close();
		}
	</script>
</head>

<body>
<logic:present name="hayImagen">
	<br>
	<form name="frm" onSubmit="javascript:Zoom(document.frm.zoom.value);return false;">
		<input type=hidden name="TxtNombreImagen">

			<table border="0" width="100%">
				<tr>
					<td>
						<center>Ver al: 
							<input type="text" size="3" name="zoom" onChange="Zoom(this.value)"> %
						    &nbsp;&nbsp;&nbsp;<A href="javascript:Cerrar();">[Cerrar]</A>
						</center>
					</td>
					<td>
						<a href="javascript:window.print();"><img src="<%=request.getContextPath()%>/images/btn_print.gif" width="83" height="25" hspace="4"></a>
					</td>
				</tr>
			</table>
			<br>
			<img 
				src="/iri/VerEsquela.do?state=verEsquela&refnumtitu=<bean:write name="refnumtitu"/>&tipoesquela=<bean:write name="tipoesquela"/>&arearegid=<bean:write name="arearegid"/>"
				name=img1 
				height="1318"
				width="1018"
			>
	</form>
 
			<script language=javascript>
				var Xx = document.img1.width 
				var Yy = document.img1.height 
				function Zoom(porcentaje){
					if (isNaN(porcentaje)) {
						document.frm.zoom.focus();
						alert ("Por favor, ingrese un porcentaje v&acute;lido.");
						return;
					}
					if (porcentaje > 100) {
						document.frm.zoom.focus();
						alert ("El porcentaje de visualizacion no puede ser mayor al 100%");
						return;
					} 
					if (porcentaje < 1) {
						document.frm.zoom.focus();
						alert ("El porcentaje de visualizacion no puede ser menor al 1%");
						return;
					}
					factor = porcentaje / 100;
					document.img1.width = Math.round(Xx*factor);
					document.img1.height = Math.round(Yy*factor);
				}
			</script>
</logic:present>
<logic:notPresent name="hayImagen">
	<script language=javascript>
		alert('No existe imagen de Esquela en la Tabla ESQUELA');
		Cerrar();
	</script>
</logic:notPresent>			
</body>
</html>

