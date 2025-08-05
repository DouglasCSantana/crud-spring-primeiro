package com.example.demo.anime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class AnimePostRequest {
    @NotBlank(message = "The field 'name' is required")
    private String name;
}


