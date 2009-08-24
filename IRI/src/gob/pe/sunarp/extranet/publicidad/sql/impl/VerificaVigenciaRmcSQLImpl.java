package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboTmDocIden;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRMCBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.publicidad.bean.TituloBean;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaVigenciaRmcSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaVigenciaRmcSQLImpl extends SQLImpl implements VerificaVigenciaRmcSQL{
	private Connection conn;
	private DBConnection dbConn;
	public VerificaVigenciaRmcSQLImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
public ArrayList busquedaPersonaNaturalVigenciaRMC(String nombre,String apePat, String apeMat)throws SQLException, CustomException, ValidacionException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoConstanciaRMC= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT distinct PN.NOMBRES,  "); 
		  	q.append(" PN.APE_PAT,  ");
			q.append("  PN.APE_MAT, ");
			q.append("  P.NUM_PARTIDA,  ");
			q.append("  P.REFNUM_PART,  ");
			q.append("  AG.DESC_EJECUCION, ");
			q.append("  P.TS_INSCRIP, ");
			q.append("  PN.TI_DOC_IDEN, ");
			q.append("  PN.NU_DOC_IDEN, "); 
			q.append("  P.REG_PUB_ID, ");
			q.append("  P.OFIC_REG_ID, ");
			q.append("  P.area_reg_id ");
			q.append(" FROM  PRTC_NAT PN, ");
			q.append("  IND_PRTC IP,   ");
			q.append("  PARTIDA P,  ");
			q.append("  ASIENTO_GARANTIA AG, ");
			q.append("  IND_PRTC_ASIENTO_GARANTIA IPAG, ");
			q.append("  GRUPO_LIBRO_PARTICIPANTE GLP, ");
			q.append(" GRUPO_LIBRO_AREA_DET GLAD ");
			q.append(" WHERE PN.CUR_PRTC=IP.CUR_PRTC ");
			q.append("  AND IP.REFNUM_PART=P.REFNUM_PART	 ");
			q.append("  AND IP.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append("  AND IP.CUR_PRTC=IPAG.CUR_PRTC ");
			q.append("  AND IP.NU_TITU=IPAG.NU_TITU ");
			q.append("  AND IP.COD_PARTIC=IPAG.COD_PARTIC ");
			q.append("  AND IP.AA_TITU=IPAG.AA_TITULO ");
			q.append("  AND AG.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append("  AND AG.COD_ACTO=IPAG.COD_ACTO ");
			q.append("  AND AG.NS_ASIENTO=IPAG.NS_ASIENTO ");
			q.append("  AND PN.REG_PUB_ID=P.REG_PUB_ID ");
			q.append("  AND PN.OFIC_REG_ID=P.OFIC_REG_ID ");
			q.append("  AND P.ESTADO='"+Constantes.ESTADO_ACTIVO+"' ");
			q.append("  AND GLP.COD_PARTIC =  IP.COD_PARTIC ");
			q.append("  AND GLP.TIP_PARTICIPANTE = 'REP' ");
			q.append("  AND GLP.TIP_LIBRO =  'RMC' ");
			q.append(" AND P.COD_LIBRO = GLAD.COD_LIBRO ");
			q.append(" AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
			q.append("  AND P.TS_INSCRIP > TO_DATE('"+Constantes.FECHA_INICIO_VIGENCIA_RMC+"') ");
			if(apePat!=null && apePat.trim().length()>0)
				q.append(" AND PN.APE_PAT= '"+apePat.trim()+"' ");
			if(apePat!=null && apeMat.trim().length()>0)
				q.append("  AND PN.APE_MAT= '"+apeMat.trim()+"'");
			if(apePat!=null && nombre.trim().length()>0)
				q.append("  AND PN.NOMBRES= '"+nombre.trim()+"'");
			q.append(" ORDER BY APE_PAT, APE_MAT, NOMBRES ");
		
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPersonaNaturalVigenciaRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
				constanciaRMCBean.setNombres(rset.getString("NOMBRES"));
				constanciaRMCBean.setApePat(rset.getString("APE_PAT"));
				constanciaRMCBean.setApeMat(rset.getString("APE_MAT"));
				constanciaRMCBean.setNumDocIdentidad(rset.getString("NU_DOC_IDEN"));
				constanciaRMCBean.setTipoDocIdentidad(rset.getString("TI_DOC_IDEN"));
				PartidaBean partida = new PartidaBean();
				partida.setRefNumPart(rset.getString("REFNUM_PART"));
				partida.setNumPartida(rset.getString("NUM_PARTIDA"));
				partida.setDescEjecucion(rset.getString("DESC_EJECUCION"));
				partida.setFechaInscripcionAsiento(rset.getString("TS_INSCRIP"));
				constanciaRMCBean.setPartida(partida);
				constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
				resultadoConstanciaRMC.add(constanciaRMCBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoConstanciaRMC;
	}
	
public ArrayList busquedaPersonaJuridicaVigenciaRMC(String rasonSocial,String siglas)throws SQLException, CustomException, ValidacionException, DBException{
	
	Statement stmt   = null;
	ResultSet rset   = null;
	ArrayList resultadoConstanciaRMC= new ArrayList();
	
	try{
		
		StringBuffer q  = new StringBuffer();
		
		q.append("SELECT distinct PJ.RAZON_SOCIAL,  "); 
	  	q.append(" PJ.SIGLAS, ");
		q.append("  P.NUM_PARTIDA, ");
		q.append("  P.REFNUM_PART, ");
		q.append("  AG.DESC_EJECUCION,  ");
		q.append("  P.TS_INSCRIP, ");
		q.append("  P.OFIC_REG_ID, ");
		q.append(" P.REG_PUB_ID,  ");
		q.append(" P.area_reg_id  ");
		q.append(" FROM  PRTC_JUR PJ, ");
		q.append("  IND_PRTC IP,  ");
		q.append("  PARTIDA P,  ");
		q.append("  ASIENTO_GARANTIA AG, ");
		q.append("  IND_PRTC_ASIENTO_GARANTIA IPAG, ");
		q.append("  GRUPO_LIBRO_PARTICIPANTE GLP, ");
		q.append(" GRUPO_LIBRO_AREA_DET GLAD ");
		q.append(" WHERE  PJ.CUR_PRTC=IP.CUR_PRTC ");
		q.append(" AND IP.REFNUM_PART=P.REFNUM_PART	 ");
		q.append(" AND IP.REFNUM_PART=IPAG.REFNUM_PART  ");
		q.append(" AND IP.CUR_PRTC=IPAG.CUR_PRTC  ");
		q.append("  AND IP.NU_TITU=IPAG.NU_TITU ");
		q.append("  AND IP.COD_PARTIC=IPAG.COD_PARTIC ");
		q.append(" AND IP.AA_TITU=IPAG.AA_TITULO  ");
		q.append(" AND AG.REFNUM_PART=IPAG.REFNUM_PART  ");
		q.append(" AND AG.COD_ACTO=IPAG.COD_ACTO  ");
		q.append(" AND AG.NS_ASIENTO=IPAG.NS_ASIENTO  ");
		q.append(" AND PJ.OFIC_REG_ID=P.OFIC_REG_ID  ");
		q.append(" AND PJ.REG_PUB_ID=P.REG_PUB_ID  ");
		q.append(" AND P.ESTADO="+Constantes.ESTADO_ACTIVO+"   ");
		q.append("  AND GLP.COD_PARTIC =  IP.COD_PARTIC ");
		q.append("  AND GLP.TIP_PARTICIPANTE = 'REP' ");
		q.append("  AND GLP.TIP_LIBRO =  'RMC' ");	
		q.append(" AND P.COD_LIBRO = GLAD.COD_LIBRO ");
		q.append(" AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
		q.append("  AND P.TS_INSCRIP > TO_DATE('"+Constantes.FECHA_INICIO_VIGENCIA_RMC+"') ");
		if(rasonSocial!=null && rasonSocial.trim().length()>0)
			q.append(" AND PJ.RAZON_SOCIAL= '"+rasonSocial.trim()+"' ");
		if(siglas!=null && siglas.trim().length()>0)
			q.append("  AND PJ.SIGLAS= '"+siglas.trim()+"'");
		q.append("ORDER BY RAZON_SOCIAL");
		
		if (isTrace(this)) System.out.println("___verquery_busquedaPersonaJuridicaVigenciaRMC__"+q.toString());
		
		stmt   = conn.createStatement();
		rset   = stmt.executeQuery(q.toString());
		
		while (rset.next())
		{
			ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
			constanciaRMCBean.setRazonSocial(rset.getString("RAZON_SOCIAL"));
			constanciaRMCBean.setSiglas(rset.getString("SIGLAS"));
			PartidaBean partida = new PartidaBean();
			partida.setRefNumPart(rset.getString("REFNUM_PART"));
			partida.setNumPartida(rset.getString("NUM_PARTIDA"));
			partida.setDescEjecucion(rset.getString("DESC_EJECUCION"));
			partida.setFechaInscripcionAsiento(rset.getString("TS_INSCRIP"));
			constanciaRMCBean.setPartida(partida);
			constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
			resultadoConstanciaRMC.add(constanciaRMCBean);
		}
	
	} finally {
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	
	return resultadoConstanciaRMC;
}

public ArrayList busquedaTipoNumeroDocVigenciaRMC(String tipoDoc,String numDoc)throws SQLException, CustomException, ValidacionException, DBException{
	
	Statement stmt   = null;
	ResultSet rset   = null;
	ArrayList resultadoConstanciaRMC= new ArrayList();
	
	try{
		DboTmDocIden dboDocIden = new DboTmDocIden(dbConn);
		dboDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDoc);
		dboDocIden.setField(DboTmDocIden.CAMPO_ESTADO, Constantes.ESTADO_ACTIVO);
		dboDocIden.find();
		String tipoPersona=dboDocIden.getField(DboTmDocIden.CAMPO_TIPO_PER);
		if(tipoPersona!=null && tipoPersona.length()>0 && tipoPersona.equalsIgnoreCase(Constantes.PERSONA_NATURAL)){
			StringBuffer q  = new StringBuffer();
			q.append(" SELECT distinct DI.NOMBRE_ABREV, "); 
		  	q.append(" DI.DESCRIPCION, ");
			q.append(" P.NUM_PARTIDA, ");
			q.append(" P.REFNUM_PART, ");
			q.append(" AG.DESC_EJECUCION, ");
			q.append("  P.TS_INSCRIP, ");
			q.append(" P.REG_PUB_ID,  ");
			q.append(" P.OFIC_REG_ID,  ");
			q.append(" P.area_reg_id, ");
			q.append(" PN.APE_PAT,  ");
			q.append("  PN.APE_MAT, ");
			q.append("  PN.NOMBRES  ");
			q.append(" FROM  PRTC_NAT PN, ");
			q.append(" IND_PRTC IP,    ");
			q.append(" PARTIDA P,  ");
			q.append(" ASIENTO_GARANTIA AG, ");
			q.append(" IND_PRTC_ASIENTO_GARANTIA IPAG, ");
			q.append(" TM_DOC_IDEN DI, ");
			q.append("  GRUPO_LIBRO_PARTICIPANTE GLP, ");
			q.append(" GRUPO_LIBRO_AREA_DET GLAD ");
			q.append(" WHERE  PN.CUR_PRTC=IP.CUR_PRTC ");
			q.append(" AND IP.REFNUM_PART=IPAG.REFNUM_PART	 ");
			q.append(" AND IP.CUR_PRTC=IPAG.CUR_PRTC  ");
			q.append(" AND IP.NU_TITU=IPAG.NU_TITU ");
			q.append(" AND IP.COD_PARTIC=IPAG.COD_PARTIC  ");
			q.append(" AND IP.AA_TITU=IPAG.AA_TITULO  ");
			q.append(" AND AG.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append(" AND AG.COD_ACTO=IPAG.COD_ACTO ");
			q.append(" AND AG.NS_ASIENTO=IPAG.NS_ASIENTO  ");
			q.append(" AND IP.REFNUM_PART=P.REFNUM_PART  ");
			q.append(" AND PN.TI_DOC_IDEN=DI.TIPO_DOC_ID  ");
			q.append(" AND PN.OFIC_REG_ID=P.OFIC_REG_ID ");
			q.append("  AND PN.REG_PUB_ID=P.REG_PUB_ID ");
			q.append(" AND P.ESTADO="+Constantes.ESTADO_ACTIVO+"  ");
			q.append("  AND GLP.COD_PARTIC =  IP.COD_PARTIC ");
			q.append("  AND GLP.TIP_PARTICIPANTE = 'REP' ");
			q.append("  AND GLP.TIP_LIBRO =  'RMC' ");
			q.append(" AND P.COD_LIBRO = GLAD.COD_LIBRO ");
			q.append(" AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
			q.append("  AND P.TS_INSCRIP > TO_DATE('"+Constantes.FECHA_INICIO_VIGENCIA_RMC+"')");
			if(tipoDoc!=null && tipoDoc.trim().length()>0)
				q.append(" AND PN.TI_DOC_IDEN= '"+tipoDoc.trim()+"' ");
			if(numDoc!=null && numDoc.trim().length()>0)
				q.append("  AND PN.NU_DOC_IDEN= '"+numDoc.trim()+"'");
			q.append(" ORDER BY APE_PAT, APE_MAT, NOMBRES ");
			

			if (isTrace(this)) System.out.println("___verquery_busquedaTipoNumDocNaturalVigenciaRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
				constanciaRMCBean.setNombres(rset.getString("NOMBRES"));
				constanciaRMCBean.setApePat(rset.getString("APE_PAT"));
				constanciaRMCBean.setApeMat(rset.getString("APE_MAT"));
				PartidaBean partida = new PartidaBean();
				partida.setRefNumPart(rset.getString("REFNUM_PART"));
				partida.setNumPartida(rset.getString("NUM_PARTIDA"));
				partida.setDescEjecucion(rset.getString("DESC_EJECUCION"));
				partida.setFechaInscripcionAsiento(rset.getString("TS_INSCRIP"));
				constanciaRMCBean.setPartida(partida);
				constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
				resultadoConstanciaRMC.add(constanciaRMCBean);
			}
		}else if(tipoPersona!=null && tipoPersona.length()>0 && tipoPersona.equalsIgnoreCase(Constantes.PERSONA_JURIDICA)){
			StringBuffer q  = new StringBuffer();
			q.append(" SELECT distinct DI.NOMBRE_ABREV, ");
			q.append(" DI.DESCRIPCION, ");
			q.append(" P.NUM_PARTIDA, ");
			q.append(" P.REFNUM_PART, ");
			q.append(" AG.DESC_EJECUCION, ");
			q.append(" P.TS_INSCRIP, ");
			q.append(" P.REG_PUB_ID, ");
			q.append(" P.OFIC_REG_ID, ");
			q.append(" P.area_reg_id, ");
			q.append(" PJ.RAZON_SOCIAL,  "); 
		  	q.append(" PJ.SIGLAS ");
			q.append(" FROM PRTC_JUR PJ, ");
			q.append(" IND_PRTC IP, ");
			q.append(" PARTIDA P, ");
			q.append(" ASIENTO_GARANTIA AG, ");
			q.append(" IND_PRTC_ASIENTO_GARANTIA IPAG, ");
			q.append(" TM_DOC_IDEN DI, ");
			q.append("  GRUPO_LIBRO_PARTICIPANTE GLP, ");
			q.append(" GRUPO_LIBRO_AREA_DET GLAD ");
			q.append(" WHERE PJ.CUR_PRTC=IP.CUR_PRTC ");
			q.append(" AND IP.REFNUM_PART=P.REFNUM_PART ");
			q.append(" AND IP.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append(" AND IP.CUR_PRTC=IPAG.CUR_PRTC ");
			q.append(" AND IP.NU_TITU=IPAG.NU_TITU ");
			q.append(" AND IP.COD_PARTIC=IPAG.COD_PARTIC ");
			q.append(" AND IP.AA_TITU=IPAG.AA_TITULO ");
			q.append(" AND AG.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append(" AND AG.COD_ACTO=IPAG.COD_ACTO ");
			q.append(" AND AG.NS_ASIENTO=IPAG.NS_ASIENTO ");
			q.append(" AND PJ.TI_DOC=DI.TIPO_DOC_ID ");
			q.append(" AND PJ.OFIC_REG_ID=P.OFIC_REG_ID ");
			q.append(" AND PJ.REG_PUB_ID=P.REG_PUB_ID ");
			q.append(" AND P.ESTADO="+Constantes.ESTADO_ACTIVO+"  ");
			q.append("  AND GLP.COD_PARTIC =  IP.COD_PARTIC ");
			q.append("  AND GLP.TIP_PARTICIPANTE = 'REP' ");
			q.append("  AND GLP.TIP_LIBRO =  'RMC' ");
			q.append(" AND P.COD_LIBRO = GLAD.COD_LIBRO ");
			q.append(" AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
			q.append(" AND P.TS_INSCRIP > TO_DATE('"+Constantes.FECHA_INICIO_VIGENCIA_RMC+"') ");
			if(tipoDoc!=null && tipoDoc.trim().length()>0)
				q.append(" AND PJ.TI_DOC= '"+tipoDoc.trim()+"' ");
			if(numDoc!=null && numDoc.trim().length()>0)
				q.append("  AND PJ.NU_DOC= '"+numDoc.trim()+"'");
			q.append(" ORDER BY RAZON_SOCIAL");
			if (isTrace(this)) System.out.println("___verquery_busquedaTipoNumCodJuridicaVigenciaRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
				constanciaRMCBean.setRazonSocial(rset.getString("RAZON_SOCIAL"));
				constanciaRMCBean.setSiglas(rset.getString("SIGLAS"));
				PartidaBean partida = new PartidaBean();
				partida.setRefNumPart(rset.getString("REFNUM_PART"));
				partida.setNumPartida(rset.getString("NUM_PARTIDA"));
				partida.setDescEjecucion(rset.getString("DESC_EJECUCION"));
				partida.setFechaInscripcionAsiento(rset.getString("TS_INSCRIP"));
				constanciaRMCBean.setPartida(partida);
				constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
				resultadoConstanciaRMC.add(constanciaRMCBean);
			}
		}else if(tipoPersona!=null && tipoPersona.length()>0 && tipoPersona.equalsIgnoreCase(Constantes.PERSONA_AMBAS)){
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT distinct DI.NOMBRE_ABREV, "); 
		  	q.append(" DI.DESCRIPCION, ");
			q.append(" P.NUM_PARTIDA, ");
			q.append(" P.REFNUM_PART, ");
			q.append(" AG.DESC_EJECUCION, ");
			q.append(" P.REG_PUB_ID,  ");
			q.append(" P.OFIC_REG_ID,  ");
			q.append(" P.area_reg_id, ");
			q.append(" P.TS_INSCRIP, ");
			q.append(" PN.APE_PAT,  ");
			q.append(" PN.APE_MAT, ");
			q.append(" PN.NOMBRES,  ");
			q.append(" '' as RAZON_SOCIAL,  "); 
		  	q.append(" '' as SIGLAS ");
			q.append(" FROM  PRTC_NAT PN, ");
			q.append(" IND_PRTC IP,    ");
			q.append(" PARTIDA P,  ");
			q.append(" ASIENTO_GARANTIA AG, ");
			q.append(" IND_PRTC_ASIENTO_GARANTIA IPAG, ");
			q.append(" TM_DOC_IDEN DI, ");
			q.append("  GRUPO_LIBRO_PARTICIPANTE GLP, ");
			q.append(" GRUPO_LIBRO_AREA_DET GLAD ");
			q.append(" WHERE  PN.CUR_PRTC=IP.CUR_PRTC ");
			q.append(" AND IP.REFNUM_PART=IPAG.REFNUM_PART	 ");
			q.append(" AND IP.CUR_PRTC=IPAG.CUR_PRTC  ");
			q.append(" AND IP.NU_TITU=IPAG.NU_TITU ");
			q.append(" AND IP.COD_PARTIC=IPAG.COD_PARTIC  ");
			q.append(" AND IP.AA_TITU=IPAG.AA_TITULO  ");
			q.append(" AND AG.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append(" AND AG.COD_ACTO=IPAG.COD_ACTO ");
			q.append(" AND AG.NS_ASIENTO=IPAG.NS_ASIENTO  ");
			q.append(" AND IP.REFNUM_PART=P.REFNUM_PART  ");
			q.append(" AND PN.TI_DOC_IDEN=DI.TIPO_DOC_ID  ");
			q.append(" AND PN.OFIC_REG_ID=P.OFIC_REG_ID ");
			q.append("  AND PN.REG_PUB_ID=P.REG_PUB_ID ");
			q.append(" AND P.ESTADO="+Constantes.ESTADO_ACTIVO+"  ");
			q.append("  AND GLP.COD_PARTIC =  IP.COD_PARTIC ");
			q.append("  AND GLP.TIP_PARTICIPANTE = 'REP' ");
			q.append("  AND GLP.TIP_LIBRO =  'RMC' ");
			q.append(" AND P.COD_LIBRO = GLAD.COD_LIBRO ");
			q.append(" AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
			q.append("  AND P.TS_INSCRIP > TO_DATE('"+Constantes.FECHA_INICIO_VIGENCIA_RMC+"')  ");
			if(tipoDoc!=null && tipoDoc.trim().length()>0)
				q.append(" AND PN.TI_DOC_IDEN= '"+tipoDoc.trim()+"' ");
			if(numDoc!=null && numDoc.trim().length()>0)
				q.append("  AND PN.NU_DOC_IDEN= '"+numDoc.trim()+"'");
			
			q.append(" UNION ");
			q.append(" SELECT distinct DI.NOMBRE_ABREV, ");
			q.append(" DI.DESCRIPCION, ");
			q.append(" P.NUM_PARTIDA, ");
			q.append(" P.REFNUM_PART, ");
			q.append(" AG.DESC_EJECUCION, ");
			q.append(" P.REG_PUB_ID, ");
			q.append(" P.OFIC_REG_ID, ");
			q.append(" P.area_reg_id, ");
			q.append(" P.TS_INSCRIP, ");
			q.append(" PJ.RAZON_SOCIAL,  "); 
		  	q.append(" PJ.SIGLAS, ");
			q.append(" '' as APE_PAT,  ");
			q.append(" '' as APE_MAT, ");
			q.append(" '' as NOMBRES  ");
			q.append(" FROM PRTC_JUR PJ, ");
			q.append(" IND_PRTC IP, ");
			q.append(" PARTIDA P, ");
			q.append(" ASIENTO_GARANTIA AG, ");
			q.append(" IND_PRTC_ASIENTO_GARANTIA IPAG, ");
			q.append(" TM_DOC_IDEN DI, ");
			q.append("  GRUPO_LIBRO_PARTICIPANTE GLP, ");
			q.append(" GRUPO_LIBRO_AREA_DET GLAD ");
			q.append(" WHERE PJ.CUR_PRTC=IP.CUR_PRTC ");
			q.append(" AND IP.REFNUM_PART=P.REFNUM_PART ");
			q.append(" AND IP.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append(" AND IP.CUR_PRTC=IPAG.CUR_PRTC ");
			q.append(" AND IP.NU_TITU=IPAG.NU_TITU ");
			q.append(" AND IP.COD_PARTIC=IPAG.COD_PARTIC ");
			q.append(" AND IP.AA_TITU=IPAG.AA_TITULO ");
			q.append(" AND AG.REFNUM_PART=IPAG.REFNUM_PART ");
			q.append(" AND AG.COD_ACTO=IPAG.COD_ACTO ");
			q.append(" AND AG.NS_ASIENTO=IPAG.NS_ASIENTO ");
			q.append(" AND PJ.TI_DOC=DI.TIPO_DOC_ID ");
			q.append(" AND PJ.OFIC_REG_ID=P.OFIC_REG_ID ");
			q.append(" AND PJ.REG_PUB_ID=P.REG_PUB_ID ");
			q.append(" AND P.ESTADO="+Constantes.ESTADO_ACTIVO+"  ");
			q.append(" AND GLP.COD_PARTIC =  IP.COD_PARTIC ");
			q.append(" AND GLP.TIP_PARTICIPANTE = 'REP' ");
			q.append(" AND GLP.TIP_LIBRO =  'RMC' ");
			q.append(" AND P.COD_LIBRO = GLAD.COD_LIBRO ");
			q.append(" AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
			q.append(" AND P.TS_INSCRIP > TO_DATE('"+Constantes.FECHA_INICIO_VIGENCIA_RMC+"')  ");
			if(tipoDoc!=null && tipoDoc.trim().length()>0)
				q.append(" AND PJ.TI_DOC= '"+tipoDoc.trim()+"' ");
			if(numDoc!=null && numDoc.trim().length()>0)
				q.append("  AND PJ.NU_DOC= '"+numDoc.trim()+"'");
			q.append(" ORDER BY APE_PAT, APE_MAT, NOMBRES,RAZON_SOCIAL");
			if (isTrace(this)) System.out.println("___verquery_busquedaTipoNumDocVigenciaRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
				constanciaRMCBean.setRazonSocial(rset.getString("RAZON_SOCIAL"));
				constanciaRMCBean.setSiglas(rset.getString("SIGLAS"));
				constanciaRMCBean.setNumDocIdentidad(rset.getString("NOMBRES"));
				constanciaRMCBean.setApePat(rset.getString("APE_PAT"));
				constanciaRMCBean.setApeMat(rset.getString("APE_MAT"));
				PartidaBean partida = new PartidaBean();
				partida.setRefNumPart(rset.getString("REFNUM_PART"));
				partida.setNumPartida(rset.getString("NUM_PARTIDA"));
				partida.setDescEjecucion(rset.getString("DESC_EJECUCION"));
				partida.setFechaInscripcionAsiento(rset.getString("TS_INSCRIP"));
				constanciaRMCBean.setPartida(partida);
				constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
				resultadoConstanciaRMC.add(constanciaRMCBean);
			}
		}
	} finally {
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	
	return resultadoConstanciaRMC;
}

private ArrayList obtenerTitulos(String numPar, String regPubId, String oficRegId,String area_reg_id) throws SQLException, CustomException, ValidacionException, DBException{
	Statement stmt   = null;
	ResultSet rset   = null;
	ArrayList listaTitulo=new ArrayList();
	try{
		StringBuffer q  = new StringBuffer();
		
		q.append("SELECT TBP.NUM_TITU,T.TS_PRESENT "); 
	 	q.append(" FROM  ");
		q.append("  TA_BLOQ_PARTIDA TBP, TITULO T ,TITULO_ORDEN TOR ");
		q.append("WHERE  ");
		q.append(" TBP.NUM_PARTIDA       ='"+numPar+"' ");
		q.append(" AND TBP.REG_PUB_ID    ='"+regPubId+"' ");
		q.append(" AND TBP.OFIC_REG_ID   ='"+oficRegId+"' ");
		q.append(" AND TBP.AREA_REG_ID   ='"+area_reg_id+"' ");
		q.append(" AND TBP.OFIC_REG_ID    = TOR.OFIC_REG_ID  ");
		q.append(" AND TBP.REG_PUB_ID     = TOR.REG_PUB_ID  ");
		q.append(" AND TBP.NUM_TITU       = TOR.NUM_TITU  ");
		q.append(" AND TBP.ANO_TITU       = TOR.ANO_TITU  ");
		q.append(" AND TBP.AREA_REG_ID    = TOR.AREA_REG_ID  ");
		q.append(" AND TOR.REFNUM_TITU    = T.REFNUM_TITU  ");
		q.append(" and TBP.estado='1' ");
			
		if (isTrace(this)) System.out.println("___verquery_obternerTitulo__"+q.toString());
		
		stmt   = conn.createStatement();
		rset   = stmt.executeQuery(q.toString());
		while (rset.next())
		{
			TituloBean titulo = new TituloBean();
			titulo.setTitulo(rset.getString("NUM_TITU"));
			titulo.setFecPresent(rset.getString("TS_PRESENT"));
			titulo.setFechaPresentacion(rset.getTimestamp("TS_PRESENT"));
			listaTitulo.add(titulo);
			
		}
							
	} finally {
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	return listaTitulo;
}
}
