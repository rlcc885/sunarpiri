/*
 * BuscarEmpresaServlet.java
 *
 * Created on 19 de mayo de 2006, 11:39 PM
 */

package gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.servlet;

import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.bean.Empresa;

import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.list.EmpresaList;
import gob.pe.sunarp.extranet.publicidad.busquedagarantiascontratos.logic.UsuarioLogic;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Christian
 * @version
 */
public class BuscarEmpresaServlet extends HttpServlet {
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String res1 = " ";
        String sql1 = " ";
        String sql2 = " ";
        int compa = 0;
        compa = Integer.parseInt(request.getParameter("combo1"));
        Empresa e = new Empresa();
        e.setRazon_social(request.getParameter("razonsocial"));
        e.setSiglas(request.getParameter("siglas"));
        if(compa==1){
            sql1 = " AND PLIB.NOMBRE IN ('ACREEDOR','DEUDOR','CONSTITUYENTE','DEPOSITARIO')";
        }else if(compa==2){
            sql1 = "AND PLIB.NOMBRE = ACREEDOR ";
        }else if(compa==3){
            sql1 = "AND PLIB.NOMBRE = DEUDOR ";
        }else if(compa==4){
            sql1 = "AND PLIB.NOMBRE = CONTRIBUYENTE ";
        }else if(compa==5){
            sql1 = "AND PLIB.NOMBRE = DEPOSITARIO ";
        }
        if(request.getParameter("estado")!=null){
            sql2 = "AND IND.ESTADO IN ('1','0') ";
        }else {
            
            sql2 ="AND IND.ESTADO='1' " ;
        }
        res1 = sql1 + sql2;
        e.setSql(res1);
        EmpresaList List = new EmpresaList();
        String res = UsuarioLogic.BuscarEmpresa(e,List);
        if(List.size()==0){
            PrintWriter out = response.getWriter();
            out.println("<h1><center>No se encontro Cliente</center></h1>");
            out.println("<h3><center>(o escriba bien los datos)</center></h3>");
        }else{
            request.setAttribute("lista",List);
            String url = "/publicidad/busquedagarantiascontratos/resultadobusqueda2.jsp";
            getServletContext().getRequestDispatcher(url).forward(request,response);
        }
        
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
