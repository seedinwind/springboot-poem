package com.seed.poem.upload;

import com.seed.poem.JsonResult;
import com.seed.poem.Util;
import com.seed.poem.auth.model.User;
import com.seed.poem.upload.model.NamedFile;
import com.seed.poem.upload.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class UploadController {
    private final FileService storageService;

    @Autowired
    public UploadController(FileService storageService) {
        this.storageService = storageService;
    }

//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    @PostMapping("/upload")
    public JsonResult<String> handleFileUpload(@RequestParam("file") MultipartFile file) {

        NamedFile named=new NamedFile(file,createFileName());
        storageService.store(named);
        return JsonResult.builder().data(named.getFileName()).build();
    }

    private String createFileName() {
        String userid=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String fileName=userid+System.currentTimeMillis();
        return Util.md5Hex(fileName);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
