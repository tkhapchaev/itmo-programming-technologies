package is.technologies.banks.business.exceptions;

/**
 * Класс, содержащий исключения для счёта
 */
public class AccountException extends Exception {
    private AccountException(String message) {
        super(message);
    }

    /**
     * Некорректный баланс счёта
     *
     * @param money Сумма денег
     * @return AccountException
     */
    public static AccountException invalidBalance(double money) {
        return new AccountException("Invalid account balance: \"" + money + "\".");
    }

    /**
     * Некорректная продолжительность депозита
     *
     * @param depositDuration Продолжительность депозита
     * @return AccountException
     */
    public static AccountException invalidDepositDuration(int depositDuration) {
        return new AccountException("Invalid deposit duration: \"" + depositDuration + "\".");
    }

    /**
     * Снятие со счёта запрещено
     *
     * @param money Сумма денег
     * @return AccountException
     */
    public static AccountException withdrawalIsNotAllowed(double money) {
        return new AccountException("Withdrawal (" + money + ") is not allowed for this account.");
    }

    /**
     * Перевод со счёта запрещёен
     *
     * @param money Сумма денег
     * @return AccountException
     */
    public static AccountException transferIsNotAllowed(double money) {
        return new AccountException("Transfer (" + money + ") is not allowed for this account.");
    }
}