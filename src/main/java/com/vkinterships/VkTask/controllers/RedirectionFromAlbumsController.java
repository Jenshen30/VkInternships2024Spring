package com.vkinterships.VkTask.controllers;


import com.vkinterships.VkTask.services.RedirectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RedirectionFromAlbumsController {

    private static final String BASIC_URL = "https://jsonplaceholder.typicode.com";

    private final RedirectionService redirectionService;

    public RedirectionFromAlbumsController(RedirectionService redirectionService) {
        this.redirectionService = redirectionService;
    }

    @RequestMapping(value = "albums",
            method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<String> basicPath(@RequestParam Map<String,String> allRequestParams,
                                            final HttpServletRequest request,
                                            final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(BASIC_URL + "/" + "albums", allRequestParams, request, entity);
    }

    @RequestMapping(value = "albums/{id}",
            method = {RequestMethod.GET,
                    RequestMethod.PUT,
                    RequestMethod.DELETE})
    public ResponseEntity<String> withIds(@PathVariable("id") String id,
                                          @RequestParam Map<String,String> allRequestParams,
                                          final HttpServletRequest request,
                                          final HttpEntity<String> entity) {

        return redirectionService.commonRedirect(
                String.join("/", BASIC_URL, "albums", id),
                allRequestParams, request, entity);
    }
}
