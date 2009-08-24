package gob.pe.sunarp.extranet.reportegeneral;

import gob.pe.sunarp.extranet.framework.xml.XErrorManejado;
import gob.pe.sunarp.extranet.reportegeneral.bean.ConsolaCentral;
import gob.pe.sunarp.extranet.reportegeneral.bean.DatosOficina;
import gob.pe.sunarp.extranet.reportegeneral.bean.OficinaConection;
import gob.pe.sunarp.extranet.reportegeneral.pool.DBConnectionFactories;
import gob.pe.sunarp.extranet.util.FechaUtil;
import gob.pe.sunarp.extranet.util.Generales;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import gob.pe.sunarp.extranet.framework.Loggy;

public class ConsolaCentralServlet extends HttpServlet {

	public final String selectwhere = "SELECT E.REG_PUB_ID, E.OFIC_REG_ID, E.AREA_REG_ID, E.ENT_TIPO, E.COD_LIBRO, E.NUM_PARTIDA, E.COD_ACTO, E.ANO_TITU, E.NUM_TITU, E.ENT_TRIGGER, T.ESTADO, E.TMSTMP_CREA, T.TMSTMP_PT, T.TMSTMP_TX, T.TMSTMP_A1, T.TMSTMP_A2, T.ERROR_CODIGO FROM EX_ENTIDAD E, EX_TRANSMISION T WHERE E.TX_REFNUM = T.TX_REFNUM ";
	public final String order = " ORDER BY REG_PUB_ID, OFIC_REG_ID, TMSTMP_CREA, AREA_REG_ID, NUM_PARTIDA, ANO_TITU, NUM_TITU";
	
	public final String cabecera1 = "<tr bgcolor=\"#C0C0C0\"><td width=\"30\" align=\"center\">RP</td><td width=\"40\" align=\"center\">Ofic</td>";
	public final String cabecera2 = "<td width=\"40\" align=\"center\">Area Reg</td><td width=\"35\" align=\"center\">Ent.</td><td width=\"35\" align=\"center\">Libro</td>";
	public final String cabecera3 = "<td width=\"70\" align=\"center\">Partida</td><td width=\"40\" align=\"center\">Acto</td><td width=\"50\" align=\"center\">Año_tit</td>";
	public final String cabecera4 = "<td width=\"70\" align=\"center\">Titulo</td><td width=\"50\" align=\"center\">Trigger</td><td width=\"40\" align=\"center\">Estado</td>";
	public final String cabecera5 = "<td width=\"70\" align=\"center\">TS_Crea</td><td width=\"70\" align=\"center\">TS_PT</td><td width=\"70\" align=\"center\">TS_TX</td>";
	public final String cabecera6 = "<td width=\"70\" align=\"center\">TS_A1</td><td width=\"70\" align=\"center\">TS_A2</td><td width=\"50\" align=\"center\">Error</td></tr>";

	public final StringBuffer cabecera = new StringBuffer(cabecera1).append(cabecera2).append(cabecera3).append(cabecera4).append(cabecera5).append(cabecera6);
		
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try{
			DBConnectionFactories.init(3);
		}catch(Exception ex){
			throw new ServletException("No se pudo iniciar las conexiones con las oficinas. ERROR: " + ex.getMessage());
		}
	}
	
	public void doPost(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException {
			
		java.sql.Connection conn = null;
		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		java.util.HashMap hash = new java.util.HashMap();
		
		try{
			PrintWriter out = response.getWriter();
			
			//Capturando los parámetros
			//  1.Estado
				StringBuffer whereEstados = new StringBuffer();
				
				String estado_Nulo = request.getParameter("E1");
				String estado_PT = request.getParameter("E2");
				String estado_TX = request.getParameter("E3");
				String estado_A1 = request.getParameter("E4");
				String estado_A2 = request.getParameter("E5");
				
				StringBuffer aux_estados = new StringBuffer("");
				String wEstado1 = null;
				String wEstado2 = null;
				boolean esEstadoA2 = false;

				if(estado_Nulo != null && estado_Nulo.equalsIgnoreCase("ON"))
					wEstado1 = " T.ESTADO IS NULL";
					
				if(estado_PT != null && estado_PT.equalsIgnoreCase("ON"))
					aux_estados.append(",'PT'");
					
				if(estado_TX != null && estado_TX.equalsIgnoreCase("ON"))
					aux_estados.append(",'TX'");
					
				if(estado_A1 != null && estado_A1.equalsIgnoreCase("ON"))
					aux_estados.append(",'A1'");
					
				if(estado_A2 != null && estado_A2.equalsIgnoreCase("ON")){
					aux_estados.append(",'A2'");
					esEstadoA2 = true;
				}
				
				if(aux_estados.toString().length() > 2)
					wEstado2 = " T.ESTADO IN (" + aux_estados.toString().substring(1) + ")";
				
				if(wEstado1 == null && wEstado2 == null)
					whereEstados.append("");
				else{
					if(wEstado1 != null && wEstado2 != null)
						whereEstados.append(" AND (");
					else
						whereEstados.append(" AND");
					
					if(wEstado1 != null)
						whereEstados.append(wEstado1);
					
					if(wEstado1 != null && wEstado2 != null)
						whereEstados.append(" OR");
					
					if(wEstado2 != null)
						whereEstados.append(wEstado2);
					
					if(wEstado1 != null && wEstado2 != null)
						whereEstados.append(")");
				}


			//  2.Error
				StringBuffer whereError = new StringBuffer("");
				
				String error = request.getParameter("error");
				
				if(error.equals("Error2"))
					whereError.append(" AND T.ERROR_CODIGO != '000000' ");
				
				StringBuffer posibleWhereError = null;
				if(error.equals("Error3")){
					String codError = null;
					boolean vaComma = false;
					posibleWhereError = new StringBuffer(" AND T.ERROR_CODIGO != '000000 AND T.ERROR_CODIGO NOT IN(");

					java.util.Set a = ConsolaCentral.getInstance().getErroresManejados().keySet();
					java.util.Iterator it = a.iterator();

					if(it.hasNext()){
						for(; it.hasNext();){
							codError = it.next().toString();
							
							if(!(!esEstadoA2 && (codError.equals("E94003") || codError.equals("E94019")))){
								if(vaComma)
									posibleWhereError.append(",'").append(codError).append("'");
								else{
									vaComma = true;
									posibleWhereError.append("'").append(codError).append("'");
								}
							}
						}
						posibleWhereError.append(") ");
					}
					if(vaComma)
						whereError.append(posibleWhereError.toString());
				}

				
			//  3.Tipo Entidad
				StringBuffer whereEntidad = new StringBuffer();
				
				String ent_TE1 = request.getParameter("TE1");
				String ent_TE2 = request.getParameter("TE2");
				String ent_TE3 = request.getParameter("TE3");
				String ent_TE4 = request.getParameter("TE4");
				
				StringBuffer aux_entidad = new StringBuffer("");

				if(ent_TE1 != null && ent_TE1.equalsIgnoreCase("ON"))
					aux_entidad.append(",'TITU'");
					
				if(ent_TE2 != null && ent_TE2.equalsIgnoreCase("ON"))
					aux_entidad.append(",'PACO'");
					
				if(ent_TE3 != null && ent_TE3.equalsIgnoreCase("ON"))
					aux_entidad.append(",'PART'");
					
				if(ent_TE4 != null && ent_TE4.equalsIgnoreCase("ON"))
					aux_entidad.append(",'ASIE'");

				
				if(aux_entidad.toString().length() > 2)
					whereEntidad.append(" AND E.ENT_TIPO IN (").append(aux_entidad.toString().substring(1)).append(")");
				else
					whereEntidad.append("");
				
			//  4.Fecha Creación
				StringBuffer whereFechas = new StringBuffer("");
				
				StringBuffer feci = new StringBuffer(request.getParameter("diainicio"));
				feci.append("/").append(request.getParameter("mesinicio")).append("/").append(request.getParameter("anoinicio"));
				String fechaInicio = FechaUtil.stringToOracleString(feci.toString());
				
				StringBuffer fecf = new StringBuffer(request.getParameter("diafin"));
				fecf.append("/").append(request.getParameter("mesfin")).append("/").append(request.getParameter("anofin"));
				String fechaFin = FechaUtil.stringToOracleString23(fecf.toString());
				
				whereFechas.append(" AND E.TMSTMP_CREA BETWEEN ").append(fechaInicio).append(" AND ").append(fechaFin);


			//Oficinas
				java.util.Vector oficinas = new java.util.Vector();
				String arrayOficinas[] = request.getParameterValues("oficinas");
				
				for(int i = 0; i < arrayOficinas.length; i++)
					oficinas.add(arrayOficinas[i]);
				
				//SortedSet s = Collections.synchronizedSortedSet(new TreeSet(oficinas));			
				
				
				String sql = new StringBuffer(selectwhere).append(whereEstados.toString()).append(whereError.toString()).append(whereEntidad.toString()).append(whereFechas.toString()).append(order).toString();
				
				//Class.forName("oracle.jdbc.driver.OracleDriver");
				if (Loggy.isTrace(this)) System.out.print("SQL >> " + sql);
				String codOfic = null;
				String nomOfic = null;
				int contador = 0;
				
				String tabla = "<TABLE cellspacing=0 width=\"1130\" border=\"1\">";
				String finTabla = "</TABLE>";
				
				//for(java.util.Iterator it = s.iterator(); it.hasNext(); ){
				for(java.util.Iterator it = oficinas.iterator(); it.hasNext(); ){
					try{
						//Mostrar Cabecera
						out.write(tabla);
						out.write(cabecera.toString());
						out.write(finTabla);
						out.flush();

						//Datos de Oficina
						codOfic = it.next().toString();
						OficinaConection oficDB = (OficinaConection) ConsolaCentral.getInstance().getDbOficinas().get(codOfic);
						nomOfic = (String) DatosOficina.getInstance().getOficinas().get(codOfic);
						
						//Datos de Conexión de Oficina
						//conn = java.sql.DriverManager.getConnection(oficDB.getUrl(), oficDB.getUser(), oficDB.getPassword());
						conn = DBConnectionFactories.getInstance(codOfic).getConnection();
						stmt = conn.createStatement();
						rs = stmt.executeQuery(sql);
	
						boolean color = true;
						String TR = "<TR bgcolor=\"white\">";
						String TR_Color = "<TR bgcolor=\"#C0C0C0\">";
						
						SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						java.sql.Timestamp fecha_aux = null;
						
						while(rs.next()){
							contador++;
							color = !color;
						
							out.write(tabla);
							out.write(color?TR_Color:TR);
								out.write("<TD width=\"30\" align=\"center\">"); out.write(rs.getString(1)==null?"--":rs.getString(1)); out.write("</TD>");
								out.write("<TD width=\"40\" align=\"center\">"); out.write(rs.getString(2)==null?"--":rs.getString(2)); out.write("</TD>");
								out.write("<TD width=\"40\" align=\"center\">"); out.write(rs.getString(3)==null?"--":rs.getString(3)); out.write("</TD>");
								out.write("<TD width=\"35\" align=\"center\">"); out.write(rs.getString(4)==null?"--":rs.getString(4)); out.write("</TD>");
								out.write("<TD width=\"35\" align=\"center\">"); out.write(rs.getString(5)==null?"--":rs.getString(5)); out.write("</TD>");
								out.write("<TD width=\"70\" align=\"center\">"); out.write(rs.getString(6)==null?"--":rs.getString(6)); out.write("</TD>");
								out.write("<TD width=\"40\" align=\"center\">"); out.write(rs.getString(7)==null?"--":rs.getString(7)); out.write("</TD>");
								out.write("<TD width=\"50\" align=\"center\">"); out.write(rs.getString(8)==null?"--":rs.getString(8)); out.write("</TD>");
								out.write("<TD width=\"70\" align=\"center\">"); out.write(rs.getString(9)==null?"--":rs.getString(9)); out.write("</TD>");
								out.write("<TD width=\"50\" align=\"center\">"); out.write(rs.getString(10)==null?"--":rs.getString(10)); out.write("</TD>");
								out.write("<TD width=\"40\" align=\"center\">"); out.write(rs.getString(11)==null?"--":rs.getString(11)); out.write("</TD>");
								
								out.write("<TD width=\"70\" align=\"center\">");
								fecha_aux = rs.getTimestamp(12);
								if(fecha_aux != null) out.write(formatFecha.format(new java.sql.Date(fecha_aux.getTime()))); 
									else out.write("--");
								out.write("</TD>");
								
								out.write("<TD width=\"70\" align=\"center\">"); 
								fecha_aux = rs.getTimestamp(13);
								if(fecha_aux != null) out.write(formatFecha.format(new java.sql.Date(fecha_aux.getTime())));
									else out.write("--");
								out.write("</TD>");
								
								out.write("<TD width=\"70\" align=\"center\">"); 
								fecha_aux = rs.getTimestamp(14);
								if(fecha_aux != null) out.write(formatFecha.format(new java.sql.Date(fecha_aux.getTime())));
									else out.write("--");
								out.write("</TD>");

								out.write("<TD width=\"70\" align=\"center\">"); 
								fecha_aux = rs.getTimestamp(15);
								if(fecha_aux != null) out.write(formatFecha.format(new java.sql.Date(fecha_aux.getTime())));
									else out.write("--");
								out.write("</TD>");

								out.write("<TD width=\"70\" align=\"center\">"); 
								fecha_aux = rs.getTimestamp(16);
								if(fecha_aux != null) out.write(formatFecha.format(new java.sql.Date(fecha_aux.getTime())));
									else out.write("--");
								out.write("</TD>");
								
								out.write("<TD width=\"50\" align=\"center\"><a href=\"javascript:\" title=\""); out.write(descripcion(conn, rs.getString(17), hash)); out.write("\">"); out.write(rs.getString(17)==null?"--":rs.getString(17)); out.write("</a></TD>");
							out.write("</TR>");
							out.write(finTabla);
								out.flush();
						}
						out.write(tabla);
						color = !color;
						out.write(color?TR_Color:TR);
							out.write("<TD colspan=\"17\"><CENTER>* FIN OFICINA: ");
							out.write(nomOfic==null?"":nomOfic);
							out.write(" *</CENTER></TD>");
						out.write("</TR>");
						out.write(finTabla);
						
						out.write("<BR><P>");
						conn.commit();
					}catch(Exception ex){
						if(conn != null)
							try{
								conn.rollback();
							}catch(Exception e){}
						out.write(tabla);
						out.write("<TR bgcolor=\"red\">");
							out.write("<TD colspan=\"17\"><CENTER>* ERROR DE CONEXION O DATA EN OFICINA: ");
							out.write(nomOfic==null?"":nomOfic);
							out.write(" *</CENTER></TD>");
						out.write("</TR>");
						out.write(finTabla);
						out.write("<BR><P>");
						out.flush();
					}finally{
						if(rs != null)
							try{
								rs.close();
							}catch(Exception e){}
						
						if(stmt != null)
							try{
								stmt.close();
							}catch(Exception e){}
			
						if(conn != null)
							try{
								//conn.close();
								DBConnectionFactories.getInstance(codOfic).release(conn);
							}catch(Exception e){}
					}
				}
				out.flush();
		}catch(Exception ex){
			if (Loggy.isTrace(this)) System.out.print("ERROR en la ejecución de consola Central: " + ex.getMessage());
		}
	}
	
	public String descripcion(Connection conn, String codError, HashMap hash) throws java.sql.SQLException{
		if(codError == null)
			return "Sin descripción. Verificar Estado de Transmisión.";
			
		java.sql.PreparedStatement pst = null;
		java.sql.ResultSet rs = null;
		
		try{
			if(hash.containsKey(codError))
				return (String) hash.get(codError);
			else{
				pst = conn.prepareStatement("SELECT ERR_DESCRIPCION FROM EXTRANET.TM_ERROR WHERE ERROR_CODIGO = ?");
				pst.setString(1, codError);
				rs = pst.executeQuery();
				
				if(rs.next()){
					hash.put(codError, rs.getString(1));
					return rs.getString(1);
				}else
					return "";
			}
		}catch(java.sql.SQLException sqle){
			throw sqle;
		}finally{
			if(rs != null)
				try{rs.close();
				}catch(Exception e){}
			if(pst != null)
				try{pst.close();
				}catch(Exception e){}
		}
	}
	
	public void doGet(
		javax.servlet.http.HttpServletRequest request,
		javax.servlet.http.HttpServletResponse response)
		throws javax.servlet.ServletException, java.io.IOException {
			
		doPost(request, response);
	}

}
