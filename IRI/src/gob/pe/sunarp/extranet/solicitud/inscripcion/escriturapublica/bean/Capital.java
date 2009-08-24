package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.math.BigDecimal;
/** @modelguid {AD00A751-E702-4381-A225-9F0B799B241A} */
public class Capital extends SunarpBean{
	
	/** @modelguid {88FC3C4D-13A4-44D9-BBFD-DC59F51DF2B7} */
	private String codigoMoneda;

	/** @modelguid {EBFCC795-D8AC-429C-9E78-8B98280BA917} */
	private String descripcionMoneda;

	/** @modelguid {1B20905A-C440-4C21-A4AD-C7C2A432E55C} */
	private BigDecimal montoCapital;

	/** @modelguid {88C8AE1F-F05D-4FCA-AB32-893EA130C860} */
	private BigDecimal valor;
	
	/** @modelguid {363AF1F7-10F1-4B01-AB25-2CAAC86F198F} */
	private int numero;

	/** @modelguid {132F4B32-6C26-4720-B3FF-9997E8AF91D2} */
	private String codigoCancelacionCapital;

	/** @modelguid {3489BD94-D06C-405C-9254-38C3DA9B3ADA} */
	private String descripcionCancelacionCapital;

	/** @modelguid {3622CA36-D1CE-4102-8327-E331E29C85E2} */
	private BigDecimal porcentajeCancelado;


	/**
	 * @return
	 * @modelguid {8470CB59-E3A6-4EFC-B8D0-CB20AA82E8D0}
	 */
	public String getCodigoCancelacionCapital() {
		return codigoCancelacionCapital;
	}

	/**
	 * @return
	 * @modelguid {A769EECB-9DB3-4C05-9CCD-246641B3B9FC}
	 */
	public String getCodigoMoneda() {
		return codigoMoneda;
	}

	/**
	 * @return
	 * @modelguid {6844DB83-3756-49C5-9401-2EFF3266562F}
	 */
	public String getDescripcionCancelacionCapital() {
		return descripcionCancelacionCapital;
	}

	/**
	 * @return
	 * @modelguid {FE1E4A90-36B2-48D8-BFC8-61B43564141C}
	 */
	public String getDescripcionMoneda() {
		return descripcionMoneda;
	}

	/**
	 * @return
	 * @modelguid {7B6DF180-7356-4FB7-8298-031ACC982825}
	 */
	public BigDecimal getMontoCapital() {
		return montoCapital;
	}

	/**
	 * @return
	 * @modelguid {064B031A-D70D-4E3C-8D51-5CD326D7641B}
	 */
	public BigDecimal getPorcentajeCancelado() {
		return porcentajeCancelado;
	}

	/**
	 * @return
	 * @modelguid {396906A1-095F-4CAF-971B-C0E229C30586}
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * @param string
	 * @modelguid {120DF6AE-9DBA-4F7A-B615-EB290252E92D}
	 */
	public void setCodigoCancelacionCapital(String string) {
		codigoCancelacionCapital = string;
	}

	/**
	 * @param string
	 * @modelguid {E2CA1435-0EA1-4381-9DAA-F5BA7987A519}
	 */
	public void setCodigoMoneda(String string) {
		codigoMoneda = string;
	}

	/**
	 * @param string
	 * @modelguid {BCB1456A-EF5D-4316-88E2-108F845E5EF3}
	 */
	public void setDescripcionCancelacionCapital(String string) {
		descripcionCancelacionCapital = string;
	}

	/**
	 * @param string
	 * @modelguid {375116B5-53BC-463F-A57B-8CCF1B088450}
	 */
	public void setDescripcionMoneda(String string) {
		descripcionMoneda = string;
	}

	/**
	 * @param decimal
	 * @modelguid {53E035C3-BBCB-4376-AD59-F5F560FF0A75}
	 */
	public void setMontoCapital(BigDecimal decimal) {
		montoCapital = decimal;
	}

	/**
	 * @param decimal
	 * @modelguid {1AE4B6EB-261A-4DCB-9F10-797C5BA0A0AB}
	 */
	public void setPorcentajeCancelado(BigDecimal decimal) {
		porcentajeCancelado = decimal;
	}

	/**
	 * @param decimal
	 * @modelguid {38E39111-D646-4ACD-9165-0FB53CBF0FF3}
	 */
	public void setValor(BigDecimal decimal) {
		valor = decimal;
	}

	/**
	 * @return
	 * @modelguid {E4AEDA48-1830-406F-A577-0B240FC5BE4E}
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @param i
	 * @modelguid {531E48C3-AF38-4745-B7E3-2F27B3537E4D}
	 */
	public void setNumero(int i) {
		numero = i;
	}

}

