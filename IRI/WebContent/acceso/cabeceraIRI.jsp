<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<html>
<head>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<script language="JavaScript" type="text/JavaScript">
//-
function MM_swapImgRestore() 
{ //v3.0
  var i,x,a=document.MM_sr; 
  for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) 
  	x.src=x.oSrc;
}

function MM_preloadImages() 
{ //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) 
{ //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() 
{ //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>


<META name="GENERATOR" content="IBM WebSphere Studio">
</head>



<body width="100%">

<div id="framecontentTop">
     <div class="innertube">
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td valign="top">
	        <img src="<%=request.getContextPath()%>/images/iri/cabecera1.jpg" height="85">
	      </td>
	</tr>
  </table>
  
   </div>
  </div>
  <br><br><br><br><br>
  	 <table width="100%">
	<tr align="right"> 
	      <td>
	        <a class="linkInicio" href="<%=request.getContextPath()%>/acceso/InfoStatic/PrivacSeg.html" target="main_frame1" onMouseOver="MM_swapImage('top1','','<%=request.getContextPath()%>/images/opc_top01x.gif',1)" onMouseOut="MM_swapImgRestore()">
	          <!-- img src="<%=request.getContextPath()%>/images/opc_top01.gif" border="0" name="top1"-->
	          Privacidad y Seguridad
	        </a>
	        &nbsp;
	        <a class="linkInicio" href="<%=request.getContextPath()%>/acceso/InfoStatic/ayuda.htm" target="main_frame1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top2','','<%=request.getContextPath()%>/images/opc_top02x.gif',1)">
	          <!-- img src="<%=request.getContextPath()%>/images/opc_top02.gif" border="0" name="top2"-->
	          Ayuda
	        </a>
	        &nbsp;
	        <a class="linkInicio" href="<%=request.getContextPath()%>/acceso/t_condiciones.htm" target="main_frame1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top3','','<%=request.getContextPath()%>/images/opc_top03x.gif',1)">
	          <!-- img src="<%=request.getContextPath()%>/images/opc_top03.gif" border="0" name="top3"-->
	          T�rminos y Condiciones
	        </a>
	        &nbsp;
	        <a class="linkInicio" href="javascript:Abrir_Ventana('<%=request.getContextPath()%>/acceso/Contactenos.html','Contactenos','',415,280)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top4','','<%=request.getContextPath()%>/images/opc_top04x.gif',1)">
	          <!-- img src="<%=request.getContextPath()%>/images/opc_top04.gif" border="0" name="top4"-->
	          Cont�ctenos
	        </a>
	        &nbsp;
	        <a class="linkInicio" href="<%=request.getContextPath()%>/acceso/InfoStatic/tarifas.html" target="main_frame1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top6','','<%=request.getContextPath()%>/images/opc_top06x.gif',1)">
	          <!-- img src="<%=request.getContextPath()%>/images/opc_top06.gif" name="top6" border="0"-->
	          Tarifas
	        </a>
	        &nbsp;
	        <a class="linkInicio" href="<%=request.getContextPath()%>/acceso/displayLoginIRI.html" target="_top" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top5','','<%=request.getContextPath()%>/images/opc_top05x.gif',1)">
	          <!-- img src="<%=request.getContextPath()%>/images/opc_top05.gif" border="0" name="top5"-->
	          Inicio
	        </a>
	        <!--img src="images/space.gif" width="15" height="30" align="absmiddle"-->
	      </td>
		</tr>
	 </table>
	 <table width="100%">
    	<tr> 
      		<td align="right">
                		<b>Usuario : </b>
                	    <span ><%=request.getAttribute("usuario")%></span>
                	    &nbsp;&nbsp;
          			    <b> Saldo Disponible :</b>
        		  		<span><%=request.getAttribute("saldo")%> Soles</span>
	        </td>
    	</tr>
   	</table>

</body>
</html>
