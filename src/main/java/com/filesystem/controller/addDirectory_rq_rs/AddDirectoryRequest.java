package com.filesystem.controller.addDirectory_rq_rs;

public class AddDirectoryRequest {

    private String name;
    private String toDirectoryName;

    public AddDirectoryRequest() {
    }

    public AddDirectoryRequest(String name, String toDirectoryName) {
        this.name = name;
        this.toDirectoryName = toDirectoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToDirectoryName() {
        return toDirectoryName;
    }

    public void setToDirectoryName(String toDirectoryName) {
        this.toDirectoryName = toDirectoryName;
    }
}
