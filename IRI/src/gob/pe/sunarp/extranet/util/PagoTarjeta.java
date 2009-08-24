package gob.pe.sunarp.extranet.util;


//paquetes del sistema
import java.sql.Connection;
import com.jcorporate.expresso.core.db.DBConnection;
import gob.pe.sunarp.extranet.dbobj.DboMediosPago;
import gob.pe.sunarp.extranet.dbobj.DboPagoEnLinea;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.prepago.bean.PagoTarjetaBean;




public class PagoTarjeta
{
	private PagoTarjetaBean ptbean = new PagoTarjetaBean();
	public boolean isTrace(Object _this) 
	{
		return Loggy.isTrace(_this);
	}
	
	public void efectuaAbono(Connection conn) throws Throwable 
	{
		DBConnection dconn = new DBConnection(conn);
		// recupera los parámetros del medio de pago
			DboMediosPago medioPagoI = new DboMediosPago(dconn);
			medioPagoI.setField(DboMediosPago.CAMPO_MEDIO_ID, ptbean.getMedioId());
			medioPagoI.find();
			
			DboPagoEnLinea pagoLineaI = new DboPagoEnLinea(dconn);
			long numOrden = 0;
			// Verificamos si ya se tiene un Numero de Orden en Sesion
			// Es decir si esta reintentando de nuevo realizar el pago en Linea
			
			//HttpSession session = ExpressoHttpSessionBean.getSession(request);
				
				if (isTrace(this)) System.out.println("Agregando nueva transacción de pago en linea.");
				pagoLineaI.clear();
				pagoLineaI.clearFieldsToRetrieve();
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_COD_VERIFIC, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_ESTADO, "P");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_RESP, null);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_HOR_SOL, FechaUtil.dateTimeToStringToOracle(new java.sql.Timestamp(System.currentTimeMillis())));
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_TS_TRANSAC, null);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_FEC_VENCIM, "*");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MEDIO_ID, ptbean.getMedioId());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MONTO, ptbean.getMonto());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_MOVIMIENTO_ID, null);
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TERMINAL, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NRO_TRANSAC, "");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NUM_ITEMS, ptbean.getNumItems());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_NUM_TARJ, "*");
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_USR_CREA, ptbean.getUserId());
				pagoLineaI.setField(DboPagoEnLinea.CAMPO_PERSONA_ID, ptbean.getPersonaId());
				//pagoLineaI.setField(DboPagoEnLinea.CAMPO_GLOSA, ptbean.getGlosa());

				if(ptbean.getSolicitudId() != null)
				{
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_SOLICITUD_ID, ptbean.getSolicitudId());
				}
				else
				{
					pagoLineaI.setField(DboPagoEnLinea.CAMPO_SOLICITUD_ID, null);
				}
				System.out.println("Antes de pagar en linea:::::");
				pagoLineaI.add();
				System.out.println("Despues de pagar en linea:::::");
				System.out.println();
				numOrden = (long) pagoLineaI.max(DboPagoEnLinea.CAMPO_PAGO_EN_LINEA_ID);
				
				//session.setAttribute("numOrden", Long.toString(numOrden));
				ptbean.setNumOrden(Long.toString(numOrden));
				if (isTrace(this)) System.out.println("Generando orden: " + numOrden);
				
			StringBuffer url = new StringBuffer();
			if(gob.pe.sunarp.extranet.util.Propiedades.getInstance().getFlagProduccion())
				url.append(gob.pe.sunarp.extranet.util.DirVisa.getInstance().getDir1Prod());
			else
				url.append(gob.pe.sunarp.extranet.util.DirVisa.getInstance().getDir1Desa());
			url.append("?CODTIENDA=").append(medioPagoI.getField(DboMediosPago.CAMPO_COD_TIENDA))
				.append("&MOUNT=").append(ptbean.getMonto())
				.append("&PAN=").append(ptbean.getNumero());
			url.append("&EXPIRYYEAR=").append(ptbean.getAno())
				.append("&EXPIRYMONTH=").append(ptbean.getMes())
				.append("&CURRENCY=").append(medioPagoI.getField(DboMediosPago.CAMPO_MONEDA));
			url.append("&NUMORDEN=").append(numOrden)
				.append("&TIPORESP=").append(medioPagoI.getField(DboMediosPago.CAMPO_TPO_RESP));
			ptbean.setUrl(url.toString());
	}
	

	/**
	 * Gets the ptbean
	 * @return Returns a PagoTarjetaBean
	 */
	public PagoTarjetaBean getPtbean() 
	{
		return ptbean;
	}
	/**
	 * Sets the ptbean
	 * @param ptbean The ptbean to set
	 */
	public void setPtbean(PagoTarjetaBean ptbean) 
	{
		this.ptbean = ptbean;
	}

}

