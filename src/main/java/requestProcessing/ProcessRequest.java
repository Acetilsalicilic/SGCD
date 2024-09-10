/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestProcessing;

import DAO.EntityDAOPool;
import Records.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ProcessRequestMethod;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author W10
 */
public final class ProcessRequest {
    private static String url = "jdbc:mysql://localhost:3306/lab4";
    private static String username = "root";
    private static String password = "pass";
    static {
        EntityDAOPool.init(url, username, password);
    }
    private static EntityDAOPool pool = EntityDAOPool.instance();
    private static ObjectMapper mapper = new ObjectMapper();
    
    private static void setResponse(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
    
    public static ProcessRequestMethod getUsuario = (request, response) -> {
        setResponse(response);
        
        var session = request.getSession(false);
        
        if (session == null || session.getAttribute("auth").equals("false")) {
            try (var out = response.getWriter()) {
                out.print("{\"error\":\"no auth\"}");
                return;
            }
        }
        
        try (var out = response.getWriter()) {
            int id;
            
            try {
                id = Integer.parseInt(request.getParameter("id"));
            } catch (Exception e) {
                out.print("null");
                return;
            }
            
            Usuario usuario = pool.getUsuarioDAO().getById(id);

            String json = mapper.writeValueAsString(usuario);
            out.print(json);
        }
    };
    
    public static ProcessRequestMethod deleteUsuario = (req, res) -> {
        setResponse(res);
        
        try (var out = res.getWriter()) {
            String id = req.getParameter("id");

            int rs = pool.getUsuarioDAO().deleteById(Integer.parseInt(id));
            
            if (rs == -1) {
                out.print("{\"error\":\"couldn't delete\"}");
            } else if (rs == 0) {
                out.print("{\"error\":\"no rows deleted\"}");
            } else {
                out.print("{\"success\":\"" + rs + "\"}");
            }
        }
    };
    
    public static ProcessRequestMethod getPaciente = (req, res) -> {
        setResponse(res);
        
        try (var out = res.getWriter()) {
            
        }
    };

    public static ProcessRequestMethod authUser = (req, res) -> {
        String user = req.getParameter("user");
        String pass = req.getParameter("password");

        var usuarios = pool.getUsuarioDAO().getAll();
        for (var usuario : usuarios) {
            if (usuario.nombre_usuario().equals(user) && usuario.contrasena().equals(pass)) {
                var session = req.getSession(true);
                session.setAttribute("auth", usuario.tipo());
                if (usuario.tipo().equals("admin")) {
                    res.sendRedirect("/usuarios");
                }
                if (usuario.tipo().equals("paciente")) {
                    res.sendRedirect("/inicio-paciente");
                }
                break;
            }
        }

        if (req.getSession().getAttribute("auth") == null) {
            res.sendRedirect("/");
        }
    };
}
