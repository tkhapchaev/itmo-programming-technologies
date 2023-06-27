package ru.tkhapchaev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tkhapchaev.models.RoleDto;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role", schema = "cats")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role(RoleDto roleDto) {
        this.id = roleDto.getId();
        this.name = roleDto.getName();
    }

    public RoleDto asDto() {
        return new RoleDto(id, name);
    }
}