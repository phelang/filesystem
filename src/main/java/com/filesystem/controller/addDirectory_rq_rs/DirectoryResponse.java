package com.filesystem.controller.addDirectory_rq_rs;

public class DirectoryResponse {

    private String name;
    private String path;

    public DirectoryResponse() {
    }

    public DirectoryResponse(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
