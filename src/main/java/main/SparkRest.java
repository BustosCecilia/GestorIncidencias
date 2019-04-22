package main;

import com.google.gson.Gson;
import enums.StatusResponse;
import incidente.IncidenteService;
import incidente.IncidenteServiceMapImpl;
import proyecto.Proyecto;
import proyecto.ProyectoService;
import proyecto.ProyectoServiceMapImpl;
import standarResponse.StandardResponse;
import usuario.Usuario;
import usuario.UsuarioService;
import usuario.UsuarioServiceMapImpl;

import static spark.Spark.*;

public class SparkRest {

    public static void main(String[] args) {
        final IncidenteService incidenteService = new IncidenteServiceMapImpl();
        final UsuarioService usuarioService = new UsuarioServiceMapImpl();
        final ProyectoService proyectoService = new ProyectoServiceMapImpl();

        /** metodos que carga datos en memoria */
        cargarDatos(incidenteService, usuarioService, proyectoService);

        /** servicios asociados a Usuario */
        post("/usuario", (request, response) -> {
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            usuarioService.addUsuario(usuario);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/usuario", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(usuarioService.getUsuarios())));
        }));

        get("/usuario/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(usuarioService.getUsuario(request.params(":id")))));
        }));

        put("/usuario/", (request, response) -> { // meter esto en un try catch
            response.type("application/json");
            Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);
            Usuario usuarioEditado = usuarioService.modifyUsuario(usuario);
            if (usuarioEditado != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(usuarioEditado)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                        "Error al editar el usuario"));
            }
        });

        delete("/usuario/:id", ((request, response) -> {
            response.type("application/json");
            usuarioService.deleteUsuario(request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "usuario borrado."));
        }));

        options("/usuario/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    (usuarioService.existUsuario(request.params(":id")) ?
                            "El usuario existe." : "El usuario no existe")));
        }));

        /** metodos Proyectos */
        post("/proyecto", (request, response) -> {
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            proyectoService.addProyecto(proyecto);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/proyecto", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.getProyectos())));
        }));

        get("/proyecto/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(proyectoService.getProyecto(request.params(":id")))));
        }));

        put("/proyecto/", (request, response) -> { // meter esto en un try catch
            response.type("application/json");
            Proyecto proyecto = new Gson().fromJson(request.body(), Proyecto.class);
            Proyecto proyectoEditado = proyectoService.modifyProyecto(proyecto);
            if (proyectoEditado != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(proyectoEditado)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                        "Error al editar el usuario"));
            }
        });

        delete("/proyecto/:id", (request, response) -> {
            response.type("application/json");
            proyectoService.deleteProyecto(request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "proyecto borrado."));
        });

        options("/proyecto/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    (proyectoService.existProyecto(request.params(":id")) ?
                            "El proyecto existe." : "El proyecto no existe")));
        }));


    }

    static void cargarDatos(IncidenteService incidenteService, UsuarioService usuarioService,
                            ProyectoService proyectoService) {
        Usuario usuario = new Usuario("1", "Cecilia", "Bustos");
        Usuario usuario1 = new Usuario("2", "Maria", "Figueroa");
        Usuario usuario2 = new Usuario("3", "Gisela", "Garcia");
        usuarioService.addUsuario(usuario);
        usuarioService.addUsuario(usuario1);
        usuarioService.addUsuario(usuario2);

        Proyecto proyecto = new Proyecto("1","app",usuario);
        Proyecto proyecto1 = new Proyecto("2", "scrum", usuario2);


    }
}
