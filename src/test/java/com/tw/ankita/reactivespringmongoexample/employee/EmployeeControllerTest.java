package com.tw.ankita.reactivespringmongoexample.employee;

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
import org.springframework.web.reactive.function.BodyInserters;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        employeeRepository.deleteAll().block();
    }

    @Test
    public void shouldGetListForAllEmployees() {

        employeeRepository.save(new Employee(1L, "A")).block();
        employeeRepository.save(new Employee(2L, "B")).block();
        employeeRepository.save(new Employee(3L, "C")).block();
        employeeRepository.save(new Employee(4L, "D")).block();

        FluxExchangeResult<Employee> result = webTestClient.get().uri("/employees/all").exchange()
                .expectStatus().isOk().returnResult(Employee.class);

        StepVerifier.create(result.getResponseBody().collectList()).expectNextMatches(l -> l.size() == 4).verifyComplete();
    }

    @Test
    public void shouldGetEmployeeById() {
        employeeRepository.save(new Employee(1L, "A")).block();
        employeeRepository.save(new Employee(2L, "B")).block();

        FluxExchangeResult<Employee> result = webTestClient.get().uri("/employees/2").exchange()
                .expectStatus().isOk().returnResult(new ParameterizedTypeReference<Employee>() {
                });


        Employee employeeReturned = result.getResponseBody().blockFirst();

        assertTrue(employeeReturned.getEmployeeId() == 2L);
        assertEquals(employeeReturned.getName(), "B");

    }

    @Test
    public void shouldSaveEmployee(){
        Employee employee = new Employee(1L, "A");
        FluxExchangeResult<Employee> result = webTestClient.post().uri("/employees/save").body(BodyInserters.fromObject(employee))
                    .exchange()
                .expectStatus().isOk().returnResult(new ParameterizedTypeReference<Employee>() {
                });

        assertTrue(1L == result.getResponseBody().blockFirst().getEmployeeId());

    }

    @Test
    @Ignore
    @DisplayName("returns random result as its async")
    public void shouldGetListOfAllEmployeesWithTestRestTemplate() {
        ResponseEntity<List> response = testRestTemplate.
                getForEntity("/employees/all", List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<Employee> responseBody = response.getBody();

        assertEquals(4, responseBody.size());

    }

    @Test
    @DisplayName("Returns first five results from infinite stream ")
    public void shouldVerifyNonbBlockingReturn() {
        assumeTrue(employeeRepository.save(new Employee(1L, "Ankita")).block() != null);

        StepVerifier.create(employeeController.getEmployeeEvents(1L).take(5).collectList())
                .expectNextMatches(l -> l.size() == 5).verifyComplete();

    }

    @Test
    public void shouldAssertException(){
        Exception exception = assertThrows(NoSuchElementException.class,()->employeeController.getById(10L).block());
        assertEquals("Employee with id 10 not found",exception.getMessage());

    }

}
