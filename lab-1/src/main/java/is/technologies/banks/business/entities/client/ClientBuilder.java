package is.technologies.banks.business.entities.client;

import is.technologies.banks.business.models.Address;
import is.technologies.banks.business.models.Passport;

/**
 * Реализация паттерна "Строитель" для сущности "Клиент"
 * Клиент создаётся "по шагам"
 */
public interface ClientBuilder {
    /**
     * Добавить клиенту имя
     *
     * @param name Имя
     * @return ClientBuilder
     */
    ClientBuilder withName(String name);

    /**
     * Добавить клиенту фамилию
     *
     * @param surname Фамилия
     * @return ClientBuilder
     */
    ClientBuilder withSurname(String surname);

    /**
     * Добавить клиенту паспорт
     *
     * @param passport Паспорт
     * @return ClientBuilder
     */
    ClientBuilder withPassport(Passport passport);

    /**
     * Добавить клиенту адрес
     *
     * @param address Адрес
     * @return ClientBuilder
     */
    ClientBuilder withAddress(Address address);

    /**
     * Создать клиента
     *
     * @return Client
     */
    Client build();
}