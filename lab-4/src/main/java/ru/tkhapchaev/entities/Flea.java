package ru.tkhapchaev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tkhapchaev.models.FleaDto;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flea", schema = "cats")
public class Flea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "cat", nullable = false)
    private Cat cat;

    public Flea(FleaDto fleaDto, Cat cat) {
        id = fleaDto.getId();
        name = fleaDto.getName();
        this.cat = cat;
    }

    public FleaDto asDto() {
        return new FleaDto(id, name, cat.getId());
    }
}