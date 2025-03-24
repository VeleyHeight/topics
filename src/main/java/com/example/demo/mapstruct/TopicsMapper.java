package com.example.demo.mapstruct;

import com.example.demo.dto.TopicsDTO;
import com.example.demo.model.Topics;
import org.mapstruct.*;

import java.sql.Timestamp;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                TopicsMapperUtils.class
        },
        imports = {Timestamp.class})
public interface TopicsMapper {

    @Mapping(target = "parentId", qualifiedByName = {"TopicsMapperUtils", "createTopicsFromParent"}, source = "parentId")
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "created_at", ignore = true)
    Topics toTopics(TopicsDTO topicsDTO);

    @Mapping(target = "parentId", source = "parentId.id")
    TopicsDTO toTopicsDTO(Topics topics);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "parentId", qualifiedByName = {"TopicsMapperUtils", "createTopicsFromParent"}, source = "parentId")
    Topics updateWithNull(TopicsDTO topicsDTO, @MappingTarget Topics topics);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", expression = "java(new Timestamp(System.currentTimeMillis()))")
    @Mapping(target = "parentId", qualifiedByName = {"TopicsMapperUtils", "createTopicsFromParent"}, source = "parentId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Topics patchingUpdate(TopicsDTO topicsDTO, @MappingTarget Topics topics);
}
