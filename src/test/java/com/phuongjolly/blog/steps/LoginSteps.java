package com.phuongjolly.blog.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginSteps extends BaseStepDefinition {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @When("^I send login request with correct information$")
    public void iSendLoginRequestWithCorrectInformation() throws Throwable {
        JSONObject loginInformation = new JSONObject()
                .put("email", "correctuser@email.com")
                .put("password", "correctpassword");

        perform(post("/api/users/login").content(loginInformation.toString()));
    }

    @Then("^the request should be successful$")
    public void theRequestShouldBeSuccessful() throws Throwable {
        result.andExpect(status().isOk());
    }

    @And("^the result must contain user information$")
    public void theResultMustContainsUserInformation() throws Throwable {
        result.andExpect(jsonPath("email").value("correctuser@email.com"));
    }

    @When("^I send login request with incorrect information$")
    public void iSendLoginRequestWithIncorrectInformation() throws Throwable {
        JSONObject loginInformation = new JSONObject()
                .put("email", "incorrectuser@email.com")
                .put("password", "correctpassword");

        perform(post("/api/users/login").content(loginInformation.toString()));
    }

    @Then("^the request should fail with Access Deny Exception$")
    public void theRequestShouldFailWithAccessDenyException() throws Throwable {
        result.andExpect(status().isForbidden());
    }

    @Given("^user data has been setup$")
    public void userDataHasBeenSetup() throws Throwable {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("data.sql"));
    }
}
