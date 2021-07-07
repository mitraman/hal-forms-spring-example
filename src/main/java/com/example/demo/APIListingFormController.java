package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* I'm still experimenting with this class to find the best way of rendering HAL-FORMS.
* The in-built spring-hateoas model feels too complicated and inflexible. Looking into building something myself.
* 
*/

@RestController
public class APIListingFormController {

    private final APIListingRepository repository;

    APIListingFormController(APIListingRepository repository) {
        this.repository = repository;
    }

    class formProperty {
        String name = "";
        Boolean required = false;
        String value = "";
        String prompt= "";
        String title = "";
        Boolean templated = false;
    }

    class formTemplate {
        String title = "";
        String method = "POST";
        String contentType = "application/json";
        List<formProperty> formProperties = new ArrayList<formProperty>();
    }

    class APIListingForm {
        private formTemplate _templates;

    }

    /*
    @GetMapping(value = "/sandbox/rels/api-listing", produces = {"application/prs.hal-forms+json"})
    EntityModel<APIListingForm> apiForm() {
       APIListingForm form = new APIListingForm();
       
       return EntityModel.of(form);
    }
    */
    
    
        @GetMapping(value = "/sandbox/rels/api-listing", produces = {"application/prs.hal-forms+json"})
    EntityModel<APIListing> apiForm() {
        
        //APIListing apiListing = repository.findById(id).get();

        /*
        return EntityModel.of(apiListing, 
            linkTo(methodOn(APIListingFormController.class).one(id)).withSelfRel()
        );
        */
        
        return EntityModel.of(new APIListing("test", "baseUri"));
    }    
    
}