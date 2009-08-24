package gob.pe.sunarp.extranet.common.cm;

//jacaceres - CM8
//import com.ibm.mm.sdk.client.*;
import com.ibm.mm.sdk.server.*;
import com.ibm.mm.sdk.common.*;

import gob.pe.sunarp.extranet.common.LoggeableException;
import gob.pe.sunarp.extranet.common.logica.Constantes;
import gob.pe.sunarp.extranet.common.logica.Errors;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.sql.Connection;
import gob.pe.sunarp.extranet.common.*;

/** 
 * @author jacaceres - CM8 - 22/02/07
 * Cambios en la clase CMProcessor para utilizar las funcionalidades de CM8
 */
public class CMProcessor {

	private static CMProcessor processor;

	public static CMProcessor getInstance() {
		if (processor == null) {
			processor = new CMProcessor();
		}
		return processor;
	}

	private CMProcessor() {
		super();
	}

	public static final Short TIPO_STRING = new Short(DKConstantDL.DK_CM_DATAITEM_TYPE_STRING);
	public static final Short TIPO_LONG = new Short(DKConstantDL.DK_CM_DATAITEM_TYPE_LONG);
	public static final Short TIPO_TMSTMP = new Short(DKConstantDL.DK_CM_DATAITEM_TYPE_TIMESTAMP);
	public static final Boolean BOOLEAN_SI = new Boolean(true);
	public static final Boolean BOOLEAN_NO = new Boolean(false);

	/**
	 * @author jacaceres - CM8
	 * Cambios en el metodo main
	 */	
	public static void main(String[] args) {
		/*
		try {
			CMConnectionFactory.getInstance("PRUEBA").init("LIBSRVRX", "CLIWEBD", "CW2U", 1, 10*60*1000, 5*60*1000);
			DKDatastoreDL cmConn = CMConnectionFactory.getInstance("PRUEBA").get();
			cmConn.disconnect();
			System.out.println(cmConn.isConnected());
			CMConnectionFactory.getInstance("PRUEBA").release(cmConn);

			byte[] contents = CMProcessor.getInstance().retrieveImage("PRUEBA", "ASIENTO", 25697, 2);
			CMConnectionFactory.getInstance("PRUEBA").close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		try {
			CMConnectionFactory.getInstance().init("icmnlsdb", "icmadmin", "icmadmin", 1, 10*60*1000, 5*60*1000);
			DKDatastoreICM cmConn = CMConnectionFactory.getInstance().get();
			cmConn.disconnect();
			System.out.println(cmConn.isConnected());
			CMConnectionFactory.getInstance().release(cmConn);

			byte[] contents = CMProcessor.getInstance().retrieveImage("ASIENTO", 25697, 2);
			CMConnectionFactory.getInstance().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	/***** jacaceres - CM8 *****/
	
/*
	public AsientoCM findAsiento(long objetoID) throws CreateCMDatastoreException, LoggeableException {
		AsientoCM resultado = new AsientoCM();
		resultado.setObjetoID(objetoID);

		DKDatastoreDL dsConn = CMConnectionFactory.getInstance().get();

		try {

			DKDDO ddoRecuperado = find(resultado, dsConn);

			return fillWithDataAsiento(resultado, ddoRecuperado);

		} catch (Exception e) {
			try {
				dsConn.rollback();
			} catch (Exception e2) {
			}
			throw new LoggeableException(Errors.EC_FALLO_RECUPERAR_IMAGEN, e.toString());
		} finally {
			CMConnectionFactory.getInstance().release(dsConn);
		}
	}
*/	

	private String updateAsiento = "update ASIENTO set ID_IMG_ASIENTO = ?, NUMPAG = ? where REFNUM_PART = ? and NS_ASIENTO = ? and COD_ACTO = ?";
	private String updateOtro = "update OTRO_IMAGE set ID_IMG_OTRO = ?, NUMPAG = ? where REFNUM_PART = ?";
	private String updateFicha = "update FICHA set ID_IMG_FICHA = ?, NUMPAG = ? where REFNUM_PART = ?";
	private String updateFolio = "update TOMO_FOLIO set ID_IMG_FOLIO = ? where REFNUM_PART = ? and NS_CADE = ?";

	/**
	 * @author jacaceres - CM8
	 * Cambio del metodo borrarObjeto
	 */
	/*
	protected boolean borrarObjeto(ObjetoCM objeto, DKDatastoreDL dsConn) throws Exception {
		if (objeto.getObjetoID() != Constantes.ID_IMAGEN_NO_IMAGEN) {
			DKDDO antiguoDDO = find(objeto, dsConn);
			if (antiguoDDO != null) {
				antiguoDDO.del();
				return true;
			}
		}
		return false;
	}*/
	protected boolean borrarObjeto(ObjetoCM objeto, DKDatastoreICM dsConn) throws Exception {
		if (objeto.getObjetoID() != Constantes.ID_IMAGEN_NO_IMAGEN) {
			DKDDO antiguoDDO = find(objeto, dsConn);
				if (antiguoDDO != null) {
					antiguoDDO.del();
					return true;
				}
		}
		return false;
	}
/***** jacaceres - CM8 *****/

	/** 
	 * @author jacaceres - CM8
	 * Cambios en la recepcion de parametros  
	 */
	//protected void insertarObjeto(ObjetoCM objeto, DKDatastoreDL dsConn, Connection conn) throws Exception {
	protected void insertarObjeto(ObjetoCM objeto, DKDatastoreICM dsConn, Connection conn) throws Exception {
		DKDDO nuevoDDO;
		PreparedStatement pstmt = null;

		try {
			
			int numAct = 0;

			// agregamos el resto de atributos particulares
			if (objeto instanceof AsientoCM) {

				nuevoDDO = crearDocumento(Constantes.INDEX_SUBCLASS_ASIENTO, objeto, dsConn);

				AsientoCM asiento = (AsientoCM) objeto;
				agregarDatosAsiento(asiento, nuevoDDO, conn);

				pstmt = conn.prepareStatement(updateAsiento);
				pstmt.setLong(1, asiento.getObjetoID());
				pstmt.setInt(2, asiento.getImagenes().length);
				pstmt.setLong(3, asiento.getPartidaRN());
				pstmt.setInt(4, asiento.getNumAsiento());
				pstmt.setString(5, asiento.getCodActoGeneral());
				numAct = pstmt.executeUpdate();

				/*			} else if (objeto instanceof OtroCM$FUE) {
				
								nuevoDDO = crearDocumento(Constantes.INDEX_SUBCLASS_OTRO, objeto, dsConn);
				
								OtroCM$FUE otro = (OtroCM$FUE) objeto;
								agregarDatosOtro(otro, nuevoDDO, conn);
				
								pstmt = conn.prepareStatement(updateOtro);
								pstmt.setLong(1, otro.getObjetoID());
								pstmt.setInt(2, otro.getImagenes().length);
								pstmt.setLong(3, otro.getPartidaRN());
								pstmt.executeUpdate();
				*/
			} else if (objeto instanceof FichaCM) {

				nuevoDDO = crearDocumento(Constantes.INDEX_SUBCLASS_FICHA, objeto, dsConn);

				FichaCM ficha = (FichaCM) objeto;
				agregarDatosFicha(ficha, nuevoDDO, conn);

				pstmt = conn.prepareStatement(updateFicha);
				pstmt.setLong(1, ficha.getObjetoID());
				pstmt.setInt(2, ficha.getImagenes().length);
				pstmt.setLong(3, ficha.getPartidaRN());
				numAct = pstmt.executeUpdate();

			} else if (objeto instanceof FolioCM) {

				nuevoDDO = crearDocumento(Constantes.INDEX_SUBCLASS_FOLIO, objeto, dsConn);

				FolioCM folio = (FolioCM) objeto;
				agregarDatosFolio(folio, nuevoDDO, conn);

				pstmt = conn.prepareStatement(updateFolio);
				pstmt.setLong(1, folio.getObjetoID());
				pstmt.setLong(2, folio.getPartidaRN());
				pstmt.setInt(3, folio.getSecuencial());
				numAct = pstmt.executeUpdate();

			} else {
				throw new IllegalArgumentException("El ObjetoCM pasado es de un tipo no soportado");
			}
			
			if (numAct != 1) {
				throw new IllegalArgumentException("Se debio actualizar solo 1 registro y se actualizaron " + numAct);
			}

			insertar(objeto, nuevoDDO, dsConn);
		} finally {
			try {
				if (pstmt != null)
						pstmt.close();
			} catch (SQLException e) {
			}
		}
	}

	/** inserta la imagen y genera su propio ID */
	/**
	 * @author jacaceres - CM8
	 * Cambio del metodo insertarObjeto
	 */
	public ObjetoCM[] insertarObjeto(String poolCMID, ObjetoCM[] objetos, Connection conn)
		throws CreateCMDatastoreException, LoggeableException {
		//jacaceres - CM8
		//DKDatastoreDL dsConn = CMConnectionFactory.getInstance(poolCMID).get();
		DKDatastoreICM dsConn = CMConnectionFactory.getInstance().get();

		boolean transaccionActiva = false;
		ObjetoCM obj = null;

		try {

			try {
				transaccionActiva = true;
				// lo ponemos antes para que, si sucede error, aparezca como transaccion activa y haga un disconnect
				dsConn.startTransaction();
			} catch (Exception e) {
				throw new LoggeableException(Errors.EC_FALLO_COMENZAR_TRANSACCION_CM, e.toString());
			}

			for (int i = 0; i < objetos.length; i++) {
				
				obj = objetos[i];
				
				if (obj == null) continue;

				borrarObjeto(obj, dsConn);
				insertarObjeto(obj, dsConn, conn);

			}

			dsConn.commit();
			transaccionActiva = false;
		} catch (SQLException e) {
			try {
				dsConn.rollback();
				transaccionActiva = false;
			} catch (Exception e2) {
				StringBuffer buffer = new StringBuffer().append("No se pudo obtener imageID del seq de asientos. ").append(e.toString());
				buffer.append("\r\nNo se pudo realizar rollback a la conexion al CM. ").append(e2.toString());
				throw new LoggeableException(Errors.EC_GENERIC_DB_ERROR, buffer.toString());
			}
			throw new LoggeableException(Errors.EC_GENERIC_DB_ERROR, "No se pudo obtener imageID del seq de asientos. " + e.toString());
		} catch (DKUsageError e) {
			try {
				dsConn.rollback();
				transaccionActiva = false;
			} catch (Exception e2) {
				StringBuffer buffer = new StringBuffer().append(e.toString());
				buffer.append("\r\nNo se pudo realizar rollback a la conexion al CM. ").append(e2.toString());
				throw new LoggeableException(Errors.EC_FALLO_ARMAR_OBJETO_CM, buffer.toString());
			}
			throw new LoggeableException(Errors.EC_FALLO_ARMAR_OBJETO_CM, e.toString());
		} catch (Exception e) {
			System.err.println(obj);
			e.printStackTrace();
			try {
				dsConn.rollback();
				transaccionActiva = false;
			} catch (Exception e2) {
				StringBuffer buffer = new StringBuffer().append(e.toString());
				buffer.append("\r\nNo se pudo realizar rollback a la conexion al CM. ").append(e2.toString());
				throw new LoggeableException(Errors.EC_FALLO_INSERTAR_IMAGEN, buffer.toString());
			}
			throw new LoggeableException(Errors.EC_FALLO_INSERTAR_IMAGEN, e.toString());
		} finally {
			if (transaccionActiva) {
				try {
					dsConn.disconnect();
				} catch (Exception e) {
					System.err.println("NO SE PUDO DESCONECTARSE DE LA CONEXION AL CM, Y LA TRANSACCION NO FUE CERRADA");
					e.printStackTrace();
				}
			}
			//jacaceres - comentario
			//CMConnectionFactory.getInstance(poolCMID).release(dsConn);
			CMConnectionFactory.getInstance().release(dsConn);			
		}

		return objetos;
	}
/*
	private void agregarDatosOtro(OtroCM$FUE otro, DKDDO nuevoDDO, Connection conn) throws SQLException, DKUsageError {
		if (otro.getObjetoID() == Constantes.ID_IMAGEN_NO_IMAGEN)
			otro.setObjetoID(Secuenciales.getInstance().getIDOtro(conn));

		short data_id;

		data_id = nuevoDDO.addData("objetoID");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(otro.getObjetoID()));
	}

	private OtroCM$FUE fillWithDataOtro(OtroCM$FUE objeto, DKDDO ddoRecuperado) throws Exception {
		return (OtroCM$FUE) fillWithData(objeto, ddoRecuperado);
	}
*/
	
	/**
	 * @author jacaceres - CM8
	 * Modificacion del metodo agregarDatosFicha
	 */
	/*
	private void agregarDatosFicha(FichaCM ficha, DKDDO nuevoDDO, Connection conn) throws SQLException, DKUsageError {
		if (ficha.getObjetoID() == Constantes.ID_IMAGEN_NO_IMAGEN)
			ficha.setObjetoID(Secuenciales.getInstance().getIDFicha(conn));

		short data_id;

		data_id = nuevoDDO.addData("objetoID");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(ficha.getObjetoID()));

		data_id = nuevoDDO.addData("numFicha");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, ficha.getNumFicha());

		data_id = nuevoDDO.addData("numFichaBis");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, (ficha.getNumFichaBis() == null) ? "0" : ficha.getNumFichaBis());
	}
	*/
	private void agregarDatosFicha(FichaCM ficha, DKDDO nuevoDDO, Connection conn) throws SQLException, DKUsageError {
		if (ficha.getObjetoID() == Constantes.ID_IMAGEN_NO_IMAGEN)
			ficha.setObjetoID(Secuenciales.getInstance().getIDFicha(conn));
			
		short data_id;
		
		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"objetoID");
		nuevoDDO.setData(data_id,  new Integer((int)ficha.getObjetoID()) );

		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numFicha");		
		nuevoDDO.setData(data_id, ficha.getNumFicha());
		
		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numFichaBis");		
		nuevoDDO.setData(data_id, ficha.getNumFichaBis());
	}
	/***** fin *****/

	private FichaCM fillWithDataFicha(FichaCM objeto, DKDDO ddoRecuperado) throws Exception {
		objeto.setNumFicha((String) ddoRecuperado.getDataByName("numFicha"));
		objeto.setNumFichaBis((String) ddoRecuperado.getDataByName("numFichaBis"));

		return (FichaCM) fillWithData(objeto, ddoRecuperado);
	}

	/**
	 * @author jacaceres - CM8
	 * Modificacion del metodo agregarDatosFolio
	 */
	/*
	private void agregarDatosFolio(FolioCM folio, DKDDO nuevoDDO, Connection conn) throws SQLException, DKUsageError {
		if (folio.getObjetoID() == Constantes.ID_IMAGEN_NO_IMAGEN)
			folio.setObjetoID(Secuenciales.getInstance().getIDFolio(conn));

		short data_id;

		data_id = nuevoDDO.addData("objetoID");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(folio.getObjetoID()));

		data_id = nuevoDDO.addData("numTomo");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, folio.getNumTomo());

		data_id = nuevoDDO.addData("numFolio");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, folio.getNumFolio());

	}
	*/
	private void agregarDatosFolio(FolioCM folio, DKDDO nuevoDDO, Connection conn) throws SQLException, DKUsageError {
		if (folio.getObjetoID() == Constantes.ID_IMAGEN_NO_IMAGEN)
			folio.setObjetoID(Secuenciales.getInstance().getIDFolio(conn));

		short data_id;
		
		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"objetoID");
		nuevoDDO.setData(data_id,  new Integer((int)folio.getObjetoID()) );

		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numFolio");		
		nuevoDDO.setData(data_id, folio.getNumFolio());
		
		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numTomo");
		nuevoDDO.setData(data_id, folio.getNumTomo());
		
	}
	/***** fin ******/

	private FolioCM fillWithDataFolio(FolioCM objeto, DKDDO ddoRecuperado) throws Exception {
		objeto.setNumTomo((String) ddoRecuperado.getDataByName("numTomo"));
		objeto.setNumFolio((String) ddoRecuperado.getDataByName("numFolio"));

		return (FolioCM) fillWithData(objeto, ddoRecuperado);
	}

	/**
	 * @author jacaceres - CM8
	 * Modificacion del metodo agregarDatosAsiento
	 */
	/*
	private void agregarDatosAsiento(AsientoCM asiento, DKDDO nuevoDDO, Connection conn) throws SQLException, DKUsageError {
		if (asiento.getObjetoID() == Constantes.ID_IMAGEN_NO_IMAGEN)
			asiento.setObjetoID(Secuenciales.getInstance().getIDAsiento(conn));

		short data_id;

		data_id = nuevoDDO.addData("objetoID");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(asiento.getObjetoID()));

		data_id = nuevoDDO.addData("numAsiento");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(asiento.getNumAsiento()));

		data_id = nuevoDDO.addData("anoTitulo");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, asiento.getAnoTitulo() == null ? Constantes.KEYFILE_ANO_NULO : asiento.getAnoTitulo());

		data_id = nuevoDDO.addData("numTitulo");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, asiento.getNumTitulo() == null ? Constantes.KEYFILE_TITULO_NULO : asiento.getNumTitulo());

	}
	 */
	private void agregarDatosAsiento(AsientoCM asiento, DKDDO nuevoDDO, Connection conn) throws SQLException, DKUsageError {
		if (asiento.getObjetoID() == Constantes.ID_IMAGEN_NO_IMAGEN)
			asiento.setObjetoID(Secuenciales.getInstance().getIDAsiento(conn));
		
		short data_id;
		
		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"objetoID");
		nuevoDDO.setData(data_id,  new Integer((int)asiento.getObjetoID()) );
		
		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numAsiento");		
		nuevoDDO.setData(data_id, new Integer(asiento.getNumAsiento()) );
		
		//data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numOrdenAsiento");		
		//nuevoDDO.setData(data_id, new Integer(asiento.getNumOrdenAsiento()) );
		
		//data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"fechaCreacion");		
		//nuevoDDO.setData(data_id, new Timestamp(asiento.getFecha().getTime()) );

		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"anoTitulo");		
		nuevoDDO.setData(data_id, asiento.getAnoTitulo() );

		data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numTitulo");		
		nuevoDDO.setData(data_id, asiento.getNumTitulo() );

	}
	/****** fin ******/

	private AsientoCM fillWithDataAsiento(AsientoCM objeto, DKDDO ddoRecuperado) throws Exception {
		objeto.setNumAsiento(((Number) ddoRecuperado.getDataByName("numAsiento")).intValue());
		objeto.setAnoTitulo((String) ddoRecuperado.getDataByName("anoTitulo"));
		objeto.setNumTitulo((String) ddoRecuperado.getDataByName("numTitulo"));

		return (AsientoCM) fillWithData(objeto, ddoRecuperado);
	}

	private ObjetoCM fillWithData(ObjetoCM objeto, DKDDO ddoRecuperado) throws Exception {
		objeto.setPartidaRN(((Number) ddoRecuperado.getDataByName("partidaRN")).longValue());
		objeto.setNumPartida((String) ddoRecuperado.getDataByName("numPartida"));
		objeto.setKeyfileOID((String) ddoRecuperado.getDataByName("keyfileOID"));
		objeto.setKeyfilePagina(((Number) ddoRecuperado.getDataByName("keyfilePagina")).intValue());

		int numPaginas = ((Number) ddoRecuperado.getDataByName("numPaginas")).intValue();

		DataImagen[] imagenes = new DataImagen[numPaginas];

		DKParts parts = (DKParts) ddoRecuperado.getDataByName(DKConstantDL.DKPARTS);

		if (parts != null) {
			dkIterator iter = parts.createIterator();
			for (int i = 0; i < numPaginas; i++) {
				imagenes[i] = new DataImagen();
				DKBlobDL blob = (DKBlobDL) iter.next();
				if (blob != null) {
					blob.retrieve();
					byte[] content = blob.getContent();
					imagenes[i].setContenido(content);
				}
			}
		}

		objeto.setImagenes(imagenes);
		return objeto;
	}

	/**
	 * @author jacaceres - CM8
	 *  Cambio en los parametros en el metodo crearDocumento
	 */
	/*
	private DKDDO crearDocumento(String indexSubclass, ObjetoCM objeto, DKDatastoreDL dsConn) throws DKUsageError {
		// creamos el PID		
		DKPid pid = new DKPid();
		pid.setObjectType(indexSubclass);

		// creamos el Documento en si
		DKDDO nuevoDDO = new DKDDO(dsConn, pid);
		nuevoDDO.addProperty(DKConstantDL.DK_CM_PROPERTY_ITEM_TYPE, new Short(DKConstantDL.DK_CM_DOCUMENT));

		short data_id;

		// seteamos los atributos
		data_id = nuevoDDO.addData("numPaginas");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(objeto.getImagenes().length));

		data_id = nuevoDDO.addData("partidaRN");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(objeto.getPartidaRN()));

		data_id = nuevoDDO.addData("numPartida");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, objeto.getNumPartida());

		data_id = nuevoDDO.addData("keyfileOID");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_STRING);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, objeto.getKeyfileOID());

		data_id = nuevoDDO.addData("keyfilePagina");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_LONG);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new Long(objeto.getKeyfilePagina()));

		data_id = nuevoDDO.addData("fechaCreacion");
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, TIPO_TMSTMP);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, new DKTimestamp(new java.util.Date().getTime()));

		return nuevoDDO;
	}
	*/
	
	private DKDDO crearDocumento(String indexSubclass, ObjetoCM objeto, DKDatastoreICM dsConn) throws DKUsageError {		
		// creamos el PID		
		DKPidICM pid = new DKPidICM();
		pid.setObjectType(indexSubclass);
		DKDDO nuevoDDO = new DKDDO(dsConn, pid);
		short data_id;
		
		try{

			// Para insertar los atributos
			nuevoDDO = dsConn.createDDO(indexSubclass,DKConstant.DK_CM_DOCUMENT);

			data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numPartida");
			nuevoDDO.setData(data_id,objeto.getNumPartida());
			data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"keyfileOID");
			nuevoDDO.setData(data_id,objeto.getKeyfileOID());
			data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"partidaRN");
			nuevoDDO.setData(data_id, new Integer( (int) objeto.getPartidaRN()) );
			
			data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"keyfilePagina");
			nuevoDDO.setData(data_id, new Integer( objeto.getKeyfilePagina()) );
			data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"fechaCreacion");
			nuevoDDO.setData(data_id, new Timestamp( objeto.getFechaCreacion().getTime() ) );
			data_id = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,"numPaginas");
			nuevoDDO.setData(data_id, new Integer( objeto.getNumImagenes() ) );
			
		} catch (Exception e) {
				e.printStackTrace();			
		}
		
		return nuevoDDO;
	}
	/***** fin ******/
	
	/**
	 * @author jacaceres - CM8 
	 * Cambio en los parametros del metodo insertar()
	 */
	/*
	private void insertar(ObjetoCM objeto, DKDDO nuevoDDO, DKDatastoreDL dsConn) throws Exception {

		DataImagen[] imagenes = objeto.getImagenes();

		DKParts parts = new DKParts();

		for (int i = 0; i < imagenes.length; i++) {

			DKBlobDL blob = new DKBlobDL(dsConn);

			DKPidXDODL pidXDO = new DKPidXDODL();
			blob.setPidObject(pidXDO);
			blob.setContentClass(DKConstantDL.DK_DL_CC_TIFF6);
			blob.setRepType(DKConstantDL.DK_DL_REP_NULL);
			blob.setContent(imagenes[i].getContenido());

			parts.addElement(blob);
		}

		short data_id = nuevoDDO.addData(DKConstantDL.DKPARTS);
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_TYPE, new Short(DKConstantDL.DK_CM_COLLECTION_XDO));
		nuevoDDO.addDataProperty(data_id, DKConstantDL.DK_CM_PROPERTY_NULLABLE, BOOLEAN_NO);
		nuevoDDO.setData(data_id, parts);

		nuevoDDO.add();
	}
	*/
	private void insertar(ObjetoCM objeto, DKDDO nuevoDDO, com.ibm.mm.sdk.server.DKDatastoreICM dsConn) throws Exception {
		
		// Para crear el DKParts
		short dataId;
		DKParts parts =new DKParts();
		DataImagen[] imagenes = objeto.getImagenes();
		dataId = nuevoDDO.dataId(DKConstant.DK_CM_NAMESPACE_ATTR,DKConstantICM.DK_CM_DKPARTS);
		if (dataId == 0) {
			dataId = nuevoDDO.addData(DKConstant.DK_CM_NAMESPACE_ATTR,DKConstantICM.DK_CM_DKPARTS);
				parts = new DKParts();
				nuevoDDO.setData(dataId, parts);
		} else {
			parts = (DKParts)nuevoDDO.getData(dataId);
			if (parts == null){
				parts = new DKParts();
				nuevoDDO.setData(dataId, parts);
			}
		}
		
		// Para insertar la imagen
		for (int i = 0; i < imagenes.length; i++) {
			DKLobICM pLobPart = (DKLobICM) dsConn.createDDO("ICMBASE",DKConstantICM.DK_ICM_SEMANTIC_TYPE_BASE);
			//pLobPart.setPartNumber(1);
			//Defina el tipo de MIME para la parte añadida
			pLobPart.setMimeType("image/tiff");
			pLobPart.setContent(imagenes[i].getContenido());
			parts.addElement((dkDataObjectBase)((DKDDO) pLobPart));
		}
		
		nuevoDDO.add();
		
	}
	/***** fin ******/

	/**
	 * @author jacaceres - CM8
	 *  Cambios en los parametros del metodo find y busquedas
	 */
	/*
	private String cmd1 = "SEARCH=(INDEX_CLASS=";
	private String cmd3 = ",MAX_RESULTS=1,COND=(objetoID==";
	private String cmd5 = "));" + "OPTION=(CONTENT=YES;" + "TYPE_QUERY=DYNAMIC;" + "TYPE_FILTER=FOLDERDOC)";

	protected DKDDO find(ObjetoCM objeto, DKDatastoreDL dsConn) throws Exception {
		String cmd2;
		if (objeto instanceof AsientoCM) {
			cmd2 = Constantes.INDEX_SUBCLASS_ASIENTO;
		//} else if (objeto instanceof OtroCM$FUE) {
		//	cmd2 = Constantes.INDEX_SUBCLASS_OTRO;
		} else if (objeto instanceof FichaCM) {
			cmd2 = Constantes.INDEX_SUBCLASS_FICHA;
		} else if (objeto instanceof FolioCM) {
			cmd2 = Constantes.INDEX_SUBCLASS_FOLIO;
		} else {
			throw new IllegalArgumentException("El ObjetoCM pasado es de un tipo no soportado");
		}
		long cmd4 = objeto.getObjetoID();

		StringBuffer buffer = new StringBuffer();
		buffer.append(cmd1);
		buffer.append(cmd2);
		buffer.append(cmd3);
		buffer.append(cmd4);
		buffer.append(cmd5);

		dkQuery query = dsConn.createQuery(buffer.toString(), DKConstantDL.DK_CM_PARAMETRIC_QL_TYPE, null);
		query.execute(null);

		dkResultSetCursor rs = null;
		
		try {
			rs = query.resultSetCursor();

			if (rs.isValid()) {
				return rs.fetchNext();
			} else {
				return null;
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}
	*/
	private String cmd1 = "/";
	private String cmd3 = "[@objetoID = ";	
	private String cmd5 = "]";

	protected DKDDO find(ObjetoCM objeto, com.ibm.mm.sdk.server.DKDatastoreICM dsConn) throws Exception {
				
				String cmd2;
				if (objeto instanceof AsientoCM) {
					cmd2 = Constantes.INDEX_SUBCLASS_ASIENTO;
				} else if (objeto instanceof FichaCM) {
					cmd2 = Constantes.INDEX_SUBCLASS_FICHA;
				} else if (objeto instanceof FolioCM) {
					cmd2 = Constantes.INDEX_SUBCLASS_FOLIO;
				} else {
					throw new IllegalArgumentException("El ObjetoCM pasado es de un tipo no soportado");
				}

				//verificar el cambio para getObjetoID
				long cmd4 = objeto.getObjetoID();								
				
				StringBuffer buffer = new StringBuffer();
				buffer.append(cmd1);
				buffer.append(cmd2);
				buffer.append(cmd3);
				buffer.append(cmd4);
				buffer.append(cmd5);				
				System.out.println("buffer --> " + buffer);
				// Para armar el query					
				DKNVPair options[] = new DKNVPair[3];				
				options[0]= new DKNVPair(DKConstant.DK_CM_PARM_MAX_RESULTS, "1"); // No Max (Default)
				options[1] = new DKNVPair(DKConstant.DK_CM_PARM_RETRIEVE,new Integer(DKConstant.DK_CM_CONTENT_ATTRONLY));
				options[2] = new DKNVPair(DKConstant.DK_CM_PARM_END, null);
				dkResultSetCursor rs = null;				
				try {
					
					//rs = query.resultSetCursor();					
					rs = dsConn.execute(buffer.toString(), DKConstantICM.DK_CM_XQPE_QL_TYPE,options);
							
					if (rs.isValid()) {
						DKDDO rpta = rs.fetchNext();						
						return rpta;
					} else {
						System.out.println("Dentro no valido , devuelve null");
						return null;
					}
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				} finally {
						if (rs != null) {
						rs.close();
				}
			}
			
	}
			
	/***** fin *****/

	/**
	 * @author jacaceres - CM8 
	 * Modificacion del metodo retrieveImage()
	 */
	/*	
	public byte[] retrieveImage(String poolCMID, String indexSubclass, long objetoID, int numPagina) throws LoggeableException {
		DKDatastoreDL dsConn = null;

		try {
			dsConn = CMConnectionFactory.getInstance(poolCMID).get();

			ObjetoCM objetoCM;

			if (Constantes.INDEX_SUBCLASS_ASIENTO.equals(indexSubclass)) {
				objetoCM = new AsientoCM();
			//} else if (Constantes.INDEX_SUBCLASS_OTRO.equals(indexSubclass)) {
			//	objetoCM = new OtroCM$FUE();
			} else if (Constantes.INDEX_SUBCLASS_FICHA.equals(indexSubclass)) {
				objetoCM = new FichaCM();
			} else if (Constantes.INDEX_SUBCLASS_FOLIO.equals(indexSubclass)) {
				objetoCM = new FolioCM();
			} else {
				throw new LoggeableException(Errors.EC_INDEXSUBCLASS_NO_ENCONTRADO, "indexSubclassReferido=" + indexSubclass);
			}

			objetoCM.setObjetoID(objetoID);

			DKDDO ddoRecuperado = find(objetoCM, dsConn);

			DKParts parts = (DKParts) ddoRecuperado.getDataByName(DKConstantDL.DKPARTS);

			if (parts == null) {
				throw new LoggeableException(Errors.EC_FALLO_RECUPERAR_IMAGEN, "No se recuperaron las páginas del objeto.");
			}

			dkIterator iter = parts.createIterator();

			int contador = 0;
			byte[] content = null;
			for (int i = 0; iter.more(); i++) {
				DKBlobDL blob = (DKBlobDL) iter.next();
				if (++contador == numPagina) {
					if (blob != null) {
						blob.retrieve();
						content = blob.getContent();
					} else {
						throw new LoggeableException(Errors.EC_FALLO_RECUPERAR_IMAGEN, "La imagen pedida no tiene data");
					}
				}
			}

			if (content == null) {
				throw new LoggeableException(Errors.EC_FALLO_RECUPERAR_IMAGEN, "La página pedida no existe");
			}

			return content;
		} catch (DKUsageError e) {
			throw new LoggeableException(Errors.EC_FALLO_ARMAR_OBJETO_CM, e.toString());
		} catch (LoggeableException e) {
			throw e;
		} catch (Exception e) {
			throw new LoggeableException(Errors.EC_FALLO_RECUPERAR_IMAGEN, e.toString());
		} finally {
			CMConnectionFactory.getInstance(poolCMID).release(dsConn);
		}
	}
	*/
	public byte[] retrieveImage(String indexSubclass, long objetoID, int numPagina) throws LoggeableException {
		
		com.ibm.mm.sdk.server.DKDatastoreICM dsConn = null;

		try {
				dsConn = CMConnectionFactory.getInstance().get();
				ObjetoCM objetoCM;

				if (Constantes.INDEX_SUBCLASS_ASIENTO.equals(indexSubclass)) {
					objetoCM = new AsientoCM();
				} else if (Constantes.INDEX_SUBCLASS_FICHA.equals(indexSubclass)) {
					objetoCM = new FichaCM();
				} else if (Constantes.INDEX_SUBCLASS_FOLIO.equals(indexSubclass)) {
					objetoCM = new FolioCM();
				} else {
					throw new LoggeableException(Errors.EC_INDEXSUBCLASS_NO_ENCONTRADO, "indexSubclassReferido=" + indexSubclass);
				} 

				objetoCM.setObjetoID(objetoID);
				
				//Usado para devolver el objeto de la base de datos				
				DKDDO ddoRecuperado = find(objetoCM, dsConn);
				ddoRecuperado.retrieve();				
				DKParts parts = (DKParts) ddoRecuperado.getDataByName(DKConstant.DK_CM_DKPARTS);
				if (parts == null) {
					throw new LoggeableException("No se recuperaron las páginas del objeto.");
				}				
				
				dkIterator iter = parts.createIterator();
				int contador = 0;
				byte[] content = null;
				for (int i = 0; iter.more(); i++) {
					DKLobICM pLobPart = (DKLobICM)iter.next();
					//DKBlobDL blob = (DKBlobDL) iter.next();
					if (++contador == numPagina) {
						if (pLobPart != null) {
							pLobPart.retrieve();
							content = pLobPart.getContent();
						} else {
							throw new LoggeableException("La imagen pedida no tiene data");
						}
					}
				}
								
				if (content == null) {
					throw new LoggeableException("La página pedida no existe");
				}

				return content;
				
			} catch (DKUsageError e) {
				e.printStackTrace();
				throw new LoggeableException(e.toString());
			} catch (LoggeableException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				 throw new LoggeableException(e.toString());
			} finally {
				 CMConnectionFactory.getInstance().release(dsConn);
			}
	}

	/***** fin ****/	
	
	public int getNumPaginas(String poolCMID, String indexSubclass, long objetoID) throws LoggeableException {
		//jacaceres - CM8
		//DKDatastoreDL dsConn = null;
		DKDatastoreICM dsConn = null;
		

		try {
			//jacaceres - CM8
			//dsConn = CMConnectionFactory.getInstance(poolCMID).get();
			dsConn = CMConnectionFactory.getInstance().get();

			ObjetoCM objetoCM;

			if (Constantes.INDEX_SUBCLASS_ASIENTO.equals(indexSubclass)) {
				objetoCM = new AsientoCM();
			//} else if (Constantes.INDEX_SUBCLASS_OTRO.equals(indexSubclass)) {
			//	objetoCM = new OtroCM$FUE();
			} else if (Constantes.INDEX_SUBCLASS_FICHA.equals(indexSubclass)) {
				objetoCM = new FichaCM();
			} else if (Constantes.INDEX_SUBCLASS_FOLIO.equals(indexSubclass)) {
				objetoCM = new FolioCM();
			} else {
				throw new LoggeableException(Errors.EC_INDEXSUBCLASS_NO_ENCONTRADO, "indexSubclassReferido=" + indexSubclass);
			}

			objetoCM.setObjetoID(objetoID);

			DKDDO ddoRecuperado = find(objetoCM, dsConn);

			DKParts parts = (DKParts) ddoRecuperado.getDataByName(DKConstantDL.DKPARTS);

			if (parts == null) {
				throw new LoggeableException(Errors.EC_FALLO_RECUPERAR_IMAGEN, "No se recuperaron las páginas del objeto.");
			}
/*
			dkIterator iter = parts.createIterator();

			int contador = 0;
			for (int i = 0; iter.more(); i++) {
				DKBlobDL blob = (DKBlobDL) iter.next();
				contador++;
			}
*/
			return parts.cardinality();
		} catch (DKUsageError e) {
			throw new LoggeableException(Errors.EC_FALLO_ARMAR_OBJETO_CM, e.toString());
		} catch (LoggeableException e) {
			throw e;
		} catch (Exception e) {
			throw new LoggeableException(Errors.EC_FALLO_RECUPERAR_IMAGEN, e.toString());
		} finally {
			//jacaceres - CM8
			//CMConnectionFactory.getInstance(poolCMID).release(dsConn);
			CMConnectionFactory.getInstance().release(dsConn);			
		}
	}

}