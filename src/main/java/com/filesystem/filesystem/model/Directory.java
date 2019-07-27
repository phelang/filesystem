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
    private String path;
    private Directory<T> parent = null;
    private List<Directory<T>> directories = new ArrayList<>();
    private List<File> files; // Can also be HashMap, Map data structure


    public Directory(T name){
        this.name = name;
        this.path = name.toString();
        this.files = null;
    }

    public Directory(T name, List<File> files) {
        this.name = name;
        this.path = name.toString();
        this.files = files;
    }

    /**
     * Upwards recursive call using parent directory as object reference
     * traverse from parent this directory to parent of immediate next upper level parent
     * until root directory is found with null parent reference
     *
     * @return Directory<T>
     */
    public Directory<T> getHomeDirectory(){
        if(parent == null){
            return this;
        }
        return parent.getHomeDirectory();
    }

    public T getName() {
        return name;
    }

    public void setName(T name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    private void setParent(Directory<T> parent){
        this.parent = parent;
    }

    public Directory<T> getParent(){
        return parent;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
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
        directory.setPath(directory.parent.getPath() + "/" + directory.getName());
        this.directories.add(directory);
        return directory;
    }

    /**
     * Return the list of sub direcories.
     *
     * @return directory list.
     */
    public List<Directory<T>> getSubDirectories(){
        return directories;
    }

    /**
     * Depth first Tree Traversal.
     * Starts from a give directory and
     * loops through all sub directories
     *
     * @param Direcotry<T>
     * @return Directory<T>
     * **/

    public Directory<T> searchDirectory(Directory<T> directory, T searchFor) {
        Directory<T> find = null;
        for (Directory<T> dir : directory.getSubDirectories()) {
            if (dir.getName().equals(searchFor)) {
                find = dir;
                break;
            } else
                searchDirectory(dir, searchFor);
        }
        return find;
    }

    /**
     * Deletes the selected directory using this object reference
     * to identity the directory to be deleted.
     *
     * @return Directory<T>
     */
    public Directory<T> deleteDirectory(){
        if(parent != null){
            this.parent.getSubDirectories().remove(this);
        } else {
            deleteDirectory();
        }
        this.getSubDirectories().clear();
        return this;
    }

    /**
     * Rename the directory name.
     *
     * @return Directory<T>
     */

    public Directory<T> updateDirectory(T name){
        this.setName(name);
        return this;
    }
}
