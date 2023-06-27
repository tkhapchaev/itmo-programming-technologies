package is.technologies.banks.business.models;

import is.technologies.banks.business.exceptions.AddressException;

/**
 * Модель, описывающая адрес клиента
 */
public class Address {
    public String country;
    public String city;
    public String street;
    public int houseNumber;

    /**
     * @param country     Страна
     * @param city        Город
     * @param street      Улица
     * @param houseNumber Номер дома
     */
    public Address(String country, String city, String street, int houseNumber) throws IllegalArgumentException, AddressException {
        if (country == null) {
            throw new IllegalArgumentException("Country name cannot be null.");
        }

        if (city == null) {
            throw new IllegalArgumentException("City name cannot be null.");
        }

        if (street == null) {
            throw new IllegalArgumentException("Street name cannot be null.");
        }

        if (houseNumber <= 0) {
            throw AddressException.invalidHouseNumber(houseNumber);
        }

        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }

    public int getHouseNumber() {
        return this.houseNumber;
    }

    /**
     * Метод, возвращающий объект класса Address в виде строки
     *
     * @return Address в виде строки
     */
    public String toString() {
        return "Country: " + country + "; City: " + city + "; Street: " + street + "; House number: " + houseNumber + ".";
    }
}