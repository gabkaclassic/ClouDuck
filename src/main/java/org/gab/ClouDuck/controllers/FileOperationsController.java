package org.gab.ClouDuck.controllers;

import org.gab.ClouDuck.aws.AWSUtils;
import org.gab.ClouDuck.handlers.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("files")
public class FileOperationsController {
    
    private final AWSUtils awsUtils;
    
    @Autowired
    public FileOperationsController(AWSUtils awsUtils) {
    
        this.awsUtils = awsUtils;
    }
    
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getFile(@RequestParam(required = false) String folder, @RequestParam String filename) {
        
        folder = (folder == null) ? "" : folder;
        
        return awsUtils.get(folder, filename);
    }
    
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response saveFile(@RequestParam(required = false) String folder,
                             @RequestParam String filename,
                             @RequestParam MultipartFile file) {
        
        return awsUtils.save(folder, filename, file);
    }
    
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateFile(@RequestParam String folder,
                             @RequestParam String filename,
                             @RequestParam MultipartFile file) {
        
        return awsUtils.update(folder, filename, file);
    }
    
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteFile(@RequestParam(required = false) String folder, @RequestParam String filename) {
        
        return awsUtils.delete(folder, filename);
    }
}
