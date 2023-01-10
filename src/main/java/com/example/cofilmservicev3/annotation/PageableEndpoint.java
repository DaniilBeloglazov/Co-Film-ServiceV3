package com.example.cofilmservicev3.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY
        , description = "Zero-based page index (0..N)"
        , name = "page"
        , schema = @Schema(type = "integer", defaultValue = "0"))
@Parameter(in = ParameterIn.QUERY
        , description = "The size of the page to be returned"
        , name = "size"
        , schema = @Schema(type = "integer", defaultValue = "10"))
@Parameter(in = ParameterIn.QUERY
        , description = "Sorting criteria in the format: property,(asc|desc). " +
        "Default sort - by production year in descending order. " +
        "Multiple sort criteria are supported. " +
        "Example: [\"productionYear,desc\", ...]"
        , name = "sort"
        , array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "productionYear,desc")))
public @interface PageableEndpoint {
}
