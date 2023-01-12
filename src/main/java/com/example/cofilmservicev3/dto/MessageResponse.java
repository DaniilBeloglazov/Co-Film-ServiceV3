package com.example.cofilmservicev3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Schema(description = "Data transfer object for error messages.")
@Data
@NoArgsConstructor @AllArgsConstructor
public class MessageResponse {
    @Schema(description = "Message's payload.", example = "Something went wrong")
    private String message;
}
