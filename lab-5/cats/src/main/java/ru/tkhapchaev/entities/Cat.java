package ru.tkhapchaev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tkhapchaev.converters.ColourConverter;
import ru.tkhapchaev.enums.Colour;
import ru.tkhapchaev.models.CatDto;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cat", schema = "cats")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthdate;

    private String breed;

    @Convert(converter = ColourConverter.class)
    private Colour colour;

    @Column(name = "tail_length")
    private Integer tailLength;

    private Long owner;

    public Cat(CatDto catDto) {
        id = catDto.getId();
        name = catDto.getName();
        birthdate = catDto.getBirthdate();
        breed = catDto.getBreed();
        colour = Colour.valueOf(catDto.getColour().toUpperCase());
        tailLength = catDto.getTailLength();
        owner = catDto.getOwner();
    }

    public CatDto asDto() {
        return new CatDto(id, name, birthdate, breed, colour.toString().toLowerCase(), tailLength, owner);
    }
}