package gob.pe.sunarp.extranet.publicidad.certificada;

import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.MailDataBean;
import gob.pe.sunarp.extranet.common.MailProcessor;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboCertificado;
import gob.pe.sunarp.extranet.dbobj.DboConsumoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboCuenta;
import gob.pe.sunarp.extranet.dbobj.DboDestinatario;
import gob.pe.sunarp.extranet.dbobj.DboGrupoLibroArea;
import gob.pe.sunarp.extranet.dbobj.DboObjetoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboPagoSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboPartida;
import gob.pe.sunarp.extranet.dbobj.DboPeJuri;
import gob.pe.sunarp.extranet.dbobj.DboPeNatu;
import gob.pe.sunarp.extranet.dbobj.DboPersona;
import gob.pe.sunarp.extranet.dbobj.DboSgmtSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboSolicitante;
import gob.pe.sunarp.extranet.dbobj.DboSolicitud;
import gob.pe.sunarp.extranet.dbobj.DboSolicitudXCarga;
import gob.pe.sunarp.extranet.dbobj.DboTmCertificados;
import gob.pe.sunarp.extranet.dbobj.DboTmDocIden;
import gob.pe.sunarp.extranet.dbobj.DboTmLibro;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.session.ExpressoHttpSessionBean;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.prepago.bean.AbonoBean;
import gob.pe.sunarp.extranet.prepago.bean.ComprobanteBean;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.*;
//import gob.pe.sunarp.extranet.publicidad.certificada.bean.DatosRegistradorEmisorBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.DetalleSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.PaginacionBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.SgmtSolicitudBean;
import gob.pe.sunarp.extranet.transaction.TipoServicio;
import gob.pe.sunarp.extranet.transaction.Transaction;
import gob.pe.sunarp.extranet.transaction.bean.LogAuditoriaCertificadoBean;
import gob.pe.sunarp.extranet.util.Abono;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.Job003;
import gob.pe.sunarp.extranet.util.LineaPrepago;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.Tarea;
import gob.pe.sunarp.extranet.util.Zona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.StringTokenizer;


public class Solicitud extends SunarpBean{
//	inicio:jrosas 29-05-2007
//	SUNARP-REGMOBCOM: Se agregarán nuevas constantesal objetoe de solicitud
	
	private static final String FLAG_CARGA_LABORAL_VERIFICADOR ="1";
	private static final String FLAG_CARGA_LABORAL_EMISOR_VERIFICADOR ="2";

//	fin:jrosas 28-05-2007


private java.util.List objetoSolicitudList = new ArrayList();//ArrayList objetoSolicitudList = new ArrayList();
private DestinatarioBean destinatarioBean;	
private SolicitanteBean solicitanteBean;
private PagoBean pagoBean;
private UltimaVerificacionBean ultimaVerificacionBean;
private SgmtSolicitudBean sgmtSolBean;
private AbonoBean aboBean;
private DBConnection dconn;
private Connection conn;
private PaginacionBean paginacBean; 
private DatosRegistradorBean datosRegisEmisorBean = new DatosRegistradorBean();
private DatosRegistradorBean datosRegisVerificadorBean = new DatosRegistradorBean();


private String solicitud_id;
private String estado;
private String estado_descripcion;
private String estado_ext_descripcion;
private String cuenta_id;
private String subtotal;
private String gasto_envio;
private String total;
private String ts_crea;
private String ts_modi;
private String usr_crea;
private String usr_modi;
private String tpo_cert_neg;
private String comentario;
private String constancia;
private String tipoPago;
private Hashtable registradores = new Hashtable();
private Propiedades propiedades;
private String descripcion;
/*** inicio:jrosas 03-08-07*****/
private ConstanciaCremBean constanciaCremBean;
/*** fin:jrosas 03-08-07*****/
public void crear()
{
	
}


public Solicitud(){
}


public Solicitud(String solic_id, Connection conn) throws Throwable{	
	setConn(conn);
	recuperarDBdetalleSol(solic_id);
	
}


public ArrayList recuperarSolxRangoFechas(String cuenta_id, String fechainicial, String fechafinal) throws Throwable{
	return recuperarDBSolxRangoFechas(cuenta_id, fechainicial, fechafinal);
}


public ComprobanteBean grabarSolicitud(UsuarioBean userbean) throws Throwable{
	
	grabarDBSolicitud();
	grabarDBObjetoSolicitud();
	grabarDBSolicitante();
	grabarDBDestinatario();

    /* inicio:jrosas 30-05-2007
	   SUNARP-REGMOBCOM: asignacion de flagcargalaboral  */

	ObjetoSolicitudBean objetoSolicitudBean;
	objetoSolicitudBean = ((ObjetoSolicitudBean)objetoSolicitudList.get(0));
	
	// cert. registro de moviliarios de contratos
    if (objetoSolicitudBean.getTpo_cert().equals("N") && objetoSolicitudBean.getCertificado_id().equals("18")){
    	
    	grabarCargaLaboral(this.FLAG_CARGA_LABORAL_VERIFICADOR); //solo hay un verificador
    	
     }else if ((objetoSolicitudBean.getTpo_cert().equals("N")) && (!objetoSolicitudBean.getCertificado_id().equals("18"))){
    	 
    	grabarCargaLaboral(this.FLAG_CARGA_LABORAL_EMISOR_VERIFICADOR); //hay un emisor y un verificador
    	
     }else if (objetoSolicitudBean.getTpo_cert().equals("R")){
    	// cert. de vigencia o gravamen de rmc
       	grabarCargaLaboral(this.FLAG_CARGA_LABORAL_VERIFICADOR); //solo hay un verificador
       	
     }else if (objetoSolicitudBean.getTpo_cert().equals("C")){
    	 //  cert. tipo CREM	
       	grabarCargaLaboral(this.FLAG_CARGA_LABORAL_VERIFICADOR); //solo hay un verificador
       	
     }else if (objetoSolicitudBean.getTpo_cert().equals("D")){
    	 // cert. dominial o de gravamen de rjb
    	 grabarCargaLaboral(this.FLAG_CARGA_LABORAL_EMISOR_VERIFICADOR); //hay un emisor y un verificador
       	
     }else if (objetoSolicitudBean.getTpo_cert().equals("G")){
    	 
       	grabarCargaLaboral(this.FLAG_CARGA_LABORAL_VERIFICADOR); //solo hay un verificador
       	
     }else if(objetoSolicitudBean.getTpo_cert().equals("B"))
	 {
		/*	fin:jrosas 30-05-2007*/
	     // Inicio:mgarate:05/06/2007	
		grabarCargaLaboral(this.FLAG_CARGA_LABORAL_VERIFICADOR);
	 }else if(objetoSolicitudBean.getTpo_cert().equals(Constantes.COPIA_LITERAL_RMC)){
		 //Inicio:jascencio:07/08/2007
		 //CC:REGMOBCON-2006
		 grabarCargaLaboral(this.FLAG_CARGA_LABORAL_VERIFICADOR); //solo hay un verificador
		 //Fin:jascencio
	 }else{
		 grabarCargaLaboral(this.FLAG_CARGA_LABORAL_EMISOR_VERIFICADOR); //hay un emisor y un verificador
	 }
    
	// Fin:mgarate:05/06/2007
	ComprobanteBean beancomp = null;
	if(!(tipoPago.substring(0,1).equals(Constantes.PAGO_LINEA_PREPAGO)) && !(tipoPago.substring(0,1).equals(Constantes.PAGO_TARJETA_DE_CREDITO)))
	{
		beancomp = grabarAbono(userbean);
	}
	return beancomp;
}	


public void actualizaEstadoSol(String est) throws Throwable{
	//inserto un registro en sgmt_solicitud
	grabarSgmtSolicitud(getEstado(), est);
	setEstado(est) ;
	actualizarDBSolicitud();
	
	
}

public void actualizaVeriManuSol() throws Throwable{
	//Actualizo solicitud
	actualizarVeriManuDBObjSolicitud();
	
	
}
protected void actualizarVeriManuDBObjSolicitud() throws Throwable{
	
		DBConnection dconn = new DBConnection(conn);		
		DboObjetoSolicitud dboObjSolicitud = new DboObjetoSolicitud(dconn);							
		
		//Update VeriManu SOLICITUD			
		dboObjSolicitud.setFieldsToUpdate(dboObjSolicitud.CAMPO_TS_VERI_MANU);
		dboObjSolicitud.setField(dboObjSolicitud.CAMPO_TS_VERI_MANU, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));		
		dboObjSolicitud.setField(dboObjSolicitud.CAMPO_OBJETO_SOL_ID, getObjetoSolicitudList(0).getObjeto_sol_id());		
		dboObjSolicitud.update();
		
}

public void grabarSgmtSolicitud(String estado_inicial, String estado_final) throws Throwable{
	SgmtSolicitudBean sgmtSolBean = new SgmtSolicitudBean();
	sgmtSolBean.setSolicitud_id(getSolicitud_id());
	sgmtSolBean.setEstado_inicial(estado_inicial);
	sgmtSolBean.setEstado_final(estado_final);	
	//hphp: se corrigio para que grabe la fecha y la hora
	sgmtSolBean.setTs_movimiento(FechaUtil.stringToOracleString23(FechaUtil.getCurrentDateTime()));	
	sgmtSolBean.setUsr_movimiento(getUsr_modi());
	setSgmtSolBean(sgmtSolBean);
	grabarDBSgmtoSolicitud();
}

public String obtenTarifaServicio(int idServicio, String gla) throws Throwable
{
	String result;
	StringBuffer q = new StringBuffer();
	q.append("SELECT t.prec_ofic FROM tarifa t ")
		.append("WHERE t.servicio_id = "+idServicio+" " +
				"  AND t.cod_grupo_libro_area = '"+gla+"'");
	
	if (Loggy.isTrace(this)) 
		System.out.println("Tarifa QUERY ---> "+q.toString());
	Statement stmt = null;
	ResultSet rset = null;
	try
	{
		stmt = conn.createStatement();
		rset = stmt.executeQuery(q.toString());


		rset.next();
		result = rset.getString("prec_ofic");
	}
	catch(SQLException e)
	{
		throw e;
	}
	finally
	{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return result;
	
}

public String obtenTarifa(String id, String gla) throws Throwable
{
	String result;
	StringBuffer q = new StringBuffer();
	q.append("SELECT t.prec_ofic FROM tm_certificados c, tarifa t ")
		.append("WHERE c.servicio_id = t.servicio_id AND c.certificado_id = '")
		.append(id).append("' AND t.cod_grupo_libro_area = ")
		.append(gla) ;
	if (Loggy.isTrace(this)) 
		System.out.println("Tarifa QUERY ---> "+q.toString());
	Statement stmt = null;
	ResultSet rset = null;
	try
	{
		stmt = conn.createStatement();
		rset = stmt.executeQuery(q.toString());

		rset.next();
		result = rset.getString("prec_ofic");
	}
	catch(SQLException e)
	{
		throw e;
	}
	finally
	{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
//		rset.close();
//		stmt.close();
	}
	return result;
	//select t.prec_ofic from tm_certificados c, tarifa t, tm_servicio s where c.servicio_id=s.servicio_id and t.servicio_id=s.servicio_id and certificado_id= '4'
		
}



protected void grabarDBSolicitud() throws Throwable{
	
		
		
		//Insert SOLICITUD
		DBConnection dconn = new DBConnection(conn);		
		DboSolicitud dboSolicitud = new DboSolicitud(dconn);				
		dboSolicitud.setField(dboSolicitud.CAMPO_ESTADO, getEstado());
		dboSolicitud.setField(dboSolicitud.CAMPO_CUENTA_ID, getCuenta_id());
		dboSolicitud.setField(dboSolicitud.CAMPO_GASTO_ENVIO, getGasto_envio());
		dboSolicitud.setField(dboSolicitud.CAMPO_TS_CREA,FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		dboSolicitud.setField(dboSolicitud.CAMPO_TS_MODI,FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		dboSolicitud.setField(dboSolicitud.CAMPO_USR_CREA,getUsr_crea() );
		dboSolicitud.setField(dboSolicitud.CAMPO_USR_MODI,getUsr_modi() );
		
		//Obtener el costo total
		double t1 = 0.0;
		for(int i = 0; i < objetoSolicitudList.size(); i++)
		{
			t1+= Double.parseDouble(((ObjetoSolicitudBean)objetoSolicitudList.get(i)).getSubTotal());
		}
		subtotal = "" + t1;
		if(destinatarioBean.getTpo_env().equals(Constantes.TIPO_DOMICILIO))
		{
			total = "" + (Double.parseDouble(subtotal)+Double.parseDouble(gasto_envio));
		}
		else
		{
			gasto_envio = "0";
			total = subtotal;
		}
		dboSolicitud.setField(dboSolicitud.CAMPO_SUBTOTAL,subtotal);
		dboSolicitud.setField(dboSolicitud.CAMPO_GASTO_ENVIO,gasto_envio);
		dboSolicitud.setField(dboSolicitud.CAMPO_TOTAL,total);
			
		dboSolicitud.add();
		
		solicitud_id = dboSolicitud.getField(DboSolicitud.CAMPO_SOLICITUD_ID);
		aboBean.setMonto_bruto(total);
}


protected void grabarDBObjetoSolicitud() throws Throwable{


		//Insert OBJETO SOLICITUD
		DBConnection dconn = new DBConnection(conn);
		ObjetoSolicitudBean objetoSolicitudBean;
		String fechaAuxDesde=""; String fechaAuxHasta="";
		String uno;
		for(int i = 0; i < objetoSolicitudList.size(); i++)
		{
			//objetoSolicitudBean = new ObjetoSolicitudBean();
			objetoSolicitudBean = ((ObjetoSolicitudBean)objetoSolicitudList.get(i));
			DboObjetoSolicitud dboObjSol = new DboObjetoSolicitud(dconn);		
			dboObjSol.setField(dboObjSol.CAMPO_SOLICITUD_ID, solicitud_id);
			dboObjSol.setField(dboObjSol.CAMPO_REG_PUB_ID, objetoSolicitudBean.getReg_pub_id());
			dboObjSol.setField(dboObjSol.CAMPO_OFIC_REG_ID, objetoSolicitudBean.getOfic_reg_id());
			dboObjSol.setField(dboObjSol.CAMPO_CERTIFICADO_ID, objetoSolicitudBean.getCertificado_id());
			dboObjSol.setField(dboObjSol.CAMPO_REFNUM_PART, objetoSolicitudBean.getRefnum_part());
			dboObjSol.setField(dboObjSol.CAMPO_NS_ASIENTO, objetoSolicitudBean.getNs_asie());
			dboObjSol.setField(dboObjSol.CAMPO_COD_ACTO, objetoSolicitudBean.getCod_acto());
			dboObjSol.setField(dboObjSol.CAMPO_AA_TITU, objetoSolicitudBean.getAa_titu());
			dboObjSol.setField(dboObjSol.CAMPO_NUMPAG, objetoSolicitudBean.getNum_pag());
			dboObjSol.setField(dboObjSol.CAMPO_TPO_PERS, objetoSolicitudBean.getTpo_pers());
			dboObjSol.setField(dboObjSol.CAMPO_APE_PAT, objetoSolicitudBean.getApe_pat());
			dboObjSol.setField(dboObjSol.CAMPO_APE_MAT, objetoSolicitudBean.getApe_mat());
			dboObjSol.setField(dboObjSol.CAMPO_NOMBRES, objetoSolicitudBean.getNombres());
			dboObjSol.setField(dboObjSol.CAMPO_RAZ_SOC, objetoSolicitudBean.getRaz_soc());
			dboObjSol.setField(dboObjSol.CAMPO_SUBTOTAL, objetoSolicitudBean.getSubTotal());
			if(objetoSolicitudBean.getCertificado_id().equals("8"))
			{
				dboObjSol.setField(dboObjSol.CAMPO_NUM_TITU, objetoSolicitudBean.getNum_titu());
				dboObjSol.setField(dboObjSol.CAMPO_AA_TITU, objetoSolicitudBean.getAa_titu());
				dboObjSol.setField(dboObjSol.CAMPO_NS_ASIENTO, objetoSolicitudBean.getNs_asie());
				dboObjSol.setField(dboObjSol.CAMPO_COD_ACTO, objetoSolicitudBean.getCod_acto());
				dboObjSol.setField(dboObjSol.CAMPO_NUM_PLACA, objetoSolicitudBean.getPlaca());
				if(objetoSolicitudBean.getNs_asie_placa()!=null && !objetoSolicitudBean.getNs_asie_placa().equals(""))
					dboObjSol.setField(dboObjSol.CAMPO_NS_ASIE_PLACA, objetoSolicitudBean.getNs_asie_placa());
			}
			//inicio:jrosas 30-05-2007
			//SUNARP-REGMOBCOM:Se agregarán campos a la tabla ObjetoSolicitud  
			if (objetoSolicitudBean.getTpo_cert().equals("N")){
				if (objetoSolicitudBean.getCertificado_id().equals("18")){
					dboObjSol.setField(dboObjSol.CAMPO_NUM_PLACA, objetoSolicitudBean.getPlaca());
					dboObjSol.setField(dboObjSol.CAMPO_NUM_MATRICULA, objetoSolicitudBean.getNumeroMatricula());
					dboObjSol.setField(dboObjSol.CAMPO_NOMBRE_BIEN, objetoSolicitudBean.getNombreBien());
					dboObjSol.setField(dboObjSol.CAMPO_NUM_SERIE, objetoSolicitudBean.getNumeroSerie());
				}
			}
			if (objetoSolicitudBean.getTpo_cert().equals("R")){
				dboObjSol.setField(dboObjSol.CAMPO_SIGLAS, objetoSolicitudBean.getSiglas());
				dboObjSol.setField(dboObjSol.CAMPO_TIP_PARTICIPANTE, objetoSolicitudBean.getTipoParticipante());
				dboObjSol.setField(dboObjSol.CAMPO_TIP_DOCUMENTO, objetoSolicitudBean.getTipoDocumento());
				dboObjSol.setField(dboObjSol.CAMPO_NUM_DOCUMENTO, objetoSolicitudBean.getNumeroDocumento());
			}
			
			if (objetoSolicitudBean.getTpo_cert().equals("C")){
				if (objetoSolicitudBean.getCertificado_id().equals("21") || objetoSolicitudBean.getCertificado_id().equals("22")){
					dboObjSol.setField(dboObjSol.CAMPO_TIP_PARTICIPANTE, objetoSolicitudBean.getTipoParticipante());
				}
				if (objetoSolicitudBean.getCertificado_id().equals("23")){
					dboObjSol.setField(dboObjSol.CAMPO_TIP_PARTICIPANTE, objetoSolicitudBean.getTipoParticipante());
					dboObjSol.setField(dboObjSol.CAMPO_TIP_REGISTRO, objetoSolicitudBean.getTipoRegistro());
					dboObjSol.setField(dboObjSol.CAMPO_FLAG_HISTORICO, objetoSolicitudBean.getFlagHistorico());
					if (objetoSolicitudBean.getFechaInscripcionASientoDesde()==null)  fechaAuxDesde="";
					if (objetoSolicitudBean.getFechaInscripcionASientoHasta()==null)  fechaAuxHasta="";
					dboObjSol.setField(dboObjSol.CAMPO_FEC_INS_ASIENTO_DESDE, FechaUtil.dateTimeToStringToOracle(FechaUtil.invertFechaToAmerican(objetoSolicitudBean.getFechaInscripcionASientoDesde())));
					dboObjSol.setField(dboObjSol.CAMPO_FEC_INS_ASIENTO_HASTA,FechaUtil.dateTimeToStringToOracle(FechaUtil.invertFechaToAmerican(objetoSolicitudBean.getFechaInscripcionASientoHasta()))); 
				}
			}
			if (objetoSolicitudBean.getTpo_cert().equals("D") || objetoSolicitudBean.getTpo_cert().equals("G")){
				//tipo:vehicular
				if (objetoSolicitudBean.getCertificado_id().equals("25") || objetoSolicitudBean.getCertificado_id().equals("29")){
					dboObjSol.setField(dboObjSol.CAMPO_NUM_PLACA, objetoSolicitudBean.getPlaca());
					dboObjSol.setField(dboObjSol.CAMPO_NUMERO_PARTIDA, objetoSolicitudBean.getNumeroPartida());
				}
				//tipo:buques
				if (objetoSolicitudBean.getCertificado_id().equals("26") || objetoSolicitudBean.getCertificado_id().equals("30")){
					dboObjSol.setField(dboObjSol.CAMPO_NUM_MATRICULA, objetoSolicitudBean.getNumeroMatricula());
					dboObjSol.setField(dboObjSol.CAMPO_NUMERO_PARTIDA, objetoSolicitudBean.getNumeroPartida());
					dboObjSol.setField(dboObjSol.CAMPO_NOMBRE_BIEN, objetoSolicitudBean.getNombreBien());
				}
				//tipo:aeronaves
				if (objetoSolicitudBean.getCertificado_id().equals("27") || objetoSolicitudBean.getCertificado_id().equals("31")){
					dboObjSol.setField(dboObjSol.CAMPO_NUM_SERIE, objetoSolicitudBean.getNumeroSerie());
					dboObjSol.setField(dboObjSol.CAMPO_NUM_MATRICULA, objetoSolicitudBean.getNumeroMatricula());
					dboObjSol.setField(dboObjSol.CAMPO_NUMERO_PARTIDA, objetoSolicitudBean.getNumeroPartida());
				}	
				//tipo:embarcaciones pesqueras
				if (objetoSolicitudBean.getCertificado_id().equals("28") || objetoSolicitudBean.getCertificado_id().equals("32")){
					dboObjSol.setField(dboObjSol.CAMPO_NUM_MATRICULA, objetoSolicitudBean.getNumeroMatricula());
					dboObjSol.setField(dboObjSol.CAMPO_NUMERO_PARTIDA, objetoSolicitudBean.getNumeroPartida());
					dboObjSol.setField(dboObjSol.CAMPO_NOMBRE_BIEN, objetoSolicitudBean.getNombreBien());

				}	
			}
			if (objetoSolicitudBean.getTpo_cert().equals("D")){
				dboObjSol.setField(dboObjSol.CAMPO_TIP_INF_DOMINIO, objetoSolicitudBean.getTipoInformacionDominio());
			}
				
			//fin:jrosas 30-05-2007
			//Inicio:jascencio:10/08/2007
			//CC: SUNARP REGMOBCON-2006
			if(objetoSolicitudBean.getTpo_cert().equals(Constantes.COPIA_LITERAL_RMC)){
					dboObjSol.setField(dboObjSol.CAMPO_REFNUM_PART_ANTIGUO,objetoSolicitudBean.getRefNumParAnterior());
				
			}
			//Fin:jascencio
			//Inicio:mgarate:04/06/2007
			if(objetoSolicitudBean.getTpo_cert().equals("B"))
			{
			  if(objetoSolicitudBean.getCertificado_id().equals("33"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  if(objetoSolicitudBean.getCertificado_id().equals("34"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  if(objetoSolicitudBean.getCertificado_id().equals("35"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  if(objetoSolicitudBean.getCertificado_id().equals("36"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  // Inicio:mgarate:19/06/2007
			  if(objetoSolicitudBean.getCertificado_id().equals("37"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  if(objetoSolicitudBean.getCertificado_id().equals("38"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  if(objetoSolicitudBean.getCertificado_id().equals("39"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  if(objetoSolicitudBean.getCertificado_id().equals("40"))
			  {
				  dboObjSol.setField(dboObjSol.CAMPO_URL_BUSQ,objetoSolicitudBean.getUrlBusqueda());
				  dboObjSol.setField(dboObjSol.CAMPO_CRIT_URL,objetoSolicitudBean.getCriterioBusqueda());
			  }
			  // Fin:mgarate:19/06/2007
			}
			//Fin:mgarate:04/06/2007
			//inicio:dbravo:10/08/2007
			if(objetoSolicitudBean.getFlagAceptaCondicion()!=null){
				dboObjSol.setField(dboObjSol.CAMPO_FLAG_ACEPTA_CONDICION,objetoSolicitudBean.getFlagAceptaCondicion());
			}
			if(objetoSolicitudBean.getFlagEnvioDomicilio()!=null){
				dboObjSol.setField(dboObjSol.CAMPO_FLAG_ENVIO_DOMICILIO,objetoSolicitudBean.getFlagEnvioDomicilio());
			}
			//fin:dbravo:10/08/2007
			
			dboObjSol.add();
			objetoSolicitudBean.setObjeto_sol_id(dboObjSol.getField(dboObjSol.CAMPO_OBJETO_SOL_ID));
		}		
}


protected void grabarDBSolicitante() throws Throwable{
		
		solicitanteBean.setTs_crea(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		
		//Insert SOLICITANTE
		DBConnection dconn = new DBConnection(conn);
		
		DboSolicitante dboSolicitante = new DboSolicitante(dconn);		
		dboSolicitante.setField(dboSolicitante.CAMPO_SOLICITUD_ID, solicitud_id);
		dboSolicitante.setField(dboSolicitante.CAMPO_TPO_PERS, solicitanteBean.getTpo_pers());
		dboSolicitante.setField(dboSolicitante.CAMPO_APE_PAT, solicitanteBean.getApe_pat());
		dboSolicitante.setField(dboSolicitante.CAMPO_APE_MAT, solicitanteBean.getApe_mat());
		dboSolicitante.setField(dboSolicitante.CAMPO_NOMBRES, solicitanteBean.getNombres());
		dboSolicitante.setField(dboSolicitante.CAMPO_RAZ_SOC, solicitanteBean.getRaz_soc());
		dboSolicitante.setField(dboSolicitante.CAMPO_TIPO_DOC_ID, solicitanteBean.getTipo_doc_id());
		dboSolicitante.setField(dboSolicitante.CAMPO_NUM_DOC_IDEN, solicitanteBean.getNum_doc_iden());
		dboSolicitante.setField(dboSolicitante.CAMPO_EMAIL, solicitanteBean.getEmail());
		dboSolicitante.setField(dboSolicitante.CAMPO_TS_CREA, solicitanteBean.getTs_crea());			
		dboSolicitante.add();
		
		solicitanteBean.setSolicitante_id(dboSolicitante.getField(dboSolicitante.CAMPO_SOLICITANTE_ID));		
}


protected void grabarDBDestinatario() throws Throwable{
	
		destinatarioBean.setTs_crea(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		//Insert DESTINATARIO
		DBConnection dconn = new DBConnection(conn);
		
		DboDestinatario dbodestinat = new DboDestinatario(dconn);		
		dbodestinat.setField(dbodestinat.CAMPO_SOLICITUD_ID, solicitud_id);
		dbodestinat.setField(dbodestinat.CAMPO_TPO_ENV, destinatarioBean.getTpo_env());
		if(destinatarioBean.getTpo_env().equals(Constantes.TIPO_DOMICILIO))
		{		
			dbodestinat.setField(dbodestinat.CAMPO_PAIS_ID, destinatarioBean.getPais_id());
			dbodestinat.setField(dbodestinat.CAMPO_DPTO_ID, destinatarioBean.getDpto_id());		
			dbodestinat.setField(dbodestinat.CAMPO_PROV_ID, destinatarioBean.getProv_id());		
			dbodestinat.setField(dbodestinat.CAMPO_DIRECC, destinatarioBean.getDirecc());
			dbodestinat.setField(dbodestinat.CAMPO_DPTO_OTRO, destinatarioBean.getDpto_otro());
			dbodestinat.setField(dbodestinat.CAMPO_DISTRITO, destinatarioBean.getDistrito());
			dbodestinat.setField(dbodestinat.CAMPO_COD_POST, destinatarioBean.getCod_post());
			
			Zona zona = new Zona();
			zona.setConn(dconn);
			//zona.setUsuario(usuario);
			zona.setPaisId(destinatarioBean.getPais_id());
			zona.setDepartamentoId(destinatarioBean.getDpto_id());
			zona.setProvinciaId(destinatarioBean.getProv_id());
			zona.calculaZona();
			destinatarioBean.setOfic_reg_id(zona.getOficRegId());
			destinatarioBean.setReg_pub_id(zona.getRegPubId());
			
		}
		dbodestinat.setField(dbodestinat.CAMPO_OFIC_REG_ID, destinatarioBean.getOfic_reg_id());
		dbodestinat.setField(dbodestinat.CAMPO_REG_PUB_ID, destinatarioBean.getReg_pub_id());
		dbodestinat.setField(dbodestinat.CAMPO_TPO_PERS, destinatarioBean.getTpo_pers());
		dbodestinat.setField(dbodestinat.CAMPO_APE_PAT, destinatarioBean.getApe_pat());
		dbodestinat.setField(dbodestinat.CAMPO_APE_MAT, destinatarioBean.getApe_mat());
		dbodestinat.setField(dbodestinat.CAMPO_NOMBRES, destinatarioBean.getNombres());
		dbodestinat.setField(dbodestinat.CAMPO_RAZ_SOC, destinatarioBean.getRaz_soc());
		dbodestinat.setField(dbodestinat.CAMPO_TIPO_DOC_ID, destinatarioBean.getTipo_doc_id());
		dbodestinat.setField(dbodestinat.CAMPO_NUM_DOC_IDEN, destinatarioBean.getNum_doc_iden());
		dbodestinat.setField(dbodestinat.CAMPO_EMAIL, destinatarioBean.getEmail());
		dbodestinat.setField(dbodestinat.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));	
		dbodestinat.add();		
		destinatarioBean.setDestinatario_id(dbodestinat.getField(dbodestinat.CAMPO_DESTINATARIO_ID));		
}


private void grabarCargaLaboral(String flagcargalaboral) throws Throwable
{
    /* inicio:jrosas 30-05-2007
	   SUNARP-REGMOBCOM: asignacion de flagcargalaboral  */
	String obs_ofic="";
	String obs_reg="";
	String obs_cer="";
	String obs_lib="";
	
	if (flagcargalaboral.equals("2")){ 
		// se registra verificador y emisor 
		obs_ofic = ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getOfic_reg_id();
		obs_reg = ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getReg_pub_id();
		obs_cer = ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getCertificado_id();
		obs_lib = ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getLibro();
	}else{
		// se registra solo verificador; flagcargalaboral=1
		obs_ofic = this.getDestinatarioBean().getOfic_reg_id();
		obs_reg = this.getDestinatarioBean().getReg_pub_id();
		obs_cer = ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getCertificado_id();
		obs_lib = ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getLibro();
	}
	
	/* fin:jrosas 30-05-2007 */
	
	StringBuffer q = new StringBuffer();
	q.append("SELECT cl.cuenta_id, pers.EMAIL, count(sl.cta_id_reg) as pend ")
		.append("FROM USER1.cuenta cl, USER1.solicitud_x_carga sl, USER1.criterios_asigna ca, USER1.tm_grupo g, ")
		.append("USER1.grupo_libro gl, USER1.pe_natu pn, USER1.persona pers ")
		.append("WHERE sl.estado(+)='").append(Constantes.ESTADO_ATEN_PENDIENTE).append("' ")
		.append("AND ca.cuenta_id=cl.cuenta_id ").append("AND pn.PE_NATU_ID = cl.PE_NATU_ID ")
		.append("AND pn.PERSONA_ID = pers.PERSONA_ID ").append("AND ca.grupo_id=g.grupo_id(+) ")
		.append("AND g.grupo_id=gl.grupo_id(+) ").append("AND cl.estado='1' ")
		.append("AND ca.ofic_reg_id='").append(obs_ofic).append("' ").append("AND ca.reg_pub_id='").append(obs_reg).append("' ");
	if(obs_lib!=null)
	{
		q.append(" AND gl.cod_libro='").append(obs_lib).append("' ");
		if(obs_cer != null && obs_cer.length()>0 && (obs_cer.equals(Constantes.COD_CERTIFICADO_COPIA_LITERAL_RMC) || obs_cer.equals(Constantes.COD_CERTIFICADO_COPIA_LITERAL_ASIENTO_RMC))){
			q.append(" AND ca.certificado_id='").append(obs_cer).append("' ");
		}
	}
	else
	{
		q.append(" AND ca.certificado_id='").append(obs_cer).append("' ");
	}
	q.append("AND ca.estado='1' ")
		.append("AND sl.cta_id_reg(+)=cl.cuenta_id ").append("GROUP BY cl.cuenta_id, pers.EMAIL ORDER BY pend ");


	if (Loggy.isTrace(this)) 
		System.out.println("Carga QUERY 1---> "+q.toString());
	
	
	Statement stmt = null;
	ResultSet rset = null;
	try	{
		stmt = conn.createStatement();
		rset = stmt.executeQuery(q.toString());
		boolean b = rset.next();
		if (!b)
			throw new CustomException(Errors.EC_NO_REGISTRATOR, "Oficina de "+((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getDesc_regis()+" no tiene Registrador disponible para "+((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getDesc_certi());
	
		String cuenta_ver = rset.getString("cuenta_id");
		String cuenta_emi; 
		datosRegisVerificadorBean.setCorreo_electronico(rset.getString("email"));
		if(!(tipoPago.substring(0,1).equals(Constantes.PAGO_TARJETA_DE_CREDITO)))
		{
			MailDataBean mailBean = new MailDataBean();
			mailBean.setTo(datosRegisVerificadorBean.getCorreo_electronico());
			mailBean.setSubject("Solicitud de Publicidad Certificada No. " + getSolicitud_id());
			mailBean.setBody("Ud ha recibido una nueva solicitud. Ingrese a la extranet y revise la solicitud "+ getSolicitud_id());
			MailProcessor.getInstance().saveMail(mailBean, conn);
		}
	
		if((obs_ofic.equals(destinatarioBean.getOfic_reg_id())) && (obs_reg.equals(destinatarioBean.getReg_pub_id())))
		{
			cuenta_emi = cuenta_ver;
			datosRegisEmisorBean.setCorreo_electronico(datosRegisVerificadorBean.getCorreo_electronico());
		}
		else
		{
			q = new StringBuffer();
			q.append("SELECT cl.cuenta_id, pers.EMAIL, count(sl.cta_id_reg) as pend ")
				.append("FROM USER1.cuenta cl, USER1.solicitud_x_carga sl, USER1.criterios_asigna ca, USER1.tm_grupo g, ")
				.append("USER1.grupo_libro gl, USER1.pe_natu pn, USER1.persona pers ")
				.append("WHERE sl.estado(+)='").append(Constantes.ESTADO_ATEN_PENDIENTE).append("' ")
				.append("AND ca.cuenta_id=cl.cuenta_id ")
				.append("AND pn.PE_NATU_ID = cl.PE_NATU_ID ")
				.append("AND pn.PERSONA_ID = pers.PERSONA_ID ")
				.append("AND ca.grupo_id=g.grupo_id(+) ")
				.append("AND g.grupo_id=gl.grupo_id(+) ")
				.append("AND cl.estado='1' ")
				.append("AND ca.ofic_reg_id='").append(destinatarioBean.getOfic_reg_id()).append("' ")
				.append("AND ca.reg_pub_id='").append(destinatarioBean.getReg_pub_id()).append("' ");
			if(obs_lib!=null)
			{
				q.append(" AND gl.cod_libro='").append(obs_lib).append("' ");
			}
			else
			{
				q.append(" AND ca.certificado_id='").append(obs_cer).append("' ");
			}
			q.append("AND ca.estado='1' ")
				.append("AND sl.cta_id_reg(+)=cl.cuenta_id ")
				.append("GROUP BY cl.cuenta_id, pers.EMAIL ORDER BY pend ");
				
			if (Loggy.isTrace(this)) 
				System.out.println("Carga QUERY 2---> "+q.toString());
			//Statement stmt = conn.createStatement();
			rset = stmt.executeQuery(q.toString());
			b = rset.next();
			if(!b)
				throw new CustomException(Errors.EC_NO_REGISTRATOR, "Oficina de "+destinatarioBean.getOfic_reg_desc()+" no tiene Registrador disponible para "+((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getDesc_certi());
			cuenta_emi = rset.getString("cuenta_id");
			datosRegisEmisorBean.setCorreo_electronico(rset.getString("email"));
			/*mailBean.setTo(rset.getString("email"));
			mailBean.setSubject("Solicitud de Publicidad Certificada No. " + getSolicitud_id());
			mailBean.setBody("Ud ha recibido una nueva solicitud. Ingrese a la extranet y revise la solicitud "+ getSolicitud_id());
			MailProcessor.getInstance().saveMail(mailBean, conn);
			*/
		}
		rset.close();
		stmt.close();
		
		DBConnection dconn = new DBConnection(conn);		
		DboSolicitudXCarga dbosolca = new DboSolicitudXCarga(dconn);
		dbosolca.setField(dbosolca.CAMPO_CUENTA_ID, cuenta_ver);
		dbosolca.setField(dbosolca.CAMPO_SOLICITUD_ID, solicitud_id);
		dbosolca.setField(dbosolca.CAMPO_ROL, Constantes.REGIS_VERIFICADOR);
		dbosolca.setField(dbosolca.CAMPO_ESTADO, Constantes.ESTADO_ATEN_PENDIENTE);
		dbosolca.setField(dbosolca.CAMPO_PRIORIDAD, "0");
		dbosolca.setField(dbosolca.CAMPO_CTA_ID_REG, cuenta_ver);
		dbosolca.setField(dbosolca.CAMPO_USR_CREA, usr_crea);
		dbosolca.setField(dbosolca.CAMPO_USR_MODI, usr_modi);
		dbosolca.setField(dbosolca.CAMPO_TS_CREA, FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));
		dbosolca.setField(dbosolca.CAMPO_TS_MODI, FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));
		dbosolca.add();
		getDatosRegisVerificadorBean().setCuentaId(cuenta_ver);
		
		/* inicio:jrosas 30-05-2007
		   SUNARP-REGMOBCOM: guarda el registro de emisor  */
		
		if (flagcargalaboral.equals("2")){ 
			dbosolca.clearAll();
			dbosolca.setField(dbosolca.CAMPO_CUENTA_ID, cuenta_emi);
			dbosolca.setField(dbosolca.CAMPO_SOLICITUD_ID, solicitud_id);
			dbosolca.setField(dbosolca.CAMPO_ROL, Constantes.REGIS_EMISOR);
			dbosolca.setField(dbosolca.CAMPO_ESTADO, Constantes.ESTADO_ATEN_PENDIENTE);
			dbosolca.setField(dbosolca.CAMPO_PRIORIDAD, "0");
			dbosolca.setField(dbosolca.CAMPO_CTA_ID_REG, cuenta_emi);
			dbosolca.setField(dbosolca.CAMPO_USR_CREA, usr_crea);
			dbosolca.setField(dbosolca.CAMPO_USR_MODI, usr_modi);
			dbosolca.setField(dbosolca.CAMPO_TS_CREA, FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));
			dbosolca.setField(dbosolca.CAMPO_TS_MODI, FechaUtil.stringToOracleString(FechaUtil.getCurrentDateTime()));
			dbosolca.add();
			getDatosRegisEmisorBean().setCuentaId(cuenta_emi);
		}else{
			if (flagcargalaboral.equals("1")){
				getDatosRegisEmisorBean().setCuentaId(cuenta_emi);
			}
		}
			
		
		/* fin:jrosas 30-05-2007 */
		
	  }
	  finally{
		   JDBC.getInstance().closeResultSet(rset);
		   JDBC.getInstance().closeStatement(stmt);
	 }
	
}


    protected ComprobanteBean grabarAbono(UsuarioBean usuarioBean)	throws Throwable
	{    
				Abono ab = new Abono();
				ab.setAbono(aboBean);
				ComprobanteBean beancomp = ab.efectuaAbono(conn,usuarioBean);
				//ExpressoHttpSessionBean.getSession(request).setAttribute("comprobante", beancomp);
				return beancomp;
	}


protected void grabarDBPagoSolicitud() throws Throwable{
		//Insert PAGO SOLICITUD
		DBConnection dconn = new DBConnection(conn);
		
		DboPagoSolicitud dboPagosoli = new DboPagoSolicitud(dconn);		
		dboPagosoli.setField(dboPagosoli.CAMPO_SOLICITUD_ID, pagoBean.getSolicitud_id());
		dboPagosoli.setField(dboPagosoli.CAMPO_TPO_PAGO, pagoBean.getTpo_pago());		
		dboPagosoli.setField(dboPagosoli.CAMPO_ABONO_ID, pagoBean.getAbono_id());
		dboPagosoli.setField(dboPagosoli.CAMPO_MONTO, pagoBean.getMonto());		
		dboPagosoli.setField(dboPagosoli.CAMPO_TS_CREA, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		dboPagosoli.setField(dboPagosoli.CAMPO_TS_MODI, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		dboPagosoli.setField(dboPagosoli.CAMPO_USR_CREA, pagoBean.getUsr_crea());
		dboPagosoli.setField(dboPagosoli.CAMPO_USR_MODI, pagoBean.getUsr_modi());		
		dboPagosoli.add();		
		pagoBean.setPago_solicitud_id(dboPagosoli.getField(dboPagosoli.CAMPO_PAGO_SOLICITUD_ID));
}


public void grabarConsumoSol(String consumo, boolean delivery) throws Throwable{
		//Insert PAGO SOLICITUD
		DBConnection dconn = new DBConnection(conn);
		
		DboConsumoSolicitud dboConSol = new DboConsumoSolicitud(dconn);
		dboConSol.setField(dboConSol.CAMPO_SOLICITUD_ID, solicitud_id);
		if(!delivery)
		{
			dboConSol.setField(dboConSol.CAMPO_OBJETO_SOL_ID, ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getObjeto_sol_id());
		}
		dboConSol.setField(dboConSol.CAMPO_CONSUMO_ID, consumo);
		dboConSol.add();		
}
public void recuperaServicio() throws Throwable
{
		//Select from TM_Certificados
		DBConnection dconn = new DBConnection(conn);
		DboTmCertificados dboCerti = new DboTmCertificados(dconn);		
		dboCerti.setField(dboCerti.CAMPO_CERTIFICADO_ID, ((ObjetoSolicitudBean)objetoSolicitudList.get(0)).getCertificado_id());
		dboCerti.setField(dboCerti.CAMPO_ESTADO, "1");
		dboCerti.find();
		((ObjetoSolicitudBean)objetoSolicitudList.get(0)).setServicio_id(dboCerti.getField(dboCerti.CAMPO_SERVICIO_ID));
		//Inicio:jascencio:28/06/2007
			//if(dboCerti.getField(dboCerti.CAMPO_TPO_CERTIFICADO).equals("N"))
		//Fin:jascencio
		//Inicio:jascencio:24/07/2007
		//CC: SUNARP-REGMOBCON-2006
		if(!dboCerti.getField(dboCerti.CAMPO_TPO_CERTIFICADO).equals("L"))
		((ObjetoSolicitudBean)objetoSolicitudList.get(0)).setArea_reg_id(dboCerti.getField(dboCerti.CAMPO_AREA_REG_ID));
		//Fin:jascencio
		
		
		
}
	/**
	 * @autor Daniel Bravo
	 * @param conn
	 * @return String
	 * @throws Throwable
	 * @descripcion recupera el servicio
	 */
	public String recuperaServicio(java.sql.Connection conn, String certificadoId) throws Throwable
	{
		DBConnection dconn = new DBConnection(conn);
		DboTmCertificados dboCerti = new DboTmCertificados(dconn);		
		dboCerti.setField(dboCerti.CAMPO_CERTIFICADO_ID, certificadoId);
		dboCerti.setField(dboCerti.CAMPO_ESTADO, "1");
		dboCerti.find();
		
		return dboCerti.getField(dboCerti.CAMPO_SERVICIO_ID);
	}

public void grabarPago() throws Throwable{
	grabarDBPagoSolicitud();
}

protected void actualizarDBSolicitud() throws Throwable{
		DBConnection dconn = new DBConnection(conn);		
		DboSolicitud dboSolicitud = new DboSolicitud(dconn);							
		
		//Update Estado SOLICITUD			
		dboSolicitud.setFieldsToUpdate(dboSolicitud.CAMPO_ESTADO+"|"+ dboSolicitud.CAMPO_SUBTOTAL+"|"+ dboSolicitud.CAMPO_GASTO_ENVIO+"|"+dboSolicitud.CAMPO_TOTAL+"|"+ dboSolicitud.CAMPO_TS_MODI+"|"+ dboSolicitud.CAMPO_USR_MODI);	
		dboSolicitud.setField(dboSolicitud.CAMPO_ESTADO, getEstado());		
		dboSolicitud.setField(dboSolicitud.CAMPO_SUBTOTAL, getSubtotal());		
		dboSolicitud.setField(dboSolicitud.CAMPO_GASTO_ENVIO, getGasto_envio());		
		dboSolicitud.setField(dboSolicitud.CAMPO_TOTAL, getTotal());		
		dboSolicitud.setField(dboSolicitud.CAMPO_TS_MODI, FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));	
		dboSolicitud.setField(dboSolicitud.CAMPO_USR_MODI, getUsr_modi());	
		dboSolicitud.setField(dboSolicitud.CAMPO_SOLICITUD_ID,solicitud_id);			
		dboSolicitud.update();
}

public void recuperarSolicitudPorVerificar(){
}

protected void grabarDBSgmtoSolicitud() throws Throwable{
		DBConnection dconn = new DBConnection(conn);		
		DboSgmtSolicitud dboSgmtSol = new DboSgmtSolicitud(dconn);		
		//insert Sgmto Solicitud
		dboSgmtSol.setField(dboSgmtSol.CAMPO_SOLICITUD_ID, sgmtSolBean.getSolicitud_id());
		dboSgmtSol.setField(dboSgmtSol.CAMPO_ESTADO_INICIAL, sgmtSolBean.getEstado_inicial());		
		dboSgmtSol.setField(dboSgmtSol.CAMPO_ESTADO_FINAL, sgmtSolBean.getEstado_final());
		dboSgmtSol.setField(dboSgmtSol.CAMPO_TS_MOVIMIENTO, sgmtSolBean.getTs_movimiento());
		dboSgmtSol.setField(dboSgmtSol.CAMPO_USR_MOVIMIENTO, sgmtSolBean.getUsr_movimiento());				
		dboSgmtSol.add();		
		sgmtSolBean.setSgmt_solicitud_id(dboSgmtSol.getField(dboSgmtSol.CAMPO_SGMT_SOLICITUD_ID));
}

public void recuperarSolicitudPorEmitir(){
}
public void emitirSolicitud(){
}

public void despacharSolicitudes(){
}
protected void recuperarDBdetalleSol(String solicitudId) throws CustomException, Throwable
{
	// Inicio:mgarate:05/06/2007
	   DBConnection dconn = new DBConnection(conn);
	   DboObjetoSolicitud objetoSolicitud = new DboObjetoSolicitud(dconn);
	   objetoSolicitud.setField(objetoSolicitud.CAMPO_SOLICITUD_ID, solicitudId );
	   objetoSolicitud.find();
	   String tpoCertificado =  objetoSolicitud.getField(objetoSolicitud.CAMPO_CERTIFICADO_ID);
	// Fin:mgarate:05/06/2007
	   
	
	StringBuffer qdetalleSol = new StringBuffer();
	
	//prepared statement
	StringBuffer quebusq = new StringBuffer();
		
	//Query busqueda de Solicitud
		quebusq.append("SELECT /*+ordered */ a.SOLICITUD_ID, a.ESTADO, b.MENSAJE_REGISTRADOR, b.MENSAJE_USUARIO, d.CERTIFICADO_ID, d.cod_grupo_libro_area, ");
		quebusq.append("d.TPO_CERTIFICADO as TPO_CERT, d.NOMBRE as TIPO_CERTIFICADO, d.AREA_REG_ID as AREA_REGISTRAL_ID,  a.CUENTA_ID, a.SUBTOTAL, ");
		quebusq.append("a.GASTO_ENVIO, a.TS_CREA, a.TS_MODI, a.USR_CREA, a.USR_MODI,  c.OBJETO_SOL_ID, c.TPO_PERS as TPO_PERS_SOL, c.APE_PAT, c.APE_MAT, ");
		quebusq.append("c.NOMBRES, c.RAZ_SOC, c.REFNUM_PART, n.NUM_PARTIDA as NUM_PARTIDA, o.NOMBRE as AREA_REGISTRAL, c.NS_ASIENTO, c.COD_ACTO,c.TIP_DOCUMENTO,c.NUM_DOCUMENTO, ");
		/* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: Agregacion de nuevos campos creados en la tabla objeto solicitud  */
		quebusq.append("c.NUM_PLACA,c.NOMBRE_BIEN,c.NUM_MATRICULA,c.TIP_INF_DOMINIO,c.NUM_PARTIDA as NUMPARTIDA, c.NUM_SERIE, c.SIGLAS,");
		 /*	fin:jrosas 30-05-2007*/
		// Inicio:mgarate:06/06/2007
		quebusq.append("c.CRIT_BUSQ,c.URL_BUSQ, ");
		// Fin:mgarate:06/06/2007
		//Inicio:jascencio:22/06/2007
		quebusq.append("c.TIP_REGISTRO,c.TIP_PARTICIPANTE,c.FLAG_HISTORICO,c.FEC_INS_ASIENTO_DESDE,c.FEC_INS_ASIENTO_HASTA, ");
		//Fin:jascencio
		quebusq.append("acto.descripcion AS desc_acto, c.AA_TITU, c.NUM_TITU, c.NUMPAG, h2.NOMBRE as OFIC_REG_SOL, c.reg_pub_id AS REG_PUB_ID_SOL, ");
		//Inicio:mgarate:07/08/2007
		quebusq.append("c.OFIC_REG_ID, ");
		//Fin:mgarate
		//Inicio:dbravo:10/08/2007
		quebusq.append("c.FLAG_ACEPTA_CONDICION, ");
		quebusq.append("c.FLAG_ENVIO_DOMICILIO, ");
		//Fin:dbravo:10/08/2007
		quebusq.append("e.TPO_PERS as TPO_PERS_SOLICITANTE, e.APE_PAT as APE_PAT_SOLICITANTE, e.APE_MAT as APE_MAT_SOLICITANTE, ");
		quebusq.append("e.NOMBRES as NOMBRES_SOLICITANTE,e.RAZ_SOC as RAZ_SOC_SOLICITANTE, f.DESCRIPCION as TPO_DOC_IDEN, e.NUM_DOC_IDEN, g.TPO_ENV, ");
		quebusq.append("i.NOMBRE as PAIS, j.NOMBRE as DEPARTAMENTO,   k.NOMBRE as PROVINCIA, g.DISTRITO, g.DIRECC, g.COD_POST, ");
		quebusq.append("h1.NOMBRE as OFIC_REG_DESTINATARIO, g.TPO_PERS as TPO_PERS_DESTINAT, g.APE_PAT as APE_PAT_DESTINAT, g.APE_MAT as APE_MAT_DESTINAT, ");
		quebusq.append("g.NOMBRES as NOMBRES_DESTINAT, g.RAZ_SOC as RAZ_SOC_DESTINAT, a.TOTAL as MONTO, to_char(l.TS_MODI, 'dd/mm/yyyy hh24:mi:ss') as PAGO_TS_MODI, to_char(l.TS_CREA, 'dd/mm/yyyy hh24:mi:ss') as PAGO_TS_CREA,");
		quebusq.append("m.TIPO_ABONO, p.TPO_CERTIFICADO as TPO_CERT_NEG, p.COMENTARIO as COMENTARIO_CERT, p.CONSTANCIA as CONSTANCIA_CERT,p.CONSTANCIA2 as CONSTANCIA2_CERT, l.TPO_PAGO,  pe_natu1.APE_PAT as APE_PAT_VE, ");
		quebusq.append("pe_natu1.APE_MAT as APE_MAT_VE, pe_natu1.NOMBRES as NOMBRES_VE, persona1.EMAIL as EMAIL_VE, pe_natu2.APE_PAT as APE_PAT_EM, ");
		quebusq.append("pe_natu2.APE_MAT as APE_MAT_EM, pe_natu2.NOMBRES as NOMBRES_EM, persona2.EMAIL as EMAIL_EM, solxcarg1.CTA_ID_REG as CTA_ID_REG_VE, ");
		quebusq.append("solxcarg2.CTA_ID_REG as CTA_ID_REG_EM ");
		quebusq.append("FROM SOLICITUD a, OBJETO_SOLICITUD c, SOLICITANTE e, DESTINATARIO g, CERTIFICADO p, ");
		quebusq.append("PARTIDA n, SOLICITUD_X_CARGA solxcarg1, SOLICITUD_X_CARGA solxcarg2, ");
		quebusq.append("PAGO_SOLICITUD l, ABONO m, ");
		quebusq.append("TM_ESTADO_SOLICITUD b, TM_CERTIFICADOS d, TM_DOC_IDEN f, ");
		quebusq.append("OFIC_REGISTRAL h1, OFIC_REGISTRAL h2,  TM_PAIS i , TM_DEPARTAMENTO j, TM_PROVINCIA k, ");
		quebusq.append("TM_AREA_REGISTRAL o, CUENTA cuenta1, PE_NATU pe_natu1, PERSONA persona1, ");
		quebusq.append("CUENTA cuenta2, PE_NATU pe_natu2, PERSONA persona2, TM_ACTO acto ");
		quebusq.append("WHERE b.ESTADO_SOLICITUD = a.ESTADO ");
		quebusq.append("AND d.CERTIFICADO_ID  = c.CERTIFICADO_ID ");
		quebusq.append("AND f.TIPO_DOC_ID = e.TIPO_DOC_ID ");
		quebusq.append("AND h2.REG_PUB_ID  = c.REG_PUB_ID ");
		quebusq.append("AND h2.OFIC_REG_ID  = c.OFIC_REG_ID ");
		quebusq.append("AND i.PAIS_ID(+) = g.PAIS_ID ");
		quebusq.append("AND j.DPTO_ID(+) = g.DPTO_ID ");
		quebusq.append("AND j.PAIS_ID(+) = g.PAIS_ID ");
		quebusq.append("AND k.PAIS_ID(+) = g.PAIS_ID ");
		quebusq.append("AND k.DPTO_ID(+) = g.DPTO_ID ");
		quebusq.append("AND k.PROV_ID(+) = g.PROV_ID ");
		quebusq.append("AND h1.REG_PUB_ID  = g.REG_PUB_ID ");
		quebusq.append("AND h1.OFIC_REG_ID = g.OFIC_REG_ID ");
		quebusq.append("AND m.ABONO_ID(+)  = l.ABONO_ID ");
		quebusq.append("AND o.AREA_REG_ID(+) = n.AREA_REG_ID ");
		quebusq.append("AND pe_natu1.PE_NATU_ID = cuenta1.PE_NATU_ID ");
		quebusq.append("AND cuenta1.CUENTA_ID = solxcarg1.CTA_ID_REG ");
		quebusq.append("AND solxcarg1.ROL = 'VE' ");
		quebusq.append("AND solxcarg2.SOLICITUD_ID = a.SOLICITUD_ID ");
		quebusq.append("AND pe_natu1.PERSONA_ID = persona1.PERSONA_ID ");
		quebusq.append("AND pe_natu2.PE_NATU_ID = cuenta2.PE_NATU_ID ");
		quebusq.append("AND cuenta2.CUENTA_ID = solxcarg2.CTA_ID_REG ");
        // Inicio:mgarate:05/06/2007
		// Inicio:jrosas:08/07/07
		//Inicio:jascencio:07/08/2007
		//Se agrego para copia literal rmc
		if(!tpoCertificado.equals("33") && !tpoCertificado.equals("34") && !tpoCertificado.equals("35") && !tpoCertificado.equals("18") &&
		   !tpoCertificado.equals("19")	&& !tpoCertificado.equals("20") && !tpoCertificado.equals("21") && !tpoCertificado.equals("22") &&
		   !tpoCertificado.equals("23") && !tpoCertificado.equals("29") && !tpoCertificado.equals("30") && !tpoCertificado.equals("31") &&
		   !tpoCertificado.equals("32") && !tpoCertificado.equals("36") && !tpoCertificado.equals("37") && !tpoCertificado.equals("38") && 
		   !tpoCertificado.equals("39") && !tpoCertificado.equals("40") && !tpoCertificado.equals(Constantes.COD_CERTIFICADO_COPIA_LITERAL_RMC)) 
		{
			quebusq.append("AND solxcarg2.ROL = 'EM'");
		}
		// fin:
		// Fin:mgarate:05/06/2007
		quebusq.append("AND pe_natu2.PERSONA_ID = persona2.PERSONA_ID ");
		quebusq.append("AND acto.cod_acto(+) = c.COD_ACTO ");
		quebusq.append("AND n.REFNUM_PART(+) = c.REFNUM_PART ");
		quebusq.append("AND solxcarg1.SOLICITUD_ID = a.SOLICITUD_ID ");
		quebusq.append("AND p.SOLICITUD_ID(+) = a.SOLICITUD_ID ");
		quebusq.append("AND l.SOLICITUD_ID(+) = a.SOLICITUD_ID ");
		quebusq.append("AND g.SOLICITUD_ID = a.SOLICITUD_ID ");
		quebusq.append("AND e.SOLICITUD_ID = a.SOLICITUD_ID ");
		quebusq.append("AND c.SOLICITUD_ID = a.SOLICITUD_ID ");
		quebusq.append("AND a.SOLICITUD_ID = ? ");
		if (Loggy.isTrace(this))
			System.out.println("Combo QUERY ---> "+quebusq.toString());

		PreparedStatement pstmt = null;
		ResultSet rset = null;
	try{
		
		pstmt = conn.prepareStatement(quebusq.toString());
		pstmt.setInt(1,Integer.parseInt(solicitudId));
		rset = pstmt.executeQuery();
	
		ArrayList resultado = new ArrayList();
		
		//Solicitud solicitudBean = new Solicitud();
		ObjetoSolicitudBean objSolicBean = new ObjetoSolicitudBean();
		SolicitanteBean solicitanteBean = new SolicitanteBean();
		DestinatarioBean destinatBean = new DestinatarioBean();	
		PagoBean pagoBean = new PagoBean();	
		DatosRegistradorBean datosRegVerifBean = new DatosRegistradorBean();
		DatosRegistradorBean datosRegEmisorBean = new DatosRegistradorBean();
		DecimalFormat df = new DecimalFormat("0.00");
	    String num_placa=null,nombre_bien=null,num_matricula=null,num_serie=null,tip_inf_dominio=null,numero_partida=null;
		/********* inicio: ifigueroa 22/08/2005***************/
	    String tip_documento=null;
	    String num_documento=null;
	    String siglas=null;
	    /********* fin: ifigueroa 22/08/2005***************/
	if(rset.next()){
	
		//Almaceno el resulado en el Bean ObjetoSolicitudBean	
		
		objSolicBean.setSolicitud_id(solicitudId);
		setSolicitud_id(solicitudId);
		setEstado(rset.getString("ESTADO"));		
		setEstado_descripcion(rset.getString("MENSAJE_REGISTRADOR"));
		setEstado_ext_descripcion(rset.getString("MENSAJE_USUARIO"));
		objSolicBean.setObjeto_sol_id(rset.getString("OBJETO_SOL_ID"));
		objSolicBean.setTpo_cert(rset.getString("TPO_CERT"));
		objSolicBean.setCertificado_desc(rset.getString("TIPO_CERTIFICADO"));
		objSolicBean.setCertificado_id(rset.getString("CERTIFICADO_ID"));		
		objSolicBean.setTpo_pers(rset.getString("TPO_PERS_SOL"));
		objSolicBean.setCod_GLA(rset.getString("cod_grupo_libro_area"));
		if (objSolicBean.getTpo_pers()==null){
			objSolicBean.setTpo_pers("");
		}		
		objSolicBean.setApe_pat(rset.getString("APE_PAT"));
		objSolicBean.setApe_mat(rset.getString("APE_MAT"));
		objSolicBean.setNombres(rset.getString("NOMBRES"));
		objSolicBean.setRaz_soc(rset.getString("RAZ_SOC"));
		objSolicBean.setRefnum_part(rset.getString("NUM_PARTIDA"));
		objSolicBean.setRefnum(rset.getString("REFNUM_PART"));		
		objSolicBean.setNs_asiento(rset.getString("NS_ASIENTO"));		
		objSolicBean.setCod_acto(rset.getString("COD_ACTO"));		
		objSolicBean.setDesc_acto(rset.getString("desc_acto"));		
		objSolicBean.setAa_titu(rset.getString("AA_TITU"));		
		objSolicBean.setNum_titu(rset.getString("NUM_TITU"));			
		objSolicBean.setNumpag(rset.getString("NUMPAG"));			
		objSolicBean.setArea_reg_desc(rset.getString("AREA_REGISTRAL"));
		objSolicBean.setArea_reg_id(rset.getString("AREA_REGISTRAL_ID"));
		objSolicBean.setReg_pub_id(rset.getString("REG_PUB_ID_SOL"));
		objSolicBean.setOfic_reg_desc(rset.getString("OFIC_REG_SOL"));
		// Inicio:mgarate:06/06/2007
		objSolicBean.setOfic_reg_id(rset.getString("OFIC_REG_ID"));
		// Fin:mgarate
		
		//inicio:dbravo:10/08/2007
		objSolicBean.setFlagAceptaCondicion(rset.getString("FLAG_ACEPTA_CONDICION"));
		objSolicBean.setFlagEnvioDomicilio(rset.getString("FLAG_ENVIO_DOMICILIO"));
		//fin:dbravo:10/08/2007
		
		
		//Inicio:jascencio:22/06/2007
		objSolicBean.setTipoRegistro(rset.getString("TIP_REGISTRO"));
		if(objSolicBean.getTipoRegistro()!=null){
			if(!objSolicBean.getTipoRegistro().equals("")){
				String codTipoRegistro=objSolicBean.getTipoRegistro();
				String []vecTipoRegistro=codTipoRegistro.split(",");
				StringBuffer desTipoRegistro=new StringBuffer();
				for(int i=0;i<vecTipoRegistro.length;i++){
					String codigo=vecTipoRegistro[i];
					if(i!=0){
						desTipoRegistro.append(",");
					}
					if(codigo.equals(Constantes.TIPO_REGISTRO_MOBILIARIO_CONTRATOS)){
						desTipoRegistro.append("Registro Mobiliario de Contratos");
					}
					else{
						if(codigo.equals(Constantes.TIPO_PROPIEDAD_VEHICULAR)){
							desTipoRegistro.append("Propiedad Vehicular");
						}
						else{
							if(codigo.equals(Constantes.TIPO_PROPIEDAD_EMBARCACION)){
								desTipoRegistro.append("Propiedad Embarcacion Pesquera");
							}
							else{
								if(codigo.equals(Constantes.TIPO_PROPIEDAD_BUQUES)){
									desTipoRegistro.append("Propiedad Buques");
								}
								else{
									if(codigo.equals(Constantes.TIPO_PROPIEDAD_AERONAVES)){
										desTipoRegistro.append("Propiedad Aeronaves");
									}
									else{
										if(codigo.equals(Constantes.TIPO_PERSONAS_JURIDICAS)){
											desTipoRegistro.append("Personas Jurídicas(Participaciones)");
										}
									}
								}
							}
						}
					}
					
				}
				objSolicBean.setDesTipoRegistro(desTipoRegistro.toString());
				
			}
			
		}
		objSolicBean.setFlagHistorico(rset.getString("FLAG_HISTORICO"));
		objSolicBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
		/**** inicio: jrosas 31-08-07 **/
		if (objSolicBean.getTipoParticipante() != null){
			if (objSolicBean.getTipoParticipante().equals("1"))
				objSolicBean.setNombreParticipante("Deudor");
			if (objSolicBean.getTipoParticipante().equals("2"))
				objSolicBean.setNombreParticipante("Acreedor");
			if (objSolicBean.getTipoParticipante().equals("3"))
				objSolicBean.setNombreParticipante("Representante");
			if (objSolicBean.getTipoParticipante().equals("4"))
				objSolicBean.setNombreParticipante("Otros");
		}	
		/**** fin: jrosas 31-08-07 **/
		/*** inicio:jrosas 03-09-07 **/
		String fechaDesdeAux=null;
		String fechaHastaAux=null;
		if (rset.getString("FEC_INS_ASIENTO_DESDE") != null){
			fechaDesdeAux= rset.getString("FEC_INS_ASIENTO_DESDE").substring(0, 11);
			objSolicBean.setFechaInscripcionAsientoDesdeDate(rset.getDate("FEC_INS_ASIENTO_DESDE"));
		}
		if (rset.getString("FEC_INS_ASIENTO_HASTA") != null){
			fechaHastaAux= rset.getString("FEC_INS_ASIENTO_HASTA").substring(0, 11);
			objSolicBean.setFechaInscripcionAsientoHastaDate(rset.getDate("FEC_INS_ASIENTO_HASTA"));
		}
		objSolicBean.setFechaInscripcionASientoDesde(fechaDesdeAux);
		objSolicBean.setFechaInscripcionASientoHasta(fechaHastaAux);
		/*** fin:jrosas 03-09-07 **/
		//Fin:jascencio
		
		if (objSolicBean.getCertificado_id().equals("37"))
		{
			CriterioBean criterioBean =new CriterioBean();
			criterioBean = Tarea.recuperarCriterio(rset.getString("URL_BUSQ")); 
			objSolicBean.setRaz_soc(criterioBean.getRazonSocial());
			objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
		}
		// Fin:mgarate:06/06/2007
		
		 /* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: seteo de nuevos campos en el objetoSolicitudBean  */
		
		num_placa= rset.getString("NUM_PLACA");
		nombre_bien= rset.getString("NOMBRE_BIEN");
		num_matricula= rset.getString("NUM_MATRICULA");
		tip_inf_dominio= rset.getString("TIP_INF_DOMINIO");
		numero_partida= rset.getString("NUMPARTIDA");
		/* inicio: ifigueroa 09/08/2007 */
		num_serie=rset.getString("NUM_SERIE");
		tip_documento=rset.getString("TIP_DOCUMENTO");
		num_documento=rset.getString("NUM_DOCUMENTO");
		siglas=rset.getString("SIGLAS");
		
		/** inicio jrosas 31-08-07 **/
		 DboTmDocIden objetoDocIden = new DboTmDocIden(dconn);
		 objetoDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tip_documento );
		 objetoDocIden.find();
		 String nombreDocumento =  objetoDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV);
		   
		/** fin jrosas 31-08-07 **/
		   
		/* fin: ifigueroa 09/08/2007 */
		/*if (num_placa == null)  num_placa="";
		if (nombre_bien == null) nombre_bien="";
		if (num_matricula == null) num_matricula="";
		if (tip_inf_dominio == null) tip_inf_dominio="";
		if (numero_partida == null) numero_partida="";*/
			
		objSolicBean.setPlaca(num_placa);
		objSolicBean.setNombreBien(nombre_bien);
		objSolicBean.setNumeroMatricula(num_matricula);
		objSolicBean.setTipoInformacionDominio(tip_inf_dominio);
		objSolicBean.setNumeroPartida(numero_partida);
		/* inicio: ifigueroa 09/08/2007 */
		objSolicBean.setNumeroSerie(num_serie);
		objSolicBean.setSiglas(siglas);
		/*** inicio jrosas 31-08-07  ***/
		objSolicBean.setTipoDocumento(tip_documento);
		objSolicBean.setNumeroDocumento(num_documento);
		objSolicBean.setNombreDocumento(nombreDocumento);
		/*** fin jrosas 31-08-07  ***/
		/* fin: ifigueroa 09/08/2007*/
		/* INICIO: ifigueroa 27/08/2007*/
		DboGrupoLibroArea gla= new DboGrupoLibroArea();
		gla.setConnection(dconn);
		gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("cod_grupo_libro_area"));
		gla.find();
		objSolicBean.setDesc_GLA(gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA));
		/* FIN: ifigueroa 27/08/2007*/

		/*	fin:jrosas 31-05-2007*/
		//Inicio:mgarate:06/06/2007
		objSolicBean.setCriterioBusqueda(rset.getString("CRIT_BUSQ"));
		if (objSolicBean.getCertificado_id().equals("33") || objSolicBean.getCertificado_id().equals("34") || objSolicBean.getCertificado_id().equals("35") || 
			objSolicBean.getCertificado_id().equals("36") || objSolicBean.getCertificado_id().equals("37") || objSolicBean.getCertificado_id().equals("38") ||
			objSolicBean.getCertificado_id().equals("39") || objSolicBean.getCertificado_id().equals("40"))
		{
			CriterioBean criterioBean =new CriterioBean();
			criterioBean = Tarea.recuperarCriterio(rset.getString("URL_BUSQ"));
			if(criterioBean.getFlagmetodo().equals("1"))
			{
				objSolicBean.setTomo(criterioBean.getTomo());
				objSolicBean.setFolio(criterioBean.getFolio());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
				objSolicBean.setLibro(criterioBean.getRegistro());
			}else if(criterioBean.getFlagmetodo().equals("2"))
			{
				objSolicBean.setNumPartida(criterioBean.getPartida());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("3"))
			{
				objSolicBean.setFicha(criterioBean.getFicha());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("4"))
			{
				objSolicBean.setNumPartida(criterioBean.getPartida());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("5"))
			{
				objSolicBean.setApe_pat(criterioBean.getApellidoParterno());
				objSolicBean.setApe_mat(criterioBean.getApellidoMaterno());
				objSolicBean.setNombres(criterioBean.getNombre());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("6"))
			{
				objSolicBean.setRaz_soc(criterioBean.getRazonSocial());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("7"))
			{
				objSolicBean.setNumeroMotor(criterioBean.getNumeroMotor());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("8"))
			{
				objSolicBean.setNumeroSerie(criterioBean.getChasis());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("9"))
			{
				objSolicBean.setApe_pat(criterioBean.getApellidoParterno());
				objSolicBean.setApe_mat(criterioBean.getApellidoMaterno());
				objSolicBean.setNombres(criterioBean.getNombre());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("10"))
			{
				objSolicBean.setRaz_soc(criterioBean.getRazonSocial());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("11"))
			{
				objSolicBean.setNumeroMatricula(criterioBean.getNumeroMatricula());
				objSolicBean.setNombreBien(criterioBean.getNombreEmbarcacion());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("12"))
			{
				objSolicBean.setNumeroMatricula(criterioBean.getNumeroMatricula());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("13"))
			{
				objSolicBean.setApe_pat(criterioBean.getApellidoParterno());
				objSolicBean.setApe_mat(criterioBean.getApellidoMaterno());
				objSolicBean.setNombres(criterioBean.getNombre());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("14"))
			{
				objSolicBean.setRaz_soc(criterioBean.getRazonSocial());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("15"))
			{
				objSolicBean.setNumeroMatricula(criterioBean.getNumeroMatricula());
				objSolicBean.setNombreBien(criterioBean.getNombreBuque());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}else if(criterioBean.getFlagmetodo().equals("41"))
			{
				objSolicBean.setPlaca(criterioBean.getPlaca());
				objSolicBean.setReg_pub_id(criterioBean.getZonaOficina());
			}
			objSolicBean.setFlagmetodo(criterioBean.getFlagmetodo());
			objSolicBean.setCod_GLA(criterioBean.getAreaRegistral());
		}
		// Fin:mgarate:06/06/2007
		solicitanteBean.setTpo_pers(rset.getString("TPO_PERS_SOLICITANTE"));
		solicitanteBean.setApe_pat(rset.getString("APE_PAT_SOLICITANTE"));
		solicitanteBean.setApe_mat(rset.getString("APE_MAT_SOLICITANTE"));
		solicitanteBean.setNombres(rset.getString("NOMBRES_SOLICITANTE"));
		solicitanteBean.setRaz_soc(rset.getString("RAZ_SOC_SOLICITANTE"));		
		solicitanteBean.setTipo_doc_id(rset.getString("TPO_DOC_IDEN")); 
		solicitanteBean.setNum_doc_iden(rset.getString("NUM_DOC_IDEN")); 
		destinatBean.setTpo_env(rset.getString("TPO_ENV")); 
		destinatBean.setPais_desc(rset.getString("PAIS"));
		destinatBean.setDpto_desc(rset.getString("DEPARTAMENTO"));
		destinatBean.setProv_desc(rset.getString("PROVINCIA"));
		destinatBean.setDistrito(rset.getString("DISTRITO"));
		destinatBean.setDirecc(rset.getString("DIRECC"));		
		destinatBean.setCod_post(rset.getString("COD_POST"));
		destinatBean.setOfic_reg_desc(rset.getString("OFIC_REG_DESTINATARIO"));
		destinatBean.setTpo_pers(rset.getString("TPO_PERS_DESTINAT"));		
		destinatBean.setApe_pat(rset.getString("APE_PAT_DESTINAT"));
		destinatBean.setApe_mat(rset.getString("APE_MAT_DESTINAT"));
		destinatBean.setNombres(rset.getString("NOMBRES_DESTINAT"));		
		destinatBean.setRaz_soc(rset.getString("RAZ_SOC_DESTINAT"));				
		total=df.format(Integer.parseInt(rset.getString("MONTO")));		
		if(rset.getString("PAGO_TS_CREA")!=null)
		{
			pagoBean.setTs_crea(rset.getString("PAGO_TS_CREA").substring(0,19)); 
			pagoBean.setTs_modi(rset.getString("PAGO_TS_MODI").substring(0,19)); 
			pagoBean.setTipo_abono(rset.getString("TPO_PAGO")); 
		}
		if (rset.getString("TPO_CERT_NEG")!=null && !rset.getString("TPO_CERT_NEG").trim().equals(""))
		{
			setTpo_cert_neg(rset.getString("TPO_CERT_NEG"));
		}
		else
		{
			setTpo_cert_neg("");
		}
		if (rset.getString("COMENTARIO_CERT")!=null && !rset.getString("COMENTARIO_CERT").trim().equals(""))
		{
			setComentario(rset.getString("COMENTARIO_CERT"));
		}
		else		
		{
			setComentario("");
		}
		if (rset.getString("CONSTANCIA_CERT")!=null && !rset.getString("CONSTANCIA_CERT").trim().equals(""))
		{
			setConstancia(rset.getString("CONSTANCIA_CERT"));
		}
		/*** inicio:jrosas 13-06-2007 **/
		else		
		{
			if (rset.getString("CONSTANCIA2_CERT")!=null && !rset.getString("CONSTANCIA2_CERT").trim().equals(""))
			{
				Certificado cert = new Certificado();
				this.setConstancia(cert.leerCampoClob((oracle.sql.CLOB)rset.getClob("CONSTANCIA2_CERT")).toString().trim().replace(" ","&nbsp;").replace("<br>",""));
			}
			else		
			{
				setConstancia("");
			}
		}
		/** fin:jrosas 13-06-2007 ***/

		setCuenta_id(rset.getString("CUENTA_ID"));
		setSubtotal(rset.getString("SUBTOTAL"));
		setGasto_envio(rset.getString("GASTO_ENVIO"));
		setTs_crea(rset.getString("TS_CREA"));
		setTs_modi(rset.getString("TS_MODI"));
		setUsr_crea(rset.getString("USR_CREA"));
		setUsr_modi(rset.getString("USR_MODI"));		
		
		datosRegVerifBean.setCuentaId(rset.getString("CTA_ID_REG_VE"));
		datosRegVerifBean.setApellidoPaterno(rset.getString("APE_PAT_VE"));
		datosRegVerifBean.setApellidoMaterno(rset.getString("APE_MAT_VE"));
		datosRegVerifBean.setNombre(rset.getString("NOMBRES_VE"));	
		datosRegVerifBean.setCorreo_electronico(rset.getString("EMAIL_VE"));		
		//addRegistrador(datosRegBean, Constantes.REGIS_VERIFICADOR);
		datosRegEmisorBean.setCuentaId(rset.getString("CTA_ID_REG_EM"));
		datosRegEmisorBean.setApellidoPaterno(rset.getString("APE_PAT_EM"));
		datosRegEmisorBean.setApellidoMaterno(rset.getString("APE_MAT_EM"));
		datosRegEmisorBean.setNombre(rset.getString("NOMBRES_EM"));			
		datosRegEmisorBean.setCorreo_electronico(rset.getString("EMAIL_EM"));
		//addRegistrador(datosRegBean, Constantes.REGIS_EMISOR);		
		
		addObjetoSolicitudList(objSolicBean);
		setSolicitanteBean(solicitanteBean);
		setDestinatarioBean(destinatBean);
		setDatosRegisVerificadorBean(datosRegVerifBean);
		setDatosRegisEmisorBean(datosRegEmisorBean);
		setPagoBean(pagoBean);
	}
	else
	{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(pstmt);
		throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD,"No se encontro Solicitud " + solicitudId);
	}
	//rset.close();
	//pstmt.close();
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(pstmt);	}
}

public ArrayList recuperarXNroSol(String numSolicitud, String cuentaId) throws Throwable{
	if(cuentaId==null)
		return recuperarDBXNroSolExt(numSolicitud);
	else
		return recuperarDBXNroSol(numSolicitud, cuentaId);
}

public ArrayList recuperarXNroSolBusqueda(String numSolicitud, String cuentaId) throws Throwable{
		return recuperarDBXNroSolBusqueda(numSolicitud);
}
	
protected ArrayList recuperarDBXNroSol(String numSolicitud, String cuentaId) throws Throwable{
	StringBuffer qsel = new StringBuffer();
	
	//Query busqueda de solicitud 
	   qsel.append("SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, f.NOMBRE as TIPO_CERT, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC , c.NOMBRE as NOMBRE_OFIC_REG, ")
 	    .append(" g.MENSAJE_REGISTRADOR, g.MENSAJE_USUARIO, d.NUM_PARTIDA as NUM_PARTIDA, h.NOMBRE as AREA_REGISTRAL, e.ROL, e.CTA_ID_REG, e.ESTADO as ESTADO_SOL_CARG, ")
 	    
 	    /* inicio:jrosas 31-05-2007
	       SUNARP-REGMOBCOM: seteo de nuevos campos de la tabla objetosolicitud  */
	    .append(" b.NUM_PLACA, b.NOMBRE_BIEN, b.NUM_MATRICULA, b.NUM_SERIE, b.SIGLAS, b.TIP_PARTICIPANTE, b.TIP_DOCUMENTO, b.NUM_DOCUMENTO, b.TIP_INF_DOMINIO, b.NUM_PARTIDA as NUMPARTIDA,f.CERTIFICADO_ID,f.COD_GRUPO_LIBRO_AREA, ")
	    /*	fin:jrosas 31-05-2007*/
	    
	    // Inicio:jrosas:31/08/2007
	    .append("b.CRIT_BUSQ, b.TIP_REGISTRO, b.FLAG_HISTORICO, b.FEC_INS_ASIENTO_DESDE, b.FEC_INS_ASIENTO_HASTA ")
	    // Fin:jrosas:31/08/2007
	    
	    .append(" FROM")
	    .append(" SOLICITUD a, OBJETO_SOLICITUD b, OFIC_REGISTRAL c, PARTIDA d, SOLICITUD_X_CARGA e, TM_CERTIFICADOS f, TM_ESTADO_SOLICITUD g, TM_AREA_REGISTRAL h ")
		.append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID")
		.append(" AND b.REG_PUB_ID = c.REG_PUB_ID ")
		.append(" AND b.OFIC_REG_ID = c.OFIC_REG_ID ")
		.append(" AND b.REFNUM_PART = d.REFNUM_PART(+)")
		.append(" AND a.SOLICITUD_ID = e.SOLICITUD_ID")
		.append(" AND b.CERTIFICADO_ID = f.CERTIFICADO_ID")
		.append(" AND a.ESTADO = g.ESTADO_SOLICITUD")
		.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+)")
		.append(" AND a.ESTADO <>'")
		.append(Constantes.ESTADO_SOL_POR_PAGAR).append("'")		
		.append(" AND a.SOLICITUD_ID = ")
		.append(numSolicitud)
		.append(" ORDER BY a.SOLICITUD_ID asc, e.ROL desc");
		
	if (Loggy.isTrace(this))
	System.out.println("Solicitud QUERY ---> "+qsel.toString());

	Statement stmt = null;
	ResultSet rset = null;
	ArrayList arrbuscaList = new ArrayList();
	try{
		stmt = conn.createStatement();
		rset = stmt.executeQuery(qsel.toString());
	
		BuscaCargaLaboralBean buscaCargLabBean = new BuscaCargaLaboralBean();
		
		boolean encontro = false;
//		 inicio: jrosas: 05-09-2007  
		boolean realizada=false;  
//		 fin: jrosas: 05-09-2007
	while (rset.next()){
		
		encontro = true;
		//Almaceno el resulado en el Bean buscaCargLabBean	
		buscaCargLabBean = new BuscaCargaLaboralBean();
		buscaCargLabBean.setSolicitud_id(rset.getString("SOLICITUD_ID"));
		
		//en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
		buscaCargLabBean.setCertificado_id(rset.getString("TPO_CERTIFICADO"));
		// en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
		buscaCargLabBean.setTipoCertificado(rset.getString("CERTIFICADO_ID"));
		//Inicio:mgarate:19/06/2007
		buscaCargLabBean.setOfic_registral(rset.getString("NOMBRE_OFIC_REG"));
		//Fin:mgarate:19/06/2007
		buscaCargLabBean.setNombre_Cert(rset.getString("TIPO_CERT"));
		buscaCargLabBean.setTpo_persona(rset.getString("TPO_PERS"));
		buscaCargLabBean.setEstado_sol_id(rset.getString("ESTADO_SOL"));
		//Inicio:jascencio:25/0607
		if(!(rset.getString("APE_PAT")==null || rset.getString("APE_PAT").equals(""))){
		
			if(!(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").equals(""))){
				if(!(rset.getString("NOMBRES")==null || rset.getString("NOMBRES").equals(""))){
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
				}
				else{
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("APE_MAT"));//Persona Natural
				}
			}
			else{
				buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT"));//Persona Natural
			}
		}
		else{
			buscaCargLabBean.setObjeto_certPN("");//Persona Natural
		}
		
		/*if(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").trim().equals(""))
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("NOMBRES"));//Persona Natural
		else		
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
		*/
		buscaCargLabBean.setObjeto_certPJ(rset.getString("RAZ_SOC"));//Razon Social
		buscaCargLabBean.setNum_partida(rset.getString("NUM_PARTIDA"));		
		buscaCargLabBean.setArea_reg_id(rset.getString("AREA_REGISTRAL"));		
		buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_REGISTRADOR"));	//Estado de la solicitud
		buscaCargLabBean.setEstado_ext_sol(rset.getString("MENSAJE_USUARIO"));	//Estado de la solicitud
		buscaCargLabBean.setCuenta_rol(rset.getString("CTA_ID_REG")); // cuenta que tiene el rol sobre la solicitud
		buscaCargLabBean.setRol(rset.getString("ROL"));	//rol del regitrador responsable
		buscaCargLabBean.setEstado_sol_x_carga(rset.getString("ESTADO_SOL_CARG")); //estado Solcitud x Carga	
		
		/* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: Seteo la descripcion del objeto de solicitud de cada tipo de certificado  */
		
		buscaCargLabBean.setPlaca(rset.getString("NUM_PLACA"));
		buscaCargLabBean.setNombreBien(rset.getString("NOMBRE_BIEN"));
		buscaCargLabBean.setNumeroMatricula(rset.getString("NUM_MATRICULA"));
		buscaCargLabBean.setNumeroSerie(rset.getString("NUM_SERIE"));
		buscaCargLabBean.setSiglas(rset.getString("SIGLAS"));
		buscaCargLabBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
		buscaCargLabBean.setTipoDocumento(rset.getString("TIP_DOCUMENTO"));
		buscaCargLabBean.setNumeroDocumento(rset.getString("NUM_DOCUMENTO"));
		buscaCargLabBean.setTipoInformacionDominio(rset.getString("TIP_INF_DOMINIO"));
		buscaCargLabBean.setNumeroPartida(rset.getString("NUMPARTIDA"));
		
		
		//ObjetoSolicitudBean objSolic= (ObjetoSolicitudBean)this.objetoSolicitudList.get(0);
		//String tipo_certi=objSolic.getTpo_cert();
		//String cert_id= objSolic.getCertificado_id();
		String tipo_certi= rset.getString("TPO_CERTIFICADO");// objSolic.getTpo_cert();
		String cert_id= rset.getString("CERTIFICADO_ID");// objSolic.getCertificado_id();
		
		String desc_aux="";
		String num_placa=null;
		String nombre_bien=null;
		String num_matricula=null;
		String num_serie=null;
		String ape_pat=null;
		String ape_mat=null;
		String nombres=null;
		String siglas=null;
		String raz_social=null;
		String tip_particip=null;
		String tip_docum=null;
		String num_docum=null;
		String tipo_pers=null;
		String tipo_inf_domin=null;
		String num_partida=null;
		String tp_registro= null;
		String tip_registro =  null;
		String flag_historico = null;
		String fec_ins_asiento_desde = null;
		String fec_ins_asiento_hasta =	null;
		
		if (tipo_certi.equals("N")){       // certificado positivo/negativo
			num_placa = rset.getString("NUM_PLACA");
			nombre_bien = rset.getString("NOMBRE_BIEN");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			if (num_placa != null) {
				desc_aux += "NUM_PLACA: "+num_placa;
			}
			if (nombre_bien != null) {
				desc_aux += "NOMBRE_BIEN: "+nombre_bien;
		    /** inicio: jrosas24-08-07 **/
				if (num_matricula != null) {
					desc_aux += ", NUM_MATRICULA: "+num_matricula;
				}	
			}else if (num_matricula != null) {
				desc_aux += "NUM_MATRICULA: "+num_matricula;
			}
			/** fin: jrosas24-08-07 **/
			if (num_serie != null) {
				desc_aux += "NUM_SERIE: "+num_serie;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		if (tipo_certi.equals("R")){     // certificado de vigencia o gravamen
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_docum= rset.getString("TIP_DOCUMENTO");
			num_docum= rset.getString("NUM_DOCUMENTO");
			tipo_pers= rset.getString("TPO_PERS");
			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if (rset.getString("TPO_PERS").equals("J")){	
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			if (tip_docum != null) {
				//Inicio:jascencio:26/06/07
				String desTipoDoc=null;
				if(!tip_docum.equals("")){
					if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LE)){
						desTipoDoc="L.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LM)){
						desTipoDoc="L.M";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CE)){
						desTipoDoc="C.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CI)){
						desTipoDoc="CI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_RUC)){
						desTipoDoc="R.U.C";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS) || tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS2)){
						desTipoDoc="PS";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_DNI)){
						desTipoDoc="DNI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LEM)){
						desTipoDoc="LEM";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CIP)){
						desTipoDoc="C.I.P";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CM)){
						desTipoDoc="C.M";
					}
				}
				//Fin:jascencio
				desc_aux += "TIP_DOCUMENTO: "+desTipoDoc;
			}
			if (num_docum != null) {
				desc_aux += ", NUM_DOCUMENTO: "+num_docum;
			}
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			if (tipo_pers != null) {
				tipo_pers="";
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		if (tipo_certi.equals("D")){       // certificado RJB (Tipo: Dominial )
			tipo_inf_domin = rset.getString("TIP_INF_DOMINIO");
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (tipo_inf_domin != null) {
				if (tipo_inf_domin.equals("C"))
					desc_aux += "TIPO_INF_DOMINIO: Completa, ";
				else
					desc_aux += "TIPO_INF_DOMINIO: Último Propietario, ";
			}
			if (cert_id.equals("25")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("26")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("27")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("28")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("G")){       // certificado RJB (Tipo: Gravamen)
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_serie = rset.getString("NUM_SERIE");
			num_matricula = rset.getString("NUM_MATRICULA");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (cert_id.equals("29")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("30")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("31")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("32")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
				
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		/*	fin:jrosas 31-05-2007*/
		// Inicio:mgarate:05/06/2007
		if (tipo_certi.equals("B"))
		{   
			buscaCargLabBean.setDescripcionObjetoCertificado(rset.getString("CRIT_BUSQ"));
		}
		//Fin:mgarate:05/06/2007
//		Inicio:27/06/07
		if(tipo_certi.equals("C")){//Certificado CREM
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			
			/*** inicio:jrosas 31-08-07 **/
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_registro =  rset.getString("TIP_REGISTRO");
			flag_historico = rset.getString("FLAG_HISTORICO");
			fec_ins_asiento_desde = rset.getString("FEC_INS_ASIENTO_DESDE");
			if (fec_ins_asiento_desde != null)
				fec_ins_asiento_desde = fec_ins_asiento_desde.substring(0, 11);
			fec_ins_asiento_hasta =	rset.getString("FEC_INS_ASIENTO_HASTA");
			if (fec_ins_asiento_hasta != null)
				fec_ins_asiento_hasta = fec_ins_asiento_hasta.substring(0, 11);
			/*** fin:jrosas 31-08-07 **/
			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat; 
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if (rset.getString("TPO_PERS").equals("J")){	
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}
			/*** FIN: Jrosas 05-09-2007 **/
			/*** inicio: jrosas 31-08-07 **/
			ArrayList cadAux= new ArrayList();
			if (tip_registro != null){
				StringTokenizer tokens=new StringTokenizer(tip_registro, ",");
				while (tokens.hasMoreTokens()){
					String str_cod_aux = tokens.nextToken();
					cadAux.add(str_cod_aux);
				}
				desc_aux += ", REGISTROS:";
				int len= cadAux.size();
				String cad_reg="";
				for (int k=0; k<len; k++){
					String str_cod= (String)cadAux.get(k);
					if (k ==(len-1)){
						if (str_cod.equals("RMC")){
							cad_reg += "RMC";
						}
						if (str_cod.equals("VEH")){
							cad_reg += "Vehicular";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones)";
			            }
					}else{
						if (str_cod.equals("RMC")){
							cad_reg += "RMC, ";
						}
					    if (str_cod.equals("VEH")){
					    	cad_reg += "Vehicular, ";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera, ";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques, ";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves, ";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones), ";
			            }
					} //else      
				}//for
				desc_aux += cad_reg;
			}//tipo_registro
			
			if (flag_historico != null){
				if (flag_historico.equals("1"))
					desc_aux += ", HISTÓRICO: Sí";
				else if (flag_historico.equals("0")){
					desc_aux += ", HISTÓRICO: No";
				}
			}
			if (fec_ins_asiento_desde != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO DESDE: "+fec_ins_asiento_desde;
				if (fec_ins_asiento_hasta != null){
					desc_aux += " HASTA "+fec_ins_asiento_hasta;
				}
			}else if (fec_ins_asiento_hasta != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO HASTA: "+fec_ins_asiento_hasta;
			}
			/*** FIN: jrosas 31-08-07 **/
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);

			
		}
		//Fin:jascencio

		  if(!cuentaId.equals(""))
		  {
			//Determinado la accion del registrador sobre la solicitud		
			//Estado de la solicitud  C= por verificar o V= por emitir o E= por despachar

			if (rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_POR_VERIFICAR)||rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_POR_EXPEDIR)||rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_POR_DESPACHAR)){
				
				//Estado de la solicitud  C= por verificar
				if (rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_POR_VERIFICAR)){
					//Estado de la sol x carga
					//VALIDAR LA CUENTA ID DEL REGISTRADOR CORRESPONDA AL USER LOGEADO
					if (buscaCargLabBean.getEstado_sol_x_carga().equals(Constantes.ESTADO_ATEN_PENDIENTE) && buscaCargLabBean.getRol().equals(Constantes.REGIS_VERIFICADOR)&&buscaCargLabBean.getCuenta_rol().equals(cuentaId)){			
						buscaCargLabBean.setAccion(Constantes.REGIS_VERIFICADOR); //muestro la solicitud por Verificar Pendiente 
						arrbuscaList.add(buscaCargLabBean);
					}
				}	
				//Estado de la solicitud  V= por emitir
				if (rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_POR_EXPEDIR)||rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_POR_DESPACHAR)){
					//Estado de la sol x carga
					if (buscaCargLabBean.getEstado_sol_x_carga().equals(Constantes.ESTADO_ATEN_PENDIENTE) && buscaCargLabBean.getRol().equals(Constantes.REGIS_EMISOR)&&buscaCargLabBean.getCuenta_rol().equals(cuentaId) && realizada==false){			
						if ((rset.getString("ESTADO_SOL")).equals(Constantes.ESTADO_SOL_POR_EXPEDIR)){
							//seteo la accion: por expedir
							buscaCargLabBean.setAccion(Constantes.REGIS_EMISOR); //muestro la solicitud por Emitir Pendiente 
						}else{
							//seteo la accion: por despachar
							buscaCargLabBean.setAccion(Constantes.ESTADO_SOL_POR_DESPACHAR); 
							// inicio: jrosas: 05-09-2007
							realizada=true;
							// fin: jrosas: 05-09-2007
						}						
						
						arrbuscaList.add(buscaCargLabBean);
					}
					// inicio: jrosas 17-08-07
					else if (buscaCargLabBean.getEstado_sol_x_carga().equals(Constantes.ESTADO_ATEN_ATENDIDA) && buscaCargLabBean.getRol().equals(Constantes.REGIS_VERIFICADOR)&&buscaCargLabBean.getCuenta_rol().equals(cuentaId) && realizada==false)
					{
						if ((rset.getString("ESTADO_SOL")).equals(Constantes.ESTADO_SOL_POR_DESPACHAR)){
							//Inicio:mgarate:20/08/2007
							// si el tipo es dominial no debe ingresar
							if(!tipo_certi.equals("D"))
							{
								// seteo la accion: por despachar
								buscaCargLabBean.setAccion(Constantes.ESTADO_SOL_POR_DESPACHAR); 
								arrbuscaList.add(buscaCargLabBean);
								//	inicio: jrosas: 05-09-2007
								realizada=true;
								// fin: jrosas: 05-09-2007
							}
							
							//Fin:mgarate
						}	
					}
					
					// fin: jrosas 17-08-07
					//Inicio:mgarate:22/06/2007
					else if( !rset.getString("TPO_CERTIFICADO").equals("L") && !rset.getString("TPO_CERTIFICADO").equals("N") || rset.getString("CERTIFICADO_ID").equals("18") )
					{
						if(!rset.getString("TPO_CERTIFICADO").equals("D")){
							if(!rset.getString("TPO_CERTIFICADO").equals("N") && !rset.getString("TPO_CERTIFICADO").equals("G") && !rset.getString("TPO_CERTIFICADO").equals("R")){
								buscaCargLabBean.setAccion(Constantes.REGIS_EMISOR);
							}else{
								
								buscaCargLabBean.setAccion("");
							}
							arrbuscaList.add(buscaCargLabBean);
						}
						 
						
					}
					//Fin:mgarate:22/06/2007
				}	 
			}
			//Estado de la solicitud  C= por verificar o V= por emitir o E= por despachar
			if (rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_DESPACHADA)||rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_CANCELADA)||rset.getString("ESTADO_SOL").equals(Constantes.ESTADO_SOL_IMPROCEDENTE)){	
				if (buscaCargLabBean.getRol().equals(Constantes.REGIS_VERIFICADOR)){
					//selecciono un registro para este estado de la solcitud 
					arrbuscaList.add(buscaCargLabBean);
					buscaCargLabBean.setAccion(Constantes.ESTADO_SOL_DESPACHADA);					
				}				
			}	
		}
		if(encontro){
		   paginacBean.setEncontro("SI");				
		}else{
		   paginacBean.setEncontro("NO");				
		}
	}
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}	
	return arrbuscaList;
}


protected ArrayList recuperarDBXNroSolBusqueda(String numSolicitud) throws Throwable{
	
	StringBuffer qsel = new StringBuffer();
	
	//Query busqueda de solicitud 
	   qsel.append(" SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, f.NOMBRE as TIPO_CERT, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC, ")
	     /* inicio:jrosas 31-05-2007
	       SUNARP-REGMOBCOM: seteo de nuevos campos de la tabla objetosolicitud  */
	    .append(" b.NUM_PLACA, b.NOMBRE_BIEN, b.NUM_MATRICULA, b.NUM_SERIE, b.SIGLAS, b.TIP_PARTICIPANTE, b.TIP_DOCUMENTO, b.NUM_DOCUMENTO, b.TIP_INF_DOMINIO, b.NUM_PARTIDA as NUMPARTIDA, f.CERTIFICADO_ID, ")
         /*	fin:jrosas 31-05-2007*/
	    // Inicio:mgarate:13/06/2007
	    // Inicio:jrosas 03-09-07 
	    .append(" b.CRIT_BUSQ, b.TIP_REGISTRO, b.FLAG_HISTORICO, b.FEC_INS_ASIENTO_DESDE, b.FEC_INS_ASIENTO_HASTA,f.COD_GRUPO_LIBRO_AREA, ")
	    // fin:jrosas 03-09-07
	    // Fin:mgarate:13/06/2007
 	    .append(" c.NOMBRE as NOMBRE_OFIC_REG,  g.MENSAJE_REGISTRADOR, g.MENSAJE_USUARIO, d.NUM_PARTIDA as NUM_PARTIDA, h.NOMBRE as AREA_REGISTRAL, i.TPO_PERS as TPO_PERS_SOLICITANTE, i.APE_MAT as APE_MAT_SOLICITANTE, i.APE_PAT as APE_PAT_SOLICITANTE, i.NOMBRES as NOMBRES_SOLICITANTE, i.RAZ_SOC as RAZ_SOC_SOLICITANTE ")
	    .append(" FROM")
	    .append(" SOLICITUD a, OBJETO_SOLICITUD b, OFIC_REGISTRAL c, PARTIDA d, TM_CERTIFICADOS f, TM_ESTADO_SOLICITUD g, TM_AREA_REGISTRAL h, SOLICITANTE i ")
		.append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID")
		.append(" AND a.SOLICITUD_ID = i.SOLICITUD_ID ")
		.append(" AND b.REG_PUB_ID = c.REG_PUB_ID ")
		.append(" AND b.OFIC_REG_ID = c.OFIC_REG_ID ")
		.append(" AND b.REFNUM_PART = d.REFNUM_PART(+)")		
		.append(" AND b.CERTIFICADO_ID = f.CERTIFICADO_ID")
		.append(" AND a.ESTADO = g.ESTADO_SOLICITUD")
		.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+)")
		.append(" AND a.ESTADO <>'")
		.append(Constantes.ESTADO_SOL_POR_PAGAR).append("'")		
		.append(" AND a.SOLICITUD_ID = ")
		.append(numSolicitud)
		.append(" ORDER BY a.SOLICITUD_ID asc");
		
	if (Loggy.isTrace(this))
	System.out.println("Solicitud QUERY ---> "+qsel.toString());
	
	Statement stmt = null;
	ResultSet rset = null;
	ArrayList arrbuscaList = new ArrayList();
	
	try{
		stmt = conn.createStatement();
		rset = stmt.executeQuery(qsel.toString());
		
		BuscaCargaLaboralBean buscaCargLabBean = new BuscaCargaLaboralBean();
		
		boolean encontro = false;
	while (rset.next()){
		
		encontro = true;
		//Almaceno el resulado en el Bean buscaCargLabBean	
		buscaCargLabBean = new BuscaCargaLaboralBean();
		buscaCargLabBean.setSolicitud_id(rset.getString("SOLICITUD_ID"));
		// en Atributo certificado_ID de buscaCargaLaboral se seteo el Tipo de certificado
		buscaCargLabBean.setCertificado_id(rset.getString("TPO_CERTIFICADO"));
		// en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
		buscaCargLabBean.setTipoCertificado(rset.getString("CERTIFICADO_ID"));
		
		buscaCargLabBean.setNombre_Cert(rset.getString("TIPO_CERT"));
		buscaCargLabBean.setTpo_persona(rset.getString("TPO_PERS"));		
		//Inicio:jascencio:25/0607
		if(!(rset.getString("APE_PAT")==null || rset.getString("APE_PAT").equals(""))){
		
			if(!(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").equals(""))){
				if(!(rset.getString("NOMBRES")==null || rset.getString("NOMBRES").equals(""))){
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
				}
				else{
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("APE_MAT"));//Persona Natural
				}
			}
			else{
				buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT"));//Persona Natural
			}
		}
		else{
			buscaCargLabBean.setObjeto_certPN("");//Persona Natural
		}
		
		/*if(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").trim().equals(""))
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("NOMBRES"));//Persona Natural
		else		
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural*/
		//Fin:jascencio
		
		buscaCargLabBean.setObjeto_certPJ(rset.getString("RAZ_SOC"));//Razon Social
		buscaCargLabBean.setNum_partida(rset.getString("NUM_PARTIDA"));		
		buscaCargLabBean.setArea_reg_id(rset.getString("AREA_REGISTRAL"));		
		buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_REGISTRADOR"));	//Estado de la solicitud
		buscaCargLabBean.setEstado_ext_sol(rset.getString("MENSAJE_USUARIO"));	//Estado de la solicitud
		buscaCargLabBean.setOfic_registral(rset.getString("NOMBRE_OFIC_REG"));//Nombre de Oficina Registral						
		buscaCargLabBean.setTpo_pers_solicitante(rset.getString("TPO_PERS_SOLICITANTE"));//tipo persona del solicitante
		if(rset.getString("APE_MAT_SOLICITANTE")==null || rset.getString("APE_MAT_SOLICITANTE").trim().equals(""))
			buscaCargLabBean.setSolicitante_PN(rset.getString("APE_PAT_SOLICITANTE") + " " + rset.getString("NOMBRES_SOLICITANTE"));//
		else		
			buscaCargLabBean.setSolicitante_PN(rset.getString("APE_PAT_SOLICITANTE")+" "+rset.getString("APE_MAT_SOLICITANTE")+" "+rset.getString("NOMBRES_SOLICITANTE"));//
		
		buscaCargLabBean.setSolicitante_PJ(rset.getString("RAZ_SOC_SOLICITANTE"));//
		//buscaCargLabBean.setCuenta_rol(rset.getString("CTA_ID_REG")); // cuenta que tiene el rol sobre la solicitud
		//buscaCargLabBean.setRol(rset.getString("ROL"));	//rol del regitrador responsable
		//buscaCargLabBean.setEstado_sol_x_carga(rset.getString("ESTADO_SOL_CARG")); //estado Solcitud x Carga			
		
		/* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: Seteo la descripcion del objeto de solicitud de cada tipo de certificado  */
		
		buscaCargLabBean.setPlaca(rset.getString("NUM_PLACA"));
		buscaCargLabBean.setNombreBien(rset.getString("NOMBRE_BIEN"));
		buscaCargLabBean.setNumeroMatricula(rset.getString("NUM_MATRICULA"));
		buscaCargLabBean.setNumeroSerie(rset.getString("NUM_SERIE"));
		buscaCargLabBean.setSiglas(rset.getString("SIGLAS"));
		buscaCargLabBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
		buscaCargLabBean.setTipoDocumento(rset.getString("TIP_DOCUMENTO"));
		buscaCargLabBean.setNumeroDocumento(rset.getString("NUM_DOCUMENTO"));
		buscaCargLabBean.setTipoInformacionDominio(rset.getString("TIP_INF_DOMINIO"));
		buscaCargLabBean.setNumeroPartida(rset.getString("NUMPARTIDA"));
		buscaCargLabBean.setEstado_sol_id(rset.getString("ESTADO_SOL"));
		//ObjetoSolicitudBean objSolic= (ObjetoSolicitudBean)this.objetoSolicitudList.get(0);
		//String tipo_certi=objSolic.getTpo_cert();
		//String cert_id= objSolic.getCertificado_id();
		String tipo_certi= rset.getString("TPO_CERTIFICADO");// objSolic.getTpo_cert();
		String cert_id= rset.getString("CERTIFICADO_ID");// objSolic.getCertificado_id();

		String desc_aux="";
		String num_placa=null;
		String nombre_bien=null;
		String num_matricula=null;
		String num_serie=null;
		String ape_pat=null;
		String ape_mat=null;
		String nombres=null;
		String siglas=null;
		String raz_social=null;
		String tip_particip=null;
		String tip_docum=null;
		String num_docum=null;
		String tipo_pers=null;
		String tipo_inf_domin=null;
		String num_partida=null;
		// inicio: jrosas 03-09-07
		String tp_registro= null;
		String tip_registro =  null;
		String flag_historico = null;
		String fec_ins_asiento_desde = null;
		String fec_ins_asiento_hasta =	null;
		//fin: jrosas 03-09-07
		//Inicio:mgarate:05/06/2007
		if(tipo_certi.equals("B"))
		{
			buscaCargLabBean.setDescripcionObjetoCertificado(rset.getString("CRIT_BUSQ"));
		}
		//Fin:mgarate:05/06/2007
		if (tipo_certi.equals("N")){       // certificado positivo/negativo
			num_placa = rset.getString("NUM_PLACA");
			nombre_bien = rset.getString("NOMBRE_BIEN");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			if (num_placa != null) {
				desc_aux += "NUM_PLACA: "+num_placa;
			}
			if (nombre_bien != null) {
				desc_aux += "NOMBRE_BIEN: "+nombre_bien;
		    /** inicio: jrosas24-08-07 **/
				if (num_matricula != null) {
					desc_aux += ", NUM_MATRICULA: "+num_matricula;
				}	
			}else if (num_matricula != null) {
				desc_aux += "NUM_MATRICULA: "+num_matricula;
			}
			/** fin: jrosas24-08-07 **/
			if (num_serie != null) {
				desc_aux += "NUM_SERIE: "+num_serie;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("R")){     // certificado de vigencia o gravamen
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_docum= rset.getString("TIP_DOCUMENTO");
			num_docum= rset.getString("NUM_DOCUMENTO");
			tipo_pers= rset.getString("TPO_PERS");
			
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			if (tip_docum != null) {
				//Inicio:jascencio:26/06/07
				String desTipoDoc=null;
				if(!tip_docum.equals("")){
					if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LE)){
						desTipoDoc="L.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LM)){
						desTipoDoc="L.M";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CE)){
						desTipoDoc="C.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CI)){
						desTipoDoc="CI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_RUC)){
						desTipoDoc="R.U.C";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS) || tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS2)){
						desTipoDoc="PS";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_DNI)){
						desTipoDoc="DNI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LEM)){
						desTipoDoc="LEM";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CIP)){
						desTipoDoc="C.I.P";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CM)){
						desTipoDoc="C.M";
					}
				}
				//Fin:jascencio
				desc_aux += "TIP_DOCUMENTO: "+desTipoDoc;
			}
			if (num_docum != null) {
				desc_aux += ", NUM_DOCUMENTO: "+num_docum;
			}
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			if (tipo_pers != null) {
				tipo_pers="";
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		if (tipo_certi.equals("D")){       // certificado RJB (Tipo: Dominial )
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			
			tipo_inf_domin = rset.getString("TIP_INF_DOMINIO");
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			if (tipo_inf_domin != null) {
				if (tipo_inf_domin.equals("C"))
					desc_aux += "TIPO_INF_DOMINIO: Completa, ";
				else
					desc_aux += "TIPO_INF_DOMINIO: Último Propietario, ";
			}
			if (cert_id.equals("25")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("26")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("27")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("28")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
			
		if (tipo_certi.equals("G")){       // certificado RJB (Tipo: Gravamen)
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("cod_grupo_libro_area"));
			gla.find();
			
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			if (cert_id.equals("29")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("30")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("31")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("32")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
				
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		
		}
		//Inicio:jascencio:27/06/07
		if(tipo_certi.equals("C")){//Certificado CREM
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			/*** inicio:jrosas 31-08-07 **/
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_registro =  rset.getString("TIP_REGISTRO");
			flag_historico = rset.getString("FLAG_HISTORICO");
			fec_ins_asiento_desde = rset.getString("FEC_INS_ASIENTO_DESDE");
			if (fec_ins_asiento_desde != null)
				fec_ins_asiento_desde = fec_ins_asiento_desde.substring(0, 11);
			fec_ins_asiento_hasta =	rset.getString("FEC_INS_ASIENTO_HASTA");
			if (fec_ins_asiento_hasta != null)
				fec_ins_asiento_hasta = fec_ins_asiento_hasta.substring(0, 11);
			/*** fin:jrosas 31-08-07 **/

			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			
			/*** inicio: jrosas 31-08-07 **/
			ArrayList cadAux= new ArrayList();
			if (tip_registro != null){
				StringTokenizer tokens=new StringTokenizer(tip_registro, ",");
				while (tokens.hasMoreTokens()){
					String str_cod_aux = tokens.nextToken();
					cadAux.add(str_cod_aux);
				}
				desc_aux += ", REGISTROS:";
				int len= cadAux.size();
				String cad_reg="";
				for (int k=0; k<len; k++){
					String str_cod= (String)cadAux.get(k);
					if (k ==(len-1)){
						if (str_cod.equals("RMC")){
							cad_reg += "RMC";
						}
						if (str_cod.equals("VEH")){
							cad_reg += "Vehicular";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones)";
			            }
					}else{
						if (str_cod.equals("RMC")){
							cad_reg += "RMC, ";
						}
					    if (str_cod.equals("VEH")){
					    	cad_reg += "Vehicular, ";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera, ";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques, ";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves, ";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones), ";
			            }
					} //else      
				}//for
				desc_aux += cad_reg;
			}//tipo_registro
			
			if (flag_historico != null){
				if (flag_historico.equals("1"))
					desc_aux += ", HISTÓRICO: Sí";
				else if (flag_historico.equals("0")){
					desc_aux += ", HISTÓRICO: No";
				}
			}
			if (fec_ins_asiento_desde != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO DESDE: "+fec_ins_asiento_desde;
				if (fec_ins_asiento_hasta != null){
					desc_aux += " HASTA "+fec_ins_asiento_hasta;
				}
			}else if (fec_ins_asiento_hasta != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO HASTA: "+fec_ins_asiento_hasta;
			}
			/*** FIN: jrosas 31-08-07 **/
			
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);

			
		}
		//Fin:jascencio
		/*	fin:jrosas 31-05-2007*/
		
		//inicio:dbravo:20/08/2007
		if(buscaCargLabBean.getTipoCertificado()!=null &&
			(
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)
			)
		){
			if(buscaCargLabBean.getEstado_sol_id()!=null && buscaCargLabBean.getEstado_sol_id().equalsIgnoreCase("E")){
				
				DboCertificado dboCertificado = new DboCertificado();
				dboCertificado.clearAll();
				dboCertificado.setFieldsToRetrieve(DboCertificado.CAMPO_FLAG_PAGO_CREM);
				dboCertificado.setField(DboCertificado.CAMPO_SOLICITUD_ID, buscaCargLabBean.getSolicitud_id());
				if(dboCertificado.find()){
					buscaCargLabBean.setFlagPagoCrem(dboCertificado.getField(DboCertificado.CAMPO_FLAG_PAGO_CREM));
				}
				
			}else{
				buscaCargLabBean.setFlagPagoCrem("1");
			}
			
		}else{
			buscaCargLabBean.setFlagPagoCrem("1");
		}
		//fin:dbravo:20/08/2007
		
		arrbuscaList.add(buscaCargLabBean);		
		if(encontro){
		   paginacBean.setEncontro("SI");				
		}else{
		   paginacBean.setEncontro("NO");				
		}
	}
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return arrbuscaList;
}
protected ArrayList recuperarDBXNroSolExt(String numSolicitud) throws Throwable{
	StringBuffer qsel = new StringBuffer();
	
	//Query busqueda de solicitud
	   qsel.append("SELECT sol.SOLICITUD_ID, sol.ESTADO as ESTADO_SOL, cert.TPO_CERTIFICADO, cert.NOMBRE as TIPO_CERT, obj.TPO_PERS, obj.APE_MAT, obj.APE_PAT, ")
	     /* inicio:jrosas 31-05-2007
	       SUNARP-REGMOBCOM: seteo de nuevos campos de la tabla objetosolicitud  */
	    .append(" obj.NUM_PLACA, obj.NOMBRE_BIEN, obj.NUM_MATRICULA, obj.NUM_SERIE, obj.SIGLAS, obj.TIP_PARTICIPANTE, obj.TIP_DOCUMENTO, obj.NUM_DOCUMENTO, obj.TIP_INF_DOMINIO,obj.NUM_PARTIDA as NUMPARTIDA,cert.CERTIFICADO_ID, ")

	    /*	fin:jrosas 31-05-2007*/
	    // Inicio:mgarate:13/06/2007
	    // inicio: jrosas 03-09-07
	    .append("obj.CRIT_BUSQ, obj.TIP_REGISTRO, obj.FLAG_HISTORICO, obj.FEC_INS_ASIENTO_DESDE, obj.FEC_INS_ASIENTO_HASTA,cert.COD_GRUPO_LIBRO_AREA, ")
	    // fin: jrosas 03-09-07
	    // Fin:mgarate:13/06/2007
	   	.append("obj.NOMBRES, obj.RAZ_SOC , ofi.NOMBRE as NOMBRE_OFIC_REG,  est.MENSAJE_USUARIO, part.NUM_PARTIDA as NUM_PARTIDA, area.NOMBRE as AREA_REGISTRAL ")
	   	.append("FROM SOLICITUD sol, OBJETO_SOLICITUD obj, OFIC_REGISTRAL ofi, PARTIDA part, TM_CERTIFICADOS cert, TM_ESTADO_SOLICITUD est, TM_AREA_REGISTRAL area ")
	   	.append("WHERE sol.SOLICITUD_ID = obj.SOLICITUD_ID AND obj.REG_PUB_ID = ofi.REG_PUB_ID  AND obj.OFIC_REG_ID = ofi.OFIC_REG_ID ")
	   	.append("AND obj.REFNUM_PART = part.REFNUM_PART(+) AND obj.CERTIFICADO_ID = cert.CERTIFICADO_ID ")
	   	.append("AND sol.ESTADO = est.ESTADO_SOLICITUD AND part.AREA_REG_ID = area.AREA_REG_ID(+) AND sol.ESTADO <>'").append(Constantes.ESTADO_SOL_POR_PAGAR)
	   	.append("' AND sol.SOLICITUD_ID = ").append(numSolicitud)
	   	.append(" ORDER BY sol.SOLICITUD_ID asc");
		
		
	if (Loggy.isTrace(this))
		System.out.println("Solicitud QUERY ---> "+qsel.toString());
	
	Statement stmt = null;
	ResultSet rset = null;
	ArrayList arrbuscaList = new ArrayList();
	
	try	{
	stmt = conn.createStatement();
	rset = stmt.executeQuery(qsel.toString());
	
	BuscaCargaLaboralBean buscaCargLabBean = new BuscaCargaLaboralBean();
	
	if (rset.next()){
	
		//Almaceno el resulado en el Bean buscaCargLabBean	
		buscaCargLabBean = new BuscaCargaLaboralBean();
		buscaCargLabBean.setSolicitud_id(rset.getString("SOLICITUD_ID"));
		
		//en Atributo certificado_ID de buscaCargaLaboral se seteo el Tipo de certificado
		buscaCargLabBean.setCertificado_id(rset.getString("TPO_CERTIFICADO"));
		// en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
		
		buscaCargLabBean.setTipoCertificado(rset.getString("CERTIFICADO_ID"));
		buscaCargLabBean.setNombre_Cert(rset.getString("TIPO_CERT"));
		buscaCargLabBean.setTpo_persona("P"+rset.getString("TPO_PERS"));		
		if(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").trim().equals(""))
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("NOMBRES"));//Persona Natural
		else		
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
		buscaCargLabBean.setObjeto_certPJ(rset.getString("RAZ_SOC"));//Razon Social
		buscaCargLabBean.setNum_partida(rset.getString("NUM_PARTIDA"));		
		buscaCargLabBean.setArea_reg_id(rset.getString("AREA_REGISTRAL"));		
		buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_USUARIO"));	//Estado de la solicitud
		buscaCargLabBean.setOfic_registral(rset.getString("NOMBRE_OFIC_REG"));//Nombre de Oficina Registral						
		//buscaCargLabBean.setCuenta_rol(rset.getString("CTA_ID_REG")); // cuenta que tiene el rol sobre la solicitud
		//buscaCargLabBean.setRol(rset.getString("ROL"));	//rol del regitrador responsable
		//buscaCargLabBean.setEstado_sol_x_carga(rset.getString("ESTADO_SOL_CARG")); //estado Solcitud x Carga
		
		/* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: Seteo la descripcion del objeto de solicitud de cada tipo de certificado  */
		
		buscaCargLabBean.setPlaca(rset.getString("NUM_PLACA"));
		buscaCargLabBean.setNombreBien(rset.getString("NOMBRE_BIEN"));
		buscaCargLabBean.setNumeroMatricula(rset.getString("NUM_MATRICULA"));
		buscaCargLabBean.setNumeroSerie(rset.getString("NUM_SERIE"));
		buscaCargLabBean.setSiglas(rset.getString("SIGLAS"));
		buscaCargLabBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
		buscaCargLabBean.setTipoDocumento(rset.getString("TIP_DOCUMENTO"));
		buscaCargLabBean.setNumeroDocumento(rset.getString("NUM_DOCUMENTO"));
		buscaCargLabBean.setTipoInformacionDominio(rset.getString("TIP_INF_DOMINIO"));
		buscaCargLabBean.setNumeroPartida(rset.getString("NUMPARTIDA"));
		
		buscaCargLabBean.setEstado_sol_id(rset.getString("ESTADO_SOL"));
		
		//ObjetoSolicitudBean objSolic= (ObjetoSolicitudBean)this.objetoSolicitudList.get(0);
		//String tipo_certi=objSolic.getTpo_cert();
		//String cert_id= objSolic.getCertificado_id();
		String tipo_certi= rset.getString("TPO_CERTIFICADO");// objSolic.getTpo_cert();
		String cert_id= rset.getString("CERTIFICADO_ID");// objSolic.getCertificado_id();
		
		String desc_aux="";
		String num_placa=null;
		String nombre_bien=null;
		String num_matricula=null;
		String num_serie=null;
		String ape_pat=null;
		String ape_mat=null;
		String nombres=null;
		String siglas=null;
		String raz_social=null;
		String tip_particip=null;
		String tip_docum=null;
		String num_docum=null;
		String tipo_pers=null;
		String tipo_inf_domin=null;
		String num_partida=null;
		/*** inicio:jrosas 03-09-07 **/
		String tp_registro= null;
		String tip_registro =  null;
		String flag_historico = null;
		String fec_ins_asiento_desde = null;
		String fec_ins_asiento_hasta =	null;
		/*** fin:jrosas 03-09-07 **/
		//Inicio:mgarate:13/06/2007
		if (tipo_certi.equals("B"))
		{
			buscaCargLabBean.setDescripcionObjetoCertificado(rset.getString("CRIT_BUSQ"));
		}
		//Fin:mgarate:13/06/2007
		if (tipo_certi.equals("N")){       // certificado positivo/negativo
			num_placa = rset.getString("NUM_PLACA");
			nombre_bien = rset.getString("NOMBRE_BIEN");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			if (num_placa != null) {
				desc_aux += "NUM_PLACA: "+num_placa;
			}
			if (nombre_bien != null) {
				desc_aux += "NOMBRE_BIEN: "+nombre_bien;
		    /** inicio: jrosas24-08-07 **/
				if (num_matricula != null) {
					desc_aux += ", NUM_MATRICULA: "+num_matricula;
				}	
			}else if (num_matricula != null) {
				desc_aux += "NUM_MATRICULA: "+num_matricula;
			}
			/** fin: jrosas24-08-07 **/
			if (num_serie != null) {
				desc_aux += "NUM_SERIE: "+num_serie;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("R")){     // certificado de vigencia o gravamen
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_docum= rset.getString("TIP_DOCUMENTO");
			num_docum= rset.getString("NUM_DOCUMENTO");
			tipo_pers= rset.getString("TPO_PERS");
			
			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			if (tip_docum != null) {
				//Inicio:jascencio:26/06/07
				String desTipoDoc=null;
				if(!tip_docum.equals("")){
					if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LE)){
						desTipoDoc="L.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LM)){
						desTipoDoc="L.M";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CE)){
						desTipoDoc="C.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CI)){
						desTipoDoc="CI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_RUC)){
						desTipoDoc="R.U.C";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS) || tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS2)){
						desTipoDoc="PS";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_DNI)){
						desTipoDoc="DNI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LEM)){
						desTipoDoc="LEM";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CIP)){
						desTipoDoc="C.I.P";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CM)){
						desTipoDoc="C.M";
					}
				}
				//Fin:jascencio
				desc_aux += "TIP_DOCUMENTO: "+desTipoDoc;
			}
			if (num_docum != null) {
				desc_aux += ", NUM_DOCUMENTO: "+num_docum;
			}
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			if (tipo_pers != null) {
				tipo_pers="";
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		if (tipo_certi.equals("D")){       // certificado RJB (Tipo: Dominial )
			tipo_inf_domin = rset.getString("TIP_INF_DOMINIO");
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (tipo_inf_domin != null) {
				if (tipo_inf_domin.equals("C"))
					desc_aux += "TIPO_INF_DOMINIO: Completa, ";
				else
					desc_aux += "TIPO_INF_DOMINIO: Último Propietario, ";
			}
			if (cert_id.equals("25")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("26")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("27")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("28")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		if (tipo_certi.equals("G")){       // certificado RJB (Tipo: Gravamen)
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (cert_id.equals("29")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("30")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("31")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("32")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
				
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		
			
		}
		/*	fin:jrosas 31-05-2007*/
//		Inicio:jascencio:27/06/07
		if(tipo_certi.equals("C")){//Certificado CREM
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			/*** inicio:jrosas 03-09-07 **/
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_registro =  rset.getString("TIP_REGISTRO");
			flag_historico = rset.getString("FLAG_HISTORICO");
			fec_ins_asiento_desde = rset.getString("FEC_INS_ASIENTO_DESDE");
			if (fec_ins_asiento_desde != null)
				fec_ins_asiento_desde = fec_ins_asiento_desde.substring(0, 11);
			fec_ins_asiento_hasta =	rset.getString("FEC_INS_ASIENTO_HASTA");
			if (fec_ins_asiento_hasta != null)
				fec_ins_asiento_hasta = fec_ins_asiento_hasta.substring(0, 11);
			/*** fin:jrosas 03-09-07 **/

			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			/*** inicio: jrosas 03-09-07 **/
			ArrayList cadAux= new ArrayList();
			if (tip_registro != null){
				StringTokenizer tokens=new StringTokenizer(tip_registro, ",");
				while (tokens.hasMoreTokens()){
					String str_cod_aux = tokens.nextToken();
					cadAux.add(str_cod_aux);
				}
				desc_aux += ", REGISTROS:";
				int len= cadAux.size();
				String cad_reg="";
				for (int k=0; k<len; k++){
					String str_cod= (String)cadAux.get(k);
					if (k ==(len-1)){
						if (str_cod.equals("RMC")){
							cad_reg += "RMC";
						}
						if (str_cod.equals("VEH")){
							cad_reg += "Vehicular";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones)";
			            }
					}else{
						if (str_cod.equals("RMC")){
							cad_reg += "RMC, ";
						}
					    if (str_cod.equals("VEH")){
					    	cad_reg += "Vehicular, ";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera, ";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques, ";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves, ";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones), ";
			            }
					} //else      
				}//for
				desc_aux += cad_reg;
			}//tipo_registro
			
			if (flag_historico != null){
				if (flag_historico.equals("1"))
					desc_aux += ", HISTÓRICO: Sí";
				else if (flag_historico.equals("0")){
					desc_aux += ", HISTÓRICO: No";
				}
			}
			if (fec_ins_asiento_desde != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO DESDE: "+fec_ins_asiento_desde;
				if (fec_ins_asiento_hasta != null){
					desc_aux += " HASTA "+fec_ins_asiento_hasta;
				}
			}else if (fec_ins_asiento_hasta != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO HASTA: "+fec_ins_asiento_hasta;
			}
			/*** FIN: jrosas 03-09-07 **/
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		//Fin:jascencio
		
		//inicio:dbravo:20/08/2007
		if(buscaCargLabBean.getTipoCertificado()!=null &&
			(
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)
			)
		){
			if(buscaCargLabBean.getEstado_sol_id()!=null && buscaCargLabBean.getEstado_sol_id().equalsIgnoreCase("E")){
				
				DboCertificado dboCertificado = new DboCertificado();
				dboCertificado.clearAll();
				dboCertificado.setFieldsToRetrieve(DboCertificado.CAMPO_FLAG_PAGO_CREM);
				dboCertificado.setField(DboCertificado.CAMPO_SOLICITUD_ID, buscaCargLabBean.getSolicitud_id());
				if(dboCertificado.find()){
					buscaCargLabBean.setFlagPagoCrem(dboCertificado.getField(DboCertificado.CAMPO_FLAG_PAGO_CREM));
				}
				
			}else{
				buscaCargLabBean.setFlagPagoCrem("1");
			}
			
		}else{
			buscaCargLabBean.setFlagPagoCrem("1");
		}
		//fin:dbravo:20/08/2007
		
		arrbuscaList.add(buscaCargLabBean);
	}
	else
	{
		return null;
	}
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return arrbuscaList;
}

public ArrayList recuperarExportaSol(String[] solId, String cuentaId, String tipoEnv) throws Throwable
{
	ArrayList arrbuscaList = new ArrayList();
	StringBuffer qestSol = new StringBuffer();
	StringBuffer solic = new StringBuffer();
	solic.append(solId[0]);
	for(int i = 1; i< solId.length; i++)
	{
		solic.append(", ").append(solId[i]);
	}
    qestSol.append("select solId, tpoEnv, direc, nombre, dist, prov, dpto, oficina from ")
	.append("( ")
	.append("select s.solicitud_id AS solId, d.tpo_env AS tpoEnv, d.direcc AS direc, d.raz_soc AS nombre, d.distrito as dist, prov.nombre as prov, dept.nombre AS dpto, ofi.nombre as oficina ")
	.append("from solicitud s, destinatario d, solicitud_x_carga sx, tm_departamento dept, tm_provincia prov, ofic_registral ofi ")
	//AND s.estado='E' 
	/*** inicio: jrosas 24-08-07 **/
	.append("where s.solicitud_id in (").append(solic.toString()).append(") AND s.solicitud_id = d.solicitud_id and ofi.reg_pub_id=d.reg_pub_id and ofi.ofic_reg_id=d.ofic_reg_id AND s.solicitud_id = sx.solicitud_id and d.tpo_pers='J' and (sx.rol = 'EM' OR sx.rol = 'VE') AND dept.dpto_id(+)=d.dpto_id AND prov.prov_id(+)=d.prov_id AND prov.dpto_id(+) = d.dpto_id and sx.cta_id_reg = ").append(cuentaId);
    /*** fin: jrosas 24-08-07 **/
    if(tipoEnv!=null && !tipoEnv.equals("") && !tipoEnv.equals("T"))	
    	qestSol.append(" and d.tpo_env='").append(tipoEnv).append("' ");
    
    qestSol.append(" UNION ")
	.append("select s.solicitud_id AS solId, d.tpo_env AS tpoEnv, d.direcc AS direc, d.nombres || ' ' || d.ape_pat || ' ' || d.ape_mat AS nombre, d.distrito as dist, prov.nombre as prov, dept.nombre AS dpto, ofi.nombre as oficina ")
	.append("from solicitud s, destinatario d, solicitud_x_carga sx, tm_departamento dept, tm_provincia prov, ofic_registral ofi ")
	//AND s.estado='E' 
	/*** inicio: jrosas 24-08-07 **/
	.append("where s.solicitud_id in (").append(solic.toString()).append(") AND s.solicitud_id = d.solicitud_id and ofi.reg_pub_id=d.reg_pub_id and ofi.ofic_reg_id=d.ofic_reg_id AND s.solicitud_id = sx.solicitud_id and d.tpo_pers='N' and (sx.rol = 'EM' OR sx.rol = 'VE') AND dept.dpto_id(+)=d.dpto_id AND prov.prov_id(+)=d.prov_id AND prov.dpto_id(+) = d.dpto_id and sx.cta_id_reg = ").append(cuentaId);
    /*** fin: jrosas 24-08-07 **/
    if(tipoEnv!=null && !tipoEnv.equals("") && !tipoEnv.equals("T"))
    	qestSol.append(" and d.tpo_env='").append(tipoEnv).append("' ");

    qestSol.append(") ");
			
	
	if (Loggy.isTrace(this))
	System.out.println("Solicitud QUERY ---> "+qestSol.toString());
	
	Statement stmt = null;
	ResultSet rset = null;
	
	try{
		stmt = conn.createStatement();
		rset = stmt.executeQuery(qestSol.toString());
			BuscaCargaLaboralBean buscaCargLabBean = null;	
	while (rset.next())
	{	
		//Almaceno el resultado en el Bean buscaCargLabBean	
		buscaCargLabBean = new BuscaCargaLaboralBean();
		buscaCargLabBean.setSolicitud_id(rset.getString("solId"));
		if(rset.getString("tpoEnv").equals("D"))
		{
			buscaCargLabBean.setDestiEnvio("Domicilio");
			buscaCargLabBean.setDestiDire(rset.getString("direc"));
			buscaCargLabBean.setDestiDpto(
				 (rset.getString("dist")==null?"":rset.getString("dist").trim())
				+(rset.getString("prov")==null?"":" " + rset.getString("prov").trim())
				+(rset.getString("dpto")==null?"":" " + rset.getString("dpto").trim()));
		}
		else
		{
			buscaCargLabBean.setDestiEnvio("Ventanilla");
			buscaCargLabBean.setDestiDire("RECOJO EN VENTANILLA");
			buscaCargLabBean.setDestiDpto("OFICINA " + rset.getString("oficina"));
		}
		buscaCargLabBean.setDestiNombre(rset.getString("nombre"));
		arrbuscaList.add(buscaCargLabBean);			
	}
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return arrbuscaList;		
}

public ArrayList recuperarXEstadoSol(String estadoSol, String cuentaId, String sfechaInicio, String sfechaFin, String tipoEnv) throws Throwable
{
	return recuperarDBXEstadoSol(estadoSol, cuentaId, sfechaInicio, sfechaFin, tipoEnv);
}
protected ArrayList recuperarDBXEstadoSol(String estadoSol, String cuentaId, String sfechaInicio, String sfechaFin, String tipoEnv) throws Throwable
{
	
	ArrayList arrbuscaList = new ArrayList();
	StringBuffer qestSol = new StringBuffer();
	
	  /* inicio:jrosas 30-05-2007
	   SUNARP-REGMOBCOM: Modificaicon de informacion de la tabla objetoSolicitud con la agregacion de  nuevos campos  */
	
	if (!estadoSol.equals("04"))
	{
		if (estadoSol.equals("01")||estadoSol.equals("02")||estadoSol.equals("08")){ // estado: PENDIENTES
		    //Query busqueda de solicitud 
		    qestSol.append("SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, f.NOMBRE as TIPO_CERT, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC , ")
			//el nombre para el tipo de certificado fue modificado - ahora se presenta el asiento y el titulo
			//qestSol.append("SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, b.NS_ASIENTO as ASIENTO, b.AA_TITULO as TITULO, b.NUM_TITU as NUTITU, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC , ")
		     /* agregado por jhenifer */
   	 	    .append(" b.NUM_PLACA, b.NOMBRE_BIEN, b.NUM_MATRICULA, b.NUM_SERIE, b.SIGLAS, b.TIP_PARTICIPANTE, b.TIP_DOCUMENTO, b.NUM_DOCUMENTO, b.TIP_INF_DOMINIO, b.NUM_PARTIDA as NUMPARTIDA, f.CERTIFICADO_ID, ")
   	 	     /* fecha: 30-05-2007 */ 
   	 	    // Inicio:mgarate:05/06/2007
   	 	    /** inicio: jrosas 03-09-07 **/
   	 	    .append("b.CRIT_BUSQ, b.TIP_REGISTRO, b.FLAG_HISTORICO, b.FEC_INS_ASIENTO_DESDE, b.FEC_INS_ASIENTO_HASTA, f.COD_GRUPO_LIBRO_AREA, ")
   	 	    /** fin: jrosas 03-09-07 **/
   	 	    // Fin:mgarate:05/06/2007
	 	    .append(" c.NOMBRE as NOMBRE_OFIC_REG,  g.MENSAJE_REGISTRADOR, g.MENSAJE_USUARIO, d.NUM_PARTIDA as NUM_PARTIDA, h.NOMBRE as AREA_REGISTRAL, e.ROL, e.CTA_ID_REG, e.ESTADO as ESTADO_SOL_CARG ")
		    .append(" FROM")
		    .append(" SOLICITUD a, OBJETO_SOLICITUD b, OFIC_REGISTRAL c, PARTIDA d, SOLICITUD_X_CARGA e, TM_CERTIFICADOS f, TM_ESTADO_SOLICITUD g, TM_AREA_REGISTRAL h ")
			.append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID")		
			.append(" AND b.REG_PUB_ID = c.REG_PUB_ID  ")		
			.append(" AND b.OFIC_REG_ID = c.OFIC_REG_ID ")		
			.append(" AND b.REFNUM_PART = d.REFNUM_PART(+) ")
			.append(" AND a.SOLICITUD_ID = e.SOLICITUD_ID ")
			.append(" AND b.CERTIFICADO_ID = f.CERTIFICADO_ID ")
			.append(" AND a.ESTADO = g.ESTADO_SOLICITUD ")
			.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+) ")
			.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+)")
			.append(" AND e.ESTADO= 'P'")
			.append(" AND e.CTA_ID_REG=")
			.append(cuentaId)
			.append(" AND e.ROL = 'VE'")
			.append(" AND a.ESTADO = 'C'");
			
			if(sfechaInicio!=null && sfechaFin!=null){
				qestSol.append(" AND to_char(a.TS_CREA,'yyyy/mm/dd') BETWEEN '");
				qestSol.append(sfechaInicio);
				qestSol.append("' and '");
				qestSol.append(sfechaFin);
				qestSol.append("'");
			}
		}
		if (estadoSol.equals("01") || estadoSol.equals("08")) { // estado: PENDIENTES
			qestSol.append(" UNION");
		}
		if (estadoSol.equals("01") || estadoSol.equals("03")
			|| estadoSol.equals("04")
			|| estadoSol.equals("05")
			|| estadoSol.equals("06")
			|| estadoSol.equals("07")
			|| estadoSol.equals("08")) { // estado: PENDIENTES

			if (estadoSol.equals("03")
				|| estadoSol.equals("04")
				|| estadoSol.equals("05")
				|| estadoSol.equals("06")
				|| estadoSol.equals("07")) {
				qestSol = new StringBuffer();
			}
			/** 
			 a.NS_ASIENTO as ASIENTO, a.AA_TITULO as TITULO, a.NUM_TITU as NUTITU 
			  SE REEMPLAZA EL NOMBRE POR EL NUMERO DE ASIENTO EL AÑO Y EL NUMERO DE TITULO
			 **/
			qestSol.append(" SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, f.NOMBRE as TIPO_CERT, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC , ")
			//qestSol.append(" SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, b.NS_ASIENTO as ASIENTO, b.AA_TITULO as TITULO, b.NUM_TITU as NUTITU, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC , ")    
			/* agregado por jhenifer */
			    .append(" b.NUM_PLACA, b.NOMBRE_BIEN, b.NUM_MATRICULA, b.NUM_SERIE, b.SIGLAS, b.TIP_PARTICIPANTE, b.TIP_DOCUMENTO, b.NUM_DOCUMENTO, b.TIP_INF_DOMINIO, b.NUM_PARTIDA as NUMPARTIDA, f.CERTIFICADO_ID, ")
			    /* fecha: 30-05-2007 */ 
			    // Inicio:mgarate:05/06/2007
   	 	    	  /** inicio: jrosas 03-09-07 **/
			    	.append("b.CRIT_BUSQ, b.TIP_REGISTRO, b.FLAG_HISTORICO, b.FEC_INS_ASIENTO_DESDE, b.FEC_INS_ASIENTO_HASTA,f.COD_GRUPO_LIBRO_AREA, ")
   	 	    	/** fin: jrosas 03-09-07 **/
   	 	    	// Fin:mgarate:05/06/2007
				.append(" c.NOMBRE as NOMBRE_OFIC_REG,  g.MENSAJE_REGISTRADOR, g.MENSAJE_USUARIO, d.NUM_PARTIDA as NUM_PARTIDA, h.NOMBRE as AREA_REGISTRAL, e.ROL, e.CTA_ID_REG, e.ESTADO as ESTADO_SOL_CARG ")
				.append(" FROM")
				.append(" SOLICITUD a, OBJETO_SOLICITUD b, OFIC_REGISTRAL c, PARTIDA d, SOLICITUD_X_CARGA e, TM_CERTIFICADOS f, TM_ESTADO_SOLICITUD g, TM_AREA_REGISTRAL h ")
				.append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID")
				.append(" AND b.REG_PUB_ID = c.REG_PUB_ID  ")
				.append(" AND b.OFIC_REG_ID = c.OFIC_REG_ID ")
				.append(" AND b.REFNUM_PART = d.REFNUM_PART(+) ")
				.append(" AND a.SOLICITUD_ID = e.SOLICITUD_ID ")
				.append(" AND b.CERTIFICADO_ID = f.CERTIFICADO_ID ")
				.append(" AND a.ESTADO = g.ESTADO_SOLICITUD ")
				.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+) ")
				.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+)");
			//estado de la solicitud x carga
			if (estadoSol.equals("01")
				|| estadoSol.equals("03")
				|| estadoSol.equals("04")) {
				//.append(" AND e.ESTADO in ('P','C')")
				qestSol.append(" AND e.ESTADO = 'P'");
			}
			if (estadoSol.equals("05")) {
				qestSol.append(" AND e.ESTADO = 'C'");
			}
			if (estadoSol.equals("06")
				|| estadoSol.equals("07")
				|| estadoSol.equals("08")) {
				qestSol.append(" AND e.ESTADO in ('P','C')");
			}

			qestSol.append(" AND e.CTA_ID_REG=").append(cuentaId).append(
				" AND (e.ROL = 'EM' OR e.rol='VE')");

			//Estado de la solicitud
			if (estadoSol.equals("01")) { //Pendientes
				qestSol.append(" AND a.ESTADO in ('V','E')");
			}
			if (estadoSol.equals("03")) { //por expedir 
				qestSol.append(" AND a.ESTADO = 'V'");
			}
			if (estadoSol.equals("04")) { //por despachar
				qestSol.append(" AND a.ESTADO = 'E'");
			}
			if (estadoSol.equals("05")) { //despachada
				qestSol.append(" AND a.ESTADO = 'D'");
			}
			if (estadoSol.equals("06")) { //cancelada
				qestSol.append(" AND a.ESTADO = 'X'");
			}
			if (estadoSol.equals("07")) { //improcedente
				qestSol.append(" AND a.ESTADO = 'I'");
			}
			if (estadoSol.equals("08")) { //Todas
				qestSol.append(" AND a.ESTADO in ('V','E','D')");
			}

			if (sfechaInicio != null && sfechaFin != null) {
				qestSol.append(" AND to_char(a.TS_CREA,'yyyy/mm/dd') BETWEEN '");
				qestSol.append(sfechaInicio);
				qestSol.append("' and '");
				qestSol.append(sfechaFin);
				qestSol.append("'");
			}
		
		}
	}
	else
	{
		qestSol.append("select DISTINCT(solId), tpoEnv, direc, nombre, dpto from ")
			.append("( ")
			.append("select s.solicitud_id AS solId, d.tpo_env AS tpoEnv, d.direcc || ' ' || d.distrito AS direc, d.raz_soc AS nombre, prov.nombre || ' ' || dept.nombre AS dpto ")
			.append("from solicitud s, destinatario d, solicitud_x_carga sx, tm_departamento dept, tm_provincia prov ")
			.append("where s.solicitud_id=d.solicitud_id AND s.solicitud_id = sx.solicitud_id AND s.estado='E' and d.tpo_pers='J' and (sx.rol = 'EM' or sx.rol = 'VE') AND dept.dpto_id(+) = prov.dpto_id AND prov.prov_id(+)=d.prov_id AND prov.dpto_id(+) = d.dpto_id and sx.cta_id_reg = ")
			.append(cuentaId);
			
		if(tipoEnv!=null && !tipoEnv.equals("") && !tipoEnv.equals("T"))	
			qestSol.append(" and d.tpo_env='").append(tipoEnv).append("' ");
		if((sfechaInicio!=null && sfechaFin!=null) && (!sfechaInicio.equals("") && !sfechaFin.equals("")))
		{
			qestSol.append(" AND to_char(s.TS_CREA,'yyyy/mm/dd') BETWEEN '")
				.append(sfechaInicio)
				.append("' and '")
				.append(sfechaFin)
				.append("' ");
		}
		qestSol.append(" UNION ALL ")
			.append("select s.solicitud_id AS solId, d.tpo_env AS tpoEnv, d.direcc || ' ' || d.distrito AS direc, d.nombres || ' ' || d.ape_pat || ' ' || d.ape_mat AS nombre, prov.nombre || ' ' || dept.nombre AS dpto ")
			.append("from solicitud s, destinatario d, solicitud_x_carga sx, tm_departamento dept, tm_provincia prov ")
			.append("where s.solicitud_id=d.solicitud_id AND s.solicitud_id = sx.solicitud_id AND s.estado='E' and d.tpo_pers='N' AND dept.dpto_id(+) = prov.dpto_id AND prov.prov_id(+)=d.prov_id and (sx.rol = 'EM' or sx.rol = 'VE') AND prov.dpto_id(+) = d.dpto_id and sx.cta_id_reg = ").append(cuentaId);
		if(tipoEnv!=null && !tipoEnv.equals("") && !tipoEnv.equals("T"))
			qestSol.append(" and d.tpo_env='").append(tipoEnv).append("' ");
		if((sfechaInicio!=null && sfechaFin!=null) && (!sfechaInicio.equals("") && !sfechaFin.equals("")))
		{
			qestSol.append(" AND to_char(s.TS_CREA,'yyyy/mm/dd') BETWEEN '")
				.append(sfechaInicio)
				.append("' and '")
				.append(sfechaFin)
				.append("' ");
		}
		
		qestSol.append(") ");
			
	}

	//*** MANEJO DE LA PAGINACION
	Propiedades propiedades = Propiedades.getInstance();		
	Statement stmt = null;
	ResultSet rset = null;
	try{
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(propiedades.getLineasPorPag());
		rset = stmt.executeQuery(qestSol.toString());
		System.out.println("Solicitud QUERY ---> "+qestSol.toString());
				
	if(paginacBean.getPaginacion() == 1){
		if (rset.last()){
			paginacBean.setTamano(rset.getRow());
			rset.beforeFirst();
		}
	}
	
	if (paginacBean.getPaginacion() > 1) {
		rset.absolute(propiedades.getLineasPorPag() * (paginacBean.getPaginacion() - 1));
	}
	
	int conta = 1;
	boolean hayNext = false;
	boolean encontro = false;
	/** inicio: jrosas 06-09-2007 **/
	boolean realizado = false;
	/** fin: jrosas 06-09-2007 **/
	boolean b = rset.next();

	BuscaCargaLaboralBean buscaCargLabBean = null;
	//while (rset.next())
	while (b == true && conta <= propiedades.getLineasPorPag()) {
		encontro = true;
		conta++;
		if (!estadoSol.equals("04")) {
			//Almaceno el resultado en el Bean buscaCargLabBean	
			buscaCargLabBean = new BuscaCargaLaboralBean();
			buscaCargLabBean.setSolicitud_id(rset.getString("SOLICITUD_ID"));
			// en Atributo certificado_id  de buscaCargaLaboral se seteo el tipo de certificado 
			buscaCargLabBean.setCertificado_id(rset.getString("TPO_CERTIFICADO"));
			// en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
			buscaCargLabBean.setTipoCertificado(rset.getString("CERTIFICADO_ID"));
			/** 
			 Se necesita el numero de asiento y el titulo (año - #titulo) 
			*/
			String asiento = "";
			String anioTitulo = "";
			String numTitulo = "";
			Statement stmt2 = conn.createStatement();
			ResultSet rset2 = stmt2.executeQuery(qestSol.toString());
			Double solicitudId = rset.getDouble("SOLICITUD_ID");
			String sql11 = "SELECT a.NS_ASIENTO as ASIENTO, a.AA_TITU as ANIO, a.NUM_TITU as NUMTITULO FROM OBJETO_SOLICITUD a where a.SOLICITUD_ID = " + solicitudId.toString();
			
			rset2 = stmt2.executeQuery(sql11);
			System.out.println("EXECUTE QUERY --> " + sql11);
			while(rset2.next()){
				asiento = String.valueOf(rset2.getInt("ASIENTO"));
				anioTitulo = rset2.getString("ANIO");
				numTitulo = rset2.getString("NUMTITULO");
			//TIPO_CERT
			}
			if (asiento.equals("0")){
				asiento = "0";
			}
			if (anioTitulo==null  || anioTitulo.equals("") ){
				anioTitulo = "0000";
			}
			if (numTitulo==null || numTitulo.equals("")   ){
				numTitulo = "00000000";
			}
			buscaCargLabBean.setNombre_Cert(asiento + "-"+ anioTitulo + numTitulo );
			buscaCargLabBean.setTpo_persona(rset.getString("TPO_PERS"));
			if(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").trim().equals(""))
				buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("NOMBRES"));//Persona Natural
			else		
				buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
			//Persona Natural
			buscaCargLabBean.setObjeto_certPJ(rset.getString("RAZ_SOC")); //Razon Social
			buscaCargLabBean.setNum_partida(rset.getString("NUM_PARTIDA"));
			buscaCargLabBean.setArea_reg_id(rset.getString("AREA_REGISTRAL"));
			buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_REGISTRADOR"));
			//Estado de la solicitud
			buscaCargLabBean.setEstado_ext_sol(rset.getString("MENSAJE_USUARIO"));
			//Estado de la solicitud
			buscaCargLabBean.setOfic_registral(rset.getString("NOMBRE_OFIC_REG"));
			//Nombre de Oficina Registral						
			buscaCargLabBean.setCuenta_rol(rset.getString("CTA_ID_REG"));
			// cuenta que tiene el rol sobre la solicitud
			buscaCargLabBean.setRol(rset.getString("ROL"));
			//rol del regitrador responsable
			buscaCargLabBean.setEstado_sol_x_carga(rset.getString("ESTADO_SOL_CARG"));
			//estado Solcitud x Carga
			buscaCargLabBean.setEstado_sol_id(rset.getString("ESTADO_SOL"));
			//Estado de la sol x carga
			
			/* inicio:jrosas 30-05-2007
			   SUNARP-REGMOBCOM: Seteo campos extras de la tabla objeto_solicitud en ben buscarcargalaboralBean  */
			
			buscaCargLabBean.setPlaca(rset.getString("NUM_PLACA"));
			buscaCargLabBean.setNombreBien(rset.getString("NOMBRE_BIEN"));
			buscaCargLabBean.setNumeroMatricula(rset.getString("NUM_MATRICULA"));
			buscaCargLabBean.setNumeroSerie(rset.getString("NUM_SERIE"));
			buscaCargLabBean.setSiglas(rset.getString("SIGLAS"));
			buscaCargLabBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
			buscaCargLabBean.setTipoDocumento(rset.getString("TIP_DOCUMENTO"));
			buscaCargLabBean.setNumeroDocumento(rset.getString("NUM_DOCUMENTO"));
			buscaCargLabBean.setNumeroPartida(rset.getString("NUMPARTIDA"));

			/*	fin:jrosas 30-05-2007*/
			
			/* inicio:jrosas 31-05-2007
			   SUNARP-REGMOBCOM: Seteo la descripcion del objeto de solicitud de cada tipo de certificado  */
			
			// Inicio:mgarate:04/06/2007
			//ObjetoSolicitudBean objSolic= (ObjetoSolicitudBean)this.objetoSolicitudList.get(0);
			String tipo_certi= rset.getString("TPO_CERTIFICADO");// objSolic.getTpo_cert();
			String cert_id= rset.getString("CERTIFICADO_ID");// objSolic.getCertificado_id();
			
			// Inicio:mgarate:04/06/2007
			
			String desc_aux="";
			String num_placa=null;
			String nombre_bien=null;
			String num_matricula=null;
			String num_serie=null;
			String ape_pat=null;
			String ape_mat=null;
			String nombres=null;
			String siglas=null;
			String raz_social=null;
			String tip_particip=null;
			String tip_docum=null;
			String num_docum=null;
			String tipo_pers=null;
			String tipo_inf_domin=null;
			String num_partida=null;
			
			/** inicio: jrosas:03-09-07 **/
			String tp_registro= null;
			String tip_registro =  null;
			String flag_historico = null;
			String fec_ins_asiento_desde = null;
			String fec_ins_asiento_hasta =	null;
			
			/** fin: jrosas:03-09-07 **/
			if (tipo_certi.equals("N")){       // certificado positivo/negativo
				num_placa = rset.getString("NUM_PLACA");
				nombre_bien = rset.getString("NOMBRE_BIEN");
				num_matricula = rset.getString("NUM_MATRICULA");
				num_serie = rset.getString("NUM_SERIE");
				
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (nombre_bien != null) {
					desc_aux += "NOMBRE_BIEN: "+nombre_bien;
			    /** inicio: jrosas24-08-07 **/
					if (num_matricula != null) {
						desc_aux += ", NUM_MATRICULA: "+num_matricula;
					}	
				}else if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
				/** fin: jrosas24-08-07 **/
				if (num_serie != null) {
					desc_aux += "NUM_SERIE: "+num_serie;
				}
				
				buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			}
			if (tipo_certi.equals("R")){     
				ape_pat = rset.getString("APE_PAT");
				ape_mat = rset.getString("APE_MAT");
				nombres= rset.getString("NOMBRES");
				siglas= rset.getString("SIGLAS");
				raz_social=	rset.getString("RAZ_SOC");
				tip_particip= rset.getString("TIP_PARTICIPANTE");
				tip_docum= rset.getString("TIP_DOCUMENTO");
				num_docum= rset.getString("NUM_DOCUMENTO");
				tipo_pers= rset.getString("TPO_PERS");
				
				/*** INICIO: Jrosas 05-09-2007 **/
				if (rset.getString("TPO_PERS").equals("N")){
					if (ape_pat != null) {
						desc_aux = "APE_PAT: "+ape_pat;
					}
					if (ape_mat != null) {
						desc_aux += ", APE_MAT: "+ape_mat;
					}
					if (nombres != null) {
						desc_aux += ", NOMBRE: "+nombres;
					}
				}else if(rset.getString("TPO_PERS").equals("J")){
					if (raz_social != null) {
						desc_aux += "RAZ_SOC: "+raz_social;
					}
					if (siglas != null) {
						desc_aux += ", SIGLAS: "+siglas;
					}
				}	
				/*** FIN: Jrosas 05-09-2007 **/
				if (tip_docum != null) {
					//Inicio:jascencio:26/06/07
					String desTipoDoc=null;
					if(!tip_docum.equals("")){
						if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LE)){
							desTipoDoc="L.E";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LM)){
							desTipoDoc="L.M";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CE)){
							desTipoDoc="C.E";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CI)){
							desTipoDoc="CI";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_RUC)){
							desTipoDoc="R.U.C";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS) || tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS2)){
							desTipoDoc="PS";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_DNI)){
							desTipoDoc="DNI";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LEM)){
							desTipoDoc="LEM";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CIP)){
							desTipoDoc="C.I.P";
						}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CM)){
							desTipoDoc="C.M";
						}
					}
					//Fin:jascencio
					desc_aux += "TIP_DOCUMENTO: "+desTipoDoc;
				}
				if (num_docum != null) {
					desc_aux += ", NUM_DOCUMENTO: "+num_docum;
				}
				if (tip_particip != null){
					String desTipoPar=null;
					if(!tip_particip.equals("")){
						if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
							desTipoPar="Deudor";
						}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
							desTipoPar="Acreedor";
						}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
							desTipoPar="Representante";
						}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
							desTipoPar="Otros";
						}
					}
					//Fin:jascencio
					
					desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
				}
				if (tipo_pers != null) {
					tipo_pers="";
				}
				
				buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			}
			if (tipo_certi.equals("D")){       // certificado RJB (Tipo: Dominial )
				//Inicio:mgarate:21/09/2007
				DboGrupoLibroArea gla= new DboGrupoLibroArea();
				gla.setConnection(dconn);
				gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
				gla.find();
				//Fin:mgarate:21/09/2007
				tipo_inf_domin = rset.getString("TIP_INF_DOMINIO");
				num_placa = rset.getString("NUM_PLACA");
				num_partida = rset.getString("NUMPARTIDA");
				num_matricula = rset.getString("NUM_MATRICULA");
				num_serie = rset.getString("NUM_SERIE");
				
				desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
				if (tipo_inf_domin != null) {
					if (tipo_inf_domin.equals("C"))
						desc_aux += "TIPO_INF_DOMINIO: Completa, ";
					else
						desc_aux += "TIPO_INF_DOMINIO: Último Propietario, ";
				}
				if (cert_id.equals("25")){     // certificado Gravamen - Vehicular
					if (num_placa != null) {
						desc_aux += "NUM_PLACA: "+num_placa;
					}
					if (num_partida != null){
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
				}
				if (cert_id.equals("26")){     // certificado Gravamen - Buques
					if (num_partida != null) {
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
					if (num_matricula != null) {
						desc_aux += "NUM_MATRICULA: "+num_matricula;
					}
				}
				if (cert_id.equals("27")){     // certificado Gravamen - Aeronaves
					if (num_partida != null) {
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
					if (num_matricula != null) {
						desc_aux += "NUM_MATRICULA: "+num_matricula;
						if (num_serie != null){
							desc_aux += ", NUM_SERIE: "+num_serie;
						}
					}else{
						if (num_serie != null){
							desc_aux += "NUM_SERIE: "+num_serie;
						}
					}
				}
				if (cert_id.equals("28")){     // certificado Gravamen - Embarcaciones Pesqueras
					if (num_partida != null) {
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
					if (num_matricula != null) {
						desc_aux += "NUM_MATRICULA: "+num_matricula;
					}
				}
				
				buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
				
			}
			if (tipo_certi.equals("G")){       // certificado RJB (Tipo: Gravamen)
				
				//Inicio:mgarate:21/09/2007
					DboGrupoLibroArea gla= new DboGrupoLibroArea();
					gla.setConnection(dconn);
					gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
					gla.find();
				//Fin:mgarate:21/09/2007
				num_placa = rset.getString("NUM_PLACA");
				num_partida = rset.getString("NUMPARTIDA");
				num_matricula = rset.getString("NUM_MATRICULA");
				num_serie = rset.getString("NUM_SERIE");
				//Inicio:mgarate:21/09/2007
				desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
				//Fin:mgarate:21/09/2007
				if (cert_id.equals("29")){     // certificado Gravamen - Vehicular
					if (num_placa != null) {
						desc_aux += "NUM_PLACA: "+num_placa;
					}
					if (num_partida != null){
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
				}
				if (cert_id.equals("30")){     // certificado Gravamen - Buques
					if (num_partida != null) {
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
					if (num_matricula != null) {
						desc_aux += "NUM_MATRICULA: "+num_matricula;
					}
				}
				if (cert_id.equals("31")){     // certificado Gravamen - Aeronaves
					if (num_partida != null) {
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
					if (num_matricula != null) {
						desc_aux += "NUM_MATRICULA: "+num_matricula;
						if (num_serie != null){
							desc_aux += ", NUM_SERIE: "+num_serie;
						}
					}else{
						if (num_serie != null){
							desc_aux += "NUM_SERIE: "+num_serie;
						}
					}
				}
				if (cert_id.equals("32")){     // certificado Gravamen - Embarcaciones Pesqueras
					if (num_partida != null) {
						desc_aux += "NUM_PARTIDA: "+num_partida;
					}
					if (num_matricula != null) {
						desc_aux += "NUM_MATRICULA: "+num_matricula;
					}
				}
					
				buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			}
			
			/*	fin:jrosas 31-05-2007*/
			// Inicio:mgarate:05/06/2007
			if(tipo_certi.equals("B"))
			{
				buscaCargLabBean.setDescripcionObjetoCertificado(rset.getString("CRIT_BUSQ"));
			}
			// Fin:mgarate:05/06/2007
//			Inicio:jascencio:27/06/07
			if(tipo_certi.equals("C")){//Certificado CREM
				ape_pat = rset.getString("APE_PAT");
				ape_mat = rset.getString("APE_MAT");
				nombres= rset.getString("NOMBRES");
				siglas= rset.getString("SIGLAS");
				raz_social=	rset.getString("RAZ_SOC");
				tip_particip= rset.getString("TIP_PARTICIPANTE");
				
				/*** inicio:jrosas 03-09-07 **/
				tip_particip= rset.getString("TIP_PARTICIPANTE");
				tip_registro =  rset.getString("TIP_REGISTRO");
				flag_historico = rset.getString("FLAG_HISTORICO");
				fec_ins_asiento_desde = rset.getString("FEC_INS_ASIENTO_DESDE");
				if (fec_ins_asiento_desde != null)
					fec_ins_asiento_desde = fec_ins_asiento_desde.substring(0, 11);
				fec_ins_asiento_hasta =	rset.getString("FEC_INS_ASIENTO_HASTA");
				if (fec_ins_asiento_hasta != null)
					fec_ins_asiento_hasta = fec_ins_asiento_hasta.substring(0, 11);
				/*** fin:jrosas 03-09-07 **/
				
				/*** INICIO: Jrosas 05-09-2007 **/
				if (rset.getString("TPO_PERS").equals("N")){
					if (ape_pat != null) {
						desc_aux = "APE_PAT: "+ape_pat;
					}
					if (ape_mat != null) {
						desc_aux += ", APE_MAT: "+ape_mat;
					}
					if (nombres != null) {
						desc_aux += ", NOMBRE: "+nombres;
					}
				}else if(rset.getString("TPO_PERS").equals("J")){
					if (raz_social != null) {
						desc_aux += "RAZ_SOC: "+raz_social;
					}
					if (siglas != null) {
						desc_aux += ", SIGLAS: "+siglas;
					}
				}	
				/*** FIN: Jrosas 05-09-2007 **/
				/*** inicio: jrosas 03-09-07 **/
				ArrayList cadAux= new ArrayList();
				if (tip_registro != null){
					StringTokenizer tokens=new StringTokenizer(tip_registro, ",");
					while (tokens.hasMoreTokens()){
						String str_cod_aux = tokens.nextToken();
						cadAux.add(str_cod_aux);
					}
					desc_aux += ", REGISTROS:";
					int len= cadAux.size();
					String cad_reg="";
					for (int k=0; k<len; k++){
						String str_cod= (String)cadAux.get(k);
						if (k ==(len-1)){
							if (str_cod.equals("RMC")){
								cad_reg += "RMC";
							}
							if (str_cod.equals("VEH")){
								cad_reg += "Vehicular";
				            }
				            if (str_cod.equals("EMB")){
				            	cad_reg += "Embarcación Pesquera";
				            }
				            if (str_cod.equals("BUQ")){
				            	cad_reg += "Buques";
				            }
				            if (str_cod.equals("AER")){
				            	cad_reg += "Aeronaves";
				            }
				            if (str_cod.equals("PEJ")){
				            	cad_reg += "Personas Jurídicas(Participaciones)";
				            }
						}else{
							if (str_cod.equals("RMC")){
								cad_reg += "RMC, ";
							}
						    if (str_cod.equals("VEH")){
						    	cad_reg += "Vehicular, ";
				            }
				            if (str_cod.equals("EMB")){
				            	cad_reg += "Embarcación Pesquera, ";
				            }
				            if (str_cod.equals("BUQ")){
				            	cad_reg += "Buques, ";
				            }
				            if (str_cod.equals("AER")){
				            	cad_reg += "Aeronaves, ";
				            }
				            if (str_cod.equals("PEJ")){
				            	cad_reg += "Personas Jurídicas(Participaciones), ";
				            }
						} //else      
					}//for
					desc_aux += cad_reg;
				}//tipo_registro
				
				if (flag_historico != null){
					if (flag_historico.equals("1"))
						desc_aux += ", HISTÓRICO: Sí";
					else if (flag_historico.equals("0")){
						desc_aux += ", HISTÓRICO: No";
					}
				}
				if (fec_ins_asiento_desde != null){
					desc_aux += ", INSCRIPCIÓN DE ASIENTO DESDE: "+fec_ins_asiento_desde;
					if (fec_ins_asiento_hasta != null){
						desc_aux += " HASTA "+fec_ins_asiento_hasta;
					}
				}else if (fec_ins_asiento_hasta != null){
					desc_aux += ", INSCRIPCIÓN DE ASIENTO HASTA: "+fec_ins_asiento_hasta;
				}
				/*** FIN: jrosas 03-09-07 **/
				if (tip_particip != null){
					String desTipoPar=null;
					if(!tip_particip.equals("")){
						if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
							desTipoPar="Deudor";
						}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
							desTipoPar="Acreedor";
						}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
							desTipoPar="Representante";
						}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
							desTipoPar="Otros";
						}
					}
					desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
				}
				
				buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			}
			//Fin:jascencio
			
			if (buscaCargLabBean.getEstado_sol_id().equals(Constantes.ESTADO_SOL_POR_VERIFICAR)||buscaCargLabBean.getEstado_sol_id().equals(Constantes.ESTADO_SOL_POR_EXPEDIR)){
				//seteo el valor de la accion para los estados de la solicitud "C" y "V"			
				if (buscaCargLabBean.getRol().equals(Constantes.REGIS_VERIFICADOR)) {
					buscaCargLabBean.setAccion(Constantes.REGIS_VERIFICADOR);
					//muestro la solicitud por Verificar Pendiente 
				}
				//Estado de la sol x carga
				if (buscaCargLabBean.getRol().equals(Constantes.REGIS_EMISOR)) {
					buscaCargLabBean.setAccion(Constantes.REGIS_EMISOR);
				//muestro la solicitud por Emitir Pendiente 
				}
			}else{
				//seteo la accion con otro valor direfente a "VE" y "EM"
				buscaCargLabBean.setAccion(rset.getString("ESTADO_SOL"));
			}	
			/*** inicio: jrosas 06-09-2007 
		         modificación para estado: Despachada ***/

			if (estadoSol.equals("05")){
				if (buscaCargLabBean.getCertificado_id().equals("N")){					
					if (buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_MOBILIARIO_CONTRATOS)){ //	excepcion para RMC
						realizado = false;
					}else{// certificados positivo/negativos antiguos
						if (buscaCargLabBean.getRol().equals("VE")){
							realizado = true;
						}else{
							realizado = false;
						}
					}
				}else{// otros que no sean certificado positivo/negativo
					if (buscaCargLabBean.getCertificado_id().equals("D")){ // certificado_dominial
						if (buscaCargLabBean.getRol().equals("VE")){
							realizado = true;
						}else{
							realizado = false;
						}
					}else{// otros que no sea certificado dominial
						realizado = false;
					}
				}
			}
			/*** fin: jrosas 06-09-2007 ***/
			
		} else { // por Despachar
			//Almaceno el resultado en el Bean buscaCargLabBean	
			buscaCargLabBean = new BuscaCargaLaboralBean();
			buscaCargLabBean.setSolicitud_id(rset.getString("solId"));
			if(rset.getString("tpoEnv").equals("D"))
				buscaCargLabBean.setDestiEnvio("Domicilio");
			else
				buscaCargLabBean.setDestiEnvio("Ventanilla");
			buscaCargLabBean.setDestiNombre(rset.getString("nombre"));
			buscaCargLabBean.setDestiDire(rset.getString("direc"));
			buscaCargLabBean.setDestiDpto(rset.getString("dpto"));
		}
		if(encontro){
		   paginacBean.setEncontro("SI");				
		}else{
			paginacBean.setEncontro("NO");				
		}
		/** inicio: jrosas 06-09-2007 **/
		if (estadoSol.equals("05")){ // para el caso de despachada
			if (realizado == false){
				arrbuscaList.add(buscaCargLabBean);
			}	
		}else{
			arrbuscaList.add(buscaCargLabBean);
		}
		/** fin: jrosas 06-09-2007 **/
		
		b = rset.next();
		
	}// fin de while
	hayNext = rset.next();

	//*PAGINACION EN EL JSP*//		
	paginacBean.setHayNext(hayNext);
	paginacBean.setPropiedades(propiedades);	
	//
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return arrbuscaList;		
}

public ArrayList recuperarXRangoFechas(String cuentaId, String sfechaInicio, String sfechaFin) throws Throwable{
	return recuperarDBSolxRangoFechas(cuentaId, sfechaInicio, sfechaFin);
}

protected ArrayList recuperarDBSolxRangoFechas(String cuentaId, String sfechaInicio, String sfechaFin) throws Throwable{
	ArrayList arrbuscaList = new ArrayList();
	StringBuffer qestSol = new StringBuffer();
	
	//Query busqueda de solicitud 
	   qestSol.append("SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, f.NOMBRE as TIPO_CERT, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC,")
	    /* inicio:jrosas 31-05-2007
	       SUNARP-REGMOBCOM: seteo de nuevos campos de la tabla objetosolicitud  */
	    .append(" b.NUM_PLACA, b.NOMBRE_BIEN, b.NUM_MATRICULA, b.NUM_SERIE, b.SIGLAS, b.TIP_PARTICIPANTE, b.TIP_DOCUMENTO, b.NUM_DOCUMENTO, b.TIP_INF_DOMINIO, b.NUM_PARTIDA as NUMPARTIDA,f.CERTIFICADO_ID, ")

	    /*	fin:jrosas 31-05-2007*/
	    // Inicio:mgarate:13/06/2007
	    /** inicio: jrosas 03-09-07 **/
		.append("b.CRIT_BUSQ, b.TIP_REGISTRO, b.FLAG_HISTORICO, b.FEC_INS_ASIENTO_DESDE, b.FEC_INS_ASIENTO_HASTA,f.COD_GRUPO_LIBRO_AREA, ")
   	 	/** fin: jrosas 03-09-07 **/
	    // Fin:mgarate:13/06/2007
 	    .append(" c.NOMBRE as NOMBRE_OFIC_REG,  g.MENSAJE_REGISTRADOR, g.MENSAJE_USUARIO, d.NUM_PARTIDA as NUM_PARTIDA, h.NOMBRE as AREA_REGISTRAL, ")
 	    .append(" i.TPO_PERS as TPO_PERS_SOLICITANTE, i.APE_MAT as APE_MAT_SOLICITANTE, i.APE_PAT as APE_PAT_SOLICITANTE, i.NOMBRES as NOMBRES_SOLICITANTE, i.RAZ_SOC as RAZ_SOC_SOLICITANTE ")
	    .append(" FROM ")
	    .append(" SOLICITUD a, OBJETO_SOLICITUD b, OFIC_REGISTRAL c, PARTIDA d, TM_CERTIFICADOS f, TM_ESTADO_SOLICITUD g, TM_AREA_REGISTRAL h , SOLICITANTE i")
		.append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID").append(" AND a.SOLICITUD_ID = i.SOLICITUD_ID ")
		.append(" AND  b.REG_PUB_ID = c.REG_PUB_ID ").append(" AND  b.OFIC_REG_ID = c.OFIC_REG_ID")		
		.append(" AND  b.REFNUM_PART = d.REFNUM_PART(+)").append(" AND  b.CERTIFICADO_ID = f.CERTIFICADO_ID ")
		.append(" AND  a.ESTADO = g.ESTADO_SOLICITUD").append(" AND  d.AREA_REG_ID = h.AREA_REG_ID(+) ")	
		.append(" AND to_char(a.TS_CREA,'yyyy/mm/dd') BETWEEN '").append(sfechaInicio)
		.append("' and '").append(sfechaFin).append("'");
		
	if (Loggy.isTrace(this))
		System.out.println("Solicitud QUERY ---> "+qestSol.toString());
	
		//*** MANEJO DE LA PAGINACION
	Propiedades propiedades = Propiedades.getInstance();		
	Statement stmt = null;
	ResultSet rset = null;
	try{
		stmt = conn.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(propiedades.getLineasPorPag());
		rset = stmt.executeQuery(qestSol.toString());
		System.out.println("Solicitud QUERY ---> "+qestSol.toString());
	
	if(paginacBean.getPaginacion() == 1){
		if (rset.last()){
			paginacBean.setTamano(rset.getRow());
			rset.beforeFirst();
		}
	}
	
	if (paginacBean.getPaginacion() > 1) {
		rset.absolute(propiedades.getLineasPorPag() * (paginacBean.getPaginacion() - 1));
	}
	
	int conta = 1;
	boolean hayNext = false;
	boolean encontro = false;
	boolean b = rset.next();
	BuscaCargaLaboralBean buscaCargLabBean = new BuscaCargaLaboralBean();	
	while (b == true && conta <= propiedades.getLineasPorPag()){	
		encontro = true;
		conta++;
		//Almaceno el resulado en el Bean buscaCargLabBean	
		buscaCargLabBean = new BuscaCargaLaboralBean();
		buscaCargLabBean.setSolicitud_id(rset.getString("SOLICITUD_ID"));

		//en Atributo certificado_ID de buscaCargaLaboral se seteo el Tipo de certificado
		buscaCargLabBean.setCertificado_id(rset.getString("TPO_CERTIFICADO"));
		// en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
		buscaCargLabBean.setTipoCertificado(rset.getString("CERTIFICADO_ID"));
		
		buscaCargLabBean.setNombre_Cert(rset.getString("TIPO_CERT"));
		buscaCargLabBean.setTpo_persona(rset.getString("TPO_PERS"));		
//		Inicio:jascencio:25/0607
		if(!(rset.getString("APE_PAT")==null || rset.getString("APE_PAT").equals(""))){
		
			if(!(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").equals(""))){
				if(!(rset.getString("NOMBRES")==null || rset.getString("NOMBRES").equals(""))){
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
				}
				else{
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("APE_MAT"));//Persona Natural
				}
			}
			else{
				buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT"));//Persona Natural
			}
		}
		else{
			buscaCargLabBean.setObjeto_certPN("");//Persona Natural
		}
		/*if(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").trim().equals(""))
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("NOMBRES"));//Persona Natural
		else		
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
		*/
		//Fin:jascencio
		buscaCargLabBean.setObjeto_certPJ(rset.getString("RAZ_SOC"));//Razon Social
		buscaCargLabBean.setNum_partida(rset.getString("NUM_PARTIDA"));		
		buscaCargLabBean.setArea_reg_id(rset.getString("AREA_REGISTRAL"));		
		buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_REGISTRADOR"));	//Estado de la solicitud
		buscaCargLabBean.setEstado_ext_sol(rset.getString("MENSAJE_USUARIO"));	//Estado de la solicitud
		buscaCargLabBean.setOfic_registral(rset.getString("NOMBRE_OFIC_REG"));//Nombre de Oficina Registral						
		buscaCargLabBean.setTpo_pers_solicitante(rset.getString("TPO_PERS_SOLICITANTE"));//tipo persona del solicitante
		buscaCargLabBean.setSolicitante_PN(rset.getString("APE_PAT_SOLICITANTE")+" "+rset.getString("APE_MAT_SOLICITANTE")+" "+rset.getString("NOMBRES_SOLICITANTE"));//
		buscaCargLabBean.setSolicitante_PJ(rset.getString("RAZ_SOC_SOLICITANTE"));//
		
		/* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: Seteo la descripcion del objeto de solicitud de cada tipo de certificado  */
		
		buscaCargLabBean.setPlaca(rset.getString("NUM_PLACA"));
		buscaCargLabBean.setNombreBien(rset.getString("NOMBRE_BIEN"));
		buscaCargLabBean.setNumeroMatricula(rset.getString("NUM_MATRICULA"));
		buscaCargLabBean.setNumeroSerie(rset.getString("NUM_SERIE"));
		buscaCargLabBean.setSiglas(rset.getString("SIGLAS"));
		buscaCargLabBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
		buscaCargLabBean.setTipoDocumento(rset.getString("TIP_DOCUMENTO"));
		buscaCargLabBean.setNumeroDocumento(rset.getString("NUM_DOCUMENTO"));
		buscaCargLabBean.setTipoInformacionDominio(rset.getString("TIP_INF_DOMINIO"));
		buscaCargLabBean.setNumeroPartida(rset.getString("NUMPARTIDA"));
		
		//ObjetoSolicitudBean objSolic= (ObjetoSolicitudBean)this.objetoSolicitudList.get(0);
		//String tipo_certi=objSolic.getTpo_cert();
		//String cert_id= objSolic.getCertificado_id();
		String tipo_certi= rset.getString("TPO_CERTIFICADO");// objSolic.getTpo_cert();
		String cert_id= rset.getString("CERTIFICADO_ID");// objSolic.getCertificado_id();

		String desc_aux="";
		String num_placa=null;
		String nombre_bien=null;
		String num_matricula=null;
		String num_serie=null;
		String ape_pat=null;
		String ape_mat=null;
		String nombres=null;
		String siglas=null;
		String raz_social=null;
		String tip_particip=null;
		String tip_docum=null;
		String num_docum=null;
		String tipo_pers=null;
		String tipo_inf_domin=null;
		String num_partida=null;
		/** inicio: jrosas 03-09-07 **/
		String tp_registro= null;
		String tip_registro =  null;
		String flag_historico = null;
		String fec_ins_asiento_desde = null;
		String fec_ins_asiento_hasta =	null;
	    /** fin: jrosas 03-09-07 **/
		//Inicio:mgarate:05/06/2007
		if(tipo_certi.equals("B"))
		{
			buscaCargLabBean.setDescripcionObjetoCertificado(rset.getString("CRIT_BUSQ"));
		}
		//Fin:mgarate:05/06/2007
		if (tipo_certi.equals("N")){       // certificado positivo/negativo
			num_placa = rset.getString("NUM_PLACA");
			nombre_bien = rset.getString("NOMBRE_BIEN");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			if (num_placa != null) {
				desc_aux += "NUM_PLACA: "+num_placa;
			}
			if (nombre_bien != null) {
				desc_aux += "NOMBRE_BIEN: "+nombre_bien;
		    /** inicio: jrosas24-08-07 **/
				if (num_matricula != null) {
					desc_aux += ", NUM_MATRICULA: "+num_matricula;
				}	
			}else if (num_matricula != null) {
				desc_aux += "NUM_MATRICULA: "+num_matricula;
			}
			/** fin: jrosas24-08-07 **/
			if (num_serie != null) {
				desc_aux += "NUM_SERIE: "+num_serie;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("R")){     // certificado de vigencia o gravamen
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_docum= rset.getString("TIP_DOCUMENTO");
			num_docum= rset.getString("NUM_DOCUMENTO");
			tipo_pers= rset.getString("TPO_PERS");
			
			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			if (tip_docum != null) {
				//Inicio:jascencio:26/06/07
				String desTipoDoc=null;
				if(!tip_docum.equals("")){
					if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LE)){
						desTipoDoc="L.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LM)){
						desTipoDoc="L.M";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CE)){
						desTipoDoc="C.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CI)){
						desTipoDoc="CI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_RUC)){
						desTipoDoc="R.U.C";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS) || tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS2)){
						desTipoDoc="PS";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_DNI)){
						desTipoDoc="DNI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LEM)){
						desTipoDoc="LEM";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CIP)){
						desTipoDoc="C.I.P";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CM)){
						desTipoDoc="C.M";
					}
				}
				//Fin:jascencio
				desc_aux += "TIP_DOCUMENTO: "+desTipoDoc;
			}
			if (num_docum != null) {
				desc_aux += ", NUM_DOCUMENTO: "+num_docum;
			}
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			if (tipo_pers != null) {
				tipo_pers="";
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("D")){       // certificado RJB (Tipo: Dominial )
			tipo_inf_domin = rset.getString("TIP_INF_DOMINIO");
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (tipo_inf_domin != null) {
				if (tipo_inf_domin.equals("C"))
					desc_aux += "TIPO_INF_DOMINIO: Completa, ";
				else
					desc_aux += "TIPO_INF_DOMINIO: Último Propietario, ";
			}
			if (cert_id.equals("25")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("26")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("27")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("28")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("G")){       // certificado RJB (Tipo: Gravamen)
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (cert_id.equals("29")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("30")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("31")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("32")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
				
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
			
		}
		/*	fin:jrosas 31-05-2007*/
//		Inicio:jascencio:27/06/07
		if(tipo_certi.equals("C")){//Certificado CREM
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			/*** inicio:jrosas 31-08-07 **/
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_registro =  rset.getString("TIP_REGISTRO");
			flag_historico = rset.getString("FLAG_HISTORICO");
			fec_ins_asiento_desde = rset.getString("FEC_INS_ASIENTO_DESDE");
			if (fec_ins_asiento_desde != null)
				fec_ins_asiento_desde = fec_ins_asiento_desde.substring(0, 11);
			fec_ins_asiento_hasta =	rset.getString("FEC_INS_ASIENTO_HASTA");
			if (fec_ins_asiento_hasta != null)
				fec_ins_asiento_hasta = fec_ins_asiento_hasta.substring(0, 11);
			/*** fin:jrosas 31-08-07 **/

			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			/*** inicio: jrosas 31-08-07 **/
			ArrayList cadAux= new ArrayList();
			if (tip_registro != null){
				StringTokenizer tokens=new StringTokenizer(tip_registro, ",");
				while (tokens.hasMoreTokens()){
					String str_cod_aux = tokens.nextToken();
					cadAux.add(str_cod_aux);
				}
				desc_aux += ", REGISTROS:";
				int len= cadAux.size();
				String cad_reg="";
				for (int k=0; k<len; k++){
					String str_cod= (String)cadAux.get(k);
					if (k ==(len-1)){
						if (str_cod.equals("RMC")){
							cad_reg += "RMC";
						}
						if (str_cod.equals("VEH")){
							cad_reg += "Vehicular";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones)";
			            }
					}else{
						if (str_cod.equals("RMC")){
							cad_reg += "RMC, ";
						}
					    if (str_cod.equals("VEH")){
					    	cad_reg += "Vehicular, ";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera, ";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques, ";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves, ";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones), ";
			            }
					} //else      
				}//for
				desc_aux += cad_reg;
			}//tipo_registro
			
			if (flag_historico != null){
				if (flag_historico.equals("1"))
					desc_aux += ", HISTÓRICO: Sí";
				else if (flag_historico.equals("0")){
					desc_aux += ", HISTÓRICO: No";
				}
			}
			if (fec_ins_asiento_desde != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO DESDE: "+fec_ins_asiento_desde;
				if (fec_ins_asiento_hasta != null){
					desc_aux += " HASTA "+fec_ins_asiento_hasta;
				}
			}else if (fec_ins_asiento_hasta != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO HASTA: "+fec_ins_asiento_hasta;
			}
			/*** FIN: jrosas 31-08-07 **/
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);

			
		}
		//Fin:jascencio

		//inicio:dbravo:20/08/2007
		if(buscaCargLabBean.getTipoCertificado()!=null &&
			(
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)
			)
		){
			if(buscaCargLabBean.getEstado_sol_id()!=null && buscaCargLabBean.getEstado_sol_id().equalsIgnoreCase("E")){
				
				DboCertificado dboCertificado = new DboCertificado();
				dboCertificado.clearAll();
				dboCertificado.setFieldsToRetrieve(DboCertificado.CAMPO_FLAG_PAGO_CREM);
				dboCertificado.setField(DboCertificado.CAMPO_SOLICITUD_ID, buscaCargLabBean.getSolicitud_id());
				if(dboCertificado.find()){
					buscaCargLabBean.setFlagPagoCrem(dboCertificado.getField(DboCertificado.CAMPO_FLAG_PAGO_CREM));
				}
				
			}else{
				buscaCargLabBean.setFlagPagoCrem("1");
			}
			
		}else{
			buscaCargLabBean.setFlagPagoCrem("1");
		}
		//fin:dbravo:20/08/2007
		
		arrbuscaList.add(buscaCargLabBean);	
		if(encontro){
		   paginacBean.setEncontro("SI");				
		}else{
			paginacBean.setEncontro("NO");				
		}		
		b = rset.next();
	}
	
	hayNext = rset.next();
	//*PAGINACION EN EL JSP*//		
	paginacBean.setHayNext(hayNext);
	paginacBean.setPropiedades(propiedades);	

	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return arrbuscaList;
}
public ArrayList recuperarXPersonaNat(String tipoBusqueda, String  sApePat, String sApeMat, String sNombre, String cuentaId) throws Throwable{
	return recuperarDBXPersonaNat(tipoBusqueda, sApePat, sApeMat, sNombre, cuentaId);
}

protected ArrayList recuperarDBXPersonaNat(String tipoBusqueda, String  sApePat, String sApeMat, String sNombre, String cuentaId) throws Throwable{
	StringBuffer qsel = new StringBuffer();
	
	   //Query busqueda de solicitud 
	   qsel.append("SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, f.NOMBRE as TIPO_CERT, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC , ")
	    /* inicio:jrosas 31-05-2007
	       SUNARP-REGMOBCOM: seteo de nuevos campos de la tabla objetosolicitud  */
	    .append(" b.NUM_PLACA, b.NOMBRE_BIEN, b.NUM_MATRICULA, b.NUM_SERIE, b.SIGLAS, b.TIP_PARTICIPANTE, b.TIP_DOCUMENTO, b.NUM_DOCUMENTO, b.TIP_INF_DOMINIO, b.NUM_PARTIDA as NUMPARTIDA,f.CERTIFICADO_ID, ")

	    /*	fin:jrosas 31-05-2007*/
	    // Inicio:mgarate:13/06/2007
	    /** inicio: jrosas 03-09-07 **/
		.append("b.CRIT_BUSQ, b.TIP_REGISTRO, b.FLAG_HISTORICO, b.FEC_INS_ASIENTO_DESDE, b.FEC_INS_ASIENTO_HASTA,f.COD_GRUPO_LIBRO_AREA, ")
   	 	/** fin: jrosas 03-09-07 **/
	    // Fin:mgarate:13/06/2007
 	    .append(" c.NOMBRE as NOMBRE_OFIC_REG,  g.MENSAJE_REGISTRADOR, g.MENSAJE_USUARIO, d.NUM_PARTIDA as NUM_PARTIDA, h.NOMBRE as AREA_REGISTRAL, ")
 	    .append(" i.TPO_PERS as TPO_PERS_SOLICITANTE, i.APE_MAT as APE_MAT_SOLICITANTE, i.APE_PAT as APE_PAT_SOLICITANTE, i.NOMBRES as NOMBRES_SOLICITANTE, i.RAZ_SOC as RAZ_SOC_SOLICITANTE" )
	    .append(" FROM")
	    .append(" SOLICITUD a, OBJETO_SOLICITUD b, OFIC_REGISTRAL c, PARTIDA d, TM_CERTIFICADOS f, TM_ESTADO_SOLICITUD g, TM_AREA_REGISTRAL h, SOLICITANTE i ")
		.append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID ").append(" AND b.REG_PUB_ID = c.REG_PUB_ID ")
		.append(" AND b.OFIC_REG_ID = c.OFIC_REG_ID ").append(" AND b.REFNUM_PART = d.REFNUM_PART(+)")
		.append(" AND b.CERTIFICADO_ID = f.CERTIFICADO_ID ").append(" AND a.ESTADO = g.ESTADO_SOLICITUD")
		.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+) ").append(" AND a.SOLICITUD_ID = i.SOLICITUD_ID ");
		if (tipoBusqueda.equals("1")){
			//busqueda de la solicitud por ApePat y nombre 
			qsel.append(" AND i.APE_PAT like '").append(sApePat).append("%'").append(" AND i.NOMBRES like '").append(sNombre).append("%'");						
		}
		if (tipoBusqueda.equals("2")){
			//busqueda de la solicitud por ApePat y ApeMat
			qsel.append(" AND i.APE_PAT like '").append(sApePat).append("%'")
			.append(" AND i.APE_MAT like '").append(sApeMat).append("%'");		
		}
		if (tipoBusqueda.equals("3")){
			//busqueda de la solicitud por ApePat, ApeMat y Nombres
			qsel.append(" AND i.APE_PAT like '").append(sApePat).append("%'")
			.append(" AND i.APE_MAT like '").append(sApeMat).append("%'")
			.append(" AND i.NOMBRES like '").append(sNombre).append("%'");			
		} 	
		qsel.append(" ORDER BY a.SOLICITUD_ID asc");
		
	if (Loggy.isTrace(this))
	System.out.println("Solicitud QUERY ---> "+qsel.toString());
	
	//*** MANEJO DE LA PAGINACION
	Propiedades propiedades = Propiedades.getInstance();
	ArrayList arrbuscaList = new ArrayList();
	Statement stmt = null;
	ResultSet rset = null;
	try{
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(propiedades.getLineasPorPag());
		rset = stmt.executeQuery(qsel.toString());
		
	if(paginacBean.getPaginacion() == 1){
		if (rset.last()){
			paginacBean.setTamano(rset.getRow());
			rset.beforeFirst();
		}
	}
	
	if (paginacBean.getPaginacion() > 1) {
		rset.absolute(propiedades.getLineasPorPag() * (paginacBean.getPaginacion() - 1));
	}
	
	int conta = 1;
	boolean hayNext = false;
	boolean encontro = false;
	boolean b = rset.next();	
	
	BuscaCargaLaboralBean buscaCargLabBean = new BuscaCargaLaboralBean();
	while (b == true && conta <= propiedades.getLineasPorPag()){
		encontro = true;
		conta++;
		//Almaceno el resulado en el Bean buscaCargLabBean	
		buscaCargLabBean = new BuscaCargaLaboralBean();
		buscaCargLabBean.setSolicitud_id(rset.getString("SOLICITUD_ID"));

		// en Atributo certificado_ID de buscaCargaLaboral se seteo el Tipo de certificado
		buscaCargLabBean.setCertificado_id(rset.getString("TPO_CERTIFICADO"));
		// en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
		buscaCargLabBean.setTipoCertificado(rset.getString("CERTIFICADO_ID"));
		
		buscaCargLabBean.setNombre_Cert(rset.getString("TIPO_CERT"));
		buscaCargLabBean.setTpo_persona(rset.getString("TPO_PERS"));	
//		Inicio:jascencio:25/0607
		if(!(rset.getString("APE_PAT")==null || rset.getString("APE_PAT").equals(""))){
		
			if(!(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").equals(""))){
				if(!(rset.getString("NOMBRES")==null || rset.getString("NOMBRES").equals(""))){
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
				}
				else{
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("APE_MAT"));//Persona Natural
				}
			}
			else{
				buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT"));//Persona Natural
			}
		}
		else{
			buscaCargLabBean.setObjeto_certPN("");//Persona Natural
		}
		/*if(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").trim().equals(""))
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("NOMBRES"));//Persona Natural
		else		
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
		*/
		//Fin:jascencio
		buscaCargLabBean.setObjeto_certPJ(rset.getString("RAZ_SOC"));//Razon Social
		buscaCargLabBean.setNum_partida(rset.getString("NUM_PARTIDA"));		
		buscaCargLabBean.setArea_reg_id(rset.getString("AREA_REGISTRAL"));		
		buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_REGISTRADOR"));	//Estado de la solicitud
		buscaCargLabBean.setEstado_ext_sol(rset.getString("MENSAJE_USUARIO"));	//Estado de la solicitud
		buscaCargLabBean.setOfic_registral(rset.getString("NOMBRE_OFIC_REG"));//Nombre de Oficina Registral										
		buscaCargLabBean.setTpo_pers_solicitante(rset.getString("TPO_PERS_SOLICITANTE"));//tipo persona del solicitante
		buscaCargLabBean.setSolicitante_PN(rset.getString("APE_PAT_SOLICITANTE")+" "+rset.getString("APE_MAT_SOLICITANTE")+" "+rset.getString("NOMBRES_SOLICITANTE"));//
		buscaCargLabBean.setSolicitante_PJ(rset.getString("RAZ_SOC_SOLICITANTE"));//
		
		/* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: Seteo la descripcion del objeto de solicitud de cada tipo de certificado  */
		
		buscaCargLabBean.setPlaca(rset.getString("NUM_PLACA"));
		buscaCargLabBean.setNombreBien(rset.getString("NOMBRE_BIEN"));
		buscaCargLabBean.setNumeroMatricula(rset.getString("NUM_MATRICULA"));
		buscaCargLabBean.setNumeroSerie(rset.getString("NUM_SERIE"));
		buscaCargLabBean.setSiglas(rset.getString("SIGLAS"));
		buscaCargLabBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
		buscaCargLabBean.setTipoDocumento(rset.getString("TIP_DOCUMENTO"));
		buscaCargLabBean.setNumeroDocumento(rset.getString("NUM_DOCUMENTO"));
		buscaCargLabBean.setTipoInformacionDominio(rset.getString("TIP_INF_DOMINIO"));
		buscaCargLabBean.setNumeroPartida(rset.getString("NUMPARTIDA"));
		
		//ObjetoSolicitudBean objSolic= (ObjetoSolicitudBean)this.objetoSolicitudList.get(0);
		//String tipo_certi=objSolic.getTpo_cert();
		//String cert_id= objSolic.getCertificado_id();
		String tipo_certi= rset.getString("TPO_CERTIFICADO");// objSolic.getTpo_cert();
		String cert_id= rset.getString("CERTIFICADO_ID");// objSolic.getCertificado_id();

		String desc_aux="";
		String num_placa=null;
		String nombre_bien=null;
		String num_matricula=null;
		String num_serie=null;
		String ape_pat=null;
		String ape_mat=null;
		String nombres=null;
		String siglas=null;
		String raz_social=null;
		String tip_particip=null;
		String tip_docum=null;
		String num_docum=null;
		String tipo_pers=null;
		String tipo_inf_domin=null;
		String num_partida=null;
		/*** inicio:jrosas 31-08-07 **/   	 	    	
		String tp_registro= null;
		String tip_registro =  null;
		String flag_historico = null;
		String fec_ins_asiento_desde = null;
		String fec_ins_asiento_hasta =	null;
		/** fin: jrosas 03-09-07 **/
				
	   //Inicio:mgarate:13/06/2007
	   if(tipo_certi.equals("B"))
	   {
		   buscaCargLabBean.setDescripcionObjetoCertificado(rset.getString("CRIT_BUSQ"));
	   }
	   // Fin:mgarate:13/06/2007
		if (tipo_certi.equals("N")){       // certificado positivo/negativo
			num_placa = rset.getString("NUM_PLACA");
			nombre_bien = rset.getString("NOMBRE_BIEN");
			num_matricula = rset.getString("NUM_MATRICULA");
			//num_serie = rset.getString("NUM_SERIE");
			
			if (num_placa != null) {
				desc_aux += "NUM_PLACA: "+num_placa;
			}
			if (nombre_bien != null) {
				desc_aux += "NOMBRE_BIEN: "+nombre_bien;
		    /** inicio: jrosas24-08-07 **/
				if (num_matricula != null) {
					desc_aux += ", NUM_MATRICULA: "+num_matricula;
				}	
			}else if (num_matricula != null) {
				desc_aux += "NUM_MATRICULA: "+num_matricula;
			}
			/** fin: jrosas24-08-07 **/
			if (num_serie != null) {
				desc_aux += "NUM_SERIE: "+num_serie;
			}
			//if (num_serie == null) num_serie="";
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("R")){     // certificado de vigencia o gravamen
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_docum= rset.getString("TIP_DOCUMENTO");
			num_docum= rset.getString("NUM_DOCUMENTO");
			tipo_pers= rset.getString("TPO_PERS");
			
			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			if (tip_docum != null) {
				//Inicio:jascencio:26/06/07
				String desTipoDoc=null;
				if(!tip_docum.equals("")){
					if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LE)){
						desTipoDoc="L.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LM)){
						desTipoDoc="L.M";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CE)){
						desTipoDoc="C.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CI)){
						desTipoDoc="CI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_RUC)){
						desTipoDoc="R.U.C";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS) || tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS2)){
						desTipoDoc="PS";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_DNI)){
						desTipoDoc="DNI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LEM)){
						desTipoDoc="LEM";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CIP)){
						desTipoDoc="C.I.P";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CM)){
						desTipoDoc="C.M";
					}
				}
				//Fin:jascencio
				desc_aux += "TIP_DOCUMENTO: "+desTipoDoc;
			}
			if (num_docum != null) {
				desc_aux += ", NUM_DOCUMENTO: "+num_docum;
			}
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			if (tipo_pers != null) {
				tipo_pers="";
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("D")){       // certificado RJB (Tipo: Dominial )
			tipo_inf_domin = rset.getString("TIP_INF_DOMINIO");
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("cod_grupo_libro_area"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (tipo_inf_domin != null) {
				if (tipo_inf_domin.equals("C"))
					desc_aux += "TIPO_INF_DOMINIO: Completa, ";
				else
					desc_aux += "TIPO_INF_DOMINIO: Último Propietario, ";
			}
			if (cert_id.equals("25")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("26")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("27")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("28")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("G")){       // certificado RJB (Tipo: Gravamen)
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");

			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("cod_grupo_libro_area"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (cert_id.equals("29")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("30")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("31")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("32")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
				
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
					
		}
		/*	fin:jrosas 31-05-2007*/
//		Inicio:jascencio:27/06/07
		if(tipo_certi.equals("C")){//Certificado CREM
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			/*** inicio:jrosas 31-08-07 **/
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_registro =  rset.getString("TIP_REGISTRO");
			flag_historico = rset.getString("FLAG_HISTORICO");
			fec_ins_asiento_desde = rset.getString("FEC_INS_ASIENTO_DESDE");
			if (fec_ins_asiento_desde != null)
				fec_ins_asiento_desde = fec_ins_asiento_desde.substring(0, 11);
			fec_ins_asiento_hasta =	rset.getString("FEC_INS_ASIENTO_HASTA");
			if (fec_ins_asiento_hasta != null)
				fec_ins_asiento_hasta = fec_ins_asiento_hasta.substring(0, 11);
			/*** fin:jrosas 31-08-07 **/

			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			/*** inicio: jrosas 31-08-07 **/
			ArrayList cadAux= new ArrayList();
			if (tip_registro != null){
				StringTokenizer tokens=new StringTokenizer(tip_registro, ",");
				while (tokens.hasMoreTokens()){
					String str_cod_aux = tokens.nextToken();
					cadAux.add(str_cod_aux);
				}
				desc_aux += ", REGISTROS:";
				int len= cadAux.size();
				String cad_reg="";
				for (int k=0; k<len; k++){
					String str_cod= (String)cadAux.get(k);
					if (k ==(len-1)){
						if (str_cod.equals("RMC")){
							cad_reg += "RMC";
						}
						if (str_cod.equals("VEH")){
							cad_reg += "Vehicular";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones)";
			            }
					}else{
						if (str_cod.equals("RMC")){
							cad_reg += "RMC, ";
						}
					    if (str_cod.equals("VEH")){
					    	cad_reg += "Vehicular, ";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera, ";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques, ";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves, ";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones), ";
			            }
					} //else      
				}//for
				desc_aux += cad_reg;
			}//tipo_registro
			
			if (flag_historico != null){
				if (flag_historico.equals("1"))
					desc_aux += ", HISTÓRICO: Sí";
				else if (flag_historico.equals("0")){
					desc_aux += ", HISTÓRICO: No";
				}
			}
			if (fec_ins_asiento_desde != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO DESDE: "+fec_ins_asiento_desde;
				if (fec_ins_asiento_hasta != null){
					desc_aux += " HASTA "+fec_ins_asiento_hasta;
				}
			}else if (fec_ins_asiento_hasta != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO HASTA: "+fec_ins_asiento_hasta;
			}
			/*** FIN: jrosas 31-08-07 **/
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}

				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);

			
		}
		//Fin:jascencio
		//inicio:dbravo:20/08/2007
		if(buscaCargLabBean.getTipoCertificado()!=null &&
			(
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)
			)
		){
			if(buscaCargLabBean.getEstado_sol_id()!=null && buscaCargLabBean.getEstado_sol_id().equalsIgnoreCase("E")){
				
				DboCertificado dboCertificado = new DboCertificado();
				dboCertificado.clearAll();
				dboCertificado.setFieldsToRetrieve(DboCertificado.CAMPO_FLAG_PAGO_CREM);
				dboCertificado.setField(DboCertificado.CAMPO_SOLICITUD_ID, buscaCargLabBean.getSolicitud_id());
				if(dboCertificado.find()){
					buscaCargLabBean.setFlagPagoCrem(dboCertificado.getField(DboCertificado.CAMPO_FLAG_PAGO_CREM));
				}
				
			}else{
				buscaCargLabBean.setFlagPagoCrem("1");
			}
			
		}else{
			buscaCargLabBean.setFlagPagoCrem("1");
		}
		//fin:dbravo:20/08/2007
		
		arrbuscaList.add(buscaCargLabBean);
		if(encontro){
		   paginacBean.setEncontro("SI");				
		}else{
			paginacBean.setEncontro("NO");				
		}
		b = rset.next();
	}	
		hayNext = rset.next();
		//*PAGINACION EN EL JSP*//		
		paginacBean.setHayNext(hayNext);
		paginacBean.setPropiedades(propiedades);	
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return arrbuscaList;
}

public ArrayList recuperarXPersonaJurid(String razonSocial, String cuentaId) throws Throwable{
	return recuperarDBXPersonaJurid(razonSocial, cuentaId);
}

public ArrayList recuperarDBXPersonaJurid(String razonSocial, String cuentaId) throws Throwable{
	StringBuffer qsel = new StringBuffer();
	//Query busqueda de solicitud 
	   qsel.append("SELECT a.SOLICITUD_ID, a.ESTADO as ESTADO_SOL, f.TPO_CERTIFICADO, f.NOMBRE as TIPO_CERT, b.TPO_PERS, b.APE_MAT, b.APE_PAT, b.NOMBRES, b.RAZ_SOC , ")
	    /* inicio:jrosas 31-05-2007
	       SUNARP-REGMOBCOM: seteo de nuevos campos de la tabla objetosolicitud  */
	    .append(" b.NUM_PLACA, b.NOMBRE_BIEN, b.NUM_MATRICULA, b.NUM_SERIE, b.SIGLAS, b.TIP_PARTICIPANTE, b.TIP_DOCUMENTO, b.NUM_DOCUMENTO, b.TIP_INF_DOMINIO, b.NUM_PARTIDA as NUMPARTIDA,f.CERTIFICADO_ID, ")

	    /*	fin:jrosas 31-05-2007*/
	    // Inicio:mgarate:13/06/2007
	    /** inicio: jrosas 03-09-07 **/
		.append("b.CRIT_BUSQ, b.TIP_REGISTRO, b.FLAG_HISTORICO, b.FEC_INS_ASIENTO_DESDE, b.FEC_INS_ASIENTO_HASTA,f.COD_GRUPO_LIBRO_AREA, ")
   	 	/** fin: jrosas 03-09-07 **/
	    // Fin:mgarate:13/06/2007
 	    .append(" c.NOMBRE as NOMBRE_OFIC_REG,  g.MENSAJE_REGISTRADOR, g.MENSAJE_USUARIO, d.NUM_PARTIDA as NUM_PARTIDA, h.NOMBRE as AREA_REGISTRAL, ")
 	    .append(" i.TPO_PERS as TPO_PERS_SOLICITANTE, i.APE_MAT as APE_MAT_SOLICITANTE, i.APE_PAT as APE_PAT_SOLICITANTE, i.NOMBRES as NOMBRES_SOLICITANTE, i.RAZ_SOC as RAZ_SOC_SOLICITANTE ")
	    .append(" FROM")
	    .append(" SOLICITUD a, OBJETO_SOLICITUD b, OFIC_REGISTRAL c, PARTIDA d, TM_CERTIFICADOS f, TM_ESTADO_SOLICITUD g, TM_AREA_REGISTRAL h, SOLICITANTE i ")
		.append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID ").append(" AND b.REG_PUB_ID = c.REG_PUB_ID ")
		.append(" AND b.OFIC_REG_ID = c.OFIC_REG_ID ").append(" AND b.REFNUM_PART = d.REFNUM_PART(+)")
		.append(" AND b.CERTIFICADO_ID = f.CERTIFICADO_ID ").append(" AND a.ESTADO = g.ESTADO_SOLICITUD")
		.append(" AND d.AREA_REG_ID = h.AREA_REG_ID(+) ").append(" AND a.SOLICITUD_ID = i.SOLICITUD_ID ")		
		.append(" AND i.RAZ_SOC like '").append(razonSocial) 
		.append("%' ORDER BY a.SOLICITUD_ID asc");
		
	if (Loggy.isTrace(this))
		System.out.println("Solicitud QUERY ---> "+qsel.toString());
	
	//*** MANEJO DE LA PAGINACION
	Propiedades propiedades = Propiedades.getInstance();
	ArrayList arrbuscaList = new ArrayList();
	Statement stmt = null;
	ResultSet rset = null;
	
	try{
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(propiedades.getLineasPorPag());
		rset = stmt.executeQuery(qsel.toString());
	
	if(paginacBean.getPaginacion() == 1){
		if (rset.last()){
			paginacBean.setTamano(rset.getRow());
			rset.beforeFirst();
		}
	}
	
	if (paginacBean.getPaginacion() > 1) {
		rset.absolute(propiedades.getLineasPorPag() * (paginacBean.getPaginacion() - 1));
	}
	
	int conta = 1;
	boolean hayNext = false;
	boolean encontro = false;
	boolean b = rset.next();	
	
	BuscaCargaLaboralBean buscaCargLabBean = new BuscaCargaLaboralBean();
	while (b == true && conta <= propiedades.getLineasPorPag()){
		encontro = true;
		conta++;
		//Almaceno el resulado en el Bean buscaCargLabBean	
		buscaCargLabBean = new BuscaCargaLaboralBean();
		buscaCargLabBean.setSolicitud_id(rset.getString("SOLICITUD_ID"));
		
        //en Atributo certificado_ID de buscaCargaLaboral se seteo el Tipo de certificado
		buscaCargLabBean.setCertificado_id(rset.getString("TPO_CERTIFICADO"));
		// en Atributo tipocertificado de buscaCargaLaboral se seteo el certificado_id
		buscaCargLabBean.setTipoCertificado(rset.getString("CERTIFICADO_ID"));
		
		buscaCargLabBean.setNombre_Cert(rset.getString("TIPO_CERT"));
		buscaCargLabBean.setTpo_persona(rset.getString("TPO_PERS"));	
//		Inicio:jascencio:25/0607
		if(!(rset.getString("APE_PAT")==null || rset.getString("APE_PAT").equals(""))){
		
			if(!(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").equals(""))){
				if(!(rset.getString("NOMBRES")==null || rset.getString("NOMBRES").equals(""))){
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
				}
				else{
					buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("APE_MAT"));//Persona Natural
				}
			}
			else{
				buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT"));//Persona Natural
			}
		}
		else{
			buscaCargLabBean.setObjeto_certPN("");//Persona Natural
		}
		
		/*if(rset.getString("APE_MAT")==null || rset.getString("APE_MAT").trim().equals(""))
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT") + " " + rset.getString("NOMBRES"));//Persona Natural
		else		
			buscaCargLabBean.setObjeto_certPN(rset.getString("APE_PAT")+ " " + rset.getString("APE_MAT")+ " " + rset.getString("NOMBRES"));//Persona Natural
		*/
		//Fin:jascencio
		buscaCargLabBean.setObjeto_certPJ(rset.getString("RAZ_SOC"));//Razon Social
		buscaCargLabBean.setNum_partida(rset.getString("NUM_PARTIDA"));		
		buscaCargLabBean.setArea_reg_id(rset.getString("AREA_REGISTRAL"));		
		buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_REGISTRADOR"));	//Estado de la solicitud
		buscaCargLabBean.setEstado_sol(rset.getString("MENSAJE_USUARIO"));	//Estado de la solicitud
		buscaCargLabBean.setOfic_registral(rset.getString("NOMBRE_OFIC_REG"));//Nombre de Oficina Registral						
		buscaCargLabBean.setTpo_pers_solicitante(rset.getString("TPO_PERS_SOLICITANTE"));//tipo persona del solicitante
		if(rset.getString("APE_MAT_SOLICITANTE")==null || rset.getString("APE_MAT_SOLICITANTE").trim().equals(""))
			buscaCargLabBean.setSolicitante_PN(rset.getString("APE_PAT_SOLICITANTE") + " " + rset.getString("NOMBRES_SOLICITANTE"));//Persona Natural
		else		
			buscaCargLabBean.setSolicitante_PN(rset.getString("APE_PAT_SOLICITANTE")+ " " + rset.getString("APE_MAT_SOLICITANTE")+ " " + rset.getString("NOMBRES_SOLICITANTE"));//Persona Natural
		buscaCargLabBean.setSolicitante_PJ(rset.getString("RAZ_SOC_SOLICITANTE"));//

		/* inicio:jrosas 31-05-2007
		   SUNARP-REGMOBCOM: Seteo la descripcion del objeto de solicitud de cada tipo de certificado  */
		
		buscaCargLabBean.setPlaca(rset.getString("NUM_PLACA"));
		buscaCargLabBean.setNombreBien(rset.getString("NOMBRE_BIEN"));
		buscaCargLabBean.setNumeroMatricula(rset.getString("NUM_MATRICULA"));
		buscaCargLabBean.setNumeroSerie(rset.getString("NUM_SERIE"));
		buscaCargLabBean.setSiglas(rset.getString("SIGLAS"));
		buscaCargLabBean.setTipoParticipante(rset.getString("TIP_PARTICIPANTE"));
		buscaCargLabBean.setTipoDocumento(rset.getString("TIP_DOCUMENTO"));
		buscaCargLabBean.setNumeroDocumento(rset.getString("NUM_DOCUMENTO"));
		buscaCargLabBean.setTipoInformacionDominio(rset.getString("TIP_INF_DOMINIO"));
		buscaCargLabBean.setNumeroPartida(rset.getString("NUMPARTIDA"));
		
		//ObjetoSolicitudBean objSolic= (ObjetoSolicitudBean)this.objetoSolicitudList.get(0);
		//String tipo_certi=objSolic.getTpo_cert();
		//String cert_id= objSolic.getCertificado_id();
		String tipo_certi= rset.getString("TPO_CERTIFICADO");// objSolic.getTpo_cert();
		String cert_id= rset.getString("CERTIFICADO_ID");// objSolic.getCertificado_id();

		String desc_aux="";
		String num_placa=null;
		String nombre_bien=null;
		String num_matricula=null;
		String num_serie=null;
		String ape_pat=null;
		String ape_mat=null;
		String nombres=null;
		String siglas=null;
		String raz_social=null;
		String tip_particip=null;
		String tip_docum=null;
		String num_docum=null;
		String tipo_pers=null;
		String tipo_inf_domin=null;
		String num_partida=null;
		/*** inicio:jrosas 31-08-07 **/   	 	    	
		String tp_registro= null;
		String tip_registro =  null;
		String flag_historico = null;
		String fec_ins_asiento_desde = null;
		String fec_ins_asiento_hasta =	null;
		/** fin: jrosas 03-09-07 **/
				
		//Inicio:mgarate:05/06/2007
		if(tipo_certi.equals("B"))
		{
			buscaCargLabBean.setDescripcionObjetoCertificado(rset.getString("CRIT_BUSQ")); 
		}
		//Fin:mgarate:05/06/2007
		if (tipo_certi.equals("N")){       // certificado positivo/negativo
			num_placa = rset.getString("NUM_PLACA");
			nombre_bien = rset.getString("NOMBRE_BIEN");
			num_matricula = rset.getString("NUM_MATRICULA");
			//num_serie = rset.getString("NUM_SERIE");
			
			if (num_placa != null) {
				desc_aux += "NUM_PLACA: "+num_placa;
			}
			if (nombre_bien != null) {
				desc_aux += "NOMBRE_BIEN: "+nombre_bien;
		    /** inicio: jrosas24-08-07 **/
				if (num_matricula != null) {
					desc_aux += ", NUM_MATRICULA: "+num_matricula;
				}					
			}else if (num_matricula != null) {
				desc_aux += "NUM_MATRICULA: "+num_matricula;
			}
			/** fin: jrosas24-08-07 **/
			if (num_serie != null) {
				desc_aux += "NUM_SERIE: "+num_serie;
			}
			//if (num_serie == null) num_serie="";
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("R")){     // certificado de vigencia o gravamen
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_docum= rset.getString("TIP_DOCUMENTO");
			num_docum= rset.getString("NUM_DOCUMENTO");
			tipo_pers= rset.getString("TPO_PERS");
			
			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			if (tip_docum != null) {
				//Inicio:jascencio:26/06/07
				String desTipoDoc=null;
				if(!tip_docum.equals("")){
					if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LE)){
						desTipoDoc="L.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LM)){
						desTipoDoc="L.M";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CE)){
						desTipoDoc="C.E";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CI)){
						desTipoDoc="CI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_RUC)){
						desTipoDoc="R.U.C";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS) || tip_docum.equals(Constantes.TIPO_DOCUMENTO_PS2)){
						desTipoDoc="PS";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_DNI)){
						desTipoDoc="DNI";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_LEM)){
						desTipoDoc="LEM";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CIP)){
						desTipoDoc="C.I.P";
					}else if(tip_docum.equals(Constantes.TIPO_DOCUMENTO_CM)){
						desTipoDoc="C.M";
					}
				}
				//Fin:jascencio
				desc_aux += "TIP_DOCUMENTO: "+desTipoDoc;
			}
			if (num_docum != null) {
				desc_aux += ", NUM_DOCUMENTO: "+num_docum;
			}
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}
				//Fin:jascencio
				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			if (tipo_pers != null) {
				tipo_pers="";
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("D")){       // certificado RJB (Tipo: Dominial )
			tipo_inf_domin = rset.getString("TIP_INF_DOMINIO");
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (tipo_inf_domin != null) {
				if (tipo_inf_domin.equals("C"))
					desc_aux += "TIPO_INF_DOMINIO: Completa, ";
				else
					desc_aux += "TIPO_INF_DOMINIO: Último Propietario, ";
			}
			if (cert_id.equals("25")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("26")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("27")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("28")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		}
		if (tipo_certi.equals("G")){       // certificado RJB (Tipo: Gravamen)
			num_placa = rset.getString("NUM_PLACA");
			num_partida = rset.getString("NUMPARTIDA");
			num_matricula = rset.getString("NUM_MATRICULA");
			num_serie = rset.getString("NUM_SERIE");
			
			DboGrupoLibroArea gla= new DboGrupoLibroArea();
			gla.setConnection(dconn);
			gla.setField(DboGrupoLibroArea.CAMPO_COD_GRUPO_LIBRO_AREA, rset.getString("COD_GRUPO_LIBRO_AREA"));
			gla.find();
			desc_aux += "LIBRO: "+gla.getField(DboGrupoLibroArea.CAMPO_DESC_GRUPO_LIBRO_AREA)+", ";
			
			if (cert_id.equals("29")){     // certificado Gravamen - Vehicular
				if (num_placa != null) {
					desc_aux += "NUM_PLACA: "+num_placa;
				}
				if (num_partida != null){
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
			}
			if (cert_id.equals("30")){     // certificado Gravamen - Buques
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
			if (cert_id.equals("31")){     // certificado Gravamen - Aeronaves
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
					if (num_serie != null){
						desc_aux += ", NUM_SERIE: "+num_serie;
					}
				}else{
					if (num_serie != null){
						desc_aux += "NUM_SERIE: "+num_serie;
					}
				}
			}
			if (cert_id.equals("32")){     // certificado Gravamen - Embarcaciones Pesqueras
				if (num_partida != null) {
					desc_aux += "NUM_PARTIDA: "+num_partida;
				}
				if (num_matricula != null) {
					desc_aux += "NUM_MATRICULA: "+num_matricula;
				}
			}
				
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);
		
		}
		/*	fin:jrosas 31-05-2007*/
//		Inicio:jascencio:27/06/07
		if(tipo_certi.equals("C")){//Certificado CREM
			ape_pat = rset.getString("APE_PAT");
			ape_mat = rset.getString("APE_MAT");
			nombres= rset.getString("NOMBRES");
			siglas= rset.getString("SIGLAS");
			raz_social=	rset.getString("RAZ_SOC");
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			/*** inicio:jrosas 31-08-07 **/
			tip_particip= rset.getString("TIP_PARTICIPANTE");
			tip_registro =  rset.getString("TIP_REGISTRO");
			flag_historico = rset.getString("FLAG_HISTORICO");
			fec_ins_asiento_desde = rset.getString("FEC_INS_ASIENTO_DESDE");
			if (fec_ins_asiento_desde != null)
				fec_ins_asiento_desde = fec_ins_asiento_desde.substring(0, 11);
			fec_ins_asiento_hasta =	rset.getString("FEC_INS_ASIENTO_HASTA");
			if (fec_ins_asiento_hasta != null)
				fec_ins_asiento_hasta = fec_ins_asiento_hasta.substring(0, 11);
			/*** fin:jrosas 31-08-07 **/

			/*** INICIO: Jrosas 05-09-2007 **/
			if (rset.getString("TPO_PERS").equals("N")){
				if (ape_pat != null) {
					desc_aux = "APE_PAT: "+ape_pat;
				}
				if (ape_mat != null) {
					desc_aux += ", APE_MAT: "+ape_mat;
				}
				if (nombres != null) {
					desc_aux += ", NOMBRE: "+nombres;
				}
			}else if(rset.getString("TPO_PERS").equals("J")){
				if (raz_social != null) {
					desc_aux += "RAZ_SOC: "+raz_social;
				}
				if (siglas != null) {
					desc_aux += ", SIGLAS: "+siglas;
				}
			}	
			/*** FIN: Jrosas 05-09-2007 **/
			/*** inicio: jrosas 31-08-07 **/
			ArrayList cadAux= new ArrayList();
			if (tip_registro != null){
				StringTokenizer tokens=new StringTokenizer(tip_registro, ",");
				while (tokens.hasMoreTokens()){
					String str_cod_aux = tokens.nextToken();
					cadAux.add(str_cod_aux);
				}
				desc_aux += ", REGISTROS:";
				int len= cadAux.size();
				String cad_reg="";
				for (int k=0; k<len; k++){
					String str_cod= (String)cadAux.get(k);
					if (k ==(len-1)){
						if (str_cod.equals("RMC")){
							cad_reg += "RMC";
						}
						if (str_cod.equals("VEH")){
							cad_reg += "Vehicular";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones)";
			            }
					}else{
						if (str_cod.equals("RMC")){
							cad_reg += "RMC, ";
						}
					    if (str_cod.equals("VEH")){
					    	cad_reg += "Vehicular, ";
			            }
			            if (str_cod.equals("EMB")){
			            	cad_reg += "Embarcación Pesquera, ";
			            }
			            if (str_cod.equals("BUQ")){
			            	cad_reg += "Buques, ";
			            }
			            if (str_cod.equals("AER")){
			            	cad_reg += "Aeronaves, ";
			            }
			            if (str_cod.equals("PEJ")){
			            	cad_reg += "Personas Jurídicas(Participaciones), ";
			            }
					} //else      
				}//for
				desc_aux += cad_reg;
			}//tipo_registro
			
			if (flag_historico != null){
				if (flag_historico.equals("1"))
					desc_aux += ", HISTÓRICO: Sí";
				else if (flag_historico.equals("0")){
					desc_aux += ", HISTÓRICO: No";
				}
			}
			if (fec_ins_asiento_desde != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO DESDE: "+fec_ins_asiento_desde;
				if (fec_ins_asiento_hasta != null){
					desc_aux += " HASTA "+fec_ins_asiento_hasta;
				}
			}else if (fec_ins_asiento_hasta != null){
				desc_aux += ", INSCRIPCIÓN DE ASIENTO HASTA: "+fec_ins_asiento_hasta;
			}
			/*** FIN: jrosas 31-08-07 **/
			if (tip_particip != null){
				String desTipoPar=null;
				if(!tip_particip.equals("")){
					if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_DEUDOR)){
						desTipoPar="Deudor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_ACREEDOR)){
						desTipoPar="Acreedor";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_REPRESENTANTE)){
						desTipoPar="Representante";
					}else if(tip_particip.equals(Constantes.TIPO_PARTICIPANTE_OTROS)){
						desTipoPar="Otros";
					}
				}

				
				desc_aux +=  ", TIP_PARTICIPANTE: "+desTipoPar;
			}
			
			buscaCargLabBean.setDescripcionObjetoCertificado(desc_aux);

			
		}
		//Fin:jascencio
		
		//inicio:dbravo:20/08/2007
		if(buscaCargLabBean.getTipoCertificado()!=null &&
			(
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_VIGENCIA) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_HISTORICO) ||
				buscaCargLabBean.getTipoCertificado().equals(Constantes.COD_CERTIFICADO_REGISTRO_CREM_CONDICIONADO)
			)
		){
			if(buscaCargLabBean.getEstado_sol_id()!=null && buscaCargLabBean.getEstado_sol_id().equalsIgnoreCase("E")){
				
				DboCertificado dboCertificado = new DboCertificado();
				dboCertificado.clearAll();
				dboCertificado.setFieldsToRetrieve(DboCertificado.CAMPO_FLAG_PAGO_CREM);
				dboCertificado.setField(DboCertificado.CAMPO_SOLICITUD_ID, buscaCargLabBean.getSolicitud_id());
				if(dboCertificado.find()){
					buscaCargLabBean.setFlagPagoCrem(dboCertificado.getField(DboCertificado.CAMPO_FLAG_PAGO_CREM));
				}
				
			}else{
				buscaCargLabBean.setFlagPagoCrem("1");
			}
			
		}else{
			buscaCargLabBean.setFlagPagoCrem("1");
		}
		//fin:dbravo:20/08/2007
		
		if(encontro){
		   paginacBean.setEncontro("SI");				
		}else{
			paginacBean.setEncontro("NO");				
		}
		arrbuscaList.add(buscaCargLabBean);
		b = rset.next();		
	}
	
	hayNext = rset.next();

	//*PAGINACION EN EL JSP*//		
	paginacBean.setHayNext(hayNext);
	paginacBean.setPropiedades(propiedades);	

	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return arrbuscaList;
}

/** DESCAJ IFIGUEROA 18/01/2006 INICIO***/
public boolean validarDevolucion(String cuentaIdLog,String cuentaIdSol, DBConnection dconn)throws Throwable{
	DboCuenta cuenta= new DboCuenta();
	cuenta.setConnection(dconn);
	cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaIdLog);
	cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_PE_NATU_ID);
	cuenta.find();
	DboPeNatu penatu= new DboPeNatu();
	penatu.setConnection(dconn);
	penatu.setField(DboPeNatu.CAMPO_PE_NATU_ID,cuenta.getField(DboCuenta.CAMPO_PE_NATU_ID));
	penatu.setFieldsToRetrieve(DboPeNatu.CAMPO_PE_JURI_ID);
	penatu.find();
	String pejuriId= penatu.getField(DboPeNatu.CAMPO_PE_JURI_ID);
	penatu.clear();
	cuenta.clear();
	cuenta.setField(DboCuenta.CAMPO_CUENTA_ID,cuentaIdSol);
	penatu.setField(DboPeNatu.CAMPO_PE_JURI_ID,pejuriId);
	MultiDBObject multi = new MultiDBObject(dconn);
	multi.addDBObj(cuenta,"cuenta");
	multi.addDBObj(penatu,"penatu");
	multi.setForeignKey("cuenta",DboCuenta.CAMPO_PE_NATU_ID,"penatu",DboPeNatu.CAMPO_PE_NATU_ID);
	multi.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
	List l = multi.searchAndRetrieve();
	if (l.size()==0)return false;
	if (l.size()==1)return true;
	return false;
}
/** DESCAJ IFIGUEROA 18/01/2006 FIN***/

	/**
	 * Gets the destinatarioBean
	 * @return Returns a DestinatarioBean
	 */
	public DestinatarioBean getDestinatarioBean() {
		return destinatarioBean;
	}
	/**
	 * Sets the destinatarioBean
	 * @param destinatarioBean The destinatarioBean to set
	 */
	public void setDestinatarioBean(DestinatarioBean destinatarioBean) {
		this.destinatarioBean = destinatarioBean;
	}
	/**
	 * Gets the solicitanteBean
	 * @return Returns a SolicitanteBean
	 */
	public SolicitanteBean getSolicitanteBean() {
		return solicitanteBean;
	}
	/**
	 * Sets the solicitanteBean
	 * @param solicitanteBean The solicitanteBean to set
	 */
	public void setSolicitanteBean(SolicitanteBean solicitanteBean) {
		this.solicitanteBean = solicitanteBean;
	}
	/**
	 * Gets the pagoBean
	 * @return Returns a PagoBean
	 */
	public PagoBean getPagoBean() {
		return pagoBean;
	}
	/**
	 * Sets the pagoBean
	 * @param pagoBean The pagoBean to set
	 */
	public void setPagoBean(PagoBean pagoBean) {
		this.pagoBean = pagoBean;
	}
	/**
	 * Gets the ultimaVerificacionBean
	 * @return Returns a UltimaVerificacionBean
	 */
	public UltimaVerificacionBean getUltimaVerificacionBean() {
		return ultimaVerificacionBean;
	}
	/**
	 * Sets the ultimaVerificacionBean
	 * @param ultimaVerificacionBean The ultimaVerificacionBean to set
	 */
	public void setUltimaVerificacionBean(UltimaVerificacionBean ultimaVerificacionBean) {
		this.ultimaVerificacionBean = ultimaVerificacionBean;
	}

	/**
	 * Gets the dconn
	 * @return Returns a DBConnection
	 */
	public DBConnection getDconn() {
		return dconn;
	}
	/**
	 * Sets the dconn
	 * @param dconn The dconn to set
	 */
	public void setDconn(DBConnection dconn) {
		this.dconn = dconn;
	}

	/**
	 * Gets the conn
	 * @return Returns a Connection
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * Sets the conn
	 * @param conn The conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	/**
	 * Gets the estado
	 * @return Returns a String
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * Sets the estado
	 * @param estado The estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Gets the cuenta_id
	 * @return Returns a String
	 */
	public String getCuenta_id() {
		return cuenta_id;
	}
	/**
	 * Sets the cuenta_id
	 * @param cuenta_id The cuenta_id to set
	 */
	public void setCuenta_id(String cuenta_id) {
		this.cuenta_id = cuenta_id;
	}
	/**
	 * Gets the ts_crea
	 * @return Returns a String
	 */
	public String getTs_crea() {
		return ts_crea;
	}
	/**
	 * Sets the ts_crea
	 * @param ts_crea The ts_crea to set
	 */
	public void setTs_crea(String ts_crea) {
		this.ts_crea = ts_crea;
	}
	/**
	 * Gets the ts_modi
	 * @return Returns a String
	 */
	public String getTs_modi() {
		return ts_modi;
	}
	/**
	 * Sets the ts_modi
	 * @param ts_modi The ts_modi to set
	 */
	public void setTs_modi(String ts_modi) {
		this.ts_modi = ts_modi;
	}
	/**
	 * Gets the usr_crea
	 * @return Returns a String
	 */
	public String getUsr_crea() {
		return usr_crea;
	}
	/**
	 * Sets the usr_crea
	 * @param usr_crea The usr_crea to set
	 */
	public void setUsr_crea(String usr_crea) {
		this.usr_crea = usr_crea;
	}

	/**
	 * Gets the usr_modi
	 * @return Returns a String
	 */
	public String getUsr_modi() {
		return usr_modi;
	}
	/**
	 * Sets the usr_modi
	 * @param usr_modi The usr_modi to set
	 */
	public void setUsr_modi(String usr_modi) {
		this.usr_modi = usr_modi;
	}


	/**
	 * Gets the objetoSolicitudBean
	 * @return Returns a ObjetoSolicitudBean
	 */
	public ObjetoSolicitudBean getObjetoSolicitudList(int pos) {
		return (ObjetoSolicitudBean)this.objetoSolicitudList.get(pos);
	}
	/**
	 * Gets the objetoSolicitudBean
	 * @return Returns a ObjetoSolicitudBean
	 */
	public java.util.List getObjetoSolicitudList() {
		return this.objetoSolicitudList;
	}
	//SVR - ObjetoSolicitudLista
	//      EL VALOR ES RECUPERADO POR STRUTS EN SU METODO GET 
	public java.util.List getObjetoSolicitudLista() {
		return this.objetoSolicitudList;
	}

	
	/**
	 * Sets the objetoSolicitudBean
	 * @param objetoSolicitudBean The objetoSolicitudBean to set
	 */
	public void addObjetoSolicitudList(ObjetoSolicitudBean objetoSolicitudBean) {
		this.objetoSolicitudList.add(objetoSolicitudBean);
	}


	/**
	 * Gets the solicitud_id
	 * @return Returns a String
	 */
	public String getSolicitud_id() {
		return solicitud_id;
	}
	/**
	 * Sets the solicitud_id
	 * @param solicitud_id The solicitud_id to set
	 */
	public void setSolicitud_id(String solicitud_id) {
		this.solicitud_id = solicitud_id;
	}
	/**
	 * Gets the subtotal
	 * @return Returns a String
	 */
	public String getSubtotal() {
		return subtotal;
	}
	/**
	 * Sets the subtotal
	 * @param subtotal The subtotal to set
	 */
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	/**
	 * Gets the gasto_envio
	 * @return Returns a String
	 */
	public String getGasto_envio() {
		return gasto_envio;
	}
	/**
	 * Sets the gasto_envio
	 * @param gasto_envio The gasto_envio to set
	 */
	public void setGasto_envio(String gasto_envio) {
		this.gasto_envio = gasto_envio;
	}

	/**
	 * Gets the total
	 * @return Returns a String
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * Sets the total
	 * @param total The total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * Gets the sgmtSolBean
	 * @return Returns a SgmtSolicitudBean
	 */
	public SgmtSolicitudBean getSgmtSolBean() {
		return sgmtSolBean;
	}
	/**
	 * Sets the sgmtSolBean
	 * @param sgmtSolBean The sgmtSolBean to set
	 */
	public void setSgmtSolBean(SgmtSolicitudBean sgmtSolBean) {
		this.sgmtSolBean = sgmtSolBean;
	}
	/**
	 * Gets the estado_descripcion
	 * @return Returns a String
	 */
	public String getEstado_descripcion() {
		return estado_descripcion;
	}
	/**
	 * Sets the estado_descripcion
	 * @param estado_descripcion The estado_descripcion to set
	 */
	public void setEstado_descripcion(String estado_descripcion) {
		this.estado_descripcion = estado_descripcion;
	}
	/**
	 * Gets the comentario
	 * @return Returns a String
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * Sets the comentario
	 * @param comentario The comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	/**
	 * Gets the tpo_cert_neg
	 * @return Returns a String
	 */
	public String getTpo_cert_neg() {
		return tpo_cert_neg;
	}
	/**
	 * Sets the tpo_cert_neg
	 * @param tpo_cert_neg The tpo_cert_neg to set
	 */
	public void setTpo_cert_neg(String tpo_cert_neg) {
		this.tpo_cert_neg = tpo_cert_neg;
	}
	/**
	 * Gets the aboBean
	 * @return Returns a AbonoBean
	 */
	public AbonoBean getAboBean() {
		return aboBean;
	}
	/**
	 * Sets the aboBean
	 * @param aboBean The aboBean to set
	 */
	public void setAboBean(AbonoBean aboBean) {
		this.aboBean = aboBean;
	}

	/**
	 * Gets the tipoPago
	 * @return Returns a String
	 */
	public String getTipoPago() {
		return tipoPago;
	}
	/**
	 * Sets the tipoPago
	 * @param tipoPago The tipoPago to set
	 */
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	/**
	 * Gets the propiedades
	 * @return Returns a Propiedades
	 */
	public Propiedades getPropiedades() {
		return propiedades;
	}
	/**
	 * Sets the propiedades
	 * @param propiedades The propiedades to set
	 */
	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}

	/**
	 * Gets the paginacBean
	 * @return Returns a PaginacionBean
	 */
	public PaginacionBean getPaginacBean() {
		return paginacBean;
	}
	/**
	 * Sets the paginacBean
	 * @param paginacBean The paginacBean to set
	 */
	public void setPaginacBean(PaginacionBean paginacBean) {
		this.paginacBean = paginacBean;
	}

	/**
	 * Gets the registradores
	 * @return Returns a ArrayList
	 */
	public Hashtable getRegistradores() {
		return registradores;
	}
	/**
	 * Sets the registradores
	 * @param registradores The registradores to set
	 */
	public void setRegistradores(Hashtable registradores) {
		this.registradores = registradores;
	}
	/**
	 * Gets the datosRegisEmisorBean
	 * @return Returns a DatosRegistradorBean
	 */
	public DatosRegistradorBean getDatosRegisEmisorBean() {
		return datosRegisEmisorBean;
	}
	/**
	 * Sets the datosRegisEmisorBean
	 * @param datosRegisEmisorBean The datosRegisEmisorBean to set
	 */
	public void setDatosRegisEmisorBean(DatosRegistradorBean datosRegisEmisorBean) {
		this.datosRegisEmisorBean = datosRegisEmisorBean;
	}
	/**
	 * Gets the datosRegisVerificadorBean
	 * @return Returns a DatosRegistradorBean
	 */
	public DatosRegistradorBean getDatosRegisVerificadorBean() {
		return datosRegisVerificadorBean;
	}
	/**
	 * Sets the datosRegisVerificadorBean
	 * @param datosRegisVerificadorBean The datosRegisVerificadorBean to set
	 */
	public void setDatosRegisVerificadorBean(DatosRegistradorBean datosRegisVerificadorBean) {
		this.datosRegisVerificadorBean = datosRegisVerificadorBean;
	}
	/**
	 * Gets the descripcion
	 * @return Returns a String
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Sets the descripcion
	 * @param descripcion The descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * Gets the estado_ext_descripcion
	 * @return Returns a String
	 */
	public String getEstado_ext_descripcion() {
		return estado_ext_descripcion;
	}
	/**
	 * Sets the estado_ext_descripcion
	 * @param estado_ext_descripcion The estado_ext_descripcion to set
	 */
	public void setEstado_ext_descripcion(String estado_ext_descripcion) {
		this.estado_ext_descripcion = estado_ext_descripcion;
	}

	/**
	 * Gets the constancia
	 * @return Returns a String
	 */
	public String getConstancia() {
		return constancia;
	}
	/**
	 * Sets the constancia
	 * @param constancia The constancia to set
	 */
	public void setConstancia(String constancia) {
		this.constancia = constancia;
	}



	/**
	 * @return the fLAG_CARGA_LABORAL_EMISOR_VERIFICADOR
	 */
	public static String getFLAG_CARGA_LABORAL_EMISOR_VERIFICADOR() {
		return FLAG_CARGA_LABORAL_EMISOR_VERIFICADOR;
	}



	/**
	 * @return the fLAG_CARGA_LABORAL_VERIFICADOR
	 */
	public static String getFLAG_CARGA_LABORAL_VERIFICADOR() {
		return FLAG_CARGA_LABORAL_VERIFICADOR;
	}



	/**
	 * @return the constanciaCremBean
	 */
	public ConstanciaCremBean getConstanciaCremBean() {
		return constanciaCremBean;
	}



	/**
	 * @param constanciaCremBean the constanciaCremBean to set
	 */
	public void setConstanciaCremBean(ConstanciaCremBean constanciaCremBean) {
		this.constanciaCremBean = constanciaCremBean;
	}



	/**
	 * @param objetoSolicitudList the objetoSolicitudList to set
	 */
	public void setObjetoSolicitudList(java.util.List objetoSolicitudList) {
		this.objetoSolicitudList = objetoSolicitudList;
	}

}
