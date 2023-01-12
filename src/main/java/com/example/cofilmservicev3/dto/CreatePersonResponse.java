package com.example.cofilmservicev3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema
@Data
@AllArgsConstructor @NoArgsConstructor
public class CreatePersonResponse {

    @Schema(description = "Id of created person.", example = "16")
    private Long id;
}
