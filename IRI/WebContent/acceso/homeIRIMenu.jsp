<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<title></title>
<META name="GENERATOR" content="IBM WebSphere Studio">
<script language=javascript>
	nav_nombre = navigator.appName.substring(0,5);
	nav_version = parseInt(navigator.appVersion);
	
	//window.open("acceso/menuInformacion.html","Informacion",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=555,height=400');
	<%-- window.open("acceso/win_premio.html","Premio",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=190,height=258'); --%>

	function informacion(){
	window.open("acceso/menuInformacion.html","Informacion",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=555,height=400');
	}
	<%--function Premio(){
	window.open("acceso/win_premio.html","Premio",'fullscreen=no,toolbar=no,status=yes,menubar=no,scrollbars=no,resizable=no,directories=no,location=no,directories=no,width=190,height=258');
	}--%>

</script>
</head>

<!-- body background="images/bk_left.gif"-->
<body>




<div id="framecontentLeft">
	<div class="innertube">
		
		
		
<img src="../images/iri/logotipo.jpg">


<div id="navMenu">
<div id="Explorer" style="visibility: visible">			

<!-- SERVICIOS DE BUSQUEDA -->
<ul>

    	<li><a href="homeIRILogin.jsp" onclick="javascript:clickHandler1('1')" target="main_frame1">Usuarios Abonados</a></li>
	<li><a href="#" onclick="javascript:clickHandler1('1')" target="_self">Pago con tarjeta</a></li>
	<li><a href="#" onclick="javascript:clickHandler1('1')" target="_self">Como Abonarse</a></li>
	<li><a href="#" onclick="javascript:clickHandler1('1')" target="_self">Condiciones de Uso</a></li>
	<li><a href="#" onclick="javascript:clickHandler1('1')" target="_self">Instrucciones de Uso</a></li>
	<li><a href="#" onclick="javascript:clickHandler1('1')" target="_self">Preguntas Frecuentes</a></li>
</ul>

</div>
</layer>
</div>

<img src="../images/iri/mapa.jpg">

</div>
</div>

<script>
if(nav_nombre =="Netsc")
{
	Netscape.visibility="SHOW";
	function clickHandler() 
	{
	
	  var targetId, srcElement, targetElement;
	  srcElement = window.event.srcElement;
	
	 if (srcElement.className == "OutlineN") 
	 {
		ElementosDelMenu = 20;
		for (h=1; h < ElementosDelMenu ; h++) 
		{
		     targetId = "OutN" + h +"details";
		     targetElement = document.all(targetId);
		     if (eval(targetElement) != null) 
				targetElement.visibility="show";			
		}
	 }
	 
	 
	 
	  if (srcElement.className == "OutlineN") 
	  {
	     targetId = srcElement.id + "details";
	     targetElement = document.all(targetId);
	     if (targetElement.visibility == "hide") 
	        targetElement.visibility = "show";
	      else 
	        targetElement.visibility = "hide";
	  }
	  
	}
	
	document.onclick = clickHandler;

}
else
{


		Explorer.style.visibility="visible";
		function clickHandler() 
		{
		  var targetId, srcElement, targetElement;
		  srcElement = window.event.srcElement;
		  //alert("srcElement=" + srcElement + "-->srcElement.className=" + srcElement.className + "-->srcElement.id"+srcElement.id);
		  
		
		 if (srcElement.className == "Outline") 
		 {
			ElementosDelMenu = 20;
			for (h=1; h < ElementosDelMenu ; h++) 
			{
			     targetId = "Out" + h +"details";
			     targetElement = document.all(targetId);
			     if (eval(targetElement) != null) 
					targetElement.style.display = "none";	
			}
		 }
		 if (srcElement.className == "Outline") {
		     targetId = srcElement.id + "details";
		     targetElement = document.all(targetId);
		
		     if (targetElement.style.display == "none") 
		        targetElement.style.display = "";
		      else 
		        targetElement.style.display = "none";
		  }
		  
		}
		
		ElementosDelMenu = 20;

		for (h=1; h < ElementosDelMenu ; h++) 
		{
		     targetId = "Out" + h +"details";
		     targetElement = document.all(targetId);
		     if (eval(targetElement) != null) 
				targetElement.style.display = "none";	
		}
		
		
		//Out1details.style.display='';
		//document.onclick = clickHandler;

}




	function clickHandler1(id) 
	{
		ElementosDelMenu = 20;
		for (h=1; h < ElementosDelMenu ; h++) 
		{
		     if (eval(id)!=eval(h)){
		     	 targetId = "Out" + h +"details";
			     targetElement = document.all(targetId);
			     if (eval(targetElement) != null) 
					targetElement.style.display = "none";	
			 }
		}
		
		var targetId, targetElement;
		targetId = "Out" + id + "details";
		targetElement = document.all(targetId);
		//alert("targetId " + targetId + "-->" + targetElement.style.display);
		if (targetElement.style.display == "none") 
		   targetElement.style.display = "";
		else 
		   targetElement.style.display = "none";
	}




</script>
</body>
</html>
