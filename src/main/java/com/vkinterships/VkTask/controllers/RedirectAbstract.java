package com.vkinterships.VkTask.controllers;

import com.vkinterships.VkTask.services.RedirectionService;

public abstract class RedirectAbstract {
    protected static final String BASIC_URL = "https://jsonplaceholder.typicode.com";

    protected final RedirectionService redirectionService = new RedirectionService();

}
