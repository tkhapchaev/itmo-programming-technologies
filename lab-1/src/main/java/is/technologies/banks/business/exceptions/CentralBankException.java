package is.technologies.banks.business.exceptions;

/**
 * Класс, содержащий исключения для сервиса - центрального банка
 */
public class CentralBankException extends Exception {
    private CentralBankException(String message) {
        super(message);
    }

    /**
     * Такой транзакции не существует
     *
     * @return CentralBankException
     */
    public static CentralBankException thereWasNoSuchTransaction() {
        return new CentralBankException("Unable to cancel transaction: there was no such transaction.");
    }

    /**
     * Такого банка не существует
     *
     * @param name Имя банка
     * @return CentralBankException
     */
    public static CentralBankException noSuchBank(String name) {
        return new CentralBankException("Unable to find bank with the name \"" + name + "\".");
    }

    /**
     * Такого клиента не существует
     *
     * @param name    Имя клиента
     * @param surname Фамилия клиента
     * @return CentralBankException
     */
    public static CentralBankException noSuchClient(String name, String surname) {
        return new CentralBankException("Unable to find client with the name \"" + name + "\" and surname \"" + surname + "\".");
    }

    /**
     * Такого счёта не существует
     *
     * @param id ID счёта
     * @return CentralBankException
     */
    public static CentralBankException noSuchAccount(String id) {
        return new CentralBankException("Unable to find account with the id \"" + id + "\".");
    }
}