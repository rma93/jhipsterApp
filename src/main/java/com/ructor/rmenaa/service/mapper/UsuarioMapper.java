package com.ructor.rmenaa.service.mapper;

import com.ructor.rmenaa.domain.*;
import com.ructor.rmenaa.service.dto.UsuarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Usuario and its DTO UsuarioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsuarioMapper extends EntityMapper <UsuarioDTO, Usuario> {
    
    

}
