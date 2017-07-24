package com.ructor.rmenaa.service.mapper;

import com.ructor.rmenaa.domain.*;
import com.ructor.rmenaa.service.dto.ValoracionesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Valoraciones and its DTO ValoracionesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ValoracionesMapper extends EntityMapper <ValoracionesDTO, Valoraciones> {
    
    

}
