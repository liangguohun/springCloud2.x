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
public class loginPactTest {
    @Rule
    public PactProviderRule pactProviderRule = new PactProviderRule("userService", this);

    @Pact(consumer = "userLogin")
    public PactFragment createFragment(PactDslWithProvider pactDslWithProvider) {
        //@formatter:off
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");
        return pactDslWithProvider
                .given("200")//生产者对应的状态
                .uponReceiving("user login")//对请求的描述
                    .path("/login")
                    .method("POST")
                    .body("{\"name\":\"liangguohun\",\"password\":\"123456\"}")
                .willRespondWith()
                    .status(200)
                    .headers(headers)
                    .body("{\"jwt\":\"jdkfjdklfjsdljflksdjflkdfjwefwer2322fsadf\",\"userInfo\":{\"head\":\"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1217336681,135226422&fm=27&gp=0.jpg\",\"name\":\"liangguohun\",\"star\":4.0,\"address\":\"Los Angeles\",\"phoneNum\":\"1-626-456-6688\",\"email\":\"liangguohun@qq.com\",\"children\":[{\"id\":1,\"firstName\":\"Annie\",\"lastName\":\"Mila\",\"school\":\"Los Angeles School\",\"grade\":2}]}}")
                .toFragment();
        //@formatter:on
    }

    @Test
    @PactVerification
    public void runTest() throws Exception {
        String url = pactProviderRule.getConfig().url();
        new ConsumerClient(url).post("/login", "{\"name\":\"liangguohun\",\"password\":\"123456\"}", ContentType.APPLICATION_JSON);
    }
   
}
