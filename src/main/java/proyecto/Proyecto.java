package proyecto;

import usuario.Usuario;

public class Proyecto {
    private String id;
    private String titulo;
    private Usuario usuario;

    public Proyecto() {
    }

    public Proyecto(String id, String titulo, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
