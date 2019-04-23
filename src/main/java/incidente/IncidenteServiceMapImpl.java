package incidente;


import enums.Estado;

import java.util.Collection;
import java.util.HashMap;

public class IncidenteServiceMapImpl implements IncidenteService {
    private HashMap<String, Incidente> incidenteMap;

    public IncidenteServiceMapImpl() {
        incidenteMap = new HashMap<String,  Incidente>();
    }

    @Override
    public void addIncidente( Incidente incidente) {
        incidenteMap.put(incidente.getId(), incidente);
    }

    @Override
    public Collection<Incidente> getIncidentes() {
        return  incidenteMap.values();
    }

    @Override
    public Incidente getIncidente(String id) {
        return incidenteMap.get(id);
    }

    @Override
    public Incidente modifyIncidente(Incidente incidente) throws IncidenteException {
        try {
            if (incidente.getId() == null) {
                throw new IncidenteException("El id de proyecto no puede ser nulo");
            }
            Incidente incidenteaEditar = incidenteMap.get(incidente.getId());
            if (incidente.getDescripcion() != null) {
                String textoAnadido= incidenteaEditar.getDescripcion()+" "+incidente.getDescripcion();
                incidenteaEditar.setDescripcion(textoAnadido);
            }
            if (incidente.getEstado() != null && incidente.getEstado()== Estado.RESUELTO) {
                incidenteaEditar.setEstado(incidente.getEstado());
            }
            return incidenteaEditar;
        } catch (Exception exception) {
            throw new IncidenteException(exception.getMessage());
        }
    }

    @Override
    public void deleteIncidente(String id) {
        incidenteMap.remove(id);
    }

    @Override
    public boolean existIncidente(String id) {
        return incidenteMap.containsKey(id);
    }
}