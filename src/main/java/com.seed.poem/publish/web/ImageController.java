package com.seed.poem.publish.web;

import com.seed.poem.JsonResult;

import com.seed.poem.publish.StorageFileNotFoundException;
import com.seed.poem.publish.service.FileService;
import com.seed.poem.publish.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class ImageController {
    private final FileService storageService;
    @Autowired
    private MaterialService materialService;

    @Autowired
    public ImageController(FileService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/media/image/category")
    public JsonResult<Map<String,List<String>>> userFiles(){
        return new JsonResult<Map<String,List<String>>>(materialService.getUserFiles());
    }

    @PostMapping("/media/image/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/media/image/upload")
    public JsonResult<List<String>> upload(@RequestParam(value="category", defaultValue="素材")String category,@RequestParam("file") MultipartFile[] file) {
        List<String> res=storageService.store(file);
        materialService.storeImageInfo(category,res);
        return new JsonResult<>(res);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
