package com.example.testproject.mapper;

import com.example.testproject.DTO.ProjetDetailDTO;
import com.example.testproject.entities.ProjetDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-18T13:16:21+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.16 (Microsoft)"
)
@Component
public class ProjetDetailMapperImpl implements ProjetDetailMapper {

    @Override
    public ProjetDetailDTO toDto(ProjetDetail projetDetail) {
        if ( projetDetail == null ) {
            return null;
        }

        ProjetDetailDTO projetDetailDTO = new ProjetDetailDTO();

        projetDetailDTO.setDateDebut( localDateToString( projetDetail.getDateDebut() ) );
        projetDetailDTO.setDescription( projetDetail.getDescription() );
        projetDetailDTO.setTechnologie( projetDetail.getTechnologie() );

        return projetDetailDTO;
    }
}
