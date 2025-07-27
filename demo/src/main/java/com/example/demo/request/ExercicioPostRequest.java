package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ExercicioPostRequest {
    @NotBlank(message = "The field 'name' is required")
    private String name;

}
