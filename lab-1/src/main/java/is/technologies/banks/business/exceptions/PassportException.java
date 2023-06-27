package is.technologies.banks.business.exceptions;

/**
 * Класс, содержащий исключения для модели паспорта
 */
public class PassportException extends Exception {
    private PassportException(String message) {
        super(message);
    }

    /**
     * Некорректная серия паспорта
     *
     * @param series Серия паспорта
     * @return PassportException
     */
    public static PassportException invalidSeries(int series) {
        return new PassportException("Unable to create passport with series \"" + series + "\".");
    }

    /**
     * Некорректный номер паспорта
     *
     * @param number Номер паспорта
     * @return Passport exception
     */
    public static PassportException invalidNumber(int number) {
        return new PassportException("Unable to create passport with number \"" + number + "\".");
    }
}