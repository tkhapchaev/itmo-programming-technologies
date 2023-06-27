package is.technologies.banks.business.entities.publisher;

/**
 * Интерфейс, реализующий "Подписчика" из паттерна "Наблюдатель"
 */
public interface Subscriber {
    /**
     * Добавить уведомление клиенту
     *
     * @param data Уведомление
     */
    void update(String data);
}