package com.filesystem.filesystem.model;

public class File {

    private String name;
    private String extension;
    private String parentName;
    private String path;

    public File() {
    }

    public File(String name, String extension, String path) {
        this.name = name;
        this.extension = extension;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
