package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


public class PatientApiStepDefinitions {
    private String resourceUrl;
    private ResponseEntity<String> response;
    private final RestTemplate restTemplate;
    private ResponseEntity<String> exchange;

    public PatientApiStepDefinitions() {
        this.restTemplate = new RestTemplate();
    }

    @Given("I set GET patient service api endpoint")
    public void iSetGETPatientServiceApiEndpoint() {
        resourceUrl = "http://34.255.193.99:8085/hospital/patients";
    }

    @When("I send GET HTTP request to get list of patients")
    public void iSendGETHTTPRequest() {
        response = restTemplate.getForEntity(resourceUrl, String.class);
    }

    @Then("I receive valid HTTP response 200 returns patients")
    public void iReceiveValidHTTPResponse() {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @And("I receive JSON as list of patients")
    public void iReceiveJSONENTITYAsListOfDoctors() {
        assertTrue(response.hasBody());
        assertThat(response.getBody()).containsAnyOf("Michael", "Jackson");
    }

    @Given("I set Post patient service api endpoint")
    public void iSetPostPatientServiceApiEndpoint() {
        resourceUrl = "http://34.255.193.99:8085/hospital/patients";
    }

    @When("I send POST HTTP request to add patient record")
    public void iSendPOSTHTTPRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        String jsonBody = " {\"name\": \"PatientTest\",\"surName\": \"PatientSaved\", \"age\": \"25\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        exchange = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, String.class);
    }

    @Then("I receive valid HTTP response 201 added new patient")
    public void iReceiveValidHTTPResponseAddedNewPatient() {
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @And("I receive ID of the saved patient")
    public void iReceiveIDOfTheSavedPatient() {
        assertThat(exchange.getBody()).isNotEmpty();
    }

    @Given("I set PUT patient service api endpoint for id {int}")
    public void iSetPUTPatientServiceApiEndpointForId(int id) {
        resourceUrl = "http://34.255.193.99:8085/hospital/patients/" + id;
    }

    @When("I send PUT HTTP request to update patient")
    public void iSendPUTHTTPRequestToUpdatePatient() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        String jsonBody = " {\"name\": \"UpdatedName\",\"surName\": \"UpdatedSurname\", \"age\": \"71\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        exchange = restTemplate.exchange(resourceUrl, HttpMethod.PUT, entity, String.class);
    }

    @Then("If patient Updated I receive valid HTTP response 200")
    public void ifPatientUpdatedIReceiveValidHTTPResponse() {
        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @And("I receive JSON of the saved patient")
    public void iReceiveJSONOfTheSavedPatient() {
        assertThat(exchange.getBody()).isNotEmpty();
        assertThat(exchange.getBody()).contains("UpdatedName", "UpdatedSurname", "71");
    }
}
