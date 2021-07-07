package com.example.demo;

import org.springframework.hateoas.RepresentationModel;

public class SandboxModel extends RepresentationModel<SandboxModel>{
    
    String sandboxName;

    SandboxModel(String name) {
        this.sandboxName = name;
    }

    public String getSandboxName() {
        return this.sandboxName;
    }

    public void setSandboxName(String name) {
        this.sandboxName = name;
    }
}