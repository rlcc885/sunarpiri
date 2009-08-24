package gob.pe.sunarp.extranet.util;
import gob.pe.sunarp.extranet.common.SunarpBean;

/*
Clase singleton para guardar las direcciones de visa

Revisar además: archivo webappResources.xml
*/

public class DirVisa extends SunarpBean{
	
	//instancia única
	private static DirVisa dirvisa;
		
	//constructor privado
	private DirVisa() {
		super();
	}
	
	//getInstance
	public static DirVisa getInstance() {
		if (dirvisa == null)
			dirvisa = new DirVisa();
		return dirvisa;
	}	

private String dir1Desa = "";
private String dir2Desa = "";
private String dir1Prod = "";
private String dir2Prod = "";
private String dirDesa = "";

//*************SETTERS Y GETTERS******************

	/**
	 * Gets the dir1Desa
	 * @return Returns a String
	 */
	public String getDir1Desa() {
		return dir1Desa;
	}
	/**
	 * Sets the dir1Desa
	 * @param dir1Desa The dir1Desa to set
	 */
	public void setDir1Desa(String dir1Desa) {
		this.dir1Desa = dir1Desa;
	}

	/**
	 * Gets the dir2Desa
	 * @return Returns a String
	 */
	public String getDir2Desa() {
		return dir2Desa;
	}
	/**
	 * Sets the dir2Desa
	 * @param dir2Desa The dir2Desa to set
	 */
	public void setDir2Desa(String dir2Desa) {
		this.dir2Desa = dir2Desa;
	}

	/**
	 * Gets the dir1Prod
	 * @return Returns a String
	 */
	public String getDir1Prod() {
		return dir1Prod;
	}
	/**
	 * Sets the dir1Prod
	 * @param dir1Prod The dir1Prod to set
	 */
	public void setDir1Prod(String dir1Prod) {
		this.dir1Prod = dir1Prod;
	}

	/**
	 * Gets the dir2Prod
	 * @return Returns a String
	 */
	public String getDir2Prod() {
		return dir2Prod;
	}
	/**
	 * Sets the dir2Prod
	 * @param dir2Prod The dir2Prod to set
	 */
	public void setDir2Prod(String dir2Prod) {
		this.dir2Prod = dir2Prod;
	}
	
	/**
	 * Gets the dirDesa
	 * @return Returns a String
	 */
	public String getDirDesa() 
	{
		return dirDesa;
	}
	/**
	 * Sets the dirDesa
	 * @param dirDesa The dirDesa to set
	 */
	public void setDirDesa(String dirDesa) 
	{
		this.dirDesa = dirDesa;
	}


}

