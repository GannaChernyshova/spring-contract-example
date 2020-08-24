package hello;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PactConsumerDrivenContractUnitTest {
    @Autowired
    MessageService messageService;

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("contract-rest-service", "localhost", 8084, this);

    @Pact(consumer = "contract-rest-client")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return builder.given("test GET")
                .uponReceiving("GET REQUEST")
                .path("/person/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"id\":1,\"name\":\"foo\",\"surname\":\"bee\"}")
                .toPact();
    }

    @Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperBody() {
        messageService.setBackendURL(mockProvider.getUrl());
        Person person = messageService.getPerson(1L);
        BDDAssertions.then(person.getName()).isEqualTo("foo");
    }

}
