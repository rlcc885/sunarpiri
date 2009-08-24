package gob.pe.sunarp.extranet.util;

import gob.pe.sunarp.extranet.framework.session.UsuarioBean;
import gob.pe.sunarp.extranet.framework.tam.SecAdmin;
import gob.pe.sunarp.extranet.pool.DBConnectionFactory;
import java.io.*;
import java.sql.*;
import javax.servlet.jsp.JspWriter;
import gob.pe.sunarp.extranet.framework.Loggy;

public class EliminaPartida 
{

FileWriter fw = null;	
BufferedWriter bw = null;
JspWriter out_jsp = null;
UsuarioBean usuario = null;
java.util.Vector bolsa = new java.util.Vector();
String IP="";


public void ejecuta()
{
/*
Enero 2003
Proceso para eliminar Partidas
*/	
DBConnectionFactory pool = DBConnectionFactory.getInstance();
Connection conn = null;

String ruta = Propiedades.getInstance().getRutaWebApplication();

try{
	//creamos archivo log de salida
	String rp = ruta+"//WEB-INF//log//eliminaPartida.txt";
	fw = new FileWriter(rp, true);
	bw = new BufferedWriter(fw);
	
	mensaje("******************************************************************");
	mensaje("Inicia Proceso elimina Partida "+FechaUtil.getCurrentDateTime());
	if (this.usuario==null)
		throw new ValidacionException("Usuario no se ha registrado");
	
	if (this.usuario.getUserId().equals("SUNARP001")==false)
		throw new ValidacionException("Usuario no autorizado");
		
	mensaje("usuario : "+this.usuario.getUserId());
	
	//obtenemos conexión a la base de datos
	conn = pool.getConnection();
	conn.setAutoCommit(false);
	
	StringBuffer sb = new StringBuffer();
	
	//contar cuantos registros serán procesados
	sb.append("select count(*) from persona where fg_del='1'");

	Statement stmt = conn.createStatement();
	ResultSet rset = stmt.executeQuery(sb.toString());
	
	int cantidad=0;
	rset.next();
	cantidad = rset.getInt(1);
	mensaje("Cantidad de registros a procesar : "+cantidad);
	
	sb.delete(0,sb.length());
	sb.append("select persona_id, tpo_pers from persona where fg_del='1'");
	mensaje("         _sql_ "+sb.toString());
	rset = stmt.executeQuery(sb.toString());
	boolean b = rset.next();
	int conta=0;
	while (b==true)
		{
			conta++;
			mensaje("...procesando registro "+ conta + " de " + cantidad);
			//verificar si es persona natural o jurídica
			String personaId   = rset.getString("persona_id");
			String tipoPersona = rset.getString("tpo_pers");
			
			bolsa.removeAllElements();
			
			try
			{
			
			//primero se borran los registros asociados, según tipo persona
			if (tipoPersona.startsWith("N"))
				procesaPersonaNatural(personaId, conn);
			else
				procesaPersonaJuridica(personaId, conn);
			
			//luego se borra la tabla PERSONA
			procesaPersona(personaId, conn);
			
			//el proceso final consiste en borrar del TAM
			//a los usuarios asociados a los registros eliminados
			procesaTAM();
			
			mensaje("Persona "+personaId+" eliminada");
			conn.commit();
			}
			catch (Throwable te)
			{
				mensaje("Error eliminando persona " + personaId + " error : " + te.getMessage());
				mensaje("se hace rollback a las operaciones de este registro");
				conn.rollback();
				te.printStackTrace();
			}
			
			b = rset.next();
		}
	
	
	conn.commit();
	}
	catch (ValidacionException ve)
	{
		mensaje("Error de validacion :" + ve.getMensaje());
		try{
			conn.rollback();
		}
		catch (Throwable y)
		{
		}
		
	}	
	catch (Throwable t)
	{
		mensaje("Error en proceso Tarea.eliminaPersona" + t.getMessage());
		mensaje("efectuando rollback a la base de datos");
		t.printStackTrace();
		try{
			conn.rollback();
		}
		catch (Throwable y)
		{
		}
		
	}
	finally
	{

		mensaje("Fin Proceso elimina Partida "+FechaUtil.getCurrentDateTime());
		pool.release(conn);
		try{
		bw.close();
		fw.close();
		}
		catch (Throwable ee)
		{
		}
	}
}//fin metodo ejecuta()


private void procesaPersonaJuridica(String personaId, Connection conn) throws Throwable
{
	StringBuffer sb = new StringBuffer();
	sb.append("update pe_juri set repres_id=null where persona_id = ").append(personaId);
	mensaje("         _sql_ "+sb.toString());
	Statement stmt = conn.createStatement();
	stmt.execute(sb.toString());	
	
	sb.delete(0,sb.length());
	sb.append("update pe_juri set repres_id=null where repres_id in (");
	sb.append(" select pe_natu_id from pe_natu where pe_juri_id in (");
	sb.append(" select pe_juri_id from pe_juri where persona_id= ").append(personaId);
	sb.append("))");
	mensaje("         _sql_ "+sb.toString());
	stmt.execute(sb.toString());	



	sb.delete(0,sb.length());
	sb.append("select usr_id from cuenta where pe_natu_id in ((");
	sb.append(" select pe_natu_id from pe_natu where pe_juri_id in (");
	sb.append(" select pe_juri_id from pe_juri where persona_id= ").append(personaId);
	sb.append(")))");
	mensaje("         _sql_ "+sb.toString());
	ResultSet rset = stmt.executeQuery(sb.toString());
	boolean b = rset.next();
	while (b)
		{
			bolsa.add(rset.getString(1));
			b = rset.next();
		}
		
		
			
	sb.delete(0,sb.length());
	sb.append(" delete from pe_natu where pe_juri_id in (");
	sb.append(" select pe_juri_id from pe_juri where persona_id= ").append(personaId);
	sb.append(")");
	mensaje("         _sql_ "+sb.toString());
	stmt.execute(sb.toString());	
	
	sb.delete(0,sb.length());
	sb.append("delete from pe_juri where persona_id = ").append(personaId);
	mensaje("         _sql_ "+sb.toString());
	stmt.execute(sb.toString());	
	
	stmt.close();
}

private void procesaPersonaNatural(String personaId, Connection conn) throws Throwable
{
	StringBuffer sb = new StringBuffer();
	sb.append("update pe_juri set repres_id = null where repres_id in ( ");
	sb.append("select pe_natu_id from pe_natu where persona_id =").append(personaId);
	sb.append(" ) ");
	mensaje("         _sql_ "+sb.toString());
	Statement stmt = conn.createStatement();
	stmt.execute(sb.toString());
	
	sb.delete(0,sb.length());
	sb.append("select usr_id from cuenta where pe_natu_id in (");
	sb.append("select pe_natu_id from pe_natu where persona_id = ");
	sb.append(personaId);
	sb.append(")");
	mensaje("         _sql_ "+sb.toString());
	ResultSet rset = stmt.executeQuery(sb.toString());
	boolean b = rset.next();
	while (b)
		{
			bolsa.add(rset.getString(1));
			b = rset.next();
		}
	
	
	sb.delete(0,sb.length());
	sb.append("delete from pe_natu where persona_id = ");
	sb.append(personaId);
	mensaje("         _sql_ "+sb.toString());
	stmt.execute(sb.toString());
	
	stmt.close();
}


private void procesaPersona(String personaId, Connection conn) throws Throwable
{
	StringBuffer sb = new StringBuffer();
	sb.append("delete from persona where persona_id =").append(personaId);
	mensaje("         _sql_ "+sb.toString());
	Statement stmt = conn.createStatement();
	stmt.execute(sb.toString());

	stmt.close();
}


private void mensaje(String m)
{
	try
	{
		out_jsp.println(m);
		if (Loggy.isTrace(this)) System.out.println(m);
		bw.write(m+"\r\n");
		bw.flush();
	}
	catch(Throwable t)
		{
		}
}

private void procesaTAM() throws Throwable
{
	SecAdmin secAdmin = SecAdmin.getInstance();
	
	for (int i = 0; i < bolsa.size(); i++)
		{
			String user = (String) bolsa.elementAt(i);
			mensaje("   eliminado usuario TAM : " + user);
			secAdmin.borrarUsuario(user);
		}
}
	
	public JspWriter getOut_jsp() {
		return out_jsp;
	}
	public void setOut_jsp(JspWriter out_jsp) {
		this.out_jsp = out_jsp;
	}

	/**
	 * Gets the usuario
	 * @return Returns a UsuarioBean
	 */
	public UsuarioBean getUsuario() {
		return usuario;
	}
	/**
	 * Sets the usuario
	 * @param usuario The usuario to set
	 */
	public void setUsuario(UsuarioBean usuario) {
		this.usuario = usuario;
	}

	/**
	 * Gets the iP
	 * @return Returns a String
	 */
	public String getIP() {
		return IP;
	}
	/**
	 * Sets the iP
	 * @param iP The iP to set
	 */
	public void setIP(String iP) {
		IP = iP;
	}

}//fin de clase