package com.example.demo;

import java.util.HashMap;
import java.util.Map;

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
public class delPostPactTest {
    @Rule
    public PactProviderRule pactProviderRule = new PactProviderRule("postService", this);
    Map<String, String> reqHeads = new HashMap<>();
    
    @Pact(consumer = "delPost")
    public PactFragment createFragment(PactDslWithProvider pactDslWithProvider) {
        //@formatter:off
    	
    	reqHeads.put("Authorization", "jdkfjdklfjsdljflksdjflkdfjwefwer2322fsadf");
    	
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return pactDslWithProvider
                .given("200")//生产者对应的状态
                .uponReceiving("del user's post")//对请求的描述
                    .path("/delPost/1")
                    .method("GET")
                    .headers(reqHeads)
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
        new ConsumerClient(url).getAsMap("/delPost/1", reqHeads);
    }
   
}
