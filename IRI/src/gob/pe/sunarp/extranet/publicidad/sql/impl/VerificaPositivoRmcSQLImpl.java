package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaRMCBean;
import gob.pe.sunarp.extranet.publicidad.bean.ParticipanteBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.publicidad.bean.TituloBean;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaPositivoRmcSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaPositivoRmcSQLImpl extends SQLImpl implements VerificaPositivoRmcSQL {

	private Connection conn;
	private DBConnection dbConn;
	
	public VerificaPositivoRmcSQLImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
	
	public ArrayList busquedaNumPlacaRMC(String placa)throws SQLException, CustomException, ValidacionException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoConstanciaRMC= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT distinct B.DESC_BIEN,  "); 
		  	q.append("  MOV.DESCRIPCION as DESCMODELO, ");
			q.append("  MAV.DESCRIPCION as DESCMARCA,  ");
			q.append("  B.NUM_SERIE, ");
			q.append("  B.NUM_MOTOR,  ");
			q.append("  B.NUM_PLACA, ");
			q.append("  B.REFNUM_PART, ");
			q.append("  P.REG_PUB_ID, ");
			q.append("  P.OFIC_REG_ID, "); 
			q.append("  P.area_reg_id ");
			q.append(" FROM  ");
			q.append("   BIEN B, ");
			q.append("   TM_MARCA_VEHI MAV,  ");
			q.append("   TM_MODELO_VEHI MOV, ");
			q.append("   PARTIDA P,  ");
			q.append("   GRUPO_LIBRO_AREA_DET GLAD  ");
			
			q.append(" WHERE  ");
			q.append(" 	B.COD_MARCA = MAV.COD_MARCA ");
			q.append("  AND B.COD_MODELO = MOV.COD_MODELO ");
			q.append("  AND P.REFNUM_PART=B.REFNUM_PART ");
			q.append("  AND P.COD_LIBRO = GLAD.COD_LIBRO  ");
			q.append("  AND B.TIPO='E'  ");
			q.append("  AND P.ESTADO != '2'  ");
			q.append("  AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
			if(placa!=null && placa.trim().length()<7){
				placa =placa.trim()+" ";
			}
			q.append("  AND B.NUM_PLACA = '"+placa.trim()+"'");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaVehicularRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
				constanciaRMCBean.setDescripcion(rset.getString("DESC_BIEN"));
				constanciaRMCBean.setMarca(rset.getString("DESCMARCA"));
				constanciaRMCBean.setModelo(rset.getString("DESCMODELO"));
				constanciaRMCBean.setMotor(rset.getString("NUM_MOTOR"));
				constanciaRMCBean.setPlaca(rset.getString("NUM_PLACA"));
				constanciaRMCBean.setSerie(rset.getString("NUM_SERIE"));
				constanciaRMCBean.setPartida(obternerPartida(rset.getString("REFNUM_PART"), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID")));
				constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
				resultadoConstanciaRMC.add(constanciaRMCBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoConstanciaRMC;
	}
	
	public ArrayList busquedaNumSerieMatriculaRMC(String numero)throws SQLException, CustomException, ValidacionException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoConstanciaRMC= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT  distinct B.DESC_BIEN,  "); 
		  	q.append("  B.NUM_SERIE, ");
			q.append("   B.NUM_MOTOR,  ");
			q.append("  B.NUM_PLACA, ");
			q.append("  B.REFNUM_PART, ");
			q.append("  P.REG_PUB_ID, ");
			q.append("  P.OFIC_REG_ID, ");
			q.append("  P.area_reg_id ");
			q.append(" FROM  ");
			q.append("   BIEN B, ");
			q.append("   PARTIDA P,  ");
			q.append("   GRUPO_LIBRO_AREA_DET GLAD  ");
			q.append(" WHERE  ");
			q.append(" 	B.REFNUM_PART=P.REFNUM_PART ");
			q.append("  AND P.COD_LIBRO = GLAD.COD_LIBRO  ");
			q.append("  AND B.TIPO='E'  ");
			q.append("  AND P.ESTADO != '2'  ");
			q.append("  AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//
			q.append(" AND (B.DESC_BIEN='"+numero+"' OR B.NUM_SERIE='"+numero+"' OR B.NUM_MOTOR='"+numero+"') ");
						
			
			if (isTrace(this)) System.out.println("___verquery_busquedaNumSerieMatriculaRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
				constanciaRMCBean.setDescripcion(rset.getString("DESC_BIEN"));
				constanciaRMCBean.setMotor(rset.getString("NUM_MOTOR"));
				constanciaRMCBean.setPlaca(rset.getString("NUM_PLACA"));
				constanciaRMCBean.setSerie(rset.getString("NUM_SERIE"));
				constanciaRMCBean.setPartida(obternerPartida(rset.getString("REFNUM_PART"), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID")));
				constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
				resultadoConstanciaRMC.add(constanciaRMCBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoConstanciaRMC;
	}

	public ArrayList busquedaNumMatriculaNombreRMC(String numMatricula, String nombre)throws SQLException, CustomException, ValidacionException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoConstanciaRMC= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT  distinct B.DESC_BIEN,  "); 
		  	q.append("  B.NUM_SERIE, ");
			q.append("   B.NUM_MOTOR,  ");
			q.append("  B.NUM_PLACA, ");
			q.append("  B.REFNUM_PART, ");
			q.append("  P.REG_PUB_ID, ");
			q.append("  P.OFIC_REG_ID, ");
			q.append("  P.area_reg_id ");
			q.append(" FROM  ");
			q.append("   BIEN B, ");
			q.append("   PARTIDA P,  ");
			q.append("   GRUPO_LIBRO_AREA_DET GLAD  ");
			q.append(" WHERE  ");
			q.append(" 	B.REFNUM_PART=P.REFNUM_PART ");
			q.append("  AND P.COD_LIBRO = GLAD.COD_LIBRO  ");
			q.append("  AND B.TIPO='E'  ");
			q.append("  AND P.ESTADO != '2'  ");
			q.append("  AND GLAD.COD_GRUPO_LIBRO_AREA ='"+Constantes.GRUPO_LIBRO_AREA_BUSQUEDA_RMC+"' ");//

			q.append(" AND (B.DESC_BIEN='"+nombre+"' OR B.NUM_SERIE='"+nombre+"' OR B.NUM_MOTOR='"+nombre+"') ");
			q.append(" AND (B.DESC_BIEN='"+numMatricula+"' OR B.NUM_SERIE='"+numMatricula+"' OR B.NUM_MOTOR='"+numMatricula+"') ");
			

			
			if (isTrace(this)) System.out.println("___verquery_busquedaNumSerieMatriculaRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				ConstanciaRMCBean constanciaRMCBean = new ConstanciaRMCBean();
				constanciaRMCBean.setDescripcion(rset.getString("DESC_BIEN"));
				constanciaRMCBean.setMotor(rset.getString("NUM_MOTOR"));
				constanciaRMCBean.setPlaca(rset.getString("NUM_PLACA"));
				constanciaRMCBean.setSerie(rset.getString("NUM_SERIE"));
				constanciaRMCBean.setPartida(obternerPartida(rset.getString("REFNUM_PART"), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID")));
				constanciaRMCBean.setTitulos(obtenerTitulos(constanciaRMCBean.getPartida().getNumPartida(), rset.getString("REG_PUB_ID"), rset.getString("OFIC_REG_ID"), rset.getString("area_reg_id")));				
				resultadoConstanciaRMC.add(constanciaRMCBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoConstanciaRMC;
	}

	
	private PartidaBean obternerPartida(String refNumPar, String regPubId, String oficRegId) throws SQLException, CustomException, ValidacionException, DBException{
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList listaParticipante=new ArrayList();
		PartidaBean partida = new PartidaBean();
		String numPartida="";
		try{
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT DISTINCT P.NUM_PARTIDA, PN.NOMBRES,PN.APE_PAT,PN.APE_MAT,PL.NOMBRE as TIPOPART"); 
		 	q.append(" FROM  ");
			q.append("  PARTIDA        P, ");
			q.append("  IND_PRTC       IP,  ");
			q.append("  PRTC_NAT       PN, ");
			q.append("  PARTIC_LIBRO   PL  ");
			q.append("WHERE  ");
			q.append(" P.REFNUM_PART=IP.REFNUM_PART 	");
			q.append(" AND IP.CUR_PRTC=PN.CUR_PRTC ");
			q.append(" AND IP.COD_PARTIC=PL.COD_PARTIC ");
			q.append(" AND P.COD_LIBRO=PL.COD_LIBRO ");
			q.append(" AND P.REFNUM_PART='"+refNumPar+"'");
			q.append(" AND PN.REG_PUB_ID= '"+regPubId+"'");
			q.append(" AND PN.OFIC_REG_ID= '"+oficRegId+"'");
			q.append(" AND ip.tipo_pers= 'N'");	
			
			if (isTrace(this)) System.out.println("___verquery_obternerPartida__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			while (rset.next())
			{
				ParticipanteBean participante = new ParticipanteBean();
				participante.setApellidoPaterno(rset.getString("APE_PAT"));
				participante.setApellidoMaterno(rset.getString("APE_MAT"));
				participante.setNombre(rset.getString("NOMBRES"));
				participante.setDescripcionTipoParticipacion(rset.getString("TIPOPART"));
				participante.setTipoPersona(Constantes.PERSONA_NATURAL);
				listaParticipante.add(participante);
				numPartida=rset.getString("NUM_PARTIDA");
			}
			StringBuffer qjuridica  = new StringBuffer();
			
			qjuridica.append("SELECT DISTINCT P.NUM_PARTIDA, PJ.RAZON_SOCIAL,PJ.SIGLAS,PL.NOMBRE as TIPOPART"); 
		 	qjuridica.append(" FROM  ");
			qjuridica.append("  PARTIDA        P, ");
			qjuridica.append("  IND_PRTC       IP,  ");
			qjuridica.append("  PRTC_JUR       PJ, ");
			qjuridica.append("  PARTIC_LIBRO   PL  ");
			qjuridica.append(" WHERE  ");
			qjuridica.append(" P.REFNUM_PART=IP.REFNUM_PART 	");
			qjuridica.append(" AND IP.CUR_PRTC=PJ.CUR_PRTC ");
			qjuridica.append(" AND IP.COD_PARTIC=PL.COD_PARTIC ");
			qjuridica.append(" AND P.COD_LIBRO=PL.COD_LIBRO ");
			qjuridica.append(" AND P.REFNUM_PART='"+refNumPar+"'");
			qjuridica.append(" AND PJ.REG_PUB_ID= '"+regPubId+"'");
			qjuridica.append(" AND PJ.OFIC_REG_ID= '"+oficRegId+"'");
			qjuridica.append(" AND ip.tipo_pers= 'J'");	
						
			if (isTrace(this)) System.out.println("___verquery_obternerPartida__"+qjuridica.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(qjuridica.toString());
			while (rset.next())
			{
				ParticipanteBean participante = new ParticipanteBean();
				participante.setRazonSocial(rset.getString("RAZON_SOCIAL"));
				participante.setTipoPersona(Constantes.PERSONA_JURIDICA);
				participante.setDescripcionTipoParticipacion(rset.getString("TIPOPART"));
				listaParticipante.add(participante);
				numPartida=rset.getString("NUM_PARTIDA");
				
			}
			partida.setNumPartida(numPartida);
			partida.setParticipantes(listaParticipante);
			
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return partida;
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
