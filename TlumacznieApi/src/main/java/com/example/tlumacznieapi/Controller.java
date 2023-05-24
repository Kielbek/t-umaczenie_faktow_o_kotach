package com.example.tlumacznieapi;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class Controller {

    @GetMapping("/translate")
    public String translateCatsFacts() {
        List<Root> root = searchTemperature();
        StringBuilder a = new StringBuilder();
        for (Root root1 : root) {
            a.append(translate(root1.text)).append("\n");
        }
        return a.toString();
    }

    public List<Root> searchTemperature() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://cat-fact.herokuapp.com/facts";

        ResponseEntity<List<Root>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Root>>() {
                }
        );

        return response.getBody();
    }

    public Root2 translate(String word) {
        String url = "https://google-translate1.p.rapidapi.com/language/translate/v2";
        RestTemplate rest = new RestTemplate();

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("q", word);
        map.add("target", "pl");
        map.add("source", "en");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", "4d659798dfmsh93836d29ed443c2p1b709ajsn0776ceacd999");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<Root2> exchange = rest.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                Root2.class);

        return exchange.getBody();
    }
}
