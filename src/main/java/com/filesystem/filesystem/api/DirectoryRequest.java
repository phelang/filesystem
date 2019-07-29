package com.filesystem.filesystem.api;

public class DirectoryRequest {

    private String newDirectoryName;
    private String toDirectoryName;

    public String getNewDirectoryName() {
        return newDirectoryName;
    }

    public void setNewDirectoryName(String newDirectoryName) {
        this.newDirectoryName = newDirectoryName;
    }

    public String getToDirectoryName() {
        return toDirectoryName;
    }

    public void setToDirectoryName(String toDirectoryName) {
        this.toDirectoryName = toDirectoryName;
    }
}
