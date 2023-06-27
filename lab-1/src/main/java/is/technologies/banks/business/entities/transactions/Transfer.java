package is.technologies.banks.business.entities.transactions;

import is.technologies.banks.business.entities.accounts.Account;
import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.MoneyException;

/**
 * Класс "Перевод", реализующий интерфейс "Транзакция"
 */
public class Transfer implements Transaction {
    private final Account accountFrom;
    private final Account accountTo;
    private final double money;

    public Transfer(Account accountFrom, Account accountTo, double money) {
        if (accountFrom == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        if (accountTo == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }

        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.money = money;
    }

    /**
     * Выполнить транзакцию
     *
     * @throws MoneyException
     * @throws AccountException
     */
    public void execute() throws MoneyException, AccountException {
        accountFrom.transfer(accountTo, money);
    }

    /**
     * Отменить транзакцию
     *
     * @throws MoneyException
     * @throws AccountException
     */
    public void cancel() throws MoneyException, AccountException {
        accountTo.transfer(accountFrom, money);
    }
}