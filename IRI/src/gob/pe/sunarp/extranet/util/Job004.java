package gob.pe.sunarp.extranet.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.publicidad.bean.FormOutputBuscarPartida;
import gob.pe.sunarp.extranet.publicidad.bean.PartidaBean;

/**
 * @autor dbravo
 * @fecha Jun 15, 2007
 * @CC:SUNARP-REGMOBCON
 * @return Hilo para el manejo de Uso de servicio
 */
public class Job004 implements Runnable {

	private UsuarioBean usuario = null;
	private int codigoServicio = 0;
	private String regPubId;
	private String oficRegId;
	private String area;
	private double costoServicio = 0;
	private String query = null;
	private FormOutputBuscarPartida formOutputBuscarPartida = null;
	
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
		Thread.currentThread().setName("Job004");
		if (trace)
			System.out.println(now() + " Inicio.");

		if (Propiedades.getInstance().getFlagTransaccion() == false)
			return;

		try {

			String currentDateYYYYMMDD = FechaUtil.getCurrentDateYYYYMMDD();

			java.util.Vector<UsoServicioBean> datos = new java.util.Vector<UsoServicioBean>();

			UsoServicioBean bean = new UsoServicioBean();
			
			if(getFormOutputBuscarPartida()!=null){
				
				ArrayList respuesta = getFormOutputBuscarPartida().getResultado();
				
				for(int x=0; respuesta!=null && x<respuesta.size(); x++){
					PartidaBean partidaBean = (PartidaBean)respuesta.get(x);
					bean.setRegPubId(partidaBean.getRegPubId());
					bean.setOficRegId(partidaBean.getOficRegId());
					bean.setAreaRegId(partidaBean.getAreaRegistralId());
					
					datos.add(bean);
				}
				
			}else if(getQuery()!=null){
				
				DBConnectionFactory pool = DBConnectionFactory.getInstance();
				Connection conn = null;
				
				try {
					conn = pool.getConnection();
					conn.setAutoCommit(false);

					Statement stmt = conn.createStatement();
					ResultSet rset = stmt.executeQuery(query);

					while (rset.next()) 
					{	
						//Inicio:mgarate:13/09/2007
						//Cambio de bean para que se instancie por cada registro
						UsoServicioBean bean2 = new UsoServicioBean();
						bean2.setRegPubId(rset.getString("reg_pub_id"));
						bean2.setOficRegId(rset.getString("ofic_reg_id"));
						bean2.setAreaRegId(rset.getString("area_reg_id"));
						if (bean2.getRegPubId() == null || bean2.getOficRegId() == null || bean2.getAreaRegId() == null){
							if (Loggy.isTrace(this)){
								System.out.println("_____job004_query_ " + query);
							}
						}
						datos.add(bean2);
						//Fin:mgarate
					}

				} finally {
					pool.release(conn);
				}
				
			}else{
				bean.setRegPubId(regPubId);
				bean.setOficRegId(oficRegId);
				bean.setAreaRegId(area);
				datos.add(bean);
			}
			
			UsoServicio usoServicio = UsoServicio.getInstance();
			usoServicio.registraUsoServicio(
				codigoServicio,
				usuario.getFgInterno(),
				usuario.getRegPublicoId(),
				usuario.getFgIndividual(),
				currentDateYYYYMMDD,
				costoServicio,
				datos);

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (trace)
				System.out.println(now() + " Fin.");
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

	/**
	 * @autor dbravo
	 * @fecha Jun 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the costoServicio
	 */
	public double getCostoServicio() {
		return costoServicio;
	}

	/**
	 * @autor dbravo
	 * @fecha Jun 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param costoServicio the costoServicio to set
	 */
	public void setCostoServicio(double costoServicio) {
		this.costoServicio = costoServicio;
	}

	/**
	 * @autor dbravo
	 * @fecha Jun 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @autor dbravo
	 * @fecha Jun 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the formOutputBuscarPartida
	 */
	public FormOutputBuscarPartida getFormOutputBuscarPartida() {
		return formOutputBuscarPartida;
	}

	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param formOutputBuscarPartida the formOutputBuscarPartida to set
	 */
	public void setFormOutputBuscarPartida(
			FormOutputBuscarPartida formOutputBuscarPartida) {
		this.formOutputBuscarPartida = formOutputBuscarPartida;
	}
}