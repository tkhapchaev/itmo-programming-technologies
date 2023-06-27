package ru.tkhapchaev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tkhapchaev.models.OwnerDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owner", schema = "cats")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthdate;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Cat> cats;

    public Owner(OwnerDto ownerDto) {
        id = ownerDto.getId();
        name = ownerDto.getName();
        birthdate = ownerDto.getBirthdate();
        cats = new ArrayList<>();
    }

    public OwnerDto asDto() {
        return new OwnerDto(id, name, birthdate);
    }
}