package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.junit.Rule;
import org.junit.Test;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRule;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;

/**
 * The purpose of this test class is to demonstrate the style of creating a contract test with PACT and annotation style.
 */
public class addMessagePactTest {
    @Rule
    public PactProviderRule pactProviderRule = new PactProviderRule("postService", this);
    Map<String, String> reqHeads = new HashMap<>();
    
    @Pact(consumer = "addFeedBack")
    public PactFragment createFragment(PactDslWithProvider pactDslWithProvider) {
        //@formatter:off
    	
    	reqHeads.put("Authorization", "jdkfjdklfjsdljflksdjflkdfjwefwer2322fsadf");
    	
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return pactDslWithProvider
                .given("200")//生产者对应的状态
                .uponReceiving("create a new post")//对请求的描述
                    .path("/addPost/1")
                    .method("POST")
                    .headers(reqHeads)
                    .body("{\"title\":\"new post\",\"content\":\"listen\",\"multimedia\":[{\"type\":1,\"href\":\"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2865672302,4153895132&fm=200&gp=0.jpg\"}],\"recipients\":[1,2]}")
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                .toFragment();
        //@formatter:on
    }

    @Test
    @PactVerification
    public void runTest() throws Exception {
        String url = pactProviderRule.getConfig().url();
        new ConsumerClient(url).post("/addPost/1",
        		"{\"title\":\"new post\",\"content\":\"listen\",\"multimedia\":[{\"type\":1,\"href\":\"https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2865672302,4153895132&fm=200&gp=0.jpg\"}],\"recipients\":[1,2]}"
        		, reqHeads, ContentType.APPLICATION_JSON);
    }
   
}
