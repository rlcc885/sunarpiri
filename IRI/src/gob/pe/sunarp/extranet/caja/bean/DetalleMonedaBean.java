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
public class DetalleMonedaBean extends SunarpBean{

	public static final String COD_MONEDA_CINCO_SOLES = "13";
	public static final String COD_MONEDA_DOS_SOLES = "17";
	public static final String COD_MONEDA_UN_SOL = "06";
	public static final String COD_MONEDA_CINCUENTA_CENTIMOS = "05";
	public static final String COD_MONEDA_VEINTE_CENTIMOS = "20";
	public static final String COD_MONEDA_DIEZ_CENTIMOS = "04";
	public static final String COD_MONEDA_CINCO_CENTIMOS = "21";

	public static final String DESC_MONEDA_CINCO_SOLES = "CINCO NUEVOS SOLES";
	public static final String DESC_MONEDA_DOS_SOLES = "DOS NUEVOS SOLES";
	public static final String DESC_MONEDA_UN_SOL = "UN NUEVO SOL";
	public static final String DESC_MONEDA_CINCUENTA_CENTIMOS = "CINCUENTA CENTIMOS";
	public static final String DESC_MONEDA_VEINTE_CENTIMOS = "VEINTE CENTIMOS";
	public static final String DESC_MONEDA_DIEZ_CENTIMOS = "DIEZ CENTIMOS";
	public static final String DESC_MONEDA_CINCO_CENTIMOS = "CINCO CENTIMOS";

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
