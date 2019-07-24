package com.filesystem.filesystem.model;

import java.util.ArrayList;
import java.util.List;

public class Directory<T> {

    private T name;
    private Directory<T> parent = null;
    private List<Directory<T>> directories = new ArrayList<>();
    private List<File> files;

    public Directory(T name, List<File> files) {
        this.name = name;
        this.files = files;
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    private void setParent(Directory<T> parent){
        this.parent = parent;
    }

    private Directory<T> getParent(){
        return parent;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    // create sub directory
    public Directory<T> addDirectory(Directory<T> directory) {
        directory.setParent(this);
        this.directories.add(directory);
        return directory;
    }
}
