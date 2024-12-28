package com.simonlangbak.agendo.web.dto;

import jakarta.validation.constraints.NotBlank;

public record BoardCreationDTO(
        @NotBlank String name,
        @NotBlank String description) {

}
