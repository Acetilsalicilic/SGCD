/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package apiServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import requestProcessing.ProcessRequest;

/**
 *
 * @author W10
 */
@WebServlet(name="CitaServlet", urlPatterns={"/api/citas"})
public class CitaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProcessRequest.getCita.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProcessRequest.postCita.process(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

}
