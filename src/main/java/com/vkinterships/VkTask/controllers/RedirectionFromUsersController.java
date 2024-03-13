package com.vkinterships.VkTask.controllers;

import com.vkinterships.VkTask.services.RedirectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RedirectionFromUsersController {

    private static final String BASIC_URL = "https://jsonplaceholder.typicode.com";

    private final RedirectionService redirectionService;

    public RedirectionFromUsersController(RedirectionService redirectionService) {
        this.redirectionService = redirectionService;
    }

    @RequestMapping(value = "users",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> basicPath(@RequestParam Map<String,String> allRequestParams,
                                            final HttpServletRequest request,
                                            final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(BASIC_URL + "/" + "users", allRequestParams, request, entity);
    }

    @RequestMapping(value = "users/{id}",
            method = {RequestMethod.GET,
                    RequestMethod.PUT,
                    RequestMethod.DELETE})
    public ResponseEntity<String> withIds(@PathVariable("id") String id,
                                          @RequestParam Map<String,String> allRequestParams,
                                          final HttpServletRequest request,
                                          final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(
                String.join("/", BASIC_URL, "users", id),
                allRequestParams, request, entity);

    }

    @RequestMapping(value = "users/{id}/albums",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> albums(@PathVariable("id") String id,
                                            @RequestParam Map<String,String> allRequestParams,
                                            final HttpServletRequest request,
                                            final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(
                String.join("/", BASIC_URL, "users", id, "albums"),
                allRequestParams,
                request, entity);

    }

    @RequestMapping(value = "users/{id}/todos",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> todos(@PathVariable("id") String id,
                                            @RequestParam Map<String,String> allRequestParams,
                                            final HttpServletRequest request,
                                            final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(
                String.join("/", BASIC_URL, "users", id, "todos"),
                allRequestParams,
                request, entity);

    }

    @RequestMapping(value = "users/{id}/posts",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> posts(@PathVariable("id") String id,
                                        @RequestParam Map<String,String> allRequestParams,
                                        final HttpServletRequest request,
                                        final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(
                String.join("/", BASIC_URL, "users", id, "posts"),
                allRequestParams,
                request, entity);

    }

}

