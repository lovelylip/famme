package com.famme.service.mapper;


import com.famme.domain.*;
import com.famme.service.dto.UploadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Upload} and its DTO {@link UploadDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UploadMapper extends EntityMapper<UploadDTO, Upload> {


}
