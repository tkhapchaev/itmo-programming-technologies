package is.technologies.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cat", schema = "cats")
public class Cat {
    @Id
    private Long id;

    private String name;

    private LocalDate birthdate;

    private String breed;

    private String colour;

    private Long owner;
}