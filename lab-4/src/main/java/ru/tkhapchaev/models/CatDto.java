package ru.tkhapchaev.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatDto {
    private Long id;

    private String name;

    private LocalDate birthdate;

    private String breed;

    private String colour;

    private Integer tailLength;

    private Long owner;
}