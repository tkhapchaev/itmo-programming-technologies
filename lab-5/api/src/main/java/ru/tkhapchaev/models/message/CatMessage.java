package ru.tkhapchaev.models.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tkhapchaev.models.CatDto;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatMessage implements Serializable {
    private Long id;

    private String method;

    private List<CatDto> cats;
}