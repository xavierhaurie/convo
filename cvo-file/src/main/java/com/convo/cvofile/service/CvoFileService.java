package com.convo.cvofile.service;

import com.convo.cvofile.dto.CvoFileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface CvoFileService {
    CvoFileDTO createCvoFile(CvoFileDTO cvoFileDTO, MultipartFile file) throws IOException;
    File get(String uuid) throws IOException;
}
