package gob.pe.sunarp.extranet.util;

/*
4 dic 2002
llamado desde BuscarXIndiceController.java
*/
import gob.pe.sunarp.extranet.dbobj.*;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import java.sql.*;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import java.util.*;
import gob.pe.sunarp.extranet.framework.Loggy;

public class Job002 implements Runnable {

	private String query;
	private UsuarioBean usuario = null;
	private int codigoServicio = 0;
	public static final boolean trace = true;
	public static final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
	private String now() {
		return new StringBuffer(this.getClass().getName())
			.append(" ")
			.append(Thread.currentThread().getName())
			.append(" ")
			.append(formatter.format(new java.util.Date()))
			.toString();
	}

	public void run() {
		Thread.currentThread().setName("Job002");
		if (trace)
			System.err.println(now() + " Inicio.");

		if (Propiedades.getInstance().getFlagTransaccion() == false)
			return;

		try {
			
			double costo_servicio=0;
			java.util.Vector datos=null;

			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			Statement stmt = null;
			ResultSet rset = null;
			

			try {
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);

				//calcular costo del servicio
				costo_servicio = 0;
				if (usuario.getFgInterno() == false) {
					DboTarifa dbo2 = new DboTarifa(dconn);
					dbo2.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
					dbo2.setField(dbo2.CAMPO_SERVICIO_ID, "" + codigoServicio);
					boolean b = dbo2.find();
					if (b == true)
						costo_servicio = Double.parseDouble(dbo2.getField(dbo2.CAMPO_PREC_OFIC));
				}

				/**
				 * SVASQUEZ - AVATAR GLOBAL
				 * ANALISIS DE job002
				 * System.err.println("query job002::"+query);
				 */
				System.err.println(now() + "query job002::"+query);
				
				stmt = conn.createStatement();				
				rset = stmt.executeQuery(query);

				datos = new java.util.Vector();
				while (rset.next()) {
					UsoServicioBean bean = new UsoServicioBean();
					bean.setRegPubId(rset.getString("reg_pub_id"));
					bean.setOficRegId(rset.getString("ofic_reg_id"));
					bean.setAreaRegId(rset.getString("area_reg_id"));
					if (bean.getRegPubId() == null || bean.getOficRegId() == null || bean.getAreaRegId() == null)
						if (Loggy.isTrace(this))
							System.out.println("_____job002_query_ " + query);
					datos.add(bean);
				}
			}catch(SQLException s){
				s.printStackTrace();
				System.err.println("Ocurrio un error de Base de Datos en el JOb002::"+s.getMessage());
				System.err.println("Estado de SQL.................................::"+s.getSQLState());

			}
			catch(Exception e){
				e.printStackTrace();
				System.err.println("Ocurrio un error de General en el JOb002::"+e.getMessage());				
			}
			finally {
				if(rset!=null)
					rset.close();
				if(stmt!=null)	
					stmt.close();
				pool.release(conn);
			}
			String currentDateYYYYMMDD = FechaUtil.getCurrentDateYYYYMMDD();

			UsoServicio usoServicio = UsoServicio.getInstance();
			usoServicio.registraUsoServicio(
				codigoServicio,
				usuario.getFgInterno(),
				usuario.getRegPublicoId(),
				usuario.getFgIndividual(),
				currentDateYYYYMMDD,
				costo_servicio,
				datos);

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (trace)
				System.err.println(now() + " Fin.");
		}
	}

	/**
	 * Sets the usuario
	 * @param usuario The usuario to set
	 */
	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * Sets the codigoServicio
	 * @param codigoServicio The codigoServicio to set
	 */
	public void setCodigoServicio(int codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

}