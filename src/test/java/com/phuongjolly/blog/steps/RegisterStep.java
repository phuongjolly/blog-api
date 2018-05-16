package com.phuongjolly.blog.steps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterStep extends BaseStepDefinition{
    @When("^User put correct information$")
    public void userPutCorrectInformation() throws Throwable {
        JSONObject alex = new JSONObject()
                .put("name", "alex")
                .put("email", "alex@email.com")
                .put("password", "alex");

        perform(post("/api/users/register").content(alex.toString()));
    }

    @Then("^result should contain new user information$")
    public void resultShouldContainNewUserInformation() throws Throwable {
        result.andExpect(jsonPath("email").value("alex@email.com"));
    }
}
