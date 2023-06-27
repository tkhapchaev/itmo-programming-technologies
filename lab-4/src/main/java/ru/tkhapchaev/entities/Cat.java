package ru.tkhapchaev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tkhapchaev.converters.ColourConverter;
import ru.tkhapchaev.enums.Colour;
import ru.tkhapchaev.models.CatDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private Owner owner;

    @JsonIgnore
    @OneToMany(mappedBy = "cat")
    private List<Flea> fleas;

    public Cat(CatDto catDto, Owner owner) {
        id = catDto.getId();
        name = catDto.getName();
        birthdate = catDto.getBirthdate();
        breed = catDto.getBreed();
        colour = Colour.valueOf(catDto.getColour().toUpperCase());
        tailLength = catDto.getTailLength();
        fleas = new ArrayList<>();
        this.owner = owner;
    }

    public CatDto asDto() {
        return new CatDto(id, name, birthdate, breed, colour.toString().toLowerCase(), tailLength, owner.getId());
    }
}