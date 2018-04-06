package com.phuongjolly.blog.steps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetCommentsStep extends BaseStepDefinition{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @When("^I send a request get list of comments with current post id$")
    public void iSendARequestGetListOfCommentsWithCurrentPostId() throws Throwable {
        perform(get("/api/posts/1/comments"));
    }

    @Then("^The result must contain list of comments$")
    public void theResultMustContainListOfComments() throws Throwable {
        result.andExpect(jsonPath("$", Matchers.hasSize(2)));

    }

    @When("^I send a request get list of comments with wrong post id$")
    public void iSendARequestGetListOfCommentsWithWrongPostId() throws Throwable {
        perform(get("/api/posts/0/comments"));
    }

    @Then("^the result should be null$")
    public void theResultShouldBeNull() throws Throwable {
        result.andExpect(content().string("[]"));
    }

    @Given("^comment data has been setup$")
    public void commentDataHasBeenSetup() throws Throwable {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("data.sql"));
    }
}
