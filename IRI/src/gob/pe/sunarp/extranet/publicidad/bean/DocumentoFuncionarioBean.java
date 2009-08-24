package gob.pe.sunarp.extranet.publicidad.bean;

import java.sql.Date;

public class DocumentoFuncionarioBean 
{
	private String documento       = "&nbsp;";
    private String tipoFuncionario = "&nbsp;";
    private String funcionario     = "&nbsp;";
    private Date   fecha;
    
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @autor dbravo
	 * @fecha Jul 18, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	public String getTipoFuncionario() {
		return tipoFuncionario;
	}
	public void setTipoFuncionario(String tipoFuncionario) {
		this.tipoFuncionario = tipoFuncionario;
	}

}
