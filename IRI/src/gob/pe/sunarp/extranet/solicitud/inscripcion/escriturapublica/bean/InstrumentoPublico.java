package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

/** @modelguid {E623462E-DB21-423D-9F71-6259879F5E1F} */
public class InstrumentoPublico extends SunarpBean{
	
	/** @modelguid {867773E7-0F07-44C7-A6A9-009DE3C70770} */
	private Long secuencia;

	/** @modelguid {729756DB-FBBE-47D5-972C-DD20E1C6228B} */
	private String codigoTipoInstrumento;

	/** @modelguid {892808E8-F816-4856-895D-B0F2F0B6A1F6} */
	private String descripcionTipoInstrumento;

	/** @modelguid {241EA1FA-5510-4F8B-9D39-BA557607521A} */
	private String lugar;

	/** @modelguid {6620B609-2E6E-4343-9BDE-EA7C17C5123C} */
	private String fecha;

	/** @modelguid {DA3EFDCC-7C6C-402D-8698-3C583F919B9F} */
	private String otros;


	/**
	 * @return
	 * @modelguid {539C9E93-C955-4B1F-941F-F69D801A438B}
	 */
	public String getCodigoTipoInstrumento() {
		return codigoTipoInstrumento;
	}

	/**
	 * @return
	 * @modelguid {A22A9B60-F795-4623-8D2A-23F31F5D92C7}
	 */
	public String getDescripcionTipoInstrumento() {
		return descripcionTipoInstrumento;
	}

	/**
	 * @return
	 * @modelguid {150892F7-BEE0-47CA-9E08-D7702A73FF09}
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @return
	 * @modelguid {206E5055-E122-4175-A2FD-9A66E04C9766}
	 */
	public String getLugar() {
		return lugar;
	}

	/**
	 * @return
	 * @modelguid {778A7A8A-5060-4345-BF45-1D3C5CF0A6C1}
	 */
	public Long getSecuencia() {
		return secuencia;
	}

	/**
	 * @param string
	 * @modelguid {F0F2B3B9-CBC6-4496-A061-017438DBDAE3}
	 */
	public void setCodigoTipoInstrumento(String string) {
		codigoTipoInstrumento = string;
	}

	/**
	 * @param string
	 * @modelguid {A66738DB-E613-49F7-914E-B5129FB76BE7}
	 */
	public void setDescripcionTipoInstrumento(String string) {
		descripcionTipoInstrumento = string;
	}

	/**
	 * @param string
	 * @modelguid {E281DF9B-0874-4518-8FA0-FFBB57319C00}
	 */
	public void setFecha(String string) {
		fecha = string;
	}

	/**
	 * @param string
	 * @modelguid {666260AD-9D90-4185-BB47-59DC92AA29E2}
	 */
	public void setLugar(String string) {
		lugar = string;
	}

	/**
	 * @param long1
	 * @modelguid {C71BD93A-1936-4CF2-8925-18AE318F8D04}
	 */
	public void setSecuencia(Long long1) {
		secuencia = long1;
	}

	/**
	 * @return
	 * @modelguid {550E7949-622B-432D-A5AC-A14DBA30ACDA}
	 */
	public String getOtros() {
		return otros;
	}

	/**
	 * @param string
	 * @modelguid {C94B8329-7852-498A-9BB1-42BA1C2575DC}
	 */
	public void setOtros(String string) {
		otros = string;
	}

}

