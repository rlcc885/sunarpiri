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
import java.util.ArrayList;
import java.util.List;

/**
 * @author lsuarez
 *
 */
public class DetalleResumenBean extends SunarpBean{

	private String codEdificio;
	private String descEdificio;
	private BigDecimal totalIngreso = new BigDecimal(0);	

	//lista de cajas
	private List listaCajas = new ArrayList();

	/**
	 * @return
	 */
	public String getCodEdificio() {
		return codEdificio;
	}

	/**
	 * @return
	 */
	public String getDescEdificio() {
		return descEdificio;
	}

	/**
	 * @return
	 */
	public List getListaCajas() {
		return listaCajas;
	}

	/**
	 * @return
	 */
	public BigDecimal getTotalIngreso() {
		return totalIngreso;
	}

	/**
	 * @return
	 */
	public String getTotalIngresoAsString() {
		
		String stringAux = "";
		if(totalIngreso == null){
			stringAux = "0.00";
		} else {
			stringAux = new DecimalFormat("0.00").format(totalIngreso.doubleValue());
		}
		
		return stringAux;
	}

	/**
	 * @param string
	 */
	public void setCodEdificio(String string) {
		codEdificio = string;
	}

	/**
	 * @param string
	 */
	public void setDescEdificio(String string) {
		descEdificio = string;
	}

	/**
	 * @param list
	 */
	public void setListaCajas(List list) {
		listaCajas = list;
	}

	/**
	 * @param decimal
	 */
	public void setTotalIngreso(BigDecimal decimal) {
		totalIngreso = decimal;
	}

}
