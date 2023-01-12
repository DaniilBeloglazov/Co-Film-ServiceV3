package com.example.cofilmservicev3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Schema(description = "Data transfer object for Person creation. All parameters are optional.")
@Data @NoArgsConstructor
public class UpdatePersonRequest {
    @Schema(description = "Person's name.", example = "Jon")
    private String name;
    @Schema(description = "Person's lastname.", example = "Tron")
    private String lastName;
    @Schema(description = "Person's poster.")
    private MultipartFile poster;

    @Schema(description = "Person's height", example = "180", minimum = "100", maximum = "250")
    @Min(100) @Max(150)
    private Double height; // in meters

    @Schema(description = "Date of birth in ISO DATE format.", example = "2021-31-12")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
}
