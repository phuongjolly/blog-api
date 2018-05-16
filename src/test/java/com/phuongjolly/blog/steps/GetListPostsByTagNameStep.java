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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetListPostsByTagNameStep extends BaseStepDefinition{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @When("^I send a request get list of posts by tag name$")
    public void iSendARequestGetListOfPostsByTagName() throws Throwable {
        perform(get("/api/posts/tags/projects"));
    }

    @Then("^the result must contain list of posts$")
    public void theResultMustContainListOfPosts() throws Throwable {
        result.andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @When("^I send a request get list of posts by wrong tag name$")
    public void iSendARequestGetListOfPostsByWrongTagName() throws Throwable {
        perform(get("/api/posts/tags/wrongTest"));
    }

    @Then("^the posts should be null$")
    public void thePostsShouldBeNull() throws Throwable {
        result.andExpect(content().string("[]"));
    }

    @Given("^data has been setup$")
    public void dataHasBeenSetup() throws Throwable {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("data.sql"));
    }
}
