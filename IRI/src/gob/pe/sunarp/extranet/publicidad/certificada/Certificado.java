package gob.pe.sunarp.extranet.publicidad.certificada;

import gob.pe.sunarp.extranet.common.Errors;
import gob.pe.sunarp.extranet.common.SunarpBean;
import gob.pe.sunarp.extranet.common.utils.JDBC;
import gob.pe.sunarp.extranet.dbobj.DboCertificado;
import gob.pe.sunarp.extranet.framework.CustomException;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.publicidad.sql.impl.SQLImpl;
import gob.pe.sunarp.extranet.util.Constantes;
import gob.pe.sunarp.extranet.util.FechaUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;


import com.jcorporate.expresso.core.db.DBConnection;

public class Certificado extends SunarpBean{
	
private String certificado_id;	
private String solicitud_id;	
private String objeto_sol_id;	
private String tpo_certificado;	
private String detalle_certificado;	
private String estado_certificado;// I, N, P	
private String comentario;	
private String constancia;	
private String reg_pub_id;	
private String zona_registral;	
private String ofic_reg_id_verif;	
private String ofic_reg_id_exp;	
private String ts_verificacion;	
private String ts_expedicion;	
private String ts_crea;	
private String ts_modi;	
private String usr_crea;	
private String usr_modi;
private String tipo_objeto;	
private String objeto_sol_PN;	
private String objeto_sol_PJ;
private String total;	
private String password;
private String hora;
private String minutos;
private String dia;
private String mes;
private String anno;
private String accion;
private String diaEmi;
private String mesEmi;
private String annoEmi;

//Inicio: jascencio: 29/05/2007
//SUNARP-REGMOBCOM: se esta agregando estas variables y sus metodos get y set. 
private String titulo;
private StringBuffer constancia2;

//Fin: jascencio: 29/05/2007
//Inicio:mgarate:05/06/2007
private String crit_busq;
private String certificado_busq_id;
private String url_busq;
//Fin:mgarate:05/06/2007	

//inicio:dbravo:15/08/2007
private String flagPagoCrem;
private String paginasCrem;
private BigDecimal pagoCrem;
private Date fechaCreacion;
//fin:dbravo:15/08/2007

private Connection conn;

public Certificado(){
}

public Certificado (String sol_id, Connection conn, String estado) throws Throwable{
	setConn(conn);
	if(estado==Constantes.ESTADO_SOL_POR_VERIFICAR)
		recuperarDBcertificadoPorVerificar(sol_id);
	else if(estado==Constantes.ESTADO_SOL_POR_EXPEDIR)
		recuperarDBcertificadoPorExpedir(sol_id);
}

public void guardarCertificadoVerificado(String userId) throws Throwable{
	    validaPassword(userId);
	    grabarDBcertificadoVerificado();  		
}

public void guardarCertificadoVerificado(String userId,String password) throws Throwable{
    validaPassword(userId,password);
    grabarDBcertificadoVerificado();  		
}

public void validaPasswordCertExpedido(String userId) throws Throwable{
		validaPassword(userId);
		//actualizarDBCertificadoExpedido();	
}	 

public void actualizarEstadoCertExpedido() throws Throwable{
		actualizarDBCertificadoExpedido();
}

protected void recuperarDBcertificadoPorVerificar(String sol_id) throws Throwable{
	
	StringBuffer qcertificado = new StringBuffer();
	DecimalFormat df = new DecimalFormat("0.00");
	
	//Query muestra los datos 
	   qcertificado.append(" SELECT /*+ ordered*/ c.NOMBRE as DETALLE_CERTIFICADO, d.NOMBRE as ZONA_REGISTRAL, ") 
	    .append(" e.NOMBRE as OFIC_REG_OBJETO_SOL, e2.NOMBRE as OFIC_REG_DESTINATARIO, ") 
 	    .append(" b.TPO_PERS, b.APE_PAT, b.APE_MAT, b.NOMBRES, b.RAZ_SOC, a.SUBTOTAL, b.OBJETO_SOL_ID, b.REG_PUB_ID, cert.TS_VERIFICACION, cert.CONSTANCIA, cert.CONSTANCIA2, ") 
 	    // Inicio:mgarate:05/06/2007
 	    .append(" b.CERTIFICADO_ID, B.CRIT_BUSQ, B.URL_BUSQ, ")
 	    // Fin:mgarate:05/06/2007
 	    
 	    //inicio:dbravo:15/08/2007
 	    .append(" cert.FLAG_PAGO_CREM, ")
 	    .append(" cert.PAGINAS_CREM, ")
 	    .append(" cert.PAGO_CREM ")
 	    //fin:dbravo:15/08/2007
 	    
 	    .append(" FROM SOLICITUD a, OBJETO_SOLICITUD b, TM_CERTIFICADOS c, REGIS_PUBLICO d, OFIC_REGISTRAL e, OFIC_REGISTRAL e2, DESTINATARIO f, USER1.CERTIFICADO cert ")
	    .append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID")		
	    .append(" AND a.SOLICITUD_ID = cert.SOLICITUD_ID(+)")
	    .append(" AND a.SOLICITUD_ID = f.SOLICITUD_ID")
	    .append(" AND b.CERTIFICADO_ID = c.CERTIFICADO_ID")
	    .append(" AND b.REG_PUB_ID = d.REG_PUB_ID")								
	    .append(" AND b.REG_PUB_ID = e.REG_PUB_ID")								
	    .append(" AND b.OFIC_REG_ID = e.OFIC_REG_ID")								
	    .append(" AND f.REG_PUB_ID = e2.REG_PUB_ID")								
	    .append(" AND f.OFIC_REG_ID = e2.OFIC_REG_ID")									    
	    .append(" AND a.SOLICITUD_ID =")									    	    
	    .append(sol_id);
		
	//if (isTrace(this))
		//System.out.println("Solicitud QUERY ---> "+qcertificado.toString());
	Statement stmt = null;
	ResultSet rset = null;
	
	try{
		stmt = conn.createStatement();
		rset = stmt.executeQuery(qcertificado.toString());
	
	if(rset.next()){
				
		setSolicitud_id(sol_id);
		setObjeto_sol_id(rset.getString("OBJETO_SOL_ID"));
		setDetalle_certificado(rset.getString("DETALLE_CERTIFICADO").toUpperCase());
		setReg_pub_id(rset.getString("REG_PUB_ID")); 
		setZona_registral(rset.getString("ZONA_REGISTRAL"));
		setOfic_reg_id_verif(rset.getString("OFIC_REG_OBJETO_SOL"));
		setOfic_reg_id_exp(rset.getString("OFIC_REG_DESTINATARIO"));
		setTipo_objeto(rset.getString("TPO_PERS"));		
		setObjeto_sol_PN(rset.getString("APE_PAT")+" "+rset.getString("APE_MAT")+" "+rset.getString("NOMBRES"));
		setObjeto_sol_PJ(rset.getString("RAZ_SOC"));
		//setTotal(rset.getString("SUBTOTAL"));
		setTotal(df.format(Integer.parseInt(rset.getString("SUBTOTAL"))));
		setTs_crea(FechaUtil.getCurrentDateTime());
		setFechaCreacion(new Date((new java.util.Date().getTime())));
		
		setTs_modi(getTs_crea());
		
		if(rset.getString("TS_VERIFICACION")!=null)
		{
			/*
			String timeStamp = rset.getString("TS_VERIFICACION");
			//setHora(timeStamp.substring(11,13));
			setHora(timeStamp.substring(11,19));
			//setMinutos(timeStamp.substring(14,16));
			setAnno(timeStamp.substring(0,4));
			setMes(Constantes.meses[Integer.parseInt(timeStamp.substring(5,7))-1]);
			setDia(timeStamp.substring(8,10));
			*/
			try{
				String ts_verif = this.getTs_crea();
				
				int anio = Integer.parseInt(getTs_crea().substring(6,10));
				setHora(ts_verif.substring(11,13));
				setMinutos(ts_verif.substring(14,16));
				setAnno(String.valueOf(anio));
				setMes(Constantes.meses[Integer.parseInt(getTs_crea().substring(3,5))-1]);
				setDia(getTs_crea().substring(0,2));
				
			}catch(Exception e){
				
				String ts_verif = this.getTs_crea();
				setHora(ts_verif.substring(11,13));
				setMinutos(ts_verif.substring(14,16));
				setAnno(ts_verif.substring(0,4));
				setMes(Constantes.meses[Integer.parseInt(ts_verif.substring(5,7))-1]);
				setDia(ts_verif.substring(8,10));
			}
		}
		else
		{
			try{
				String ts_verif = this.getTs_crea();
				
				int anio = Integer.parseInt(getTs_crea().substring(6,10));
				setHora(ts_verif.substring(11,13));
				setMinutos(ts_verif.substring(14,16));
				setAnno(String.valueOf(anio));
				setMes(Constantes.meses[Integer.parseInt(getTs_crea().substring(3,5))-1]);
				setDia(getTs_crea().substring(0,2));
				
			}catch(Exception e){
				
				String ts_verif = this.getTs_crea();
				setHora(ts_verif.substring(11,13));
				setMinutos(ts_verif.substring(14,16));
				setAnno(ts_verif.substring(0,4));
				setMes(Constantes.meses[Integer.parseInt(ts_verif.substring(5,7))-1]);
				setDia(ts_verif.substring(8,10));
			}
			
		}
		// Inicio:mgarate:08/06/2007
		if(rset.getString("CERTIFICADO_ID").equals("33") || rset.getString("CERTIFICADO_ID").equals("34") || rset.getString("CERTIFICADO_ID").equals("35") || rset.getString("CERTIFICADO_ID").equals("36") ||
		   rset.getString("CERTIFICADO_ID").equals("37") || rset.getString("CERTIFICADO_ID").equals("38") || rset.getString("CERTIFICADO_ID").equals("39") || rset.getString("CERTIFICADO_ID").equals("40"))
		{
			setCertificado_busq_id(rset.getString("CERTIFICADO_ID"));
			setCrit_busq(rset.getString("CRIT_BUSQ"));
			setUrl_busq(rset.getString("URL_BUSQ"));
		}
		// Fin:mgarate:08/06/2007
		if (rset.getString("CONSTANCIA") !=null){
			setConstancia((""+rset.getString("CONSTANCIA")).trim());
		}	

		//inicio:dbravo:10/08/2007
		setConstancia2(leerCampoClob((oracle.sql.CLOB)rset.getClob("CONSTANCIA2")));
		setFlagPagoCrem(rset.getString("FLAG_PAGO_CREM"));
		setPaginasCrem(rset.getString("PAGINAS_CREM"));
		setPagoCrem(rset.getBigDecimal("PAGO_CREM"));
		//fin:dbravo:10/08/2007
		
		
		setAccion(Constantes.REGIS_VERIFICADOR);
	}else{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
		throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD,"No se encontró Solicitud " + sol_id);
	}
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(stmt);
	}
}

protected void recuperarDBcertificadoPorExpedir(String sol_id) throws Throwable{
	
	StringBuffer qcertificado = new StringBuffer();
	DecimalFormat df = new DecimalFormat("0.00");
	
	//Query muestra los datos 
	   qcertificado.append(" SELECT /*+ ordered*/ c.NOMBRE as DETALLE_CERTIFICADO, d.NOMBRE as ZONA_REGISTRAL, ")	   
	    .append(" e.NOMBRE as OFIC_REG_OBJETO_SOL, e2.NOMBRE as OFIC_REG_DESTINATARIO, ") 
 	    .append(" b.TPO_PERS, b.APE_PAT, b.APE_MAT, b.NOMBRES, b.RAZ_SOC, a.SUBTOTAL, b.OBJETO_SOL_ID, b.REG_PUB_ID, ") 	    
	    .append(" cert.CERTIFICADO_ID, cert.SOLICITUD_ID, cert.OBJETO_SOL_ID, cert.TPO_CERTIFICADO, ")
	    .append(" cert.COMENTARIO, cert.CONSTANCIA, cert.CONSTANCIA2,cert.TITULO ,cert.REG_PUB_ID, cert.OFIC_REG_ID_VERIF, cert.OFIC_REG_ID_EXP, cert.TS_VERIFICACION, ") 
 	    .append(" cert.TS_EXPEDICION, cert.TS_CREA, cert.TS_MODI, cert.USR_CREA, cert.USR_MODI, cert.CERTIFICADO_ID, ")
 	    
 	    //inicio:dbravo:15/08/2007
 	    .append(" cert.FLAG_PAGO_CREM, ")
 	    .append(" cert.PAGINAS_CREM, ")
 	    .append(" cert.PAGO_CREM ")
 	    //fin:dbravo:15/08/2007
 	    
 	    .append(" FROM SOLICITUD a, OBJETO_SOLICITUD b, TM_CERTIFICADOS c, REGIS_PUBLICO d, OFIC_REGISTRAL e, OFIC_REGISTRAL e2, DESTINATARIO f, USER1.CERTIFICADO cert ")
	    .append(" WHERE a.SOLICITUD_ID = b.SOLICITUD_ID")		
	    .append(" AND a.SOLICITUD_ID = f.SOLICITUD_ID")
	    .append(" AND a.SOLICITUD_ID = cert.SOLICITUD_ID")
	    .append(" AND b.CERTIFICADO_ID = c.CERTIFICADO_ID")
	    .append(" AND b.REG_PUB_ID = d.REG_PUB_ID")								
	    .append(" AND b.REG_PUB_ID = e.REG_PUB_ID")								
	    .append(" AND b.OFIC_REG_ID = e.OFIC_REG_ID")								
	    .append(" AND f.REG_PUB_ID = e2.REG_PUB_ID")								
	    .append(" AND f.OFIC_REG_ID = e2.OFIC_REG_ID")									    
	    .append(" AND a.SOLICITUD_ID = ? ");
	   
	PreparedStatement pStmt = null;
	ResultSet rset = null;
	
	try{
		pStmt = conn.prepareStatement(qcertificado.toString());
		pStmt.setInt(1,Integer.parseInt(sol_id));
		rset = pStmt.executeQuery();
	
	if(rset.next()){
				
		setCertificado_id(rset.getString("CERTIFICADO_ID"));
		setSolicitud_id(sol_id);
		setObjeto_sol_id(rset.getString("OBJETO_SOL_ID"));		
		setDetalle_certificado(rset.getString("DETALLE_CERTIFICADO").toUpperCase());
		setReg_pub_id(rset.getString("REG_PUB_ID")); 
		setZona_registral(rset.getString("ZONA_REGISTRAL"));
		setOfic_reg_id_verif(rset.getString("OFIC_REG_OBJETO_SOL"));
		setOfic_reg_id_exp(rset.getString("OFIC_REG_DESTINATARIO"));
		setTipo_objeto(rset.getString("TPO_PERS"));		
		setObjeto_sol_PN(rset.getString("APE_PAT")+" "+rset.getString("APE_MAT")+" "+rset.getString("NOMBRES"));
		setObjeto_sol_PJ(rset.getString("RAZ_SOC"));
		//setTotal(rset.getString("SUBTOTAL"));
		setTotal(df.format(Integer.parseInt(rset.getString("SUBTOTAL"))));
		setTs_crea(rset.getString("TS_CREA"));
		
		//inicio:dbravo:24/08/2007
		if(rset.getDate("TS_CREA")!=null){
			setFechaCreacion(new Date(rset.getTimestamp("TS_CREA").getTime()));
		}else{
			setFechaCreacion(new Date(new java.util.Date().getTime()));
		}
		//fin:dbravo:24/08/2007
		
		if(getTs_crea()==null)
			setTs_crea(FechaUtil.getCurrentDateTime());
		setTs_modi(rset.getString("TS_MODI"));		
		setTs_verificacion(rset.getString("TS_VERIFICACION"));
		if(getTs_verificacion()!=null)
		{
			
			Locale local= new Locale("es","ES");
			SimpleDateFormat minutoSDF = new SimpleDateFormat("mm",local);
			SimpleDateFormat horaSDF = new SimpleDateFormat("HH",local);
			SimpleDateFormat diaSDF  = new SimpleDateFormat("dd",local);
			SimpleDateFormat mesSDF  = new SimpleDateFormat("MM",local);
			SimpleDateFormat anioSDF  = new SimpleDateFormat("yyyy",local);
			
			setMinutos(minutoSDF.format(getFechaCreacion()));
			setHora(horaSDF.format(getFechaCreacion()));
			setDia(diaSDF.format(getFechaCreacion()));
			setMes(mesSDF.format(getFechaCreacion()));
			setAnno(anioSDF.format(getFechaCreacion()));
			/*
			setHora(getTs_verificacion().substring(11,19));
			setDia(getTs_verificacion().substring(8,10));
			setMes(Constantes.meses[Integer.parseInt(getTs_verificacion().substring(5,7))-1]);
			setAnno(getTs_verificacion().substring(0,4));
			*/
		}
		else
		{
			/*
			setHora(getTs_crea().substring(11,19));
			setDia(getTs_crea().substring(8,10));
			setMes(Constantes.meses[Integer.parseInt(getTs_crea().substring(5,7))-1]);
			setAnno(getTs_crea().substring(0,4));
			*/
			
			Locale local= new Locale("es","ES");
			SimpleDateFormat minutoSDF = new SimpleDateFormat("mm",local);
			SimpleDateFormat horaSDF = new SimpleDateFormat("HH",local);
			SimpleDateFormat diaSDF  = new SimpleDateFormat("dd",local);
			SimpleDateFormat mesSDF  = new SimpleDateFormat("MM",local);
			SimpleDateFormat anioSDF  = new SimpleDateFormat("yyyy",local);
			
			setMinutos(minutoSDF.format(getFechaCreacion()));
			setHora(horaSDF.format(getFechaCreacion()));
			setDia(diaSDF.format(getFechaCreacion()));
			setMes(mesSDF.format(getFechaCreacion()));
			setAnno(anioSDF.format(getFechaCreacion()));
			
			/*
			String ts_verif = this.getTs_crea();
			setHora(getTs_crea().substring(11,19));
			setAnno(getTs_crea().substring(6,10));
			setMes(Constantes.meses[Integer.parseInt(getTs_crea().substring(3,5))-1]);
			setDia(getTs_crea().substring(0,2));
			*/
		}
		
		setTs_expedicion(rset.getString("TS_EXPEDICION"));
		if(getTs_expedicion()!=null)
		{
			setDiaEmi(getTs_expedicion().substring(8,10));
			setMesEmi(Constantes.meses[Integer.parseInt(getTs_expedicion().substring(5,7))-1]);
			setAnnoEmi(getTs_expedicion().substring(0,4));
		}
		else
		{	
			setDiaEmi(getTs_crea().substring(8,10));
			setMesEmi(Constantes.meses[Integer.parseInt(getTs_crea().substring(5,7))-1]);
			setAnnoEmi(getTs_crea().substring(0,4));
		}
		//hphp:2003/10/29
		setConstancia((""+rset.getString("CONSTANCIA")).trim());
		
		//inicio:dbravo:10/08/2007
		setConstancia2(leerCampoClob((oracle.sql.CLOB)rset.getClob("CONSTANCIA2")));
		
		setFlagPagoCrem(rset.getString("FLAG_PAGO_CREM"));
		setPaginasCrem(rset.getString("PAGINAS_CREM"));
		setPagoCrem(rset.getBigDecimal("PAGO_CREM"));
		//fin:dbravo:10/08/2007
		/***Inicio: ifigueroa 20/08/2007 ****/
		setTitulo(rset.getString("TITULO"));
		/***Fin: ifigueroa 20/08/2007 ****/
		
		//accion para determinar la oficina registral de de expedicion
		setAccion(Constantes.REGIS_EMISOR);
		
	}else{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(pStmt);
		throw new CustomException(Errors.EC_GENERIC_DB_ERROR_INTEGRIDAD,"No se encontró Solicitud " + sol_id);
	}
	}finally{
		JDBC.getInstance().closeResultSet(rset);
		JDBC.getInstance().closeStatement(pStmt);
	}
}


protected void grabarDBcertificadoVerificado() throws Throwable{
	
	
		setTs_crea(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		setTs_modi(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));
		
		//dbravo:inicio:24/08/2007
		setFechaCreacion(new Date(new java.util.Date().getTime()));
		//dbravo:final:24/08/2007
		
		//Insert certificado
		DBConnection dconn = new DBConnection(getConn());		
		DboCertificado dboCertif = new DboCertificado(dconn);				
		dboCertif.setField(dboCertif.CAMPO_SOLICITUD_ID, getSolicitud_id());
		dboCertif.setField(dboCertif.CAMPO_OBJETO_SOL_ID, getObjeto_sol_id());
		dboCertif.setField(dboCertif.CAMPO_TPO_CERTIFICADO, getTpo_certificado());
		dboCertif.setField(dboCertif.CAMPO_COMENTARIO, getComentario());
		/**** jrosas: 13-06-2007 **/
		if (this.getCertificado_id().equals("18") || this.getCertificado_id().equals("19") || this.getCertificado_id().equals("20")
			|| this.getCertificado_id().equals("21")|| this.getCertificado_id().equals("22")|| this.getCertificado_id().equals("23")
			|| this.getCertificado_id().equals("25")|| this.getCertificado_id().equals("26")|| this.getCertificado_id().equals("27")
			|| this.getCertificado_id().equals("28")|| this.getCertificado_id().equals("29")|| this.getCertificado_id().equals("20")
			|| this.getCertificado_id().equals("31")|| this.getCertificado_id().equals("32")){
			
		}else{
			dboCertif.setField(dboCertif.CAMPO_CONSTANCIA, getConstancia());	
		}
		/*** fin jrosas:13-06-2007****/
		dboCertif.setField(dboCertif.CAMPO_CERTIFICADO_ID, this.getCertificado_id());
		dboCertif.setField(dboCertif.CAMPO_REG_PUB_ID, getReg_pub_id());
		dboCertif.setField(dboCertif.CAMPO_OFIC_REG_ID_VERIF, getOfic_reg_id_verif());
		dboCertif.setField(dboCertif.CAMPO_OFIC_REG_ID_EXP, getOfic_reg_id_exp());
		dboCertif.setField(dboCertif.CAMPO_TS_VERIFICACION, getTs_verificacion());
		dboCertif.setField(dboCertif.CAMPO_TS_EXPEDICION, getTs_expedicion());
		dboCertif.setField(dboCertif.CAMPO_TS_CREA, getTs_crea());
		dboCertif.setField(dboCertif.CAMPO_TS_MODI, getTs_modi());
		dboCertif.setField(dboCertif.CAMPO_USR_CREA, getUsr_crea());
		dboCertif.setField(dboCertif.CAMPO_USR_MODI, getUsr_modi());
		
		//Inicio: jascencio: 29/05/2007
		//SUNARP-REGMOBCOM: se agregaran fields
        dboCertif.setField(dboCertif.CAMPO_TITULO, getTitulo());
        //Fin: jascencio: 29/05/2007
        
        //inicio:dbravo:15/08/2007
        dboCertif.setField(DboCertificado.CAMPO_FLAG_PAGO_CREM, getFlagPagoCrem());
        dboCertif.setField(DboCertificado.CAMPO_PAGINAS_CREM, getPaginasCrem());
        if(getPagoCrem()!=null)
        {
        	dboCertif.setField(DboCertificado.CAMPO_PAGO_CREM, getPagoCrem().toString());
        }
        //fin:dbravo:15/08/2007
        
		dboCertif.add();		
		certificado_id = dboCertif.getField(dboCertif.CAMPO_CERTIFICADO_ID);
		
		/**
		 * inicio:dbravo:10/08/2007
		 */
		if(this.getConstancia2()!=null && this.getConstancia2().toString().trim().length()>0){		
			
			PreparedStatement ps = conn.prepareStatement("UPDATE CERTIFICADO SET CONSTANCIA2 = ? WHERE CERTIFICADO_ID = ?");
	        oracle.sql.CLOB newClob = oracle.sql.CLOB.createTemporary(conn, false, oracle.sql.CLOB.DURATION_SESSION);
	        
	        //newClob.putString(1,this.getConstancia2());
	        newClob.setString(this.getConstancia2().toString().length(), this.getConstancia2().toString());
	        ps.setClob(1, newClob);
	        ps.setInt(2, Integer.parseInt(certificado_id));
	        
	        int rowcnt = ps.executeUpdate();
	        
	        
	        ps.close();
	        
		}
        /**
		 * inicio:dbravo:10/08/2007
		 */
        
}	

protected void actualizarDBCertificadoExpedido() throws Throwable{
		//actualizo la fecha de expedicion del certificado 
		setTs_modi(FechaUtil.dateTimeToStringToOracle(new Timestamp(System.currentTimeMillis())));		
		
		DBConnection dconn= new DBConnection(getConn());
		DboCertificado dboCertif = new DboCertificado(dconn);				
		dboCertif.setFieldsToUpdate(dboCertif.CAMPO_TS_EXPEDICION+"|"+ dboCertif.CAMPO_TS_MODI+"|"+ dboCertif.CAMPO_USR_MODI);	
		dboCertif.setField(dboCertif.CAMPO_TS_EXPEDICION, getTs_expedicion());				
		dboCertif.setField(dboCertif.CAMPO_TS_MODI, getTs_modi());
		dboCertif.setField(dboCertif.CAMPO_USR_MODI, getUsr_modi());
		dboCertif.setField(dboCertif.CAMPO_CERTIFICADO_ID, getCertificado_id());		
		dboCertif.update();		
		//certificado_id = dboCertif.getField(dboCertif.CAMPO_CERTIFICADO_ID);
	
} 

public void validaPassword(String userId) throws Throwable{
			SecAdmin secAdmin = SecAdmin.getInstance();		
			//if (isTrace(this)) trace("Validando el password del registrador", request);
				try {
					String usuario = userId;
					String password = getPassword();
					secAdmin.validaUsuario(usuario, password);
										
				} catch (CustomException ce) {					
						throw new CustomException(ce.getCodigoError(), "Password del registrador es incorrecto", "errorRegistrador");					
				}
}


public void validaPassword(String userId,String passw) throws Throwable{
	SecAdmin secAdmin = SecAdmin.getInstance();		
	//if (isTrace(this)) trace("Validando el password del registrador", request);
		try {
			String usuario = userId;
			String password = passw;
			secAdmin.validaUsuario(usuario, password);								
		} catch (CustomException ce) {					
				throw new CustomException(ce.getCodigoError(), "Password del registrador es incorrecto", "errorRegistrador");					
		}
}



public String recuperaDatosVerificados() throws Throwable{
	  	StringBuffer qverifica = new StringBuffer();	
		String existe = "";	  	
	  	
	  	qverifica.append(" SELECT OBJETO_SOL_ID FROM VERIFICA_ASIENTO WHERE OBJETO_SOL_ID = ? ")	   
	    .append(" UNION SELECT OBJETO_SOL_ID FROM VERIFICA_FICHA WHERE OBJETO_SOL_ID = ? ")
 	    .append(" UNION SELECT OBJETO_SOL_ID FROM VERIFICA_TITU_PEND WHERE OBJETO_SOL_ID = ? ")  
	    .append(" UNION SELECT OBJETO_SOL_ID FROM VERIFICA_TOMO_FOJA WHERE OBJETO_SOL_ID = ? ");
		
		PreparedStatement pStmt = null;
		ResultSet rset = null;
		try{
		
		pStmt = conn.prepareStatement(qverifica.toString());
		pStmt.setInt(1,Integer.parseInt(getObjeto_sol_id()));
		pStmt.setInt(2,Integer.parseInt(getObjeto_sol_id()));
		pStmt.setInt(3,Integer.parseInt(getObjeto_sol_id()));
		pStmt.setInt(4,Integer.parseInt(getObjeto_sol_id()));
		rset = pStmt.executeQuery();
		
		if (rset.next()){			
			existe = rset.getString("OBJETO_SOL_ID"); 
		}
		}finally{
			JDBC.getInstance().closeResultSet(rset);
			JDBC.getInstance().closeStatement(pStmt);
		}
	return existe;
}

	/**
	 * Gets the certificado_id
	 * @return Returns a String
	 */
	public String getCertificado_id() {
		return certificado_id;
	}
	/**
	 * Sets the certificado_id
	 * @param certificado_id The certificado_id to set
	 */
	public void setCertificado_id(String certificado_id) {
		this.certificado_id = certificado_id;
	}

	/**
	 * Gets the solicitud_id
	 * @return Returns a String
	 */
	public String getSolicitud_id() {
		return solicitud_id;
	}
	/**
	 * Sets the solicitud_id
	 * @param solicitud_id The solicitud_id to set
	 */
	public void setSolicitud_id(String solicitud_id) {
		this.solicitud_id = solicitud_id;
	}

	/**
	 * Gets the objeto_sol_id
	 * @return Returns a String
	 */
	public String getObjeto_sol_id() {
		return objeto_sol_id;
	}
	/**
	 * Sets the objeto_sol_id
	 * @param objeto_sol_id The objeto_sol_id to set
	 */
	public void setObjeto_sol_id(String objeto_sol_id) {
		this.objeto_sol_id = objeto_sol_id;
	}

	/**
	 * Gets the tpo_certificado
	 * @return Returns a String
	 */
	public String getTpo_certificado() {
		return tpo_certificado;
	}
	/**
	 * Sets the tpo_certificado
	 * @param tpo_certificado The tpo_certificado to set
	 */
	public void setTpo_certificado(String tpo_certificado) {
		this.tpo_certificado = tpo_certificado;
	}

	/**
	 * Gets the comentario
	 * @return Returns a String
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * Sets the comentario
	 * @param comentario The comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * Gets the reg_pub_id
	 * @return Returns a String
	 */
	public String getReg_pub_id() {
		return reg_pub_id;
	}
	/**
	 * Sets the reg_pub_id
	 * @param reg_pub_id The reg_pub_id to set
	 */
	public void setReg_pub_id(String reg_pub_id) {
		this.reg_pub_id = reg_pub_id;
	}

	/**
	 * Gets the ofic_reg_id_verif
	 * @return Returns a String
	 */
	public String getOfic_reg_id_verif() {
		return ofic_reg_id_verif;
	}
	/**
	 * Sets the ofic_reg_id_verif
	 * @param ofic_reg_id_verif The ofic_reg_id_verif to set
	 */
	public void setOfic_reg_id_verif(String ofic_reg_id_verif) {
		this.ofic_reg_id_verif = ofic_reg_id_verif;
	}

	/**
	 * Gets the ofic_reg_id_exp
	 * @return Returns a String
	 */
	public String getOfic_reg_id_exp() {
		return ofic_reg_id_exp;
	}
	/**
	 * Sets the ofic_reg_id_exp
	 * @param ofic_reg_id_exp The ofic_reg_id_exp to set
	 */
	public void setOfic_reg_id_exp(String ofic_reg_id_exp) {
		this.ofic_reg_id_exp = ofic_reg_id_exp;
	}

	/**
	 * Gets the ts_verificacion
	 * @return Returns a String
	 */
	public String getTs_verificacion() {
		return ts_verificacion;
	}
	/**
	 * Sets the ts_verificacion
	 * @param ts_verificacion The ts_verificacion to set
	 */
	public void setTs_verificacion(String ts_verificacion) {
		this.ts_verificacion = ts_verificacion;
	}

	/**
	 * Gets the ts_expedicion
	 * @return Returns a String
	 */
	public String getTs_expedicion() {
		return ts_expedicion;
	}
	/**
	 * Sets the ts_expedicion
	 * @param ts_expedicion The ts_expedicion to set
	 */
	public void setTs_expedicion(String ts_expedicion) {
		this.ts_expedicion = ts_expedicion;
	}

	/**
	 * Gets the ts_crea
	 * @return Returns a String
	 */
	public String getTs_crea() {
		return ts_crea;
	}
	/**
	 * Sets the ts_crea
	 * @param ts_crea The ts_crea to set
	 */
	public void setTs_crea(String ts_crea) {
		this.ts_crea = ts_crea;
	}

	/**
	 * Gets the ts_modi
	 * @return Returns a String
	 */
	public String getTs_modi() {
		return ts_modi;
	}
	/**
	 * Sets the ts_modi
	 * @param ts_modi The ts_modi to set
	 */
	public void setTs_modi(String ts_modi) {
		this.ts_modi = ts_modi;
	}

	/**
	 * Gets the usr_crea
	 * @return Returns a String
	 */
	public String getUsr_crea() {
		return usr_crea;
	}
	/**
	 * Sets the usr_crea
	 * @param usr_crea The usr_crea to set
	 */
	public void setUsr_crea(String usr_crea) {
		this.usr_crea = usr_crea;
	}

	/**
	 * Gets the usr_modi
	 * @return Returns a String
	 */
	public String getUsr_modi() {
		return usr_modi;
	}
	/**
	 * Sets the usr_modi
	 * @param usr_modi The usr_modi to set
	 */
	public void setUsr_modi(String usr_modi) {
		this.usr_modi = usr_modi;
	}

	/**
	 * Gets the detalle_certificado
	 * @return Returns a String
	 */
	public String getDetalle_certificado() {
		return detalle_certificado;
	}
	/**
	 * Sets the detalle_certificado
	 * @param detalle_certificado The detalle_certificado to set
	 */
	public void setDetalle_certificado(String detalle_certificado) {
		this.detalle_certificado = detalle_certificado;
	}

	/**
	 * Gets the zona_registral
	 * @return Returns a String
	 */
	public String getZona_registral() {
		return zona_registral;
	}
	/**
	 * Sets the zona_registral
	 * @param zona_registral The zona_registral to set
	 */
	public void setZona_registral(String zona_registral) {
		this.zona_registral = zona_registral;
	}

	/**
	 * Gets the objeto_sol_PN
	 * @return Returns a String
	 */
	public String getObjeto_sol_PN() {
		return objeto_sol_PN;
	}
	/**
	 * Sets the objeto_sol_PN
	 * @param objeto_sol_PN The objeto_sol_PN to set
	 */
	public void setObjeto_sol_PN(String objeto_sol_PN) {
		this.objeto_sol_PN = objeto_sol_PN;
	}

	/**
	 * Gets the objeto_sol_PJ
	 * @return Returns a String
	 */
	public String getObjeto_sol_PJ() {
		return objeto_sol_PJ;
	}
	/**
	 * Sets the objeto_sol_PJ
	 * @param objeto_sol_PJ The objeto_sol_PJ to set
	 */
	public void setObjeto_sol_PJ(String objeto_sol_PJ) {
		this.objeto_sol_PJ = objeto_sol_PJ;
	}

	/**
	 * Gets the total
	 * @return Returns a String
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * Sets the total
	 * @param total The total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * Gets the conn
	 * @return Returns a Connection
	 */
	public Connection getConn() {
		return conn;
	}
	/**
	 * Sets the conn
	 * @param conn The conn to set
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Gets the tipo_objeto
	 * @return Returns a String
	 */
	public String getTipo_objeto() {
		return tipo_objeto;
	}
	/**
	 * Sets the tipo_objeto
	 * @param tipo_objeto The tipo_objeto to set
	 */
	public void setTipo_objeto(String tipo_objeto) {
		this.tipo_objeto = tipo_objeto;
	}

	/**
	 * Gets the estado_certificado
	 * @return Returns a String
	 */
	public String getEstado_certificado() {
		return estado_certificado;
	}
	/**
	 * Sets the estado_certificado
	 * @param estado_certificado The estado_certificado to set
	 */
	public void setEstado_certificado(String estado_certificado) {
		this.estado_certificado = estado_certificado;
	}

	/**
	 * Gets the password
	 * @return Returns a String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the password
	 * @param password The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the hora
	 * @return Returns a String
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * Sets the hora
	 * @param hora The hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * Gets the dia
	 * @return Returns a String
	 */
	public String getDia() {
		return dia;
	}
	/**
	 * Sets the dia
	 * @param dia The dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}

	/**
	 * Gets the mes
	 * @return Returns a String
	 */
	public String getMes() {
		return mes;
	}
	/**
	 * Sets the mes
	 * @param mes The mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * Gets the anno
	 * @return Returns a String
	 */
	public String getAnno() {
		return anno;
	}
	/**
	 * Sets the anno
	 * @param anno The anno to set
	 */
	public void setAnno(String anno) {
		this.anno = anno;
	}

	/**
	 * Gets the accion
	 * @return Returns a String
	 */
	public String getAccion() {
		return accion;
	}
	/**
	 * Sets the accion
	 * @param accion The accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * Gets the diaEmi
	 * @return Returns a String
	 */
	public String getDiaEmi() {
		return diaEmi;
	}
	/**
	 * Sets the diaEmi
	 * @param diaEmi The diaEmi to set
	 */
	public void setDiaEmi(String diaEmi) {
		this.diaEmi = diaEmi;
	}

	/**
	 * Gets the mesEmi
	 * @return Returns a String
	 */
	public String getMesEmi() {
		return mesEmi;
	}
	/**
	 * Sets the mesEmi
	 * @param mesEmi The mesEmi to set
	 */
	public void setMesEmi(String mesEmi) {
		this.mesEmi = mesEmi;
	}

	/**
	 * Gets the annoEmi
	 * @return Returns a String
	 */
	public String getAnnoEmi() {
		return annoEmi;
	}
	/**
	 * Sets the annoEmi
	 * @param annoEmi The annoEmi to set
	 */
	public void setAnnoEmi(String annoEmi) {
		this.annoEmi = annoEmi;
	}
	/**
	 * Gets the constancia
	 * @return Returns a String
	 */
	public String getConstancia() {
		return constancia;
	}
	/**
	 * Sets the constancia
	 * @param constancia The constancia to set
	 */
	public void setConstancia(String constancia) {
		this.constancia = constancia;
	}

	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @autor jascencio
	 * @fecha May 29, 2007
	 * @CC: SUNARP-REGMOBCON-2006
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
// Inicio:mgarate:05/06/2007
	public String getCertificado_busq_id() {
		return certificado_busq_id;
	}

	public void setCertificado_busq_id(String certificado_busq_id) {
		this.certificado_busq_id = certificado_busq_id;
	}

	public String getCrit_busq() {
		return crit_busq;
	}
	public void setCrit_busq(String crit_busq) {
		this.crit_busq = crit_busq;
	}


	public String getUrl_busq() {
		return url_busq;
	}

	public void setUrl_busq(String url_busq) {
		this.url_busq = url_busq;
	}
// Fin:mgarate:05/06/2007

	/**
	 * @return the minutos
	 */
	public String getMinutos() {
		return minutos;
	}

	/**
	 * @param minutos the minutos to set
	 */
	public void setMinutos(String minutos) {
		this.minutos = minutos;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 10, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the constancia2
	 */
	public StringBuffer getConstancia2() {
		return constancia2;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 10, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param constancia2 the constancia2 to set
	 */
	public void setConstancia2(StringBuffer constancia2) {
		this.constancia2 = constancia2;
	}
	/**
	 * @autor Daniel L. Bravo Falcón
	 * @descripcion Se encarga de leer un objeto de tipo clob
	 * @param constancia2
	 * @return
	 * @throws SQLException
	 */
	public StringBuffer leerCampoClob(oracle.sql.CLOB clob) throws SQLException{
		
		StringBuffer sb = new StringBuffer();
		
		if (clob!= null && clob.length()>0){
			
			long pos = 1L;
			long len = clob.length(); 
	        
	        sb = new StringBuffer();
	        long left = len;
	        //System.out.println("len: "+len);
	        
	        while (len > pos) {
	            int charsToCopy = oracle.sql.CLOB.MAX_CHUNK_SIZE;
	            if (left < oracle.sql.CLOB.MAX_CHUNK_SIZE) {
	                charsToCopy = (int)left;
	            }
	            sb.append(clob.getSubString(pos, charsToCopy));
	            
	            left -= charsToCopy;
	            pos = pos + charsToCopy;
	        }
	        
		}
		
		return sb;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the flagPagoCrem
	 */
	public String getFlagPagoCrem() {
		return flagPagoCrem;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param flagPagoCrem the flagPagoCrem to set
	 */
	public void setFlagPagoCrem(String flagPagoCrem) {
		this.flagPagoCrem = flagPagoCrem;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the paginasCrem
	 */
	public String getPaginasCrem() {
		return paginasCrem;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param paginasCrem the paginasCrem to set
	 */
	public void setPaginasCrem(String paginasCrem) {
		this.paginasCrem = paginasCrem;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the pagoCrem
	 */
	public BigDecimal getPagoCrem() {
		return pagoCrem;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 15, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param pagoCrem the pagoCrem to set
	 */
	public void setPagoCrem(BigDecimal pagoCrem) {
		this.pagoCrem = pagoCrem;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 24, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @autor dbravo
	 * @fecha Aug 24, 2007
	 * @CC:SUNARP-REGMOBCON
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	
}

