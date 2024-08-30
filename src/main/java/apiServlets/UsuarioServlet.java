/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package apiServlets;

import DAO.JdbcDao;
import Records.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requestProcessing.ProcessRequest;

/**
 *
 * @author W10
 */
@WebServlet(name="UsuarioServlet", urlPatterns={"/api/usuarios"})
public class UsuarioServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ProcessRequest.getUsuario.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        throw new UnsupportedOperationException("Not suppoerted yet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
