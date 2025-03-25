package com.example.demo.mapstruct;

import com.example.demo.dto.ReactionsDTO;
import com.example.demo.model.Reactions;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
uses = {ReactionsMapperUtils.class})
public interface ReactionsMapper {
    @Mapping(target = "questionsId", qualifiedByName = {"ReactionsMapperUtils", "createQuestionsToReactions"})
    @Mapping(target = "created_at", ignore = true)
    Reactions toReactions(ReactionsDTO reactionsDTO);
    @Mapping(target = "questionsId", source = "questionsId.id")
    ReactionsDTO toReactionsDTO(Reactions reactions);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "questionsId", qualifiedByName = {"ReactionsMapperUtils", "createQuestionsToReactions"}, source = "questionsId")
    Reactions updateWithNull(ReactionsDTO reactionsDTO, @MappingTarget Reactions reactions);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "questionsId", qualifiedByName = {"ReactionsMapperUtils", "createQuestionsToReactions"}, source = "questionsId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reactions patchingUpdate(ReactionsDTO reactionsDTO, @MappingTarget Reactions reactions);
}
