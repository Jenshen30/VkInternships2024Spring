package com.vkinterships.VkTask.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "audits")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 10000)
    private String userName;

    @NotEmpty
    @Size(min = 1, max = 10000)
    private String url;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    public Audit() {
    }

    public Audit(Long id, String userName, String url) {
        this.id = id;
        this.userName = userName;
        this.url = url;
    }
}
