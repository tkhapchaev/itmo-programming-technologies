package is.technologies.banks.business.models;

import is.technologies.banks.business.exceptions.PassportException;

/**
 * Модель, описывающая паспорт клиента
 */
public class Passport {
    public int series;
    public int number;

    /**
     * @param series Серия паспорта
     * @param number Номер паспорта
     */
    public Passport(int series, int number) throws PassportException {
        if (series <= 0) {
            throw PassportException.invalidSeries(series);
        }

        if (number <= 0) {
            throw PassportException.invalidNumber(number);
        }

        this.series = series;
        this.number = number;
    }

    public int getSeries() {
        return series;
    }

    public int getNumber() {
        return number;
    }

    /**
     * @return Строковое представление объекта класса Passport
     */
    public String toString() {
        return "Series: " + series + "; Number: " + number + ".";
    }
}