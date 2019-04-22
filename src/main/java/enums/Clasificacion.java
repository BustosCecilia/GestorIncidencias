package enums;

public enum Clasificacion {
    CRITICO("CRITICO"),
    NORMAL("NORMAL"),
    MENOR("MENOR");

    private String clasificacion;

    Clasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
