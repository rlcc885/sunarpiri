<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>Visualizaci&oacute;n de Partida</TITLE>


<title>Visualizaci&oacute;n de Partidas</title></head>

<html>


<FRAMESET border=1 frameSpacing=0 cols=20%,* frameBorder=0>
	<FRAMESET border=1 frameSpacing=0 rows=20%,* frameBorder=0>
		<FRAME name=top_nav marginWidth=0 marginHeight=0 src="/iri/Publicidad.do?state=muestraUsuarioMasSaldo" frameBorder=yes noResize scrolling=no>
		<FRAME name=main_nav marginWidth=0 marginHeight=0 src="/iri/Publicidad.do?state=muestraIndiceAsientos&accion=<%= request.getParameter("accion") %>&noSolicitud=<%= request.getParameter("noSolicitud") %>&objetoId=<%= request.getParameter("objetoId") %>&refnum_part=<%= request.getParameter("refnum_part") %>" frameBorder=yes noResize scrolling=yes>
	</FRAMESET>
<!--	<FRAME name=left marginWidth=0 marginHeight=0 src="navegador.html" frameBorder=yes noResize scrolling=no> -->
	<FRAME name=rigth marginWidth=0 marginHeight=0 src="/iri/Publicidad.do?state=muestraTituloVisualizacion&accion=<%= request.getParameter("accion") %>&refnum_part=<%= request.getParameter("refnum_part") %>" frameBorder=yes noResize scrolling=yes>
</FRAMESET>
 
</HTML>
