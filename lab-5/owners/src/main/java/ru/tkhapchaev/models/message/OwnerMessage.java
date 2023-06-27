package ru.tkhapchaev.models.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tkhapchaev.models.OwnerDto;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerMessage implements Serializable {
    private Long id;

    private String method;

    private List<OwnerDto> owners;
}