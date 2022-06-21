package in.co.gorest.forestinfo;


import in.co.gorest.gorestinfo.UsersSteps;
import in.co.gorest.testbase.TestBase;
import in.co.gorest.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCURDTest extends TestBase {
    static String name = "TenaliramaKrishna" + TestUtils.getRandomValue();
    static String gender = "female";
    static String email = "tenali.ramakrishna" + TestUtils.getRandomValue() + "@email.com";
    static String status = "active";
    static int userID;

    @Steps
    UsersSteps usersSteps;

    @Title("This will create a new User")
    @Test
    public void test001() {
        ValidatableResponse response = usersSteps.createUser(name, gender, email, status);
        response.log().all().statusCode(201);
        userID = response.log().all().extract().path("id");
        System.out.println(userID);
    }

    @Title("This will Fetch user by ID")
    @Test
    public void test002() {
        ValidatableResponse response = usersSteps.getUserByID(userID);
        response.log().all().statusCode(200);
        HashMap<?, ?> userMap = response.log().all().extract().path("");
        Assert.assertThat(userMap, hasValue(name));
    }

    @Title("This will Fetch user by ID")
    @Test
    public void test003() {
        name = name + "_updated";
        email = "tenali.ramakrishna" + TestUtils.getRandomValue() + "@email.com";
        ValidatableResponse response = usersSteps.updateUser(userID, name, gender, email, status);
        response.log().all().statusCode(200);
        HashMap<?, ?> userMap = response.log().all().extract().path("");
        Assert.assertThat(userMap, hasValue(name));
    }

    @Title("This will Delete user by ID")
    @Test
    public void test004() {
        ValidatableResponse response = usersSteps.deleteUser(userID);
        response.log().all().statusCode(204);
        ValidatableResponse response1 = usersSteps.getUserByID(userID);
        response1.log().all().statusCode(404);
    }

    @Title("1. This will Verify if Total record is 20")
    @Test
    public void test005() {
        ValidatableResponse response = usersSteps.getAllUsers();
        List<?> totalRecord = response.log().all().extract().path("");
        Assert.assertEquals(20, totalRecord.size());
    }

    @Title("2.id 3838 has name =Chaturbhuj Nehru DVM")
    @Test
    public void test006() {
        ValidatableResponse response = usersSteps.getAllUsers();
        List<?> totalRecord = response.log().all().extract().path("findAll{it.id==3838}.name");
        Assert.assertEquals("Chaturbhuj Nehru DVM", totalRecord.get(0));
    }

    @Title("3. id 3838 has email =dvm_chaturbhuj_nehru@pagac.name")
    @Test
    public void test007() {
        ValidatableResponse response = usersSteps.getAllUsers();
        List<?> totalRecord = response.log().all().extract().path("findAll{it.id==3838}.email");
        Assert.assertEquals("dvm_chaturbhuj_nehru@pagac.name", totalRecord.get(0));
    }

    @Title("4.All ID has status =active")
    @Test
    public void test008() {
        ValidatableResponse response = usersSteps.getAllUsers();
        List<String> status = response.log().all().extract().path("status");
        for (String data : status) {
            if (data.equalsIgnoreCase("active")) {
                Assert.assertEquals("active", data);
            }
        }
    }

    @Title("5.id 3829 has gender =female")
    @Test
    public void test009() {
        ValidatableResponse response = usersSteps.getAllUsers();
        List<String> femaleGender = response.log().all().extract().path("findAll{it.id==3829}.gender");
        Assert.assertEquals("female", femaleGender.get(0));
    }
    @Title("6.id 3819 has gender =male")
    @Test
    public void test010() {
        ValidatableResponse response = usersSteps.getAllUsers();
        List<String> femaleGender = response.log().all().extract().path("findAll{it.id==3819}.gender");
        Assert.assertEquals("male", femaleGender.get(0));
    }

}

