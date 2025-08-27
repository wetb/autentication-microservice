package co.com.bancolombia.model.rol;


public class Rol {
    private Long idRol;
    private String name;
    private String description;

    public Rol(Long idRol, String description, String name) {
        this.idRol = idRol;
        this.description = description;
        this.name = name;
    }

    public Long getIdRol() {
        return idRol;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
