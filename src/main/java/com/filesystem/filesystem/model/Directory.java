package com.filesystem.filesystem.model;

import java.util.List;

public class Directory {

    private String name;
    private List<File> files;
    private String path;

    public Directory(String name, List<File> files, String path) {
        this.name = name;
        this.files = files;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
