package is.technologies.banks.business.models;

/**
 * Модель, хранящая процент депозита для определённого диапазона вложенных средств
 */
public class DepositRange {
    public double rangeStart;
    public double rangeEnd;
    public double percent;

    /**
     * @param rangeStart Начало диапазона
     * @param rangeEnd   Конец диапазона
     * @param percent    Процент депозита
     */
    public DepositRange(double rangeStart, double rangeEnd, double percent) {
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.percent = percent;
    }

    public double getRangeStart() {
        return this.rangeStart;
    }

    public double getRangeEnd() {
        return this.rangeEnd;
    }

    public double getPercent() {
        return this.percent;
    }
}