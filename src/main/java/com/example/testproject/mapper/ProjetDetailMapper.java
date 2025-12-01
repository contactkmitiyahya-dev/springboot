package com.example.testproject.mapper;

import com.example.testproject.DTO.ProjetDetailDTO;
import com.example.testproject.entities.ProjetDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface ProjetDetailMapper {

    @Mapping(target = "dateDebut", source = "dateDebut", qualifiedByName = "localDateToString")
    ProjetDetailDTO toDto(ProjetDetail projetDetail);

    @Named("localDateToString")
    default String localDateToString(LocalDate date) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
}