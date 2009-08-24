package gob.pe.sunarp.extranet.publicidad.sql.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jcorporate.expresso.core.controller.Input;
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
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarNacionalIndicePartidasBienesSQL;
import gob.pe.sunarp.extranet.publicidad.sql.ConsultarPartidaDirectaSQL;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.Propiedades;
import gob.pe.sunarp.extranet.util.ValidacionException;

public class ConsultarNacionalIndicePartidasBienesSQLImpl extends SQLImpl implements ConsultarNacionalIndicePartidasBienesSQL,Constantes{

	private Connection conn;
	private DBConnection dbConn;
	
	
	public ConsultarNacionalIndicePartidasBienesSQLImpl(Connection conn, DBConnection dbConn) {
		this.conn = conn;
		this.dbConn = dbConn;
	}

	public FormOutputBuscarPartida busquedaIndiceBienesSIGC(int medioDeAcceso, InputBusqIndirectaBean inputBusqIndirectaBean,boolean flagUsuarioInterno, String idSession) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		FormOutputBuscarPartida output 	= new FormOutputBuscarPartida();
		Statement stmt					= null;
		ResultSet rset   				= null;
		String numeroPlaca				= null;
		String numeroMatricula			= null;
		String nombreBien 				= null;
		String numeroSerie				= null;
		boolean continua				= false;
		try {

			StringBuffer sql= new StringBuffer();
			StringBuffer descripcionParticipante=new StringBuffer();
			Propiedades propiedades = Propiedades.getInstance();
			int conteo=0;
			if (inputBusqIndirectaBean.getFlagPagineo()==false)
			{
				conteo=contarBusquedaIndiceBienesSIGC(inputBusqIndirectaBean);
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
			
				ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
				ArrayList resultado = new ArrayList();
				boolean haySiguiente = false;
				StringBuffer sb = new StringBuffer();
				StringBuffer q  = new StringBuffer();
				//query
				numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
				numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
				nombreBien=inputBusqIndirectaBean.getNombreBien();
				numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
				
				//busqueda por numero de placa
				if(numeroPlaca != null && !numeroPlaca.equals("") && (numeroMatricula ==null || numeroMatricula.equals(""))
					&& (nombreBien == null || nombreBien.equals("")) && (numeroSerie == null || numeroSerie.equals(""))){
					
					sql.append(sqlPersonaNaturalConAsientoVehiculo(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoVehiculo(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalConAsientoBien(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoBien(inputBusqIndirectaBean).toString());
					//sql.append(" ORDER BY SIGLAS, NOMBRE, NUM_PARTIDA");
				}
				//busqueda por numero de serie
				if(numeroSerie !=null && !numeroSerie.equals("") && (numeroMatricula == null || numeroMatricula.equals(""))
					&& (nombreBien == null || nombreBien.equals("")) && (numeroPlaca == null || numeroPlaca.equals(""))){
					
					sql.append(sqlPersonaNaturalConAsientoBien(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoBien(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalConAsientoVehiculo(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoVehiculo(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalConAsientoAeronave(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoAeronave(inputBusqIndirectaBean).toString());
					//sql.append(" ORDER BY SIGLAS,NOMBRE,NUM_PARTIDA");
				}
				//busqueda por nombre
				if( (numeroPlaca == null || numeroPlaca.equals("")) && (numeroSerie == null || numeroSerie.equals("")) ){
					
					sql.append(sqlPersonaNaturalConAsientoBien(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoBien(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalConAsientoAeronave(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoAeronave(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalConAsientoBuques(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoBuques(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalConAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaConAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
					//sql.append(" ORDER BY SIGLAS,NOMBRE, NUM_PARTIDA");
				}
				sql.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'");
				sql.append(" ORDER BY SIGLAS, NOMBRE, NUM_PARTIDA");
				
				if (isTrace(this)) System.out.println("___verquery_busquedaIndiceBienesSIGC__"+sql.toString());
	 			
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
					
					String nombrePart			= null;
					String apellidoPaternoPart	= null;
					String apellidoMaternoPart	= null;
					String razonSocialPart		= null;
					
					descripcionParticipante.delete(0, descripcionParticipante.length());
					
					nombrePart			= rset.getString("nombres");
					apellidoPaternoPart	= rset.getString("ape_pat");
					apellidoMaternoPart	= rset.getString("ape_mat");
					razonSocialPart		= rset.getString("razon_social");
					
					if(apellidoPaternoPart != null && !apellidoPaternoPart.equals("")){
						
						descripcionParticipante.append(apellidoPaternoPart+" ");
						if(apellidoMaternoPart != null && !apellidoPaternoPart.equals("")){
							descripcionParticipante.append(apellidoMaternoPart+" ");
						}
						if(nombrePart != null && !nombrePart.equals("")){
							descripcionParticipante.append(nombrePart);
						}
					}
					else{
						if(razonSocialPart != null && !razonSocialPart.equals("")){
							descripcionParticipante.append(razonSocialPart);
						}
						else{
							descripcionParticipante.append(" ");
						}
					}
					
					partidaBean.setParticipanteDescripcion(descripcionParticipante.toString());
	
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
		}//fin del if continua
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

	public int contarBusquedaIndiceBienesSIGC(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException {
		int contador=0;
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		StringBuffer sql=new StringBuffer();
		String numeroPlaca=null;
		String numeroMatricula=null;
		String nombreBien =null;
		String numeroSerie=null;
		try {
			Propiedades propiedades = Propiedades.getInstance();
			numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
			numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
			nombreBien=inputBusqIndirectaBean.getNombreBien();
			numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
			
			//busqueda por numero de placa
			if(numeroPlaca != null && !numeroPlaca.equals("") && (numeroMatricula ==null || numeroMatricula.equals(""))
				&& (nombreBien == null || nombreBien.equals("")) && (numeroSerie == null || numeroSerie.equals(""))){
				sql.append(" Select COUNT(*) FROM ( ");
				sql.append(sqlPersonaNaturalConAsientoVehiculo(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoVehiculo(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalConAsientoBien(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoBien(inputBusqIndirectaBean).toString());
				//sql.append(" )");
			}
			//busqueda por numero de serie
			if(numeroSerie !=null && !numeroSerie.equals("") && (numeroMatricula == null || numeroMatricula.equals(""))
				&& (nombreBien == null || nombreBien.equals("")) && (numeroPlaca == null || numeroPlaca.equals(""))){
				sql.append(" Select COUNT(*) FROM ( ");
				sql.append(sqlPersonaNaturalConAsientoBien(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoBien(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalConAsientoVehiculo(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoVehiculo(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalConAsientoAeronave(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoAeronave(inputBusqIndirectaBean).toString());
				//sql.append(" )");
			}
			//busqueda por nombre
			if( (numeroPlaca == null || numeroPlaca.equals("")) && (numeroSerie == null || numeroSerie.equals("")) ){
				sql.append(" Select COUNT(*) FROM ( ");
				sql.append(sqlPersonaNaturalConAsientoBien(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoBien(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalConAsientoAeronave(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoAeronave(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalConAsientoBuques(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoBuques(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalConAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaConAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
				//sql.append(" )");
				
			}
			sql.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')");
			
			if (isTrace(this)) System.out.println("___verquery_contarBusquedaIndiceBienesSIGC__"+sql.toString());
			
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			if(rset.next()){
				contador=rset.getInt(1);
			}
			
		} catch (Exception e) {
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(pstmt);
		}
		return contador;
	}


	public FormOutputBuscarPartida busquedaIndiceBienesSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean, String idSession) throws SQLException, CustomException, ValidacionException, DBException, Throwable {
		FormOutputBuscarPartida output 	= new FormOutputBuscarPartida();
		Statement stmt					= null;
		ResultSet rset   				= null;
		String numeroPlaca				= null;
		String numeroMatricula			= null;
		String nombreBien 				= null;
		String numeroSerie				= null;
		
		try {

			StringBuffer sql= new StringBuffer();
			StringBuffer descripcionParticipante=new StringBuffer();
			Propiedades propiedades = Propiedades.getInstance();
			int conteo=0;
			if (inputBusqIndirectaBean.isFlagPagineoInferior() == false)
			{
				conteo=contarBusquedaIndiceBienesSIGCInterno(inputBusqIndirectaBean);
				if (conteo > (propiedades.getMaxResultadosBusqueda()*2))
					throw new ValidacionException("Su búsqueda contiene demasiados resultados, por favor refine el criterio de búsqueda");
			}else{
				conteo = Integer.parseInt(inputBusqIndirectaBean.getCantidadInferior());
			}
			
			ConsultarPartidaDirectaSQL consultarPartidaDirectaSQLImpl = new ConsultarPartidaDirectaSQLImpl(this.conn, this.dbConn);
			ArrayList resultado = new ArrayList();
			boolean haySiguiente = false;
			StringBuffer sb = new StringBuffer();
			StringBuffer q  = new StringBuffer();
			//query
			
			if(conteo > 0){
				numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
				numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
				nombreBien=inputBusqIndirectaBean.getNombreBien();
				numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
				
				//busqueda por numero de placa
				if(numeroPlaca != null && !numeroPlaca.equals("") && (numeroMatricula ==null || numeroMatricula.equals(""))
					&& (nombreBien == null || nombreBien.equals("")) && (numeroSerie == null || numeroSerie.equals(""))){
					
					sql.append(sqlPersonaNaturalSinAsientoVehiculo(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaSinAsientoVehiculo(inputBusqIndirectaBean).toString());
					//sql.append(" ORDER BY SIGLAS,NOMBRE, NUM_PARTIDA");
					
				}
				//busqueda por numero de serie
				if(numeroSerie !=null && !numeroSerie.equals("") && (numeroMatricula == null || numeroMatricula.equals(""))
					&& (nombreBien == null || nombreBien.equals("")) && (numeroPlaca == null || numeroPlaca.equals(""))){
					
					sql.append(sqlPersonaNaturalSinAsientoVehiculo(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaSinAsientoVehiculo(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalSinAsientoAeronave(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaSinAsientoAeronave(inputBusqIndirectaBean).toString());
					//sql.append(" ORDER BY SIGLAS,NOMBRE, NUM_PARTIDA");
					
				}
				//busqueda por nombre
				if( (numeroPlaca == null || numeroPlaca.equals("")) && (numeroSerie == null || numeroSerie.equals("")) ){
					
					sql.append(sqlPersonaNaturalSinAsientoAeronave(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaSinAsientoAeronave(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalSinAsientoBuques(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaSinAsientoBuques(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaNaturalSinAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
					sql.append(" UNION ");
					sql.append(sqlPersonaJuridicaSinAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
					//sql.append(" ORDER BY SIGLAS,NOMBRE, NUM_PARTIDA");
					
				}
				sql.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"'");
				sql.append(" ORDER BY SIGLAS,NOMBRE, NUM_PARTIDA");
				
				if (isTrace(this)) System.out.println("___verquery_busquedaIndiceBienesSIGCInterno__"+sql.toString());
	 			
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
					
					String nombrePart			= null;
					String apellidoPaternoPart	= null;
					String apellidoMaternoPart	= null;
					String razonSocialPart		= null;
					
					descripcionParticipante.delete(0, descripcionParticipante.length());
					
					nombrePart			= rset.getString("nombres");
					apellidoPaternoPart	= rset.getString("ape_pat");
					apellidoMaternoPart	= rset.getString("ape_mat");
					razonSocialPart		= rset.getString("razon_social");
					
					if(apellidoPaternoPart != null && !apellidoPaternoPart.equals("")){
						
						descripcionParticipante.append(apellidoPaternoPart+" ");
						if(apellidoMaternoPart != null && !apellidoPaternoPart.equals("")){
							descripcionParticipante.append(apellidoMaternoPart+" ");
						}
						if(nombrePart != null && !nombrePart.equals("")){
							descripcionParticipante.append(nombrePart);
						}
					}
					else{
						if(razonSocialPart != null && !razonSocialPart.equals("")){
							descripcionParticipante.append(razonSocialPart);
						}
						else{
							descripcionParticipante.append(" ");
						}
					}
					
					partidaBean.setParticipanteDescripcion(descripcionParticipante.toString());

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
				
				if (inputBusqIndirectaBean.isFlagPagineoInferior() == false){
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

	public int contarBusquedaIndiceBienesSIGCInterno(InputBusqIndirectaBean inputBusqIndirectaBean) throws SQLException, CustomException, DBException {
		
		PreparedStatement pstmt	= null;
		ResultSet rset			= null;
		StringBuffer sql		= new StringBuffer();
		String numeroPlaca		= null;
		String numeroMatricula	= null;
		String nombreBien 		= null;
		String numeroSerie		= null;
		int contador = 0;
		try {
			Propiedades propiedades = Propiedades.getInstance();
			numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
			numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
			nombreBien=inputBusqIndirectaBean.getNombreBien();
			numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
			
			//busqueda por numero de placa
			if(numeroPlaca != null && !numeroPlaca.equals("") && (numeroMatricula ==null || numeroMatricula.equals(""))
				&& (nombreBien == null || nombreBien.equals("")) && (numeroSerie == null || numeroSerie.equals(""))){
				sql.append(" Select COUNT(*) FROM ( ");
				sql.append(sqlPersonaNaturalSinAsientoVehiculo(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaSinAsientoVehiculo(inputBusqIndirectaBean).toString());
				//sql.append(" )");
			}
			//busqueda por numero de serie
			if(numeroSerie !=null && !numeroSerie.equals("") && (numeroMatricula == null || numeroMatricula.equals(""))
				&& (nombreBien == null || nombreBien.equals("")) && (numeroPlaca == null || numeroPlaca.equals(""))){
				sql.append(" Select COUNT(*) FROM ( ");
				sql.append(sqlPersonaNaturalSinAsientoVehiculo(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaSinAsientoVehiculo(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalSinAsientoAeronave(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaSinAsientoAeronave(inputBusqIndirectaBean).toString());
				//sql.append(" )");
			}
			//busqueda por nombre
			if( (numeroPlaca == null || numeroPlaca.equals("")) && (numeroSerie == null || numeroSerie.equals("")) ){
				sql.append(" Select COUNT(*) FROM ( ");
				sql.append(sqlPersonaNaturalSinAsientoAeronave(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaSinAsientoAeronave(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalSinAsientoBuques(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaSinAsientoBuques(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaNaturalSinAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
				sql.append(" UNION ");
				sql.append(sqlPersonaJuridicaSinAsientoEmbarcaciones(inputBusqIndirectaBean).toString());
				//sql.append(" )");
				
			}
			sql.append(" AND ROWNUM <= '"+propiedades.getMaxResultadosBusqueda()*2+"')");
			
			if (isTrace(this)) System.out.println("___verquery_contarBusquedaIndiceBienesSIGCInterno__"+sql.toString());
		
			pstmt=conn.prepareStatement(sql.toString());
			rset=pstmt.executeQuery();
			if(rset.next()){
				contador=rset.getInt(1);
			}
			
		} catch (Exception e) {
			
		} finally {
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(pstmt);
		}
		return contador;
	}
	
	
	
	private StringBuffer sqlPersonaNaturalSinAsientoVehiculo(InputBusqIndirectaBean inputBusqIndirectaBean){
		String numeroPlaca = null;
		String numeroSerie = null;
		
		numeroPlaca = inputBusqIndirectaBean.getNumeroPlaca();
		numeroSerie = inputBusqIndirectaBean.getNumeroSerie();
		
		StringBuffer sql=new StringBuffer();
		sql.append(" select distinct " +
				//" p.refnum_part, " +
				" p.estado, ");
		/*if(numeroPlaca!=null && !numeroPlaca.equals("")){
			sql.append(" v.num_placa,");
		}
		if(numeroSerie  != null && !numeroSerie.equals("")){
			sql.append(" v.num_serie,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" rp.siglas," +
				" o.nombre," +
				" pn.ape_pat," +
				" pn.ape_mat," +
				" pn.nombres," +
				" '' as RAZON_SOCIAL," +
				" pn.ti_doc_iden as TI_DOC," +
				" pn.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from " +
				" vehiculo v, partida p, asiento a, asiento_garantia ag," +
				" OFIC_REGISTRAL o, REGIS_PUBLICO rp, ind_prtc ip,prtc_nat pn," +
				" grupo_libro_area gla, grupo_libro_area_det glad" +
				" where" +
				" v.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pn.cur_prtc" +
				" and p.reg_pub_id = pn.reg_pub_id" +
				" and p.ofic_reg_id = pn.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'");
		if(numeroPlaca !=null && !numeroPlaca.equals("")){
			sql.append(" and v.num_placa = '").append(numeroPlaca).append("'");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" and v.NUM_SERIE = '").append(numeroSerie).append("'");
		}
		sql.append(" and p.ESTADO != '2'" +
				" and ip.tipo_pers = 'N'");
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
			sql.append(" and ip.estado = '1'");
		}
		
				
		return sql;
	}
	
	private StringBuffer sqlPersonaJuridicaSinAsientoVehiculo(InputBusqIndirectaBean inputBusqIndirectaBean){
		
		StringBuffer sql=new StringBuffer();
		String numeroPlaca=null;
		String numeroSerie=null;
		numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		
		sql.append(" select distinct" +
				//" p.refnum_part," +
				" p.estado,");
		/*if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" v.num_placa,");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" v.num_serie,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" rp.siglas, " +
				" o.nombre," +
				" '' AS ape_pat," +
				" '' AS ape_mat," +
				" '' AS nombres," +
				" pj.RAZON_SOCIAL," +
				" pj.TI_DOC," +
				" pj.NU_DOC," +
				" ip.COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from" +
				" vehiculo v," +
				" partida p," +
				" asiento a," +
				" asiento_garantia ag," +
				" OFIC_REGISTRAL o," +
				" REGIS_PUBLICO rp," +
				" ind_prtc ip," +
				" prtc_jur pj," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad" +
				" where" +
				" v.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pj.cur_prtc" +
				" and p.reg_pub_id = pj.reg_pub_id" +
				" and p.ofic_reg_id = pj.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'");
		if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append("and v.num_placa = '").append(numeroPlaca).append("'");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" and v.NUM_SERIE = '").append(numeroSerie).append("'");
		}
		sql.append(" and p.ESTADO != '2'" +
				" and ip.tipo_pers = 'J'");
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
			sql.append(" and ip.estado = '1'");
		}
				
		return sql;
		
	}
	
	private StringBuffer sqlPersonaNaturalConAsientoVehiculo(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroPlaca=null;
		String numeroSerie=null;
		numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		
		sql.append(" select DISTINCT " +
				//" PARTIDA.REFNUM_PART," +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" vehiculo.num_placa,");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" vehiculo.num_serie,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" prtc_nat.ape_pat," +
				" prtc_nat.ape_mat," +
				" prtc_nat.nombres," +
				" '' as RAZON_SOCIAL," +
				" prtc_nat.ti_doc_iden as TI_DOC," +
				" prtc_nat.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				" FROM" +
				" vehiculo," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_nat" +
				" WHERE" +
				" vehiculo.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_nat.cur_prtc=ip.cur_prtc" +
				" and partida.refnum_part= ip.refnum_part" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part=ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID" +
				" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
		if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" AND VEHICULO.num_placa = '").append(numeroPlaca).append("'");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" AND VEHICULO.Num_Serie = '").append(numeroSerie).append("'");
		}
		sql.append(" and PARTIDA.ESTADO != '2'" +
				" AND IP.ESTADO='1'" +
				" AND IP.TIPO_PERS='N'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'");
				//" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");
		return sql;
	}
	
	private StringBuffer sqlPersonaJuridicaConAsientoVehiculo(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroPlaca=null;
		String numeroSerie=null;
		numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		sql.append(" select DISTINCT " +
				//" PARTIDA.REFNUM_PART," +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" vehiculo.num_placa,");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" vehiculo.num_serie,");
		}
		
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" '' AS ape_pat," +
				" '' AS ape_mat," +
				" '' AS nombres," +
				" PRTC_JUR.RAZON_SOCIAL," +
				" PRTC_JUR.TI_DOC," +
				" PRTC_JUR.NU_DOC," +
				" ip.COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				//" prtc_jur.reg_pub_id," +
				//" prtc_jur.ofic_reg_id" +
				" FROM" +
				" vehiculo," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_jur" +
				" WHERE" +
				" vehiculo.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_jur.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part=ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_jur.reg_pub_id" +
				" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" AND VEHICULO.NUM_SERIE = '").append(numeroSerie).append("'");
		}
		if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" AND VEHICULO.num_placa = '").append(numeroPlaca).append("'");
		}
		sql.append(" and PARTIDA.ESTADO != '2' " +
				"AND IP.ESTADO='1'" +
				" AND IP.TIPO_PERS='J'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'");
				//" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");
		
		return sql;
	}
	private StringBuffer sqlPersonaNaturalConAsientoBien(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroPlaca=null;
		String numeroMatricula=null;
		String numeroSerie=null;
		String nombreBien=null;
		
		numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		
		sql.append(" select DISTINCT " +
				"PARTIDA.ESTADO as estado,");
		/*if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" '' as num_placa,");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" '' as num_serie,");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" '' as num_matricula,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" '' as nombre_bien,");
		}
		sql.append(" bien.desc_bien," +
				" bien.num_placa," +
				" bien.num_serie," +
				" bien.num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" prtc_nat.ape_pat," +
				" prtc_nat.ape_mat," +
				" prtc_nat.nombres," +
				" '' as RAZON_SOCIAL," +
				" prtc_nat.ti_doc_iden as TI_DOC," +
				" prtc_nat.nu_doc_iden AS NU_DOC," +
				" '' AS COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				" FROM" +
				" bien," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" prtc_nat" +
				" WHERE" +
				" bien.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_nat.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part= ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc= ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID" +
				" AND PARTIDA.OFIC_REG_ID= OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
		if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" AND bien.num_placa = '").append(numeroPlaca).append("'");
		}
		if (numeroMatricula != null && !numeroMatricula.equals("")){
		    sql.append(" AND (bien.num_serie= '").append(numeroMatricula).append("'" +
		    		" or bien.num_motor= '").append(numeroMatricula).append("'" +
		    		" or bien.desc_bien like '").append(numeroMatricula).append("%')");
		}
		if (nombreBien !=null && !nombreBien.equals("")){
			sql.append(" AND (bien.num_serie= '").append(nombreBien).append("'" +
    		" or bien.num_motor= '").append(nombreBien).append("'" +
    		" or bien.desc_bien like '").append(nombreBien).append("%')");
		}
		if (numeroSerie!=null && !numeroSerie.equals("")){
			sql.append(" AND (bien.num_serie= '").append(numeroSerie).append("'" +
    		" or bien.num_motor= '").append(numeroSerie).append("'" +
    		" or bien.desc_bien like '").append(numeroSerie).append("%')");
		}
		sql.append(" and bien.tipo='E' " +
						" and PARTIDA.ESTADO != '2'" +
						" AND IP.ESTADO='1'" +
						" AND IP.TIPO_PERS='N'" +
						" AND partida.cod_libro = glad.cod_libro" +
						" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
						" and gla.cod_grupo_libro_area ='28'");
						//" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");
		return sql;
	}
	
	private StringBuffer sqlPersonaJuridicaConAsientoBien(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroPlaca=null;
		String numeroMatricula=null;
		String numeroSerie=null;
		String nombreBien=null;
		
		numeroPlaca=inputBusqIndirectaBean.getNumeroPlaca();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		
		sql.append("select DISTINCT " +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" '' as num_placa,");
		}
		if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" '' as num_serie,");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" '' as num_matricula,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" '' as nombre_bien,");
		}
		sql.append(" bien.desc_bien," +
				" bien.num_placa," +
				" bien.num_serie," +
				" bien.num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" '' AS ape_pat," +
				" '' AS ape_mat," +
				" '' AS nombres," +
				" PRTC_JUR.RAZON_SOCIAL," +
				" PRTC_JUR.NU_DOC," +
				" PRTC_JUR.TI_DOC," +
				" ip.COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				" FROM" +
				" bien," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" PRTC_JUR" +
				" WHERE" +
				" bien.refnum_part=PARTIDA.REFNUM_PART" +
				" and PRTC_JUR.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part= ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc= ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_jur.reg_pub_id" +
				" AND PARTIDA.OFIC_REG_ID= OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID" +
				" and bien.tipo='E'" +
				" and PARTIDA.ESTADO != '2'" +
				" AND IP.TIPO_PERS='J'" +
				" AND IP.ESTADO='1'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'");
		if(numeroPlaca != null && !numeroPlaca.equals("")){
			sql.append(" AND bien.num_placa = '").append(numeroPlaca).append("'");
		}
		if (numeroMatricula != null && !numeroMatricula.equals("")){
		    sql.append(" AND (bien.num_serie= '").append(numeroMatricula).append("'" +
		    		" or bien.num_motor= '").append(numeroMatricula).append("'" +
		    		" or bien.desc_bien like '").append(numeroMatricula).append("%')");
		}
		if (nombreBien !=null && !nombreBien.equals("")){
			sql.append(" AND (bien.num_serie= '").append(nombreBien).append("'" +
    		" or bien.num_motor= '").append(nombreBien).append("'" +
    		" or bien.desc_bien like '").append(nombreBien).append("%')");
		}
		if (numeroSerie!=null && !numeroSerie.equals("")){
			sql.append(" AND (bien.num_serie= '").append(numeroSerie).append("'" +
    		" or bien.num_motor= '").append(numeroSerie).append("'" +
    		" or bien.desc_bien like '").append(numeroSerie).append("%')");
		}
		
		//		" ORDER BY REGIS_PUBLICO.SIGLAS, OFIC_REGISTRAL.NOMBRE, PARTIDA.NUM_PARTIDA");
		
		return sql;
		
	}
	
	private StringBuffer sqlPersonaNaturalConAsientoAeronave(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroSerie=null;
		String numeroMatricula=null;
		
		
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		String nombreBien=null;//usado solo para pintar
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		
		sql.append(" select DISTINCT " +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" reg_aeronaves.serie as num_serie,");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" reg_aeronaves.num_matricula,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" '' as nombre_bien,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" prtc_nat.ape_pat," +
				" prtc_nat.ape_mat," +
				" prtc_nat.nombres," +
				" '' AS RAZON_SOCIAL," +
				" prtc_nat.ti_doc_iden as TI_DOC," +
				" prtc_nat.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				" FROM" +
				" reg_aeronaves," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_nat" +
				" WHERE" +
				" reg_aeronaves.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_nat.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part=ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID" +
				" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" AND reg_aeronaves.num_matricula='").append(numeroMatricula).append("'");
		}
		if(numeroSerie!=null && !numeroSerie.equals("")){
			sql.append(" AND reg_aeronaves.serie='").append(numeroSerie).append("'");
		}
		sql.append(" and reg_aeronaves.estado='1'" +
				" and PARTIDA.ESTADO != '2'" +
				" AND IP.TIPO_PERS='N'" +
				" AND IP.ESTADO='1'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'");
		
		return sql;
	}
	private StringBuffer sqlPersonaJuridicaConAsientoAeronave(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroSerie=null;
		String numeroMatricula=null;
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		
		String nombreBien=null;//usado solo para pintar
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		
		sql.append("select DISTINCT " +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" reg_aeronaves.serie as num_serie,");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" reg_aeronaves.num_matricula,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" '' as nombre_bien,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" '' AS ape_pat," +
				" '' AS ape_mat," +
				" '' AS nombres," +
				" PRTC_JUR.RAZON_SOCIAL," +
				" PRTC_JUR.TI_DOC," +
				" PRTC_JUR.NU_DOC," +
				" ip.COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				//" prtc_jur.reg_pub_id," +
				//" prtc_jur.ofic_reg_id" +
				" FROM" +
				" reg_aeronaves," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_jur" +
				" WHERE" +
				" reg_aeronaves.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_jur.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part=ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_jur.reg_pub_id" +
				" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" AND reg_aeronaves.num_matricula='").append(numeroMatricula).append("'");
		}
		if(numeroSerie!=null && !numeroSerie.equals("")){
			sql.append(" AND reg_aeronaves.serie='").append(numeroSerie).append("'");
		}
		sql.append(" and reg_aeronaves.estado='1'" +
				" and PARTIDA.ESTADO != '2'" +
				" AND IP.TIPO_PERS='J'" +
				" AND IP.ESTADO='1'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'");
		
		return sql;
	}
	private StringBuffer sqlPersonaNaturalSinAsientoAeronave(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroSerie=null;
		String numeroMatricula=null;
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		String nombreBien=null;//usado solo para pintar
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		
		sql.append(" select distinct" +
				" p.estado,");
				//" p.refnum_part," +
		/*if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" ra.serie as num_serie,");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" ra.num_matricula,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" '' as nombre_bien,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" rp.siglas," +
				" o.nombre," +
				" pn.ape_pat," +
				" pn.ape_mat," +
				" pn.nombres," +
				" '' as RAZON_SOCIAL," +
				" pn.ti_doc_iden as TI_DOC," +
				" pn.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from" +
				" reg_aeronaves ra," +
				" partida p," +
				" asiento a," +
				" asiento_garantia ag," +
				" OFIC_REGISTRAL o," +
				" REGIS_PUBLICO rp," +
				" ind_prtc ip," +
				" prtc_nat pn," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad" +
				" where" +
				" ra.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pn.cur_prtc" +
				" and p.reg_pub_id = pn.reg_pub_id" +
				" and p.ofic_reg_id = pn.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'");
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" AND ra.num_matricula='").append(numeroMatricula).append("'");
		}
		if(numeroSerie!=null && !numeroSerie.equals("")){
			sql.append(" AND ra.serie='").append(numeroSerie).append("'");
		}
		sql.append(" and p.ESTADO != '2'" +
				" and ra.estado = '1'" +
				" and ip.tipo_pers = 'N'");
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
			sql.append(" and ip.estado = '1'");
		}
		
		return sql;
	}
	private StringBuffer sqlPersonaJuridicaSinAsientoAeronave(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroSerie=null;
		String numeroMatricula=null;
		numeroSerie=inputBusqIndirectaBean.getNumeroSerie();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		String nombreBien=null;//usado solo para pintar
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		
		sql.append(" select distinct" +
				" p.estado,");
		/*if(numeroSerie != null && !numeroSerie.equals("")){
			sql.append(" ra.serie as num_serie,");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" ra.num_matricula,");
		}	
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" '' as nombre_bien,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" rp.siglas," +
				" o.nombre," +
				" '' AS ape_pat," +
				" '' AS ape_mat," +
				" '' AS nombres," +
				" pj.RAZON_SOCIAL," +
				" pj.TI_DOC," +
				" pj.NU_DOC," +
				" ip.COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from" +
				" reg_aeronaves ra," +
				" partida p," +
				" asiento a," +
				" asiento_garantia ag," +
				" OFIC_REGISTRAL o," +
				" REGIS_PUBLICO rp," +
				" ind_prtc ip," +
				" prtc_jur pj," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad" +
				" where" +
				" ra.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pj.cur_prtc" +
				" and p.reg_pub_id = pj.reg_pub_id" +
				" and p.ofic_reg_id = pj.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'");
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" AND ra.num_matricula='").append(numeroMatricula).append("'");
		}
		if(numeroSerie!=null && !numeroSerie.equals("")){
			sql.append(" AND ra.serie='").append(numeroSerie).append("'");
		}
		sql.append(" and p.ESTADO != '2'" +
				" and ra.estado = '1'" +
				" and ip.tipo_pers = 'J'");
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
			sql.append(" and ip.estado = '1'");
		}
		
		return sql;
	}
	private StringBuffer sqlPersonaNaturalConAsientoBuques(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select DISTINCT " +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" REG_BUQUES.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" REG_BUQUES.NOMBRE as nombre_bien,");
		}
		
		sql.append(" '' as desc_bien," +
				" '' as num_placa," +
				" '' as num_serie," +
				" '' as num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" prtc_nat.ape_pat," +
				" prtc_nat.ape_mat," +
				" prtc_nat.nombres," +
				" '' as RAZON_SOCIAL," +
				" prtc_nat.ti_doc_iden as TI_DOC," +
				" prtc_nat.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				" FROM" +
				" REG_BUQUES," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_nat" +
				" WHERE" +
				" REG_BUQUES.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_nat.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part= ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id " +
				" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID" +
				" AND PARTIDA.OFIC_REG_ID= OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND REG_BUQUES.NOMBRE LIKE '").append(nombreBien).append("%'");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" and REG_BUQUES.NUM_MATRICULA='").append(numeroMatricula).append("'");
		}
		sql.append(" and reg_buques.estado='1'" +
				" and PARTIDA.ESTADO != '2'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'" +
				" AND IP.TIPO_PERS='N'" +
				" AND IP.ESTADO='1'");
				
		return sql;
	}
	private StringBuffer sqlPersonaJuridicaConAsientoBuques(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select DISTINCT " +
				" PARTIDA.ESTADO as estado,");
				
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" REG_BUQUES.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" REG_BUQUES.NOMBRE as nombre_bien,");
		}
		
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" '' as ape_pat," +
				" '' as ape_mat," +
				" '' as nombres," +
				" PRTC_JUR.RAZON_SOCIAL," +
				" PRTC_JUR.TI_DOC," +
				" PRTC_JUR.NU_DOC," +
				" ip.COD_PARTIC," +
				//" REG_BUQUES.NOMBRE," +
				//" REG_BUQUES.NUM_MATRICULA," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
			//	" prtc_jur.reg_pub_id," +
			//	" prtc_jur.ofic_reg_id" +
				" FROM" +
				" REG_BUQUES," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_jur" +
				" WHERE" +
				" REG_BUQUES.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_jur.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part=ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_jur.reg_pub_id" +
				" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID");
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND REG_BUQUES.NOMBRE LIKE '").append(nombreBien).append("%'");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" and REG_BUQUES.NUM_MATRICULA='").append(numeroMatricula).append("'");
		}
		 sql.append(" and reg_buques.estado='1'" +
		 		" and PARTIDA.ESTADO != '2'" +
		 		" AND IP.TIPO_PERS='J'" +
		 		" AND IP.ESTADO='1'" +
		 		" AND partida.cod_libro = glad.cod_libro" +
		 		" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
		 		" and gla.cod_grupo_libro_area ='28'");
		 		
		
		return sql;
	}
	private StringBuffer sqlPersonaNaturalSinAsientoBuques(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select distinct" +
				//" p.refnum_part," +
				" p.estado,");
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" rb.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" rb.NOMBRE as nombre_bien,");
		}
		
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +
				//" rb.NOMBRE," +
				//" rb.NUM_MATRICULA," +*/
		sql.append(" rp.siglas," +
				" o.nombre," +
				" pn.ape_pat," +
				" pn.ape_mat," +
				" pn.nombres," +
				" '' as RAZON_SOCIAL," +
				" pn.ti_doc_iden as TI_DOC," +
				" pn.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from" +
				" REG_BUQUES rb," +
				" partida p," +
				" asiento a," +
				" asiento_garantia ag," +
				" OFIC_REGISTRAL o, " +
				" REGIS_PUBLICO rp," +
				" ind_prtc ip," +
				" prtc_nat pn," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad" +
				" where" +
				" rb.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pn.cur_prtc" +
				" and p.reg_pub_id = pn.reg_pub_id" +
				" and p.ofic_reg_id = pn.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'" +
				" and p.ESTADO != '2'" +
				" and rb.estado = '1'" +
				" and ip.tipo_pers = 'N'");

		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND rb.nombre LIKE '").append(nombreBien).append("%'");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" and rb.NUM_MATRICULA='").append(numeroMatricula).append("'");
		}
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == true){
			sql.append(" and ip.estado = '1'");
		}
		
		return sql;
	}
	private StringBuffer sqlPersonaJuridicaSinAsientoBuques(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select distinct" +
				//" p.refnum_part," +
				" p.estado,");
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" rb.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" rb.NOMBRE as nombre_bien,");
		}
		
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +*/
				//" rb.NOMBRE," +
				//" rb.NUM_MATRICULA," +
		sql.append(" rp.siglas," +
				" o.nombre," +
				" '' as ape_pat," +
				" '' as ape_mat," +
				" '' as nombres," +
				" pj.RAZON_SOCIAL," +
				" pj.TI_DOC," +
				" pj.NU_DOC," +
				" ip.COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from" +
				" REG_BUQUES rb," +
				" partida p," +
				" asiento a," +
				" asiento_garantia ag," +
				" OFIC_REGISTRAL o," +
				" REGIS_PUBLICO rp," +
				" ind_prtc ip," +
				" prtc_jur pj," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad" +
				" where" +
				" rb.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pj.cur_prtc" +
				" and p.reg_pub_id = pj.reg_pub_id" +
				" and p.ofic_reg_id = pj.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'" +
				" and p.ESTADO != '2'" +
				" and rb.estado = '1'" +
				" and ip.tipo_pers = 'J'");
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND rb.nombre LIKE '").append(nombreBien).append("%'");
		}
		if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" and rb.NUM_MATRICULA='").append(numeroMatricula).append("'");
		}
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
			sql.append(" and ip.estado = '1'");
		}

		
		return sql;
	}
	private StringBuffer sqlPersonaNaturalConAsientoEmbarcaciones(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select DISTINCT " +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" reg_emb_pesq.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" reg_emb_pesq.nombre_emb as nombre_bien,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +
				//" reg_emb_pesq.nombre_emb," +
				//" reg_emb_pesq.num_matricula," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" prtc_nat.ape_pat," +
				" prtc_nat.ape_mat," +
				" prtc_nat.nombres," +
				" '' as RAZON_SOCIAL," +
				" prtc_nat.ti_doc_iden as TI_DOC," +
				" prtc_nat.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				" FROM" +
				" reg_emb_pesq," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_nat" +
				" WHERE" +
				" reg_emb_pesq.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_nat.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part=ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_nat.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_nat.REG_PUB_ID" +
				" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID" +
				" and reg_emb_pesq.estado='1'" +
				" and PARTIDA.ESTADO != '2'" +
				" AND IP.ESTADO='1'" +
				" AND IP.TIPO_PERS='N'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'");
		
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND reg_emb_pesq.nombre_emb LIKE '").append(nombreBien).append("%' ");
		}
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" and reg_emb_pesq.num_matricula='").append(numeroMatricula).append("'");
		}
		return sql;
	}
	private StringBuffer sqlPersonaJuridicaConAsientoEmbarcaciones(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select DISTINCT " +
				" PARTIDA.ESTADO as estado,");
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" reg_emb_pesq.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" reg_emb_pesq.nombre_emb as nombre_bien,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +
				//" reg_emb_pesq.nombre_emb," +
				//" reg_emb_pesq.num_matricula," +*/
		sql.append(" REGIS_PUBLICO.SIGLAS," +
				" OFIC_REGISTRAL.NOMBRE," +
				" '' as ape_pat," +
				" '' as ape_mat," +
				" '' as nombres," +
				" PRTC_JUR.RAZON_SOCIAL," +
				" PRTC_JUR.TI_DOC," +
				" PRTC_JUR.NU_DOC," +
				" ip.COD_PARTIC," +
				" PARTIDA.REFNUM_PART," +
				" PARTIDA.NUM_PARTIDA," +
				" PARTIDA.COD_LIBRO," +
				" partida.reg_pub_id," +
				" partida.ofic_reg_id," +
				" partida.area_reg_id" +
				//" prtc_jur.reg_pub_id," +
				//" prtc_jur.ofic_reg_id" +
				" FROM" +
				" reg_emb_pesq," +
				" PARTIDA," +
				" OFIC_REGISTRAL," +
				" REGIS_PUBLICO," +
				" ind_prtc ip," +
				" ind_prtc_asiento_garantia ig," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad," +
				" asiento_garantia ag," +
				" prtc_jur" +
				" WHERE" +
				" reg_emb_pesq.refnum_part=PARTIDA.REFNUM_PART" +
				" and prtc_jur.cur_prtc=ip.cur_prtc" +
				" and ag.refnum_part=partida.refnum_part" +
				" and ag.refnum_part=ig.refnum_part" +
				" and ag.ns_asiento=ig.ns_asiento" +
				" and ag.cod_acto=ig.cod_acto" +
				" and ip.refnum_part=ig.refnum_part" +
				" and ip.cur_prtc=ig.cur_prtc" +
				" AND partida.ofic_reg_id = prtc_jur.ofic_reg_id" +
				" AND PARTIDA.REG_PUB_ID  = prtc_jur.reg_pub_id " +
				" AND PARTIDA.OFIC_REG_ID=OFIC_REGISTRAL.OFIC_REG_ID" +
				" AND PARTIDA.REG_PUB_ID=OFIC_REGISTRAL.REG_PUB_ID" +
				" AND OFIC_REGISTRAL.REG_PUB_ID = REGIS_PUBLICO.REG_PUB_ID" +
				" and reg_emb_pesq.estado='1'" +
				" and PARTIDA.ESTADO != '2'" +
				" AND IP.TIPO_PERS='J'" +
				" AND IP.ESTADO='1'" +
				" AND partida.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and gla.cod_grupo_libro_area ='28'");
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND reg_emb_pesq.nombre_emb LIKE '").append(nombreBien).append("%' ");
		}
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" and reg_emb_pesq.num_matricula='").append(numeroMatricula).append("'");
		}
		
		
		return sql;
	}
	private StringBuffer sqlPersonaNaturalSinAsientoEmbarcaciones(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select distinct " +
				//" p.refnum_part," +
				" p.estado,");
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" ep.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" ep.nombre_emb as nombre_bien,");
		}		
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +
				//" ep.nombre_emb," +
				//" ep.num_matricula," +*/
		sql.append(" rp.siglas," +
				" o.nombre," +
				" pn.ape_pat," +
				" pn.ape_mat," +
				" pn.nombres," +
				" '' as RAZON_SOCIAL," +
				" pn.ti_doc_iden as TI_DOC," +
				" pn.nu_doc_iden as NU_DOC," +
				" '' AS COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from" +
				" reg_emb_pesq ep," +
				" partida p," +
				" asiento a," +
				" asiento_garantia ag," +
				" OFIC_REGISTRAL o," +
				" REGIS_PUBLICO rp," +
				" ind_prtc ip," +
				" prtc_nat pn," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad" +
				" where" +
				" ep.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pn.cur_prtc" +
				" and p.reg_pub_id = pn.reg_pub_id" +
				" and p.ofic_reg_id = pn.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'" +
				" and p.ESTADO != '2'" +
				" and ep.estado = '1'" +
				" and ip.tipo_pers = 'N'");				
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND ep.nombre_emb LIKE '").append(nombreBien).append("%' ");
		}
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" and ep.num_matricula='").append(numeroMatricula).append("'");
		}
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
			sql.append(" and ip.estado = '1'");
		}
		
		
		return sql;
	}
	private StringBuffer sqlPersonaJuridicaSinAsientoEmbarcaciones(InputBusqIndirectaBean inputBusqIndirectaBean){
		StringBuffer sql=new StringBuffer();
		String numeroMatricula=null;
		String nombreBien=null;
		nombreBien=inputBusqIndirectaBean.getNombreBien();
		numeroMatricula=inputBusqIndirectaBean.getNumeroMatricula();
		sql.append("select distinct" +
				//" p.refnum_part," +
				" p.estado,");
		/*if(numeroMatricula != null && !numeroMatricula.equals("")){
			sql.append(" ep.NUM_MATRICULA,");
		}
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" ep.nombre_emb as nombre_bien,");
		}
		sql.append(" '' AS desc_bien," +
				" '' AS num_placa," +
				" '' AS num_serie," +
				" '' AS num_motor," +
				//" ep.nombre_emb," +
				//" ep.num_matricula," +*/
		sql.append(" rp.siglas," +
				" o.nombre," +
				" '' as ape_pat," +
				" '' as ape_mat," +
				" '' as nombres," +
				" pj.RAZON_SOCIAL," +
				" pj.TI_DOC," +
				" pj.NU_DOC," +
				" ip.COD_PARTIC," +
				" p.REFNUM_PART," +
				" P.NUM_PARTIDA," +
				" P.COD_LIBRO," +
				" p.reg_pub_id," +
				" p.ofic_reg_id," +
				" p.area_reg_id" +
				" from" +
				" reg_emb_pesq ep," +
				" partida p," +
				" asiento a," +
				" asiento_garantia ag," +
				" OFIC_REGISTRAL o," +
				" REGIS_PUBLICO rp," +
				" ind_prtc ip," +
				" prtc_jur pj," +
				" grupo_libro_area gla," +
				" grupo_libro_area_det glad" +
				" where" +
				" ep.refnum_part = p.refnum_part" +
				" and a.refnum_part = p.refnum_part" +
				" and p.reg_pub_id = rp.reg_pub_id" +
				" and p.ofic_reg_id = o.ofic_reg_id" +
				" and o.reg_pub_id  = rp.reg_pub_id" +
				" and p.refnum_part = ip.refnum_part" +
				" and ag.refnum_part(+) = a.refnum_part" +
				" and ip.cur_prtc  = pj.cur_prtc" +
				" and p.reg_pub_id = pj.reg_pub_id" +
				" and p.ofic_reg_id = pj.ofic_reg_id" +
				" AND p.cod_libro = glad.cod_libro" +
				" and gla.cod_grupo_libro_area = glad.cod_grupo_libro_area" +
				" and ag.refnum_part is null" +
				" and gla.cod_grupo_libro_area = '28'" +
				" and p.ESTADO != '2'" +
				" and ep.estado = '1'" +
				" and ip.tipo_pers = 'J'");
		if(nombreBien != null && !nombreBien.equals("")){
			sql.append(" AND ep.nombre_emb LIKE '").append(nombreBien).append("%' ");
		}
		if(numeroMatricula !=null && !numeroMatricula.equals("")){
			sql.append(" and ep.num_matricula='").append(numeroMatricula).append("'");
		}
		if(inputBusqIndirectaBean.getFlagIncluirInactivos() == false){
			sql.append(" and ip.estado = '1'");
		}
		
		
		return sql;
	}
	
}
