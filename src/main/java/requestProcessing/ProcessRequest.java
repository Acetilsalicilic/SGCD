/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestProcessing;

import static Auth.Authorize.authPermission;
import DAO.EntityDAOPool;
import Records.Paciente;
import Records.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ProcessRequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import util.Utils;

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

    //--------------AUTH METHODS---------------
    private static boolean authAccess(HttpServletRequest req, HttpServletResponse res, String requiredLevel) throws IOException {
        var auth = authPermission(req.getSession(false), requiredLevel);

        if (!auth) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"no auth\"}");
            }
            return false;
        }

        return true;
    }

    //--------------------METHDOS---------------
    public static ProcessRequestMethod ping = (req, res) -> {
        setResponse(res);

        try (var out = res.getWriter()) {
            out.print("{\"api_status\":\"ok\"}");
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
                    res.sendRedirect("/admin");
                }
                if (usuario.tipo().equals("paciente")) {
                    res.sendRedirect("/paciente");
                }
                break;
            }
        }

        if (req.getSession().getAttribute("auth") == null) {
            res.sendRedirect("/");
        }
    };

    //---------------------USUARIOS METHODS--------------
    public static ProcessRequestMethod getUsuario = (request, response) -> {
        setResponse(response);
        
        if (!authAccess(request, response, "admin")) {
            return;
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

        if (!authAccess(req, res, "admin")) {
            return;
        }
        
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

    //---------------------------PACIENTES METHODS--------------
    public static ProcessRequestMethod postPaciente = (req, res) -> {
        setResponse(res);

        if (!authAccess(req, res, "admin")) {
            return;
        }

        String[] datos = new String[4];
        datos[0] = req.getParameter("nombre");
        datos[1] = req.getParameter("apellidos");
        datos[2] = req.getParameter("telefono");
        datos[3] = req.getParameter("direccion");

        for (String dato : datos) {
            if (dato == null) {
                res.sendRedirect("/admin/pacientes");
                return;
            }
        }

        var rs = pool.getPacienteDAO().create(new Paciente(
                1,
                datos[0],
                datos[1],
                datos[2],
                datos[3],
                new Usuario(
                        1,
                        datos[0],
                        "asd",
                        "paciente"
                )
        ));

        if (rs == 1) {
            req.setAttribute("create-status", "{\"status\":\"ok\"}");
        } else {
            req.setAttribute("create-status", "{\"status\":\"error\"}");
        }

        req.getRequestDispatcher("/admin/pacientes").forward(req, res);
    };

    public static ProcessRequestMethod deletePaciente = (req, res) -> {
        setResponse(res);

        if (!authAccess(req, res, "admin")) {
            return;
        }

        String id_paciente = req.getParameter("id_paciente");
        System.out.println("id paciente" + id_paciente);

        if (id_paciente == null) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"empty id\"}");
            }
            return;
        }
        if (!Utils.isNumeric(id_paciente)) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"invalid id\"}");
            }
            return;
        }

        var rs = pool.getPacienteDAO().deleteById(Integer.parseInt(id_paciente));

        try (var out = res.getWriter()) {
            out.print("{\"status\":\"ok\"}");
        }
    };

    //--------------------------CITA METHODS---------------------
    public static ProcessRequestMethod postCita = (req, res) -> {
        setResponse(res);
        if (!authAccess(req, res, "admin")) {
            return;
        }
    };

}
