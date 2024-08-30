/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestProcessing;

import DAO.JdbcDao;
import Records.Paciente;
import Records.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ProcessRequestMethod;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author W10
 */
public final class ProcessRequest {
    private static String url = "jdbc:mysql://localhost:3306/lab4";
    private static String username = "root";
    private static String password = "pass";
    static {
        JdbcDao.init(url, username, password);
    }
    private static JdbcDao dao = JdbcDao.instance();
    private static ObjectMapper mapper = new ObjectMapper();
    
    private static void setResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
    
    public static ProcessRequestMethod getUsuario = (request, response) -> {
        setResponse(response);
        
        try (var out = response.getWriter()) {
            String id = request.getParameter("id");
            
            Usuario usuario = dao.getUsuario(id);

            String json = mapper.writeValueAsString(usuario);
            out.print(json);
        }
    };
    
    public static ProcessRequestMethod getPaciente = (req, res) -> {
        setResponse(res);
        
        try (var out = res.getWriter()) {
            String id = req.getParameter("id");
            
            Paciente paciente = dao.getPaciente(id);
            
            out.print(mapper.writeValueAsString(paciente));
        }
    };
}
