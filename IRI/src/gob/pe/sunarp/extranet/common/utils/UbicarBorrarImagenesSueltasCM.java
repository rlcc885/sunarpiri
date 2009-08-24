package gob.pe.sunarp.extranet.common.utils;

import gob.pe.sunarp.extranet.common.LoggeableException;
import gob.pe.sunarp.extranet.common.cm.CMConnectionFactory;
import gob.pe.sunarp.extranet.common.cm.CMProcessor;

import gob.pe.sunarp.extranet.common.logica.Constantes;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class UbicarBorrarImagenesSueltasCM {

	private static boolean salir = false;

	private static final String selectAsientos =
		"select refnum_part from asiento where id_img_asiento = ?";
	private static final String selectFichas =
		"select refnum_part from ficha where id_img_ficha = ?";
	private static final String selectFolios =
		"select refnum_part from tomo_folio where id_img_folio = ?";

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd.HHmmss");
/*
	public static void main(String[] args) {

		try {

			new EsperarSalir(new EsperarSalirListener() {
				public void salir() {
					salir = true;
				}
			}).start();

			Connection conn = null;

			PrintWriter fileOK = null;
			PrintWriter fileErrorLoggeable = null;
			PrintWriter fileErrorSQL = null;

			try {
				CMConnectionFactory.getInstance("DEFAULT").init("LIBSRVRX", "J1O1U1", "UN4FRN", 1, 10 * 60 * 1000, 5 * 60 * 1000);
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();

				// realizando las conexiones a las 2 bases de datos
				conn = DriverManager.getConnection("jdbc:oracle:thin:@172.18.1.23:1521:DBSP", "user1", "user12002");
				conn.setAutoCommit(true);

				String p = formatter.format(new java.util.Date());

				fileOK = new PrintWriter(new BufferedWriter(new FileWriter(p + ".actualizadas.txt")));
				fileErrorLoggeable = new PrintWriter(new BufferedWriter(new FileWriter(p + "errorLoggeable.txt")));
				fileErrorSQL = new PrintWriter(new BufferedWriter(new FileWriter(p + "errorSQL.txt")));
				fileErrorNotFound = new PrintWriter(new BufferedWriter(new FileWriter(p + "errorNotFound.txt")));

				Statement selectStmt = conn.createStatement();

				procesarFichas(conn, selectStmt, fileOK, fileErrorLoggeable, fileErrorSQL, fileErrorNotFound);
				
				procesarAsientos(conn, selectStmt, fileOK, fileErrorLoggeable, fileErrorSQL, fileErrorNotFound);

				selectStmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fileOK != null)
					fileOK.close();
				if (fileErrorLoggeable != null)
					fileErrorLoggeable.close();
				if (fileErrorSQL != null)
					fileErrorSQL.close();
				if (fileErrorNotFound != null)
					fileErrorNotFound.close();
				if (conn != null)
					conn.close();
				CMConnectionFactory.getInstance("DEFAULT").close();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.out.println("Proceso finalizado.");

	}

	private static void procesarAsientos(
		Connection conn,
		Statement selectStmt,
		PrintWriter fileOK,
		PrintWriter fileErrorLoggeable,
		PrintWriter fileErrorSQL,
		PrintWriter fileErrorNotFound)
		throws SQLException {

		PreparedStatement pstmt = conn.prepareStatement(selectAsientos);

		String indexClass = Constantes.INDEX_SUBCLASS_ASIENTO;

		long contador = 0;
		while (rs.next()) {

			if (salir) {
				System.out.println("Proceso interrumpido");
				break;
			}

			if (contador % 100 == 0) {
				pstmt.close();
				System.gc();

				pstmt = conn.prepareStatement(selectAsientos);
				System.out.println("Procesadas " + contador + " " + indexClass);
				System.out.println("Memoria usada -> " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			}


			contador++;

			long refnum_part = rs.getLong("refnum_part");
			int ns_asiento = rs.getInt("ns_asiento");
			String cod_acto = rs.getString("cod_acto");
			long id_img_asiento = rs.getLong("id_img_asiento");

			String key =
				new StringBuffer(indexClass)
					.append(" ")
					.append(refnum_part)
					.append(" ")
					.append(ns_asiento)
					.append(" ")
					.append(cod_acto)
					.append(" ")
					.append(" ")
					.append(id_img_asiento)
					.toString();

			try {
				int numPaginas = CMProcessor.getInstance().getNumPaginas("DEFAULT", indexClass, id_img_asiento);
				updatePstmt.setInt(1, numPaginas);
				updatePstmt.setLong(2, refnum_part);
				updatePstmt.setInt(3, ns_asiento);
				updatePstmt.setString(4, cod_acto);
				updatePstmt.setLong(5, id_img_asiento);
				realizarUpdate(updatePstmt, indexClass, key, fileOK, fileErrorNotFound);
			} catch (LoggeableException e) {
				fileErrorLoggeable.println(
					new StringBuffer(key).append(" ").append(e.getErrorCode()).append(" ").append(e.getOtroMensaje()).toString());
			} catch (SQLException e) {
				fileErrorSQL.println(new StringBuffer(key).append(" ").append(e.getErrorCode()).toString());
			}

		}
		
		rs.close();
		updatePstmt.close();
	}

	private static void procesarFichas(
		Connection conn,
		Statement selectStmt,
		PrintWriter fileOK,
		PrintWriter fileErrorLoggeable,
		PrintWriter fileErrorSQL,
		PrintWriter fileErrorNotFound)
		throws SQLException {

		PreparedStatement updatePstmt = conn.prepareStatement(updateFichas);

		String indexClass = Constantes.INDEX_SUBCLASS_FICHA;
		ResultSet rs = selectStmt.executeQuery(selectFichas);

		long contador = 0;
		while (rs.next()) {

			if (salir) {
				System.out.println("Proceso interrumpido");
				break;
			}

			if (contador % 100 == 0) {
				updatePstmt.close();
				System.gc();

				updatePstmt = conn.prepareStatement(updateFichas);
				System.out.println("Procesadas " + contador + " " + indexClass);
				long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				System.out.println("Memoria usada -> " + mem);
				if (mem > 200000000) {
					System.out.println("REINICIAR LA CARGA!!!!!!");
					break;
				}
			}

			contador++;

			long refnum_part = rs.getLong("refnum_part");
			long id_img_ficha = rs.getLong("id_img_ficha");

			String key = new StringBuffer(indexClass).append(" ").append(refnum_part).append(" ").append(id_img_ficha).toString();


			try {
				int numPaginas = CMProcessor.getInstance().getNumPaginas("DEFAULT", indexClass, id_img_ficha);

				updatePstmt.setInt(1, numPaginas);
				updatePstmt.setLong(2, refnum_part);
				updatePstmt.setLong(3, id_img_ficha);
				realizarUpdate(updatePstmt, indexClass, key, fileOK, fileErrorNotFound);

			} catch (LoggeableException e) {
				fileErrorLoggeable.println(
					new StringBuffer(key).append(" ").append(e.getErrorCode()).append(" ").append(e.getOtroMensaje()).toString());
			} catch (SQLException e) {
				fileErrorSQL.println(new StringBuffer(key).append(" ").append(e.getErrorCode()).toString());
			}

		}

		rs.close();
		updatePstmt.close();
	}

	private static void realizarUpdate(
		PreparedStatement updatePstmt,
		String indexClass,
		String key,
		PrintWriter fileOK,
		PrintWriter fileErrorNotFound)
		throws SQLException {
		int num = updatePstmt.executeUpdate();
		if (num == 1) {
			fileOK.println(key);
		} else if (num < 1) {
			fileErrorNotFound.println(key);
		} else {
			throw new RuntimeException("Se actualizo a 2 o mas " + indexClass + " con un solo update!!!!.  KEY=" + key);
		}
	}
*/
}