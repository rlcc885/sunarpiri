<html>
<head>
<title>SUNARP - Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/iri.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/overlib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script language="javascript">
function Abrir_Ventana(dirURL,titulo,propiedad,ancho,alto)
	{
		if(screen.width){
			var venleft = (screen.availWidth-ancho)/2;
			var ventop = (screen.availHeight-alto)/2;
		}else{
			venleft = 0;
			ventop =0;
		}
		if (venleft < 0) venleft = 0;
		if (ventop < 0) ventop = 0;
		var titulo=titulo;
		var propiedad=propiedad;
		var propiedades="toolbar=no,location=no,status=no,menubar=no,scrollbars=no,resizable=no,";
		if(propiedad==""){
			propiedades= propiedades + "top=" + ventop + ",left=" + venleft + ",width=" + ancho + ",height=" +alto;
		}else{
			propiedades= propiedad + ",top=" + ventop + ",left=" + venleft + ",width=" + ancho + ",height=" +alto;
		}
		window.open(dirURL,titulo,propiedades);
	}

function ingresar()
{
  if(validarformulario()){

 	document.form1.user.value=document.form1.user.value.toUpperCase();
  	document.form1.clave.value=document.form1.clave.value.toUpperCase();
  	document.form1.method = "POST";
  	document.form1.action = "<%=request.getContextPath()%>/IngresoIRI.do";
	document.form1.target="_top";
  	document.form1.submit();
  	return true;
  }
  return false;
}

function validarformulario()
{
	if(document.form1.user.value.length<6 || document.form1.user.value.length>13)
	{
		alert("Nombre de Usuario Incorrecto. El nombre de usuario debe tener una longitud entre 6 y 13 caracteres");
		document.form1.user.focus();
		return false;
	}
	
	/*if(document.form1.clave.value.length<6 || document.form1.clave.value.length>10)
	{
		alert("Password Incorrecto. La contrasena debe tener una longitud entre 6 y 10 caracteres");
		document.form1.clave.focus();
		return false;
	}*/	
	return true;	
}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<div id="overDiv" style="position:absolute; visibility:hide; z-index:1;"></DIV>
<script language=="JavaScript" SRC="<%=request.getContextPath()%>/javascript/overlib.js"></script>
<div id="maincontent">
	    <div class="innertube">
	    
	    	<b><font color="#949400">Inicio &gt;&gt;</font><font color="#666666">Usuarios Abonados</font></b>
	    
<form name="form1" onSubmit="if (!ingresar()) return false;"> 
  <table width="780" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <td width="302" rowspan="2" align="center" valign="top" >
      	  <p class="txtDescrip">
      	  	<br>
          	<b>Acceso al Sistema IRI</b>
          	<br>
        <table width="220" border="0" cellspacing="0" cellpadding="1">
          <tr align="center" > 
            <td colspan="2"><strong><font color="#FFFFFF" size="2" face="Verdana">Usuarios Registrados</font></strong></td>
          </tr>
          <tr > 
            <td colspan="2"><img src="../images/space.gif" width="15" height="2"></td>
          </tr>
          <tr > 
            <td width="40%" align="right"><strong>Usuario 
              :</strong></td>
            <td align="center"><input name=user type="text" size="11" maxlength="13" style="width:96px;height:22px;font-family:Arial,'Lucida Sans','Trebuchet MS',Verdana,Helvetica,sans-serif;text-transform: uppercase" onblur="this.value.toUpperCase()" value="DANNYESPIRITU">
          </tr>
          <tr > 
            <td width="40%" align="right"><strong>Contrase&ntilde;a:</strong></td>
            <td align="center"><input type="password" name=clave  size="11" maxlength="10" style="width:96px;height:22px;text-transform: uppercase" onblur="this.value.toUpperCase()" value="clave1"></td>
          </tr>
          <tr > 
            <td colspan="2"></td>
          </tr>
        </table>
        <p> 
           <input name="B1" type="button" class="formbutton" onclick="javascript:onclick=ingresar()" value="Ingresar" />
          <br>
          </p>
        <p>Esta p&aacute;gina funciona con Internet Explorer 5.0 o superior </p>
      </td>
    </tr>
  </table>
</form>
</div>
</div>
</body>
</html>



