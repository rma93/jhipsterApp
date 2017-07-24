package com.ructor.rmenaa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Usuario entity.
 */
public class UsuarioDTO implements Serializable {

    private String id;

    @NotNull
    private String nombre;

    @NotNull
    private String apellido1;

    private String apellido2;

    private Long edad;

    @NotNull
    private String email;

    private String calle;

    private byte[] foto;
    private String fotoContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Long getEdad() {
        return edad;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) o;
        if(usuarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido1='" + getApellido1() + "'" +
            ", apellido2='" + getApellido2() + "'" +
            ", edad='" + getEdad() + "'" +
            ", email='" + getEmail() + "'" +
            ", calle='" + getCalle() + "'" +
            ", foto='" + getFoto() + "'" +
            "}";
    }
}
