package is.technologies.banks.business.entities.client;

import is.technologies.banks.business.models.Address;
import is.technologies.banks.business.models.Passport;

/**
 * Класс, реализующий интерфейс "Строителя" ClientBuilder
 */
public class ClientBuilderImpl implements ClientBuilder {
    private String name;
    private String surname;
    private Passport passport;
    private Address address;

    /**
     * Добавить клиенту имя
     *
     * @param name Имя
     * @return ClientBuilder
     */
    public ClientBuilder withName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Client name cannot be null.");
        }

        this.name = name;

        return this;
    }

    /**
     * Добавить клиенту фамилию
     *
     * @param surname Фамилия
     * @return ClientBuilder
     */
    public ClientBuilder withSurname(String surname) {
        if (surname == null) {
            throw new IllegalArgumentException("Client surname cannot be null.");
        }

        this.surname = surname;

        return this;
    }

    /**
     * Добавить клиенту паспорт
     *
     * @param passport Паспорт
     * @return ClientBuilder
     */
    public ClientBuilder withPassport(Passport passport) {
        this.passport = passport;

        return this;
    }

    /**
     * Добавить клиенту адрес
     *
     * @param address Адрес
     * @return ClientBuilder
     */
    public ClientBuilder withAddress(Address address) {
        this.address = address;

        return this;
    }

    /**
     * Создать клиента
     *
     * @return Client
     */
    public Client build() {
        Client result = new Client(name, surname);
        result.setAddress(address);
        result.setPassport(passport);

        return result;
    }
}