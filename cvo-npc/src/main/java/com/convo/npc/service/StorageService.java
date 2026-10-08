package com.convo.npc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface StorageService {
    String upload(MultipartFile file) throws IOException;
    File download(String url) throws IOException;
}
