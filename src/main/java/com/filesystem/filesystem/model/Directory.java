package com.filesystem.filesystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A file system tree structure that supports
 * adding, deleting
 * directories and sub directories
 *
 * @param <T>
 * @Author Qhu
 */
public class Directory<T> {

    private T name;
    private Directory<T> parent = null;
    private List<Directory<T>> directories = new ArrayList<>();
    private List<File> files; // Can also be HashMap, Map data structure


    public Directory(T name){
        this.name = name;
        this.files = null;
    }

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

    /***
     * Creates a directory. And Set the parent of the
     * new directory using this keyword object reference
     * directory. Parent directory is this directory
     * (Recursive)
     *
     * @param Directory<T> The type of directory.
     * @return The new directory.
     */
    public Directory<T> addDirectory(Directory<T> directory) {
        directory.setParent(this);
        this.directories.add(directory);
        return directory;
    }

    /**
     * Retutn the list of sub direcories.
     *
     * @return directory list.
     */
    public List<Directory<T>> getSubDirectories(){
        return directories;
    }


}
