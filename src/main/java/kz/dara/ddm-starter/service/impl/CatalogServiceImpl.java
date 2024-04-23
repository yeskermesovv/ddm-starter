package kz.dara.service.impl;

import kz.dara.service.CatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
public class CatalogServiceImpl implements CatalogService {

    @Value("${integration.ddm}")
    private String ddm;
    final RestTemplate restTemplate;

    public CatalogServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Map<String, Object>> getFromItems(String domain) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<?> response = restTemplate.exchange(
                ddm + domain + "/items" ,
                HttpMethod.GET,
                request,
                Object.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            if(response.getBody() instanceof Map) {
                Map<String, Object> responceBody = (Map<String, Object>) response.getBody();
                if (responceBody.get("status").equals("SUCCESS")) {
                    if(responceBody.get("result") instanceof List) {
                        List<Map<String, Object>> regions = (List<Map<String, Object>>) responceBody.get("result");
                        return regions;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> getFromDdm(String domain, String item)  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<?> response = restTemplate.exchange(
                    ddm + domain + "/items/" + item,
                    HttpMethod.GET,
                    request,
                    Object.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                if(response.getBody() instanceof Map) {
                    Map<String, Object> responceBody = (Map<String, Object>) response.getBody();
                    if (responceBody.get("status").equals("SUCCESS")) {
                        if(responceBody.get("result") instanceof Map) {
                            Map<String, Object> regions = (Map<String, Object>) responceBody.get("result");
                            return regions;
                        }
                    }
                }
            }
        } catch (RestClientException e) {
            log.error("error occured while getting item {} from domain {}", item, domain);
        }

        return null;
    }
}
