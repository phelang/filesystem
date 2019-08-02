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
public class Directory<T> implements Cloneable{

    private T name;
    private String path;
    private int level;
    private Directory<T> parent = null;
    private List<Directory<T>> directories = new ArrayList<>();
    private List<File> files;

    public Directory(T name){
        this.name = name;
        this.path = name.toString();
        this.level = 0;
        this.files = new ArrayList<>();
    }

    public Directory(T name, List<File> files) {
        this.name = name;
        this.path = name.toString();
        this.level = 0;
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

    public void setName(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private void setParent(Directory<T> parent){
        this.parent = parent;
    }

    public Directory<T> getParent(){
        return parent;
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
        directory.setPath(this.getPath() + "/" + directory.getName());
        directory.setLevel(findLevel());
        this.directories.add(directory);
        return directory;
    }

    /**
     * Find level of directory using directory path.
     *
     * @return length
     */

    private int findLevel(){
        String[] pathVariables = this.path.split("[/]");

        return pathVariables.length;
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

    public final Directory<T> searchDirectory(Directory<T> directory, T searchFor, Directory<T> find) {
        for (Directory<T> dir : directory.getSubDirectories()) {
            if (dir.getName().equals(searchFor)) {
                return dir;
            } else {
                return searchDirectory(dir, searchFor, find);
            }
        }
        return null;
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
        if(this.getSubDirectories().size() > 0)
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


    /**
     * Move directory from current directory to another directory in file system.
     *
     * @return Directory<T>
     */
    public boolean moveDirectory(Directory<T> toDirectory){

        String[] fromPathVariables = this.path.split("[/]");
        String[] toPathVariables = toDirectory.getPath().split("[/]");

        if(fromPathVariables[this.getLevel()].equals(toPathVariables[this.getLevel()])){
            /*
                Process not allowed
             */
            return false;
        } else{
            /**
             * Move this directory,
             * Because by default java variables point to object reference,
             * the moved directory is the same object reference simple changes if the parent directory,
             * And that there is no need to delete moved directory
             */
            toDirectory.addDirectory(this);

            /** check that Directory being moved has sub directories
             * if so then recursively amend the path variables of
             * sub directories
             */

            if(this.getSubDirectories().size() > 0){

                this.updateSubDirectoryPaths(this);
            }

            return true;
        }

    }

    /**
     * Depth travesal on only sub directories that belong to the moved directory
     * Use path of parent to check previous level
     *
     * if previous home/hanlie/Music is the same as sub directory, rename path to
     * new parent plus moved directory plus chil name
     *
     * @param
     */
    public void updateSubDirectoryPaths(Directory<T> directory){

        List<Directory<T>> directories = directory.getSubDirectories();

        for(Directory<T> dir: directories){

            dir.setPath(directory.getPath() + "/" + dir.getName());

            this.updateSubDirectoryPaths(dir);
        }
    }

    private boolean flag = false;

    /**
     * Parallel copy method. Set mutable values for a new Directory
     *
     * Find this directory, using search, given name
     *
     *
     */

    public void copyDirectory(Directory<T> to, Directory<T> from){

        Directory<T> copy = new Directory<>(this.getName());

        if (flag == false) {
            copy = new Directory<>(from.getName());
            copy.setPath(to.getPath() + "/" + copy.getName());
            copy.setParent(to);
            flag = true;
        }
        for (Directory<T> dir: from.getSubDirectories()){

            Directory<T> copyC = new Directory<>(dir.getName());
            copyC.setPath(to.getPath() + "/" + from.getName() + "/" + dir.getName());
            copyC.setParent(copy);

            // Directory<T> copyChile = new Directory<>(dir.getName());

            copyDirectory(dir, copyC);

        }
    }

    public boolean createFile(Directory<T> toDirectory, String fileName, String extension){

        File file = new File();

        file.setName(fileName);
        file.setExtension(extension);
        file.setParentName(toDirectory.getName().toString());
        file.setPath((toDirectory.getPath() + "/" + fileName));

        return toDirectory.getFiles().add(file);
    }

    static File foundFile = null;

    public File searchFile(Directory<T> directory, String fileName){
        foundFile = null;
        for (Directory<T> dir : directory.getSubDirectories()) {
            for(File f: dir.getFiles()) {
                if(f.getName().equals(fileName)){
                    foundFile = f;
                    break;
                }
            }
            if(foundFile != null)
                break;
            searchFile(dir, fileName);
        }
        return foundFile;
    }

    public boolean deleteFile(Directory<T> directory, File file){
        return directory.getFiles().remove(file);
    }

    public File updateFile(Directory<T> directory, File oldFile, File updatedFile){

        int index = directory.getFiles().indexOf(oldFile);

        return directory.getFiles().set(index, updatedFile);
    }
    public void moveFile(Directory<T> fromDirectory, Directory<T> toDirectory, File file){

        fromDirectory.getFiles().remove(file);
        toDirectory.getFiles().add(file);

    }

    public void copyFile(Directory<T> directory){

    }

    public Directory<String> searchDirectory2(Directory<String> directory, String val){
        for(Directory<String> dir: directory.getSubDirectories()){
            if(dir.getName().equals("TO")){
               return dir;
            }
            return searchDirectory2(dir, val);
        }
        return null;
    }

    public void printDirectoryTree(Directory<T> directory, String appender){
        System.out.println(appender + directory.getName());
        for (File f : directory.getFiles()) {
            System.out.println("    " + appender + f.getName());
        }
        for(Directory<T> dir: directory.getSubDirectories()){
            printDirectoryTree(dir, appender + appender);
        }
    }
}



