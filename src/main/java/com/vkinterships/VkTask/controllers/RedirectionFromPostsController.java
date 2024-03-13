package com.vkinterships.VkTask.controllers;

import com.vkinterships.VkTask.services.RedirectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RedirectionFromPostsController {

    private static final String BASIC_URL = "https://jsonplaceholder.typicode.com";

    private final RedirectionService redirectionService;

    public RedirectionFromPostsController(RedirectionService redirectionService) {
        this.redirectionService = redirectionService;
    }

    @RequestMapping(value = "posts",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> basicPath(@RequestParam Map<String,String> allRequestParams,
                                            final HttpServletRequest request,
                                            final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(BASIC_URL + "/" + "posts", allRequestParams, request, entity);
    }

    @RequestMapping(value = "posts/{id}",
            method = {RequestMethod.GET,
                    RequestMethod.PUT,
                    RequestMethod.DELETE})
    public ResponseEntity<String> withIds(@PathVariable("id") String id,
                                          @RequestParam Map<String,String> allRequestParams,
                                          final HttpServletRequest request,
                                          final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(
                String.join("/", BASIC_URL, "posts", id),
                allRequestParams, request, entity);
    }

    @RequestMapping(value = "posts/{id}/comments",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> basicPath(@PathVariable("id") String id,
                                            @RequestParam Map<String,String> allRequestParams,
                                            final HttpServletRequest request,
                                            final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(
                String.join("/", BASIC_URL, "posts", id, "comments"),
                allRequestParams,
                request, entity);
    }
}
