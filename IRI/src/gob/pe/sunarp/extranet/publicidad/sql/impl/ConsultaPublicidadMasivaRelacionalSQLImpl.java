package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboTmCapitania;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoAeronave;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoComb;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoEmbPesquera;
import gob.pe.sunarp.extranet.dbobj.DboTmTipoVehi;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.InputPMasivaRelacionalBean;
import gob.pe.sunarp.extranet.publicidad.bean.OutputPMasivaRelacionalBean;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultaPublicidadMasivaRelacionalSQL;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.Tarea;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class ConsultaPublicidadMasivaRelacionalSQLImpl extends SQLImpl implements ConsultaPublicidadMasivaRelacionalSQL
{
	private Connection conn;
	private DBConnection dbConn;
	
	
	public ConsultaPublicidadMasivaRelacionalSQLImpl(Connection conn, DBConnection dbConn) 
	{
		this.conn = conn;
		this.dbConn = dbConn;
	}

	public HashMap consultarAeronaveConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException 
	{
		HashMap map = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			StringBuffer queryUsoServicio  = new StringBuffer();
			
			queryUsoServicio.append("SELECT DISTINCT PART.NUM_PARTIDA,PART.REG_PUB_ID,PART.AREA_REG_ID, PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			q.append("SELECT COUNT(DISTINCT PART.NUM_PARTIDA),PART.REG_PUB_ID,REG.NOMBRE ");
			q.append("FROM   REG_AERONAVES AERO, PARTIDA PART,ASIENTO ASI,TM_TIPO_AERONAVE TPOAERO, ");
			queryUsoServicio.append("FROM   REG_AERONAVES AERO, PARTIDA PART,ASIENTO ASI,TM_TIPO_AERONAVE TPOAERO, ");
			q.append("REGIS_PUBLICO REG, OFIC_REGISTRAL OFI ");
			queryUsoServicio.append("REGIS_PUBLICO REG, OFIC_REGISTRAL OFI ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
				queryUsoServicio.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE  AERO.REFNUM_PART = PART.REFNUM_PART AND ");
			queryUsoServicio.append("WHERE  AERO.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = AERO.REFNUM_PART AND ");
			queryUsoServicio.append("ASI.REFNUM_PART = AERO.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
				queryUsoServicio.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
				queryUsoServicio.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getModelo().equals(""))
			{	
				q.append("AERO.MODELO LIKE '").append(inputPMasivaRelacionalBean.getModelo()).append("%' AND ");
				queryUsoServicio.append("AERO.MODELO LIKE '").append(inputPMasivaRelacionalBean.getModelo()).append("%' AND ");
			}
			
			q.append("AERO.ESTADO ='1' AND ");
			queryUsoServicio.append("AERO.ESTADO ='1' AND ");
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART AND ");
			queryUsoServicio.append("PART.REFNUM_PART = ASI.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
				queryUsoServicio.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				queryUsoServicio.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' AND ");
				queryUsoServicio.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' AND ");
			}
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			queryUsoServicio.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
				 queryUsoServicio.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
				queryUsoServicio.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("AERO.COD_TIPO_AERONAVE = TPOAERO.COD_TIPO_AERONAVE(+) ");
			queryUsoServicio.append("AERO.COD_TIPO_AERONAVE = TPOAERO.COD_TIPO_AERONAVE(+) ");
			if(!inputPMasivaRelacionalBean.getCodTipoAeronave().equals(""))
			{
				q.append("AND AERO.COD_TIPO_AERONAVE = '").append(inputPMasivaRelacionalBean.getCodTipoAeronave()).append("' ");
				queryUsoServicio.append("AND AERO.COD_TIPO_AERONAVE = '").append(inputPMasivaRelacionalBean.getCodTipoAeronave()).append("' ");
			}
			q.append("GROUP BY PART.REG_PUB_ID,REG.NOMBRE ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
 			inputPMasivaRelacionalBean.setCadenaQuery(queryUsoServicio.toString());
 			while(rset.next())
 			{
 				OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
 				outputBean.setCantidadRegistros(rset.getString(1));
 				outputBean.setZonaregistral(rset.getString(3));
 				
 				resultado.add(outputBean);
 			}
 			if(!inputPMasivaRelacionalBean.getCodTipoAeronave().equals(""))
 			{
	 			DboTmTipoAeronave dboAero = new DboTmTipoAeronave();
	 			dboAero.setFieldsToRetrieve(dboAero.CAMPO_DESCRIPCION);
	 			dboAero.setField(dboAero.CAMPO_COD_TIPO_AERONAVE, inputPMasivaRelacionalBean.getCodTipoAeronave());
	 			if(dboAero.find())
	 			{
	 				inputPMasivaRelacionalBean.setDescripcionTipoAeronave(dboAero.getField(dboAero.CAMPO_DESCRIPCION));
	 			}
 			}
 			map.put("resultado",resultado );
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
	}

	public HashMap consultarAeronaveDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException 
	{
		HashMap map = new HashMap();
		HashMap mapProp = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			int conta=0;
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			
			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
			{
				q.append("SELECT COUNT(DISTINCT PART.REFNUM_PART) ");
			}else
			{
				 q.append("SELECT DISTINCT REG.SIGLAS, OFI.NOMBRE, PART.NUM_PARTIDA, ");
				 q.append("AERO.NUM_MATRICULA ,AERO.SERIE, TPOAERO.DESCRIPCION AS TIPO, ");
				 q.append("AERO.MODELO, PART.REFNUM_PART,PART.TS_INSCRIP, TO_CHAR(PART.TS_INSCRIP,'DD/MON/YYYY'), PART.ESTADO,PART.AREA_REG_ID, ");
				 q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID ");	
			}
			q.append("FROM   REG_AERONAVES AERO, PARTIDA PART,ASIENTO ASI,TM_TIPO_AERONAVE TPOAERO, ");
			q.append("REGIS_PUBLICO REG, OFIC_REGISTRAL OFI ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE  AERO.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = AERO.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getModelo().equals(""))
			{	
				q.append("AERO.MODELO LIKE '").append(inputPMasivaRelacionalBean.getModelo()).append("%' AND ");
			}
			q.append("AERO.ESTADO ='1' AND ");
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append("ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' AND ");
			}
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("AERO.COD_TIPO_AERONAVE = TPOAERO.COD_TIPO_AERONAVE(+) ");
			if(!inputPMasivaRelacionalBean.getCodTipoAeronave().equals(""))
			{
				q.append("AND AERO.COD_TIPO_AERONAVE = '").append(inputPMasivaRelacionalBean.getCodTipoAeronave()).append("' ");
			}
			if(inputPMasivaRelacionalBean.getOrdenamiento()==null || inputPMasivaRelacionalBean.getOrdenamiento().equals(""))
			{
				q.append("ORDER BY PART.TS_INSCRIP DESC");
			}else
			{
				q.append("ORDER BY ").append(inputPMasivaRelacionalBean.getOrdenamiento()).append(" DESC ");
			}
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
 			boolean b = rset.next();
 			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
 			{
				if(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setCantidadRegistros(rset.getString(1));
					resultado.add(outputBean);
				}
				
 			}else
 			{
 				inputPMasivaRelacionalBean.setCadenaQuery(q.toString());
				while(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setSigla(rset.getString(1));
					outputBean.setNombreoficina(rset.getString(2));
					outputBean.setNumeroPartida(rset.getString(3));
					outputBean.setNumeromatricula(rset.getString(4));
					outputBean.setNumeroserie(rset.getString(5));
					outputBean.setTipoAeronave(rset.getString(6));
					outputBean.setModelo(rset.getString(7));
					mapProp = recuperarPropietario("A",rset.getString(8));
					outputBean.setRefnumpart(rset.getString(8));
					outputBean.setPropietario(""+mapProp.get("nombre"));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					if (rset.getDate(9) != null){
						outputBean.setFechaInscripcionDesde(simpleDateFormat.format(rset.getDate(9)));
					}else{
						outputBean.setFechaInscripcionDesde("");
					}
					outputBean.setEstado(rset.getString(11));
					outputBean.setAreaid(rset.getString(12));
					if(!(inputPMasivaRelacionalBean.getCodTipoAeronave()==null || inputPMasivaRelacionalBean.getCodTipoAeronave().equals("")))
					{
						inputPMasivaRelacionalBean.setDescripcionTipoAeronave(rset.getString(6));
					}
					resultado.add(outputBean);
					conta++;
					b = rset.next();
					if(!(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null))
					{
						if (conta==propiedades.getLineasPorPag())
						{
							if(b==true){	
								haySiguiente = true;
							}
							break;
						}
					}
				}
 			}
			map.put("resultado",resultado );
			
			if (inputPMasivaRelacionalBean.getFlagPagineo()==null){
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(resultado.size()));
			}else{
				inputPMasivaRelacionalBean.setCantidadRegistros(inputPMasivaRelacionalBean.getCantidadRegistros());
			}

			//calcular numero para boton "retroceder pagina"		
			if (inputPMasivaRelacionalBean.getSalto()==1){
				inputPMasivaRelacionalBean.setPagAnterior(-1);
			}else{
				inputPMasivaRelacionalBean.setPagAnterior(inputPMasivaRelacionalBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				inputPMasivaRelacionalBean.setPagSiguiente(-1);
			}else{
				inputPMasivaRelacionalBean.setPagSiguiente(inputPMasivaRelacionalBean.getSalto()+1);
			}

			//calcular regs del x al y
			int del = ((inputPMasivaRelacionalBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			inputPMasivaRelacionalBean.setNdel(del);
			inputPMasivaRelacionalBean.setNal(al);
			
			//map.put("pagineo", inputPMasivaRelacionalBean);
			
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}

	}

	public HashMap consultarBuqueConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException {
		HashMap map = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			StringBuffer queryUsoServicio  = new StringBuffer();
			
			queryUsoServicio.append("SELECT DISTINCT PART.NUM_PARTIDA,PART.REG_PUB_ID,PART.AREA_REG_ID, PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			q.append("SELECT COUNT(DISTINCT PART.NUM_PARTIDA), PART.REG_PUB_ID,REG.NOMBRE ");
			q.append("FROM REG_BUQUES BUQ, PARTIDA PART,TM_CAPITANIA CAP, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ");
			queryUsoServicio.append("FROM REG_BUQUES BUQ, PARTIDA PART,TM_CAPITANIA CAP, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append("GRUPO_LIBRO_ACTO GLA, ");
				queryUsoServicio.append("GRUPO_LIBRO_ACTO GLA, ");
			}
			q.append("ASIENTO ASI WHERE ");
			queryUsoServicio.append("ASIENTO ASI WHERE ");
			if(!inputPMasivaRelacionalBean.getNombreBuque().equals(""))
			{
				q.append("BUQ.NOMBRE LIKE '").append(inputPMasivaRelacionalBean.getNombreBuque()).append("%' AND "); 
				queryUsoServicio.append("BUQ.NOMBRE LIKE '").append(inputPMasivaRelacionalBean.getNombreBuque()).append("%' AND ");
			}
			q.append("BUQ.ESTADO = '1' AND ");
			queryUsoServicio.append("BUQ.ESTADO = '1' AND ");
			q.append("BUQ.REFNUM_PART = PART.REFNUM_PART AND ");
			queryUsoServicio.append("BUQ.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = BUQ.REFNUM_PART AND ");
			queryUsoServicio.append("ASI.REFNUM_PART = BUQ.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
				queryUsoServicio.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND ");
			}
			else if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
				queryUsoServicio.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			q.append("BUQ.COD_CAPITANIA = CAP.COD_CAPITANIA(+) AND ");
			queryUsoServicio.append("BUQ.COD_CAPITANIA = CAP.COD_CAPITANIA(+) AND ");
			if(!inputPMasivaRelacionalBean.getCodCapitania().equals(""))
			{
				q.append("CAP.COD_CAPITANIA = '").append(inputPMasivaRelacionalBean.getCodCapitania()).append("' AND ");
				queryUsoServicio.append("CAP.COD_CAPITANIA = '").append(inputPMasivaRelacionalBean.getCodCapitania()).append("' AND ");
			}
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			queryUsoServicio.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
				 queryUsoServicio.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
				queryUsoServicio.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("PART.COD_LIBRO IN ('053','054') AND ");
			queryUsoServicio.append("PART.COD_LIBRO IN ('053','054') AND ");
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART(+) ");
			queryUsoServicio.append("PART.REFNUM_PART = ASI.REFNUM_PART(+) ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				queryUsoServicio.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				queryUsoServicio.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
				queryUsoServicio.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			q.append("GROUP BY PART.REG_PUB_ID,REG.NOMBRE ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
			inputPMasivaRelacionalBean.setCadenaQuery(queryUsoServicio.toString());
 			while(rset.next())
 			{
 				OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
 				outputBean.setCantidadRegistros(rset.getString(1));
 				outputBean.setZonaregistral(rset.getString(3));
 				
 				resultado.add(outputBean);
 			}
 			
 			if(!inputPMasivaRelacionalBean.getCodCapitania().equals(""))
 			{
	 			DboTmCapitania dbocap = new DboTmCapitania(dbConn);
	 			dbocap.setFieldsToRetrieve(dbocap.CAMPO_DESCRIPCION);
	 			dbocap.setField(dbocap.CAMPO_CAPITANIA_ID, inputPMasivaRelacionalBean.getCodCapitania());
	 			if(dbocap.find())
	 			{
	 				inputPMasivaRelacionalBean.setDescripcionCapitania(dbocap.getField(dbocap.CAMPO_DESCRIPCION));
	 			}
 			}
 			map.put("resultado",resultado );
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
	}

	public HashMap consultarBuqueDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException {
		HashMap map = new HashMap();
		HashMap mapProp = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			int conta=0;
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
			{
				q.append("SELECT COUNT(DISTINCT PART.REFNUM_PART) ");
			}else
			{
				q.append("SELECT DISTINCT REG.SIGLAS,OFI.NOMBRE,PART.NUM_PARTIDA, BUQ.NUM_MATRICULA ,BUQ.NOMBRE, ");
				q.append("CAP.DESCRIPCION AS CAPITANIA,PART.REFNUM_PART, PART.TS_INSCRIP, TO_CHAR(PART.TS_INSCRIP,'DD/MON/YYYY'), PART.ESTADO, PART.AREA_REG_ID, ");
				q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			}
			q.append("FROM   REG_BUQUES BUQ, PARTIDA PART,TM_CAPITANIA CAP, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI,ASIENTO ASI ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE ");
			if(!inputPMasivaRelacionalBean.getNombreBuque().equals(""))
			{
				q.append("BUQ.NOMBRE LIKE '").append(inputPMasivaRelacionalBean.getNombreBuque()).append("%' AND "); 
			} 
			q.append("BUQ.ESTADO = '1' AND ");
			q.append("BUQ.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = BUQ.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
			}
			else if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			q.append("BUQ.COD_CAPITANIA = CAP.COD_CAPITANIA(+) AND ");
			if(!inputPMasivaRelacionalBean.getCodCapitania().equals(""))
			{
				q.append("CAP.COD_CAPITANIA = '").append(inputPMasivaRelacionalBean.getCodCapitania()).append("' AND ");
			}
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("PART.COD_LIBRO IN ('053','054') AND ");
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART(+) ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			if(inputPMasivaRelacionalBean.getOrdenamiento()==null || inputPMasivaRelacionalBean.getOrdenamiento().equals(""))
			{
				q.append("ORDER BY PART.TS_INSCRIP DESC");
			}else
			{
				q.append("ORDER BY ").append(inputPMasivaRelacionalBean.getOrdenamiento()).append(" DESC ");
			}
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
 			boolean b = rset.next();
 			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
 			{
				if(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setCantidadRegistros(rset.getString(1));
					resultado.add(outputBean);
				}
				
 			}else
 			{
 				inputPMasivaRelacionalBean.setCadenaQuery(q.toString());
				while(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setSigla(rset.getString(1));
					outputBean.setNombreoficina(rset.getString(2));
					outputBean.setNumeroPartida(rset.getString(3));
					outputBean.setNumeromatricula(rset.getString(4));
					outputBean.setNombreBuque(rset.getString(5));
					outputBean.setCapitaniaDescripcion(rset.getString(6));
					mapProp = recuperarPropietario("B",rset.getString(7));
					outputBean.setRefnumpart(rset.getString(7));
					outputBean.setPropietario(""+mapProp.get("nombre"));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					if (rset.getDate(8) != null){
						outputBean.setFechaInscripcionDesde(simpleDateFormat.format(rset.getDate(8)));
					}else{
						outputBean.setFechaInscripcionDesde("");
					}
					outputBean.setEstado(rset.getString(10));
					outputBean.setAreaid(rset.getString(11));
					inputPMasivaRelacionalBean.setDescripcionCapitania(rset.getString(6));
					resultado.add(outputBean);
					conta++;
					b = rset.next();
					if(!(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null))
					{
						if (conta==propiedades.getLineasPorPag())
						{
							if(b==true){	
								haySiguiente = true;
							}
							break;
						}
					}
				}
 			}
			map.put("resultado",resultado );
			
			if (inputPMasivaRelacionalBean.getFlagPagineo()==null){
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(resultado.size()));
			}else{
				inputPMasivaRelacionalBean.setCantidadRegistros(inputPMasivaRelacionalBean.getCantidadRegistros());
			}

			//calcular numero para boton "retroceder pagina"		
			if (inputPMasivaRelacionalBean.getSalto()==1){
				inputPMasivaRelacionalBean.setPagAnterior(-1);
			}else{
				inputPMasivaRelacionalBean.setPagAnterior(inputPMasivaRelacionalBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				inputPMasivaRelacionalBean.setPagSiguiente(-1);
			}else{
				inputPMasivaRelacionalBean.setPagSiguiente(inputPMasivaRelacionalBean.getSalto()+1);
			}

			//calcular regs del x al y
			int del = ((inputPMasivaRelacionalBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			inputPMasivaRelacionalBean.setNdel(del);
			inputPMasivaRelacionalBean.setNal(al);
			
			//map.put("pagineo", inputPMasivaRelacionalBean);
			
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
	}

	public HashMap consultarEmbarcacionConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException {
		HashMap map = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			StringBuffer queryUsoServicio  = new StringBuffer();
			
			queryUsoServicio.append("SELECT DISTINCT PART.NUM_PARTIDA,PART.REG_PUB_ID,PART.AREA_REG_ID, PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			q.append("SELECT COUNT(DISTINCT PART.NUM_PARTIDA), PART.REG_PUB_ID, REG.NOMBRE ");
			q.append("FROM   REG_EMB_PESQ EMB, PARTIDA PART, TM_CAPITANIA CAP, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, TM_TIPO_EMB_PESQ TEMB, ");
			queryUsoServicio.append("FROM   REG_EMB_PESQ EMB, PARTIDA PART, TM_CAPITANIA CAP, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, TM_TIPO_EMB_PESQ TEMB, ");
			q.append("ASIENTO ASI ");
			queryUsoServicio.append("ASIENTO ASI ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
				queryUsoServicio.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE  EMB.REFNUM_PART = PART.REFNUM_PART AND ");
			queryUsoServicio.append("WHERE  EMB.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = EMB.REFNUM_PART AND ");
			queryUsoServicio.append("ASI.REFNUM_PART = EMB.REFNUM_PART AND ");
			q.append("EMB.ESTADO = '1' AND ");
			queryUsoServicio.append("EMB.ESTADO = '1' AND ");
			q.append("EMB.COD_CAPITANIA = CAP.COD_CAPITANIA(+) AND ");
			queryUsoServicio.append("EMB.COD_CAPITANIA = CAP.COD_CAPITANIA(+) AND ");
			if(!inputPMasivaRelacionalBean.getCodCapitania().equals(""))
			{
				q.append("CAP.COD_CAPITANIA = '").append(inputPMasivaRelacionalBean.getCodCapitania()).append("' AND ");
				queryUsoServicio.append("CAP.COD_CAPITANIA = '").append(inputPMasivaRelacionalBean.getCodCapitania()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
				queryUsoServicio.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
				queryUsoServicio.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera().equals(""))
			{
				q.append("EMB.NOMBRE_EMB LIKE '").append(inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera()).append("%' AND ");
				queryUsoServicio.append("EMB.NOMBRE_EMB LIKE '").append(inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera()).append(" %' AND ");
			}
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			queryUsoServicio.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
				 queryUsoServicio.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
				queryUsoServicio.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("PART.COD_LIBRO = '038' AND ");
			queryUsoServicio.append("PART.COD_LIBRO = '038' AND ");
			q.append("EMB.COD_TIPO_EMB_PESQ = TEMB.COD_TIPO_EMB_PESQ(+) AND ");
			queryUsoServicio.append("EMB.COD_TIPO_EMB_PESQ = TEMB.COD_TIPO_EMB_PESQ(+) AND ");
			if(!inputPMasivaRelacionalBean.getCodTipoEmbarcacion().equals(""))
			{
				q.append("EMB.COD_TIPO_EMB_PESQ = '").append(inputPMasivaRelacionalBean.getCodTipoEmbarcacion()).append("' AND ");
				queryUsoServicio.append("EMB.COD_TIPO_EMB_PESQ = '").append(inputPMasivaRelacionalBean.getCodTipoEmbarcacion()).append("' AND ");
			}
			
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART(+) ");
			queryUsoServicio.append("PART.REFNUM_PART = ASI.REFNUM_PART(+) ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				queryUsoServicio.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				queryUsoServicio.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
				queryUsoServicio.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			q.append("GROUP BY PART.REG_PUB_ID, REG.NOMBRE ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
			inputPMasivaRelacionalBean.setCadenaQuery(queryUsoServicio.toString());
 			
 			while(rset.next())
 			{
 				OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
 				outputBean.setCantidadRegistros(rset.getString(1));
 				outputBean.setZonaregistral(rset.getString(3));
 				
 				resultado.add(outputBean);
 			}
 			
 			if(!inputPMasivaRelacionalBean.getCodTipoEmbarcacion().equals(""))
 			{	
	 			DboTmTipoEmbPesquera dboemb = new DboTmTipoEmbPesquera(dbConn);
	 			dboemb.setFieldsToRetrieve(dboemb.CAMPO_DESCRIPCION);
	 			dboemb.setField(dboemb.CAMPO_COD_TIPO_EMB_PESQ, inputPMasivaRelacionalBean.getCodTipoEmbarcacion());
	 			if(dboemb.find())
	 			{
	 				inputPMasivaRelacionalBean.setDescripcionTipoEmb(dboemb.getField(dboemb.CAMPO_DESCRIPCION));
	 			}
 			}
 			if(!inputPMasivaRelacionalBean.getCodCapitania().equals(""))
 			{
	 			DboTmCapitania dbocap = new DboTmCapitania(dbConn);
	 			dbocap.setFieldsToRetrieve(dbocap.CAMPO_DESCRIPCION);
	 			dbocap.setField(dbocap.CAMPO_CAPITANIA_ID, inputPMasivaRelacionalBean.getCodCapitania());
	 			if(dbocap.find())
	 			{
	 				inputPMasivaRelacionalBean.setDescripcionCapitania(dbocap.getField(dbocap.CAMPO_DESCRIPCION));
	 			}
 			}
 			map.put("resultado",resultado );
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
	}

	public HashMap consultarEmbarcacionDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException {
		HashMap map = new HashMap();
		HashMap mapProp = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			int conta=0;
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
			{
				q.append("SELECT COUNT(DISTINCT PART.REFNUM_PART) ");
			}else
			{
				q.append("SELECT DISTINCT REG.SIGLAS,OFI.NOMBRE,PART.NUM_PARTIDA,EMB.NUM_MATRICULA,EMB.NOMBRE_EMB,TEMB.DESCRIPCION AS TIPO, ");
				q.append("CAP.DESCRIPCION , PART.REFNUM_PART, PART.TS_INSCRIP , TO_CHAR(PART.TS_INSCRIP,'DD/MON/YYYY'), PART.ESTADO, PART.AREA_REG_ID, ");
				q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			}
			q.append("FROM   REG_EMB_PESQ EMB, PARTIDA PART, TM_CAPITANIA CAP, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, TM_TIPO_EMB_PESQ TEMB, ");
			q.append("ASIENTO ASI ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE  EMB.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = EMB.REFNUM_PART AND ");
			q.append("EMB.ESTADO = '1' AND ");
			q.append("EMB.COD_CAPITANIA = CAP.COD_CAPITANIA(+) AND ");
			if(!inputPMasivaRelacionalBean.getCodCapitania().equals(""))
			{
				q.append("CAP.COD_CAPITANIA = '").append(inputPMasivaRelacionalBean.getCodCapitania()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
			}
			else if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera().equals(""))
			{
				q.append("EMB.NOMBRE_EMB LIKE '").append(inputPMasivaRelacionalBean.getNombreEmbarcacionPesquera()).append("%' AND ");
			}
			q.append("PART.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("PART.COD_LIBRO = '038' AND ");
			q.append("EMB.COD_TIPO_EMB_PESQ = TEMB.COD_TIPO_EMB_PESQ(+) AND ");
			if(!inputPMasivaRelacionalBean.getCodTipoEmbarcacion().equals(""))
			{
				q.append("EMB.COD_TIPO_EMB_PESQ = '").append(inputPMasivaRelacionalBean.getCodTipoEmbarcacion()).append("' AND ");
			}
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART(+) ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			if(inputPMasivaRelacionalBean.getOrdenamiento()==null || inputPMasivaRelacionalBean.getOrdenamiento().equals(""))
			{
				q.append("ORDER BY PART.TS_INSCRIP DESC ");
			}else
			{
				q.append("ORDER BY ").append(inputPMasivaRelacionalBean.getOrdenamiento()).append(" DESC ");
			}
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			System.out.println("___verquery_busquedaPMR__"+q.toString());
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
 			boolean b = rset.next();
 			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
 			{
				if(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setCantidadRegistros(rset.getString(1));
					resultado.add(outputBean);
				}
				
 			}else
 			{
 				inputPMasivaRelacionalBean.setCadenaQuery(q.toString());
				while(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setSigla(rset.getString(1));
					outputBean.setNombreoficina(rset.getString(2));
					outputBean.setNumeroPartida(rset.getString(3));
					outputBean.setNumeromatricula(rset.getString(4));
					outputBean.setNombreEmbarcacion(rset.getString(5));
					outputBean.setTipoEmbarcacion(rset.getString(6));
					outputBean.setCapitaniaDescripcion(rset.getString(7));
					mapProp = recuperarPropietario("E",rset.getString(8));
					outputBean.setRefnumpart(rset.getString(8));
					outputBean.setPropietario(""+mapProp.get("nombre"));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					if (rset.getDate(9) != null){
						outputBean.setFechaInscripcionDesde(simpleDateFormat.format(rset.getDate(9)));
					}else{
						outputBean.setFechaInscripcionDesde("");
					}
					
					outputBean.setEstado(rset.getString(11));
					outputBean.setAreaid(rset.getString(12));
					inputPMasivaRelacionalBean.setDescripcionTipoEmb(rset.getString(6));
					inputPMasivaRelacionalBean.setDescripcionCapitania(rset.getString(7));
					resultado.add(outputBean);
					conta++;
					b = rset.next();
					if(!(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null))
					{
						if (conta==propiedades.getLineasPorPag())
						{
							if(b==true){	
								haySiguiente = true;
							}
							break;
						}
					}
				}
 			}
			map.put("resultado",resultado);
			
			if (inputPMasivaRelacionalBean.getFlagPagineo()==null){
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(resultado.size()));
			}else{
				inputPMasivaRelacionalBean.setCantidadRegistros(inputPMasivaRelacionalBean.getCantidadRegistros());
			}

			//calcular numero para boton "retroceder pagina"		
			if (inputPMasivaRelacionalBean.getSalto()==1){
				inputPMasivaRelacionalBean.setPagAnterior(-1);
			}else{
				inputPMasivaRelacionalBean.setPagAnterior(inputPMasivaRelacionalBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				inputPMasivaRelacionalBean.setPagSiguiente(-1);
			}else{
				inputPMasivaRelacionalBean.setPagSiguiente(inputPMasivaRelacionalBean.getSalto()+1);
			}

			//calcular regs del x al y
			int del = ((inputPMasivaRelacionalBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			inputPMasivaRelacionalBean.setNdel(del);
			inputPMasivaRelacionalBean.setNal(al);
			
			//map.put("pagineo", inputPMasivaRelacionalBean);
			
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
	}

	public HashMap recuperarPropietario(String registro, String refnumpart) throws SQLException, CustomException, ValidacionException, DBException 
	{
		HashMap map = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		String nombre="";
		String direccion="";
		String codigoLibro="";
		String codigoParti="";
		if(registro.equals("A"))
		{
			codigoLibro = "040";
			codigoParti = "003";
		}else if(registro.equals("B"))
		{
			codigoLibro = "('053','054')";
			codigoParti = "('022','123','125','133','140')";
		}else if(registro.equals("E"))
		{
			codigoLibro = "038";
			codigoParti = "001";
		}else if(registro.equals("V"))
		{
			codigoLibro = "088";
			codigoParti = "038";
		}
		
		try
		{
			Propiedades propiedades = Propiedades.getInstance();
			int conta=0;
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT DECODE(IP.TIPO_PERS,'N',RTRIM(LTRIM(PN.NOMBRES))||' '||RTRIM(LTRIM(PN.APE_PAT))||' '||RTRIM(LTRIM(PN.APE_MAT)),'J',PJ.RAZON_SOCIAL) ");
			q.append("AS PROPIETARIO, IP.DIRECCION ");
			q.append("FROM   IND_PRTC IP, PARTIC_LIBRO PL , PRTC_NAT PN, PRTC_JUR PJ ");
			q.append("WHERE  IP.REFNUM_PART ='").append(refnumpart).append("' AND ");
			q.append("IP.COD_PARTIC = PL.COD_PARTIC AND ");
			if(registro.equals("B"))
			{
				q.append("PL.COD_LIBRO IN ").append(codigoLibro).append(" AND ");
				q.append("PL.COD_PARTIC IN ").append(codigoParti).append(" AND ");
			}else
			{
				q.append("PL.COD_LIBRO = '").append(codigoLibro).append("' AND ");
				q.append("PL.COD_PARTIC='").append(codigoParti).append("' AND ");
			}
			
			q.append("IP.CUR_PRTC = PN.CUR_PRTC(+) AND ");
			q.append("IP.CUR_PRTC = PJ.CUR_PRTC(+) AND ");
			q.append("IP.ESTADO = '1' ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			boolean b = rset.next();
			while(b)
			{
				nombre = rset.getString(1);
				direccion = rset.getString(2);
				break;
			}
			map.put("nombre",nombre);
			map.put("direccion", direccion);
			
		}finally{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return map;
	}

	public HashMap consultarRMCConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException {
		HashMap map = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		String refnum_partN="";
		String refnum_partA="";
		int count=0;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			ConsultarPartidaDirectaSQL consultarPartida = new ConsultarPartidaDirectaSQLImpl(this.conn,this.dbConn);
			ArrayList resultado = new ArrayList();
			ArrayList resultadoNuevo = new ArrayList();
			boolean haySiguiente = false;
			String cadena = "";
			StringBuffer q  = new StringBuffer();
			StringBuffer queryUsoServicio  = new StringBuffer();
			
			//q.append("SELECT DISTINCT PART.REFNUM_PART ");
			//q.append("FROM   BIEN BI, PARTIDA PART, TM_ACTO ACT, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ASIENTO ASI, GRUPO_LIBRO_AREA_DET GLAD ");
			queryUsoServicio.append("SELECT DISTINCT PART.NUM_PARTIDA,PART.REG_PUB_ID,PART.AREA_REG_ID, PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			q.append("SELECT COUNT(DISTINCT PART.NUM_PARTIDA), PART.REG_PUB_ID, REG.NOMBRE ");
 			q.append("FROM ASIENTO_GARANTIA BI, PARTIDA PART, TM_ACTO ACT, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ASIENTO ASI, GRUPO_LIBRO_AREA_DET GLAD ");
 			queryUsoServicio.append("FROM   ASIENTO_GARANTIA BI, PARTIDA PART, TM_ACTO ACT, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ASIENTO ASI, GRUPO_LIBRO_AREA_DET GLAD ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
				queryUsoServicio.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE  BI.REFNUM_PART = PART.REFNUM_PART AND ");
			queryUsoServicio.append("WHERE  BI.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = BI.REFNUM_PART AND ");
			queryUsoServicio.append("ASI.REFNUM_PART = BI.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
				queryUsoServicio.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
				queryUsoServicio.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			q.append("BI.COD_ACTO = ACT.COD_ACTO AND ");
			queryUsoServicio.append("BI.COD_ACTO = ACT.COD_ACTO AND ");
			if(!inputPMasivaRelacionalBean.getMontoGarantiaDesde().equals(""))
			{
				q.append("BI.MONTO_IMPO >= '").append(inputPMasivaRelacionalBean.getMontoGarantiaDesde()).append("' AND ");	
				queryUsoServicio.append("BI.MONTO_IMPO >= '").append(inputPMasivaRelacionalBean.getMontoGarantiaDesde()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getMontoGarantiaHasta().equals(""))
			{
				q.append("BI.MONTO_IMPO <= '").append(inputPMasivaRelacionalBean.getMontoGarantiaHasta()).append("' AND ");
				queryUsoServicio.append("BI.MONTO_IMPO <= '").append(inputPMasivaRelacionalBean.getMontoGarantiaHasta()).append("' AND ");
			}
			q.append("PART.REG_PUB_ID  = REG.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID  = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			queryUsoServicio.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART AND ");
			queryUsoServicio.append("PART.REFNUM_PART = ASI.REFNUM_PART AND ");
			q.append("PART.COD_LIBRO = GLAD.COD_LIBRO AND ");
			queryUsoServicio.append("PART.COD_LIBRO = GLAD.COD_LIBRO AND ");
			q.append("GLAD.COD_GRUPO_LIBRO_AREA='21' ");
			queryUsoServicio.append("GLAD.COD_GRUPO_LIBRO_AREA='21' ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("AND PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' ");
				 queryUsoServicio.append("AND PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("AND PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" ");
				queryUsoServicio.append("AND PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" ");
			}
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append("AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				queryUsoServicio.append("AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				queryUsoServicio.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
				queryUsoServicio.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			q.append("GROUP BY PART.REG_PUB_ID, REG.NOMBRE ");
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			/*while(rset.next())
 			{
 				resultado.add(rset.getString(1));
 				count = count + 1;
 			}*/
 			inputPMasivaRelacionalBean.setCadenaQuery(queryUsoServicio.toString());
 			while(rset.next())
 			{
 				OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
 				outputBean.setCantidadRegistros(rset.getString(1));
 				outputBean.setZonaregistral(rset.getString(3));
 				resultado.add(outputBean);
 			}
 			/*if(count == 0)
 			{
 				throw new ValidacionException("No se encontraron resultados para su búsqueda");
 			}
 			for(int i=0;i<resultado.size();i++)
 			{
 			 	refnum_partA = (String)resultado.get(i);
 				refnum_partN = consultarPartida.obtenerRefNumParNuevo(refnum_partA);
 				if(refnum_partN==null)
 				{
 					resultadoNuevo.add(refnum_partA);
 				}else
 				{
 					resultadoNuevo.add(refnum_partN);
 				}
 				
 			}
 			cadena = Tarea.recuperarRefnum_part(resultadoNuevo);
 			
 			q2.append("SELECT COUNT(DISTINCT PART.NUM_PARTIDA), PART.REG_PUB_ID, REG.NOMBRE ");
 			q2.append("FROM   BIEN BI, PARTIDA PART, TM_ACTO ACT, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ASIENTO ASI ");
 			q2.append("WHERE  BI.REFNUM_PART IN ").append(cadena).append(" AND ");
 			q2.append("BI.REFNUM_PART = PART.REFNUM_PART AND ");
 			q2.append("ASI.REFNUM_PART = BI.REFNUM_PART AND ");
 			q2.append("BI.COD_ACTO = ACT.COD_ACTO AND ");
 			q2.append("PART.REG_PUB_ID  = REG.REG_PUB_ID AND ");
 			q2.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
 			q2.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
 			q2.append("PART.REFNUM_PART = ASI.REFNUM_PART ");
 			q2.append("GROUP BY PART.REG_PUB_ID, REG.NOMBRE ");
 			
 			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q2.toString());
 			
 			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q2.toString());
 			
 			resultado.clear();
 			
 			while(rset.next())
 			{
 				OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
 				outputBean.setCantidadRegistros(rset.getString(1));
 				outputBean.setZonaregistral(rset.getString(3));
 				resultado.add(outputBean);
 			}*/
 			map.put("resultado",resultado);
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
	}

	public HashMap consultarRMCDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException 
	{
		HashMap map = new HashMap();
		HashMap mapProp = new HashMap();
		ArrayList lista = new ArrayList();
		Statement stmt   = null;
		ResultSet rset   = null;
		
		String refnum_part = "";
		
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			int conta=0;
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			ConsultarPartidaDirectaSQL consultarPartida = new ConsultarPartidaDirectaSQLImpl(this.conn,this.dbConn);
			
			StringBuffer q  = new StringBuffer();
			StringBuffer q2  = new StringBuffer();
			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
			{
				//q.append("SELECT COUNT(DISTINCT PART.REFNUM_PART) ");
				q.append("SELECT COUNT(REFNUM_PART) FROM (");
				q.append("SELECT DISTINCT PART.REFNUM_PART,REG.SIGLAS,OFI.NOMBRE,PART.NUM_PARTIDA, ");
				q.append("BI.MONTO_IMPO , DECODE(PART.ESTADO,'1','ACTIVO','0','INACTIVO'), ");
				q.append("BI.TS_ULT_SYNC, PART.TS_INSCRIP,  ");
				q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID, PART.AREA_REG_ID ");
			}else
			{
				q.append("SELECT DISTINCT PART.REFNUM_PART, BI.NS_ASIENTO, REG.SIGLAS,OFI.NOMBRE,PART.NUM_PARTIDA, ");
				q.append("BI.MONTO_IMPO , DECODE(PART.ESTADO,'1','ACTIVO','0','INACTIVO'), ");
				q.append("BI.TS_ULT_SYNC, PART.TS_INSCRIP,  ");
				q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID, PART.AREA_REG_ID ");
			}
			q.append("FROM ASIENTO_GARANTIA BI, PARTIDA PART, TM_ACTO ACT, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ASIENTO ASI, GRUPO_LIBRO_AREA_DET GLAD ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE  BI.REFNUM_PART = PART.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = BI.REFNUM_PART AND ");
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("PART.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("PART.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			if(!inputPMasivaRelacionalBean.getMontoGarantiaDesde().equals(""))
			{
				q.append("BI.MONTO_IMPO >= '").append(inputPMasivaRelacionalBean.getMontoGarantiaDesde()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getMontoGarantiaHasta().equals(""))
			{
				q.append("BI.MONTO_IMPO <= '").append(inputPMasivaRelacionalBean.getMontoGarantiaHasta()).append("' AND ");
			}
			q.append("BI.COD_ACTO = ACT.COD_ACTO AND ");
			q.append("PART.REG_PUB_ID  = REG.REG_PUB_ID AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			q.append("PART.REFNUM_PART = ASI.REFNUM_PART AND ");
			q.append("PART.COD_LIBRO = GLAD.COD_LIBRO AND ");
			q.append("GLAD.COD_GRUPO_LIBRO_AREA='21' ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append(" AND PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append(" AND PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" ");
			}
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append("AND ASI.COD_ACTO = GLA.COD_ACTO ");
				q.append("AND GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				//q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			//q.append("BI.TS_ULT_SYNC = (SELECT MIN(TS_ULT_SYNC) FROM BIEN WHERE BIEN.REFNUM_PART=PART.REFNUM_PART) ");
			if(inputPMasivaRelacionalBean.getOrdenamiento()==null || inputPMasivaRelacionalBean.getOrdenamiento().equals(""))
			{
				q.append("ORDER BY PART.TS_INSCRIP DESC ");
			}else
			{
				q.append("ORDER BY ").append(inputPMasivaRelacionalBean.getOrdenamiento()).append(" DESC ");
			}
			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
			{
				q.append(")");
			}
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
 			boolean b = rset.next();
 			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
 			{
				if(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setCantidadRegistros(rset.getString(1));
					resultado.add(outputBean);
				}
				
 			}else
 			{
 				inputPMasivaRelacionalBean.setCadenaQuery(q.toString());
				while(b)
				{
					Statement stmt2   = null;
					ResultSet rset2   = null;
					
					refnum_part = rset.getString(1);
					String nsAsiento = rset.getString(2);
					
					q2.delete(0, q2.length());
					q2.append("SELECT DISTINCT PART.REFNUM_PART,REG.SIGLAS,OFI.NOMBRE,PART.NUM_PARTIDA, ");
					q2.append("BI.MONTO_IMPO,ACT.DESCRIPCION AS TIPO , DECODE(PART.ESTADO,'1','ACTIVO','0','INACTIVO'), ");
					q2.append("BI.TS_ULT_SYNC ,PART.TS_INSCRIP, PART.ESTADO, PART.AREA_REG_ID, PART.COD_LIBRO ");
					q2.append("FROM   ASIENTO_GARANTIA BI, PARTIDA PART, TM_ACTO ACT, REGIS_PUBLICO REG, OFIC_REGISTRAL OFI, ASIENTO ASI ");
					q2.append("WHERE  PART.REFNUM_PART = '").append(refnum_part).append("' AND ");
					q2.append("       BI.NS_ASIENTO = ").append(nsAsiento).append(" AND ");
					q2.append("BI.REFNUM_PART = PART.REFNUM_PART AND ");
					q2.append("ASI.REFNUM_PART = BI.REFNUM_PART AND ");
					q2.append("BI.COD_ACTO = ACT.COD_ACTO AND ");
					q2.append("PART.REG_PUB_ID  = REG.REG_PUB_ID AND ");
					q2.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
					q2.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
					//q2.append("PART.REFNUM_PART = ASI.REFNUM_PART AND ");
					q2.append("PART.REFNUM_PART = ASI.REFNUM_PART ");
					//q2.append("BI.TS_ULT_SYNC = (SELECT MIN(TS_ULT_SYNC) FROM BIEN WHERE BIEN.REFNUM_PART='").append(refnum_part).append("') ");
					if(inputPMasivaRelacionalBean.getOrdenamiento()==null || inputPMasivaRelacionalBean.getOrdenamiento().equals(""))
					{
						q2.append("ORDER BY PART.TS_INSCRIP DESC ");
					}else
					{
						q2.append("ORDER BY ").append(inputPMasivaRelacionalBean.getOrdenamiento()).append(" DESC ");
					}
					
					if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q2.toString());
					
					stmt2   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		 			stmt2.setFetchSize(propiedades.getLineasPorPag());
		 			rset2   = stmt2.executeQuery(q2.toString());
		 			
		 			if(rset2.next())
					{
						OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
						outputBean.setRefnumpart(rset2.getString(1));
		 				outputBean.setSigla(rset2.getString(2));
		 				outputBean.setNombreoficina(rset2.getString(3));
		 				outputBean.setNumeroPartida(rset2.getString(4));
		 				outputBean.setMonto(rset2.getString(5));
		 				outputBean.setTipoContrato(rset2.getString(6));
		 				outputBean.setEstadoPartida(rset2.getString(7));
		 				
		 				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 				if (rset2.getDate(9) != null){
		 					outputBean.setFechaInscripcionDesde(simpleDateFormat.format(rset2.getDate(9)));
		 				}else{
		 					outputBean.setFechaInscripcionDesde("");
		 				}
		 				outputBean.setEstado(rset2.getString(10));
		 				outputBean.setAreaid(rset2.getString(11));
		 				outputBean.setCodLibro(rset2.getString(12));
		 				resultado.add(outputBean);
					}
		 			conta++;
					b = rset.next();
					
					if(!(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null))
					{
						if (conta==propiedades.getLineasPorPag())
						{
							if(b==true){	
								haySiguiente = true;
							}
							break;
						}
					}
					JDBC.getInstance().closeResultSet(rset2);
					JDBC.getInstance().closeStatement(stmt2);
				}
 			}
 			
			map.put("resultado",resultado);
			
			if (inputPMasivaRelacionalBean.getFlagPagineo()==null){
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(resultado.size()));
			}else{
				inputPMasivaRelacionalBean.setCantidadRegistros(inputPMasivaRelacionalBean.getCantidadRegistros());
			}

			//calcular numero para boton "retroceder pagina"		
			if (inputPMasivaRelacionalBean.getSalto()==1){
				inputPMasivaRelacionalBean.setPagAnterior(-1);
			}else{
				inputPMasivaRelacionalBean.setPagAnterior(inputPMasivaRelacionalBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				inputPMasivaRelacionalBean.setPagSiguiente(-1);
			}else{
				inputPMasivaRelacionalBean.setPagSiguiente(inputPMasivaRelacionalBean.getSalto()+1);
			}

			//calcular regs del x al y
			int del = ((inputPMasivaRelacionalBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			inputPMasivaRelacionalBean.setNdel(del);
			inputPMasivaRelacionalBean.setNal(al);
			
			//map.put("pagineo", inputPMasivaRelacionalBean);
			
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
			
		}
	}

	public HashMap consultarVehiculoConsolidadoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException {
		HashMap map = new HashMap();
		Statement stmt   = null;
		Statement stmt2   = null;
		ResultSet rset   = null;
		ResultSet rset2   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			int agrupacion = 0;
			agrupacion = Integer.parseInt(inputPMasivaRelacionalBean.getAgrupación());
			String cadena = "";
			
			switch (agrupacion) {
			case 1:
				cadena = "REG.NOMBRE ";
				break;
			case 2:
				cadena = "REG.NOMBRE, MAR.DESCRIPCION ";
				break;
			case 3:
				cadena = "REG.NOMBRE, MO.DESCRIPCION ";
				break;
			case 4:
				cadena = "REG.NOMBRE, VEH.ANO_FABRIC ";
				break;
			case 5:
				cadena = "REG.NOMBRE, TO_CHAR(VEH.TS_INSCRIP,'DD/MON/YYYY') ";
				break;
			case 6:
				cadena = "REG.NOMBRE, TV.DESCRIPCION ";
				break;
			case 7:
				cadena = "REG.NOMBRE, VEH.DESC_COLOR_01 ";
				break;
			case 8:
				cadena = "REG.NOMBRE, TC.DESCRIPCION ";
				break;
			default:
				cadena = "REG.NOMBRE ";
				break;
			}
			
			StringBuffer q  = new StringBuffer();
			StringBuffer q2  = new StringBuffer();
			StringBuffer queryUsoServicio  = new StringBuffer();
			
			queryUsoServicio.append("SELECT DISTINCT PART.NUM_PARTIDA,PART.REG_PUB_ID,PART.AREA_REG_ID, PART.REG_PUB_ID, PART.OFIC_REG_ID ");
			q.append("SELECT COUNT(DISTINCT PART.NUM_PARTIDA), PART.REG_PUB_ID, ").append(cadena);
			q.append("FROM VEHICULO VEH, TM_MARCA_VEHI MAR, TM_MODELO_VEHI MO, PARTIDA PART, REGIS_PUBLICO REG, ");
			queryUsoServicio.append("FROM VEHICULO VEH, TM_MARCA_VEHI MAR, TM_MODELO_VEHI MO, PARTIDA PART, REGIS_PUBLICO REG, ");
			q.append("OFIC_REGISTRAL OFI , ASIENTO ASI, TM_TIPO_VEHI TV, TM_TIPO_COMB TC ");
			queryUsoServicio.append("OFIC_REGISTRAL OFI , ASIENTO ASI, TM_TIPO_VEHI TV, TM_TIPO_COMB TC ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
				queryUsoServicio.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE ");
			queryUsoServicio.append("WHERE ");
			if(!inputPMasivaRelacionalBean.getCodTipoVehiculo().equals(""))
			{
				q.append(" VEH.COD_TIPO_VEHI='").append(inputPMasivaRelacionalBean.getCodTipoVehiculo()).append("' AND ");
				queryUsoServicio.append(" VEH.COD_TIPO_VEHI='").append(inputPMasivaRelacionalBean.getCodTipoVehiculo()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getCodTipoCombustible().equals(""))
			{
				q.append("VEH.COD_TIPO_COMB='").append(inputPMasivaRelacionalBean.getCodTipoCombustible()).append("' AND ");
				queryUsoServicio.append("VEH.COD_TIPO_COMB='").append(inputPMasivaRelacionalBean.getCodTipoCombustible()).append("' AND ");
			}
			q.append("VEH.COD_TIPO_COMB = TC.COD_TIPO_COMB AND ");
			queryUsoServicio.append("VEH.COD_TIPO_COMB = TC.COD_TIPO_COMB AND ");
			q.append("VEH.COD_TIPO_VEHI = TV.COD_TIPO_VEHI AND ");
			queryUsoServicio.append("VEH.COD_TIPO_VEHI = TV.COD_TIPO_VEHI AND ");
			if(!inputPMasivaRelacionalBean.getColorVeh().equals(""))
			{
				q.append("VEH.DESC_COLOR_01 LIKE '").append(inputPMasivaRelacionalBean.getColorVeh()).append("%' AND ");
				queryUsoServicio.append("VEH.DESC_COLOR_01 LIKE '").append(inputPMasivaRelacionalBean.getColorVeh()).append("%' AND ");
			}
			q.append("VEH.COD_MARCA = MAR.COD_MARCA AND ");
			queryUsoServicio.append("VEH.COD_MARCA = MAR.COD_MARCA AND ");
			if(!inputPMasivaRelacionalBean.getMarca().equals(""))
			{
				q.append("MAR.DESCRIPCION LIKE '").append(inputPMasivaRelacionalBean.getMarca()).append("%' AND ");
				queryUsoServicio.append("MAR.DESCRIPCION LIKE '").append(inputPMasivaRelacionalBean.getMarca()).append("%' AND ");
			}
			q.append("VEH.COD_MODELO = MO.COD_MODELO AND ");
			queryUsoServicio.append("VEH.COD_MODELO = MO.COD_MODELO AND ");
			if(!inputPMasivaRelacionalBean.getModelo().equals(""))
			{
				q.append("MO.DESCRIPCION LIKE '").append(inputPMasivaRelacionalBean.getModelo()).append("%' AND ");
				queryUsoServicio.append("MO.DESCRIPCION LIKE '").append(inputPMasivaRelacionalBean.getModelo()).append("%' AND ");
			}
			if(!inputPMasivaRelacionalBean.getAnoFabricacionDesde().equals(""))
			{
				q.append("VEH.ANO_FABRIC >= '").append(inputPMasivaRelacionalBean.getAnoFabricacionDesde()).append("' AND ");	
				queryUsoServicio.append("VEH.ANO_FABRIC >= '").append(inputPMasivaRelacionalBean.getAnoFabricacionDesde()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getAnoFabricacionHasta().equals(""))
			{
				q.append("VEH.ANO_FABRIC <= '").append(inputPMasivaRelacionalBean.getAnoFabricacionHasta()).append("' AND ");
				queryUsoServicio.append("VEH.ANO_FABRIC <= '").append(inputPMasivaRelacionalBean.getAnoFabricacionHasta()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("VEH.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
				queryUsoServicio.append("VEH.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND ");
			}
			else if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("VEH.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
				queryUsoServicio.append("VEH.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			q.append("PART.REFNUM_PART = VEH.REFNUM_PART AND ");
			queryUsoServicio.append("PART.REFNUM_PART = VEH.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = VEH.REFNUM_PART AND ");
			queryUsoServicio.append("ASI.REFNUM_PART = VEH.REFNUM_PART AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			queryUsoServicio.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			queryUsoServicio.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			q.append("OFI.REG_PUB_ID = REG.REG_PUB_ID AND ");
			queryUsoServicio.append("OFI.REG_PUB_ID = REG.REG_PUB_ID AND ");
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
				 queryUsoServicio.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
				queryUsoServicio.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("ASI.REFNUM_PART = PART.REFNUM_PART ");
			queryUsoServicio.append("ASI.REFNUM_PART = PART.REFNUM_PART ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				queryUsoServicio.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				queryUsoServicio.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
				queryUsoServicio.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			
			q.append("GROUP BY PART.REG_PUB_ID, ").append(cadena);
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
			inputPMasivaRelacionalBean.setCadenaQuery(queryUsoServicio.toString());
 			while(rset.next())
 			{
 				OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
 				outputBean.setCantidadRegistros(rset.getString(1));
 				outputBean.setZonaregistral(rset.getString(3));
 				if(agrupacion != 1)
 				{
 					outputBean.setSubagrupacion(rset.getString(4));
 				}
 				resultado.add(outputBean);
 			}
 			
 			q2.append("SELECT TV.DESCRIPCION "); 
 			q2.append("FROM TM_TIPO_VEHI TV ");
 			q2.append("WHERE TV.COD_TIPO_VEHI = '").append(inputPMasivaRelacionalBean.getCodTipoVehiculo()).append("' ");
 			
 			stmt2   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset2   = stmt2.executeQuery(q2.toString());
 			
 			if(rset2.next())
 			{
 				inputPMasivaRelacionalBean.setDescripcionTipoVehiculo(rset2.getString(1));
 			}
 			if(!inputPMasivaRelacionalBean.getCodTipoCombustible().equals(""))
 			{
	 			DboTmTipoComb dboCom = new DboTmTipoComb(dbConn);
	 			dboCom.setFieldsToRetrieve(dboCom.CAMPO_DESCRIPCION);
	 			dboCom.setField(dboCom.CAMPO_COD_TIPO_COMB, inputPMasivaRelacionalBean.getCodTipoCombustible());
	 			if(dboCom.find())
	 			{
	 				inputPMasivaRelacionalBean.setDescripcionTipoComb(dboCom.getField(dboCom.CAMPO_DESCRIPCION));
	 			}
 			}
 			map.put("resultado",resultado );
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
			JDBC.getInstance().closeResultSet(rset2);
			JDBC.getInstance().closeStatement(stmt2);
			
		}
	}

	public HashMap consultarVehiculoDetalladoPMR(InputPMasivaRelacionalBean inputPMasivaRelacionalBean) throws SQLException, CustomException, ValidacionException, DBException 
	{
		HashMap map = new HashMap();
		HashMap mapProp = new HashMap();
		Statement stmt   = null;
		ResultSet rset   = null;
		Statement stmt2   = null;
		ResultSet rset2   = null;
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			int conta=0;
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			
			StringBuffer q  = new StringBuffer();
			StringBuffer q2  = new StringBuffer();
			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
			{
				q.append("SELECT COUNT(DISTINCT PART.REFNUM_PART) ");
			}else
			{
				q.append("SELECT DISTINCT REG.SIGLAS, OFI.NOMBRE,PART.NUM_PARTIDA, VEH.NUM_PLACA, VEH.NUM_SERIE, ");
				q.append("VEH.NUM_MOTOR,VEH.ANO_FABRIC,VEH.DESC_COLOR_01, ");
				q.append("MAR.DESCRIPCION AS MARCA, MO.DESCRIPCION AS MODELO, PART.REFNUM_PART ,VEH.TS_INSCRIP ,VEH.TS_INSCRIP, PART.ESTADO, ");
				q.append("PART.REG_PUB_ID, PART.OFIC_REG_ID, PART.AREA_REG_ID, TV.DESCRIPCION, TC.DESCRIPCION ");
			}
			q.append("FROM   VEHICULO VEH, TM_MARCA_VEHI MAR, TM_MODELO_VEHI MO, PARTIDA PART, REGIS_PUBLICO REG, ");
			q.append("OFIC_REGISTRAL OFI , ASIENTO ASI, TM_TIPO_VEHI TV, TM_TIPO_COMB TC ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(", GRUPO_LIBRO_ACTO GLA ");
			}
			q.append("WHERE ");
			if(!inputPMasivaRelacionalBean.getCodTipoVehiculo().equals(""))
			{
				q.append("VEH.COD_TIPO_VEHI='").append(inputPMasivaRelacionalBean.getCodTipoVehiculo()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getCodTipoCombustible().equals(""))
			{
				q.append("VEH.COD_TIPO_COMB='").append(inputPMasivaRelacionalBean.getCodTipoCombustible()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getColorVeh().equals(""))
			{
				q.append("VEH.DESC_COLOR_01 LIKE '").append(inputPMasivaRelacionalBean.getColorVeh()).append("%' AND ");
			}
			q.append("VEH.COD_MARCA = MAR.COD_MARCA AND ");
			if(!inputPMasivaRelacionalBean.getMarca().equals(""))
			{
				q.append("MAR.DESCRIPCION LIKE '").append(inputPMasivaRelacionalBean.getMarca()).append("%' AND ");
			}
			q.append("VEH.COD_MODELO = MO.COD_MODELO AND ");
			if(!inputPMasivaRelacionalBean.getModelo().equals(""))
			{
				q.append("MO.DESCRIPCION LIKE '").append(inputPMasivaRelacionalBean.getModelo()).append("%' AND ");
			}
			if(!inputPMasivaRelacionalBean.getAnoFabricacionDesde().equals(""))
			{
				q.append("VEH.ANO_FABRIC >= '").append(inputPMasivaRelacionalBean.getAnoFabricacionDesde()).append("' AND ");	
			}
			if(!inputPMasivaRelacionalBean.getAnoFabricacionHasta().equals(""))
			{
				q.append("VEH.ANO_FABRIC <= '").append(inputPMasivaRelacionalBean.getAnoFabricacionHasta()).append("' AND ");
			}
			if(!inputPMasivaRelacionalBean.getFechaInscripcionDesde().equals(""))
			{
				q.append("VEH.TS_INSCRIP >= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionDesde()).append("','dd/mm/yyyy') AND "); 
			}
			else if(!inputPMasivaRelacionalBean.getFechaInscripcionHasta().equals(""))
			{
				q.append("VEH.TS_INSCRIP <= TO_DATE('").append(inputPMasivaRelacionalBean.getFechaInscripcionHasta()).append("','dd/mm/yyyy') AND ");
			}
			q.append("PART.REFNUM_PART = VEH.REFNUM_PART AND ");
			q.append("ASI.REFNUM_PART = VEH.REFNUM_PART AND ");
			q.append("PART.REG_PUB_ID = OFI.REG_PUB_ID AND ");
			q.append("PART.OFIC_REG_ID = OFI.OFIC_REG_ID AND ");
			q.append("OFI.REG_PUB_ID = REG.REG_PUB_ID AND ");
			q.append("VEH.COD_TIPO_VEHI = TV.COD_TIPO_VEHI AND ");
			q.append("VEH.COD_TIPO_COMB = TC.COD_TIPO_COMB AND ");	
			if (inputPMasivaRelacionalBean.getSedesElegidas().length==1)
			{
				 q.append("PART.REG_PUB_ID = '").append(inputPMasivaRelacionalBean.getSedesElegidas()[0]).append("' AND ");
			}
			if (inputPMasivaRelacionalBean.getSedesElegidas().length>=2 && inputPMasivaRelacionalBean.getSedesElegidas().length<=12)
			{
				q.append("PART.REG_PUB_ID IN ").append(inputPMasivaRelacionalBean.getSedesSQLString()).append(" AND ");
			}
			q.append("ASI.REFNUM_PART = PART.REFNUM_PART ");
			if(!inputPMasivaRelacionalBean.getCodTipoActoCausal().equals("T"))
			{
				q.append(" AND ASI.COD_ACTO = GLA.COD_ACTO AND ");
				q.append("GLA.TIP_ACTO = '").append(inputPMasivaRelacionalBean.getTipActo()).append("' AND ");
				q.append("GLA.TIP_LIBRO = '").append(inputPMasivaRelacionalBean.getTipLibro()).append("' ");
			}
			if(inputPMasivaRelacionalBean.getOrdenamiento()==null || inputPMasivaRelacionalBean.getOrdenamiento().equals(""))
			{
				q.append("ORDER BY VEH.TS_INSCRIP DESC");
			}else
			{
				q.append("ORDER BY ").append(inputPMasivaRelacionalBean.getOrdenamiento()).append(" DESC");
			}
			
			
			if (isTrace(this)) System.out.println("___verquery_busquedaPMR__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			if (inputPMasivaRelacionalBean.getSalto()>1){
 				rset.absolute(propiedades.getLineasPorPag() * (inputPMasivaRelacionalBean.getSalto() - 1));
 			}
 			boolean b = rset.next();
 			if(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null)
 			{
				if(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setCantidadRegistros(rset.getString(1));
					resultado.add(outputBean);
				}
				
 			}else
 			{
 				inputPMasivaRelacionalBean.setCadenaQuery(q.toString());
 				while(b)
				{
					OutputPMasivaRelacionalBean outputBean = new OutputPMasivaRelacionalBean();
					outputBean.setSigla(rset.getString(1));
					outputBean.setNombreoficina(rset.getString(2));
					outputBean.setNumeroPartida(rset.getString(3));
					outputBean.setNumeroplaca(rset.getString(4));
					outputBean.setNumeroserie(rset.getString(5));
					outputBean.setNumeromotor(rset.getString(6));
					outputBean.setAnofabricacion(rset.getString(7));
					outputBean.setColor(rset.getString(8));
					outputBean.setMarca(rset.getString(9));
					outputBean.setModelo(rset.getString(10));
					mapProp = recuperarPropietario("V",rset.getString(11));
					outputBean.setRefnumpart(rset.getString(11));
					outputBean.setPropietario(""+mapProp.get("nombre"));
					
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
					if (rset.getDate(13) != null){
						outputBean.setFechaInscripcionDesde(simpleDateFormat.format(rset.getDate(13)));
					}else{
						outputBean.setFechaInscripcionDesde("");
					}
					outputBean.setEstadoVehiculo(rset.getString(14));
					outputBean.setRegpubid(rset.getString(15));
					outputBean.setOficregid(rset.getString(16));
					outputBean.setAreaid(rset.getString(17));
					resultado.add(outputBean);
					conta++;
					b = rset.next();
					if(!(inputPMasivaRelacionalBean.getFlagRespuesta()==null & inputPMasivaRelacionalBean.getFlagPagineo()==null))
					{
						if (conta==propiedades.getLineasPorPag())
						{
							if(b==true){	
								haySiguiente = true;
							}
							break;
						}
					}
				}
 			}
 			
			q2.append("SELECT TV.DESCRIPCION "); 
 			q2.append("FROM TM_TIPO_VEHI TV ");
 			q2.append("WHERE TV.COD_TIPO_VEHI = '").append(inputPMasivaRelacionalBean.getCodTipoVehiculo()).append("' ");
 			
 			stmt2   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			rset2   = stmt2.executeQuery(q2.toString());
 			
 			if(rset2.next())
 			{
 				inputPMasivaRelacionalBean.setDescripcionTipoVehiculo(rset2.getString(1));
 			}
 			if(!inputPMasivaRelacionalBean.getCodTipoCombustible().equals(""))
 			{
	 			DboTmTipoComb dboCom = new DboTmTipoComb(dbConn);
	 			dboCom.setFieldsToRetrieve(dboCom.CAMPO_DESCRIPCION);
	 			dboCom.setField(dboCom.CAMPO_COD_TIPO_COMB, inputPMasivaRelacionalBean.getCodTipoCombustible());
	 			if(dboCom.find())
	 			{
	 				inputPMasivaRelacionalBean.setDescripcionTipoComb(dboCom.getField(dboCom.CAMPO_DESCRIPCION));
	 			}
 			}
			map.put("resultado",resultado);
			
			if (inputPMasivaRelacionalBean.getFlagPagineo()==null){
				inputPMasivaRelacionalBean.setCantidadRegistros(String.valueOf(resultado.size()));
			}else{
				inputPMasivaRelacionalBean.setCantidadRegistros(inputPMasivaRelacionalBean.getCantidadRegistros());
			}

			//calcular numero para boton "retroceder pagina"		
			if (inputPMasivaRelacionalBean.getSalto()==1){
				inputPMasivaRelacionalBean.setPagAnterior(-1);
			}else{
				inputPMasivaRelacionalBean.setPagAnterior(inputPMasivaRelacionalBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				inputPMasivaRelacionalBean.setPagSiguiente(-1);
			}else{
				inputPMasivaRelacionalBean.setPagSiguiente(inputPMasivaRelacionalBean.getSalto()+1);
			}

			//calcular regs del x al y
			int del = ((inputPMasivaRelacionalBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			inputPMasivaRelacionalBean.setNdel(del);
			inputPMasivaRelacionalBean.setNal(al);
			
			//map.put("pagineo", inputPMasivaRelacionalBean);
			
 			return map;
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
	}
	
}
