package com.filesystem.controller.updateDirectory_rq_rs;

public class UpdateDirectoryRequest {

    private String toUpdateName;
    private String name;

    public UpdateDirectoryRequest(){}

    public UpdateDirectoryRequest(String toUpdateName, String name) {
        this.toUpdateName = toUpdateName;
        this.name = name;
    }

    public String getToUpdateName() {
        return toUpdateName;
    }

    public void setToUpdateName(String toUpdateName) {
        this.toUpdateName = toUpdateName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
