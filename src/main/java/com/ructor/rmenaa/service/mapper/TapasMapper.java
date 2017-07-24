package com.ructor.rmenaa.service.mapper;

import com.ructor.rmenaa.domain.*;
import com.ructor.rmenaa.service.dto.TapasDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tapas and its DTO TapasDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TapasMapper extends EntityMapper <TapasDTO, Tapas> {
    
    

}
