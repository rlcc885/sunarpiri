package gob.pe.sunarp.extranet.util;

/*
Clase Utilitaria para funciones comunes
*/
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.prepago.bean.PrepagoBean;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqDirectaBean;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.CriterioBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ibm.ws.management.commands.cluster.CreateMemberCommand.FirstMemberStep;
import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;
import gob.pe.sunarp.extranet.administracion.bean.*;
import gob.pe.sunarp.extranet.caja.bean.DetalleCajaBean;
import gob.pe.sunarp.extranet.common.Secuenciales;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import javax.servlet.jsp.*;
import javax.swing.ComboBoxEditor;

import java.io.*;

public class Tarea implements Constantes {

public static String getDireccion(DBConnection conn, String personaId) throws Throwable
{
	StringBuffer sb = new StringBuffer();
	DboDireccion dboDireccion = new DboDireccion(conn);
	dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID,personaId);
	if (dboDireccion.find()==false)
		return sb.toString();
		
	sb.append(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
	sb.append(" ").append(dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));	
	
	String paisId  = dboDireccion.getField(DboDireccion.CAMPO_PAIS_ID);
	
	DboTmPais dboTmPais = new DboTmPais(conn);
	dboTmPais.setField(DboTmPais.CAMPO_PAIS_ID,paisId);
	String paisDesc="";
	if (dboTmPais.find())
		paisDesc = dboTmPais.getField(DboTmPais.CAMPO_NOMBRE);
	
	if (paisId.equals("01")==false)
	{
		//no es perú
		sb.append(" ").append(dboDireccion.getField(DboDireccion.CAMPO_LUG_EXT));	
		sb.append(" ").append(paisDesc);
		return sb.toString();
	}

	DboTmDepartamento dboTmDepartamento = new DboTmDepartamento(conn);
	dboTmDepartamento.setField(DboTmDepartamento.CAMPO_PAIS_ID,paisId);
	dboTmDepartamento.setField(DboTmDepartamento.CAMPO_ESTADO,"1");
	dboTmDepartamento.setField(DboTmDepartamento.CAMPO_DPTO_ID,dboDireccion.getField(dboDireccion.CAMPO_DPTO_ID));
	if (dboTmDepartamento.find())
	{
		sb.append(" ").append(dboTmDepartamento.getField(DboTmDepartamento.CAMPO_NOMBRE));
		DboTmProvincia dboTmProvincia = new DboTmProvincia(conn);
		dboTmProvincia.setField(DboTmProvincia.CAMPO_PAIS_ID,paisId);
		dboTmProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID,dboDireccion.getField(dboDireccion.CAMPO_DPTO_ID));
		dboTmProvincia.setField(DboTmProvincia.CAMPO_PROV_ID,dboDireccion.getField(DboDireccion.CAMPO_PROV_ID));
		dboTmProvincia.setField(DboTmProvincia.CAMPO_ESTADO,"1");
		if (dboTmProvincia.find())
			sb.append(" ").append(dboTmProvincia.getField(DboTmProvincia.CAMPO_NOMBRE));
	}
	
	sb.append(" ").append(paisDesc);
	return sb.toString();
}

public static String getNombreClase(int codTabla)
{
String nombreClase="";
switch (codTabla)
	{
	case Constantes.TABLA_TM_ACTO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmActo"; 
		break;
	case Constantes.TABLA_TM_AREA_REGISTRAL: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmAreaRegistral"; 
		break;
	case Constantes.TABLA_TM_LIBRO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmLibro"; 
		break;
	case Constantes.TABLA_TM_RUBRO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmRubro"; 
		break;						
	case Constantes.TABLA_TM_DEPARTAMENTO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmDepartamento"; 
		break;
	case Constantes.TABLA_TM_PROVINCIA: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmProvincia"; 
		break;						
	case Constantes.TABLA_TM_DISTRITO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmDistrito"; 
		break;
	case Constantes.TABLA_TM_DOC_IDEN: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmDocIden";
		break;						
	case Constantes.TABLA_TM_GIRO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmGiro";
		break;												
	case Constantes.TABLA_NOTARIA: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmInstSir";
		break;						
	case Constantes.TABLA_PARTIC_LIBRO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskParticLibro";
		break;																		
	case Constantes.TABLA_TM_SERVICIO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmServicio";
		break;												
		
	case Constantes.TABLA_TM_JURISDICCION: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmJurisdiccion";
		break;	
	case Constantes.TABLA_REGIS_PUBLICO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskRegisPublico";
		break;	
	case Constantes.TABLA_OFIC_REGISTRAL: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskOficRegistral";
		break;	
	case Constantes.TABLA_TM_BANCO: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmBanco";
		break;
	//modificado por JACR - inicio
	case Constantes.TABLA_TM_MODELO_VEHI: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmModelo";
		break;		
	case Constantes.TABLA_TM_MARCA_VEHI: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmMarca";
		break;												
	case Constantes.TABLA_TM_COND_VEHI: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmCondicion";
		break;	
	case Constantes.TABLA_TM_TIPO_VEHI: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmTipoVehi";
		break;
	case Constantes.TABLA_TM_TIPO_COMB: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmTipoComb";
		break;
	case Constantes.TABLA_TM_TIPO_CARR: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTmTipoCarroceria";
		break;
	case Constantes.TABLA_TIPO_AFEC: 
		nombreClase = "gob.pe.sunarp.extranet.mantenimiento.task.TaskTipoAfec";
		break;
	//modificado por JACR - fin
	}
return nombreClase;	
}

	/******************* MODIFICADO POR GIANCARLO OCHOA *************************/
	public static ArrayList getComboPaises(DBConnection conn) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmPais dbo2 = new DboTmPais(conn);
		dbo2.setFieldsToRetrieve(
			DboTmPais.CAMPO_PAIS_ID + "|" + DboTmPais.CAMPO_NOMBRE + "|" + DboTmPais.CAMPO_GENTILICIO);
		dbo2.setField(DboTmPais.CAMPO_ESTADO, "1");
		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboTmPais.CAMPO_NOMBRE);
		for (int i = 0; i < arr2.size(); i++) {
			ComboBean b = new ComboBean();
			DboTmPais d = (DboTmPais) arr2.get(i);
			b.setCodigo(d.getField(DboTmPais.CAMPO_PAIS_ID));
			b.setDescripcion(d.getField(DboTmPais.CAMPO_NOMBRE));
			b.setAtributo1(d.getField(DboTmPais.CAMPO_GENTILICIO));
			resultado.add(b);
		}
		return resultado;
	}
	/**************************************************************************/
	
	/***** AGREGADO JBUGARIN INICIO DESCAJ 16/01/2007*****/
	public static ArrayList getComboCajas(DBConnection conn, String codigoZona)throws Throwable {
	ArrayList resultadoCajas = new ArrayList();
	DboTaCaja dboTaCaja = new DboTaCaja(conn);
	dboTaCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_EMPL +"|"+ DboTaCaja.CAMPO_CO_CAJA);
	dboTaCaja.setField(DboTaCaja.CAMPO_CO_SEDE,codigoZona);
	java.util.ArrayList arr2 = dboTaCaja.searchAndRetrieveList(DboTaCaja.CAMPO_CO_EMPL);
	
	DboCuenta dboCuenta = new DboCuenta(conn);
	//java.util.ArrayList arr = dboCuenta.searchAndRetrieveList(DboCuenta.CAMPO_USR_ID);
	
	for (int i = 0; i < arr2.size(); i++) {
				ComboBean b = new ComboBean();
				DboTaCaja d = (DboTaCaja) arr2.get(i);
				String codEmpleado = d.getField(DboTaCaja.CAMPO_CO_EMPL);
				
				//b.setDescripcion(d.getField(DboTaCaja.CAMPO_CO_EMPL));
				dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
				dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID,codEmpleado);
				if(dboCuenta.find()==true){
		
				b.setCodigo(dboCuenta.getField(DboCuenta.CAMPO_USR_ID));
				b.setDescripcion(dboCuenta.getField(DboCuenta.CAMPO_USR_ID));
					}
		        resultadoCajas.add(b);
				dboCuenta.clearAll();
			}
	
	
	
	return resultadoCajas;
		
	}
	public static ArrayList getComboMotivosExtorno(DBConnection conn)throws Throwable {
		ArrayList resultadoMotivos = new ArrayList();
		DboTATabl dboTaTabl = new DboTATabl(conn);
		dboTaTabl.setFieldsToRetrieve(DboTATabl.CAMPO_VA_COLU +"|"+ DboTATabl.CAMPO_DE_VALO);
		dboTaTabl.setField(DboTATabl.CAMPO_CO_COLU,"CO_EXT");
		java.util.ArrayList arr2 = dboTaTabl.searchAndRetrieveList(DboTATabl.CAMPO_VA_COLU +"|"+ DboTATabl.CAMPO_DE_VALO);
		
			
		for (int i = 0; i < arr2.size(); i++) {
					ComboBean b = new ComboBean();
					DboTATabl d = (DboTATabl) arr2.get(i);
					b.setCodigo(d.getField(DboTATabl.CAMPO_VA_COLU));
					b.setDescripcion(d.getField(DboTATabl.CAMPO_DE_VALO));
					resultadoMotivos.add(b);
					
				}
	
	
	
		return resultadoMotivos;
		
		}
	/***** AGREGADO JBUGARIN FIN DESCAJ 16/01/2007*****/

	/***** AGREGADO LSUAREZ INICIO DESCAJ 19/01/2007*****/
	public static ArrayList getComboCajasPorEdificio(DBConnection conn, String codigoZonaRegistral, String codigoEdificio)throws Throwable {
	ArrayList resultadoCajas = new ArrayList();
	DboTaCaja dboTaCaja = new DboTaCaja(conn);
	dboTaCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_EMPL +"|"+ DboTaCaja.CAMPO_CO_CAJA);
	dboTaCaja.setField(DboTaCaja.CAMPO_CO_SEDE, codigoZonaRegistral);
	dboTaCaja.setField(DboTaCaja.CAMPO_CO_ZONA, codigoEdificio);
	dboTaCaja.setField(DboTaCaja.CAMPO_ESTA, DetalleCajaBean.ESTADO_CERRADO);
	java.util.ArrayList arr2 = dboTaCaja.searchAndRetrieveList(DboTaCaja.CAMPO_CO_EMPL);
	
	DboCuenta dboCuenta = new DboCuenta(conn);
	ComboBean comboBean = null;
	for (int i = 0; i < arr2.size(); i++) {
		comboBean = new ComboBean();
		DboTaCaja d = (DboTaCaja) arr2.get(i);
		String codEmpleado = d.getField(DboTaCaja.CAMPO_CO_EMPL);
		dboCuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
		dboCuenta.setField(DboCuenta.CAMPO_CUENTA_ID, codEmpleado);
		if(dboCuenta.find()==true){
			comboBean.setCodigo(d.getField(DboTaCaja.CAMPO_CO_CAJA));
			comboBean.setDescripcion(dboCuenta.getField(DboCuenta.CAMPO_USR_ID));
		}
		resultadoCajas.add(comboBean);
		dboCuenta.clearAll();
	}
	
	return resultadoCajas;
		
	}
	/***** AGREGADO LSUAREZ FIN DESCAJ 19/01/2007*****/
	
	public static String getValorParametro(DBConnection conn, String id) throws Throwable 
	{
		DboParametros dbpar = new DboParametros(conn);
		dbpar.setFieldsToRetrieve(DboParametros.CAMPO_VALOR);
		dbpar.setField(DboParametros.CAMPO_PARAM_ID, id);
		dbpar.find();
		return dbpar.getField(dbpar.CAMPO_VALOR);
	}
	
	
	
	public static ArrayList getComboOficinasRegistrales(DBConnection conn) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboOficRegistral dbo2 = new DboOficRegistral(conn);
		dbo2.setFieldsToRetrieve(
			DboOficRegistral.CAMPO_REG_PUB_ID +"|"+ 
			DboOficRegistral.CAMPO_OFIC_REG_ID+"|"+
			DboOficRegistral.CAMPO_NOMBRE);

		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboOficRegistral.CAMPO_NOMBRE);
		for (int i = 0; i < arr2.size(); i++) 
		{
			ComboBean b = new ComboBean();
			DboOficRegistral d = (DboOficRegistral) arr2.get(i);
			String ofi =d.getField(DboOficRegistral.CAMPO_REG_PUB_ID)+"|"+d.getField(DboOficRegistral.CAMPO_OFIC_REG_ID);
			b.setCodigo(ofi);
			b.setDescripcion(d.getField(DboOficRegistral.CAMPO_NOMBRE));
			resultado.add(b);
		}
		return resultado;
	}
	

	public static ArrayList getComboDepartamentos(DBConnection conn) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmDepartamento dbo2 = new DboTmDepartamento(conn);
		/******* AGREGADO POR GIANCARLO OCHOA VICENTE ***/
		dbo2.setField(dbo2.CAMPO_PAIS_ID, "01");
		/***********************************************/
		dbo2.setField(dbo2.CAMPO_ESTADO,"1");
		dbo2.setFieldsToRetrieve(
			DboTmDepartamento.CAMPO_PAIS_ID +"|"+ 
			DboTmDepartamento.CAMPO_DPTO_ID+"|"+
			DboTmDepartamento.CAMPO_NOMBRE);

		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
		for (int i = 0; i < arr2.size(); i++) 
		{
			ComboBean b = new ComboBean();
			DboTmDepartamento d = (DboTmDepartamento) arr2.get(i);
			String cod =d.getField(DboTmDepartamento.CAMPO_PAIS_ID)+"|"+d.getField(DboTmDepartamento.CAMPO_DPTO_ID);
			b.setCodigo(cod);
			b.setDescripcion(d.getField(DboTmDepartamento.CAMPO_NOMBRE));
			resultado.add(b);
		}
		return resultado;
	}			
	

	/*** AGREGADO POR GIANCARLO OCHOA ***********/
	public static ArrayList getComboProvincias(DBConnection conn, String idDepartamento) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmProvincia dbo2 = new DboTmProvincia(conn);
		dbo2.setField(dbo2.CAMPO_PAIS_ID, "01");
		dbo2.setField(dbo2.CAMPO_DPTO_ID, idDepartamento);
		dbo2.setField(dbo2.CAMPO_ESTADO,"1");
		dbo2.setFieldsToRetrieve(
			DboTmProvincia.CAMPO_PAIS_ID +"|"+ 
			DboTmProvincia.CAMPO_DPTO_ID+"|"+
			DboTmProvincia.CAMPO_PROV_ID+"|"+
			DboTmProvincia.CAMPO_NOMBRE);

		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
		for (int i = 0; i < arr2.size(); i++) 
		{
			ComboBean b = new ComboBean();
			DboTmProvincia d = (DboTmProvincia) arr2.get(i);
			String cod =d.getField(DboTmProvincia.CAMPO_PAIS_ID)+"|"+d.getField(DboTmProvincia.CAMPO_DPTO_ID)+"|"+d.getField(DboTmProvincia.CAMPO_PROV_ID);
			b.setCodigo(cod);
			b.setDescripcion(d.getField(DboTmProvincia.CAMPO_NOMBRE));
			resultado.add(b);
		}
		return resultado;
	}			
	
	public static ArrayList getComboDistritos(DBConnection conn, String idDepartamento, String idProvincia) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmDistrito dbo2 = new DboTmDistrito(conn);
		dbo2.setField(dbo2.CAMPO_PAIS_ID, "01");
		dbo2.setField(dbo2.CAMPO_DPTO_ID, idDepartamento);
		dbo2.setField(dbo2.CAMPO_PROV_ID, idProvincia);
		dbo2.setField(dbo2.CAMPO_ESTADO,"1");
		dbo2.setFieldsToRetrieve(
			DboTmDistrito.CAMPO_PAIS_ID +"|"+ 
			DboTmDistrito.CAMPO_DPTO_ID+"|"+
			DboTmDistrito.CAMPO_PROV_ID+"|"+
			DboTmDistrito.CAMPO_DIST_ID+"|"+
			DboTmDistrito.CAMPO_NOMBRE);

		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboTmDistrito.CAMPO_NOMBRE);
		for (int i = 0; i < arr2.size(); i++) 
		{
			ComboBean b = new ComboBean();
			DboTmDistrito d = (DboTmDistrito) arr2.get(i);
			String cod =d.getField(DboTmDistrito.CAMPO_PAIS_ID)+"|"+d.getField(DboTmDistrito.CAMPO_DPTO_ID)+"|"+d.getField(DboTmDistrito.CAMPO_PROV_ID)+"|"+d.getField(DboTmDistrito.CAMPO_DIST_ID);
			b.setCodigo(cod);
			b.setDescripcion(d.getField(DboTmDistrito.CAMPO_NOMBRE));
			resultado.add(b);
		}
		return resultado;
	}
	
	public static ArrayList getComboEstadoCiviles(DBConnection conn) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTATabl dbo2 = new DboTATabl(conn);
		dbo2.setField(dbo2.CAMPO_CO_COLU, "ES_CIVL");
		dbo2.setField(dbo2.CAMPO_IN_ESTD,"A");
		dbo2.setFieldsToRetrieve(
			DboTATabl.CAMPO_VA_COLU +"|"+ 
			DboTATabl.CAMPO_DE_VALO);

		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboTATabl.CAMPO_DE_VALO);
		for (int i = 0; i < arr2.size(); i++) 
		{
			ComboBean b = new ComboBean();
			DboTATabl d = (DboTATabl) arr2.get(i);
			String cod =d.getField(DboTATabl.CAMPO_VA_COLU);
			b.setCodigo(cod);
			b.setDescripcion(d.getField(DboTATabl.CAMPO_DE_VALO));
			resultado.add(b);
		}
		return resultado;
	}	
	/*******************************************/
/*DESCAJ 03/02/2007 IFIGUEROA INICIO*/
	public static ArrayList getComboCaducidadClave(DBConnection conn) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTATabl dbo2 = new DboTATabl(conn);
		dbo2.setField(DboTATabl.CAMPO_CO_COLU, "CAD_CLAVE_EXT");
		dbo2.setField(DboTATabl.CAMPO_IN_ESTD,"A");
		dbo2.setFieldsToRetrieve(
			DboTATabl.CAMPO_VA_COLU +"|"+ 
			DboTATabl.CAMPO_DE_VALO+"|"+DboTATabl.CAMPO_DE_CORT_VALO);
	
		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboTATabl.CAMPO_VA_COLU);
		for (int i = 0; i < arr2.size(); i++) 
		{
			ComboBean b = new ComboBean();
			DboTATabl d = (DboTATabl) arr2.get(i);
			String cod =d.getField(DboTATabl.CAMPO_VA_COLU);
			b.setCodigo(cod);
			b.setDescripcion(d.getField(DboTATabl.CAMPO_DE_VALO));
			b.setAtributo1(d.getField(DboTATabl.CAMPO_DE_CORT_VALO));
			resultado.add(b);
		}
		return resultado;
	}	
	
	public static ComboBean getDiasCaducidadClaveInt(DBConnection conn) throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTATabl dbo2 = new DboTATabl(conn);
		dbo2.setField(DboTATabl.CAMPO_CO_COLU, "CAD_CLAVE_INT");
		dbo2.setField(DboTATabl.CAMPO_IN_ESTD,"A");
		dbo2.setFieldsToRetrieve(
			DboTATabl.CAMPO_VA_COLU +"|"+ 
			DboTATabl.CAMPO_DE_VALO+"|"+DboTATabl.CAMPO_DE_CORT_VALO);
	
		java.util.ArrayList arr2 = dbo2.searchAndRetrieveList(DboTATabl.CAMPO_VA_COLU);
	
			ComboBean b = new ComboBean();
			DboTATabl d = (DboTATabl) arr2.get(0);
			String cod =d.getField(DboTATabl.CAMPO_VA_COLU);
			b.setCodigo(cod);
			b.setDescripcion(d.getField(DboTATabl.CAMPO_DE_VALO));
			b.setAtributo1(d.getField(DboTATabl.CAMPO_DE_CORT_VALO));
			resultado.add(b);
		
		return b;
	}		
/*DESCAJ 03/02/2007 IFIGUEROA FIN*/		

/*DESCAJ 18/01/2007 LSUAREZ INICIO*/	
public static ArrayList getComboEdificios(DBConnection conn, String codigoZonaRegistral) throws Throwable {

	ArrayList listaEdificios = new ArrayList();
	DboTaCaja dboTaCaja = new DboTaCaja(conn);
	dboTaCaja.setFieldsToRetrieve(DboTaCaja.CAMPO_CO_ZONA);
	dboTaCaja.setField(DboTaCaja.CAMPO_CO_SEDE, codigoZonaRegistral);
	dboTaCaja.setFieldDistinct(DboTaCaja.CAMPO_CO_ZONA, true);
	List listaEdificiosAux = dboTaCaja.searchAndRetrieveList();
	
	ComboBean comboBean = null;
	for (int i = 0; i < listaEdificiosAux.size(); i++) {
		comboBean = new ComboBean();
		DboTaCaja caja = (DboTaCaja) listaEdificiosAux.get(i);
		DboTATabl dboTATabl = new DboTATabl(conn);
		dboTATabl.setFieldsToRetrieve(DboTATabl.CAMPO_VA_COLU + "|" + DboTATabl.CAMPO_DE_VALO);
		dboTATabl.setField(DboTATabl.CAMPO_CO_COLU, "CO_EDIF");
		dboTATabl.setField(DboTATabl.CAMPO_IN_ESTD, "A");
		dboTATabl.setField(DboTATabl.CAMPO_VA_COLU, caja.getField(DboTaCaja.CAMPO_CO_ZONA));
		if(dboTATabl.find()==true){
			comboBean.setCodigo(dboTATabl.getField(DboTATabl.CAMPO_VA_COLU));
			comboBean.setDescripcion(dboTATabl.getField(DboTATabl.CAMPO_DE_VALO));
		}
		listaEdificios.add(comboBean);
	}
	
	return listaEdificios;

}	
/*DESCAJ 18/01/2007 LSUAREZ FIN*/	

public static ArrayList getComboRubros(DBConnection conn) throws Throwable 
{
		ArrayList resultado = new ArrayList();
		DboTmRubro dbo = new DboTmRubro(conn);
		dbo.setFieldsToRetrieve(dbo.CAMPO_COD_RUBRO + "|" + dbo.CAMPO_NOMBRE);
		dbo.setField(dbo.CAMPO_ESTADO, "1");
		java.util.ArrayList arr2 = dbo.searchAndRetrieveList(dbo.CAMPO_NOMBRE);
		for (int i = 0; i < arr2.size(); i++) 
		{
			ComboBean b = new ComboBean();
			DboTmRubro d = (DboTmRubro) arr2.get(i);
			b.setCodigo(d.getField(dbo.CAMPO_COD_RUBRO));
			b.setDescripcion(d.getField(dbo.CAMPO_NOMBRE));
			resultado.add(b);
		}
		return resultado;
}	

	public static ArrayList getComboTiposDocumento(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmDocIden dbo1 = new DboTmDocIden(conn);
		dbo1.setCustomWhereClause(
			DboTmDocIden.CAMPO_ESTADO
				+ "= '1' and("
				+ DboTmDocIden.CAMPO_TIPO_PER
				+ "='N' or "
				+ DboTmDocIden.CAMPO_TIPO_PER
				+ "='A')");
		java.util.ArrayList arr1 = dbo1.searchAndRetrieveList();

		for (int i = 0; i < arr1.size(); i++) {
			ComboBean b = new ComboBean();
			DboTmDocIden d = (DboTmDocIden) arr1.get(i);
			b.setCodigo(d.getField(DboTmDocIden.CAMPO_TIPO_DOC_ID));
			b.setDescripcion(d.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
			resultado.add(b);
		}
		return resultado;
	}
	
	public static ArrayList getComboTiposDocumentoPJ(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmDocIden dbo1 = new DboTmDocIden(conn);
		dbo1.setCustomWhereClause(
			DboTmDocIden.CAMPO_ESTADO
				+ "= '1' and("
				+ DboTmDocIden.CAMPO_TIPO_PER
				+ "='J' or "
				+ DboTmDocIden.CAMPO_TIPO_PER
				+ "='A')");
		java.util.ArrayList arr1 = dbo1.searchAndRetrieveList();

		for (int i = 0; i < arr1.size(); i++) {
			ComboBean b = new ComboBean();
			DboTmDocIden d = (DboTmDocIden) arr1.get(i);
			b.setCodigo(d.getField(DboTmDocIden.CAMPO_TIPO_DOC_ID));
			b.setDescripcion(d.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
			resultado.add(b);
		}
		return resultado;
	}
	
	
	public static ArrayList getComboAreasRegistrales(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmAreaRegistral dboAreaRegistral = new DboTmAreaRegistral(conn);
		dboAreaRegistral.setFieldsToRetrieve(DboTmAreaRegistral.CAMPO_AREA_REG_ID + "|" + DboTmAreaRegistral.CAMPO_NOMBRE);
		dboAreaRegistral.setField(DboTmAreaRegistral.CAMPO_ESTADO, "1");
		ArrayList arrx = dboAreaRegistral.searchAndRetrieveList(DboTmAreaRegistral.CAMPO_NOMBRE);
		
		java.util.ArrayList arreglo2 = new java.util.ArrayList();
			for (int i = 0; i < arrx.size(); i++) 
			{
				ComboBean bean1 = new ComboBean();
				bean1.setCodigo(((DboTmAreaRegistral) arrx.get(i)).getField(DboTmAreaRegistral.CAMPO_AREA_REG_ID));
				bean1.setDescripcion(((DboTmAreaRegistral) arrx.get(i)).getField(DboTmAreaRegistral.CAMPO_NOMBRE));
				resultado.add(bean1);
			}

		return resultado;
	}
//Tarifario
	public static ArrayList getComboAreaLibro(Connection conn, int servId)
		throws Throwable 
	{
		StringBuffer quebusq = new StringBuffer();
		
		//Query busqueda de combo
		quebusq.append("SELECT gla.cod_grupo_libro_area, gla.desc_grupo_libro_area, t.prec_ofic, gla.cod_area ");
		quebusq.append("FROM grupo_libro_area gla, tarifa t ");
		quebusq.append("WHERE gla.cod_grupo_libro_area = t.cod_grupo_libro_area ");
		/*** inicio: jrosas 20-07-07***/
		quebusq.append("AND t.servicio_id = ? and gla.cod_grupo_libro_area!='5' AND  gla.cod_grupo_libro_area!='10'");// eliminarmos prenda agricola y mineria
		/*** fin: jrosas 20-07-07***/
		//if (Loggy.isTrace(this))
		//System.out.println("Combo QUERY ---> "+quebusq.toString());
		//Statement stmt = conn.createStatement();
		//ResultSet rset = stmt.executeQuery(quebusq.toString());
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(quebusq.toString());
		pstmt.setInt(1,servId);
		ResultSet rset = pstmt.executeQuery();
	
		ArrayList resultado = new ArrayList();
		boolean encontro = false;
		while (rset.next())
		{
			ComboBean bean1 = new ComboBean();
			bean1.setCodigo(rset.getString("cod_grupo_libro_area"));
			bean1.setDescripcion(rset.getString("desc_grupo_libro_area"));
			bean1.setAtributo1(rset.getString("prec_ofic"));
			bean1.setAtributo2(rset.getString("cod_area"));
			
			resultado.add(bean1);
		}
		return resultado;
	}
	//Inicio:mgarate:11/07/2007
	public static ArrayList getComboAreaLibro(Connection conn, int served, int servRmc)	throws Throwable 
	{
		StringBuffer quebusq = new StringBuffer();
		
		quebusq.append("SELECT gla.cod_grupo_libro_area, gla.desc_grupo_libro_area, t.prec_ofic, gla.cod_area "); 
		quebusq.append("FROM grupo_libro_area gla, tarifa t ");
		quebusq.append("WHERE gla.cod_grupo_libro_area = t.cod_grupo_libro_area and ");
		quebusq.append("gla.cod_grupo_libro_area!='5' AND (t.servicio_id =? or t.servicio_id=?) ");   
		quebusq.append("order by 1 ");

		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(quebusq.toString());
		
		pstmt.setInt(1,served);
		pstmt.setInt(2,servRmc);
		
		ResultSet rset = pstmt.executeQuery();
	
		ArrayList resultado = new ArrayList();
		while(rset.next())
		{
			ComboBean bean = new ComboBean();
			bean.setCodigo(rset.getString("cod_grupo_libro_area"));
			bean.setDescripcion(rset.getString("desc_grupo_libro_area"));
			bean.setAtributo1(rset.getString("prec_ofic"));
			bean.setAtributo2(rset.getString("cod_area"));
			
			resultado.add(bean);
		}
		return resultado;
	}
	public static ArrayList getComboAreaLibro(Connection conn, int served, int servRmc, int servSigc)	throws Throwable 
	{
		StringBuffer quebusq = new StringBuffer();
		
		quebusq.append("SELECT gla.cod_grupo_libro_area, gla.desc_grupo_libro_area, t.prec_ofic, gla.cod_area "); 
		quebusq.append("FROM grupo_libro_area gla, tarifa t ");
		quebusq.append("WHERE gla.cod_grupo_libro_area = t.cod_grupo_libro_area and ");
		quebusq.append("gla.cod_grupo_libro_area!='5' AND (t.servicio_id =? or t.servicio_id=? or t.servicio_id= ?) ");   
		quebusq.append("order by 1 ");

		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(quebusq.toString());
		
		pstmt.setInt(1,served);
		pstmt.setInt(2,servRmc);
		pstmt.setInt(3,servSigc);
		
		ResultSet rset = pstmt.executeQuery();
	
		ArrayList resultado = new ArrayList();
		while(rset.next())
		{
			ComboBean bean = new ComboBean();
			bean.setCodigo(rset.getString("cod_grupo_libro_area"));
			bean.setDescripcion(rset.getString("desc_grupo_libro_area"));
			bean.setAtributo1(rset.getString("prec_ofic"));
			bean.setAtributo2(rset.getString("cod_area"));
			
			resultado.add(bean);
		}
		return resultado;
	}
	
	//Fin mgarate
	public static ArrayList getComboCertificados(Connection conn)
		throws Throwable 
	{
		
		StringBuffer quebusq = new StringBuffer();
		
		//Query busqueda de combo
		quebusq.append("SELECT c.certificado_id, gla.cod_grupo_libro_area, gla.desc_grupo_libro_area, gla.cod_area  ");
		quebusq.append("FROM grupo_libro_area gla, tm_certificados c  ");
		quebusq.append("WHERE  c.area_reg_id = gla.cod_area  ");
		quebusq.append("AND c.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
		quebusq.append("AND c.tpo_certificado = ? ");
		quebusq.append("AND c.estado = ? ");
		//if (Loggy.isTrace(this))
		//System.out.println("Combo QUERY ---> "+quebusq.toString());
		//Statement stmt = conn.createStatement();
		//ResultSet rset = stmt.executeQuery(quebusq.toString());
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(quebusq.toString());
		pstmt.setString(1,Constantes.CERTIFICADO_NEGATIVO);
		pstmt.setString(2,Constantes.ESTADO_ACTIVO);
		ResultSet rset = pstmt.executeQuery();
		
		ArrayList resultado = new ArrayList();
		while (rset.next())
		{
			ComboBean bean1 = new ComboBean();
			bean1.setCodigo(rset.getString("certificado_id"));
			bean1.setDescripcion(rset.getString("desc_grupo_libro_area"));
			bean1.setAtributo1(rset.getString("cod_grupo_libro_area"));
			bean1.setAtributo2(rset.getString("cod_area"));
			resultado.add(bean1);
		}
		
		
		return resultado;
	}
	
	/*
	*  @autor: jrosas
	*  @fecha: 30-05-2007
	*  @cc: SUNARP-REGMOBCOM
	*  @descripcion: Sobrecarga de método getComboCertificados
	*/

	public static ArrayList getComboCertificados(String tipo_Certificado,Connection conn)throws Throwable 
    {
		StringBuffer quebusq = new StringBuffer();
		//Query busqueda de combo
		quebusq.append("SELECT c.certificado_id, gla.cod_grupo_libro_area, gla.desc_grupo_libro_area, gla.cod_area  ");
		quebusq.append("FROM grupo_libro_area gla, tm_certificados c  ");
		quebusq.append("WHERE  c.area_reg_id = gla.cod_area  ");
		quebusq.append("AND c.cod_grupo_libro_area = gla.cod_grupo_libro_area ");
		quebusq.append("AND c.tpo_certificado = ? ");
		quebusq.append("AND c.estado = ? ");
		
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(quebusq.toString());
		pstmt.setString(1,tipo_Certificado);
		pstmt.setString(2,Constantes.ESTADO_ACTIVO);
		ResultSet rset = pstmt.executeQuery();
		
		ArrayList resultado = new ArrayList();
		while (rset.next())
		{
			ComboBean bean1 = new ComboBean();
			bean1.setCodigo(rset.getString("certificado_id"));
			bean1.setDescripcion(rset.getString("desc_grupo_libro_area"));
			bean1.setAtributo1(rset.getString("cod_grupo_libro_area"));
			bean1.setAtributo2(rset.getString("cod_area"));
			resultado.add(bean1);
		}
		
		return resultado;
   }
	
	public static ArrayList getComboRegisPublico(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();
		
		DboRegisPublico dboRegisPublico = new DboRegisPublico(conn);
		dboRegisPublico.setFieldsToRetrieve(DboRegisPublico.CAMPO_REG_PUB_ID + "|" + 
											DboRegisPublico.CAMPO_NOMBRE);

		ArrayList arrx = dboRegisPublico.searchAndRetrieveList(DboRegisPublico.CAMPO_NOMBRE);
		
		java.util.ArrayList arreglo2 = new java.util.ArrayList();
			for (int i = 0; i < arrx.size(); i++) 
			{
				ComboBean bean1 = new ComboBean();
				bean1.setCodigo(((DboRegisPublico) arrx.get(i)).getField(DboRegisPublico.CAMPO_REG_PUB_ID));
				bean1.setDescripcion(((DboRegisPublico) arrx.get(i)).getField(DboRegisPublico.CAMPO_NOMBRE));
				resultado.add(bean1);
			}

		return resultado;
	}	
	
public static ArrayList getComboLibros(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmLibro dbo = new DboTmLibro(conn);
		dbo.setFieldsToRetrieve(DboTmLibro.CAMPO_COD_LIBRO + "|" + 
								DboTmLibro.CAMPO_DESCRIPCION);
		dbo.setField(DboTmLibro.CAMPO_ESTADO, "1");
		ArrayList arrx = dbo.searchAndRetrieveList(DboTmLibro.CAMPO_DESCRIPCION);
		
		java.util.ArrayList arreglo2 = new java.util.ArrayList();
			for (int i = 0; i < arrx.size(); i++) 
			{
				ComboBean bean1 = new ComboBean();
				bean1.setCodigo(((DboTmLibro) arrx.get(i)).getField(DboTmLibro.CAMPO_COD_LIBRO));
				bean1.setDescripcion(((DboTmLibro) arrx.get(i)).getField(DboTmLibro.CAMPO_DESCRIPCION));
				resultado.add(bean1);
			}

		return resultado;
	}	
	
	public static ArrayList getComboMarca(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();
		DboTmMarcaVehi dbomarcavehi = new DboTmMarcaVehi(conn);
		ArrayList arrx = dbomarcavehi.searchAndRetrieveList(dbomarcavehi.CAMPO_COD_MARCA);		
		java.util.ArrayList arreglo2 = new java.util.ArrayList();
			for (int i = 0; i < arrx.size(); i++)
			{
				ComboBean bean1 = new ComboBean();
				bean1.setCodigo(((DboTmMarcaVehi)arrx.get(i)).getField(dbomarcavehi.CAMPO_COD_MARCA));				
				bean1.setDescripcion(((DboTmMarcaVehi)arrx.get(i)).getField(dbomarcavehi.CAMPO_DESCRIPCION));
				resultado.add(bean1);
			}

		return resultado;
	}	

	
public static DatosUsuarioBean getDatosUsuario(DBConnection conn, String userId) throws Throwable
{
	DatosUsuarioBean datosUsuarioBean= new DatosUsuarioBean();
	


			DboCuenta dboCuenta = new DboCuenta(conn);
			dboCuenta.setField(DboCuenta.CAMPO_USR_ID, userId);
			if (dboCuenta.find()==false)
				throw new CustomException(E09999_ERROR_GENERICO);
				
			String peNatuId = dboCuenta.getField(DboCuenta.CAMPO_PE_NATU_ID);
			String cuentaId = dboCuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
			
			datosUsuarioBean.setCuentaId(dboCuenta.getField(DboCuenta.CAMPO_CUENTA_ID));
			datosUsuarioBean.setUserId(dboCuenta.getField(DboCuenta.CAMPO_USR_ID));
			
			if (dboCuenta.getField(DboCuenta.CAMPO_ESTADO).equals("1"))
				datosUsuarioBean.setFlagActivo(true);
			else
				datosUsuarioBean.setFlagActivo(false);
				
			if (dboCuenta.getField(DboCuenta.CAMPO_EXON_PAGO).equals("1"))
				datosUsuarioBean.setFlagExoneradoPago(true);
			else
				datosUsuarioBean.setFlagExoneradoPago(false);
			
			String xinterno = dboCuenta.getField(DboCuenta.CAMPO_TIPO_USR);
			if (xinterno.startsWith("0")==true)
				datosUsuarioBean.setFlagInterno(true);
			else
				datosUsuarioBean.setFlagInterno(false);
				
			//pregunta secreta
			datosUsuarioBean.setPreguntaSecreta(dboCuenta.getField(DboCuenta.CAMPO_PREG_SEC_ID));
			datosUsuarioBean.setRespuestaSecreta(dboCuenta.getField(DboCuenta.CAMPO_RESP_SECRETA));
			
			//Persona Natural
			DboPeNatu dboPeNatu = new DboPeNatu(conn);
			dboPeNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, peNatuId);
			if (dboPeNatu.find()==false)
				throw new CustomException(E09999_ERROR_GENERICO);
				
			datosUsuarioBean.setApellidoMaterno(dboPeNatu.getField(DboPeNatu.CAMPO_APE_MAT));
			datosUsuarioBean.setApellidoPaterno(dboPeNatu.getField(DboPeNatu.CAMPO_APE_PAT));			
			datosUsuarioBean.setNombres(dboPeNatu.getField(DboPeNatu.CAMPO_NOMBRES));			
			String personaId = dboPeNatu.getField(DboPeNatu.CAMPO_PERSONA_ID);
			/********** AGREGADO POR JOSE BUGARIN   *******************/
			datosUsuarioBean.setPersJuriId(dboPeNatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
			System.out.println("Seteando persjuriId" + dboPeNatu.getField(DboPeNatu.CAMPO_PE_JURI_ID) );
			/**********************************************************/
			//Persona
			DboPersona dboPersona = new DboPersona(conn);
			dboPersona.setField(DboPersona.CAMPO_PERSONA_ID, personaId);
			if (dboPersona.find()==false)
				throw new CustomException(E09999_ERROR_GENERICO);
				
			datosUsuarioBean.setTipoDocumento(dboPersona.getField(DboPersona.CAMPO_TIPO_DOC_ID));
			datosUsuarioBean.setNumDocumento(dboPersona.getField(DboPersona.CAMPO_NUM_DOC_IDEN));			
			datosUsuarioBean.setFax(dboPersona.getField(DboPersona.CAMPO_FAX));
			datosUsuarioBean.setTelefono(dboPersona.getField(DboPersona.CAMPO_TELEF));			
			datosUsuarioBean.setAnexo(dboPersona.getField(DboPersona.CAMPO_ANEXO));
			datosUsuarioBean.setEmail(dboPersona.getField(DboPersona.CAMPO_EMAIL));			
			/********** AGREGADO POR GIANCARLO OCHOA*******************/
			datosUsuarioBean.setPersonaId(dboPersona.getField(DboPersona.CAMPO_PERSONA_ID));			
			/**********************************************************/

			//direccion
			DboDireccion dboDireccion = new DboDireccion(conn);
			dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID,personaId);
			if (dboDireccion.find()==false)
				throw new CustomException(E09999_ERROR_GENERICO);
							
			datosUsuarioBean.setPais(dboDireccion.getField(DboDireccion.CAMPO_PAIS_ID));
			datosUsuarioBean.setDepartamento(dboDireccion.getField(DboDireccion.CAMPO_DPTO_ID));
			datosUsuarioBean.setOtroDepartamento(dboDireccion.getField(DboDireccion.CAMPO_LUG_EXT));
			datosUsuarioBean.setProvincia(dboDireccion.getField(DboDireccion.CAMPO_PROV_ID));
			datosUsuarioBean.setDistrito(dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
			datosUsuarioBean.setDireccion(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
			datosUsuarioBean.setCodPostal(dboDireccion.getField(DboDireccion.CAMPO_COD_POST));			


			//Tabla Perfil Cuenta
			DboPerfilCuenta dboPerfilCuenta = new DboPerfilCuenta(conn);
			dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_CUENTA_ID,cuentaId);
			if (dboPerfilCuenta.find()==false)
				throw new CustomException(E09999_ERROR_GENERICO);
				
			datosUsuarioBean.setPerfilId(dboPerfilCuenta.getField(dboPerfilCuenta.CAMPO_PERFIL_ID));
			
			//Permisos extras
			//buscar sus permisos Extra Actuales
			DboPermisoUsr dboPermisoUsr = new DboPermisoUsr(conn);
			DboTmPermisoExt dboTmPermisoExt = null;
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_CUENTA_ID,datosUsuarioBean.getCuentaId());
			dboPermisoUsr.setField(DboPermisoUsr.CAMPO_ESTADO,"1");
			dboPermisoUsr.setFieldsToRetrieve(DboPermisoUsr.CAMPO_PERMISO_ID);
			java.util.ArrayList arr1 = dboPermisoUsr.searchAndRetrieveList();
			if (arr1!=null && arr1.size()>0)
			{
				dboTmPermisoExt = new  DboTmPermisoExt(conn);
				
				String[] arrx = new String[arr1.size()];
				String[] arry = new String[arr1.size()];
				for(int i = 0; i < arr1.size(); i++)
					{
						String p = ((DboPermisoUsr) arr1.get(i)).getField(DboPermisoUsr.CAMPO_PERMISO_ID);
						arrx[i] = p;
						
						//descripcion
						dboTmPermisoExt.clearAll();
						dboTmPermisoExt.setField(DboTmPermisoExt.CAMPO_PERMISO_ID,p);
						dboTmPermisoExt.setFieldsToRetrieve(DboTmPermisoExt.CAMPO_NOMBRE);
						dboTmPermisoExt.find();
						arry[i]=dboTmPermisoExt.getField(DboTmPermisoExt.CAMPO_NOMBRE);
					}
				datosUsuarioBean.setArrPermisoId(arrx);
				datosUsuarioBean.setArrPermisoDesc(arry);
			}
	return datosUsuarioBean;
}



public static void actualizaDatosUsuario(DBConnection conn, UsuarioBean usuario, DatosUsuarioBean datosUsuarioBean, int flag1) throws Throwable
{
// flag1, valores:
//   0 : fue llamado por EditarUsuarioController
//   1 : fue llamado por EditarMisDatosController - tiene menos campos para actualizar
Propiedades propiedades = Propiedades.getInstance();
//----------------------------------------------------------------------------------------
//*****************************************************
//PROCESO DE ACTUALIZACION DE DATOS DEL USUARIO
//*****************************************************

/*
durante las actualizaciones NO SE CAMBIAN:
	- Perfiles
	- Flags interno / externo (esto provoca que tampoco se puedan cambiar
	                           jurisdiccion ID)
*/

		SecAdmin secAdmin = SecAdmin.getInstance();
				
		//Tabla cuenta
		DboCuenta dboCuenta = new DboCuenta(conn);
		dboCuenta.setField(DboCuenta.CAMPO_USR_ID,datosUsuarioBean.getUserId());
		dboCuenta.find();
		
		String peNatuId = dboCuenta.getField(DboCuenta.CAMPO_PE_NATU_ID);
		String cuentaId = dboCuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
		
		String tipo = dboCuenta.getField(DboCuenta.CAMPO_TIPO_USR);
		
		if (tipo.startsWith("0"))
			datosUsuarioBean.setFlagInterno(true);
		else
			datosUsuarioBean.setFlagInterno(false);
		
		StringBuffer sb = new StringBuffer();
		boolean cambioPassword = false;
		String nuevaContrasena=null;
		
		if (flag1==0)
		{
			sb.append(DboCuenta.CAMPO_TS_ULT_ACC);
			dboCuenta.setField(DboCuenta.CAMPO_TS_ULT_ACC, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
			
			if (datosUsuarioBean.getFlagInterno()==false)
			{
				sb.append("|").append(DboCuenta.CAMPO_EXON_PAGO);
				if (datosUsuarioBean.getFlagExoneradoPago()==true)
					dboCuenta.setField(DboCuenta.CAMPO_EXON_PAGO,"1");
				else
					dboCuenta.setField(DboCuenta.CAMPO_EXON_PAGO,"0");
			}
			
				
/*			
			sb.append(DboCuenta.CAMPO_TIPO_USR);
			String tipoUsr="";
			if (datosUsuarioBean.getFlagInterno()==true)
				{
					tipoUsr="0000";
					if (datosUsuarioBean.getPerfilId().equals(""+PERFIL_ADMIN_JURISDICCION))
						tipoUsr="0010";
				}
			else
				tipoUsr="1100";
				
			if (usuario.getPerfilId()==PERFIL_ADMIN_ORG_EXT)
				tipoUsr="1000";
			if (usuario.getPerfilId()==PERFIL_CAJERO)
				tipoUsr="1100";
				
			dboCuenta.setField(DboCuenta.CAMPO_TIPO_USR,tipoUsr);
*/			
			
			String clave = datosUsuarioBean.getClave();
			if (clave!=null && clave.trim().length()>0)
			{
				sb.append("|").append(DboCuenta.CAMPO_CLAVE);
				sb.append("|").append(DboCuenta.CAMPO_RESP_SECRETA);
				sb.append("|").append(DboCuenta.CAMPO_PREG_SEC_ID);
				
				/**DESCAJ 03/01/2007 IFIGUEROA INICIO**/
				sb.append("|").append(DboCuenta.CAMPO_TS_CAD_CLAVE);
				Calendar gCal= Calendar.getInstance();
				gCal.add(Calendar.DAY_OF_MONTH,datosUsuarioBean.getDiasCad());
				if(datosUsuarioBean.getDiasCad()>0)
					dboCuenta.setField("TS_CAD_CLAVE",FechaUtil.dateTimeToStringToOracle(new Timestamp(gCal.getTime().getTime())));
				else
					dboCuenta.setField("TS_CAD_CLAVE",null);
				
				/** DESCAJ 03/01/2007 IFIGUEROA FIN **/
				
				
				if (propiedades.getFlagGrabaClave()==true)
					dboCuenta.setField(DboCuenta.CAMPO_CLAVE,datosUsuarioBean.getClave());
				else
					dboCuenta.setField(DboCuenta.CAMPO_CLAVE,"*");
					
				dboCuenta.setField(DboCuenta.CAMPO_RESP_SECRETA,datosUsuarioBean.getRespuestaSecreta());
				dboCuenta.setField(DboCuenta.CAMPO_PREG_SEC_ID,datosUsuarioBean.getPreguntaSecreta());
				
				cambioPassword=true;
				nuevaContrasena = datosUsuarioBean.getClave();
			}
			
			sb.append("|").append(DboCuenta.CAMPO_ESTADO);			
			
			if (datosUsuarioBean.getFlagActivo()==true)
				dboCuenta.setField(DboCuenta.CAMPO_ESTADO,"1");
			else
				dboCuenta.setField(DboCuenta.CAMPO_ESTADO,"0");
			

			dboCuenta.setFieldsToUpdate(sb.toString());
			dboCuenta.update();	
		}//if (flag1==0)
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		if (flag1==1)
		{
			
			String contrasena1 = datosUsuarioBean.getContrasena1();
			if (contrasena1!=null && contrasena1.trim().length()>0)
			{
				//valida que la contrasena que ha ingresado sea la actual del usuario
				boolean val = secAdmin.validaUsuario(datosUsuarioBean.getUserId(),contrasena1);
				if (val==false)
					throw new ValidacionException("Contrasena actual no valida","contrasena1");
				
				sb.append(DboCuenta.CAMPO_CLAVE).append("|");
				sb.append(DboCuenta.CAMPO_RESP_SECRETA).append("|");
				sb.append(DboCuenta.CAMPO_PREG_SEC_ID);
				/**DESCAJ 03/01/2007 IFIGUEROA INICIO**/
				sb.append("|").append(DboCuenta.CAMPO_TS_CAD_CLAVE);
				Calendar gCal= Calendar.getInstance();
				gCal.add(Calendar.DAY_OF_MONTH,datosUsuarioBean.getDiasCad());
				if(datosUsuarioBean.getDiasCad()>0)
					dboCuenta.setField("TS_CAD_CLAVE",FechaUtil.dateTimeToStringToOracle(new Timestamp(gCal.getTime().getTime())));
				else
					dboCuenta.setField("TS_CAD_CLAVE",null);
				
				/** DESCAJ 03/01/2007 IFIGUEROA FIN **/
				if (propiedades.getFlagGrabaClave()==true)
					dboCuenta.setField(DboCuenta.CAMPO_CLAVE,datosUsuarioBean.getContrasena2());
				else
					dboCuenta.setField(DboCuenta.CAMPO_CLAVE,'*');
					
				dboCuenta.setField(DboCuenta.CAMPO_RESP_SECRETA,datosUsuarioBean.getRespuestaSecreta());
				dboCuenta.setField(DboCuenta.CAMPO_PREG_SEC_ID,datosUsuarioBean.getPreguntaSecreta());
				
				cambioPassword=true;
				nuevaContrasena = datosUsuarioBean.getContrasena2();				
			
				dboCuenta.setFieldsToUpdate(sb.toString());
				dboCuenta.update();	
							
			}
			
			
		}//if (flag1==1)
	

		






		//persona natural
		DboPeNatu dboPeNatu = new DboPeNatu(conn);
		dboPeNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,peNatuId);
		dboPeNatu.find();
		
		String personaId = dboPeNatu.getField(DboPeNatu.CAMPO_PERSONA_ID);
		
		dboPeNatu.setFieldsToUpdate(
					DboPeNatu.CAMPO_APE_MAT+"|"+
					DboPeNatu.CAMPO_APE_PAT+"|"+
					DboPeNatu.CAMPO_NOMBRES);
		dboPeNatu.setField(DboPeNatu.CAMPO_APE_MAT,datosUsuarioBean.getApellidoMaterno());
		dboPeNatu.setField(DboPeNatu.CAMPO_APE_PAT,datosUsuarioBean.getApellidoPaterno());
		dboPeNatu.setField(DboPeNatu.CAMPO_NOMBRES,datosUsuarioBean.getNombres());
		dboPeNatu.update();
		
		
		
		
		//Tabla Persona
		DboPersona dboPersona = new DboPersona(conn);
		dboPersona.setField(DboPersona.CAMPO_PERSONA_ID, personaId);
		dboPersona.find();
		dboPersona.setFieldsToUpdate(
					DboPersona.CAMPO_TIPO_DOC_ID+"|"+
					DboPersona.CAMPO_NUM_DOC_IDEN+"|"+
					DboPersona.CAMPO_FAX+"|"+
					DboPersona.CAMPO_EMAIL+"|"+
					DboPersona.CAMPO_TELEF+"|"+
					DboPersona.CAMPO_ANEXO);
		dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, datosUsuarioBean.getTipoDocumento());
		dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN,datosUsuarioBean.getNumDocumento());
		dboPersona.setField(DboPersona.CAMPO_FAX,datosUsuarioBean.getFax());
		dboPersona.setField(DboPersona.CAMPO_TELEF,datosUsuarioBean.getTelefono());
		dboPersona.setField(DboPersona.CAMPO_EMAIL,datosUsuarioBean.getEmail());
		dboPersona.setField(DboPersona.CAMPO_ANEXO,datosUsuarioBean.getAnexo());
		dboPersona.update();
		



		// Tabla Direccion 
		DboDireccion dbo3 = new DboDireccion(conn);
		dbo3.setField(DboDireccion.CAMPO_PERSONA_ID, personaId);
		ArrayList arr3 = dbo3.searchAndRetrieveList();
		DboDireccion dboDireccion = (DboDireccion) arr3.get(0);
		dboDireccion.setConnection(conn);
		
		sb.delete(0,sb.length());
		sb.append(DboDireccion.CAMPO_NO_DIST);
		sb.append("|").append(DboDireccion.CAMPO_NOM_NUM_VIA);
		sb.append("|").append(DboDireccion.CAMPO_COD_POST);
		dboDireccion.setField(DboDireccion.CAMPO_NO_DIST, datosUsuarioBean.getDistrito());		
		dboDireccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA, datosUsuarioBean.getDireccion());
		dboDireccion.setField(DboDireccion.CAMPO_COD_POST,datosUsuarioBean.getCodPostal());
		
		if (datosUsuarioBean.getPais()!=null)
			{
				sb.append("|").append(DboDireccion.CAMPO_PAIS_ID);
				sb.append("|").append(DboDireccion.CAMPO_LUG_EXT);
				dboDireccion.setField(DboDireccion.CAMPO_PAIS_ID, datosUsuarioBean.getPais());		
				dboDireccion.setField(DboDireccion.CAMPO_LUG_EXT, datosUsuarioBean.getOtroDepartamento());				
			}
		
		dboDireccion.setFieldsToUpdate(sb.toString());
		dboDireccion.update();
		
		if (flag1==0)
		{
				//Tabla Perfil Cuenta
				/*
				//_borrar perfil antiguo
				DboPerfilCuenta dbo4 = new DboPerfilCuenta(conn);
				dbo4.setField(DboPerfilCuenta.CAMPO_CUENTA_ID, cuentaId);
				
				ArrayList arr4 = dbo4.searchAndRetrieveList();
				DboPerfilCuenta dboPerfilCuenta = (DboPerfilCuenta) arr4.get(0);
				dboPerfilCuenta.setConnection(conn);
				dboPerfilCuenta.delete();
				
				//_insertar nuevo perfil
				dboPerfilCuenta.clearAll();
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_PERFIL_ID,datosUsuarioBean.getPerfilId());
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_CUENTA_ID,cuentaId);
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_ESTADO,"1");
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_USR_CREA,usuario.getUserId());
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_USR_ULT_MODIF,usuario.getUserId());
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_TS_CREA,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_TS_ULT_MODIF,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				dboPerfilCuenta.setField(DboPerfilCuenta.CAMPO_NIVEL_ACCESO_ID,"0");
				dboPerfilCuenta.add();
				*/
				
				//permisos extras
				//primero, quitarle los permisos Extras actuales
				DboPermisoUsr dbo5 = new DboPermisoUsr(conn);
				dbo5.setField(DboPermisoUsr.CAMPO_CUENTA_ID,cuentaId);
				ArrayList arr5 = dbo5.searchAndRetrieveList();
				
				String[] permisosExtrasOld = new String[arr5.size()];
		
				//quitar permisos en base de datos local		
				for(int i = 0 ; i < arr5.size(); i++)
					{
						DboPermisoUsr dpu = (DboPermisoUsr) arr5.get(i);
						String p = dpu.getField(DboPermisoUsr.CAMPO_PERMISO_ID);
						permisosExtrasOld[i] = p;
						dpu.setConnection(conn);
						dpu.delete();
					}
			
				//asignar permisos extras en base de datos local
				String[] permisosExtrasNew = datosUsuarioBean.getArrPermisoId();
				DboPermisoUsr dboPermisoUsr = new DboPermisoUsr(conn);
				for(int i = 0 ; i < permisosExtrasNew.length; i++)
				{
					dboPermisoUsr.clearAll();
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_CUENTA_ID,cuentaId);
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_PERMISO_ID,permisosExtrasNew[i]);
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_ESTADO,"1");
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_USR_CREA,usuario.getUserId());
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_USR_ULT_MODIF,usuario.getUserId());
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_TS_CREA,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_TS_ULT_MODIF,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
					dboPermisoUsr.setField(DboPermisoUsr.CAMPO_FG_SYNC_TAM,"1");
					dboPermisoUsr.add();
				}
				
				//quitar permisosextras en TAM
				for(int i = 0 ; i < permisosExtrasOld.length; i++)	
				{
					secAdmin.removerACLdeUsuario(conn, permisosExtrasOld[i], datosUsuarioBean.getUserId());					
				}
		
				//agregar permisos extras en TAM
				for(int i = 0 ; i < permisosExtrasNew.length; i++)
				{		
					secAdmin.asignarACLaUsuario(conn, permisosExtrasNew[i], datosUsuarioBean.getUserId());			
				}
		}//if flag1==0
		
		if (cambioPassword==true)
			secAdmin.cambiaPasswordUsuario(datosUsuarioBean.getUserId(),nuevaContrasena);
//-----------------------------------------------------------------------------------------				
}

public static void actualizaDatosOrganizacion(DBConnection conn, UsuarioBean userLogged, DatosOrganizacionBean datosOrganizacionBean, int flag1) throws Throwable
{
/*
flag1, valores:
	0 : metodo llamado desde EditarOrganizacionController
	1 : metodo llamado desde EditarMisDatosController - tiene menos datos
*/
Propiedades propiedades = Propiedades.getInstance();
StringBuffer sb = new StringBuffer();
//------------------------------------------------------------------
//------------------------------------------------------------------
//PROCESO DE ACTUALIZACION
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
/*
durante las actualizaciones NO SE CAMBIAN:
	- Perfiles
	- Flags interno / externo 
	- CUR
*/
		//******************ACTUALIZAR DATOS DE LA ORGANIZACION**********************//
		// Tabla PE_JURI
		DboPeJuri dboPeJuri = new DboPeJuri(conn);
		dboPeJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, datosOrganizacionBean.getOrganizacionPeJuriId());		
		boolean b = dboPeJuri.find();
		
		String organizacionPersonaId = dboPeJuri.getField(DboPeJuri.CAMPO_PERSONA_ID);
		String represId= dboPeJuri.getField(DboPeJuri.CAMPO_REPRES_ID);
		datosOrganizacionBean.setRazonSocial(dboPeJuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
		String tipoOrg = dboPeJuri.getField(DboPeJuri.CAMPO_TIPO_ORG);
		if (tipoOrg.equals("1"))	
			datosOrganizacionBean.setFlagOrganizacionInterna(true);
		else
			datosOrganizacionBean.setFlagOrganizacionInterna(false);

		//tabla Persona
		DboPersona dboPersona = new DboPersona(conn);
		dboPersona.setField(DboPersona.CAMPO_PERSONA_ID,organizacionPersonaId);
		b = dboPersona.find();
		
		dboPersona.setFieldsToUpdate(
			DboPersona.CAMPO_FAX + "|" + 
			DboPersona.CAMPO_EMAIL + "|" + 
			DboPersona.CAMPO_TELEF + "|" + 
			DboPersona.CAMPO_ANEXO);
		dboPersona.setField(DboPersona.CAMPO_FAX, datosOrganizacionBean.getFaxAdministrador());			
		dboPersona.setField(DboPersona.CAMPO_EMAIL, datosOrganizacionBean.getEmailAdministrador());
		dboPersona.setField(DboPersona.CAMPO_TELEF, datosOrganizacionBean.getTelefonoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_ANEXO, datosOrganizacionBean.getAnexoAdministrador());
		dboPersona.update();

		dboPeJuri.setFieldsToUpdate(
				DboPeJuri.CAMPO_GIRO_ID + "|" + 
				DboPeJuri.CAMPO_TS_ULT_ACC);
		dboPeJuri.setField(DboPeJuri.CAMPO_GIRO_ID, datosOrganizacionBean.getGiroNegocio());
		dboPeJuri.setField(DboPeJuri.CAMPO_TS_ULT_ACC, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		dboPeJuri.update();

/*
		if (flag1==0)
		{
				//verificar CUR
				if (datosOrganizacionBean.getFlagOrganizacionInterna()==false)
					{
						String cur = datosOrganizacionBean.getCur();
						if (cur!=null && cur.trim().length()>0)
						{
							DboTmInstSir dboTmInstSir = new DboTmInstSir(conn);
							dboTmInstSir.setField(DboTmInstSir.CAMPO_CUR_PRES, cur);
							dboTmInstSir.setField(DboTmInstSir.CAMPO_OFIC_REG_ID, oficRegId);
							dboTmInstSir.setField(DboTmInstSir.CAMPO_REG_PUB_ID, regPubId);					
							ArrayList arrc = dboTmInstSir.searchAndRetrieveList();
							if (arrc.size() == 0)
								throw new ValidacionException("Cur no existe","cur");
							dboTmInstSir = (DboTmInstSir) arrc.get(0);
							dboTmInstSir.setConnection(conn);
							dboTmInstSir.setFieldsToUpdate(DboTmInstSir.CAMPO_PE_JURI_ID);
							dboTmInstSir.setField(DboTmInstSir.CAMPO_PE_JURI_ID, datosOrganizacionBean.getOrganizacionPeJuriId());
							dboTmInstSir.update();					
						}
					} 
		}
*/		


/*
direccion de la organizacion:

	siempre se pueden cambiar el distrito, el código postal y el nombreVIA
	
	si la organización es interna:
		No se puede cambiar nada más
	
	si la organización es externa:
		Se pueden cambiar el resto de datos de la dirección
		
*/

		// En el caso de la organizacion, 
		// el departamento,país y provincia solamente se pueden
		// cambiar si la organización es Externa
		DboDireccion dboDireccion = new DboDireccion(conn);
		dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, organizacionPersonaId);
		b = dboDireccion.find();
		
		sb.append(DboDireccion.CAMPO_NOM_NUM_VIA);	
		sb.append("|").append(DboDireccion.CAMPO_COD_POST);
		sb.append("|").append(DboDireccion.CAMPO_NO_DIST);
		
		dboDireccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA, datosOrganizacionBean.getDireccionOrganizacion());
		dboDireccion.setField(DboDireccion.CAMPO_COD_POST, datosOrganizacionBean.getCodPostalOrganizacion());
		dboDireccion.setField(DboDireccion.CAMPO_NO_DIST, datosOrganizacionBean.getDistritoOrganizacion());		

		if (datosOrganizacionBean.getFlagOrganizacionInterna()==false)
			{
				if (datosOrganizacionBean.getPaisIdOrganizacion()!=null)
				{
					sb.append("|").append(DboDireccion.CAMPO_PAIS_ID);
					dboDireccion.setField(DboDireccion.CAMPO_PAIS_ID, datosOrganizacionBean.getPaisIdOrganizacion());
				}
				if (datosOrganizacionBean.getOtroDepartamentoOrganizacion()!=null)
				{
					sb.append("|").append(DboDireccion.CAMPO_LUG_EXT);
					dboDireccion.setField(DboDireccion.CAMPO_LUG_EXT, datosOrganizacionBean.getOtroDepartamentoOrganizacion());
				}

				if (datosOrganizacionBean.getProvinciaIdOrganizacion()!=null)
				{
					sb.append("|").append(DboDireccion.CAMPO_PROV_ID);
					dboDireccion.setField(DboDireccion.CAMPO_PROV_ID, datosOrganizacionBean.getProvinciaIdOrganizacion());
				}
				if (datosOrganizacionBean.getDepartamentoIdOrganizacion()!=null)
				{
					sb.append("|").append(DboDireccion.CAMPO_DPTO_ID);
					dboDireccion.setField(DboDireccion.CAMPO_DPTO_ID, datosOrganizacionBean.getDepartamentoIdOrganizacion());
				}				
				
			}				
		
		dboDireccion.setFieldsToUpdate(sb.toString());
		dboDireccion.update();
		//************* Fin de Actualizo Datos de la organizacion *****************////


		//************* Actualizo Datos del Representante *****************////
		//primero buscar persona natural asociada a la organizacion
		//tabla PE_NATU
		DboPeNatu dboPeNatu = new DboPeNatu(conn);
		dboPeNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, represId);		
		b = dboPeNatu.find();
		
		String representantePersonaId = dboPeNatu.getField(DboPeNatu.CAMPO_PERSONA_ID);
		
		dboPeNatu.setFieldsToUpdate(
			DboPeNatu.CAMPO_NOMBRES + "|" + 
			DboPeNatu.CAMPO_APE_PAT + "|" + 
			DboPeNatu.CAMPO_APE_MAT);
		dboPeNatu.setField(DboPeNatu.CAMPO_APE_PAT, datosOrganizacionBean.getApellidoPaternoRepresentante());
		dboPeNatu.setField(DboPeNatu.CAMPO_APE_MAT, datosOrganizacionBean.getApellidoMaternoRepresentante());
		dboPeNatu.setField(DboPeNatu.CAMPO_NOMBRES, datosOrganizacionBean.getNombresRepresentante());
		dboPeNatu.update();
		
				
		//en la tabla persona 
		dboPersona.clearAll();
		dboPersona.setField(DboPersona.CAMPO_PERSONA_ID, representantePersonaId);	
		dboPersona.setConnection(conn);
		b = dboPersona.find();
		dboPersona.setFieldsToUpdate(
				dboPersona.CAMPO_NUM_DOC_IDEN + "|" + 
				dboPersona.CAMPO_TIPO_DOC_ID + "|" + 
				dboPersona.CAMPO_EMAIL);
		dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN, datosOrganizacionBean.getNumeroDocumentoRepresentante());				
		dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, datosOrganizacionBean.getTipoDocumentoRepresentante());
		dboPersona.setField(DboPersona.CAMPO_EMAIL, datosOrganizacionBean.getEmailAdministrador()); //como no tiene email se le pone el del administrador
		dboPersona.update();
		//************* Fin de Actualizo Datos del Representante *****************////





		//************* Actualizo Datos del Administrador *****************////
		/* OJO: en la tabla pe_natu hay varios registros que le pertenecen a la misma organizacion
		        pero uno es el representante y se descarta, y el que sigue a continucacion
		        es el administrador
		*/
		sb.delete(0,sb.length());
		sb.append(DboPeNatu.CAMPO_PE_JURI_ID).append("=").append(datosOrganizacionBean.getOrganizacionPeJuriId());
		sb.append(" and ");
		sb.append(DboPeNatu.CAMPO_PE_NATU_ID).append("!=").append(represId);
		DboPeNatu dboPeNatu0 = new DboPeNatu(conn);
		dboPeNatu0.setCustomWhereClause(sb.toString());
		java.util.ArrayList arrw = dboPeNatu0.searchAndRetrieveList(DboPeNatu.CAMPO_PE_NATU_ID);

		dboPeNatu.clearAll();
		dboPeNatu = (DboPeNatu) arrw.get(0);		
		dboPeNatu.setConnection(conn);
		
		String administradorPersonaId = dboPeNatu.getField(DboPeNatu.CAMPO_PERSONA_ID);
		String administradorPeNatuId  = dboPeNatu.getField(DboPeNatu.CAMPO_PE_NATU_ID);
		
		dboPeNatu.setFieldsToUpdate(
			DboPeNatu.CAMPO_NOMBRES + "|" + 
			DboPeNatu.CAMPO_APE_PAT + "|" + 
			DboPeNatu.CAMPO_APE_MAT);
		dboPeNatu.setField(DboPeNatu.CAMPO_APE_PAT, datosOrganizacionBean.getApellidoPaternoAdministrador());
		dboPeNatu.setField(DboPeNatu.CAMPO_APE_MAT, datosOrganizacionBean.getApellidoMaternoAdministrador());
		dboPeNatu.setField(DboPeNatu.CAMPO_NOMBRES, datosOrganizacionBean.getNombresAdministrador());
		dboPeNatu.update();
		
		//Tabla persona
		dboPersona.clearAll();
		dboPersona.setField(DboPeNatu.CAMPO_PERSONA_ID, administradorPersonaId);
		b = dboPersona.find();		
		dboPersona.setFieldsToUpdate(
			DboPersona.CAMPO_NUM_DOC_IDEN + "|" + 
			DboPersona.CAMPO_TIPO_DOC_ID + "|" + 
			DboPersona.CAMPO_FAX + "|" + 
			DboPersona.CAMPO_EMAIL + "|" + 
			DboPersona.CAMPO_TELEF + "|" + 
			DboPersona.CAMPO_ANEXO);
		dboPersona.setField(DboPersona.CAMPO_TIPO_DOC_ID, datosOrganizacionBean.getTipoDocumentoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_NUM_DOC_IDEN, datosOrganizacionBean.getNumeroDocumentoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_EMAIL, datosOrganizacionBean.getEmailAdministrador());
		dboPersona.setField(DboPersona.CAMPO_FAX, datosOrganizacionBean.getFaxAdministrador());
		dboPersona.setField(DboPersona.CAMPO_TELEF, datosOrganizacionBean.getTelefonoAdministrador());
		dboPersona.setField(DboPersona.CAMPO_ANEXO, datosOrganizacionBean.getAnexoAdministrador());
		dboPersona.update();

		//Registro direccion (administrador jurisdiccion)
		dboDireccion.clearAll();
		dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, administradorPersonaId);
		b = dboDireccion.find();
		
		//el pais podria estar nulo, validar:
		if (datosOrganizacionBean.getPaisAdministrador()==null)
		{
		dboDireccion.setFieldsToUpdate(
			DboDireccion.CAMPO_DPTO_ID + "|" + 
			DboDireccion.CAMPO_PROV_ID + "|" + 
			DboDireccion.CAMPO_LUG_EXT + "|" + 
			DboDireccion.CAMPO_NOM_NUM_VIA + "|" + 
			DboDireccion.CAMPO_COD_POST + "|" + 
			DboDireccion.CAMPO_NO_DIST);
		}
		else
		{
		dboDireccion.setFieldsToUpdate(
			DboDireccion.CAMPO_PAIS_ID + "|" + 
			DboDireccion.CAMPO_DPTO_ID + "|" + 
			DboDireccion.CAMPO_PROV_ID + "|" + 
			DboDireccion.CAMPO_LUG_EXT + "|" + 
			DboDireccion.CAMPO_NOM_NUM_VIA + "|" + 
			DboDireccion.CAMPO_COD_POST + "|" + 
			DboDireccion.CAMPO_NO_DIST);
			dboDireccion.setField(DboDireccion.CAMPO_PAIS_ID, datosOrganizacionBean.getPaisAdministrador());
		}
		
		dboDireccion.setField(DboDireccion.CAMPO_DPTO_ID, datosOrganizacionBean.getDepartamentoAdministrador());
		dboDireccion.setField(DboDireccion.CAMPO_LUG_EXT, datosOrganizacionBean.getOtroDepartamentoAdministrador());
		dboDireccion.setField(DboDireccion.CAMPO_PROV_ID, datosOrganizacionBean.getProvinciaAdministrador());
		dboDireccion.setField(DboDireccion.CAMPO_NO_DIST, datosOrganizacionBean.getDistritoAdministrador());
		dboDireccion.setField(DboDireccion.CAMPO_NOM_NUM_VIA, datosOrganizacionBean.getDireccionAdministrador());
		dboDireccion.setField(DboDireccion.CAMPO_COD_POST, datosOrganizacionBean.getCodPostalAdministrador());
		if (b==true)
			dboDireccion.update();

		//cuenta
		DboCuenta dboCuenta = new DboCuenta(conn);
		dboCuenta.setField(DboCuenta.CAMPO_PE_NATU_ID,administradorPeNatuId );
		b = dboCuenta.find();
		if (b==false)
			throw new ValidacionException("Error buscando cuenta","");
		
		String usr_id = dboCuenta.getField(DboCuenta.CAMPO_USR_ID);
		
		boolean cambioPassword = false;
		String  nuevaClave=null;
		
		SecAdmin secAdmin = SecAdmin.getInstance();
							
		sb.delete(0,sb.length());
		
		if (flag1==0)
		{
				sb.append(DboCuenta.CAMPO_TS_ULT_ACC);
				dboCuenta.setField(DboCuenta.CAMPO_TS_ULT_ACC, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));				
								
				if (datosOrganizacionBean.getFlagOrganizacionInterna()==false)
				{
					sb.append("|").append(DboCuenta.CAMPO_EXON_PAGO);
					if (datosOrganizacionBean.getFlagExonerarPago()==true)
						dboCuenta.setField(DboCuenta.CAMPO_EXON_PAGO, "1");
					else
						dboCuenta.setField(DboCuenta.CAMPO_EXON_PAGO, "0");
				}
					
				
				
				nuevaClave = datosOrganizacionBean.getClave();
				if (nuevaClave!=null && nuevaClave.trim().length()>0)
				{
					if (nuevaClave.equals(datosOrganizacionBean.getConfirmacionClave())==false)
							throw new ValidacionException("Contrasena no confirmada","clave");
					
					cambioPassword = true;
					
					sb.append("|");
					sb.append(DboCuenta.CAMPO_CLAVE).append("|");
					sb.append(DboCuenta.CAMPO_PREG_SEC_ID).append("|");
					sb.append(DboCuenta.CAMPO_RESP_SECRETA);

					if (propiedades.getFlagGrabaClave()==true)
						dboCuenta.setField(DboCuenta.CAMPO_CLAVE, nuevaClave);
					else
						dboCuenta.setField(DboCuenta.CAMPO_CLAVE, "*");
						
					dboCuenta.setField(DboCuenta.CAMPO_PREG_SEC_ID, datosOrganizacionBean.getPreguntaSecretaId());
					dboCuenta.setField(DboCuenta.CAMPO_RESP_SECRETA, datosOrganizacionBean.getRespuestaSecreta());								
				}
				
				dboCuenta.setFieldsToUpdate(sb.toString());				
				dboCuenta.update();
		}//if (flag1==0)
		
		
		if (flag1==1)
		{
			String contrasena1 = datosOrganizacionBean.getContrasena1();
			if (contrasena1!=null && contrasena1.trim().length()>0)
			{
				nuevaClave = datosOrganizacionBean.getContrasena2();
				if (nuevaClave!=null && nuevaClave.trim().length()>0)
				{
					if (nuevaClave.equals(datosOrganizacionBean.getContrasena3())==false)
						throw new ValidacionException("Nueva contrasena no confirmada","contrasena2");
						
					//validar que la contrasena1 sea igual a contrasena actual
					boolean val = secAdmin.validaUsuario(usr_id,contrasena1);
					if (val==false)
						throw new ValidacionException("Contrasena actual no es correcta","contrasena1");
					
					if (propiedades.getFlagGrabaClave()==false)
						dboCuenta.setField(DboCuenta.CAMPO_CLAVE, nuevaClave);
					else
						dboCuenta.setField(DboCuenta.CAMPO_CLAVE, "*");
							
							
					sb.append(DboCuenta.CAMPO_CLAVE);
					sb.append("|").append(DboCuenta.CAMPO_PREG_SEC_ID);
					sb.append("|").append(DboCuenta.CAMPO_RESP_SECRETA);							
					
					dboCuenta.setField(DboCuenta.CAMPO_PREG_SEC_ID, datosOrganizacionBean.getPreguntaSecretaId());
					dboCuenta.setField(DboCuenta.CAMPO_RESP_SECRETA, datosOrganizacionBean.getRespuestaSecreta());													
					
					dboCuenta.setFieldsToUpdate(sb.toString());				
					dboCuenta.update();					
				}
			}
		}//if (flag1==1)
		
		//cambiar password en TAM
		if (cambioPassword==true)
				secAdmin.cambiaPasswordUsuario(usr_id, nuevaClave);
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------	
}

public static DatosOrganizacionBean getDatosOrganizacion(DBConnection conn, String codOrg) throws Throwable
{
DatosOrganizacionBean datosOrganizacionBean= new DatosOrganizacionBean();
//------------------------------------------------------------------
//------------------------------------------------------------------
//BUSCAR DATOS DE LA ORGANIZACION
//------------------------------------------------------------------
//------------------------------------------------------------------
//------------------------------------------------------------------
datosOrganizacionBean.setOrganizacionPeJuriId(codOrg);		
		//****************** DATOS DE LA ORGANIZACION**********************//
		// Tabla PE_JURI
		DboPeJuri dboPeJuri = new DboPeJuri(conn);
		dboPeJuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, datosOrganizacionBean.getOrganizacionPeJuriId());		
		boolean b = dboPeJuri.find();

		String organizacionPersonaId = dboPeJuri.getField(DboPeJuri.CAMPO_PERSONA_ID);
		String represId= dboPeJuri.getField(DboPeJuri.CAMPO_REPRES_ID);
		
		datosOrganizacionBean.setRazonSocial(dboPeJuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
		datosOrganizacionBean.setSiglas(dboPeJuri.getField(DboPeJuri.CAMPO_SIGLAS));
		datosOrganizacionBean.setPrefijoCuenta(dboPeJuri.getField(DboPeJuri.CAMPO_PREF_CTA));		
		datosOrganizacionBean.setGiroNegocio(dboPeJuri.getField(DboPeJuri.CAMPO_GIRO_ID));

		if (dboPeJuri.getField(DboPeJuri.CAMPO_TIPO_ORG).equals("1")==true)
			datosOrganizacionBean.setFlagOrganizacionInterna(true);		
		else
			datosOrganizacionBean.setFlagOrganizacionInterna(false);

		//verificar CUR
		if (datosOrganizacionBean.getFlagOrganizacionInterna()==false)
			{
					DboTmInstSir dboTmInstSir = new DboTmInstSir(conn);
					dboTmInstSir.setField(DboTmInstSir.CAMPO_PE_JURI_ID, datosOrganizacionBean.getOrganizacionPeJuriId());					
					if (dboTmInstSir.find()==true)
						datosOrganizacionBean.setCur(dboTmInstSir.getField(DboTmInstSir.CAMPO_CUR_PRES));
					else
						datosOrganizacionBean.setCur("");
			} 


		//tabla Persona
		DboPersona dboPersona = new DboPersona(conn);
		dboPersona.setField(DboPersona.CAMPO_PERSONA_ID,organizacionPersonaId);
		b = dboPersona.find();
		
		datosOrganizacionBean.setJurisdiccionId(dboPersona.getField(DboPersona.CAMPO_JURIS_ID));
		datosOrganizacionBean.setRuc(dboPersona.getField(DboPersona.CAMPO_NUM_DOC_IDEN));
		

		// Tabla Direccion 		
		DboDireccion dboDireccion = new DboDireccion(conn);
		dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, organizacionPersonaId);
		b = dboDireccion.find();		

		datosOrganizacionBean.setPaisIdOrganizacion(dboDireccion.getField(DboDireccion.CAMPO_PAIS_ID));
		datosOrganizacionBean.setDepartamentoIdOrganizacion(dboDireccion.getField(DboDireccion.CAMPO_DPTO_ID));
		datosOrganizacionBean.setOtroDepartamentoOrganizacion(dboDireccion.getField(DboDireccion.CAMPO_LUG_EXT));
		datosOrganizacionBean.setProvinciaIdOrganizacion(dboDireccion.getField(DboDireccion.CAMPO_PROV_ID));
		datosOrganizacionBean.setDistritoOrganizacion(dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
		datosOrganizacionBean.setDireccionOrganizacion(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
		datosOrganizacionBean.setCodPostalOrganizacion(dboDireccion.getField(DboDireccion.CAMPO_COD_POST));









		//************* Datos del Representante *****************////
		
		//primero buscar persona natural asociada a la organizacion
		//tabla PE_NATU
		DboPeNatu dboPeNatu = new DboPeNatu(conn);
		dboPeNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, represId);		
		b = dboPeNatu.find();
		
		String representantePersonaId = dboPeNatu.getField(DboPeNatu.CAMPO_PERSONA_ID);
		
		datosOrganizacionBean.setApellidoPaternoRepresentante(dboPeNatu.getField(DboPeNatu.CAMPO_APE_PAT));
		datosOrganizacionBean.setApellidoMaternoRepresentante(dboPeNatu.getField(DboPeNatu.CAMPO_APE_MAT));
		datosOrganizacionBean.setNombresRepresentante(dboPeNatu.getField(DboPeNatu.CAMPO_NOMBRES));
				
		//en la tabla persona 
		dboPersona.clearAll();
		dboPersona.setConnection(conn);
		dboPersona.setField(DboPersona.CAMPO_PERSONA_ID, representantePersonaId);	
		b = dboPersona.find();

		datosOrganizacionBean.setTipoDocumentoRepresentante(dboPersona.getField(DboPersona.CAMPO_TIPO_DOC_ID));
		datosOrganizacionBean.setNumeroDocumentoRepresentante(dboPersona.getField(DboPersona.CAMPO_NUM_DOC_IDEN));

		//************* Datos del Administrador *****************////
		/* OJO: en la tabla pe_natu hay varios registros que le pertenecen a la misma organizacion
		        pero uno es el representante y otro ese el administrador
		*/
		StringBuffer sb = new StringBuffer();
		sb.append(DboPeNatu.CAMPO_PE_JURI_ID).append("=").append(datosOrganizacionBean.getOrganizacionPeJuriId());
		sb.append(" and ");
		sb.append(DboPeNatu.CAMPO_PE_NATU_ID).append(" != ").append(represId);
		DboPeNatu dboPeNatu0 = new DboPeNatu(conn);
		dboPeNatu0.setCustomWhereClause(sb.toString());
		
		java.util.ArrayList arrw = dboPeNatu0.searchAndRetrieveList(DboPeNatu.CAMPO_PE_NATU_ID);
		
		dboPeNatu.clearAll();
		dboPeNatu = (DboPeNatu) arrw.get(0);
		dboPeNatu.setConnection(conn);
		
		String administradorPersonaId = dboPeNatu.getField(DboPeNatu.CAMPO_PERSONA_ID);
		String administradorPeNatuId  = dboPeNatu.getField(DboPeNatu.CAMPO_PE_NATU_ID);
		
		datosOrganizacionBean.setApellidoPaternoAdministrador(dboPeNatu.getField(DboPeNatu.CAMPO_APE_PAT));
		datosOrganizacionBean.setApellidoMaternoAdministrador(dboPeNatu.getField(DboPeNatu.CAMPO_APE_MAT));
		datosOrganizacionBean.setNombresAdministrador(dboPeNatu.getField(DboPeNatu.CAMPO_NOMBRES));
		
		//Tabla persona
		dboPersona.clearAll();
		dboPersona.setField(DboPeNatu.CAMPO_PERSONA_ID, administradorPersonaId);
		b = dboPersona.find();		
		
		datosOrganizacionBean.setTipoDocumentoAdministrador(dboPersona.getField(DboPersona.CAMPO_TIPO_DOC_ID));
		datosOrganizacionBean.setNumeroDocumentoAdministrador(dboPersona.getField(DboPersona.CAMPO_NUM_DOC_IDEN));
		datosOrganizacionBean.setEmailAdministrador(dboPersona.getField(DboPersona.CAMPO_EMAIL));
		datosOrganizacionBean.setFaxAdministrador(dboPersona.getField(DboPersona.CAMPO_FAX));
		datosOrganizacionBean.setTelefonoAdministrador(dboPersona.getField(DboPersona.CAMPO_TELEF));
		datosOrganizacionBean.setAnexoAdministrador(dboPersona.getField(DboPersona.CAMPO_ANEXO));

		//Registro direccion (administrador jurisdiccion)
		dboDireccion.clearAll();
		dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, administradorPersonaId);
		b = dboDireccion.find();

		datosOrganizacionBean.setPaisAdministrador(dboDireccion.getField(DboDireccion.CAMPO_PAIS_ID));
		datosOrganizacionBean.setDepartamentoAdministrador(dboDireccion.getField(DboDireccion.CAMPO_DPTO_ID));
		datosOrganizacionBean.setOtroDepartamentoAdministrador(dboDireccion.getField(DboDireccion.CAMPO_LUG_EXT));
		datosOrganizacionBean.setProvinciaAdministrador(dboDireccion.getField(DboDireccion.CAMPO_PROV_ID));
		datosOrganizacionBean.setDistritoAdministrador(dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
		datosOrganizacionBean.setDireccionAdministrador(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
		datosOrganizacionBean.setCodPostalAdministrador(dboDireccion.getField(DboDireccion.CAMPO_COD_POST));

		//cuenta
		DboCuenta dboCuenta = new DboCuenta(conn);
		dboCuenta.setField(DboCuenta.CAMPO_PE_NATU_ID,administradorPeNatuId );
		b = dboCuenta.find();
		
		if (dboCuenta.getField(DboCuenta.CAMPO_EXON_PAGO).equals("1")==true)
			datosOrganizacionBean.setFlagExonerarPago(true);
		else
			datosOrganizacionBean.setFlagExonerarPago(false);
			
			
		//-extra
		datosOrganizacionBean.setRespuestaSecreta(dboCuenta.getField(DboCuenta.CAMPO_RESP_SECRETA));
		datosOrganizacionBean.setPreguntaSecretaId(dboCuenta.getField(DboCuenta.CAMPO_PREG_SEC_ID));
//------------------------------------------------------------------			
	return datosOrganizacionBean;
}

	public static ArrayList getComboGiros(DBConnection conn) throws Throwable {
		ArrayList resultado = new ArrayList();

		DboTmGiro giro = new DboTmGiro(conn);
		giro.setFieldsToRetrieve(
			DboTmGiro.CAMPO_GIRO_ID + "|" + DboTmGiro.CAMPO_NOMBRE);
		giro.setField(DboTmGiro.CAMPO_ESTADO, "1");
		ArrayList arr1 = giro.searchAndRetrieveList(DboTmGiro.CAMPO_NOMBRE);

		for (int i = 0; i < arr1.size(); i++) {
			ComboBean b = new ComboBean();
			DboTmGiro d = (DboTmGiro) arr1.get(i);
			b.setCodigo(d.getField(DboTmGiro.CAMPO_GIRO_ID));
			b.setDescripcion(d.getField(DboTmGiro.CAMPO_NOMBRE));
			resultado.add(b);
		}
		return resultado;
	}

	public static ArrayList getComboJurisdicciones(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();

		DboTmJurisdiccion dboTmJurisdiccion = new DboTmJurisdiccion(conn);
		dboTmJurisdiccion.setFieldsToRetrieve(
			DboTmJurisdiccion.CAMPO_JURIS_ID + "|" + DboTmJurisdiccion.CAMPO_NOMBRE);
		dboTmJurisdiccion.setField(DboTmJurisdiccion.CAMPO_ESTADO,"1");
		ArrayList arr1 = dboTmJurisdiccion.searchAndRetrieveList();

		for (int i = 0; i < arr1.size(); i++) 
		{
			DboTmJurisdiccion d = (DboTmJurisdiccion) arr1.get(i);
			int j = Integer.parseInt(d.getField(DboTmJurisdiccion.CAMPO_JURIS_ID));
			if (j > 1)
			{
				ComboBean b = new ComboBean();
				b.setCodigo(d.getField(DboTmJurisdiccion.CAMPO_JURIS_ID));
				b.setDescripcion(d.getField(DboTmJurisdiccion.CAMPO_NOMBRE));
				resultado.add(b);
			}
		}
		return resultado;
	}

	public static FormBean getDepartamento_Provincia(DBConnection conn)
		throws Throwable {
		FormBean output = new FormBean();

		DboTmProvincia dboProvincia = new DboTmProvincia(conn);
		ArrayList arreglo3 = new ArrayList();

		DboTmDepartamento dbo3 = new DboTmDepartamento(conn);
		dbo3.setField(DboTmDepartamento.CAMPO_PAIS_ID, "01");
		dbo3.setField(DboTmDepartamento.CAMPO_ESTADO, "1");
		java.util.ArrayList arr3 = dbo3.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
		java.util.ArrayList arrpro = new java.util.ArrayList();
		for (int i = 0; i < arr3.size(); i++) {
			ComboBean b = new ComboBean();
			DboTmDepartamento d = (DboTmDepartamento) arr3.get(i);
			String dptoId = d.getField(DboTmDepartamento.CAMPO_DPTO_ID);
			
			if (dptoId.equals("00")==false)			
			{
			b.setCodigo(dptoId);
			b.setDescripcion(d.getField(DboTmDepartamento.CAMPO_NOMBRE));
			arreglo3.add(b);

			//buscar las provincias del departamento
			dboProvincia.clearAll();
			dboProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, b.getCodigo());
			dboProvincia.setField(DboTmProvincia.CAMPO_ESTADO,"1");
			java.util.ArrayList arrpv = dboProvincia.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
			for (int w = 0; w < arrpv.size(); w++) {
				DboTmProvincia dp = (DboTmProvincia) arrpv.get(w);
				Value05Bean b5 = new Value05Bean();
				b5.setValue01(dp.getField(DboTmProvincia.CAMPO_PROV_ID));
				b5.setValue02(dp.getField(DboTmProvincia.CAMPO_NOMBRE));
				b5.setValue03(b.getCodigo());
				arrpro.add(b5);
			} //for w
			
			}			
		} //for i

		output.setArray1(arreglo3);
		output.setArray2(arrpro);

		return output;
	}

	public static FormBean getDepartamento_ProvinciaPorJurisdiccion(
		DBConnection conn,
		String inputJurisId)
		throws Throwable {
			
		FormBean output = new FormBean();
		ArrayList arrDepartamentos = new ArrayList();
		ArrayList arrProvincias = new ArrayList();

		DboTmProvincia    dboTmProvincia    = new DboTmProvincia(conn);
		DboTmDepartamento dboTmDepartamento = new DboTmDepartamento(conn);
		DboOficRegistral  dboOficRegistral  = new DboOficRegistral(conn);
		
		/*
		Pasos para obtener los departamentos de la jurisdiccion:
		
		select * from ofic_registral where juris_id= LA_JURISDICCION
		
		de alli se obtienen el regPubId y el OficRegId y se busca en:
		
		select * from tm_provincia where reg_pub_id='reg' and ofic_reg_id='ofi'
		
		y se obtienen la provincias, y de alli, se deben capturar solamente
		los departamentos que pertenezcan a esas provincias
		*/
		
		dboOficRegistral.setField(DboOficRegistral.CAMPO_JURIS_ID,inputJurisId);
		dboOficRegistral.find();
		
		String xr = dboOficRegistral.getField(DboOficRegistral.CAMPO_REG_PUB_ID);
		String xo = dboOficRegistral.getField(DboOficRegistral.CAMPO_OFIC_REG_ID);
		
		dboTmDepartamento.setField(DboTmDepartamento.CAMPO_PAIS_ID, "01");
		dboTmDepartamento.setField(DboTmDepartamento.CAMPO_ESTADO, "1");
		
		java.util.ArrayList arri = dboTmDepartamento.searchAndRetrieveList(DboTmDepartamento.CAMPO_NOMBRE);
		for (int i = 0; i < arri.size(); i++) 
			{
			String departamentoId     = ((DboTmDepartamento) arri.get(i)).getField(DboTmDepartamento.CAMPO_DPTO_ID);
			String departamentoNombre = ((DboTmDepartamento) arri.get(i)).getField(DboTmDepartamento.CAMPO_NOMBRE);
			
			boolean flag1 = false;
			//buscar las provincias del departamento
			dboTmProvincia.clearAll();
			dboTmProvincia.setField(DboTmProvincia.CAMPO_DPTO_ID, departamentoId);
			dboTmProvincia.setField(DboTmProvincia.CAMPO_ESTADO, "1");
			java.util.ArrayList arrw = dboTmProvincia.searchAndRetrieveList(DboTmProvincia.CAMPO_NOMBRE);
			for (int w = 0; w < arrw.size(); w++) 
				{
				DboTmProvincia dp = (DboTmProvincia) arrw.get(w);
				String oficRegId = dp.getField(DboTmProvincia.CAMPO_OFIC_REG_ID);
				String regPubId  = dp.getField(DboTmProvincia.CAMPO_REG_PUB_ID);
				
				if (oficRegId.equals(xo) && regPubId.equals(xr))
					{
						//provincia pertenece a la jurisdiccion
						flag1=true;
						Value05Bean b5 = new Value05Bean();
						b5.setValue01(dp.getField(DboTmProvincia.CAMPO_PROV_ID));
						b5.setValue02(dp.getField(DboTmProvincia.CAMPO_NOMBRE));
						b5.setValue03(departamentoId);
						arrProvincias.add(b5);
					}
			} //for w

			if (flag1 == true) 
			{
				ComboBean b = new ComboBean();
				b.setCodigo(departamentoId);
				b.setDescripcion(departamentoNombre);
				arrDepartamentos.add(b);
			}
		} //for i

		output.setArray1(arrDepartamentos);
		output.setArray2(arrProvincias);

		return output;
	}

	public static ArrayList getComboPreguntasSecretas(DBConnection conn)
		throws Throwable {
		ArrayList resultado = new ArrayList();

		DboTmPregSecretas dbo5 = new DboTmPregSecretas(conn);
		dbo5.setField(DboTmPregSecretas.CAMPO_ESTADO, "1");
		java.util.ArrayList arr5 = dbo5.searchAndRetrieveList();
		for (int i = 0; i < arr5.size(); i++) {
			ComboBean b = new ComboBean();
			DboTmPregSecretas d = (DboTmPregSecretas) arr5.get(i);
			b.setCodigo(d.getField(DboTmPregSecretas.CAMPO_PREG_SEC_ID));
			b.setDescripcion(d.getField(DboTmPregSecretas.CAMPO_DESCRIPCION));
			resultado.add(b);
		}
		return resultado;
	}

/*devolver UNIVERSO TOTAL de permisos que puede asignar
  el perfil recibido
  
  Arreglo resultante compuesto por 3 campos:
  	-PerfilId que puede recibir el permiso
  	-PermisoId
  	-PermisoNombre
*/
	public static ArrayList getListaPermisosAsignablesSegunPerfil(
		DBConnection conn,
		ControllerRequest request,
		long perfilId)
		throws Throwable {
		//asignacion de permisos 
		MultiDBObject asignacionPermisos = new MultiDBObject(conn);
		asignacionPermisos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmPermisoAsig","permisoAsig");
		asignacionPermisos.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmPermisoExt","perfilExt");
		asignacionPermisos.setForeignKey("permisoAsig",DboTmPermisoAsig.CAMPO_PERMISO_ID,"perfilExt",DboTmPermisoExt.CAMPO_PERMISO_ID);
		asignacionPermisos.setField("permisoAsig",DboTmPermisoAsig.CAMPO_PERFIL_ID_1,String.valueOf(perfilId));
		Vector resultados2 = new Vector();

		ArrayList resultado = new ArrayList();

		resultados2 = asignacionPermisos.searchAndRetrieve();

		for (int i = 0; i < resultados2.size(); i++) {
			Value05Bean bean5 = new Value05Bean();
			MultiDBObject multi = (MultiDBObject) resultados2.get(i);
			bean5.setValue01(multi.getField("permisoAsig", DboTmPermisoAsig.CAMPO_PERFIL_ID_2));
			bean5.setValue02(multi.getField("perfilExt", DboTmPermisoExt.CAMPO_PERMISO_ID));
			bean5.setValue03(multi.getField("perfilExt", DboTmPermisoExt.CAMPO_NOMBRE));
			resultado.add(bean5);
		}
		return resultado;
	}

	//devolver combo de perfiles que pueden ser asignados por el perfil recibido
	public static ArrayList getListaPerfilesAsignablesSegunPerfil(
		DBConnection conn,
		ControllerRequest request,
		long perfilId)
		throws Throwable {
			
		//Asignacion de perfiles
		MultiDBObject asignacionPerfiles = new MultiDBObject(conn);
		asignacionPerfiles.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmPerfil","perfil");
		asignacionPerfiles.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmPerfilAsignac","perfilAsig");
		asignacionPerfiles.setForeignKey("perfil",DboTmPerfil.CAMPO_PERFIL_ID,"perfilAsig",		DboTmPerfilAsignac.CAMPO_PERFIL_ID_2);
		asignacionPerfiles.setField("perfilAsig",DboTmPerfilAsignac.CAMPO_PERFIL_ID_1,String.valueOf(perfilId));		
		asignacionPerfiles.setField("perfil", DboTmPerfil.CAMPO_ESTADO, "1");

		Vector vec1 = new Vector();

		ArrayList resultado = new ArrayList();

		vec1 = asignacionPerfiles.searchAndRetrieve(DboTmPerfil.CAMPO_PERFIL_ID+" DESC");

		for (int i = 0; i < vec1.size(); i++) {
			Value05Bean bean = new Value05Bean();
			MultiDBObject multi = (MultiDBObject) vec1.get(i);
			bean.setValue01(multi.getField("perfil", DboTmPerfil.CAMPO_PERFIL_ID));
			bean.setValue02(multi.getField("perfil", DboTmPerfil.CAMPO_NOMBRE));
			bean.setValue03(multi.getField("perfil", DboTmPerfil.CAMPO_FG_INTERNO));
			resultado.add(bean);
		}
		return resultado;
	}

public static String[] convierteTiraEnArreglo(String tira, String separador) throws Throwable
{
	StringTokenizer st = new java.util.StringTokenizer(tira, separador, false);	
	String[] resultado = new String[st.countTokens()];
		int x = 0;
		while (st.hasMoreTokens() == true) {
			String elemento = st.nextToken();
				resultado[x] = elemento;
				x++;
			}
	return resultado;				
}


/*
funcion validaJS: Se usa desde las paginas JSP
genera codigo JavaScript de validacion
parametros:
	campo        : nombre de campo en html
	descripcion  : nombre descriptivo del campo
	tipoCampo    :  0 = libre, acepta cualquier caracter
	                1 = solo acepta números
	                2 = no acepta números
	obl          : true = campo de entrada obligatoria
	mayu         : convertir el campo a mayúsculas antes del submit
*/
public static String validaJS(String  campo,
							  String  descripcion,
							  int     tipoCampo,
							  boolean obl,
							  boolean mayu)
{
StringBuffer sb = new StringBuffer();
sb.append("//___validaciones JS para el campo ["+campo+"] ("+descripcion+")\n");
sb.append("var oCampo = document.frm1."+campo+".value;\n");
if (obl==true)
{
	sb.append("if (oCampo.length == 0)\n");
	sb.append("   {\n");
	sb.append("     alert('El campo <"+descripcion+"> es obligatorio');\n");
	sb.append("     document.frm1."+campo+".focus();\n");
	sb.append("     return;\n");
	sb.append("   }\n");
}
if (mayu==true)
	sb.append("document.frm1."+campo+".value = document.frm1."+campo+".value.toUpperCase();\n");
switch (tipoCampo)	
{
case 1 :
	sb.append("if (isNaN(oCampo) == true )\n");
	sb.append("   {\n");
	sb.append("    alert('Por favor ingrese un numero valido en el campo <"+descripcion+">');\n");
	sb.append("    document.frm1."+campo+".focus();\n");
	sb.append("    return;\n");
	sb.append("   }\n");
	break;	
case 2 :
	sb.append("for (var ii = 0; ii < oCampo.length; ii++)\n");
	sb.append("   {\n");
	sb.append("    var xchar = oCampo.substr(ii,1);\n");
	sb.append("    if (xchar == '0' || xchar == '1' || xchar == '2' || xchar == '3' || xchar == '4' || xchar == '5' ||\n");
	sb.append("        xchar == '6' || xchar == '7' || xchar == '8' || xchar == '9')\n");
	sb.append("        {\n");
	sb.append("          alert('El campo <"+descripcion+"> no acepta digitos');\n");
	sb.append("          document.frm1."+campo+".focus();\n");
	sb.append("          return;\n");
	sb.append("        }\n");
	sb.append("    }\n");	
	break;	
}//switch

return sb.toString();
}//fin metodo validaJS


public static DatosOrganizacionBean recojeDatosRequestOrganizacion(HttpServletRequest req, long perfilId) throws Throwable
{
	DatosOrganizacionBean bean = new DatosOrganizacionBean();
	
	String xparam;
	
	//---Organizacion---
	xparam = req.getParameter("razonSocial");
	if (xparam!=null)
		xparam = xparam.toUpperCase();
	bean.setRazonSocial(xparam);
	bean.setRuc(req.getParameter("ruc"));
	bean.setGiroNegocio(req.getParameter("giroNegocio"));
	
	if (perfilId==PERFIL_ADMIN_JURISDICCION)
		bean.setPaisIdOrganizacion("01");			
	else
		bean.setPaisIdOrganizacion(req.getParameter("paisOrganizacion"));
		bean.setDepartamentoIdOrganizacion(req.getParameter("departamentoOrganizacion"));
	
	String odp = req.getParameter("otroDepartamentoOrganizacion");
	if (odp==null)
		bean.setOtroDepartamentoOrganizacion(" ");
	else
		bean.setOtroDepartamentoOrganizacion(odp.toUpperCase());
	
	bean.setProvinciaIdOrganizacion(req.getParameter("provinciaOrganizacion"));
	bean.setDistritoOrganizacion(req.getParameter("distritoOrganizacion"));
	bean.setDireccionOrganizacion(req.getParameter("direccionOrganizacion"));
	bean.setCodPostalOrganizacion(req.getParameter("codPostalOrganizacion"));
	bean.setEmailOrganizacion(req.getParameter("emailAdministrador"));
	bean.setCur(req.getParameter("cur"));
	bean.setJurisdiccionId(req.getParameter("jurisdiccion"));
	
	xparam = req.getParameter("tipoOrg");
	if (xparam==null)
	{
		bean.setFlagOrganizacionInterna(false);
	}
	else
	{
		if (xparam.equals("I")==true)
			bean.setFlagOrganizacionInterna(true);
		else
			bean.setFlagOrganizacionInterna(false);
	}
	
	xparam = req.getParameter("exonerarPago");
	if (xparam ==null)
	{
		bean.setFlagExonerarPago(false);
	}
	else
	{
		if (xparam.equals("S")==true)
			bean.setFlagExonerarPago(true);
		else
			bean.setFlagExonerarPago(false);
	}
	
	//-Representante--
	bean.setApellidoPaternoRepresentante(req.getParameter("apellidoPaternoRepresentante").toUpperCase());
	bean.setApellidoMaternoRepresentante(req.getParameter("apellidoMaternoRepresentante").toUpperCase());
	bean.setNombresRepresentante(req.getParameter("nombresRepresentante").toUpperCase());
	bean.setTipoDocumentoRepresentante(req.getParameter("tipoDocumentoRepresentante"));
	bean.setNumeroDocumentoRepresentante(req.getParameter("numeroDocumentoRepresentante"));
	
	//--Administrador --
	bean.setApellidoPaternoAdministrador(req.getParameter("apellidoPaternoAdministrador").toUpperCase());
	bean.setApellidoMaternoAdministrador(req.getParameter("apellidoMaternoAdministrador").toUpperCase());
	bean.setNombresAdministrador(req.getParameter("nombresAdministrador").toUpperCase());
	
	xparam = req.getParameter("prefijoCuenta");
	if (xparam!=null)
		xparam = xparam.toUpperCase();
	bean.setPrefijoCuenta(xparam);
	bean.setTipoDocumentoAdministrador(req.getParameter("tipoDocumentoAdministrador"));
	bean.setNumeroDocumentoAdministrador(req.getParameter("numeroDocumentoAdministrador"));
	bean.setEmailAdministrador(req.getParameter("emailAdministrador"));
	if (perfilId==PERFIL_ADMIN_JURISDICCION)
		bean.setPaisAdministrador("01");			
	else
		bean.setPaisAdministrador(req.getParameter("paisAdministrador"));
	
	bean.setDepartamentoAdministrador(req.getParameter("departamentoAdministrador"));
	
	String oda = req.getParameter("otroDepartamentoAdministrador");
	if (oda==null)
		bean.setOtroDepartamentoAdministrador(" ");
	else
		bean.setOtroDepartamentoAdministrador(oda);
	
	bean.setProvinciaAdministrador(req.getParameter("provinciaAdministrador"));
	bean.setDistritoAdministrador(req.getParameter("distritoAdministrador"));
	bean.setDireccionAdministrador(req.getParameter("direccionAdministrador"));
	bean.setCodPostalAdministrador(req.getParameter("codPostalAdministrador"));
	bean.setTelefonoAdministrador(req.getParameter("telefonoAdministrador"));
	bean.setAnexoAdministrador(req.getParameter("anexoAdministrador"));
	bean.setFaxAdministrador(req.getParameter("faxAdministrador"));
	bean.setClave(req.getParameter("clave"));
	bean.setConfirmacionClave(req.getParameter("confirmacionClave"));
	bean.setPreguntaSecretaId(req.getParameter("pregSecreta"));
	bean.setRespuestaSecreta(req.getParameter("respPregSecreta"));
	
	
	//-datos extras para link "Editar mis datos"
	bean.setContrasena1(req.getParameter("contrasena1"));
	bean.setContrasena2(req.getParameter("contrasena2"));
	bean.setContrasena3(req.getParameter("contrasena3"));
	return bean;
}


public static DatosUsuarioBean recojeDatosRequestUsuario(HttpServletRequest req, long perfilId) throws Throwable
{
	DatosUsuarioBean datosUsuarioBean = new DatosUsuarioBean();
	String xparam=null;
	
			datosUsuarioBean.setApellidoPaterno(req.getParameter("apellidoPaterno").toUpperCase());
			datosUsuarioBean.setApellidoMaterno(req.getParameter("apellidoMaterno").toUpperCase());
			datosUsuarioBean.setNombres(req.getParameter("nombres").toUpperCase());
			datosUsuarioBean.setTipoDocumento(req.getParameter("tipoDoc"));
			datosUsuarioBean.setNumDocumento(req.getParameter("numDoc"));
			datosUsuarioBean.setFax(req.getParameter("fax"));
			datosUsuarioBean.setTelefono(req.getParameter("telefono"));
			datosUsuarioBean.setAnexo(req.getParameter("anexo"));
			datosUsuarioBean.setPais(req.getParameter("pais"));
			datosUsuarioBean.setDepartamento(req.getParameter("departamento"));
			datosUsuarioBean.setOtroDepartamento(req.getParameter("otroDepartamento"));
			if (datosUsuarioBean.getOtroDepartamento()==null)
				datosUsuarioBean.setOtroDepartamento("");
			datosUsuarioBean.setProvincia(req.getParameter("provincia"));
			if (datosUsuarioBean.getProvincia()==null)
				datosUsuarioBean.setProvincia("");
			datosUsuarioBean.setDistrito(req.getParameter("distrito"));
			if (datosUsuarioBean.getDistrito()==null)
				datosUsuarioBean.setDistrito("");
			datosUsuarioBean.setDireccion(req.getParameter("direccion"));
			datosUsuarioBean.setCodPostal(req.getParameter("codPostal"));
			datosUsuarioBean.setEmail(req.getParameter("email"));
			
			xparam = req.getParameter("userId");
			if (xparam!=null)
				xparam = xparam.toUpperCase();
			datosUsuarioBean.setUserId(xparam);
			
			datosUsuarioBean.setClave(req.getParameter("clave"));
			datosUsuarioBean.setConfirmacionClave(req.getParameter("confirmacionClave"));
			datosUsuarioBean.setPreguntaSecreta(req.getParameter("pregSecreta"));
			datosUsuarioBean.setRespuestaSecreta(req.getParameter("respuestaSecreta"));
			datosUsuarioBean.setPerfilId(req.getParameter("perfiles"));
			
			//flags
			datosUsuarioBean.setFlagInterno(false);
			datosUsuarioBean.setFlagActivo(false);
			datosUsuarioBean.setFlagExoneradoPago(false);
			
			xparam = req.getParameter("tipoUser");			
			if (xparam!=null)
			{
				if (xparam.equals("I"))
					datosUsuarioBean.setFlagInterno(true);
			}
			
			xparam = req.getParameter("estadoUsuario");
			if (xparam!=null)
			{
				if (xparam.equals("A"))
					datosUsuarioBean.setFlagActivo(true);
			}
			
			xparam = req.getParameter("exonerado");
			if (xparam!=null)
			{
				if (xparam.equals("S"))
					datosUsuarioBean.setFlagExoneradoPago(true);
			}
			/** DESCAJ 03/01/2007**/
			String strPerfilIdUsuario=req.getParameter("hidPerfilUsuario");
			if (datosUsuarioBean.getPerfilId() == null && !strPerfilIdUsuario.equalsIgnoreCase("0")){
	
				
				datosUsuarioBean.setPerfilId(strPerfilIdUsuario);
				long perfilIdUsuario=Integer.parseInt(strPerfilIdUsuario);
				int dias=0;
		
			if( perfilIdUsuario == PERFIL_INTERNO ||
				perfilIdUsuario == PERFIL_CAJERO ||
				perfilIdUsuario == PERFIL_TESORERO){
				String strDias=(String)req.getParameter("ndias");
				dias=Integer.parseInt(strDias);				
			}
			if( perfilIdUsuario == PERFIL_INDIVIDUAL_EXTERNO ||
				perfilIdUsuario == PERFIL_AFILIADO_EXTERNO ||
				perfilIdUsuario == PERFIL_ADMIN_GENERAL){
				String strDias=(String)req.getParameter("cboCaducidad");
				dias=Integer.parseInt(strDias);				
			}
			datosUsuarioBean.setDiasCad(dias);
                        } 
			/**DESCAJ 03/01/2007**/

			if (datosUsuarioBean.getPerfilId() == null )
				datosUsuarioBean.setPerfilId(""+PERFIL_INDIVIDUAL_EXTERNO);

			
			//Recuperar los ids de los permisos que le corresponden al usuario
			//Recuperar los permisos ADICIONALES que le asignan al momento de crear el usuario
			//El CAJERO no puede asignar ningun permiso adicional
			//Los ADMINISTRADORES de organizacion, jurisdiccion y de extranet si pueden.			
			String tira = req.getParameter("hidPermisos");
			
			if (tira!=null)
				{
					//permisos ID
					String[] permisosExtras = Tarea.convierteTiraEnArreglo(tira,"*");
					datosUsuarioBean.setArrPermisoId(permisosExtras);
					
					//descripciones de los permisos
					tira = req.getParameter("hidPermisos2");
					String[] permisosDesc = Tarea.convierteTiraEnArreglo(tira,"*");
					datosUsuarioBean.setArrPermisoDesc(permisosDesc);
				}	
				
			//-datos extras para link "Editar mis datos"
			datosUsuarioBean.setContrasena1(req.getParameter("contrasena1"));
			datosUsuarioBean.setContrasena2(req.getParameter("contrasena2"));
			datosUsuarioBean.setContrasena3(req.getParameter("contrasena3"));
				
		return datosUsuarioBean;
	}
    
    /**** inicio: jrosas 12-07-07*****/
		public static InputBusqIndirectaBean recojeDatosRequestBusqIndirectaPartidaRMC(HttpServletRequest req)
		throws Throwable 
		{
			HttpSession session = req.getSession();
			InputBusqIndirectaBean bean = null;
			
			String xparam = req.getParameter("flagPagineo2");
			
			if (xparam==null)
			{
				
				//la busqueda empieza y todavia no hay pagineo
				bean = new InputBusqIndirectaBean();	
				// Inicio:jrosas:20/07/2007
				/** inicio:ifigueroa 17-08-07 **/
					bean.setVerifica(req.getParameter("verifica"));
				/** fin:ifigueroa 17-08-07 **/
				bean.setSedesElegidas(Constantes.SEDES_PORDEFECTO_BUSQUEDA_RMC); // aqui se setea por defecto todas las sedes
				
				if(req.getParameter("refNumPart")!=null)
				{
					bean.setRefNumPart(req.getParameter("refNumPart"));
				}
				//	Fin:jrosas:20/07/2007
				bean.setComboArea(req.getParameter("comboArea"));
				bean.setCodGrupoLibroArea(req.getParameter("comboAreaLibro"));
				bean.setDescGrupoLibroArea(req.getParameter("hidAreaLibro"));
				
				if(req.getParameter("refNumPart")!=null)
				{
					bean.setRefNumPart(req.getParameter("refNumPart"));
				}
				
				if(bean.getCodGrupoLibroArea().equals("21")){
					String param = req.getParameter("tipo") == null?"":req.getParameter("tipo");
					if (param.equals("N")){ // participante persona natural
						bean.setArea3ParticipanteApePat(reemplazaApos(req.getParameter("txtApellidoPaterno").trim().toUpperCase()));
						bean.setArea3ParticipanteApeMat(reemplazaApos(req.getParameter("txtApellidoMaterno").trim().toUpperCase()));
						bean.setArea3ParticipanteNombre(reemplazaApos(req.getParameter("txtNombres").trim().toUpperCase()));
						
					}
					if (param.equals("J")){ // participante persona juridica
						bean.setArea3ParticipanteRazon(reemplazaApos(req.getParameter("txtRazonSocial").trim().toUpperCase()));
						bean.setArea2Siglas(reemplazaApos(req.getParameter("txtSiglas").trim().toUpperCase()));	
					}
					if (param.equals("D")){// por tipo y numero de documento
						bean.setTipoDocumento(reemplazaApos(req.getParameter("cboTipoDocumento").trim()));
						bean.setNumeroDocumento(reemplazaApos(req.getParameter("txtNumeroDocumento").trim().toUpperCase()));	
					}
					if (param.equals("B")){// por bienes
						if(req.getParameter("txtNumeroPlaca")!=null)
							bean.setNumeroPlaca(reemplazaApos(req.getParameter("txtNumeroPlaca").trim().toUpperCase()));
						if(req.getParameter("txtOtrosDatos")!=null)
							bean.setOtrosDatos(reemplazaApos(req.getParameter("txtOtrosDatos").trim().toUpperCase()));
						/** inicio:ifigueroa 17-08-07 **/
						
						if(bean.getVerifica()!=null){
							if(req.getParameter("numplaca")!=null && req.getParameter("numplaca").length()>0)
								bean.setNumeroPlaca(reemplazaApos(req.getParameter("numplaca").trim().toUpperCase()));
							else if(req.getParameter("numSerie")!=null && req.getParameter("numSerie").length()>0)
								bean.setOtrosDatos(reemplazaApos(req.getParameter("numSerie").trim().toUpperCase()));
							else if(req.getParameter("numMatricula")!=null && req.getParameter("numMatricula").length()>0){
								if(req.getParameter("nombreBien")!=null && req.getParameter("nombreBien").length()>0)
									bean.setNombreBien(reemplazaApos(req.getParameter("nombreBien").trim().toUpperCase()));
								bean.setOtrosDatos(reemplazaApos(req.getParameter("numMatricula").trim().toUpperCase()));
							}
					
						}
						/** fin:ifigueroa 17-08-07 **/
					
					}
				}
				session.setAttribute("INPUT_BEAN",bean);
			}
			
			if (xparam!=null)
				{
					bean = (InputBusqIndirectaBean) session.getAttribute("INPUT_BEAN");
					bean.setFlagPagineo(true);
					bean.setSalto(Integer.parseInt(req.getParameter("salto")));
					bean.setCantidad(req.getParameter("cantidad"));
				}			
				
			return bean;
			
		}	


    /**** fin: jrosas 12-07-07*****/

	public static InputBusqIndirectaBean recojeDatosRequestBusqIndirectaPartida(DBConnection conn, HttpServletRequest req, int tipoBusqueda)
		throws Throwable {
			
		HttpSession session = req.getSession();
		InputBusqIndirectaBean b = null;
		
		String xparam = req.getParameter("flagPagineo");
		
		if (xparam==null)
		{
			//la busqueda empieza y todavia no hay pagineo
				b = new InputBusqIndirectaBean();
				//	Inicio:jrosas:20/07/2007
				if(req.getParameter("refNumPart")!=null)
				{
					b.setRefNumPart(req.getParameter("refNumPart"));
				}
				//	Fin:jrosas:20/07/2007
				b.setComboArea(req.getParameter("comboArea"));
				b.setCodGrupoLibroArea(req.getParameter("comboAreaLibro"));
				b.setDescGrupoLibroArea(req.getParameter("hidAreaLibro"));
				
				if (req.getParameter("area3PredioSinNum") != null)
					b.setArea3PredioSinNum(true);
		
				b.setHid2(req.getParameter("hid2"));
				b.setArea1ApePat(reemplazaApos(req.getParameter("area1ApePat").trim().toUpperCase()));
				b.setArea1ApeMat(reemplazaApos(req.getParameter("area1ApeMat").trim().toUpperCase()));
				b.setArea1Nombre(reemplazaApos(req.getParameter("area1Nombre").trim().toUpperCase()));
				b.setArea1Razon(reemplazaApos(req.getParameter("area1Razon").trim().toUpperCase()));
				b.setArea1Siglas(reemplazaApos(req.getParameter("area1Siglas").trim().toUpperCase()));
				b.setArea1TipoParticipacion(req.getParameter("area1TipoParticipacion"));
				if (b.getArea1TipoParticipacion()==null)
					b.setArea1TipoParticipacion("**");   //todos
						
				b.setArea2TipoParticipacion(req.getParameter("area2TipoParticipacion"));
				b.setArea2ApePat(reemplazaApos(req.getParameter("area2ApePat").trim().toUpperCase()));
				b.setArea2ApeMat(reemplazaApos(req.getParameter("area2ApeMat").trim().toUpperCase()));
				b.setArea2Nombre(reemplazaApos(req.getParameter("area2Nombre").trim().toUpperCase()));
				b.setArea2Razon1(reemplazaApos(req.getParameter("area2Razon1").trim().toUpperCase()));
				b.setArea2Siglas(reemplazaApos(req.getParameter("area2Siglas").trim().toUpperCase()));
				b.setArea2SiglasB(reemplazaApos(req.getParameter("area2SiglasB").trim().toUpperCase()));
				//b.setArea2TipoDocumento(req.getParameter("area2TipoDocumento"));
				//b.setArea2NumeroDocumento(req.getParameter("area2NumeroDocumento").trim());
				b.setArea2Razon2(reemplazaApos(req.getParameter("area2Razon2").toUpperCase()));
				if (b.getArea2TipoParticipacion()==null)
					b.setArea2TipoParticipacion("**");   //todos
							
				b.setArea3TipoParticipacion(req.getParameter("area3TipoParticipacion"));
				b.setArea3ParticipanteApePat(reemplazaApos(req.getParameter("area3ParticipanteApePat").trim().toUpperCase()));
				b.setArea3ParticipanteApeMat(reemplazaApos(req.getParameter("area3ParticipanteApeMat").trim().toUpperCase()));
				b.setArea3ParticipanteNombre(reemplazaApos(req.getParameter("area3ParticipanteNombre").trim().toUpperCase()));
				b.setArea3ParticipanteRazon(reemplazaApos(req.getParameter("area3ParticipanteRazon").trim().toUpperCase()));
				

				//**añadido por Leo**//
				b.setArea4TipoParticipacion(req.getParameter("area4TipoParticipacion"));
				b.setArea4ApePat(reemplazaApos(req.getParameter("area4PropietarioApePat").trim().toUpperCase()));
				b.setArea4ApeMat(reemplazaApos(req.getParameter("area4PropietarioApeMat").trim().toUpperCase()));
				b.setArea4Nombre(reemplazaApos(req.getParameter("area4PropietarioNombre").trim().toUpperCase()));
				
				b.setArea4Razon(reemplazaApos(req.getParameter("area4PropietarioRazon").trim().toUpperCase()));				
				
				b.setArea4NumMotor(reemplazaApos(req.getParameter("area4NumMotor").trim().toUpperCase()));		
				b.setArea4NumChasis(reemplazaApos(req.getParameter("area4Chasis").trim().toUpperCase()));
				
				b.setArea4Tipo(req.getParameter("tipo"));
				if (b.getArea4TipoParticipacion()==null)
					b.setArea4TipoParticipacion(Constantes.PROPIETARIO_VEHI);
				//**//	

				
				xparam = req.getParameter("area3PredioDepartamento");
				if (xparam!=null)
					b.setArea3PredioDepartamento(Tarea.rellenaIzq(xparam,"0",2));
					
					
				xparam = req.getParameter("area3PredioProvincia");
				if (xparam!=null)
					b.setArea3PredioProvincia(Tarea.rellenaIzq(xparam,"0",2));
				
				xparam = req.getParameter("area3PredioDistrito");
				if (xparam!=null)
					b.setArea3PredioDistrito(Tarea.rellenaIzq(xparam,"0",2));
				
				b.setArea3PredioTipoZona(req.getParameter("area3PredioTipoZona"));
		
				xparam = req.getParameter("area3PredioNombreZona");
				if (xparam!=null)		
						b.setArea3PredioNombreZona(xparam.trim().toUpperCase());
		
				b.setArea3PredioTipoVia(req.getParameter("area3PredioTipoVia"));
				xparam = req.getParameter("area3PredioNombreVia");
				if (xparam!=null)
					b.setArea3PredioNombreVia(xparam.trim().toUpperCase());
		
				b.setArea3PredioTipoNumerac(req.getParameter("area3TipoNumerac"));
				xparam = req.getParameter("area3PredioNumero");
				if (xparam!=null)
					b.setArea3PredioNumero(xparam.trim().toUpperCase());
		
				b.setArea3PredioTipoInterior(req.getParameter("area3PredioTipoInterior"));
				xparam = req.getParameter("area3PredioInteriorNro");
				if (xparam!=null)
					b.setArea3PredioInteriorNro(xparam.trim().toUpperCase());
				
				xparam = req.getParameter("area3MineriaDerechoMinero");
				if (xparam!=null)
					b.setArea3MineriaDerechoMinero(xparam.trim().toUpperCase());
				xparam = req.getParameter("area3MineriaSociedad");
				if (xparam!=null)
					b.setArea3MineriaSociedad(xparam.trim().toUpperCase());
				
				b.setArea3EmbarcacionNumeroMatricula(req.getParameter("area3EmbarcacionNumeroMatricula").trim().toUpperCase());
				b.setArea3EmbarcacionNombre(reemplazaApos(req.getParameter("area3EmbarcacionNombre").trim().toUpperCase()));
				b.setArea3BuqueNumeroMatricula(req.getParameter("area3BuqueNumeroMatricula").trim().toUpperCase());
				b.setArea3BuqueNombre(reemplazaApos(req.getParameter("area3BuqueNombre").trim().toUpperCase()));
				b.setArea3AeronaveNumeroMatricula(req.getParameter("area3AeronaveNumeroMatricula").trim().toUpperCase());
				b.setArea3AeronaveApePat(reemplazaApos(req.getParameter("area3AeronaveApePat").trim().toUpperCase()));
				b.setArea3AeronaveApeMat(reemplazaApos(req.getParameter("area3AeronaveApeMat").trim().toUpperCase()));
				b.setArea3AeronaveNombre(reemplazaApos(req.getParameter("area3AeronaveNombre").trim().toUpperCase()));
				b.setArea3AeronaveRazon(reemplazaApos(req.getParameter("area3AeronaveRazon").trim().toUpperCase()));
				if (b.getArea3TipoParticipacion()==null)
					b.setArea3TipoParticipacion("**");   //todos
				b.setArea3Siglas(reemplazaApos(req.getParameter("area3Siglas").trim().toUpperCase()));
				b.setArea3SiglasB(reemplazaApos(req.getParameter("area3SiglasB").trim().toUpperCase()));
				//Inicio:mgarate:11/07/2007
				if(req.getParameter("refNumPart")!=null)
				{
					b.setRefNumPart(req.getParameter("refNumPart"));
				}
				//Fin:mgarate
				//calculo del costo
				String tira = req.getParameter("hid1");
				
		
				xparam = req.getParameter("flagPagineo");
				if (xparam!=null)
					{
						b.setFlagPagineo(true);		
						b.setSalto(Integer.parseInt(req.getParameter("salto")));
					}
		
				/*
				Nota:
				la variable "hid1" de la pagina web contiene una cadena String con las
				sedes elegidas por el usuario, separadas por un "*"
				ejemplo:
				
				       sede1*sede2*sede3*
				       
				 esto se convierte aqui en una cadena para poder ser usada en un 
				 query SQL
				 select xxx from ssss where regPubId in ('sede1','sede2','sede3')
				*/
				java.util.StringTokenizer st = new java.util.StringTokenizer(tira, "*", false);
		
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				int cuenta = st.countTokens(); //numero de sedes elegidas
				
				//if (cuenta==0)
				//	throw new ValidacionException("Por favor seleccione al menos una sede","");
				
					
				int x = 0;
				String[] tokens = new String[cuenta];
				String[] tokens2 = new String[cuenta];
				int z=0;
		
				while (st.hasMoreTokens() == true) 
				{
					String elemento = st.nextToken();
					
					tokens2[z]=elemento;
					/*
					int nElemento = Integer.parseInt(elemento);
					switch (nElemento)
					{
						case  1: elemento="05"; break;
						case  2: elemento="11"; break;
						case  3: elemento="12"; break;
						case  4: elemento="09"; break;
						case  5: elemento="08"; break;
						case  6: elemento="13"; break;
						case  7: elemento="04"; break;
						case  8: elemento="02"; break;
						case 35: elemento="01"; break;
						case 10: elemento="06"; break;
						case 11: elemento="10"; break;
						case 12: elemento="03"; break;
						case 13: elemento="07"; break;
					}*/
					
					//poner en tira SQL
					sb.append("'");
					sb.append(elemento);
					sb.append("'");
					if (x <= (cuenta - 2))
						sb.append(",");
					
					x++;
					
					//poner en arreglo
					tokens[z]=elemento;
					
					z++;
				}
		
			sb.append(")");
				
			b.setSedesSQLString(sb.toString());
			b.setSedesElegidas(tokens);
			b.setSedesElegidasOriginales(tokens2);
		
		//determinar si se incluyen los estados inactivos	
		boolean ia=false;
		
		if (tipoBusqueda==Constantes.BUSQUEDA_INDIRECTA_PERSONA_NATURAL)
		{		
			//if(b.getComboArea().equals("22000"))
			if(b.getCodGrupoLibroArea().equals("3"))
			{//Personas Juridicas
				if (req.getParameter("checkInactivosPN2")!=null)
					ia = true;
			}
			//if(b.getComboArea().equals("23000"))
			if(b.getCodGrupoLibroArea().equals("4") || b.getCodGrupoLibroArea().equals("5"))
			{	//Personas Naturales
				if (req.getParameter("checkInactivosPN3")!=null)
					ia=true;
			}
			//if(b.getComboArea().equals("21000"))
			if(b.getCodGrupoLibroArea().equals("1") || b.getCodGrupoLibroArea().equals("2"))
			{	//Propiedad Inmueble
				if (req.getParameter("checkInactivosPN1")!=null)
					ia=true;
			}
			///modificado por Leo, y luego por Omar	
			//if(b.getComboArea().equals("24000"))
			if(b.getCodGrupoLibroArea().equals("6"))
			{	//Propiedad Vehicular
				if (req.getParameter("checkInactivosPN4")!=null)
					ia=true;
			}
		}
		
		if (tipoBusqueda==Constantes.BUSQUEDA_INDIRECTA_PERSONA_JURIDICA)
		{
			//if(b.getComboArea().equals("22000"))
			if(b.getCodGrupoLibroArea().equals("3"))
			{//Personas Juridicas
					if (req.getParameter("checkInactivosPJ2")!=null)
						ia=true;
			}
			//if(b.getComboArea().equals("23000"))
			if(b.getCodGrupoLibroArea().equals("4") || b.getCodGrupoLibroArea().equals("5"))
			{//Personas Naturales
					if (req.getParameter("checkInactivosPJ3")!=null)
						ia=true;
			}
			//if(b.getComboArea().equals("21000"))
			if(b.getCodGrupoLibroArea().equals("1") || b.getCodGrupoLibroArea().equals("2"))
			{//Propiedad Inmueble
					if(req.getParameter("checkInactivosPJ1")!=null)
						ia=true;
			}
				
			//if(b.getComboArea().equals("24000"))
			if(b.getCodGrupoLibroArea().equals("6"))
			{//Propiedad Inmueble
					if(req.getParameter("checkInactivosPJ4")!=null)
						ia=true;
			}				
				
		}


		if (tipoBusqueda==Constantes.BUSQUEDA_INDIRECTA_NUMERO_MOTOR)
		{
			//if(b.getComboArea().equals("24000"))
			if(b.getCodGrupoLibroArea().equals("6"))
			{//Propiedad Inmueble
					if(req.getParameter("checkInactivosNM4")!=null)
						ia=true;
			}				
			
		}
		
		if (tipoBusqueda==Constantes.BUSQUEDA_INDIRECTA_NUMERO_CHASIS)
		{
			//if(b.getComboArea().equals("24000"))
			if(b.getCodGrupoLibroArea().equals("6"))
			{//Propiedad Inmueble
					if(req.getParameter("checkInactivosCH4")!=null)
						ia=true;
			}
		}
		


		b.setFlagIncluirInactivos(ia);
			
			session.setAttribute("INPUT_BEAN",b);
			
		}
		else
		{
			//estamos en pagineo
			b = (InputBusqIndirectaBean) session.getAttribute("INPUT_BEAN");
			b.setFlagPagineo(true);
			b.setSalto(Integer.parseInt(req.getParameter("salto")));
			b.setCantidad(req.getParameter("cantidad"));			
		}//if (xparam==null)
		
		
		
		
		return b;
	} //fin metodo 	
	
	public static InputBusqDirectaBean recojeDatosRequestBusqDirectaPartida(HttpServletRequest req)
		throws Throwable 
		{
		HttpSession session = req.getSession();
		InputBusqDirectaBean bean = null;
		
		String xparam = req.getParameter("flagPagineo");
        /*** inicio: jrosas 14-08-07 ***/
		String busq = req.getParameter("busq");
		/*** fin: jrosas 14-08-07 ***/
		
		if (xparam==null)
		{
			//la busqueda empieza y todavia no hay pagineo
			bean = new InputBusqDirectaBean();
			
			/** inicio:dbravo 10-08-07 **/
			bean.setVerifica(req.getParameter("verifica"));
			/** fin:dbravo 10-08-07 **/
			
			/*** inicio: jrosas 13-08-07 ***/
			bean.setAreaRegistral(req.getParameter("comboArea")!=null?req.getParameter("comboArea"):"");
			bean.setCodGrupoLibroArea(req.getParameter("comboAreaLibro")!=null?req.getParameter("comboAreaLibro"):"");
			bean.setDescGrupoLibroArea(req.getParameter("hidAreaLibro")!=null?req.getParameter("hidAreaLibro"):"");
			
			//Inicio:mgarate:11/07/2007
			
			bean.setRefNumPart(req.getParameter("refNumPart")!=null?req.getParameter("refNumPart"):"");
			
			//if(bean.getAreaRegistral().equals("24000"))
			if(bean.getCodGrupoLibroArea().equals("6"))
			{
				if(req.getParameter("CboOficinas")==null)
				{
					bean.setRegPubId("01");
					bean.setOficRegId("01");
				}
				else
				{
					String[] datos = Tarea.getEquivalenciaOficina(req.getParameter("CboOficinas"));
					bean.setRegPubId(datos[0]);
					bean.setOficRegId(datos[1]);
					
				}
				String param = req.getParameter("radBuscar2") == null?"":req.getParameter("radBuscar2");
				if (param.equals("P")){
					bean.setNumeroPartida(req.getParameter("txt4"));
					bean.setNumeroPlaca("");
				}else{
					bean.setNumeroPartida("");
					bean.setNumeroPlaca(req.getParameter("txt4"));
				}
			}
			else
			{
				if (req.getParameter("CboOficinas") != null){
					String[] datos = Tarea.getEquivalenciaOficina(req.getParameter("CboOficinas"));
					
					bean.setRegPubId(datos[0]);
					bean.setOficRegId(datos[1]);
				}else{
					bean.setRegPubId("");
					bean.setRegPubId("");
				}
				
				bean.setNumeroPartida(req.getParameter("txt1")!=null?req.getParameter("txt1"):"");
			
			}
			
			bean.setNumeroFicha(req.getParameter("txt1")!=null?req.getParameter("txt1"):"");
				
			bean.setLibro(req.getParameter("combo3")!=null?req.getParameter("combo3"):"");
			bean.setTomo(req.getParameter("txt2")!=null?req.getParameter("txt2"):"");
			bean.setFolio(req.getParameter("txt3")!=null?req.getParameter("txt3"):"");	
			
			/*** fin: jrosas 13-08-07 ***/
			if (busq== null){
				session.setAttribute("INPUT_BEAN",bean);
			}	
		}
		
		if (xparam!=null)
			{
				bean = (InputBusqDirectaBean) session.getAttribute("INPUT_BEAN");
				bean.setFlagPagineo(true);
				bean.setSalto(Integer.parseInt(req.getParameter("salto")));
				bean.setCantidad(req.getParameter("cantidad"));
				
			}			
			
		return bean;
		}	
	
	
	
	
	
	
	
	
public static String[] getEquivalenciaOficina(String codOficina) throws Throwable
{
	/*
	Este metodo recibe un codigo de oficina de un combo de pagina JSP
	Ejemplo: /Publicidad/FrmBusqDirectaPartidas.jsp
	
	y devuelve el regPubId y el OficRegId en las posiciones 0 y 1 del
	arreglo String
	*/
	
	//int ncod = Integer.parseInt(codOficina);
	
	//String regPubId="00";
	//String oficRegId="00";
	
	String regPubId="01";
	String oficRegId="01";
	
	if(codOficina!=null)
	{
		regPubId=codOficina.substring(0,2);
		oficRegId=codOficina.substring(3,5);
	}
	/*
	else
	{
		throw new CustomException("No se ha recibido la Oficina Registral");
	}
	*/
	/*
	switch (ncod)
	{
		case 34: regPubId="01";	oficRegId="01"; break;	//LIMA
		case  8: regPubId="01";	oficRegId="02"; break;	//OFICINA CALLAO
		case 25: regPubId="01";	oficRegId="03"; break;	//OFICINA HUARAL
		case 19: regPubId="01";	oficRegId="04"; break;	//OFICINA HUACHO
		case 10: regPubId="01";	oficRegId="05"; break;	//OFICINA CANETE
		case  6: regPubId="01";	oficRegId="06"; break;	//OFICINA BARRANCA
		case 22: regPubId="02";	oficRegId="01"; break;	//HUANCAYO
		case 24: regPubId="02";	oficRegId="02"; break;	//HUANUCO
		//case   : regPubId="02";	oficRegId="03"; break;	//LA UNION
		case 40: regPubId="02";	oficRegId="04"; break;	//PASCO
		case 48: regPubId="02";	oficRegId="05"; break;	//SATIPO
		case 33: regPubId="02";	oficRegId="06"; break;	//SELVA CENTRAL    (la merced)
		case 53: regPubId="02";	oficRegId="07"; break;	//TARMA
		case 54: regPubId="02";	oficRegId="08"; break;	//TINGO MARIA
		case  3: regPubId="03";	oficRegId="01"; break;	//AREQUIPA
		case  9: regPubId="03";	oficRegId="02"; break;	//CAMANA
		case  2: regPubId="03";	oficRegId="03"; break;	//CASTILLA - APLAO
		case 35: regPubId="03";	oficRegId="04"; break;	//ISLAY - MOLLENDO
		case 26: regPubId="04";	oficRegId="01"; break;	//HUARAZ
		case 11: regPubId="04";	oficRegId="02"; break;	//CASMA
		case 15: regPubId="04";	oficRegId="03"; break;	//CHIMBOTE
		case 42: regPubId="05";	oficRegId="01"; break;	//PIURA
		case 50: regPubId="05";	oficRegId="02"; break;	//SULLANA
		case 56: regPubId="05";	oficRegId="03"; break;	//TUMBES
		case 18: regPubId="06";	oficRegId="01"; break;	//CUSCO
		case  0: regPubId="06";	oficRegId="02"; break;	//ABANCAY
		case 44: regPubId="06";	oficRegId="03"; break;	//MADRE DE DIOS (puerto maldonado)
		case 46: regPubId="06";	oficRegId="04"; break;	//QUILLABAMBA
		case 49: regPubId="06";	oficRegId="05"; break;	//SICUANI
		case 51: regPubId="07";	oficRegId="01"; break;	//TACNA
		case 28: regPubId="07";	oficRegId="02"; break;	//ILO
		case 32: regPubId="07";	oficRegId="03"; break;	//JULIACA
		case 36: regPubId="07";	oficRegId="04"; break;	//MOQUEGUA
		case 45: regPubId="07";	oficRegId="05"; break;	//PUNO
		case 55: regPubId="08";	oficRegId="01"; break;	//TRUJILLO
		case 13: regPubId="08";	oficRegId="02"; break;	//CHEPEN
		case 20: regPubId="08";	oficRegId="03"; break;	//HUAMACHUCO
		case 39: regPubId="08";	oficRegId="04"; break;	//OTUZCO
		case 47: regPubId="08";	oficRegId="05"; break;	//SAN PEDRO (San pedro de Lloc)
		case 29: regPubId="09";	oficRegId="01"; break;	//MAYNAS  (iquitos)
		case 57: regPubId="09";	oficRegId="02"; break;	//YURIMAGUAS
		case 27: regPubId="10";	oficRegId="01"; break;	//ICA
		case 16: regPubId="10";	oficRegId="02"; break;	//CHINCHA
		case 41: regPubId="10";	oficRegId="03"; break;	//PISCO
		case 38: regPubId="10";	oficRegId="04"; break;	//NAZCA
		case  4: regPubId="10";	oficRegId="05"; break;	//AYACUCHO
		case 23: regPubId="10";	oficRegId="06"; break;	//HUANTA
		case  1: regPubId="10";	oficRegId="07"; break;	//ANDAHUAYLAS
		case 21: regPubId="10";	oficRegId="08"; break;	//HUANCAVELICA
		case 14: regPubId="11";	oficRegId="01"; break;	//CHICLAYO
		case  7: regPubId="11";	oficRegId="02"; break;	//CAJAMARCA
		case 30: regPubId="11";	oficRegId="03"; break;	//JAEN
		case  5: regPubId="11";	oficRegId="04"; break;	//BAGUA
		case 12: regPubId="11";	oficRegId="05"; break;	//CHACHAPOYAS
		case 17: regPubId="11";	oficRegId="06"; break;	//CHOTA
		case 37: regPubId="12";	oficRegId="01"; break;	//MOYOBAMBA
		case 52: regPubId="12";	oficRegId="02"; break;	//TARAPOTO
		case 31: regPubId="12";	oficRegId="03"; break;	//JUANJUI
		case 43: regPubId="13";	oficRegId="01"; break;	//PUCALLPA
	}
	*/
	String[] st = new String[2];
	st[0] = regPubId;
	st[1] = oficRegId;
	return st;
}

public static int getServicioIdPorNumeroSedes(int cantidadSedes)
{
	int a =32;
	
	switch (cantidadSedes)
	{
		case 1: a  = 20; break;
		case 2: a  = 21; break;
		case 3: a  = 22; break;
		case 4: a  = 23; break;
		case 5: a  = 24; break;
		case 6: a  = 25; break;
		case 7: a  = 26; break;
		case 8: a  = 27; break;
		case 9: a  = 28; break;
		case 10: a = 29; break;
		case 11: a = 30; break;
		case 12: a = 31; break;
		case 13: a = 32; break;
	}
	
	return a;
}




public static String rellenaIzq(String cadena, String relleno, int pad)
{
	if (cadena.length()>=pad)
		return cadena;
		
	StringBuffer sb = new StringBuffer();
	
	for (int i = 0; i < (pad-cadena.length()); i++ )
		{
		sb.append(relleno);
		}
	
	sb.append(cadena);
	
	return sb.toString();
}

public static String formatoNumero(double numero)
{
	DecimalFormat df = new DecimalFormat();
	DecimalFormatSymbols symb = df.getDecimalFormatSymbols();	
	
	symb.setDecimalSeparator('.');
	symb.setGroupingSeparator(',');
	
	df.setMaximumFractionDigits(2);
	df.setMinimumFractionDigits(2);
	df.setDecimalFormatSymbols(symb);
	
	String resultado = df.format(numero);
	
	return resultado;
}

public static String formatoNumero(String numero)
{
	double n = Double.parseDouble(numero);
	return Tarea.formatoNumero(n);
}

public static String reemplazaApos(String cadena)
	{
		if (cadena == null)
		{
			return cadena;
		}
		else
		{
			if (cadena.indexOf("'")<0)
			{
				return cadena;
			}
			else
			{
				int indice = 0;
				StringBuffer nuevo = new StringBuffer();
				while ( (cadena.indexOf("'", indice)) >= 0 ) {
					nuevo.append(cadena.substring(indice,cadena.indexOf("'", indice)+1));
					nuevo.append("'");
					indice = cadena.indexOf("'", indice)+1;
				}
			
				if(indice<=cadena.length())
					nuevo.append(cadena.substring(indice,cadena.length()));
				
				return nuevo.toString();
			}
			
		}
	}

public static UsuarioBean getUserBean(DBConnection dconn, String userId) throws Throwable
{
	UsuarioBean usuario= new UsuarioBean();
	DboCuenta cuentaUserI = new DboCuenta(dconn);
	cuentaUserI.setField(DboCuenta.CAMPO_USR_ID, userId);
		
	ArrayList listCuentaUser = cuentaUserI.searchAndRetrieveList();
		
	if(listCuentaUser.size() < 1)
		throw new CustomException(Constantes.EC_MISSING_PARAM, "Usuario incorrecto", "errorLogon");//Se puso ese descripcion de error para que no envie Mail
	
	if(listCuentaUser.size() > 1)
		throw new CustomException(Constantes.EC_GENERIC_DB_ERROR_INTEGRIDAD, "Existen mas de dos cuentas con el mismo usuario", "errorLogon");
	
	DboCuenta cuentaUser = (DboCuenta) listCuentaUser.get(0);
	
	// Estado = 0
	if (!cuentaUser.getField(DboCuenta.CAMPO_ESTADO).equals("1"))
		throw new CustomException(Constantes.CUENTA_DESHABILITADA, "La cuenta de usuario se encuentra inactiva", "errorLogon");
			
	// Estado = 1
	String cuentaId = cuentaUser.getField(DboCuenta.CAMPO_CUENTA_ID);
	String usrId = cuentaUser.getField(DboCuenta.CAMPO_USR_ID);
	String fgNewUsrVent = cuentaUser.getField(DboCuenta.CAMPO_FG_NEW_USR_VENT);
	String peNatuId = cuentaUser.getField(DboCuenta.CAMPO_PE_NATU_ID);
	
	boolean exonPago = cuentaUser.getField(DboCuenta.CAMPO_EXON_PAGO).equals("1")?true:false;
	String tipoUsr = cuentaUser.getField(DboCuenta.CAMPO_TIPO_USR);
	
	//Parte 3: Obtengo Perfil y Nivel de Usuario
	DboPerfilCuenta perfilCtaI = new DboPerfilCuenta(dconn);
	perfilCtaI.setField(DboPerfilCuenta.CAMPO_CUENTA_ID, cuentaId);
	perfilCtaI.setField(DboPerfilCuenta.CAMPO_ESTADO, "1");
	perfilCtaI.setFieldsToRetrieve(
	DboPerfilCuenta.CAMPO_PERFIL_ID + "|" + DboPerfilCuenta.CAMPO_NIVEL_ACCESO_ID);
	ArrayList listaperfilCta = perfilCtaI.searchAndRetrieveList();
	perfilCtaI.clearFieldsToRetrieve();
	if (listaperfilCta.size() != 1)
		throw new CustomException(Constantes.NO_PERFILCUENTA_USUARIO);
		
	DboPerfilCuenta perfilCta = (DboPerfilCuenta) listaperfilCta.get(0);
	
	long perfilId = Long.parseLong(perfilCta.getField(DboPerfilCuenta.CAMPO_PERFIL_ID));
	
	// Capturamos los datos de nombre y apellidos
	String nombres = null;
	String apPat = null;
	String apMat = null;
	DboPeNatu dboPN = new DboPeNatu(dconn);
	dboPN.setFieldsToRetrieve(DboPeNatu.CAMPO_NOMBRES + "|" + DboPeNatu.CAMPO_APE_PAT + "|" + DboPeNatu.CAMPO_APE_MAT);
	dboPN.setField(DboPeNatu.CAMPO_PE_NATU_ID, peNatuId);
			
	if (dboPN.find()) 
	{
		nombres = dboPN.getField(DboPeNatu.CAMPO_NOMBRES);
		apPat = dboPN.getField(DboPeNatu.CAMPO_APE_PAT);
		apMat = dboPN.getField(DboPeNatu.CAMPO_APE_MAT);
	}
	
	//Fin Parte 3: Obtengo Perfil y Nivel de Usuario
	
	//Parte 2: Obtengo Saldo de Usuario
	
	// Usuario Externo con Linea de Prepago
	double saldo_aux = 0.0;
	String lineaPrePago_aux = null;
	
	if (tipoUsr.substring(0, 1).equals("1")) 
	{
		String a = "";
		//if (isTrace(this)) trace("Usuario es Externo: Tipo Usuario : 1XXX", request);
		DboLineaPrepago lineaPrePagoI = new DboLineaPrepago(dconn);
		lineaPrePagoI.setField(DboLineaPrepago.CAMPO_CUENTA_ID, cuentaId);
		lineaPrePagoI.setFieldsToRetrieve(
			DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID + "|" + DboLineaPrepago.CAMPO_SALDO);
			
		ArrayList listLineaPrePago = lineaPrePagoI.searchAndRetrieveList();
		lineaPrePagoI.clearFieldsToRetrieve();
		if (listLineaPrePago.size() == 1) 
		{
			DboLineaPrepago lineaPrePago = (DboLineaPrepago) listLineaPrePago.get(0);
			saldo_aux =
				Double.parseDouble(lineaPrePago.getField(DboLineaPrepago.CAMPO_SALDO));
			lineaPrePago_aux =
				lineaPrePago.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
		} 
		else
			throw new CustomException(Constantes.NO_SALDO_DE_LINEA_PREPAGO);
		} 
		else 
		{
		// Usuario Interno
			//if (isTrace(this)) trace("Usuario es Interno: Tipo Usuario : 0XXX", request);
			saldo_aux = 9999;
			lineaPrePago_aux = null;
				
				/*DboCargaLaboral dboCarLab = new DboCargaLaboral(dconn);
				dboCarLab.setField(dboCarLab.CAMPO_CUENTA_ID, cuentaId);
				dboCarLab.setField(dboCarLab.CAMPO_ESTADO, "1");
				if(dboCarLab.haySiguiente)
					session.setAttribute("REGIS","SI");
				*/
		}
		
		//Fin Parte 2: Obtengo Saldo de Usuario
		/* Obteniendo Permisos del Usuario */
			
		StringBuffer cadenacj = new StringBuffer(DboCuentaJuris.CAMPO_PERSONA_ID);
		cadenacj.append("|").append(DboCuentaJuris.CAMPO_OFIC_REG_ID);
		cadenacj.append("|").append(DboCuentaJuris.CAMPO_REG_PUB_ID);
		cadenacj.append("|").append(DboCuentaJuris.CAMPO_JURIS_ID);
		
		DboCuentaJuris cuentajuri = new DboCuentaJuris(dconn);
		cuentajuri.setFieldsToRetrieve(cadenacj.toString());
		cuentajuri.setField(DboCuentaJuris.CAMPO_CUENTA_ID, cuentaId);
			
		if (!cuentajuri.find())
			throw new CustomException(Constantes.NO_REG_CUENTA_JURIS);
			
		//Parte 4: Guardo Datos en Sesion
		usuario.setUserId(usrId);
		usuario.setCuentaId(cuentaId);
		usuario.setPeNatuId(peNatuId);
		usuario.setExonPago(exonPago);
		usuario.setTipoUser(tipoUsr);
		if (tipoUsr.substring(0, 1).equals("0")==true)
			usuario.setFgInterno(true);
		else
			usuario.setFgInterno(false);
				
		usuario.setFgIndividual(tipoUsr.substring(1, 2).equals("1"));
		usuario.setFgAdmin(tipoUsr.substring(2, 3).equals("1"));
		usuario.setPerfilId(perfilId);
		usuario.setNivelAccesoId(0);
		usuario.setSaldo(saldo_aux);
		usuario.setLinPrePago(lineaPrePago_aux);
		usuario.setPersonaId(cuentajuri.getField(DboCuentaJuris.CAMPO_PERSONA_ID));
		usuario.setOficRegistralId(
			cuentajuri.getField(DboCuentaJuris.CAMPO_OFIC_REG_ID));
		usuario.setRegPublicoId(cuentajuri.getField(DboCuentaJuris.CAMPO_REG_PUB_ID));
		usuario.setJurisdiccionId(cuentajuri.getField(DboCuentaJuris.CAMPO_JURIS_ID));
		usuario.setApeMat(apMat);
		usuario.setApePat(apPat);
		usuario.setNombres(nombres);
		
		//Parte 4.1: Es Persona Juridica?
		
		String codOrg = null;
		
		if (tipoUsr.substring(1, 2).equals("0")) 
		{
			
			//if (isTrace(this)) trace("Usuario Es Persona Juridica. Tipo Usuario: X0XX", request);
			DboPeNatu peNatu = new DboPeNatu(dconn);
			peNatu.setField(DboPeNatu.CAMPO_PE_NATU_ID, peNatuId);
			peNatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
			
			if (!peNatu.find())
				throw new CustomException(Constantes.NO_REG_PE_NATU);
				
			codOrg = peNatu.getField(DboPeNatu.CAMPO_PE_JURI_ID);
			usuario.setCodOrg(codOrg);
			
			//if (isTrace(this)) trace("Codigo Organizacion(PE_JURI_ID) = " + codOrg, request);
			
			DboPeJuri pejuri = new DboPeJuri(dconn);
			pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_PREF_CTA + "|" + DboPeJuri.CAMPO_RAZ_SOC);
			pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, codOrg);
			
			if (!pejuri.find())
				throw new CustomException(Constantes.NO_REG_PE_JURI);
				
			usuario.setUserAdminOrg(pejuri.getField(DboPeJuri.CAMPO_PREF_CTA) + "001");
			usuario.setRazSocial(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
			
			//if (isTrace(this)) trace("Usuario Administrador de la Organizacion ingresada es: "
			//		+ usuario.getUserAdminOrg(),
			//	request);
					
			// 17Sept2002cjvc77 - Es usuario Externo y Juridico 
			if(tipoUsr.substring(0,1).equals("1"))
			{
				DboTmInstSir temp = new DboTmInstSir(dconn);
				temp.setFieldsToRetrieve(DboTmInstSir.CAMPO_CUR_PRES);
				temp.setField(DboTmInstSir.CAMPO_PE_JURI_ID, codOrg);
				
				if(temp.find())
					usuario.setCur(temp.getField(DboTmInstSir.CAMPO_CUR_PRES));
				else
					usuario.setCur(null);
			}
			else
				usuario.setCur(null);
						
			//Anadido 14 Setiembre modificado el 16
			if (tipoUsr.substring(0, 1).equals("1")) 
			{ //Usuario Externo
				DboLineaPrepago lp = new DboLineaPrepago(dconn);
				lp.setFieldsToRetrieve(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
				lp.setField(DboLineaPrepago.CAMPO_PE_JURI_ID, codOrg);
				lp.setField(DboLineaPrepago.CAMPO_CUENTA_ID, null);
				
				if(!lp.find())
					throw new CustomException(Constantes.NO_LINEA_PREPAGO_ORG);
				
				usuario.setLineaPrePagoOrganizacion(lp.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID));
		}
		else
			usuario.setLineaPrePagoOrganizacion(null);
			
	} 
	else 
	{
		//if (isTrace(this)) trace("Usuario Es Persona Natural Usuario: X1XX", request);
		usuario.setCodOrg(null);
		usuario.setUserAdminOrg(null);
		//Anadido 14 de Setiembre
		usuario.setLineaPrePagoOrganizacion(null);
		
	}
			
	//validar CONTRATO
	//Reglas : -TODO USUARIO EXTERNO TIENE CONTRATO
	//         -LOS  USUARIOS INTERNOS NO TIENEN CONTRATO
	//sep2002_HT
	
	//_old
	//_old Es Externo Natural o Externo Juridico Administrador
	//_old if (tipoUsr.startsWith("110") || tipoUsr.startsWith("101")) 
		
	//_new
	if (usuario.getFgInterno()==false)
	{
		DboContrato contrato = new DboContrato(dconn);
		// Persona Natural
		if (tipoUsr.substring(1, 2).equals("1")) 
		{
			contrato.setField(DboContrato.CAMPO_CUENTA_ID, cuentaId);
			contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
		} 
		else 
		{
			// Persona Juridica
			contrato.setField(DboContrato.CAMPO_PE_JURI_ID, codOrg);
			contrato.setFieldsToRetrieve(DboContrato.CAMPO_CONTRATO_ID);
		}
			
		if (!contrato.find())
			throw new CustomException(Constantes.NO_REG_CONTRATO);
			
		usuario.setNum_contrato(contrato.getField(DboContrato.CAMPO_CONTRATO_ID));
	} 
	else
		usuario.setNum_contrato(null);
		
	return usuario;
}
public static ArrayList seleccionarBusqueda(String criterio,String flag, Connection conn) throws Throwable
{
	int flagSeleccion;
	ArrayList listResultado;
	listResultado = null;
	flagSeleccion = Integer.parseInt(flag);
	
	switch (flagSeleccion) 
	{
		case 1:
			listResultado = buscarXTomoFolio(criterio, conn);
			break;
		case 2:
			listResultado = buscarXNroPartida(criterio, conn);
			break;
		case 3:
			listResultado = buscarXFicha(criterio, conn);
			break;
		case 4:
			listResultado = buscarVehiculoXPartida(criterio, conn);
			break;
		case 41:
			listResultado = buscarVehiculoXPlaca(criterio, conn);
			break;
		case 5:
			listResultado = buscarXNombreVehicular(criterio, conn);
			break;
		case 6:
			listResultado = buscarXNombreVehicularJuri(criterio, conn);
			break;
		case 7:
			listResultado = buscarXNombreVehicularMotor(criterio, conn);
			break;
		case 8:
			listResultado = buscarXNombreVehicularChasis(criterio, conn);
			break;
		case 9:
			listResultado = buscarPersonaNatural(criterio, conn);
			break;
		case 10:
			listResultado = buscarPersonaJuridica(criterio, conn);
			break;
		case 11:
			listResultado = buscarEmbarcacion(criterio, conn);
			break;
		case 12:
			listResultado = buscarAeronaveXMatricula(criterio, conn);
			break;
		case 13:
			listResultado = buscarAeronaveXNombre(criterio, conn);
			break;
		case 14:
			listResultado = buscarAeronaveXRazonSocial(criterio, conn);
			break;
		case 15:
			listResultado = buscarBuque(criterio, conn);
			break;
			
	}
	
	return listResultado; 
}
/*
*  @autor: mgarate
*  @fecha: 25-05-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que genera el criterio de la busqueda para ser mostrada
*/
public static String generarCriterio(String criterio)
{
	int count2=0;
	StringTokenizer token = new StringTokenizer(criterio,"/",false);
	StringBuffer cadenaMuestra = new StringBuffer("");
	String resultado="";
	String firstToken="";
	while(token.hasMoreTokens())
	{
		count2++;
		String parametroBusqueda = token.nextToken();
		if(count2>2)
		{
			StringTokenizer token2 = new StringTokenizer(parametroBusqueda,"=",false);
			while(token2.hasMoreElements())
			{
				String cadenaNombre = token2.nextToken();
				
				if(cadenaNombre.equals("ficha"))
				{
					firstToken="";
					cadenaMuestra.append("Ficha=");
				}
				else if(cadenaNombre.equals("partida"))
				{
					firstToken="";
					cadenaMuestra.append("Partida=");
				}
				else if(cadenaNombre.equals("tomo"))
				{
					firstToken="";
					cadenaMuestra.append("Tomo=");
				}
				else if(cadenaNombre.equals("folio"))
				{
					firstToken="";
					cadenaMuestra.append("Folio=");
				}
				else if(cadenaNombre.equals("placa"))
				{
					firstToken="";
					cadenaMuestra.append("Placa=");
				}
				else if(cadenaNombre.equals("apepat") || cadenaNombre.equals("apepataero"))
				{
					firstToken="";
					cadenaMuestra.append("Apellido Paterno=");
				}
				else if(cadenaNombre.equals("apemat") || cadenaNombre.equals("apemataero"))
				{
					firstToken="";
					cadenaMuestra.append("Apellido Materno=");
				}
				else if(cadenaNombre.equals("nombre") || cadenaNombre.equals("nombreaero"))
				{
					firstToken="";
					cadenaMuestra.append("Nombres=");
				}
				else if(cadenaNombre.equals("razonsocial") || cadenaNombre.equals("razonsocialaero"))
				{
					firstToken="";
					cadenaMuestra.append("Razon Social=");
				}
				else if(cadenaNombre.equals("sigla") || cadenaNombre.equals("siglaaero"))
				{
					firstToken="";
					cadenaMuestra.append("Sigla=");
				}
				else if(cadenaNombre.equals("numeromatricula"))
				{
					firstToken="";
					cadenaMuestra.append("Numero de Matricula =");
				}
				else if(cadenaNombre.equals("nombreembarcacion"))
				{
					firstToken="";
					cadenaMuestra.append("Nombre de Embarcacion=");
				}
				else if(cadenaNombre.equals("numeromotor"))
				{
					firstToken="";
					cadenaMuestra.append("Numero de Motor=");
				}
				else if(cadenaNombre.equals("nombrebuque"))
				{
					firstToken="";
					cadenaMuestra.append("Nombre de Buque=");
				}
				else if(cadenaNombre.equals("chasis"))
				{
					firstToken="";
					cadenaMuestra.append("Chasis=");
				}
				else if(cadenaNombre.equals("registro"))
				{
					firstToken="";
					cadenaMuestra.append("");
					firstToken="registro";
				}else if(cadenaNombre.equals("flagmetodo"))
				{
					firstToken="";
					firstToken="flagmetodo";
				}
				else
				{
					if(firstToken.equals("flagmetodo"))
					{
					}else if(firstToken.equals("registro"))
					{
					}else
					{
						cadenaMuestra.append(cadenaNombre);
					}
					
				}
			}
			cadenaMuestra.append(" ");
			resultado = cadenaMuestra.substring(0,cadenaMuestra.length()-1);
		}		
		
	}
	return resultado; 
}
public static ArrayList buscarBuque(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String matricula;
	String nombre;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	matricula = criterioBean.getNumeroMatricula();
	nombre = criterioBean.getNombreBuque();
	
	StringBuffer query = new StringBuffer();
	
	boolean m = false;
	boolean n = false;

	if(matricula== null)
	{
		matricula = "";
	}
	if(nombre == null)
	{
		nombre = "";
	}
	
	if (matricula.length() > 0)
	{
		m = true;
	}
	if (m == false)
	{
		if (nombre.length() > 0)
		{
			n = true;
		}
	}
	
	query.append("SELECT PARTIDA.ESTADO as estado, ");
	query.append("PARTIDA.REFNUM_PART      as partida_refnum_part, ");
	query.append("PARTIDA.NUM_PARTIDA      as partida_num_partida, ");
	query.append("REGIS_PUBLICO.SIGLAS     as regis_publico_siglas, ");
	query.append("OFIC_REGISTRAL.NOMBRE    as ofic_registral_nombre, ");
	query.append("REG_BUQUES.NOMBRE        as reg_buques_nombre, ");
	query.append("REG_BUQUES.NUM_MATRICULA as reg_buques_num_matricula ");
	query.append("FROM REGIS_PUBLICO, ");
	query.append("OFIC_REGISTRAL, ");
	query.append("REG_BUQUES, ");
	query.append("PARTIDA, ");
	query.append("grupo_libro_area gla, ");
	query.append("grupo_libro_area_det glad ");
	query.append("WHERE REG_BUQUES.ESTADO = '1' ");
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("and reg_buques.refnum_part = partida.refnum_part ");
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND partida.reg_pub_id = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND partida.reg_pub_id IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("and partida.cod_libro = glad.cod_libro ");
	query.append("and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ");
	query.append("and gla.cod_grupo_libro_area = '").append(criterioBean.getAreaRegistral()).append("' ");
	if (m == true && n == true)
	{
		query.append("and REG_BUQUES.NUM_MATRICULA like '").append(matricula).append("%' ");
		query.append("AND REG_BUQUES.NOMBRE like '").append(nombre).append("%' ");
	}
	if (m == true && n == false)
	{
		query.append("and REG_BUQUES.NUM_MATRICULA like '").append(matricula).append("%' ");
	}
	if (m == false && n == true)
	{
		query.append("and REG_BUQUES.NOMBRE like '").append(nombre).append("%' ");
	}
	
	query.append("and PARTIDA.ESTADO != '2' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
			
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();
		String refNumPart = rset.getString("partida_refnum_part");
		partida.setRefNumPart(refNumPart);
		partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
		partida.setNumPartida(rset.getString("partida_num_partida"));
		partida.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
		partida.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
		// 2003-07-31 enviar el estado al JSP
		partida.setEstado(rset.getString("estado"));

		//ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
		if (dboFicha.find())
		{
			partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
			String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
			int nbis = 0;
			try
			{
				nbis = Integer.parseInt(bis);
			}
			catch (NumberFormatException nuf)
			{
				nbis =0;
			}
			if (nbis>=1)
			{
				partida.setFichaId(partida.getFichaId() + " BIS");
			}
		}

		//obtener tomo y foja
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find())
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}
			if (bisf.trim().length() > 0)
			{
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
			}
			if (partida.getTomoId().length()>0)
			{
				if (partida.getTomoId().startsWith("9"))
				{
					String ntomo = partida.getTomoId().substring(1);
					partida.setTomoId(ntomo);
				}
			}
		}

		partida.setBuqueNombre(rset.getString("reg_buques_nombre"));
		partida.setBuqueMatricula(rset.getString("reg_buques_num_matricula"));

		listaResultado.add(partida);
		
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 13-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice de aeronave por razon social
*/
public static ArrayList buscarAeronaveXRazonSocial(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String razonSocial = "";
	String siglas = "";
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	
	DBConnection dconn = new DBConnection(conn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	razonSocial = criterioBean.getRazonSocial();
	siglas = criterioBean.getSigla();
	
	if(razonSocial==null)
	{
		razonSocial="";
	}
	if(siglas == null)
	{
		siglas ="";
	}
	
	StringBuffer query = new StringBuffer();
	
	query.append("SELECT PARTIDA.ESTADO as estado, ");
	query.append("PARTIDA.REFNUM_PART as partida_refnum_part, ");
	query.append("PARTIDA.NUM_PARTIDA as partida_num_partida, ");
	query.append("REGIS_PUBLICO.SIGLAS as regis_publico_siglas, ");
	query.append("OFIC_REGISTRAL.NOMBRE as ofic_registral_nombre, ");
	query.append("REG_AERONAVES.NUM_MATRICULA as reg_aeronaves_num_matricula, ");
	query.append("PRTC_JUR.RAZON_SOCIAL as prtc_jur_razon_social ");
	query.append("FROM REGIS_PUBLICO, REG_AERONAVES, OFIC_REGISTRAL, IND_PRTC,  PRTC_JUR, ");
	query.append("PARTIDA, grupo_libro_area gla, grupo_libro_area_det glad ");
	query.append("WHERE REG_AERONAVES.ESTADO = '1' ");
	query.append("AND IND_PRTC.COD_PARTIC = '003' ");
	query.append("AND IND_PRTC.TIPO_PERS = 'J' ");
	query.append("AND PARTIDA.COD_LIBRO = '040' ");
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("AND PRTC_JUR.CUR_PRTC = IND_PRTC.CUR_PRTC ");
	query.append("AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
	query.append("AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");
	query.append("and PRTC_JUR.RAZON_SOCIAL LIKE '").append(razonSocial.trim()).append("%' ");
	if (siglas.trim().length()>0)
	{
		query.append("and PRTC_JUR.SIGLAS like '").append(siglas.trim()).append("%' ");
	}
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND partida.reg_pub_id = '").append(criterioBean.getSedesElegidas()[0]).append("'");
		query.append("and PRTC_JUR.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND partida.reg_pub_id IN ").append(criterioBean.getSedesSQLString());
		query.append("and PRTC_JUR.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("and partida.cod_libro = glad.cod_libro ");
	query.append("and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ");
	query.append("and gla.cod_grupo_libro_area = '").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("AND PARTIDA.ESTADO != '2' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();
		String refNumPart = rset.getString("partida_refnum_part");
		partida.setRefNumPart(refNumPart);
		partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
		partida.setNumPartida(rset.getString("partida_num_partida"));
		partida.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
		partida.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
		// 2003-07-31 enviar el estado al JSP
		partida.setEstado(rset.getString("estado"));

		partida.setAeronaveMatricula(rset.getString("reg_aeronaves_num_matricula"));
		partida.setAeronaveTipoTitular("PJ");
		partida.setAeronaveTitular(rset.getString("prtc_jur_razon_social"));

		listaResultado.add(partida);
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 13-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice de aeronave por nombre
*/
public static ArrayList buscarAeronaveXNombre(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String apellidoPaterno;
	String apellidoMaterno;
	String nombre;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	apellidoPaterno = criterioBean.getApellidoParterno().trim();
	apellidoMaterno = criterioBean.getApellidoMaterno().trim();
	nombre = criterioBean.getNombre().trim();
	
	StringBuffer query = new StringBuffer();
	
	query.append("SELECT PARTIDA.ESTADO AS ESTADO, ");
	query.append("PARTIDA.REFNUM_PART AS PARTIDA_REFNUM_PART, ");
	query.append("PARTIDA.NUM_PARTIDA AS PARTIDA_NUM_PARTIDA, ");
	query.append("REGIS_PUBLICO.SIGLAS AS REGIS_PUBLICO_SIGLAS, ");
	query.append("OFIC_REGISTRAL.NOMBRE AS OFIC_REGISTRAL_NOMBRE, ");
	query.append("REG_AERONAVES.NUM_MATRICULA AS REG_AERONAVES_NUM_MATRICULA, ");
	query.append("PRTC_NAT.APE_PAT AS PRTC_NAT_APE_PAT, ");
	query.append("PRTC_NAT.APE_PAT AS PRTC_NAT_APE_MAT, ");
	query.append("PRTC_NAT.NOMBRES AS PRTC_NAT_NOMBRES ");
	query.append("FROM REGIS_PUBLICO, REG_AERONAVES, OFIC_REGISTRAL, IND_PRTC,  PRTC_NAT, ");
	query.append("PARTIDA, GRUPO_LIBRO_AREA GLA, GRUPO_LIBRO_AREA_DET GLAD ");
	query.append("WHERE REG_AERONAVES.ESTADO = '1' ");
	query.append("AND IND_PRTC.COD_PARTIC = '003' ");
	query.append("AND IND_PRTC.TIPO_PERS = 'N' ");
	query.append("AND PARTIDA.COD_LIBRO = '040' ");
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("AND PRTC_NAT.CUR_PRTC = IND_PRTC.CUR_PRTC ");
	query.append("AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
	query.append("AND IND_PRTC.REFNUM_PART = REG_AERONAVES.REFNUM_PART ");
	query.append("AND APE_PAT LIKE '").append(apellidoPaterno).append("%' ");
	
	if (apellidoMaterno.trim().length()>0)
	{	
		query.append("AND APE_MAT LIKE '").append(apellidoMaterno.trim()).append("%' ");
	}
	if (nombre.trim().length()>0)
	{
		query.append("AND NOMBRES LIKE '").append(nombre.trim()).append("%' ");
	}
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("' ");
		query.append("AND PRTC_NAT.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("' ");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
		query.append("AND PRTC_NAT.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = '").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("AND PARTIDA.ESTADO != '2' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();
		String refNumPart = rset.getString("partida_refnum_part");
		partida.setRefNumPart(refNumPart);
		partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
		partida.setNumPartida(rset.getString("partida_num_partida"));
		partida.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
		partida.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
		partida.setEstado(rset.getString("estado"));

		//ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
		if (dboFicha.find())
				{
					partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
					String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
					int nbis = 0;
					try {
						nbis = Integer.parseInt(bis);
					}
					catch (NumberFormatException n)
					{
						nbis =0;
					}
					if (nbis>=1)
					{
						partida.setFichaId(partida.getFichaId() + " BIS");
					}
				}

		//obtener tomo y foja
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find())
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}
			
			if (bisf.trim().length() > 0)
			{
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
			}
					
			if (partida.getTomoId().length()>0)
			{
				if (partida.getTomoId().startsWith("9"))
				{
					String ntomo = partida.getTomoId().substring(1);
					partida.setTomoId(ntomo);
				}
			}
		}

		partida.setAeronaveMatricula(rset.getString("reg_aeronaves_num_matricula"));
		partida.setAeronaveTipoTitular("PN");

		//propietario de la aeronave
		sb.delete(0, sb.length());
		String prtc_nat_ape_pat = rset.getString("prtc_nat_ape_pat");
		String prtc_nat_ape_mat = rset.getString("prtc_nat_ape_mat");
		String prtc_nat_nombres = rset.getString("prtc_nat_nombres");
		sb.append(prtc_nat_ape_pat);
		sb.append(" ");
		sb.append(prtc_nat_ape_mat);
		sb.append(", ");
		sb.append(prtc_nat_nombres);
		partida.setAeronaveTipoTitular(sb.toString());

		listaResultado.add(partida);
			

	}
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 13-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice de aeronave por matricula
*/
public static ArrayList buscarAeronaveXMatricula(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String matricula;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	DboPrtcNat dboPrtcNat = new DboPrtcNat(dconn);
	DboPrtcJur dboPrtcJur = new DboPrtcJur(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	matricula = criterioBean.getNumeroMatricula();
	
	StringBuffer query = new StringBuffer();
	
	query.append("SELECT PARTIDA.ESTADO AS ESTADO, ");
	query.append("PARTIDA.REFNUM_PART         AS PARTIDA_REFNUM_PART, ");
	query.append("PARTIDA.NUM_PARTIDA         AS PARTIDA_NUM_PARTIDA, ");
	query.append("REGIS_PUBLICO.SIGLAS        AS REGIS_PUBLICO_SIGLAS, ");
	query.append("OFIC_REGISTRAL.NOMBRE       AS OFIC_REGISTRAL_NOMBRE, ");
	query.append("REG_AERONAVES.NUM_MATRICULA AS REG_AERONAVES_NUM_MATRICULA, ");
	query.append("IND_PRTC.TIPO_PERS          AS IND_PRTC_TIPO_PERS, ");
	query.append("IND_PRTC.CUR_PRTC           AS IND_PRTC_CUR_PRTC, ");
	query.append("PARTIDA.REG_PUB_ID          AS PARTIDA_REG_PUB_ID, ");
	query.append("PARTIDA.OFIC_REG_ID         AS PARTIDA_OFIC_REG_ID ");
	query.append("FROM REGIS_PUBLICO,  REG_AERONAVES, OFIC_REGISTRAL, IND_PRTC, PARTIDA, ");
	query.append("GRUPO_LIBRO_AREA GLA, GRUPO_LIBRO_AREA_DET GLAD ");
	query.append("WHERE REG_AERONAVES.ESTADO = '1' ");
	query.append("AND IND_PRTC.COD_PARTIC = '003' ");
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("AND REG_AERONAVES.REFNUM_PART = PARTIDA.REFNUM_PART ");
	query.append("AND PARTIDA.REFNUM_PART = IND_PRTC.REFNUM_PART ");
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
		
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = '").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("AND REG_AERONAVES.NUM_MATRICULA = '").append(matricula).append("' ");
	query.append("AND PARTIDA.ESTADO != '2' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();

		String refNumPart = rset.getString("partida_refnum_part");
		partida.setRefNumPart(refNumPart);
		partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
		partida.setNumPartida(rset.getString("partida_num_partida"));
		partida.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
		partida.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
		
		partida.setEstado(rset.getString("estado"));

		String regPubId   = rset.getString("partida_reg_pub_id");
		String oficRegId  = rset.getString("partida_ofic_reg_id");

		//ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
		if (dboFicha.find())
		{
			partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
			String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
			int nbis = 0;
			try {
				nbis = Integer.parseInt(bis);
			}
			catch (NumberFormatException n)
			{
				nbis =0;
			}
			if (nbis>=1)
			{
				partida.setFichaId(partida.getFichaId() + " BIS");
			}
		}
		//obtener tomo y foja
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find())
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}

			if (bisf.trim().length() > 0)
			{
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
			}
			
			if (partida.getTomoId().length()>0)
			{
				if (partida.getTomoId().startsWith("9"))
				{
					String ntomo = partida.getTomoId().substring(1);
					partida.setTomoId(ntomo);
				}
			}
		}


		partida.setAeronaveMatricula(rset.getString("reg_aeronaves_num_matricula"));

		//propietario de la aeronave
			String tipoPersona = rset.getString("ind_prtc_tipo_pers");
			String curPrtc     = rset.getString("ind_prtc_cur_prtc");
			if (tipoPersona.equals("N"))
					{
						partida.setAeronaveTipoTitular("PN");
						//persona natural
						dboPrtcNat.clearAll();
						sb.delete(0,sb.length());
						sb.append(DboPrtcNat.CAMPO_APE_PAT).append("|");
						sb.append(DboPrtcNat.CAMPO_APE_MAT).append("|");
						sb.append(DboPrtcNat.CAMPO_NOMBRES);
						dboPrtcNat.setFieldsToRetrieve(sb.toString());


						dboPrtcNat.setField(DboPrtcNat.CAMPO_CUR_PRTC, curPrtc);
						dboPrtcNat.setField(DboPrtcNat.CAMPO_REG_PUB_ID, regPubId);
						dboPrtcNat.setField(DboPrtcNat.CAMPO_OFIC_REG_ID, oficRegId);
						if (dboPrtcNat.find())
							{
								sb.delete(0, sb.length());
								sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_PAT));
								sb.append(" ");
								sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_APE_MAT));
								sb.append(", ");
								sb.append(dboPrtcNat.getField(DboPrtcNat.CAMPO_NOMBRES));
								partida.setAeronaveTipoTitular(sb.toString());
							}
					}
				else
					{
						partida.setAeronaveTipoTitular("PJ");
						//persona juridica
						dboPrtcJur.clearAll();
						dboPrtcJur.setFieldsToRetrieve(DboPrtcJur.CAMPO_RAZON_SOCIAL);
						dboPrtcJur.setField(dboPrtcJur.CAMPO_CUR_PRTC, curPrtc);
						dboPrtcJur.setField(DboPrtcJur.CAMPO_REG_PUB_ID, regPubId);
						dboPrtcJur.setField(DboPrtcJur.CAMPO_OFIC_REG_ID, oficRegId);
						if (dboPrtcJur.find())
						{
							partida.setAeronaveTipoTitular(dboPrtcJur.getField(DboPrtcJur.CAMPO_RAZON_SOCIAL));
						}
					} //if (tipoPersona.equals("N")

			
		listaResultado.add(partida);
		
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 13-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice de busqueda de Embarcacion
*/
public static ArrayList buscarEmbarcacion(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String matricula;
	String nombre;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
	DboParticLibro dboParticLibro = new DboParticLibro(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	matricula = criterioBean.getNumeroMatricula();
	nombre = criterioBean.getNombreEmbarcacion();
	
	StringBuffer query = new StringBuffer();
	StringBuffer cadenaPart = new StringBuffer();
	
	boolean m = false;
	boolean n = false;

	if (matricula.length() > 0)
	{
		m = true;
	}
	if (m == false)
	{
		if (nombre.length() > 0)
		n = true;
	}
	
	query.append("SELECT PARTIDA.ESTADO AS ESTADO, ");
	query.append("PARTIDA.REFNUM_PART        AS PARTIDA_REFNUM_PART, ");
	query.append("PARTIDA.COD_LIBRO          AS PARTIDA_COD_LIBRO, ");
	query.append("PARTIDA.NUM_PARTIDA        AS PARTIDA_NUM_PARTIDA, ");
	query.append("REGIS_PUBLICO.SIGLAS       AS REGIS_PUBLICO_SIGLAS, ");
	query.append("OFIC_REGISTRAL.NOMBRE      AS OFIC_REGISTRAL_NOMBRE, ");
	query.append("REG_EMB_PESQ.NUM_MATRICULA AS REG_EMB_PESQ_NUM_MATRICULA, ");
	query.append("REG_EMB_PESQ.NOMBRE_EMB    AS REG_EMB_PESQ_NOMBRE_EMB ");
	query.append("FROM REGIS_PUBLICO,OFIC_REGISTRAL,REG_EMB_PESQ,PARTIDA, GRUPO_LIBRO_AREA_DET GLAD, GRUPO_LIBRO_AREA GLA ");
	query.append("WHERE REG_EMB_PESQ.ESTADO = '1' ");
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("AND REG_EMB_PESQ.REFNUM_PART = PARTIDA.REFNUM_PART ");
	
	if (m && n)
	{
		query.append("AND REG_EMB_PESQ.NUM_MATRICULA LIKE '" + matricula + "%' AND ");
		query.append("AND REG_EMB_PESQ.NOMBRE_EMB LIKE '" + nombre + "%' ");
	}
	if (m)
	{
		query.append("AND REG_EMB_PESQ.NUM_MATRICULA LIKE '" + matricula + "%' ");
	}
	if (n)
	{
		query.append("AND REG_EMB_PESQ.NOMBRE_EMB LIKE '" + nombre + "%' ");
	}
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = '").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("AND PARTIDA.ESTADO != '2' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();

		//_completar los detalles de la partida encontrada
		String refNumPart = rset.getString("partida_refnum_part");
		String codLibro   = rset.getString("partida_cod_libro");
		partida.setRefNumPart(refNumPart);
		partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
		partida.setNumPartida(rset.getString("partida_num_partida"));
		partida.setRegPubDescripcion(rset.getString("regis_publico_siglas"));
		partida.setOficRegDescripcion(rset.getString("ofic_registral_nombre"));
		// 2003-07-31 enviar el estado al JSP
		partida.setEstado(rset.getString("estado"));

		//ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
		if (dboFicha.find())
		{
			partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
			String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
			int nbis = 0;
			try {
					nbis = Integer.parseInt(bis);
			}
			catch (NumberFormatException nfe)
			{
				nbis =0;
			}
			if (nbis>=1)
			{
				partida.setFichaId(partida.getFichaId() + " BIS");
			}

		}

		//obtener tomo y foja
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find())
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}

			if (bisf.trim().length() > 0)
			{
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
			}

			if (partida.getTomoId().length()>0)
			{
				if (partida.getTomoId().startsWith("9"))
				{
					String ntomo = partida.getTomoId().substring(1);
					partida.setTomoId(ntomo);
				}
			}
		}

		partida.setEmbarcacionMatricula(rset.getString("reg_emb_pesq_num_matricula"));
		if (partida.getEmbarcacionMatricula()==null)
		{
			partida.setEmbarcacionMatricula("");
		}
		
		partida.setEmbarcacionNombre(rset.getString("reg_emb_pesq_nombre_emb"));

		listaResultado.add(partida);
	
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 13-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice de personal juridica
*/
public static ArrayList buscarPersonaJuridica(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String razonSocial = "";
	String siglas = "";
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
	DboParticLibro dboParticLibro = new DboParticLibro(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	razonSocial = criterioBean.getRazonSocial();
	siglas = criterioBean.getSigla();
	if(razonSocial == null)
	{
		razonSocial="";
	}
	if(siglas==null)
	{
		siglas="";
	}
	
	StringBuffer query = new StringBuffer();
	
	query.append("SELECT PARTIDA.ESTADO AS ESTADO, REGIS_PUBLICO.SIGLAS, ");
	query.append("OFIC_REGISTRAL.NOMBRE, IND_PRTC.COD_PARTIC, ");
	query.append("PRTC_JUR.RAZON_SOCIAL, PRTC_JUR.TI_DOC, ");
	query.append("PRTC_JUR.NU_DOC, PARTIDA.REFNUM_PART, ");
	query.append("PARTIDA.NUM_PARTIDA, PARTIDA.COD_LIBRO, ");
	query.append("IND_PRTC.ESTADO ");
	query.append("FROM PRTC_JUR, IND_PRTC, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, GRUPO_LIBRO_AREA GLA, GRUPO_LIBRO_AREA_DET GLAD ");
	query.append("WHERE PRTC_JUR.CUR_PRTC = IND_PRTC.CUR_PRTC ");
	query.append("AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART ");
	query.append("AND PARTIDA.OFIC_REG_ID = PRTC_JUR.OFIC_REG_ID ");
	query.append("AND PARTIDA.REG_PUB_ID = PRTC_JUR.REG_PUB_ID ");
	query.append("AND PARTIDA.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID ");
	query.append("AND PARTIDA.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
	if(razonSocial.length()>0)
	{
		query.append("AND PRTC_JUR.RAZON_SOCIAL LIKE '").append(razonSocial).append("%' ");
	}
	if(siglas.length()>0)
	{
		query.append("AND PRTC_JUR.SIGLAS LIKE '").append(siglas).append("%' ");
	}
	query.append("AND PARTIDA.ESTADO != '2' ");
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = '").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("AND IND_PRTC.ESTADO = '1' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();

		String refNumPart = rset.getString("refnum_part");
		String codLibro   = rset.getString("cod_libro");
		partida.setRefNumPart(refNumPart);
		partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
		partida.setNumPartida(rset.getString("num_partida"));
		partida.setRegPubDescripcion(rset.getString("siglas"));
		partida.setOficRegDescripcion(rset.getString("nombre"));
		
		partida.setEstado(rset.getString("estado"));

		partida.setEstadoInd("Inactivo");
		if (rset.getString("estado").startsWith("1"))
		{
			partida.setEstadoInd("Activo");
		}

		//ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
		if (dboFicha.find())
		{
			partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
			String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
			int nbis = 0;
			try{
				nbis = Integer.parseInt(bis);
			}
			catch (NumberFormatException n)
			{
				nbis =0;
			}
			if (nbis>=1)
			{
				partida.setFichaId(partida.getFichaId() + " BIS");
			}
		}

		//obtener tomo y foja
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find())
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}

			if (bisf.trim().length() > 0)
			{
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
			}

			if (partida.getTomoId().length()>0)
				{
					if (partida.getTomoId().startsWith("9"))
						{
							String ntomo = partida.getTomoId().substring(1);
							partida.setTomoId(ntomo);
						}
				}
		}

		//descripcion libro
		dboTmLibro.clearAll();
		dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
		dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
		if (dboTmLibro.find())
		{
			partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
		}

		//participante y su número de documento
		String tipoDocumento="";
		String codPartic="";

		partida.setParticipanteDescripcion(rset.getString("RAZON_SOCIAL"));

		String nuDocIden = rset.getString("nu_doc");
		if (nuDocIden==null || nuDocIden.trim().length()==0)
		{
			partida.setParticipanteNumeroDocumento("&nbsp;");
		}
		else
		{
			partida.setParticipanteNumeroDocumento(nuDocIden);
		}

		tipoDocumento = rset.getString("ti_doc");
		codPartic     = rset.getString("cod_partic");

		//descripción de documento
		if (tipoDocumento!=null)
		{
			if (tipoDocumento.trim().length()>0)
			{
				dboTmDocIden.clearAll();
				dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
				dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
				dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO, "1");
				if (dboTmDocIden.find())
				{
					partida.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
				}
			}
		}

		//descripción del tipo de participación
		dboParticLibro.clearAll();
		dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
		dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
		dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
		dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
		if (dboParticLibro.find())
		{
			partida.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
		}

		listaResultado.add(partida);

	}
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 12-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice de personal natural
*/
public static ArrayList buscarPersonaNatural(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String apellidoPaterno;
	String apellidoMaterno;
	String nombre;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
	DboParticLibro dboParticLibro = new DboParticLibro(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	apellidoPaterno = criterioBean.getApellidoParterno().trim();
	apellidoMaterno = criterioBean.getApellidoMaterno().trim();
	nombre = criterioBean.getNombre().trim();
	
	StringBuffer query = new StringBuffer();
	StringBuffer cadenaPart = new StringBuffer();
	
	query.append("SELECT PARTIDA.ESTADO AS ESTADO,  PARTIDA.REFNUM_PART, ");  
	query.append("PARTIDA.COD_LIBRO,PARTIDA.NUM_PARTIDA, ");
	query.append("REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, ");
	query.append("PRTC_NAT.APE_PAT, PRTC_NAT.APE_MAT,PRTC_NAT.NOMBRES, ");
	query.append("PRTC_NAT.NU_DOC_IDEN, PRTC_NAT.TI_DOC_IDEN, IND_PRTC.COD_PARTIC, ");   
	query.append("IND_PRTC.ESTADO AS ESTADOPARTIC ");
	query.append("FROM PRTC_NAT, IND_PRTC, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO , "); 
	query.append("GRUPO_LIBRO_AREA GLA, GRUPO_LIBRO_AREA_DET GLAD "); 
	query.append("WHERE  PRTC_NAT.CUR_PRTC = IND_PRTC.CUR_PRTC "); 
	query.append("AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART "); 
	query.append("AND PARTIDA.OFIC_REG_ID = PRTC_NAT.OFIC_REG_ID ");   
	query.append("AND PARTIDA.REG_PUB_ID  = PRTC_NAT.REG_PUB_ID "); 
	query.append("AND PARTIDA.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID "); 
	query.append("AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID "); 
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");  
	query.append("AND APE_PAT LIKE '").append(apellidoPaterno).append("%' ");  
	query.append("AND PARTIDA.ESTADO != '2' ");  
	
	if (apellidoMaterno.length()>0)
	{
		query.append("AND APE_MAT LIKE '").append(apellidoMaterno).append("%' ");
	}
	if (nombre.length()>0)
	{
		query.append("AND NOMBRES LIKE '").append(nombre).append("%' ");
	}
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");  
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");   
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA ='").append(criterioBean.getAreaRegistral()).append("' "); 
	query.append("AND IND_PRTC.ESTADO = '1' ");
	query.append("ORDER BY PRTC_NAT.APE_PAT, PRTC_NAT.APE_MAT, PRTC_NAT.NOMBRES, IND_PRTC.ESTADO DESC, PARTIDA.REG_PUB_ID, PARTIDA.OFIC_REG_ID, PARTIDA.NUM_PARTIDA ");  
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	String antRefNumPart = "";
	String antRegPubDescripcion = "";
	String antnombre = "";
	String antEstadoAct = "";
	boolean nuevo = true;
	
	PartidaBean partida = new PartidaBean();
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		String refNumPart = rset.getString("refnum_part");
		String oficRegDesc = rset.getString("nombre");
		String codLibro   = rset.getString("cod_libro");
		String estadoAct = "";
		//if (rset.getString("estadoPartic").startsWith("1"))
		if ((rset.getString("estadoPartic")!=null)&&(rset.getString("estadoPartic").startsWith("1")))
		{
			estadoAct = "Activo";
		}
		else
		{
			estadoAct = "Inactivo";
		}

		sb.delete(0,sb.length());
		
		String ape_mat = rset.getString("ape_mat")==null?"":rset.getString("ape_mat").trim();
		String nombres = rset.getString("nombres")==null?"":rset.getString("nombres").trim();
		sb.append(rset.getString("ape_pat")==null?"":rset.getString("ape_pat").trim()).append(" ");
		sb.append(ape_mat).append(", ");
		sb.append(nombres);
		String nombreAct = sb.toString();

		if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)) && (antnombre.equals(nombreAct)))
		{
			nuevo = false;
		}
		else
		{
			if(!antRefNumPart.equals(""))
			{
				partida.setParticipanteDescripcion(cadenaPart.toString());

			}

			antRefNumPart = refNumPart;
			antRegPubDescripcion = oficRegDesc;
			antnombre = nombreAct;
			antEstadoAct = estadoAct;

			cadenaPart.delete(0,cadenaPart.length());
			cadenaPart.append(nombreAct);
			nuevo = true;
		}
		if(nuevo)
		{
			partida.setRefNumPart(refNumPart);
			partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
			partida.setNumPartida(rset.getString("num_partida"));
			partida.setRegPubDescripcion(rset.getString("siglas"));
			partida.setOficRegDescripcion(oficRegDesc);
			partida.setEstado(rset.getString("estado"));
			partida.setEstadoInd(estadoAct);

			//ficha
			dboFicha.clearAll();
			sb.delete(0, sb.length());
			sb.append(dboFicha.CAMPO_FICHA).append("|");
			sb.append(dboFicha.CAMPO_FICHA_BIS);
			dboFicha.setFieldsToRetrieve(sb.toString());
			dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
			if (dboFicha.find())
			{
				partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
				String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
				int nbis = 0;
				try {
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException n)
				{
					nbis =0;
				}
				if (nbis>=1)
				{
					partida.setFichaId(partida.getFichaId() + " BIS");
				}
			}
			//obtener tomo y foja
			dboTomoFolio.clearAll();
			sb.delete(0, sb.length());
			sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
			sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
			sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
			sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
			dboTomoFolio.setFieldsToRetrieve(sb.toString());
			dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			if (dboTomoFolio.find())
			{
				partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

				if (bist.trim().length() > 0)
				{
					partida.setTomoId(partida.getTomoId() + "-" + bist);
				}

				if (bisf.trim().length() > 0)
				{
					partida.setFojaId(partida.getFojaId() + "-" + bisf);
				}

				if (partida.getTomoId().length()>0)
				{
					if (partida.getTomoId().startsWith("9"))
						{
							String ntomo = partida.getTomoId().substring(1);
							partida.setTomoId(ntomo);
						}
				}
			}
			//descripcion libro
			dboTmLibro.clearAll();
			dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
			dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
			if (dboTmLibro.find())
			{
				partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
			}


			//participante y su número de documento
			String tipoDocumento="";
			String codPartic="";

			String nuDocIden = rset.getString("nu_doc_iden");
			if ((nuDocIden==null) || (nuDocIden.trim().length()==0))
			{
				partida.setParticipanteNumeroDocumento("&nbsp;");
			}
			else
			{
				partida.setParticipanteNumeroDocumento(nuDocIden);
			}


			tipoDocumento = rset.getString("ti_doc_iden");
			codPartic     = rset.getString("cod_partic");

			//descripción de documento
			if (tipoDocumento!=null)
			{
				if (tipoDocumento.trim().length()>0)
				{
					dboTmDocIden.clearAll();
					dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO, "1");
					if (dboTmDocIden.find())
					{
						partida.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
					}
				}
			}

			//descripción del tipo de participación
			dboParticLibro.clearAll();
			dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
			dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
				if (dboParticLibro.find())
				{
					partida.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
				}


		}
		listaResultado.add(partida);
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 12-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice por chasis del vehiculo
*/
public static ArrayList buscarXNombreVehicularChasis(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboParticLibro dboParticLibro = new DboParticLibro(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	StringBuffer query = new StringBuffer();
	StringBuffer cadenaPart = new StringBuffer();
	
	query.append("SELECT PARTIDA.REG_PUB_ID AS REG_PUB_ID , PARTIDA.REFNUM_PART, ");  
	query.append("PARTIDA.COD_LIBRO,  PARTIDA.ESTADO, "); 
	query.append("PARTIDA.OFIC_REG_ID  AS OFIC_REG_ID, PARTIDA.NUM_PARTIDA AS NUM_PARTIDA, "); 
	query.append("REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, ");  
	query.append("VEHICULO.NUM_PLACA , VEHICULO.FG_BAJA , "); 
	query.append("VEHICULO.NUM_MOTOR AS NUM_MOTOR, VEHICULO.NUM_SERIE, ");  
	query.append("VEHICULO.COD_MARCA, VEHICULO.COD_MODELO, ");  
	query.append("TM_MODELO_VEHI.DESCRIPCION AS DESCRIP1 , TM_MARCA_VEHI.DESCRIPCION AS DESCRIP2 "); 
	query.append("FROM   VEHICULO, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, TM_MARCA_VEHI, TM_MODELO_VEHI ");  
	query.append("WHERE  VEHICULO.NUM_SERIE LIKE '").append(criterioBean.getChasis()).append("%' "); 
	query.append("AND VEHICULO.FG_BAJA = '0' ");  
	query.append("AND PARTIDA.REFNUM_PART = VEHICULO.REFNUM_PART ");  
	query.append("AND VEHICULO.FG_BAJA = '0' "); 
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("AND AREA_REG_ID = '").append(areaRegistral).append("' "); 
	query.append("AND PARTIDA.ESTADO != '2' ");  
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");  
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");  
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");  
	query.append("AND TM_MARCA_VEHI.COD_MARCA = VEHICULO.COD_MARCA ");  
	query.append("AND TM_MODELO_VEHI.COD_MODELO = VEHICULO.COD_MODELO ");  
	query.append("ORDER BY PARTIDA.REG_PUB_ID, PARTIDA.OFIC_REG_ID, PARTIDA.NUM_PARTIDA DESC, VEHICULO.NUM_SERIE ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	String antRefNumPart = "";
	String antRegPubDescripcion = "";
	String antnombre = "";
	
	boolean nuevo = true;
	
	PartidaBean partida = new PartidaBean();
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		String refNumPart = rset.getString("refnum_part");
		String oficRegDesc = rset.getString("nombre");
		String codLibro   = rset.getString("cod_libro");

		String numerChasis = rset.getString("NUM_SERIE").trim();/////by leo

		if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)))
		{
			nuevo = false;
			if(!antnombre.equals(numerChasis))
			{
				cadenaPart.append(" ; ").append(numerChasis);
				antnombre = numerChasis;
			}

		}
		else
		{
			if(!antRefNumPart.equals(""))
			{
				partida.setParticipanteDescripcion(cadenaPart.toString());
			}

			antRefNumPart = refNumPart;
			antRegPubDescripcion = oficRegDesc;
			antnombre = numerChasis;
			//antEstadoAct = estadoAct;

			cadenaPart.delete(0,cadenaPart.length());
			cadenaPart.append(numerChasis);
			nuevo = true;
		}
		if(nuevo)
		{

			partida.setRefNumPart(refNumPart);
			partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
			partida.setNumPartida(rset.getString("num_partida"));
			partida.setRegPubDescripcion(rset.getString("siglas"));
			partida.setOficRegDescripcion(oficRegDesc);

			partida.setRegPubId(rset.getString("reg_pub_id"));
			partida.setOficRegId(rset.getString("ofic_reg_id"));

			partida.setNumeroPlaca(rset.getString("num_placa"));
			partida.setNumeroMotor(rset.getString("NUM_MOTOR"));
			partida.setNumeroSerie(rset.getString("NUM_SERIE"));

			partida.setMarca(rset.getString("descrip2"));
			partida.setModelo(rset.getString("descrip1"));

			partida.setEstado(rset.getString("estado"));

			String Baja = rset.getString("fg_baja");
			if(Baja.equals("0"))
			{
				partida.setBaja("En circulación");
			}
			else
			{
				partida.setBaja("Fuera de circulación");
			}

			dboFicha.clearAll();
			sb.delete(0, sb.length());
			sb.append(dboFicha.CAMPO_FICHA).append("|");
			sb.append(dboFicha.CAMPO_FICHA_BIS);
			dboFicha.setFieldsToRetrieve(sb.toString());
			dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
			if (dboFicha.find())
			{
				partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
				String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
				int nbis = 0;
				try {
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException n)
				{
					nbis =0;
				}
				if (nbis>=1)
				{
					partida.setFichaId(partida.getFichaId() + " BIS");
				}
			}

			dboTomoFolio.clearAll();
			sb.delete(0, sb.length());
			sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
			sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
			sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
			sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
			dboTomoFolio.setFieldsToRetrieve(sb.toString());
			dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			if (dboTomoFolio.find())
			{
				partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

				if (bist.trim().length() > 0)
				{
					partida.setTomoId(partida.getTomoId() + "-" + bist);
				}

				if (bisf.trim().length() > 0)
				{
					partida.setFojaId(partida.getFojaId() + "-" + bisf);
				}

				if (partida.getTomoId().length()>0)
				{
					if (partida.getTomoId().startsWith("9"))
						{
							String ntomo = partida.getTomoId().substring(1);
							partida.setTomoId(ntomo);
						}
				}
			}

			//descripcion libro
			dboTmLibro.clearAll();
			dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
			dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
			if (dboTmLibro.find())
			{
				partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
			}

			//descripción del tipo de participación
			dboParticLibro.clearAll();
			dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
			//dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
			dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
				if (dboParticLibro.find())
				{
					partida.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
				}

			listaResultado.add(partida);
		}
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 12-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice por motor del vehiculo
*/
public static ArrayList buscarXNombreVehicularMotor(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboParticLibro dboParticLibro = new DboParticLibro(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	StringBuffer query = new StringBuffer();
	StringBuffer cadenaPart = new StringBuffer();
	
	query.append("SELECT PARTIDA.REG_PUB_ID AS REG_PUB_ID, PARTIDA.REFNUM_PART, ");  
	query.append("PARTIDA.COD_LIBRO,  PARTIDA.ESTADO, ");  
	query.append("PARTIDA.OFIC_REG_ID  AS OFIC_REG_ID,PARTIDA.NUM_PARTIDA AS NUM_PARTIDA, ");
	query.append("REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, ");  
	query.append("VEHICULO.NUM_PLACA ,  VEHICULO.FG_BAJA , "); 
	query.append("VEHICULO.NUM_MOTOR AS NUM_MOTOR, VEHICULO.NUM_SERIE, ");  
	query.append("VEHICULO.COD_MARCA, VEHICULO.COD_MODELO, ");  
	query.append("TM_MODELO_VEHI.DESCRIPCION AS DESCRIP1  , TM_MARCA_VEHI.DESCRIPCION AS DESCRIP2 "); 
	query.append("FROM    VEHICULO, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, TM_MARCA_VEHI, TM_MODELO_VEHI ");  
	query.append("WHERE   VEHICULO.NUM_MOTOR LIKE '").append(criterioBean.getNumeroMotor()).append("%' "); 
	query.append("AND VEHICULO.FG_BAJA = '0' ");  
	query.append("AND PARTIDA.REFNUM_PART = VEHICULO.REFNUM_PART ");  
	query.append("AND VEHICULO.FG_BAJA = '0' ");
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("AND AREA_REG_ID = '").append(areaRegistral).append("' ");
	query.append("AND PARTIDA.ESTADO != '2' ");  
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");  
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");  
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");  
	query.append("AND TM_MARCA_VEHI.COD_MARCA = VEHICULO.COD_MARCA ");  
	query.append("AND TM_MODELO_VEHI.COD_MODELO = VEHICULO.COD_MODELO ");  
	query.append("ORDER BY PARTIDA.REG_PUB_ID, PARTIDA.OFIC_REG_ID, PARTIDA.NUM_PARTIDA DESC, VEHICULO.NUM_MOTOR ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	String antRefNumPart = "";
	String antRegPubDescripcion = "";
	String antnombre = "";
	
	boolean nuevo = true;
	
	PartidaBean partida = new PartidaBean();
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		String refNumPart = rset.getString("refnum_part");
		String oficRegDesc = rset.getString("nombre");
		String codLibro   = rset.getString("cod_libro");

		String numerMotor = rset.getString("NUM_MOTOR").trim();/////by leo


		if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)))
		{
			nuevo = false;
			if(!antnombre.equals(numerMotor))
			{
				cadenaPart.append(" ; ").append(numerMotor);
				antnombre = numerMotor;
			}
		}
		else
		{
			if(!antRefNumPart.equals(""))
			{
				partida.setParticipanteDescripcion(cadenaPart.toString());
			}
			antRefNumPart = refNumPart;
			antRegPubDescripcion = oficRegDesc;
			antnombre = numerMotor;

			cadenaPart.delete(0,cadenaPart.length());
			cadenaPart.append(numerMotor);
			nuevo = true;
		}
		if(nuevo)
		{

			partida.setRefNumPart(refNumPart);
			partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
			partida.setNumPartida(rset.getString("num_partida"));
			partida.setRegPubDescripcion(rset.getString("siglas"));
			partida.setOficRegDescripcion(oficRegDesc);
			partida.setEstado(rset.getString("estado"));

			partida.setRegPubId(rset.getString("reg_pub_id"));
			partida.setOficRegId(rset.getString("ofic_reg_id"));
	
			partida.setNumeroPlaca(rset.getString("num_placa"));
			partida.setNumeroMotor(rset.getString("NUM_MOTOR"));
			partida.setNumeroSerie(rset.getString("NUM_SERIE"));

			partida.setMarca(rset.getString("descrip2"));
			partida.setModelo(rset.getString("descrip1"));


			String Baja = rset.getString("fg_baja");
			if(Baja.equals("0"))
			{
				partida.setBaja("En circulación");
			}
			else
			{
				partida.setBaja("Fuera de circulación");
			}

			//ficha
			dboFicha.clearAll();
			sb.delete(0, sb.length());
			sb.append(dboFicha.CAMPO_FICHA).append("|");
			sb.append(dboFicha.CAMPO_FICHA_BIS);
			dboFicha.setFieldsToRetrieve(sb.toString());
			dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
			if (dboFicha.find())
			{
				partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
				String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
				int nbis = 0;
				try{
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException n)
				{
					nbis =0;
				}
				if (nbis>=1)
					partida.setFichaId(partida.getFichaId() + " BIS");
			}
			//obtener tomo y foja
			dboTomoFolio.clearAll();
			sb.delete(0, sb.length());
			sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
			sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
			sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
			sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
			dboTomoFolio.setFieldsToRetrieve(sb.toString());
			dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			if (dboTomoFolio.find())
			{
				partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

				if (bist.trim().length() > 0)
				{
					partida.setTomoId(partida.getTomoId() + "-" + bist);
				}
				
				if (bisf.trim().length() > 0)
				{
					partida.setFojaId(partida.getFojaId() + "-" + bisf);
				}

				if (partida.getTomoId().length()>0)
					{
						if (partida.getTomoId().startsWith("9"))
							{
								String ntomo = partida.getTomoId().substring(1);
								partida.setTomoId(ntomo);
							}
					}
			}

			//descripcion libro
			dboTmLibro.clearAll();
			dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
			dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
			if (dboTmLibro.find())
			{
				partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
			}


			//descripción del tipo de participación
			dboParticLibro.clearAll();
			dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
			dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
				if (dboParticLibro.find())
				{
					partida.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
				}

			listaResultado.add(partida);
		}
	}
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 12-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice por razon social del vehiculo
*/
public static ArrayList buscarXNombreVehicularJuri(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboParticLibro dboParticLibro = new DboParticLibro(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	StringBuffer query = new StringBuffer();
	StringBuffer cadenaPart = new StringBuffer();
	
	query.append("SELECT  PARTIDA.REFNUM_PART, PARTIDA.COD_LIBRO, ");     
	query.append("PARTIDA.NUM_PARTIDA, PARTIDA.ESTADO, "); 
	query.append("PARTIDA.REG_PUB_ID, PARTIDA.OFIC_REG_ID, ");  
	query.append("REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, ");  
	query.append("PRTC_JUR.RAZON_SOCIAL, PRTC_JUR.NU_DOC, PRTC_JUR.TI_DOC, ");
	query.append("IND_PRTC.COD_PARTIC,IND_PRTC.ESTADO AS ESTADOIND, ");
	query.append("VEHICULO.NUM_PLACA, VEHICULO.FG_BAJA ");
	query.append("FROM    PRTC_JUR, IND_PRTC, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO ,VEHICULO "); 
	query.append("WHERE   RAZON_SOCIAL LIKE '").append(criterioBean.getRazonSocial()).append("%' ") ; 
	query.append("AND IND_PRTC.CUR_PRTC = PRTC_JUR.CUR_PRTC ");  
	query.append("AND IND_PRTC.TIPO_PERS = 'J' ");  
	query.append("AND IND_PRTC.COD_PARTIC = "+Constantes.PROPIETARIO_VEHI);
	query.append("AND IND_PRTC.ESTADO = '1' "); 
	query.append("AND PARTIDA.REFNUM_PART = IND_PRTC.REFNUM_PART ");  
	query.append("AND PARTIDA.ESTADO != '2' ");
	if (criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID = '").append(criterioBean.getSedesElegidas()[0]).append("'");
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString());
	}
	query.append("AND AREA_REG_ID = '").append(areaRegistral).append("' "); 
	query.append("AND PARTIDA.REG_PUB_ID  = PRTC_JUR.REG_PUB_ID ");  
	query.append("AND PARTIDA.OFIC_REG_ID = PRTC_JUR.OFIC_REG_ID ");   
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");  
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");  
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");  
	query.append("AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");  
	query.append("AND VEHICULO.FG_BAJA = '0' ");  
	query.append("ORDER BY PARTIDA.REG_PUB_ID, PARTIDA.OFIC_REG_ID, PARTIDA.NUM_PARTIDA, IND_PRTC.ESTADO DESC, PRTC_JUR.RAZON_SOCIAL ");

	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	String antRefNumPart = "";
	String antRegPubDescripcion = "";
	String antnombre = "";
	String antEstadoAct = "";
	
	boolean nuevo = true;
	
	PartidaBean partida = new PartidaBean();
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		
		String refNumPart = rset.getString("REFNUM_PART");
		String oficRegDesc = rset.getString("NOMBRE");
		String codLibro   = rset.getString("COD_LIBRO");
		String estadoAct = "";
		if (rset.getString("ESTADOIND").startsWith("1"))
		{
			estadoAct = "Activo";
		}
		else
		{
			estadoAct = "Inactivo";
		}

		String nombRazonSoc = rset.getString("RAZON_SOCIAL").trim();


		if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)))
		{
			nuevo = false;

			if(!antnombre.equals(nombRazonSoc))
			{
				cadenaPart.append(" ; ").append(nombRazonSoc);
				antnombre = nombRazonSoc;
			}

		}
		else
		{
			if(!antRefNumPart.equals(""))
			{
				partida.setParticipanteDescripcion(cadenaPart.toString());
			}

			antRefNumPart = refNumPart;
			antRegPubDescripcion = oficRegDesc;
			antnombre = nombRazonSoc;
			antEstadoAct = estadoAct;

			cadenaPart.delete(0,cadenaPart.length());
			cadenaPart.append(nombRazonSoc);
			nuevo = true;
		}

		if(nuevo)
		{

			partida.setRefNumPart(refNumPart);
			partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
			partida.setNumPartida(rset.getString("NUM_PARTIDA"));
			partida.setRegPubDescripcion(rset.getString("SIGLAS"));
			partida.setOficRegDescripcion(oficRegDesc);
			partida.setEstadoInd(estadoAct);
			
			partida.setEstado(rset.getString("ESTADO"));

			partida.setRegPubId(rset.getString("REG_PUB_ID"));
			partida.setOficRegId(rset.getString("OFIC_REG_ID"));

			partida.setNumeroPlaca(rset.getString("NUM_PLACA"));

			String Baja = rset.getString("FG_BAJA");
			
			if(Baja.equals("0"))
			{
				partida.setBaja("En circulación");
			}
			else
			{
				partida.setBaja("Fuera de circulación");
			}

			dboFicha.clearAll();
			sb.delete(0, sb.length());
			sb.append(dboFicha.CAMPO_FICHA).append("|");
			sb.append(dboFicha.CAMPO_FICHA_BIS);
			dboFicha.setFieldsToRetrieve(sb.toString());
			dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
			if (dboFicha.find())
			{
				partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
				String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
				int nbis = 0;
				try{
					nbis = Integer.parseInt(bis);
				}
				catch (NumberFormatException n)
				{
					nbis =0;
				}
				if (nbis>=1)
				{
					partida.setFichaId(partida.getFichaId() + " BIS");
				}
			}

			//obtener tomo y foja
			dboTomoFolio.clearAll();
			sb.delete(0, sb.length());
			sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
			sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
			sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
			sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
			dboTomoFolio.setFieldsToRetrieve(sb.toString());
			dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			if (dboTomoFolio.find())
			{
				partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
				partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
				
				String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
				String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

				if (bist.trim().length() > 0)
				{
					partida.setTomoId(partida.getTomoId() + "-" + bist);
				}

				if (bisf.trim().length() > 0)
				{
					partida.setFojaId(partida.getFojaId() + "-" + bisf);
				}

				if (partida.getTomoId().length()>0)
				{
					if (partida.getTomoId().startsWith("9"))
					{
						String ntomo = partida.getTomoId().substring(1);
						partida.setTomoId(ntomo);
					}
				}
			}

			//descripcion libro
			dboTmLibro.clearAll();
			dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
			dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
			if (dboTmLibro.find())
			{
				partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
			}

			//participante y su número de documento
			String tipoDocumento="";
			String codPartic="";
			codPartic     = rset.getString("COD_PARTIC");

			//descripción del tipo de participación
			dboParticLibro.clearAll();
			dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
			dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
				if (dboParticLibro.find())
				{
					partida.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
				}


		}
		
		listaResultado.add(partida);
	}
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 12-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda de indice por nombre de vehiculo
*/
public static ArrayList buscarXNombreVehicular(String criterio, Connection conn) throws Throwable
{
	String areaRegistral;
	String apellidoPaterno;
	String apellidoMaterno;
	String nombre;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	DboTmDocIden dboTmDocIden = new DboTmDocIden(dconn);
	DboParticLibro dboParticLibro = new DboParticLibro(dconn);
	DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(dconn);
	
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	apellidoPaterno = criterioBean.getApellidoParterno().trim();
	apellidoMaterno = criterioBean.getApellidoMaterno().trim();
	nombre = criterioBean.getNombre().trim();
	
	StringBuffer query = new StringBuffer();
	StringBuffer cadenaPart = new StringBuffer();
	
	query.append("SELECT PARTIDA.REFNUM_PART,PARTIDA.COD_LIBRO, ");
	query.append("PARTIDA.NUM_PARTIDA,PARTIDA.REG_PUB_ID, ");
	query.append("PARTIDA.OFIC_REG_ID,PARTIDA.ESTADO, ");   
	query.append("REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, "); 
	query.append("PRTC_NAT.APE_PAT, PRTC_NAT.APE_MAT, ");     
	query.append("PRTC_NAT.NOMBRES,PRTC_NAT.NU_DOC_IDEN, "); 
	query.append("PRTC_NAT.TI_DOC_IDEN, IND_PRTC.COD_PARTIC, ");   
	query.append("IND_PRTC.ESTADO AS ESTADOIND, VEHICULO.NUM_PLACA, "); 
	query.append("VEHICULO.FG_BAJA "); 
	query.append("FROM PRTC_NAT, IND_PRTC, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO ,VEHICULO "); 
	query.append("WHERE APE_PAT LIKE '").append(apellidoPaterno).append("%'");
	if (apellidoMaterno.length()>0)
	{
		query.append("AND APE_MAT LIKE '").append(apellidoMaterno).append("%'");
	}
	if (nombre.length()>0)
	{
		query.append("AND NOMBRES LIKE '").append(nombre).append("%'"); 
	}
	query.append("AND IND_PRTC.CUR_PRTC = PRTC_NAT.CUR_PRTC  AND IND_PRTC.TIPO_PERS = 'N'  AND IND_PRTC.ESTADO = '1' "); 
	query.append("AND PARTIDA.REFNUM_PART = IND_PRTC.REFNUM_PART  AND PARTIDA.ESTADO != '2' ");  
	if(criterioBean.getSedesElegidas().length==1)
	{
		query.append("AND PARTIDA.REG_PUB_ID='").append(criterioBean.getSedesElegidas()[0]).append("' "); 
	}
	if (criterioBean.getSedesElegidas().length>=2 && criterioBean.getSedesElegidas().length<=12)
	{
		query.append("AND PARTIDA.REG_PUB_ID IN ").append(criterioBean.getSedesSQLString()); 
	}
	query.append("AND AREA_REG_ID ='").append(areaRegistral).append("' "); 
	query.append("AND PARTIDA.REG_PUB_ID  = PRTC_NAT.REG_PUB_ID  AND PARTIDA.OFIC_REG_ID = PRTC_NAT.OFIC_REG_ID ");   
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID  AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");  
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID  AND VEHICULO.REFNUM_PART = PARTIDA.REFNUM_PART ");  
	query.append("AND IND_PRTC.COD_PARTIC ='").append(Constantes.PROPIETARIO_VEHI).append("' ");
	query.append("AND VEHICULO.FG_BAJA = '0' ");  
	query.append("ORDER BY PARTIDA.REG_PUB_ID, PARTIDA.OFIC_REG_ID, PARTIDA.NUM_PARTIDA, IND_PRTC.ESTADO DESC, PRTC_NAT.APE_PAT, PRTC_NAT.APE_MAT, PRTC_NAT.NOMBRES ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	String antRefNumPart = "";
	String antRegPubDescripcion = "";
	String antnombre = "";
	String antEstadoAct = "";
	boolean nuevo = true;
	
	PartidaBean partida = new PartidaBean();
	
	dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,areaRegistral);
	dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
	String descripcionAreaRegistral="";
	if (dboTmAreaRegistral.find())
	{
		descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);
	}
	
	while(rset.next())
	{
		
		
		String refNumPart = rset.getString("REFNUM_PART");
		String oficRegDesc = rset.getString("NOMBRE");
		String codLibro   = rset.getString("COD_LIBRO");
		String estadoAct = "";
		if (rset.getString("ESTADOIND").startsWith("1"))
		{
			estadoAct = "Activo";
		}
		else
		{
			estadoAct = "Inactivo";
		}

		sb.delete(0,sb.length());
		String ape_mat = rset.getString("APE_MAT").trim();
		if (ape_mat==null)
		{
			ape_mat="";
		}
		String nombres = rset.getString("NOMBRES").trim();
		if (nombres==null)
		{
			nombres="";
		}
		sb.append(rset.getString("APE_PAT").trim()).append(" ");
		sb.append(ape_mat).append(", ");
		sb.append(nombres);
		String nombreAct = sb.toString();

		if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)))
		{
			nuevo = false;
			if(!antnombre.equals(nombreAct))
			{
				cadenaPart.append(" ; ").append(nombreAct);
				antnombre = nombreAct;
			}

		}
		else
		{

			if(!antRefNumPart.equals(""))
			{
				partida.setParticipanteDescripcion(cadenaPart.toString());
			}

			antRefNumPart = refNumPart;
			antRegPubDescripcion = oficRegDesc;
			antnombre = nombreAct;
			antEstadoAct = estadoAct;

			cadenaPart.delete(0,cadenaPart.length());
			cadenaPart.append(nombreAct);
			nuevo = true;
		}

		if(nuevo)
		{

			partida.setRefNumPart(refNumPart);
			partida.setAreaRegistralDescripcion(descripcionAreaRegistral);
			partida.setNumPartida(rset.getString("num_partida"));
			partida.setRegPubDescripcion(rset.getString("siglas"));
			partida.setOficRegDescripcion(oficRegDesc);
			partida.setEstadoInd(estadoAct);
			//hphp
			partida.setEstado(rset.getString("estado"));

			/**kuma 2003/09/02**/
			partida.setRegPubId(rset.getString("reg_pub_id"));
			partida.setOficRegId(rset.getString("ofic_reg_id"));
			/**fin kuma**/

			//*vehicular*//añadido por Leo

			partida.setNumeroPlaca(rset.getString("num_placa"));

			String Baja = rset.getString("fg_baja");
			if(Baja.equals("0"))
			{
				partida.setBaja("En circulación");
			}
			else
			{
				partida.setBaja("Fuera de circulación");
			}

			//**//


			//ficha
			dboFicha.clearAll();
			sb.delete(0, sb.length());
			sb.append(dboFicha.CAMPO_FICHA).append("|");
			sb.append(dboFicha.CAMPO_FICHA_BIS);
			dboFicha.setFieldsToRetrieve(sb.toString());
			dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
			if (dboFicha.find())
					{
						partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
						String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
						int nbis = 0;
						try {
							nbis = Integer.parseInt(bis);
						}
						catch (NumberFormatException n)
						{
							nbis =0;
						}
						if (nbis>=1)
						{
							partida.setFichaId(partida.getFichaId() + " BIS");
						}
					}

			//obtener tomo y foja
			dboTomoFolio.clearAll();
			sb.delete(0, sb.length());
			sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
			sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
			sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
			sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
			dboTomoFolio.setFieldsToRetrieve(sb.toString());
			dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			if (dboTomoFolio.find())
			{
						partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
						partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));


						String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
						String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);


						if (bist.trim().length() > 0)
						{
							partida.setTomoId(partida.getTomoId() + "-" + bist);
						}


						if (bisf.trim().length() > 0)
						{
							partida.setFojaId(partida.getFojaId() + "-" + bisf);
						}

						if (partida.getTomoId().length()>0)
						{
							if (partida.getTomoId().startsWith("9"))
							{
								String ntomo = partida.getTomoId().substring(1);
								partida.setTomoId(ntomo);
							}
						}
			}

			//descripcion libro
			dboTmLibro.clearAll();
			dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
			dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
			if (dboTmLibro.find())
			{
				partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
			}


			//participante y su número de documento
			String tipoDocumento="";
			String codPartic="";

			String nuDocIden = rset.getString("nu_doc_iden");
			if (nuDocIden==null || nuDocIden.trim().length()==0)
			{
				partida.setParticipanteNumeroDocumento("&nbsp;");
			}
			else
			{
				partida.setParticipanteNumeroDocumento(nuDocIden);
			}

			tipoDocumento = rset.getString("ti_doc_iden");
			codPartic     = rset.getString("cod_partic");

			//descripción de documento
			if (tipoDocumento!=null)
			{
				if (tipoDocumento.trim().length()>0)
				{
					dboTmDocIden.clearAll();
					dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
					dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO, "1");
					if (dboTmDocIden.find())
						partida.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
				}
			}


			//descripción del tipo de participación
			dboParticLibro.clearAll();
			dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
			dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
			dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
				if (dboParticLibro.find())
				{
					partida.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
				}

		}

		listaResultado.add(partida);
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 12-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda por tomo/folio
*/
public static ArrayList buscarXTomoFolio(String criterio, Connection conn) throws Throwable
{
	String [] oficina;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	oficina =  getEquivalenciaOficina(criterioBean.getZonaOficina());
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	
	StringBuffer query = new StringBuffer();
	StringBuffer nt = new StringBuffer();
	
	query.append("SELECT PARTIDA.ESTADO AS ESTADO, ");
	query.append("REGIS_PUBLICO.SIGLAS AS SIGLAS, ");
	query.append("OFIC_REGISTRAL.NOMBRE AS NOMBRE, ");
	query.append("PARTIDA.AREA_REG_ID AS AREA_REG_ID, ");
	query.append("PARTIDA.REFNUM_PART AS REFNUM_PART, ");
	query.append("PARTIDA.NUM_PARTIDA AS NUM_PARTIDA, ");
	query.append("PARTIDA.COD_LIBRO AS COD_LIBRO, ");
	query.append("PARTIDA.REG_PUB_ID AS REGPUBID, ");
	query.append("PARTIDA.OFIC_REG_ID AS OFICREGID ");
	query.append("FROM TOMO_FOLIO, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO, GRUPO_LIBRO_AREA GLA, GRUPO_LIBRO_AREA_DET GLAD, "); 
	query.append("TM_AREA_REGISTRAL AREA ");
	
	if(criterioBean.getTomo().startsWith("0"))
	{
		String otroNumTomo = new StringBuffer("9").append(criterioBean.getTomo().substring(1)).toString();
		nt.append(" in ('").append(criterioBean.getTomo()).append("','").append(otroNumTomo).append("')");
	}
	else
	{
		nt.append("='").append(criterioBean.getTomo()).append("'");
	}
	
	query.append("WHERE TOMO_FOLIO.REFNUM_PART = PARTIDA.REFNUM_PART ");
	query.append("AND  PARTIDA.REG_PUB_ID ='").append(oficina[0]).append("' ");
	query.append("AND PARTIDA.OFIC_REG_ID = '").append(oficina[1]).append("' ");
	query.append("AND PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA "); 
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA ='").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("AND PARTIDA.COD_LIBRO = '").append(criterioBean.getRegistro()).append("' ");
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("AND TOMO_FOLIO.NU_TOMO ").append(nt.toString());
	query.append("AND TOMO_FOLIO.NU_FOJA = '").append(criterioBean.getFolio()).append("' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");       
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	while(rset.next())
	{
		
		PartidaBean partida = new PartidaBean();
		partida.setRegPubDescripcion(rset.getString("SIGLAS"));
		partida.setOficRegDescripcion(rset.getString("NOMBRE"));
		partida.setNumPartida(rset.getString("NUM_PARTIDA"));
		String refNumPart = rset.getString("REFNUM_PART"); 
		String codLibro = rset.getString("COD_LIBRO");
		
		//Buscar ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART,refNumPart);
		if (dboFicha.find()) 
		{
			partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
			String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
			int nbis = 0;
			try{
				nbis = Integer.parseInt(bis);
			}
			catch (NumberFormatException n)
			{
				nbis =0;
			}
			if (nbis>=1)
			{
				partida.setFichaId(partida.getFichaId() + " BIS");
			}
		}
		// Obtener Tomo/Folio
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find()) 
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}

			if (bisf.trim().length() > 0)
			{
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
			}
							
			if (partida.getTomoId().length()>0)
			{
				if (partida.getTomoId().startsWith("9"))
					{
						String ntomo = partida.getTomoId().substring(1);
						partida.setTomoId(ntomo);
					}
			}
							
		}
		//Obtener Libro
		dboTmLibro.clearAll();
		dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
		dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
		if(dboTmLibro.find())
		{
			partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
		}
	
		
		listaResultado.add(partida);
		
	}
	
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 11-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda por partida
*/
public static ArrayList buscarXNroPartida(String criterio, Connection conn) throws Throwable
{
	String [] oficina;
	
	ArrayList listaResultado = new ArrayList();
	CriterioBean criterioBean = recuperarCriterio(criterio);
	StringBuffer sb = new StringBuffer();
	oficina =  getEquivalenciaOficina(criterioBean.getZonaOficina());
	
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	
	StringBuffer query = new StringBuffer();
		
	query.append("SELECT PARTIDA.ESTADO AS ESTADO, ");
	query.append("REGIS_PUBLICO.SIGLAS AS SIGLAS, ");
	query.append("OFIC_REGISTRAL.NOMBRE AS NOMBRE, ");
	query.append("PARTIDA.AREA_REG_ID AS AREA_REG_ID, ");
	query.append("PARTIDA.REFNUM_PART AS REFNUM_PART, "); 
	query.append("PARTIDA.NUM_PARTIDA AS NUM_PARTIDA, ");
	query.append("PARTIDA.COD_LIBRO AS COD_LIBRO, ");
	query.append("GLA.DESC_GRUPO_LIBRO_AREA  ");
	query.append("FROM REGIS_PUBLICO,OFIC_REGISTRAL,PARTIDA, GRUPO_LIBRO_AREA GLA, GRUPO_LIBRO_AREA_DET GLAD, TM_AREA_REGISTRAL AREA ");
	query.append("WHERE PARTIDA.NUM_PARTIDA = '").append(criterioBean.getPartida()).append("' ") ;
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("AND PARTIDA.REG_PUB_ID ='").append(oficina[0]).append("' ");
	query.append("AND PARTIDA.OFIC_REG_ID = '").append(oficina[1]).append("' ");
	query.append("AND PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA ='").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();
		partida.setRegPubDescripcion(rset.getString("SIGLAS"));
		partida.setOficRegDescripcion(rset.getString("NOMBRE"));
		partida.setNumPartida(rset.getString("NUM_PARTIDA"));
		partida.setAreaRegistralDescripcion(rset.getString("DESC_GRUPO_LIBRO_AREA"));
		String refNumPart = rset.getString("REFNUM_PART"); 
		String codLibro = rset.getString("COD_LIBRO");
		
		// Obtener Tomo/Folio
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find()) 
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));
			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);
			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}
			if (bisf.trim().length() > 0)
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
				if (partida.getTomoId().length()>0)
				{
					if (partida.getTomoId().startsWith("9"))
						{
							String ntomo = partida.getTomoId().substring(1);
							partida.setTomoId(ntomo);
						}
				}						
		}
		// Obtener Libro
		dboTmLibro.clearAll();
		dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
		dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
		if (dboTmLibro.find())
		{
			partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
		}
		
		//Obtener Ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART,refNumPart);
		if (dboFicha.find()) 
		{
			partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
			String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
			int nbis = 0;
			try{
				nbis = Integer.parseInt(bis);
			}
			catch (NumberFormatException n)
			{
				nbis =0;
			}
			if (nbis>=1)
			{
				partida.setFichaId(partida.getFichaId() + " BIS");
			}
		}
		
		
		listaResultado.add(partida);
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 11-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda por ficha
*/
public static ArrayList buscarXFicha(String criterio, Connection conn) throws Throwable
{
	String [] oficina;
		
	DBConnection dconn = new DBConnection(conn);
	DboTomoFolio dboTomoFolio = new DboTomoFolio(dconn);
	DboTmLibro dboTmLibro = new DboTmLibro(dconn);
	DboFicha dboFicha = new DboFicha(dconn);
	
	ArrayList listaResultado = new ArrayList();
	
	CriterioBean criterioBean = recuperarCriterio(criterio);
	
	oficina =  getEquivalenciaOficina(criterioBean.getZonaOficina());
	
	StringBuffer sb = new StringBuffer();
	StringBuffer query = new StringBuffer();
	
	query.append("SELECT PARTIDA.ESTADO AS ESTADO, ");
	query.append("REGIS_PUBLICO.SIGLAS AS SIGLAS, ");
	query.append("OFIC_REGISTRAL.NOMBRE AS NOMBRE, ");
	query.append("PARTIDA.AREA_REG_ID AS AREA_REG_ID, ");
	query.append("PARTIDA.REFNUM_PART AS REFNUM_PART, ");
	query.append("PARTIDA.NUM_PARTIDA AS NUM_PARTIDA, ");
	query.append("PARTIDA.COD_LIBRO AS COD_LIBRO, ");
	query.append("AREA.NOMBRE AS DESCRIPCION ");
	query.append("FROM FICHA,PARTIDA, REGIS_PUBLICO,OFIC_REGISTRAL,GRUPO_LIBRO_AREA_DET GLAD, GRUPO_LIBRO_AREA GLA, TM_AREA_REGISTRAL AREA "); 
	query.append("WHERE FICHA.FICHA = '").append(criterioBean.getFicha()).append("' ") ;
	query.append("AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
	query.append("AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
	query.append("AND FICHA.REFNUM_PART = PARTIDA.REFNUM_PART ");
	query.append("AND PARTIDA.REG_PUB_ID ='").append(oficina[0]).append("' ");
	query.append("AND PARTIDA.OFIC_REG_ID = '").append(oficina[1]).append("' ") ;
	query.append("AND PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
	query.append("AND PARTIDA.COD_LIBRO = GLAD.COD_LIBRO ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
	query.append("AND GLA.COD_GRUPO_LIBRO_AREA ='").append(criterioBean.getAreaRegistral()).append("' ");
	query.append("ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();
		partida.setRegPubDescripcion(rset.getString("SIGLAS"));
		partida.setOficRegDescripcion(rset.getString("NOMBRE"));
		partida.setNumPartida(rset.getString("NUM_PARTIDA"));
		partida.setAreaRegistralDescripcion(rset.getString("DESCRIPCION"));
		String refNumPart = rset.getString("REFNUM_PART"); 
		String codLibro = rset.getString("COD_LIBRO");
		
		//Obtener Libro
		dboTmLibro.clearAll();
		dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
		dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
		if (dboTmLibro.find())
		{
			partida.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));	
		}
		//Obtener Ficha
		dboFicha.clearAll();
		sb.delete(0, sb.length());
		sb.append(dboFicha.CAMPO_FICHA).append("|");
		sb.append(dboFicha.CAMPO_FICHA_BIS);
		dboFicha.setFieldsToRetrieve(sb.toString());
		dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
		if (dboFicha.find()) 
		{
			partida.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
			String bis = dboFicha.getField(dboFicha.CAMPO_FICHA_BIS);
			int nbis = 0;
			try {
				nbis = Integer.parseInt(bis);
			}
			catch (NumberFormatException n)
			{
				nbis =0;
			}
			if (nbis>=1)
			{
				partida.setFichaId(partida.getFichaId() + " BIS");
			}
		}
		
		//Obtener Tomo/Folio
		dboTomoFolio.clearAll();
		sb.delete(0, sb.length());
		sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
		sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
		sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
		sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
		dboTomoFolio.setFieldsToRetrieve(sb.toString());
		dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
		if (dboTomoFolio.find()) 
		{
			partida.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
			partida.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

			String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
			String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

			if (bist.trim().length() > 0)
			{
				partida.setTomoId(partida.getTomoId() + "-" + bist);
			}
			if (bisf.trim().length() > 0)
			{
				partida.setFojaId(partida.getFojaId() + "-" + bisf);
			}				
			if (partida.getTomoId().length()>0)
				{
					if (partida.getTomoId().startsWith("9"))
						{
							String ntomo = partida.getTomoId().substring(1);
							partida.setTomoId(ntomo);
						}
				}						
		}
		
		
		listaResultado.add(partida);
	}
	
	return listaResultado;
}

/*
*  @autor: mgarate
*  @fecha: 11-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda realizado por el cliente
*/
public static ArrayList buscarVehiculoXPartida(String criterio, Connection conn) throws Throwable
{
	String [] oficina;
	String areaRegistral;
	
	ArrayList listaResultado = new ArrayList();
	
	CriterioBean criterioBean = recuperarCriterio(criterio);
	
	oficina =  getEquivalenciaOficina(criterioBean.getZonaOficina());
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	StringBuffer query = new StringBuffer();
	
	query.append("SELECT VH.NUM_PLACA,VH.FG_BAJA, PA.ESTADO, PA.NUM_PARTIDA, PA.AREA_REG_ID, PA.COD_LIBRO, REGP.SIGLAS AS SIGLAS, ");
	query.append("OFIR.NOMBRE AS NOMBRE, PA.REFNUM_PART AS REFNUM_PART, AREA.NOMBRE AS DESCRIPCIONAREAREGISTRAL ");
	query.append("FROM PARTIDA PA, VEHICULO VH, REGIS_PUBLICO REGP, OFIC_REGISTRAL OFIR, TM_AREA_REGISTRAL AREA ");
	query.append("WHERE  PA.REG_PUB_ID = '").append(oficina[0]).append("' ");
	query.append("AND PA.OFIC_REG_ID = '").append(oficina[1]).append("' ");
	query.append("AND PA.AREA_REG_ID = '").append(areaRegistral).append("' "); 
	query.append("AND PA.ESTADO != '2' ");  
	query.append("AND PA.NUM_PARTIDA = '").append(criterioBean.getPartida()).append("' "); 
	query.append("AND PA.REFNUM_PART = VH.REFNUM_PART "); 
	query.append("AND VH.FG_BAJA = '0' ");
	query.append("AND REGP.REG_PUB_ID = OFIR.REG_PUB_ID "); 
	query.append("AND OFIR.OFIC_REG_ID = PA.OFIC_REG_ID ");
	query.append("AND OFIR.REG_PUB_ID = PA.REG_PUB_ID ");
	query.append("AND AREA.AREA_REG_ID = PA.AREA_REG_ID ");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();
		partida.setRefNumPart(rset.getString("REFNUM_PART"));
		partida.setAreaRegistralDescripcion(rset.getString("DESCRIPCIONAREAREGISTRAL"));
		partida.setNumPartida(rset.getString("NUM_PARTIDA"));
		partida.setRegPubDescripcion(rset.getString("SIGLAS"));
		partida.setOficRegDescripcion(rset.getString("NOMBRE"));
		partida.setRegPubId(oficina[0]);
		partida.setOficRegId(oficina[1]);
		partida.setEstado(rset.getString("ESTADO"));
		partida.setAreaRegistralId(rset.getString("AREA_REG_ID"));
		partida.setCodLibro(rset.getString("COD_LIBRO"));
		partida.setNumeroPlaca(rset.getString("NUM_PLACA"));
		
		listaResultado.add(partida);
		
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 08-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el resultado de busqueda realizado por el cliente
*/
public static ArrayList buscarVehiculoXPlaca(String criterio, Connection conn) throws Throwable
{
	String [] oficina ;
	String areaRegistral;
	
	ArrayList listaResultado = new ArrayList();
	
	CriterioBean criterioBean = recuperarCriterio(criterio);
	
	oficina =  getEquivalenciaOficina(criterioBean.getZonaOficina());
	areaRegistral = recuperaAreaRegistral(criterioBean.getAreaRegistral(), conn);
	
	StringBuffer query = new StringBuffer();
	
	query.append("SELECT VH.NUM_PLACA, VH.FG_BAJA, PA.ESTADO, PA.NUM_PARTIDA, PA.AREA_REG_ID, PA.COD_LIBRO, ");
	query.append("REGP.SIGLAS AS SIGLAS, OFIR.NOMBRE AS NOMBRE, PA.REFNUM_PART AS REFNUM_PART, AREA.NOMBRE AS DESCRIPCIONAREAREGISTRAL "); 
	query.append("FROM VEHICULO VH, PARTIDA PA, REGIS_PUBLICO REGP, OFIC_REGISTRAL OFIR, TM_AREA_REGISTRAL AREA ");
	query.append("WHERE VH.NUM_PLACA = '").append(criterioBean.getPlaca()).append("' ");
	query.append("AND PA.REFNUM_PART = VH.REFNUM_PART "); 
	query.append("AND PA.REG_PUB_ID ='").append(oficina[0]).append("' ");
	query.append("AND PA.OFIC_REG_ID='").append(oficina[1]).append("' ");
	query.append("AND PA.AREA_REG_ID ='").append(areaRegistral).append("' "); 
	query.append("AND PA.ESTADO != '2' ");
	query.append("AND REGP.REG_PUB_ID = OFIR.REG_PUB_ID "); 
	query.append("AND OFIR.OFIC_REG_ID = PA.OFIC_REG_ID ");
	query.append("AND OFIR.REG_PUB_ID = PA.REG_PUB_ID ");
	query.append("AND AREA.AREA_REG_ID = PA.AREA_REG_ID");
	
	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(query.toString());

		System.out.println("Combo QUERY ---> "+query.toString());
	
	ResultSet rset = pstmt.executeQuery();
	
	
	
	while(rset.next())
	{
		PartidaBean partida = new PartidaBean();
		partida.setRefNumPart(rset.getString("REFNUM_PART"));
		partida.setAreaRegistralDescripcion("DESCRIPCIONAREAREGISTRAL");
		partida.setNumPartida(rset.getString("NUM_PARTIDA"));
		partida.setRegPubDescripcion(rset.getString("SIGLAS"));
		partida.setOficRegDescripcion(rset.getString("NOMBRE"));
		if(rset.getString("FG_BAJA")==null)
		{
			partida.setBaja("No Especificado");
		}
		else if (rset.getString("FG_BAJA").equals("1"))
		{
			partida.setBaja("Baja");
		}
		else if (rset.getString("FG_BAJA").equals("0"))
		{
			partida.setBaja("En Circulación");
		}
		else
		{
			partida.setBaja("No Especificado");
		}
		partida.setRegPubId(oficina[0]);
		partida.setOficRegId(oficina[1]);
		partida.setEstado(rset.getString("ESTADO"));
		partida.setAreaRegistralId(rset.getString("AREA_REG_ID"));
		partida.setCodLibro(rset.getString("COD_LIBRO"));
		partida.setNumeroPlaca(rset.getString("NUM_PLACA"));
		
		listaResultado.add(partida);
	}
	
	return listaResultado;
}
/*
*  @autor: mgarate
*  @fecha: 08-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera el area_reg_id
*/
public static String recuperaAreaRegistral(String parametro, Connection conn) throws Throwable 
{
	String resultado;
	resultado="";
	StringBuffer quebusq = new StringBuffer();
	
	quebusq.append("SELECT COD_AREA ");
	quebusq.append("FROM GRUPO_LIBRO_AREA ");
	quebusq.append("WHERE COD_GRUPO_LIBRO_AREA = ? ");

	PreparedStatement pstmt = null;
	pstmt = conn.prepareStatement(quebusq.toString());
	pstmt.setString(1,parametro);
	ResultSet rset = pstmt.executeQuery();

	if(rset.next())
	{
		resultado = rset.getString(1);
	}
	return resultado;
}
/*
*  @autor: mgarate
*  @fecha: 08-06-2007
*  @cc: SUNARP-REGMOBCOM
*  @descripcion: metodo que recupera en un bean los criterios de la busqueda para ser mostrada
*/
public static CriterioBean recuperarCriterio(String criterio)
{
	int count2=0;
	String firstToken;
	StringTokenizer token = new StringTokenizer(criterio,"/",false);
	firstToken="";
	CriterioBean crit = new CriterioBean();
	
	while(token.hasMoreTokens())
	{
		count2++;
		String parametroBusqueda = token.nextToken();
		
			StringTokenizer token2 = new StringTokenizer(parametroBusqueda,"=",false);
			
			while(token2.hasMoreElements())
			{
				String cadenaNombre = token2.nextToken();
				if(cadenaNombre.equals("oficinaregistral"))
				{
					firstToken = "oficinaregistral";
				}
				else if(cadenaNombre.equals("arearegistral"))
				{
					firstToken = "arearegistral";
				}
				else if(cadenaNombre.equals("ficha"))					
				{
					firstToken = "ficha";
				}
				else if(cadenaNombre.equals("partida"))
				{
					firstToken = "partida";
				}
				else if(cadenaNombre.equals("tomo"))
				{
					firstToken = "tomo";
				}
				else if(cadenaNombre.equals("folio"))
				{
					firstToken = "folio";
				}
				else if(cadenaNombre.equals("placa"))
				{
					firstToken = "placa";
				}
				else if(cadenaNombre.equals("apepat") || cadenaNombre.equals("apepataero"))
				{
					firstToken = "apepat";
				}
				else if(cadenaNombre.equals("apemat") || cadenaNombre.equals("apemataero"))
				{
					firstToken = "apemat";
				}
				else if(cadenaNombre.equals("nombre") || cadenaNombre.equals("nombreaero"))
				{
					firstToken = "nombre";
				}
				else if(cadenaNombre.equals("razonsocial") || cadenaNombre.equals("razonsocialaero"))
				{
					firstToken = "razonsocial";
				}
				else if(cadenaNombre.equals("sigla") || cadenaNombre.equals("siglaaero"))
				{
					firstToken = "sigla";
				}
				else if(cadenaNombre.equals("numeromatricula"))
				{
					firstToken = "numeromatricula";
				}
				else if(cadenaNombre.equals("nombreembarcacion"))
				{
					firstToken = "nombreembarcacion";
				}
				else if(cadenaNombre.equals("nombrebuque"))
				{
					firstToken = "nombrebuque";
				}
				else if(cadenaNombre.equals("numeromotor"))
				{
					firstToken = "numeromotor";
				}
				else if(cadenaNombre.equals("chasis"))
				{
					firstToken = "chasis";
				}
				else if(cadenaNombre.equals("registro"))
				{
					firstToken = "registro";
				}
				else if(cadenaNombre.equals("flagmetodo"))
				{
					firstToken = "flagmetodo";
				}
				else
				{
					if(firstToken.equals("oficinaregistral"))
					{
						StringTokenizer st = new StringTokenizer(cadenaNombre, "*", false);
						
						StringBuffer sb = new StringBuffer();
						sb.append("(");
						int cuenta = st.countTokens(); //numero de sedes elegidas
		
						int x = 0;
						String[] tokens = new String[cuenta];
						String[] tokens2 = new String[cuenta];
						int z=0;
				
						while (st.hasMoreTokens() == true) 
						{
							String elemento = st.nextToken();
							
							tokens2[z]=elemento;
							
							sb.append("'");
							sb.append(elemento);
							sb.append("'");
							if (x <= (cuenta - 2))
								sb.append(",");
							
							x++;
							
							//poner en arreglo
							tokens[z]=elemento;
							
							z++;
						}
				
						sb.append(")");
							
						crit.setSedesSQLString(sb.toString());
						crit.setSedesElegidas(tokens);
						crit.setZonaOficina(cadenaNombre);
					}else if(firstToken.equals("arearegistral"))
					{
						crit.setAreaRegistral(cadenaNombre);
					}
					else if(firstToken.equals("ficha"))
					{
						crit.setFicha(cadenaNombre);
					}else if(firstToken.equals("partida"))
					{
						crit.setPartida(cadenaNombre);
					}else if(firstToken.equals("tomo"))
					{
						crit.setTomo(cadenaNombre);
					}else if(firstToken.equals("folio"))
					{
						crit.setFolio(cadenaNombre);
					}else if(firstToken.equals("placa"))
					{
						crit.setPlaca(cadenaNombre);
					}else if(firstToken.equals("apepat"))
					{
						crit.setApellidoParterno(cadenaNombre);
					}else if(firstToken.equals("apemat"))
					{
						crit.setApellidoMaterno(cadenaNombre);
					}else if(firstToken.equals("nombre"))
					{
						crit.setNombre(cadenaNombre);
					}else if(firstToken.equals("razonsocial"))
					{
						crit.setRazonSocial(cadenaNombre);
					}else if(firstToken.equals("sigla"))
					{
						crit.setSigla(cadenaNombre);
					}else if(firstToken.equals("numeromatricula"))
					{
						crit.setNumeroMatricula(cadenaNombre);
					}else if(firstToken.equals("numeromotor"))
					{
						crit.setNumeroMotor(cadenaNombre);
					}else if(firstToken.equals("chasis"))
					{
						crit.setChasis(cadenaNombre);
					}else if(firstToken.equals("registro"))
					{
						crit.setRegistro(cadenaNombre);
					}else if(firstToken.equals("nombreembarcacion"))
					{
						crit.setNombreEmbarcacion(cadenaNombre);
					}
					else if(firstToken.equals("nombrebuque"))
					{
						crit.setNombreBuque(cadenaNombre);
					}
					else if(firstToken.equals("flagmetodo"))
					{
						crit.setFlagmetodo(cadenaNombre);
					}
					
				}
			}
		}		
		
	return crit;
}
	/*
	 * Inicio:jascencio:13/07/07
	 * CC:REGMOBCON-2006
	 */

		public static List recuperarTipoVehiculo(Connection conn) throws Throwable{
			List lista=null;
			StringBuffer sql;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ComboBean combo;
			sql=new StringBuffer();
			
			sql.append("SELECT TPOVEH.COD_TIPO_VEHI,TPOVEH.DESCRIPCION ")
			   .append("FROM TM_TIPO_VEHI TPOVEH ")
			   .append("ORDER BY TPOVEH.DESCRIPCION");
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_VEHI"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_VEHI"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			return lista;
		}
		
		public static List recuperarTipoCombustible(Connection conn) throws Throwable{
			List lista=null;
			StringBuffer sql;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ComboBean combo;
			sql=new StringBuffer();
			
			sql.append("SELECT TCOM.COD_TIPO_COMB,TCOM.DESCRIPCION ")
			   .append("FROM TM_TIPO_COMB TCOM ")
			   .append("ORDER BY TCOM.DESCRIPCION");
			
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_COMB"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_COMB"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			return lista;
		}
		
		public static List recuperarTipoActoVehicular(Connection conn) throws Throwable{
			List lista=null;
			StringBuffer sql;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ComboBean combo;
			sql=new StringBuffer();
			
			sql.append("SELECT RU.COD_RUBRO, RU.NOMBRE ")
			   .append("FROM TM_RUBRO RU ")
			   .append("ORDER BY RU.NOMBRE");
			
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_RUBRO"));
				combo.setDescripcion(rset.getString("NOMBRE"));
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_RUBRO"));
				combo.setDescripcion(rset.getString("NOMBRE"));
				lista.add(combo);
			}
			return lista;
		}
		
		public static List recuperarTipoActoRMC(Connection conn) throws Throwable{
			List lista=null;
			StringBuffer sql;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ComboBean combo;
			sql=new StringBuffer();
			
			sql.append("SELECT RU.COD_RUBRO, RU.NOMBRE ")
			   .append("FROM TM_RUBRO RU ")
			   .append("ORDER BY RU.NOMBRE");
			
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_RUBRO"));
				combo.setDescripcion(rset.getString("NOMBRE"));
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_RUBRO"));
				combo.setDescripcion(rset.getString("NOMBRE"));
				lista.add(combo);
			}
			return lista;
		}
		
		public static List recuperarTipoEmbPesquera(Connection conn) throws Throwable{
			List lista=null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ComboBean combo;
			StringBuffer sql=new StringBuffer();
			
			sql.append("SELECT TPOEMB.COD_TIPO_EMB_PESQ, TPOEMB.DESCRIPCION ")
			   .append("FROM TM_TIPO_EMB_PESQ TPOEMB ");

			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_EMB_PESQ"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_EMB_PESQ"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			return lista;
		}
		
		public static List recuperarTipoAeronave(Connection conn) throws Throwable{
			List lista=null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ComboBean combo;
			StringBuffer sql=new StringBuffer();
			
			sql.append("SELECT TPOAERO.COD_TIPO_AERONAVE, TPOAERO.DESCRIPCION ")
			   .append("FROM TM_TIPO_AERONAVE TPOAERO ")
			   .append("ORDER BY TPOAERO.DESCRIPCION");
			
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_AERONAVE"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_TIPO_AERONAVE"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			return lista;
		}
		
		public static List recuperarCapitania(Connection conn) throws Throwable{
			List lista=null;
			PreparedStatement pstmt = null;
			ResultSet rset = null;
			ComboBean combo;
			StringBuffer sql=new StringBuffer();
			
			sql.append("SELECT CAP.COD_CAPITANIA, CAP.DESCRIPCION ")
			   .append("FROM TM_CAPITANIA CAP ")
			   .append("ORDER BY CAP.DESCRIPCION");
						
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_CAPITANIA"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("COD_CAPITANIA"));
				combo.setDescripcion(rset.getString("DESCRIPCION"));
				lista.add(combo);
			}
			return lista;
		}
		
		public static List getComboAreaLibro(int served, int servRmc, int servSigc,Connection conn)throws Throwable{
			ArrayList lista=null;
			PreparedStatement pstmt=null;
			ResultSet rset=null;
			ComboBean combo;
			StringBuffer sql=new StringBuffer();
			sql.append("SELECT gla.cod_grupo_libro_area, gla.desc_grupo_libro_area, t.prec_ofic, gla.cod_area ")
				.append("FROM grupo_libro_area gla, tarifa t ")
				.append("WHERE gla.cod_grupo_libro_area = t.cod_grupo_libro_area and ")
				.append("gla.cod_grupo_libro_area!='5' ")
				.append("AND (t.servicio_id =").append(served)
				.append(" or t.servicio_id=").append(servRmc)
				.append(" or t.servicio_id=").append(servSigc).append(")")
				.append(" order by 1");
			
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			if(rset.next()){
				lista=new ArrayList();
				combo=new ComboBean();
				combo.setCodigo(rset.getString("cod_grupo_libro_area"));
				combo.setDescripcion(rset.getString("desc_grupo_libro_area"));
				combo.setAtributo1("prec_ofic");
				combo.setAtributo2("cod_area");
				lista.add(combo);
			}
			
			while(rset.next()){
				combo=new ComboBean();
				combo.setCodigo(rset.getString("cod_grupo_libro_area"));
				combo.setDescripcion(rset.getString("desc_grupo_libro_area"));
				combo.setAtributo1("prec_ofic");
				combo.setAtributo2("cod_area");
				lista.add(combo);
			}
			return lista;
		}
		
		public static InputBusqIndirectaBean recojeDatosRequestBusqIndirectaPartidaSIGC(HttpServletRequest req)
		throws Throwable
		{
			HttpSession session = req.getSession();
			InputBusqIndirectaBean bean = null;
			
			boolean inactivo=false;
			
			String xparam = req.getParameter("flagPagineo2");
			
			if (xparam==null)
			{	
				bean = new InputBusqIndirectaBean();	
				bean.setComboArea(req.getParameter("comboArea"));
				bean.setCodGrupoLibroArea(req.getParameter("comboAreaLibro"));
				bean.setDescGrupoLibroArea(req.getParameter("comboArea"));
				/** inicio:jrosas 10-08-07 **/
				bean.setVerifica(req.getParameter("verifica"));
				/** fin:jrosas 10-08-07 **/
				if(req.getParameter("refNumPart")!=null)
				{
					bean.setRefNumPart(req.getParameter("refNumPart"));
				}
				
				if(bean.getCodGrupoLibroArea().equals(Constantes.CODIGO_SISTEMA_INTEGRADO_GARANTIAS_CONTRATOS)){
					String param = req.getParameter("tipo") == null?"":req.getParameter("tipo");
					
					if (param.equals("N")){ // participante persona natural
						if(req.getParameter("txtParticipanteApePatArea15") != null && req.getParameter("txtParticipanteApePatArea15").length() > 0){
							bean.setArea1ApePat(reemplazaApos(req.getParameter("txtParticipanteApePatArea15").trim().toUpperCase()));
						}
						if(req.getParameter("txtParticipanteApeMatArea15") != null && req.getParameter("txtParticipanteApeMatArea15").length() > 0){
							bean.setArea1ApeMat(reemplazaApos(req.getParameter("txtParticipanteApeMatArea15").trim().toUpperCase()));
						}
						if(req.getParameter("txtParticipanteNombreArea15") != null && req.getParameter("txtParticipanteNombreArea15").length() > 0){
							bean.setArea1Nombre(reemplazaApos(req.getParameter("txtParticipanteNombreArea15").trim().toUpperCase()));
						}
					}
					if (param.equals("J")){ // participante persona juridica
						if(req.getParameter("txtParticipanteRazonArea15") != null && req.getParameter("txtParticipanteRazonArea15").length() > 0){
							bean.setArea2Razon1(reemplazaApos(req.getParameter("txtParticipanteRazonArea15").trim().toUpperCase()));
						}
						if(req.getParameter("txtSiglasArea15") != null && req.getParameter("txtSiglasArea15").length() > 0){
							bean.setArea2Siglas(reemplazaApos(req.getParameter("txtSiglasArea15").trim().toUpperCase()));
						}
					}
					if (param.equals("D")){// por tipo y numero de documento
						if(req.getParameter("cboTipoDocumentoArea15") != null && req.getParameter("cboTipoDocumentoArea15").length() > 0){
							bean.setTipoDocumento(reemplazaApos(req.getParameter("cboTipoDocumentoArea15").trim()));
						}
						if(req.getParameter("txtNumeroDocumentoArea15") != null && req.getParameter("txtNumeroDocumentoArea15").length() > 0){
							bean.setNumeroDocumento(reemplazaApos(req.getParameter("txtNumeroDocumentoArea15").trim().toUpperCase()));
						}
					}
					if (param.equals("B")){// por bienes
						if(req.getParameter("txtNumeroPlacaArea15") != null && req.getParameter("txtNumeroPlacaArea15").length() > 0){
							bean.setNumeroPlaca(reemplazaApos(req.getParameter("txtNumeroPlacaArea15").trim().toUpperCase()));
						}
						if(req.getParameter("txtNumeroMatriculaArea15") != null && req.getParameter("txtNumeroMatriculaArea15").length() > 0){
							bean.setNumeroMatricula(reemplazaApos(req.getParameter("txtNumeroMatriculaArea15").trim().toUpperCase()));
						}
						if(req.getParameter("txtNombreBienArea15") != null && req.getParameter("txtNombreBienArea15").length() > 0){
							bean.setNombreBien(reemplazaApos(req.getParameter("txtNombreBienArea15").trim().toUpperCase()));
						}
						if(req.getParameter("txtNumeroSerieArea15") != null && req.getParameter("txtNumeroSerieArea15").length() > 0){
							bean.setNumeroSerie(reemplazaApos(req.getParameter("txtNumeroSerieArea15").trim().toUpperCase()));
						}
					}
				}
				session.setAttribute("INPUT_BEAN",bean);
			}
			
			if (xparam!=null)
				{
					bean = (InputBusqIndirectaBean) session.getAttribute("INPUT_BEAN");
					bean.setFlagPagineo(true);
					if(req.getParameter("salto") != null && req.getParameter("salto").length() > 0){
						bean.setSalto(Integer.parseInt(req.getParameter("salto")));
					}
					bean.setCantidad(req.getParameter("cantidad"));
				}
			
			if(req.getParameter("checkInactivosArea15") != null){
				if(req.getParameter("checkInactivosArea15").equals("1")){
					inactivo = true;
				}
				else{
					inactivo = false;
				}
			}
			bean.setFlagIncluirInactivos(inactivo);
			
			String userInterno=req.getParameter("hidFlagInterno");
			if(userInterno != null){
				if(userInterno.equals("1")){
					if(req.getParameter("saltoInferior") != null && req.getParameter("saltoInferior").length() > 0){
						bean.setSaltoInferior(Integer.parseInt(req.getParameter("saltoInferior")));
					}
					bean.setCantidadInferior(req.getParameter("cantidadInferior"));
				}
			}
			
			String flagInferior=req.getParameter("flagPagineoInferior");
			//flagInferior=1 significa que tiene que hacer otra vez una consulta para  recalcular la
			//cantidad de registros encontrados
			if(flagInferior != null){
			
				if(flagInferior.equals("1")){
					bean.setFlagPagineoInferior(false);
				}
				else{
					bean.setFlagPagineoInferior(true);
				}
			}
			return bean;
		}	
		
		
	/*
	 * Fin:jascencio
	 */
	// Inicio:mgarate:19/07/2007
	public static String recuperarRefnum_part(ArrayList refnum)
	{
		String cadena="(";
		for(int i=0;i<refnum.size();i++)
		{
			cadena = cadena +"'"+(String)refnum.get(i)+"',";
		}
		cadena = cadena.substring(0,cadena.length()-1);
		cadena = cadena+(")");
		return cadena;
	}
	//Fin:mgarate
	//Inicio:mgarate:24/08/2007
	public static String recuperarRefnum_part(String numeroPartida, String idRegistroPublico, String idOficinaRegistral, String areaRegistral , Connection conn) throws Throwable 
	{
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		StringBuffer sql=new StringBuffer();
		String resultado = "";
		
		sql.append("SELECT PART.REFNUM_PART ");
		sql.append("FROM PARTIDA PART ");
		sql.append("WHERE  PART.NUM_PARTIDA='").append(numeroPartida).append("' AND ");
		sql.append("PART.REG_PUB_ID = '").append(idRegistroPublico).append("' AND ");
		sql.append("PART.OFIC_REG_ID = '").append(idOficinaRegistral).append("' AND ");
		sql.append("PART.AREA_REG_ID = '").append(areaRegistral).append("' ");
		
		pstmt=conn.prepareStatement(sql.toString());
		rset=pstmt.executeQuery();
		if(rset.next())
		{
			resultado = rset.getString(1);
		}
		return resultado;
	}
	//Fin:mgarate
	/*** JBUGARIN BUSCADOR**
	 * @throws DBException */ 
	public static Integer getTarifa(DBConnection conn, Integer servicio) throws DBException{
		Integer montoTarifa = new Integer(0);
		DboTarifa dboTarifa = new DboTarifa(conn);
		dboTarifa.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
		dboTarifa.setField(DboTarifa.CAMPO_SERVICIO_ID, servicio);
		if (dboTarifa.find())
			montoTarifa=Integer.parseInt(dboTarifa.getField(DboTarifa.CAMPO_PREC_OFIC));
			
			return montoTarifa;
	}
	
		
} 
	
	/*** JBUGARIN BUSCADOR***/
//fin de clase