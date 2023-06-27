package is.technologies.banks.business.entities.publisher;

import is.technologies.banks.business.entities.client.Client;

/**
 * Реализация паттерна "Наблюдатель" для системы оповещений клиентов
 */
public interface Publisher {
    /**
     * Подписать клиента на уведомления
     *
     * @param eventType Тип события
     * @param client    Клиент
     */
    void subscribe(String eventType, Client client);

    /**
     * Отписать клиента от уведомлений
     *
     * @param eventType Тип события
     * @param client    Клиент
     */
    void unsubscribe(String eventType, Client client);
}