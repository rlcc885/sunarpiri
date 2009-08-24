/*
 * Created on 18/01/2007
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
 */
public class DetalleCajaResumenBean extends SunarpBean{

	private String codCaja;
	private String descCaja;
	private BigDecimal totalIngresoCaja = new BigDecimal(0);
  
  	private String cuentaId;
  
	/**
	 * @return
	 */
	public String getCodCaja() {
		return codCaja;
	}

	/**
	 * @return
	 */
	public String getDescCaja() {
		return descCaja;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotalIngresoCaja() {
		return totalIngresoCaja;
	}

	/**
	 * @return
	 */
	public String getTotalIngresoCajaAsString() {

		String stringAux = "";
		if(totalIngresoCaja == null){
			stringAux = "0.00";
		} else {
			stringAux = new DecimalFormat("0.00").format(totalIngresoCaja.doubleValue());
		}
		
		return stringAux;
	}

	/**
	 * @param string
	 */
	public void setCodCaja(String string) {
		codCaja = string;
	}

	/**
	 * @param string
	 */
	public void setDescCaja(String string) {
		descCaja = string;
	}

	/**
	 * @param decimal
	 */
	public void setTotalIngresoCaja(BigDecimal decimal) {
		totalIngresoCaja = decimal;
	}

	/**
	 * @return
	 */
	public String getCuentaId() {
		return cuentaId;
	}

	/**
	 * @param string
	 */
	public void setCuentaId(String string) {
		cuentaId = string;
	}

}
