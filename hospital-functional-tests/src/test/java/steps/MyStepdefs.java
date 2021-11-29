package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Selenide.open;

public class MyStepdefs {
    @Given("I set get request for api endpoint {string}")
    public void iSetGetRequestForApiEndpoint(String url) {
    }

    @When("I send get http request")
    public void iSendGetHttpRequest() {
    }

    @Then("I receive valid HTTP response {int}")
    public void iReceiveValidHTTPResponse(int status) {
    }

    @And("I receive {string} list of doctors")
    public void iReceiveListOfDoctors(String json) {
    }
}
