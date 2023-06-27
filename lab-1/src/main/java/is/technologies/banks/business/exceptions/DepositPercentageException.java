package is.technologies.banks.business.exceptions;

/**
 * Класс, содержащий исключения для процента депозита
 */
public class DepositPercentageException extends Exception {
    private DepositPercentageException(String message) {
        super(message);
    }

    /**
     * Нет подходящего депозитного диапазона
     *
     * @param money Сумма денег
     * @return DepositPercentageException
     */
    public static DepositPercentageException noAppropriateRange(double money) {
        return new DepositPercentageException("There is no appropriate deposit range for such amount of money: \"" + money + "\".");
    }
}