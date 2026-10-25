package com.convo.cvofile.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface CvoFileService {
    String create(MultipartFile file) throws IOException;
    File get(String uuid) throws IOException;
}
