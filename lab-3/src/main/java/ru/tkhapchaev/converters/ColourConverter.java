package ru.tkhapchaev.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.tkhapchaev.enums.Colour;

@Converter
public class ColourConverter implements AttributeConverter<Colour, String> {
    @Override
    public String convertToDatabaseColumn(Colour colour) {
        return colour.toString().charAt(0) + colour.toString().substring(1).toLowerCase();
    }

    @Override
    public Colour convertToEntityAttribute(String s) {
        return Colour.valueOf(s.toUpperCase());
    }
}