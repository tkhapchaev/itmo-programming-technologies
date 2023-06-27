package is.technologies.banks.business.services;

import is.technologies.banks.business.entities.accounts.*;
import is.technologies.banks.business.entities.bank.Bank;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.entities.client.ClientBuilder;
import is.technologies.banks.business.entities.client.ClientBuilderImpl;
import is.technologies.banks.business.entities.transactions.Transaction;
import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.DepositPercentageException;
import is.technologies.banks.business.exceptions.CentralBankException;
import is.technologies.banks.business.exceptions.MoneyException;
import is.technologies.banks.business.models.Address;
import is.technologies.banks.business.models.Passport;

import java.util.*;
import java.util.stream.Stream;

/**
 * Класс-реализация интерфейса CentralBank (сервис - центральный банк)
 */
public class CentralBankImpl implements CentralBank {
    private final ArrayList<Bank> banks;

    private final ArrayList<Client> clients;

    private final ArrayList<Account> accounts;

    private final ArrayList<Transaction> transactions;

    private Calendar currentDate;

    public CentralBankImpl() {
        currentDate = new GregorianCalendar(2000, Calendar.JANUARY, 3);
        banks = new ArrayList<>();
        clients = new ArrayList<>();
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public Collection<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public Collection<Bank> getBanks() {
        return Collections.unmodifiableList(banks);
    }

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
    public Bank addBank(String name, double transferLimit, double withdrawalLimit, double balancePercentage, double minimalCreditBalance, double creditAccountCommission, DepositPercentage depositPercentage) {
        Bank bank = new Bank(name, transferLimit, withdrawalLimit, balancePercentage, minimalCreditBalance, creditAccountCommission, depositPercentage);
        banks.add(bank);

        return bank;
    }

    /**
     * Метод для добавления клиента
     *
     * @param name     Имя клиента
     * @param surname  Фамилия клиента
     * @param passport Паспорт
     * @param address  Адрес
     * @return Созданный клиент
     */
    public Client addClient(String name, String surname, Passport passport, Address address) {
        ClientBuilder clientBuilder = new ClientBuilderImpl();
        Client client = clientBuilder.withName(name).withSurname(surname).withPassport(passport).withAddress(address).build();
        clients.add(client);

        return client;
    }

    /**
     * Метод для добавления дебетового счёта
     *
     * @param bank   Банк
     * @param client Клиент
     * @return DebitAccount Созданный дебетовый счёт
     * @throws AccountException
     */
    public DebitAccount addDebitAccount(Bank bank, Client client) throws AccountException {
        var debitAccount = new DebitAccount(bank, client, currentDate);

        client.addAccount(debitAccount);
        bank.addClient(client);

        accounts.add(debitAccount);

        return debitAccount;
    }

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
    public DepositAccount addDepositAccount(Bank bank, Client client, double depositAmount, int depositDuration) throws AccountException, DepositPercentageException {
        var depositAccount = new DepositAccount(bank, client, currentDate, depositAmount, depositDuration);

        client.addAccount(depositAccount);
        bank.addClient(client);

        accounts.add(depositAccount);

        return depositAccount;
    }

    /**
     * Метод для добавления кредитного счёта
     *
     * @param bank          Банк
     * @param client        Клиент
     * @param creditBalance Кредитный баланс
     * @return CreditAccount Созданный кредитный счёт
     * @throws AccountException
     */
    public CreditAccount addCreditAccount(Bank bank, Client client, double creditBalance) throws AccountException {
        var creditAccount = new CreditAccount(bank, client, currentDate, creditBalance);

        client.addAccount(creditAccount);
        bank.addClient(client);

        accounts.add(creditAccount);

        return creditAccount;
    }

    /**
     * Метод для выполнения транзакции
     *
     * @param transaction Транзакция
     * @throws MoneyException
     * @throws AccountException
     */
    public void executeTransaction(Transaction transaction) throws MoneyException, AccountException {
        transaction.execute();
        transactions.add(transaction);
    }

    /**
     * Метод для отмены транзакции
     *
     * @param transaction Транзакция
     * @throws CentralBankException
     * @throws MoneyException
     * @throws AccountException
     */
    public void cancelTransaction(Transaction transaction) throws CentralBankException, MoneyException, AccountException {
        if (!transactions.contains(transaction)) {
            throw CentralBankException.thereWasNoSuchTransaction();
        }

        transaction.cancel();
        transactions.remove(transaction);
    }

    /**
     * Метод для перемотки времени (обновления счетов)
     *
     * @param days Количество дней для перемотки
     * @throws MoneyException
     * @throws AccountException
     */
    public void update(int days) throws MoneyException, AccountException {
        for (Account account : accounts) {
            for (int i = 0; i < days; i++) {
                account.update();
            }
        }

        currentDate.add(Calendar.DAY_OF_MONTH, days);
    }

    /**
     * Метод для получения банка
     *
     * @param name Имя банка
     * @return Bank Банк
     * @throws CentralBankException
     */
    public Bank getBank(String name) throws CentralBankException {
        return banks.stream()
                .filter(x -> Objects.equals(x.name, name))
                .findFirst()
                .orElseThrow(() -> CentralBankException.noSuchBank(name));
    }

    /**
     * Метод для получения клиента
     *
     * @param name    Имя клиента
     * @param surname Фамилия клиента
     * @return Client Клиент
     * @throws CentralBankException
     */
    public Client getClient(String name, String surname) throws CentralBankException {
        return clients.stream()
                .filter(x -> Objects.equals(x.name, name) && Objects.equals(x.surname, surname))
                .findFirst()
                .orElseThrow(() -> CentralBankException.noSuchClient(name, surname));
    }

    /**
     * Метод для получения счёта
     *
     * @param id ID счёта
     * @return Account счёт
     * @throws CentralBankException
     */
    public Account getAccount(String id) throws CentralBankException {
        return accounts.stream()
                .filter(x -> Objects.equals(x.getId().toString(), id))
                .findFirst()
                .orElseThrow(() -> CentralBankException.noSuchAccount(id));
    }
}