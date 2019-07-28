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
    private List<File> files; // Can also be HashMap, Map data structure

    public Directory(T name){
        this.name = name;
        this.path = name.toString();
        this.level = 0;
        this.files = null;
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

    /**
     * Parallel copy method. Set mutable values for a new Directory
     */
    private boolean firstCopyDirectory = true;
    public void copyDirectory(Directory<T> toDirectory, Directory<T> fromDirectory){

        Directory<T> copy = new Directory<>(this.getName());

//        if(firstCopyDirectory){

            //try {
                // copy = (Directory<T>)this.clone();

                //copy.setName(this.getName());
                copy.setPath(toDirectory.getPath() + "/" + this.getName());
                copy.setLevel(toDirectory.getLevel() + 1 );
                copy.setParent(toDirectory);
                // set the directories
                copy.setFiles(toDirectory.getFiles());




/*            } catch (CloneNotSupportedException e){
                e.printStackTrace();
            }*/

            System.out.println("");
            System.out.print("Original Name : " + this.name);
            System.out.print(" | Hash Code : " + this.hashCode());
            System.out.print(" | Parent : " + this.parent.name);

            System.out.println();

            System.out.print("Copy Name : " + copy.getName());
            System.out.print(" | Hash Code : " + copy.hashCode());
            System.out.print(" | Parent : " + copy.getParent().name);

            System.out.println();

            toDirectory.addDirectory(copy); // add the copied directory if it is the first root directory being copied
            firstCopyDirectory = false;
  //      } else {
            //copy.getParent();
    //    }

        List<Directory<T>> directories = this.getSubDirectories(); // GET THE COPY OF thisDirectory

        for(Directory<T> dir: directories){

            Directory<T> subDirectory = new Directory<>(dir.getName());

            subDirectory.setPath(copy.getPath() + "/" + dir.getName());
            subDirectory.setLevel(copy.getLevel() + 1 );
            subDirectory.setParent(copy);
            // set the directories
            copy.setFiles(copy.getFiles());


            copy.addDirectory(subDirectory);

            /*try{
                copySubDirectory = (Directory<T>)dir.clone();
            }catch (CloneNotSupportedException e){
                e.printStackTrace();
            }

            if(firstCopyDirectory){
                //copySubDirectory.setParent(copy);
                copy.addDirectory(copySubDirectory);

                System.out.println();
            }

            // toDirectory.addDirectory(copySubDirectory);

            System.out.println("\t\tOriginal Sub Directory PATH : " + dir.getPath() + " | hashCode " + dir.hashCode() + " | Parent " + dir.getParent().getName() + " <- " + dir.getParent().hashCode());
            System.out.println("\t\tCopy Sub Directory PATH : " + copySubDirectory.getPath() + " | hashCode " + copySubDirectory.hashCode() + " | Parent " + copySubDirectory.getParent().getName() + " <- " + copySubDirectory.getParent().hashCode());


            System.out.println("*** TO DIRECTORY - "+ toDirectory.getPath() + " | " + toDirectory.getName() + " | " + toDirectory.hashCode()+ "***");
            System.out.println("*** TO DIRECTORY UNCHANGED - "+ toDirectoryDontChane.getPath() + " | " + toDirectoryDontChane.getName() + " | " + toDirectoryDontChane.hashCode()+ "***");
*/
            //toDirectory.addDirectory(copySubDirectory);

            //toDirectory.addDirectory(copySubDirectory);
            //dir.setPath(directory.getPath() + "/" + dir.getName());

            //dir.copyDirectory(dir);
        }

        /* toDirectory.addDirectory(this);

        if(this.getSubDirectories().size() > 0){

            this.updateSubDirectoryPaths(this);
        }*/
    }
}
