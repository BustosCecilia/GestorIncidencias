package usuario;



import java.util.Collection;

public interface UsuarioService {

    void addUsuario(Usuario usuario);

    Collection<Usuario> getUsuarios(); //Preguntar q es esto y q tiene q ver con spark

    Usuario getUsuario(String id);

    Usuario modifyUsuario(Usuario usuario) throws UsuarioException;

    void deleteUsuario(String id);

    boolean existUsuario(String id);

    //ver de crear un metodo agraga proyecto e incidente
}
