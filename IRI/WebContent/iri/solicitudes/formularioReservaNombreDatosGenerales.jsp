<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<link href="<%=request.getContextPath()%>/styles/iri.css" rel="stylesheet" type="text/css"/>
	<script language="JavaScript" src="../javascript/util.js"></script>
	<title>Formulario Reserva de Preferencia Registral - Datos Generales</title>
	<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body>
	<br>
	<table cellspacing="0" class="titulo">
		<tr>
			<td><FONT COLOR="black">SOLICITUDES <font size="1">&gt;&gt;</font></FONT><font color="900000"> Solicitud de Inscripci&oacute;n</FONT></td>
  		</tr>
	</table>
	<br>
	<form name="frm" method="POST" class="formulario">
	<table class="tablasinestilo">
    	<tr>
        	<th colspan="5">DATOS BASICOS DE LA SOLICITUD</th>
    	</tr>
    	<tr>
    		<td width="5">&nbsp;</td>
    		<td width="150"><b>ACTO</b></td>
      		<td>
      			<select size="1" name="acto" onChange="#" style="width:330">
            		<option>&lt;&lt; Seleccione el Tipo de Acto &gt;&gt;</option>
            		<option>Acto 1</option>
            		<option>Acto 2</option>
            		<option>Acto 3</option>
            		<option>Acto 4</option>
        		</select>      
        	</td>
        	<td width="65">
	    		<A href="#" onmouseover="javascript:mensaje_status('Buscar por Nombre de Persona Natural Presentante');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
	        		<IMG src="../images/btn_solic.gif" border="0">
	        	</A>
	    	</td>
	    	<td>&nbsp;</td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
        	<td>&nbsp;</td>
        	<td width="150"><b>OFICINA REGISTRAL</b></td>
        	<td>
      			<select size="1" name="cboOficinas" onChange="frm.hidOfic.value = document.frm.cboOficinas.options[document.frm.cboOficinas.selectedIndex].text">
        			<option value="10|07">Andahuaylas</option>
        			<option value="06|02">Apurimac</option>
	        		<option value="03|01">Arequipa</option>
					<option value="10|05">Ayacucho</option>
			      <option value="01|06">Barranca</option>
			      <option value="11|04">Bagua</option>
			      <option value="01|02">Callao</option>
			      <option value="11|02">Cajamarca</option>
		 	      <option value="03|02">Caman&aacute;</option>
			      <option value="01|05">Ca&ntilde;ete</option>
			      <option value="04|02">Casma</option>
			      <option value="03|03">Castilla</option>
			      <option value="11|05">Chachapoyas</option>
		 	      <option value="08|02">Chep&eacute;n</option>
		  		   <option value="11|01">Chiclayo</option>
			      <option value="04|03">Chimbote</option>
			      <option value="10|02">Chincha</option>
			      <option value="11|06">Chota</option>
		 	      <!--option value="13|01">Coronel portillo</option-->
		  	      <option value="06|01">Cusco</option>
		        	<option value="01|04">Huacho</option>
		        	<option value="08|03">Huamachuco</option>
		        	<option value="10|08">Huancavelica</option>
		        	<option value="02|01">Huancayo</option>
		        	<option value="10|06">Huanta</option>
		        	<option value="02|02">Hu&aacute;nuco</option>
		        	<option value="01|03">Huaral</option>
		        	<option value="04|01">Huaraz</option>
		        	<option value="10|01">Ica</option>
		        	<option value="07|02">Ilo</option>
		        	<option value="09|01">Iquitos</option>
		        	<option value="03|04">Islay</option>
		        	<option value="11|03">Ja&eacute;n</option>
		        	<option value="12|03">Juanju&iacute;</option>
		        	<option value="07|03">Juliaca</option>
		        	<option value="02|06">La Merced</option>        
		        	<option value="01|01" selected>Lima</option>
		        	<option value="06|03">Madre de dios</option>
		        	<option value="07|04">Moquegua</option>
		        	<option value="12|01">Moyobamba</option>
		        	<option value="10|04">Nazca</option>
		        	<option value="08|04">Otuzco</option>
		        	<option value="02|04">Pasco</option>
		        	<option value="10|03">Pisco</option>
		        	<option value="05|01">Piura</option>
		        	<option value="13|01">Pucallpa</option>
		        	<option value="07|05">Puno</option>
		        	<option value="06|04">Quillabamba</option>
		        	<option value="08|05">San pedro de lloc</option>
		        	<option value="02|05">Satipo</option>
		        	<!--option value="02|06">Selva Central</option-->
		        	<option value="06|05">Sicuani</option>
		        	<option value="05|02">Sullana</option>
		        	<option value="07|01">Tacna</option>
		        	<option value="12|02">Tarapoto</option>
		        	<option value="02|07">Tarma</option>
		        	<option value="02|08">Tingo Mar&iacute;a</option>
		        	<option value="08|01">Trujillo</option>
		        	<option value="05|03">Tumbes</option>
		        	<option value="09|02">Yurimaguas</option>
		    	</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    	<input type="hidden" name="hidOfic" value="Lima">
		    	<a href="javascript:Abrir_Ventana('acceso/mapas/MAPA1.htm','Oficinas_Registrales','',500,600)" onmouseover="javascript:mensaje_status('Identifique su Oficina Resgistral');return true;" onmouseOut="javascript:mensaje_status(' ');return true;">
		        	Identifique su Oficina Registral
		    	</a>
			</td>
        	<td colspan="2">&nbsp;</td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
        	<td>&nbsp;</td>
      		<td width="150" height="38"><b>DOCUMENTOS</b></td>
      		<td><textarea name="documentos" rows="4" cols="50"></textarea></td>
  			<td colspan="2">&nbsp;</td>
  		</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
		<tr>
			<th colspan="5">DATOS DEL PRESENTANTE</th>
		</tr>
    	<tr>
        	<td colspan="5">
        	<div id="areaPresentante" style="visibility:visible;display:">  
			<table>
				<tr> 
			      	<td>&nbsp;</td>
			      	<td width="150"><strong>PRESENTANTE</strong></td>
			      	<td> 
						<input type="radio" name="presentante">Notario
				  	</td>
			      	<td colspan="2">
						<input type="radio" name="presentante" onchange="">Participante
					</td>
		    	</tr>
	    	</table>
        	</div>  
			</td>
    	</tr>        
    	<tr>
    		<td colspan="5">
    		<div id="areaNatu" style="visibility:visible;display:">
				<table>
		  	 		<tr>
		   	 	    	<td width="5">&nbsp;</td>
		    	      	<td width="150"><strong>APELLIDOS Y NOMBRES<br>A nombre de quien presenta el tr&aacute;mite</strong></td>
		      	    	<td>
		       	     		<table>
		              			<tr>
		                			<td><input type="text" name="txtApPa" size="20" maxlength="50" style="width:133" onblur="sololet(this)"></td>
		                			<td><input type="text" name="txtApMa" size="20" maxlength="40" style="width:133" onblur="sololet(this)"></td>
		                			<td><input type="text" name="txtNom" size="20" maxlength="40" style="width:133" onblur="sololet(this)"></td>
		              			</tr>
		              			<tr>
		                			<td>&nbsp;Apellido Paterno</td>
		                			<td>&nbsp;Apellido Materno</td>
		               	 			<td>&nbsp;Nombres</td>
		              			</tr>
		            		</table>
		            		<!--input type="text" name="txtApPa" size="20" maxlength="18" style="width:133" onblur="sololet(this)" value="">
		            		<input type="text" name="txtApMa" size="20" maxlength="18" style="width:133" onblur="sololet(this)" value="">
		            		<input type="text" name="txtNom" size="20" maxlength="18" style="width:133" onblur="sololet(this)" value=""-->
		          		</td>
		        	</tr>
				</table>
			</div>
        	</td>
    	</tr>
    	<tr>
	    	<td colspan="5">
	    	<div id="areaNatu" style="visibility:visible;display:">
				<table>
					<tr>
			  	  		<td colspan="5">&nbsp;</td>
			    	</tr>
			    	<tr> 
			     	 	<td>&nbsp;</td>
			      		<td width="150"><strong>TIPO DE DOCUMENTO</strong></td>
			      		<td>
	    						<select name="cboSolTipDoc" onChange="#" style="width:180">
									<logic:iterate name="docu" id="item1" scope="request">
			  							<option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
									</logic:iterate>
								</select>
 				  		</td>
			      		<td width="133"><b>NUMERO DE DOCUMENTO</b></td>
			      		<td width="133"><input type="text" name="txtSolNumDoc" size="12" maxlength="11" style="width:87" onBlur="sololet(this)" value=""></td>
		        	</tr>
			  		<tr>
			    		<td colspan="5">&nbsp;</td>
			  		</tr>  
			  		<tr>
			    		<td width="5">&nbsp;</td>
				  		<td width="150"><strong>DEPARTAMENTO</strong></td>
				  		<td>
			      			<select size="1" name="departamento" onChange="#" style="width:180">
			         	   		<option>Departamento 1</option>
			          	  		<option>Departamento 2</option>
			            		<option>Departamento 3</option>
			            		<option>Departamento 4</option>
			        		</select>
			   	  		</td>
				  		<td width="65">&nbsp;</td>
				  		<td>&nbsp;</td>
			  		</tr>
			  		<tr>
			      		<td colspan="5">&nbsp;</td>
			  		</tr>
			  		<tr>
			      		<td width="5">&nbsp;</td>
				  		<td width="150"><strong>PROVINCIA</strong></td>
				  		<td> 
			      			<select size="1" name="provincia" onChange="#" style="width:180">
			            		<option>Provincia 1</option>
			            		<option>Provincia 2</option>
			            		<option>Provincia 3</option>
			            		<option>Provincia 4</option>
			        		</select>
				  		</td>
				  		<td width="150"><strong>DISTRITO</strong></td>
				  		<td><input type="text" name="distrito" onBlur="sololet(this)"></td>
			  		</tr>
			  		<tr>
			    		<td colspan="5">&nbsp;</td>
			  		</tr>
			  		<tr>
			      		<td>&nbsp;</td>
				  		<td width="150"><strong>DIRECCION</strong></td>
				  		<td width="180"><input name="direccion" type="text" onBlur="sololet(this)" maxlength="90"></td>
				  		<td width="100"><strong>COD POSTAL</strong></td>
				  		<td width="150"><input name="codPostal" type="text" onBlur="sololet(this)"></td>
			  		</tr>
				</table>
			</div>
			</td>
    	</tr>
  		<tr>
    		<td colspan="5">&nbsp;</td>
  		</tr>
    	<tr>
    		<td align="right" colspan="5"><input type="image" src="../images/btn_continuar.gif" onmouseover="#" onmouseOut="#" onClick="#"></td>
    	</tr>
  	</table>
	</form>
	<br>
    <table>
       	<tr>
            <td width="596" align="justify">
				Art�culo 15.- Denominaci�n y raz�n social No es inscribible la sociedad que adopte una denominaci�n completa o abreviada o una raz�n social igual a la de otra preexistente en el �ndice. Tampoco es inscribible la sociedad que adopte una denominaci�n abreviada que no est� compuesta por palabras, primeras letras o s�labas de la denominaci�n completa. No es exigible la inclusi�n de siglas de la forma societaria en la denominaci�n abreviada, salvo mandato legal en contrario.<BR><BR>
				Art�culo 16.- Igualdad de denominaci�n o de raz�n social Se entiende que existe igualdad cuando hay total coincidencia entre una denominaci�n o una raz�n social con otra preexistente en el �ndice, cualquiera sea la forma societaria adoptada. Tambi�n existe igualdad, en las variaciones de matices de escasa significaci�n tales como el uso de las mismas palabras con la adici�n o supresi�n de art�culos, espacios, preposiciones, conjunciones, acentos, guiones o signos de puntuaci�n; el uso de las mismas palabras en diferente orden, as� como del singular y plural.<BR><BR>
				Art�culo 18.- Reserva de preferencia registral La reserva de preferencia registral salvaguarda una denominaci�n completa y, en caso de ser solicitada, su denominaci�n abreviada, o una raz�n social, durante el proceso de constituci�n de una sociedad o de modificaci�n del pacto social.<BR><BR>
				Art�culo 19.- Personas legitimadas para solicitar la reserva La solicitud de Reserva puede ser presentada por uno o varios socios, el abogado o el Notario interviniente en la constituci�n de una sociedad o en la modificaci�n del pacto social, o por la persona autorizada por la propia sociedad, si �sta estuviera constituida.
			</td>
		</tr>
	</table>
</html>

