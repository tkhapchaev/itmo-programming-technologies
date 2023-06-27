package is.technologies.banks.business.entities.transactions;

import is.technologies.banks.business.exceptions.AccountException;
import is.technologies.banks.business.exceptions.MoneyException;

/**
 * Интерфейс для транзакций
 */
public interface Transaction {
    /**
     * Выполнить транзакцию
     *
     * @throws MoneyException
     * @throws AccountException
     */
    void execute() throws MoneyException, AccountException;

    /**
     * Отменить транзакцию
     *
     * @throws MoneyException
     * @throws AccountException
     */
    void cancel() throws MoneyException, AccountException;
}