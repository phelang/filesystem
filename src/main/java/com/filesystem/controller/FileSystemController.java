package com.filesystem.controller;


import com.filesystem.controller.addDirectory_rq_rs.AddDirectoryRequest;
import com.filesystem.controller.addDirectory_rq_rs.DirectoryResponse;
import com.filesystem.controller.getTree_req_rs.DirectoryRequest;
import com.filesystem.controller.moveDirectory_rq_rs.MoveDirectoryRequest;
import com.filesystem.controller.updateDirectory_rq_rs.UpdateDirectoryRequest;
import com.filesystem.filesystem.model.Directory;
import com.filesystem.filesystem.model.File;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/directory")
public class FileSystemController {

    private static Directory<String> home = new Directory<>("home");

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectoryResponse> createDirectory(@RequestBody AddDirectoryRequest request){

        Directory<String> requestNewDirectory = new Directory<String>(request.getName());
        Directory<String> findToDirectory = home.searchDirectory(home, request.getToDirectoryName());

        DirectoryResponse response = new DirectoryResponse();
        if(request.getToDirectoryName().equals(home.getName())){
            home
                    .addDirectory(new Directory<>(request.getName()));
        }else {
            Directory<String> newDir = findToDirectory.addDirectory(requestNewDirectory);

            response.setName(newDir.getName());
        }
        return new ResponseEntity<DirectoryResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectoryResponse> deleteDirectory(@RequestBody DirectoryRequest request){

        Directory<String> findDeleteDirectory = home.searchDirectory(home, request.getName());

        Directory<String> deleted = findDeleteDirectory.deleteDirectory();

        DirectoryResponse response = new DirectoryResponse(deleted.getName());

        return new ResponseEntity<DirectoryResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectoryResponse> updateDirectory(@RequestBody UpdateDirectoryRequest request){

        Directory<String> findUpdateDirectory = home.searchDirectory(home, request.getToUpdateName());

        Directory<String> updateDirectory = findUpdateDirectory.updateDirectory(request.getName());

        DirectoryResponse response = new DirectoryResponse(updateDirectory.getName());

        return new ResponseEntity<DirectoryResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/move", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectoryResponse> updateDirectory(@RequestBody MoveDirectoryRequest request){

        Directory<String> fromDirectory = home.searchDirectory(home, request.getMoveDirectoryName());

        Directory<String> toDirectory = home.searchDirectory(home, request.getToDirectoryName());

        boolean moved = fromDirectory.moveDirectory(toDirectory);

        DirectoryResponse response = new DirectoryResponse();

        return new ResponseEntity<DirectoryResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public ResponseEntity<String> getTree(){

        JSONObject jsonObject = toJSON(home);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        String response = jsonArray.toString();

        return new ResponseEntity<String>( response, HttpStatus.OK);
    }

    public JSONObject toJSON(Directory<String> directory) {
        try {
            JSONObject json = new JSONObject();
            json.put("label", directory.getName());
            json.put("id", "role1");

            List subDirectories = new ArrayList<JSONObject>();

            List files = new ArrayList<JSONObject>();

            List<Directory<String>> directories = directory.getSubDirectories();

            for (Directory<String> subdir : directories) {

                if (isSubDirectoryOf(directory,subdir)) {
                    for(File f: subdir.getFiles()){
                        files.add(f.getName());
                    }
                    subDirectories.add(toJSON(subdir));
                }
            }
            json.put("children", subDirectories);
            //json.put("fils", files);

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
