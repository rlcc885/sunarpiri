<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="gob.pe.sunarp.extranet.framework.session.UsuarioBean"%>
<html>
<head>
<link href="styles/stylo1.css" rel="stylesheet" type="text/css">
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



<body  background="images/bk_cabecera.gif">

  <table width="798" border="0" cellspacing="0" cellpadding="0">
	<tr> 
	      <td width="173" rowspan="2" valign="top">
	        <img src="images/tit_cabecera1.gif" width="173" height="59" >
	      </td>
	      <td colspan="3">
	        <a href="acceso/InfoStatic/PrivacSeg.html" target="main_frame1" onMouseOver="MM_swapImage('top1','','/iri/images/opc_top01x.gif',1)" onMouseOut="MM_swapImgRestore()">
	          <img src="<%=request.getContextPath()%>/images/opc_top01.gif" border="0" name="top1">
	        </a>
	        <a href="<%=request.getContextPath()%>/acceso/InfoStatic/ayuda.htm" target="main_frame1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top2','','/iri/images/opc_top02x.gif',1)">
	          <img src="<%=request.getContextPath()%>/images/opc_top02.gif" border="0" name="top2">
	        </a>
	        <a href="acceso/t_condiciones.htm" target="main_frame1" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top3','','/iri/images/opc_top03x.gif',1)">
	          <img src="<%=request.getContextPath()%>/images/opc_top03.gif" border="0" name="top3">
	        </a>
	        <a href="javascript:Abrir_Ventana('acceso/Contactenos.html','Contactenos','',415,280)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top4','','/iri/images/opc_top04x.gif',1)">
	          <img src="<%=request.getContextPath()%>/images/opc_top04.gif" border="0" name="top4">
	        </a>
	        <a href="acceso/frm_tarifas.htm" target="_top" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top6','','/iri/images/opc_top06x.gif',1)">
	          <img src="<%=request.getContextPath()%>/images/opc_top06.gif" name="top6" border="0">
	        </a>
	        <a href="acceso/displayLogin.html" target="_top" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('top5','','/iri/images/opc_top05x.gif',1)">
	          <img src="<%=request.getContextPath()%>/images/opc_top05.gif" border="0" name="top5">
	        </a>
	        <!--img src="images/space.gif" width="15" height="30" align="absmiddle"-->
	      </td>
	</tr>
    <tr> 
      		<td colspan="3" align="center">
	    		<table width="98%" border="0" cellspacing="2" cellpadding="1">
      		    <tr> 
        		    <td width="50%" class="txtDescrip">
          			  <table width="95%" border="1" cellpadding="1" cellspacing="2" bordercolor="610000">
             			   <tr>
                			  <td width="25%" align="right" bordercolor="450000" bgcolor="813333">
                			    <font color="#ffffff">Usuario : </font>
                			  </td>
                  			  <td width="75">
                  			    <span class="usuario"><%=request.getAttribute("usuario")%></span>
                  			    
                  			    
                  			  </td>
		                  </tr>
    		          </table>              
        		    </td>
         		   <td width="50%" align="right" class="txtDescrip">
					<table width="95%" border="1" cellpadding="1" cellspacing="2" bordercolor="610000">
    		            <tr>
      		  		          <td width="35%" align="right" bordercolor="450000" bgcolor="813333"><font color="#ffffff">Saldo Disponible :</font></td>
        		  		      <td width="65%"><span class="usuario"><%=request.getAttribute("saldo")%> Soles</span></td>
        		  		      
		                </tr>
  		            </table>	
    	           </td> 
       	        </tr>  
			   </table>              
	         </td>
    </tr>
   </table>
</body>
</html>
