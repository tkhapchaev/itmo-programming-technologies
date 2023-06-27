package is.technologies.banks.business.entities.accounts;

import is.technologies.banks.business.entities.bank.Bank;
import is.technologies.banks.business.entities.client.Client;
import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.DepositPercentageException;
import is.technologies.banks.business.exceptions.MoneyException;

import java.util.Calendar;
import java.util.UUID;

/**
 * Класс "Депозитный счёт", реализующий интерфейс "Счёт"
 */
public class DepositAccount implements Account {
    private double balance;
    private boolean isRestricted;
    public Bank bank;
    public Client owner;
    public Calendar creationDate;
    public int daysBeforeReplenishment;
    public int depositDuration;
    public double replenishment;
    public double depositPercentage;
    public UUID id;

    public DepositAccount(Bank bank, Client owner, Calendar creationDate, double depositAmount, int depositDuration) throws IllegalArgumentException, AccountException, DepositPercentageException {
        if (bank == null) {
            throw new IllegalArgumentException("Bank cannot be null.");
        }

        if (owner == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }

        if (creationDate == null) {
            throw new IllegalArgumentException("Creation date cannot be null.");
        }

        if (depositDuration < 30) {
            throw AccountException.invalidDepositDuration(depositDuration);
        }

        this.bank = bank;
        this.owner = owner;
        this.creationDate = creationDate;
        this.depositPercentage = bank.getDepositPercentage(depositAmount);
        this.daysBeforeReplenishment = 30;
        this.replenishment = 0;
        this.isRestricted = owner.address == null && owner.passport == null;
        this.balance = depositAmount;
        this.id = UUID.randomUUID();
        this.depositDuration = depositDuration;
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

        if (this.balance - money < 0) {
            throw AccountException.withdrawalIsNotAllowed(money);
        }

        if (this.isRestricted && money > this.bank.getWithdrawalLimit()) {
            throw AccountException.withdrawalIsNotAllowed(money);
        }

        if (this.depositDuration != 0) {
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

        if (this.balance - money < 0) {
            throw AccountException.transferIsNotAllowed(money);
        }

        if (this.isRestricted && money > this.bank.getTransferLimit()) {
            throw AccountException.transferIsNotAllowed(money);
        }

        if (this.depositDuration != 0) {
            throw AccountException.withdrawalIsNotAllowed(money);
        }

        withdraw(money);
        account.topUp(money);
    }

    /**
     * Обновить счёт
     */
    public void update() {
        final int NUMBER_OF_DAYS_IN_YEAR = 365;

        if (owner.address != null || owner.passport != null) {
            this.isRestricted = false;
        }

        this.daysBeforeReplenishment -= 1;
        this.replenishment += this.balance * (bank.getBalancePercentage() / NUMBER_OF_DAYS_IN_YEAR);

        if (daysBeforeReplenishment == 0) {
            this.daysBeforeReplenishment = 30;
            this.balance += this.replenishment;
            this.replenishment = 0;
        }

        if (depositDuration != 0) {
            depositDuration -= 1;
        }
    }

    public String toString() {
        return "Deposit account";
    }
}