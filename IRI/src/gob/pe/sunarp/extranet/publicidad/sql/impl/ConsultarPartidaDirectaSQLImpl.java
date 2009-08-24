package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.ibm.ObjectQuery.crud.util.Array;
import com.ibm.ws390.tx.BBOT_MinorCodes;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboFicha;
import gob.pe.sunarp.extranet.dbobj.DboOficRegistral;
import gob.pe.sunarp.extranet.dbobj.DboPartida;
import gob.pe.sunarp.extranet.dbobj.DboRegisPublico;
import gob.pe.sunarp.extranet.dbobj.DboTmLibro;
import gob.pe.sunarp.extranet.dbobj.DboTmMarcaVehi;
import gob.pe.sunarp.extranet.dbobj.DboTmModeloVehi;
import gob.pe.sunarp.extranet.dbobj.DboTomoFolio;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.ActoBean;
import gob.pe.sunarp.extranet.publicidad.bean.AsientoRMCBean;
import gob.pe.sunarp.extranet.publicidad.bean.BienBean;
import gob.pe.sunarp.extranet.publicidad.bean.DocumentoFuncionarioBean;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqDirectaBean;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusquedaBean;
import gob.pe.sunarp.extranet.publicidad.bean.ParticipanteBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.publicidad.bean.TituloBean;
import gob.pe.sunarp.extranet.publicidad.bean.TituloPendienteBean;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class ConsultarPartidaDirectaSQLImpl extends SQLImpl implements ConsultarPartidaDirectaSQL, Constantes{

	private Connection conn;
	private DBConnection dbConn;
	
	public ConsultarPartidaDirectaSQLImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
	
	public PartidaBean busquedaDirectaPorRefNumPartRMC(String refNumPart)throws SQLException, DBException{
		
		PartidaBean partidaBean = null;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
		
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT PARTIDA.ESTADO as estado,  ");
			q.append("        REGIS_PUBLICO.SIGLAS  as siglas, ");
			q.append("        OFIC_REGISTRAL.NOMBRE as nombre, ");
			q.append("        PARTIDA.area_reg_id   as area_reg_id, ");
			q.append("        PARTIDA.REFNUM_PART   as refnum_part, ");
			q.append("        PARTIDA.NUM_PARTIDA   as num_partida, ");
			q.append("        PARTIDA.COD_LIBRO     as cod_libro, ");
			q.append("        AREA.NOMBRE           as descripcion, ");
			q.append("        OFIC_REGISTRAL.OFIC_REG_ID as ofic_reg_id, ");
			q.append("        OFIC_REGISTRAL.REG_PUB_ID as reg_pub_id ");
			q.append("   FROM FICHA, ");
			q.append("        PARTIDA, ");
			q.append("        REGIS_PUBLICO, ");
			q.append("        OFIC_REGISTRAL, ");   
			q.append("        TM_AREA_REGISTRAL area "); 
			q.append("  WHERE REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID "); 
			q.append("    AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID "); 
			q.append("    AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q.append("    AND FICHA.REFNUM_PART(+) = PARTIDA.REFNUM_PART ");
			q.append("    and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");  
			q.append("    and PARTIDA.Refnum_Part = "+refNumPart+"");
		       
			if (isTrace(this)) System.out.println("___verquery_busquedaPorRefNumPart__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				partidaBean = new PartidaBean();
				
				partidaBean.setEstado(rset.getString("estado"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setCodLibro(rset.getString("cod_libro"));
				partidaBean.setAreaRegistralDescripcion(rset.getString("descripcion"));
				partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
				partidaBean.setRegPubId(rset.getString("reg_pub_id"));
							
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return partidaBean;
	}
	
	public FormOutputBuscarPartida busquedaDirectaPorFichaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			
			int conteo=0;
			if (inputBusqDirectaBean.getFlagPagineo()==false)
			{
				conteo = contarBusquedaDirectaPorFichaRMC(inputBusqDirectaBean);
				
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}else{
				conteo = Integer.parseInt(inputBusqDirectaBean.getCantidad());
			}
			
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT  ");
			q.append(" PARTIDA.ESTADO as estado, ");
			q.append(" REGIS_PUBLICO.SIGLAS as siglas, ");
			q.append(" OFIC_REGISTRAL.NOMBRE as nombre, ");
			q.append(" PARTIDA.area_reg_id as area_reg_id, ");
			q.append(" PARTIDA.REFNUM_PART as refnum_part, ");
			q.append(" PARTIDA.NUM_PARTIDA as num_partida, ");
			q.append(" PARTIDA.COD_LIBRO as cod_libro, ");
			q.append(" AREA.NOMBRE as descripcion, ");
			q.append(" OFIC_REGISTRAL.OFIC_REG_ID as ofic_reg_id, ");
			q.append(" OFIC_REGISTRAL.REG_PUB_ID as reg_pub_id ");
			q.append(" FROM FICHA,PARTIDA, REGIS_PUBLICO,OFIC_REGISTRAL,grupo_libro_area_det glad, grupo_libro_area gla, TM_AREA_REGISTRAL area ");
			q.append(" WHERE ");
			q.append(" FICHA.FICHA = '").append(inputBusqDirectaBean.getNumeroFicha()).append("' ");
			q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q.append(" AND FICHA.REFNUM_PART(+) = PARTIDA.REFNUM_PART ");
			q.append(" and PARTIDA.REG_PUB_ID ='").append(inputBusqDirectaBean.getRegPubId()).append("' ");
			q.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBusqDirectaBean.getOficRegId()).append("' ");
			q.append(" and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID ");
			q.append(" and partida.cod_libro = glad.cod_libro  ");
			q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append(" and gla.cod_grupo_libro_area ='").append(inputBusqDirectaBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(q);
			q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaDirectaPorFichaRMC__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			
			/**
			 * @autor dbravo
			 * @descripcion Si el medio es un controller realiza la paginación caso contrario, 
			 * 				si es un WebService, no se realiza la paginación
			 * @inicio
			 */
			if(medioDeAcceso==MEDIO_CONTROLLER){
				if (inputBusqDirectaBean.getSalto()>1){
					rset.absolute(propiedades.getLineasPorPag() * (inputBusqDirectaBean.getSalto() - 1));
				}
			}
			/**
			 * @final
			 */
			
			boolean b = rset.next();
			
			ArrayList resultado = new ArrayList();
			
			DboFicha dboFicha = new DboFicha(this.dbConn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(this.dbConn);
			DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
			DboPartida dboPartida = new DboPartida(this.dbConn);
			
			StringBuffer sb = new StringBuffer();
	
			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				String descripcionAreaRegistral = rset.getString("descripcion");
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setCodLibro(codLibro);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				partidaBean.setEstado(rset.getString("estado"));
				partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
				partidaBean.setRegPubId(rset.getString("reg_pub_id"));
				
				//dbravo: Recupera el refnum_part de la partida migrada si existiera
				String partidaMigrada = obtenerRefNumParAntiguo(refNumPart);
				String partidaNueva = null;
				double paginas = 0;
				
				//dbravo: Si no existe una partida migrada, Recupera, si existiera, el refnum_part de la partida nueva
				if(partidaMigrada==null){
					partidaNueva   = obtenerRefNumParNuevo(refNumPart);
				}
				
				//dbravo: Si existe una partida nueva significa que la partida con la que se realizo la busqueda era una partida migrada,
				//        por lo que se procede a buscar los datos de la partida a Actual.
				if(partidaNueva!=null){
					
					partidaBean = busquedaDirectaPorRefNumPartRMC(partidaNueva);
					partidaMigrada = refNumPart;
					refNumPart = partidaNueva;
					codLibro = partidaBean.getCodLibro();

				}
				
				//dbravo: Si existe parida migrada hay que recuperar la descripcion del libro migrado y el numero de paginas
				if(partidaMigrada!=null){
					dboPartida.clearAll();
					dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
					dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
					dboPartida.setField(DboPartida.CAMPO_REFNUM_PART,partidaMigrada);
					if(dboPartida.find()){
						
						partidaBean.setNumPartidaMigrado(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA));
						dboTmLibro.clearAll();
						dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
						dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,dboPartida.getField(DboPartida.CAMPO_COD_LIBRO));
						if(dboTmLibro.find()){
							partidaBean.setLibroDescripcionMigrado(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));
						}
					}
					paginas = paginas + numeroPaginas(partidaMigrada);
				}
				
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboFicha.CAMPO_FICHA).append("|");
				sb.append(DboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(DboFicha.CAMPO_REFNUM_PART, refNumPart);
				
				if (dboFicha.find() == true) {
					partidaBean.setFichaId(dboFicha.getField(DboFicha.CAMPO_FICHA));
					String bis = dboFicha.getField(DboFicha.CAMPO_FICHA_BIS);
					int nbis = 0;
					try {
						nbis = Integer.parseInt(bis);
					}
					catch (NumberFormatException n)
					{
						nbis =0;
					}
					if (nbis>=1)
						partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
				}	
						
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(DboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			
				if (dboTomoFolio.find() == true) 
				{
					partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
					partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

					String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
					String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

					if (bist.trim().length() > 0){
							partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
					}

					if (bisf.trim().length() > 0){
							partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
					}
					
					//quitar el caracter "9" del inicio del tomoid
					if (partidaBean.getTomoId().length()>0)
					{
						if (partidaBean.getTomoId().startsWith("9"))
							{
								String ntomo = partidaBean.getTomoId().substring(1);
								partidaBean.setTomoId(ntomo);
							}
					}						
				}
	
				//descripcion libro
				dboTmLibro.clearAll();
				dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
				dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,codLibro);
				
				if (dboTmLibro.find() == true){
					partidaBean.setLibroDescripcion(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));								
				}
				
				paginas = paginas + numeroPaginas(refNumPart);
				partidaBean.setNumeroPaginas(Double.toString(paginas));
				
				resultado.add(partidaBean);
				
				conta++;
				b = rset.next();
				
				/**
				 * @autor dbravo
				 * @descripcion Si el medio es un controller realiza la paginación caso contrario, 
				 * 				si es un WebService, no se realiza la paginación
				 * @inicio
				 */
				if(medioDeAcceso==MEDIO_CONTROLLER){
				
					if (conta==propiedades.getLineasPorPag())
					{
						if(b==true){	
							haySiguiente = true;
						}
						break;
					}
				}
				/**
				 * @final
				 */
				
			}
	
			output.setResultado(resultado);
			
			if (inputBusqDirectaBean.getFlagPagineo()==false){
				output.setCantidadRegistros(String.valueOf(conteo));
			}else{
				output.setCantidadRegistros(inputBusqDirectaBean.getCantidad());
			}
	
			//calcular numero para boton "retroceder pagina"		
			if (inputBusqDirectaBean.getSalto()==1){
				output.setPagAnterior(-1);
			}else{
				output.setPagAnterior(inputBusqDirectaBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				output.setPagSiguiente(-1);
			}else{
				output.setPagSiguiente(inputBusqDirectaBean.getSalto()+1);
			}
	
			//calcular regs del x al y
			int del = ((inputBusqDirectaBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);

		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return output;
		
	}
	
	public int numeroPaginas(String refNumPart) throws SQLException{
		
		int numeroPaginas=0;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			StringBuffer q  = new StringBuffer();
			
			q.append(" select sum(sub) AS paginas " +
					 "   from ( " +
					 "	       select sum(b.numpag) AS sub " +
					 "           from user1.partida a, user1.asiento b " +
					 "          where a.refnum_part = b.refnum_part " +
					 "            and a.refnum_part = "+refNumPart+" " +
					 "  UNION ALL " +
					 "         select sum(c.numpag) AS sub " +
					 "           from user1.partida a, user1.ficha c " +
					 "          where a.refnum_part = c.refnum_part " +
					 "			  and a.refnum_part = "+refNumPart+"" +
					 "  UNION ALL " +
					 "         select count(*) AS sub " +
					 "           from user1.partida a, user1.tomo_folio d " +
					 "          where a.refnum_part = d.refnum_part " +
					 "            and a.refnum_part = "+refNumPart+") ");
	
			if (isTrace(this)) System.out.println("___verquery_numeroPaginas__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			boolean bc = rset.next();
			if(bc){
				numeroPaginas = rset.getInt(1);
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return numeroPaginas;
			
	}
	
	public String obtenerRefNumParAntiguo(String refNumParNuevo) throws SQLException{
		
		String refNumParAntiguo=null;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT R.REFNUM_PART_ANTIGUO " +
					 "   FROM PARTIDA_RMC R " +
					 "	WHERE R.REFNUM_PART_NUEVO = "+refNumParNuevo+" ");
	
			if (isTrace(this)) System.out.println("___verquery_obtenerRefNumParAntiguo__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			boolean bc = rset.next();
			if(bc){
				refNumParAntiguo = rset.getString(1);
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return refNumParAntiguo;
		
	}
	
	public String obtenerRefNumParNuevo(String refNumParAntiguo) throws SQLException{
		
		String refNumParNuevo=null;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT R.REFNUM_PART_NUEVO " +
					 "   FROM PARTIDA_RMC R " +
					 "	WHERE R.REFNUM_PART_ANTIGUO = "+refNumParAntiguo+" ");
	
			if (isTrace(this)) System.out.println("___verquery_obtenerRefNumParNuevo__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			boolean bc = rset.next();
			if(bc){
				refNumParNuevo = rset.getString(1);
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return refNumParNuevo;
		
	}
	
	private void appendCondicionEstadoPartida(StringBuffer q) {

		q.append(" and PARTIDA.ESTADO != '2' ");
		
	}
	
	private int contarBusquedaDirectaPorPartidaRMC(InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException{
		
		int conteo=0;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT count(partida.refnum_part) " +
					" FROM REGIS_PUBLICO, " +
					" OFIC_REGISTRAL, " +
					" PARTIDA, " +
					" grupo_libro_area gla, " +
					" grupo_libro_area_det glad " +
					" WHERE PARTIDA.NUM_PARTIDA = ").append(inputBusqDirectaBean.getNumeroPartida()).append(
					"   AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					"   AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					"   AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID " +
					"   and PARTIDA.REG_PUB_ID = '").append(inputBusqDirectaBean.getRegPubId()).append("' " +
					"   and PARTIDA.OFIC_REG_ID = '").append(inputBusqDirectaBean.getOficRegId()).append("' " +
					"   and partida.cod_libro = glad.cod_libro " +
					"   and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area " +
					"   and gla.cod_grupo_libro_area = '").append(inputBusqDirectaBean.getCodGrupoLibroArea()).append("'");
			
			appendCondicionEstadoPartida(q);
		        
			if (isTrace(this)) System.out.println("___verquery_contarBusquedaDirectaPorPartidaRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			boolean bc = rset.next();
			if(bc){
				conteo = rset.getInt(1);
			}
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return conteo;
		
	}
	
	
	private int contarBusquedaDirectaPorTomoFolioRMC(InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException{
		
		int conteo=0;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT count(partida.refnum_part) " +
					"   FROM TOMO_FOLIO, " +
					"        PARTIDA, " +
					"        OFIC_REGISTRAL, " +
					"        REGIS_PUBLICO, " +
					"        grupo_libro_area gla, " +
					"        grupo_libro_area_det glad " +
					"  WHERE TOMO_FOLIO.REFNUM_PART = PARTIDA.REFNUM_PART " +
					"    and partida.cod_libro = glad.cod_libro " +
					"    and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area " +
					"    AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					"    AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					"    AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID " +
					"    AND PARTIDA.COD_LIBRO = glad.cod_libro " +
					"    and gla.cod_grupo_libro_area = '").append(inputBusqDirectaBean.getCodGrupoLibroArea()).append("' " +
					"	 and PARTIDA.COD_LIBRO = '").append(inputBusqDirectaBean.getLibro()).append("' " +
					"    and PARTIDA.REG_PUB_ID = '").append(inputBusqDirectaBean.getRegPubId()).append("' " +
					"    and PARTIDA.OFIC_REG_ID = '").append(inputBusqDirectaBean.getOficRegId()).append("' " +
					"    and TOMO_FOLIO.NU_TOMO = '").append(inputBusqDirectaBean.getTomo()).append("' " +
					"    AND TOMO_FOLIO.NU_FOJA = '").append(inputBusqDirectaBean.getFolio()).append("' ");
			appendCondicionEstadoPartida(q);
		        
			if (isTrace(this)) System.out.println("___verquery_contarBusquedaDirectaPorTomoFolioRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			boolean bc = rset.next();
			if(bc){
				conteo = rset.getInt(1);
			}
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return conteo;
		
	}
	
	private int contarBusquedaDirectaPorFichaRMC(InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException{
		
		int conteo=0;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			
			StringBuffer q = new StringBuffer();
	
			q.append(" SELECT ");
			q.append(" count(partida.refnum_part) ");
			q.append(" FROM FICHA,PARTIDA, REGIS_PUBLICO,OFIC_REGISTRAL, grupo_libro_area_det glad, grupo_libro_area gla ");
			q.append(" WHERE ");
			q.append(" FICHA.FICHA = '").append(inputBusqDirectaBean.getNumeroFicha()).append("' ");
			q.append(" AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID ");
			q.append(" AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID ");
			q.append(" AND FICHA.REFNUM_PART (+)= PARTIDA.REFNUM_PART ");
			q.append(" and PARTIDA.REG_PUB_ID ='").append(inputBusqDirectaBean.getRegPubId()).append("' ");
			q.append(" and PARTIDA.OFIC_REG_ID = '").append(inputBusqDirectaBean.getOficRegId()).append("' ");
			q.append(" and partida.cod_libro = glad.cod_libro  ");
			q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append(" and gla.cod_grupo_libro_area ='").append(inputBusqDirectaBean.getCodGrupoLibroArea()).append("' ");
			appendCondicionEstadoPartida(q);
	
			if (isTrace(this)) System.out.println("___verquery_contarBusquedaDirectaPorFichaRMC_A__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			boolean bc = rset.next();
			if(bc){
				conteo = rset.getInt(1);
			}
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return conteo;
		
	}
	
	public FormOutputBuscarPartida busquedaDirectaPorPartidaRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			
			int conteo=0;
			if (inputBusqDirectaBean.getFlagPagineo()==false)
			{
				conteo = contarBusquedaDirectaPorPartidaRMC(inputBusqDirectaBean);
				
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}else{
				conteo = Integer.parseInt(inputBusqDirectaBean.getCantidad());
			}
			
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT PARTIDA.ESTADO as estado, " +
					 "        REGIS_PUBLICO.SIGLAS  as siglas, " +
					 "        OFIC_REGISTRAL.NOMBRE as nombre, " +
					 "        PARTIDA.area_reg_id   as area_reg_id, " +
					 "        PARTIDA.REFNUM_PART   as refnum_part, " +
					 "        PARTIDA.NUM_PARTIDA   as num_partida, " +
					 "        PARTIDA.COD_LIBRO     as cod_libro, " +
					 "        AREA.NOMBRE           as descripcion," +
					 "        OFIC_REGISTRAL.OFIC_REG_ID as ofic_reg_id, " +
					 "		  OFIC_REGISTRAL.REG_PUB_ID as reg_pub_id " +
					 "   FROM REGIS_PUBLICO, " +
					 "        OFIC_REGISTRAL, " +
					 "        PARTIDA, " +
					 "        grupo_libro_area gla, " +
					 "        grupo_libro_area_det glad, " +
					 "        TM_AREA_REGISTRAL area" +
					 "  WHERE REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					 "    AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					 "    AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID " +
					 "    and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID " +
					 "    and partida.cod_libro = glad.cod_libro " +
					 "    and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area " +
					 "    and PARTIDA.NUM_PARTIDA = '").append(inputBusqDirectaBean.getNumeroPartida()).append("' " +
					 "    and gla.cod_grupo_libro_area = '").append(inputBusqDirectaBean.getCodGrupoLibroArea()).append("' " +
					 "    and PARTIDA.REG_PUB_ID = '").append(inputBusqDirectaBean.getRegPubId()).append("' " +
					 "    and PARTIDA.OFIC_REG_ID = '").append(inputBusqDirectaBean.getOficRegId()).append("' ");
			
			appendCondicionEstadoPartida(q);
			
			q.append("    ORDER BY REGIS_PUBLICO.SIGLAS, " +
					 "             OFIC_REGISTRAL.NOMBRE, " +
					 "             PARTIDA.NUM_PARTIDA ");
			
			if (isTrace(this)) System.out.println("___verquery_busquedaDirectaPorPartidaRMC__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			/**
			 * @autor mgarate
			 * @descripcion Si el medio es un controller realiza la paginación caso contrario, 
			 * 				si es un WebService, no se realiza la paginación
			 * @inicio
			 */
			if(medioDeAcceso==MEDIO_CONTROLLER)
			{
				if (inputBusqDirectaBean.getSalto()>1)
				{
					rset.absolute(propiedades.getLineasPorPag() * (inputBusqDirectaBean.getSalto() - 1));
				}
			}
			/**
			 * @final
			 */
			boolean b = rset.next();
			
			ArrayList resultado = new ArrayList();
			
			DboFicha dboFicha = new DboFicha(this.dbConn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(this.dbConn);
			DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
			DboPartida dboPartida = new DboPartida(this.dbConn);
			
			StringBuffer sb = new StringBuffer();
	
			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				String descripcionAreaRegistral = rset.getString("descripcion");
				
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setCodLibro(codLibro);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				partidaBean.setEstado(rset.getString("estado"));
				partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
				partidaBean.setRegPubId(rset.getString("reg_pub_id"));
				
				//dbravo: Recupera el refnum_part de la partida migrada si existiera
				String partidaMigrada = obtenerRefNumParAntiguo(refNumPart);
				String partidaNueva = null;
				double paginas = 0;
				
				//dbravo: Si no existe una partida migrada, Recupera, si existiera, el refnum_part de la partida nueva
				if(partidaMigrada==null){
					partidaNueva   = obtenerRefNumParNuevo(refNumPart);
				}
				
				//dbravo: Si existe una partida nueva significa que la partida con la que se realizo la busqueda era una partida migrada,
				//        por lo que se procede a buscar los datos de la partida a Actual.
				if(partidaNueva!=null){
					
					partidaBean = busquedaDirectaPorRefNumPartRMC(partidaNueva);
					partidaMigrada = refNumPart;
					refNumPart = partidaNueva;
					codLibro = partidaBean.getCodLibro();

				}
				
				//dbravo: Si existe parida migrada hay que recuperar la descripcion del libro migrado y el numero de paginas
				if(partidaMigrada!=null){
					dboPartida.clearAll();
					dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
					dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
					dboPartida.setField(DboPartida.CAMPO_REFNUM_PART,partidaMigrada);
					if(dboPartida.find()){
						
						partidaBean.setNumPartidaMigrado(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA));
						dboTmLibro.clearAll();
						dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
						dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,dboPartida.getField(DboPartida.CAMPO_COD_LIBRO));
						if(dboTmLibro.find()){
							partidaBean.setLibroDescripcionMigrado(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));
						}
					}
					paginas = paginas + numeroPaginas(partidaMigrada);
				}
				
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboFicha.CAMPO_FICHA).append("|");
				sb.append(DboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(DboFicha.CAMPO_REFNUM_PART, refNumPart);
				
				if (dboFicha.find() == true) {
					partidaBean.setFichaId(dboFicha.getField(DboFicha.CAMPO_FICHA));
					String bis = dboFicha.getField(DboFicha.CAMPO_FICHA_BIS);
					int nbis = 0;
					try {
						nbis = Integer.parseInt(bis);
					}
					catch (NumberFormatException n)
					{
						nbis =0;
					}
					if (nbis>=1)
						partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
				}	
						
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(DboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			
				if (dboTomoFolio.find() == true) 
				{
					partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
					partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

					String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
					String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

					if (bist.trim().length() > 0){
							partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
					}

					if (bisf.trim().length() > 0){
							partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
					}
					
					//quitar el caracter "9" del inicio del tomoid
					if (partidaBean.getTomoId().length()>0)
					{
						if (partidaBean.getTomoId().startsWith("9"))
							{
								String ntomo = partidaBean.getTomoId().substring(1);
								partidaBean.setTomoId(ntomo);
							}
					}						
				}
	
				//descripcion libro
				dboTmLibro.clearAll();
				dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
				dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,codLibro);
				
				if (dboTmLibro.find() == true){
					partidaBean.setLibroDescripcion(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));								
				}
				
				paginas = paginas + numeroPaginas(refNumPart);
				partidaBean.setNumeroPaginas(Double.toString(paginas));
				
				resultado.add(partidaBean);
				
				conta++;
				b = rset.next();
				/**
				 * @autor mgarate
				 * @descripcion Si el medio es un controller realiza la paginación caso contrario, 
				 * 				si es un WebService, no se realiza la paginación
				 * @inicio
				 */
				if(medioDeAcceso==MEDIO_CONTROLLER)
				{
					if (conta==propiedades.getLineasPorPag())
					{
						if(b==true){	
							haySiguiente = true;
						}
						break;
					}	
				}
				/**
				 * @final
				 */
			}
	
			output.setResultado(resultado);
			
			if (inputBusqDirectaBean.getFlagPagineo()==false){
				output.setCantidadRegistros(String.valueOf(conteo));
			}else{
				output.setCantidadRegistros(inputBusqDirectaBean.getCantidad());
			}
	
			//calcular numero para boton "retroceder pagina"		
			if (inputBusqDirectaBean.getSalto()==1){
				output.setPagAnterior(-1);
			}else{
				output.setPagAnterior(inputBusqDirectaBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				output.setPagSiguiente(-1);
			}else{
				output.setPagSiguiente(inputBusqDirectaBean.getSalto()+1);
			}
	
			//calcular regs del x al y
			int del = ((inputBusqDirectaBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);

		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return output;
		
	}

	public FormOutputBuscarPartida busquedaDirectaPorTomoFolioRMC(int medioDeAcceso, InputBusqDirectaBean inputBusqDirectaBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			
			Propiedades propiedades = Propiedades.getInstance();
			
			int conteo=0;
			if (inputBusqDirectaBean.getFlagPagineo()==false)
			{
				conteo = contarBusquedaDirectaPorTomoFolioRMC(inputBusqDirectaBean);
				
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}else{
				conteo = Integer.parseInt(inputBusqDirectaBean.getCantidad());
			}
			
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT PARTIDA.ESTADO        as estado, " +
					 "        REGIS_PUBLICO.SIGLAS  as siglas, " +
					 "        OFIC_REGISTRAL.NOMBRE as nombre, " +
					 "		  PARTIDA.area_reg_id   as area_reg_id, " +
					 "        area.NOMBRE           as descripcion," +
					 "        PARTIDA.REFNUM_PART   as refnum_part, " +
					 "        PARTIDA.NUM_PARTIDA   as num_partida, " +
					 "        PARTIDA.COD_LIBRO     as cod_libro, " +
					 "        PARTIDA.REG_PUB_ID    as reg_pub_id, " +
					 "        PARTIDA.OFIC_REG_ID   as ofic_reg_id " +
					 "   FROM TOMO_FOLIO, " +
					 "        PARTIDA, " +
					 "        OFIC_REGISTRAL, " +
					 "        REGIS_PUBLICO, " +
					 "        grupo_libro_area gla, " +
					 "        grupo_libro_area_det glad, " +
					 "        TM_AREA_REGISTRAL area " +
					 "  WHERE TOMO_FOLIO.REFNUM_PART = PARTIDA.REFNUM_PART " +
					 "    and PARTIDA.AREA_REG_ID = AREA.AREA_REG_ID" +
					 "    and partida.cod_libro = glad.cod_libro " +
					 "    and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area " +
					 "    AND REGIS_PUBLICO.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					 "    AND OFIC_REGISTRAL.REG_PUB_ID = PARTIDA.REG_PUB_ID " +
					 "    AND OFIC_REGISTRAL.OFIC_REG_ID = PARTIDA.OFIC_REG_ID " +
					 "    AND PARTIDA.COD_LIBRO = glad.cod_libro " +
					 "    and gla.cod_grupo_libro_area = '").append(inputBusqDirectaBean.getCodGrupoLibroArea()).append("' " +
					 "	  AND PARTIDA.COD_LIBRO = '").append(inputBusqDirectaBean.getLibro()).append("' " +
					 "    and PARTIDA.REG_PUB_ID = '").append(inputBusqDirectaBean.getRegPubId()).append("' " +
					 "    and PARTIDA.OFIC_REG_ID = '").append(inputBusqDirectaBean.getOficRegId()).append("' " +
					 "    and TOMO_FOLIO.NU_TOMO = '").append(inputBusqDirectaBean.getTomo()).append("' " +
					 "    AND TOMO_FOLIO.NU_FOJA = '").append(inputBusqDirectaBean.getFolio()).append("' ");
			appendCondicionEstadoPartida(q);		
			q.append(" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA ");
			
			
			if (isTrace(this)) System.out.println("___verquery_busquedaDirectaPorTomoFolioRMC__"+q.toString());
			
			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(propiedades.getLineasPorPag());
			rset   = stmt.executeQuery(q.toString());
			
			/**
			 * @autor mgarate
			 * @descripcion Si el medio es un controller realiza la paginación caso contrario, 
			 * 				si es un WebService, no se realiza la paginación
			 * @inicio
			 */
			if(medioDeAcceso==MEDIO_CONTROLLER)
			{
				if (inputBusqDirectaBean.getSalto()>1)
				{
					rset.absolute(propiedades.getLineasPorPag() * (inputBusqDirectaBean.getSalto() - 1));
				}
			}
			/**
			 * @final
			 */
	
			boolean b = rset.next();
			
			ArrayList resultado = new ArrayList();
			
			DboFicha dboFicha = new DboFicha(this.dbConn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(this.dbConn);
			DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
			DboPartida dboPartida = new DboPartida(this.dbConn);
			
			StringBuffer sb = new StringBuffer();
	
			int conta=0;
			boolean haySiguiente = false;
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				String descripcionAreaRegistral = rset.getString("descripcion");
				
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setCodLibro(codLibro);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				partidaBean.setEstado(rset.getString("estado"));
				partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
				partidaBean.setRegPubId(rset.getString("reg_pub_id"));
				
				//dbravo: Recupera el refnum_part de la partida migrada si existiera
				String partidaMigrada = obtenerRefNumParAntiguo(refNumPart);
				String partidaNueva = null;
				double paginas = 0;
				
				//dbravo: Si no existe una partida migrada, Recupera, si existiera, el refnum_part de la partida nueva
				if(partidaMigrada==null){
					partidaNueva   = obtenerRefNumParNuevo(refNumPart);
				}
				
				//dbravo: Si existe una partida nueva significa que la partida con la que se realizo la busqueda era una partida migrada,
				//        por lo que se procede a buscar los datos de la partida a Actual.
				if(partidaNueva!=null){
					
					partidaBean = busquedaDirectaPorRefNumPartRMC(partidaNueva);
					partidaMigrada = refNumPart;
					refNumPart = partidaNueva;
					codLibro = partidaBean.getCodLibro();

				}
				
				//dbravo: Si existe parida migrada hay que recuperar la descripcion del libro migrado y el numero de paginas
				if(partidaMigrada!=null){
					dboPartida.clearAll();
					dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_COD_LIBRO);
					dboPartida.setFieldsToRetrieve(DboPartida.CAMPO_NUM_PARTIDA);
					dboPartida.setField(DboPartida.CAMPO_REFNUM_PART,partidaMigrada);
					if(dboPartida.find()){
						
						partidaBean.setNumPartidaMigrado(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA));
						dboTmLibro.clearAll();
						dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
						dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,dboPartida.getField(DboPartida.CAMPO_COD_LIBRO));
						if(dboTmLibro.find()){
							partidaBean.setLibroDescripcionMigrado(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));
						}
					}
					paginas = paginas + numeroPaginas(partidaMigrada);
				}
				
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboFicha.CAMPO_FICHA).append("|");
				sb.append(DboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(DboFicha.CAMPO_REFNUM_PART, refNumPart);
				
				if (dboFicha.find() == true) {
					partidaBean.setFichaId(dboFicha.getField(DboFicha.CAMPO_FICHA));
					String bis = dboFicha.getField(DboFicha.CAMPO_FICHA_BIS);
					int nbis = 0;
					try {
						nbis = Integer.parseInt(bis);
					}
					catch (NumberFormatException n)
					{
						nbis =0;
					}
					if (nbis>=1)
						partidaBean.setFichaId(partidaBean.getFichaId() + " BIS");
				}	
						
				//obtener tomo y foja
				dboTomoFolio.clearAll();
				sb.delete(0, sb.length());
				sb.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
				sb.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
				sb.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
				sb.append(DboTomoFolio.CAMPO_FOLIO_BIS);
				dboTomoFolio.setFieldsToRetrieve(sb.toString());
				dboTomoFolio.setField(DboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
			
				if (dboTomoFolio.find() == true) 
				{
					partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
					partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

					String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
					String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

					if (bist.trim().length() > 0){
							partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);
					}

					if (bisf.trim().length() > 0){
							partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
					}
					
					//quitar el caracter "9" del inicio del tomoid
					if (partidaBean.getTomoId().length()>0)
					{
						if (partidaBean.getTomoId().startsWith("9"))
							{
								String ntomo = partidaBean.getTomoId().substring(1);
								partidaBean.setTomoId(ntomo);
							}
					}						
				}
	
				//descripcion libro
				dboTmLibro.clearAll();
				dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
				dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,codLibro);
				
				if (dboTmLibro.find() == true){
					partidaBean.setLibroDescripcion(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));								
				}
				
				paginas = paginas + numeroPaginas(refNumPart);
				partidaBean.setNumeroPaginas(Double.toString(paginas));
				
				resultado.add(partidaBean);
				
				conta++;
				b = rset.next();
				/**
				 * @autor mgarate
				 * @descripcion Si el medio es un controller realiza la paginación caso contrario, 
				 * 				si es un WebService, no se realiza la paginación
				 * @inicio
				 */
				if(medioDeAcceso==MEDIO_CONTROLLER)
				{
					if (conta==propiedades.getLineasPorPag())
					{
						if(b==true){	
							haySiguiente = true;
						}
						break;
					}	
				}
				/**
				 * @final
				 */		
			}
	
			output.setResultado(resultado);
			
			if (inputBusqDirectaBean.getFlagPagineo()==false){
				output.setCantidadRegistros(String.valueOf(conteo));
			}else{
				output.setCantidadRegistros(inputBusqDirectaBean.getCantidad());
			}
	
			//calcular numero para boton "retroceder pagina"		
			if (inputBusqDirectaBean.getSalto()==1){
				output.setPagAnterior(-1);
			}else{
				output.setPagAnterior(inputBusqDirectaBean.getSalto()-1);
			}
			
			//calcular numero para boton "avanzar pagina"
			if (haySiguiente==false){
				output.setPagSiguiente(-1);
			}else{
				output.setPagSiguiente(inputBusqDirectaBean.getSalto()+1);
			}
	
			//calcular regs del x al y
			int del = ((inputBusqDirectaBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
			int al  = del+resultado.size()-1;
			output.setNdel(del);
			output.setNal(al);

		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return output;
		
	}
	
	public ArrayList buscarActosRMC(String refNumPart) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		int conteo=0;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		ArrayList respuesta = new ArrayList();
		
		try{
			
			StringBuffer q = new StringBuffer();
		    
			q.append("SELECT A.REFNUM_PART     AS REFNUM_PART, " +
					 "       A.NS_ASIENTO      AS NS_ASIENTO, " +
					 "       TA.DESCRIPCION    AS DESCRIPCION, " +
					 "       AG.TS_ACTO_CONST  AS TS_ACTO_CONST, " +
					 "       AG.MONTO_IMPO     AS MONTO_IMPO, " +
					 "       AG.COD_ACTO       AS COD_ACTO, " +
					 "       AG.NS_ASIENTO     AS NS_ASIENTO, " +
					 "       A.NUM_TITU        AS NUM_TITU, " +
					 "       A.AA_TITU         AS AA_TITU, " +
					 "       AG.DESC_EJECUCION AS DESC_EJECUCION, " +
					 "       AG.PACT_ESPEC     AS PACT_ESPEC, " +
					 "       TM.DESC_TIPO_MONEDA AS DESC_TIPO_MONEDA, " +
					 "       TM.DESC_ABRV_MONE   AS DES_ABV_MONEDA, " +
					 "       AG.AN_PLAZO         AS AN_PLAZO, " +
					 "       AG.MES_PLAZO        AS MES_PLAZO, " +
					 "       AG.DIA_PLAZO        AS DIA_PLAZO, " +
					 "		 AG.COD_ACTO         AS COD_ACTO, " +
					 " 		 AG.NS_ASIENTO       AS NS_ASIENTO" +
					 "  FROM ASIENTO A, " +
					 "       TM_ACTO TA, " +
					 "       ASIENTO_GARANTIA AG, " +
					 "       TM_TIPO_MONEDA TM " +
					 " WHERE TA.COD_ACTO    = A.COD_ACTO " +
					 "   AND AG.COD_TIPO_MONEDA = TM.COD_TIPO_MONEDA(+) " + // dado que cod_tipo_moneda pueda que sea null para monto_impo=0.00
					 "   AND AG.REFNUM_PART = A.REFNUM_PART " +
					 "   AND AG.NS_ASIENTO  = A.NS_ASIENTO " +
					 "   AND A.REFNUM_PART  = "+refNumPart + 
			 		 "   order by a.TS_INSCRIP  ");
			
			if (isTrace(this)) System.out.println("___verquery_buscarActosRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				
				String actoDescripcion   = rset.getString("DESCRIPCION");
				Date   fechaConstitutivo = rset.getDate("TS_ACTO_CONST");
				String anoPlazo          = rset.getString("AN_PLAZO");
				String mesPlazo          = rset.getString("MES_PLAZO");
				String diaPlazo          = rset.getString("DIA_PLAZO");
				Double montoAfectacion   = rset.getDouble("MONTO_IMPO");
				String forma             = rset.getString("DESC_EJECUCION"); 
				String condicion		 = rset.getString("PACT_ESPEC");
				String codigoActo        = rset.getString("COD_ACTO");
				String ns_asiento        = rset.getString("NS_ASIENTO");
				String numeroTitulo      = rset.getString("NUM_TITU");
				String anoTitulo         = rset.getString("AA_TITU");
				String simboloMoneda     = rset.getString("DES_ABV_MONEDA");
				
				ActoBean actoBean = new ActoBean();
				actoBean.setActoDescripcion(actoDescripcion);
				if (fechaConstitutivo!=null){
					actoBean.setFechaConstitutivo(fechaConstitutivo.toString());
				}
				actoBean.setAnoPlazo(anoPlazo);
				actoBean.setMesPlazo(mesPlazo);
				actoBean.setDiaPlazo(diaPlazo);
				actoBean.setMontoAfectacion(montoAfectacion);
				if (simboloMoneda!=null){
					actoBean.setSimboloMonto(simboloMoneda);
				}else{
					actoBean.setSimboloMonto("S/"); // por defecto se seteara este tipo de moneda
				}
				actoBean.setForma(forma);
				actoBean.setCondicion(condicion);
				
				ArrayList participantes = new ArrayList();
				participantes = getParticipantesNaturales(refNumPart, ns_asiento, codigoActo);
				participantes.addAll(getParticipantesJuridicas(refNumPart, ns_asiento, codigoActo));
				
				AsientoRMCBean asientoRMCBean = new AsientoRMCBean();
				asientoRMCBean.setActoBean(actoBean);
				asientoRMCBean.setParticipantes(participantes);
				
				TituloBean tituloBean = getTitulo(numeroTitulo, anoTitulo);
				asientoRMCBean.setTituloBean(tituloBean);
				
				ArrayList bienes = getBienes(refNumPart, codigoActo, ns_asiento);
				asientoRMCBean.setBienes(bienes);
				
				if(asientoRMCBean.getTituloBean()!=null && asientoRMCBean.getTituloBean().getRefNumTitulo()!=null){
					ArrayList docuementosFuncionarios = getDocumentoFuncionario(asientoRMCBean.getTituloBean().getRefNumTitulo());
						asientoRMCBean.setDocumentosFuncionarios(docuementosFuncionarios);
				}else{
					ArrayList docuementosFuncionarios =new ArrayList();
					asientoRMCBean.setDocumentosFuncionarios(docuementosFuncionarios);
				}
				
				respuesta.add(asientoRMCBean);
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	
	private ArrayList getParticipantesNaturales(String refNumPart, String nsAsiento, String codigoActo) throws SQLException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		
		ArrayList respuesta = new ArrayList();
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append(" SELECT pn.ape_pat      AS APE_PAT, " +
					 "        pn.ape_mat      AS APE_MAT, " +
					 " 		  pn.nombres       AS NOMBRES, " +
					 " 		  TD.NOMBRE_ABREV AS NOMBRE_ABREV, " +
					 " 		  PN.NU_DOC_IDEN  AS NU_DOC_IDEN, " +
					 " 		  PL.NOMBRE       AS PARTICIPANTE, " +
					 "        IP.DIRECCION    AS DIRECCION, " +
					 "        IG.NS_ASIENTO   AS NS_ASIENTO, " +
					 "        IG.COD_ACTO     AS COD_ACTO, " +
					 "		  IP.TIPO_PERS AS TIPO_PERSONA" +			
					 "   FROM IND_PRTC_ASIENTO_GARANTIA IG, " +
					 "		  IND_PRTC                  IP," +
					 "		  PRTC_NAT                  PN, " +
					 "        PARTIDA                   P, " +
					 "		  TM_DOC_IDEN               TD, " +
					 "		  PARTIC_LIBRO              PL " +
					 "  WHERE IG.REFNUM_PART = IP.REFNUM_PART " +
					 "    AND IG.CUR_PRTC = IP.CUR_PRTC " +
					 "	  AND IG.REFNUM_PART = P.REFNUM_PART " +
					 "    AND IG.CUR_PRTC = PN.CUR_PRTC " +
					 "	  AND P.REG_PUB_ID = PN.REG_PUB_ID " +
					 "    AND P.OFIC_REG_ID = PN.OFIC_REG_ID " +
					 "	  AND P.COD_LIBRO = PL.COD_LIBRO " +
					 "	  AND IG.COD_PARTIC = PL.COD_PARTIC " +
					 "	  AND PN.TI_DOC_IDEN = TD.TIPO_DOC_ID " +
					 "	  and IP.TIPO_PERS   = 'N'       " + 
					 "    AND IG.REFNUM_PART = "+refNumPart+
					 "    AND IG.NS_ASIENTO  = "+nsAsiento+
					 "    AND IG.COD_ACTO    = '"+codigoActo+ "'"+
			 		 "  ORDER BY NOMBRES ");
		
			if (isTrace(this)) System.out.println("___verquery_getParticipantesNaturales__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				
				String apellidoPaterno 				= rset.getString("APE_PAT");
				String apellidoMaterno 				= rset.getString("APE_MAT");
				String nombre                   	= rset.getString("NOMBRES");
				String tipoPersona					= rset.getString("TIPO_PERSONA");
				String descripcionTipoDocumento 	= rset.getString("NOMBRE_ABREV");
				String numeroDocumento				= rset.getString("NU_DOC_IDEN");
				String descripcionTipoParticipacion = rset.getString("PARTICIPANTE");
				String descripcionDomicilio			= rset.getString("DIRECCION");
				
				ParticipanteBean participanteBean = new ParticipanteBean();
				participanteBean.setApellidoPaterno(apellidoPaterno);
				participanteBean.setApellidoMaterno(apellidoMaterno);
				participanteBean.setNombre(nombre);
				participanteBean.setTipoPersona(tipoPersona);
				participanteBean.setDescripcionTipoDocumento(descripcionTipoDocumento);
				participanteBean.setNumeroDocumento(numeroDocumento);
				participanteBean.setDescripcionTipoParticipacion(descripcionTipoParticipacion);
				participanteBean.setDescripcionDomicilio(descripcionDomicilio);
				
				respuesta.add(participanteBean);
				
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	
	private ArrayList getParticipantesJuridicas(String refNumPart, String nsAsiento, String codigoActo) throws SQLException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		
		ArrayList respuesta = new ArrayList();
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append(" SELECT PJ.RAZON_SOCIAL AS RAZON_SOCIAL, " +
					 " 		  TD.NOMBRE_ABREV AS NOMBRE_ABREV, " +
					 "	      PJ.NU_DOC AS NU_DOC, " +
					 "		  PL.NOMBRE AS PARTICIPANTE, " +
					 "		  IP.DIRECCION AS DIRECCION,  " +
					 "		  IG.NS_ASIENTO, " +
					 "		  IG.COD_ACTO, " +
					 "		  IP.TIPO_PERS AS TIPO_PERSONA" +					 
					 "   FROM IND_PRTC_ASIENTO_GARANTIA IG, " +
					 "		  IND_PRTC                  IP, " +
					 "		  PRTC_JUR                  PJ, " +
					 "		  PARTIDA                   P, " +
					 "		  TM_DOC_IDEN               TD, " +
					 "		  PARTIC_LIBRO              PL " +
					 "  WHERE IG.REFNUM_PART = IP.REFNUM_PART " +
					 "	  AND IG.CUR_PRTC = IP.CUR_PRTC " +
					 "	  AND IG.REFNUM_PART = P.REFNUM_PART " +
					 "	  AND IG.CUR_PRTC = PJ.CUR_PRTC " +
					 "	  AND P.REG_PUB_ID = PJ.REG_PUB_ID " +
					 "    AND P.OFIC_REG_ID = PJ.OFIC_REG_ID " +
					 "    AND P.COD_LIBRO = PL.COD_LIBRO " +
					 "    AND IG.COD_PARTIC = PL.COD_PARTIC " +
					 "    AND PJ.TI_DOC = TD.TIPO_DOC_ID " +
					 "	  and IP.TIPO_PERS   = 'J'       " + 
					 "    AND IG.REFNUM_PART = " + refNumPart +
					 "    AND IG.NS_ASIENTO  = " + nsAsiento +
					 "    AND IG.COD_ACTO    = '"+codigoActo+"' " );
			
			if (isTrace(this)) System.out.println("___verquery_getParticipantesJuridicas__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				
				String razonSocial 					= rset.getString("RAZON_SOCIAL");
				String tipoPersona					= rset.getString("TIPO_PERSONA");
				String descripcionTipoDocumento 	= rset.getString("NOMBRE_ABREV");
				String numeroDocumento				= rset.getString("NU_DOC");
				String descripcionTipoParticipacion = rset.getString("PARTICIPANTE");
				String descripcionDomicilio			= rset.getString("DIRECCION");
				
				ParticipanteBean participanteBean = new ParticipanteBean();
				participanteBean.setRazonSocial(razonSocial);
				participanteBean.setTipoPersona(tipoPersona);
				participanteBean.setDescripcionTipoDocumento(descripcionTipoDocumento);
				participanteBean.setNumeroDocumento(numeroDocumento);
				participanteBean.setDescripcionTipoParticipacion(descripcionTipoParticipacion);
				participanteBean.setDescripcionDomicilio(descripcionDomicilio);
				
				respuesta.add(participanteBean);
				
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	
	private ArrayList getBienes(String refNumPart, String codigoActo, String ns_asiento) throws SQLException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		
		ArrayList respuesta = new ArrayList();
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append("SELECT B.DESC_BIEN   AS DESC_BIEN, " +
					 "		 B.TIPO        AS TIPO, " +
					 "		 B.NS_BIEN     AS NS_BIEN, " +
					 "		 B.CANTIDAD    AS CANTIDAD, " +
					 "		 B.MONTO_IMPO  AS MONTO_IMPO, " +
					 "  	 B.COD_ACTO    AS COD_ACTO, " +
					 "		 B.REFNUM_PART AS REFNUM_PART, " +
					 "       B.NS_ASIENTO  AS NS_ASIENTO, " +
					 "		 B.NUM_PLACA   AS NUM_PLACA, " +
					 "		 B.NUM_SERIE   AS NUM_SERIE, " +
					 "		 B.NUM_MOTOR   AS NUM_MOTOR, " +
					 "		 B.COD_MODELO  AS COD_MODELO, " +
					 "		 B.COD_MARCA   AS COD_MARCA " +
					 "  FROM BIEN B " +
					 " WHERE B.REFNUM_PART = " + refNumPart +
					 "   AND B.COD_ACTO    = '"+codigoActo+"' "+
					 "   AND B.NS_ASIENTO  = " + ns_asiento +
					 " ORDER BY TIPO ");
		   
			if (isTrace(this)) System.out.println("___verquery_getBienes__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				
				String descripcion 		= rset.getString("DESC_BIEN");
				String tipo				= rset.getString("TIPO");
				String nsBien			= rset.getString("NS_BIEN");
				String cantidad			= rset.getString("CANTIDAD");
				String monto			= rset.getString("MONTO_IMPO");				
				String numeroPlaca		= rset.getString("NUM_PLACA");
				String numeroSerie		= rset.getString("NUM_SERIE");
				String numeroMotor		= rset.getString("NUM_MOTOR");
				String codigoModelo     = rset.getString("COD_MODELO");
				String codigoMarca      = rset.getString("COD_MARCA");
				
				BienBean bienBean = new BienBean();
				if (descripcion != null){
					bienBean.setDescripcion(descripcion);
				}else{
					bienBean.setDescripcion(null);
				}
				bienBean.setTipo(tipo);
				bienBean.setNsBien(nsBien);
				if (cantidad != null){
					bienBean.setCantidad(cantidad);
				}else{
					bienBean.setCantidad(null);
				}
				bienBean.setMonto(monto);
				bienBean.setNumeroPlaca(numeroPlaca);
				bienBean.setNumeroSerie(numeroSerie);
				bienBean.setNumeroMotor(numeroMotor);
				
				if(codigoMarca != null && codigoMarca.length()>0){
					DboTmMarcaVehi dboTmMarcaVehi = new DboTmMarcaVehi(this.dbConn);
					dboTmMarcaVehi.setField(DboTmMarcaVehi.CAMPO_COD_MARCA, codigoMarca);
					if(dboTmMarcaVehi.find()==true){
						bienBean.setMarca(dboTmMarcaVehi.getField(DboTmMarcaVehi.CAMPO_DESCRIPCION));
					}
				}else{
					bienBean.setMarca(null);
				}
				
				if(codigoModelo != null && codigoMarca != null && codigoModelo.length()>0){
					DboTmModeloVehi dboTmModeloVehi = new DboTmModeloVehi(this.dbConn);
					dboTmModeloVehi.clearAll();
					dboTmModeloVehi.setField(DboTmModeloVehi.CAMPO_COD_MARCA, codigoMarca);
					dboTmModeloVehi.setField(DboTmModeloVehi.CAMPO_COD_MODELO, codigoModelo);
					if(dboTmModeloVehi.find()==true){
						bienBean.setModelo(dboTmModeloVehi.getField(DboTmModeloVehi.CAMPO_DESCRIPCION));
					}
				}else{
					bienBean.setModelo(null);
				}
				
				respuesta.add(bienBean);
				
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	
	private TituloBean getTitulo(String numeroTitulo, String anoTitulo) throws SQLException, DBException{
		TituloBean tituloBean=null;
		
		StringBuffer q = new StringBuffer();
		q.append(" SELECT T.NUM_TITU AS NUM_TITU, " +
				 "		  O.NUM_ORDEN AS NUM_ORDEN, " +
				 "		  T.TS_PRESENT AS TS_PRESENT, " +
				 "		  T.OFIC_REG_ID AS OFIC_REG_ID, " +
				 "		  T.REG_PUB_ID AS REG_PUB_ID, " +
				 "		  O.REFNUM_ORDEN AS REFNUM_ORDEN, " +
				 "		  T.REFNUM_TITU AS REFNUM_TITU " +
				 "   FROM TITULO T, " +
				 "		  ORDEN O " +
				 "  WHERE T.REFNUM_TITU  = O.REFNUM_TITU AND " +
				 "		  T.NUM_TITU     = '" + numeroTitulo + "' AND " +
				 "		  T.ANO_TITU     = '" + anoTitulo + "'");
		
		if (isTrace(this)) System.out.println("___verquery_getBienes__"+q.toString());
		
		Statement stmt   = conn.createStatement();
		ResultSet rset   = stmt.executeQuery(q.toString());
		
		while(rset.next()){
			tituloBean = new TituloBean();
			
			String titulo      			= rset.getString("NUM_TITU");
			String numeroOrden 			= rset.getString("NUM_ORDEN");
			Timestamp fechaPresentacion = rset.getTimestamp("TS_PRESENT");
			String oficReg_id 			= rset.getString("OFIC_REG_ID");
			String regPub_id 			= rset.getString("REG_PUB_ID");
			String refNumOrden          = rset.getString("REFNUM_ORDEN");
			String refNumTitulo         = rset.getString("REFNUM_TITU");
			//String simboloMoneda		= rset.getString("SIMBOLO");
			
			tituloBean.setRefNumTitulo(refNumTitulo);
			tituloBean.setTitulo(titulo);
			tituloBean.setNumeroOrden(numeroOrden);
			tituloBean.setFechaPresentacion(fechaPresentacion);
			tituloBean.setOficReg_id(oficReg_id);
			tituloBean.setRegPub_id(regPub_id);
			//tituloBean.setSimboloMontoPagado(simboloMoneda);
			
			if(oficReg_id!=null && regPub_id!=null){
				DboOficRegistral dboOficRegistral = new DboOficRegistral(this.dbConn);
				dboOficRegistral.setField(DboOficRegistral.CAMPO_REG_PUB_ID, regPub_id);
				dboOficRegistral.setField(DboOficRegistral.CAMPO_OFIC_REG_ID, oficReg_id);
				if(dboOficRegistral.find()==true){
					tituloBean.setOficReg_nombre(dboOficRegistral.getField(DboOficRegistral.CAMPO_NOMBRE));
				}
			}
			
			StringBuffer q2 = new StringBuffer();
			q2.append("SELECT R.NS_RECIBO, " +
					  "		  R.MONTO_COBR AS MONTO_COBR, " +
					  "		  R.AN_RECIBO AS AN_RECIBO, " +
					  "		  R.CO_CAJA AS CO_CAJA, " +
					  "		  R.NUM_TICK NUM_TICK, " +
					  "		  TM.DESC_ABRV_MONE AS SIMBOLO " +
					  "	 FROM RECIBO R, TM_TIPO_MONEDA TM" +
					  "	 WHERE R.COD_TIPO_MONEDA = TM.COD_TIPO_MONEDA AND " +
					  "        R.REFNUM_ORDEN = '"+refNumOrden+"'");   
				
			Statement stmt2   = conn.createStatement();
			ResultSet rset2   = stmt2.executeQuery(q2.toString());
			
			double montoPagado = 0;
			String recibos = "";
			while(rset2.next()){
				String simboloMoneda		= rset2.getString("SIMBOLO");          // DEBE ESTAR TODO EN SOLES
				tituloBean.setSimboloMontoPagado(simboloMoneda);
				
				montoPagado += Double.parseDouble(rset2.getString("MONTO_COBR"));
				if (recibos.equals("")){
					recibos += (rset2.getString("AN_RECIBO") + " - " + rset2.getString("CO_CAJA") + " - " + rset2.getString("NUM_TICK"));
				}else{
					recibos += " , " + (rset2.getString("AN_RECIBO") + " - " + rset2.getString("CO_CAJA") + " - " + rset2.getString("NUM_TICK"));
				}
			}
			
			tituloBean.setMontoPagado(montoPagado);
			tituloBean.setNumeroRecibo(recibos);
		}
		
		return tituloBean;
	}

	private ArrayList getDocumentoFuncionario(String refNumTitulo) throws SQLException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		
		ArrayList respuesta = new ArrayList();
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append(" SELECT TDL.DESC_TIPO_DOCU_LEGA AS DESC_TIPO_DOCU_LEGA, " +
					 "		  TF.DESC_TIPO_FUNC AS DESC_TIPO_FUNC, " +
					 "		  TD.FUNCIONARIO AS FUNCIONARIO, " +
					 "  	  TD.TS_DOCU_LEGA AS TS_DOCU_LEGA" +
					 "   FROM TITULO_DOCUMENTO     TD, " +
					 "		  TM_TIPO_DOCU_LEGALES TDL, " +
					 "	      TM_TIPO_FUNCIONARIO  TF " +
					 "  WHERE TD.COD_TIPO_DOCU_LEGA = TDL.COD_TIPO_DOCU_LEGA " +
					 "    AND TD.COD_TIPO_FUNC = TF.COD_TIPO_FUNC " +
					 "    AND TD.REFNUM_TITU = '"+refNumTitulo+"'");
		
			if (isTrace(this)) System.out.println("___verquery_getDocumentoFuncionario__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				
				String documento 	   = rset.getString("DESC_TIPO_DOCU_LEGA");
				String tipoFuncionario = rset.getString("DESC_TIPO_FUNC");
				String funcionario     = rset.getString("FUNCIONARIO");
				Date fecha			   = rset.getDate("TS_DOCU_LEGA");
				
				DocumentoFuncionarioBean documentoFuncionarioBean = new DocumentoFuncionarioBean();
				documentoFuncionarioBean.setDocumento(documento);
				documentoFuncionarioBean.setFecha(fecha);
				documentoFuncionarioBean.setFuncionario(funcionario);
				documentoFuncionarioBean.setTipoFuncionario(tipoFuncionario);
				
				
				respuesta.add(documentoFuncionarioBean);
				
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	
	private ArrayList buscarTitulosPendientesRMC(String refNumPart) throws SQLException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		
		ArrayList respuesta = new ArrayList();
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append(" SELECT T.REG_PUB_ID AS REG_PUB_ID, " +
					 "        T.REFNUM_TITU AS REFNUM_TITU, " +
					 "		  T.NUM_TITU AS NUM_TITU, " +
					 "		  T.TS_PRESENT AS TS_PRESENT, " +
					 "		  T.FEC_VENC AS FEC_VENC," +
					 "		  T.OFIC_REG_ID AS OFIC_REG_ID, " +
					 "		  T.ANO_TITU AS ANO, " +
					 "		  T.AREA_REG_ID AS AREA_REG_ID, " +
					 "		  DT.ESTADO_TITULO_ID AS COD_ESTADO_TITU" +
					 "  FROM TA_BLOQ_PARTIDA TP, PARTIDA P, TITULO T, DETALLE_TITULO DT, TITULO_ORDEN TOR" +
					 "  WHERE TP.REG_PUB_ID = P.REG_PUB_ID " +
					 "    AND TP.NUM_PARTIDA = P.NUM_PARTIDA " +
					 "	  AND TP.OFIC_REG_ID = P.OFIC_REG_ID " +
					 /** inicio: jrosas: 07-09-2007 **/
					 "	  AND TP.AREA_REG_ID = P.AREA_REG_ID " +
					 "	  AND TP.OFIC_REG_ID = TOR.OFIC_REG_ID " +
					 "    AND TP.REG_PUB_ID  = TOR.REG_PUB_ID " +
					 "    AND TP.NUM_TITU    = TOR.NUM_TITU " +
					 "    AND TP.ANO_TITU    = TOR.ANO_TITU " +
					 "	  AND TP.AREA_REG_ID = TOR.AREA_REG_ID " +
					 "	  AND TOR.REFNUM_TITU  = T.REFNUM_TITU " +
					 "	  AND T.REFNUM_TITU  = DT.REFNUM_TITU " +
					 "	  AND DT.FG_ACTIVO   = '1' " +
					 "	  AND TP.ESTADO      = '1' " +
					 /** fin: jrosas: 07-09-2007 **/
					 "    AND P.REFNUM_PART = "+refNumPart);
			
			if (isTrace(this)) System.out.println("___verquery_buscarTitulosPendientesRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				
				String registroPublicoId  = rset.getString("REG_PUB_ID");
				String oficinaRegistralId = rset.getString("OFIC_REG_ID");
				String area_reg_id 		  =  rset.getString("AREA_REG_ID");
				
				String numeroTitulo       = rset.getString("NUM_TITU");
				String anoTitulo		  = rset.getString("ANO");	
				Date fechaPresentacion    = rset.getDate("TS_PRESENT");
				Date fechaVencimiento     = rset.getDate("FEC_VENC");
				String refNumTitulo       = rset.getString("REFNUM_TITU");
				String estado_id		  =	rset.getString("COD_ESTADO_TITU");
				
				DboOficRegistral dboOficina = new DboOficRegistral(this.dbConn);
				dboOficina.setFieldsToRetrieve(DboOficRegistral.CAMPO_NOMBRE);
				dboOficina.setField(DboOficRegistral.CAMPO_REG_PUB_ID,registroPublicoId);
				dboOficina.setField(DboOficRegistral.CAMPO_OFIC_REG_ID,oficinaRegistralId);
				
				DboRegisPublico dboRegisPublico = new DboRegisPublico(this.dbConn);
				dboRegisPublico.setFieldsToRetrieve(dboRegisPublico.CAMPO_NOMBRE);
				dboRegisPublico.setField(dboRegisPublico.CAMPO_REG_PUB_ID,registroPublicoId);
				
				TituloPendienteBean tituloPendienteBean = new TituloPendienteBean();
				String nombreRegis="";
				if (dboOficina.find()){
					tituloPendienteBean.setOficReg(dboOficina.getField(DboOficRegistral.CAMPO_NOMBRE));
				}
				
				if (dboRegisPublico.find() == true){
					nombreRegis = dboRegisPublico.getField(dboRegisPublico.CAMPO_NOMBRE);
					String zona = nombreRegis.substring(14, 17);
					tituloPendienteBean.setSede(nombreRegis);
					tituloPendienteBean.setZonaReg(zona);
				}
				String aux_ofic = registroPublicoId + "|" + oficinaRegistralId + "|" +nombreRegis;
				
				tituloPendienteBean.setNroTitulo(numeroTitulo);
				tituloPendienteBean.setAaTitulo(anoTitulo);
				tituloPendienteBean.setArea_registral(area_reg_id);
				tituloPendienteBean.setFechaPresentacion(fechaPresentacion);
				tituloPendienteBean.setFechaVencimiento(fechaVencimiento);
				tituloPendienteBean.setActoDescripcion(buscarActosTitulo(refNumTitulo));
				tituloPendienteBean.setEstadoDescripcion(buscarEstadoTitulo(refNumTitulo));
				tituloPendienteBean.setEstado_id(estado_id);
				tituloPendienteBean.setOficinas(aux_ofic);
				respuesta.add(tituloPendienteBean);
				
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	
	public String buscarActosTitulo(String refNumTitulo) throws SQLException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		
		String respuesta = null;
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append(" SELECT A.DESCRIPCION AS DESCRIPCION, " +
					 "		  TA.ESTADO AS ESTADO" +
					 "   FROM ACTOS_TITULO TA,  " +
					 "		  TM_ACTO A " +
					 "  WHERE TA.COD_ACTO = A.COD_ACTO" +
					 "    AND TA.REFNUM_TITU = "+refNumTitulo);
			
			if (isTrace(this)) System.out.println("___verquery_buscarActosTitulo__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				respuesta = "";
				String descripcion  = rset.getString("DESCRIPCION");
				respuesta = descripcion;
				
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	 
	private String buscarEstadoTitulo(String refNumTitulo) throws SQLException, DBException{
		
		Statement stmt   = null;
		ResultSet rset   = null;
		
		String respuesta = "";
		
		try{
			
			StringBuffer q = new StringBuffer();
			
			q.append(" SELECT ET.ESTADO AS ESTADO" +
					 "   FROM DETALLE_TITULO DT, " +
					 "		  TM_ESTADO_TITULO ET " +
					 "  WHERE DT.ESTADO_TITULO_ID = ET.ESTADO_TITULO_ID " +
					 "    AND DT.FG_ACTIVO = '1' " +
					 "    AND DT.REFNUM_TITU = "+refNumTitulo);
			
			if (isTrace(this)) System.out.println("___verquery_buscarEstadoTitulo__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while(rset.next()){
				
				String estado  = rset.getString("ESTADO");
				
				respuesta = estado;
				
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return respuesta;
	}
	
	public FormOutputBuscarPartida busquedaDetallePartidaRMC(InputBusqDirectaBean inputBusquedaBean) throws CustomException, ValidacionException, Throwable{
		
		FormOutputBuscarPartida respuesta = new FormOutputBuscarPartida();
		String aux=null;
		String refnumpart_partidamigrada="";
		
		if(inputBusquedaBean.getRefNumPart()!=null){
			
			ArrayList resultadoTituloPendientesRMC = buscarTitulosPendientesRMC(inputBusquedaBean.getRefNumPart());
			ArrayList resultadoActosRMC = buscarActosRMC(inputBusquedaBean.getRefNumPart());
			
			PartidaBean partidaBean = new PartidaBean();
			
			DboPartida dboPartida = new DboPartida(this.dbConn);
			dboPartida.clearAll();
			dboPartida.setField(DboPartida.CAMPO_REFNUM_PART, inputBusquedaBean.getRefNumPart());
			
			if (dboPartida.find()){
				partidaBean.setRefNumPart(dboPartida.getField(DboPartida.CAMPO_REFNUM_PART));
				partidaBean.setNumPartida(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA));
				partidaBean.setEstado(dboPartida.getField(DboPartida.CAMPO_ESTADO));
				partidaBean.setCodLibro(dboPartida.getField(DboPartida.CAMPO_COD_LIBRO));
				partidaBean.setAreaRegistralId(dboPartida.getField(DboPartida.CAMPO_AREA_REG_ID));
				partidaBean.setOficRegId(dboPartida.getField(DboPartida.CAMPO_OFIC_REG_ID));
				partidaBean.setRegPubId(dboPartida.getField(DboPartida.CAMPO_REG_PUB_ID));
				refnumpart_partidamigrada = obtenerRefNumParAntiguo(dboPartida.getField(DboPartida.CAMPO_REFNUM_PART));
				partidaBean.setRefNumPartMigrado(refnumpart_partidamigrada);
				
				if(refnumpart_partidamigrada!=null){
					dboPartida.clearAll();
					dboPartida.setField(DboPartida.CAMPO_REFNUM_PART,refnumpart_partidamigrada);
					if(dboPartida.find()){
						partidaBean.setNumPartidaMigrado(dboPartida.getField(DboPartida.CAMPO_NUM_PARTIDA));
					}
				}else{
					partidaBean.setNumPartidaMigrado(aux);
				}
			}
			// setea numero partida, ofic_reg_id, reg_pub_id
			inputBusquedaBean.setNumeroPartida(inputBusquedaBean.getNumeroPartida()==null || inputBusquedaBean.getNumeroPartida().equals("")?partidaBean.getNumPartida():inputBusquedaBean.getNumeroPartida());
			inputBusquedaBean.setOficRegId(inputBusquedaBean.getOficRegId()==null || inputBusquedaBean.getOficRegId().equals("")?partidaBean.getOficRegId():inputBusquedaBean.getOficRegId());
			inputBusquedaBean.setRegPubId(inputBusquedaBean.getRegPubId()==null || inputBusquedaBean.getRegPubId().equals("")?partidaBean.getRegPubId():inputBusquedaBean.getRegPubId());
			
			DboOficRegistral dboOficRegistral = new DboOficRegistral(this.dbConn);
			dboOficRegistral.setFieldsToRetrieve(dboOficRegistral.CAMPO_NOMBRE);
			dboOficRegistral.setField(dboOficRegistral.CAMPO_OFIC_REG_ID,partidaBean.getOficRegId());
			dboOficRegistral.setField(dboOficRegistral.CAMPO_REG_PUB_ID,partidaBean.getRegPubId());
			if (dboOficRegistral.find() == true)
				partidaBean.setOficRegDescripcion(dboOficRegistral.getField(dboOficRegistral.CAMPO_NOMBRE));
			
			respuesta.setResultadoTituloPendientesRMC(resultadoTituloPendientesRMC);
			respuesta.setResultadoActosRMC(resultadoActosRMC);
			respuesta.setPartidaBean(partidaBean);
			
		}
		
		return respuesta;
	}
	

}
