package com.filesystem.filesystem.api;

public class DirectoryResponse {

    private String name;

    public DirectoryResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
