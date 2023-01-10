package com.example.cofilmservicev3.dto;

import com.example.cofilmservicev3.annotation.NullableNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.util.List;

@Schema(description = "Data transfer object for patching Film. All parameters are optional. Blanks will be ignored.")
@Data
@NoArgsConstructor
public class UpdateFilmRequest {

    @Schema(description = "Title of the film.")
    @NullableNotBlank
    private String title;

    @Schema(description = "Description of the film.")
    private String description;
    @Schema(description = "Year of film production.")
    private String productionYear;

    @Schema(description = "Film budget (dollars).")
    private Double budget; // бюджет

    @Schema(description = "Film box office (dollars).")
    private Double boxOffice; // сборы

    @Schema(description = "Film audience.")
    private Long audience;
    @Min(value = 25)
    @Schema(description = "Minimum allowable age.")
    private Long ageRating;

    @Schema(description = "Picture used as Film's poster.")
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
