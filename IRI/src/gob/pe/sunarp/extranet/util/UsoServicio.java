package gob.pe.sunarp.extranet.util;

import com.jcorporate.expresso.core.controller.*;
import com.jcorporate.expresso.core.db.*;
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.publicidad.bean.*;
import gob.pe.sunarp.extranet.util.*;
import java.sql.*;

public class UsoServicio extends SunarpBean{
	//instancia única
	private static UsoServicio usoServicio;
		
	//constructor privado
	private UsoServicio() {
		super();
	}
	
	//getInstance
	public static UsoServicio getInstance() {
		if (usoServicio == null)
			usoServicio = new UsoServicio();
		return usoServicio;
	}	

	public synchronized void registraUsoServicio(
		int     codigoServicio,
		boolean flagInterno,
		String  reg_pub_orig,
		boolean flagIndividual,
		String  currentDateYYYYMMDD,
		double  costo_servicio,
		java.util.Vector  datos)
		throws Throwable {
			
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;			
			
try{		
		String xInterno="0";
		if (flagInterno)
			xInterno="1";
				
		String xIndividual="0";
		if (flagIndividual)
			xIndividual="1";	

conn = pool.getConnection();
conn.setAutoCommit(false);
DBConnection dconn = new DBConnection(conn);
								
		//insertar o actualizar servicio			
		DboUsoServicio dbo1 = new DboUsoServicio(dconn);
		StringBuffer sb = new StringBuffer();
		
		for(int w =0; w < datos.size(); w++		)
		{
			dbo1.clearAll();
			sb.delete(0,sb.length());
			UsoServicioBean bean = (UsoServicioBean) datos.elementAt(w);
								sb.append(DboUsoServicio.CAMPO_SERVICIO_ID);
								sb.append("=").append(codigoServicio);
								sb.append(" and ");
								sb.append(DboUsoServicio.CAMPO_AAAAMMDD);
								sb.append(" = ").append(currentDateYYYYMMDD);
								sb.append(" and ");
								sb.append(DboUsoServicio.CAMPO_COD_REG_PUB);
								sb.append(" = '").append(bean.getRegPubId()).append("'");
								sb.append(" and ");
								sb.append(DboUsoServicio.CAMPO_COD_OFIC_REG);
								sb.append(" = '").append(bean.getOficRegId()).append("'");
								sb.append(" and ");
								sb.append(DboUsoServicio.CAMPO_REG_PUB_ORIG);
								sb.append(" = '").append(reg_pub_orig).append("'");
								sb.append(" and ");
								sb.append(DboUsoServicio.CAMPO_FG_INTERNO);
								sb.append(" = '").append(xInterno).append("'");
								sb.append(" and ");
								sb.append(DboUsoServicio.CAMPO_TIPO_USR);
								sb.append(" = '").append(xIndividual).append("'");
								
								//if (isTrace(this)) System.out.println(sb.toString());
								
								dbo1.setCustomWhereClause(sb.toString());
						
								java.util.ArrayList arr1 = dbo1.searchAndRetrieveList();
						
								if ((arr1 != null) && (arr1.size() > 0)) {
									for (int k = 0; k < arr1.size(); k++) {
										//actualiza registros
										DboUsoServicio dbou = (DboUsoServicio) arr1.get(k);
										dbou.setConnection(dconn);
										int rbm = Integer.parseInt(dbou.getField(DboUsoServicio.CAMPO_RBM));
										int rpn = Integer.parseInt(dbou.getField(DboUsoServicio.CAMPO_RPN));
										int rpi = Integer.parseInt(dbou.getField(DboUsoServicio.CAMPO_RPI));
										int rpj = Integer.parseInt(dbou.getField(DboUsoServicio.CAMPO_RPJ));
										double costo_actual = Double.parseDouble(dbou.getField(DboUsoServicio.CAMPO_VAL_TOTAL));
										if (bean.getAreaRegId().equals("24000") == true) {
											dbou.setFieldsToUpdate(DboUsoServicio.CAMPO_RBM + "|" + DboUsoServicio.CAMPO_VAL_TOTAL);
											rbm++;
											dbou.setField(DboUsoServicio.CAMPO_RBM, rbm);
										}
										if (bean.getAreaRegId().equals("23000") == true) {
											dbou.setFieldsToUpdate(DboUsoServicio.CAMPO_RPN + "|" + DboUsoServicio.CAMPO_VAL_TOTAL);
											rpn++;
											dbou.setField(DboUsoServicio.CAMPO_RPN, rpn);
										}
										if (bean.getAreaRegId().equals("22000") == true) {
											dbou.setFieldsToUpdate(DboUsoServicio.CAMPO_RPJ + "|" + DboUsoServicio.CAMPO_VAL_TOTAL);
											rpj++;
											dbou.setField(DboUsoServicio.CAMPO_RPJ, rpj);
										}
										if (bean.getAreaRegId().equals("21000") == true) {
											dbou.setFieldsToUpdate(DboUsoServicio.CAMPO_RPI + "|" + DboUsoServicio.CAMPO_VAL_TOTAL);
											rpi++;
											dbou.setField(DboUsoServicio.CAMPO_RPI, rpi);
										}
										costo_actual = costo_actual + costo_servicio;
										dbou.setField(DboUsoServicio.CAMPO_VAL_TOTAL, "" + costo_actual);
										dbou.update();
									}
						
								} else {
									//si no hay registro entonces es el primero del dia
									//y se debe insertar
										dbo1.clearAll();
										dbo1.setField(DboUsoServicio.CAMPO_COD_OFIC_REG, bean.getOficRegId());
										dbo1.setField(DboUsoServicio.CAMPO_COD_REG_PUB, bean.getRegPubId());
										dbo1.setField(DboUsoServicio.CAMPO_AAAAMMDD, currentDateYYYYMMDD);
										dbo1.setField(DboUsoServicio.CAMPO_FG_INTERNO, xInterno);
										dbo1.setField(DboUsoServicio.CAMPO_REG_PUB_ORIG, reg_pub_orig);
										dbo1.setField(DboUsoServicio.CAMPO_RPI, "0");
										dbo1.setField(DboUsoServicio.CAMPO_RPJ, "0");
										dbo1.setField(DboUsoServicio.CAMPO_RPN, "0");
										dbo1.setField(DboUsoServicio.CAMPO_RBM, "0");
										if (bean.getAreaRegId().equals("24000") == true)
											dbo1.setField(DboUsoServicio.CAMPO_RBM, "1");
										if (bean.getAreaRegId().equals("23000") == true)
											dbo1.setField(DboUsoServicio.CAMPO_RPN, "1");
										if (bean.getAreaRegId().equals("22000") == true)
											dbo1.setField(DboUsoServicio.CAMPO_RPJ, "1");
										if (bean.getAreaRegId().equals("21000") == true)
											dbo1.setField(DboUsoServicio.CAMPO_RPI, "1");
										dbo1.setField(DboUsoServicio.CAMPO_SERVICIO_ID, codigoServicio);
										dbo1.setField(DboUsoServicio.CAMPO_TIPO_USR, xIndividual);
										dbo1.setField(DboUsoServicio.CAMPO_VAL_TOTAL, "" + costo_servicio);
										try{
										dbo1.add();
										}
										catch (Throwable t)
										{
											StringBuffer sbx = new StringBuffer();
											sbx.append("__CheckInsertUsoServicio");
											sbx.append(bean.getOficRegId()).append("|");
											sbx.append(bean.getRegPubId()).append("*");
											sbx.append(currentDateYYYYMMDD).append("@");
											sbx.append(xInterno).append("%");
											sbx.append(reg_pub_orig).append("?");
											sbx.append(codigoServicio).append("|");
											sbx.append(reg_pub_orig).append("*");
											sbx.append(xIndividual).append("%");
											if (Loggy.isTrace(this)) System.out.println(sbx.toString());
											t.printStackTrace();
										}
								}	
		} //for(int w =0; w < datos.size(); w++		)	
		conn.commit();
}//try
catch (Throwable t)
{
	conn.rollback();
	throw t;
}
finally
{
	pool.release(conn);
}
		
		
	}//fin metodo
	
	
}//fin clase

