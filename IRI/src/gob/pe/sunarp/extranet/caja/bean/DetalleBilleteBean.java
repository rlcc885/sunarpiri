/*
 * Created on 19/01/2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.caja.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author lsuarez
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DetalleBilleteBean extends SunarpBean{
	
	public static final String COD_BILLETE_DOSCIENTOS_SOLES = "14";
	public static final String COD_BILLETE_CIEN_SOLES = "01";
	public static final String COD_BILLETE_CINCUENTA_SOLES = "02";
	public static final String COD_BILLETE_VEINTE_SOLES = "15";
	public static final String COD_BILLETE_DIEZ_SOLES = "03";

	public static final String DESC_BILLETE_DOSCIENTOS_SOLES = "DOSCIENTOS NUEVOS SOLES";
	public static final String DESC_BILLETE_CIEN_SOLES = "CIEN NUEVOS SOLES";
	public static final String DESC_BILLETE_CINCUENTA_SOLES = "CINCUENTA NUEVOS SOLES";
	public static final String DESC_BILLETE_VEINTE_SOLES = "VEINTE NUEVOS SOLES";
	public static final String DESC_BILLETE_DIEZ_SOLES = "DIEZ NUEVOS SOLES";

	private String codigo;
	private String descripcion;
	private Long unidades;
	private BigDecimal monto = new BigDecimal(0);


	/**
	 * @return
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @return
	 */
	public BigDecimal getMonto() {
		return monto;
	}

	/**
	 * @return
	 */
	public String getMontoAsString() {

		String stringAux = "";
		if(monto == null){
			stringAux = "0.00";
		} else {
			stringAux = new DecimalFormat("0.00").format(monto.doubleValue());
		}
		
		return stringAux;
	}

	/**
	 * @return
	 */
	public Long getUnidades() {
		return unidades;
	}

	/**
	 * @param string
	 */
	public void setCodigo(String string) {
		codigo = string;
	}

	/**
	 * @param string
	 */
	public void setDescripcion(String string) {
		descripcion = string;
	}

	/**
	 * @param decimal
	 */
	public void setMonto(BigDecimal decimal) {
		monto = decimal;
	}

	/**
	 * @param long1
	 */
	public void setUnidades(Long long1) {
		unidades = long1;
	}

}
