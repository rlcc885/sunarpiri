package gob.pe.sunarp.extranet.framework;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;
import com.jcorporate.expresso.core.job.*;
import com.jcorporate.expresso.core.security.User;
import com.jcorporate.expresso.services.html.*;
import com.jcorporate.expresso.core.misc.StringUtil;

import com.jcorporate.expresso.core.db.*;
import gob.pe.sunarp.extranet.common.*;
import gob.pe.sunarp.extranet.dbobj.*;
import gob.pe.sunarp.extranet.framework.*;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import gob.pe.sunarp.extranet.util.*;
import java.sql.*;


public class MailJob extends Thread{
	private DBConnectionFactory pool = null;
	private Connection conn = null;
	private PreparedStatement pstSelect = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private boolean esWEB;
	
	private String sqlUpdate = "UPDATE MAIL SET MAIL_ESTADO = 1, MAIL_SEND_DATE = sysdate WHERE MAIL_REFNUM = ?";
	private String sqlUpdateError = "UPDATE MAIL SET MAIL_ESTADO = 2, MAIL_SEND_DATE = sysdate WHERE MAIL_REFNUM = ?";
	private String sqlSelect = "SELECT MAIL_REFNUM, MAIL_MFROM, MAIL_MTO, MAIL_MCOPY, MAIL_MBCC, MAIL_SUBJ, MAIL_BODY, MAIL_ESTADO, MAIL_SEND_DATE FROM MAIL WHERE MAIL_ESTADO = 0 AND MAIL_STORE_DATE >= (sysdate - ?) ";
	private long minutos = 15;
	/**
	 * SVASQUEZ - AVATAR GLOBAL
	 * SE ACTUALIZA LOS DIAS ACTIVOS DE 30 A 7
	 */
	private int diasActivos = 7;
	//private int diasActivos = 30;

	
	private int contadorMensaje = 0;
	/**
	 * SVASQUEZ - AVATAR GLOBAL
	 * SE AÑADE LA VARIABLE: estadoMailJob, CONTROLADOR DE ESTADO DE ENVIO DE MAILS
	 * SE AÑADE LA VARIABLE: timeSleepMail, TIEMPO DE SLEEP DEL ENVIO DE MAILS
	 * SE AÑADE LA VARIABLE: numeroMailEnvio, NUMERO DE ENVIO DE MAIlS 
	 * 
	 */
	//Bandera para controlar el estado del Hilo de Envio de Mails	
	private boolean estadoMailJob = false;	
	private long timeSleepMail = 1000*30*1; // default 60 segundos	
	private int numeroMailEnvio = 10; // DEFAULT 30 MAILS ENVIADOS
	private long mailRefNum = 0;
	
	public MailJob(){
		this(false);
	}

	public MailJob(boolean esWEB){
		this.esWEB = esWEB;
		pool = DBConnectionFactory.getInstance();
		if(this.esWEB){
			diasActivos = Propiedades.getInstance().getMailActivoDias();
			sqlSelect += "AND MAIL_SENDER_AGENT = 'WEB'";
			minutos = (long) (Propiedades.getInstance().getSendMailTiempo() * 60000);
		}else{
			diasActivos = Propiedades.getInstance().getMailActivoDiasNoWeb();
			sqlSelect += "AND MAIL_SENDER_AGENT IS NULL";
			minutos = (long) (Propiedades.getInstance().getSendMailTiempoNoWeb() * 60000);
		}
	}

	public void run() {
		while(true){
			try{
				// Obteniendo la conexión
				conn = pool.getConnection();
				conn.setAutoCommit(false);
				
				// Query de UPDATE
				pstmt = conn.prepareStatement(sqlUpdate);
				
				// Query de SELECT
				pstSelect = conn.prepareStatement(sqlSelect);
				pstSelect.setInt(1, diasActivos);
				rs = pstSelect.executeQuery();
				while(rs.next() && estadoMailJob){
					contadorMensaje ++;
					mailRefNum = rs.getLong("MAIL_REFNUM");
					try{
						MailSender.getInstance().sendMail(
							rs.getString("MAIL_MFROM"), rs.getString("MAIL_MTO"), rs.getString("MAIL_MCOPY"),
							rs.getString("MAIL_MBCC"), rs.getString("MAIL_SUBJ"), rs.getString("MAIL_BODY"));
						    System.out.println("Se envio mail con referencia      ::"+mailRefNum);
						
						try {
							pstmt.setLong(1, mailRefNum);
							pstmt.executeUpdate();
							conn.commit();
							System.out.println("Se actualizo mail - referencia    ::"+mailRefNum);
						} catch (java.sql.SQLException dbe) {
							if (Loggy.isTrace(this)) 
								System.out.println("ERROR: " + dbe.getMessage());
							try{
								if(conn != null)	
									conn.rollback();
							}catch(SQLException ex){}
						}
		  			}catch(MessagingException me){
		  				if (Loggy.isTrace(this)){
		  					System.out.println("MailRefNum                        :: " + mailRefNum);
		  					System.out.println("No se puede enviar el mail        :: " + me.getMessage());
		  				}
		  				PreparedStatement pstmt1 = null;
		  				try{
			  				pstmt1 = conn.prepareStatement(sqlUpdateError);
			  				pstmt1.setLong(1, mailRefNum);
							pstmt1.executeUpdate();							
		  				}catch(SQLException e){
		  					e.printStackTrace();
		  				}
		  				finally{
		  					if(pstmt1!=null){
		  						pstmt1.close();
		  					}
		  				}		  				
					}
		  			/**
		  			 * SVASQUEZ - AVATAR GLOBAL
		  			 * 
		  			 */
		  			if(contadorMensaje == numeroMailEnvio){
		  				contadorMensaje=0;
		  				estadoMailJob=false;
					}
				}
			} catch (SQLException ex) {
				if (Loggy.isTrace(this)) System.out.println(ex.getMessage());
			} catch (Throwable ex) {
				if (Loggy.isTrace(this)) System.out.println(ex.getMessage());
			} finally {
				closeRs(rs);
				closeStm(pstSelect);
				closeStm(pstmt);
				pool.release(conn);
				try{
					/**
					 * SVASQUEZ - AVATAR GLOBAL
					 * Se cambia el tiempo del sleep del Hilo de Mails
					 */
					//sleep(minutos);
	  				sleep(timeSleepMail);					
					estadoMailJob=true;
				}catch(Exception e){
					if (Loggy.isTrace(this)) 
						System.out.println("ERROR: Se interrumpio el thread de envio de emails en el metodo sleep().");
				}
			}
		}
	}
	
	public void closeStm(Statement stm){
		if(stm != null)
			try{stm.close();
			}catch(Exception ex){}
	}
	public void closeRs(ResultSet rs){
		if(rs != null)
			try{rs.close();
			}catch(Exception ex){}
	}
	
}