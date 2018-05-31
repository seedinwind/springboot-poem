package com.seed.poem.publish.service;

import com.seed.poem.JsonResult;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface FileService {

    void init();

    JsonResult<List<String>> store(MultipartFile[] file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
