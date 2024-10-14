package com.consumerApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ConsumerController implements CommandLineRunner {
  
	 @Autowired
	private  RestTemplate restTemplate;

  

    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("ConsumerController run method called."); 
        try {
            String url = "http://localhost:4040/providerApp/message";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println(response.getBody());
        } catch (RestClientException e) {
            System.err.println("Error occurred while calling the provider: " + e.getMessage());
        }
    }
    
    

}
