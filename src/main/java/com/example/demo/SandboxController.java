package com.example.demo;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// The root resource for the HAL sandbox API

@RestController
public class SandboxController {

    Logger log = LoggerFactory.getLogger(SandboxController.class);

    // @RequestMapping(value = "/sandbox", produces = {"application/hal+json"})
    // @GetMapping(value = "/sandbox", produces = {"application/hal+json"})
    @GetMapping(value = "/sandbox")
    public HttpEntity<SandboxModel> start() {

        SandboxModel sandbox = new SandboxModel("Demo Sandbox");        

        // Create the rel URI by getting a link to the HAL Forms method for creating new API listings
        String apiListRel = linkTo(methodOn(APIListingController.class).newAPIForm(null)).toUri().toString();

        // Create a link to the api listing controller
        Link apiListLink = linkTo(methodOn(APIListingController.class).all()).withRel(apiListRel);

        // Add an affordance to the get listings link, tying it to the POST create listing operation. This should generate
        // a HAL-FORMS representation automatically in the middleware.
        apiListLink.andAffordance(afford(methodOn(APIListingController.class).newAPIListing(null)));
            
        // Add the links to our sandbox representation
        sandbox.add(linkTo(methodOn(SandboxController.class).start()).withSelfRel());
        sandbox.add(apiListLink);

        return new ResponseEntity<>(sandbox, HttpStatus.OK);
        
    }    
}