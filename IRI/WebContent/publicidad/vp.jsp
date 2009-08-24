<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Expires" CONTENT="0">
<META HTTP-EQUIV="Pragma" CONTENT="No-cache">
<META HTTP-EQUIV="Cache-Control", "private">
<META name="GENERATOR" content="IBM WebSphere Studio">
<LINK REL="stylesheet" type="text/css" href="<%=request.getContextPath()%>/styles/global.css">
<TITLE>Impresi&oacute;n de Asiento</TITLE>
<SCRIPT LANGUAGE=javascript>
<!--
// pagina probada en:
// ie 4, ie 5, ie 5.5, ie 6
// ns 6.2.3, ns 7
// var ImagenVP = new Image(); 
var NombreBrowser = navigator.appName;
var VersionBrowser = navigator.appVersion;
var PosIni;
var PosFin;
var VersionFinal;
var AnchoMaxA4 = 625*1.15;
var AltoMaxA4Bruto = 925;
var AltoTextosCabeceraPie = 58;
var AltoMaxA4 = (AltoMaxA4Bruto - AltoTextosCabeceraPie)*1.15;
var ValorAntiguo;
var IndVoltear = false;
var AnchoFinal;
var AltoFinal;
var TimeOutID;

function FormatearImagen(){
	if (IndVoltear) {
		ImagenImpresion.style.filter = "progid:DXImageTransform.Microsoft.BasicImage(rotation=3)";
	}
	//TimeOutID = window.setTimeout("printPage();",1000);
}
//-->
</SCRIPT>
</HEAD>


<logic:present name="imageWidth" scope="session">
	<logic:present name="imageHeight" scope="session">
		<body onload="FormatearImagen()" class=bodyvistaprevia>
			<form name=frm>
				<div ID="ImagenImpresion" STYLE="position:absolute; top:0px; left:0px;">
					<script LANGUAGE=javascript>
						valor1 = window.opener.document.frm.TxtNombreImagen.value;
		
						//ImagenVP.src = valor1;
						//AnchoFinalOld = ImagenVP.width;
						//AltoFinalOld = ImagenVP.height;
					
						
						AnchoFinal = <bean:write name="imageWidth" scope="session"/>;
						AltoFinal = <bean:write name="imageHeight" scope="session"/>;						
						
										
						
						NombreBrowser = NombreBrowser.toUpperCase();
						VersionBrowser = VersionBrowser.toUpperCase();
						
						if (NombreBrowser.indexOf("INTERNET EXPLORER") > -1) {
							PosIni = VersionBrowser.indexOf("MSIE ") + 5;
							PosFin = VersionBrowser.indexOf(";",PosIni);
							VersionFinal = parseFloat(VersionBrowser.substring (PosIni,PosFin));
							if (VersionFinal >= 5.5) {
								if (AnchoFinal > AltoFinal){
									IndVoltear = true;
									ValorAntiguo = AnchoMaxA4;
									AnchoMaxA4 = AltoMaxA4;
									AltoMaxA4 = ValorAntiguo;
								}
							}
						}
					
						if (AnchoFinal > AnchoMaxA4) {
							ValorAntiguo = AnchoFinal;
							AnchoFinal = AnchoMaxA4;
							AltoFinal = (AltoFinal * AnchoMaxA4) / ValorAntiguo
						}
						
						if (AltoFinal > AltoMaxA4) {
							ValorAntiguo = AltoFinal;
							AltoFinal = AltoMaxA4;
							AnchoFinal = (AnchoFinal * AltoMaxA4) / ValorAntiguo
						}
						
						document.writeln  ( "<img width='" + AnchoFinal + "' height='" + AltoFinal + "' name='IMGVP' src='" + valor1 + "'>" );
						//alert(valor1 + " " + AnchoFinalOld + " " + AltoFinalOld);
					</script>
				</div>
				<script LANGUAGE="JavaScript">

					var da = (document.all) ? 1 : 0;
					var pr = (window.print) ? 1 : 0;
					var mac = (navigator.userAgent.indexOf("Mac") != -1); 
					
					function printPage(){
					 	//window.clearTimeout( TimeOutID );
					  	if (document['IMGVP'].height == 0) {
						  	//TimeOutID = window.setTimeout("printPage();",1000);
							return;
				  		}

	  					if (pr) // NS4, IE5
	    					window.print()
	  					else if (da && !mac) // IE4 (Windows)
	    					vbPrintPage()
						else // otros, aunqeu esto deberia ser interceptado desde antes para que no llegue a esta pagina...
	    					alert("Lo sentimos, su browser no soporta impresión automática. Imprima manualmente desde el Menú.");
	  					return;
					}
	
					if (da && !pr && !mac) with (document) {
						writeln('<OBJECT ID="WB" WIDTH="0" HEIGHT="0" CLASSID="clsid:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>');
						writeln('<' + 'SCRIPT LANGUAGE="VBScript">');
						writeln('Sub window_onunload');
						writeln('  On Error Resume Next');
						writeln('  Set WB = nothing');
						writeln('End Sub');
						writeln('Sub vbPrintPage');
						writeln('  msgbox aaa');
						writeln('  OLECMDID_PRINT = 6');
						writeln('  OLECMDEXECOPT_DONTPROMPTUSER = 2');
						writeln('  OLECMDEXECOPT_PROMPTUSER = 1');
						writeln('  On Error Resume Next');
						writeln('  WB.ExecWB OLECMDID_PRINT, OLECMDEXECOPT_PROMPTUSER');
						writeln('End Sub');
						writeln('<' + '/SCRIPT>');
					}
				</script>
			</form>
		</body>
	</logic:present>
</logic:present>

<%
	String salida = "<BODY><P> LO SENTIMOS, NO SE PUEDE IMPRIMIR DICHA IMAGEN POR EL MOMENTO </BODY>";
	boolean auxiliar = true;
%>
<logic:notPresent name="imageWidth" scope="session">
	<%	
		out.print(salida);
		auxiliar = false;
	%>
</logic:notPresent>

<logic:notPresent name="imageWidth" scope="session">	
	<%	
		if(auxiliar)
			out.print(salida);
	%>
</logic:notPresent>

</HTML>
