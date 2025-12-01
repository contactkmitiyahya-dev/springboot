package com.example.testproject.services;

import com.example.testproject.DTO.ProjetDetailDTO;
import com.example.testproject.entities.ProjetDetail;
import com.example.testproject.repository.ProjetDetailRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjetDetailDTOService {

    private final ProjetDetailRepository projetDetailRepository;

    // Get by ID
    public ProjetDetailDTO getDetailsProjet(long id) {
        ProjetDetail detail = projetDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Détails du projet non trouvés pour l'id: " + id));
        return convertToDto(detail);
    }

    // Get all
    public List<ProjetDetailDTO> getAllProjects() {
        List<ProjetDetail> projects = projetDetailRepository.findAll();
        return projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Add new project
    public ProjetDetailDTO addProject(ProjetDetailDTO projetDetailDTO) {
        ProjetDetail projetDetail = convertToEntity(projetDetailDTO);
        ProjetDetail savedProject = projetDetailRepository.save(projetDetail);
        return convertToDto(savedProject);
    }

    // Conversion DTO -> Entity
    private ProjetDetail convertToEntity(ProjetDetailDTO dto) {
        ProjetDetail entity = new ProjetDetail();
        entity.setDescription(dto.getDescription());
        entity.setTechnologie(dto.getTechnologie());
        entity.setCout(50000L); // Valeur par défaut

        // Conversion de la date string -> LocalDate
        if (dto.getDateDebut() != null && !dto.getDateDebut().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            entity.setDateDebut(LocalDate.parse(dto.getDateDebut(), formatter));
        } else {
            entity.setDateDebut(LocalDate.now());
        }

        return entity;
    }

    // Conversion Entity -> DTO
    private ProjetDetailDTO convertToDto(ProjetDetail detail) {
        ProjetDetailDTO dto = new ProjetDetailDTO();
        dto.setDescription(detail.getDescription());
        dto.setTechnologie(detail.getTechnologie());

        if (detail.getDateDebut() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dto.setDateDebut(detail.getDateDebut().format(formatter));
        } else {
            dto.setDateDebut("Date non définie");
        }

        return dto;
    }
}