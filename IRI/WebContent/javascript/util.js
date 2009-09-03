var espacio = " ";
var defaultvacioOK = false;
var espacioblanco = " \t\n\r";
var cadnumeroneg ="0123456789-";
var cadespacio = " ";	
var cadnumero = "0123456789";
var cadnumeroespacio = "1234567890 ";
var cadnumeroespacioguion = "1234567890 -";
var cadcorreo = "1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ_-.@";
var cadtelefono = "1234567890 -";
var cadnombre = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ñÑáéíóúÁÉÍÓÚ'";
var cadusuariopassword = "1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ&";
var cadnumeronombre = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ñÑáéíóúÁÉÍÓÚ'";
var cadnumeronombrebas = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ ";
var cadnumeroletrasimbolos = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_.- ñÑáéíóúÁÉÍÓÚ()':/#%$&@!¡?¿+-*,;üÜ";
			
esNS4 = "";
esIE4 = "";
esIE5 = "";
esNS6 = "";			
var navegador="";
function solonumlet(objeto){
//  objeto.value=objeto.value.toUpperCase().replace(/([^0-9A-Z ])/g,"");
  objeto.value=objeto.value.toUpperCase();
}

function solonum(objeto){
  objeto.value=objeto.value.toUpperCase().replace(/([^0-9])/g,"");
  //objeto.value=objeto.value.toUpperCase();  
}

//function trim(str)
//{
//  return( (""+str).replace(/^\s*([\s\S]*\S+)\s*$|^\s*$/,'$1') );
//}

function trim(input)
{
  var lre = /^\s*/;
  var rre = /\s*$/;
  input = input.replace(lre, "");
  input = input.replace(rre, "");
  return input;
}
function sololet(objeto){
//  objeto.value=objeto.value.toUpperCase().replace(/([^A-Z])/g,"");
  objeto.value=objeto.value.toUpperCase();
  objeto.value=trim(objeto.value);
}


	function rellenaIzq(texto, relleno, total)
	{
	if (texto.length >= total)
		return texto;
	
	var resultado = texto;
	
	total = total - texto.length
	
	for (i = 0; i < total; i++)
	  {
	    resultado = relleno + resultado
	  }
	  
	  
	return resultado;
	}

	function extraeDer(texto, cantidad)
	{
	if (texto.length <= cantidad)
		return texto;
		
	var resultado ="";
	
	for (i = (texto.length - cantidad); i < texto.length; i++)
	  {
	  	var xchar = texto.substr(i,1);
	   resultado = resultado + xchar
	  }
	  
	return resultado;
	}

	function extraeIzq(texto, cantidad)
	{
	if (texto.length <= cantidad)
		return texto;
		
	var resultado ="";
	
	for (i = 0; i < texto.length-cantidad; i++)
	  {
	  	var xchar = texto.substr(i,1);
	   resultado = resultado + xchar
	  }
	  
	return resultado;
	}


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

	function esVacio(cadena)
	{
		return ((cadena == null) || (cadena.length == 0) || trim(cadena)== "")
	}
	
	function esDigito(cadena)
	{   return ((cadena >= "0") && (cadena <= "9"))
	}			
	
	function esNumero(cadena){
	  if ( isNaN( cadena ) )
	  {
	    return false
	  } else {
	    return true
	  }
	}

	function esEntero(cadena) {
	for (var i = 0; i < cadena.length; i++) {
		var c = cadena.charAt(i);
		if (!((c >= "0") && (c <= "9"))) {
			return false;
		}
	}
	
	return true;
	}

	// AGREGADO POR GIANCARLO OCHOA
	function esDecimal(cadena) {
		var cont = 0;
		for (var i = 0; i < cadena.length; i++) {
			var c = cadena.charAt(i);
			if ( (c==".") && (cont==0) ) {
				cont = cont + 1;
			}
			else if ( (c==".") && (cont>0) ) {
				return false;
			} 
			else if (!((c >= "0") && (c <= "9"))) {
				return false;
			}
		}
		
		return true;
	}
	//
	
	function esEspacioBlanco(cadena)
	{   var i;
	    if (esVacio(cadena)) return true;    
		for (i = 0; i < cadena.length; i++)
		{   
			var c = cadena.charAt(i);
			if (espacioblanco.indexOf(c) == -1) 
			{			
			return false;
			}
		}
	    return true;
	}	
		
	function esEspacio(cadena)
	{   var i;
	    // Es vacio?
	    if (esVacio(cadena)) return true;
	    // Buscar un caracter no blanco en la cadena
	    for (i = 0; i < cadena.length; i++)
	    {   
	        // Verificar caracter no blanco
	        var c = cadena.charAt(i);
	        if (espacio.indexOf(c) == -1) return false;
	    }
	    return true;
	}

	function esEmail (cadena)
	{
		if (esVacio(cadena))  
   		if (esEmail.arguments.length == 1) return defaultvacioOK;
    		else return (esEmail.arguments[1] == true);
    //  es un espacioblanco?
    	if (esEspacioBlanco(cadena)) return false;
	// debe existir al menos un caracter antes del arroba 
		var i = 1;
		var k=0;
 		var sLength = cadena.length;
	// Busca @
		//while 
		//((i < sLength) && (cadena.charAt(i) != "@"))
	    //if ((i >= sLength) || (cadena.charAt(i) != "@")) return false;
   	    //else i += 2;
		for (j=1;j<sLength;j++){
			if(cadena.charAt(j) == "@")
		    { 
		    	i=j;
		    	k=k+1;
		    }
	    }
	    if(k==1)
	    {
	    	i=i+2;
	    }else{
	    	return false;
	    }

	// Busca .
	    while ((i < sLength) && (cadena.charAt(i) != "."))
	    { i++
	    }
	// Debe existir almenos un caracterantes del .
	    if ((i >= sLength - 1) || (cadena.charAt(i) != ".")) return false;
	    else return true;    
	}		

function Navegador()
{
esNS4 = (document.layers) ? true : false;
esIE4 = (document.all && !document.getElementById) ? true : false;
esIE5 = (document.all && document.getElementById) ? true : false;
esNS6 = (!document.all && document.getElementById) ? true : false;
if (esNS4) { navegador='NS4';}
if (esIE4) { navegador='IE4';}
if (esIE5) { navegador='IE5';}
if (esNS6) { navegador='NS6';}
}

function invisible_navegador(navegador,lbl_IE,lbl_NS,estado)
{
	switch (navegador)
	{ 
		case 'NS4': 
		if(estado=='i'){lbl_NS.visibility="hide"}else{lbl_NS.visibility="show"};	
		break;	
		case 'IE4':
		if(estado=='i')
		{
			lbl_IE.style.visibility="hidden";
			lbl_IE.style.display="none";
		}
		else
		{
			lbl_IE.style.visibility="visible";
			lbl_IE.style.display="";
		};		
		break;		
		case 'IE5':
	    lbl_IE = document.getElementById(lbl_IE);
		if(estado=='i')
		{
			lbl_IE.style.visibility="hidden";
			lbl_IE.style.display="none";
		}
		else
		{
			lbl_IE.style.visibility="visible";
			lbl_IE.style.display="";
		};
		break;		
		case 'NS6':
	    lbl_IE = document.getElementById(lbl_IE);
		if(estado=='i'){lbl_IE.style.visibility="hidden"}else{lbl_IE.style.visibility="visible"};
		break;		
	}
}

function obj_mayuscula(objeto){
  objeto.value= trim(objeto.value.toUpperCase());
}

function mayuscula(campo){
  return campo.value.toUpperCase()
}
function minuscula(campo){
  return campo.value.toLowerCase()
}

	function esLongitudEntre(cadena,minimo,maximo) 
	{				
		if (cadena.length<minimo || cadena.length>maximo){
			return false;				
		}else{
			return true;
		}
	}	
	function esLongitudMenor(cadena,maximo) 					
	{
		if (cadena.length>maximo){
			return false;				
		}else{
			return true;
		}
	}	
	function esLongitudMayor(cadena,minimo) 						
	{
		if (cadena.length<minimo){
			return false;				
		}else{
			return true;
		}
	}	

	function esEnteroMayor(cadena,minimo) 						
	{
		if (eval(cadena)<minimo){
			return false;				
		}else{
			return true;
		}
	}	


	function esMenor(cadena,maximo) 					
	{
		if (cadena.length>maximo){
			return false;				
		}else{
			return true;
		}
	}	
	function esMayor(cadena,minimo) 						
	{
		if (cadena.length<minimo){
			return false;				
		}else{
			return true;
		}
	}	
	function contieneNumero(obj)
	{
	  for (var i=0;i<obj.value.length;i++)
	  {
	    chr=obj.value.substring(i,i+1);
	    if(esEntero(chr))
	    {
		return true;
	    }
	  }
	  return false;
	}
		
	function lTrim(lstr)
	{
		lstr = String(lstr);
		if (lstr!="") 
		{
			var strlen, cptr, lpflag, chk;
			strlen=lstr.length;	
			cptr=0;
			lpflag=true;	
			do
			{
				chk=lstr.charAt(cptr);
				if (chk !=" ") 
				{
					lpflag=false;
				}else {
					if (cptr == strlen) 
					{
						lpflag=false;
					}else {
						cptr++;
					} 		
				}
			}
			while (lpflag == true)
			if (cptr > 0) 
			{
				lstr = lstr.substring(cptr,strlen);
			}
		}
		return lstr;
	}
	
	function rTrim(lstr)
	{
		lstr = String(lstr);
		if (lstr != "") 
		{
			var strlen, cptr, lpflag, chk;
			strlen=lstr.length;
			cptr=strlen;
			lpflag=true;
			do
			{
				chk=lstr.charAt(cptr-1);
				if (chk !=" ") 
				{
					lpflag=false;
				}else{
					if (cptr == 0) 
					{
						lpflag=false
					}else {
						cptr--;
					} 		
				}
			}
			while (lpflag == true)
			if (cptr < strlen) {
				lstr=lstr.substring(0,cptr);
			}
		}
		return lstr;
	}
				
	function trim(lstr) 
	{
		return lTrim(rTrim(lstr))
	}
	

function mensaje_status(men)
{
    status=men;
    return true;
}
function VentanaFlotante(pag,px,py,x,y)
{
	var ancho= x;
	var alto= y;
	NombreVentana=window.open(pag,"NombreVentana","bar=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,resizable=1,width=" + ancho + ",height=" + alto + ",top=" + px + ",left=" + py+"");
}
function esFecha(dia,mes,ano) {

	if (!esNumero(dia) || !esNumero(mes)
		||!esNumero(ano)){
		alert("Caracteres no validos");
		return false;
	}
	if (ano <1850){
		alert("Año no valido");
		return false;
	}
	if ((mes<1)||(mes>12 )){
		alert("Mes no valido");
		return false;
	}
	if ((dia<1)||(dia>31)){
		alert("Dia no valido");
		return false;
	}
	if((mes==1)||(mes==3)||(mes==5)||
		(mes==7)||(mes==8)||(mes==10)||
		(mes==12)){
		if(dia > 31){
			alert('Dia no valido')
			return false;
		}
	}
	if((mes==4)||(mes==6)||(mes==9)||(mes==11)){
		if(dia > 30){
			alert('Dia no valido')
			return false;
		}
	}
	if(mes==2){
		if((ano  % 4 == 0)&&((!(ano  % 100 == 0))||(ano  % 400 == 0))){
			if(dia > 29){
				alert('Dia no valido')
				return false;
			}
		}
		else{
			if(dia > 28){ 
				alert('Dia no valido')
				return false;
			}
		}
	}
	return true;
}
function esRangoFecha(diainicio,mesinicio,anoinicio,diafin,mesfin,anofin) {

    if(!esFecha(sel_Obtener_Texto(document.form1.diainicio),sel_Obtener_Texto(document.form1.mesinicio),sel_Obtener_Texto(document.form1.anoinicio)))
    {
    	return false;
    }
    if(!esFecha(sel_Obtener_Texto(document.form1.diafin),sel_Obtener_Texto(document.form1.mesfin),sel_Obtener_Texto(document.form1.anofin)))
    {

    	return false;
    }   
	if(eval(anoinicio+mesinicio+diainicio)>eval(anofin+mesfin+diafin))
	{
   			alert('Rango de Fechas no valido')
	    	return false;
	}
	return true;
}
			function cadenadeobjeto(objeto){
				if( objeto.value==undefined){
					cadena=objeto;				
				}else{
					switch(objeto){
					case 'text':
						cadena=objeto.value;
					case 'checkbox':
						cadena=objeto.value;					
					case 'radio':
						cadena=objeto.value;										
					case 'select-one':
						cadena=objeto.value;																
					}
				}
				if( tipo=="[object]" || tipo=="object HTMLInputElement"){				
				alert("a");
				return cadena;
				}
			}
			
			function contieneCarateresValidos(cadena, cadenavalida)
			{
				if(!esEspacio(cadena))
				{	
					cad= eval('cad'+ cadenavalida);					   
					for(var i=0; i<cadena.length; i++)
					{
						var caracter = cadena.substring(i,i+1);
						if (cad.indexOf(caracter) == -1)
							{
							return false;
							}
					}
					return true;	
				}
				else
				{
					return false;
				}
			}	
			function contieneCarateresInvalidos(cadena, cadenainvalida){
				if(!esEspacio(cadena)){
					cad= eval('cad'+ cadenainvalida);					   
					for(var i=0; i<cadena.length; i++){
						var caracter = cadena.substring(i,i+1);
						if (cad.indexOf(caracter) >0){
							return true;}
						}
					return false;	
				}else{
					return true;
				}
			}	
			function contieneCaracterInvalido(cadena, cadenainvalida)
			{
				alert("a");
			  
			}
			
			


function sel_Eliminar_Opciones(objeto_sel)
{
//Elimina todas las opciones de un select
	if (objeto_sel.length != 0)
	{ 
		for(var i=0; i<objeto_sel.options.length; ++i)
			{
				objeto_sel.options[i]=null;
				--i;
			}
    	}
}

function sel_Obtener_Valor(objeto_sel)
{
//Obtener codigo de papa
	var valor ="";
	for(var i=0; i< objeto_sel.options.length; i++)
		{
			if (objeto_sel.options[i].selected)
				{
					valor=objeto_sel.options[i].value;
					return valor;
					break;
				}
		}
}

function sel_Obtener_Texto(objeto_sel)
{
//Obtener codigo de papa
	var valor ="";
	for(var i=0; i< objeto_sel.options.length; i++)
		{
			if (objeto_sel.options[i].selected)
				{
					valor=objeto_sel.options[i].text;
					return valor;
					break;
				}
		}
}


function sel_Generar_Opciones(objeto_sel_destino,vector,clave)
{
	//llenar combo hijo con informaci&oacute;n de acuerdo al Id de combo padre
	var campo_valor;
	var campo_descripcion;
	var campo_clave;			
	//var clave = sel_Obtener_Valor(objeto_sel_origen);
	for (var i=0; i<vector.length; i++)
			{
				campo_clave = vector[i][0];
				campo_valor = vector[i][1];
				campo_descripcion = vector[i][2];
				if (campo_clave  == clave)
					objeto_sel_destino.options[objeto_sel_destino.length] = new Option(campo_descripcion,campo_valor);
			}
}

function sel_Reiniciar_Opciones(objeto_sel_destino,vector,objeto_sel_origen,defecto)
{
	sel_Eliminar_Opciones(objeto_sel_destino);
	var clave= sel_Obtener_Valor(objeto_sel_origen);
	if (defecto!="default")
	{
		sel_Generar_Opciones(objeto_sel_destino,vector,defecto);
	}
	if(defecto!=clave){	
		sel_Generar_Opciones(objeto_sel_destino,vector,clave);
	}
}

function sel_ActivarOpcion(objeto_sel, opcion)
{ 
for(var i=0; i< objeto_sel.options.length; i++)
	{
		if (objeto_sel.options[i].value == opcion)
				objeto_sel.options[i].selected=true;
	}
}

function sel_DesactivarOpcion(objeto_sel)
{ 
for(var i=0; i< objeto_sel.options.length; i++)
	{
			objeto_sel.options[i].selected=false;
	}
}

function tieneCaracterNoValido(cadena)
{
	for (i = 0; i < cadena.length; i++)
		{   
			var c = cadena.charAt(i);
			if (c=='%')
				return true;
		}
	return false;
}

////////////////////////////////////////////////////////////////////////////
// Agregado - Ing. Carlos Castañeda Salmón

function moveToLeft() { 
	left = document.frm.leftList; 
	right = document.frm.rightList; 

	if(right.selectedIndex == -1) 
		return; 
	
	while(right.selectedIndex != -1) { 
		selected = right.options[right.selectedIndex]; 
		
		right.options[right.selectedIndex] = null; 
		left.options[left.length] = selected; 
	} 
} 

function moveToRight() { 
	left = document.frm.leftList; 
	right = document.frm.rightList; 
	
	if(left.selectedIndex == -1) 
		return; 
	
	while(left.selectedIndex != -1) { 
		selected = left.options[left.selectedIndex]; 
		
		left.options[left.selectedIndex] = null; 
		right.options[right.length] = selected; 
	} 
} 

function moveAllToRight() { 
	left = document.frm.leftList;
	right = document.frm.rightList;
	
	for(i = 0; i < left.options.length; i++) { 
		left.options[i].selected = true; 
	}

	moveToRight();
}

function moveAllToLeft() { 
	left = document.frm.leftList;
	right = document.frm.rightList;
	
	for(i = 0; i < right.options.length; i++) { 
		right.options[i].selected = true; 
	}

	moveToLeft();
}

////////////////////////////////////////////////////////////////////////////
function fechas(control)
{ 
	caja=control.value;
   if (caja)
   {  
      borrar = caja;
      if ((caja.substr(2,1) == "/") && (caja.substr(5,1) =="/"))
      {      
         for (i=0; i<10; i++)
	     {	
            if (((caja.substr(i,1)<"0") || (caja.substr(i,1)>"9")) && (i != 2) && (i != 5))
			{
               borrar = '';
               break;  
			}  
         }
	     if (borrar)
	     { 
	        a = caja.substr(6,4);
		    m = caja.substr(3,2);
		    d = caja.substr(0,2);
		    if((a < 1900) || (a > 2050) || (m < 1) || (m > 12) || (d < 1) || (d > 31))
		       borrar = '';
		    else
		    {
		       if((a%4 != 0) && (m == 2) && (d > 28))	   
		          borrar = ''; // Año no viciesto y es febrero y el dia es mayor a 28
			   else	
			   {
		          if ((((m == 4) || (m == 6) || (m == 9) || (m==11)) && (d>30)) || ((m==2) && (d>29)))
			         borrar = '';	      				  	 
			   }  // else
		    } // fin else
         } // if (error)
      } // if ((caja.substr(2,1) == \"/\") && (caja.substr(5,1) == \"/\"))			    			
	  else
	     borrar = '';
	  if (borrar == ''){
	     alert('La fecha ingresada no es válida');
	     control.focus();
	  }
   } // if (caja)   
}
/**
 * inicio:validarAcceso:02/10/2007
 * @autor: dbravo 
 * @necesidad: Se desea que para el pase a producción inicial de RMC, las nuevas funcionalidades de RMC no esten activas 
 *             para los usuarios externos(con la excepción del usuario de la variable 'usuarioAccesoRecursosNuevos'), para 
 *             lo cual antes de realizar una invocación a una nueva funcionalidad
 * @descripcion: Este javascript realiza la validación, si el perfil o usuario tiene acceso a las nuevas funcionalidades, cada
 * 				funcionalidad maneja un identificador. 
 * @retorno:    - Un false si tiene acceso a la funcionalidad 
 *				- Un true si no tiene acceso a la funcionlidad
 *
**/
var usuarioAccesoRecursosNuevos = 'INFORMATICA';

function noTieneAccesoRecursoRMC(funcionalidadId, perfilId, usuario){
		
	if(funcionalidadId==1){
		//funcionlidad Certificado Positivo Negativo RMC
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==2){
	
		//funcionlidad Certificado de Vigencia RMC
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==3){
	
		//funcionlidad Certificado Compendioso de Gravamen RMC
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==4){
	
		//funcionlidad CREM Actos Vigentes
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==5){
	
		//funcionlidad CREM Historico
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==6){
	
		//funcionlidad CREM Condicionado
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==7){
	
		//funcionlidad Certificado Compendioso de Gravamente RJB
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==8){
	
		//funcionlidad Certificado Compendioso Dominial de RJB
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==9){
	
		//funcionlidad Certificado de Busqueda
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==10){
	
		//funcionlidad Busqueda Directa RMC
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==11){
	
		//funcionlidad Busqueda Indice RMC
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==12){
	
		//funcionlidad Busqueda Nacional Indices
		return vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}else if(funcionalidadId==13){
	
		//funcionlidad Publicidad Masiva Relacional
		return false;//vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario);
		
	}
	
	return false;
}

function vaidaPerfilUsuarioNuevaFuncionalidadRMC(perfilId, usuario){

	if((perfilId==20 || perfilId==30 || perfilId==40) && usuario!=usuarioAccesoRecursosNuevos){
		alert('No puede ingresar a esta opción');
		return true;
	}
	
}
function soloNumero(evt){
   var charCode = (evt.which) ? evt.which : evt.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
	   return false;   
   
   return true;
}
/**
 *  final:validarAcceso:02/10/2007
 **/