package com.seed.poem.upload.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class NamedFile implements MultipartFile{
      private MultipartFile realFile;
      private String fileName;

    public NamedFile(MultipartFile realFile,String fileName) {
        this.realFile = realFile;
        this.fileName = fileName;
    }

    public MultipartFile getRealFile() {
        return realFile;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String getName() {
        return realFile.getName();
    }

    @Override
    public String getOriginalFilename() {
        return fileName;
    }

    @Override
    public String getContentType() {
        return realFile.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return realFile.isEmpty();
    }

    @Override
    public long getSize() {
        return realFile.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return realFile.getBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return realFile.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
           realFile.transferTo(dest);
    }
}
