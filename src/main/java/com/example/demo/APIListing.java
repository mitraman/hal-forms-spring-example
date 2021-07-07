package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.hateoas.server.core.Relation;

@Relation(value="/sandbox/rels/api-listing", collectionRelation="/sandbox/rels/api-listing")
@Entity
public class APIListing {
    
    private @Id @GeneratedValue Long id;
    private String name;
    private String baseURI;

    APIListing() {}
    
    APIListing(String name, String baseURI) {
        this.name = name;
        this.baseURI = baseURI;
    }

    @org.springframework.data.annotation.Id
    @GeneratedValue
    public Long getId() {
        return this.id;
    }

    public String getBaseURI() {
        return this.baseURI;
    }

    public String getName() {
        return this.name;
    }

    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    public void setName(String name) {
        this.name = name;
    }


}