package com.miarma.cynthia.repository;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileRepository {

    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteFile(String filename) throws IOException;

    String storeResized(MultipartFile file,int width) throws Exception;

    String storeVideoResized(MultipartFile file,int width) throws Exception;

    void deleteAll();
}