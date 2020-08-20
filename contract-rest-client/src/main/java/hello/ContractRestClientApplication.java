package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static java.lang.String.format;

@SpringBootApplication
public class ContractRestClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ContractRestClientApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);
    }
}

@RestController
class MessageRestController {
    private final MessageService messageService;

    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping("/message/{personId}")
    String getMessage(@PathVariable("personId") Long personId) {
        String greeting = "Hello %s!";
        return format(greeting, messageService.getPerson(personId).getName());
    }

}