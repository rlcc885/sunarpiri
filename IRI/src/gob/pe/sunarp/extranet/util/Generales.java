package gob.pe.sunarp.extranet.util;

import com.jcorporate.expresso.core.controller.ControllerRequest;
import com.jcorporate.expresso.core.db.DBConnection;
import com.jcorporate.expresso.core.db.DBException;
import com.jcorporate.expresso.core.dbobj.MultiDBObject;

import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.Loggy;
import gob.pe.sunarp.extranet.publicidad.bean.DetalleTituloBean;
import java.util.Iterator;
import gob.pe.sunarp.extranet.pool.*;
import java.sql.*;
import java.io.*;
import gob.pe.sunarp.extranet.framework.Loggy;

public class Generales extends SunarpBean{

	private static void evalua(String noNulo) throws DBException{
		if(noNulo == null)
			throw new DBException("Valor no debe ser nulo, es posible que se muestren inconsistencia de datos");
	}

	public static String getNombreEmpresaXCuentaID(String cuentaID, DBConnection conn) throws DBException{
		evalua(cuentaID);
		
		DboOrgCtas orgctas = new DboOrgCtas();
		orgctas.setConnection(conn);
		
		orgctas.clearAll();
		orgctas.setFieldsToRetrieve(DboOrgCtas.CAMPO_PE_JURI_ID);
		orgctas.setField(DboOrgCtas.CAMPO_CUENTA_ID, cuentaID);
		if(orgctas.find())
		{
			return getRazonSocial(orgctas.getField(DboOrgCtas.CAMPO_PE_JURI_ID), conn);
		}
		else
		{
			throw new DBException("No se encontro registro pejuriid para orgctas");
		}
	}

	public static String getRazonSocial(String peJuriId, DBConnection conn) throws DBException{
		evalua(peJuriId);
		
		DboPeJuri pejuri = new DboPeJuri();
		pejuri.setConnection(conn);
		
		pejuri.clearAll();
		pejuri.setFieldsToRetrieve(DboPeJuri.CAMPO_RAZ_SOC);
		pejuri.setField(DboPeJuri.CAMPO_PE_JURI_ID, peJuriId);
		
		if(pejuri.find())
			return pejuri.getField(DboPeJuri.CAMPO_RAZ_SOC);
		else
			return null;
	}
	
	public static String getUserID(String cuentaID, DBConnection conn) throws DBException{
		evalua(cuentaID);
		
		DboCuenta cuenta = new DboCuenta();
		cuenta.setConnection(conn);
		
		cuenta.clearAll();
		cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_USR_ID);
		cuenta.setField(DboCuenta.CAMPO_CUENTA_ID, cuentaID);
		
		if(cuenta.find())
			return cuenta.getField(DboCuenta.CAMPO_USR_ID);
		else
			return null;
	}

	public static String getCuentaID(String userID, DBConnection conn) throws DBException{
		evalua(userID);
		
		DboCuenta cuenta = new DboCuenta();
		cuenta.setConnection(conn);
		
		cuenta.clearAll();
		cuenta.setFieldsToRetrieve(DboCuenta.CAMPO_CUENTA_ID);
		cuenta.setField(DboCuenta.CAMPO_USR_ID, userID);
		
		if(cuenta.find())
			return cuenta.getField(DboCuenta.CAMPO_CUENTA_ID);
		else
			return null;
	}

	public static String getNombreOficina(String regPubId, String oficRegId, DBConnection myConn) throws DBException, Throwable{
		evalua(regPubId);
		evalua(oficRegId);
		
		DboOficRegistral oficina = new DboOficRegistral();
		oficina.setConnection(myConn);

		oficina.clearAll();
		oficina.setFieldsToRetrieve(DboOficRegistral.CAMPO_NOMBRE);
		oficina.setField(DboOficRegistral.CAMPO_REG_PUB_ID, regPubId);
		oficina.setField(DboOficRegistral.CAMPO_OFIC_REG_ID, oficRegId);
		if(oficina.find())
			return oficina.getField(DboOficRegistral.CAMPO_NOMBRE);
		else
			return null;
	}

	public static String getNombreSede(String regPubId, DBConnection myConn) throws DBException, Throwable{
		evalua(regPubId);
		
		DboRegisPublico sede = new DboRegisPublico();
		sede.setConnection(myConn);

		sede.clearAll();
		sede.setFieldsToRetrieve(DboRegisPublico.CAMPO_NOMBRE);
		sede.setField(DboRegisPublico.CAMPO_REG_PUB_ID, regPubId);
		if(sede.find())
			return sede.getField(DboRegisPublico.CAMPO_NOMBRE);
		else
			return null;
	}

	public static String getNombreServicio(String servicioID, DBConnection myConn) throws DBException, Throwable{
		evalua(servicioID);
		
		DboTmServicio servicio = new DboTmServicio();
		servicio.setConnection(myConn);

		servicio.clearAll();
		servicio.setFieldsToRetrieve(DboTmServicio.CAMPO_NOMBRE);
		servicio.setField(DboTmServicio.CAMPO_SERVICIO_ID, servicioID);
		if(servicio.find())
			return servicio.getField(DboRegisPublico.CAMPO_NOMBRE);
		else
			return null;
	}

	public static boolean perteneceJurisdiccion(String cuentaID, String jurisID, DBConnection conn) throws DBException{
		evalua(cuentaID);
		
		DboCuentaJuris cuentaJuris = new DboCuentaJuris();
		cuentaJuris.setConnection(conn);
		
		cuentaJuris.clearAll();
		cuentaJuris.setFieldsToRetrieve(DboCuentaJuris.CAMPO_CUENTA_ID);
		cuentaJuris.setField(DboCuentaJuris.CAMPO_CUENTA_ID, cuentaID);
		
		if(jurisID != null)
			cuentaJuris.setField(DboCuentaJuris.CAMPO_JURIS_ID, jurisID);
		
		return cuentaJuris.find();
	}

	public static boolean perteneceOrganizacion(String cuentaID, String peJuriID, DBConnection conn) throws DBException{
		// hphp: evalua no es nulo
		evalua(cuentaID);
		
		DboOrgCtas orgctas = new DboOrgCtas();
		orgctas.setConnection(conn);
		
		orgctas.clearAll();
		orgctas.setFieldsToRetrieve(DboOrgCtas.CAMPO_CUENTA_ID);
		orgctas.setField(DboOrgCtas.CAMPO_CUENTA_ID, cuentaID);
		//if (Loggy.isTrace(this)) System.out.println("cuentaID = " + cuentaID + " - peJuriID = " + peJuriID);
		if(peJuriID != null)
			orgctas.setField(DboOrgCtas.CAMPO_PE_JURI_ID, peJuriID);
		
		return orgctas.find();
	}

	public static String formateoUrlEsquela(String refNumTitu, String tipoEsquela, String areaRegId, boolean esPrimerParametro) throws Exception{
		String param = esPrimerParametro?"?":"&";
		
		StringBuffer stb = new StringBuffer(param).append("refnumtitu=").append(refNumTitu);
		stb.append("&tipoesquela=").append(tipoEsquela);
		stb.append("&arearegid=").append(areaRegId);
		return stb.toString();
	}

	public static java.sql.Blob getEsquela(String refNumTitu, String tipoEsquela, String areaRegId, DBConnection conn) throws DBException{
		
		java.text.DecimalFormat format = new java.text.DecimalFormat("00000000");
		
		DboEsquela esquela = new DboEsquela(conn);
		esquela.setFieldsToRetrieve(DboEsquela.CAMPO_NU_ESQUELA);
		esquela.setField(DboEsquela.CAMPO_REFNUM_TITU, refNumTitu);
		esquela.setField(DboEsquela.CAMPO_TIPO_ESQ, tipoEsquela);
		esquela.setField(DboEsquela.CAMPO_AREA_REG_ID, areaRegId);

		String numEsquela = format.format(esquela.max(DboEsquela.CAMPO_NU_ESQUELA));
		
		StringBuffer stb = new StringBuffer("SELECT DATA FROM ESQUELA WHERE ");
		stb.append(DboEsquela.CAMPO_REFNUM_TITU).append(" = ").append(Long.parseLong(refNumTitu)).append(" AND ");
		stb.append(DboEsquela.CAMPO_TIPO_ESQ).append(" = '").append(tipoEsquela).append("' AND ");
		stb.append(DboEsquela.CAMPO_AREA_REG_ID).append(" = '").append(areaRegId).append("' AND ");
		stb.append(DboEsquela.CAMPO_NU_ESQUELA).append(" = '").append(numEsquela).append("'");

		String sql = stb.toString();
		
		//if (Loggy.isTrace(this)) System.out.println("QUERY para búsqueda de imagen de Esquela: " + sql);
		
		java.sql.Connection con = null;
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		oracle.jdbc.driver.OracleResultSet ors = null;
		java.sql.Blob blob = null;
		
		try{
			con = conn.getMyConnection();
			stmt = conn.createStatement();
			ors = (oracle.jdbc.driver.OracleResultSet) stmt.executeQuery(sql);
			
			//blob = ors.getBLOB(1);
			if(ors.next()){
				blob = ors.getBlob(1);
				//if (Loggy.isTrace(this)) System.out.println("Se recupero imagen de Esquela.");
			}else
				//if (Loggy.isTrace(this)) System.out.println("No se tiene registrada una imagen de Esquela en la tabla ESQUELA.");
			
			ors.close();
			stmt.close();
		}catch(Exception ex){
			//if (Loggy.isTrace(this)) System.out.println("ERROR: No se pudo recuperar imagen de Esquela.");
			try{
				if(ors != null)	ors.close();
				if(stmt != null)	stmt.close();
			}catch(java.sql.SQLException sqle){
			}				
			throw new DBException(ex.getMessage());
		}

		return blob;
	}

	public static DetalleTituloBean esTituloPresentado(String numTitulo, String anoTitulo, String regPubId, String oficRegId, DBConnection dconn) throws DBException, Exception
	{
		evalua(numTitulo);
		evalua(anoTitulo);
		evalua(oficRegId);
		evalua(regPubId);
		
		DetalleTituloBean detalle = null;
		
		MultiDBObject multi = new MultiDBObject(dconn);
		multi.setDistinct(true);
				
		multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTitulo", "ti");
		multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboDetalleTitulo", "dt");
		multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboTmEstadoTitulo", "et");
		//Inicio:mgarate:11/07/2007
		double num1= Double.parseDouble(numTitulo);
		double num2= Double.parseDouble(Constantes.NUM_TITULO_RMC);
		
		if(!(num1 > num2))
		{
			multi.addDBObj("gob.pe.sunarp.extranet.dbobj.DboOficRegistral", "ofr");
		}
		
		//Fin:mgarate
			
		multi.setForeignKey("ti", DboTitulo.CAMPO_REFNUM_TITU, "dt", DboDetalleTitulo.CAMPO_REFNUM_TITU);
		multi.setForeignKey("dt", DboDetalleTitulo.CAMPO_ESTADO_TITULO_ID, "et", DboTmEstadoTitulo.CAMPO_ESTADO_TITULO_ID);
		//Inicio:mgarate:11/07/2007
		if(!(Double.parseDouble(numTitulo) > Double.parseDouble(Constantes.NUM_TITULO_RMC)))
		{
			multi.setForeignKey("ti", DboTitulo.CAMPO_OFIC_REG_ID, "ofr", DboOficRegistral.CAMPO_OFIC_REG_ID);
			multi.setForeignKey("ti", DboTitulo.CAMPO_REG_PUB_ID, "ofr", DboOficRegistral.CAMPO_REG_PUB_ID);
			multi.setField("ti", DboTitulo.CAMPO_REG_PUB_ID, regPubId);
			multi.setField("ti", DboTitulo.CAMPO_OFIC_REG_ID, oficRegId);
		}
		// Fin:mgarate:11/07/2007		
		
		multi.setField("ti", DboTitulo.CAMPO_ANO_TITU, anoTitulo);
		multi.setField("ti", DboTitulo.CAMPO_NUM_TITU, numTitulo);
		multi.setField("et", DboTmEstadoTitulo.CAMPO_ESTADO_TITULO_ID, "10");
		multi.setField("dt", DboDetalleTitulo.CAMPO_FG_ACTIVO, "1");
		
		StringBuffer ordenar = new StringBuffer(DboTitulo.CAMPO_NUM_TITU);
		ordenar.append("|").append(DboTitulo.CAMPO_ANO_TITU);
		
		MultiDBObject multis = new MultiDBObject(dconn);		
		Iterator i = multi.searchAndRetrieve(ordenar.toString()).iterator();

		if(i.hasNext())
		{
			multis = (MultiDBObject) i.next();
			detalle = new DetalleTituloBean();

			detalle.setNum_titulo(multis.getField("ti", DboTitulo.CAMPO_NUM_TITU));
			detalle.setAno_titulo(multis.getField("ti", DboTitulo.CAMPO_ANO_TITU));
			detalle.setAreaRegistral(multis.getField("ti", DboTitulo.CAMPO_AREA_REG_ID));
			if(!(num1 > num2))
			{
				detalle.setOficina(multis.getField("ofr", DboOficRegistral.CAMPO_NOMBRE));
			}
			
			detalle.setMensaje(multis.getField("et", DboTmEstadoTitulo.CAMPO_MENSAJE));
							
			String auxiliar = multis.getField("ti", DboTitulo.CAMPO_TS_PRESENT);
			detalle.setFecha(auxiliar.substring(0,19));
							
			String estId = multis.getField("et", DboTmEstadoTitulo.CAMPO_ESTADO_TITULO_ID);
			String est = multis.getField("et", DboTmEstadoTitulo.CAMPO_ESTADO);

			if(estId.equalsIgnoreCase("10"))
				detalle.setResultado(est);
		}
		return detalle;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	




/*		

ESTE METODO SE USA SOLAMENTE PARA BUSCAR TITULOS QUE TIENEN
MAS DE UNA PAGINA

	public static void busca(DBConnection conn) 
	{
		java.text.DecimalFormat format = new java.text.DecimalFormat("00000000");
		
		StringBuffer stb = new StringBuffer("SELECT DATA, refnum_titu FROM ESQUELA where data is not null");

		String sql = stb.toString();
		
		java.sql.Connection con = null;
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		oracle.jdbc.driver.OracleResultSet ors = null;
		java.sql.Blob blob = null;
		
		try{
			con = conn.getMyConnection();
			stmt = conn.createStatement();
			ors = (oracle.jdbc.driver.OracleResultSet) stmt.executeQuery(sql);
			
			
			int conta = 0;
			int contatotal=0;
			
			while (ors.next())
				{
					conta++;
					contatotal++;
					if (conta > 1500)
						{
							conta=0;
							if (isTrace(this)) System.out.println("voy en " + contatotal);
						}
						
				Tiff tiff =null;
				int ref=0;
				try{
				blob = ors.getBlob(1);
				ref = ors.getInt(2);
				
				tiff = new Tiff();
				tiff.readInputStream(blob.getBinaryStream()); 
				}
				catch (Throwable ajo)
					{
						//ajo.printStackTrace();
					}
				
				if (tiff!=null)
					{
					int pp = tiff.getPageCount();
					if (pp>1)
						if (isTrace(this)) System.out.println("tiene mas de una pagina " + ref);
					}
				}//while
				
				
				
				
			ors.close();
			stmt.close();
		}
		catch(Throwable tt)
		{
			tt.printStackTrace();
		}
	}
*/	












/*
	public static void lee() 
	{
		
		try{
			
		File file = new File("//tmp//t1.tif");	
		//File file = new File("D:\\t1.tif");	
		
		long lo = file.length();

		FileInputStream is = new FileInputStream(file);

		int tamano = (int) lo;
		
		byte[] arrb = new byte[tamano];
		
		int rr = is.read(arrb,0,tamano);
		
		//if (Loggy.isTrace(this)) System.out.println("rr_tamano="+rr+"_"+tamano);

		Tiff tiff = new Tiff();
		
		//if (Loggy.isTrace(this)) System.out.println("lectura");
		
		tiff.read(arrb);
		
		//if (Loggy.isTrace(this)) System.out.println("leyo!");
		
		is.close();

		}
		catch(Throwable tt)
		{
			tt.printStackTrace();
		}
	}
	*/
}//fin clase
