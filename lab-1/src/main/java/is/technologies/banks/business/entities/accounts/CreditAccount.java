package is.technologies.banks.business.entities.accounts;

import is.technologies.banks.business.entities.bank.Bank;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.MoneyException;

import java.util.Calendar;
import java.util.UUID;

/**
 * Класс "Кредитный счёт", реализующий интерфейс "Счёт"
 */
public class CreditAccount implements Account {
    private double balance;
    private boolean isRestricted;
    public Bank bank;
    public Client owner;
    public Calendar creationDate;
    public UUID id;

    public CreditAccount(Bank bank, Client owner, Calendar creationDate, double balance) throws IllegalArgumentException, AccountException {
        if (bank == null) {
            throw new IllegalArgumentException("Bank cannot be null.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }

        if (creationDate == null) {
            throw new IllegalArgumentException("Creation date cannot be null.");
        }

        if (balance < bank.getMinimalCreditBalance()) {
            throw AccountException.invalidBalance(balance);
        }

        this.bank = bank;
        this.owner = owner;
        this.creationDate = creationDate;
        this.balance = balance;
        this.isRestricted = owner.address == null && owner.passport == null;
        this.id = UUID.randomUUID();
    }

    public Bank getBank() {
        return this.bank;
    }

    public Client getOwner() {
        return this.owner;
    }

    public Calendar getCreationDate() {
        return this.creationDate;
    }

    public UUID getId() {
        return this.id;
    }

    public boolean isRestricted() {
        return this.isRestricted;
    }

    public double getBalance() {
        return this.balance;
    }

    /**
     * Пополнить счёт
     *
     * @param money Сумма денег
     * @throws MoneyException
     */
    public void topUp(double money) throws MoneyException {
        if (money <= 0) {
            throw MoneyException.invalidAmountOfMoney(money);
        }

        this.balance += money;
    }

    /**
     * Снять со счёта
     *
     * @param money Сумма денег
     * @throws MoneyException
     * @throws AccountException
     */
    public void withdraw(double money) throws MoneyException, AccountException {
        if (money <= 0) {
            throw MoneyException.invalidAmountOfMoney(money);
        }

        if (isRestricted && money > bank.getWithdrawalLimit()) {
            throw AccountException.withdrawalIsNotAllowed(money);
        }

        this.balance -= money;
    }

    /**
     * Перевод на другой счёт
     *
     * @param account Счёт получателя
     * @param money   Сумма денег
     * @throws MoneyException
     * @throws AccountException
     */
    public void transfer(Account account, double money) throws MoneyException, AccountException {
        if (money <= 0) {
            throw MoneyException.invalidAmountOfMoney(money);
        }

        if (isRestricted && money > bank.getTransferLimit()) {
            throw AccountException.transferIsNotAllowed(money);
        }

        withdraw(money);
        account.topUp(money);
    }

    /**
     * Обновить счёт
     *
     * @throws MoneyException
     * @throws AccountException
     */
    public void update() throws MoneyException, AccountException {
        if (owner.address != null || owner.passport != null) {
            this.isRestricted = false;
        }

        if (balance < 0) {
            withdraw(bank.getCreditAccountCommission());
        }
    }

    public String toString() {
        return "Credit account";
    }
}