package is.technologies.banks.business.entities.accounts;

import is.technologies.banks.business.entities.bank.Bank;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.MoneyException;

import java.util.UUID;

/**
 * Интерфейс для "Счёта"
 */
public interface Account {
    Bank getBank();

    Client getOwner();

    UUID getId();

    double getBalance();

    /**
     * Является ли счёт ограниченным
     *
     * @return True если счёт ограничен, False в ином случае
     */
    boolean isRestricted();

    /**
     * Пополнить счёт
     *
     * @param money Сумма денег
     * @throws MoneyException
     */
    void topUp(double money) throws MoneyException;

    /**
     * Снять со счёта
     *
     * @param money Сумма денег
     * @throws MoneyException
     * @throws AccountException
     */
    void withdraw(double money) throws MoneyException, AccountException;

    /**
     * Перевод со счёта
     *
     * @param account Счёт получателя
     * @param money   Сумма денег
     * @throws MoneyException
     * @throws AccountException
     */
    void transfer(Account account, double money) throws MoneyException, AccountException;

    /**
     * Обновить счёт
     *
     * @throws MoneyException
     * @throws AccountException
     */
    void update() throws MoneyException, AccountException;
}