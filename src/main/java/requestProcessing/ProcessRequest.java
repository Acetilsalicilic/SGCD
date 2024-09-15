/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package requestProcessing;

import static Auth.Authorize.authPermission;
import DAO.EntityDAOPool;
import Records.Medico;
import Records.Paciente;
import Records.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ProcessRequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
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
        setResponse(res);
        var auth = authPermission(req.getSession(false), requiredLevel);

        if (!auth) {
            System.out.println("Access denied for auth value " + req.getSession(false).getAttribute("auth") + " in servlet " + req.getServletPath());
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
                session.setAttribute("auth", usuario.tipoUsuario().desc_tipo());
                session.setAttribute("username", usuario.nombre_usuario());

                setResponse(res);
                String response = "{\"auth_correct\":\"" + usuario.tipoUsuario().desc_tipo() + "\"}";
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
        setResponse(res);

        if (!authAccess(req, res, "admin")) {
            return;
        }

        var json = mapper.readValue(req.getReader(), jsonReference);

        var usuario = new Usuario(
                pool.getTipoUsuarioDAO().getByDesc(json.get("tipo_usuario")),
                json.get("usuario"),
                json.get("contrasena")
        );

        var paciente = new Paciente(
                json.get("nombre"),
                json.get("apellidos"),
                json.get("telefono"),
                json.get("direccion")
        );

        int usuarioRs = pool.getUsuarioDAO().create(usuario);
        int id_usuario = pool.getUsuarioDAO().getByUsername(json.get("usuario")).id_usuario();

        int pacienteRs = pool.getPacienteDAO().create(paciente, id_usuario);

        try (var out = res.getWriter()) {
            if (usuarioRs == -1) {
                out.print("{\"error\":\"couldn't insert user\"}");
                return;
            }
            if (pacienteRs == -1) {
                out.print("{\"error\":\"couldn't insert paciente\"}");
                return;
            }

            out.print("{\"status\":\"ok\"}");
        }

    };

    public static ProcessRequestMethod deletePaciente = (req, res) -> {
        setResponse(res);

        if (!authAccess(req, res, "admin")) {
            return;
        }

        var json = mapper.readValue(req.getReader(), jsonReference);

        String id_paciente = json.get("paciente_id");
        System.out.println("id paciente " + id_paciente);

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

        var paciente = pool.getPacienteDAO().getById(Integer.parseInt(id_paciente));
        var rs = pool.getPacienteDAO().deletePacienteById(Integer.parseInt(id_paciente));
        var rsUst = pool.getUsuarioDAO().deleteById(paciente.usuario().id_usuario());

        try (var out = res.getWriter()) {
            out.print("{\"status\":\"ok\"}");
        }
    };

    public static ProcessRequestMethod getPaciente = (req, res) -> {
        if (!authAccess(req, res, "admin")) {
            return;
        }
        setResponse(res);

        String type = req.getParameter("type");
        String query = req.getParameter("query");

        if (type == null || (!type.equals("name") && !type.equals("id"))) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"invalid type\"}");
                return;
            }
        }

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

        try (var out = res.getWriter()) {
            out.print(responseJson);
        }
    };

    public static ProcessRequestMethod patchPaciente = (req, res) -> {
        if (!authAccess(req, res, "admin")) {
            return;
        }
        System.out.println("SOMEONE IS PUTTING SOME SHIT HERE");
        setResponse(res);
        var dao = pool.getPacienteDAO();

        var json = mapper.readValue(req.getReader(), jsonReference);
        String[] datos = new String[7];

        datos[0] = json.get("nombre");
        datos[1] = json.get("apellidos");
        datos[2] = json.get("telefono");
        datos[3] = json.get("direccion");
        datos[4] = json.get("usuario");
        datos[5] = json.get("contrasena");
        datos[6] = json.get("id");

        var usuario = pool.getUsuarioDAO().getByUsername(json.get("old_username"));

        System.out.println(usuario);
        var paciente = new Paciente(
                Integer.parseInt(datos[6]),
                usuario,
                datos[0],
                datos[1],
                datos[2],
                datos[3]
        );

        var rsUs = pool.getUsuarioDAO().update(new Usuario(
                usuario.id_usuario(),
                usuario.tipoUsuario(),
                datos[4],
                datos[5]
        ));

        var rs = dao.updatePaciente(paciente);

        try (var out = res.getWriter()) {
            if (rs != 1) {
                out.print("{\"error\":\"couldn't update paciente\"}");
                return;
            }

            if (rsUs != 1) {
                out.print("{\"error\":\"couldn't update user\"}");
                return;
            }

            out.print("{\"status\":\"ok\"}");
        }

    };

    //-------------------------MEDICO METHODS-------------------
    public static ProcessRequestMethod getMedico = (req, res) -> {
        setResponse(res);
        if (!authAccess(req, res, "admin")) {
            return;
        }

        String type = req.getParameter("type");
        String query = req.getParameter("query");

        if (!type.equals("name") && !type.equals("specialty")) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"invalid type\"}");
            }
        }

        ArrayList<Medico> medicos;

        if (type.equals("name")) {
            medicos = pool.getMedicoDAO().getByNombre(query);
        } else {
            medicos = pool.getMedicoDAO().getBySpeciality(query);
        }

        try (var out = res.getWriter()) {
            out.print(mapper.writeValueAsString(medicos));
        }
    };

    public static ProcessRequestMethod postMedico = (req, res) -> {
        System.out.println("Posting of medico");
        setResponse(res);
        if (!authAccess(req, res, "admin")) {
            return;
        }

        var json = mapper.readValue(req.getReader(), jsonReference);

        var values = json.entrySet();

        for (var value : values) {
            if (value == null) {
                try (var out = res.getWriter()) {
                    out.print("{\"error\":\"invalid parameter\"}");
                }
                return;
            }
        }

        Usuario usuario = new Usuario(
                pool.getTipoUsuarioDAO().getByDesc(json.get("desc_tipo")),
                json.get("nombre_usuario"),
                json.get("contrasena")
        );

        var especialidad = pool.getEspecialidadDAO().getByDesc(json.get("especialidad"));

        var rsUs = pool.getUsuarioDAO().create(usuario);

        usuario = pool.getUsuarioDAO().getByUsername(usuario.nombre_usuario());
        System.out.println("Usuario del medico: " + usuario);

        Medico medico = new Medico(
                usuario,
                especialidad,
                json.get("nombre"),
                json.get("apellidos")
        );

        var rsMd = pool.getMedicoDAO().createMedico(medico);

        if (rsUs < 0 || rsMd < 0) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"couldn't create medico\"}");
            }
            return;
        } else {
            try (var out = res.getWriter()) {
                out.print("{\"status\":\"ok\"}");
            }
        }
    };

    public static ProcessRequestMethod deleteMedico = (req, res) -> {
        setResponse(res);
        if (!authAccess(req, res, "admin")) {
            return;
        }

        var json = mapper.readValue(req.getReader(), jsonReference);

        Integer id = Integer.parseInt(json.get("id"));

        if (id == null) {
            try (var out = res.getWriter()) {
                out.print("{\"error\":\"invalid id\"}");
                return;
            }
        }

        var medico = pool.getMedicoDAO().getById(id);
        int id_usuario = medico.usuario().id_usuario();

        var rsMd = pool.getMedicoDAO().deleteMedicoById(id);
        var rsUs = pool.getUsuarioDAO().deleteById(id_usuario);

        try (var out = res.getWriter()) {
            if (rsMd < 0) {
                out.print("{\"error\":\"couldn't delete medico\"}");
                return;
            }
            if (rsUs < 0) {
                out.print("{\"error\":\"couldn't delete user\"}");
                return;
            }

            out.print("{\"status\":\"ok\"}");
        }
    };

    public static ProcessRequestMethod putMedico = (req, res) -> {
        setResponse(res);
        if (!authAccess(req, res, "admin")) {
            return;
        }

        var json = mapper.readValue(req.getReader(), jsonReference);

        String[] data = new String[8];

        data[0] = json.get("id");
        data[1] = json.get("nombre");
        data[2] = json.get("apellidos");
        data[3] = json.get("especialidad");
        data[4] = json.get("nombre_usuario");
        data[5] = json.get("contrasena");
        data[6] = json.get("id_tipo");
        data[7] = json.get("id_usuario");

        for (var field : data) {
            if (field == null) {
                try (var out = res.getWriter()) {
                    out.print("{\"error\":\"invalid field\"}");
                    return;
                }
            }
        }

        var usuario = new Usuario(
                Integer.parseInt(data[7]),
                pool.getTipoUsuarioDAO().getTypeUser(Integer.parseInt(data[6])),
                data[4],
                data[5]
        );

        var rsUs = pool.getUsuarioDAO().update(usuario);
        var medico = new Medico(
                Integer.parseInt(data[0]),
                usuario,
                pool.getEspecialidadDAO().getByDescription(data[3]),
                data[1],
                data[2]
        );

        var rsMd = pool.getMedicoDAO().updateMedico(medico);

        try (var out = res.getWriter()) {
            if (rsMd < 0) {
                out.print("{\"error\":\"couldn't update medico\"}");
                return;
            }
            if (rsUs < 0) {
                out.print("{\"error\":\"couldn't update user\"}");
                return;
            }

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

    public static ProcessRequestMethod getCita = (req, res) -> {
        setResponse(res);
        if (!authAccess(req, res, "paciente")) {
            return;
        }

        String type = req.getParameter("type");

        if (type.equals("available-medicos")) {
            var medicos = pool.getMedicoDAO().getAll();
            var resJson = mapper.writeValueAsString(medicos);

            try (var out = res.getWriter()) {
                out.print(resJson);
                return;
            }
        }

        if (type.equals("available-times")) {
            int id_medico = Integer.parseInt(req.getParameter("id_medico"));
            Integer[] timeData = new Integer[3];

            timeData[0] = Integer.parseInt(req.getParameter("year"));
            timeData[1] = Integer.parseInt(req.getParameter("month"));
            timeData[2] = Integer.parseInt(req.getParameter("day"));

            var date = LocalDate.of(timeData[0], timeData[1], timeData[2]);
            var times = pool.getCitaDAO().horasDisponiblesCitas(id_medico, date);
            var resJson = mapper.writeValueAsString(times);

            try (var out = res.getWriter()) {
                out.print(resJson);
                return;
            }
        }
    };
}
