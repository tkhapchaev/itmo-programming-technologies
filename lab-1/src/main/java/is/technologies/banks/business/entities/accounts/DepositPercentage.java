package is.technologies.banks.business.entities.accounts;

import is.technologies.banks.business.exceptions.DepositPercentageException;
import is.technologies.banks.business.models.DepositRange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Класс "Процент депозита", содержащий коллекцию из DepositRange
 */
public class DepositPercentage {
    private final ArrayList<DepositRange> depositRanges;

    public DepositPercentage() {
        depositRanges = new ArrayList<>();
    }

    public Collection<DepositRange> getDepositRanges() {
        return Collections.unmodifiableList(depositRanges);
    }

    /**
     * Добавить депозитный интервал
     *
     * @param depositRange Депозитный интервал
     * @throws IllegalArgumentException
     */
    public void addDepositRange(DepositRange depositRange) throws IllegalArgumentException {
        if (depositRange == null) {
            throw new IllegalArgumentException("Deposit range cannot be null.");
        }

        depositRanges.add(depositRange);
    }

    /**
     * Удалить депозитный интервал
     *
     * @param depositRange Депозитный интервал
     * @throws IllegalArgumentException
     */
    public void removeDepositRange(DepositRange depositRange) throws IllegalArgumentException {
        if (depositRange == null) {
            throw new IllegalArgumentException("Deposit range cannot be null.");
        }

        depositRanges.remove(depositRange);
    }

    /**
     * Получить депозитный интервал
     *
     * @param money Сумма денег
     * @return double Процент по депозиту
     * @throws DepositPercentageException
     */
    public double getDepositPercentage(double money) throws DepositPercentageException {
        for (DepositRange depositRange : depositRanges) {
            if (money <= depositRange.rangeEnd && money >= depositRange.rangeStart) {
                return depositRange.percent;
            }
        }

        throw DepositPercentageException.noAppropriateRange(money);
    }
}
