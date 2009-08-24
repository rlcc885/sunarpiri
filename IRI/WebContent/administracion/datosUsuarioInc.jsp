<%@ page contentType="text/html;charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>

<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
</HEAD>
<tr>
    <td width="114">APELLIDO PATERNO</td>
    <td width="187"><input type="text" name="apellidoPaterno" size="20" maxlength="30" style="width:133" onblur="solonumlet(this)"></td>
    <td width="159">APELLIDO MATERNO</td>
    <td width="140"><input type="text" name="apellidoMaterno" size="20" maxlength="30" style="width:133" onblur="solonumlet(this)"></td>
</tr>
  <tr>
    <td width="114">NOMBRES</td>
    <td width="187"><input type="text" name="nombres" size="20" maxlength="30" style="width:133" onblur="solonumlet(this)"></td>
    <td width="159"></td>
    <td width="140"></td>
  </tr>
  <tr>
    <td width="114">TIPO DOCUMENTO</td>
    <td width="187">
		<select size="1" name="tipoDoc">
        <logic:iterate name="tiposDocumento" id="tipDoc" scope="request">
            <option value="<bean:write name="tipDoc" property="codigo"/>"><bean:write name="tipDoc" property="descripcion"/></option>
        </logic:iterate> 
      </select>
    </td>
    <td width="159">N&Uacute;MERO DOCUMENTO</td>
    <td width="140"><input type="text" name="numDoc" size="11" maxlength="15" style="width:133" onblur="solonumlet(this)"></td>
  </tr>
  <tr>
    <td width="114">TEL&Eacute;FONO</td>
    <td width="187"><input type="text" name="telefono" size="11" maxlength=32 style="width:133" onblur="solonumlet(this)"></td>  
    <td width="159">ANEXO</td>
    <td width="140"><input type="text" name="anexo" size="11" maxlength=10 style="width:133" onblur="solonumlet(this)"></td>
  </tr>
  <tr>
    <td width="114">FAX</td>
    <td width="187"><input type="text" name="fax" size="11" maxlength=32 style="width:133" onblur="solonumlet(this)"></td>
    <td width="159"></td>
    <td width="140"></td>
  </tr>  
  <tr>
    <td width="114">PA&Iacute;S</td>
    <td width="187">
      <select size="1" name="pais" onChange="doValidaPais();" style="width:187">
			<logic:iterate name="arrPais" id="item1" scope="request">
					<logic:equal name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>" selected> <bean:write name="item1" property="descripcion"/> </option>
					</logic:equal>
					<logic:notEqual name="item1" property="codigo" value="01">
						<option value="<bean:write name="item1" property="codigo"/>"> <bean:write name="item1" property="descripcion"/> </option>
					</logic:notEqual>
			</logic:iterate>
      </select>
    </td> 
    <td width="159"></td>
    <td width="140">
    </td>
  </tr>
  <tr>
    <td width="114">DEPARTAMENTO</td>
    <td width="187">
      <select name="departamento"  onchange=llenaComboHijo(); style="width:187" >
       <logic:iterate name="arrDepartamento" id="dpto" scope="request">
            <option value="<bean:write name="dpto" property="codigo"/>"><bean:write name="dpto" property="descripcion"/></option>
       </logic:iterate>
      </select>
    </td> 
    <td width="159">OTRO</td>
    <td width="140">
		<input type="text" name="otroDepartamento" disabled="true" size="11" maxlength=30 style="width:133"  onblur="solonumlet(this)">
    </td>
  </tr>

  <tr>
    <td width="114">PROVINCIA</td>
    <td width="187">
      <SELECT  name="provincia" style="width:187">
      </SELECT>
    </td>    
    <td width="159">DISTRITO</td>
    <td width="140"><input type="text" name="distrito" size="11" maxlength="40" style="width:133" onblur="solonumlet(this)">    </td>
  </tr>
  <tr>
    <td width="114">AV/CALLE/JR Y NRO</td>
    <td width="187"><input type="text" name="direccion" size="11" maxlength=40 style="width:133" value="" onblur="solonumlet(this)">    </td>  
    <td width="159">COD POSTAL</td>
    <td width="140"><input type="text" name="codPostal" size="3" maxlength=12 style="width:133" onblur="solonumlet(this)">    </td>
  </tr>
  <tr>
    <td width="114">CORREO ELECTR&Oacute;NICO</td>
    <td width="187"><input type="text" name="email" size="11" maxlength=40 style="width:133">    </td>    
    <td width="159">    </td>
    <td width="140">    </td>
  </tr>