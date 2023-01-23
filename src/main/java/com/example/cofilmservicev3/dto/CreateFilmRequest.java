package com.example.cofilmservicev3.dto;

import com.example.cofilmservicev3.annotation.MultipartSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Optional;

@Schema(description = "Data transfer object for Film creation")
@Data @NoArgsConstructor
public class CreateFilmRequest {

    @Schema(description = "Title of the film.", example = "Forrest Gump")
    @NotEmpty @NotBlank
    private String title;

    @Schema(description = "Description of the film.", example = "В центре действия фильма находится главный герой — Форрест Гамп (созвучно с англ. forest gump, то есть «лесной болван») из вымышленного города Гринбоу, штат Алабама. По сюжету, умственно отсталого Форреста жизнь как птичье пёрышко проносит через важнейшие события американской истории второй половины XX века. Впрочем, он не теряется, и благодаря своим спортивным способностям, дружелюбному характеру, а также привитой матерью необыкновенной жизнестойкости совершает военный подвиг, добивается «американской мечты» и невольно, походя, оказывает влияние на политику и популярную культуру США.")
    @NotEmpty @NotBlank
    private String description;

    @Schema(description = "Year of film production.", example = "1994")
    @NotNull
    @Min(value = 1895) @Max(value = 2030)
    private Long productionYear;

    @Schema(description = "Film budget (dollars).", example = "55000000")
    @NotNull
    private Double budget; // бюджет

    @Schema(description = "Film box office (dollars).", example = "677945399")
    @NotNull
    private Double boxOffice; // сборы

    @Schema(description = "Film audience.", example = "12343124")
    @NotNull
    private Long audience;

    @Schema(description = "Minimum allowable age.", example = "12")
    @NotNull
    @Min(value = 0) @Max(value = 18)
    private Long ageRating;

    @Schema(description = "Picture used as Film's poster.")
    @NotNull
    @MultipartSize(min = 128, max = 5 << 20)
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
