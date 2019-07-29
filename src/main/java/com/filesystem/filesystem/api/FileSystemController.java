package com.filesystem.filesystem.api;


import com.filesystem.filesystem.service.FileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/directory")
public class FileSystemController {

    @Autowired
    private FileSystemService fileSystem;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectoryResponse> add(@RequestBody DirectoryRequest student){

        DirectoryResponse response = new DirectoryResponse("home/files");  // status, error or success


        return new ResponseEntity<DirectoryResponse>(response, HttpStatus.OK);
    }
}
