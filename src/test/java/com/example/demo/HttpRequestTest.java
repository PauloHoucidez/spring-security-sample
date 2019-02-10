package com.example.demo;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void getHelloMessageTestwithToken() throws Exception {
        ResponseEntity<String> exchange = this.restTemplate
                .withBasicAuth("admin", "pass")
                .exchange("http://localhost:" + port + "/index", HttpMethod.GET, null, String.class);
        
        if(exchange.getHeaders().containsKey("Authorization")){
            
            HttpEntity httpEntity = new HttpEntity(exchange.getHeaders());
            ResponseEntity<String> exchange1 = this.restTemplate.exchange("http://localhost:" + port + "/index", HttpMethod.GET, httpEntity, String.class);
            assertThat(exchange1.getBody()).contains("Hello");
            
        }
        
    }
}