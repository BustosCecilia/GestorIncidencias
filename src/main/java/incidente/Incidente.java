package incidente;

import enums.Clasificacion;
import proyecto.Proyecto;
import usuario.Usuario;

import java.util.Date;

public class Incidente {
    private String id;
    private Clasificacion clasificacion;
    private String descripcion;
    private Usuario reportador;
    private Usuario responsable;
    private Date dateCreacion;
    private Date dateSolucion;
    private Proyecto proyecto;

    public Incidente() {
    }

    public Incidente(String id, Clasificacion clasificacion, String descripcion, Usuario reportador,
                     Usuario responsable, Date dateCreacion, Date dateSolucion, Proyecto proyecto) {
        this.id = id;
        this.clasificacion = clasificacion;
        this.descripcion = descripcion;
        this.reportador = reportador;
        this.responsable = responsable;
        this.dateCreacion = dateCreacion;
        this.dateSolucion = dateSolucion;
        this.proyecto = proyecto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getReportador() {
        return reportador;
    }

    public void setReportador(Usuario reportador) {
        this.reportador = reportador;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public Date getDateCreacion() {
        return dateCreacion;
    }

    public void setDateCreacion(Date dateCreacion) {
        this.dateCreacion = dateCreacion;
    }

    public Date getDateSolucion() {
        return dateSolucion;
    }

    public void setDateSolucion(Date dateSolucion) {
        this.dateSolucion = dateSolucion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
}
