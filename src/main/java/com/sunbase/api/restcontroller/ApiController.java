package com.sunbase.api.restcontroller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestBody NewUser body) {

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<NewUser> requestEntity = new HttpEntity<>(body);

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Proxy Error");
        }
    }
    @GetMapping("/getCustomerList")
    public ResponseEntity<Object> getData(@RequestHeader("Authorization") String reqHeader) {
        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
       

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders header = new HttpHeaders();
            header.set("Authorization", reqHeader);
            header.setAccessControlAllowOrigin("http://localhost:5500");

            HttpEntity<?> entity = new HttpEntity<>(header);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            String data = response.getBody();

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Proxy Error");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(
            @RequestBody UserData body,
            @RequestHeader("Authorization") String reqHeader) {

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", reqHeader);
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.setAccessControlAllowOrigin("http://localhost:5500");
            // Set query parameters
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("cmd", "create");

            // Create the URL with query parameters
            String urlWithParams = builder.toUriString();

            HttpEntity<UserData> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    urlWithParams,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Proxy Error");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestParam String uuid,
            @RequestBody UserData updatedBody,
            @RequestHeader("Authorization") String reqHeader) {

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();

            headers.set("Authorization", reqHeader);

            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.setAccessControlAllowOrigin("http://localhost:5500/");

            System.out.println(uuid + "body " + updatedBody + "reqHeader " + reqHeader);

            // Set query parameters
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("cmd", "update")
                    .queryParam("uuid", uuid);

            // Create the URL with query parameters
            String urlWithParams = builder.toUriString();

            HttpEntity<UserData> requestEntity = new HttpEntity<>(updatedBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    urlWithParams,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Proxy Error");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(
            @RequestParam String uuid,
            @RequestHeader("Authorization") String reqHeader) {

        String apiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", reqHeader);
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.setAccessControlAllowOrigin("http://localhost:5500");

            // Set query parameters
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .queryParam("cmd", "delete")
                    .queryParam("uuid", uuid);

            // Create the URL with query parameters
            String urlWithParams = builder.toUriString();

            HttpEntity<UserData> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    urlWithParams,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Proxy Error");
        }
    }
}
