package org.gab.ClouDuck.controllers;

import org.gab.ClouDuck.aws.AWSUtils;
import org.gab.ClouDuck.responses.Response;
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
    public Response getFile(@RequestParam String key,
                            @RequestParam(required = false) String folder,
                            @RequestParam String filename) {
        
        folder = (folder == null) ? "" : folder;
        
        return awsUtils.get(key, folder, filename);
    }
    
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response saveFile(@RequestParam String key,
                             @RequestParam(required = false) String folder,
                             @RequestParam String filename,
                             @RequestParam MultipartFile file) {
        
        return awsUtils.save(key, folder, filename, file);
    }
    
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateFile(@RequestParam String key,
                               @RequestParam String folder,
                               @RequestParam String filename,
                               @RequestParam MultipartFile file) {
        
        return awsUtils.update(key, folder, filename, file);
    }
    
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteFile(@RequestParam String key,
                               @RequestParam(required = false) String folder,
                               @RequestParam String filename) {
        
        return awsUtils.delete(key, folder, filename);
    }
}
