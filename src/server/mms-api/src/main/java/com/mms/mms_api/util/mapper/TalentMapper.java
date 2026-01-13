package com.mms.mms_api.util.mapper;

import org.mapstruct.Mapper;
import com.mms.mms_api.model.Talent;
import com.mms.mms_api.dto.TalentDto;

@Mapper(config = DefaultMapperConfig.class)
public interface TalentMapper {
	TalentDto toDto(Talent talent);
}
