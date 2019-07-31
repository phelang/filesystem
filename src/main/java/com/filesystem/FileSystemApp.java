package com.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@SpringBootApplication
@ComponentScan({
        "com.filesystem.springConfig", "com.filesystem.number_summarizer.service",
        "com.filesystem.controller"})
public class FileSystemApp {

    public static void main(String[] args){
        SpringApplication.run(FileSystemApp.class, args);
    }
}
