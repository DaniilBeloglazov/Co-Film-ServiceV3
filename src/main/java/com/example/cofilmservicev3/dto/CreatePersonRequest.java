package com.example.cofilmservicev3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Data transfer object for Person creation.")
@Data @NoArgsConstructor
public class CreatePersonRequest {
    @Schema(description = "Name of the Person.", example = "Dwayne")
    @NotEmpty
    private String name;
    @Schema(description = "Last name of the Person.", example = "Johnson")
    @NotEmpty
    private String lastName;
    @Schema(description = "Picture used as Person's poster.")
    @NotNull
    private MultipartFile poster;

    @Schema(description = "Height of the person", example = "180")
    @NotEmpty
    private Double height; // in meters

    @Schema(description = "Person's date of birth. The most common ISO Date Format yyyy-MM-dd.", example = "2000-10-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotEmpty
    private LocalDate dateOfBirth;
}
