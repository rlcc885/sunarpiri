package gob.pe.sunarp.extranet.util;

/*
13 dic 2002
llamado desde ConsultarPartida y BuscarTitulo
*/
import gob.pe.sunarp.extranet.dbobj.*;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBConnectionPool;
import java.sql.*;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import java.util.*;

public class Job003 implements Runnable {

	private UsuarioBean usuario = null;
	private int codigoServicio = 0;
	private String regPubId;
	private String oficRegId;
	private String area;

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
		Thread.currentThread().setName("Job003");
		if (trace)
			System.err.println(now() + " Inicio.");

		if (Propiedades.getInstance().getFlagTransaccion() == false)
			return;

		try {

			DBConnectionFactory pool = DBConnectionFactory.getInstance();
			Connection conn = null;
			double costo_servicio = 0;

			try {
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				DBConnection dconn = new DBConnection(conn);

				//calcular costo del servicio

				if (usuario.getFgInterno() == false) {
					DboTarifa dbo2 = new DboTarifa(dconn);
					dbo2.setFieldsToRetrieve(DboTarifa.CAMPO_PREC_OFIC);
					dbo2.setField(dbo2.CAMPO_SERVICIO_ID, new Integer(codigoServicio).toString());
					boolean b = dbo2.find();
					if (b == true)
						costo_servicio = Double.parseDouble(dbo2.getField(dbo2.CAMPO_PREC_OFIC));
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				pool.release(conn);
			}

			String currentDateYYYYMMDD = FechaUtil.getCurrentDateYYYYMMDD();

			java.util.Vector datos = new java.util.Vector();

			UsoServicioBean bean = new UsoServicioBean();
			bean.setRegPubId(regPubId);
			bean.setOficRegId(oficRegId);
			bean.setAreaRegId(area);
			datos.add(bean);

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

	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}
	public void setCodigoServicio(int codigoServicio) {
		this.codigoServicio = codigoServicio;
	}
	public void setRegPubId(String regPubId) {
		this.regPubId = regPubId;
	}
	public void setOficRegId(String oficRegId) {
		this.oficRegId = oficRegId;
	}
	public void setArea(String area) {
		this.area = area;
	}
}