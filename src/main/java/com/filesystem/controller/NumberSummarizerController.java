package com.filesystem.controller;

import com.filesystem.number_summarizer.service.SummarizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/summarizer")
public class NumberSummarizerController {

    @Autowired
    private SummarizerService summarizerService;

    @RequestMapping(value = "/numbers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NumberResponse> addStudent(@RequestBody NumberRequest student){

        String summary = summarizerService.summarizeCollection(student.getNumbers());

        return new ResponseEntity<NumberResponse>(new NumberResponse(summary), HttpStatus.OK);
    }

}