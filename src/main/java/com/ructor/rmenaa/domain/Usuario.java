package com.ructor.rmenaa.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Usuario.
 */
@Document(collection = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("nombre")
    private String nombre;

    @NotNull
    @Field("apellido_1")
    private String apellido1;

    @Field("apellido_2")
    private String apellido2;

    @Field("edad")
    private Long edad;

    @NotNull
    @Field("email")
    private String email;

    @Field("calle")
    private String calle;

    @Field("foto")
    private byte[] foto;

    @Field("foto_content_type")
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

    public Usuario nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public Usuario apellido1(String apellido1) {
        this.apellido1 = apellido1;
        return this;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public Usuario apellido2(String apellido2) {
        this.apellido2 = apellido2;
        return this;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public Long getEdad() {
        return edad;
    }

    public Usuario edad(Long edad) {
        this.edad = edad;
        return this;
    }

    public void setEdad(Long edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public Usuario email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCalle() {
        return calle;
    }

    public Usuario calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Usuario foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Usuario fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
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
        Usuario usuario = (Usuario) o;
        if (usuario.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido1='" + getApellido1() + "'" +
            ", apellido2='" + getApellido2() + "'" +
            ", edad='" + getEdad() + "'" +
            ", email='" + getEmail() + "'" +
            ", calle='" + getCalle() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            "}";
    }
}
