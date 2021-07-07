package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIListingController {

    private final APIListingRepository repository;

    APIListingController(APIListingRepository repository) {
        this.repository = repository;
    }


/*
    @GetMapping("/employees")
CollectionModel<EntityModel<Employee>> all() {

  List<EntityModel<Employee>> employees = repository.findAll().stream()
      .map(employee -> EntityModel.of(employee,
          linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
          linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
      .collect(Collectors.toList());

  return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}
*/



    @GetMapping(value = "/sandbox/apis", produces = {"application/hal+json"})
    CollectionModel<EntityModel<APIListing>> all() {
        
        List <EntityModel<APIListing>> apiListingCollection = repository.findAll().stream()
            .map(apiListing -> EntityModel.of(apiListing,
                linkTo(methodOn(APIListingController.class).one(apiListing.getId())).withSelfRel()))
            .collect(Collectors.toList());

        return CollectionModel.of(apiListingCollection,
            linkTo(methodOn(APIListingController.class).all()).withSelfRel()
        );
    }
        
    @RequestMapping(value = "/sandbox/apis/{id}", produces = {"application/hal+json"})
    EntityModel<APIListing> one(@PathVariable Long id) {
        APIListing apiListing = repository.findById(id).get();

        return EntityModel.of(apiListing, 
            linkTo(methodOn(APIListingController.class).one(id)).withSelfRel()
        );
    }    

    /*
    @GetMapping("/apis/{id}")
    HttpEntity<APIListing> one(@PathVariable Long id) {

    }
    */
    
    @PostMapping("/sandbox/apis")
    APIListing newAPIListing(@RequestBody APIListing newAPIListing) {
        return repository.save(newAPIListing);
    }
}