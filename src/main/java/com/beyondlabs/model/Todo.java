package com.beyondlabs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
public class Todo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userid;
    
    @NotNull
   // @Min(3)
    //@Max(100)
    @Length(min = 3,max = 100,message = "shoule be greater then 3 or less then 100 ")
    private String title;
    
    private String description;
    
    private Status status;
    
    
    @CreatedDate
    private Instant createdAt;
    
    @LastModifiedDate
    protected Instant updatedAt;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUserid() {
        return userid;
    }
    
    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
