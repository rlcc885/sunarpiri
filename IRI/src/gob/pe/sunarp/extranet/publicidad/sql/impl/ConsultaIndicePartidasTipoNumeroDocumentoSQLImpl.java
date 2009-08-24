package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.io.IOException;
import java.sql.Connection;
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
import gob.pe.sunarp.extranet.publicidad.sql.ConsultaIndicePartidasTipoNumeroDocumentoSQL;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class ConsultaIndicePartidasTipoNumeroDocumentoSQLImpl extends SQLImpl implements ConsultaIndicePartidasTipoNumeroDocumentoSQL, Constantes{
	private Connection conn;
	private DBConnection dbConn;
	
	public ConsultaIndicePartidasTipoNumeroDocumentoSQLImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
	
	public FormOutputBuscarPartida busquedaIndiceTipoNumeroDocumentoRmc(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean)
	    throws SQLException, CustomException, ValidacionException, DBException, IOException, ClassNotFoundException{
		
		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		Statement stmt   = null;
		Statement stmt2   = null;
		ResultSet rset   = null;
		ResultSet rset2   = null;
		try{
		
			Propiedades propiedades = Propiedades.getInstance();
			
			int conteo=0;
			if (inputBusqIndirectaBean.getFlagPagineo()==false)
			{
				conteo = ContarBusquedaIndiceTipoNumeroDocumentoRMC(inputBusqIndirectaBean);
				
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}else{
				conteo = Integer.parseInt(inputBusqIndirectaBean.getCantidad());
			}
			
			String numero_documento= inputBusqIndirectaBean.getNumeroDocumento();
			String tipo_documento= inputBusqIndirectaBean.getTipoDocumento();
			String codigo_grupo_libro_area= inputBusqIndirectaBean.getCodGrupoLibroArea();
			
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			StringBuffer sb = new StringBuffer();
			StringBuffer q  = new StringBuffer();
			StringBuffer qaux  = new StringBuffer();
			String tipoPers = ""; 
			
			qaux.append( " select td.tipo_per as tipo_pers  ");
			qaux.append( " from tm_doc_iden td ");
			qaux.append( " where td.tipo_doc_id = '").append(tipo_documento).append("' ");
			stmt2   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset2   = stmt2.executeQuery(qaux.toString());
			
			if (rset2.next()){
				tipoPers = rset2.getString("tipo_pers");
			}
			
			if (tipoPers.equals("N") || tipoPers.equals("A") ){
				q.append(" SELECT DISTINCT ");
				q.append("  PARTIDA.ESTADO as estado,  ");
				q.append("  partida.refnum_part as refnumpart, partida.cod_libro as cod_libro, partida.num_partida as num_partida,  ");
				q.append("  regis_publico.siglas as siglas, ofic_registral.nombre as nombre, prtc_nat.ape_pat as ape_pat, ");
				q.append("  prtc_nat.ape_mat as ape_mat, prtc_nat.nombres as nombres,'' as razon_social,  ");
				q.append("  prtc_nat.nu_doc_iden as numdocIden,prtc_nat.ti_doc_iden as tipodocIden,  ");
				q.append("  ind_prtc.TIPO_PERS AS tipoPersona,'' as cod_partic,");
				q.append("  partida.reg_pub_id as reg_pub_id, partida.ofic_reg_id as ofic_reg_id, partida.area_reg_id as area_reg_id ");
				q.append("FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico ,  ");
				q.append("     grupo_libro_area gla,grupo_libro_area_det glad ");
				q.append("WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc  ");
				q.append("  AND ind_prtc.refnum_part = partida.refnum_part  ");
				q.append("  AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
				q.append("  AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID  ");
				q.append("  AND partida.ofic_reg_id = ofic_registral.ofic_reg_id  ");
				q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
				q.append("  AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id  ");
				/** inicio: ifigueroa 17/08/2007******/
				 if (inputBusqIndirectaBean.getVerifica()!= null)
					 q.append("  and prtc_nat.nu_doc_iden = '").append(numero_documento).append("'");
				 else
				 /** fin: ifigueroa 17/08/2007******/
				q.append("  and prtc_nat.nu_doc_iden like '").append(numero_documento).append("%'");
				q.append("  and prtc_nat.ti_doc_iden = '").append(tipo_documento).append("' ");
				q.append("  and PARTIDA.ESTADO = '1'  ");
				q.append("  AND partida.cod_libro = glad.cod_libro   ");
				q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
				q.append("  and gla.cod_grupo_libro_area = '").append(codigo_grupo_libro_area).append("' ");
				q.append("  AND IND_PRTC.ESTADO = '1' ");
				q.append("  AND IND_PRTC.TIPO_PERS='N' ");
				q.append("  AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'");
				if (tipoPers.equals("N")){
					q.append("ORDER BY num_partida,siglas, nombre ");
				}	
			}
			if (tipoPers.equals("J") || tipoPers.equals("A") ){
				if (tipoPers.equals("A")){
					q.append("union ");
				}	
				q.append("select DISTINCT ");
				q.append("  PARTIDA.ESTADO as estado,  ");
				q.append("  PARTIDA.REFNUM_PART as refnumpart, PARTIDA.COD_LIBRO as cod_libro, PARTIDA.NUM_PARTIDA as num_partida,  ");
				q.append("  REGIS_PUBLICO.SIGLAS as siglas ,OFIC_REGISTRAL.NOMBRE as nombre, '' as ape_pat, ");
				q.append("  '' as ape_mat, '' AS nombres, PRTC_JUR.RAZON_SOCIAL as razon_social, PRTC_JUR.NU_DOC as numdocIden, ");
				q.append("  PRTC_JUR.TI_DOC as tipodocIden, ind_prtc.TIPO_PERS AS tipoPersona,IND_PRTC.COD_PARTIC as cod_partic, ");
				q.append("  partida.reg_pub_id as reg_pub_id, partida.ofic_reg_id as ofic_reg_id, partida.area_reg_id as area_reg_id  ");
				q.append("FROM prtc_jur, IND_PRTC, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO,  ");
				q.append("     grupo_libro_area gla, grupo_libro_area_det glad  ");
				q.append("WHERE  ");
				q.append("  PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC  ");
				q.append("  AND IND_PRTC.REFNUM_PART=PARTIDA.REFNUM_PART ");
				q.append("  AND partida.ofic_reg_id = prtc_jur.ofic_reg_id  ");
				q.append("  AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID   ");
				q.append("  AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID  ");
				q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
				q.append("  AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
				q.append("  AND prtc_jur.ti_doc = '").append(tipo_documento).append("'");
				/** inicio: ifigueroa 17/08/2007******/
				 if (inputBusqIndirectaBean.getVerifica()!= null)
					 q.append("  and prtc_jur.nu_doc = '").append(numero_documento).append("' ");
				 else
				 /** fin: ifigueroa 17/08/2007******/
				q.append("  and prtc_jur.nu_doc like '").append(numero_documento).append("%' ");
				q.append("  and PARTIDA.ESTADO = '1'  ");
				q.append("  AND partida.cod_libro = glad.cod_libro  ");
				q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
				q.append("  and gla.cod_grupo_libro_area = '").append(codigo_grupo_libro_area).append("' ");
				q.append("  AND IND_PRTC.ESTADO = '1' ");
				q.append("  AND IND_PRTC.TIPO_PERS='J' ");
				q.append("  AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'");
				q.append(" ORDER BY num_partida,siglas, nombre ");
			}	
			
             if (isTrace(this)) System.out.println("___verquery_busquedaIndiceTipoNumeroDocumentoRmc__"+q.toString());
 			
 			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
 			stmt.setFetchSize(propiedades.getLineasPorPag());
 			rset   = stmt.executeQuery(q.toString());
 			
 			//Inicio:mgarate:27/08/2007
 			//si el medio de acceso es por web service no realiza la paginacion
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
			StringBuffer cadenaPart = new StringBuffer();
			
			while (b==true)
			{
				PartidaBean partidaBean = new PartidaBean();
				
				String refNumPart = rset.getString("refnumpart");
				String oficRegDesc = rset.getString("nombre");
				String codLibro   = rset.getString("cod_libro");
                String tipoPersona= rset.getString("tipoPersona");
                
				partidaBean.setRefNumPart(refNumPart);
				partidaBean.setCodLibro(codLibro);
				partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
				partidaBean.setTipoPersona(tipoPersona);
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
					partidaBean.setTipoPersona(tipoPersona);
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
						partidaBean.setTipoPersona(tipoPersona);
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

                // se setea la descripcion de la participacion dependiendo de tipo de persona
				if (tipoPersona.equals("N")){
					sb.delete(0,sb.length());
					String ape_mat = rset.getString("ape_mat")==null?"":rset.getString("ape_mat").trim();
					String nombres = rset.getString("nombres")==null?"":rset.getString("nombres").trim();
					sb.append(rset.getString("ape_pat")==null?"":rset.getString("ape_pat").trim()).append(" ");
					sb.append(ape_mat).append(", ");
					sb.append(nombres);
					String nombreAct = sb.toString();
					
					cadenaPart.delete(0,cadenaPart.length());
					cadenaPart.append(nombreAct);
					partidaBean.setParticipanteDescripcion(cadenaPart.toString());
					
				}else{
					if (tipoPersona.equals("J")){
						partidaBean.setParticipanteDescripcion(rset.getString("RAZON_SOCIAL"));						
					}
				}
				
				//participante y su número de documento
				String tipoDocumento="";
				String codPartic="";

				
				String nuDocIden = rset.getString("numdocIden");
				if ((nuDocIden==null) || (nuDocIden.trim().length()==0))
					partidaBean.setParticipanteNumeroDocumento("&nbsp;");
				else
					partidaBean.setParticipanteNumeroDocumento(nuDocIden);


				tipoDocumento = rset.getString("tipodocIden");
				codPartic     = rset.getString("cod_partic");
				
				//descripción de documento
				if (tipoDocumento!=null)
				{
					if (tipoDocumento.trim().length()>0)
					{
						dboTmDocIden.clearAll();
						dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
						dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
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
		if (inputBusqIndirectaBean.getSalto()==1){
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
		
	}finally {
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
	
	return output;   
		
}
	
	public int ContarBusquedaIndiceTipoNumeroDocumentoRMC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException{
		int conteo=0;
		Statement stmt   = null;
		ResultSet rset   = null;
		Statement stmt2   = null;
		ResultSet rset2   = null;
		try{
			Propiedades propiedades = Propiedades.getInstance();
			StringBuffer q = new StringBuffer();	
			StringBuffer qaux  = new StringBuffer();
			String numero_documento= inputBusqIndirectaBean.getNumeroDocumento();
			String tipo_documento= inputBusqIndirectaBean.getTipoDocumento();
			String codigo_grupo_libro_area= inputBusqIndirectaBean.getCodGrupoLibroArea();
			String tipoPers = ""; 
			
			qaux.append( " select td.tipo_per as tipo_pers  ");
			qaux.append( " from tm_doc_iden td ");
			qaux.append( " where td.tipo_doc_id = '").append(tipo_documento).append("' ");
			stmt2   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rset2   = stmt2.executeQuery(qaux.toString());
			
			if (rset2.next()){
				tipoPers = rset2.getString("tipo_pers");
			}
			
			if (tipoPers.equals("N") || tipoPers.equals("A") ){
				q.append("  SELECT COUNT(*) ");
				q.append("  FROM ");
				q.append(" (SELECT DISTINCT ");
				q.append("  PARTIDA.ESTADO as estado,  ");
				q.append("  partida.refnum_part as refnumpart, partida.cod_libro as cod_libro, partida.num_partida as num_partida,  ");
				q.append("  regis_publico.siglas as siglas, ofic_registral.nombre as nombre, prtc_nat.ape_pat as ape_pat, ");
				q.append("  prtc_nat.ape_mat as ape_mat, prtc_nat.nombres as nombres,'' as razon_social,  ");
				q.append("  prtc_nat.nu_doc_iden as numdocIden,prtc_nat.ti_doc_iden as tipodocIden,  ");
				q.append("  ind_prtc.TIPO_PERS AS tipoPersona,'' as cod_partic,");
				q.append("  partida.reg_pub_id as reg_pub_id, partida.ofic_reg_id as ofic_reg_id, partida.area_reg_id as area_reg_id ");
				q.append("FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico ,  ");
				q.append("     grupo_libro_area gla,grupo_libro_area_det glad ");
				q.append("WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc  ");
				q.append("  AND ind_prtc.refnum_part = partida.refnum_part  ");
				q.append("  AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
				q.append("  AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID  ");
				q.append("  AND partida.ofic_reg_id = ofic_registral.ofic_reg_id  ");
				q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
				q.append("  AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id  ");
				/** inicio: ifigueroa 17/08/2007******/
				 if (inputBusqIndirectaBean.getVerifica()!= null)
					 q.append("  and prtc_nat.nu_doc_iden = '").append(numero_documento).append("'");
				 else
				 /** fin: ifigueroa 17/08/2007******/
				q.append("  and prtc_nat.nu_doc_iden like '").append(numero_documento).append("%'");
				q.append("  and prtc_nat.ti_doc_iden = '").append(tipo_documento).append("' ");
				q.append("  and PARTIDA.ESTADO = '1'  ");
				q.append("  AND partida.cod_libro = glad.cod_libro   ");
				q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
				q.append("  and gla.cod_grupo_libro_area = '").append(codigo_grupo_libro_area).append("' ");
				q.append("  AND IND_PRTC.ESTADO = '1' ");
				q.append("  AND IND_PRTC.TIPO_PERS='N'");
				/** inicio: Jrosas: 07-09-2007 **/
				q.append("   AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')");
				/** fin: Jrosas: 07-09-2007 **/		
			}
			if (tipoPers.equals("J") || tipoPers.equals("A") ){
				if (tipoPers.equals("A")){
					q.append("union ");
				}
				q.append("  SELECT COUNT(*) ");
				q.append("  FROM ");
				q.append(" (select DISTINCT ");
				q.append("  PARTIDA.ESTADO as estado,  ");
				q.append("  PARTIDA.REFNUM_PART as refnumpart, PARTIDA.COD_LIBRO as cod_libro, PARTIDA.NUM_PARTIDA as num_partida,  ");
				q.append("  REGIS_PUBLICO.SIGLAS as siglas ,OFIC_REGISTRAL.NOMBRE as nombre, '' as ape_pat, ");
				q.append("  '' as ape_mat, '' AS nombres, PRTC_JUR.RAZON_SOCIAL as razon_social, PRTC_JUR.NU_DOC as numdocIden, ");
				q.append("  PRTC_JUR.TI_DOC as tipodocIden, ind_prtc.TIPO_PERS AS tipoPersona,IND_PRTC.COD_PARTIC as cod_partic, ");
				q.append("  partida.reg_pub_id as reg_pub_id, partida.ofic_reg_id as ofic_reg_id, partida.area_reg_id as area_reg_id  ");
				q.append("FROM prtc_jur, IND_PRTC, PARTIDA, OFIC_REGISTRAL, REGIS_PUBLICO,  ");
				q.append("     grupo_libro_area gla, grupo_libro_area_det glad  ");
				q.append("WHERE  ");
				q.append("  PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC  ");
				q.append("  AND IND_PRTC.REFNUM_PART=PARTIDA.REFNUM_PART ");
				q.append("  AND partida.ofic_reg_id = prtc_jur.ofic_reg_id  ");
				q.append("  AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID   ");
				q.append("  AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID  ");
				q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
				q.append("  AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
				/** inicio: ifigueroa 17/08/2007******/
				 if (inputBusqIndirectaBean.getVerifica()!= null)
					 q.append("  and prtc_jur.nu_doc = '").append(numero_documento).append("' ");
				 else
				 /** fin: ifigueroa 17/08/2007******/
				q.append("  AND prtc_jur.nu_doc like '").append(numero_documento).append("%'");
				q.append("  and prtc_jur.ti_doc = '").append(tipo_documento).append("' ");
				q.append("  and PARTIDA.ESTADO = '1'  ");
				q.append("  AND partida.cod_libro = glad.cod_libro  ");
				q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
				q.append("  and gla.cod_grupo_libro_area = '").append(codigo_grupo_libro_area).append("' ");
				q.append("  AND IND_PRTC.ESTADO = '1' ");
				q.append("  AND IND_PRTC.TIPO_PERS='J' ");
				/** inicio: Jrosas: 07-09-2007 **/
				q.append("   AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')");
				/** fin: Jrosas: 07-09-2007 **/		
			}	

			if (isTrace(this)) System.out.println("___verquery_ContarBusquedaIndiceTipoNumeroDocumentoRMC_A__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			//boolean bc = rset.next();
			while(rset.next()){
				conteo += rset.getInt(1);
			}
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return conteo;
	}
	

	private void appendCondicionEstadoPartida(StringBuffer q) {

		q.append(" and PARTIDA.ESTADO = '1' ");
		
	}
}
