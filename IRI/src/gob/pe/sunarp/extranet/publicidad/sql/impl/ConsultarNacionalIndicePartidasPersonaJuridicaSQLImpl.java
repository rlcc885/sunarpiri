
package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboFicha;
import gob.pe.sunarp.extranet.dbobj.DboParticLibro;
import gob.pe.sunarp.extranet.dbobj.DboPartida;
import gob.pe.sunarp.extranet.dbobj.DboTmAreaRegistral;
import gob.pe.sunarp.extranet.dbobj.DboTmDocIden;
import gob.pe.sunarp.extranet.dbobj.DboTmLibro;
import gob.pe.sunarp.extranet.dbobj.DboTomoFolio;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarNacionalIndicePartidasPersonaJuridicaSQL;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class ConsultarNacionalIndicePartidasPersonaJuridicaSQLImpl extends SQLImpl
implements ConsultarNacionalIndicePartidasPersonaJuridicaSQL,Constantes{

	private Connection conn;
	private DBConnection dbConn;
	
	
	public ConsultarNacionalIndicePartidasPersonaJuridicaSQLImpl(Connection conn, DBConnection dbConn) {

		this.conn = conn;
		this.dbConn = dbConn;
	}

	public FormOutputBuscarPartida busquedaIndicePersonaJuridicaSIGC(int medioDeAcceso, InputBusqIndirectaBean inputBusqIndirectaBean, boolean flagUsuarioInterno,  String session_id) throws SQLException, CustomException, ValidacionException, DBException {
		
		FormOutputBuscarPartida output 	= new FormOutputBuscarPartida();
		Statement stmt					= null;
		ResultSet rset   				= null;
		String razonSocial				= null;
		String siglas					= null;
		boolean continua				= false;
		String verifica					= null;
		try {

			StringBuffer sql= new StringBuffer();
			Propiedades propiedades = Propiedades.getInstance();
			int conteo=0;
			if (inputBusqIndirectaBean.getFlagPagineo()==false)
			{
				conteo=contarBusquedaIndicePersonaJuridicaSIGC(inputBusqIndirectaBean);
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				if(flagUsuarioInterno != true){
					if (conteo==0)
						throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
					
				}
			}else{
				conteo = Integer.parseInt(inputBusqIndirectaBean.getCantidad());
			}
			if(conteo>0){
				continua=true;
			}
			if(continua == true ){
				
			razonSocial= inputBusqIndirectaBean.getArea2Razon1();
			siglas= inputBusqIndirectaBean.getArea2Siglas();
			verifica= inputBusqIndirectaBean.getVerifica();
			
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			StringBuffer sb = new StringBuffer();
			StringBuffer q  = new StringBuffer();
			
			sql.append(" SELECT DISTINCT " +
						" PARTIDA.ESTADO as ESTADO, REGIS_PUBLICO.SIGLAS as SIGLAS," +
						" PRTC_JUR.SIGLAS AS SIGLAS_JUR, OFIC_REGISTRAL.NOMBRE as NOMBRE," +
						" IND_PRTC.COD_PARTIC as COD_PARTIC,PRTC_JUR.RAZON_SOCIAL as RAZON_SOCIAL," +
						" PRTC_JUR.TI_DOC as TI_DOC,PRTC_JUR.NU_DOC as NU_DOC,PARTIDA.REFNUM_PART as REFNUM_PART," +
						" PARTIDA.NUM_PARTIDA as NUM_PARTIDA,PARTIDA.COD_LIBRO as COD_LIBRO,IND_PRTC.ESTADO AS estadoPartic," +
						" partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id," +
						" partida.area_reg_id as area_reg_id " +
					" FROM " +
						" PRTC_JUR,IND_PRTC,PARTIDA,OFIC_REGISTRAL,REGIS_PUBLICO,grupo_libro_area gla, grupo_libro_area_det glad," +
						" asiento_garantia ag " +
					" WHERE PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC" +
					" AND IND_PRTC.REFNUM_PART=PARTIDA.REFNUM_PART" +
					" and ag.refnum_part=partida.refnum_part" +
					" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
					" AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID" +
					" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
					" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID " +
					" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
			
			if(verifica!= null){
				if (razonSocial!=null && razonSocial.length()>0){
					sql.append(" AND prtc_jur.RAZON_SOCIAL = '").append(razonSocial).append("'");
				}				
			}else{
				if (razonSocial!=null && razonSocial.length()>0){
					sql.append(" AND prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%'");
				}
				if (siglas!=null && siglas.length()>0){
					sql.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%'");
				}
				
			}
			sql.append(" and partida.estado!='2'" +
					" AND partida.cod_libro = glad.cod_libro" +
					" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
					" and gla.cod_grupo_libro_area ='28'" +
					" AND IND_PRTC.ESTADO = '1'" +
					" AND IND_PRTC.TIPO_PERS='J'" +
					" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'" +
					" ORDER BY RAZON_SOCIAL,SIGLAS_JUR DESC,REG_PUB_ID,ofic_reg_id,num_partida");

             if (isTrace(this)) System.out.println("___verquery_busquedaIndicePersonaJuridicaSIGC__"+sql.toString());
 			
 			stmt   =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(sql.toString());
 			//Inicio:mgarate:27/08/2007
 			if(medioDeAcceso==MEDIO_CONTROLLER)
 			{
	 			if (inputBusqIndirectaBean.getSalto()>1){ 
	 				rset.absolute(propiedades.getLineasPorPag() * (inputBusqIndirectaBean.getSalto() - 1));
	 			} 
 			}
 			//Fin:mgarate
 			boolean b = rset.next();
 			
 			// descripcion area registral
			DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(this.dbConn);
			dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,inputBusqIndirectaBean.getComboArea());
			dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
			String descripcionAreaRegistral="";
			
			if (dboTmAreaRegistral.find() == true)
				descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

			DboFicha dboFicha = new DboFicha(this.dbConn);
			DboTomoFolio dboTomoFolio = new DboTomoFolio(this.dbConn);
			DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
			DboPartida dboPartida = new DboPartida(this.dbConn);
			DboTmDocIden dboTmDocIden = new DboTmDocIden(this.dbConn);
			DboParticLibro dboParticLibro = new DboParticLibro(this.dbConn);
			int conta=0;
			
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				String refNumPart = rset.getString("refnum_part");
				String oficRegDesc = rset.getString("nombre");
				String codLibro   = rset.getString("cod_libro");

				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
				partidaBean.setNumPartida(rset.getString("num_partida"));
				partidaBean.setRegPubDescripcion(rset.getString("siglas"));
				partidaBean.setOficRegDescripcion(rset.getString("nombre"));
				partidaBean.setEstado(rset.getString("estado"));
				partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
				partidaBean.setRegPubId(rset.getString("reg_pub_id"));
				partidaBean.setOficRegDescripcion(oficRegDesc);
				partidaBean.setEstadoInd("Inactivo");
				
				if (rset.getString("estado").startsWith("1"))
					partidaBean.setEstadoInd("Activo");
				
				//dbravo: Recupera el refnum_part de la partida migrada si existiera
				String partidaMigrada = consultarPartidaDirectaSQLImpl.obtenerRefNumParAntiguo(refNumPart);
				String partidaNueva = null;
				double paginas = 0;
				
				//dbravo: Si no existe una partida migrada, Recupera, si existiera, el refnum_part de la partida nueva
				if(partidaMigrada==null){
					partidaNueva   = consultarPartidaDirectaSQLImpl.obtenerRefNumParNuevo(refNumPart);
				}
				
				//dbravo: Si existe una partida nueva significa que la partida con la que se realizo la busqueda era una partida migrada,
				//        por lo que se procede a buscar los datos de la partida a Actual.
				if(partidaNueva!=null){
					partidaMigrada = refNumPart;
					partidaBean = consultarPartidaDirectaSQLImpl.busquedaDirectaPorRefNumPartRMC(refNumPart);
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
					paginas = paginas + consultarPartidaDirectaSQLImpl.numeroPaginas(partidaMigrada);
				}

				//ficha
				dboFicha.clearAll();
				sb.delete(0, sb.length());
				sb.append(dboFicha.CAMPO_FICHA).append("|");
				sb.append(dboFicha.CAMPO_FICHA_BIS);
				dboFicha.setFieldsToRetrieve(sb.toString());
				dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
				if (dboFicha.find() == true)
				{
					partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
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
				dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
				if (dboTomoFolio.find() == true)
				{
					partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
					partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

					String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
					String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

					if (bist.trim().length() > 0)
							partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);

					if (bisf.trim().length() > 0)
							partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

					//28dic2002
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
				dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
				dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
				if (dboTmLibro.find() == true){
					partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));
					partidaBean.setCodLibro(dboTmLibro.getField(dboTmLibro.CAMPO_COD_LIBRO));
				}
					


				//participante y su número de documento
				String tipoDocumento="";
				String codPartic="";

				partidaBean.setParticipanteDescripcion(rset.getString("RAZON_SOCIAL"));

				String nuDocIden = rset.getString("nu_doc");
				if (nuDocIden==null || nuDocIden.trim().length()==0)
					partidaBean.setParticipanteNumeroDocumento("&nbsp;");
				else
					partidaBean.setParticipanteNumeroDocumento(nuDocIden);

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
						if (dboTmDocIden.find() == true)
							partidaBean.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
					}
				}

				//descripción del tipo de participación
				dboParticLibro.clearAll();
				dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
				dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
				dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
				dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
				
				if (dboParticLibro.find()==true)
					partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
					
				paginas = paginas + consultarPartidaDirectaSQLImpl.numeroPaginas(refNumPart);
				partidaBean.setNumeroPaginas(Double.toString(paginas));
				
				resultado.add(partidaBean);
				conta++;
				
				b = rset.next();
				//Inicio:mgarate:27/08/2007
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
	 			//Fin:mgarate
		}//while (b==true)
			
		
		output.setResultado(resultado);
		
		if (inputBusqIndirectaBean.getFlagPagineo()==false){
			output.setCantidadRegistros(String.valueOf(conteo));
		}else{
			output.setCantidadRegistros(inputBusqIndirectaBean.getCantidad());
		}

		//calcular numero para boton "retroceder pagina"		
		if (inputBusqIndirectaBean.getSalto()==1){ // if (inputBusqIndirectaBean.getSalto()==1)
			output.setPagAnterior(-1);
		}else{
			output.setPagAnterior(inputBusqIndirectaBean.getSalto()-1);
		}
		
		//calcular numero para boton "avanzar pagina"
		if (haySiguiente==false){
			output.setPagSiguiente(-1);
		}else{
			output.setPagSiguiente(inputBusqIndirectaBean.getSalto()+1);
		}

		//calcular regs del x al y
		int del = ((inputBusqIndirectaBean.getSalto()-1)*propiedades.getLineasPorPag())+1;
		int al  = del+resultado.size()-1;
		output.setNdel(del);
		output.setNal(al);
		}//final del if continua
		else{
			output.setCantidadRegistros(String.valueOf(conteo));
			output.setPagAnterior(-1);
			output.setPagSiguiente(-1);
			output.setNdel(0);
			output.setNal(0);
		}
	
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		
		
		return output;
	}

	public int contarBusquedaIndicePersonaJuridicaSIGC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException {
		int contador=0;
		String razonSocial=null;
		String siglas=null;
		String verifica =null;
		PreparedStatement pstmt=null;
		ResultSet res=null;
		
		try {
			Propiedades propiedades = Propiedades.getInstance();
			StringBuffer sql=new StringBuffer();
			razonSocial=inputBusqIndirectaBean.getArea2Razon1();
			siglas=inputBusqIndirectaBean.getArea2Siglas();
			verifica=inputBusqIndirectaBean.getVerifica();
			
			sql.append( " SELECT COUNT(*) " +
					    " FROM  "+
					    " (SELECT DISTINCT " +
							" PARTIDA.ESTADO as ESTADO, REGIS_PUBLICO.SIGLAS as SIGLAS," +
							" PRTC_JUR.SIGLAS AS SIGLAS_JUR, OFIC_REGISTRAL.NOMBRE as NOMBRE," +
							" IND_PRTC.COD_PARTIC as COD_PARTIC,PRTC_JUR.RAZON_SOCIAL as RAZON_SOCIAL," +
							" PRTC_JUR.TI_DOC as TI_DOC,PRTC_JUR.NU_DOC as NU_DOC,PARTIDA.REFNUM_PART as REFNUM_PART," +
							" PARTIDA.NUM_PARTIDA as NUM_PARTIDA,PARTIDA.COD_LIBRO as COD_LIBRO,IND_PRTC.ESTADO AS estadoPartic," +
							" partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id," +
							" partida.area_reg_id as area_reg_id " +
						" FROM PRTC_JUR,IND_PRTC,PARTIDA,OFIC_REGISTRAL,REGIS_PUBLICO,grupo_libro_area gla, grupo_libro_area_det glad," +
							" asiento_garantia ag " +
						" WHERE PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC" +
						" AND IND_PRTC.REFNUM_PART=PARTIDA.REFNUM_PART" +
						" and ag.refnum_part=partida.refnum_part" +
						" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
						" AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID" +
						" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
						" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID " +
						" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");

			if(verifica!= null){
				if (razonSocial!=null && razonSocial.length()>0){
					sql.append(" AND prtc_jur.RAZON_SOCIAL = '").append(razonSocial).append("'");
				}				
			}else{
				if (razonSocial!=null && razonSocial.length()>0){
					sql.append(" AND prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%'");
				}
				if (siglas!=null && siglas.length()>0){
					sql.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%'");
				}
				
			}
			sql.append(" and partida.estado!='2'" +
					" AND partida.cod_libro = glad.cod_libro" +
					" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
					" and gla.cod_grupo_libro_area ='28'" +
					" AND IND_PRTC.TIPO_PERS='J'" +
					" AND IND_PRTC.ESTADO = '1'");
			sql.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')"); 
			
			if (isTrace(this)) System.out.println("___verquery_contarBusquedaIndicePersonaNaturalSIGC__"+sql.toString());
			
			pstmt=conn.prepareStatement(sql.toString());
			res=pstmt.executeQuery();
			if(res.next()){
				contador=res.getInt(1);
			}
			
		} catch (Exception e) {
			
		} finally {
			JDBC.getInstance().closeResultSet(res);
			JDBC.getInstance().closeStatement(pstmt);
		}
		return contador;
	}
	
	public FormOutputBuscarPartida busquedaIndicePersonaJuridicaSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean, String session_id) throws SQLException, CustomException, ValidacionException, DBException {
		
		FormOutputBuscarPartida output 	= new FormOutputBuscarPartida();
		Statement stmt					= null;
		ResultSet rset   				= null;
		String razonSocial				= null;
		String siglas					= null;
		String verifica					= null;
		try {

			StringBuffer sql= new StringBuffer();
			Propiedades propiedades = Propiedades.getInstance();
			int conteo=0;
			if (inputBusqIndirectaBean.isFlagPagineoInferior()==false)
			{
				conteo=contarBusquedaIndicePersonaJuridicaSIGCInterno(inputBusqIndirectaBean);
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
			}else{
				conteo = Integer.parseInt(inputBusqIndirectaBean.getCantidadInferior());
			}
			
			if(conteo > 0){
				razonSocial= inputBusqIndirectaBean.getArea2Razon1();
				siglas= inputBusqIndirectaBean.getArea2Siglas();
				verifica= inputBusqIndirectaBean.getVerifica();
				
				ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
				ArrayList resultado = new ArrayList();
				boolean haySiguiente = false;
				StringBuffer sb = new StringBuffer();
				
				
				sql.append(" SELECT DISTINCT" +
						" PARTIDA.ESTADO as ESTADO, REGIS_PUBLICO.SIGLAS as SIGLAS," +
						" PRTC_JUR.SIGLAS AS SIGLAS_JUR, OFIC_REGISTRAL.NOMBRE as NOMBRE," +
						" IND_PRTC.COD_PARTIC as COD_PARTIC,PRTC_JUR.RAZON_SOCIAL as RAZON_SOCIAL," +
						" PRTC_JUR.TI_DOC as TI_DOC,PRTC_JUR.NU_DOC as NU_DOC,PARTIDA.REFNUM_PART as REFNUM_PART," +
						" PARTIDA.NUM_PARTIDA as NUM_PARTIDA,PARTIDA.COD_LIBRO as COD_LIBRO,IND_PRTC.ESTADO AS estadoPartic," +
						" partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id," +
						" partida.area_reg_id as area_reg_id " +
						" FROM " +
						" prtc_jur, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla," +
						" grupo_libro_area_det glad, asiento_garantia ag , asiento a" +
						" WHERE PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC" +
						" AND ind_prtc.refnum_part = partida.refnum_part" +
						" and a.refnum_part=ag.refnum_part (+)" +
						" and ag.refnum_part is null" +
						" and a.refnum_part=partida.refnum_part" +
						" AND IND_PRTC.REFNUM_PART=A.REFNUM_PART" +
						" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
						" AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID" +
						" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id" +
						" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
						" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id");
				
				if(verifica!= null){
					if (razonSocial!=null && razonSocial.length()>0){
						sql.append(" AND prtc_jur.RAZON_SOCIAL = '").append(razonSocial).append("'");
					}				
				}else{
					if (razonSocial!=null && razonSocial.length()>0){
						sql.append(" AND prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%'");
					}
					if (siglas!=null && siglas.length()>0){
						sql.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%'");
					}
					
				}
				sql.append(" and partida.estado !='2'" +
						" AND partida.cod_libro = glad.cod_libro" +
						" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
						" and gla.cod_grupo_libro_area ='28' " +
						" AND IND_PRTC.TIPO_PERS='J'");
				if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
					sql.append(" AND IND_PRTC.ESTADO = '1'");
				}
				sql.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'");
				sql.append(" ORDER BY RAZON_SOCIAL,SIGLAS_JUR DESC,REG_PUB_ID,ofic_reg_id,num_partida");

	             if (isTrace(this)) System.out.println("___verquery_busquedaIndicePersonaJuridicaSIGCInterno__"+sql.toString());
	 			
	 			stmt   =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	 			
	 			stmt.setFetchSize(propiedades.getLineasPorPag());
	 			rset   = stmt.executeQuery(sql.toString());
	 			
	 			if (inputBusqIndirectaBean.getSaltoInferior()>1){ 
	 				rset.absolute(propiedades.getLineasPorPag() * (inputBusqIndirectaBean.getSaltoInferior() - 1));
	 			} 
	 			
	 			boolean b = rset.next();
	 			
	 			// descripcion area registral
				DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(this.dbConn);
				dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
				dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,inputBusqIndirectaBean.getComboArea());
				dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
				String descripcionAreaRegistral="";
				
				if (dboTmAreaRegistral.find() == true)
					descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

				DboFicha dboFicha = new DboFicha(this.dbConn);
				DboTomoFolio dboTomoFolio = new DboTomoFolio(this.dbConn);
				DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
				DboPartida dboPartida = new DboPartida(this.dbConn);
				DboTmDocIden dboTmDocIden = new DboTmDocIden(this.dbConn);
				DboParticLibro dboParticLibro = new DboParticLibro(this.dbConn);
				int conta=0;
				
				while (b==true)
				{
					PartidaBean partidaBean = new PartidaBean();
					
					String refNumPart = rset.getString("refnum_part");
					String oficRegDesc = rset.getString("nombre");
					String codLibro   = rset.getString("cod_libro");

					partidaBean.setRefNumPart(refNumPart);
					partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
					partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setRegPubDescripcion(rset.getString("siglas"));
					partidaBean.setOficRegDescripcion(rset.getString("nombre"));
					partidaBean.setEstado(rset.getString("estado"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setOficRegDescripcion(oficRegDesc);
					partidaBean.setEstadoInd("Inactivo");
					
					if (rset.getString("estado").startsWith("1"))
						partidaBean.setEstadoInd("Activo");
					
					//dbravo: Recupera el refnum_part de la partida migrada si existiera
					String partidaMigrada = consultarPartidaDirectaSQLImpl.obtenerRefNumParAntiguo(refNumPart);
					String partidaNueva = null;
					double paginas = 0;
					
					//dbravo: Si no existe una partida migrada, Recupera, si existiera, el refnum_part de la partida nueva
					if(partidaMigrada==null){
						partidaNueva   = consultarPartidaDirectaSQLImpl.obtenerRefNumParNuevo(refNumPart);
					}
					
					//dbravo: Si existe una partida nueva significa que la partida con la que se realizo la busqueda era una partida migrada,
					//        por lo que se procede a buscar los datos de la partida a Actual.
					if(partidaNueva!=null){
						partidaMigrada = refNumPart;
						partidaBean = consultarPartidaDirectaSQLImpl.busquedaDirectaPorRefNumPartRMC(refNumPart);
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
						paginas = paginas + consultarPartidaDirectaSQLImpl.numeroPaginas(partidaMigrada);
					}

					//ficha
					dboFicha.clearAll();
					sb.delete(0, sb.length());
					sb.append(dboFicha.CAMPO_FICHA).append("|");
					sb.append(dboFicha.CAMPO_FICHA_BIS);
					dboFicha.setFieldsToRetrieve(sb.toString());
					dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
					if (dboFicha.find() == true)
					{
						partidaBean.setFichaId(dboFicha.getField(dboFicha.CAMPO_FICHA));
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
					dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
					if (dboTomoFolio.find() == true)
					{
						partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
						partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

						String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
						String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

						if (bist.trim().length() > 0)
								partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);

						if (bisf.trim().length() > 0)
								partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);

						//28dic2002
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
					dboTmLibro.setFieldsToRetrieve(dboTmLibro.CAMPO_DESCRIPCION);
					dboTmLibro.setField(dboTmLibro.CAMPO_COD_LIBRO,codLibro);
					if (dboTmLibro.find() == true)
						partidaBean.setLibroDescripcion(dboTmLibro.getField(dboTmLibro.CAMPO_DESCRIPCION));


					//participante y su número de documento
					String tipoDocumento="";
					String codPartic="";

					partidaBean.setParticipanteDescripcion(rset.getString("RAZON_SOCIAL"));

					String nuDocIden = rset.getString("nu_doc");
					if (nuDocIden==null || nuDocIden.trim().length()==0)
						partidaBean.setParticipanteNumeroDocumento("&nbsp;");
					else
						partidaBean.setParticipanteNumeroDocumento(nuDocIden);

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
							if (dboTmDocIden.find() == true)
								partidaBean.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
						}
					}

					//descripción del tipo de participación
					dboParticLibro.clearAll();
					dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
					dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
					
					if (dboParticLibro.find()==true)
						partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));
						
					paginas = paginas + consultarPartidaDirectaSQLImpl.numeroPaginas(refNumPart);
					partidaBean.setNumeroPaginas(Double.toString(paginas));
					
					resultado.add(partidaBean);
					conta++;
					
					b = rset.next();
					if (conta==propiedades.getLineasPorPag())
					{
						if(b==true){	
							haySiguiente = true;
						}
						break;
					}			
				}//while (b==true)
				
			
				output.setResultado(resultado);
				
				if (inputBusqIndirectaBean.isFlagPagineoInferior()==false){
					output.setCantidadRegistros(String.valueOf(conteo));
				}else{
					output.setCantidadRegistros(inputBusqIndirectaBean.getCantidadInferior());
				}
	
				//calcular numero para boton "retroceder pagina"		
				if (inputBusqIndirectaBean.getSaltoInferior()==1){ // if (inputBusqIndirectaBean.getSalto()==1)
					output.setPagAnterior(-1);
				}else{
					output.setPagAnterior(inputBusqIndirectaBean.getSaltoInferior()-1);
				}
				
				//calcular numero para boton "avanzar pagina"
				if (haySiguiente==false){
					output.setPagSiguiente(-1);
				}else{
					output.setPagSiguiente(inputBusqIndirectaBean.getSaltoInferior()+1);
				}
	
				//calcular regs del x al y
				int del = ((inputBusqIndirectaBean.getSaltoInferior()-1)*propiedades.getLineasPorPag())+1;
				int al  = del+resultado.size()-1;
				output.setNdel(del);
				output.setNal(al);
				
			}else{
				output.setCantidadRegistros(String.valueOf(conteo));
				output.setPagAnterior(-1);
				output.setPagSiguiente(-1);
				output.setNdel(0);
				output.setNal(0);
			}
			if(inputBusqIndirectaBean.getFlagIncluirInactivos()== true){
				output.setFlagInactivo(true);
			}
	
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		
		
		return output;
	}

	public int contarBusquedaIndicePersonaJuridicaSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException {
		int contador=0;
		String razonSocial=null;
		String siglas=null;
		String verifica= null;
		
		PreparedStatement pstmt=null;
		ResultSet res=null;
		
		try {
			Propiedades propiedades = Propiedades.getInstance();
			StringBuffer sql=new StringBuffer();
			razonSocial=inputBusqIndirectaBean.getArea2Razon1();
			siglas=inputBusqIndirectaBean.getArea2Siglas();
			verifica=inputBusqIndirectaBean.getVerifica();
			
			sql.append( " SELECT COUNT(*) " +
					    " FROM  "+
					    " (SELECT DISTINCT " +
							" PARTIDA.ESTADO as ESTADO, REGIS_PUBLICO.SIGLAS as SIGLAS," +
							" PRTC_JUR.SIGLAS AS SIGLAS_JUR, OFIC_REGISTRAL.NOMBRE as NOMBRE," +
							" IND_PRTC.COD_PARTIC as COD_PARTIC,PRTC_JUR.RAZON_SOCIAL as RAZON_SOCIAL," +
							" PRTC_JUR.TI_DOC as TI_DOC,PRTC_JUR.NU_DOC as NU_DOC,PARTIDA.REFNUM_PART as REFNUM_PART," +
							" PARTIDA.NUM_PARTIDA as NUM_PARTIDA,PARTIDA.COD_LIBRO as COD_LIBRO,IND_PRTC.ESTADO AS estadoPartic," +
							" partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id," +
							" partida.area_reg_id as area_reg_id " +
						" FROM prtc_jur, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla," +
							" grupo_libro_area_det glad, asiento_garantia ag , asiento a" +
						" WHERE PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC" +
						" AND ind_prtc.refnum_part = partida.refnum_part" +
						" and a.refnum_part=ag.refnum_part (+)" +
						" and ag.refnum_part is null" +
						" and a.refnum_part=partida.refnum_part" +
						" AND IND_PRTC.REFNUM_PART=A.REFNUM_PART" +
						" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
						" AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID" +
						" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id" +
						" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
						" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id");
			
			if(verifica!= null){
				if (razonSocial!=null && razonSocial.length()>0){
					sql.append(" AND prtc_jur.RAZON_SOCIAL = '").append(razonSocial).append("'");
				}				
			}else{
				if (razonSocial!=null && razonSocial.length()>0){
					sql.append(" AND prtc_jur.RAZON_SOCIAL like '").append(razonSocial).append("%'");
				}
				if (siglas!=null && siglas.length()>0){
					sql.append(" and PRTC_JUR.SIGLAS like '").append(siglas).append("%'");
				}
				
			}
			sql.append(" and partida.estado !='2'" +
					" AND partida.cod_libro = glad.cod_libro" +
					" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
					" and gla.cod_grupo_libro_area ='28' " +
					" AND IND_PRTC.TIPO_PERS='J'");
			if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
				sql.append(" AND IND_PRTC.ESTADO = '1'");
			}
			sql.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')");
			
			if (isTrace(this)) System.out.println("___verquery_contarBusquedaIndicePersonaNaturalSIGCInterno__"+sql.toString());
			
			pstmt=conn.prepareStatement(sql.toString());
			res=pstmt.executeQuery();
			if(res.next()){
				contador=res.getInt(1);
			}
			
		} catch (Exception e) {
			
		} finally {
			JDBC.getInstance().closeResultSet(res);
			JDBC.getInstance().closeStatement(pstmt);
		}
		return contador;
	}

}
