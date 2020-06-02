package br.com.anibal.armory.simpleserver.controllers;

import br.com.anibal.armory.simpleserver.services.storage.StorageException;
import br.com.anibal.armory.simpleserver.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@CrossOrigin(allowedHeaders = "*")
public class UploadController {

    private final StorageService storageService;

    @Autowired
    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/file")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            storageService.store(file);
        } catch (StorageException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
