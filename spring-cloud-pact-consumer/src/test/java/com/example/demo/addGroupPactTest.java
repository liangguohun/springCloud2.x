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
public class addGroupPactTest {
    @Rule
    public PactProviderRule pactProviderRule = new PactProviderRule("userService", this);
    Map<String, String> reqHeads = new HashMap<>();
    
    @Pact(consumer = "addGroup")
    public PactFragment createFragment(PactDslWithProvider pactDslWithProvider) {
        //@formatter:off
    	
    	reqHeads.put("Authorization", "jdkfjdklfjsdljflksdjflkdfjwefwer2322fsadf");
    	
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return pactDslWithProvider
                .given("200")//生产者对应的状态
                .uponReceiving("create a new user group")//对请求的描述
                    .path("/addGroup")
                    .method("POST")
                    .headers(reqHeads)
                    .body("{\"groupName\":\"Jack's new friends\",\"toAdd\":[1,2,3]}")
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{\"id\":1,\"memberNum\":2,\"name\":\"Jack's Friends\"}")
                .toFragment();
        //@formatter:on
    }

    @Test
    @PactVerification
    public void runTest() throws Exception {
        String url = pactProviderRule.getConfig().url();
        new ConsumerClient(url).post("/addGroup",
        		"{\"groupName\":\"Jack's new friends\",\"toAdd\":[1,2,3]}"
        		, reqHeads, ContentType.APPLICATION_JSON);
    }
   
}
