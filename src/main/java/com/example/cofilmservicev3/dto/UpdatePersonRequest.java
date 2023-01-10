package com.example.cofilmservicev3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
@Schema(description = "Data transfer object for Person creation. All parameters are optional.")
@Data @NoArgsConstructor
public class UpdatePersonRequest {
    @Schema(description = "Name of the Person.")
    private String name;
    @Schema(description = "Last name of the Person.")
    private String lastName;
    @Schema(description = "Picture used as Person's poster.")
    private MultipartFile poster;
}
