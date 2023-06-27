package is.technologies.banks.business.exceptions;

/**
 * Класс, содержащий исключения для денежной суммы
 */
public class MoneyException extends Exception {
    private MoneyException(String message) {
        super(message);
    }

    /**
     * Некорректная денежная сумма
     *
     * @param money Сумма денег
     * @return MoneyException
     */
    public static MoneyException invalidAmountOfMoney(double money) {
        return new MoneyException("Invalid amount of money: \"" + money + "\".");
    }
}