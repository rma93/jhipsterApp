package com.ructor.rmenaa.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.ructor.rmenaa.domain.enumeration.Alergenos;

/**
 * A DTO for the Tapas entity.
 */
public class TapasDTO implements Serializable {

    private String id;

    private Integer idtapa;

    private String nombre;

    private String ingrediente;

    private Alergenos alergenos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdtapa() {
        return idtapa;
    }

    public void setIdtapa(Integer idtapa) {
        this.idtapa = idtapa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Alergenos getAlergenos() {
        return alergenos;
    }

    public void setAlergenos(Alergenos alergenos) {
        this.alergenos = alergenos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TapasDTO tapasDTO = (TapasDTO) o;
        if(tapasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tapasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TapasDTO{" +
            "id=" + getId() +
            ", idtapa='" + getIdtapa() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", ingrediente='" + getIngrediente() + "'" +
            ", alergenos='" + getAlergenos() + "'" +
            "}";
    }
}
