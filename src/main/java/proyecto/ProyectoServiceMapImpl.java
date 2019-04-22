package proyecto;


import usuario.Usuario;
import usuario.UsuarioException;

import java.util.Collection;
import java.util.HashMap;

public class ProyectoServiceMapImpl implements ProyectoService {
    private HashMap<String, Proyecto> proyectoMap;

    public ProyectoServiceMapImpl() {
        proyectoMap = new HashMap<String, Proyecto>();
    }

    @Override
    public void addProyecto(Proyecto proyecto) {
        proyectoMap.put(proyecto.getId(), proyecto);
    }

    @Override
    public Collection<Proyecto> getProyectos() {
        return  proyectoMap.values();
    }

    @Override
    public Proyecto getProyecto(String id) {
        return proyectoMap.get(id);
    }

    @Override
    public Proyecto modifyProyecto(Proyecto proyecto) throws ProyectoException {
        try {
            if (proyecto.getId() == null) {
                throw new UsuarioException("El id de proyecto no puede ser nulo");
            }
            Proyecto proyectoEditar = proyectoMap.get(proyecto.getId());
            if (proyecto.getTitulo() != null) {
                proyectoEditar.setTitulo(proyecto.getTitulo());
            }
            if (proyecto.getUsuario() != null) {
                proyectoEditar.setUsuario(proyecto.getUsuario());
            }
            return proyectoEditar;
        } catch (Exception exception) {
            throw new ProyectoException(exception.getMessage());
        }
    }

    @Override
    public void deleteProyecto(String id) {
        proyectoMap.remove(id);
    }

    @Override
    public boolean existProyecto(String id) {
        return proyectoMap.containsKey(id);
    }
}
