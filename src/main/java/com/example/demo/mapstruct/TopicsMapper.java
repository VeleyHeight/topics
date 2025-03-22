package com.example.demo.mapstruct;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.model.Topics;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public interface TopicsMapper {
    @Mapping(target = "parentId", ignore = true) // Игнорируем прямое присвоение
    Topics toTopics(TopicsDTO topicsDTO);
    @Mapping(target = "parentId", source = "parentId.id")
    TopicsDTO toTopicsDTO(Topics topics);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "created_at", ignore = true)
//    @Mapping(target = "updated_at", ignore = true)
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Topics partialUpdate(TopicsDTO topicsDTO, @MappingTarget Topics topics);
}
