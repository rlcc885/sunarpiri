<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<HTML>
<HEAD>
<script language="JavaScript" src="javascript/util.js"></script>
	<title>B&uacute;squeda - Indice Personas Juridicas</title>
	     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	 <META name="GENERATOR" content="IBM WebSphere Studio">
	 <META HTTP-EQUIV="Expires" CONTENT="0">
     <META HTTP-EQUIV="Pragma" CONTENT="No-cache">
     <META HTTP-EQUIV="Cache-Control", "private">
	<LINK REL="stylesheet" type="text/css" href="styles/global.css">
	<script language="JavaScript" src="javascript/util.js"></script>
<script language="JavaScript">
function buscar(){
		if(document.form1.rb.checked){
			if(document.form1.txtRazSoc.value==""){
				alert("Ingrese una razon Social o denominacion para la busqueda.");
			}else{
			//var band="1";
				if(document.form1.txtRazSoc.value.length>1){
					var band="1";
				}else{
					alert("Ingrese por lo menos 2 caracteres para realizar la busqueda");
				}
			}
	}
	if (document.form1.rb2.checked){
			if(document.form1.txtSiglas.value==""){
				alert("Ingrese una sigla para la busqueda.");
			}else{
				if(document.form1.txtSiglas.value.length>1){
					var band="1";
				}else{
					alert("Ingrese por lo menos 2 caracteres para realizar la busqueda");
				}
			}
	}
	if(band=="1"){
		document.form1.method="POST";
		document.form1.action="/iri/IndicePJ.do?state=buscar";
		document.form1.submit();
	}
}
function desactivaSigla(){
	if(document.form1.rb.checked){
		document.form1.rb2.checked=false;
	document.form1.txtSiglas.value="";
	}
}
function desactivaRaz(){
	if(document.form1.rb2.checked){
		document.form1.rb.checked=false;
	document.form1.txtRazSoc.value="";
	}
}
</script>
</HEAD>
<BODY>
<br>
<table cellspacing=0 class=titulo>
   <tr>
	<td>
		<FONT COLOR="black">BUSQUEDAS<font size="1"> &gt;&gt; </font></FONT><font color="900000">B&uacute;squeda de Personas Juridicas a Nivel Nacional </FONT></td>
  </tr>
</table>
<br>
<form name="form1" method="post" class="formulario">
<table border="0" class="tablasinestilo" cellspacing=0 >
  <tr>
    <th colspan="5"><font size="2">&nbsp;Indice de Personas Juridicas a Nivel Nacional </font></th>
  </tr>

  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td valign="middle" width="97">
      <label>
      <input name="rb" id="rb" type="radio" value="R" checked="checked" onclick="desactivaSigla()">
      </label></td>
    <td valign="middle" width="159"><strong> &nbsp;Raz&oacute;n Social / Denominaci&oacute;n :</strong>
      <label></label></td>

    <td valign="middle" width="81"><input type="text" name="txtRazSoc" size="50"></td>
    <td valign="middle" width="209">&nbsp;</td>
    <td  valign="middle" width="35"></td>
  </tr>
  <tr>
    <td valign="middle" width="97">
      <label>
      <input name="rb2" id="rb2" type="radio" value="S" onclick="desactivaRaz()">
      </label></td>
    <td valign="middle" width="159"><strong> &nbsp;Siglas :</strong>
      <label></label></td>

    <td valign="middle" width="81"><input type="text" name="txtSiglas" size="50"></td>
    <td valign="middle" width="209">&nbsp;<A href="javascript:buscar();"
			onMouseOver="javascript:mensaje_status('Buscar Personas Juridicas');return true;"
			onmouseOut="javascript:mensaje_status(' ');return true;"> <IMG
			src="images/btn_buscar.gif" border="0"> </A></td>
    <td  valign="middle" width="35"></td>
  </tr>
  <tr>
  	<td colspan="5"><td width="7">
  </tr>
  <tr>
  </tr>
 <tr> </tr>
</table>
<div id="consejos">
<font color="900000"><h3>CONSEJOS PRACTICOS</h3></font>
<div id="contenido">
<p>Para obtener resultados de b&uacute;squeda  satisfactorios en el &Iacute;ndice Nacional del Registro de Personas Jur&iacute;dicas, le sugerimos que  lea detenidamente las siguientes recomendaciones.</p>
</div>
<div id="recomendacion">
<ul>
  <li>La tasa registral por concepto de b&uacute;squeda es  de S/.4.00 (cuatro nuevos soles), y se cobra por la utilizaci&oacute;n del servicio,  independientemente del resultado positivo o negativo de la b&uacute;squeda.</li>
  <li>S&iacute;rvase consignar como texto a buscar la  palabra o palabras completas que conforman el nombre de la persona jur&iacute;dica, o  cuando menos la mayor cantidad de palabras que conforman la denominaci&oacute;n o  raz&oacute;n social.</li>
  <li>La utilizaci&oacute;n de una sola palabra o de algunas letras que  forman la parte inicial de una palabra, como criterio de b&uacute;squeda, tiene muchas  posibilidades de dar como resultado una cantidad muy elevada de resultados  similares. </li>
  <li>Se buscar&aacute; por el total de palabras  consignadas como criterio de b&uacute;squeda, sin importar el orden en que estas se  encuentren dentro del &ldquo;campo&rdquo; elegido como materia de la b&uacute;squeda (denominaci&oacute;n  o raz&oacute;n social).</li>
  <li>El texto consignado como criterio de  b&uacute;squeda, ser&aacute; buscado como palabra(s) completa(s) o como parte de alguna  palabra dentro del &ldquo;campo&rdquo; elegido como materia de la b&uacute;squeda (denominaci&oacute;n o raz&oacute;n  social).</li>
  <li>Sin perjuicio de tener en cuenta los consejos  anteriores, el m&iacute;nimo de letras a consignar como criterio de b&uacute;squeda debe ser  dos.</li>
  <li>El sistema hace distinci&oacute;n entre las letras  acentuadas o no, consider&aacute;ndolas como caracteres diferentes.&nbsp; Ejemplo: Se considerar&aacute;n b&uacute;squedas distintas  las que se efect&uacute;en tomando como criterio: Per<strong>&uacute;</strong> (con tilde) y Peru (sin tilde).</li>
  <li>No deben consignarse como parte del criterio  de b&uacute;squeda, los siguientes caracteres especiales: el ap&oacute;strofe (&lsquo;), el gui&oacute;n  (-), los par&eacute;ntesis, el signo m&aacute;s (+) y las comillas (&ldquo; &ldquo;).</li>
</ul>
</div>
</div>
</form>
</BODY>
</HTML>