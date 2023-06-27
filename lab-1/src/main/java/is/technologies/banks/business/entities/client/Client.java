package is.technologies.banks.business.entities.client;

import is.technologies.banks.business.entities.accounts.Account;
import is.technologies.banks.business.entities.publisher.Subscriber;
import is.technologies.banks.business.models.Address;
import is.technologies.banks.business.models.Passport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Класс, реализующий сущность "Клиент"
 */
public class Client implements Subscriber {
    private final ArrayList<Account> accounts;
    private final ArrayList<String> notifications;

    public String name;
    public String surname;
    public Passport passport;
    public Address address;

    public Client(String name, String surname) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }

        if (surname == null) {
            throw new IllegalArgumentException("Surname cannot be null.");
        }

        this.name = name;
        this.surname = surname;
        accounts = new ArrayList<Account>();
        notifications = new ArrayList<String>();
    }

    public Collection<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public Collection<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    /**
     * Добавить счёт
     *
     * @param account Счёт
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Удалить счёт
     *
     * @param account Счёт
     */
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Добавить уведомление
     *
     * @param data Уведомление
     * @throws IllegalArgumentException
     */
    public void update(String data) throws IllegalArgumentException {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        notifications.add(data);
    }
}