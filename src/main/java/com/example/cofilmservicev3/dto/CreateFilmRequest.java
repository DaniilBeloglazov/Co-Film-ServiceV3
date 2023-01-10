package com.example.cofilmservicev3.dto;

import com.example.cofilmservicev3.annotation.NotBlankMultipart;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;
@Schema(description = "Data transfer object for Film creation")
@Data @NoArgsConstructor
public class CreateFilmRequest {

    @Schema(description = "Title of the film.")
    @NotEmpty @NotBlank
    private String title;

    @Schema(description = "Description of the film.")
    @NotEmpty @NotBlank
    private String description;

    @Schema(description = "Year of film production.")
    @NotNull
    @Min(value = 1895) @Max(value = 2030)
    private Long productionYear;

    @Schema(description = "Film budget (dollars).")
    @NotNull
    private Double budget; // бюджет

    @Schema(description = "Film box office (dollars).")
    @NotNull
    private Double boxOffice; // сборы

    @Schema(description = "Film audience.")
    @NotNull
    private Long audience;

    @Schema(description = "Minimum allowable age.")
    @NotNull
    @Min(value = 0) @Max(value = 18)
    private Long ageRating;

    @Schema(description = "Picture used as Film's poster.")
    @NotNull @NotBlankMultipart
    private MultipartFile poster;

    @Schema(description = "Genre ID array.")
    private List<Long> genres;

    @Schema(description = "Director ID array")
    private List<Long> directors;

    @Schema(description = "Writer ID array")
    private List<Long> writers;

    @Schema(description = "Actor ID array")
    private List<Long> actors;

}
