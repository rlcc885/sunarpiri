/*
 * Created on Jan 12, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.dbobj.DboSeqRegisPub;

import java.math.BigInteger;

import com.jcorporate.expresso.core.db.DBConnection;

/**
 * @author ifigueroa
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SecuenciaZona {
	public static synchronized String getSecuenciaZona(String zona, DBConnection dcon) throws Throwable{
		DboSeqRegisPub dboSeq= new DboSeqRegisPub();
		dboSeq.setConnection(dcon);
		dboSeq.setField(DboSeqRegisPub.CAMPO_REG_PUB_ID,zona);
		dboSeq.find();
		long secuencia= dboSeq.getFieldLong(DboSeqRegisPub.CAMPO_SEQ_VALOR);
		dboSeq.setField(DboSeqRegisPub.CAMPO_SEQ_VALOR,String.valueOf(secuencia+1));
		dboSeq.setFieldsToUpdate(DboSeqRegisPub.CAMPO_SEQ_VALOR);
		dboSeq.update();
		return String.valueOf(secuencia);
	}

}
