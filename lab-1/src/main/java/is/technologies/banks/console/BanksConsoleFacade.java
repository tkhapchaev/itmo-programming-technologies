package is.technologies.banks.console;

import is.technologies.banks.business.entities.accounts.*;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.entities.transactions.Refill;
import is.technologies.banks.business.entities.transactions.Transfer;
import is.technologies.banks.business.entities.transactions.Withdrawal;
import is.technologies.banks.business.exceptions.*;
import is.technologies.banks.business.models.Address;
import is.technologies.banks.business.models.DepositRange;
import is.technologies.banks.business.models.Passport;
import is.technologies.banks.business.services.CentralBankImpl;

import java.util.ArrayList;

/**
 * Класс, являющийся фасадом для сервиса - центального банка
 */
public class BanksConsoleFacade {
    private final CentralBankImpl centralBank;

    public BanksConsoleFacade() {
        centralBank = new CentralBankImpl();
    }

    /**
     * Добавить банк
     *
     * @param name                    Имя банка
     * @param transferLimit           Лимит на перевод
     * @param withdrawalLimit         Лимит на снятие
     * @param balancePercentage       Процент на остаток
     * @param minimalCreditBalance    Минимальный кредитный баланс
     * @param creditAccountCommission Коммиссия для кредитного счёта
     */
    public void addBank(String name, String transferLimit, String withdrawalLimit, String balancePercentage, String minimalCreditBalance, String creditAccountCommission) {
        var depositPercentage = new DepositPercentage();
        depositPercentage.addDepositRange(new DepositRange(0, 50000, 3));
        depositPercentage.addDepositRange(new DepositRange(50000, 100000, 3.5));
        depositPercentage.addDepositRange(new DepositRange(100000, Integer.MAX_VALUE, 4));

        centralBank.addBank(
                name,
                Double.parseDouble(transferLimit),
                Double.parseDouble(withdrawalLimit),
                Double.parseDouble(balancePercentage),
                Double.parseDouble(minimalCreditBalance),
                Double.parseDouble(creditAccountCommission),
                depositPercentage);
    }

    /**
     * Добавить клиента
     *
     * @param name           Имя клиента
     * @param surname        Фамилия клиента
     * @param passportSeries Серия паспорта
     * @param passportNumber Номер паспорта
     * @param country        Страна
     * @param city           Город
     * @param street         Улица
     * @param houseNumber    Номер дома
     * @throws PassportException
     * @throws AddressException
     */
    public void addClient(String name, String surname, String passportSeries, String passportNumber, String country, String city, String street, String houseNumber) throws PassportException, AddressException {
        centralBank.addClient(
                name,
                surname,
                new Passport(Integer.parseInt(passportSeries), Integer.parseInt(passportNumber)),
                new Address(country, city, street, Integer.parseInt(houseNumber)));
    }

    /**
     * Добавить дебетовый счёт
     *
     * @param bankName      Имя банка
     * @param clientName    Имя клиента
     * @param clientSurname Фамилия клиента
     * @return ID добавленного счёта
     * @throws CentralBankException
     * @throws AccountException
     */
    public String addDebitAccount(String bankName, String clientName, String clientSurname) throws CentralBankException, AccountException {
        DebitAccount debitAccount = centralBank.addDebitAccount(centralBank.getBank(bankName), centralBank.getClient(clientName, clientSurname));

        return debitAccount.getId().toString();
    }

    /**
     * Добавить депозитный счёт
     *
     * @param bankName        Имя банка
     * @param clientName      Имя клиента
     * @param clientSurname   Фамилия клиента
     * @param depositAmount   Размер депозита
     * @param depositDuration Продолжительность депозита
     * @return ID добавленного счёта
     * @throws CentralBankException
     * @throws AccountException
     * @throws DepositPercentageException
     */
    public String addDepositAccount(String bankName, String clientName, String clientSurname, String depositAmount, String depositDuration) throws CentralBankException, AccountException, DepositPercentageException {
        DepositAccount depositAccount = centralBank.addDepositAccount(
                centralBank.getBank(bankName),
                centralBank.getClient(clientName, clientSurname),
                Double.parseDouble(depositAmount),
                Integer.parseInt(depositDuration));

        return depositAccount.getId().toString();
    }

    /**
     * Добавить кредитный счёт
     *
     * @param bankName      Имя банка
     * @param clientName    Имя клиента
     * @param clientSurname Фамилия клиента
     * @param creditBalance Кредитный баланс
     * @return ID добавленного счёта
     * @throws CentralBankException
     * @throws AccountException
     */
    public String addCreditAccount(String bankName, String clientName, String clientSurname, String creditBalance) throws CentralBankException, AccountException {
        CreditAccount creditAccount = centralBank.addCreditAccount(
                centralBank.getBank(bankName),
                centralBank.getClient(clientName, clientSurname),
                Double.parseDouble(creditBalance));

        return creditAccount.getId().toString();
    }

    /**
     * Добавить средства на счёт
     *
     * @param accountId ID счёта
     * @param money     Сумма денег
     * @throws CentralBankException
     * @throws MoneyException
     * @throws AccountException
     */
    public void topUp(String accountId, String money) throws CentralBankException, MoneyException, AccountException {
        var refill = new Refill(centralBank.getAccount(accountId), Double.parseDouble(money));

        centralBank.executeTransaction(refill);
    }

    /**
     * Снять деньги со счёта
     *
     * @param accountId ID счёта
     * @param money     Сумма денег
     * @throws CentralBankException
     * @throws MoneyException
     * @throws AccountException
     */
    public void withdraw(String accountId, String money) throws CentralBankException, MoneyException, AccountException {
        var withdrawal = new Withdrawal(centralBank.getAccount(accountId), Double.parseDouble(money));

        centralBank.executeTransaction(withdrawal);
    }

    /**
     * Перевести деньги со счёта
     *
     * @param accountFromId ID счёта отправителя
     * @param accountToId   ID счёта получателя
     * @param money         Сумма денег
     * @throws CentralBankException
     * @throws MoneyException
     * @throws AccountException
     */
    public void transfer(String accountFromId, String accountToId, String money) throws CentralBankException, MoneyException, AccountException {
        var transfer = new Transfer(centralBank.getAccount(accountFromId), centralBank.getAccount(accountToId), Double.parseDouble(money));

        centralBank.executeTransaction(transfer);
    }

    /**
     * Получить баланс счёта
     *
     * @param accountId ID счёта
     * @return Баланс счёта
     * @throws CentralBankException
     */
    public String getBalance(String accountId) throws CentralBankException {
        return Double.toString(centralBank.getAccount(accountId).getBalance());
    }

    /**
     * Получить счета клиента
     *
     * @param name    Имя клиента
     * @param surname Фамилия клиента
     * @return Счета пользователя
     * @throws CentralBankException
     */
    public ArrayList<String> getAccounts(String name, String surname) throws CentralBankException {
        Client client = centralBank.getClient(name, surname);
        var result = new ArrayList<String>();

        for (Account account : client.getAccounts()) {
            result.add(account.toString() + " with ID " + account.getId() + ".");
        }

        return result;
    }
}