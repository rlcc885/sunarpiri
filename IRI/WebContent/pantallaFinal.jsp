<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE></TITLE>
<LINK REL="stylesheet" type="text/css" href="styles/global.css">
<%
String destino = (String) request.getAttribute("destino");
%>
<script language="javascript">
function doOk()
{
	if((window.opener)&&(!window.opener.closed))
	{
		close();
	} else {
	<%if (destino!=null) {%>
		<%if (destino.equals("back")) {%>
	history.back();
		
		<%} else {%>
	location.href="/iri/<%=destino%>";
		<% } %>
	<% } %>
	
	}
}
</script>
</HEAD>

<BODY>
<br><br>
<table cellspacing=0 class=formulario>
<tr>
	<td>
		<br><br>
		<center>
			<!--h3><%=request.getAttribute("mensaje1")%> mal</h3-->
			<h3><%=request.getAttribute("mensaje1")%></h3>
		</center>
		<center>
			<%if (destino!=null) {%>		
			<br><A href="javascript:doOk()"><img border=0 src="images/btn_regresa.gif"></a>
			<%}%>
		</center>
	</td>
</tr>
</table>

</BODY>
</HTML>