package com.example.cofilmservicev3.dto;

import com.example.cofilmservicev3.annotation.NullableNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@Schema(description = "Data transfer object for patching Film. All parameters are optional. Blanks will be ignored.")
@Data
@NoArgsConstructor
public class UpdateFilmRequest {
    @NullableNotBlank
    @Schema(description = "Title of the film.")
    private String title;

    @NullableNotBlank
    @Schema(description = "Description of the film.")
    private String description;

    @Schema(description = "Year of film production.")
    private Long productionYear;

    @Schema(description = "Film budget (dollars).")
    private Double budget; // бюджет

    @Schema(description = "Film box office (dollars).")
    private Double boxOffice; // сборы

    @Schema(description = "Film audience.")
    private Long audience;

    @Min(0) @Max(18)
    @Schema(description = "Minimum allowable age.", minimum = "0", maximum = "18")
    private Long ageRating;

    @Schema(description = "Picture used as Film's poster")
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
