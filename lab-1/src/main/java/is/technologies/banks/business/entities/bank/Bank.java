package is.technologies.banks.business.entities.bank;

import is.technologies.banks.business.entities.accounts.Account;
import is.technologies.banks.business.entities.accounts.DepositPercentage;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.entities.publisher.Publisher;
import is.technologies.banks.business.entities.publisher.Subscribers;
import is.technologies.banks.business.exceptions.BankException;
import is.technologies.banks.business.exceptions.DepositPercentageException;
import is.technologies.banks.business.models.DepositRange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * Класс, описывающий сущность "Банк"
 */
public class Bank implements Publisher {
    private final ArrayList<Client> clients;
    private final HashMap<String, Subscribers> subscribers;
    private final DepositPercentage depositPercentage;
    private double transferLimit;
    private double withdrawalLimit;
    private double balancePercentage;
    private double minimalCreditBalance;
    private double creditAccountCommission;

    public String name;

    public Bank(String name,
                double transferLimit,
                double withdrawalLimit,
                double balancePercentage,
                double minimalCreditBalance,
                double creditAccountCommission,
                DepositPercentage depositPercentage) {
        if (name == null) {
            throw new IllegalArgumentException("Bank name cannot be null.");
        }

        if (depositPercentage == null) {
            throw new IllegalArgumentException("Deposit percentage cannot be null.");
        }

        this.name = name;
        this.transferLimit = transferLimit;
        this.withdrawalLimit = withdrawalLimit;
        this.balancePercentage = balancePercentage;
        this.minimalCreditBalance = minimalCreditBalance;
        this.creditAccountCommission = creditAccountCommission;
        this.depositPercentage = depositPercentage;

        clients = new ArrayList<>();
        subscribers = new HashMap<>();

        subscribers.put("Debit account", new Subscribers());
        subscribers.put("Deposit account", new Subscribers());
        subscribers.put("Credit account", new Subscribers());
    }

    public double getTransferLimit() {
        return transferLimit;
    }

    public double getWithdrawalLimit() {
        return this.withdrawalLimit;
    }

    public double getBalancePercentage() {
        return this.balancePercentage;
    }

    public double getMinimalCreditBalance() {
        return this.minimalCreditBalance;
    }

    public double getCreditAccountCommission() {
        return this.creditAccountCommission;
    }

    public DepositPercentage getDepositPercentage() {
        return this.depositPercentage;
    }

    public double getDepositPercentage(double money) throws DepositPercentageException {
        return this.depositPercentage.getDepositPercentage(money);
    }

    public Collection<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    public void setTransferLimit(double value) throws BankException {
        if (value <= 0) {
            throw BankException.invalidLimitValue(value);
        }

        this.transferLimit = value;

        String notification = "Notification from bank " + "\"" + name + "\"" + ". New transfer limit for restricted accounts: " + value + ".";

        notify("Debit account", notification);
        notify("Deposit account", notification);
        notify("Credit account", notification);
    }

    public void setWithdrawalLimit(double value) throws BankException {
        if (value <= 0) {
            throw BankException.invalidLimitValue(value);
        }

        this.withdrawalLimit = value;

        String notification = "Notification from bank " + "\"" + name + "\"" + ". New withdrawal limit for restricted accounts: " + value + ".";

        notify("Debit account", notification);
        notify("Deposit account", notification);
        notify("Credit account", notification);
    }

    public void setBalancePercentage(double value) throws BankException {
        if (value <= 0) {
            throw BankException.invalidPercentageValue(value);
        }

        this.balancePercentage = value;

        String notification = "Notification from bank " + "\"" + name + "\"" + ": your percentage on the balance has been changed. New value: " + value + ".";

        notify("Debit account", notification);
        notify("Deposit account", notification);
    }

    public void setMinimalCreditBalance(double value) throws BankException {
        if (value <= 0) {
            throw BankException.invalidLimitValue(value);
        }

        this.minimalCreditBalance = value;

        String notification = "Notification from bank " + "\"" + name + "\"" + ": minimal credit balance has been changed. New value: " + value + ".";

        notify("Credit account", notification);
    }

    public void setCreditAccountCommission(double value) throws BankException {
        if (value <= 0) {
            throw BankException.invalidLimitValue(value);
        }

        this.creditAccountCommission = value;

        String notification = "Notification from bank " + "\"" + name + "\"" + ": the commission for using your account with a negative balance has been changed. New value: " + value + ".";

        notify("Credit account", notification);
    }

    public void addDepositRange(DepositRange depositRange) {
        depositPercentage.addDepositRange(depositRange);

        String notification = "Notification from bank " + "\"" + name + "\"" + ": new rate for a deposit from " + depositRange.rangeStart + " to " + depositRange.rangeEnd + " is " + depositRange.percent + ".";

        notify("Deposit account", notification);
    }

    public void removeDepositRange(DepositRange depositRange) {
        depositPercentage.removeDepositRange(depositRange);
    }

    /**
     * Добавить клиента
     *
     * @param client Клиент
     */
    public void addClient(Client client) {
        clients.add(client);

        for (Account account : client.getAccounts()) {
            subscribe(account.toString(), client);
        }
    }

    /**
     * Удалить клиента
     *
     * @param client Клиент
     */
    public void removeClient(Client client) {
        clients.add(client);

        for (Account account : client.getAccounts()) {
            unsubscribe(account.toString(), client);
        }
    }

    /**
     * Подписать клиента на уведомления
     *
     * @param eventType Тип события
     * @param client    Клиент
     */
    public void subscribe(String eventType, Client client) {
        subscribers.get(eventType).addClient(client);
    }

    /**
     * Отписать клиента от уведомлений
     *
     * @param eventType Тип события
     * @param client    Клиент
     */
    public void unsubscribe(String eventType, Client client) {
        subscribers.get(eventType).removeClient(client);
    }

    /**
     * Уведомить клиента об изменений
     *
     * @param eventType Тип события
     * @param data      Информация
     */
    private void notify(String eventType, String data) {
        if (eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null.");
        }

        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }

        for (Client client : subscribers.get(eventType).getClients()) {
            client.update(data);
        }
    }
}