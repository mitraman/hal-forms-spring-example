package com.example.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIListingController {

    Logger log = LoggerFactory.getLogger(SandboxController.class);

    private final APIListingRepository repository;
    private final String APIListingPath = "/sandbox/apis";

    APIListingController(APIListingRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/sandbox/apis", produces = { "application/hal+json" })
    CollectionModel<EntityModel<APIListing>> all() {

        List<EntityModel<APIListing>> apiListingCollection = repository.findAll().stream()
                .map(apiListing -> EntityModel.of(apiListing,
                        linkTo(methodOn(APIListingController.class).one(apiListing.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(apiListingCollection,
                linkTo(methodOn(APIListingController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/sandbox/apis/{id}", produces = { "application/hal+json", "application/prs.hal-forms+json" })
    EntityModel<APIListing> one(@PathVariable Long id) {
        APIListing apiListing = repository.findById(id).get();

        return EntityModel.of(apiListing, linkTo(methodOn(APIListingController.class).one(id)).withSelfRel()
            .andAffordance(afford(methodOn(APIListingController.class).updateAPIListing(apiListing))));
    }

    // Factory endpoint for creating a new API listing
    @PostMapping(value = "/sandbox/apis", produces = { "application/hal+json" })
    APIListing newAPIListing(@RequestBody APIListing newAPIListing) {
        return repository.save(newAPIListing);    
    }

    // Patch an existing endpoint
    @PatchMapping(value ="/sandbox/apis/{id}", produces = { "application/hal+json" })
    APIListing updateAPIListing(@RequestBody APIListing updatedAPIListing) {
        // TODO: perform the update

        return updatedAPIListing;
    }

    // A VO to build the form data. This may not be needed in the future.
    class APIListingFormRep extends RepresentationModel<APIListingFormRep> {
        String name;
        String baseURI;

        APIListingFormRep(String name, String baseURI) {
            this.name = name;
            this.baseURI = baseURI;
        }

        public String getName() {
            return this.name;
        }

        public String getBaseURI() {
            return this.baseURI;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBaseURI(String baseURI) {
            this.baseURI = baseURI;
        }
    }

    // This endpoint serves the HAL-FORM for the api-listing rel
    @GetMapping(value = "/sandbox/rels/api-listing", produces = { "application/prs.hal-forms+json" })
    EntityModel<APIListingFormRep> newAPIForm(@RequestParam(required = false) Long _id) {

        // Create an empty VO so that we can generate a form based on it's properties
        APIListingFormRep apiListingForm = new APIListingFormRep("", "");

        // The use of '_id' is a non-standard implementation, but we're using it for now to avoid the complexity
        // of handling _hdoc parameters.
        if (_id != null) {

            // Try to retrieve an API listing matching this id
            APIListing apiListing = repository.findById(_id).get();
            apiListingForm.setName(apiListing.getName());
            apiListingForm.setBaseURI(apiListing.getBaseURI());

            // Return a model with an affordance to the update listing method. This will generate a form
            // through the middleware.
            return EntityModel.of(apiListingForm,
                linkTo(methodOn(APIListingController.class).newAPIForm(_id)).withSelfRel()
                    .andAffordance(afford(methodOn(APIListingController.class).updateAPIListing(apiListing))));            
        }

        // We need to create a link to self and an 'affordance' that points to the target of the form
        return EntityModel.of(apiListingForm,
            linkTo(methodOn(APIListingController.class).newAPIForm(null)).withSelfRel()
                .andAffordance(afford(methodOn(APIListingController.class).newAPIListing(null))));
    }
}