package proyecto;

import java.util.Collection;

public interface ProyectoService {

    void addProyecto(Proyecto proyecto);

    Collection<Proyecto> getProyectos(); //Preguntar q es esto y q tiene q ver con spark

    Proyecto getProyecto(String id);

    Proyecto modifyProyecto(Proyecto proyecto) throws ProyectoException;

    void deleteProyecto(String id);

    boolean existProyecto(String id);
}
