package com.vkinterships.VkTask.controllers;

import com.vkinterships.VkTask.entities.Audit;
import com.vkinterships.VkTask.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/audits")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("")
    public List<Audit> getAllAudits() {
        return auditService.getAllAudits();
    }

    @GetMapping("/{id}")
    public Audit getAuditById(@PathVariable("id") String id) {
        return auditService.getAuditById(Long.parseLong(id));
    }
}
