package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class AnimePostRequest {
    @NotBlank(message = "The field 'lastName' is required")
    private String name;
}


