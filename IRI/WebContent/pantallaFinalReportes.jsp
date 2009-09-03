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
		document.form1.method="POST";
		document.form1.action="/iri/<%=destino%>";
		document.form1.submit();
}
</script>
</HEAD>
<BODY>
<br><br>
<form name="form1">
<table cellspacing=0 class=formulario>
<tr>
	<td>
		<center>
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
</form>
</BODY>
</HTML>
