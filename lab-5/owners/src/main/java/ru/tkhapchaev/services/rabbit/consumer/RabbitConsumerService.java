package ru.tkhapchaev.services.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Owner;
import ru.tkhapchaev.models.OwnerDto;
import ru.tkhapchaev.models.message.OwnerMessage;
import ru.tkhapchaev.services.owner.OwnerService;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableRabbit
public class RabbitConsumerService {
    private final OwnerService ownerService;

    @Autowired
    public RabbitConsumerService(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RabbitListener(queues = "owners")
    public OwnerMessage listen(OwnerMessage message) {
        switch (message.getMethod()) {
            case "create" -> {
                OwnerDto ownerDto = message.getOwners().get(0);
                ownerService.save(new Owner(ownerDto));
                List<OwnerDto> ownerDtos = new ArrayList<>();
                ownerDtos.add(ownerDto);

                return new OwnerMessage(message.getId(), message.getMethod(), ownerDtos);
            }

            case "update" -> {
                OwnerDto ownerDto = message.getOwners().get(0);
                ownerService.update(new Owner(ownerDto));
                List<OwnerDto> ownerDtos = new ArrayList<>();
                ownerDtos.add(ownerDto);

                return new OwnerMessage(message.getId(), message.getMethod(), ownerDtos);
            }

            case "get" -> {
                OwnerDto ownerDto = ownerService.getById(message.getId()).asDto();
                List<OwnerDto> ownerDtos = new ArrayList<>();
                ownerDtos.add(ownerDto);

                return new OwnerMessage(message.getId(), message.getMethod(), ownerDtos);
            }

            case "getAll" -> {
                List<Owner> owners = ownerService.getAll();
                List<OwnerDto> ownerDtos = new ArrayList<>();

                for (Owner owner : owners) {
                    ownerDtos.add(owner.asDto());
                }

                return new OwnerMessage(message.getId(), message.getMethod(), ownerDtos);
            }

            case "delete" -> {
                OwnerDto ownerDto = ownerService.getById(message.getId()).asDto();
                ownerService.deleteById(message.getId());
                List<OwnerDto> ownerDtos = new ArrayList<>();
                ownerDtos.add(ownerDto);

                return new OwnerMessage(message.getId(), message.getMethod(), ownerDtos);
            }

            case "deleteAll" -> {
                ownerService.deleteAll();
                List<OwnerDto> ownerDtos = new ArrayList<>();

                return new OwnerMessage(message.getId(), message.getMethod(), ownerDtos);
            }

            default -> {
                return new OwnerMessage(null, "500", null);
            }
        }
    }
}