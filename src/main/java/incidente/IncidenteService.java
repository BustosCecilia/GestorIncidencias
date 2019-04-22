package incidente;

import java.util.Collection;

public interface IncidenteService {

    void addIncidente(Incidente incidente);

    Collection<Incidente> getIncidentes(); //Preguntar q es esto y q tiene q ver con spark

    Incidente getIncidente(String id);

    Incidente modifyIncidente(Incidente incidente) throws IncidenteException;

    void deleteIncidente(String id);

    boolean existIncidente(String id);
}
