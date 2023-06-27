package ru.tkhapchaev.services.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tkhapchaev.entities.Cat;
import ru.tkhapchaev.models.CatDto;
import ru.tkhapchaev.models.message.CatMessage;
import ru.tkhapchaev.services.cat.CatService;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableRabbit
public class RabbitConsumerService {
    private final CatService catService;

    @Autowired
    public RabbitConsumerService(CatService catService) {
        this.catService = catService;
    }

    @RabbitListener(queues = "cats")
    public CatMessage listen(CatMessage message) {
        switch (message.getMethod()) {
            case "create" -> {
                CatDto catDto = message.getCats().get(0);
                catService.save(new Cat(catDto));
                List<CatDto> catDtos = new ArrayList<>();
                catDtos.add(catDto);

                return new CatMessage(message.getId(), message.getMethod(), catDtos);
            }

            case "update" -> {
                CatDto catDto = message.getCats().get(0);
                catService.update(new Cat(catDto));
                List<CatDto> catDtos = new ArrayList<>();
                catDtos.add(catDto);

                return new CatMessage(message.getId(), message.getMethod(), catDtos);
            }

            case "get" -> {
                CatDto catDto = catService.getById(message.getId()).asDto();
                List<CatDto> catDtos = new ArrayList<>();
                catDtos.add(catDto);

                return new CatMessage(message.getId(), message.getMethod(), catDtos);
            }

            case "getAll" -> {
                List<Cat> cats = catService.getAll();
                List<CatDto> catDtos = new ArrayList<>();

                for (Cat cat : cats) {
                    catDtos.add(cat.asDto());
                }

                return new CatMessage(message.getId(), message.getMethod(), catDtos);
            }

            case "getAllCatsByOwnerId" -> {
                List<Cat> cats = catService.getAllByOwnerId(message.getId());
                List<CatDto> catDtos = new ArrayList<>();

                for (Cat cat : cats) {
                    catDtos.add(cat.asDto());
                }

                return new CatMessage(message.getId(), message.getMethod(), catDtos);
            }

            case "delete" -> {
                CatDto catDto = catService.getById(message.getId()).asDto();
                catService.deleteById(message.getId());
                List<CatDto> catDtos = new ArrayList<>();
                catDtos.add(catDto);

                return new CatMessage(message.getId(), message.getMethod(), catDtos);
            }

            case "deleteAll" -> {
                catService.deleteAll();
                List<CatDto> catDtos = new ArrayList<>();

                return new CatMessage(message.getId(), message.getMethod(), catDtos);
            }

            default -> {
                return new CatMessage(null, "500", null);
            }
        }
    }
}