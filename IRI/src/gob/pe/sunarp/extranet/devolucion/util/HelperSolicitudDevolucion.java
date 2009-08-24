/*
 * Created on Jan 25, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.devolucion.util;

import gob.pe.sunarp.extranet.administracion.bean.RegisPublicoBean;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboDireccion;
import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboPersona;
import gob.pe.sunarp.extranet.dbobj.DboRazSocPj;
import gob.pe.sunarp.extranet.dbobj.DboRegisPublico;
import gob.pe.sunarp.extranet.dbobj.DboTaSoliDevo;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.SolicitanteBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;

import com.jcorporate.expresso.core.db.DBConnection;

/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelperSolicitudDevolucion {
	public static void crearSolicitudBaja(String cuentaId,String regisPubId,String oficRegId,String tipoUsr ,DBConnection dconn) throws Exception
   {
	  
	   try 
	   {
	
		DboCuenta cuenta= new DboCuenta();
		cuenta.setConnection(dconn);
		cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaId);
		cuenta.find();
		DboPeNatu penatu= new DboPeNatu();
		penatu.setConnection(dconn);
		penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
		penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
		penatu.find();
		String strMonto="";
		if(tipoUsr.equalsIgnoreCase(Constantes.TIPO_USR_ORGANIZACION)){
			strMonto=HelperCuenta.calcularTotalOrganizacion(penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID),dconn);
		}else{
			strMonto=HelperCuenta.calcularTotalUsuario(cuentaId,dconn);
		}
		DboTaSoliDevo solDev= new DboTaSoliDevo();
		solDev.setConnection(dconn);
		solDev.setField(DboTaSoliDevo.CAMPO_CUENTA_ID,cuentaId);
		solDev.setField(DboTaSoliDevo.CAMPO_FE_SOLI,FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
		solDev.setField(DboTaSoliDevo.CAMPO_OFIC_REG_ID,oficRegId);
		solDev.setField(DboTaSoliDevo.CAMPO_REG_PUB_ID,regisPubId);
		solDev.setField(DboTaSoliDevo.CAMPO_TIPO_USR,tipoUsr);
		solDev.setField(DboTaSoliDevo.CAMPO_ESTA,Constantes.ESTADO_SOL_DEV_REGISTRADO);
		solDev.setField(DboTaSoliDevo.CAMPO_MONTO,strMonto);
		solDev.add();
		}
	   catch (Exception e) 
	   {
		   throw e;
	   }
	  
	
   }
   public static SolicitanteBean obtenerSolicitanteBean(UsuarioBean userBean, DBConnection dconn) throws Exception
	  {
	  
		  try 
		  {
		  	SolicitanteBean solicitante= new SolicitanteBean();
		  	solicitante.setApe_mat(userBean.getApeMat());
			solicitante.setApe_pat(userBean.getApePat());
			solicitante.setNombres(userBean.getNombres());
			DboPeNatu penatu= new DboPeNatu();
			penatu.setConnection(dconn);
			penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,userBean.getPeNatuId());
			penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PERSONA_ID+"|"+DboPeNatu.CAMPO_PE_JURI_ID);
			penatu.find();
			if(userBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT){
				DboPeJuri pejuri = new DboPeJuri();
				pejuri.setConnection(dconn);
				pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
				pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
				pejuri.find();
				solicitante.setRaz_soc(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
				solicitante.setSaldo(String.valueOf(HelperCuenta.calcularTotalOrganizacion(penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID),dconn)));
			}else
			solicitante.setSaldo(HelperCuenta.calcularTotalUsuario(userBean.getCuentaId(),dconn));
			DboDireccion dboDireccion= new DboDireccion();
			dboDireccion.setConnection(dconn);
			dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, penatu.getField(DboPeNatu.CAMPO_PERSONA_ID));
			dboDireccion.find();
			solicitante.setDireccion(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
			if(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA)!=null)
				solicitante.setDireccion(solicitante.getDireccion()+" "+dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
			DboPersona persona = new DboPersona();
			persona.setConnection(dconn);
			persona.setField(DboPersona.CAMPO_PERSONA_ID,penatu.getField(DboPeNatu.CAMPO_PERSONA_ID));
			persona.setFieldsToRetrieve(DboPersona.CAMPO_REG_PUB_ID);
			persona.find();
//			DboRegisPublico regPub= new DboRegisPublico();
//			regPub.setConnection(dconn);
//			regPub.setField(DboRegisPublico.CAMPO_REG_PUB_ID,persona.getField(DboPersona.CAMPO_REG_PUB_ID));
//			regPub.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
//			regPub.find();
//			solicitante.setZonaRegistral(regPub.getField(DboRegisPublico.CAMPO_NOMBRE));
		  
		   return solicitante;
		   }
		  catch (Exception e) 
		  {
			  throw e;
		  }
	  
	
	  }
	public static SolicitanteBean obtenerSolicitanteBeanUsrDev(UsuarioBean userBean, String solDevId,DBConnection dconn) throws Exception
		  {
	  
			  try 
			  {
				SolicitanteBean solicitante= new SolicitanteBean();
				solicitante.setApe_mat(userBean.getApeMat());
				solicitante.setApe_pat(userBean.getApePat());
				solicitante.setNombres(userBean.getNombres());
				DboPeNatu penatu= new DboPeNatu();
				penatu.setConnection(dconn);
				penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,userBean.getPeNatuId());
				penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PERSONA_ID+"|"+DboPeNatu.CAMPO_PE_JURI_ID);
				penatu.find();
				if(userBean.getPerfilId()==Constantes.PERFIL_ADMIN_ORG_EXT){
					DboPeJuri pejuri = new DboPeJuri();
					pejuri.setConnection(dconn);
					pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID,penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
					pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
					pejuri.find();
					solicitante.setRaz_soc(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));
					solicitante.setSaldo(String.valueOf(HelperCuenta.obtenerTotalOrganizacion(solDevId,dconn)));
				}else
				solicitante.setSaldo(HelperCuenta.obtenerTotalUsuario(solDevId,dconn));
				DboDireccion dboDireccion= new DboDireccion();
				dboDireccion.setConnection(dconn);
				dboDireccion.setField(DboDireccion.CAMPO_PERSONA_ID, penatu.getField(DboPeNatu.CAMPO_PERSONA_ID));
				dboDireccion.find();
				solicitante.setDireccion(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA));
				if(dboDireccion.getField(DboDireccion.CAMPO_NOM_NUM_VIA)!=null)
					solicitante.setDireccion(solicitante.getDireccion()+" "+dboDireccion.getField(DboDireccion.CAMPO_NO_DIST));
				DboPersona persona = new DboPersona();
				persona.setConnection(dconn);
				persona.setField(DboPersona.CAMPO_PERSONA_ID,penatu.getField(DboPeNatu.CAMPO_PERSONA_ID));
				persona.setFieldsToRetrieve(DboPersona.CAMPO_REG_PUB_ID);
				persona.find();
//				DboRegisPublico regPub= new DboRegisPublico();
//				regPub.setConnection(dconn);
//				regPub.setField(DboRegisPublico.CAMPO_REG_PUB_ID,persona.getField(DboPersona.CAMPO_REG_PUB_ID));
//				regPub.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
//				regPub.find();
//				solicitante.setZonaRegistral(regPub.getField(DboRegisPublico.CAMPO_NOMBRE));
		  
			   return solicitante;
			   }
			  catch (Exception e) 
			  {
				  throw e;
			  }
	  
	
		  }
	public static String obtenerZonaRegistral(String zonaId, DBConnection dconn) throws Exception
	   {
	  
		   try 
		   {
			DboRegisPublico regis= new DboRegisPublico();
			
			regis.setConnection(dconn);
			regis.setField(DboRegisPublico.CAMPO_REG_PUB_ID,zonaId);
			regis.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
			regis.find();
			return regis.getField(DboRegisPublico.CAMPO_NOMBRE);
			
			}
		   catch (Exception e) 
		   {
			   throw e;
		   }
	  
	
	   }
	public static String obtenerDepartamento(String regisPubId,String oficRegId, DBConnection dconn) throws Exception
	   {
  
		   try 
		   {
			DboOficRegistral oficRegis= new DboOficRegistral();
		
			oficRegis.setConnection(dconn);
			oficRegis.setField(DboOficRegistral.CAMPO_REG_PUB_ID,regisPubId);
			oficRegis.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficRegId);
			oficRegis.find();
			return oficRegis.getField(DboOficRegistral.CAMPO_NOMBRE);
		
			}
		   catch (Exception e) 
		   {
			   throw e;
		   }
  

	   }
	

}
