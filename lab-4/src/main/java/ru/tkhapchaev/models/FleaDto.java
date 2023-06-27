package ru.tkhapchaev.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FleaDto {
    private Long id;

    private String name;

    private Long cat;
}