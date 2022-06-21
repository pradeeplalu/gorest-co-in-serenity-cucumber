package in.co.gorest.cucumber.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import in.co.gorest.gorestinfo.UsersSteps;
import in.co.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

public class MyStepdefs {
  static String name = "Tenali Ramakrishna" + TestUtils.getRandomValue();
  static String gender = "female";
  static String email = "tenali.ramakrishna" + TestUtils.getRandomValue() + "@email.com";
  static String status = "active";
  static int userID;
  static ValidatableResponse response;

  @Steps
  UsersSteps usersSteps;

  @Given("^I am on homepage of the given application$")
  public void iAmOnHomepageOfTheGivenApplication() {
  }

  @When("^I send a POST request to the application using a valid payload$")
  public void iSendAPOSTRequestToTheApplicationUsingAValidPayload() {
    response = usersSteps.createUser(name, gender, email, status);
    response.log().all().statusCode(201);
    userID = response.log().all().extract().path("id");
    System.out.println(userID);
  }

  @Then("^I get a valid status code (\\d+) from the application$")
  public void iGetAValidStatusCodeFromTheApplication(int code) {
    response.assertThat().statusCode(code);
  }

  @When("^I send a GET request to the application to read newly created user$")
  public void iSendAGETRequestToTheApplicationToReadNewlyCreatedUser() {
    response = usersSteps.getUserByID(userID);
    response.log().all().statusCode(200);
  }

  @And("^I verify if the new user created with correct details$")
  public void iVerifyIfTheNewUserCreatedWithCorrectDetails() {
    HashMap<?, ?> userMap = response.log().all().extract().path("");
    Assert.assertThat(userMap, hasValue(name));
  }


  @When("^I send a PATCH request to the application using a valid payload$")
  public void iSendAPATCHRequestToTheApplicationUsingAValidPayload() {
    name = name + "_updated";
    email = "tenali.ramakrishna" + TestUtils.getRandomValue() + "@email.com";
    response = usersSteps.updateUser(userID, name, gender, email, status);
    response.log().all().statusCode(200);
  }

  @And("^I verify if the new user updated with correct details$")
  public void iVerifyIfTheNewUserUpdatedWithCorrectDetails() {
    HashMap<?, ?> userMap = response.log().all().extract().path("");
    Assert.assertThat(userMap, hasValue(name));
  }

  @When("^I send a DELETE request to the application$")
  public void iSendADELETERequestToTheApplication() {
    response = usersSteps.deleteUser(userID);
    response.log().all().statusCode(204);

  }

  @And("^I verify if the new user deleted from the application$")
  public void iVerifyIfTheNewUserDeletedFromTheApplication() {
    response = usersSteps.getUserByID(userID);
    response.log().all().statusCode(404);
  }

  @When("^I send a GET request to the application to read all users$")
  public void iSendAGETRequestToTheApplicationToReadAllUsers() {
    response = usersSteps.getAllUsers();
  }

  @And("^I verify if the id (\\d+) has name \"([^\"]*)\"$")
  public void iVerifyIfTheIdHasName(int arg0, String expected)  {
    response = usersSteps.getAllUsers();
    List<?> totalRecord = response.log().all().extract().path("findAll{it.id==3900}.name");
    Assert.assertEquals(expected, totalRecord.get(0));
  }

  @And("^I verify if the id (\\d+) had email \"([^\"]*)\"$")
  public void iVerifyIfTheIdHadEmail(int arg0, String expected)  {
    response = usersSteps.getAllUsers();
    List<?> totalRecord = response.log().all().extract().path("findAll{it.id==3900}.email");
    Assert.assertEquals(expected, totalRecord.get(0));
  }

  @And("^I verify if the all id has status \"([^\"]*)\"$")
  public void iVerifyIfTheAllIdHasStatus(String expected)  {
    response = usersSteps.getAllUsers();
    List<String> status = response.log().all().extract().path("status");
    for (String data : status) {
      if (data.equalsIgnoreCase("active")) {
        Assert.assertEquals(expected, data);
      }
    }
  }

  @And("^I verify if the id (\\d+) has gender \"([^\"]*)\"$")
  public void iVerifyIfTheIdHasGender(int arg0, String expected)  {
    response = usersSteps.getAllUsers();
    List<String> gender = response.log().all().extract().path("findAll{it.id==3900}.gender");
    Assert.assertEquals(expected, gender.get(0));
  }

  @And("^I verify if the id (\\d+) had gender \"([^\"]*)\"$")
  public void iVerifyIfTheIdHadGender(int arg0, String expected)  {
    response = usersSteps.getAllUsers();
    List<String> gender = response.log().all().extract().path("findAll{it.id==3897}.gender");
    Assert.assertEquals(expected, gender.get(0));
  }
}
