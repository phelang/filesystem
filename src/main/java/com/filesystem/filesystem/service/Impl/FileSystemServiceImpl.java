package com.filesystem.filesystem.service.Impl;

import com.filesystem.filesystem.model.Directory;
import com.filesystem.filesystem.service.FileSystemService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileSystemServiceImpl implements FileSystemService {

    private Directory<String> home = new Directory<>("home");

    @Override
    public Directory<String> getRoot() {
        return home;
    }

    @Override
    public String addDirectory(String toDirectory, String newDirectory) {

        /**
         * First if statement, check that directory tree is empty
         *
         * Second if statement, check that user is adding to root
         *
         * third if statement, search the directory the user is trying to add
         * new directory to and add the new directory.
         */
        if(home.getSubDirectories().size() == 0) {
            home.addDirectory(new Directory<>(newDirectory));
            return "success";
        } else if(home.getName().equals(toDirectory)){
            home.addDirectory(new Directory<>(newDirectory));
            return "success";
        }
        else {
            Directory<String> findDirectory = home.searchDirectory(home, toDirectory);

            if (findDirectory != null) {
                findDirectory.addDirectory(new Directory<>(newDirectory));
                return "success";
            } else {
                return "error";
            }
        }
    }

    @Override
    public Directory<String> searchDirectory(String searchFor) {

        return home.searchDirectory(home, searchFor);
    }

    @Override
    public String deleteDirectory(String deleteByName) {

        Directory<String> deleteThis = home.searchDirectory(home, deleteByName);

        if(deleteThis != null) {
            deleteThis.deleteDirectory();
            return "success";
        }else {
            return "error";
        }
    }

    @Override
    public Directory<String> updateDirectory(String fromName, String toName) {

        Directory<String> findToUpdateDirectory = home.searchDirectory(home, fromName);

        if(findToUpdateDirectory != null) {
           return findToUpdateDirectory.updateDirectory(toName);
        }else {
            return null;
        }
    }

    @Override
    public boolean moveDirectory(String fromDirectory, String toDirectory) {

        Directory<String> from = home.searchDirectory(home, fromDirectory);
        Directory<String> to = home.searchDirectory(home, toDirectory);

        if (from != null){
            return from.moveDirectory(to);
        } else {
            return false;
        }
    }

    @Override
    public void copyDirectory(Directory<String> toDirectory, Directory<String> fromDirectory) {

    }

    @Override
    public JSONObject directoryTreeToJSON() {
        return toJSON(home);
    }

    public JSONObject toJSON(Directory<String> directory) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", directory.getName());

            List subDirectories = new ArrayList<JSONObject>();

            List<Directory<String>> directories = directory.getSubDirectories();

            for (Directory<String> subdir : directories) {

                if (isSubDirectoryOf(directory,subdir)) {
                    subDirectories.add(toJSON(subdir));
                }
            }
            json.put("directories", subDirectories);

            return json;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private boolean isSubDirectoryOf(Directory<String> parentDirectory, Directory<String> subDirectory){
        if(subDirectory.getParent().equals(parentDirectory)){
            return true;
        } else {
            return false;
        }
    }
}
