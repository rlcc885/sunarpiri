package gob.pe.sunarp.extranet.publicidad.sql.impl;

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
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarNacionalIndicePartidasPersonaNaturalSQL;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class ConsultarNacionalIndicePartidasPersonaNaturalSQLImpl extends SQLImpl
implements ConsultarNacionalIndicePartidasPersonaNaturalSQL,Constantes{
	
	private Connection conn;
	private DBConnection dbConn;


	public ConsultarNacionalIndicePartidasPersonaNaturalSQLImpl(Connection conn, DBConnection dbConn) {
		this.conn = conn;
		this.dbConn = dbConn;
	}

	
	public FormOutputBuscarPartida busquedaIndicePersonaNaturalSIGC(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean, boolean flagUsuarioInterno, String idSession)
	throws SQLException, CustomException, ValidacionException, DBException,IOException, ClassNotFoundException {
		
		FormOutputBuscarPartida output	= new FormOutputBuscarPartida();
		Statement stmt					= null;
		ResultSet rset					= null;
		String apellidoPaterno			= null;
		String apellidoMaterno			= null;
		String nombres					= null;
		boolean continua				= false;
		String verifica					= null;
		try {
			
			Propiedades propiedades=Propiedades.getInstance();
			int conteo=0;
			if (inputBusqIndirectaBean.getFlagPagineo()==false)
			{
				conteo=contarBusquedaIndicePersonaNaturalSIGC(inputBusqIndirectaBean);
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
			
			apellidoPaterno=inputBusqIndirectaBean.getArea1ApePat();
			apellidoMaterno=inputBusqIndirectaBean.getArea1ApeMat();
			verifica=inputBusqIndirectaBean.getVerifica();
			ArrayList resultado=new ArrayList();
			nombres=inputBusqIndirectaBean.getArea1Nombre();
			StringBuffer sql=new StringBuffer();
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			int salto= inputBusqIndirectaBean.getSalto();
			boolean haySiguiente = false;
			StringBuffer variableDinamica=new StringBuffer();
			
			sql.append(" SELECT DISTINCT ")
			   .append(" PARTIDA.ESTADO as estado, partida.refnum_part as refnum_part,partida.cod_libro as cod_libro,partida.num_partida as num_partida,")
			   .append(" regis_publico.siglas as siglas,ofic_registral.nombre as nombre,prtc_nat.ape_pat as ape_pat,prtc_nat.ape_mat as ape_mat,")
			   .append(" prtc_nat.nombres as nombres,prtc_nat.nu_doc_iden as nu_doc_iden,prtc_nat.ti_doc_iden as ti_doc_iden, ")
			   .append(" ind_prtc.estado AS estadoPartic,partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id,partida.area_reg_id as area_reg_id ") 
			   .append(" FROM ")
			   .append(" prtc_nat,ind_prtc,partida,ofic_registral,regis_publico,grupo_libro_area gla,")
			   .append(" grupo_libro_area_det glad, asiento_garantia ag ")
			   .append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ")
			   .append(" AND ind_prtc.refnum_part = partida.refnum_part ")
			   .append(" and ag.refnum_part=partida.refnum_part ")
			   .append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id ")
			   .append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ")
			   .append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ")
			   .append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ")
			   .append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
				   
				if(verifica!=null){
					sql.append(" and PRTC_NAT.ape_pat ='").append(apellidoPaterno).append("' ");
				}else{
					sql.append(" and PRTC_NAT.ape_pat like '").append(apellidoPaterno).append("%' ");
				}
				
				if(verifica!=null){
					if(apellidoMaterno!=null && apellidoMaterno.length()>0){
						sql.append(" and PRTC_NAT.ape_mat = '").append(apellidoMaterno).append("' ");
					}
				}else{
					if(apellidoMaterno!=null && apellidoMaterno.length()>0){
						sql.append(" and PRTC_NAT.ape_mat like '").append(apellidoMaterno).append("%' ");
					}
				}
				
				if(verifica!=null){
					if(nombres!=null && nombres.length()>0){
						sql.append(" and prtc_nat.nombres = '").append(nombres).append("' ");
					}
				}else{
					if(nombres!=null && nombres.length()>0){
						sql.append(" and prtc_nat.nombres like '").append(nombres).append("%' ");
					}
				}
				
				sql.append(" and partida.estado !=2 ")
				   .append(" AND partida.cod_libro = glad.cod_libro ")
				   .append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ")
				   .append(" and gla.cod_grupo_libro_area ='28' ")
				   .append(" AND IND_PRTC.ESTADO = '1' ")
				   .append(" AND IND_PRTC.TIPO_PERS='N' ")
				   .append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'")
				   .append(" ORDER BY ape_pat, ape_mat, nombres, estado DESC,REG_PUB_ID,ofic_reg_id,num_partida");
				
				if (isTrace(this)) System.out.println("___verquery_busquedaIndicePersonaNaturalSIGC__"+sql.toString());
				
				stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(propiedades.getLineasPorPag());
				rset=stmt.executeQuery(sql.toString());
				
				//descripcion area registral
				
				DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(this.dbConn);
				dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
				dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,inputBusqIndirectaBean.getComboArea());
				dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
				String descripcionAreaRegistral=null;
				
				if (dboTmAreaRegistral.find() == true)
					descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

				DboFicha dboFicha = new DboFicha(this.dbConn);
				DboTomoFolio dboTomoFolio = new DboTomoFolio(this.dbConn);
				DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
				DboPartida dboPartida = new DboPartida(this.dbConn);
				DboTmDocIden dboTmDocIden = new DboTmDocIden(this.dbConn);
				DboParticLibro dboParticLibro = new DboParticLibro(this.dbConn);
		
		
				int conta=0;
				boolean nuevo = true;
				String antRefNumPart 		= "";
				String antRegPubDescripcion = "";
				String antnombre 			= "";
				String antEstadoAct 		= "";
				StringBuffer cadenaPart 	= new StringBuffer();
				
				//variables para usar dentro del siguiente while
				String refNumPart 	= null;
				String oficRegDesc 	= null;
				String codLibro   	= null;
				String estadoAct 	= null;
				apellidoMaterno		= null;
				nombres				= null;
				String nombreAct	= null;
				int contador=0;
				boolean b = rset.next();
				while(b == true){
				
					PartidaBean partidaBean = new PartidaBean();
					contador++;
					refNumPart = rset.getString("refnum_part");
					oficRegDesc = rset.getString("nombre");
					codLibro   = rset.getString("cod_libro");
					estadoAct = null;
					
					if ((rset.getString("estadoPartic")!=null)&&(rset.getString("estadoPartic").startsWith("1")))
					{
						estadoAct = "Activo";
					}
					else
					{
						estadoAct = "Inactivo";
					}

					variableDinamica.delete(0,variableDinamica.length());
					
					apellidoMaterno= rset.getString("ape_mat")==null?"":rset.getString("ape_mat").trim();
					nombres = rset.getString("nombres")==null?"":rset.getString("nombres").trim();
					variableDinamica.append(rset.getString("ape_pat")==null?"":rset.getString("ape_pat").trim()).append(" ");
					variableDinamica.append(apellidoMaterno).append(", ");
					variableDinamica.append(nombres);
					nombreAct = variableDinamica.toString();
					partidaBean.setParticipanteDescripcion(nombreAct);
					
					antRefNumPart = refNumPart;
					antRegPubDescripcion = oficRegDesc;
					antnombre = nombreAct;
					antEstadoAct = estadoAct;

					cadenaPart.delete(0,cadenaPart.length());
					cadenaPart.append(nombreAct);
					nuevo = true;
					
					partidaBean.setRefNumPart(refNumPart);
					partidaBean.setAreaRegistralDescripcion(descripcionAreaRegistral);
					partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setRegPubDescripcion(rset.getString("siglas"));
					partidaBean.setOficRegDescripcion(oficRegDesc);
					partidaBean.setEstado(rset.getString("estado"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setEstadoInd(estadoAct);
					
					//---
//						dbravo: Recupera el refnum_part de la partida migrada si existiera
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
					variableDinamica.delete(0, variableDinamica.length());
					variableDinamica.append(dboFicha.CAMPO_FICHA).append("|");
					variableDinamica.append(dboFicha.CAMPO_FICHA_BIS);
					dboFicha.setFieldsToRetrieve(variableDinamica.toString());
					dboFicha.setField(dboFicha.CAMPO_REFNUM_PART, refNumPart);
					
					if (dboFicha.find() == true){
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
					variableDinamica.delete(0,variableDinamica.length());
					variableDinamica.append(DboTomoFolio.CAMPO_NU_TOMO).append("|");
					variableDinamica.append(DboTomoFolio.CAMPO_NU_FOJA).append("|");
					variableDinamica.append(DboTomoFolio.CAMPO_TOMO_BIS).append("|");
					variableDinamica.append(DboTomoFolio.CAMPO_FOLIO_BIS);
					dboTomoFolio.setFieldsToRetrieve(variableDinamica.toString());
					dboTomoFolio.setField(dboTomoFolio.CAMPO_REFNUM_PART, refNumPart);
					if (dboTomoFolio.find() == true){
						partidaBean.setTomoId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_TOMO));
						partidaBean.setFojaId(dboTomoFolio.getField(DboTomoFolio.CAMPO_NU_FOJA));

						String bist = dboTomoFolio.getField(DboTomoFolio.CAMPO_TOMO_BIS);
						String bisf = dboTomoFolio.getField(DboTomoFolio.CAMPO_FOLIO_BIS);

						if (bist.trim().length() > 0)
								partidaBean.setTomoId(partidaBean.getTomoId() + "-" + bist);

						if (bisf.trim().length() > 0)
								partidaBean.setFojaId(partidaBean.getFojaId() + "-" + bisf);
					
						if (partidaBean.getTomoId().length()>0){
							if (partidaBean.getTomoId().startsWith("9")){
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

					String nuDocIden = rset.getString("nu_doc_iden");
					
					if ((nuDocIden==null) || (nuDocIden.trim().length()==0))
						partidaBean.setParticipanteNumeroDocumento("&nbsp;");
					else
						partidaBean.setParticipanteNumeroDocumento(nuDocIden);

					tipoDocumento = rset.getString("ti_doc_iden");
					//codPartic     = rset.getString("cod_partic");

					//descripción de documento
					if (tipoDocumento!=null){
						if (tipoDocumento.trim().length()>0){
							dboTmDocIden.clearAll();
							dboTmDocIden.setFieldsToRetrieve(DboTmDocIden.CAMPO_NOMBRE_ABREV);
							dboTmDocIden.setField(DboTmDocIden.CAMPO_TIPO_DOC_ID, tipoDocumento);
							dboTmDocIden.setField(DboTmDocIden.CAMPO_ESTADO, "1");
							if (dboTmDocIden.find() == true)
								partidaBean.setParticipanteTipoDocumentoDescripcion(dboTmDocIden.getField(DboTmDocIden.CAMPO_NOMBRE_ABREV));
						}
					}

					//descripción del tipo de participación
					/*dboParticLibro.clearAll();
					dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
					dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
					dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
						if (dboParticLibro.find()==true)
							partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));*/
					
					paginas = paginas + consultarPartidaDirectaSQLImpl.numeroPaginas(refNumPart);
					partidaBean.setNumeroPaginas(Double.toString(paginas));

					resultado.add(partidaBean);
					conta++;
					b=rset.next();
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

						
			//*****	
				
				}
				
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
		 
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return output;
	}

	public int contarBusquedaIndicePersonaNaturalSIGC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException {
		int contador=0;
		String apellidoPaterno	= null;
		String apellidoMaterno	= null;
		String nombre			= null;
		String verifica			= null;
		PreparedStatement pstmt	= null;
		ResultSet res			= null;
		
		try {
			Propiedades propiedades = Propiedades.getInstance();
			StringBuffer sql=new StringBuffer();
			apellidoPaterno=inputBusqIndirectaBean.getArea1ApePat();
			apellidoMaterno=inputBusqIndirectaBean.getArea1ApeMat();
			nombre=inputBusqIndirectaBean.getArea1Nombre();
			verifica= inputBusqIndirectaBean.getVerifica();
			
			sql.append(" SELECT COUNT(*) ")
			   .append(" FROM  ")
			   .append(" (SELECT DISTINCT  ")
			   .append("  PARTIDA.ESTADO as estado, partida.refnum_part as refnum_part,partida.cod_libro as cod_libro,partida.num_partida as num_partida, ")
			   .append("  regis_publico.siglas as siglas,ofic_registral.nombre as nombre,prtc_nat.ape_pat as ape_pat,prtc_nat.ape_mat as ape_mat, ")
			   .append("  prtc_nat.nombres as nombres,prtc_nat.nu_doc_iden as nu_doc_iden,prtc_nat.ti_doc_iden as ti_doc_iden, ")
			   .append("  ind_prtc.estado AS estadoPartic,partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id,partida.area_reg_id as area_reg_id ")
			   .append(" FROM ")
			   .append(" prtc_nat,ind_prtc,partida,ofic_registral,regis_publico,grupo_libro_area gla,")
			   .append(" grupo_libro_area_det glad, asiento_garantia ag ")
			   .append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ")
			   .append(" AND ind_prtc.refnum_part = partida.refnum_part ")
			   .append(" and ag.refnum_part=partida.refnum_part ")
			   .append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id ")
			   .append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ")
			   .append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ")
			   .append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ")
			   .append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
			
			   /**** inicio: jrosas 10-08-07 ***/
				if(verifica!=null){
					sql.append(" and PRTC_NAT.ape_pat ='").append(apellidoPaterno).append("' ");
				}else{
					sql.append(" and PRTC_NAT.ape_pat like '").append(apellidoPaterno).append("%' ");
				}
				
				if(verifica!=null){
					if(apellidoMaterno!=null && apellidoMaterno.length()>0){
						sql.append(" and PRTC_NAT.ape_mat = '").append(apellidoMaterno).append("' ");
					}
				}else{
					if(apellidoMaterno!=null && apellidoMaterno.length()>0){
						sql.append(" and PRTC_NAT.ape_mat like '").append(apellidoMaterno).append("%' ");
					}
				}
				
				if(verifica!=null){
					if(nombre!=null && nombre.length()>0){
						sql.append(" and prtc_nat.nombres = '").append(nombre).append("' ");
					}
				}else{
					if(nombre!=null && nombre.length()>0){
						sql.append(" and prtc_nat.nombres like '").append(nombre).append("%' ");
					}
				}
				
				/**** fin: jrosas 10-08-07 ***/
			sql.append(" and partida.estado !=2 ")
			   .append(" AND partida.cod_libro = glad.cod_libro ")
			   .append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ")
			   .append(" and gla.cod_grupo_libro_area ='28' ")
			   .append(" AND IND_PRTC.ESTADO = '1' ")
			   .append(" AND IND_PRTC.TIPO_PERS='N' ")
			   .append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')");
			   
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
	

	private void appendCondicionEstadoPartida(StringBuffer q) {

		q.append(" and PARTIDA.ESTADO != '2' ");
		
	}
	
	public int contarBusquedaIndicePersonaNaturalSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException {
		int contador=0;
		String apellidoPaterno	= null;
		String apellidoMaterno	= null;
		String nombre			= null;
		String verifica			= null;
		PreparedStatement pstmt	= null;
		ResultSet res			= null;
		
		try {
			Propiedades propiedades = Propiedades.getInstance();
			StringBuffer sql=new StringBuffer();
			apellidoPaterno=inputBusqIndirectaBean.getArea1ApePat();
			apellidoMaterno=inputBusqIndirectaBean.getArea1ApeMat();
			nombre=inputBusqIndirectaBean.getArea1Nombre();
			verifica=inputBusqIndirectaBean.getVerifica();
			sql.append(" SELECT COUNT(*) ")
			   .append(" FROM  ")
			   .append(" (SELECT DISTINCT  ")
			   .append("  PARTIDA.ESTADO as estado, partida.refnum_part as refnum_part,partida.cod_libro as cod_libro,partida.num_partida as num_partida, ")
			   .append("  regis_publico.siglas as siglas,ofic_registral.nombre as nombre,prtc_nat.ape_pat as ape_pat,prtc_nat.ape_mat as ape_mat, ")
			   .append("  prtc_nat.nombres as nombres,prtc_nat.nu_doc_iden as nu_doc_iden,prtc_nat.ti_doc_iden as ti_doc_iden, ")
			   .append("  ind_prtc.estado AS estadoPartic,partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id,partida.area_reg_id as area_reg_id ")			
			   .append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla,")
			   .append(" grupo_libro_area_det glad, asiento_garantia ag , asiento a")
			   .append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ")
			   .append(" AND ind_prtc.refnum_part = partida.refnum_part ")
			   .append(" and a.refnum_part=ag.refnum_part (+) ")
			   .append(" and ag.refnum_part is null ")
			   .append(" and a.refnum_part=partida.refnum_part ")
			   .append(" AND IND_PRTC.REFNUM_PART=A.REFNUM_PART ")
			   .append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id ")
			   .append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ")
			   .append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ")
			   .append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ")
			   .append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
			
			   /**** inicio: jrosas 10-08-07 ***/
				if(verifica!=null){
					sql.append(" and PRTC_NAT.ape_pat ='").append(apellidoPaterno).append("' ");
				}else{
					sql.append(" and PRTC_NAT.ape_pat like '").append(apellidoPaterno).append("%' ");
				}
				
				if(verifica!=null){
					if(apellidoMaterno!=null && apellidoMaterno.length()>0){
						sql.append(" and PRTC_NAT.ape_mat = '").append(apellidoMaterno).append("' ");
					}
				}else{
					if(apellidoMaterno!=null && apellidoMaterno.length()>0){
						sql.append(" and PRTC_NAT.ape_mat like '").append(apellidoMaterno).append("%' ");
					}
				}
				
				if(verifica!=null){
					if(nombre!=null && nombre.length()>0){
						sql.append(" and prtc_nat.nombres = '").append(nombre).append("' ");
					}
				}else{
					if(nombre!=null && nombre.length()>0){
						sql.append(" and prtc_nat.nombres like '").append(nombre).append("%' ");
					}
				}
				
				/**** fin: jrosas 10-08-07 ***/
			sql.append(" and partida.estado !='2' ")
			   .append(" AND partida.cod_libro = glad.cod_libro ")
			   .append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ")
			   .append(" and gla.cod_grupo_libro_area ='28' ")
			   .append(" AND IND_PRTC.TIPO_PERS='N'");
			if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
				sql.append(" AND IND_PRTC.ESTADO = '1' ");
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
	

	public FormOutputBuscarPartida busquedaIndicePersonaNaturalSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean, String idSession)
	throws SQLException, CustomException, ValidacionException, DBException,IOException, ClassNotFoundException {
		
		FormOutputBuscarPartida output	= new FormOutputBuscarPartida();
		Statement stmt					= null;
		ResultSet rset					= null;
		String apellidoPaterno			= null;
		String apellidoMaterno			= null;
		String nombres					= null;
		String verifica					= null;
		try {
			
			
			Propiedades propiedades=Propiedades.getInstance();
			int conteo=0;
			if(inputBusqIndirectaBean.isFlagPagineoInferior()==false){
				conteo=contarBusquedaIndicePersonaNaturalSIGCInterno(inputBusqIndirectaBean);
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
			}else{
				conteo = Integer.parseInt(inputBusqIndirectaBean.getCantidadInferior());
			}
			
			if(conteo > 0){
				apellidoPaterno=inputBusqIndirectaBean.getArea1ApePat();
				apellidoMaterno=inputBusqIndirectaBean.getArea1ApeMat();
				nombres=inputBusqIndirectaBean.getArea1Nombre();
				verifica=inputBusqIndirectaBean.getVerifica();
				
				ArrayList resultado=new ArrayList();
				StringBuffer sql=new StringBuffer();
				ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
				int salto= inputBusqIndirectaBean.getSaltoInferior();
				boolean haySiguiente = false;
				StringBuffer variableDinamica=new StringBuffer();
				StringBuffer sb = new StringBuffer();
				
					
					sql.append(" SELECT DISTINCT ")
					   .append(" PARTIDA.ESTADO as estado, partida.refnum_part as refnum_part,partida.cod_libro as cod_libro,partida.num_partida as num_partida,")
					   .append(" regis_publico.siglas as siglas,ofic_registral.nombre as nombre,prtc_nat.ape_pat as ape_pat,prtc_nat.ape_mat as ape_mat,")
					   .append(" prtc_nat.nombres as nombres,prtc_nat.nu_doc_iden as nu_doc_iden,prtc_nat.ti_doc_iden as ti_doc_iden, ")
					   .append(" ind_prtc.estado AS estadoPartic,partida.reg_pub_id as reg_pub_id,partida.ofic_reg_id as ofic_reg_id,partida.area_reg_id as area_reg_id ") 
					   .append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla,")
					   .append(" grupo_libro_area_det glad, asiento_garantia ag , asiento a ")
					   .append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ")
					   .append(" AND ind_prtc.refnum_part = partida.refnum_part ")
					   .append(" and a.refnum_part=ag.refnum_part (+) ")
					   .append(" and ag.refnum_part is null ")
					   .append(" and a.refnum_part=partida.refnum_part ")
					   .append(" AND IND_PRTC.REFNUM_PART=A.REFNUM_PART ")
					   .append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id ")
					   .append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ")
					   .append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ")
					   .append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ")
					   .append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
					
					   /**** inicio: jrosas 10-08-07 ***/
						if(verifica!=null){
							sql.append(" and PRTC_NAT.ape_pat ='").append(apellidoPaterno).append("' ");
						}else{
							sql.append(" and PRTC_NAT.ape_pat like '").append(apellidoPaterno).append("%' ");
						}
						
						if(verifica!=null){
							if(apellidoMaterno!=null && apellidoMaterno.length()>0){
								sql.append(" and PRTC_NAT.ape_mat = '").append(apellidoMaterno).append("' ");
							}
						}else{
							if(apellidoMaterno!=null && apellidoMaterno.length()>0){
								sql.append(" and PRTC_NAT.ape_mat like '").append(apellidoMaterno).append("%' ");
							}
						}
						
						if(verifica!=null){
							if(nombres!=null && nombres.length()>0){
								sql.append(" and prtc_nat.nombres = '").append(nombres).append("' ");
							}
						}else{
							if(nombres!=null && nombres.length()>0){
								sql.append(" and prtc_nat.nombres like '").append(nombres).append("%' ");
							}
						}
						
						/**** fin: jrosas 10-08-07 ***/
					sql.append(" and partida.estado !='2' ")
					   .append(" AND partida.cod_libro = glad.cod_libro ")
					   .append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ")
					   .append(" and gla.cod_grupo_libro_area ='28' ")
					   .append(" AND IND_PRTC.TIPO_PERS='N'")
					   .append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'");
					if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
						sql.append(" AND IND_PRTC.ESTADO = '1' ");
					}
					
					sql.append(" ORDER BY ape_pat, ape_mat, nombres, estado DESC,REG_PUB_ID,ofic_reg_id,num_partida");
					
					if (isTrace(this)) System.out.println("___verquery_busquedaIndicePersonaNaturalSIGCInterno__"+sql.toString());
					
					stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(propiedades.getLineasPorPag());
					rset=stmt.executeQuery(sql.toString());
					
					if (inputBusqIndirectaBean.getSaltoInferior()>1){ 
		 				rset.absolute(propiedades.getLineasPorPag() * (inputBusqIndirectaBean.getSaltoInferior() - 1));
		 			} 
					
					//descripcion area registral
					boolean b = rset.next();
					
					DboTmAreaRegistral dboTmAreaRegistral = new  DboTmAreaRegistral(this.dbConn);
					dboTmAreaRegistral.setFieldsToRetrieve(dboTmAreaRegistral.CAMPO_NOMBRE);
					dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_AREA_REG_ID,inputBusqIndirectaBean.getComboArea());
					dboTmAreaRegistral.setField(dboTmAreaRegistral.CAMPO_ESTADO,"1");
					String descripcionAreaRegistral=null;
					
					if (dboTmAreaRegistral.find() == true)
						descripcionAreaRegistral = dboTmAreaRegistral.getField(dboTmAreaRegistral.CAMPO_NOMBRE);

					DboFicha dboFicha = new DboFicha(this.dbConn);
					DboTomoFolio dboTomoFolio = new DboTomoFolio(this.dbConn);
					DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
					DboPartida dboPartida = new DboPartida(this.dbConn);
					DboTmDocIden dboTmDocIden = new DboTmDocIden(this.dbConn);
					DboParticLibro dboParticLibro = new DboParticLibro(this.dbConn);
			
			
					int conta=0;
					boolean nuevo = true;
					String antRefNumPart 		= "";
					String antRegPubDescripcion = "";
					String antnombre 			= "";
					String antEstadoAct 		= "";
					StringBuffer cadenaPart 	= new StringBuffer();
					
					//variables para usar dentro del siguiente while
					String refNumPart 	= null;
					String oficRegDesc 	= null;
					String codLibro   	= null;
					String estadoAct 	= null;
					apellidoMaterno		= null;
					nombres				= null;
					String nombreAct	= null;
					int contador=0;
					
					
					while(b == true){
					

						PartidaBean partidaBean = new PartidaBean();
						
						refNumPart = rset.getString("refnum_part");
						oficRegDesc = rset.getString("nombre");
						codLibro   = rset.getString("cod_libro");

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
						
						variableDinamica.delete(0,variableDinamica.length());
						
						apellidoMaterno= rset.getString("ape_mat")==null?"":rset.getString("ape_mat").trim();
						nombres = rset.getString("nombres")==null?"":rset.getString("nombres").trim();
						variableDinamica.append(rset.getString("ape_pat")==null?"":rset.getString("ape_pat").trim()).append(" ");
						variableDinamica.append(apellidoMaterno).append(", ");
						variableDinamica.append(nombres);
						nombreAct = variableDinamica.toString();
						partidaBean.setParticipanteDescripcion(nombreAct);

						
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

						String nuDocIden = rset.getString("nu_doc_iden");
						if (nuDocIden==null || nuDocIden.trim().length()==0)
							partidaBean.setParticipanteNumeroDocumento("&nbsp;");
						else
							partidaBean.setParticipanteNumeroDocumento(nuDocIden);

						tipoDocumento = rset.getString("ti_doc_iden");
						//codPartic     = rset.getString("cod_partic");

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
						/*dboParticLibro.clearAll();
						dboParticLibro.setFieldsToRetrieve(DboParticLibro.CAMPO_NOMBRE);
						dboParticLibro.setField(DboParticLibro.CAMPO_COD_LIBRO,codLibro);
						dboParticLibro.setField(DboParticLibro.CAMPO_COD_PARTIC,codPartic);
						dboParticLibro.setField(DboParticLibro.CAMPO_ESTADO,"1");
						
						if (dboParticLibro.find()==true)
							partidaBean.setParticipacionDescripcion(dboParticLibro.getField(dboParticLibro.CAMPO_NOMBRE));*/
							
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
				
						
					}//fin de while
								
				output.setResultado(resultado);
			
				if (inputBusqIndirectaBean.isFlagPagineoInferior()==false){
					output.setCantidadRegistros(String.valueOf(conteo));
				}else{
					output.setCantidadRegistros(inputBusqIndirectaBean.getCantidadInferior());
				}
		
				//calcular numero para boton "retroceder pagina"		
				if (inputBusqIndirectaBean.getSaltoInferior() == 1){
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
			
			
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		return output;
	}

}
