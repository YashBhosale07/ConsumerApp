package com.consumerApp.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.function.ServerRequest.Headers;

import com.consumerApp.model.Customer;

@Controller
@RequestMapping("/wishMessage")
public class CustomerController {

	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/wish")
	public String wishMessage(Map<String,Object>map) {
		String url = "http://localhost:4040/providerApp/message";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String msg=response.getBody();
        map.put("Msg",msg);
		return "home";
	}
	
	@ResponseBody
	@GetMapping("/display/{id}/{name}")
	public String displayMessage(@PathVariable int id,@PathVariable String name) {
		String url = "http://localhost:4040/providerApp/welcome/{id}/{name}";
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("name",name);
		ResponseEntity<String>response=restTemplate.getForEntity(url, String.class,map);
		String msg=response.getBody();
		return msg;
	}
	
	@PostMapping("/saveCust/{id}/{name}/{address}")
	@ResponseBody
	public String saveCustomer(@PathVariable int id,@PathVariable String name,@PathVariable String address) {
		Customer c=new Customer();
		c.setId(id);
		c.setName(name);
		c.setAddress(address);
		String url = "http://localhost:4040/providerApp/saveCustomer";
		HttpHeaders http=new HttpHeaders();
		http.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Customer>reEntity=new HttpEntity<Customer>(c,http);
		ResponseEntity<String>reponse=restTemplate.postForEntity(url, reEntity, String.class);
		String msg=reponse.getBody();
		return msg;
	}
	
	@GetMapping("/find/{id}")
	@ResponseBody
	public String findCustomer(@PathVariable int id) {
		String url="http://localhost:4040/providerApp/findById/{id}";
		Map<String,Object>map=new HashMap<>();
		map.put("id", id);
		ResponseEntity<Customer>response=restTemplate.getForEntity(url, Customer.class,map);

		return response.getBody().getName();
	}
	
	
}
