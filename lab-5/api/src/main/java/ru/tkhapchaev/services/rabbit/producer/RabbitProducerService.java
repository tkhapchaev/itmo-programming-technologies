package ru.tkhapchaev.services.rabbit.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tkhapchaev.models.message.CatMessage;
import ru.tkhapchaev.models.message.OwnerMessage;

@Service
public class RabbitProducerService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.exchange-name}")
    private String exchangeName;

    @Value("${rabbit.routing-keys.owners}")
    private String ownersRoutingKey;

    @Value("${rabbit.routing-keys.cats}")
    private String catsRoutingKey;

    @Autowired
    public RabbitProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public CatMessage sendCats(CatMessage message) {
        return (CatMessage) rabbitTemplate.convertSendAndReceive(exchangeName, catsRoutingKey, message);
    }

    public OwnerMessage sendOwners(OwnerMessage message) {
        return (OwnerMessage) rabbitTemplate.convertSendAndReceive(exchangeName, ownersRoutingKey, message);
    }
}