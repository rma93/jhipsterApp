package com.ructor.rmenaa.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;

import com.ructor.rmenaa.domain.enumeration.Alergenos;

/**
 * A Tapas.
 */
@Document(collection = "tapas")
public class Tapas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("idtapa")
    private Integer idtapa;

    @Field("nombre")
    private String nombre;

    @Field("ingrediente")
    private String ingrediente;

    @Field("alergenos")
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

    public Tapas idtapa(Integer idtapa) {
        this.idtapa = idtapa;
        return this;
    }

    public void setIdtapa(Integer idtapa) {
        this.idtapa = idtapa;
    }

    public String getNombre() {
        return nombre;
    }

    public Tapas nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public Tapas ingrediente(String ingrediente) {
        this.ingrediente = ingrediente;
        return this;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Alergenos getAlergenos() {
        return alergenos;
    }

    public Tapas alergenos(Alergenos alergenos) {
        this.alergenos = alergenos;
        return this;
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
        Tapas tapas = (Tapas) o;
        if (tapas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tapas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tapas{" +
            "id=" + getId() +
            ", idtapa='" + getIdtapa() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", ingrediente='" + getIngrediente() + "'" +
            ", alergenos='" + getAlergenos() + "'" +
            "}";
    }
}
