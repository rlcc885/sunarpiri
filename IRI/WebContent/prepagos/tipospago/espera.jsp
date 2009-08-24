<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/expresso-bean.tld" prefix="bean"%>
<html>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>
<script>
	function redireccionar() {
		document.form1.method="POST";
		url = "<bean:write property="urlParams"/>";
		document.form1.action = url;
		document.form1.submit();
	}
</script>
<body>
<center></center>
<form name="form1">
<input type=hidden name="nombre" value=valor>
</form>
</body>
<script>
	redireccionar();
</script>
</html>