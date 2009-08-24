<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>SUNARP - T&eacute;rminos y Condiciones</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/global.css">
<script language="JavaScript" src="<%=request.getContextPath()%>/javascript/util.js">
</script>
<%
String flag = (String) request.getAttribute("flag");
%>
<script language="JavaScript">

<%if (flag.equals("1")==true) {%>
function continuar()
{ 
	//window.open("/iri/Afiliacion.do?state=mostrarOpcionPago",target="_self")
	document.form1.method="post";
	document.form1.action="/iri/Afiliacion.do?state=mostrarOpcionPago";
	document.form1.submit();
	//location.href="/iri/Afiliacion.do?state=mostrarOpcionPago";
	//window.location.reload();
}
<%}%>
function SiAcepto()
{ 
	//location.href="/iri/Afiliacion.do?state=aceptarContrato";
	//window.location.reload();	
	document.form1.method="post";
	document.form1.action="/iri/Afiliacion.do?state=aceptarContrato";
	document.form1.submit();	
}

function NoAcepto()
{ 
	//window.open("NoAcepto.jsp",target="_top");
	location.href="/iri/afiliacion/NoAcepto.jsp";
}
function Imprimir()
{ 
	window.print();
}



function regresar()
{ 
	document.form1.method="post";
	document.form1.action="/iri/Afiliacion.do?state=regresar";
	document.form1.submit();	
}

</script>

<META name="GENERATOR" content="IBM WebSphere Studio">
</head>

<body>
<form name=form1>
</form>
<br>
<table class="titulo"><tr><td>Afiliaci&oacute;n de Usuario Individual (Paso 2 de 3)</td></tr></table>
<br>
&nbsp;



<body background="<%=request.getContextPath()%>/images/bk_cuerpo2.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="75%" border="0" cellspacing="1" cellpadding="0" >
  <tr>
    <td align="center"><table width="90%" border="0" cellspacing="1" cellpadding="0">
        <tr> 
          <td class="txtDescrip"><div align="center"><strong>CONTRATO DE ACCESO 
              A LA INFORMACI&Oacute;N REGISTRAL <br>
              DE LA SUPERINTENDENCIA NACIONAL DE LOS REGISTROS PUBLICOS</strong></div>
            <p align="left">Consta por el presente documento el <strong>CONTRATO</strong> 
              DE ACCESO A LA INFORMACI&Oacute;N REGISTRAL A TRAV&Eacute;S DEL 
              SERVICIO DE PUBLICIDAD EN LINEA DE LA SUPERINTENDENCIA NACIONAL 
              DE LOS REGISTROS PUBLICOS que celebran de una parte la <strong>SUPERINTENDENCIA 
              NACIONAL DE REGISTROS P&Uacute;BLICOS</strong> con RUC N&ordm; 202-6707-3580, 
              con domicilio en Calle Armando Blondet 260-264, distrito de San 
              Isidro, Lima, a quien en adelante se denominar&aacute; <strong>SUNARP</strong> 
              y de otra parte <strong>EL USUARIO</strong></p></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA PRIMERA .- SUNARP</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">La <strong>SUNARP</strong> 
              es un Organismo Descentralizado aut&oacute;nomo del Sector Justicia 
              y ente rector del Sistema Nacional de los Registros P&uacute;blicos, 
              con personer&iacute;a jur&iacute;dica de Derecho P&uacute;blico, 
              con patrimonio propio y autonom&iacute;a funcional, jur&iacute;dico 
              registral, t&eacute;cnica, econ&oacute;mica, financiera y administrativa; 
              y, con domicilio y Sede Central en el departamento de Lima, provincia 
              de Lima y Oficinas Desconcentradas en el territorio de la Rep&uacute;blica 
              del Per&uacute;.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA SEGUNDA .- DEL SERVICIO</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">En virtud del presente <strong>Contrato</strong>, 
              el usuario podr&aacute; acceder a la base de datos de contenido 
              jur&iacute;dico registral de la <strong>SUNARP</strong>, para la 
              visualizaci&oacute;n del contenido de las Partidas Registrales incorporadas 
              al Servicio de Publicidad Registral en L&iacute;nea, constituido 
              por los asientos de inscripci&oacute;n y las anotaciones de correlaci&oacute;n, 
              as&iacute; como de la informaci&oacute;n contenida en los &iacute;ndices 
              del Registro en el Repositorio Central de datos de la <strong>SUNARP</strong>, 
              a la fecha y hora en que se formula la consulta.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA TERCERA .- EFICACIA DE LA PUBLICIDAD</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">La publicidad en l&iacute;nea 
              tendr&aacute; efectos &Uacute;NICAMENTE INFORMATIVOS. En ning&uacute;n 
              caso reemplazar&aacute; a la publicidad formal derivada de las certificaciones 
              literales y/o compendiosas que emite la <strong>SUNARP</strong>, 
              en cada uno de sus &oacute;rganos desconcentrados. En consecuencia 
              las copias que se obtengan mediante este servicio, no tendr&aacute;n 
              valor para tr&aacute;mites administrativos ni judiciales, llevar&aacute;n 
              un tramado cruzado con la leyenda: &#8220;COPIA INFORMATIVA EMITIDA 
              A TRAVES DE CONSULTA POR INTERNET. NO TIENE VALIDEZ PARA NING&Uacute;N 
              TR&Aacute;MITE ADMINISTRATIVO, JUDICIAL U OTROS&#8221;. El Servicio 
              de Publicidad Registral en L&iacute;nea comprende tambi&eacute;n 
              las b&uacute;squedas en los &iacute;ndices de la Base de Datos del 
              Repositorio Central de la SUNARP.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA CUARTA .- DE LOS REQUERIMIENTOS 
            T&Eacute;CNICOS M&Iacute;NIMOS </strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">Para acceder al servicio 
              materia del presente Contrato, EL USUARIO deber&aacute; hacerlo 
              a trav&eacute;s de una computadora conectada a internet y configurada 
              para navegaci&oacute;n por web, soportando Cookies y Frames. El 
              sistema de la SUNARP se encuentra optimizado para trabajar con Microsoft 
              Internet Explorer versi&oacute;n 5.0 &oacute; el Netscape Navigator 
              versi&oacute;n 6.0 &oacute; versiones superiores.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA QUINTA .- DEL COSTO DEL SERVICIO</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"> 
              <p>5.1. El costo del Servicio de Publicidad Registral en L&iacute;nea 
                a que se refiere la Cl&aacute;usula Tercera, ser&aacute; el mismo 
                de la tasa registral vigente al momento de la utilizaci&oacute;n 
                del servicio y estar&aacute; en relaci&oacute;n a la b&uacute;squeda 
                de datos o a la visualizaci&oacute;n de cada imagen que aparezca 
                en pantalla y no necesariamente a la impresi&oacute;n de la imagen.</p>
              <p> 5.2. El costo del servicio, variar&aacute; de manera autom&aacute;tica, 
                en la misma oportunidad y monto en que var&iacute;e la respectiva 
                tasa registral.<br>
              </p>
            </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CL&Aacute;USULA SEXTA .- DEL PAGO</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">El pago por el Servicio 
              de Publicidad Registral en L&iacute;nea se har&aacute; de manera 
              anticipada, en el local de la <strong>SUNARP</strong> o mediante 
              pago en l&iacute;nea a trav&eacute;s de las pasarelas de pago que 
              presente el sistema. En ambos casos, se generar&aacute; un cr&eacute;dito 
              cuyo monto <strong>EL USUARIO</strong> debe calcular en funci&oacute;n 
              de la informaci&oacute;n a la que desee acceder. De dicho monto 
              se ir&aacute; debitando autom&aacute;ticamente en funci&oacute;n 
              del costo de los servicios prestados, hasta la extinci&oacute;n 
              del mismo; una vez consumido el cr&eacute;dito a favor de <strong>EL 
              USUARIO</strong> &eacute;ste deber&aacute; efectuar un nuevo pago 
              para continuar accediendo al servicio. </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA SETIMA .- DEL PLAZO</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">El presente <strong>Contrato</strong> 
              es de duraci&oacute;n indeterminada. Sin embargo, si la cuenta permaneciera 
              un mes sin actividad y sin saldo, el <strong>Contrato</strong> se 
              resuelve autom&aacute;ticamente, de pleno derecho, sin necesidad 
              de comunicaci&oacute;n previa alguna.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"><strong>CLAUSULA OCTAVA 
              .- DE LA VIGENCIA</strong></div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"> <div align="justify">El presente <strong>Contrato</strong> 
              estar&aacute; en vigencia inmediatamente despu&eacute;s de haber 
              aceptado las presentes Cl&aacute;usulas Generales. Podr&aacute; 
              requerirse las prestaciones correspondientes luego de acreditado 
              el primer Abono por Derecho de Servicio. </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA NOVENA .- DE LAS OBLIGACIONES 
            DE LA SUNARP</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"> 
              <p>La <strong>SUNARP</strong> se obliga a:</p>
              <p> 9.1. Mantener la permanencia del acceso a la informaci&oacute;n, 
                salvo circunstancias de fuerza mayor o caso fortuito.</p>
              <p>9.2. Proporcionar a EL USUARIO la informaci&oacute;n requerida 
                para el uso del Servicio.<br>
              </p>
            </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DECIMA .- DE LOS DERECHOS DEL 
            USUARIO</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"> 
              <p>EL USUARIO tendr&aacute; derecho a:</p>
              <p> 10.1. Acceder al Servicio de Publicidad Registral en L&iacute;nea 
                desde cualquier lugar, siempre que cuente con un equipo que cumpla 
                con los requisitos t&eacute;cnicos establecidos en la Cl&aacute;usula 
                Cuarta y haya realizado el pago correspondiente de acuerdo a la 
                Cl&aacute;usula Sexta del presente Contrato.</p>
              <p>10.2. Imprimir las im&aacute;genes obtenidas si as&iacute; lo 
                requiere.<br>
              </p>
            </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA UNDECIMA .- DE LAS OBLIGACIONES 
            DEL USUARIO</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"> 
              <p>EL USUARIO se obliga a:</p>
              <p> 11.1. Emplear correctamente el servicio, es decir queda prohibido 
                el uso de alg&uacute;n tipo de software o hardware que afecte 
                el normal funcionamiento del servidor desde el cual se proporciona.</p>
              <p> 11.2. No transferir, prestar o arrendar total o parcialmente 
                el servicio.</p>
              <p> 11.3. No acceder al servicio simult&aacute;neamente utilizando 
                el mismo C&oacute;digo de usuario (C&oacute;digo de usuario).</p>
              <p> 11.4. Utilizar el servicio &uacute;nicamente, con fines informativos.</p>
              <p> 11.5. Dar correcto uso a la informaci&oacute;n obtenida, guardando 
                la confidencialidad del caso, no pudiendo divulgarla, fotocopiarla, 
                transferirla o proporcionarla a terceros, ya sea parcial o totalmente.</p>
              <p> 11.6. No modificar las im&aacute;genes obtenidas a trav&eacute;s 
                del servicio, por ning&uacute;n medio.</p>
            </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DUODECIMA .- DE LA SUPERVISION</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"> 
              <p>LA <strong>SUNARP</strong> est&aacute; facultada para supervisar 
                el cumplimiento de las estipulaciones sobre el uso, as&iacute; 
                como las restricciones y prohibiciones que forman parte del presente<strong> 
                Contrato</strong>.</p>
              <p> <strong>EL USUARIO</strong> dar&aacute; las facilidades a LA 
                <strong>SUNARP</strong> para hacer efectiva dicha supervisi&oacute;n, 
                en caso de negativo o restricci&oacute;n por parte de <strong>EL 
                USUARIO</strong>, LA <strong>SUNARP</strong> podr&aacute; resolver 
                unilateralmente el<strong> Contrato</strong> sin necesidad de 
                comunicaci&oacute;n previa alguna.</p>
            </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DECIMOTERCERA .- DE LOS DERECHOS 
            DE AUTOR</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">La titularidad y derechos 
              de autor del servicio que brinda LA <strong>SUNARP</strong> incluyen 
              las im&aacute;genes, fotograf&iacute;as, textos, aplicaciones o 
              subprogramas incorporados en el servicio, as&iacute; como los documentos 
              y manuales del servicio, corresponden a LA<strong> SUNARP</strong>; 
              encontr&aacute;ndose protegidos con la ley de derechos de autor 
              y disposiciones contenidas en normas internacionales.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DECIMOCUARTA .- CASO FORTUITO 
            O FUERZA MAYOR</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"> 
              <p><strong>LA</strong> <strong>SUNARP</strong> no asume responsabilidad 
                alguna si, por caso fortuito o fuerza mayor, no pudiera cumplir 
                con cualquiera de las obligaciones materia del presente <strong>Contrato</strong>. 
                Se consideran, a t&iacute;tulo de ejemplo, entre &eacute;stos 
                los siguientes actos:</p>
              <p> - Terremotos.</p>
              <p> - Otros Siniestros.</p>
              <p> - Contaminaci&oacute;n por virus inform&aacute;ticos.</p>
              <p> - Fallas en la conexi&oacute;n a <strong>LA</strong> <strong>SUNARP</strong>.</p>
              <p> - Interrupci&oacute;n del sistema de c&oacute;mputo, de la red 
                de teleproceso local y/o la telecomunicaci&oacute;n.</p>
              <p> - Actos y consecuencias de vandalismos, terrorismo y conmoci&oacute;n 
                social.</p>
              <p> - Huelgas y paros.</p>
              <p> - Actos y consecuencias imprevisibles debidamente justificados 
                por <strong> LA SUNARP</strong>.</p>
            </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DECIMOQUINTA .- EXONERACION 
            DE RESPONSABILIDAD</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify"><strong>LA SUNARP</strong> 
              no se hace responsable del mal uso que <strong>EL USUARIO</strong> 
              d&eacute; a la informaci&oacute;n a la que accede a trav&eacute;s 
              del Servicio de Publicidad Registral en L&iacute;nea, ni del uso 
              indebido de las copias que obtenga como resultado del servicio brindado 
              por <strong>La SUNARP</strong>.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DECIMOSEXTA .- RESOLUCION DE 
            CONTRATOS</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">El incumplimiento de alguna 
              de las obligaciones asumidas por <strong>EL USUARIO</strong> en 
              el presente <strong>Contrato</strong> ser&aacute; causal de resoluci&oacute;n 
              autom&aacute;tica del mismo y la eliminaci&oacute;n del c&oacute;digo 
              de usuario (C&oacute;digo de usuario) y la contrase&ntilde;a. <br>
              Para estos efectos, <strong>LA SUNARP</strong> cursar&aacute; una 
              carta notarial a <strong>EL USUARIO</strong> comunic&aacute;ndole 
              la resoluci&oacute;n de Contrato, la que deber&aacute; expresar 
              el motivo de la Resoluci&oacute;n. Esto se har&aacute; sin perjuicio 
              de las acciones legales a las que hubiera lugar. <br>
              Si a la fecha de resoluci&oacute;n del <strong>Contrato</strong>, 
              existiera alg&uacute;n saldo pendiente de consumo, <strong>EL USUARIO</strong> 
              deber&aacute; acercarse a la Oficina Registral m&aacute;s cercana 
              a fin de solicitar la devoluci&oacute;n de dicho saldo. En los casos 
              que el mal uso del servicio haya originado alg&uacute;n tipo de 
              da&ntilde;o (al sistema, a la imagen institucional, etc), el saldo 
              de la cuenta, podr&aacute; ser retenido en calidad de garant&iacute;a 
              de pago, hasta la determinaci&oacute;n de la responsabilidad y del 
              monto de la indemnizaci&oacute;n.<br>
            </div></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DECIMOSETIMA .- MODIFICACI&Oacute;N 
            DE LAS CL&Aacute;USULAS CONTRACTUALES</strong></td>
        </tr>
        <tr>
          <td class="txtDescrip"><p><strong>LA SUNARP</strong> podr&aacute; realizar 
              las modificaciones que crea conveniente a las presentes cl&aacute;usulas 
              generales de contrataci&oacute;n, en cualquier momento, as&iacute; 
              como incluir nuevas cl&aacute;usulas o suprimir algunas de las existentes 
              a la fecha de aceptaci&oacute;n de las mismas por <strong>EL USUARIO</strong>. 
              Toda modificaci&oacute;n del presente <strong>Contrato</strong>, 
              ser&aacute; advertida expresamente a EL USUARIO, en el momento en 
              que &eacute;ste acceda al Servicio de Publicidad Registral en L&iacute;nea 
              de <strong>LA SUNARP</strong>. <strong>EL USUARIO</strong>, podr&aacute; 
              aceptarlas o no. La aceptaci&oacute;n de las modificaciones tiene 
              eficacia normativa inmediata.</p>
            <p>Si <strong>El USUARIO</strong> no acepta las modificaciones al 
              <strong>Contrato</strong>, &eacute;ste se resolver&aacute; inmediatamente. 
              De existir saldo pendiente, se proceder&aacute; de acuerdo a la 
              Cl&aacute;usula D&eacute;cima S&eacute;ptima del presente Contrato.</p></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><strong>CLAUSULA DECIMOOCTAVA .- SOLUCION DE 
            CONTROVERSIAS</strong></td>
        </tr>
        <tr> 
          <td class="txtDescrip"><div align="justify">Las partes someter&aacute;n 
              a arbitraje cualquier controversia que se genere respecto a la ejecuci&oacute;n 
              de las obligaciones derivadas del presente <strong>Contrato</strong> 
              o de la interpretaci&oacute;n de algunas de sus cl&aacute;usulas. 
              El arbitraje se ejecutar&aacute; en la ciudad de Lima. Ser&aacute; 
              v&aacute;lida cualquier comunicaci&oacute;n cursada al domicilio 
              establecido por las partes al momento de la suscripci&oacute;n del 
              presente <strong>Contrato</strong>, salvo se comunique, por v&iacute;a 
              notarial, la respectiva modificaci&oacute;n, con una anticipaci&oacute;n 
              de quince d&iacute;as.</div></td>
        </tr>
        <tr> 
          <td class="txtDescrip">&nbsp;</td>
        </tr>        
 <tr>
<td>
<%if (flag.equals("0")==false) {%>
<HR>
<pre>
FECHA : <%=request.getAttribute("fecha_hora_sistema")%>
USUARIO ID : <font color="#800000"><%=request.getAttribute("usr_id")%>&nbsp;&nbsp;&nbsp;</font>CONTRATO:<font color="#800000"><%=request.getAttribute("num_contrato")%>&nbsp;&nbsp;&nbsp;</font>&nbsp; VERSION:<font color="#800000"><%=request.getAttribute("version_contrato")%></font>
APELLIDOS Y NOMBRES : <font color="#800000"><%=request.getAttribute("usr_nombre")%>&nbsp;&nbsp;&nbsp;&nbsp; </font><%=request.getAttribute("tipo_id")%><font color="#800000">&nbsp;<%=request.getAttribute("num_id")%></font>
</pre><%}%>
    </td>
  </tr>
</table>
<center>
<%if (flag.equals("0")==true) {%>
<table><tr>
    <td width="592"> <div align="left">
        <a href="javascript:SiAcepto()"><img src="images/btn_SiAcepto.gif"  border=0 onmouseover="javascript:mensaje_status('Aceptar Contrato');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
    	<a href="javascript:NoAcepto()"><img src="images/btn_NoAcepto.gif"  border=0 onmouseover="javascript:mensaje_status('No Aceptar Contrato');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
        <a href="javascript:regresar()"><img src="images/btn_regresa.gif"  border=0 onmouseover="javascript:mensaje_status('Regresar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a>     
        <a href="javascript:window.print();"><img src="images/btn_print.gif"  border=0 onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
        <!--input type="BUTTON" value="Imprimir" name="continue"--> 
      </div></td></tr></table>

<% } else {%>
<table><tr>
    <td width="592"> <div align="left">
<a href="javascript:window.print();"><img src="images/btn_print.gif"  border=0 onmouseover="javascript:mensaje_status('Imprimir');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
<a href="javascript:continuar();"><img src="images/btn_continuar.gif"  border=0 onmouseover="javascript:mensaje_status('Continuar');return true;" onmouseOut="javascript:mensaje_status(' ');return true;"></a> 
      </div></td></tr></table>
<% } %>
</center>
</body>
</html>