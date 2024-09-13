/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestProcessing;

import static Auth.Authorize.authPermission;
import DAO.EntityDAOPool;
import Records.Paciente;
import Records.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ProcessRequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    private static TypeReference<HashMap<String, String>> jsonReference = new TypeReference<>() {
    };
    
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

        var json = mapper.readValue(req.getReader(), jsonReference);

        System.out.println(json);

        String username = json.get("username");
        String password = json.get("password");

        if (username == null || password == null) {
            setResponse(res);
            System.out.println("INVALID LOGIN CREDENTIALS!!");

            try (var out = res.getWriter()) {
                out.print("{\"auth_error\":\"invalid credentials\"}");
            }
            return;
        }

        var usuarios = pool.getUsuarioDAO().getAll();
        for (var usuario : usuarios) {
            if (usuario.nombre_usuario().equals(username) && usuario.contrasena().equals(password)) {
                System.out.println("Login correct with credentials " + username + " " + password);

                var session = req.getSession(true);
                session.setAttribute("auth", usuario.desc_tipo());

//                res.sendRedirect("/admin");
                setResponse(res);
                String response = "{\"auth_correct\":\"" + usuario.desc_tipo() + "\"}";
                System.out.println(response);
                try (var out = res.getWriter()) {
                    out.print(response);
                }
                return;
            }
        }

        try (var out = res.getWriter()) {
            setResponse(res);
            out.print("{\"auth_error\":\"wrong credentials\"}");
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

    };

    public static ProcessRequestMethod deletePaciente = (req, res) -> {
        setResponse(res);

        if (!authAccess(req, res, "admin")) {
            return;
        }

        var json = mapper.readValue(req.getReader(), jsonReference);

        String id_paciente = json.get("id_paciente");
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

    public static ProcessRequestMethod getPaciente = (req, res) -> {
        setResponse(res);

        var json = mapper.readValue(req.getReader(), jsonReference);

        System.out.println("json: " + json);
        String type = json.get("type");
        System.out.println("thing in type: " + type);

        if (type == null || (!type.equals("name") && !type.equals("id"))) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"invalid type\"}");
                return;
            }
        }

        String query = json.get("query");

        if (type.equals("id") && !Utils.isNumeric(query)) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"invalid id\"}");
                return;
            }
        }

        var allPacientes = pool.getPacienteDAO().getAll();
        Stream streamPacientes;

        if (type.equals("name"))
            streamPacientes = allPacientes.stream().filter(paciente -> paciente.nombre().toLowerCase().contains(query.toLowerCase()));
        else
            streamPacientes = allPacientes.stream().filter(paciente -> paciente.id_paciente().intValue() == Integer.parseInt(query));

        var pacientes = (ArrayList<Paciente>) streamPacientes.collect(Collectors.toCollection(ArrayList::new));
        var responseMap = new HashMap<String, ArrayList<Paciente>>();
        responseMap.put("pacientes", pacientes);

        var responseJson = mapper.writeValueAsString(responseMap);
        System.out.println("response json: " + responseJson);

        try (var out = res.getWriter()) {
            out.print(responseJson);
        }
    };

    public static ProcessRequestMethod patchPaciente = (req, res) -> {

    };

    //--------------------------CITA METHODS---------------------
    public static ProcessRequestMethod postCita = (req, res) -> {
        setResponse(res);
        if (!authAccess(req, res, "admin")) {
            return;
        }
    };

}
