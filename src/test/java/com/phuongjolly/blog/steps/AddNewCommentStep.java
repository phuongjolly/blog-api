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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AddNewCommentStep extends BaseStepDefinition{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Given("^data has been setup$")
    public void dataHasBeenSetup() throws Throwable {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("data.sql"));
    }

    /*@And("^user has login$")
    public void userHasLogin() throws Throwable {
        JSONObject loginInformation = new JSONObject()
                .put("email", "correctuser@email.com")
                .put("password", "correctpassword");

        perform(post("/api/users/login").content(loginInformation.toString()));
    }*/
/*
    @When("^user send a request add new comment with correct information$")
    public void userSendARequestAddNewCommentWithCorrectInformation() throws Throwable {
        JSONObject loginInformation = new JSONObject()
                .put("email", "correctuser@email.com")
                .put("password", "correctpassword");

        JSONObject commentInfo = new JSONObject();
        commentInfo.put("content", "Hello Phuong");
        perform(post("/api/posts/1/addNewComment").content(commentInfo.toString())
                .sessionAttr("currentUser", loginInformation));
    }

    @Then("^the result should contain new comment in post$")
    public void theResultShouldContainNewCommentInPost() throws Throwable {
        result.andExpect(jsonPath("content").value("Hello Phuong"))
                .andExpect(jsonPath("postId").value("1"));
    }
    
    @When("^user send a request add new comment with empty information$")
    public void userSendARequestAddNewCommentWithEmptyInformation() throws Throwable {
        JSONObject commentInfo = new JSONObject();
        commentInfo.put("content", ' ');
        perform(post("/api/posts/1/addNewComment")
                .content(commentInfo.toString())
                .sessionAttr("currentUser", ""));
    }


    @Then("^the request should be null$")
    public void theRequestShouldBeNull() throws Throwable {
        result.andExpect(content().string(""));
    }
    */
}
