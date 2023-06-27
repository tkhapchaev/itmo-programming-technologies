package is.technologies.banks.business.entities.transactions;

import is.technologies.banks.business.entities.accounts.Account;
import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.MoneyException;

/**
 * Класс "Пополнение", реализующий интерфейс "Транзакция"
 */
public class Refill implements Transaction {
    private final Account account;
    private final double money;

    public Refill(Account account, double money) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        this.account = account;
        this.money = money;
    }

    /**
     * Выполнить транзакцию
     *
     * @throws MoneyException
     */
    public void execute() throws MoneyException {
        account.topUp(money);
    }


    /**
     * Отменить транзакцию
     *
     * @throws MoneyException
     * @throws AccountException
     */
    public void cancel() throws MoneyException, AccountException {
        account.withdraw(money);
    }
}