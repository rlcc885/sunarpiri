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
import gob.pe.sunarp.extranet.framework.session.MonitorDeSesion;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.InputBusqIndirectaBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultaIndicePartidasPersonaNaturalSQL;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.publicidad.sql.impl.ConsultarPartidaDirectaSQLImpl;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

public class ConsultaIndicePartidasPersonaNaturalSQLImpl extends SQLImpl implements ConsultaIndicePartidasPersonaNaturalSQL, Constantes{
	
	private Connection conn;
	private DBConnection dbConn;
	
	public ConsultaIndicePartidasPersonaNaturalSQLImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
	public FormOutputBuscarPartida busquedaIndicePersonaNaturalRmc(int medioDeAcceso,InputBusqIndirectaBean inputBusqIndirectaBean , String session_id )
	    throws SQLException, CustomException, ValidacionException, DBException, IOException, ClassNotFoundException{
		
		FormOutputBuscarPartida output = new FormOutputBuscarPartida();
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
		
			Propiedades propiedades = Propiedades.getInstance();
			
			int conteo=0;
			if (inputBusqIndirectaBean.getFlagPagineo()==false)
			{
				conteo = ContarBusquedaIndicePersonaNaturalRMC(inputBusqIndirectaBean);
				
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
				
				if (conteo==0)
					throw new CustomException(E70002_NO_ENCONTRO_PARTIDA);
			}else{
				conteo = Integer.parseInt(inputBusqIndirectaBean.getCantidad());
			}
			
			int salto= inputBusqIndirectaBean.getSalto();
			String apellidoPaterno= inputBusqIndirectaBean.getArea3ParticipanteApePat();
			String apellidoMaterno= inputBusqIndirectaBean.getArea3ParticipanteApeMat();
			String nombre= inputBusqIndirectaBean.getArea3ParticipanteNombre();
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			StringBuffer sb = new StringBuffer();
			StringBuffer q  = new StringBuffer();
			
				 q.append(" SELECT DISTINCT ");
				 q.append(" PARTIDA.ESTADO as estado, ");
				 q.append(" partida.refnum_part,  partida.cod_libro,     partida.num_partida,");
				 q.append(" regis_publico.siglas, ofic_registral.nombre, prtc_nat.ape_pat,");
				 q.append(" prtc_nat.ape_mat,     prtc_nat.nombres,      prtc_nat.nu_doc_iden,");
				 q.append(" prtc_nat.ti_doc_iden, ind_prtc.estado AS estadoPartic, ");
				 q.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
				 q.append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla,   grupo_libro_area_det glad ");
				 q.append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ");
			     q.append(" AND ind_prtc.refnum_part = partida.refnum_part ");
				 q.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
				 q.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
				 q.append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ");
				 q.append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
				 q.append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
				 /** inicio: ifigueroa 17/08/2007******/
				 if (inputBusqIndirectaBean.getVerifica()!= null)
					 q.append(" and ape_pat = '").append(apellidoPaterno).append("' ");
				 else
				 /** fin: ifigueroa 17/08/2007******/
					 q.append(" and ape_pat like '").append(apellidoPaterno).append("%' ");
				 appendCondicionEstadoPartida(q);
		
				 if (apellidoMaterno.length()>0)
				 {
					 /** inicio: ifigueroa 17/08/2007******/
					 if (inputBusqIndirectaBean.getVerifica()!= null)
						 q.append(" and ape_mat =   '").append(apellidoMaterno).append("'");
					 else
					 /** fin: ifigueroa 17/08/2007******/
						 q.append(" and ape_mat like   '").append(apellidoMaterno).append("%'");
			     }
			     if (nombre.length()>0)
			     {
			    	 /** inicio: ifigueroa 17/08/2007******/
					 if (inputBusqIndirectaBean.getVerifica()!= null)
						 q.append(" and nombres = '").append(nombre).append("'");
					 else
					 /** fin: ifigueroa 17/08/2007******/
						 q.append(" and nombres like '").append(nombre).append("%'");
			     }
	             q.append(" AND partida.cod_libro = glad.cod_libro  "); 
	             q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
	             q.append(" and gla.cod_grupo_libro_area  ='").append(inputBusqIndirectaBean.getCodGrupoLibroArea()).append("' ");
	             q.append(" AND IND_PRTC.ESTADO = '1'");
	             q.append(" AND IND_PRTC.TIPO_PERS='N'");
	             q.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'");
	             q.append(" ORDER BY prtc_nat.ape_pat, prtc_nat.ape_mat,  prtc_nat.nombres, ind_prtc.estado DESC,   PARTIDA.REG_PUB_ID, partida.ofic_reg_id, partida.num_partida  ");
	
	             if (isTrace(this)) System.out.println("___verquery_busquedaIndicePersonaNaturalRmc__"+q.toString());
	 			
	 			stmt   = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	 			stmt.setFetchSize(propiedades.getLineasPorPag());
	 			rset   = stmt.executeQuery(q.toString());
	 			
	 			if(medioDeAcceso==MEDIO_CONTROLLER)
	 			{
		 			if (inputBusqIndirectaBean.getSalto()>1){ 
		 				rset.absolute(propiedades.getLineasPorPag() * (inputBusqIndirectaBean.getSalto() - 1));
		 			} 
	 			}
	 			/*if (inputBusqIndirectaBean.getSalto()>1){
	 				rset.absolute(propiedades.getLineasPorPag() * (inputBusqIndirectaBean.getSalto() - 1));
	 			} */
	 			
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
				boolean nuevo = true;
				String antRefNumPart = "";
				String antRegPubDescripcion = "";
				String antnombre = "";
				String antEstadoAct = "";
				StringBuffer cadenaPart = new StringBuffer();
				PartidaBean partidaBean = new PartidaBean();
				
				while (b==true)
				{
					//completar los detalles de la partida encontrada
					String refNumPart = rset.getString("refnum_part");
					String oficRegDesc = rset.getString("nombre");
					String codLibro   = rset.getString("cod_libro");
	
					String estadoAct = "";
					if ((rset.getString("estadoPartic")!=null)&&(rset.getString("estadoPartic").startsWith("1")))
					{
						estadoAct = "Activo";
					}
					else
					{
						estadoAct = "Inactivo";
					}
					sb.delete(0,sb.length());
					String ape_mat = rset.getString("ape_mat")==null?"":rset.getString("ape_mat").trim();
					String nombres = rset.getString("nombres")==null?"":rset.getString("nombres").trim();
					sb.append(rset.getString("ape_pat")==null?"":rset.getString("ape_pat").trim()).append(" ");
					sb.append(ape_mat).append(", ");
					sb.append(nombres);
					String nombreAct = sb.toString();
	
					if((antRefNumPart.equals(refNumPart)) && (antRegPubDescripcion.equals(oficRegDesc)) && (antEstadoAct.equals(estadoAct)) && (antnombre.equals(nombreAct))){
						nuevo = false;
					}
					else
					{
						if(!antRefNumPart.equals("")){
							partidaBean.setParticipanteDescripcion(cadenaPart.toString());
							resultado.add(partidaBean);
							conta++;
							partidaBean = new PartidaBean();
						}
						antRefNumPart = refNumPart;
						antRegPubDescripcion = oficRegDesc;
						antnombre = nombreAct;
						antEstadoAct = estadoAct;
	
						cadenaPart.delete(0,cadenaPart.length());
						cadenaPart.append(nombreAct);
						nuevo = true;
					}
					
					if(nuevo){
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
						partidaBean.setEstadoInd(estadoAct);
						partidaBean.setOficRegDescripcion(oficRegDesc);
						
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
	
						String nuDocIden = rset.getString("nu_doc_iden");
						if ((nuDocIden==null) || (nuDocIden.trim().length()==0))
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
	
					}
	
					b = rset.next();
					//Inicio:mgarate:27/08/2007
					//evita la paginacion si el medio de acceso es web service
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
			
				partidaBean.setParticipanteDescripcion(cadenaPart.toString());
				resultado.add(partidaBean);
				
				output.setResultado(resultado);
				
				if (inputBusqIndirectaBean.getFlagPagineo()==false){
					output.setCantidadRegistros(String.valueOf(conteo));
				}else{
					output.setCantidadRegistros(inputBusqIndirectaBean.getCantidad());
				}
		
//				calcular numero para boton "retroceder pagina"		
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
			
		}finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return output;   
		
	}
	
	public int ContarBusquedaIndicePersonaNaturalRMC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException{
		int conteo=0;
		Statement stmt   = null;
		ResultSet rset   = null;
		
		try{
			Propiedades propiedades = Propiedades.getInstance();
			StringBuffer q = new StringBuffer();	
			String apellidoPaterno= inputBusqIndirectaBean.getArea3ParticipanteApePat();
			String apellidoMaterno= inputBusqIndirectaBean.getArea3ParticipanteApeMat();
			String nombre= inputBusqIndirectaBean.getArea3ParticipanteNombre();

			 q.append(" SELECT COUNT(*) ");
			 q.append(" FROM ");
			 q.append(" (SELECT DISTINCT ");
			 q.append(" PARTIDA.ESTADO as estado, ");
			 q.append(" partida.refnum_part,  partida.cod_libro,     partida.num_partida,");
			 q.append(" regis_publico.siglas, ofic_registral.nombre, prtc_nat.ape_pat,");
			 q.append(" prtc_nat.ape_mat,     prtc_nat.nombres,      prtc_nat.nu_doc_iden,");
			 q.append(" prtc_nat.ti_doc_iden, ind_prtc.estado AS estadoPartic, ");
			 q.append(" partida.reg_pub_id, partida.ofic_reg_id, partida.area_reg_id ");
			 q.append(" FROM prtc_nat, ind_prtc, partida, ofic_registral, regis_publico , grupo_libro_area gla,   grupo_libro_area_det glad ");
			 q.append(" WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc ");
		     q.append(" AND ind_prtc.refnum_part = partida.refnum_part ");
			 q.append(" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id  ");
			 q.append(" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID ");
			 q.append(" AND partida.ofic_reg_id = ofic_registral.ofic_reg_id ");
			 q.append(" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID ");
			 q.append(" AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id ");
			
			 /** inicio: ifigueroa 17/08/2007******/
			 if (inputBusqIndirectaBean.getVerifica()!= null)
				 q.append(" and ape_pat = '").append(apellidoPaterno).append("' ");
			 else
			 /** fin: ifigueroa 17/08/2007******/
			 q.append(" and ape_pat like '").append(apellidoPaterno).append("%' ");
			 appendCondicionEstadoPartida(q);
	
			 if (apellidoMaterno.length()>0)
			 {
				 /** inicio: ifigueroa 17/08/2007******/
				 if (inputBusqIndirectaBean.getVerifica()!= null)
					 q.append(" and ape_mat =   '").append(apellidoMaterno).append("'");
				 else
				 /** fin: ifigueroa 17/08/2007******/
			     q.append(" and ape_mat like   '").append(apellidoMaterno).append("%'");
		     }
		     if (nombre.length()>0)
		     {
		    	 /** inicio: ifigueroa 17/08/2007******/
				 if (inputBusqIndirectaBean.getVerifica()!= null)
					 q.append(" and nombres = '").append(nombre).append("'");
				 else
				 /** fin: ifigueroa 17/08/2007******/
		         q.append(" and nombres like '").append(nombre).append("%'");
		     }
            q.append(" AND partida.cod_libro = glad.cod_libro  "); 
            q.append(" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
            q.append(" and gla.cod_grupo_libro_area  ='").append(inputBusqIndirectaBean.getCodGrupoLibroArea()).append("' ");
            q.append(" AND IND_PRTC.ESTADO = '1'");
            q.append(" AND IND_PRTC.TIPO_PERS='N'");
            /** inicio: Jrosas: 07-09-2007 **/
			q.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')");
			/** fin: Jrosas: 07-09-2007 **/	
			
			if (isTrace(this)) System.out.println("___verquery_ContarBusquedaIndicePersonaNaturalRMC_A__"+q.toString());
			
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
	

	private void appendCondicionEstadoPartida(StringBuffer q) {

		q.append(" and PARTIDA.ESTADO = '1' ");
		
	}
	
}
