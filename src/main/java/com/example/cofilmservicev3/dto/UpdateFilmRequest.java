package com.example.cofilmservicev3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Schema(description = "Data transfer object for patching Film. All parameters are optional. Blanks will be ignored.")
@Data
@NoArgsConstructor
public class UpdateFilmRequest {

    @Schema(description = "Title of the film.", example = "Updated Title")
    private String title;

    @Schema(description = "Description of the film.", example = "Brief(or not) description of the film")
    private String description;
    @Min(1895) @Max(2030)
    @Schema(description = "Year of film production.", example = "2022")
    private Long productionYear;

    @Schema(description = "Film budget (dollars).", example = "45.500")
    private Double budget; // бюджет

    @Schema(description = "Film box office (dollars).", example = "100500")
    private Double boxOffice; // сборы

    @Schema(description = "Film audience.", example = "300000")
    private Long audience;
    @Min(0) @Max(18)
    @Schema(description = "Minimum allowable age.", minimum = "0", maximum = "18", example = "12")
    private Long ageRating;

    @Schema(description = "Picture used as Film's poster")
    private MultipartFile poster;

    @Schema(description = "Genre ID array.", example = "[1, 2, 3]")
    private List<Long> genres;

    @Schema(description = "Director ID array", example = "[1, 2, 3]")
    private List<Long> directors;

    @Schema(description = "Writer ID array", example = "[1, 2, 3]")
    private List<Long> writers;

    @Schema(description = "Actor ID array", example = "[1, 2, 3]")
    private List<Long> actors;
}
