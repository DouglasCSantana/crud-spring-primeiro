package com.example.demo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExercicioPostResponse {
    private String name;
    private Long id;
}
