package com.smartosc.training.service.impl;

import com.duong.training.entity.APIResponse;
import com.duong.training.entity.LoginRequest;
import com.duong.training.exception.RestTemplateException;
import com.smartosc.training.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class RestServiceImpl implements RestService {

    private static final Logger logger = LoggerFactory.getLogger(RestServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    public <T> APIResponse<T> execute(String url, HttpMethod method, HttpHeaders headers,
                                      Object body, ParameterizedTypeReference<APIResponse<T>> type,
                                      Map<String, Object> values) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<APIResponse<T>> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type,
                    values);
            if (response.getStatusCodeValue() >= HttpStatus.OK.value()
                    && response.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return response.getBody();
            }
            logger.info("Can't get response from API (with params) - {}", response.getBody().getMessage());
            throw new RestTemplateException(response.getBody().getMessage());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    public <T> APIResponse<T> execute(String url, HttpMethod method, HttpHeaders headers,
                                      Object body, ParameterizedTypeReference<APIResponse<T>> type) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<APIResponse<T>> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type);
            if (response.getStatusCodeValue() >= HttpStatus.OK.value()
                    && response.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return response.getBody();
            }
            logger.info("Can't get response from API - {}", response.getBody().getMessage());
            throw new RestTemplateException(response.getBody().getMessage());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public <T> ResponseEntity<T> execute2(String url, HttpMethod method, HttpHeaders headers, Object body, ParameterizedTypeReference<T> type, Map<String, Object> values) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type,
                    values);
            if (response.getStatusCodeValue() >= HttpStatus.OK.value()
                    && response.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return response;
            }
            logger.info("Can't get response from API (with params) - {}");
            throw new RestTemplateException(response.getBody().toString());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    public <T> ResponseEntity<T> execute2(String url, HttpMethod method, HttpHeaders headers,
                                          Object body, ParameterizedTypeReference<T> type) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    type);
            if (response.getStatusCodeValue() >= HttpStatus.OK.value() && response.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return response;
            }
            logger.info("Can't get response from API - {}", response.getStatusCode());
            throw new RestTemplateException("Could not get data");
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public String getToken(String url, HttpMethod method, LoginRequest loginRequest) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(loginRequest);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    method,
                    entity,
                    new ParameterizedTypeReference<String>() {
                    });
            if (response.getStatusCodeValue() >= HttpStatus.OK.value()) {
                return response.getBody();
            }
            logger.info("Can't get data from API - {}");
            throw new RestTemplateException(response.getBody());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }

    @Override
    public <T> APIResponse<T> exchangePaging(String url, HttpMethod method, HttpHeaders headers, Object body) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ParameterizedTypeReference<APIResponse<T>> reponseType = new ParameterizedTypeReference<APIResponse<T>>() {
            };
            ResponseEntity<APIResponse<T>> res = restTemplate.exchange(url, method, entity, reponseType);
            if (res.getStatusCodeValue() >= HttpStatus.OK.value() && res.getStatusCodeValue() < HttpStatus.MULTIPLE_CHOICES.value()) {
                return res.getBody();
            }
            logger.error(res.getBody().getMessage());
            throw new RestTemplateException(res.getBody().getMessage());
        } catch (Exception e) {
            throw new RestTemplateException(e.getMessage(), e);
        }
    }
}