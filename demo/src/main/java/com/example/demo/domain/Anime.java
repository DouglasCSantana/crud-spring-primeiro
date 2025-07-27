package com.example.demo.domain;

import lombok.*;

@ToString
@Builder
@Setter
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {
    @EqualsAndHashCode.Include
    private Long id;
    private String name;


    public Anime(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static AnimeBuilder builder() {
        return new AnimeBuilder();
    }


    public static class AnimeBuilder {
        private Long id;
        private String name;

        AnimeBuilder() {
        }

        public AnimeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AnimeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Anime build() {
            return new Anime(this.id, this.name);
        }

        public String toString() {
            return "Anime.AnimeBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}

