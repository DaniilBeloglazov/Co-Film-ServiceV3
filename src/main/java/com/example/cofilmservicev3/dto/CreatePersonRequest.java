package com.example.cofilmservicev3.dto;

import com.example.cofilmservicev3.annotation.NotBlankMultipart;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Data transfer object for Person creation.")
@Data @NoArgsConstructor
public class CreatePersonRequest {
    @Schema(description = "Name of the Person.")
    @NotEmpty
    private String name;
    @Schema(description = "Last name of the Person.")
    @NotEmpty
    private String lastName;
    @Schema(description = "Picture used as Person's poster.")
    @NotNull @NotBlankMultipart
    private MultipartFile poster;
}
