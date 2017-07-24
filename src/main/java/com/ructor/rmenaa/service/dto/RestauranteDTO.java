package com.ructor.rmenaa.service.dto;


import javax.validation.constraints.*;

import com.ructor.rmenaa.domain.Tapas;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Restaurante entity.
 */
public class RestauranteDTO implements Serializable {

    private String id;

    @NotNull
    private String direccion;

    @NotNull
    private String nombre;

    @NotNull
    private String ubicacionLat;

    @NotNull
    private String ubicacionLong;
    
    private Tapas tapas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacionLat() {
        return ubicacionLat;
    }

    public void setUbicacionLat(String ubicacionLat) {
        this.ubicacionLat = ubicacionLat;
    }

    public String getUbicacionLong() {
        return ubicacionLong;
    }

    public void setUbicacionLong(String ubicacionLong) {
        this.ubicacionLong = ubicacionLong;
    }

    public Tapas getTapas() {
		return tapas;
	}

	public void setTapas(Tapas tapas) {
		this.tapas = tapas;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestauranteDTO restauranteDTO = (RestauranteDTO) o;
        if(restauranteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restauranteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RestauranteDTO{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", ubicacionLat='" + getUbicacionLat() + "'" +
            ", ubicacionLong='" + getUbicacionLong() + "'" +
            "}";
    }
}
