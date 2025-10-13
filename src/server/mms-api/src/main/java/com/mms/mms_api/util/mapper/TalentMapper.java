package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import com.mms.mms_api.model.Talent;
import com.mms.mms_api.dto.TalentDto;

@Mapper(componentModel = "spring")
public interface TalentMapper {
	TalentDto toDto(Talent talent);
}
