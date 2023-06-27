package ru.tkhapchaev.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private Long id;

    private String name;

    private LocalDate birthdate;
}