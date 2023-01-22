package com.example.cofilmservicev3.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "Data transfer object for Person creation.")
@Data @NoArgsConstructor @AllArgsConstructor
public class CreatePersonRequest {

    @Schema(description = "Name of the Person.", example = "Dwayne")
    @NotEmpty
    private String name;
    @Schema(description = "Last name of the Person.", example = "Johnson")
    @NotEmpty
    private String lastName;
    @Schema(description = "Picture used as Person's poster.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    private MultipartFile poster;

    @Schema(description = "Height of the person", example = "180", minimum = "100", maximum = "250")
    @Min(100) @Max(250)
    private Long height; // in meters

    @Schema(description = "Person's date of birth. The most common ISO Date Format yyyy-MM-dd.", example = "2000-10-31")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate dateOfBirth;
}
