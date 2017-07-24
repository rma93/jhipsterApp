package com.ructor.rmenaa.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Valoraciones entity.
 */
public class ValoracionesDTO implements Serializable {

    private String id;

    @NotNull
    private Long puntuacion;

    private String comentario;

    private String ubicacionLatRest;

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

    public void setPuntuacion(Long puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUbicacionLatRest() {
        return ubicacionLatRest;
    }

    public void setUbicacionLatRest(String ubicacionLatRest) {
        this.ubicacionLatRest = ubicacionLatRest;
    }

    public String getUbicacionLongRest() {
        return ubicacionLongRest;
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

        ValoracionesDTO valoracionesDTO = (ValoracionesDTO) o;
        if(valoracionesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valoracionesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValoracionesDTO{" +
            "id=" + getId() +
            ", puntuacion='" + getPuntuacion() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", ubicacionLatRest='" + getUbicacionLatRest() + "'" +
            ", ubicacionLongRest='" + getUbicacionLongRest() + "'" +
            "}";
    }
}
