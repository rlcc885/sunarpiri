/*
 * Created on 26-ene-07
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.caja.bean;
import gob.pe.sunarp.extranet.common.SunarpBean;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jbugarin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConsolidadoExtornoAdmBean extends SunarpBean{
	
	private BigDecimal subTotal = new BigDecimal(0);
	//listado para el reporte de tesoreros
	private List listaExtornos = new ArrayList();
	private String subTotalFormat ;

	/**
	 * @return
	 */
	public List getListaExtornos() {
		return listaExtornos;
	}

	/**
	 * @return
	 */
	public BigDecimal getSubTotal() {
		return subTotal;
	}

	/**
	 * @param list
	 */
	public void setListaExtornos(List list) {
		listaExtornos = list;
	}

	/**
	 * @param decimal
	 */
	public void setSubTotal(BigDecimal decimal) {
		subTotal = decimal;
	}

	/**
	 * @return
	 */
	public String getSubTotalFormat() {
		return subTotalFormat;
	}

	/**
	 * @param string
	 */
	public void setSubTotalFormat(String string) {
		subTotalFormat = string;
	}

}
