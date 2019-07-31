package com.filesystem.controller.getTree_req_rs;

public class DirectoryRequest {

    private String name;

    public DirectoryRequest() {
    }

    public DirectoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
