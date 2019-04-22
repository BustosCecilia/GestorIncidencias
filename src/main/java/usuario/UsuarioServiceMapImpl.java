package usuario;


import java.util.Collection;
import java.util.HashMap;

public class UsuarioServiceMapImpl implements UsuarioService {
    private HashMap<String, Usuario> usuarioMap;

    public UsuarioServiceMapImpl() {
        usuarioMap = new HashMap<String, Usuario>();
    }

    @Override
    public void addUsuario(Usuario usuario) {
        usuarioMap.put(usuario.getId(), usuario);
    }

    @Override
    public Collection<Usuario> getUsuarios() {
        return usuarioMap.values();
    }

    @Override
    public Usuario getUsuario(String id) {
        return usuarioMap.get(id);
    }

    @Override
    public Usuario modifyUsuario(Usuario usuario) throws UsuarioException {
        try {
            if (usuario.getId() == null) {
                throw new UsuarioException("El id de usuario no puede ser nulo");
            }
            Usuario usuarioEditar = usuarioMap.get(usuario.getId());
            if (usuario.getNombre() != null) {
                usuarioEditar.setNombre(usuario.getNombre());
            }
            if (usuario.getApellido() != null) {
                usuarioEditar.setApellido(usuario.getApellido());
            }
            return usuarioEditar;
        } catch (Exception exception) {
            throw new UsuarioException(exception.getMessage());
        }
    }

    @Override
    public void deleteUsuario(String id) {
        usuarioMap.remove(id);
    }

    @Override
    public boolean existUsuario(String id) {
        return usuarioMap.containsKey(id);
    }
}
