package com.vkinterships.VkTask.services;

import com.vkinterships.VkTask.entities.Audit;
import com.vkinterships.VkTask.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }

    public Audit getAuditById(Long id) {
        return auditRepository.findById(id).orElse(null);
    }

    public Audit createAudit(Audit book) {
        return auditRepository.save(book);
    }
}
