package is.technologies.banks.business.exceptions;

/**
 * Класс, содержащий исключения для банка
 */
public class BankException extends Exception {
    private BankException(String message) {
        super(message);
    }

    /**
     * Некорректный лимит для счёта
     *
     * @param money Сумма денег
     * @return BankException
     */
    public static BankException invalidLimitValue(double money) {
        return new BankException("Invalid limit value: \"" + money + "\".");
    }

    /**
     * Некорректное значение для процентной ставки
     *
     * @param money Сумма денег
     * @return BankException
     */
    public static BankException invalidPercentageValue(double money) {
        return new BankException("Invalid percentage value: \"" + money + "\".");
    }

    /**
     * Некорректное значение комиссии
     *
     * @param money Сумма денег
     * @return BankException
     */
    public static BankException invalidCommissionValue(double money) {
        return new BankException("Invalid commission value: \"" + money + "\".");
    }

    /**
     * Некорректное значение баланса кредитного счёта
     *
     * @param money Сумма денег
     * @return BankException
     */
    public static BankException invalidCreditBalanceValue(double money) {
        return new BankException("Invalid credit balance value: \"" + money + "\".");
    }
}