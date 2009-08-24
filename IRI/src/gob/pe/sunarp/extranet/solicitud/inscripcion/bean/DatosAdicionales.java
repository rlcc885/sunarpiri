package gob.pe.sunarp.extranet.solicitud.inscripcion.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/**
 * 
 * @modelguid {EFDB66E3-694F-4030-B78E-C364E26BA4FF}
 */
public class DatosAdicionales extends SunarpBean{
	
	/** @modelguid {E5A58939-F46E-4DB6-BA24-86414A7A7C69} */
	private String monto;

	/** @modelguid {D7DE8E53-2FD8-42A2-8D26-0564C5EB185F} */
	private String movimientoId;

	/** @modelguid {FFE7C18C-B952-4ED1-A737-DFCDAE5CFC7D} */
	private String fecHor;

	/** @modelguid {3192F4B3-7ADA-47CC-8BEF-FA31CF0ADF88} */
	private String lineaPrePagoId;

	/** @modelguid {F64CCEFE-119F-4B91-A238-10D72CC6BEBB} */
	private String cuentaId;

	/** @modelguid {88BF2443-484C-4F30-937A-D46D2E940E5B} */
	private String consumoId;


	/**
	 * @return
	 * @modelguid {2E9D8A92-0EF3-48F3-B71F-9B78891657DE}
	 */
	public String getConsumoId() {
		return consumoId;
	}

	/**
	 * @return
	 * @modelguid {02585C26-4870-42AA-BBA2-82CF5B176A77}
	 */
	public String getCuentaId() {
		return cuentaId;
	}

	/**
	 * @return
	 * @modelguid {58FC033F-6ACD-47F3-BFE0-2AF737E39FA6}
	 */
	public String getFecHor() {
		return fecHor;
	}

	/**
	 * @return
	 * @modelguid {13225124-2CFD-4EAC-868E-33799AD2C0B5}
	 */
	public String getLineaPrePagoId() {
		return lineaPrePagoId;
	}

	/**
	 * @return
	 * @modelguid {DDD50060-21BC-4E81-9F2A-2ED7BE467867}
	 */
	public String getMonto() {
		return monto;
	}

	/**
	 * @return
	 * @modelguid {BAAF9E81-B020-4BC3-AFE4-A973D56EE013}
	 */
	public String getMovimientoId() {
		return movimientoId;
	}

	/**
	 * @param string
	 * @modelguid {3883A82F-BD44-4960-A83E-926D1B6367E2}
	 */
	public void setConsumoId(String string) {
		consumoId = string;
	}

	/**
	 * @param string
	 * @modelguid {42B6585A-17AA-4C5D-9B2C-D64D2B1C317D}
	 */
	public void setCuentaId(String string) {
		cuentaId = string;
	}

	/**
	 * @param string
	 * @modelguid {0B0F09EC-1623-4753-9641-A5469FCD9E6A}
	 */
	public void setFecHor(String string) {
		fecHor = string;
	}

	/**
	 * @param string
	 * @modelguid {8BE2EDEC-CC74-4762-B73E-3FBE14D234FC}
	 */
	public void setLineaPrePagoId(String string) {
		lineaPrePagoId = string;
	}

	/**
	 * @param string
	 * @modelguid {1ED64731-0C89-43A2-BB5F-08B92201E4D5}
	 */
	public void setMonto(String string) {
		monto = string;
	}

	/**
	 * @param string
	 * @modelguid {D6933BBA-74D5-41C8-A60A-F48C076174D0}
	 */
	public void setMovimientoId(String string) {
		movimientoId = string;
	}

}

