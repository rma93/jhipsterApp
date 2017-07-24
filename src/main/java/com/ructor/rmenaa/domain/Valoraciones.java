package com.ructor.rmenaa.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Valoraciones.
 */
@Document(collection = "valoraciones")
public class Valoraciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("puntuacion")
    private Long puntuacion;

    @Field("comentario")
    private String comentario;

    @Field("ubicacion_lat_rest")
    private String ubicacionLatRest;

    @Field("ubicacion_long_rest")
    private String ubicacionLongRest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPuntuacion() {
        return puntuacion;
    }

    public Valoraciones puntuacion(Long puntuacion) {
        this.puntuacion = puntuacion;
        return this;
    }

    public void setPuntuacion(Long puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public Valoraciones comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUbicacionLatRest() {
        return ubicacionLatRest;
    }

    public Valoraciones ubicacionLatRest(String ubicacionLatRest) {
        this.ubicacionLatRest = ubicacionLatRest;
        return this;
    }

    public void setUbicacionLatRest(String ubicacionLatRest) {
        this.ubicacionLatRest = ubicacionLatRest;
    }

    public String getUbicacionLongRest() {
        return ubicacionLongRest;
    }

    public Valoraciones ubicacionLongRest(String ubicacionLongRest) {
        this.ubicacionLongRest = ubicacionLongRest;
        return this;
    }

    public void setUbicacionLongRest(String ubicacionLongRest) {
        this.ubicacionLongRest = ubicacionLongRest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Valoraciones valoraciones = (Valoraciones) o;
        if (valoraciones.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valoraciones.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Valoraciones{" +
            "id=" + getId() +
            ", puntuacion='" + getPuntuacion() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", ubicacionLatRest='" + getUbicacionLatRest() + "'" +
            ", ubicacionLongRest='" + getUbicacionLongRest() + "'" +
            "}";
    }
}
