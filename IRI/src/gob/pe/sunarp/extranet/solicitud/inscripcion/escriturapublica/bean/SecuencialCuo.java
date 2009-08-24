package gob.pe.sunarp.extranet.solicitud.inscripcion.escriturapublica.bean;

import gob.pe.sunarp.extranet.common.SunarpBean;

public class SecuencialCuo extends SunarpBean{
	

public static long cuo = 0;




	/**
	 * Constructor for SecuencialCuo
	 */
	public SecuencialCuo() {
		super();
	}
	
	public static synchronized String obtieneSecuenciaCuo(){
	
	long resp1 = 0;
	int sizeResp1 = 0;
	int dif = 0;
	String cad = null;
	
	resp1 = cuo++;
			
			//FORMAMOS LA CADENA
			sizeResp1 = (String.valueOf(resp1)).length();
			cad = String.valueOf(resp1);
			
			if ( sizeResp1 < 10 ) {
				dif = 10 - sizeResp1;
				for (int i=0; i< dif; i++) {
					cad = "0" + cad;
				}
			}
	
			if ( sizeResp1 > 10 ) {
				cuo = 1 ;
			}
	return cad;
	}

}

