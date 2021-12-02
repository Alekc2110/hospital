package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;



public class DoctorApiStepDefinitions {

    private String resourceUrl;
    private ResponseEntity<String> response;
    private final RestTemplate restTemplate;
    private ResponseEntity<String> exchange;

    public DoctorApiStepDefinitions() {
        this.restTemplate = new RestTemplate();
    }

    @Given("I set GET doctor service api endpoint")
    public void iSetGETDoctorServiceApiEndpoint() {
        resourceUrl = "http://localhost:8080/hospital/doctors";
    }


    @When("I send GET HTTP request to get list of doctors")
    public void iSendGETHTTPRequest() {
        response = restTemplate.getForEntity(resourceUrl, String.class);
    }

    @Then("I receive valid HTTP response 200")
    public void iReceiveValidHTTPResponse() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @And("I receive JSON as list of doctors")
    public void iReceiveJSONENTITYAsListOfDoctors() {
        assertTrue(response.hasBody());
        assertThat(response.getBody()).containsAnyOf("Oldrich", "psychiatrist", "Smith", "optometrist");
    }

    @Given("I set Post doctor service api endpoint")
    public void iSetPostDoctorServiceApiEndpoint() {
        resourceUrl = "http://localhost:8080/hospital/doctors";
    }

    @When("I send POST HTTP request to add doctor record")
    public void iSendPOSTHTTPRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String jsonBody = " {\"name\": \"TestBdd\",\"surName\": \"Passed\", \"position\": \"doctor\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        exchange = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, String.class);
    }

    @Then("I receive valid HTTP response 201 added new doctor")
    public void iReceiveValidHTTPResponseCreated() {
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @And("I receive ID of the saved doctor")
    public void iReceiveIDOfSavedDoctor() {
        assertThat(exchange.getBody()).isNotEmpty();
    }

    @Given("I set PUT doctor service api endpoint for id {int}")
    public void iSetPUTDoctorServiceApiEndpointForId(int id) {
        resourceUrl = "http://localhost:8080/hospital/doctors/" + id;
    }

    @When("I send PUT HTTP request to update doctor")
    public void iSendPUTHTTPRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        String jsonBody = " {\"name\": \"UpdatedName\",\"surName\": \"UpdatedSurname\", \"position\": \"doctor\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        exchange = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, String.class);

    }

    @Then("If doctor Updated I receive valid HTTP response 200")
    public void ifUpdatedIReceiveValidHTTPResponse() {
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @And("I receive JSON of the saved doctor")
    public void iReceiveJSONOfTheSavedDoctor() {
        assertThat(exchange.getBody()).isNotEmpty();
        assertThat(exchange.getBody()).contains("UpdatedName", "UpdatedSurname");
    }



}
