package com.example.cofilmservicev3.dto;

import com.example.cofilmservicev3.annotation.NotBlankMultipart;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Schema(description = "Data transfer object for Person creation. All parameters are optional.")
@Data @NoArgsConstructor
public class UpdatePersonRequest {
    @Schema(description = "Name of the Person.")
    private String name;
    @Schema(description = "Last name of the Person.")
    private String lastName;
    @Schema(description = "Picture used as Person's poster.")
    @NotBlankMultipart
    private MultipartFile poster;

    private Double height; // in meters
    @Schema(description = "Date of birth in ISO DATE format.", example = "2021-31.12")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
}
