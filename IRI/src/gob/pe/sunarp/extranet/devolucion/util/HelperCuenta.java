/*
 * Created on Jan 25, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.devolucion.util;

import java.text.DecimalFormat;
import java.util.Iterator;

import gob.pe.sunarp.extranet.administracion.bean.MantenOrganizacionBean;
import gob.pe.sunarp.extranet.administracion.bean.MantenUsuarioBean;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboLineaPrepago;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboTAAranCal;
import gob.pe.sunarp.extranet.dbobj.DboTaSoliDevo;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;

import com.jcorporate.expresso.core.db.DBConnection;

/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelperCuenta {
	public static void bloquearCuenta(String cuentaId, DBConnection dconn) throws Exception
   {
	  
	   try 
	   {
	   	DboCuenta cuenta= new DboCuenta();
	   	cuenta.setConnection(dconn);
	   	cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaId);
		cuenta.setField(DboCuenta.CAMPO_ESTADO,Constantes.ESTADO_INACTIVO);
	   	cuenta.setFieldsToUpdate(DboCuenta.CAMPO_ESTADO);
	   	cuenta.update();
		DboLineaPrepago linea= new DboLineaPrepago();
		linea.setConnection(dconn);
		linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID,cuentaId);
		linea.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_ESTADO+"|"+DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
		if(linea.find()){
			String estado=linea.getField(DboLineaPrepago.CAMPO_ESTADO);
			String lineaId=linea.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
			if(estado.equalsIgnoreCase(Constantes.ESTADO_ACTIVO)){
				linea.clear();
				linea.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID,lineaId);
				linea.setField(DboLineaPrepago.CAMPO_ESTADO,Constantes.ESTADO_INACTIVO);
				linea.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
				linea.update();
			}
				  
		 }
	   }
	   catch (Exception e) 
	   {
		   throw e;
	   }
	  
	
   }
	   
	public static void bloquearOrganizacion(String cuentaId, DBConnection dconn) throws Exception
   {
   	try 
	   {
		DboCuenta cuenta= new DboCuenta();
		cuenta.setConnection(dconn);
		cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaId);
		cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_PE_NATU_ID);
		cuenta.find();
		DboPeNatu penatu= new DboPeNatu();
		penatu.setConnection(dconn);
		penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
		penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
		penatu.find();
		String pejuriId= penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID);
		penatu.clear();
		penatu.setField(DboPeNatu.CAMPO_PE_JURI_ID,pejuriId);
		penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_NATU_ID);
		java.util.List lista= penatu.searchAndRetrieveList();
		Iterator i=lista.iterator();
		while(i.hasNext()){
			DboPeNatu peAfiliada=(DboPeNatu)i.next();
			System.out.println(peAfiliada.getField(DboPeNatu.CAMPO_PE_NATU_ID));
			DboCuenta cuentaAfiliado=new DboCuenta();
			cuentaAfiliado.setConnection(dconn);
			cuentaAfiliado.setField(DboCuenta.CAMPO_PE_NATU_ID,peAfiliada.getField(DboPeNatu.CAMPO_PE_NATU_ID));
			if(cuentaAfiliado.find()){
				cuentaAfiliado.setFieldsToUpdate(DboCuenta.CAMPO_ESTADO);
				cuentaAfiliado.setField(DboCuenta.CAMPO_ESTADO,Constantes.ESTADO_INACTIVO);
				cuentaAfiliado.update();
			}
			
		}
		bloquearLinPrepOrg(pejuriId,dconn);
   	
	   }
	   catch (Exception e) 
	   {
		   throw e;
	   }
  

   }
 public static MantenUsuarioBean obtenerOrganizacion(UsuarioBean userBean, DBConnection dconn) throws Throwable//organizacion que se esta dando de baja
  {
   try{
	  
	   DboCuenta cuenta= new DboCuenta();
	   cuenta.setConnection(dconn);
	   cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,userBean.getCuentaId());
	   cuenta.find();
	   DboPeNatu penatu= new DboPeNatu();
	   penatu.setConnection(dconn);
	   penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
	   penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
	   penatu.find();
	   String pejuriId= penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID);
	   String monto=calcularTotalOrganizacion(pejuriId,dconn);
	   MantenUsuarioBean usuario= new MantenUsuarioBean();
	   usuario.setSaldo(monto);
	   usuario.setApe_Nom(userBean.getApePat().trim()+" "+userBean.getApeMat().trim()+", "+userBean.getNombres().trim() );
	   usuario.setUsuario(cuenta.getField(DboCuenta.CAMPO_USR_ID));
	   String tipoUsuario="";

	   if(userBean.getTipoUser().startsWith("1")||userBean.getTipoUser().equalsIgnoreCase(Constantes.TIPO_USR_ORGANIZACION))
		   tipoUsuario = "EXTERNO";
	   else
		   tipoUsuario = "INTERNO";
	 
	   usuario.setTipo(tipoUsuario);
	   usuario.setFechaAfiliacion(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_CREA)));
	   usuario.setFechaUltimoAcceso(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_ULT_ACC)));
	   int diasUltAc=FechaUtil.getDays(usuario.getFechaUltimoAcceso(),FechaUtil.getCurrentDate());
	   usuario.setDiasDesdeUltimoAcceso(""+diasUltAc);
	   DboPeJuri pejuri = new DboPeJuri();
	   pejuri.setConnection(dconn);
	   pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
	   pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
	   pejuri.find();
	   usuario.setOrg_afiliada(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));		
	   usuario.setAdmin_Org(Constantes.INDICA_AFIRMACION);

   		return usuario;
	  }
	  catch (Exception e) 
	  {
		  throw e;
	  }
  

  }
   public static MantenUsuarioBean obtenerOrganizacionUsrDev(UsuarioBean userBean, String solDevId,DBConnection dconn) throws Throwable //obtener organizacion para consultas
  {
   try{
	  
	   DboCuenta cuenta= new DboCuenta();
	   cuenta.setConnection(dconn);
	   cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,userBean.getCuentaId());
	   cuenta.find();
	   DboPeNatu penatu= new DboPeNatu();
	   penatu.setConnection(dconn);
	   penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
	   penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
	   penatu.find();
	   String pejuriId= penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID);
	   String monto=obtenerTotalOrganizacion(solDevId,dconn);
	   MantenUsuarioBean usuario= new MantenUsuarioBean();
	   usuario.setSaldo(monto);
	   usuario.setApe_Nom(userBean.getApePat().trim()+" "+userBean.getApeMat().trim()+", "+userBean.getNombres().trim() );
	   usuario.setUsuario(cuenta.getField(DboCuenta.CAMPO_USR_ID));
	   String tipoUsuario="";

	   if(userBean.getTipoUser().startsWith("1")||userBean.getTipoUser().equalsIgnoreCase(Constantes.TIPO_USR_ORGANIZACION))
		   tipoUsuario = "EXTERNO";
	   else
		   tipoUsuario = "INTERNO";
	 
	   usuario.setTipo(tipoUsuario);
	   usuario.setFechaAfiliacion(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_CREA)));
	   usuario.setFechaUltimoAcceso(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_ULT_ACC)));
	   int diasUltAc=FechaUtil.getDays(usuario.getFechaUltimoAcceso(),FechaUtil.getCurrentDate());
	   usuario.setDiasDesdeUltimoAcceso(""+diasUltAc);
	   DboPeJuri pejuri = new DboPeJuri();
	   pejuri.setConnection(dconn);
	   pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
	   pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID));
	   pejuri.find();
	   usuario.setOrg_afiliada(pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC));		
	   usuario.setAdmin_Org(Constantes.INDICA_AFIRMACION);

   		return usuario;
	  }
	  catch (Exception e) 
	  {
		  throw e;
	  }
  

  }
  public static MantenUsuarioBean obtenerUsuario(UsuarioBean userBean, DBConnection dconn) throws Throwable// usuario dandose de baja
   {
	try 
	   {
		MantenUsuarioBean usuario= new MantenUsuarioBean();
		
		DboCuenta cuenta= new DboCuenta();
		cuenta.setConnection(dconn);
		cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,userBean.getCuentaId());
		cuenta.find();
		usuario.setSaldo(calcularTotalUsuario(userBean.getCuentaId(),dconn));
	    usuario.setApe_Nom(userBean.getApePat().trim()+" "+userBean.getApeMat().trim()+", "+userBean.getNombres().trim() );
		usuario.setUsuario(cuenta.getField(DboCuenta.CAMPO_USR_ID));
		String tipoUsuario="";
		
				if(userBean.getTipoUser().startsWith("1"))
					tipoUsuario = "EXTERNO";
				else
					tipoUsuario = "INTERNO";
		 
		usuario.setTipo(tipoUsuario);
		usuario.setFechaAfiliacion(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_CREA)));
		usuario.setFechaUltimoAcceso(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_ULT_ACC)));
		int diasUltAc=FechaUtil.getDays(usuario.getFechaUltimoAcceso(),FechaUtil.getCurrentDate());
		usuario.setDiasDesdeUltimoAcceso(""+diasUltAc);		
		usuario.setOrg_afiliada("---");		
		usuario.setAdmin_Org("---");
		 return usuario;
	   }
	   catch (Exception e) 
	   {
		   throw e;
	   }
  

   }
   public static MantenUsuarioBean obtenerUsuarioUsrDev(UsuarioBean userBean,String solDevId, DBConnection dconn) throws Throwable//usuario consultas
	 {
	  try 
		 {
		  MantenUsuarioBean usuario= new MantenUsuarioBean();
		
		  DboCuenta cuenta= new DboCuenta();
		  cuenta.setConnection(dconn);
		  cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,userBean.getCuentaId());
		  cuenta.find();
		  usuario.setSaldo(obtenerTotalUsuario(solDevId,dconn));
		  usuario.setApe_Nom(userBean.getApePat().trim()+" "+userBean.getApeMat().trim()+", "+userBean.getNombres().trim() );
		  usuario.setUsuario(cuenta.getField(DboCuenta.CAMPO_USR_ID));
		  String tipoUsuario="";
		
				  if(userBean.getTipoUser().startsWith("1"))
					  tipoUsuario = "EXTERNO";
				  else
					  tipoUsuario = "INTERNO";
		 
		  usuario.setTipo(tipoUsuario);
		  usuario.setFechaAfiliacion(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_CREA)));
		  usuario.setFechaUltimoAcceso(FechaUtil.expressoDateToUtilDate(cuenta.getField(DboCuenta.CAMPO_TS_ULT_ACC)));
		  int diasUltAc=FechaUtil.getDays(usuario.getFechaUltimoAcceso(),FechaUtil.getCurrentDate());
		  usuario.setDiasDesdeUltimoAcceso(""+diasUltAc);		
		  usuario.setOrg_afiliada("---");		
		  usuario.setAdmin_Org("---");
		   return usuario;
		 }
		 catch (Exception e) 
		 {
			 throw e;
		 }
  

	 }
   public static String calcularTotalOrganizacion(String peJuriId, DBConnection dconn) throws Exception{
   	
	try {
	DboLineaPrepago linea = new DboLineaPrepago();
	linea.setConnection(dconn);
	linea.setField(DboLineaPrepago.CAMPO_PE_JURI_ID,peJuriId);
	linea.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_ESTADO);
	linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID, "is null");
	   double monto=0;
	   if(linea.find()){
		  String strSaldo=linea.getField(DboLineaPrepago.CAMPO_SALDO);
		  String estado=linea.getField(DboLineaPrepago.CAMPO_ESTADO);
		  if(estado.equalsIgnoreCase(Constantes.ESTADO_ACTIVO)){
			monto=monto+Double.parseDouble(strSaldo);
		  } 
	   }
	DboPeNatu penatu= new DboPeNatu();
	penatu.setConnection(dconn);
	penatu.setField(DboPeNatu.CAMPO_PE_JURI_ID,peJuriId);
	penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_NATU_ID);
	java.util.List lpersona= penatu.searchAndRetrieveList();
	Iterator it=lpersona.iterator();
			
	while(it.hasNext()){
	   DboPeNatu peNatuAfil=(DboPeNatu)it.next();
	   DboCuenta cuentaAfil=new DboCuenta();
	   cuentaAfil.setConnection(dconn);
	   cuentaAfil.setField(DboCuenta.CAMPO_PE_NATU_ID,peNatuAfil.getField(DboPeNatu.CAMPO_PE_NATU_ID));
	   if(cuentaAfil.find()){
	   DboLineaPrepago lineaAfil= new DboLineaPrepago();
	   lineaAfil.setConnection(dconn);
	   lineaAfil.setField(DboLineaPrepago.CAMPO_CUENTA_ID,cuentaAfil.getField(DboCuenta.CAMPO_CUENTA_ID));
	   lineaAfil.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_ESTADO);
	   if(lineaAfil.find()){
	   	   String strSaldo=lineaAfil.getField(DboLineaPrepago.CAMPO_SALDO);
	   	   String estado=lineaAfil.getField(DboLineaPrepago.CAMPO_ESTADO);
	   	   if(estado.equalsIgnoreCase(Constantes.ESTADO_ACTIVO)){
		 		monto=monto+Double.parseDouble(strSaldo);
		   } 
	   	}
   
		}
	}
	return String.valueOf(monto);
   	
   }
  catch (Exception e) 
	{
		throw e;
	}
	}  
	public static String calcularTotalUsuario(String cuentaId, DBConnection dconn) throws Exception{
   	
	   try {
		DboLineaPrepago linea= new DboLineaPrepago();
		linea.setConnection(dconn);
		linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID,cuentaId);
		linea.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_ESTADO);
		linea.find();
		String estado=linea.getField(DboLineaPrepago.CAMPO_ESTADO);
		String monto="0.0";
		if(estado.equalsIgnoreCase(Constantes.ESTADO_ACTIVO))
		   monto=linea.getField(DboLineaPrepago.CAMPO_SALDO);
		
	 
	   return monto;
   	
	  }
  
	   catch (Exception e) 
	   {
		   throw e;
	   }
	}  
	public static UsuarioBean obtenerUsuarioBean(String cuentaId,String idSolDev,DBConnection dconn) throws Exception{
   	
	try {
		UsuarioBean user=new UsuarioBean();
		user.setCuentaId(cuentaId);
		DboCuenta cuenta= new DboCuenta();
		cuenta.setConnection(dconn);
		cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,user.getCuentaId());
		cuenta.find();
		user.setUserId(cuenta.getField(DboCuenta.CAMPO_USR_ID));
		DboPeNatu penatu=new DboPeNatu();
		penatu.setConnection(dconn);
		penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
		penatu.find();
		user.setApeMat(penatu.getField(DboPeNatu.CAMPO_APE_MAT));
		user.setApePat(penatu.getField(DboPeNatu.CAMPO_APE_PAT));
		user.setNombres(penatu.getField(DboPeNatu.CAMPO_NOMBRES));
		user.setPeNatuId(penatu.getField(DboPeNatu.CAMPO_PE_NATU_ID));
		DboTaSoliDevo solDev= new DboTaSoliDevo();
		solDev.setConnection(dconn);
		solDev.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,idSolDev);
		solDev.find();
		if(solDev.getField(DboTaSoliDevo.CAMPO_TIPO_USR)!=null){
			if(solDev.getField(DboTaSoliDevo.CAMPO_TIPO_USR).equalsIgnoreCase(Constantes.TIPO_USR_ORGANIZACION))
				user.setPerfilId(Constantes.PERFIL_ADMIN_ORG_EXT);
			else
				user.setPerfilId(Constantes.PERFIL_INDIVIDUAL_EXTERNO);
			user.setTipoUser(DboTaSoliDevo.CAMPO_TIPO_USR);
		}
		return user;
		}
		
  
   catch (Exception e){
		throw e;
   }
   }  
   public static void bloquearLinPrepOrg(String peJuriId, DBConnection dconn) throws Exception{
   	
	   try {
	   DboLineaPrepago linea = new DboLineaPrepago();
	   linea.setConnection(dconn);
	   linea.setField(DboLineaPrepago.CAMPO_PE_JURI_ID,peJuriId);
	   linea.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_ESTADO);
	   linea.setField(DboLineaPrepago.CAMPO_CUENTA_ID, "is null");
	    if(linea.find()){
	   	  String strSaldo=linea.getField(DboLineaPrepago.CAMPO_SALDO);
		  String estado=linea.getField(DboLineaPrepago.CAMPO_ESTADO);
		  String lineaId=linea.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
		  if(estado.equalsIgnoreCase(Constantes.ESTADO_ACTIVO)){
			linea.clear();
			linea.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID,lineaId);
			linea.setField(DboLineaPrepago.CAMPO_ESTADO,Constantes.ESTADO_INACTIVO);
			linea.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
			linea.update();
		   } 
		   
	   }
	   DboPeNatu penatu= new DboPeNatu();
	   penatu.setConnection(dconn);
	   penatu.setField(DboPeNatu.CAMPO_PE_JURI_ID,peJuriId);
	   penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_NATU_ID);
	   java.util.List lpersona= penatu.searchAndRetrieveList();
	   Iterator it=lpersona.iterator();
			
	   while(it.hasNext()){
		  DboPeNatu peNatuAfil=(DboPeNatu)it.next();
		  DboCuenta cuentaAfil=new DboCuenta();
		  cuentaAfil.setConnection(dconn);
		  cuentaAfil.setField(DboCuenta.CAMPO_PE_NATU_ID,peNatuAfil.getField(DboPeNatu.CAMPO_PE_NATU_ID));
		  if(cuentaAfil.find()){
		  DboLineaPrepago lineaAfil= new DboLineaPrepago();
		  lineaAfil.setConnection(dconn);
		  lineaAfil.setField(DboLineaPrepago.CAMPO_CUENTA_ID,cuentaAfil.getField(DboCuenta.CAMPO_CUENTA_ID));
		  lineaAfil.setFieldsToRetrieve(DboLineaPrepago.CAMPO_SALDO+"|"+DboLineaPrepago.CAMPO_ESTADO+"|"+DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
		  if(lineaAfil.find()){
		  	  String strSaldo=lineaAfil.getField(DboLineaPrepago.CAMPO_SALDO);
		  	  String lineaId=lineaAfil.getField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID);
		  	String estado=lineaAfil.getField(DboLineaPrepago.CAMPO_ESTADO);
		  	if(estado.equalsIgnoreCase(Constantes.ESTADO_ACTIVO)){
		  		lineaAfil.clear();
		  		lineaAfil.setField(DboLineaPrepago.CAMPO_LINEA_PREPAGO_ID,lineaId);
				lineaAfil.setField(DboLineaPrepago.CAMPO_ESTADO,Constantes.ESTADO_INACTIVO);
				lineaAfil.setFieldsToUpdate(DboLineaPrepago.CAMPO_ESTADO);
				lineaAfil.update();
		   	} 
		  	}
		  }
   
	   }
	  
   	
	  }
   
  
	   catch (Exception e) 
	   {
		   throw e;
	   }
	   }  
	   
	   
public static String obtenerTotalOrganizacion(String solDevId, DBConnection dconn) throws Exception{
   	
	 try {
		DboTaSoliDevo solDev= new DboTaSoliDevo();
		   solDev.setConnection(dconn);
		   solDev.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solDevId);
		   solDev.setFieldsToRetrieve(DboTaSoliDevo.CAMPO_MONTO);
		   solDev.find();
		DecimalFormat df = new DecimalFormat("0.00");
		String monto=df.format(Double.parseDouble(solDev.getField(DboTaSoliDevo.CAMPO_MONTO)));
		  return monto;
   	
	}
   catch (Exception e) 
	 {
		 throw e;
	 }
}  
	 public static String obtenerTotalUsuario(String solDevId, DBConnection dconn) throws Exception{
   	
		try {
		 DboTaSoliDevo solDev= new DboTaSoliDevo();
		 solDev.setConnection(dconn);
		 solDev.setField(DboTaSoliDevo.CAMPO_ID_SOLI_DEVO,solDevId);
		 solDev.setFieldsToRetrieve(DboTaSoliDevo.CAMPO_MONTO);
		 solDev.find();
		
		 DecimalFormat df = new DecimalFormat("0.00");
		 String monto=df.format(Double.parseDouble(solDev.getField(DboTaSoliDevo.CAMPO_MONTO)));
		return monto;
   	
	   }
  
		catch (Exception e) 
		{
			throw e;
		}
	 }  

}
