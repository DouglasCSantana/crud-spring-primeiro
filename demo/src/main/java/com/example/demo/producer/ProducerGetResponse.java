package com.example.demo.producer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class ProducerGetResponse {
    private String name;
    private Long id;
    private LocalDateTime localDateTime;
}
