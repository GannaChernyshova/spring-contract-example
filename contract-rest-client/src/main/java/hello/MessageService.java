package hello;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageService {
    private String backendURL = "http://localhost:8084";

    public void setBackendURL(String backendURL) {
        this.backendURL = backendURL;
    }

    Person getPerson(Long personId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendURL+ "/person/{personId}", Person.class, personId);
    }
}
