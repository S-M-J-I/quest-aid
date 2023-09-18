package com.project.questaidbackend.controllers;

import com.project.questaidbackend.services.interfaces.IFileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.tomcat.util.codec.binary.Base64;

@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class FileController {

    private IFileStorageService fileStorageService;

    @GetMapping("/load/{desiredPath}/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String desiredPath, @PathVariable String filename) {
        Resource file = fileStorageService.load(filename, desiredPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
