package incidente;

import java.util.Collection;

public interface IncidenteService {

    void addIncidente(Incidente incidente);

    Collection<Incidente> getIncidentes(); //Preguntar q es esto y q tiene q ver con spark

    Incidente getIncidente(int id);

    Incidente modifyIncidente(Incidente incidente) throws IncidenteException;

    void deleteIncidente(int id);

    boolean existIncidente(int id);
}
