package gob.pe.sunarp.extranet.publicidad.bean;

import java.util.ArrayList;

public class AsientoRMCBean 
{
	private ActoBean actoBean ;                
	private ArrayList participantes;
	private TituloBean tituloBean;   
	private ArrayList bienes;
	private ArrayList documentosFuncionarios;
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the bienes
	 */
	public ArrayList getBienes() {
		return bienes;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param bienes the bienes to set
	 */
	public void setBienes(ArrayList bienes) {
		this.bienes = bienes;
	}
	public ActoBean getActoBean() {
		return actoBean;
	}
	public void setActoBean(ActoBean actoBean) {
		this.actoBean = actoBean;
	}
	public ArrayList getParticipantes() {
		return participantes;
	}
	public void setParticipantes(ArrayList participantes) {
		this.participantes = participantes;
	}
	public TituloBean getTituloBean() {
		return tituloBean;
	}
	public void setTituloBean(TituloBean tituloBean) {
		this.tituloBean = tituloBean;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the documentosFuncionarios
	 */
	public ArrayList getDocumentosFuncionarios() {
		return documentosFuncionarios;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param documentosFuncionarios the documentosFuncionarios to set
	 */
	public void setDocumentosFuncionarios(ArrayList documentosFuncionarios) {
		this.documentosFuncionarios = documentosFuncionarios;
	}


}
