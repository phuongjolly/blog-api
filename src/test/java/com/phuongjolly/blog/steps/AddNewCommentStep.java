package com.phuongjolly.blog.steps;
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

public class AddNewCommentStep extends BaseStepDefinition{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @When("^user send a request add new comment with correct information$")
    public void userSendARequestAddNewCommentWithCorrectInformation() throws Throwable {
        JSONObject loginInformation = new JSONObject()
                .put("email", "correctuser@email.com")
                .put("password", "admin");

        JSONObject commentInfo = new JSONObject();
        commentInfo.put("content", "Hello Phuong");
        perform(post("/api/posts/1/addNewComment").content(commentInfo.toString())
                .sessionAttr("currentUser", loginInformation));
    }

    @Given("^data comment  has been setup$")
    public void dataCommentHasBeenSetup() throws Throwable {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("data.sql"));

    }

    @Then("^the result should contain new comment in post$")
    public void theResultShouldContainNewCommentInPost() throws Throwable {
        result.andExpect(jsonPath("content").value( "Hello Phuong"));
    }

    @When("^user send a request add new comment with empty information$")
    public void userSendARequestAddNewCommentWithEmptyInformation() throws Throwable {
        JSONObject loginInformation = new JSONObject()
                .put("email", "correctuser@email.com")
                .put("password", "admin");

        JSONObject commentInfo = new JSONObject();
        commentInfo.put("content", ' ');
        perform(post("/api/posts/1/addNewComment")
                .content(commentInfo.toString())
                .sessionAttr("currentUser", loginInformation));
    }

    @Then("^the request should be null$")
    public void theRequestShouldBeNull() throws Throwable {
        result.andExpect(status().isBadRequest());
    }
}
