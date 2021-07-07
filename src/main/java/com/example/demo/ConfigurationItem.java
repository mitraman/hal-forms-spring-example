package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.core.Relation;

@Entity
public class ConfigurationItem {
    
    private @Id @GeneratedValue Long id;
    private String name;
    private String type;
    private String description;
    private Boolean required;

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

}