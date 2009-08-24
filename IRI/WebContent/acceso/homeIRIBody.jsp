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
<script language=="JavaScript" SRC="<%=request.getContextPath()%>/javascript/overlib.js"></script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<div id="maincontent">
	    <div class="innertube">
	    	 <b>Inicio</b>
	         <br>
	         <br>
	         <font color="#949400" size="4">Interconexión Registral Iberoamericana</font>
	         <br>
	         <br>
	         <font color="#666666" size="4">Perú</font>
	         <br>
	         <!-- img src="images/iri/foto1.jpg" width="487" height="323"/-->
	         <img src="<%=request.getContextPath()%>/images/iri/foto1.jpg" width="487" height="320"/>
	         <br>
	         <font color="#666666">Usuarios Abonados | Pago con tarjeta | Como abonarse /</font>
	         <br>
		</div>
	</div>
</body>
</html>



