package com.filesystem.filesystem.service;

import com.filesystem.filesystem.model.Directory;
import org.json.simple.JSONObject;

public interface FileSystemService {

    Directory<String> getRoot();
    String addDirectory(String toDirectory, String name);
    Directory<String>  searchDirectory(String searchFor);
    String deleteDirectory(String deleteByName);
    Directory<String> updateDirectory(String fromName, String toName);
    boolean moveDirectory(String fromDirectory, String toDirectory);
    void copyDirectory(Directory<String> toDirectory, Directory<String> fromDirectory);

    JSONObject directoryTreeToJSON();
}
