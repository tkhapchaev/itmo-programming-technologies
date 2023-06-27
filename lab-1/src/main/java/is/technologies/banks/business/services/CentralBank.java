package is.technologies.banks.business.services;

import is.technologies.banks.business.entities.accounts.*;
import is.technologies.banks.business.entities.bank.Bank;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.entities.transactions.Transaction;
import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.CentralBankException;
import is.technologies.banks.business.exceptions.DepositPercentageException;
import is.technologies.banks.business.exceptions.MoneyException;
import is.technologies.banks.business.models.Address;
import is.technologies.banks.business.models.Passport;

/**
 * Интерфейс сервиса - центрального банка
 */
public interface CentralBank {
    /**
     * Метод для добавления банка
     *
     * @param name                    Имя банка
     * @param transferLimit           Лимит на перевод
     * @param withdrawalLimit         Лимит на снятие
     * @param balancePercentage       Процент на остаток
     * @param minimalCreditBalance    Минимальный кредитный баланс
     * @param creditAccountCommission Коммиссия для кредитного счёта
     * @param depositPercentage       Процент депозита
     * @return Созданный банк
     */
    Bank addBank(String name, double transferLimit, double withdrawalLimit, double balancePercentage, double minimalCreditBalance, double creditAccountCommission, DepositPercentage depositPercentage);

    /**
     * Метод для добавления клиента
     *
     * @param name     Имя клиента
     * @param surname  Фамилия клиента
     * @param passport Паспорт
     * @param address  Адрес
     * @return Созданный клиент
     */
    Client addClient(String name, String surname, Passport passport, Address address);

    /**
     * Метод для добавления дебетового счёта
     *
     * @param bank   Банк
     * @param client Клиент
     * @return DebitAccount Созданный дебетовый счёт
     * @throws AccountException
     */
    DebitAccount addDebitAccount(Bank bank, Client client) throws AccountException;

    /**
     * Метод для добавления депозитного счёта
     *
     * @param bank            Банк
     * @param client          Клиент
     * @param depositAmount   Сумма депозита
     * @param depositDuration Продолжительность депозита
     * @return DepositAccount Созданный депозитный счёт
     * @throws AccountException
     * @throws DepositPercentageException
     */
    DepositAccount addDepositAccount(Bank bank, Client client, double depositAmount, int depositDuration) throws AccountException, DepositPercentageException;

    /**
     * Метод для добавления кредитного счёта
     *
     * @param bank          Банк
     * @param client        Клиент
     * @param creditBalance Кредитный баланс
     * @return CreditAccount Созданный кредитный счёт
     * @throws AccountException
     */
    CreditAccount addCreditAccount(Bank bank, Client client, double creditBalance) throws AccountException;

    /**
     * Метод для выполнения транзакции
     *
     * @param transaction Транзакция
     * @throws MoneyException
     * @throws AccountException
     */
    void executeTransaction(Transaction transaction) throws MoneyException, AccountException;

    /**
     * Метод для отмены транзакции
     *
     * @param transaction Транзакция
     * @throws CentralBankException
     * @throws MoneyException
     * @throws AccountException
     */
    void cancelTransaction(Transaction transaction) throws CentralBankException, MoneyException, AccountException;

    /**
     * Метод для перемотки времени (обновления счетов)
     *
     * @param days Количество дней для перемотки
     * @throws MoneyException
     * @throws AccountException
     */
    void update(int days) throws MoneyException, AccountException;

    /**
     * Метод для получения банка
     *
     * @param name Имя банка
     * @return Bank Банк
     * @throws CentralBankException
     */
    Bank getBank(String name) throws CentralBankException;

    /**
     * Метод для получения клиента
     *
     * @param name    Имя клиента
     * @param surname Фамилия клиента
     * @return Client Клиент
     * @throws CentralBankException
     */
    Client getClient(String name, String surname) throws CentralBankException;

    /**
     * Метод для получения счёта
     *
     * @param id ID счёта
     * @return Account счёт
     * @throws CentralBankException
     */
    Account getAccount(String id) throws CentralBankException;
}