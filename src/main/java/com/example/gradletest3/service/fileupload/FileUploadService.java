package com.example.gradletest3.service.fileupload;


import com.example.gradletest3.dao.fileupload.FileUploadDAO;
import com.example.gradletest3.dao.fileupload.FileUploadDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileUploadService {

    private final FileUploadDAO fileUploadDAO;

    public FileUploadService(FileUploadDAO fileUploadDAO) {
        this.fileUploadDAO = fileUploadDAO;
    }

    public int FileUpload(FileUploadDTO fileUploadDTO) {
        return fileUploadDAO.fileUpload(fileUploadDTO);
    }

    public List<FileUploadDTO> getFileList(int b_num) {
        return fileUploadDAO.getFileList(b_num);
    }

    public FileUploadDTO getdownloadFile(int f_num) {
        return fileUploadDAO.getdownloadFile(f_num);
    }

    public int fileDelete(int f_num) {
        return fileUploadDAO.fileDelete(f_num);
    }
}
