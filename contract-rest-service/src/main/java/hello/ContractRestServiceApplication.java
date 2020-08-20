package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@SpringBootApplication
public class ContractRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ContractRestServiceApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8084"));
        app.run(args);
    }
}

@RestController
class PersonRestController {

    private final PersonService personService;

    public PersonRestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person/{id}")
    public Person findPersonById(@PathVariable("id") Long id) {
        return personService.findPersonById(id);
    }
}
