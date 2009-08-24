package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;

import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboPartida;
import gob.pe.sunarp.extranet.dbobj.DboTmLibro;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.publicidad.bean.AeronaveBean;
import gob.pe.sunarp.extranet.publicidad.bean.BuquesBean;
import gob.pe.sunarp.extranet.publicidad.bean.ConstanciaCremBean;
import gob.pe.sunarp.extranet.publicidad.bean.EmbarcacionPesqueraBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBloqueadaBean;
import gob.pe.sunarp.extranet.publicidad.bean.PersonaJuridicaBean;
import gob.pe.sunarp.extranet.publicidad.bean.TituloPendienteBean;
import gob.pe.sunarp.extranet.publicidad.certificada.bean.ObjetoSolicitudBean;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.publicidad.sql.VerificaCremCondicionadoSQL;
import gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean.Vehiculo;
import gob.pe.sunarp.extranet.util.Tarea;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class VerificaCremCondicionadoSQLImpl extends SQLImpl implements VerificaCremCondicionadoSQL{

	private Connection conn;
	private DBConnection dbConn;
	
	public VerificaCremCondicionadoSQLImpl(Connection conn, DBConnection dbConn){
		this.conn = conn;
		this.dbConn = dbConn;
	}
	
	@SuppressWarnings("unchecked")
	public ConstanciaCremBean comentarioCertificadoCREMCondicionado(ObjetoSolicitudBean objetoSolicitudBean) throws SQLException, CustomException, ValidacionException, DBException, Throwable{
		
		ConstanciaCremBean constanciaCremBean = new ConstanciaCremBean();
		
		String tipoPersona = objetoSolicitudBean.getTpo_pers();
		
		System.out.println("[CREMC] tipoPersona: "+tipoPersona);
		
		Locale local= new Locale("es","ES");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",local);
		
		if(objetoSolicitudBean!=null && objetoSolicitudBean.getFechaInscripcionASientoDesde()!=null && objetoSolicitudBean.getFechaInscripcionAsientoDesdeDate()!=null){
			objetoSolicitudBean.setFechaInscripcionASientoDesde(simpleDateFormat.format(objetoSolicitudBean.getFechaInscripcionAsientoDesdeDate()));
		}
		if(objetoSolicitudBean!=null && objetoSolicitudBean.getFechaInscripcionASientoHasta()!=null && objetoSolicitudBean.getFechaInscripcionAsientoHastaDate()!=null){
			objetoSolicitudBean.setFechaInscripcionASientoHasta(simpleDateFormat.format(objetoSolicitudBean.getFechaInscripcionAsientoHastaDate()));
		}
		
		String[] tipos = Tarea.convierteTiraEnArreglo(objetoSolicitudBean.getTipoRegistro(), ",");
		
		for(int x=0; tipos!=null && x<tipos.length; x++){
			String tipo = tipos[x];
			
			if(tipo.equals("RMC")){
				
				if(tipoPersona.equals("N")){
					constanciaCremBean.setListadoPartidasRMC(buscarPartidasPersonaNaturalRMC(objetoSolicitudBean));
				}else{
					constanciaCremBean.setListadoPartidasRMC(buscarPartidasPersonaJuridicasRMC(objetoSolicitudBean));
				}
				constanciaCremBean.setListadoTitulosPendientesRMC(busquedaTitulosPendientes(constanciaCremBean.getListadoPartidasRMC(), "R"));
				
			}else if(tipo.equals("BUQ")){
			
				if(tipoPersona.equals("N")){
					constanciaCremBean.setListadoAsientosBuques(busquedaPersonaNaturalBuquesRJB(objetoSolicitudBean));
				}else{
					constanciaCremBean.setListadoAsientosBuques(busquedaPersonaJuridicaBuquesRJB(objetoSolicitudBean));
				}
				constanciaCremBean.setListadoTitulosPendientesBuques(busquedaTitulosPendientes(constanciaCremBean.getListadoAsientosBuques(),"B"));
				
			}else if(tipo.equals("AER")){
			
				if(tipoPersona.equals("N")){
					constanciaCremBean.setListadoAsientosAeronaves(busquedaPersonaNaturalAeronaveRJB(objetoSolicitudBean));
				}else{
					constanciaCremBean.setListadoAsientosAeronaves(busquedaPersonaJuridicaAeronaveRJB(objetoSolicitudBean));
				}
				constanciaCremBean.setListadoTitulosPendientesAeronaves(busquedaTitulosPendientes(constanciaCremBean.getListadoAsientosAeronaves(),"A"));
				
			}else if(tipo.equals("EMB")){
			
				if(tipoPersona.equals("N")){
					constanciaCremBean.setListadoAsientosEmbarcacionPesquera(busquedaPersonaNaturalEmbarcacionesPesquerasRJB(objetoSolicitudBean));
				}else{
					constanciaCremBean.setListadoAsientosEmbarcacionPesquera(busquedaPersonaJuridicaEmbarcacionesPesquerasRJB(objetoSolicitudBean));
				}
				constanciaCremBean.setListadoTitulosPendientesEmbarcacionPesquera(busquedaTitulosPendientes(constanciaCremBean.getListadoAsientosEmbarcacionPesquera(),"E"));
				
			}else if(tipo.equals("VEH")){
				
				if(tipoPersona.equals("N")){
					constanciaCremBean.setListadoAsientosVehicular(busquedaPersonaNaturalVehicularRJB(objetoSolicitudBean));
				}else{
					constanciaCremBean.setListadoAsientosVehicular(busquedaPersonaJuridicaVehicularRJB(objetoSolicitudBean));
				}
				constanciaCremBean.setListadoTitulosPendientesVehicular(busquedaTitulosPendientes(constanciaCremBean.getListadoAsientosVehicular(),"V"));
				
			}else if(tipo.equals("PEJ")){
				
				if(tipoPersona.equals("N")){
					constanciaCremBean.setListadoAsientosPersonasJuridicas(busquedaPersonaNaturalJuridicaRJB(objetoSolicitudBean));
				}else{
					constanciaCremBean.setListadoAsientosPersonasJuridicas(busquedaPersonaJuridicaRJB(objetoSolicitudBean));
				}
				
				constanciaCremBean.setListadoTitulosPendientesPersonasJuridicas(busquedaTitulosPendientes(constanciaCremBean.getListadoAsientosPersonasJuridicas(),"J"));
			}
			
		}
		
		constanciaCremBean.setTipoRegistro(objetoSolicitudBean.getTipoRegistro());
		
		return constanciaCremBean;
	}
	
	public ArrayList<PartidaBean> buscarPartidasPersonaNaturalRMC(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String apellidoPaterno = objetoSolicitudBean.getApe_pat();
		String apellidoMaterno = objetoSolicitudBean.getApe_mat();
		String nombres		   = objetoSolicitudBean.getNombres();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		PartidaBean partidaBean = null;
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList<PartidaBean> resultadoRMC= new ArrayList<PartidaBean>();
		
		ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT DISTINCT ");
			q.append( "   PARTIDA.ESTADO as estado, partida.refnum_part as refnum_part, ");
			q.append( "   partida.cod_libro as cod_libro, partida.num_partida as num_partida, ");
			q.append( "   ofic_registral.nombre as nombre, partida.reg_pub_id as reg_pub_id, ");
			q.append( "   partida.ofic_reg_id as ofic_reg_id, partida.area_reg_id as area_reg_id,  ");
			q.append( "   pl.nombre as participacionDescripcion ");
			q.append( "FROM  ");
			q.append( "   prtc_nat, " +
					  "   ind_prtc, " +
					  "   partida, " +
					  "   ofic_registral, ");
			q.append( "   regis_publico, " +
					  "   grupo_libro_area gla," +
					  "   ind_prtc_asiento_garantia ig, ");
			q.append( "   grupo_libro_area_det glad, " +
					  "   asiento_garantia ag, ");
			q.append( "   partic_libro pl," +
					  "   asiento a ");
			q.append( "WHERE prtc_nat.cur_prtc = ind_prtc.cur_prtc  ");
			q.append( "     AND ind_prtc.refnum_part = partida.refnum_part  ");
			q.append( "     and ag.refnum_part=partida.refnum_part ");
			q.append( "     and ag.refnum_part=ig.refnum_part ");
			q.append( "     and ag.ns_asiento=ig.ns_asiento ");
			q.append( "     and ag.cod_acto=ig.cod_acto ");
			
			q.append("		and ag.cod_acto    = a.cod_acto ");
			q.append("		and ag.refnum_part = a.refnum_part ");
			q.append("		and ag.ns_asiento  = a.ns_asiento ");
			
			q.append( "     and ind_prtc.refnum_part=ig.refnum_part ");
			q.append( "     and ind_prtc.cur_prtc=ig.cur_prtc ");
			q.append( "     AND partida.ofic_reg_id = prtc_nat.ofic_reg_id   ");
			q.append( "     AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID  ");
			q.append( "     AND partida.ofic_reg_id = ofic_registral.ofic_reg_id  ");
			q.append( "     AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
			q.append( "     AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id  ");
			q.append( "     and PRTC_NAT.ape_pat = '").append(apellidoPaterno).append("'");
			
			if (apellidoMaterno.length()>0){
				q.append( "  and prtc_nat.ape_mat = '").append(apellidoMaterno).append("'");
			}
			if (nombres.length()>0){
				q = q.append( "  and prtc_nat.nombres = '").append(nombres).append("'");
			}
			
			q.append( "    and partida.estado !=2 ");
			q.append( "     AND partida.cod_libro = glad.cod_libro   ");
			q.append( "    and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append( "    and gla.cod_grupo_libro_area =16 ");							
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append( "     and pl.cod_libro=partida.cod_libro ");
			q.append( "     and ind_prtc.cod_partic=pl.cod_partic ");
			q.append( "     AND IND_PRTC.TIPO_PERS='N' ");
			q.append( "     ORDER BY PARTIDA.NUM_PARTIDA,OFIC_REGISTRAL.NOMBRE");

		       
			if (isTrace(this)) System.out.println("___verquery_buscarPartidasPersonaNaturalRMC__"+q);
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
			DboPartida dboPartida = new DboPartida(this.dbConn);
			//String antRefnumpart="";
			boolean b = rset.next();
			
			while (b==true)
			{
				partidaBean = new PartidaBean();
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				
				//if (!(refNumPart.equals(antRefnumpart))){
					partidaBean.setEstado(rset.getString("estado"));
					partidaBean.setOficRegDescripcion(rset.getString("nombre"));
					partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
					partidaBean.setRefNumPart("refnum_part");
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setCodLibro(rset.getString("cod_libro"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setParticipacionDescripcion(rset.getString("participacionDescripcion"));
						
					//dbravo: Recupera el refnum_part de la partida migrada si existiera
					String partidaMigrada = consultarPartidaDirectaSQLImpl.obtenerRefNumParAntiguo(refNumPart);
					String partidaNueva = null;
					
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
					}
	
					//descripcion libro
					dboTmLibro.clearAll();
					dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
					dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,codLibro);
					if (dboTmLibro.find() == true)
						partidaBean.setLibroDescripcion(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));
	
					resultadoRMC.add(partidaBean);
					b = rset.next();
					//antRefnumpart=refNumPart;
					
				//}else{
				//	b = rset.next();
				//}
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRMC;
	}
	
	private ArrayList<PartidaBean> buscarPartidasPersonaJuridicasRMC(ObjetoSolicitudBean objetoSolicitudBean) throws SQLException, DBException{
	
		String razonSocial 	   = objetoSolicitudBean.getRaz_soc();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		PartidaBean partidaBean = null;
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList<PartidaBean> resultadoRMC= new ArrayList<PartidaBean>();
		ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
		
		if(razonSocial==null){
			return resultadoRMC;
		}
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT DISTINCT ");
			q.append("   PARTIDA.ESTADO as estado, " +
					 "   partida.refnum_part as refnum_part, " +
					 "   PRTC_JUR.RAZON_SOCIAL AS RAZONSOCIAL, ");
			q.append("   partida.cod_libro as cod_libro, " +
					 "   partida.num_partida as num_partida, " +
					 "   IND_PRTC.TIPO_PERS, ");
			q.append("   ofic_registral.nombre as nombre, " +
					 "   partida.reg_pub_id as reg_pub_id, ");
			q.append("   partida.ofic_reg_id as ofic_reg_id, " +
					 "   partida.area_reg_id as area_reg_id,  ");
			q.append("   pl.nombre as participacionDescripcion ");
			q.append("FROM  ");
			q.append("   PRTC_JUR, " +
					 "	 ind_prtc, " +
					 "   partida, " +
					 "   ofic_registral, ");
			q.append("   regis_publico , " +
					 "   grupo_libro_area gla, " +
					 "   ind_prtc_asiento_garantia ig,   ");
			q.append("   grupo_libro_area_det glad, " +
					 "   asiento_garantia ag, " +
					 "   asiento a, ");
			q.append("   partic_libro pl ");
			q.append("WHERE PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC  ");
			q.append("     AND ind_prtc.refnum_part = partida.refnum_part  ");
			q.append("     and ag.refnum_part=partida.refnum_part ");
			q.append( "    and ag.refnum_part=ig.refnum_part ");
			q.append( "    and ag.ns_asiento=ig.ns_asiento ");
			q.append( "    and ag.cod_acto=ig.cod_acto ");
			
			q.append("	   and ag.cod_acto    = a.cod_acto ");
			q.append("	   and ag.refnum_part = a.refnum_part ");
			q.append("	   and ag.ns_asiento  = a.ns_asiento ");
			
			q.append( "    and ind_prtc.refnum_part=ig.refnum_part ");
			q.append( "    and ind_prtc.cur_prtc=ig.cur_prtc ");
			q.append("     AND partida.ofic_reg_id = prtc_JUR.ofic_reg_id   ");
			q.append("     AND PARTIDA.REG_PUB_ID  = prtc_JUR.REG_PUB_ID  ");
			q.append("     AND partida.ofic_reg_id = ofic_registral.ofic_reg_id  ");
			q.append("     AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
			q.append("     AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id  ");
			
			if (razonSocial != null && razonSocial.length()>0){
				q.append("  AND prtc_jur.RAZON_SOCIAL = '"+razonSocial+"' ");
			}  
			
			q.append("     and partida.estado !=2 ");
			q.append("     AND partida.cod_libro = glad.cod_libro   ");
			q.append("     and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append("     and gla.cod_grupo_libro_area = 16 ");                        
			q.append("     and pl.cod_libro=partida.cod_libro ");
			q.append("     and ind_prtc.cod_partic=pl.cod_partic ");
			q.append("     AND IND_PRTC.ESTADO = '1' ");
			q.append("     AND IND_PRTC.TIPO_PERS='J' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("ORDER BY PARTIDA.NUM_PARTIDA,OFIC_REGISTRAL.NOMBRE ");

		       
			if (isTrace(this)) System.out.println("___verquery_buscarPartidasPersonaJuridicasRMC__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			DboTmLibro dboTmLibro = new DboTmLibro(this.dbConn);
			DboPartida dboPartida = new DboPartida(this.dbConn);
			
			//String antRefnumpart="";
			boolean b = rset.next();
			
			while (b==true)
			{
				partidaBean = new PartidaBean();
				String refNumPart = rset.getString("refnum_part");
				String codLibro   = rset.getString("cod_libro");
				
				//if (!(refNumPart.equals(antRefnumpart))){
					partidaBean.setEstado(rset.getString("estado"));
					partidaBean.setOficRegDescripcion(rset.getString("nombre"));
					partidaBean.setAreaRegistralId(rset.getString("area_reg_id"));
					partidaBean.setRefNumPart("refnum_part");
					partidaBean.setNumPartida(rset.getString("num_partida"));
					partidaBean.setCodLibro(rset.getString("cod_libro"));
					partidaBean.setOficRegId(rset.getString("ofic_reg_id"));
					partidaBean.setRegPubId(rset.getString("reg_pub_id"));
					partidaBean.setParticipacionDescripcion(rset.getString("participacionDescripcion"));
						
					//dbravo: Recupera el refnum_part de la partida migrada si existiera
					String partidaMigrada = consultarPartidaDirectaSQLImpl.obtenerRefNumParAntiguo(refNumPart);
					String partidaNueva = null;
					
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
					}
	
					//descripcion libro
					dboTmLibro.clearAll();
					dboTmLibro.setFieldsToRetrieve(DboTmLibro.CAMPO_DESCRIPCION);
					dboTmLibro.setField(DboTmLibro.CAMPO_COD_LIBRO,codLibro);
					if (dboTmLibro.find() == true)
						partidaBean.setLibroDescripcion(dboTmLibro.getField(DboTmLibro.CAMPO_DESCRIPCION));
	
					resultadoRMC.add(partidaBean);
					b = rset.next();
					//antRefnumpart=refNumPart;
					
				//}else{
				//	b = rset.next();
				//}
				
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRMC;
	}
	
	public ArrayList busquedaPersonaNaturalVehicularRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String apellidoPaterno = objetoSolicitudBean.getApe_pat();
		String apellidoMaterno = objetoSolicitudBean.getApe_mat();
		String nombres         = objetoSolicitudBean.getNombres();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		String flagHistorico   = objetoSolicitudBean.getFlagHistorico();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBVehiculo= new ArrayList();
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append(" SELECT  ");
			q.append("  TA.DESCRIPCION as descripcionActo,   ");
			q.append("  tv.descripcion as clase, ");
			q.append("  tm.descripcion as marca, ");
			q.append("  vehiculo.num_serie as serie, ");
			q.append("  vehiculo.num_motor as motor, ");
			q.append("  vehiculo.num_placa as placa, ");
			q.append("  partida.num_partida as numeroPartida, ");
			q.append("  OFIC_REGISTRAL.NOMBRE as oficina,  ");
			q.append("  pl.nombre as tipoParticipacion, ");
			q.append("  PARTIDA.REG_PUB_ID as reg_pub_id, ");
			q.append("  PARTIDA.OFIC_REG_ID as ofic_reg_id, ");
			q.append("  partida.area_reg_id as area_id, ");
			q.append("  AG.VIGENCIA as vigencia, ");
			q.append("  ip.ESTADO as estado ");
			q.append("FROM  ");
			q.append("  vehiculo,PARTIDA, ");
			q.append("  OFIC_REGISTRAL,REGIS_PUBLICO,  ");
			q.append("  ind_prtc ip,ind_prtc_asiento_garantia ig, ");
			q.append("  grupo_libro_area gla, grupo_libro_area_det glad, ");
			q.append("  asiento_garantia ag,asiento a, prtc_nat,TM_ACTO ta,  partic_libro pl, ");
			q.append("  tm_marca_vehi tm, tm_tipo_vehi tv ");
			q.append("WHERE  ");
			q.append("  a.refnum_part=ag.refnum_part ");
			q.append("  and a.cod_acto=ag.cod_acto ");
			q.append("  and a.ns_asiento=ag.ns_asiento ");
			q.append("  and vehiculo.refnum_part=PARTIDA.REFNUM_PART ");
			q.append("  and prtc_nat.cur_prtc=ip.cur_prtc ");
			q.append("  and partida.refnum_part= ip.refnum_part ");
			q.append("  and ag.refnum_part=partida.refnum_part ");
			q.append("  and ag.refnum_part=ig.refnum_part ");
			q.append("  and ag.ns_asiento=ig.ns_asiento ");
			q.append("  and ag.cod_acto=ig.cod_acto ");
			q.append("  and ip.refnum_part=ig.refnum_part ");
			q.append("  and ip.cur_prtc=ig.cur_prtc ");
			q.append("  AND partida.ofic_reg_id = prtc_nat.ofic_reg_id   ");
			q.append("  AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID  ");
			q.append("  AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID  ");
			q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
			q.append("  AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
			
			q.append("  and PRTC_NAT.ape_pat = '"+apellidoPaterno+"' ");
			if (apellidoMaterno.length()>0){
				q.append(" and PRTC_NAT.ape_mat = '"+apellidoMaterno+"' ");
			}
			if (nombres.length()>0){
				q.append(" and prtc_nat.nombres = '"+nombres+"' ");
			}	
			q.append("  and PARTIDA.ESTADO != '2'  ");
			q.append("  AND IP.TIPO_PERS='N' ");
			q.append("  AND partida.cod_libro = glad.cod_libro  ");
			q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append("  and gla.cod_grupo_libro_area ='29' ");         // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("  AND ag.cod_acto = ta.cod_acto ");
			q.append("  and pl.cod_libro = partida.cod_libro ");
			q.append("  and pl.cod_partic =ip.cod_partic ");
			q.append("  and tm.cod_marca = vehiculo.cod_marca ");
			q.append("  and tv.cod_tipo_vehi = vehiculo.cod_tipo_vehi ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}	
			if(flagHistorico!=null && flagHistorico.trim().length()>0){
				q.append("    and ag.vigencia = '"+flagHistorico+"' ");
			}
			
			q.append("  union ");
			q.append("  select  ");
			q.append("        TA.DESCRIPCION as descripcionActo,   ");
			q.append("        tv.descripcion as clase, ");
			q.append("        tm.descripcion as marca, ");
			q.append("        v.num_serie as serie, ");
			q.append("        v.num_motor as motor, ");
			q.append("        v.num_placa as placa, ");
			q.append("        p.num_partida as numeroPartida, ");
			q.append("        o.NOMBRE as oficina,  ");
			q.append("        pl.nombre as tipoParticipacion, ");
			q.append("        P.REG_PUB_ID as reg_pub_id, ");
			q.append("		  P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("		  p.area_reg_id as area_id, ");
			q.append("		  AG.VIGENCIA as vigencia, ");
			q.append("		  ip.ESTADO as estado ");
			q.append("  from vehiculo v, partida p,  ");
			q.append("       asiento a, asiento_garantia ag, ");
			q.append("       OFIC_REGISTRAL o,REGIS_PUBLICO rp, ");
			q.append("       ind_prtc ip, prtc_nat pn, ");
			q.append("       grupo_libro_area gla, grupo_libro_area_det glad, ");
			q.append("       TM_ACTO ta,  partic_libro pl, ");
			q.append("       tm_marca_vehi tm, tm_tipo_vehi tv, ");
			q.append("	   grupo_libro_acto glac  ");
			q.append(" where v.refnum_part = p.refnum_part ");
			q.append("   and a.refnum_part = p.refnum_part ");
			q.append("   and p.ofic_reg_id = o.ofic_reg_id ");
			q.append("   and p.reg_pub_id = rp.reg_pub_id ");
			q.append("   and p.refnum_part = ip.refnum_part ");
			q.append("   and o.reg_pub_id  = rp.reg_pub_id ");
			q.append("   and ag.refnum_part(+) = a.refnum_part ");
			q.append("   and ip.cur_prtc = pn.cur_prtc ");
			q.append("   and p.reg_pub_id = pn.reg_pub_id ");
			q.append("   and p.ofic_reg_id = pn.ofic_reg_id ");
			q.append("   AND p.cod_libro = glad.cod_libro ");
			q.append("   and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ");
			q.append("   and gla.cod_grupo_libro_area = '29' ");            // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("   and p.ESTADO != '2' ");
			q.append("   and ag.refnum_part is null ");
			q.append("   and ip.tipo_pers = 'N' ");
			q.append("   and pn.ape_pat = '"+apellidoPaterno+"' ");
			if (apellidoMaterno.length()>0){ 
				q.append(" and pn.ape_mat = '"+apellidoMaterno+"' ");
			}
			if (nombres.length()>0){
				q.append(" and pn.nombres = '"+nombres+"'  ");
			}	
			q.append("  AND a.cod_acto = ta.cod_acto ");
			q.append("  and pl.cod_libro = p.cod_libro ");
			q.append("  and pl.cod_partic =ip.cod_partic ");
			q.append("  and tm.cod_marca = v.cod_marca ");
			q.append("  and tv.cod_tipo_vehi = v.cod_tipo_vehi   ");
			q.append("  and ta.cod_acto = glac.cod_acto ");
			q.append("  and glac.TIP_LIBRO = 'VEH'");
			q.append("  and glac.TIP_ACTO = 'DOM'");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append(" ORDER BY estado desc , vigencia  ");
		       
			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaNaturalVehicularRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				Vehiculo vehiculoBean = new Vehiculo();
				vehiculoBean.setDescripcionSubActo(rset.getString("descripcionActo"));
				vehiculoBean.setClase(rset.getString("clase"));
				vehiculoBean.setMarca(rset.getString("marca"));
				vehiculoBean.setMotor(rset.getString("motor"));
				vehiculoBean.setSerie(rset.getString("serie"));
				vehiculoBean.setPlaca(rset.getString("placa"));
				vehiculoBean.setNumeroPartida(rset.getString("numeroPartida"));
				vehiculoBean.setOficinaRegistral(rset.getString("oficina"));
				vehiculoBean.setCodigoOficinaRegistral(rset.getString("ofic_reg_id"));
				vehiculoBean.setCodigoZonaRegistral(rset.getString("reg_pub_id"));
				vehiculoBean.setTipoParticipacion(rset.getString("tipoParticipacion"));
				vehiculoBean.setEstadoParticipante(rset.getString("estado"));	
				vehiculoBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBVehiculo.add(vehiculoBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBVehiculo;
	}
	
	public ArrayList busquedaPersonaJuridicaVehicularRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String razonSocial	   = objetoSolicitudBean.getRaz_soc();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		String flagHistorico   = objetoSolicitudBean.getFlagHistorico();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBVehiculo= new ArrayList();
		
		if(razonSocial==null){
			return resultadoRJBVehiculo;
		}
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append(" select  ");
			q.append("  TA.DESCRIPCION as descripcionActo, ");
			q.append("  tv.descripcion as clase, ");
			q.append("  tm.descripcion as marca, ");
			q.append("  vehiculo.num_serie as serie, ");
			q.append("  vehiculo.num_motor as motor, ");
			q.append("  vehiculo.num_placa as placa, ");
			q.append("  partida.num_partida as numeroPartida, ");
			q.append("  OFIC_REGISTRAL.NOMBRE as oficina,  ");
			q.append("  pl.nombre as tipoParticipacion, ");
			q.append("  PARTIDA.REG_PUB_ID as reg_pub_id, ");
			q.append("  PARTIDA.OFIC_REG_ID as ofic_reg_id, ");
			q.append("  partida.area_reg_id as area_id, ");
			q.append("  AG.VIGENCIA as vigencia, ");
			q.append("  ip.ESTADO as estado ");
			q.append("FROM  ");
			q.append("  vehiculo,PARTIDA, ");
			q.append("  OFIC_REGISTRAL,REGIS_PUBLICO,  ");
			q.append("  ind_prtc ip,ind_prtc_asiento_garantia ig, ");
			q.append("  grupo_libro_area gla, grupo_libro_area_det glad,  ");
			q.append("  asiento_garantia ag,asiento a, prtc_jur, ");
			q.append("  TM_ACTO ta, partic_libro pl, ");
			q.append("  tm_marca_vehi tm, tm_tipo_vehi tv   ");
			q.append("WHERE  ");
			q.append("  a.refnum_part=ag.refnum_part ");
			q.append("  and a.cod_acto=ag.cod_acto ");
			q.append("  and a.ns_asiento=ag.ns_asiento ");
			q.append("  and vehiculo.refnum_part=PARTIDA.REFNUM_PART ");
			q.append("  and prtc_jur.cur_prtc=ip.cur_prtc ");
			q.append("  and ag.refnum_part=partida.refnum_part ");
			q.append("  and ag.refnum_part=ig.refnum_part ");
			q.append("  and ag.ns_asiento=ig.ns_asiento ");
			q.append("  and ag.cod_acto=ig.cod_acto ");
			q.append("  and ip.refnum_part=ig.refnum_part ");
			q.append("  and ip.cur_prtc=ig.cur_prtc ");
			q.append("  AND partida.ofic_reg_id = prtc_jur.ofic_reg_id ");
			q.append("  AND PARTIDA.REG_PUB_ID  = prtc_jur.reg_pub_id ");
			q.append("  AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID  ");
			q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
			q.append("  AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("  AND PRTC_JUR.RAZON_SOCIAL = '"+razonSocial+"' ");
			}	
			q.append("  and PARTIDA.ESTADO != '2'  ");
			q.append("  AND IP.TIPO_PERS='J' ");
			q.append("  AND partida.cod_libro = glad.cod_libro  ");
			q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append("  and gla.cod_grupo_libro_area ='29' ");   // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("  AND ag.cod_acto = ta.cod_acto ");
			q.append("  and pl.cod_libro = partida.cod_libro ");
			q.append("  and pl.cod_partic = ip.cod_partic ");
			q.append("  and tm.cod_marca = vehiculo.cod_marca ");
			q.append("  and tv.cod_tipo_vehi = vehiculo.cod_tipo_vehi ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			if(flagHistorico!=null && flagHistorico.trim().length()>0){
				q.append("    and ag.vigencia = '"+flagHistorico+"' ");
			}
			
			q.append("  union ");
			q.append("  select  ");
			q.append("        TA.DESCRIPCION as descripcionActo,  ");
			q.append("        tv.descripcion as clase, ");
			q.append("        tm.descripcion as marca, ");
			q.append("        v.num_serie as serie, ");
			q.append("        v.num_motor as motor, ");
			q.append("        v.num_placa as placa, ");
			q.append("        p.num_partida as numeroPartida, ");
			q.append("        o.NOMBRE as oficina,  ");
			q.append("        pl.nombre as tipoParticipacion, ");
			q.append("        P.REG_PUB_ID as reg_pub_id, ");
			q.append("        P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("		  p.area_reg_id as area_id, ");
			q.append("		  AG.VIGENCIA as vigencia, ");
			q.append("		 ip.ESTADO as estado ");
			q.append("  from vehiculo v, partida p,  ");
			q.append("       asiento a, asiento_garantia ag, ");
			q.append("       OFIC_REGISTRAL o,REGIS_PUBLICO rp, ");
			q.append("       ind_prtc ip, prtc_jur pj, ");
			q.append("       grupo_libro_area gla, grupo_libro_area_det glad, ");
			q.append("       TM_ACTO ta,  partic_libro pl, ");
			q.append("       tm_marca_vehi tm, tm_tipo_vehi tv,  ");
			q.append("       grupo_libro_acto glac ");
			q.append(" where ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("   pj.RAZON_SOCIAL = '"+razonSocial+"' ");
			}	
			q.append("   and v.refnum_part = p.refnum_part ");
			q.append("   and a.refnum_part = p.refnum_part ");
			q.append("   and p.ofic_reg_id = o.ofic_reg_id ");
			q.append("   and p.reg_pub_id = rp.reg_pub_id ");
			q.append("   and p.refnum_part = ip.refnum_part ");
			q.append("   and o.reg_pub_id  = rp.reg_pub_id ");
			q.append("   and ag.refnum_part(+) = a.refnum_part ");
			q.append("   and ag.refnum_part is null ");
			q.append("   and ip.cur_prtc  = pj.cur_prtc ");
			q.append("   and p.reg_pub_id = pj.reg_pub_id ");
			q.append("   and p.ofic_reg_id = pj.ofic_reg_id ");
			q.append("   AND p.cod_libro = glad.cod_libro ");
			q.append("   and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ");
			q.append("   and gla.cod_grupo_libro_area = '29' ");            // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("   and ip.tipo_pers = 'J' ");
			q.append("   and p.ESTADO != '2' ");
			q.append("   AND a.cod_acto = ta.cod_acto ");
			q.append("   and pl.cod_libro = p.cod_libro ");
			q.append("   and pl.cod_partic = ip.cod_partic ");
			q.append("   and tm.cod_marca = v.cod_marca ");
			q.append("   and tv.cod_tipo_vehi = v.cod_tipo_vehi   ");
			q.append("   and ta.cod_acto = glac.cod_acto ");
			q.append("  and glac.TIP_LIBRO = 'VEH'");
			q.append("  and glac.TIP_ACTO = 'DOM'");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("   ORDER BY estado desc, vigencia");

		       
			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaJuridicaVehicularRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				Vehiculo vehiculoBean = new Vehiculo();
				vehiculoBean.setDescripcionSubActo(rset.getString("descripcionActo"));
				vehiculoBean.setClase(rset.getString("clase"));
				vehiculoBean.setMarca(rset.getString("marca"));
				vehiculoBean.setMotor(rset.getString("motor"));
				vehiculoBean.setSerie(rset.getString("serie"));
				vehiculoBean.setPlaca(rset.getString("placa"));
				vehiculoBean.setNumeroPartida(rset.getString("numeroPartida"));
				//vehiculoBean.setCodigoLibro(rset.getString("cod_libro"));
				vehiculoBean.setOficinaRegistral(rset.getString("oficina"));
				vehiculoBean.setCodigoOficinaRegistral(rset.getString("ofic_reg_id"));
				vehiculoBean.setCodigoZonaRegistral(rset.getString("reg_pub_id"));
				vehiculoBean.setTipoParticipacion(rset.getString("tipoParticipacion"));
				vehiculoBean.setEstadoParticipante(rset.getString("estado"));	
				vehiculoBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBVehiculo.add(vehiculoBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBVehiculo;
	}
	
	public ArrayList busquedaPersonaNaturalEmbarcacionesPesquerasRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String apellidoPaterno = objetoSolicitudBean.getApe_pat();
		String apellidoMaterno = objetoSolicitudBean.getApe_mat();
		String nombres         = objetoSolicitudBean.getNombres();
		String tipoRegistros   = objetoSolicitudBean.getTipoRegistro();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBEmbarcaciones= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("select  ");
			q.append("  ta.descripcion as descripcionActo,  ");
			q.append("  reg_emb_pesq.NUM_MATRICULA as matricula, ");
			q.append("  reg_emb_pesq.nombre_emb as nombre, ");
			q.append("  tep.descripcion as tipo, ");
			q.append("  partida.num_partida as numeroPartida, ");
			q.append("  OFIC_REGISTRAL.NOMBRE as oficina,  ");
			q.append("  pl.nombre as tipoParticipacion,  ");
			q.append("  PARTIDA.REG_PUB_ID as reg_pub_id, ");
			q.append("  PARTIDA.OFIC_REG_ID as ofic_reg_id, ");
			q.append("  partida.area_reg_id as area_id, ");
			q.append("  AG.VIGENCIA as vigencia, ");
			q.append("  a.ts_inscrip as fechainscripcion, ");
			q.append("  ip.ESTADO as estado ");
			q.append("FROM  ");
			q.append("  reg_emb_pesq , PARTIDA, ");
			q.append("  OFIC_REGISTRAL , REGIS_PUBLICO,  ");
			q.append("  ind_prtc ip , ind_prtc_asiento_garantia ig, ");
			q.append("  grupo_libro_area gla , grupo_libro_area_det glad, ");
			q.append("  asiento_garantia ag , prtc_nat, TM_ACTO ta, ");
			q.append("  partic_libro pl , tm_tipo_emb_pesq tep, asiento a ");
			q.append("WHERE  ");
			q.append("  a.refnum_part=ag.refnum_part ");
			q.append("  and a.cod_acto=ag.cod_acto ");
			q.append("  and a.ns_asiento=ag.ns_asiento ");
			q.append("  and reg_emb_pesq.refnum_part=PARTIDA.REFNUM_PART ");
			q.append("  and prtc_nat.cur_prtc=ip.cur_prtc ");
			q.append("  and partida.refnum_part= ip.refnum_part ");
			q.append("  and ag.refnum_part=partida.refnum_part ");
			q.append("  and ag.refnum_part=ig.refnum_part ");
			q.append("  and ag.ns_asiento=ig.ns_asiento ");
			q.append("  and ag.cod_acto=ig.cod_acto ");
			q.append("  and ip.refnum_part=ig.refnum_part ");
			q.append("  and ip.cur_prtc=ig.cur_prtc ");
			q.append("  AND partida.ofic_reg_id = prtc_nat.ofic_reg_id   ");
			q.append("  AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID  ");
			q.append("  AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID  ");
			q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
			q.append("  AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
			q.append("  and PRTC_NAT.ape_pat = '"+apellidoPaterno+"' ");
			
			if (apellidoMaterno.length()>0){
				q.append(" and PRTC_NAT.ape_mat = '"+apellidoMaterno+"'");
			}
			if (nombres.length()>0){
				q.append("  and prtc_nat.nombres = '"+nombres+"' ");
			}
			
			q.append("  and PARTIDA.ESTADO != '2'  ");
			q.append("  AND IP.TIPO_PERS='N' ");
			q.append("  and reg_emb_pesq.estado='1' ");
			q.append("  AND partida.cod_libro = glad.cod_libro  ");
			q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append("  and gla.cod_grupo_libro_area ='29' ");                     // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("  AND ag.cod_acto = ta.cod_acto ");
			q.append("  and pl.cod_libro = partida.cod_libro ");
			q.append("  and pl.cod_partic =ip.cod_partic ");
			q.append("  and tep.cod_tipo_emb_pesq(+) = reg_emb_pesq.cod_tipo_emb_pesq  ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("  union ");
			q.append("  select  ");
			q.append("    ta.descripcion as descripcionActo,  ");
			q.append("    remp.NUM_MATRICULA as matricula, ");
			q.append("    remp.nombre_emb as nombre, ");
			q.append("    tep.descripcion as tipo, ");
			q.append("    p.num_partida as numeroPartida, ");
			q.append("    o.NOMBRE as oficina,  ");
			q.append("    pl.nombre as tipoParticipacion,  ");
			q.append("    P.REG_PUB_ID as reg_pub_id, ");
			q.append("    P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("    p.area_reg_id as area_id, ");
			q.append("    AG.VIGENCIA as vigencia, ");
			q.append("    a.ts_inscrip as fechainscripcion, ");
			q.append("    ip.ESTADO as estado ");
			q.append(" from reg_emb_pesq remp, partida p,  ");
			q.append("   asiento a, asiento_garantia ag, ");
			q.append("   OFIC_REGISTRAL o,REGIS_PUBLICO rp, ");
			q.append("   ind_prtc ip, prtc_nat pn, ");
			q.append("   grupo_libro_area gla, grupo_libro_area_det glad, ");
			q.append("   TM_ACTO ta,  partic_libro pl,  ");
			q.append("   grupo_libro_acto glac, ");
			q.append("   tm_tipo_emb_pesq tep ");
			q.append(" where  ");
			q.append("   remp.refnum_part = p.refnum_part ");
			q.append("   and a.refnum_part = p.refnum_part ");
			q.append("   and p.ofic_reg_id = o.ofic_reg_id ");
			q.append("   and p.reg_pub_id = rp.reg_pub_id ");
			q.append("   and p.refnum_part = ip.refnum_part ");
			q.append("   and o.reg_pub_id  = rp.reg_pub_id ");
			q.append("   and ag.refnum_part(+) = a.refnum_part ");
			q.append("   and ag.refnum_part is null ");
			q.append("   and ip.cur_prtc  = pn.cur_prtc ");
			q.append("   and p.reg_pub_id = pn.reg_pub_id ");
			q.append("   and p.ofic_reg_id = pn.ofic_reg_id ");
			q.append("   AND p.cod_libro = glad.cod_libro ");
			q.append("   and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ");
			q.append("   and gla.cod_grupo_libro_area = '29' ");              // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("   and p.ESTADO != '2' ");
			q.append("   and ip.tipo_pers = 'N' ");
			q.append("   and pn.ape_pat = '"+apellidoPaterno+"'  ");
			q.append("   and remp.estado='1' ");
			
			if (apellidoMaterno.length()>0){ 
				q.append(" and pn.ape_mat = '"+apellidoMaterno+"'  ");
			} 
			if (nombres.length()>0){ 
				q.append("  and pn.nombres = '"+nombres+"'  ");
			}	
			q.append("  AND a.cod_acto = ta.cod_acto ");
			q.append("  and pl.cod_libro = p.cod_libro ");
			q.append("  and pl.cod_partic =ip.cod_partic ");
			q.append("  and tep.cod_tipo_emb_pesq(+) = remp.cod_tipo_emb_pesq ");
			q.append("  and ta.cod_acto = glac.cod_acto ");
			q.append("  and glac.TIP_LIBRO = 'OTR'");
			q.append("  and glac.TIP_ACTO = 'DOM'");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append(" ORDER BY estado desc, vigencia, fechainscripcion  ");

		       
			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaNaturalEmbarcacionesPesquerasRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				EmbarcacionPesqueraBean embarcacionBean = new EmbarcacionPesqueraBean();
				embarcacionBean.setDescripciónActo(rset.getString("descripcionActo"));
				embarcacionBean.setNúmeroMatricula(rset.getString("matricula"));
				embarcacionBean.setNombreEmbarcacion(rset.getString("nombre"));
				embarcacionBean.setTipoEmbarcación(rset.getString("tipo"));
				embarcacionBean.setFechaInscripción(rset.getString("fechainscripcion"));
				embarcacionBean.setNúmeroPartida(rset.getString("numeroPartida"));
				embarcacionBean.setNombreOficinaRegistral(rset.getString("oficina"));
				embarcacionBean.setTipoParticipación(rset.getString("tipoParticipacion"));
				//embarcacionBean.setCodigoLibro(rset.getString("cod_libro"));
				embarcacionBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				embarcacionBean.setZonaRegistral(rset.getString("reg_pub_id"));
				embarcacionBean.setEstadoParticipacion(rset.getString("estado"));	
				embarcacionBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBEmbarcaciones.add(embarcacionBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBEmbarcaciones;
	}
	
	public ArrayList busquedaPersonaJuridicaEmbarcacionesPesquerasRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String razonSocial	   = objetoSolicitudBean.getRaz_soc();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBEmbarcaciones= new ArrayList();
		
		if(razonSocial==null){
			return resultadoRJBEmbarcaciones;
		}
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("select  ");
			q.append("  ta.descripcion as descripcionActo,  ");
			q.append("  reg_emb_pesq.NUM_MATRICULA as matricula, ");
			q.append("  reg_emb_pesq.nombre_emb as nombre, ");
			q.append("  tep.descripcion as tipo, ");
			q.append("  partida.num_partida as numeroPartida, ");
			q.append("  OFIC_REGISTRAL.NOMBRE as oficina,  ");
			q.append("  pl.nombre as tipoParticipacion,  ");
			q.append("  PARTIDA.REG_PUB_ID as reg_pub_id, ");
			q.append("  PARTIDA.OFIC_REG_ID as ofic_reg_id, ");
			q.append("  PARTIDA.area_reg_id as area_id, ");
			q.append("  AG.VIGENCIA as vigencia, ");
			q.append("  a.ts_inscrip as fechainscripcion, ");
			q.append("  ip.ESTADO as estado ");
			q.append("FROM  ");
			q.append("  reg_emb_pesq , PARTIDA, ");
			q.append("  OFIC_REGISTRAL , REGIS_PUBLICO,  ");
			q.append("  ind_prtc ip , ind_prtc_asiento_garantia ig, ");
			q.append("  grupo_libro_area gla , grupo_libro_area_det glad,  ");
			q.append("  asiento_garantia ag , prtc_jur, ");
			q.append("  TM_ACTO ta, partic_libro pl,asiento a,  ");
			q.append("  tm_tipo_emb_pesq tep ");
			q.append("WHERE  ");
			q.append("  a.refnum_part=ag.refnum_part ");
			q.append("  and a.cod_acto=ag.cod_acto ");
			q.append("  and a.ns_asiento=ag.ns_asiento ");
			q.append("  and reg_emb_pesq.refnum_part=PARTIDA.REFNUM_PART ");
			q.append("  and prtc_jur.cur_prtc=ip.cur_prtc ");
			q.append("  and ag.refnum_part=partida.refnum_part ");
			q.append("  and ag.refnum_part=ig.refnum_part ");
			q.append("  and ag.ns_asiento=ig.ns_asiento ");
			q.append("  and ag.cod_acto=ig.cod_acto ");
			q.append("  and ip.refnum_part=ig.refnum_part ");
			q.append("  and ip.cur_prtc=ig.cur_prtc ");
			q.append("  AND partida.ofic_reg_id = prtc_jur.ofic_reg_id ");
			q.append("  AND PARTIDA.REG_PUB_ID  = prtc_jur.reg_pub_id ");
			q.append("  AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID  ");
			q.append("  AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
			q.append("  AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("  AND PRTC_JUR.RAZON_SOCIAL = '"+razonSocial+"' ");
			}	
			q.append("  and PARTIDA.ESTADO != '2'  ");
			q.append("  AND IP.TIPO_PERS='J' ");
			q.append("  and reg_emb_pesq.estado='1' ");
			q.append("  AND partida.cod_libro = glad.cod_libro  ");
			q.append("  and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append("  and gla.cod_grupo_libro_area ='29' ");                       // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("  AND ag.cod_acto = ta.cod_acto ");
			q.append("  and pl.cod_libro = partida.cod_libro ");
			q.append("  and pl.cod_partic = ip.cod_partic ");
			q.append("  and tep.cod_tipo_emb_pesq(+) = reg_emb_pesq.cod_tipo_emb_pesq ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("  union ");
			q.append("  select  ");
			q.append("	  ta.descripcion as descripcionActo,  ");
			q.append("	  remp.NUM_MATRICULA as matricula, ");
			q.append("	  remp.nombre_emb as nombre, ");
			q.append("	  tep.descripcion as tipo, ");
			q.append("	  p.num_partida as numeroPartida, ");
			q.append("	  o.NOMBRE as oficina,  ");
			q.append("	  pl.nombre as tipoParticipacion,  ");
			q.append("	  P.REG_PUB_ID as reg_pub_id, ");
			q.append("	  P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("	  p.area_reg_id as area_id, ");
			q.append("	  AG.VIGENCIA as vigencia, ");
			q.append("	  a.ts_inscrip as fechainscripcion, ");
			q.append("	  ip.ESTADO as estado ");
			q.append(" from reg_emb_pesq remp, partida p,  ");
			q.append("   asiento a, asiento_garantia ag, ");
			q.append("   OFIC_REGISTRAL o,REGIS_PUBLICO rp, ");
			q.append("   ind_prtc ip, prtc_jur pj, ");
			q.append("   grupo_libro_area gla, grupo_libro_area_det glad, ");
			q.append("   TM_ACTO ta,  partic_libro pl,  ");
			q.append("    grupo_libro_acto glac, ");
			q.append("   tm_tipo_emb_pesq tep ");
			q.append(" where  ");
			q.append("   remp.refnum_part = p.refnum_part ");
			q.append("   and a.refnum_part = p.refnum_part ");
			q.append("   and p.ofic_reg_id = o.ofic_reg_id ");
			q.append("   and p.reg_pub_id = rp.reg_pub_id ");
			q.append("   and p.refnum_part = ip.refnum_part ");
			q.append("   and o.reg_pub_id  = rp.reg_pub_id ");
			q.append("   and ag.refnum_part(+) = a.refnum_part ");
			q.append("   and ag.refnum_part is null ");
			q.append("   and ip.cur_prtc  = pj.cur_prtc ");
			q.append("   and p.reg_pub_id = pj.reg_pub_id ");
			q.append("   and p.ofic_reg_id = pj.ofic_reg_id ");
			q.append("   AND p.cod_libro = glad.cod_libro ");
			q.append("   and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area ");
			q.append("   and gla.cod_grupo_libro_area = '29' ");                  // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("   and p.ESTADO != '2' ");
			q.append("   and remp.estado='1' ");
			q.append("   and ip.tipo_pers = 'J' ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("   AND pj.RAZON_SOCIAL = '"+razonSocial+"'");
			}	
			q.append("   AND a.cod_acto = ta.cod_acto ");
			q.append("   and pl.cod_libro = p.cod_libro ");
			q.append("   and pl.cod_partic =ip.cod_partic ");
			q.append("   and tep.cod_tipo_emb_pesq(+) = remp.cod_tipo_emb_pesq ");
			q.append("   and ta.cod_acto = glac.cod_acto ");
			q.append("   and glac.TIP_LIBRO='OTR' ");
			q.append("   and glac.TIP_ACTO='DOM' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("  ORDER BY estado desc, vigencia, fechainscripcion  ");

			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaJuridicaEmbarcacionesPesquerasRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				EmbarcacionPesqueraBean embarcacionBean = new EmbarcacionPesqueraBean();
				embarcacionBean.setDescripciónActo(rset.getString("descripcionActo"));
				embarcacionBean.setNúmeroMatricula(rset.getString("matricula"));
				embarcacionBean.setNombreEmbarcacion(rset.getString("nombre"));
				embarcacionBean.setTipoEmbarcación(rset.getString("tipo"));
				embarcacionBean.setFechaInscripción(rset.getString("fechainscripcion"));
				embarcacionBean.setNúmeroPartida(rset.getString("numeroPartida"));
				embarcacionBean.setNombreOficinaRegistral(rset.getString("oficina"));
				embarcacionBean.setTipoParticipación(rset.getString("tipoParticipacion"));
				//embarcacionBean.setCodigoLibro(rset.getString("cod_libro"));
				embarcacionBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				embarcacionBean.setZonaRegistral(rset.getString("reg_pub_id"));
				embarcacionBean.setEstadoParticipacion(rset.getString("estado"));	
				embarcacionBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBEmbarcaciones.add(embarcacionBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBEmbarcaciones;
	}

	public ArrayList busquedaPersonaNaturalBuquesRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String apellidoPaterno = objetoSolicitudBean.getApe_pat();
		String apellidoMaterno = objetoSolicitudBean.getApe_mat();
		String nombres         = objetoSolicitudBean.getNombres();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBBuques= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RB.NOMBRE AS NOMBRE, ");
			q.append("       RB.NUM_MATRICULA AS matricula, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("       REG_BUQUES RB, ");
			q.append("       PARTIDA P, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       IND_PRTC IP, ");
			q.append("       IND_PRTC_ASIENTO_GARANTIA IG, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PRTC_NAT PN, ");
			q.append("       TM_ACTO TA, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("	   asiento a ");
			q.append(" WHERE  ");
			q.append("   a.refnum_part=ag.refnum_part ");
			q.append("   and a.cod_acto=ag.cod_acto ");
			q.append("   and a.ns_asiento=ag.ns_asiento ");
			q.append("   and RB.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND PN.CUR_PRTC     = IP.CUR_PRTC ");
			q.append("   AND P.REFNUM_PART   = IP.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND AG.NS_ASIENTO   = IG.NS_ASIENTO ");
			q.append("   AND AG.COD_ACTO     = IG.COD_ACTO ");
			q.append("   AND IP.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC     = IG.CUR_PRTC ");
			q.append("   AND P.OFIC_REG_ID   = PN.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = PN.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID   = RO.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID   = RP.REG_PUB_ID ");
			q.append("   AND AG.COD_ACTO    = TA.COD_ACTO ");
			q.append("   AND PL.COD_LIBRO   = P.COD_LIBRO ");
			q.append("   AND PL.COD_PARTIC  = IP.COD_PARTIC    ");
			q.append("   AND P.COD_LIBRO     = GLAD.COD_LIBRO ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			q.append("   AND PN.APE_PAT = '"+apellidoPaterno+"' ");
			if (apellidoMaterno.length()>0){ 
				q.append(" AND PN.APE_MAT = '"+apellidoMaterno+"' ");
			}
			if (nombres.length()>0){ 
				q.append("  AND PN.NOMBRES = '"+nombres+"' ");
			}	
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND IP.TIPO_PERS    = 'N' ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = '29' ");          // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("   AND RB.ESTADO      = '1' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("   union ");
			q.append("   SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RB.NOMBRE AS NOMBRE, ");
			q.append("       RB.NUM_MATRICULA AS matricula, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("  	   ASIENTO A, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PARTIDA P, ");
			q.append("       REG_BUQUES RB, ");
			q.append("       IND_PRTC IP, ");
			q.append("       PRTC_NAT PN, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       TM_ACTO TA, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("	   grupo_libro_acto glac ");
			q.append(" WHERE AG.REFNUM_PART (+)= A.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART IS NULL ");
			q.append("   AND P.REFNUM_PART  = A.REFNUM_PART ");
			q.append("   AND RB.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND RB.ESTADO      = '1' ");
			q.append("   AND IP.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC    = PN.CUR_PRTC ");
			q.append("   AND P.REG_PUB_ID   = PN.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = PN.OFIC_REG_ID ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			q.append("   AND P.COD_LIBRO    = GLAD.COD_LIBRO ");
			q.append("   AND A.COD_ACTO     = TA.COD_ACTO ");
			q.append("   AND P.REG_PUB_ID   = RP.REG_PUB_ID   ");
			q.append("   AND P.REG_PUB_ID   = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID  = RP.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = RO.OFIC_REG_ID ");
			q.append("   AND P.COD_LIBRO    = PL.COD_LIBRO ");
			q.append("   AND IP.COD_PARTIC  = PL.COD_PARTIC ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = '29' ");             // codigoGrupoLibroArea para RJB CremHistorico= 29
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND RB.ESTADO      = '1' ");
			q.append("   AND IP.TIPO_PERS    = 'N' ");
			q.append("   AND PN.APE_PAT = '"+apellidoPaterno+"' ");
			if (apellidoMaterno.length()>0){ 
				q.append(" AND PN.APE_MAT = '"+apellidoMaterno+"' ");
			} 
			if (nombres.length()>0){ 
				q.append(" AND PN.NOMBRES = '"+nombres+"' ");
			}	
			q.append("   and ta.cod_acto = glac.cod_acto ");
			q.append("   and glac.TIP_LIBRO='OTR' ");
			q.append("   and glac.TIP_ACTO='DOM' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("   ORDER BY estado desc, vigencia, fechainscripcion  ");

			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaNaturalBuquesRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				BuquesBean buquesBean = new BuquesBean();
				buquesBean.setDescripciónActo(rset.getString("descripcionActo"));
				buquesBean.setNúmeroMatricula(rset.getString("matricula"));
				buquesBean.setNombreBuque(rset.getString("nombre"));
				//buquesBean.setTipoBuque(rset.getString("tipo"));
				buquesBean.setFechaInscripción(rset.getString("fechainscripcion"));
				buquesBean.setNúmeroPartida(rset.getString("NUMEROPARTIDA"));
				buquesBean.setNombreOficinaRegistral(rset.getString("oficina"));
				buquesBean.setTipoParticipación(rset.getString("TIPOPARTICIPACION"));
				//buquesBean.setCodigoLibro(rset.getString("cod_libro"));
				buquesBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				buquesBean.setZonaRegistral(rset.getString("reg_pub_id"));
				buquesBean.setEstadoParticipacion(rset.getString("estado"));	
				buquesBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBBuques.add(buquesBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBBuques;
	}

	public ArrayList busquedaPersonaJuridicaBuquesRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String razonSocial	   = objetoSolicitudBean.getRaz_soc();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBBuques= new ArrayList();
		
		if(razonSocial==null){
			return resultadoRJBBuques;
		}
		
		if(razonSocial==null){
			return resultadoRJBBuques;
		}
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RB.NOMBRE AS NOMBRE, ");
			q.append("       RB.NUM_MATRICULA AS matricula, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("  	   REG_BUQUES RB, ");
			q.append("       PARTIDA P, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       IND_PRTC IP, ");
			q.append("       IND_PRTC_ASIENTO_GARANTIA IG, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PRTC_JUR PJ, ");
			q.append("       TM_ACTO TA, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("	   asiento a ");
			q.append(" WHERE ");
			q.append("   a.refnum_part = ag.refnum_part ");
			q.append("   and a.cod_acto = ag.cod_acto ");
			q.append("   and a.ns_asiento = ag.ns_asiento  ");
			q.append("   and RB.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND PJ.CUR_PRTC     = IP.CUR_PRTC ");
			q.append("   AND P.REFNUM_PART   = IP.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND AG.NS_ASIENTO   = IG.NS_ASIENTO ");
			q.append("   AND AG.COD_ACTO     = IG.COD_ACTO ");
			q.append("   AND IP.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC     = IG.CUR_PRTC ");
			q.append("   AND P.OFIC_REG_ID   = PJ.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = PJ.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID   = RO.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID   = RP.REG_PUB_ID ");
			q.append("   AND AG.COD_ACTO     = TA.COD_ACTO ");
			q.append("   AND PL.COD_LIBRO    = P.COD_LIBRO ");
			q.append("   AND PL.COD_PARTIC   = IP.COD_PARTIC ");
			q.append("   AND P.COD_LIBRO     = GLAD.COD_LIBRO ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("   AND PJ.RAZON_SOCIAL = '"+razonSocial+"' ");
			}	
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND RB.ESTADO      = '1' ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = '29' ");
			q.append("   AND IP.TIPO_PERS    = 'J' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("union ");
			q.append("SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RB.NOMBRE AS NOMBRE, ");
			q.append("       RB.NUM_MATRICULA AS matricula, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("  	 ASIENTO A, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PARTIDA P, ");
			q.append("       REG_BUQUES RB, ");
			q.append("       IND_PRTC IP, ");
			q.append("       PRTC_JUR PJ, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       TM_ACTO TA, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("       grupo_libro_acto glac ");
			q.append(" WHERE  ");
			q.append("   AG.REFNUM_PART (+)= A.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART IS NULL ");
			q.append("   AND P.REFNUM_PART  = A.REFNUM_PART ");
			q.append("   AND RB.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND IP.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC    = PJ.CUR_PRTC ");
			q.append("   AND P.REG_PUB_ID   = PJ.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = PJ.OFIC_REG_ID ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			q.append("   AND P.COD_LIBRO    = GLAD.COD_LIBRO ");
			q.append("   AND A.COD_ACTO     = TA.COD_ACTO ");
			q.append("   AND P.REG_PUB_ID   = RP.REG_PUB_ID   ");
			q.append("   AND P.REG_PUB_ID   = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID  = RP.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = RO.OFIC_REG_ID ");
			q.append("   AND P.COD_LIBRO    = PL.COD_LIBRO ");
			q.append("   AND IP.COD_PARTIC  = PL.COD_PARTIC ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = '29'  ");
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND RB.ESTADO      = '1' ");
			q.append("   AND IP.TIPO_PERS    = 'J' ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("   AND PJ.RAZON_SOCIAL = '"+razonSocial+"' ");
			}	
			q.append("   and ta.cod_acto = glac.cod_acto ");
			q.append("   and glac.TIP_LIBRO='OTR' ");
			q.append("   and glac.TIP_ACTO='DOM' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("   ORDER BY estado desc, vigencia, fechainscripcion ");


			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaJuridicaBuquesRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				BuquesBean buquesBean = new BuquesBean();
				buquesBean.setDescripciónActo(rset.getString("descripcionActo"));
				buquesBean.setNúmeroMatricula(rset.getString("matricula"));
				buquesBean.setNombreBuque(rset.getString("nombre"));
				//buquesBean.setTipoBuque(rset.getString("tipo"));
				buquesBean.setFechaInscripción(rset.getString("fechainscripcion"));
				buquesBean.setNúmeroPartida(rset.getString("NUMEROPARTIDA"));
				buquesBean.setNombreOficinaRegistral(rset.getString("oficina"));
				buquesBean.setTipoParticipación(rset.getString("TIPOPARTICIPACION"));
				//buquesBean.setCodigoLibro(rset.getString("cod_libro"));
				buquesBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				buquesBean.setZonaRegistral(rset.getString("reg_pub_id"));
				buquesBean.setEstadoParticipacion(rset.getString("estado"));	
				buquesBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBBuques.add(buquesBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBBuques;
	}
	
	
	
	public ArrayList busquedaPersonaNaturalAeronaveRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String apellidoPaterno = objetoSolicitudBean.getApe_pat();
		String apellidoMaterno = objetoSolicitudBean.getApe_mat();
		String nombres         = objetoSolicitudBean.getNombres();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBAeronave= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RA.NUM_MATRICULA AS matricula, ");
			q.append("       RA.SERIE AS SERIE, ");
			q.append("	   TIPNAVE.descripcion as tipo, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("       REG_AERONAVES RA, ");
			q.append("       PARTIDA P, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       IND_PRTC IP, ");
			q.append("       IND_PRTC_ASIENTO_GARANTIA IG, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PRTC_NAT PN, ");
			q.append("       TM_ACTO TA, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("	   asiento a, ");
			q.append("	   TM_TIPO_AERONAVE TIPNAVE ");
			q.append(" WHERE  ");
			q.append("   a.refnum_part=ag.refnum_part ");
			q.append("   and a.cod_acto=ag.cod_acto ");
			q.append("   and a.ns_asiento=ag.ns_asiento ");
			q.append("   and RA.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND PN.CUR_PRTC     = IP.CUR_PRTC ");
			q.append("   AND P.REFNUM_PART   = IP.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND AG.NS_ASIENTO   = IG.NS_ASIENTO ");
			q.append("   AND AG.COD_ACTO     = IG.COD_ACTO ");
			q.append("   AND IP.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC     = IG.CUR_PRTC ");
			q.append("   AND P.OFIC_REG_ID   = PN.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = PN.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID   = RO.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID   = RP.REG_PUB_ID ");
			q.append("   AND AG.COD_ACTO    = TA.COD_ACTO ");
			q.append("   AND PL.COD_LIBRO   = P.COD_LIBRO ");
			q.append("   AND PL.COD_PARTIC  = IP.COD_PARTIC    ");
			q.append("   AND P.COD_LIBRO     = GLAD.COD_LIBRO ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			q.append("   and TIPNAVE.COD_TIPO_AERONAVE(+) = RA.COD_TIPO_AERONAVE ");
			q.append("   AND PN.APE_PAT = '"+apellidoPaterno+"' ");
			
			if (apellidoMaterno.length()>0){ 
				q.append("  AND PN.APE_MAT = '"+apellidoMaterno+"' ");
			}
			if (nombres.length()>0){
				q.append("  AND PN.NOMBRES = '"+nombres+"' ");
			}	
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND IP.TIPO_PERS    = 'N' ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = '29' ");
			q.append("   AND RA.ESTADO      = '1' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append(" union ");
			q.append(" SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RA.NUM_MATRICULA AS matricula, ");
			q.append("       RA.SERIE AS SERIE, ");
			q.append("	     TIPNAVE.descripcion as tipo, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("       ASIENTO A, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PARTIDA P, ");
			q.append("       REG_AERONAVES RA, ");
			q.append("       IND_PRTC IP, ");
			q.append("       PRTC_NAT PN, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       TM_ACTO TA, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("	   TM_TIPO_AERONAVE TIPNAVE, ");
			q.append("	   grupo_libro_acto glac ");
			q.append(" WHERE  ");
			q.append("   AG.REFNUM_PART (+)= A.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART IS NULL ");
			q.append("   AND P.REFNUM_PART  = A.REFNUM_PART ");
			q.append("   AND RA.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND RA.ESTADO      = '1' ");
			q.append("   AND IP.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC    = PN.CUR_PRTC ");
			q.append("   AND P.REG_PUB_ID   = PN.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = PN.OFIC_REG_ID ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			q.append("   AND P.COD_LIBRO    = GLAD.COD_LIBRO ");
			q.append("   AND A.COD_ACTO     = TA.COD_ACTO ");
			q.append("   AND P.REG_PUB_ID   = RP.REG_PUB_ID   ");
			q.append("   AND P.REG_PUB_ID   = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID  = RP.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = RO.OFIC_REG_ID ");
			q.append("   AND P.COD_LIBRO    = PL.COD_LIBRO ");
			q.append("   AND IP.COD_PARTIC  = PL.COD_PARTIC ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = 29 ");
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND RA.ESTADO      = '1' ");
			q.append("   AND IP.TIPO_PERS    = 'N' ");
			q.append("   and TIPNAVE.COD_TIPO_AERONAVE(+) = RA.COD_TIPO_AERONAVE ");
			q.append("   AND PN.APE_PAT = '"+apellidoPaterno+"' ");
			
			if (apellidoMaterno.length()>0){
				q.append(" AND PN.APE_MAT = '"+apellidoMaterno+"' ");
			} 
			if (nombres.length()>0){
				q.append("  AND PN.NOMBRES = '"+nombres+"' ");
			}	
			q.append("   and ta.cod_acto = glac.cod_acto ");
			q.append("   and glac.TIP_LIBRO='OTR' ");
			q.append("   and glac.TIP_ACTO='DOM' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("   ORDER BY estado desc, vigencia, fechainscripcion   ");


			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaNaturalAeronaveRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				AeronaveBean aeronaveBean = new AeronaveBean();
				aeronaveBean.setDescripciónActo(rset.getString("descripcionActo"));
				aeronaveBean.setNúmeroMatricula(rset.getString("matricula"));
				aeronaveBean.setNúmeroSerie(rset.getString("SERIE"));
				aeronaveBean.setTipoAeronave(rset.getString("tipo"));
				aeronaveBean.setFechaInscripción(rset.getString("fechainscripcion"));
				aeronaveBean.setNúmeroPartida(rset.getString("NUMEROPARTIDA"));
				aeronaveBean.setNombreOficinaRegistral(rset.getString("oficina"));
				aeronaveBean.setTipoParticipación(rset.getString("TIPOPARTICIPACION"));
				//aeronaveBean.setCodigoLibro(rset.getString("cod_libro"));
				aeronaveBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				aeronaveBean.setZonaRegistral(rset.getString("reg_pub_id"));
				aeronaveBean.setEstadoParticipacion(rset.getString("estado"));	
				aeronaveBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBAeronave.add(aeronaveBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBAeronave;
	}
	
	public ArrayList busquedaPersonaJuridicaAeronaveRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String razonSocial	   = objetoSolicitudBean.getRaz_soc();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBAeronave= new ArrayList();
		
		if(razonSocial==null){
			return resultadoRJBAeronave;
		}
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RA.NUM_MATRICULA AS matricula, ");
			q.append("       RA.SERIE AS SERIE, ");
			q.append("	   TIPNAVE.descripcion as tipo, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("       REG_AERONAVES RA, ");
			q.append("       PARTIDA P, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       IND_PRTC IP, ");
			q.append("       IND_PRTC_ASIENTO_GARANTIA IG, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PRTC_JUR PJ, ");
			q.append("       TM_ACTO TA, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("	   asiento a, ");
			q.append("	   TM_TIPO_AERONAVE TIPNAVE ");
			q.append(" WHERE  ");
			q.append("   a.refnum_part=ag.refnum_part ");
			q.append("   and a.cod_acto=ag.cod_acto ");
			q.append("   and a.ns_asiento=ag.ns_asiento ");
			q.append("   and RA.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND PJ.CUR_PRTC     = IP.CUR_PRTC ");
			q.append("   AND P.REFNUM_PART   = IP.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = P.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND AG.NS_ASIENTO   = IG.NS_ASIENTO ");
			q.append("   AND AG.COD_ACTO     = IG.COD_ACTO ");
			q.append("   AND IP.REFNUM_PART  = IG.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC     = IG.CUR_PRTC ");
			q.append("   AND P.OFIC_REG_ID   = PJ.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = PJ.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID   = RO.OFIC_REG_ID ");
			q.append("   AND P.REG_PUB_ID    = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID   = RP.REG_PUB_ID ");
			q.append("   AND AG.COD_ACTO     = TA.COD_ACTO ");
			q.append("   AND PL.COD_LIBRO    = P.COD_LIBRO ");
			q.append("   AND PL.COD_PARTIC   = IP.COD_PARTIC ");
			q.append("   AND P.COD_LIBRO     = GLAD.COD_LIBRO ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			q.append("   and TIPNAVE.COD_TIPO_AERONAVE(+) = RA.COD_TIPO_AERONAVE ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("   AND PJ.RAZON_SOCIAL = '"+razonSocial+"' ");
			}	
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND RA.ESTADO      = '1' ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = '29' ");
			q.append("   AND IP.TIPO_PERS    = 'J' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("union ");
			q.append("SELECT  ");
			q.append("       TA.DESCRIPCION as descripcionActo, ");
			q.append("       RA.NUM_MATRICULA AS matricula, ");
			q.append("       RA.SERIE AS SERIE, ");
			q.append("	   TIPNAVE.descripcion as tipo, ");
			q.append("       P.NUM_PARTIDA AS NUMEROPARTIDA, ");
			q.append("       RO.NOMBRE AS OFICINA, ");
			q.append("       PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("       P.REG_PUB_ID as reg_pub_id, ");
			q.append("       P.OFIC_REG_ID as ofic_reg_id, ");
			q.append("       p.area_reg_id as area_id, ");
			q.append("       AG.VIGENCIA as vigencia, ");
			q.append("       a.ts_inscrip as fechainscripcion, ");
			q.append("       ip.ESTADO as estado ");
			q.append("  FROM  ");
			q.append("       ASIENTO A, ");
			q.append("       ASIENTO_GARANTIA AG, ");
			q.append("       PARTIDA P, ");
			q.append("       REG_AERONAVES RA, ");
			q.append("       IND_PRTC IP, ");
			q.append("       PRTC_JUR PJ, ");
			q.append("       GRUPO_LIBRO_AREA GLA, ");
			q.append("       GRUPO_LIBRO_AREA_DET GLAD, ");
			q.append("       TM_ACTO TA, ");
			q.append("       OFIC_REGISTRAL RO, ");
			q.append("       REGIS_PUBLICO RP, ");
			q.append("       PARTIC_LIBRO PL, ");
			q.append("	   TM_TIPO_AERONAVE TIPNAVE, ");
			q.append("	   grupo_libro_acto glac ");
			q.append(" WHERE  ");
			q.append("   AG.REFNUM_PART (+)= A.REFNUM_PART ");
			q.append("   AND AG.REFNUM_PART IS NULL ");
			q.append("   AND P.REFNUM_PART  = A.REFNUM_PART ");
			q.append("   AND RA.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND RA.ESTADO      = '1' ");
			q.append("   AND IP.REFNUM_PART = P.REFNUM_PART ");
			q.append("   AND IP.CUR_PRTC    = PJ.CUR_PRTC ");
			q.append("   AND P.REG_PUB_ID   = PJ.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = PJ.OFIC_REG_ID ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = GLAD.COD_GRUPO_LIBRO_AREA ");
			q.append("   AND P.COD_LIBRO    = GLAD.COD_LIBRO ");
			q.append("   AND A.COD_ACTO     = TA.COD_ACTO ");
			q.append("   AND P.REG_PUB_ID   = RP.REG_PUB_ID   ");
			q.append("   AND P.REG_PUB_ID   = RO.REG_PUB_ID ");
			q.append("   AND RO.REG_PUB_ID  = RP.REG_PUB_ID ");
			q.append("   AND P.OFIC_REG_ID  = RO.OFIC_REG_ID ");
			q.append("   AND P.COD_LIBRO    = PL.COD_LIBRO ");
			q.append("   AND IP.COD_PARTIC  = PL.COD_PARTIC ");
			q.append("   and TIPNAVE.COD_TIPO_AERONAVE(+) = RA.COD_TIPO_AERONAVE ");
			q.append("   AND GLA.COD_GRUPO_LIBRO_AREA = '29' ");
			q.append("   AND P.ESTADO       != '2' ");
			q.append("   AND RA.ESTADO      = '1' ");
			q.append("   AND IP.TIPO_PERS    = 'J' ");
			if (razonSocial != null && razonSocial.length()>0){
				q.append("   AND PJ.RAZON_SOCIAL = '"+razonSocial+"' ");
			}	
			q.append("   and ta.cod_acto = glac.cod_acto ");
			q.append("   and glac.TIP_LIBRO='OTR' ");
			q.append("   and glac.TIP_ACTO='DOM' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append("   ORDER BY estado desc, vigencia, fechainscripcion   ");

			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaJuridicaAeronaveRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				AeronaveBean aeronaveBean = new AeronaveBean();
				aeronaveBean.setDescripciónActo(rset.getString("descripcionActo"));
				aeronaveBean.setNúmeroMatricula(rset.getString("matricula"));
				aeronaveBean.setNúmeroSerie(rset.getString("SERIE"));
				aeronaveBean.setTipoAeronave(rset.getString("tipo"));
				aeronaveBean.setFechaInscripción(rset.getString("fechainscripcion"));
				aeronaveBean.setNúmeroPartida(rset.getString("NUMEROPARTIDA"));
				aeronaveBean.setNombreOficinaRegistral(rset.getString("oficina"));
				aeronaveBean.setTipoParticipación(rset.getString("TIPOPARTICIPACION"));
				//aeronaveBean.setCodigoLibro(rset.getString("cod_libro"));
				aeronaveBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				aeronaveBean.setZonaRegistral(rset.getString("reg_pub_id"));
				aeronaveBean.setEstadoParticipacion(rset.getString("estado"));	
				aeronaveBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBAeronave.add(aeronaveBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBAeronave;
	}
	
	public ArrayList busquedaPersonaJuridicaRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String razonSocial	   = objetoSolicitudBean.getRaz_soc();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBPersonaJuridica= new ArrayList();
		
		if(razonSocial==null){
			return resultadoRJBPersonaJuridica;
		}
		
		try{
			
			StringBuffer q  = new StringBuffer();
			
			q.append("SELECT distinct ");
			q.append("   rpj.razon_soc  as razonSocial,  ");
			q.append("   OFIC_REGISTRAL.NOMBRE as OFICINA, ");
			q.append("   PARTIDA.NUM_PARTIDA as NUMEROPARTIDA,  ");
			q.append("   PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("   PARTIDA.REG_PUB_ID as reg_pub_id, ");
			q.append("   PARTIDA.OFIC_REG_ID as ofic_reg_id, ");
			q.append("   partida.area_reg_id as area_id, ");
			q.append("   AG.VIGENCIA as vigencia, ");
			q.append("   IND_PRTC.ESTADO as estado ");
			q.append("FROM  ");
			q.append("   PRTC_JUR,IND_PRTC, PARTIDA, ");
			q.append("   OFIC_REGISTRAL,REGIS_PUBLICO,  ");
			q.append("   grupo_libro_area gla, grupo_libro_area_det glad, ");
			q.append("   asiento_garantia ag, TM_ACTO ta,   ");
			q.append("   partic_libro pl, asiento a, ");
			q.append("   IND_PRTC_ASIENTO_GARANTIA IG, raz_soc_pj rpj  ");
			q.append("WHERE  ");
			q.append(" 	  a.refnum_part = ag.refnum_part ");
			q.append("    AND a.cod_acto = ag.cod_acto ");
			q.append("    AND a.ns_asiento = ag.ns_asiento ");
			q.append("    AND PRTC_JUR.CUR_PRTC = IND_PRTC.CUR_PRTC  ");
			q.append("    AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART  ");
			q.append("    AND ag.refnum_part = partida.refnum_part ");
			q.append("    and ag.refnum_part = ig.refnum_part ");
			q.append("    and ag.ns_asiento = ig.ns_asiento ");
			q.append("    and ag.cod_acto = ig.cod_acto ");
			q.append("    and IND_PRTC.refnum_part = ig.refnum_part ");
			q.append("    and IND_PRTC.cur_prtc = ig.cur_prtc ");
			q.append("    AND partida.ofic_reg_id = prtc_jur.ofic_reg_id  ");
			q.append("    AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID  ");
			q.append("    AND PARTIDA.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID ");
			q.append("    AND PARTIDA.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID ");
			q.append("    AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID ");
			
			if (razonSocial != null && razonSocial.length()>0){
				q.append("    AND prtc_jur.RAZON_SOCIAL = '"+razonSocial+"'");
			}   
			q.append("    AND partida.estado = '1' ");
			q.append("    AND partida.cod_libro = glad.cod_libro  ");
			q.append("    AND gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append("    AND gla.cod_grupo_libro_area ='30' ");
			q.append("    AND IND_PRTC.TIPO_PERS = 'J' ");
			q.append("    AND ag.cod_acto = ta.cod_acto ");
			q.append("    AND pl.cod_libro = partida.cod_libro ");
			q.append("    AND pl.cod_partic = ind_prtc.cod_partic ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			q.append("    AND rpj.refnum_part = PARTIDA.REFNUM_PART ");
			q.append("union ");
			q.append("SELECT distinct ");
			q.append("    rpj.razon_soc  as razonSocial,  ");
			q.append("    ofic_registral.nombre as OFICINA,   ");
			q.append("	partida.num_partida as NUMEROPARTIDA, ");
			q.append("	PL.NOMBRE AS TIPOPARTICIPACION, ");
			q.append("    PARTIDA.REG_PUB_ID as reg_pub_id, ");
			q.append("    PARTIDA.OFIC_REG_ID as ofic_reg_id, ");
			q.append("    partida.area_reg_id as area_id, ");
			q.append("    AG.VIGENCIA as vigencia, ");
			q.append("    IND_PRTC.ESTADO as estado ");
			q.append("FROM  ");
			q.append("    prtc_jur, ind_prtc, partida,  ");
			q.append("	ofic_registral, regis_publico ,  ");
			q.append("	grupo_libro_area gla, grupo_libro_area_det glad, ");
			q.append("	 asiento_garantia ag ,  ");
			q.append("    asiento a , TM_ACTO ta,   ");
			q.append("    partic_libro pl,  grupo_libro_acto glac, raz_soc_pj rpj  ");
			q.append("WHERE  ");
			q.append("    PRTC_JUR.CUR_PRTC=IND_PRTC.CUR_PRTC   ");
			q.append("    AND ind_prtc.refnum_part = partida.refnum_part  ");
			q.append("    and a.refnum_part=ag.refnum_part (+) ");
			q.append("    and ag.refnum_part is null ");
			q.append("    and a.refnum_part=partida.refnum_part ");
			q.append("    AND IND_PRTC.REFNUM_PART=A.REFNUM_PART ");
			q.append("    AND partida.ofic_reg_id = prtc_jur.ofic_reg_id   ");
			q.append("    AND PARTIDA.REG_PUB_ID  = prtc_jur.REG_PUB_ID  ");
			q.append("    AND partida.ofic_reg_id = ofic_registral.ofic_reg_id  ");
			q.append("    AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID  ");
			q.append("    AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id  ");
			
			if (razonSocial != null && razonSocial.length()>0){
				q.append("    and PRTC_jur.Razon_Social = '"+razonSocial+"' ");
			}	
			q.append("    and partida.estado ='1' ");
			q.append("    AND partida.cod_libro = glad.cod_libro   ");
			q.append("    and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area  ");
			q.append("    and gla.cod_grupo_libro_area ='30' ");
			q.append("    AND IND_PRTC.ESTADO = '1' ");
			q.append("    AND IND_PRTC.TIPO_PERS='J' ");
			q.append("    AND a.cod_acto = ta.cod_acto ");
			q.append("    and pl.cod_partic = ind_prtc.cod_partic ");
			q.append("    and pl.cod_libro = partida.cod_libro ");
			q.append("    and ta.cod_acto = glac.cod_acto ");
			q.append("    and glac.TIP_LIBRO='OTR' ");
			q.append("    and glac.TIP_ACTO='DOM' ");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			q.append("    AND rpj.refnum_part = PARTIDA.REFNUM_PART ");
			q.append("ORDER BY estado desc, vigencia");

			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaJuridicaRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				PersonaJuridicaBean personaJuridicaBean = new PersonaJuridicaBean();
				
				//personaJuridicaBean.setDescripciónActo(rset.getString("descripcionActo"));
				personaJuridicaBean.setRazonSocial(rset.getString("razonSocial"));
				personaJuridicaBean.setNúmeroPartida(rset.getString("NUMEROPARTIDA"));
				personaJuridicaBean.setNombreOficinaRegistral(rset.getString("OFICINA"));
				personaJuridicaBean.setTipoParticipación(rset.getString("TIPOPARTICIPACION"));
				//personaJuridicaBean.setCodigoLibro(rset.getString("cod_libro"));
				personaJuridicaBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				personaJuridicaBean.setZonaRegistral(rset.getString("reg_pub_id"));
				personaJuridicaBean.setEstadoParticipacion(rset.getString("estado"));	
				personaJuridicaBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBPersonaJuridica.add(personaJuridicaBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBPersonaJuridica;
	}
	
	public ArrayList busquedaPersonaNaturalJuridicaRJB(ObjetoSolicitudBean objetoSolicitudBean)throws SQLException, CustomException, ValidacionException, DBException{
		
		String apellidoPaterno = objetoSolicitudBean.getApe_pat();
		String apellidoMaterno = objetoSolicitudBean.getApe_mat();
		String nombres         = objetoSolicitudBean.getNombres();
		String fechaInicio     = objetoSolicitudBean.getFechaInscripcionASientoDesde();
		String fechaFin        = objetoSolicitudBean.getFechaInscripcionASientoHasta();
		
		Statement stmt   = null;
		ResultSet rset   = null;
		ArrayList resultadoRJBPersonaJuridica= new ArrayList();
		
		try{
			
			StringBuffer q  = new StringBuffer();
			q.append(" SELECT distinct ");
			q.append("       rpj.razon_soc  as razonSocial,");
			q.append("       OFIC_REGISTRAL.NOMBRE as OFICINA,");
			q.append("       PARTIDA.NUM_PARTIDA   as NUMEROPARTIDA,");
			q.append("       PL.NOMBRE             AS TIPOPARTICIPACION,");
			q.append("       PARTIDA.REG_PUB_ID    as reg_pub_id,");
			q.append("       PARTIDA.OFIC_REG_ID   as ofic_reg_id,");
			q.append("       partida.area_reg_id   as area_id,");
			q.append("       AG.VIGENCIA           as vigencia,");
			q.append("       IND_PRTC.ESTADO       as estado");
			q.append("  FROM PRTC_NAT,");
			q.append("       IND_PRTC,");
			q.append("       PARTIDA,");
			q.append("       OFIC_REGISTRAL,");
			q.append("       REGIS_PUBLICO,");
			q.append("       grupo_libro_area gla,");
			q.append("       grupo_libro_area_det glad,");
			q.append("       asiento_garantia ag,");
			q.append("       partic_libro pl,");
			q.append("       asiento a,");
			q.append("       IND_PRTC_ASIENTO_GARANTIA IG,");
			q.append("       raz_soc_pj rpj");
			q.append(" WHERE ");
			q.append("   a.refnum_part = ag.refnum_part");
			q.append("   AND a.cod_acto = ag.cod_acto");
			q.append("   AND a.ns_asiento = ag.ns_asiento");
			q.append("   AND PRTC_NAT.CUR_PRTC = IND_PRTC.CUR_PRTC");
			q.append("   AND IND_PRTC.REFNUM_PART = PARTIDA.REFNUM_PART");
			q.append("   AND ag.refnum_part = partida.refnum_part");
			q.append("   and ag.refnum_part = ig.refnum_part");
			q.append("   and ag.ns_asiento = ig.ns_asiento");
			q.append("   and ag.cod_acto = ig.cod_acto");
			q.append("   and IND_PRTC.refnum_part = ig.refnum_part");
			q.append("   and IND_PRTC.cur_prtc = ig.cur_prtc");
			q.append("   AND partida.ofic_reg_id = prtc_NAT.ofic_reg_id");
			q.append("   AND PARTIDA.REG_PUB_ID = prtc_NAT.REG_PUB_ID");
			q.append("   AND PARTIDA.OFIC_REG_ID = OFIC_REGISTRAL.OFIC_REG_ID");
			q.append("   AND PARTIDA.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID");
			q.append("   AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
			
			q.append("   AND prtc_NAT.APE_PAT = '"+apellidoPaterno+"' ");
			if (apellidoMaterno.length()>0){
				q.append(" AND prtc_NAT.APE_MAT = '"+apellidoMaterno+"' ");
			} 
			if (nombres.length()>0){
				q.append("  AND prtc_NAT.NOMBRES = '"+nombres+"' ");
			}
			
			q.append("   AND partida.estado != '2'");
			q.append("   AND partida.cod_libro = glad.cod_libro");
			q.append("   AND gla.cod_grupo_libro_area = glad.cod_grupo_libro_area");
			q.append("   AND gla.cod_grupo_libro_area = '30'");
			q.append("   AND IND_PRTC.TIPO_PERS = 'N'");
			q.append("   AND pl.cod_libro = partida.cod_libro");
			q.append("   AND pl.cod_partic = ind_prtc.cod_partic");
			q.append("   AND rpj.refnum_part = PARTIDA.REFNUM_PART");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append(" union ");
			q.append(" SELECT distinct ");
			q.append("       rpj.razon_soc  as razonSocial,");
			q.append("       ofic_registral.nombre as OFICINA,");
			q.append("       partida.num_partida   as NUMEROPARTIDA,");
			q.append("       PL.NOMBRE             AS TIPOPARTICIPACION,");
			q.append("       PARTIDA.REG_PUB_ID    as reg_pub_id,");
			q.append("       PARTIDA.OFIC_REG_ID   as ofic_reg_id,");
			q.append("       partida.area_reg_id   as area_id,");
			q.append("       AG.VIGENCIA           as vigencia,");
			q.append("       IND_PRTC.ESTADO       as estado");
			q.append("  FROM PRTC_NAT,");
			q.append("       ind_prtc,");
			q.append("       partida,");
			q.append("       ofic_registral,");
			q.append("       regis_publico,");
			q.append("       grupo_libro_area gla,");
			q.append("       grupo_libro_area_det glad,");
			q.append("       asiento_garantia ag,");
			q.append("       asiento a,");
			q.append("       partic_libro pl,");
			q.append("       grupo_libro_acto glac,");
			q.append("       raz_soc_pj rpj");
			q.append(" WHERE ");
			q.append("   PRTC_NAT.CUR_PRTC = IND_PRTC.CUR_PRTC");
			q.append("   AND ind_prtc.refnum_part = partida.refnum_part");
			q.append("   and a.refnum_part = ag.refnum_part(+)");
			q.append("   and ag.refnum_part is null");
			q.append("   and a.refnum_part = partida.refnum_part");
			q.append("   AND IND_PRTC.REFNUM_PART = A.REFNUM_PART");
			q.append("   AND partida.ofic_reg_id = PRTC_NAT.ofic_reg_id");
			q.append("   AND PARTIDA.REG_PUB_ID = PRTC_NAT.REG_PUB_ID");
			q.append("   AND partida.ofic_reg_id = ofic_registral.ofic_reg_id");
			q.append("   AND PARTIDA.REG_PUB_ID = OFIC_REGISTRAL.REG_PUB_ID");
			q.append("   AND ofic_registral.reg_pub_id = regis_publico.reg_pub_id");
			
			q.append("   AND prtc_NAT.APE_PAT = '"+apellidoPaterno+"' ");
			if (apellidoMaterno.length()>0){
				q.append(" AND prtc_NAT.APE_MAT = '"+apellidoMaterno+"' ");
			} 
			if (nombres.length()>0){
				q.append("  AND prtc_NAT.NOMBRES = '"+nombres+"' ");
			}
			
			q.append("   and partida.estado != '2'");
			q.append("   AND partida.cod_libro = glad.cod_libro");
			q.append("   and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area");
			q.append("   and gla.cod_grupo_libro_area = '30'");
			q.append("   AND IND_PRTC.ESTADO = '1'");
			q.append("   AND IND_PRTC.TIPO_PERS = 'N'");
			q.append("   and pl.cod_partic = ind_prtc.cod_partic");
			q.append("   and pl.cod_libro = partida.cod_libro");
			q.append("   AND rpj.refnum_part = PARTIDA.REFNUM_PART");
			q.append("   and glac.TIP_LIBRO = 'OTR'");
			q.append("   and glac.TIP_ACTO = 'DOM'");
			
			if (fechaInicio!=null && fechaInicio.trim().length()>0){
				q.append("    and a.ts_inscrip >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') ");
			}
			if (fechaFin!=null && fechaFin.trim().length()>0){		 
				q.append("    and a.ts_inscrip <= to_date('"+fechaFin+"', 'yyyy-mm-dd') ");
			}
			
			q.append(" ORDER BY estado desc, vigencia");
			
			if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaPersonaNaturalJuridicaRJB__"+q.toString());
			
			stmt   = conn.createStatement();
			rset   = stmt.executeQuery(q.toString());
			
			while (rset.next())
			{
				PersonaJuridicaBean personaJuridicaBean = new PersonaJuridicaBean();
				
				personaJuridicaBean.setRazonSocial(rset.getString("razonSocial"));
				personaJuridicaBean.setNúmeroPartida(rset.getString("NUMEROPARTIDA"));
				personaJuridicaBean.setNombreOficinaRegistral(rset.getString("OFICINA"));
				personaJuridicaBean.setTipoParticipación(rset.getString("TIPOPARTICIPACION"));
				personaJuridicaBean.setOficinaRegistral(rset.getString("ofic_reg_id"));
				personaJuridicaBean.setZonaRegistral(rset.getString("reg_pub_id"));
				personaJuridicaBean.setEstadoParticipacion(rset.getString("estado"));	
				personaJuridicaBean.setArea_reg_id(rset.getString("area_id"));
				resultadoRJBPersonaJuridica.add(personaJuridicaBean);
			}
		
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(stmt);
		}
		
		return resultadoRJBPersonaJuridica;
	}
	
	public ArrayList busquedaTitulosPendientes(ArrayList listadoAsientos, String tipo)throws SQLException, CustomException, ValidacionException, DBException{
		
		ArrayList resultadoTitulosPendientes= new ArrayList();
		PartidaBean partidaBean = null;
		AeronaveBean aeronave = null;
		Vehiculo vehiculo = null;
		EmbarcacionPesqueraBean embarcacion= null;
		BuquesBean buques= null;
		PersonaJuridicaBean personaJuridica= null;
		String numeroPartida="";
		String area_reg_id="";
		String ofic_reg_id="";
		String reg_pub_id= "";	
		//StringBuffer q  = new StringBuffer();
		ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
		Statement stmt   = null;
		ResultSet rset   = null;
		Statement stmt2   = null;
		ResultSet rset2   = null;
		StringBuffer q  = new StringBuffer();
		StringBuffer q2  = new StringBuffer();
		
		for (int i=0 ; i<listadoAsientos.size(); i++){
	        q.delete(0, q.length());
	    	q2.delete(0, q2.length());
	    	
			boolean b = false;
			boolean flagRjb = false;
			boolean flagRmc = false;
			String refNumTitulo="";
			
			if (tipo.equals("R")){ // RMC
				partidaBean = (PartidaBean)listadoAsientos.get(i);
				numeroPartida= partidaBean.getNumPartida();
				ofic_reg_id = partidaBean.getOficRegId();
				reg_pub_id = partidaBean.getRegPubId();
				area_reg_id= partidaBean.getAreaRegistralId(); 
			}
			if (tipo.equals("V")){ // RJB Vehiculo
				vehiculo = (Vehiculo)listadoAsientos.get(i);
				numeroPartida= vehiculo.getNumeroPartida();
				ofic_reg_id = vehiculo.getCodigoOficinaRegistral();
				reg_pub_id = vehiculo.getCodigoZonaRegistral();
				area_reg_id= vehiculo.getArea_reg_id();
			}
			if (tipo.equals("E")){ // RJB Embarcacion Pesquera
				embarcacion = (EmbarcacionPesqueraBean)listadoAsientos.get(i);
				numeroPartida= embarcacion.getNúmeroPartida();
				ofic_reg_id = embarcacion.getOficinaRegistral();
				reg_pub_id = embarcacion.getZonaRegistral();
				area_reg_id= embarcacion.getArea_reg_id(); 
			}
			if (tipo.equals("B")){ // RJB Buques
				buques = (BuquesBean)listadoAsientos.get(i);
				numeroPartida= buques.getNúmeroPartida();
				ofic_reg_id = buques.getOficinaRegistral();
				reg_pub_id = buques.getZonaRegistral();
				area_reg_id= buques.getArea_reg_id(); 
			}
			if (tipo.equals("A")){ // RJB Aeronaves
				aeronave = (AeronaveBean)listadoAsientos.get(i);
				numeroPartida= aeronave.getNúmeroPartida();
				ofic_reg_id = aeronave.getOficinaRegistral();
				reg_pub_id = aeronave.getZonaRegistral();
				area_reg_id= aeronave.getArea_reg_id(); 
			}
			if (tipo.equals("J")){ // RJB Persona Juridica
				personaJuridica = (PersonaJuridicaBean)listadoAsientos.get(i);
				numeroPartida= personaJuridica.getNúmeroPartida();
				ofic_reg_id = personaJuridica.getOficinaRegistral();
				reg_pub_id = personaJuridica.getZonaRegistral();
				area_reg_id= personaJuridica.getArea_reg_id(); 
			}
				
			try{	
				if (!tipo.equals("R")){
					q.append(" SELECT TB.ANO_TITU as anoTitu, TB.NUM_TITU as numeroTitu, tit.REFNUM_TITU as refnumTit");
					q.append(" FROM ta_bloq_partida tb ,titulo tit");
					q.append(" WHERE tb.reg_pub_id = '"+reg_pub_id+"' and tb.ofic_reg_id='"+ofic_reg_id+"' and tb.AREA_REG_ID='"+area_reg_id+"'");
					q.append(" and tb.num_partida='"+numeroPartida+"' and tit.ano_titu = tb.ano_titu and tit.num_titu = tb.num_titu ");
					q.append(" and tit.area_reg_id = tb.area_reg_id and tit.reg_pub_id= tb.reg_pub_id ");
					q.append(" and tit.ofic_reg_id= tb.ofic_reg_id ");
					q.append(" and tb.estado='1' ");
					
					if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaTitulosPendientes RJB__"+q.toString());
					stmt   = conn.createStatement();
					rset   = stmt.executeQuery(q.toString());
					b = rset.next();
						
					while (b==true)
					 {  
						flagRjb = true;
						TituloPendienteBean titulopendiente= new TituloPendienteBean();
						refNumTitulo = rset.getString("refnumTit");
						titulopendiente.setNumRef(refNumTitulo);
						titulopendiente.setAaTitulo(rset.getString("anoTitu"));
						titulopendiente.setNroTitulo(rset.getString("numeroTitu"));
						titulopendiente.setActoDescripcion(consultarPartidaDirectaSQLImpl.buscarActosTitulo(refNumTitulo));
						resultadoTitulosPendientes.add(titulopendiente);
						b = rset.next();
						
					 }// while	
				}
				
				if (flagRjb == false || tipo.equals("R")){
					//q2.delete(0, q.length());
					 q2.append(" SELECT TB.ANO_TITU as anoTitu, tit.NUM_TITU as numeroTitu ,tit.REFNUM_TITU as refnumTit"+
							  " FROM  "+
							  "      ta_bloq_partida tb ,"+
							  " 	 titulo tit,TITULO_ORDEN TOR "+
							  " WHERE  "+
							  "  tb.reg_pub_id         = '"+reg_pub_id+"'"+
							  "	 and tb.ofic_reg_id    = '"+ofic_reg_id+"'"+
							  "	 and tb.AREA_REG_ID    = '"+area_reg_id+"'"+
							  "  and tb.num_partida    = '"+numeroPartida+"'"+
							  "	 AND tb.OFIC_REG_ID    = TOR.OFIC_REG_ID  "+
							  "  AND tb.REG_PUB_ID     = TOR.REG_PUB_ID  "+
							  "  AND tb.NUM_TITU       = TOR.NUM_TITU  "+
							  "  AND tb.ANO_TITU       = TOR.ANO_TITU  "+
							  "  AND tb.AREA_REG_ID    = TOR.AREA_REG_ID  "+
							  "  AND TOR.REFNUM_TITU   = tit.REFNUM_TITU  "+
							  "	 and tb.estado='1'                       ");
					 
					 if (isTrace(this)) System.out.println("___verquery_Verifica_busquedaTitulosPendientes RMC__"+q2.toString());
					 stmt2   = conn.createStatement();
					 rset2   = stmt2.executeQuery(q2.toString());
					 b = rset2.next();
					 
					 while (b==true)
					 {  
						flagRmc = true;
						TituloPendienteBean titulopendiente= new TituloPendienteBean();
						refNumTitulo = rset2.getString("refnumTit");
						titulopendiente.setNumRef(refNumTitulo);
						titulopendiente.setAaTitulo(rset2.getString("anoTitu"));
						titulopendiente.setNroTitulo(rset2.getString("numeroTitu"));
						titulopendiente.setActoDescripcion(consultarPartidaDirectaSQLImpl.buscarActosTitulo(refNumTitulo));
						resultadoTitulosPendientes.add(titulopendiente);
						b = rset2.next();
						
					 }// while	
						
				}	
				
			} finally {
				//if (flagRjb == true){
				JDBC.getInstance().closeResultSet(rset);
				JDBC.getInstance().closeStatement(stmt);
				//}
				//if (flagRmc == true){
				JDBC.getInstance().closeResultSet(rset2);
				JDBC.getInstance().closeStatement(stmt2);
				//}
			}
				
		}// for
		
		return resultadoTitulosPendientes;
	}
}

