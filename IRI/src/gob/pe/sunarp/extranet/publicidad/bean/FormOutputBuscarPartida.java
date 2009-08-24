package gob.pe.sunarp.extranet.publicidad.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

import java.util.ArrayList;


public class FormOutputBuscarPartida extends SunarpBean{
	
private int tipoBusqueda;
private ArrayList resultado=null;
private String action="";
private int pagSiguiente=-1;
private int pagAnterior=-1;
private String cantidadRegistros;
//valores registros del x al y
private int ndel=0;
private int nal=0;
private boolean flagEstado=false;
private double tarifa =0;

//inicio:dbravo:18/07/2007

private ArrayList resultadoTituloPendientesRMC ;
private ArrayList resultadoActosRMC ;
private PartidaBean partidaBean;

//fin:dbravo:18/07/2007
//Inicio:jascencio:24/07/07
private int pagSiguientePrimero=-1;
private int pagAnteriorPrimero=-1;
private int pagSiguienteSegundo=-1;
private int pagAnteriorSegundo=-1;
private int delta=0;
private FormOutputBuscarPartida outputInterno=null;
private boolean flagInactivo=false;
//fin:jascencio


	/**
 * @autor dbravo
 * @fecha Jul 18, 2007
 * @CC:SUNARP-REGMOBCON
 * @return the partidaBean
 */
public PartidaBean getPartidaBean() {
	return partidaBean;
}
/**
 * @autor dbravo
 * @fecha Jul 18, 2007
 * @CC:SUNARP-REGMOBCON
 * @param partidaBean the partidaBean to set
 */
public void setPartidaBean(PartidaBean partidaBean) {
	this.partidaBean = partidaBean;
}
/**
 * @autor dbravo
 * @fecha Jul 18, 2007
 * @CC:SUNARP-REGMOBCON
 * @return the resultadoActosRMC
 */
public ArrayList getResultadoActosRMC() {
	return resultadoActosRMC;
}
/**
 * @autor dbravo
 * @fecha Jul 18, 2007
 * @CC:SUNARP-REGMOBCON
 * @param resultadoActosRMC the resultadoActosRMC to set
 */
public void setResultadoActosRMC(ArrayList resultadoActosRMC) {
	this.resultadoActosRMC = resultadoActosRMC;
}
/**
 * @autor dbravo
 * @fecha Jul 18, 2007
 * @CC:SUNARP-REGMOBCON
 * @return the resultadoTituloPendientesRMC
 */
public ArrayList getResultadoTituloPendientesRMC() {
	return resultadoTituloPendientesRMC;
}
/**
 * @autor dbravo
 * @fecha Jul 18, 2007
 * @CC:SUNARP-REGMOBCON
 * @param resultadoTituloPendientesRMC the resultadoTituloPendientesRMC to set
 */
public void setResultadoTituloPendientesRMC(
		ArrayList resultadoTituloPendientesRMC) {
	this.resultadoTituloPendientesRMC = resultadoTituloPendientesRMC;
}
	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the tarifa
	 */
	public double getTarifa() {
		return tarifa;
	}
	/**
	 * @autor dbravo
	 * @fecha Jun 21, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param tarifa the tarifa to set
	 */
	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}
	/**
	 * Gets the resultado
	 * @return Returns a ArrayList
	 */
	public ArrayList getResultado() {
		return resultado;
	}
	/**
	 * Sets the resultado
	 * @param resultado The resultado to set
	 */
	public void setResultado(ArrayList resultado) {
		this.resultado = resultado;
	}

	/**
	 * Gets the action
	 * @return Returns a String
	 */
	public String getAction() {
		return action;
	}
	/**
	 * Sets the action
	 * @param action The action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the pagSiguiente
	 * @return Returns a int
	 */
	public int getPagSiguiente() {
		return pagSiguiente;
	}
	/**
	 * Sets the pagSiguiente
	 * @param pagSiguiente The pagSiguiente to set
	 */
	public void setPagSiguiente(int pagSiguiente) {
		this.pagSiguiente = pagSiguiente;
	}

	/**
	 * Gets the pagAnterior
	 * @return Returns a int
	 */
	public int getPagAnterior() {
		return pagAnterior;
	}
	/**
	 * Sets the pagAnterior
	 * @param pagAnterior The pagAnterior to set
	 */
	public void setPagAnterior(int pagAnterior) {
		this.pagAnterior = pagAnterior;
	}

	/**
	 * Gets the tipoBusqueda
	 * @return Returns a int
	 */
	public int getTipoBusqueda() {
		return tipoBusqueda;
	}
	/**
	 * Sets the tipoBusqueda
	 * @param tipoBusqueda The tipoBusqueda to set
	 */
	public void setTipoBusqueda(int tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * Gets the ndel
	 * @return Returns a int
	 */
	public int getNdel() {
		return ndel;
	}
	/**
	 * Sets the ndel
	 * @param ndel The ndel to set
	 */
	public void setNdel(int ndel) {
		this.ndel = ndel;
	}

	/**
	 * Gets the nal
	 * @return Returns a int
	 */
	public int getNal() {
		return nal;
	}
	/**
	 * Sets the nal
	 * @param nal The nal to set
	 */
	public void setNal(int nal) {
		this.nal = nal;
	}

	/**
	 * Gets the cantidadRegistros
	 * @return Returns a String
	 */
	public String getCantidadRegistros() {
		return cantidadRegistros;
	}
	/**
	 * Sets the cantidadRegistros
	 * @param cantidadRegistros The cantidadRegistros to set
	 */
	public void setCantidadRegistros(String cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}

	/**
	 * Gets the flagEstado
	 * @return Returns a boolean
	 */
	public boolean getFlagEstado() {
		return flagEstado;
	}
	/**
	 * Sets the flagEstado
	 * @param flagEstado The flagEstado to set
	 */
	public void setFlagEstado(boolean flagEstado) {
		this.flagEstado = flagEstado;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the pagAnterioreSegundo
	 */
	public int getPagAnterioreSegundo() {
		return pagAnteriorSegundo;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param pagAnterioreSegundo the pagAnterioreSegundo to set
	 */
	public void setPagAnteriorSegundo(int pagAnteriorSegundo) {
		this.pagAnteriorSegundo = pagAnteriorSegundo;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the pagAnteriorPrimero
	 */
	public int getPagAnteriorPrimero() {
		return pagAnteriorPrimero;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param pagAnteriorPrimero the pagAnteriorPrimero to set
	 */
	public void setPagAnteriorPrimero(int pagAnteriorPrimero) {
		this.pagAnteriorPrimero = pagAnteriorPrimero;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the pagSiguientePrimero
	 */
	public int getPagSiguientePrimero() {
		return pagSiguientePrimero;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param pagSiguientePrimero the pagSiguientePrimero to set
	 */
	public void setPagSiguientePrimero(int pagSiguientePrimero) {
		this.pagSiguientePrimero = pagSiguientePrimero;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the pagSiguienteSegundo
	 */
	public int getPagSiguienteSegundo() {
		return pagSiguienteSegundo;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param pagSiguienteSegundo the pagSiguienteSegundo to set
	 */
	public void setPagSiguienteSegundo(int pagSiguienteSegundo) {
		this.pagSiguienteSegundo = pagSiguienteSegundo;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the delta
	 */
	public int getDelta() {
		return delta;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param delta the delta to set
	 */
	public void setDelta(int delta) {
		this.delta = delta;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 24, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the pagAnteriorSegundo
	 */
	public int getPagAnteriorSegundo() {
		return pagAnteriorSegundo;
	}
	
	/**
	 * @autor jascencio
	 * @fecha Jul 27, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the outputInterno
	 */
	public FormOutputBuscarPartida getOutputInterno() {
		return outputInterno;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 27, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param outputInterno the outputInterno to set
	 */
	public void setOutputInterno(FormOutputBuscarPartida outputInterno) {
		this.outputInterno = outputInterno;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 31, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the flagInactivo
	 */
	public boolean isFlagInactivo() {
		return flagInactivo;
	}
	/**
	 * @autor jascencio
	 * @fecha Jul 31, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param flagInactivo the flagInactivo to set
	 */
	public void setFlagInactivo(boolean flagInactivo) {
		this.flagInactivo = flagInactivo;
	}
	

}

