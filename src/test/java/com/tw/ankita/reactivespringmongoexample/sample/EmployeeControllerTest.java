package com.tw.ankita.reactivespringmongoexample.sample;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    private WebTestClient webTestClient;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    EmployeeController employeeController;

    @Autowired
    EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        webTestClient = WebTestClient.bindToController(employeeController).build();
    }

    @Test
    public void shouldGetListForAllEmployees() {
        FluxExchangeResult<EmployeeEvent> result = webTestClient.get().uri("/sample/all").exchange()
                .expectStatus().isOk().returnResult(new ParameterizedTypeReference<EmployeeEvent>() {
                });


        StepVerifier.create(result.getResponseBody().collectList()).expectNextMatches(l -> l.size() == 4).verifyComplete();
    }

    @Test
    public void shouldGetEmployeeById() {
        FluxExchangeResult<Employee> result = webTestClient.get().uri("/sample/2").exchange()
                .expectStatus().isOk().returnResult(new ParameterizedTypeReference<Employee>() {
                });


        Employee employeeReturned = result.getResponseBody().blockFirst();

        assertTrue(employeeReturned.getEmployeeId() == 2L);
        assertEquals(employeeReturned.getName(), "B");

    }

    @Test
    @Ignore
    @DisplayName("returns random result as its async")
    public void shouldGetListOfAllEmployeesWithTestRestTemplate() {
        ResponseEntity<List> response = testRestTemplate.
                getForEntity("/sample/all", List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<Employee> responseBody = response.getBody();

        assertEquals(4, responseBody.size());

    }

    @Test
    @DisplayName("Returns first five results from infinite stream ")
    public void shouldVerifyNonbBlockingReturn() {

        employeeRepository.deleteAll().subscribe(e -> System.out.println(e + " deleted all"));
        employeeRepository.save(new Employee(1L, "Ankita")).block();

        StepVerifier.create(employeeController.getEmployeeEvents(1L).take(5).collectList())
                .expectNextMatches(l -> l.size() == 5).verifyComplete();

    }

}
