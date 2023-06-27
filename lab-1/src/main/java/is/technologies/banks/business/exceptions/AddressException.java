package is.technologies.banks.business.exceptions;

/**
 * Класс, содержащий исключения для модели адреса
 */
public class AddressException extends Exception {
    private AddressException(String message) {
        super(message);
    }

    /**
     * Некорректный номер дома
     *
     * @param houseNumber Номер дома
     * @return AddressException
     */
    public static AddressException invalidHouseNumber(int houseNumber) {
        return new AddressException("Unable to create address with house number \"" + houseNumber + "\".");
    }
}