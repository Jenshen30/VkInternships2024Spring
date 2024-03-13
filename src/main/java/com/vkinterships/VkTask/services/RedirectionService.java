package com.vkinterships.VkTask.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vkinterships.VkTask.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RedirectionService {

    private static LoadingCache<CachingRequest, ResponseEntity<String>> memo = CacheBuilder.newBuilder()
            .maximumSize(100)
            .build(new CacheLoader<>() {
                @Override
                public ResponseEntity<String> load(CachingRequest key) throws Exception {
                    return makeRequest(key);
                }
            });


    public static ResponseEntity<String> commonRedirect(final String path,
                                                  @RequestParam Map<String,String> allRequestParams,
                                                  final HttpServletRequest request,
                                                  final HttpEntity<String> entity) {
        // especially to clear all others useless headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=UTF-8");

        CachingRequest cached = new CachingRequest(HttpMethod.valueOf(request.getMethod()),
                new HttpEntity<>(entity.getBody(), headers), path, allRequestParams);

        // take or invoke makeRequest
        return memo.getUnchecked(cached);
    }

    private static ResponseEntity<String> makeRequest(CachingRequest cached) {

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
                cached.path + Utils.mapToHttpParams(cached.allRequestParams),
                cached.method,
                cached.entity,
                String.class,
                cached.allRequestParams);
    }

    private static class CachingRequest {
        final HttpMethod method;
        final HttpEntity entity;

        final String path;
        final Map<String,String> allRequestParams;

        public CachingRequest(HttpMethod method, HttpEntity entity, String path, Map<String, String> allRequestParams) {
            this.method = method;
            this.entity = entity;
            this.path = path;
            this.allRequestParams = allRequestParams;
        }
    }

}
