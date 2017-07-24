package com.ructor.rmenaa.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Restaurante.
 */
@Document(collection = "restaurante")
public class Restaurante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("direccion")
    private String direccion;

    @NotNull
    @Field("nombre")
    private String nombre;

    @NotNull
    @Field("ubicacion_lat")
    private String ubicacionLat;

    @NotNull
    @Field("ubicacion_long")
    private String ubicacionLong;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public Restaurante direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public Restaurante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacionLat() {
        return ubicacionLat;
    }

    public Restaurante ubicacionLat(String ubicacionLat) {
        this.ubicacionLat = ubicacionLat;
        return this;
    }

    public void setUbicacionLat(String ubicacionLat) {
        this.ubicacionLat = ubicacionLat;
    }

    public String getUbicacionLong() {
        return ubicacionLong;
    }

    public Restaurante ubicacionLong(String ubicacionLong) {
        this.ubicacionLong = ubicacionLong;
        return this;
    }

    public void setUbicacionLong(String ubicacionLong) {
        this.ubicacionLong = ubicacionLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restaurante restaurante = (Restaurante) o;
        if (restaurante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restaurante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restaurante{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", ubicacionLat='" + getUbicacionLat() + "'" +
            ", ubicacionLong='" + getUbicacionLong() + "'" +
            "}";
    }
}
