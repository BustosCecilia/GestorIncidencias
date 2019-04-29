package main;

import com.google.gson.Gson;
import enums.Clasificacion;
import enums.Estado;
import enums.StatusResponse;
import incidente.Incidente;
import incidente.IncidenteService;
import incidente.IncidenteServiceMapImpl;
import proyecto.Proyecto;
import proyecto.ProyectoService;
import proyecto.ProyectoServiceMapImpl;
import standarResponse.StandardResponse;
import usuario.Usuario;
import usuario.UsuarioService;
import usuario.UsuarioServiceMapImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;
public class SparkRest {

    public static void main(String[] args) throws ParseException {
        final IncidenteService incidenteService = new IncidenteServiceMapImpl();
        final UsuarioService usuarioService = new UsuarioServiceMapImpl();
        final ProyectoService proyectoService = new ProyectoServiceMapImpl();

        port(4567);
        /** metodos que carga datos en memoria */
        cargarDatos(incidenteService, usuarioService, proyectoService);

        /** metodos asociados a Usuario *******************************************************************************/

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

        /*
         * metodo get que me devuelve todos los proyectos que un usuario es propietario
         */

        get("/usuario/:id/proyectos", (request, response) -> {
            response.type("application/json");
            List<Proyecto> listProyectos = new ArrayList<>();
            int cantidadProyectos = proyectoService.getProyectos().size();
            if (usuarioService.existUsuario(request.params(":id"))) {
                for (int i = 1; i <= cantidadProyectos; i++) {
                    if (usuarioService.getUsuario(request.params(":id")) == proyectoService.getProyecto(String.valueOf(i)).
                            getPropietario()) {
                        listProyectos.add(proyectoService.getProyecto(String.valueOf(i)));
                    }
                }
                if (listProyectos.isEmpty()) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            "No es propietario de ningun proyecto"));
                }
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(listProyectos)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        "Usuario no existe"));
            }
        });

        /*
         * metodo get que me devuelve  todos los incidentes asignados a un usuario
         */

        get("/usuario/:id/incidentesReportados", (request, response) -> {
            response.type("application/json");
            List<Incidente> listIncidentes = new ArrayList<>();
            int cantidadIncidentes = incidenteService.getIncidentes().size();
            if (usuarioService.existUsuario(request.params(":id"))) {
                for (int i = 1; i <= cantidadIncidentes; i++) {
                    if (usuarioService.getUsuario(request.params(":id")) == incidenteService.
                            getIncidente(String.valueOf(i)).getReportador()) {
                        listIncidentes.add(incidenteService.getIncidente(String.valueOf(i)));
                    }
                }
                if (listIncidentes.isEmpty()) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            "No es creador de ningun Incidente"));
                }
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(listIncidentes)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        "Usuario no existe"));
            }
        });
        /*
         * metodo get que me devuelve  todos los incidentes asignados a un usuario
         */

        get("/usuario/:id/incidentesResponsable", (request, response) -> {
            response.type("application/json");
            List<Incidente> listIncidentes = new ArrayList<>();
            int cantidadIncidentes = incidenteService.getIncidentes().size();
            if (usuarioService.existUsuario(request.params(":id"))) {
                for (int i = 1; i <= cantidadIncidentes; i++) {
                    if (usuarioService.getUsuario(request.params(":id")) == incidenteService.
                            getIncidente(String.valueOf(i)).getResponsable()) {
                        listIncidentes.add(incidenteService.getIncidente(String.valueOf(i)));
                    }
                }
                if (listIncidentes.isEmpty()) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            "No tiene ningun incidente asignado"));
                }
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(listIncidentes)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        "Usuario no existe"));
            }
        });
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
            int cantidadProyectos = proyectoService.getProyectos().size();
            int cantidadIncidentes = incidenteService.getIncidentes().size();
            for (int i = 1; i <= cantidadProyectos; i++) {
                if (usuarioService.getUsuario(request.params(":id")) == proyectoService.
                        getProyecto(String.valueOf(i)).getPropietario()) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            "usuario es propietario del proyecto id="+i));
                }
            }
            for (int i = 1; i <= cantidadIncidentes; i++) {
                if (usuarioService.getUsuario(request.params(":id")) == incidenteService.
                        getIncidente(String.valueOf(i)).getReportador()) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            "usuario es Reportador del proyecto id=" + i));
                } else {
                    if (usuarioService.getUsuario(request.params(":id")) == incidenteService.
                            getIncidente(String.valueOf(i)).getResponsable()) {
                        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                                "usuario es Responsable del proyecto id=" + i));
                    }


                }
            }
            usuarioService.deleteUsuario(request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "usuario borrado."));
        }));

        options("/usuario/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    (usuarioService.existUsuario(request.params(":id")) ?
                            "El usuario existe." : "El usuario no existe")));
        }));

        /** metodos asociado a Proyectos ******************************************************************************/

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

        /*
         * metodo get que me devuelve  todos los incidentes asociados a un proyecto
         */

        get("/proyecto/:id/incidentes", (request, response) -> {
            response.type("application/json");
            List<Incidente> listIncidentes = new ArrayList<>();
            int cantidadIncidentes = incidenteService.getIncidentes().size();
            if (proyectoService.existProyecto(request.params(":id"))) {
                for (int i = 1; i <= cantidadIncidentes; i++) {
                    if (proyectoService.getProyecto(request.params(":id")) == incidenteService.
                            getIncidente(String.valueOf(i)).getProyecto()) {
                        listIncidentes.add(incidenteService.getIncidente(String.valueOf(i)));
                    }
                }
                if (listIncidentes.isEmpty()) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            "No tiene ningun incidente asignado"));
                }
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(listIncidentes)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        "Proyecto no existe"));
            }
        });
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
            int cantidadIncidentes = incidenteService.getIncidentes().size();
            for (int i = 1; i <= cantidadIncidentes; i++) {
                if (proyectoService.getProyecto(request.params(":id")) == incidenteService.
                        getIncidente(String.valueOf(i)).getProyecto()) {
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                            "proyecto tiene incidentes reportados"));
                }
            }
            proyectoService.deleteProyecto(request.params(":id"));
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "proyecto borrado."));
        });

        options("/proyecto/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    (proyectoService.existProyecto(request.params(":id")) ?
                            "El proyecto existe." : "El proyecto no existe")));
        }));

        /** metodos asociados a Incidentes*****************************************************************************/

        post("/incidente", (request, response) -> {
            response.type("application/json");
            Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);
            incidenteService.addIncidente(incidente);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/incidente", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidentes())));
        }));

        get("/incidente/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    new Gson().toJsonTree(incidenteService.getIncidente(request.params(":id")))));
        }));

        put("/incidente", (request, response) -> { // meter esto en un try catch
            response.type("application/json");
            Incidente incidente = new Gson().fromJson(request.body(), Incidente.class);
            Incidente incidenteEditado = incidenteService.modifyIncidente(incidente);
            if (incidenteEditado != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        new Gson().toJsonTree(incidenteEditado),
                        "SOLO SE PUEDE MODIFICAR ESTADO Y AÑADIR TEXTO A DESCRIPCION"));

            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR,
                        "Error al editar el incidente"));
            }
        });

        delete("/incidente/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, "No se puede eliminar " +
                    "Incidentes."));
        });

        options("/incidente/:id", ((request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                    (incidenteService.existIncidente(request.params(":id")) ?
                            "El proyecto existe." : "El proyecto no existe")));
        }));

        /*
         * metodo get que me devuelve  todos los incidentes abiertos o pendientes
         */

        get("/incidentes/abiertos", (request, response) -> {
            response.type("application/json");
            List<Incidente> listIncidentes = new ArrayList<>();
            int cantidadIncidentes = incidenteService.getIncidentes().size();
            for (int i = 1; i <= cantidadIncidentes; i++) {
                if (incidenteService.getIncidente(String.valueOf(i)).getEstado() == Estado.ASIGNADO) {
                    listIncidentes.add(incidenteService.getIncidente(String.valueOf(i)));
                }
            }
            if (listIncidentes.isEmpty()) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        "No hay incidentes abiertos"));
            }

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(listIncidentes)));

        });

        /*
         * metodo get que me devuelve  todos los incidentes cerrado o resueltos
         */

        get("/incidentes/cerrados", (request, response) -> {
            response.type("application/json");
            List<Incidente> listIncidentes = new ArrayList<>();
            int cantidadIncidentes = incidenteService.getIncidentes().size();
            for (int i = 1; i <= cantidadIncidentes; i++) {
                if (incidenteService.getIncidente(String.valueOf(i)).getEstado() == Estado.RESUELTO) {
                    listIncidentes.add(incidenteService.getIncidente(String.valueOf(i)));
                }
            }
            if (listIncidentes.isEmpty()) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS,
                        "No hay incidentes resueltos"));
            }

            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(listIncidentes)));

        });

    }

    static void cargarDatos(IncidenteService incidenteService, UsuarioService usuarioService,
                            ProyectoService proyectoService) throws ParseException {
        Usuario usuario = new Usuario("1", "Cecilia", "Bustos");
        Usuario usuario1 = new Usuario("2", "Maria", "Figueroa");
        Usuario usuario2 = new Usuario("3", "Gisela", "Garcia");

        usuarioService.addUsuario(usuario);
        usuarioService.addUsuario(usuario1);
        usuarioService.addUsuario(usuario2);

        Proyecto proyecto= new Proyecto("1","app",usuario);
        proyectoService.addProyecto(proyecto);
        Proyecto proyecto1= new Proyecto("2","app1",usuario1);
        proyectoService.addProyecto(proyecto1);

        SimpleDateFormat d = new SimpleDateFormat("dd-MM-yy");
        Date dateCreacion = d.parse("31-03-2016");
        Date dateSolucion = d.parse("04-04-2016");

        Incidente incidente = new Incidente("1", Clasificacion.CRITICO, "se perdieron datos", usuario, usuario1,
                dateCreacion, dateSolucion, proyecto, Estado.RESUELTO);
        Incidente incident1 = new Incidente("2", Clasificacion.MENOR, "mejorar solucion", usuario, usuario2,
                dateCreacion, dateSolucion, proyecto, Estado.ASIGNADO);

        incidenteService.addIncidente(incidente);
        incidenteService.addIncidente(incident1);


    }

}
