package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationController {

    private final ConfigurationItemRepository repository;

    ConfigurationController(ConfigurationItemRepository repository) {
        this.repository = repository;
    }

    /*
    @GetMapping
    CollectionModel<EntityModel<Configuration>> list() {
        ArrayList<EntityModel> collection = new ArrayList<EntityModel>;

        Configuration config = new Configuration();

        collection.add(EntityModel.of(config, linkTo(methodOn(ConfigurationController.class).list()).withSelfRel()));

        return CollectionModel.of(collection)
    }
    */

    @GetMapping(value = "/sandbox/config/global")
    EntityModel<?> global() {
        
        HashMap<String,String> configMap = new HashMap<String,String>();
        configMap.put("testing", "test");
        configMap.put("blahblah", "blah");

        return EntityModel.of(configMap,
            linkTo(methodOn(ConfigurationController.class).global()).withSelfRel());
    }


    @GetMapping(value = "/sandbox/config/global", produces = {"application/prs.hal-forms+json" })
    Object globalForm() {
        

        
        ArrayList<HashMap<String,String>> properties = new ArrayList<HashMap<String,String>>();
        repository.findAll().stream().forEach(config ->{
            HashMap<String,String> property = new HashMap<String,String>();
            property.put("name", config.getName());
            property.put("prompt", config.getDescription());
            property.put("value", "0");
            properties.add(property);
        });
                                        
        HashMap<String,Object> defaultTemplate = new HashMap<String,Object>();
        defaultTemplate.put("title", "Change Sandbox Behaviour");
        defaultTemplate.put("method", "PATCH");
        defaultTemplate.put("contentType", "application/json");
        defaultTemplate.put("properties", properties);

        HashMap<String,HashMap> templates = new HashMap<String,HashMap>();
        templates.put("default", defaultTemplate);
        
        HashMap<String,Object> halForm = new HashMap<String,Object>();        
        halForm.put("_templates", templates);
        
        return halForm;
        
    }


    // Adding this just for debug and testing
    @PostMapping( value ="/debug/config/global")
    EntityModel<ConfigurationItem> newConfig(@RequestBody ConfigurationItem newConfigurationItem) {
        repository.save(newConfigurationItem);
        return EntityModel.of(newConfigurationItem);
    }

    
    
    
}