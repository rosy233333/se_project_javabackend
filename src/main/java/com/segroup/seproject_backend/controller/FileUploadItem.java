package com.segroup.seproject_backend.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadItem {
    private MultipartFile file;
    private String postfix;
}
