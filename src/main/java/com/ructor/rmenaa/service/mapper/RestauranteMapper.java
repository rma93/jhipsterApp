package com.ructor.rmenaa.service.mapper;

import com.ructor.rmenaa.domain.*;
import com.ructor.rmenaa.service.dto.RestauranteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Restaurante and its DTO RestauranteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RestauranteMapper extends EntityMapper <RestauranteDTO, Restaurante> {
    
    

}
