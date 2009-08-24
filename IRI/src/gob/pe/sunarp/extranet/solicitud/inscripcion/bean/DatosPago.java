package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.math.BigDecimal;
/** @modelguid {40323D44-ABD0-4BC3-A80F-EA7048F6966F} */
public class DatosPago extends SunarpBean{
	
	/** @modelguid {63649DBA-36ED-4497-8089-752939926D21} */
	private BigDecimal costoTotalServicio;

	/** @modelguid {96EC8CCD-4D47-4A7C-9070-DCFF47F0AEAE} */
	private String codigoFormaPago;

	/** @modelguid {34A503A8-1F7E-4B15-ADA4-5A39FAAC3252} */
	private String descripcionFormaPago;

	/** @modelguid {3199F07B-7DA0-48CF-9111-32F4B3928802} */
	private String numeroOperacion;

	/** @modelguid {DD195685-234F-490E-AE09-489A3AC710C5} */
	private String fechaPago;

	/** @modelguid {12D43F92-2D7F-4891-BC99-A9B1807FFE6B} */
	private String horaPago;

	/** @modelguid {1369383C-0B92-4AB0-B090-BFB4BCA4E6E5} */
	private String codigoTipoPago;

	/** @modelguid {3C7078E4-47B2-4844-80F2-1F2C0FF32F2F} */
	private String descripcionTipoPago;



	/**
	 * @return
	 * @modelguid {BBC70AFF-38C1-424C-849A-4DE0ECFD7142}
	 */
	public String getCodigoFormaPago() {
		return codigoFormaPago;
	}

	/**
	 * @return
	 * @modelguid {B952C347-254F-455C-AC16-88910E1CE264}
	 */
	public String getCodigoTipoPago() {
		return codigoTipoPago;
	}

	/**
	 * @return
	 * @modelguid {C16877CD-16B3-4A74-B42A-A1BA2EE63AD1}
	 */
	public BigDecimal getCostoTotalServicio() {
		return costoTotalServicio;
	}

	/**
	 * @return
	 * @modelguid {BC842720-A1E8-47B3-9961-698AE5F7265F}
	 */
	public String getDescripcionFormaPago() {
		return descripcionFormaPago;
	}

	/**
	 * @return
	 * @modelguid {FD8C42CC-56FD-4C0B-B35F-F36C6D902CC1}
	 */
	public String getDescripcionTipoPago() {
		return descripcionTipoPago;
	}

	/**
	 * @return
	 * @modelguid {AE5E983F-4835-4E04-9014-40549A13F449}
	 */
	public String getFechaPago() {
		return fechaPago;
	}

	/**
	 * @return
	 * @modelguid {A3BD5465-E1B1-47DE-A812-C261BDDD9B34}
	 */
	public String getHoraPago() {
		return horaPago;
	}

	/**
	 * @return
	 * @modelguid {68BFF334-CCE7-4992-954D-353CCD8D672E}
	 */
	public String getNumeroOperacion() {
		return numeroOperacion;
	}

	/**
	 * @param string
	 * @modelguid {E87BEFB5-4313-49AE-AA5B-9F0EBD2559F3}
	 */
	public void setCodigoFormaPago(String string) {
		codigoFormaPago = string;
	}

	/**
	 * @param string
	 * @modelguid {E076D9B6-79F8-4C92-B33B-662431189C2A}
	 */
	public void setCodigoTipoPago(String string) {
		codigoTipoPago = string;
	}

	/**
	 * @param decimal
	 * @modelguid {E59EC693-0B3B-4F86-B6E9-550F5A2C4FBC}
	 */
	public void setCostoTotalServicio(BigDecimal decimal) {
		costoTotalServicio = decimal;
	}

	/**
	 * @param string
	 * @modelguid {A1D5C4EA-5540-4B8E-853B-1307D4744205}
	 */
	public void setDescripcionFormaPago(String string) {
		descripcionFormaPago = string;
	}

	/**
	 * @param string
	 * @modelguid {AD6D4BDD-E173-4A39-9C80-89699ACFDEC7}
	 */
	public void setDescripcionTipoPago(String string) {
		descripcionTipoPago = string;
	}

	/**
	 * @param string
	 * @modelguid {56E901A2-0E97-46E7-8F8A-B4BC6D26B68A}
	 */
	public void setFechaPago(String string) {
		fechaPago = string;
	}

	/**
	 * @param string
	 * @modelguid {70A60568-9C99-4348-A05C-E06233AFC18A}
	 */
	public void setHoraPago(String string) {
		horaPago = string;
	}

	/**
	 * @param string
	 * @modelguid {E3AC1735-1678-4ABC-8890-D9C624352135}
	 */
	public void setNumeroOperacion(String string) {
		numeroOperacion = string;
	}

}

