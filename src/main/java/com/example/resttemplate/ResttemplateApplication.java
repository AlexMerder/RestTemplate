package com.example.resttemplate;

import com.example.resttemplate.model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
public class ResttemplateApplication {

    private static final String URL_USER = "http://91.241.64.178:7081/api/users";
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        String cookies = getAllUsers();
        System.out.println("Cookies: " + cookies);
        String resultCode = saveUser(cookies) + updateUser(cookies) + deleteUser(cookies);
        System.out.println(resultCode);

    }

    private static String getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> result = restTemplate
                .exchange(URL_USER, HttpMethod.GET, entity, String.class);

        return result.getHeaders().getFirst("set-cookie");
    }

    private static String saveUser(String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookies);

        return restTemplate.exchange(URL_USER, HttpMethod.POST,
                new HttpEntity<>(new User(3L, "James", "Brown", (byte) 16),
                        headers), String.class).getBody();
    }

    private static String updateUser(String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.COOKIE, cookies);

        return restTemplate.exchange(URL_USER, HttpMethod.PUT,
                new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 29),
                        headers), String.class).getBody();

    }

    private static String deleteUser(String cookies) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, cookies);

        return restTemplate
                .exchange(URL_USER + "/3", HttpMethod.DELETE, new HttpEntity<>(headers), String.class)
                .getBody();
    }


}
