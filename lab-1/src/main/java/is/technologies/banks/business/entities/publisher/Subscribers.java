package is.technologies.banks.business.entities.publisher;

import is.technologies.banks.business.entities.client.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Класс с коллекцией "подписчиков"
 */
public class Subscribers {
    private final ArrayList<Client> clients;

    public Subscribers() {
        this.clients = new ArrayList<>();
    }

    public Collection<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    /**
     * Добавить клиента в подписчики
     *
     * @param client Клиент
     */
    public void addClient(Client client) {
        clients.add(client);
    }

    /**
     * Удалить клиента из подписчиков
     *
     * @param client Клиент
     */
    public void removeClient(Client client) {
        clients.remove(client);
    }
}