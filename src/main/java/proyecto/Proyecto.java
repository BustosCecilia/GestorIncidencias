package proyecto;

import incidente.Incidente;
import usuario.Usuario;

public class Proyecto {
    private String id;
    private String titulo;
    private Usuario propietario;

    public Proyecto() {
    }

    public Proyecto(String id, String titulo, Usuario propietario) {
        this.id = id;
        this.titulo = titulo;
        this.propietario = propietario;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }
}
