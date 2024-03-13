package com.vkinterships.VkTask.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "audits")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 10000)
    private String USER_NAME;

    @NotEmpty
    @Size(min = 1, max = 10000)
    private String url;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp created_at;

    public Audit() {
    }

    public Audit(Long id, String userName, String url) {
        this.id = id;
        this.USER_NAME = userName;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getUrl() {
        return url;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", USER_NAME='" + USER_NAME + '\'' +
                ", url='" + url + '\'' +
                ", createdAt=" + created_at +
                '}';
    }
}
