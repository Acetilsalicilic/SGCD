/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package apiServlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import interfaces.GeneralDAO;


/**
 *
 * @author W10
 */
//@WebServlet("/api/sucursal")
public class SucursalServlet extends HttpServlet {

//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet GetSucursalServlet</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet GetSucursalServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Get the parameters
//        
//        String ID = request.getParameter("id");
//        
//        try (PrintWriter out = response.getWriter()) {
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            //GeneralDAO pxdao = ProxyDAO.getInstance();
//            JdbcDao.init("jdbc:mysql://localhost:3306/jdbcdb", "root", "pass");
//            GeneralDAO pxdao = JdbcDao.instance();
//            var sc = pxdao.getSucursal(ID);
//            var json = new ObjectMapper().writeValueAsString(sc);
//            System.out.println(json);
//            out.print(json);
//            out.flush();
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        ObjectMapper mp = new ObjectMapper();
//        Sucursal data = mp.readValue(request.getInputStream(), Sucursal.class);
//        
//        var pxdao = ProxyDAO.getInstance();
//        
//        pxdao.addSucursal(data);
//        System.out.println("added sucursal " + data);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>

}
