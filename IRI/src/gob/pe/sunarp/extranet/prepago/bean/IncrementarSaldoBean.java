package gob.pe.sunarp.extranet.prepago.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import com.jcorporate.expresso.core.controller.Output;

// Creado por CCS - 15/06/06

public class IncrementarSaldoBean extends SunarpBean{


	private Output medioId = null;
	private Output monto = null;
	private Output fecha = null;
	//private Output glosa = null;
	
	public Output getMedioId() {
		return medioId;
	}

	public void setMedioId(Output medioId) {
		this.medioId = medioId;
	}

	public Output getMonto() {
		return monto;
	}

	public void setMonto(Output monto) {
		this.monto = monto;
	}

	public Output getFecha() {
		return fecha;
	}

	public void setFecha(Output fecha) {
		this.fecha = fecha;
	}

	/*public Output getGlosa() {
		return glosa;
	}

	public void setGlosa(Output glosa) {
		this.glosa = glosa;
	}*/
}

