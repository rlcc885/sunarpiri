package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRjbBean;
import gob.pe.sunarp.extranet.publicidad.bean.DetalleRjbBean;
import gob.pe.sunarp.extranet.publicidad.certificada.Solicitud;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaRjbSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaRjbSQLImpl extends SQLImpl implements VerificaRjbSQL
{
	
	private Connection conn;
	private DBConnection dbConn;

	public VerificaRjbSQLImpl(Connection conn, DBConnection dbConn) 
	{
		this.conn = conn;
		this.dbConn = dbConn;
	}

	public ArrayList recuperarDocumento(String numeroTitulo, String anoTitulo) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String documentoLegal = "";
		String funcionario = "";
		ArrayList resultado = new ArrayList();
		try
		{
			
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT TD.NS_DOCU,DL.DESC_TIPO_DOCU_LEGA,TD.FUNCIONARIO ");  
			q.append("FROM   TITULO TI, TITULO_DOCUMENTO TD, TM_TIPO_DOCU_LEGALES DL ");
			q.append("WHERE  TI.REFNUM_TITU = TD.REFNUM_TITU AND ");
			q.append("TD.COD_TIPO_DOCU_LEGA = DL.COD_TIPO_DOCU_LEGA AND ");
			q.append("TI.NUM_TITU = '").append(numeroTitulo).append("' AND ");
			q.append("TI.ANO_TITU = '").append(anoTitulo).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
 			
 			while(rset.next())
 			{
 				documentoLegal = rset.getString(2);
 				if(!(rset.getString(3)==null) && !rset.getString(3).equals(""))
 				{
 				  documentoLegal = documentoLegal + "("+rset.getShort(3)+")";
 				}
 				resultado.add(documentoLegal);
 			}
			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}

	public ArrayList recuperarGravamenVehicularRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
		
		ConstanciaRjbBean consRjb;
		try
		{
			StringBuffer q = new StringBuffer();
			
			
			q.append("SELECT PART.REFNUM_PART, ACT.DESCRIPCION AS DESCRIPCION ,TO_CHAR(ASIG.TS_ACTO_CONST,'DD/MM/YYYY') ,MON.DESC_ABRV_MONE||' '||ASIG.MONTO_IMPO AS MONTO, "); 
			q.append("PART.REG_PUB_ID,PART.OFIC_REG_ID,ASI.NUM_TITU, ASI.AA_TITU ");
			q.append("FROM   VEHICULO VEH, PARTIDA PART, ASIENTO_GARANTIA ASIG,ASIENTO ASI, ");
			q.append("TM_ACTO ACT, TM_TIPO_MONEDA MON, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE ");
			if(!(objSol.getPlaca()==null) && !objSol.getPlaca().equals(""))
			{
				q.append("VEH.NUM_PLACA = '").append(objSol.getPlaca().trim()).append("' AND ");
			}
			q.append("VEH.REFNUM_PART = PART.REFNUM_PART AND ");
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida().trim()).append("' AND ");
			}
			q.append("ASIG.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASIG.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASIG.COD_TIPO_MONEDA = MON.COD_TIPO_MONEDA(+) AND ");
			q.append("ASI.REFNUM_PART = ASIG.REFNUM_PART  AND ");
			q.append("ASIG.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("GLA.TIP_LIBRO = 'VEH' AND ");
			q.append("GLA.TIP_ACTO = 'GRA' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
 			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(2));
 				consRjb.setFechaAfectacion(rset.getString(3));
 				consRjb.setMontoGravamen(rset.getString(4));
 				consRjb.setAnoTitulo(rset.getString(8));
 				consRjb.setNumeroTitulo(rset.getString(7));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.LIBRO_VEHI;
 				regPubId = rset.getString(5);
 				ofiRegId = rset.getString(6);
 				consRjb.setListaParticipante(recuperarTipoPersonaGravamen(refNumPart, codLibro, objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				consRjb.setListaDocumento(recuperarDocumento(consRjb.getNumeroTitulo(),consRjb.getAnoTitulo()));
 				
 				resultado.add(consRjb);
 			}
			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}

	public HashMap recuperarPersonaNatural(String curPrtc, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException {
		
		Statement stmt   = null;
		ResultSet rset   = null;
		HashMap map = new HashMap();
		
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT PN.NOMBRES||' '||PN.APE_PAT||' '||PN.APE_MAT  AS NOMBRE, PN.NU_DOC_IDEN, DI.NOMBRE_ABREV ");
			q.append("FROM   PRTC_NAT PN, TM_DOC_IDEN DI ");
			q.append("WHERE ");
			q.append("PN.TI_DOC_IDEN = DI.TIPO_DOC_ID(+) AND ");
			q.append("PN.CUR_PRTC = '").append(curPrtc).append("' AND "); 
			q.append("PN.REG_PUB_ID = '").append(regPubId).append("' AND ");
			q.append("PN.OFIC_REG_ID = '").append(ofiRegId).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				if (rset.getString(1) == null){
 					map.put("nombrePersona", "");
 				}else{
 					map.put("nombrePersona", rset.getString(1));
 				}
 				
 				if (rset.getString(2) == null){
 					map.put("numeroDocumento","");
 				}else{
 					map.put("numeroDocumento",rset.getString(2));	
 				}
 				
 				if (rset.getString(3) == null){
 					map.put("tipoDocumento","");
 				}else{
 					map.put("tipoDocumento",rset.getString(3));	
 				}
 				
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return map;
	}

	public HashMap recuperarRazonSocial(String curPrtc, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		HashMap map = new HashMap();
	
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT PJ.RAZON_SOCIAL, PJ.NU_DOC, DI.NOMBRE_ABREV ");
			q.append("FROM   PRTC_JUR PJ, TM_DOC_IDEN DI ");
			q.append("WHERE ");
			q.append("PJ.TI_DOC = DI.TIPO_DOC_ID(+) AND ");
			q.append("PJ.CUR_PRTC = '").append(curPrtc).append("' AND "); 
			q.append("PJ.REG_PUB_ID = '").append(regPubId).append("' AND ");
			q.append("PJ.OFIC_REG_ID = '").append(ofiRegId).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				if (rset.getString(1) == null){
 					map.put("nombrePersona", "");	 					
 				}else{
 					map.put("nombrePersona", rset.getString(1));	
 				}
 				
 				if (rset.getString(2) == null){
 					map.put("numeroDocumento", "");
 				}else{
 					map.put("numeroDocumento", rset.getString(2));	
 				}
 				
 				if (rset.getString(3) == null){
 					map.put("tipoDocumento", "");
 				}else{
 					map.put("tipoDocumento", rset.getString(3));	
 				}
 				
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return map;
	}

	public ArrayList recuperarTipoPersonaDominial(String refNumPart, String codLibro, String numeroTitulo, String anoTitulo, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String tipoPersona = "";
		String nombrePersona = "";
		
		ArrayList resultado = new ArrayList();
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT IP.CUR_PRTC, IP.TIPO_PERS ");
			q.append("FROM   IND_PRTC IP, PARTIC_LIBRO PL ");
			q.append("WHERE  IP.REFNUM_PART = '").append(refNumPart).append("' AND ");
			q.append("IP.NU_TITU = '").append(numeroTitulo).append("' AND ");
			q.append("IP.AA_TITU = '").append(anoTitulo).append("' AND ");
			q.append("IP.COD_PARTIC = PL.COD_PARTIC AND ");
			    	   
			if(codLibro.equals("053") || codLibro.equals("054"))
			{
				q.append("PL.COD_LIBRO IN ('053','054') ");
			}else
			{
				q.append("PL.COD_LIBRO = '").append(codLibro).append("' ");
			}
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				tipoPersona = rset.getString(2);
 				if(tipoPersona.equals("N"))
 				{
 					nombrePersona = (String)(recuperarPersonaNatural(rset.getString(1), regPubId , ofiRegId).get("nombrePersona"));
 				}else if(tipoPersona.equals("J"))
 				{
 					nombrePersona = (String)(recuperarRazonSocial(rset.getString(1) , regPubId, ofiRegId).get("nombrePersona"));
 				}
 						
 				resultado.add(nombrePersona);
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}

	public ArrayList recuperarTipoPersonaDominialPro(String refNumPart, String codLibro, String numeroTitulo, String anoTitulo , String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String tipoPersona = "";
		String nombrePersona = "";
		HashMap map = new HashMap();
		ArrayList listadoTipoPersonaDominProp = new ArrayList();
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT IP.REFNUM_PART, IP.TIPO_PERS, IP.CUR_PRTC, IP.DIRECCION ");
			q.append("FROM   IND_PRTC IP, PARTIC_LIBRO PL ");
			q.append("WHERE  IP.REFNUM_PART = '").append(refNumPart).append("' AND ");
			q.append("IP.ESTADO = '1' AND ");
			q.append("IP.COD_PARTIC = PL.COD_PARTIC AND ");
			if(codLibro.equals("053") || codLibro.equals("054"))
			{
				q.append("IP.COD_PARTIC IN ('125','123','022','140','133') AND ");
				q.append("PL.COD_LIBRO IN ('053','054') ");
			}else
			{
				if(codLibro.equals(Constantes.CODIGO_LIBRO_VEHICULAR))
				{
					q.append("IP.COD_PARTIC = '038' AND ");
				}else if(codLibro.equals(Constantes.CODIGO_LIBRO_EMBARCACION_PESQUERA))
				{
					q.append("IP.COD_PARTIC = '001' AND ");
				}else if(codLibro.equals(Constantes.CODIGO_LIBRO_AERONAVES))
				{
					q.append("IP.COD_PARTIC = '003' AND ");
				}
				q.append("PL.COD_LIBRO = '").append(codLibro).append("' ");
			}
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			String auxDirecc="";
 			while(rset.next())
 			{
 				tipoPersona = rset.getString(2);
 				if(tipoPersona.equals("N"))
 				{
 					map = recuperarPersonaNatural(rset.getString(3), regPubId , ofiRegId);
 				}else if(tipoPersona.equals("J"))
 				{
 					map = recuperarRazonSocial(rset.getString(3) , regPubId, ofiRegId);
 				}
 				if (rset.getString(4) == null){
 					map.put("direccion", auxDirecc);
 				}else{
 					map.put("direccion", rset.getString(4));
 				}
 				
 				listadoTipoPersonaDominProp.add(map);
 			}
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return listadoTipoPersonaDominProp;
	}

	public ArrayList recuperarTipoPersonaGravamen(String refNumPart, String codLibro, String regPubId, String ofiRegId) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String tipoPersona = "";
		String nombrePersona = "";
		StringBuffer cadenaResultado = new StringBuffer(); 
		ArrayList resultado = new ArrayList();
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT IP.COD_PARTIC, IP.TIPO_PERS, IP.CUR_PRTC, PL.NOMBRE ");
			q.append("FROM   IND_PRTC_ASIENTO_GARANTIA IPA, IND_PRTC IP, PARTIC_LIBRO PL ");
			q.append("WHERE  IPA.REFNUM_PART ='").append(refNumPart).append("' AND ");
			q.append("IP.ESTADO = '1' AND ");
			q.append("IP.COD_PARTIC = PL.COD_PARTIC AND ");
			q.append("IPA.COD_PARTIC = PL.COD_PARTIC AND ");
			q.append("IP.REFNUM_PART = IPA.REFNUM_PART AND ");
			if(codLibro.equals("053") || codLibro.equals("054"))
			{
				q.append("PL.COD_LIBRO IN ('053','054') ");
			}else
			{
				q.append("PL.COD_LIBRO = '").append(codLibro).append("' ");
			}
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				tipoPersona = rset.getString(2);
 				if(tipoPersona.equals("N"))
 				{
 					nombrePersona = (String)(recuperarPersonaNatural(rset.getString(3), regPubId , ofiRegId).get("nombrePersona"));
 				}else if(tipoPersona.equals("J"))
 				{
 					nombrePersona = (String)(recuperarRazonSocial(rset.getString(3) , regPubId, ofiRegId).get("nombrePersona"));
 				}
 				if(!nombrePersona.equals(""))
 				{
	 				cadenaResultado.append(nombrePersona);
	 				if(!rset.getString(4).equals(""))
	 				{
	 					cadenaResultado.append(" ("+rset.getString(4)+") ");
	 				}
	 					
	 				resultado.add(cadenaResultado.toString());
 				}
 				cadenaResultado.delete(0,cadenaResultado.length());
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}

	public ArrayList recuperarDominialAeronaveRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
	
		ConstanciaRjbBean consRjb;
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT DISTINCT PART.REFNUM_PART, ASI.COD_ACTO, ACT.DESCRIPCION, ASI.AA_TITU,ASI.NUM_TITU, TO_CHAR(ASI.TS_INSCRIP,'dd/mm/yyyy hh:mm') ");
			q.append("FROM REG_AERONAVES AER , PARTIDA PART, ASIENTO ASI, TM_ACTO ACT, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE ");
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida()).append("' AND ");
			}else
			{
				if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
				{
					q.append("AER.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
				}
				if(!(objSol.getNumeroSerie()==null) && !objSol.getNumeroSerie().equals(""))
				{
					q.append("AER.SERIE = '").append(objSol.getNumeroSerie()).append("' AND ");
				}
			}
			q.append("AER.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("PART.REFNUM_PART  = ASI.REFNUM_PART AND "); 
			q.append("AER.REFNUM_PART = ASI.REFNUM_PART AND ");
			q.append("ASI.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("GLA.TIP_LIBRO = 'OTR' AND ");
			q.append("GLA.TIP_ACTO = 'DOM' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			if(objSol.getTipoInformacionDominio().equals("U"))
			{
				q.append("AND ASI.TS_INSCRIP = (SELECT MAX(TS_INSCRIP) FROM ASIENTO ASIE, GRUPO_LIBRO_ACTO GL WHERE ASIE.REFNUM_PART=PART.REFNUM_PART ");
				q.append("AND GL.COD_ACTO=ASIE.COD_ACTO AND GL.TIP_LIBRO = 'OTR' AND GL.TIP_ACTO = 'DOM') ");
			}
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(3));
 				consRjb.setAnoTitulo(rset.getString(4));
 				consRjb.setNumeroTitulo(rset.getString(5));
 				consRjb.setFechaInscripcion(rset.getString(6));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.CODIGO_LIBRO_AERONAVES;
 				if(objSol.getTipoInformacionDominio().equals("U"))
 				{    
 				    ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo() , objSol.getReg_pub_id(), objSol.getOfic_reg_id());
 				    ArrayList listadonombresProp= new ArrayList();
					for (int i=0; i<listadoPropietarios.size(); i++){
						HashMap objPropietario = (HashMap)listadoPropietarios.get(i);
						String nombrePropietario = (String)objPropietario.get("nombrePersona");
						listadonombresProp.add(nombrePropietario);
					}
					consRjb.setNombrePropietario(listadonombresProp); // guarda listado de nombres de propiertarios
 				}else
 				{
 					consRjb.setListaParticipante(recuperarTipoPersonaDominial(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo() , objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				}
 				resultado.add(consRjb);
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return resultado;
	}

	public ArrayList recuperarDominialBuqueRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
		ConstanciaRjbBean consRjb;
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT DISTINCT PART.REFNUM_PART, ASI.COD_ACTO, ACT.DESCRIPCION, ASI.AA_TITU,ASI.NUM_TITU, TO_CHAR(ASI.TS_INSCRIP,'dd/mm/yyyy hh:mm') ");
			q.append("FROM REG_BUQUES BUQ , PARTIDA PART, ASIENTO ASI, TM_ACTO ACT, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE ");
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("BUQ.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
			}
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida()).append("' AND ");
			}
			q.append("BUQ.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("PART.REFNUM_PART  = ASI.REFNUM_PART AND ");
			q.append("BUQ.REFNUM_PART = ASI.REFNUM_PART AND ");
			q.append("ASI.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("GLA.TIP_LIBRO = 'OTR' AND ");
			q.append("GLA.TIP_ACTO = 'DOM' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			if(objSol.getTipoInformacionDominio().equals("U"))
			{
				//q.append("AND ASI.TS_INSCRIP = (SELECT MAX(TS_INSCRIP) FROM ASIENTO WHERE REFNUM_PART=PART.REFNUM_PART) ");
				q.append("AND ASI.TS_INSCRIP = (SELECT MAX(TS_INSCRIP) FROM ASIENTO ASIE, GRUPO_LIBRO_ACTO GL WHERE ASIE.REFNUM_PART=PART.REFNUM_PART ");
				q.append("AND GL.COD_ACTO=ASIE.COD_ACTO AND GL.TIP_LIBRO = 'OTR' AND GL.TIP_ACTO = 'DOM') ");
			}
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(3));
 				consRjb.setAnoTitulo(rset.getString(4));
 				consRjb.setNumeroTitulo(rset.getString(5));
 				consRjb.setFechaInscripcion(rset.getString(6));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.CODIGO_LIBRO_BUQUES;
 				if(objSol.getTipoInformacionDominio().equals("U"))
 				{
 					ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo() , objSol.getReg_pub_id(), objSol.getOfic_reg_id());
 				    ArrayList listadonombresProp= new ArrayList();
					for (int i=0; i<listadoPropietarios.size(); i++){
						HashMap objPropietario = (HashMap)listadoPropietarios.get(i);
						String nombrePropietario = (String)objPropietario.get("nombrePersona");
						listadonombresProp.add(nombrePropietario);
					}
					consRjb.setNombrePropietario(listadonombresProp); // guarda listado de nombres de propiertarios
 				}else
 				{
 					consRjb.setListaParticipante(recuperarTipoPersonaDominial(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo() , objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				}
 				resultado.add(consRjb);
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return resultado;
	}

	public ArrayList recuperarDominialEmbPesqueraRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
		ConstanciaRjbBean consRjb;
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT DISTINCT PART.REFNUM_PART, ASI.COD_ACTO, ACT.DESCRIPCION, ASI.AA_TITU,ASI.NUM_TITU, TO_CHAR(ASI.TS_INSCRIP,'dd/mm/yyyy hh:mm') ");
			q.append("FROM REG_EMB_PESQ EMB , PARTIDA PART, ASIENTO ASI, TM_ACTO ACT, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE ");
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("EMB.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
			}
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida()).append("' AND ");
			}
			q.append("EMB.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("PART.REFNUM_PART  = ASI.REFNUM_PART AND ");
			q.append("EMB.REFNUM_PART = ASI.REFNUM_PART AND ");
			q.append("ASI.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND "); 
			q.append("GLA.TIP_LIBRO = 'OTR' AND ");
			q.append("GLA.TIP_ACTO = 'DOM' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			if(objSol.getTipoInformacionDominio().equals("U"))
			{
				//q.append("AND ASI.TS_INSCRIP = (SELECT MAX(TS_INSCRIP) FROM ASIENTO WHERE REFNUM_PART=PART.REFNUM_PART) ");
				q.append("AND ASI.TS_INSCRIP = (SELECT MAX(TS_INSCRIP) FROM ASIENTO ASIE, GRUPO_LIBRO_ACTO GL WHERE ASIE.REFNUM_PART=PART.REFNUM_PART ");
				q.append("AND GL.COD_ACTO=ASIE.COD_ACTO AND GL.TIP_LIBRO = 'OTR' AND GL.TIP_ACTO = 'DOM') ");
			}
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(3));
 				consRjb.setAnoTitulo(rset.getString(4));
 				consRjb.setNumeroTitulo(rset.getString(5));
 				consRjb.setFechaInscripcion(rset.getString(6));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.CODIGO_LIBRO_EMBARCACION_PESQUERA;
 				if(objSol.getTipoInformacionDominio().equals("U"))
 				{
 					ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo() , objSol.getReg_pub_id(), objSol.getOfic_reg_id());
 				    ArrayList listadonombresProp= new ArrayList();
					for (int i=0; i<listadoPropietarios.size(); i++){
						HashMap objPropietario = (HashMap)listadoPropietarios.get(i);
						String nombrePropietario = (String)objPropietario.get("nombrePersona");
						listadonombresProp.add(nombrePropietario);
					}
					consRjb.setNombrePropietario(listadonombresProp); // guarda listado de nombres de propiertarios
 				}else
 				{
 					consRjb.setListaParticipante(recuperarTipoPersonaDominial(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo() , objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				}
 				
 				resultado.add(consRjb);
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return resultado;
	}

	public ArrayList recuperarDominialVehicularRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
		ConstanciaRjbBean consRjb;
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT DISTINCT PART.REFNUM_PART, ASI.COD_ACTO, ACT.DESCRIPCION, ASI.AA_TITU,ASI.NUM_TITU, TO_CHAR(ASI.TS_INSCRIP,'dd/mm/yyyy hh:mm') ");
			q.append("FROM   VEHICULO VEH, PARTIDA PART, ASIENTO ASI, TM_ACTO ACT, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE  PART.REFNUM_PART = VEH.REFNUM_PART AND ");
			if(!(objSol.getPlaca()==null) && !objSol.getPlaca().equals(""))
			{
				q.append("VEH.NUM_PLACA = '").append(objSol.getPlaca().trim()).append("' AND ");
			}
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida().trim()).append("' AND ");
			}
			q.append("ASI.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("VEH.REFNUM_PART = ASI.REFNUM_PART AND ");
			q.append("ASI.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("GLA.TIP_LIBRO = 'VEH' AND ");
			q.append("GLA.TIP_ACTO = 'DOM' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id().trim()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id().trim()).append("' ");
			if(objSol.getTipoInformacionDominio().equals("U"))
			{
				//q.append("AND ASI.TS_INSCRIP = (SELECT MAX(TS_INSCRIP) FROM ASIENTO WHERE REFNUM_PART=PART.REFNUM_PART) ");
				q.append("AND ASI.TS_INSCRIP = (SELECT MAX(TS_INSCRIP) FROM ASIENTO ASIE, GRUPO_LIBRO_ACTO GL WHERE ASIE.REFNUM_PART=PART.REFNUM_PART ");
				q.append("AND GL.COD_ACTO=ASIE.COD_ACTO AND GL.TIP_LIBRO = 'VEH' AND GL.TIP_ACTO = 'DOM') ");
			}
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(3));
 				consRjb.setAnoTitulo(rset.getString(4));
 				consRjb.setNumeroTitulo(rset.getString(5));
 				consRjb.setFechaInscripcion(rset.getString(6));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.CODIGO_LIBRO_VEHICULAR;
 				if(objSol.getTipoInformacionDominio().equals("U"))
 				{
 					ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo() , objSol.getReg_pub_id(), objSol.getOfic_reg_id());
 				    ArrayList listadonombresProp= new ArrayList();
					for (int i=0; i<listadoPropietarios.size(); i++){
						HashMap objPropietario = (HashMap)listadoPropietarios.get(i);
						String nombrePropietario = (String)objPropietario.get("nombrePersona");
						listadonombresProp.add(nombrePropietario);
					}
					consRjb.setNombrePropietario(listadonombresProp); // guarda listado de nombres de propiertarios
 				}else
 				{
 					consRjb.setListaParticipante(recuperarTipoPersonaDominial(refNumPart, codLibro, consRjb.getNumeroTitulo(), consRjb.getAnoTitulo(), objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				}
 				
 				resultado.add(consRjb);
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return resultado;
	}

	public ArrayList recuperarGravamenAeronaveRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
		ConstanciaRjbBean consRjb;
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT PART.REFNUM_PART, ACT.DESCRIPCION,TO_CHAR(ASIG.TS_ACTO_CONST,'DD/MM/YYYY') , MON.DESC_ABRV_MONE||' '||ASIG.MONTO_IMPO, ");
			q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID, ASI.NUM_TITU, ASI.AA_TITU ");
			q.append("FROM   REG_AERONAVES AER, PARTIDA PART , ASIENTO_GARANTIA ASIG, TM_ACTO ACT, TM_TIPO_MONEDA MON, ");
			q.append("ASIENTO ASI, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE  AER.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("AER.ESTADO = '1' AND ");
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida()).append("' AND "); 
			}
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("AER.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
			}
			if(!(objSol.getNumeroSerie()==null) && !objSol.getNumeroSerie().equals(""))
			{
				q.append("AER.SERIE = '").append(objSol.getNumeroSerie()).append("' AND ");
			}
			q.append("PART.REFNUM_PART = ASIG.REFNUM_PART AND ");
			q.append("ASIG.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASIG.COD_TIPO_MONEDA = MON.COD_TIPO_MONEDA AND ");
			q.append("ASI.REFNUM_PART = ASIG.REFNUM_PART AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("ASIG.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("GLA.TIP_LIBRO = 'AER' AND ");
			q.append("GLA.TIP_ACTO = 'GRA' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(2));
 				consRjb.setFechaAfectacion(rset.getString(3));
 				consRjb.setMontoGravamen(rset.getString(4));
 				consRjb.setNumeroTitulo(rset.getString(7));
 				consRjb.setAnoTitulo(rset.getString(8));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.CODIGO_LIBRO_AERONAVES;
 				consRjb.setListaParticipante(recuperarTipoPersonaGravamen(refNumPart, codLibro, objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				consRjb.setListaDocumento(recuperarDocumento(consRjb.getNumeroTitulo(), consRjb.getAnoTitulo()));
 				
 				resultado.add(consRjb);
 			}
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}

	public ArrayList recuperarGravamenBusqueRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
		ConstanciaRjbBean consRjb;
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT PART.REFNUM_PART, ACT.DESCRIPCION, TO_CHAR(ASIG.TS_ACTO_CONST,'DD/MM/YYYY'), MON.DESC_ABRV_MONE||' '||ASIG.MONTO_IMPO, ");
			q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID, ASI.NUM_TITU , ASI.AA_TITU ");
			q.append("FROM   REG_BUQUES BUQ, PARTIDA PART , ASIENTO_GARANTIA ASIG, TM_ACTO ACT, TM_TIPO_MONEDA MON, ");
			q.append("ASIENTO ASI, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE  BUQ.ESTADO = '1' AND ");
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida()).append("' AND ");
			}
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("BUQ.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND "); 
			}
			q.append("BUQ.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("PART.REFNUM_PART = ASIG.REFNUM_PART AND ");
			q.append("ASIG.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASIG.COD_TIPO_MONEDA = MON.COD_TIPO_MONEDA AND ");
			q.append("ASIG.REFNUM_PART = ASI.REFNUM_PART AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("ASIG.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("GLA.TIP_LIBRO = 'BUQ' AND ");
			q.append("GLA.TIP_ACTO = 'GRA' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(2));
 				consRjb.setFechaAfectacion(rset.getString(3));
 				consRjb.setMontoGravamen(rset.getString(4));
 				consRjb.setNumeroTitulo(rset.getString(7));
 				consRjb.setAnoTitulo(rset.getString(8));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.CODIGO_LIBRO_BUQUES;
 				consRjb.setListaParticipante(recuperarTipoPersonaGravamen(refNumPart, codLibro, objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				consRjb.setListaDocumento(recuperarDocumento(consRjb.getNumeroTitulo(), consRjb.getAnoTitulo()));
 				resultado.add(consRjb);
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return resultado;
	}

	public ArrayList recuperarGravamenEmbPesqueraRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "";
		String codLibro   = "";
		String regPubId   = "";
		String ofiRegId   = "";
		
		ArrayList resultado = new ArrayList();
		ConstanciaRjbBean consRjb;

		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT PART.REFNUM_PART, ACT.DESCRIPCION,TO_CHAR(ASIG.TS_ACTO_CONST,'DD/MM/YYYY') , MON.DESC_ABRV_MONE||' '||ASIG.MONTO_IMPO, ");
			q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID, ASI.NUM_TITU , ASI.AA_TITU ");
			q.append("FROM REG_EMB_PESQ EMB, PARTIDA PART , ASIENTO_GARANTIA ASIG, TM_ACTO ACT, TM_TIPO_MONEDA MON, ");
			q.append("ASIENTO ASI, GRUPO_LIBRO_ACTO GLA ");
			q.append("WHERE  EMB.ESTADO = '1' AND ");
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida()).append("' AND ");
			}
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("EMB.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
			}
			q.append("EMB.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("PART.REFNUM_PART = ASIG.REFNUM_PART AND ");
			q.append("ASIG.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("ASIG.COD_TIPO_MONEDA = MON.COD_TIPO_MONEDA AND ");
			q.append("ASI.REFNUM_PART = ASIG.REFNUM_PART AND ");
			q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("ASIG.COD_ACTO = GLA.COD_ACTO AND ");
			q.append("GLA.TIP_LIBRO = 'EMB' AND ");      
			q.append("GLA.TIP_ACTO = 'GRA' AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
			
 			while(rset.next())
 			{
 				consRjb = new ConstanciaRjbBean();
 				consRjb.setDescripciónActo(rset.getString(2));
 				consRjb.setFechaAfectacion(rset.getString(3));
 				consRjb.setMontoGravamen(rset.getString(4));
 				consRjb.setNumeroTitulo(rset.getString(7));
 				consRjb.setAnoTitulo(rset.getString(8));
 				refNumPart = rset.getString(1);
 				codLibro = Constantes.CODIGO_LIBRO_EMBARCACION_PESQUERA;
 				consRjb.setListaParticipante(recuperarTipoPersonaGravamen(refNumPart, codLibro, objSol.getReg_pub_id(), objSol.getOfic_reg_id()));
 				consRjb.setListaDocumento(recuperarDocumento(consRjb.getNumeroTitulo(), consRjb.getAnoTitulo()));
 				
 				resultado.add(consRjb);
 			}
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return resultado;
	}

	public DetalleRjbBean recuperarDetalleAeronaveRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		
		String refNumPart = "" ;
		String codLibro = "";
		String numeroTitulo = "";
		String anoTitulo = "";
		HashMap map = new HashMap();
		
		DetalleRjbBean resultado = new DetalleRjbBean();
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT AER.NUM_MATRICULA,AER.MODELO, TO_CHAR(PART.TS_INSCRIP,'dd/mm/yyyy hh:mm'), PART.NUM_PARTIDA, REG.NOMBRE, OFI.NOMBRE, PART.REFNUM_PART ");
			q.append("FROM REG_AERONAVES AER, PARTIDA PART, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI ");
			q.append("WHERE "); 
			if(!(objSol.getNumPartida()==null) && !objSol.getNumPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumPartida()).append("' AND ");
			}
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("AER.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
			}
			if(!(objSol.getNumeroSerie()==null) && !objSol.getNumeroSerie().equals(""))
			{
				q.append("AER.SERIE = '").append(objSol.getNumeroSerie()).append("' AND ");
			}
			q.append("AER.REFNUM_PART = PART.REFNUM_PART AND ");  
			q.append("AER.ESTADO = '1' AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("OFI.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
 			
 			if(rset.next())
 			{
 				resultado.setClaseBien("AERONAVES");
 				resultado.setNumMatricula(rset.getString(1));
 				resultado.setModeloBien(rset.getString(2));
 				resultado.setFechaInscripcion(rset.getString(3));
 				resultado.setNumPartida(rset.getString(4));
 				resultado.setZonaRegistral(rset.getString(5));
 				resultado.setOficinaRegistral(rset.getString(6));
 				refNumPart = rset.getString(7);
 				codLibro = Constantes.CODIGO_LIBRO_AERONAVES;
 				
 				/** inicio: jrosas 15-10-07 **/
 				ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, numeroTitulo, anoTitulo, objSol.getReg_pub_id(), objSol.getOfic_reg_id());
				ArrayList listadonombresProp= new ArrayList();
				ArrayList listadodireccionProp= new ArrayList();
				ArrayList listadodniProp= new ArrayList();
				ArrayList listadotipoDocProp= new ArrayList();
				
				for (int j=0; j<listadoPropietarios.size(); j++){
					HashMap objPropietario = (HashMap)listadoPropietarios.get(j);
					String nombrePropietario = (String)objPropietario.get("nombrePersona"); // listado de propietarios
					String direccionProp = (String)objPropietario.get("direccion"); // listado de direcciones de propietario
					String dni = (String)objPropietario.get("numeroDocumento"); // listado de dni
					String tipoDoc = (String)objPropietario.get("tipoDocumento"); // listado tipoDoc
					
					listadonombresProp.add(nombrePropietario);
					listadodireccionProp.add(direccionProp);
					listadodniProp.add(dni);
					listadotipoDocProp.add(tipoDoc);
				}
				resultado.setNombrePropietario(listadonombresProp);
 				resultado.setDireccion(listadodireccionProp);
 				resultado.setDni(listadodniProp);
 				resultado.setTipoDocumento(listadotipoDocProp);
 				
				/** fin: jrosas 15-10-07 **/
 				resultado.setTipoRegistro("A");
 			}
 			
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}

	public DetalleRjbBean recuperarDetalleBuqueRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "" ;
		String codLibro = "";
		String numeroTitulo = "";
		String anoTitulo = "";
		DetalleRjbBean resultado = new DetalleRjbBean();
		HashMap map = new HashMap();
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT BUQ.NUM_MATRICULA,BUQ.NOMBRE,TO_CHAR(PART.TS_INSCRIP,'dd/mm/yyyy hh:mm'), PART.NUM_PARTIDA, REG.NOMBRE, OFI.NOMBRE, PART.REFNUM_PART ");
			q.append("FROM REG_BUQUES BUQ, PARTIDA PART, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI ");
			q.append("WHERE ");
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("BUQ.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
			}
			if(!(objSol.getNumPartida()==null) && !objSol.getNumPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumPartida()).append("' AND ");
			}
			q.append("BUQ.REFNUM_PART = PART.REFNUM_PART AND ");  
			q.append("BUQ.ESTADO = '1' AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID =  OFI.OFIC_REG_ID AND ");
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("OFI.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
 			
 			if(rset.next())
 			{
 				resultado.setClaseBien("BUQUES");
 				resultado.setNumMatricula(rset.getString(1));
 				resultado.setNombreBien(rset.getString(2));
 				resultado.setFechaInscripcion(rset.getString(3));
 				resultado.setNumPartida(rset.getString(4));
 				resultado.setZonaRegistral(rset.getString(5));
 				resultado.setOficinaRegistral(rset.getString(6));
 				refNumPart = rset.getString(7);
 				codLibro = Constantes.CODIGO_LIBRO_BUQUES;
 				
 				/** inicio: jrosas 15-10-07 **/
 				ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, numeroTitulo, anoTitulo, objSol.getReg_pub_id(), objSol.getOfic_reg_id());
				ArrayList listadonombresProp= new ArrayList();
				ArrayList listadodireccionProp= new ArrayList();
				ArrayList listadodniProp= new ArrayList();
				ArrayList listadotipoDocProp= new ArrayList();
				
				for (int j=0; j<listadoPropietarios.size(); j++){
					HashMap objPropietario = (HashMap)listadoPropietarios.get(j);
					String nombrePropietario = (String)objPropietario.get("nombrePersona"); // listado de propietarios
					String direccionProp = (String)objPropietario.get("direccion"); // listado de direcciones de propietario
					String dni = (String)objPropietario.get("numeroDocumento"); // listado de dni
					String tipoDoc = (String)objPropietario.get("tipoDocumento"); // listado tipoDoc
					
					listadonombresProp.add(nombrePropietario);
					listadodireccionProp.add(direccionProp);
					listadodniProp.add(dni);
					listadotipoDocProp.add(tipoDoc);
				}
				resultado.setNombrePropietario(listadonombresProp);
 				resultado.setDireccion(listadodireccionProp);
 				resultado.setDni(listadodniProp);
 				resultado.setTipoDocumento(listadotipoDocProp);
 				
				/** fin: jrosas 15-10-07 **/
 				resultado.setTipoRegistro("B");
 			}
 			
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}

	public DetalleRjbBean recuperarDetalleEmbPesqueraRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "" ;
		String codLibro = "";
		String numeroTitulo = "";
		String anoTitulo = "";
		DetalleRjbBean resultado = new DetalleRjbBean();
		HashMap map = new HashMap();
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT EMB.NUM_MATRICULA,EMB.NOMBRE_EMB,TO_CHAR(PART.TS_INSCRIP,'dd/mm/yyyy hh:mm'), PART.NUM_PARTIDA, REG.NOMBRE, OFI.NOMBRE, ");
			q.append("TE.DESCRIPCION, PART.REFNUM_PART ");
			q.append("FROM REG_EMB_PESQ EMB , PARTIDA PART, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, TM_TIPO_EMB_PESQ TE ");
			q.append("WHERE ");
			if(!(objSol.getNumeroMatricula()==null) && !objSol.getNumeroMatricula().equals(""))
			{
				q.append("EMB.NUM_MATRICULA = '").append(objSol.getNumeroMatricula()).append("' AND ");
			}
			if(!(objSol.getNumPartida()==null) && !objSol.getNumPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumPartida()).append("' AND ");
			}
			q.append("EMB.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("EMB.ESTADO = '1' AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID =  OFI.OFIC_REG_ID AND ");
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("OFI.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("EMB.COD_TIPO_EMB_PESQ = TE.COD_TIPO_EMB_PESQ(+) AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
 			
 			if(rset.next())
 			{
 				resultado.setClaseBien("EMBARCACION PESQUERA");
 				resultado.setNumMatricula(rset.getString(1));
 				resultado.setNombreBien(rset.getString(2));
 				resultado.setFechaInscripcion(rset.getString(3));
 				resultado.setNumPartida(rset.getString(4));
 				resultado.setZonaRegistral(rset.getString(5));
 				resultado.setOficinaRegistral(rset.getString(6));
 				resultado.setTipoBien(rset.getString(7));
 				refNumPart = rset.getString(8);
 				codLibro = Constantes.CODIGO_LIBRO_EMBARCACION_PESQUERA;
 				/** inicio: jrosas 15-10-07 **/
 				ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, numeroTitulo, anoTitulo, objSol.getReg_pub_id(), objSol.getOfic_reg_id());
				ArrayList listadonombresProp= new ArrayList();
				ArrayList listadodireccionProp= new ArrayList();
				ArrayList listadodniProp= new ArrayList();
				ArrayList listadotipoDocProp= new ArrayList();
				
				for (int j=0; j<listadoPropietarios.size(); j++){
					HashMap objPropietario = (HashMap)listadoPropietarios.get(j);
					String nombrePropietario = (String)objPropietario.get("nombrePersona"); // listado de propietarios
					String direccionProp = (String)objPropietario.get("direccion"); // listado de direcciones de propietario
					String dni = (String)objPropietario.get("numeroDocumento"); // listado de dni
					String tipoDoc = (String)objPropietario.get("tipoDocumento"); // listado tipoDoc
					
					listadonombresProp.add(nombrePropietario);
					listadodireccionProp.add(direccionProp);
					listadodniProp.add(dni);
					listadotipoDocProp.add(tipoDoc);
				}
				resultado.setNombrePropietario(listadonombresProp);
 				resultado.setDireccion(listadodireccionProp);
 				resultado.setDni(listadodniProp);
 				resultado.setTipoDocumento(listadotipoDocProp);
 				
				/** fin: jrosas 15-10-07 **/
 				resultado.setTipoRegistro("E");
 			}
 			
 			
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}
	public DetalleRjbBean recuperarDetalleVehicularRJB(ObjetoSolicitudBean objSol) throws SQLException, CustomException, ValidacionException, DBException 
	{
		Statement stmt   = null;
		ResultSet rset   = null;
		String refNumPart = "" ;
		String codLibro = "";
		String numeroTitulo = "";
		String anoTitulo = "";
		HashMap map = new HashMap();
		DetalleRjbBean resultado = new DetalleRjbBean();
		
		try
		{
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT PART.REFNUM_PART, VEH.NUM_PLACA, ");
			q.append("VEH.FG_BAJA AS ESTADO, TO_CHAR(VEH.TS_INSCRIP,'dd/mm/yyyy hh:mm'),CV.DESCRIPCION AS CONDICION, PART.NUM_PARTIDA, TV.DESCRIPCION AS CLASE, ");
			q.append("MV.DESCRIPCION AS MODELO, TC.DESCRIPCION AS CARROCERIA, VEH.DESC_COLOR_01 AS COLOR, VEH.NUM_SERIE, VEH.NUM_PASAJEROS, ");
			q.append("VEH.PESO_SECO,VEH.LONGITUD,MAV.DESCRIPCION AS MARCA, TCOM.DESCRIPCION AS COMBUSTIBLE, VEH.NUM_EJES, VEH.NUM_MOTOR,VEH.NUM_RUEDAS, ");
			q.append("VEH.NUM_ASIENTOS,VEH.PESO_BRUTO,VEH.ALTURA,VEH.ANO_FABRIC,VEH.NUM_CILINDROS,VEH.ANCHO,REG.NOMBRE AS ZONA, ");
			q.append("OFI.NOMBRE OFICINA, PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			q.append("FROM VEHICULO VEH, PARTIDA PART,TM_COND_VEHI CV, TM_TIPO_VEHI TV, ");
			q.append("TM_MODELO_VEHI MV, TM_TIPO_CARR TC, TM_MARCA_VEHI MAV, TM_TIPO_COMB TCOM,REGIS_PUBLICO REG, OFIC_REGISTRAL OFI ");
			q.append("WHERE ");
			if(!(objSol.getPlaca()==null) && !objSol.getPlaca().equals(""))
			{
				q.append("VEH.NUM_PLACA = '").append(objSol.getPlaca()).append("' AND ");
			}
			if(!(objSol.getNumeroPartida()==null) && !objSol.getNumeroPartida().equals(""))
			{
				q.append("PART.NUM_PARTIDA = '").append(objSol.getNumeroPartida()).append("' AND ");
			}
			q.append("VEH.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("VEH.COD_COND_VEHI = CV.COD_COND_VEHI AND ");
			q.append("VEH.COD_TIPO_VEHI = TV.COD_TIPO_VEHI AND ");
			q.append("VEH.COD_MODELO = MV.COD_MODELO AND ");
			q.append("VEH.COD_TIPO_CARR = TC.COD_TIPO_CARR AND ");
			q.append("VEH.COD_MARCA = MAV.COD_MARCA AND ");
			q.append("VEH.COD_TIPO_COMB = TCOM.COD_TIPO_COMB AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			q.append("OFI.REG_PUB_ID =  REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = '").append(objSol.getReg_pub_id()).append("' AND ");
			q.append("PART.OFIC_REG_ID = '").append(objSol.getOfic_reg_id()).append("' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset   = stmt.executeQuery(q.toString());
 			
 			if(rset.next())
 			{
 				resultado.setNumPlaca(rset.getString("NUM_PLACA"));
 				if(rset.getString("ESTADO").equals("0"))
 				{
 					resultado.setEstadoVehiculo("En circulación");
 				}else
 				{
 					resultado.setEstadoVehiculo("Fuera de circulación");
 				}
 				resultado.setFechaInscripcion(rset.getString(4));
 				resultado.setCondicionVehiculo(rset.getString("CONDICION"));
 				resultado.setNumPartida(rset.getString("NUM_PARTIDA"));
 				resultado.setClaseVehiculo(rset.getString("CLASE"));
 				resultado.setModeloVehiculo(rset.getString("MODELO"));
 				resultado.setCarrocería(rset.getString("CARROCERIA"));
 				resultado.setColor(rset.getString("COLOR"));
 				resultado.setNumSerie(rset.getString("NUM_SERIE"));
 				resultado.setNumPasajeros(rset.getString("NUM_PASAJEROS"));
 				resultado.setPesoSeco(rset.getString("PESO_SECO"));
 				resultado.setLongitud(rset.getString("LONGITUD"));
 				resultado.setMarcaVehiculo(rset.getString("MARCA"));
 				resultado.setTipoCombustible(rset.getString("COMBUSTIBLE"));
 				resultado.setEjeVehiculo(rset.getString("NUM_EJES"));
 				resultado.setNumMotor(rset.getString("NUM_MOTOR"));  
 				resultado.setNumRueda(rset.getString("NUM_RUEDAS"));
 				resultado.setAsiento(rset.getString("NUM_ASIENTOS"));
 				resultado.setPesoBruto(rset.getString("PESO_BRUTO"));
 				resultado.setAltura(rset.getString("ALTURA"));
 				resultado.setAnoFabricacion(rset.getString("ANO_FABRIC"));
 				resultado.setNumCilindro(rset.getString("NUM_CILINDROS"));
 				resultado.setAncho(rset.getString("ANCHO"));
 				resultado.setZonaRegistral(rset.getString("ZONA"));
 				resultado.setOficinaRegistral(rset.getString("OFICINA"));
 				refNumPart = rset.getString("REFNUM_PART");
 				codLibro = Constantes.CODIGO_LIBRO_VEHICULAR;
 				/** inicio: jrosas 15-10-07 **/
 				ArrayList listadoPropietarios = recuperarTipoPersonaDominialPro(refNumPart, codLibro, numeroTitulo, anoTitulo, objSol.getReg_pub_id(), objSol.getOfic_reg_id());
				ArrayList listadonombresProp= new ArrayList();
				ArrayList listadodireccionProp= new ArrayList();
				ArrayList listadodniProp= new ArrayList();
				ArrayList listadotipoDocProp= new ArrayList();
				
				for (int j=0; j<listadoPropietarios.size(); j++){
					HashMap objPropietario = (HashMap)listadoPropietarios.get(j);
					String nombrePropietario = (String)objPropietario.get("nombrePersona"); // listado de propietarios
					String direccionProp = (String)objPropietario.get("direccion"); // listado de direcciones de propietario
					String dni = (String)objPropietario.get("numeroDocumento"); // listado de dni
					String tipoDoc = (String)objPropietario.get("tipoDocumento"); // listado tipoDoc
					
					listadonombresProp.add(nombrePropietario);
					listadodireccionProp.add(direccionProp);
					listadodniProp.add(dni);
					listadotipoDocProp.add(tipoDoc);
				}
				resultado.setNombrePropietario(listadonombresProp);
 				resultado.setDireccion(listadodireccionProp);
 				resultado.setDni(listadodniProp);
 				resultado.setTipoDocumento(listadotipoDocProp);
 				
				/** fin: jrosas 15-10-07 **/
 				resultado.setTipoRegistro("V");
 			}
		}finally
		{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultado;
	}
	
}
